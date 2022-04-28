package com.android.keyguard;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ KeyguardSecurityContainerController f$0;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda0(KeyguardSecurityContainerController keyguardSecurityContainerController) {
        this.f$0 = keyguardSecurityContainerController;
    }

    public final Object get() {
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.f$0;
        Objects.requireNonNull(keyguardSecurityContainerController);
        return keyguardSecurityContainerController.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
    }
}
