package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SwipeDismissHandler$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SwipeDismissHandler f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ SwipeDismissHandler$$ExternalSyntheticLambda0(SwipeDismissHandler swipeDismissHandler, float f) {
        this.f$0 = swipeDismissHandler;
        this.f$1 = f;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SwipeDismissHandler swipeDismissHandler = this.f$0;
        float f = this.f$1;
        Objects.requireNonNull(swipeDismissHandler);
        swipeDismissHandler.mView.setTranslationX(MathUtils.lerp(f, 0.0f, valueAnimator.getAnimatedFraction()));
    }
}
