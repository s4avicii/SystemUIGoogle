package com.android.systemui;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: DisplayCutoutBaseView.kt */
public final class DisplayCutoutBaseView$enableShowProtection$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DisplayCutoutBaseView this$0;

    public DisplayCutoutBaseView$enableShowProtection$1(DisplayCutoutBaseView displayCutoutBaseView) {
        this.this$0 = displayCutoutBaseView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        DisplayCutoutBaseView displayCutoutBaseView = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        displayCutoutBaseView.cameraProtectionProgress = ((Float) animatedValue).floatValue();
        this.this$0.invalidate();
    }
}
