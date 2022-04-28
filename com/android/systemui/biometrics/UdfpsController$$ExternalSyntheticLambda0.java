package com.android.systemui.biometrics;

import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UdfpsController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ float f$4;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda0(UdfpsController udfpsController, int i, int i2, float f, float f2) {
        this.f$0 = udfpsController;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = f;
        this.f$4 = f2;
    }

    public final void run() {
        UdfpsController udfpsController = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        float f = this.f$3;
        float f2 = this.f$4;
        Objects.requireNonNull(udfpsController);
        udfpsController.mIsAodInterruptActive = true;
        udfpsController.mCancelAodTimeoutAction = udfpsController.mFgExecutor.executeDelayed(new ScreenDecorations$$ExternalSyntheticLambda1(udfpsController, 2), 1000);
        udfpsController.onFingerDown(i, i2, f, f2);
    }
}
