package com.android.systemui.statusbar.phone;

import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import java.util.Objects;

/* compiled from: SplitShadeHeaderController.kt */
public final class SplitShadeHeaderController$updateListeners$1 implements QSCarrierGroupController.OnSingleCarrierChangedListener {
    public final /* synthetic */ SplitShadeHeaderController this$0;

    public SplitShadeHeaderController$updateListeners$1(SplitShadeHeaderController splitShadeHeaderController) {
        this.this$0 = splitShadeHeaderController;
    }

    public final void onSingleCarrierChanged(boolean z) {
        SplitShadeHeaderController splitShadeHeaderController = this.this$0;
        Objects.requireNonNull(splitShadeHeaderController);
        if (z) {
            splitShadeHeaderController.iconContainer.removeIgnoredSlots(splitShadeHeaderController.carrierIconSlots);
        } else {
            splitShadeHeaderController.iconContainer.addIgnoredSlots(splitShadeHeaderController.carrierIconSlots);
        }
    }
}
