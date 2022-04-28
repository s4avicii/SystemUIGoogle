package com.android.systemui.controls;

import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import java.util.Objects;

/* compiled from: TooltipManager.kt */
public final class TooltipManager$show$1 implements Runnable {

    /* renamed from: $x */
    public final /* synthetic */ int f42$x;

    /* renamed from: $y */
    public final /* synthetic */ int f43$y;
    public final /* synthetic */ TooltipManager this$0;

    public TooltipManager$show$1(TooltipManager tooltipManager, int i, int i2) {
        this.this$0 = tooltipManager;
        this.f42$x = i;
        this.f43$y = i2;
    }

    public final void run() {
        int i;
        int[] iArr = new int[2];
        TooltipManager tooltipManager = this.this$0;
        Objects.requireNonNull(tooltipManager);
        tooltipManager.layout.getLocationOnScreen(iArr);
        TooltipManager tooltipManager2 = this.this$0;
        Objects.requireNonNull(tooltipManager2);
        ViewGroup viewGroup = tooltipManager2.layout;
        boolean z = false;
        int i2 = this.f42$x - iArr[0];
        TooltipManager tooltipManager3 = this.this$0;
        Objects.requireNonNull(tooltipManager3);
        viewGroup.setTranslationX((float) (i2 - (tooltipManager3.layout.getWidth() / 2)));
        TooltipManager tooltipManager4 = this.this$0;
        Objects.requireNonNull(tooltipManager4);
        ViewGroup viewGroup2 = tooltipManager4.layout;
        float f = (float) (this.f43$y - iArr[1]);
        TooltipManager tooltipManager5 = this.this$0;
        if (!tooltipManager5.below) {
            Objects.requireNonNull(tooltipManager5);
            i = tooltipManager5.layout.getHeight();
        } else {
            i = 0;
        }
        viewGroup2.setTranslationY(f - ((float) i));
        TooltipManager tooltipManager6 = this.this$0;
        Objects.requireNonNull(tooltipManager6);
        if (tooltipManager6.layout.getAlpha() == 0.0f) {
            z = true;
        }
        if (z) {
            TooltipManager tooltipManager7 = this.this$0;
            Objects.requireNonNull(tooltipManager7);
            tooltipManager7.layout.animate().alpha(1.0f).withLayer().setStartDelay(500).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
        }
    }
}
