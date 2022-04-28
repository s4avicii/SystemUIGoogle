package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: Gate.kt */
public final class Gate$notifyListeners$1$1 implements Runnable {
    public final /* synthetic */ Gate.Listener $it;
    public final /* synthetic */ Gate this$0;

    public Gate$notifyListeners$1$1(Gate.Listener listener, Gate gate) {
        this.$it = listener;
        this.this$0 = gate;
    }

    public final void run() {
        this.$it.onGateChanged(this.this$0);
    }
}
