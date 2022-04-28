package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Objects;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2 implements Runnable {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2(HeadsUpCoordinator headsUpCoordinator, NotificationEntry notificationEntry) {
        this.this$0 = headsUpCoordinator;
        this.$entry = notificationEntry;
    }

    public final void run() {
        HeadsUpManager headsUpManager = this.this$0.mHeadsUpManager;
        NotificationEntry notificationEntry = this.$entry;
        Objects.requireNonNull(notificationEntry);
        headsUpManager.removeNotification(notificationEntry.mKey, false);
    }
}
