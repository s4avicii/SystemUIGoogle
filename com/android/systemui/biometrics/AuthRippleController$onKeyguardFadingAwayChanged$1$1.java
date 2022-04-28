package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.LightRevealScrim;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$onKeyguardFadingAwayChanged$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ LightRevealScrim $lightRevealScrim;
    public final /* synthetic */ AuthRippleController this$0;

    public AuthRippleController$onKeyguardFadingAwayChanged$1$1(LightRevealScrim lightRevealScrim, AuthRippleController authRippleController) {
        this.$lightRevealScrim = lightRevealScrim;
        this.this$0 = authRippleController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        LightRevealScrim lightRevealScrim = this.$lightRevealScrim;
        Objects.requireNonNull(lightRevealScrim);
        if (Intrinsics.areEqual(lightRevealScrim.revealEffect, this.this$0.circleReveal)) {
            LightRevealScrim lightRevealScrim2 = this.$lightRevealScrim;
            Object animatedValue = valueAnimator.getAnimatedValue();
            Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
            lightRevealScrim2.setRevealAmount(((Float) animatedValue).floatValue());
        }
    }
}
