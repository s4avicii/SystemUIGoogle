package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$setDragDownAmountAnimated$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public LockscreenShadeTransitionController$setDragDownAmountAnimated$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        lockscreenShadeTransitionController.mo11568xcfc05636(((Float) animatedValue).floatValue());
    }
}
