package com.android.systemui.p006qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ InternetDialogController f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda0(InternetDialogController internetDialogController) {
        this.f$0 = internetDialogController;
    }

    public final Object apply(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        Objects.requireNonNull(this.f$0);
        return new Object(subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim()) {
            public CharSequence originalName;
            public SubscriptionInfo subscriptionInfo;
            public CharSequence uniqueName;

            {
                this.subscriptionInfo = r1;
                this.originalName = r2;
            }
        };
    }
}
