package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$attach$2 implements NotificationLockscreenUserManager.KeyguardNotificationSuppressor {
    public final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    public SmartspaceDedupingCoordinator$attach$2(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.this$0 = smartspaceDedupingCoordinator;
    }

    public final boolean shouldSuppressOnKeyguard(NotificationEntry notificationEntry) {
        SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = this.this$0;
        Objects.requireNonNull(smartspaceDedupingCoordinator);
        LinkedHashMap linkedHashMap = smartspaceDedupingCoordinator.trackedSmartspaceTargets;
        Objects.requireNonNull(notificationEntry);
        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) linkedHashMap.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget == null) {
            return false;
        }
        return trackedSmartspaceTarget.shouldFilter;
    }
}
