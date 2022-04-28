package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SwipeDismissHandler$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SwipeDismissHandler f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ SwipeDismissHandler$$ExternalSyntheticLambda1(SwipeDismissHandler swipeDismissHandler, float f, float f2) {
        this.f$0 = swipeDismissHandler;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SwipeDismissHandler swipeDismissHandler = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        Objects.requireNonNull(swipeDismissHandler);
        swipeDismissHandler.mView.setTranslationX(MathUtils.lerp(f, f2, valueAnimator.getAnimatedFraction()));
        swipeDismissHandler.mView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }
}
