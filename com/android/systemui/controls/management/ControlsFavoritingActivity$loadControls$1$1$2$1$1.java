package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.controls.TooltipManager;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$loadControls$1$1$2$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$loadControls$1$1$2$1$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onAnimationEnd(Animator animator) {
        ManagementPageIndicator managementPageIndicator = this.this$0.pageIndicator;
        ManagementPageIndicator managementPageIndicator2 = null;
        if (managementPageIndicator == null) {
            managementPageIndicator = null;
        }
        if (managementPageIndicator.getVisibility() == 0) {
            ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
            if (controlsFavoritingActivity.mTooltipManager != null) {
                int[] iArr = new int[2];
                ManagementPageIndicator managementPageIndicator3 = controlsFavoritingActivity.pageIndicator;
                if (managementPageIndicator3 == null) {
                    managementPageIndicator3 = null;
                }
                managementPageIndicator3.getLocationOnScreen(iArr);
                int i = iArr[0];
                ManagementPageIndicator managementPageIndicator4 = this.this$0.pageIndicator;
                if (managementPageIndicator4 == null) {
                    managementPageIndicator4 = null;
                }
                int width = (managementPageIndicator4.getWidth() / 2) + i;
                int i2 = iArr[1];
                ManagementPageIndicator managementPageIndicator5 = this.this$0.pageIndicator;
                if (managementPageIndicator5 != null) {
                    managementPageIndicator2 = managementPageIndicator5;
                }
                int height = managementPageIndicator2.getHeight() + i2;
                TooltipManager tooltipManager = this.this$0.mTooltipManager;
                if (tooltipManager != null) {
                    tooltipManager.show(width, height);
                }
            }
        }
    }
}
