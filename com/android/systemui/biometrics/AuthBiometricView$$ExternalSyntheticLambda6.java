package com.android.systemui.biometrics;

import android.util.Log;
import android.view.View;
import com.android.systemui.biometrics.AuthContainerView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthBiometricView$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ AuthBiometricView f$0;

    public /* synthetic */ AuthBiometricView$$ExternalSyntheticLambda6(AuthBiometricView authBiometricView) {
        this.f$0 = authBiometricView;
    }

    public final void onClick(View view) {
        AuthBiometricView authBiometricView = this.f$0;
        int i = AuthBiometricView.$r8$clinit;
        Objects.requireNonNull(authBiometricView);
        if (authBiometricView.mState == 6) {
            Log.w("BiometricPrompt/AuthBiometricView", "Ignoring background click after authenticated");
            return;
        }
        int i2 = authBiometricView.mSize;
        if (i2 == 1) {
            Log.w("BiometricPrompt/AuthBiometricView", "Ignoring background click during small dialog");
        } else if (i2 == 3) {
            Log.w("BiometricPrompt/AuthBiometricView", "Ignoring background click during large dialog");
        } else {
            ((AuthContainerView.BiometricCallback) authBiometricView.mCallback).onAction(2);
        }
    }
}
