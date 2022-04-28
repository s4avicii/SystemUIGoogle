package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.PipBoundsState;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda1(boolean z, int i) {
        this.f$0 = z;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        boolean z = this.f$0;
        int i = this.f$1;
        PipController pipController = (PipController) obj;
        Objects.requireNonNull(pipController);
        if (!z) {
            i = 0;
        }
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        pipBoundsState.setShelfVisibility(z, i, true);
    }
}
