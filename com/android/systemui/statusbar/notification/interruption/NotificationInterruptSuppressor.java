package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface NotificationInterruptSuppressor {
    boolean suppressAwakeHeadsUp(NotificationEntry notificationEntry) {
        return false;
    }

    boolean suppressAwakeInterruptions() {
        return false;
    }

    boolean suppressInterruptions() {
        return false;
    }

    String getName() {
        return getClass().getName();
    }
}
