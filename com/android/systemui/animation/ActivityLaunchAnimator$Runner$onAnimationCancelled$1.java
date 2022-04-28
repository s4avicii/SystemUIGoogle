package com.android.systemui.animation;

import com.android.systemui.animation.ActivityLaunchAnimator;

/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator$Runner$onAnimationCancelled$1 implements Runnable {
    public final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

    public ActivityLaunchAnimator$Runner$onAnimationCancelled$1(ActivityLaunchAnimator.Runner runner) {
        this.this$0 = runner;
    }

    public final void run() {
        LaunchAnimator$startAnimation$3 launchAnimator$startAnimation$3 = this.this$0.animation;
        if (launchAnimator$startAnimation$3 != null) {
            launchAnimator$startAnimation$3.$cancelled.element = true;
            launchAnimator$startAnimation$3.$animator.cancel();
        }
        this.this$0.controller.onLaunchAnimationCancelled();
    }
}
