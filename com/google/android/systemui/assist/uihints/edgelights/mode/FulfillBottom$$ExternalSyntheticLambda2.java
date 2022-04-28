package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FulfillBottom$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillBottom f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ FulfillBottom$$ExternalSyntheticLambda2(FulfillBottom fulfillBottom, float f, float f2, float f3) {
        this.f$0 = fulfillBottom;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        float f2;
        float f3;
        FulfillBottom fulfillBottom = this.f$0;
        float f4 = this.f$1;
        float f5 = this.f$2;
        float f6 = this.f$3;
        Objects.requireNonNull(fulfillBottom);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (fulfillBottom.mSwingLeft) {
            f = 0.69f;
        } else {
            f = 0.035f;
        }
        float lerp = MathUtils.lerp(f4, f, animatedFraction);
        if (fulfillBottom.mSwingLeft) {
            f2 = 0.87f;
        } else {
            f2 = 0.13f;
        }
        float lerp2 = MathUtils.lerp(f5, f2, animatedFraction);
        if (fulfillBottom.mSwingLeft) {
            f3 = 0.965f;
        } else {
            f3 = 0.31f;
        }
        fulfillBottom.setRelativePoints(lerp, lerp2, MathUtils.lerp(f6, f3, animatedFraction));
    }
}
