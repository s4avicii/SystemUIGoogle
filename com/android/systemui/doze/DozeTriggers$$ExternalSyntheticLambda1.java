package com.android.systemui.doze;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float[] f$3;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda1(DozeTriggers dozeTriggers, float f, float f2, float[] fArr) {
        this.f$0 = dozeTriggers;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = fArr;
    }

    public final void run() {
        DozeTriggers dozeTriggers = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        float[] fArr = this.f$3;
        Objects.requireNonNull(dozeTriggers);
        AuthController authController = dozeTriggers.mAuthController;
        int i = (int) f;
        int i2 = (int) f2;
        float f3 = fArr[3];
        float f4 = fArr[4];
        Objects.requireNonNull(authController);
        UdfpsController udfpsController = authController.mUdfpsController;
        if (udfpsController == null || udfpsController.mIsAodInterruptActive) {
            return;
        }
        if (!udfpsController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            udfpsController.mKeyguardViewManager.showBouncer(true);
            return;
        }
        UdfpsController$$ExternalSyntheticLambda0 udfpsController$$ExternalSyntheticLambda0 = new UdfpsController$$ExternalSyntheticLambda0(udfpsController, i, i2, f4, f3);
        udfpsController.mAodInterruptRunnable = udfpsController$$ExternalSyntheticLambda0;
        if (udfpsController.mScreenOn) {
            udfpsController$$ExternalSyntheticLambda0.run();
            udfpsController.mAodInterruptRunnable = null;
        }
    }
}
