package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startUnlockedRipple$alphaInAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$startUnlockedRipple$alphaInAnimator$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        RippleShader rippleShader = this.this$0.rippleShader;
        Objects.requireNonNull(rippleShader);
        int i = rippleShader.color;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        rippleShader.setColor(ColorUtils.setAlphaComponent(i, ((Integer) animatedValue).intValue()));
        this.this$0.invalidate();
    }
}
