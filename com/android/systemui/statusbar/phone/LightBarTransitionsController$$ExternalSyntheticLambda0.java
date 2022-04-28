package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LightBarTransitionsController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ LightBarTransitionsController f$0;

    public /* synthetic */ LightBarTransitionsController$$ExternalSyntheticLambda0(LightBarTransitionsController lightBarTransitionsController) {
        this.f$0 = lightBarTransitionsController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        LightBarTransitionsController lightBarTransitionsController = this.f$0;
        Objects.requireNonNull(lightBarTransitionsController);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        lightBarTransitionsController.mDarkIntensity = floatValue;
        lightBarTransitionsController.mApplier.applyDarkIntensity(MathUtils.lerp(floatValue, 0.0f, lightBarTransitionsController.mDozeAmount));
    }
}
