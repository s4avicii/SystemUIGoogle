package com.android.systemui.statusbar.phone;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import com.android.systemui.util.ViewController;
import java.util.Objects;
import java.util.Optional;

/* compiled from: PhoneStatusBarViewController.kt */
public final class PhoneStatusBarViewController extends ViewController<PhoneStatusBarView> {
    public final ConfigurationController configurationController;
    public final PhoneStatusBarViewController$configurationListener$1 configurationListener = new PhoneStatusBarViewController$configurationListener$1(this);
    public final StatusBarMoveFromCenterAnimationController moveFromCenterAnimationController;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final StatusBarUserSwitcherController userSwitcherController;

    /* compiled from: PhoneStatusBarViewController.kt */
    public static final class Factory {
        public final ConfigurationController configurationController;
        public final Optional<ScopedUnfoldTransitionProgressProvider> progressProvider;
        public final Optional<SysUIUnfoldComponent> unfoldComponent;
        public final StatusBarUserSwitcherController userSwitcherController;

        public Factory(Optional<SysUIUnfoldComponent> optional, Optional<ScopedUnfoldTransitionProgressProvider> optional2, StatusBarUserSwitcherController statusBarUserSwitcherController, ConfigurationController configurationController2) {
            this.unfoldComponent = optional;
            this.progressProvider = optional2;
            this.userSwitcherController = statusBarUserSwitcherController;
            this.configurationController = configurationController2;
        }
    }

    /* compiled from: PhoneStatusBarViewController.kt */
    public static final class StatusBarViewsCenterProvider implements UnfoldMoveFromCenterAnimator.ViewCenterProvider {
        public static void getViewEdgeCenter(View view, Point point, boolean z) {
            boolean z2;
            int i;
            if (view.getResources().getConfiguration().getLayoutDirection() == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            boolean z3 = z ^ z2;
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i2 = iArr[0];
            int i3 = iArr[1];
            if (z3) {
                i = view.getHeight() / 2;
            } else {
                i = view.getWidth() - (view.getHeight() / 2);
            }
            point.x = i2 + i;
            point.y = (view.getHeight() / 2) + i3;
        }

        public final void getViewCenter(View view, Point point) {
            int id = view.getId();
            if (id == C1777R.C1779id.status_bar_left_side) {
                getViewEdgeCenter(view, point, true);
            } else if (id == C1777R.C1779id.system_icon_area) {
                getViewEdgeCenter(view, point, false);
            } else {
                UnfoldMoveFromCenterAnimator.ViewCenterProvider.DefaultImpls.getViewCenter(view, point);
            }
        }
    }

    public final void onInit() {
        this.userSwitcherController.init();
    }

    public final void onViewAttached() {
        if (this.moveFromCenterAnimationController != null) {
            ((PhoneStatusBarView) this.mView).getViewTreeObserver().addOnPreDrawListener(new PhoneStatusBarViewController$onViewAttached$1(this, new View[]{((PhoneStatusBarView) this.mView).findViewById(C1777R.C1779id.status_bar_left_side), (ViewGroup) ((PhoneStatusBarView) this.mView).findViewById(C1777R.C1779id.system_icon_area)}));
            ((PhoneStatusBarView) this.mView).addOnLayoutChangeListener(new PhoneStatusBarViewController$onViewAttached$2(this));
            ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
            if (scopedUnfoldTransitionProgressProvider != null) {
                scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(true);
            }
            this.configurationController.addCallback(this.configurationListener);
        }
    }

    public final void onViewDetached() {
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(false);
        }
        StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = this.moveFromCenterAnimationController;
        if (statusBarMoveFromCenterAnimationController != null) {
            ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider2 = statusBarMoveFromCenterAnimationController.progressProvider;
            StatusBarMoveFromCenterAnimationController.TransitionListener transitionListener = statusBarMoveFromCenterAnimationController.transitionListener;
            Objects.requireNonNull(scopedUnfoldTransitionProgressProvider2);
            scopedUnfoldTransitionProgressProvider2.listeners.remove(transitionListener);
            UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
            Objects.requireNonNull(unfoldMoveFromCenterAnimator);
            unfoldMoveFromCenterAnimator.onTransitionProgress(1.0f);
            unfoldMoveFromCenterAnimator.animatedViews.clear();
        }
        this.configurationController.removeCallback(this.configurationListener);
    }

    public PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserSwitcherController statusBarUserSwitcherController, NotificationPanelViewController.C148318 r5, ConfigurationController configurationController2) {
        super(phoneStatusBarView);
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.moveFromCenterAnimationController = statusBarMoveFromCenterAnimationController;
        this.userSwitcherController = statusBarUserSwitcherController;
        this.configurationController = configurationController2;
        Objects.requireNonNull(phoneStatusBarView);
        phoneStatusBarView.mTouchEventHandler = r5;
    }
}
