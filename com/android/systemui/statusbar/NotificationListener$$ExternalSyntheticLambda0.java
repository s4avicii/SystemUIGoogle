package com.android.systemui.statusbar;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NotificationListener f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ NotificationListenerService.RankingMap f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ NotificationListener$$ExternalSyntheticLambda0(NotificationListener notificationListener, StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        this.f$0 = notificationListener;
        this.f$1 = statusBarNotification;
        this.f$2 = rankingMap;
        this.f$3 = i;
    }

    public final void run() {
        NotificationListener notificationListener = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        NotificationListenerService.RankingMap rankingMap = this.f$2;
        int i = this.f$3;
        Objects.requireNonNull(notificationListener);
        Iterator it = notificationListener.mNotificationHandlers.iterator();
        while (it.hasNext()) {
            ((NotificationListener.NotificationHandler) it.next()).onNotificationRemoved(statusBarNotification, rankingMap, i);
        }
    }
}
