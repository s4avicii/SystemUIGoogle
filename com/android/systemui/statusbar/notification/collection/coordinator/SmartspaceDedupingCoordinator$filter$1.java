package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$filter$1 extends NotifFilter {
    public final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SmartspaceDedupingCoordinator$filter$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        super("SmartspaceDedupingFilter");
        this.this$0 = smartspaceDedupingCoordinator;
    }

    public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
        boolean z;
        SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = this.this$0;
        if (!smartspaceDedupingCoordinator.isOnLockscreen) {
            return false;
        }
        LinkedHashMap linkedHashMap = smartspaceDedupingCoordinator.trackedSmartspaceTargets;
        Objects.requireNonNull(notificationEntry);
        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) linkedHashMap.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget == null) {
            z = false;
        } else {
            z = trackedSmartspaceTarget.shouldFilter;
        }
        if (z) {
            return true;
        }
        return false;
    }
}
