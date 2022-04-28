package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthPanelController$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthPanelController f$0;

    public /* synthetic */ AuthPanelController$$ExternalSyntheticLambda1(AuthPanelController authPanelController) {
        this.f$0 = authPanelController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AuthPanelController authPanelController = this.f$0;
        Objects.requireNonNull(authPanelController);
        authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
    }
}
