package com.android.systemui.biometrics;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitorCallback;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$keyguardUpdateMonitorCallback$1 extends KeyguardUpdateMonitorCallback {
    public final /* synthetic */ AuthRippleController this$0;

    public AuthRippleController$keyguardUpdateMonitorCallback$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
        ((AuthRippleView) this.this$0.mView).retractRipple();
    }

    public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
        this.this$0.showRipple(biometricSourceType);
    }
}
