package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FulfillBottom$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillBottom f$0;

    public /* synthetic */ FulfillBottom$$ExternalSyntheticLambda1(FulfillBottom fulfillBottom) {
        this.f$0 = fulfillBottom;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        FulfillBottom fulfillBottom = this.f$0;
        Objects.requireNonNull(fulfillBottom);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (!fulfillBottom.mSwingLeft) {
            animatedFraction = 1.0f - animatedFraction;
        }
        fulfillBottom.setRelativePoints(MathUtils.lerp(0.69f, 0.035f, animatedFraction), MathUtils.lerp(0.87f, 0.13f, animatedFraction), MathUtils.lerp(0.965f, 0.31f, animatedFraction));
    }
}
