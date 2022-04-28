package com.android.systemui.dreams;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda7 implements Consumer {
    public static final /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda7 INSTANCE = new DreamOverlayStateController$$ExternalSyntheticLambda7(0);
    public static final /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda7 INSTANCE$1 = new DreamOverlayStateController$$ExternalSyntheticLambda7(1);
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((DreamOverlayStateController.Callback) obj).onAvailableComplicationTypesChanged();
                return;
            default:
                ((LegacySplitScreen) obj).onAppTransitionFinished();
                return;
        }
    }

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }
}
