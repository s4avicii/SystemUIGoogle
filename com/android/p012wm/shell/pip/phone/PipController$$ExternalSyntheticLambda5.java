package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.PipBoundsState;
import java.util.Objects;
import java.util.function.IntConsumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda5 implements IntConsumer {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda5(PipController pipController) {
        this.f$0 = pipController;
    }

    public final void accept(int i) {
        PipController pipController = this.f$0;
        Objects.requireNonNull(pipController);
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        pipBoundsState.mDisplayId = i;
        pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), false);
    }
}
