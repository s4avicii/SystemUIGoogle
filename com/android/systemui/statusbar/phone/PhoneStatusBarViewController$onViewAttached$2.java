package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: PhoneStatusBarViewController.kt */
public final class PhoneStatusBarViewController$onViewAttached$2 implements View.OnLayoutChangeListener {
    public final /* synthetic */ PhoneStatusBarViewController this$0;

    public PhoneStatusBarViewController$onViewAttached$2(PhoneStatusBarViewController phoneStatusBarViewController) {
        this.this$0 = phoneStatusBarViewController;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        boolean z;
        if (i3 - i != i7 - i5) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = this.this$0.moveFromCenterAnimationController;
            Objects.requireNonNull(statusBarMoveFromCenterAnimationController);
            statusBarMoveFromCenterAnimationController.moveFromCenterAnimator.updateDisplayProperties();
            UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
            Objects.requireNonNull(unfoldMoveFromCenterAnimator);
            Iterator it = unfoldMoveFromCenterAnimator.animatedViews.iterator();
            while (it.hasNext()) {
                UnfoldMoveFromCenterAnimator.AnimatedView animatedView = (UnfoldMoveFromCenterAnimator.AnimatedView) it.next();
                Objects.requireNonNull(animatedView);
                View view2 = animatedView.view.get();
                if (view2 != null) {
                    unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view2);
                }
            }
            unfoldMoveFromCenterAnimator.onTransitionProgress(unfoldMoveFromCenterAnimator.lastAnimationProgress);
        }
    }
}
