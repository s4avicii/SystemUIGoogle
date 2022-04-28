package com.google.android.systemui.columbus.feedback;

import android.animation.ValueAnimator;
import com.google.android.systemui.assist.AssistManagerGoogle;
import java.util.Objects;

/* compiled from: AssistInvocationEffect.kt */
public final class AssistInvocationEffect$animatorUpdateListener$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AssistInvocationEffect this$0;

    public AssistInvocationEffect$animatorUpdateListener$1(AssistInvocationEffect assistInvocationEffect) {
        this.this$0 = assistInvocationEffect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (valueAnimator != null) {
            AssistInvocationEffect assistInvocationEffect = this.this$0;
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            float floatValue = ((Float) animatedValue).floatValue();
            assistInvocationEffect.progress = floatValue;
            AssistManagerGoogle assistManagerGoogle = assistInvocationEffect.assistManager;
            if (assistManagerGoogle != null) {
                assistManagerGoogle.onInvocationProgress(2, floatValue);
            }
        }
    }
}
