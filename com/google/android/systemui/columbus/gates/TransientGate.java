package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: TransientGate.kt */
public abstract class TransientGate extends Gate {
    public final Function0<Unit> resetGate = new TransientGate$resetGate$1(this);
    public final Handler resetGateHandler;

    public final void blockForMillis(long j) {
        this.resetGateHandler.removeCallbacks(new TransientGate$sam$java_lang_Runnable$0(this.resetGate));
        setBlocking(true);
        this.resetGateHandler.postDelayed(new TransientGate$sam$java_lang_Runnable$0(this.resetGate), j);
    }

    public TransientGate(Context context, Handler handler) {
        super(context);
        this.resetGateHandler = handler;
    }
}
