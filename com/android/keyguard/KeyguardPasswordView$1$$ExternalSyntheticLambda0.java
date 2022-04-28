package com.android.keyguard;

import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.view.WindowInsetsAnimationController;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPasswordView$1$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ WindowInsetsAnimationController f$0;
    public final /* synthetic */ ValueAnimator f$1;

    public /* synthetic */ KeyguardPasswordView$1$$ExternalSyntheticLambda0(WindowInsetsAnimationController windowInsetsAnimationController, ValueAnimator valueAnimator) {
        this.f$0 = windowInsetsAnimationController;
        this.f$1 = valueAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        WindowInsetsAnimationController windowInsetsAnimationController = this.f$0;
        ValueAnimator valueAnimator2 = this.f$1;
        if (!windowInsetsAnimationController.isCancelled()) {
            Insets shownStateInsets = windowInsetsAnimationController.getShownStateInsets();
            windowInsetsAnimationController.setInsetsAndAlpha(Insets.add(shownStateInsets, Insets.of(0, 0, 0, (int) (valueAnimator2.getAnimatedFraction() * ((float) ((-shownStateInsets.bottom) / 4))))), ((Float) valueAnimator.getAnimatedValue()).floatValue(), valueAnimator2.getAnimatedFraction());
        }
    }
}
