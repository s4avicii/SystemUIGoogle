package com.android.systemui.controls;

import android.view.animation.AccelerateInterpolator;
import java.util.Objects;

/* compiled from: TooltipManager.kt */
public final class TooltipManager$hide$1 implements Runnable {
    public final /* synthetic */ boolean $animate;
    public final /* synthetic */ TooltipManager this$0;

    public TooltipManager$hide$1(boolean z, TooltipManager tooltipManager) {
        this.$animate = z;
        this.this$0 = tooltipManager;
    }

    public final void run() {
        if (this.$animate) {
            TooltipManager tooltipManager = this.this$0;
            Objects.requireNonNull(tooltipManager);
            tooltipManager.layout.animate().alpha(0.0f).withLayer().setStartDelay(0).setDuration(100).setInterpolator(new AccelerateInterpolator()).start();
            return;
        }
        TooltipManager tooltipManager2 = this.this$0;
        Objects.requireNonNull(tooltipManager2);
        tooltipManager2.layout.animate().cancel();
        TooltipManager tooltipManager3 = this.this$0;
        Objects.requireNonNull(tooltipManager3);
        tooltipManager3.layout.setAlpha(0.0f);
    }
}
