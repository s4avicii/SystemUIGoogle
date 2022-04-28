package com.android.systemui.doze;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeScreenState$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ DozeScreenState f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DozeScreenState$$ExternalSyntheticLambda1(DozeScreenState dozeScreenState, int i) {
        this.f$0 = dozeScreenState;
        this.f$1 = i;
    }

    public final void run() {
        DozeScreenState dozeScreenState = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(dozeScreenState);
        dozeScreenState.applyScreenState(i);
    }
}
