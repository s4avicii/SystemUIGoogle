package com.android.systemui.statusbar.phone;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.ExpandHelper;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.p006qs.QSFooterView$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class ShadeControllerImpl implements ShadeController {
    public final Lazy<AssistManager> mAssistManagerLazy;
    public final Optional<Bubbles> mBubblesOptional;
    public final CommandQueue mCommandQueue;
    public final int mDisplayId;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ArrayList<Runnable> mPostCollapseRunnables = new ArrayList<>();
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final StatusBarStateController mStatusBarStateController;

    public final void animateCollapsePanels() {
        animateCollapsePanels(0);
    }

    public final void animateCollapsePanels$1(int i) {
        animateCollapsePanels(i, true, false, 1.0f);
    }

    public final boolean collapsePanel() {
        if (getNotificationPanelViewController().isFullyCollapsed()) {
            return false;
        }
        animateCollapsePanels$1();
        getStatusBar().visibilityChanged(false);
        return true;
    }

    public final void addPostCollapseAction(Runnable runnable) {
        this.mPostCollapseRunnables.add(runnable);
    }

    public final void animateCollapsePanels(int i) {
        animateCollapsePanels(i, false, false, 1.0f);
    }

    public final void animateCollapsePanels$1() {
        animateCollapsePanels(2, true, true, 1.0f);
    }

    public final StatusBar getStatusBar() {
        return (StatusBar) this.mStatusBarOptionalLazy.get().get();
    }

    public final void runPostCollapseRunnables() {
        ArrayList arrayList = new ArrayList(this.mPostCollapseRunnables);
        this.mPostCollapseRunnables.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Runnable) arrayList.get(i)).run();
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        statusBarKeyguardViewManager.mViewMediatorCallback.readyForKeyguardDone();
    }

    public ShadeControllerImpl(CommandQueue commandQueue, StatusBarStateController statusBarStateController, NotificationShadeWindowController notificationShadeWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, WindowManager windowManager, Lazy<Optional<StatusBar>> lazy, Lazy<AssistManager> lazy2, Optional<Bubbles> optional) {
        this.mCommandQueue = commandQueue;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mDisplayId = windowManager.getDefaultDisplay().getDisplayId();
        this.mStatusBarOptionalLazy = lazy;
        this.mAssistManagerLazy = lazy2;
        this.mBubblesOptional = optional;
    }

    public final void animateCollapsePanels(int i, boolean z, boolean z2, float f) {
        if (z || this.mStatusBarStateController.getState() == 0) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NotificationShadeWindow: ");
            StatusBar statusBar = getStatusBar();
            Objects.requireNonNull(statusBar);
            m.append(statusBar.mNotificationShadeWindowView);
            m.append(" canPanelBeCollapsed(): ");
            m.append(getNotificationPanelViewController().canPanelBeCollapsed());
            Log.v("ShadeControllerImpl", m.toString());
            StatusBar statusBar2 = getStatusBar();
            Objects.requireNonNull(statusBar2);
            if (statusBar2.mNotificationShadeWindowView != null && getNotificationPanelViewController().canPanelBeCollapsed() && (i & 4) == 0) {
                this.mNotificationShadeWindowController.setNotificationShadeFocusable(false);
                StatusBar statusBar3 = getStatusBar();
                Objects.requireNonNull(statusBar3);
                NotificationShadeWindowViewController notificationShadeWindowViewController = statusBar3.mNotificationShadeWindowViewController;
                Objects.requireNonNull(notificationShadeWindowViewController);
                NotificationStackScrollLayout notificationStackScrollLayout = notificationShadeWindowViewController.mStackScrollLayout;
                if (notificationStackScrollLayout != null) {
                    ExpandHelper expandHelper = notificationStackScrollLayout.mExpandHelper;
                    Objects.requireNonNull(expandHelper);
                    expandHelper.finishExpanding(true, 0.0f, true);
                    expandHelper.mResizedView = null;
                    expandHelper.mSGD = new ScaleGestureDetector(expandHelper.mContext, expandHelper.mScaleGestureListener);
                }
                getNotificationPanelViewController().collapsePanel(true, z2, f);
            } else if (this.mBubblesOptional.isPresent()) {
                this.mBubblesOptional.get().collapseStack();
            }
        } else {
            runPostCollapseRunnables();
        }
    }

    public final void closeShadeIfOpen() {
        if (!getNotificationPanelViewController().isFullyCollapsed()) {
            this.mCommandQueue.animateCollapsePanels(2, true);
            getStatusBar().visibilityChanged(false);
            this.mAssistManagerLazy.get().hideAssist();
        }
    }

    public final NotificationPanelViewController getNotificationPanelViewController() {
        StatusBar statusBar = getStatusBar();
        Objects.requireNonNull(statusBar);
        return statusBar.mNotificationPanelViewController;
    }

    public final void instantExpandNotificationsPanel() {
        getStatusBar().makeExpandedVisible(true);
        getNotificationPanelViewController().expand(false);
        this.mCommandQueue.recomputeDisableFlags(this.mDisplayId, false);
    }

    public final boolean isShadeOpen() {
        NotificationPanelViewController notificationPanelViewController = getNotificationPanelViewController();
        Objects.requireNonNull(notificationPanelViewController);
        if (notificationPanelViewController.mIsExpanding || notificationPanelViewController.isFullyExpanded()) {
            return true;
        }
        return false;
    }

    public final void postOnShadeExpanded(final QSFooterView$$ExternalSyntheticLambda0 qSFooterView$$ExternalSyntheticLambda0) {
        NotificationPanelViewController notificationPanelViewController = getNotificationPanelViewController();
        C15311 r1 = new ViewTreeObserver.OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                StatusBar statusBar = ShadeControllerImpl.this.getStatusBar();
                Objects.requireNonNull(statusBar);
                if (statusBar.mNotificationShadeWindowView.isVisibleToUser()) {
                    NotificationPanelViewController notificationPanelViewController = ShadeControllerImpl.this.getNotificationPanelViewController();
                    Objects.requireNonNull(notificationPanelViewController);
                    notificationPanelViewController.mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    NotificationPanelViewController notificationPanelViewController2 = ShadeControllerImpl.this.getNotificationPanelViewController();
                    Objects.requireNonNull(notificationPanelViewController2);
                    notificationPanelViewController2.mView.post(qSFooterView$$ExternalSyntheticLambda0);
                }
            }
        };
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mView.getViewTreeObserver().addOnGlobalLayoutListener(r1);
    }

    public final void collapsePanel(boolean z) {
        if (!z) {
            StatusBar statusBar = getStatusBar();
            Objects.requireNonNull(statusBar);
            if (!statusBar.mPresenter.isPresenterFullyCollapsed()) {
                getStatusBar().instantCollapseNotificationPanel();
                getStatusBar().visibilityChanged(false);
                return;
            }
            runPostCollapseRunnables();
        } else if (!collapsePanel()) {
            runPostCollapseRunnables();
        }
    }
}
