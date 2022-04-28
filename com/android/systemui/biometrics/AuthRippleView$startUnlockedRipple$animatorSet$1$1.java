package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startUnlockedRipple$animatorSet$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $onAnimationEnd;
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$startUnlockedRipple$animatorSet$1$1(AuthRippleView authRippleView, AuthRippleController$showUnlockedRipple$2 authRippleController$showUnlockedRipple$2) {
        this.this$0 = authRippleView;
        this.$onAnimationEnd = authRippleController$showUnlockedRipple$2;
    }

    public final void onAnimationEnd(Animator animator) {
        Runnable runnable = this.$onAnimationEnd;
        if (runnable != null) {
            runnable.run();
        }
        AuthRippleView authRippleView = this.this$0;
        authRippleView.unlockedRippleInProgress = false;
        authRippleView.drawRipple = false;
        authRippleView.setVisibility(8);
    }

    public final void onAnimationStart(Animator animator) {
        AuthRippleView authRippleView = this.this$0;
        authRippleView.unlockedRippleInProgress = true;
        RippleShader rippleShader = authRippleView.rippleShader;
        Objects.requireNonNull(rippleShader);
        rippleShader.shouldFadeOutRipple = true;
        AuthRippleView authRippleView2 = this.this$0;
        authRippleView2.drawRipple = true;
        authRippleView2.setVisibility(0);
    }
}
