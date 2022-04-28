package com.android.systemui.statusbar.p007tv.notifications;

import android.app.Notification;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.NotificationListener;

/* renamed from: com.android.systemui.statusbar.tv.notifications.TvNotificationHandler */
public class TvNotificationHandler extends CoreStartable implements NotificationListener.NotificationHandler {
    public final NotificationListener mNotificationListener;
    public final SparseArray<StatusBarNotification> mNotifications = new SparseArray<>();
    public Listener mUpdateListener;

    /* renamed from: com.android.systemui.statusbar.tv.notifications.TvNotificationHandler$Listener */
    public interface Listener {
    }

    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
    }

    public final void onNotificationsInitialized() {
    }

    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (!new Notification.TvExtender(statusBarNotification.getNotification()).isAvailableOnTv()) {
            Log.v("TvNotificationHandler", "Notification not added because it isn't relevant for tv");
            return;
        }
        this.mNotifications.put(statusBarNotification.getId(), statusBarNotification);
        Listener listener = this.mUpdateListener;
        if (listener != null) {
            ((TvNotificationPanelActivity) listener).notificationsUpdated(this.mNotifications);
        }
        Log.d("TvNotificationHandler", "Notification added");
    }

    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        if (this.mNotifications.contains(statusBarNotification.getId())) {
            this.mNotifications.remove(statusBarNotification.getId());
            Log.d("TvNotificationHandler", "Notification removed");
            Listener listener = this.mUpdateListener;
            if (listener != null) {
                ((TvNotificationPanelActivity) listener).notificationsUpdated(this.mNotifications);
            }
        }
    }

    public final void start() {
        this.mNotificationListener.addNotificationHandler(this);
        this.mNotificationListener.registerAsSystemService();
    }

    public TvNotificationHandler(Context context, NotificationListener notificationListener) {
        super(context);
        this.mNotificationListener = notificationListener;
    }
}
