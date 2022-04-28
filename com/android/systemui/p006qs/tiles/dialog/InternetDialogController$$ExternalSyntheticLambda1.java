package com.android.systemui.p006qs.tiles.dialog;

import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Function;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda1(Set set) {
        this.f$0 = set;
    }

    public final Object apply(Object obj) {
        InternetDialogController.AnonymousClass1DisplayInfo r2 = (InternetDialogController.AnonymousClass1DisplayInfo) obj;
        if (this.f$0.contains(r2.uniqueName)) {
            r2.uniqueName = r2.originalName + " " + r2.subscriptionInfo.getSubscriptionId();
        }
        return r2;
    }
}
