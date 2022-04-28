package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface NotificationRemoveInterceptor {
    boolean onNotificationRemoveRequested(NotificationEntry notificationEntry, int i);
}
