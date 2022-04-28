package com.google.android.systemui.columbus.gates;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

/* compiled from: KeyguardVisibility.kt */
public final class KeyguardVisibility$keyguardMonitorCallback$1 implements KeyguardStateController.Callback {
    public final /* synthetic */ KeyguardVisibility this$0;

    public KeyguardVisibility$keyguardMonitorCallback$1(KeyguardVisibility keyguardVisibility) {
        this.this$0 = keyguardVisibility;
    }

    public final void onKeyguardShowingChanged() {
        KeyguardVisibility keyguardVisibility = this.this$0;
        Objects.requireNonNull(keyguardVisibility);
        keyguardVisibility.setBlocking(keyguardVisibility.keyguardStateController.get().isShowing());
    }
}
