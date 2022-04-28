package com.android.p012wm.shell.onehanded;

import com.android.p012wm.shell.onehanded.OneHandedAnimationController;

/* renamed from: com.android.wm.shell.onehanded.OneHandedAnimationCallback */
public interface OneHandedAnimationCallback {
    void onAnimationUpdate(float f) {
    }

    void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
    }

    void onOneHandedAnimationEnd(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
    }

    void onOneHandedAnimationStart(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
    }
}
