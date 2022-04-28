package com.android.keyguard;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: TextAnimator.kt */
public final class TextAnimator$animator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ TextAnimator this$0;

    public TextAnimator$animator$1$1(TextAnimator textAnimator) {
        this.this$0 = textAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        TextAnimator textAnimator = this.this$0;
        Objects.requireNonNull(textAnimator);
        TextInterpolator textInterpolator = textAnimator.textInterpolator;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float floatValue = ((Float) animatedValue).floatValue();
        Objects.requireNonNull(textInterpolator);
        textInterpolator.progress = floatValue;
        this.this$0.invalidateCallback.invoke();
    }
}
