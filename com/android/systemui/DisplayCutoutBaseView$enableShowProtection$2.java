package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: DisplayCutoutBaseView.kt */
public final class DisplayCutoutBaseView$enableShowProtection$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ DisplayCutoutBaseView this$0;

    public DisplayCutoutBaseView$enableShowProtection$2(DisplayCutoutBaseView displayCutoutBaseView) {
        this.this$0 = displayCutoutBaseView;
    }

    public final void onAnimationEnd(Animator animator) {
        DisplayCutoutBaseView displayCutoutBaseView = this.this$0;
        displayCutoutBaseView.cameraProtectionAnimator = null;
        if (!displayCutoutBaseView.showProtection) {
            displayCutoutBaseView.requestLayout();
        }
    }
}
