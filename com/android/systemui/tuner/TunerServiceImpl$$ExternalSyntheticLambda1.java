package com.android.systemui.tuner;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TunerServiceImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ TunerServiceImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TunerServiceImpl$$ExternalSyntheticLambda1(TunerServiceImpl tunerServiceImpl, int i) {
        this.f$0 = tunerServiceImpl;
        this.f$1 = i;
    }

    public final void run() {
        TunerServiceImpl tunerServiceImpl = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(tunerServiceImpl);
        tunerServiceImpl.clearAllFromUser(i);
    }
}
