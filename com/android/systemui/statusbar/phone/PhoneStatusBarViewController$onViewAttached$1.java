package com.android.systemui.statusbar.phone;

import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: PhoneStatusBarViewController.kt */
public final class PhoneStatusBarViewController$onViewAttached$1 implements ViewTreeObserver.OnPreDrawListener {
    public final /* synthetic */ View[] $viewsToAnimate;
    public final /* synthetic */ PhoneStatusBarViewController this$0;

    public PhoneStatusBarViewController$onViewAttached$1(PhoneStatusBarViewController phoneStatusBarViewController, View[] viewArr) {
        this.this$0 = phoneStatusBarViewController;
        this.$viewsToAnimate = viewArr;
    }

    public final boolean onPreDraw() {
        StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = this.this$0.moveFromCenterAnimationController;
        View[] viewArr = this.$viewsToAnimate;
        Objects.requireNonNull(statusBarMoveFromCenterAnimationController);
        statusBarMoveFromCenterAnimationController.moveFromCenterAnimator.updateDisplayProperties();
        int length = viewArr.length;
        int i = 0;
        while (i < length) {
            View view = viewArr[i];
            i++;
            UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
            Objects.requireNonNull(unfoldMoveFromCenterAnimator);
            UnfoldMoveFromCenterAnimator.AnimatedView animatedView = new UnfoldMoveFromCenterAnimator.AnimatedView(new WeakReference(view));
            unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view);
            unfoldMoveFromCenterAnimator.animatedViews.add(animatedView);
        }
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = statusBarMoveFromCenterAnimationController.progressProvider;
        StatusBarMoveFromCenterAnimationController.TransitionListener transitionListener = statusBarMoveFromCenterAnimationController.transitionListener;
        Objects.requireNonNull(scopedUnfoldTransitionProgressProvider);
        scopedUnfoldTransitionProgressProvider.listeners.add(transitionListener);
        ((PhoneStatusBarView) this.this$0.mView).getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
    }
}
