package com.android.launcher3.util;

import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FlagOp$$ExternalSyntheticLambda0 implements FlagOp {
    public final /* synthetic */ FlagOp f$0 = DebugInfo$$ExternalSyntheticOutline0.INSTANCE;
    public final /* synthetic */ int f$1 = 1;

    public final int apply(int i) {
        FlagOp flagOp = this.f$0;
        int i2 = this.f$1;
        Objects.requireNonNull(flagOp);
        return i2 | flagOp.apply(i);
    }
}
