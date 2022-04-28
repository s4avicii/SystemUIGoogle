package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.statusbar.charging.DwellRippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView$retractRipple$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ AuthRippleView this$0;

    public AuthRippleView$retractRipple$1$1(AuthRippleView authRippleView) {
        this.this$0 = authRippleView;
    }

    public final void onAnimationEnd(Animator animator) {
        AuthRippleView authRippleView = this.this$0;
        authRippleView.drawDwell = false;
        DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
        Objects.requireNonNull(dwellRippleShader);
        int alphaComponent = ColorUtils.setAlphaComponent(dwellRippleShader.color, 255);
        dwellRippleShader.color = alphaComponent;
        dwellRippleShader.setColorUniform("in_color", alphaComponent);
    }

    public final void onAnimationStart(Animator animator) {
        AnimatorSet animatorSet = this.this$0.dwellPulseOutAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.this$0.drawDwell = true;
    }
}
