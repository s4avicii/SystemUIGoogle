package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;
import android.view.animation.OvershootInterpolator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NgaUiController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ OvershootInterpolator f$2;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda0(NgaUiController ngaUiController, int i, OvershootInterpolator overshootInterpolator) {
        this.f$0 = ngaUiController;
        this.f$1 = i;
        this.f$2 = overshootInterpolator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        NgaUiController ngaUiController = this.f$0;
        int i = this.f$1;
        OvershootInterpolator overshootInterpolator = this.f$2;
        Objects.requireNonNull(ngaUiController);
        ngaUiController.setProgress(i, overshootInterpolator.getInterpolation(((Float) valueAnimator.getAnimatedValue()).floatValue()));
    }
}
