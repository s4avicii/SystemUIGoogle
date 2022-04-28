package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda3 implements PanelExpansionListener {
    public final /* synthetic */ ScrimController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda3(ScrimController scrimController) {
        this.f$0 = scrimController;
    }

    public final void onPanelExpansionChanged(float f, boolean z, boolean z2) {
        ScrimController scrimController = this.f$0;
        Objects.requireNonNull(scrimController);
        scrimController.setRawPanelExpansionFraction(f);
    }
}
