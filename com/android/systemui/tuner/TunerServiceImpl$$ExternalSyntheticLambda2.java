package com.android.systemui.tuner;

import android.provider.Settings;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TunerServiceImpl$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ TunerServiceImpl f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ TunerServiceImpl$$ExternalSyntheticLambda2(TunerServiceImpl tunerServiceImpl, String str) {
        this.f$0 = tunerServiceImpl;
        this.f$1 = str;
    }

    public final Object get() {
        TunerServiceImpl tunerServiceImpl = this.f$0;
        String str = this.f$1;
        Objects.requireNonNull(tunerServiceImpl);
        return Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
    }
}
