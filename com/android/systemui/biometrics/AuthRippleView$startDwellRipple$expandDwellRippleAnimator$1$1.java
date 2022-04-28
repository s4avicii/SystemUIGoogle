package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.charging.DwellRippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        long currentPlayTime = valueAnimator.getCurrentPlayTime();
        DwellRippleShader dwellRippleShader = this.this$0.dwellShader;
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        dwellRippleShader.setProgress(((Float) animatedValue).floatValue());
        this.this$0.dwellShader.setTime((float) currentPlayTime);
        this.this$0.invalidate();
    }
}
