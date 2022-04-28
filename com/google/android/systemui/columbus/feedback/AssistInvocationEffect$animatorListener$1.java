package com.google.android.systemui.columbus.feedback;

import android.animation.Animator;

/* compiled from: AssistInvocationEffect.kt */
public final class AssistInvocationEffect$animatorListener$1 implements Animator.AnimatorListener {
    public final /* synthetic */ AssistInvocationEffect this$0;

    public final void onAnimationRepeat(Animator animator) {
    }

    public final void onAnimationStart(Animator animator) {
    }

    public AssistInvocationEffect$animatorListener$1(AssistInvocationEffect assistInvocationEffect) {
        this.this$0 = assistInvocationEffect;
    }

    public final void onAnimationCancel(Animator animator) {
        this.this$0.progress = 0.0f;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.progress = 0.0f;
    }
}
