package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.statusbar.charging.DwellRippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$retractRipple$retractAlphaAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$retractRipple$retractAlphaAnimator$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        DwellRippleShader dwellRippleShader = this.this$0.dwellShader;
        Objects.requireNonNull(dwellRippleShader);
        int i = dwellRippleShader.color;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int alphaComponent = ColorUtils.setAlphaComponent(i, ((Integer) animatedValue).intValue());
        dwellRippleShader.color = alphaComponent;
        dwellRippleShader.setColorUniform("in_color", alphaComponent);
        this.this$0.invalidate();
    }
}
