package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$updateAlertException$1 implements Runnable {
    public final /* synthetic */ TrackedSmartspaceTarget $target;
    public final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    public SmartspaceDedupingCoordinator$updateAlertException$1(TrackedSmartspaceTarget trackedSmartspaceTarget, SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.$target = trackedSmartspaceTarget;
        this.this$0 = smartspaceDedupingCoordinator;
    }

    public final void run() {
        TrackedSmartspaceTarget trackedSmartspaceTarget = this.$target;
        Objects.requireNonNull(trackedSmartspaceTarget);
        trackedSmartspaceTarget.cancelTimeoutRunnable = null;
        TrackedSmartspaceTarget trackedSmartspaceTarget2 = this.$target;
        Objects.requireNonNull(trackedSmartspaceTarget2);
        trackedSmartspaceTarget2.shouldFilter = true;
        this.this$0.filter.invalidateList();
        this.this$0.notificationEntryManager.updateNotifications("deduping timeout expired");
    }
}
