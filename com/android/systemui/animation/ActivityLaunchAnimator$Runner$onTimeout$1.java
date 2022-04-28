package com.android.systemui.animation;

import android.util.Log;
import com.android.systemui.animation.ActivityLaunchAnimator;
import java.util.Objects;

/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator$Runner$onTimeout$1 implements Runnable {
    public final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

    public ActivityLaunchAnimator$Runner$onTimeout$1(ActivityLaunchAnimator.Runner runner) {
        this.this$0 = runner;
    }

    public final void run() {
        ActivityLaunchAnimator.Runner runner = this.this$0;
        int i = ActivityLaunchAnimator.Runner.$r8$clinit;
        Objects.requireNonNull(runner);
        if (!runner.cancelled) {
            Log.i("ActivityLaunchAnimator", "Remote animation timed out");
            runner.timedOut = true;
            runner.controller.onLaunchAnimationCancelled();
        }
    }
}
