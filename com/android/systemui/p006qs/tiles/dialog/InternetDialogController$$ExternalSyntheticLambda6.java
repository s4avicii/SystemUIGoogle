package com.android.systemui.p006qs.tiles.dialog;

import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda6 implements Supplier {
    public final /* synthetic */ InternetDialogController f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda6(InternetDialogController internetDialogController) {
        this.f$0 = internetDialogController;
    }

    public final Object get() {
        InternetDialogController internetDialogController = this.f$0;
        Objects.requireNonNull(internetDialogController);
        return internetDialogController.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo().stream().filter(InternetDialogController$$ExternalSyntheticLambda5.INSTANCE).map(new InternetDialogController$$ExternalSyntheticLambda0(internetDialogController));
    }
}
