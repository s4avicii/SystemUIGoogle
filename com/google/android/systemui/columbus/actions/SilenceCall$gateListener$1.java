package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: SilenceCall.kt */
public final class SilenceCall$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ SilenceCall this$0;

    public SilenceCall$gateListener$1(SilenceCall silenceCall) {
        this.this$0 = silenceCall;
    }

    public final void onGateChanged(Gate gate) {
        this.this$0.updatePhoneStateListener();
    }
}
