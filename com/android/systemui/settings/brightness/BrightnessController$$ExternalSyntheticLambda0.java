package com.android.systemui.settings.brightness;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BrightnessController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ BrightnessController f$0;

    public /* synthetic */ BrightnessController$$ExternalSyntheticLambda0(BrightnessController brightnessController) {
        this.f$0 = brightnessController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        BrightnessController brightnessController = this.f$0;
        Objects.requireNonNull(brightnessController);
        brightnessController.mExternalChange = true;
        ((BrightnessSliderController) brightnessController.mControl).setValue(((Integer) valueAnimator.getAnimatedValue()).intValue());
        brightnessController.mExternalChange = false;
    }
}
