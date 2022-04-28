package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda3(DreamOverlayStateController dreamOverlayStateController) {
        DreamOverlayStateController$$ExternalSyntheticLambda8 dreamOverlayStateController$$ExternalSyntheticLambda8 = DreamOverlayStateController$$ExternalSyntheticLambda8.INSTANCE;
        this.f$0 = dreamOverlayStateController;
        this.f$1 = dreamOverlayStateController$$ExternalSyntheticLambda8;
    }

    public final void run() {
        DreamOverlayStateController dreamOverlayStateController = this.f$0;
        Consumer consumer = this.f$1;
        Objects.requireNonNull(dreamOverlayStateController);
        Iterator<DreamOverlayStateController.Callback> it = dreamOverlayStateController.mCallbacks.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }
}
