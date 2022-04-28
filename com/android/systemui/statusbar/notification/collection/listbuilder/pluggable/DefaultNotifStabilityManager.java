package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: NotifStabilityManager.kt */
public final class DefaultNotifStabilityManager extends NotifStabilityManager {
    public static final DefaultNotifStabilityManager INSTANCE = new DefaultNotifStabilityManager();

    public final boolean isEntryReorderingAllowed() {
        return true;
    }

    public final boolean isEveryChangeAllowed() {
        return true;
    }

    public final boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
        return true;
    }

    public final boolean isPipelineRunAllowed() {
        return true;
    }

    public final boolean isSectionChangeAllowed(NotificationEntry notificationEntry) {
        return true;
    }

    public final void onBeginRun() {
    }

    public final void onEntryReorderSuppressed() {
    }

    public DefaultNotifStabilityManager() {
        super("DefaultNotifStabilityManager");
    }
}
