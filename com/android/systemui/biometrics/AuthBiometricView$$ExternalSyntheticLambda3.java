package com.android.systemui.biometrics;

import android.animation.ValueAnimator;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthBiometricView$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ AuthBiometricView f$0;

    public /* synthetic */ AuthBiometricView$$ExternalSyntheticLambda3(AuthBiometricView authBiometricView) {
        this.f$0 = authBiometricView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AuthBiometricView authBiometricView = this.f$0;
        int i = AuthBiometricView.$r8$clinit;
        authBiometricView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
