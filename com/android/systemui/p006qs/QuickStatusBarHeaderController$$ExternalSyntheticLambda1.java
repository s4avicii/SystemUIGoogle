package com.android.systemui.p006qs;

import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QuickStatusBarHeaderController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QuickStatusBarHeaderController$$ExternalSyntheticLambda1 implements QSCarrierGroupController.OnSingleCarrierChangedListener {
    public final /* synthetic */ QuickStatusBarHeader f$0;

    public /* synthetic */ QuickStatusBarHeaderController$$ExternalSyntheticLambda1(QuickStatusBarHeader quickStatusBarHeader) {
        this.f$0 = quickStatusBarHeader;
    }

    public final void onSingleCarrierChanged(boolean z) {
        QuickStatusBarHeader quickStatusBarHeader = this.f$0;
        Objects.requireNonNull(quickStatusBarHeader);
        quickStatusBarHeader.mIsSingleCarrier = z;
        quickStatusBarHeader.updateAlphaAnimator();
    }
}
