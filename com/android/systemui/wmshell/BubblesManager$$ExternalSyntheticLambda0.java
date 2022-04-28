package com.android.systemui.wmshell;

import com.android.systemui.statusbar.NotificationRemoveInterceptor;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda0 implements NotificationRemoveInterceptor {
    public final /* synthetic */ BubblesManager f$0;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda0(BubblesManager bubblesManager) {
        this.f$0 = bubblesManager;
    }

    public final boolean onNotificationRemoveRequested(NotificationEntry notificationEntry, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        BubblesManager bubblesManager = this.f$0;
        Objects.requireNonNull(bubblesManager);
        boolean z5 = true;
        if (i == 3) {
            z = true;
        } else {
            z = false;
        }
        if (i == 2 || i == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (i == 8 || i == 9) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (i == 12) {
            z4 = true;
        } else {
            z4 = false;
        }
        if ((notificationEntry == null || !notificationEntry.isRowDismissed() || z3) && !z && !z2 && !z4) {
            z5 = false;
        }
        if (z5) {
            return bubblesManager.handleDismissalInterception(notificationEntry);
        }
        return false;
    }
}
