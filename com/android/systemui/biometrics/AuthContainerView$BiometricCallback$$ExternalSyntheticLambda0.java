package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthContainerView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthContainerView$BiometricCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AuthContainerView.BiometricCallback f$0;

    public /* synthetic */ AuthContainerView$BiometricCallback$$ExternalSyntheticLambda0(AuthContainerView.BiometricCallback biometricCallback) {
        this.f$0 = biometricCallback;
    }

    public final void run() {
        AuthContainerView.BiometricCallback biometricCallback = this.f$0;
        Objects.requireNonNull(biometricCallback);
        AuthContainerView authContainerView = AuthContainerView.this;
        int i = AuthContainerView.$r8$clinit;
        authContainerView.addCredentialView(false, true);
    }
}
