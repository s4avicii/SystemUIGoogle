package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface NotifDismissInterceptor {

    public interface OnEndDismissInterception {
    }

    void cancelDismissInterception(NotificationEntry notificationEntry);

    void getName();

    boolean shouldInterceptDismissal(NotificationEntry notificationEntry);
}
