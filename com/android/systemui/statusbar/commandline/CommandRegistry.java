package com.android.systemui.statusbar.commandline;

import android.content.Context;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CommandRegistry.kt */
public final class CommandRegistry {
    public final LinkedHashMap commandMap = new LinkedHashMap();
    public final Context context;
    public boolean initialized;
    public final Executor mainExecutor;

    public final synchronized void registerCommand(String str, Function0<? extends Command> function0, Executor executor) {
        if (this.commandMap.get(str) == null) {
            this.commandMap.put(str, new CommandWrapper(function0, executor));
        } else {
            throw new IllegalStateException("A command is already registered for (" + str + ')');
        }
    }

    public final void help(PrintWriter printWriter) {
        printWriter.println("Usage: adb shell cmd statusbar <command>");
        printWriter.println("  known commands:");
        for (String stringPlus : this.commandMap.keySet()) {
            printWriter.println(Intrinsics.stringPlus("   ", stringPlus));
        }
    }

    public final void onShellCommand(PrintWriter printWriter, String[] strArr) {
        boolean z = true;
        if (!this.initialized) {
            this.initialized = true;
            registerCommand("prefs", new CommandRegistry$initializeCommands$1(this));
        }
        if (strArr.length != 0) {
            z = false;
        }
        if (z) {
            help(printWriter);
            return;
        }
        CommandWrapper commandWrapper = (CommandWrapper) this.commandMap.get(strArr[0]);
        if (commandWrapper == null) {
            help(printWriter);
            return;
        }
        FutureTask futureTask = new FutureTask(new CommandRegistry$onShellCommand$task$1(commandWrapper.commandFactory.invoke(), printWriter, strArr));
        commandWrapper.executor.execute(new CommandRegistry$onShellCommand$1(futureTask));
        futureTask.get();
    }

    public CommandRegistry(Context context2, Executor executor) {
        this.context = context2;
        this.mainExecutor = executor;
    }

    public final synchronized void registerCommand(String str, Function0<? extends Command> function0) {
        registerCommand(str, function0, this.mainExecutor);
    }
}
