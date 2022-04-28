package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$startDwellRipple$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$startDwellRipple$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationEnd(Animator animator) {
        AuthRippleView authRippleView = this.this$0;
        authRippleView.drawDwell = false;
        RippleShader rippleShader = authRippleView.rippleShader;
        Objects.requireNonNull(rippleShader);
        rippleShader.setColor(ColorUtils.setAlphaComponent(rippleShader.color, 255));
    }

    public final void onAnimationStart(Animator animator) {
        AnimatorSet animatorSet = this.this$0.retractAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.this$0.setVisibility(0);
        this.this$0.drawDwell = true;
    }
}
