package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startUnlockedRipple$rippleAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$startUnlockedRipple$rippleAnimator$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        long currentPlayTime = valueAnimator.getCurrentPlayTime();
        RippleShader rippleShader = this.this$0.rippleShader;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        rippleShader.setProgress(((Float) animatedValue).floatValue());
        RippleShader rippleShader2 = this.this$0.rippleShader;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_time", (float) currentPlayTime);
        this.this$0.invalidate();
    }
}
