package com.android.systemui.privacy;

import android.content.pm.UserInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$update$1 implements Runnable {
    public final /* synthetic */ boolean $updateUsers;
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$update$1(boolean z, PrivacyItemController privacyItemController) {
        this.$updateUsers = z;
        this.this$0 = privacyItemController;
    }

    public final void run() {
        if (this.$updateUsers) {
            PrivacyItemController privacyItemController = this.this$0;
            List<UserInfo> userProfiles = privacyItemController.userTracker.getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(userProfiles, 10));
            for (UserInfo userInfo : userProfiles) {
                arrayList.add(Integer.valueOf(userInfo.id));
            }
            privacyItemController.currentUserIds = arrayList;
            PrivacyItemController privacyItemController2 = this.this$0;
            privacyItemController2.logger.logCurrentProfilesChanged(privacyItemController2.currentUserIds);
        }
        this.this$0.updateListAndNotifyChanges.run();
    }
}
