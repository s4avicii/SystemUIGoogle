package com.android.systemui.statusbar.notification;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public interface NotificationEntryListener {
    void onEntryInflated(NotificationEntry notificationEntry) {
    }

    void onEntryReinflated(NotificationEntry notificationEntry) {
    }

    void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
    }

    void onInflationError(StatusBarNotification statusBarNotification, Exception exc) {
    }

    void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }

    void onNotificationRankingUpdated(NotificationListenerService.RankingMap rankingMap) {
    }

    void onPendingEntryAdded(NotificationEntry notificationEntry) {
    }

    void onPostEntryUpdated(NotificationEntry notificationEntry) {
    }

    void onPreEntryUpdated(NotificationEntry notificationEntry) {
    }
}
