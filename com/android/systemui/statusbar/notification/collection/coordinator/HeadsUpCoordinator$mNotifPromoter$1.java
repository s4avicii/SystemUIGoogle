package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mNotifPromoter$1 extends NotifPromoter {
    public final /* synthetic */ HeadsUpCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinator$mNotifPromoter$1(HeadsUpCoordinator headsUpCoordinator) {
        super("HeadsUpCoordinator");
        this.this$0 = headsUpCoordinator;
    }

    public final boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
        return HeadsUpCoordinator.access$isGoingToShowHunNoRetract(this.this$0, notificationEntry);
    }
}
