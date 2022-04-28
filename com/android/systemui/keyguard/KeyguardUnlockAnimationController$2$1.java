package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$2$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$2$1(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        keyguardUnlockAnimationController.surfaceBehindAlpha = ((Float) animatedValue).floatValue();
        KeyguardUnlockAnimationController keyguardUnlockAnimationController2 = this.this$0;
        Object animatedValue2 = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue2, "null cannot be cast to non-null type kotlin.Float");
        keyguardUnlockAnimationController2.setSurfaceBehindAppearAmount(((Float) animatedValue2).floatValue());
    }
}
