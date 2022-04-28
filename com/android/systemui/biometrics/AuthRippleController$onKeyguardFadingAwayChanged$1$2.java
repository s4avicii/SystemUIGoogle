package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$onKeyguardFadingAwayChanged$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ LightRevealScrim $lightRevealScrim;
    public final /* synthetic */ AuthRippleController this$0;

    public AuthRippleController$onKeyguardFadingAwayChanged$1$2(LightRevealScrim lightRevealScrim, AuthRippleController authRippleController) {
        this.$lightRevealScrim = lightRevealScrim;
        this.this$0 = authRippleController;
    }

    public final void onAnimationEnd(Animator animator) {
        LightRevealScrim lightRevealScrim = this.$lightRevealScrim;
        Objects.requireNonNull(lightRevealScrim);
        if (Intrinsics.areEqual(lightRevealScrim.revealEffect, this.this$0.circleReveal)) {
            this.$lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
        }
    }
}
