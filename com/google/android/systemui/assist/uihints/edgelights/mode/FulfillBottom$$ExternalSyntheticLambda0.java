package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FulfillBottom$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillBottom f$0;

    public /* synthetic */ FulfillBottom$$ExternalSyntheticLambda0(FulfillBottom fulfillBottom) {
        this.f$0 = fulfillBottom;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        FulfillBottom fulfillBottom = this.f$0;
        Objects.requireNonNull(fulfillBottom);
        fulfillBottom.mEdgeLightsView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }
}
