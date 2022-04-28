package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda4 implements Consumer {
    public static final /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda4 INSTANCE = new DreamOverlayStateController$$ExternalSyntheticLambda4();

    public final void accept(Object obj) {
        ((DreamOverlayStateController.Callback) obj).onAvailableComplicationTypesChanged();
    }
}
