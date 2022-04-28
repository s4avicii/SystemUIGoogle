package com.android.systemui.privacy;

import android.os.UserHandle;
import com.android.systemui.appops.AppOpsController;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$cb$1 implements AppOpsController.Callback {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$cb$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    public final void onActiveStateChanged(int i, int i2, String str, boolean z) {
        Objects.requireNonNull(PrivacyItemController.Companion);
        if (ArraysKt___ArraysKt.contains(PrivacyItemController.OPS_LOCATION, i)) {
            PrivacyItemController privacyItemController = this.this$0;
            Objects.requireNonNull(privacyItemController);
            if (!privacyItemController.locationAvailable) {
                return;
            }
        }
        if (this.this$0.currentUserIds.contains(Integer.valueOf(UserHandle.getUserId(i2))) || i == 100 || i == 101) {
            this.this$0.logger.logUpdatedItemFromAppOps(i, i2, str, z);
            this.this$0.update(false);
        }
    }
}
