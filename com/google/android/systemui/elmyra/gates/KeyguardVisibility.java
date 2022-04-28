package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.KeyguardStateController;

public final class KeyguardVisibility extends Gate {
    public final C22521 mKeyguardMonitorCallback = new KeyguardStateController.Callback() {
        public final void onKeyguardShowingChanged() {
            KeyguardVisibility.this.notifyListener();
        }
    };
    public final KeyguardStateController mKeyguardStateController = ((KeyguardStateController) Dependency.get(KeyguardStateController.class));

    public final boolean isBlocked() {
        return this.mKeyguardStateController.isShowing();
    }

    public final void onActivate() {
        this.mKeyguardStateController.addCallback(this.mKeyguardMonitorCallback);
    }

    public final void onDeactivate() {
        this.mKeyguardStateController.removeCallback(this.mKeyguardMonitorCallback);
    }

    public KeyguardVisibility(Context context) {
        super(context);
    }
}
