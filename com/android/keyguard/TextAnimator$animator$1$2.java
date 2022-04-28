package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: TextAnimator.kt */
public final class TextAnimator$animator$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ TextAnimator this$0;

    public TextAnimator$animator$1$2(TextAnimator textAnimator) {
        this.this$0 = textAnimator;
    }

    public final void onAnimationCancel(Animator animator) {
        TextAnimator textAnimator = this.this$0;
        Objects.requireNonNull(textAnimator);
        textAnimator.textInterpolator.rebase();
    }

    public final void onAnimationEnd(Animator animator) {
        TextAnimator textAnimator = this.this$0;
        Objects.requireNonNull(textAnimator);
        textAnimator.textInterpolator.rebase();
    }
}
