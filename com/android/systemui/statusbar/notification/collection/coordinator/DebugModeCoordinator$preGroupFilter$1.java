package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import java.util.Objects;

/* compiled from: DebugModeCoordinator.kt */
public final class DebugModeCoordinator$preGroupFilter$1 extends NotifFilter {
    public final /* synthetic */ DebugModeCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DebugModeCoordinator$preGroupFilter$1(DebugModeCoordinator debugModeCoordinator) {
        super("DebugModeCoordinator");
        this.this$0 = debugModeCoordinator;
    }

    public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
        DebugModeFilterProvider debugModeFilterProvider = this.this$0.debugModeFilterProvider;
        Objects.requireNonNull(debugModeFilterProvider);
        if (debugModeFilterProvider.allowedPackages.isEmpty()) {
            return false;
        }
        return !debugModeFilterProvider.allowedPackages.contains(notificationEntry.mSbn.getPackageName());
    }
}
