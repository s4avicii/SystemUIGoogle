package com.android.systemui.p006qs.tiles.dialog;

import android.graphics.drawable.ColorDrawable;
import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda5 implements Predicate {
    public static final /* synthetic */ InternetDialogController$$ExternalSyntheticLambda5 INSTANCE = new InternetDialogController$$ExternalSyntheticLambda5();

    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        ColorDrawable colorDrawable = InternetDialogController.EMPTY_DRAWABLE;
        if (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) {
            return false;
        }
        return true;
    }
}
