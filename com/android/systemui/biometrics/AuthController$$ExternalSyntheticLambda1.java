package com.android.systemui.biometrics;

import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthController$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ AuthController f$0;

    public /* synthetic */ AuthController$$ExternalSyntheticLambda1(AuthController authController) {
        this.f$0 = authController;
    }

    public final Object invoke() {
        AuthController authController = this.f$0;
        Objects.requireNonNull(authController);
        authController.updateFingerprintLocation();
        AuthDialog authDialog = authController.mCurrentDialog;
        if (authDialog != null) {
            ((AuthContainerView) authDialog).maybeUpdatePositionForUdfps(true);
        }
        return Unit.INSTANCE;
    }
}
