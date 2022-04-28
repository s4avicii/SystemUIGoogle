package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.SystemClock;
import com.android.systemui.statusbar.AlertingNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Objects;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mLifetimeExtender$1 implements NotifLifetimeExtender {
    public final /* synthetic */ HeadsUpCoordinator this$0;

    public final String getName() {
        return "HeadsUpCoordinator";
    }

    public HeadsUpCoordinator$mLifetimeExtender$1(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        this.this$0.mNotifsExtendingLifetime.remove(notificationEntry);
    }

    public final boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
        boolean z = false;
        if (this.this$0.mHeadsUpManager.canRemoveImmediately(notificationEntry.mKey)) {
            return false;
        }
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        Objects.requireNonNull(headsUpCoordinator);
        HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpManager);
        AlertingNotificationManager.AlertEntry alertEntry = headsUpManager.mAlertEntries.get(str);
        if (alertEntry != null) {
            z = alertEntry.isSticky();
        }
        if (z) {
            HeadsUpManager headsUpManager2 = this.this$0.mHeadsUpManager;
            String str2 = notificationEntry.mKey;
            Objects.requireNonNull(headsUpManager2);
            AlertingNotificationManager.AlertEntry alertEntry2 = headsUpManager2.mAlertEntries.get(str2);
            long j = 0;
            if (alertEntry2 != null) {
                long j2 = alertEntry2.mEarliestRemovaltime;
                Objects.requireNonNull(headsUpManager2.mClock);
                j = Math.max(0, j2 - SystemClock.elapsedRealtime());
            }
            HeadsUpCoordinator headsUpCoordinator2 = this.this$0;
            headsUpCoordinator2.mExecutor.executeDelayed(new HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$1(headsUpCoordinator2, notificationEntry), j);
        } else {
            HeadsUpCoordinator headsUpCoordinator3 = this.this$0;
            headsUpCoordinator3.mExecutor.execute(new HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2(headsUpCoordinator3, notificationEntry));
        }
        this.this$0.mNotifsExtendingLifetime.add(notificationEntry);
        return true;
    }

    public final void setCallback(NotifCollection$$ExternalSyntheticLambda2 notifCollection$$ExternalSyntheticLambda2) {
        this.this$0.mEndLifetimeExtension = notifCollection$$ExternalSyntheticLambda2;
    }
}
