package com.android.keyguard;

import android.hardware.fingerprint.FingerprintManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda5 implements FingerprintManager.FingerprintDetectionCallback {
    public final /* synthetic */ KeyguardUpdateMonitor f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda5(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.f$0 = keyguardUpdateMonitor;
    }

    public final void onFingerprintDetected(int i, int i2, boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
        Objects.requireNonNull(keyguardUpdateMonitor);
        keyguardUpdateMonitor.handleFingerprintAuthenticated(i2, z);
    }
}
