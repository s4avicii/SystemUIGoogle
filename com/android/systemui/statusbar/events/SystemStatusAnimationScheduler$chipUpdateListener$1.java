package com.android.systemui.statusbar.events;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Objects;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$chipUpdateListener$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SystemStatusAnimationScheduler this$0;

    public SystemStatusAnimationScheduler$chipUpdateListener$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
        SystemEventChipAnimationController systemEventChipAnimationController = systemStatusAnimationScheduler.chipAnimationController;
        Objects.requireNonNull(systemStatusAnimationScheduler);
        Objects.requireNonNull(systemEventChipAnimationController);
        View view = systemEventChipAnimationController.currentAnimatedView;
        if (view != null) {
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            float floatValue = ((Float) animatedValue).floatValue();
            view.setAlpha(floatValue);
            float width = (((float) 1) - floatValue) * ((float) view.getWidth());
            if (view.isLayoutRtl()) {
                width = -width;
            }
            view.setTranslationX(width);
        }
    }
}
