package com.android.systemui.statusbar;

import android.app.NotificationChannel;
import android.content.pm.ShortcutInfo;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.NotificationListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationListener$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ NotificationListener f$0;
    public final /* synthetic */ StatusBarNotification[] f$1;
    public final /* synthetic */ NotificationListenerService.RankingMap f$2;

    public /* synthetic */ NotificationListener$$ExternalSyntheticLambda2(NotificationListener notificationListener, StatusBarNotification[] statusBarNotificationArr, NotificationListenerService.RankingMap rankingMap) {
        this.f$0 = notificationListener;
        this.f$1 = statusBarNotificationArr;
        this.f$2 = rankingMap;
    }

    public final void run() {
        NotificationListener notificationListener = this.f$0;
        StatusBarNotification[] statusBarNotificationArr = this.f$1;
        NotificationListenerService.RankingMap rankingMap = this.f$2;
        Objects.requireNonNull(notificationListener);
        ArrayList arrayList = new ArrayList();
        for (StatusBarNotification key : statusBarNotificationArr) {
            String key2 = key.getKey();
            NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
            if (!rankingMap.getRanking(key2, ranking)) {
                ArrayList arrayList2 = r8;
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = r8;
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = r8;
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = r8;
                ArrayList arrayList9 = new ArrayList();
                ranking.populate(key2, 0, false, 0, 0, 0, (CharSequence) null, (String) null, (NotificationChannel) null, arrayList2, arrayList4, false, 0, false, 0, false, arrayList6, arrayList8, false, false, false, (ShortcutInfo) null, 0, false);
            }
            arrayList.add(ranking);
        }
        NotificationListenerService.RankingMap rankingMap2 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
        for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
            Iterator it = notificationListener.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationListener.NotificationHandler) it.next()).onNotificationPosted(statusBarNotification, rankingMap2);
            }
        }
        Iterator it2 = notificationListener.mNotificationHandlers.iterator();
        while (it2.hasNext()) {
            ((NotificationListener.NotificationHandler) it2.next()).onNotificationsInitialized();
        }
    }
}
