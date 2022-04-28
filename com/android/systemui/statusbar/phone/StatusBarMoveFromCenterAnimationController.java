package com.android.systemui.statusbar.phone;

import android.view.WindowManager;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;

/* compiled from: StatusBarMoveFromCenterAnimationController.kt */
public final class StatusBarMoveFromCenterAnimationController {
    public final UnfoldMoveFromCenterAnimator moveFromCenterAnimator;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final TransitionListener transitionListener = new TransitionListener();

    /* compiled from: StatusBarMoveFromCenterAnimationController.kt */
    public static final class StatusBarIconsAlphaProvider implements UnfoldMoveFromCenterAnimator.AlphaProvider {
        public final float getAlpha(float f) {
            return Math.max(0.0f, (f - 0.75f) / 0.25f);
        }
    }

    /* compiled from: StatusBarMoveFromCenterAnimationController.kt */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public final void onTransitionStarted() {
        }

        public TransitionListener() {
        }

        public final void onTransitionFinished() {
            StatusBarMoveFromCenterAnimationController.this.moveFromCenterAnimator.onTransitionProgress(1.0f);
        }

        public final void onTransitionProgress(float f) {
            StatusBarMoveFromCenterAnimationController.this.moveFromCenterAnimator.onTransitionProgress(f);
        }
    }

    public StatusBarMoveFromCenterAnimationController(ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, WindowManager windowManager) {
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.moveFromCenterAnimator = new UnfoldMoveFromCenterAnimator(windowManager, new PhoneStatusBarViewController.StatusBarViewsCenterProvider(), new StatusBarIconsAlphaProvider());
    }
}
