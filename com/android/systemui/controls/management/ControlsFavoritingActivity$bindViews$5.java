package com.android.systemui.controls.management;

import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.controls.TooltipManager;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$bindViews$5 extends ViewPager2.OnPageChangeCallback {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$bindViews$5(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onPageSelected(int i) {
        TooltipManager tooltipManager = this.this$0.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(true);
        }
    }
}
