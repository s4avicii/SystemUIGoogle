package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;

/* compiled from: DelegateLaunchAnimatorController.kt */
public class DelegateLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public final ActivityLaunchAnimator.Controller delegate;

    public final LaunchAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    public final ViewGroup getLaunchContainer() {
        return this.delegate.getLaunchContainer();
    }

    public final View getOpeningWindowSyncView() {
        return this.delegate.getOpeningWindowSyncView();
    }

    public final boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    public void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
    }

    public void onLaunchAnimationCancelled() {
        this.delegate.onLaunchAnimationCancelled();
    }

    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        this.delegate.onLaunchAnimationProgress(state, f, f2);
    }

    public void onLaunchAnimationStart(boolean z) {
        this.delegate.onLaunchAnimationStart(z);
    }

    public final void setLaunchContainer(ViewGroup viewGroup) {
        this.delegate.setLaunchContainer(viewGroup);
    }

    public DelegateLaunchAnimatorController(ActivityLaunchAnimator.Controller controller) {
        this.delegate = controller;
    }
}
