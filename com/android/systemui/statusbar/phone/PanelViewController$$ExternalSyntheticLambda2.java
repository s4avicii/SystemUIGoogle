package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda6;
import com.android.systemui.DejankUtils;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PanelViewController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ PanelViewController f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ PanelViewController$$ExternalSyntheticLambda2(PanelViewController panelViewController, float f) {
        this.f$0 = panelViewController;
        this.f$1 = f;
    }

    public final void run() {
        float f;
        PanelViewController panelViewController = this.f$0;
        float f2 = this.f$1;
        Objects.requireNonNull(panelViewController);
        float f3 = 0.0f;
        if (panelViewController.mExpandLatencyTracking && f2 != 0.0f) {
            DejankUtils.postAfterTraversal(new TaskView$$ExternalSyntheticLambda6(panelViewController, 4));
            panelViewController.mExpandLatencyTracking = false;
        }
        float maxPanelHeight = (float) panelViewController.getMaxPanelHeight();
        if (panelViewController.mHeightAnimator == null) {
            if (panelViewController.mTracking) {
                panelViewController.setOverExpansionInternal(Math.max(0.0f, f2 - maxPanelHeight), true);
            }
            panelViewController.mExpandedHeight = Math.min(f2, maxPanelHeight);
        } else {
            panelViewController.mExpandedHeight = f2;
        }
        float f4 = panelViewController.mExpandedHeight;
        if (f4 < 1.0f && f4 != 0.0f && panelViewController.mClosing) {
            panelViewController.mExpandedHeight = 0.0f;
            ValueAnimator valueAnimator = panelViewController.mHeightAnimator;
            if (valueAnimator != null) {
                valueAnimator.end();
            }
        }
        if (maxPanelHeight != 0.0f) {
            f3 = panelViewController.mExpandedHeight / maxPanelHeight;
        }
        panelViewController.mExpandedFraction = Math.min(1.0f, f3);
        float f5 = panelViewController.mExpandedHeight;
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) panelViewController;
        if ((!notificationPanelViewController.mQsExpanded || notificationPanelViewController.mQsExpandImmediate || (notificationPanelViewController.mIsExpanding && notificationPanelViewController.mQsExpandedWhenExpandingStarted)) && notificationPanelViewController.mStackScrollerMeasuringPass <= 2) {
            notificationPanelViewController.positionClockAndNotifications(false);
        }
        if (notificationPanelViewController.mQsExpandImmediate || (notificationPanelViewController.mQsExpanded && !notificationPanelViewController.mQsTracking && notificationPanelViewController.mQsExpansionAnimator == null && !notificationPanelViewController.mQsExpansionFromOverscroll)) {
            if (notificationPanelViewController.mKeyguardShowing) {
                f = f5 / ((float) notificationPanelViewController.getMaxPanelHeight());
            } else {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                Objects.requireNonNull(notificationStackScrollLayout);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController.mNotificationStackScrollLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController2);
                float layoutMinHeight = ((float) notificationStackScrollLayoutController2.mView.getLayoutMinHeight()) + ((float) notificationStackScrollLayout.mIntrinsicPadding);
                f = (f5 - layoutMinHeight) / (((float) notificationPanelViewController.calculatePanelHeightQsExpanded()) - layoutMinHeight);
            }
            int i = notificationPanelViewController.mQsMinExpansionHeight;
            notificationPanelViewController.setQsExpansion((f * ((float) (notificationPanelViewController.mQsMaxExpansionHeight - i))) + ((float) i));
        }
        notificationPanelViewController.updateExpandedHeight(f5);
        if (notificationPanelViewController.mBarState == 1) {
            notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState();
        }
        notificationPanelViewController.updateQsExpansion$1();
        notificationPanelViewController.updateNotificationTranslucency();
        notificationPanelViewController.updatePanelExpanded();
        notificationPanelViewController.updateGestureExclusionRect();
        panelViewController.updatePanelExpansionAndVisibility();
    }
}
