package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda0(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        Objects.requireNonNull(keyguardStatusBarViewController);
        keyguardStatusBarViewController.mKeyguardStatusBarAnimateAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        keyguardStatusBarViewController.updateViewState();
    }
}
