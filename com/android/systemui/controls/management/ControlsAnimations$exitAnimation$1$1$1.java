package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: ControlsAnimations.kt */
public final class ControlsAnimations$exitAnimation$1$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $it;

    public ControlsAnimations$exitAnimation$1$1$1(Runnable runnable) {
        this.$it = runnable;
    }

    public final void onAnimationEnd(Animator animator) {
        this.$it.run();
    }
}
