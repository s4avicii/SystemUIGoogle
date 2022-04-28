package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$3$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$3$1(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        keyguardUnlockAnimationController.smartspaceUnlockProgress = ((Float) animatedValue).floatValue();
    }
}
