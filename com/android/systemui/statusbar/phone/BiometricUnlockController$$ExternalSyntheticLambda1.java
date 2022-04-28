package com.android.systemui.statusbar.phone;

import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BiometricUnlockController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ BiometricUnlockController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BiometricUnlockController$$ExternalSyntheticLambda1(BiometricUnlockController biometricUnlockController, boolean z, boolean z2) {
        this.f$0 = biometricUnlockController;
        this.f$1 = z;
        this.f$2 = z2;
    }

    public final void run() {
        BiometricUnlockController biometricUnlockController = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        Objects.requireNonNull(biometricUnlockController);
        if (!z) {
            Log.i("BiometricUnlockCtrl", "bio wakelock: Authenticated, waking up...");
            biometricUnlockController.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "android.policy:BIOMETRIC");
        }
        if (z2) {
            biometricUnlockController.mKeyguardViewMediator.onWakeAndUnlocking();
        }
        Trace.beginSection("release wake-and-unlock");
        biometricUnlockController.releaseBiometricWakeLock();
        Trace.endSection();
    }
}
