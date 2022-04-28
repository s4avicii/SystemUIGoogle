package com.google.android.systemui.columbus.gates;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: TransientGate.kt */
public final class TransientGate$resetGate$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ TransientGate this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TransientGate$resetGate$1(TransientGate transientGate) {
        super(0);
        this.this$0 = transientGate;
    }

    public final Object invoke() {
        this.this$0.setBlocking(false);
        return Unit.INSTANCE;
    }
}
