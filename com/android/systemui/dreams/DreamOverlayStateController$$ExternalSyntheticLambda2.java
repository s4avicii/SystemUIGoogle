package com.android.systemui.dreams;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda2(DreamOverlayStateController dreamOverlayStateController, int i) {
        this.f$0 = dreamOverlayStateController;
        this.f$1 = i;
    }

    public final void run() {
        DreamOverlayStateController dreamOverlayStateController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(dreamOverlayStateController);
        dreamOverlayStateController.mAvailableComplicationTypes = i;
        dreamOverlayStateController.mCallbacks.forEach(DreamOverlayStateController$$ExternalSyntheticLambda4.INSTANCE);
    }
}
