package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

/* compiled from: ShadeEventCoordinator.kt */
public final class ShadeEventCoordinator$mNotifCollectionListener$1 implements NotifCollectionListener {
    public final /* synthetic */ ShadeEventCoordinator this$0;

    public ShadeEventCoordinator$mNotifCollectionListener$1(ShadeEventCoordinator shadeEventCoordinator) {
        this.this$0 = shadeEventCoordinator;
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        ShadeEventCoordinator shadeEventCoordinator = this.this$0;
        boolean z = true;
        shadeEventCoordinator.mEntryRemoved = true;
        if (!(i == 1 || i == 3 || i == 2)) {
            z = false;
        }
        shadeEventCoordinator.mEntryRemovedByUser = z;
    }
}
