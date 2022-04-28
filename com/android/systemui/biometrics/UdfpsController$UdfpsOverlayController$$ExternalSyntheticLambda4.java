package com.android.systemui.biometrics;

import android.os.VibrationAttributes;
import android.view.MotionEvent;
import android.view.View;
import kotlin.jvm.functions.Function3;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4 implements Function3 {
    public final /* synthetic */ UdfpsController f$0;

    public /* synthetic */ UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4(UdfpsController udfpsController) {
        this.f$0 = udfpsController;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        VibrationAttributes vibrationAttributes = UdfpsController.VIBRATION_ATTRIBUTES;
        return Boolean.valueOf(this.f$0.onTouch((View) obj, (MotionEvent) obj2, booleanValue));
    }
}
