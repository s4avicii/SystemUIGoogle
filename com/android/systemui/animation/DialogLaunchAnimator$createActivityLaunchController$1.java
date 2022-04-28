package com.android.systemui.animation;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;

/* compiled from: DialogLaunchAnimator.kt */
public final class DialogLaunchAnimator$createActivityLaunchController$1 implements ActivityLaunchAnimator.Controller {
    public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
    public final /* synthetic */ AnimatedDialog $animatedDialog;
    public final /* synthetic */ ActivityLaunchAnimator.Controller $controller;
    public final /* synthetic */ Dialog $dialog;

    public final LaunchAnimator.State createAnimatorState() {
        return this.$$delegate_0.createAnimatorState();
    }

    public final ViewGroup getLaunchContainer() {
        return this.$$delegate_0.getLaunchContainer();
    }

    public final View getOpeningWindowSyncView() {
        return this.$$delegate_0.getOpeningWindowSyncView();
    }

    public final boolean isDialogLaunch() {
        return true;
    }

    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        this.$$delegate_0.onLaunchAnimationProgress(state, f, f2);
    }

    public final void setLaunchContainer(ViewGroup viewGroup) {
        this.$$delegate_0.setLaunchContainer(viewGroup);
    }

    public DialogLaunchAnimator$createActivityLaunchController$1(GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController, Dialog dialog, AnimatedDialog animatedDialog) {
        this.$controller = ghostedViewLaunchAnimatorController;
        this.$dialog = dialog;
        this.$animatedDialog = animatedDialog;
        this.$$delegate_0 = ghostedViewLaunchAnimatorController;
    }

    public final void onIntentStarted(boolean z) {
        this.$controller.onIntentStarted(z);
        if (!z) {
            this.$dialog.dismiss();
        }
    }

    public final void onLaunchAnimationCancelled() {
        this.$controller.onLaunchAnimationCancelled();
        this.$dialog.setDismissOverride(new C0667xf3619ef1(this.$animatedDialog));
        this.$dialog.dismiss();
    }

    public final void onLaunchAnimationEnd(boolean z) {
        this.$controller.onLaunchAnimationEnd(z);
        this.$dialog.hide();
        this.$dialog.setDismissOverride(new C0667xf3619ef1(this.$animatedDialog));
        this.$dialog.dismiss();
    }

    public final void onLaunchAnimationStart(boolean z) {
        this.$controller.onLaunchAnimationStart(z);
        this.$dialog.setDismissOverride(C0666x59753e02.INSTANCE);
        AnimatedDialog animatedDialog = this.$animatedDialog;
        animatedDialog.touchSurface = animatedDialog.prepareForStackDismiss();
        this.$dialog.getWindow().clearFlags(2);
    }
}
