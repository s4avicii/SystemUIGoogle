package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: TextAnimator.kt */
public final class TextAnimator$setTextStyle$listener$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $onAnimationEnd;
    public final /* synthetic */ TextAnimator this$0;

    public TextAnimator$setTextStyle$listener$1(Runnable runnable, TextAnimator textAnimator) {
        this.$onAnimationEnd = runnable;
        this.this$0 = textAnimator;
    }

    public final void onAnimationCancel(Animator animator) {
        TextAnimator textAnimator = this.this$0;
        Objects.requireNonNull(textAnimator);
        textAnimator.animator.removeListener(this);
    }

    public final void onAnimationEnd(Animator animator) {
        this.$onAnimationEnd.run();
        TextAnimator textAnimator = this.this$0;
        Objects.requireNonNull(textAnimator);
        textAnimator.animator.removeListener(this);
    }
}
