package com.android.systemui.statusbar.commandline;

import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: CommandRegistry.kt */
final class CommandRegistry$initializeCommands$1 extends Lambda implements Function0<Command> {
    public final /* synthetic */ CommandRegistry this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CommandRegistry$initializeCommands$1(CommandRegistry commandRegistry) {
        super(0);
        this.this$0 = commandRegistry;
    }

    public final Object invoke() {
        Objects.requireNonNull(this.this$0);
        return new PrefsCommand();
    }
}
