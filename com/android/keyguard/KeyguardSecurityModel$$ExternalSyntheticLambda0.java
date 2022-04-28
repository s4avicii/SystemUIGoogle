package com.android.keyguard;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSecurityModel$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ KeyguardSecurityModel f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardSecurityModel$$ExternalSyntheticLambda0(KeyguardSecurityModel keyguardSecurityModel, int i) {
        this.f$0 = keyguardSecurityModel;
        this.f$1 = i;
    }

    public final Object get() {
        KeyguardSecurityModel keyguardSecurityModel = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(keyguardSecurityModel);
        return Integer.valueOf(keyguardSecurityModel.mLockPatternUtils.getActivePasswordQuality(i));
    }
}
