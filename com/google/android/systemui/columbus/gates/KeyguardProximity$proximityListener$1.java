package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;
import java.util.Objects;

/* compiled from: KeyguardProximity.kt */
public final class KeyguardProximity$proximityListener$1 implements Gate.Listener {
    public final /* synthetic */ KeyguardProximity this$0;

    public KeyguardProximity$proximityListener$1(KeyguardProximity keyguardProximity) {
        this.this$0 = keyguardProximity;
    }

    public final void onGateChanged(Gate gate) {
        boolean z;
        KeyguardProximity keyguardProximity = this.this$0;
        Objects.requireNonNull(keyguardProximity);
        if (!keyguardProximity.isListening || !keyguardProximity.proximity.isBlocking()) {
            z = false;
        } else {
            z = true;
        }
        keyguardProximity.setBlocking(z);
    }
}
