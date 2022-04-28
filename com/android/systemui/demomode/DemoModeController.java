package com.android.systemui.demomode;

import android.content.Context;
import android.os.Bundle;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoModeController.kt */
public final class DemoModeController implements CallbackController<DemoMode>, Dumpable {
    public final DemoModeController$broadcastReceiver$1 broadcastReceiver;
    public final Context context;
    public final GlobalSettings globalSettings;
    public boolean initialized;
    public boolean isInDemoMode;
    public final LinkedHashMap receiverMap;
    public final ArrayList receivers = new ArrayList();
    public final DemoModeController$tracker$1 tracker;

    public final void enterDemoMode() {
        List<DemoModeCommandReceiver> list;
        this.isInDemoMode = true;
        Assert.isMainThread();
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
        }
        for (DemoModeCommandReceiver onDemoModeStarted : list) {
            onDemoModeStarted.onDemoModeStarted();
        }
    }

    public final void exitDemoMode() {
        List<DemoModeCommandReceiver> list;
        this.isInDemoMode = false;
        Assert.isMainThread();
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
        }
        for (DemoModeCommandReceiver onDemoModeFinished : list) {
            onDemoModeFinished.onDemoModeFinished();
        }
    }

    public final void addCallback(DemoMode demoMode) {
        for (String str : demoMode.demoCommands()) {
            if (this.receiverMap.containsKey(str)) {
                Object obj = this.receiverMap.get(str);
                Intrinsics.checkNotNull(obj);
                ((List) obj).add(demoMode);
            } else {
                throw new IllegalStateException("Command (" + str + ") not recognized. See DemoMode.java for valid commands");
            }
        }
        synchronized (this) {
            this.receivers.add(demoMode);
        }
        if (this.isInDemoMode) {
            demoMode.onDemoModeStarted();
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        List<DemoModeCommandReceiver> list;
        printWriter.println("DemoModeController state -");
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.isInDemoMode, "  isInDemoMode=", printWriter);
        DemoModeController$tracker$1 demoModeController$tracker$1 = this.tracker;
        Objects.requireNonNull(demoModeController$tracker$1);
        printWriter.println(Intrinsics.stringPlus("  isDemoModeAllowed=", Boolean.valueOf(demoModeController$tracker$1.isDemoModeAvailable)));
        printWriter.print("  receivers=[");
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
        }
        for (DemoModeCommandReceiver demoModeCommandReceiver : list) {
            printWriter.print(Intrinsics.stringPlus(" ", demoModeCommandReceiver.getClass().getSimpleName()));
        }
        printWriter.println(" ]");
        printWriter.println("  receiverMap= [");
        for (String str : this.receiverMap.keySet()) {
            printWriter.print("    " + str + " : [");
            Object obj = this.receiverMap.get(str);
            Intrinsics.checkNotNull(obj);
            Iterable<DemoMode> iterable = (Iterable) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(iterable, 10));
            for (DemoMode demoMode : iterable) {
                arrayList.add(demoMode.getClass().getSimpleName());
            }
            printWriter.println(Intrinsics.stringPlus(CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", (String) null, (String) null, (Function1) null, 62), " ]"));
        }
    }

    public final void removeCallback(DemoMode demoMode) {
        synchronized (this) {
            for (String str : demoMode.demoCommands()) {
                Object obj = this.receiverMap.get(str);
                Intrinsics.checkNotNull(obj);
                ((List) obj).remove(demoMode);
            }
            this.receivers.remove(demoMode);
        }
    }

    public DemoModeController(Context context2, DumpManager dumpManager, GlobalSettings globalSettings2) {
        this.context = context2;
        this.globalSettings = globalSettings2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList<String> arrayList = DemoMode.COMMANDS;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(arrayList, 10));
        for (String put : arrayList) {
            arrayList2.add((List) linkedHashMap.put(put, new ArrayList()));
        }
        this.receiverMap = linkedHashMap;
        this.tracker = new DemoModeController$tracker$1(this, this.context);
        this.broadcastReceiver = new DemoModeController$broadcastReceiver$1(this);
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        Assert.isMainThread();
        DemoModeController$tracker$1 demoModeController$tracker$1 = this.tracker;
        Objects.requireNonNull(demoModeController$tracker$1);
        if (demoModeController$tracker$1.isDemoModeAvailable) {
            if (Intrinsics.areEqual(str, "enter")) {
                enterDemoMode();
            } else if (Intrinsics.areEqual(str, "exit")) {
                exitDemoMode();
            } else if (!this.isInDemoMode) {
                enterDemoMode();
            }
            Object obj = this.receiverMap.get(str);
            Intrinsics.checkNotNull(obj);
            for (DemoMode dispatchDemoCommand : (Iterable) obj) {
                dispatchDemoCommand.dispatchDemoCommand(str, bundle);
            }
        }
    }
}
