package com.android.systemui.biometrics;

import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ UdfpsController f$0;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda1(UdfpsController udfpsController) {
        this.f$0 = udfpsController;
    }

    public final Object invoke() {
        UdfpsController udfpsController = this.f$0;
        Objects.requireNonNull(udfpsController);
        boolean isShowingAlternateAuth = udfpsController.mKeyguardViewManager.isShowingAlternateAuth();
        UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
        if (udfpsControllerOverlay != null) {
            udfpsController.hideUdfpsOverlay();
            udfpsController.showUdfpsOverlay(udfpsControllerOverlay);
        }
        if (isShowingAlternateAuth) {
            udfpsController.mKeyguardViewManager.showGenericBouncer();
        }
        return Unit.INSTANCE;
    }
}
