package com.android.systemui.biometrics;

import android.graphics.PointF;
import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$udfpsControllerCallback$1 implements UdfpsController.Callback {
    public final /* synthetic */ AuthRippleController this$0;

    public AuthRippleController$udfpsControllerCallback$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    public final void onFingerDown() {
        AuthRippleController authRippleController = this.this$0;
        Objects.requireNonNull(authRippleController);
        if (authRippleController.fingerprintSensorLocation == null) {
            Log.e("AuthRipple", "fingerprintSensorLocation=null onFingerDown. Skip showing dwell ripple");
            return;
        }
        AuthRippleController authRippleController2 = this.this$0;
        Objects.requireNonNull(authRippleController2);
        PointF pointF = authRippleController2.fingerprintSensorLocation;
        Intrinsics.checkNotNull(pointF);
        ((AuthRippleView) authRippleController2.mView).setFingerprintSensorLocation(pointF, this.this$0.udfpsRadius);
        AuthRippleController.access$showDwellRipple(this.this$0);
    }

    public final void onFingerUp() {
        ((AuthRippleView) this.this$0.mView).retractRipple();
    }
}
