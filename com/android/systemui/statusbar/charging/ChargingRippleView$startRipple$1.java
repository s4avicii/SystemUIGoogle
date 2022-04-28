package com.android.systemui.statusbar.charging;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: ChargingRippleView.kt */
public final class ChargingRippleView$startRipple$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ChargingRippleView this$0;

    public ChargingRippleView$startRipple$1(ChargingRippleView chargingRippleView) {
        this.this$0 = chargingRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        long currentPlayTime = valueAnimator.getCurrentPlayTime();
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float floatValue = ((Float) animatedValue).floatValue();
        this.this$0.rippleShader.setProgress(floatValue);
        RippleShader rippleShader = this.this$0.rippleShader;
        float f = ((float) 1) - floatValue;
        Objects.requireNonNull(rippleShader);
        float f2 = (float) 75;
        rippleShader.setFloatUniform("in_distort_radial", rippleShader.progress * f2 * f);
        rippleShader.setFloatUniform("in_distort_xy", f2 * f);
        RippleShader rippleShader2 = this.this$0.rippleShader;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_time", (float) currentPlayTime);
        this.this$0.invalidate();
    }
}
