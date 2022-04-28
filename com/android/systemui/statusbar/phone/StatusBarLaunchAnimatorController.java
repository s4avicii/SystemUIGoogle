package com.android.systemui.statusbar.phone;

import android.graphics.PorterDuffXfermode;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.Objects;

/* compiled from: StatusBarLaunchAnimatorController.kt */
public final class StatusBarLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public final ActivityLaunchAnimator.Controller delegate;
    public final boolean isLaunchForActivity;
    public final StatusBar statusBar;

    public final LaunchAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    public final ViewGroup getLaunchContainer() {
        return this.delegate.getLaunchContainer();
    }

    public final boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    public final void setLaunchContainer(ViewGroup viewGroup) {
        this.delegate.setLaunchContainer(viewGroup);
    }

    public final View getOpeningWindowSyncView() {
        StatusBar statusBar2 = this.statusBar;
        Objects.requireNonNull(statusBar2);
        return statusBar2.mNotificationShadeWindowView;
    }

    public final void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
        if (!z) {
            this.statusBar.collapsePanelOnMainThread();
        }
    }

    public final void onLaunchAnimationCancelled() {
        this.delegate.onLaunchAnimationCancelled();
        StatusBar statusBar2 = this.statusBar;
        boolean z = this.isLaunchForActivity;
        Objects.requireNonNull(statusBar2);
        if (!statusBar2.mPresenter.isPresenterFullyCollapsed() || statusBar2.mPresenter.isCollapsing() || !z) {
            statusBar2.mShadeController.collapsePanel(true);
        } else {
            statusBar2.onClosingFinished();
        }
    }

    public final void onLaunchAnimationEnd(boolean z) {
        this.delegate.onLaunchAnimationEnd(z);
        StatusBar statusBar2 = this.statusBar;
        Objects.requireNonNull(statusBar2);
        NotificationPanelViewController notificationPanelViewController = statusBar2.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mIsLaunchAnimationRunning = false;
        StatusBar statusBar3 = this.statusBar;
        Objects.requireNonNull(statusBar3);
        if (!statusBar3.mPresenter.isCollapsing()) {
            statusBar3.onClosingFinished();
        }
        if (z) {
            statusBar3.instantCollapseNotificationPanel();
        }
    }

    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        boolean z;
        this.delegate.onLaunchAnimationProgress(state, f, f2);
        StatusBar statusBar2 = this.statusBar;
        Objects.requireNonNull(statusBar2);
        NotificationPanelViewController notificationPanelViewController = statusBar2.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
        long j = NotificationPanelViewController.ANIMATION_DELAY_ICON_FADE_IN;
        PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
        if (LaunchAnimator.Companion.getProgress(timings, f2, j, 100) == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z != notificationPanelViewController.mHideIconsDuringLaunchAnimation) {
            notificationPanelViewController.mHideIconsDuringLaunchAnimation = z;
            if (!z) {
                notificationPanelViewController.mCommandQueue.recomputeDisableFlags(notificationPanelViewController.mDisplayId, true);
            }
        }
    }

    public final void onLaunchAnimationStart(boolean z) {
        this.delegate.onLaunchAnimationStart(z);
        StatusBar statusBar2 = this.statusBar;
        Objects.requireNonNull(statusBar2);
        NotificationPanelViewController notificationPanelViewController = statusBar2.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mIsLaunchAnimationRunning = true;
        if (!z) {
            StatusBar statusBar3 = this.statusBar;
            LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
            Objects.requireNonNull(statusBar3);
            NotificationPanelViewController notificationPanelViewController2 = statusBar3.mNotificationPanelViewController;
            Objects.requireNonNull(notificationPanelViewController2);
            notificationPanelViewController2.mFixedDuration = (int) 500;
            notificationPanelViewController2.collapse(false, 1.0f);
            notificationPanelViewController2.mFixedDuration = -1;
        }
    }

    public StatusBarLaunchAnimatorController(ActivityLaunchAnimator.Controller controller, StatusBar statusBar2, boolean z) {
        this.delegate = controller;
        this.statusBar = statusBar2;
        this.isLaunchForActivity = z;
    }
}
