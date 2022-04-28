package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: KeyguardProximity.kt */
public final class KeyguardProximity$keyguardListener$1 implements Gate.Listener {
    public final /* synthetic */ KeyguardProximity this$0;

    public KeyguardProximity$keyguardListener$1(KeyguardProximity keyguardProximity) {
        this.this$0 = keyguardProximity;
    }

    public final void onGateChanged(Gate gate) {
        this.this$0.updateProximityListener();
    }
}
