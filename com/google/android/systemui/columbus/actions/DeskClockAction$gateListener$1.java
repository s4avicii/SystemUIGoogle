package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ DeskClockAction this$0;

    public DeskClockAction$gateListener$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }

    public final void onGateChanged(Gate gate) {
        this.this$0.updateBroadcastReceiver();
    }
}
