package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$collectionListener$1 implements NotifCollectionListener {
    public final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    public SmartspaceDedupingCoordinator$collectionListener$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.this$0 = smartspaceDedupingCoordinator;
    }

    public final void onEntryAdded(NotificationEntry notificationEntry) {
        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) this.this$0.trackedSmartspaceTargets.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget != null) {
            this.this$0.updateFilterStatus(trackedSmartspaceTarget);
        }
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) this.this$0.trackedSmartspaceTargets.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget != null) {
            Objects.requireNonNull(this.this$0);
            Runnable runnable = trackedSmartspaceTarget.cancelTimeoutRunnable;
            if (runnable != null) {
                runnable.run();
            }
            trackedSmartspaceTarget.cancelTimeoutRunnable = null;
            trackedSmartspaceTarget.alertExceptionExpires = 0;
        }
    }

    public final void onEntryUpdated(NotificationEntry notificationEntry) {
        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) this.this$0.trackedSmartspaceTargets.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget != null) {
            this.this$0.updateFilterStatus(trackedSmartspaceTarget);
        }
    }
}
