package com.android.systemui.navigationbar;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda12(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NavigationBar navigationBar = (NavigationBar) this.f$0;
                Objects.requireNonNull(navigationBar);
                navigationBar.mHandler.postDelayed(navigationBar.mOnVariableDurationHomeLongClick, ((Long) obj).longValue());
                return;
            default:
                NotificationListenerWithPlugins notificationListenerWithPlugins = (NotificationListenerWithPlugins) this.f$0;
                int i = NotificationListenerWithPlugins.$r8$clinit;
                Objects.requireNonNull(notificationListenerWithPlugins);
                ((NotificationListenerController) obj).onListenerConnected(new NotificationListenerController.NotificationProvider() {
                    public final void addNotification(StatusBarNotification statusBarNotification) {
                        NotificationListenerWithPlugins.this.onNotificationPosted(statusBarNotification, getRankingMap());
                    }

                    public final StatusBarNotification[] getActiveNotifications() {
                        return NotificationListenerWithPlugins.super.getActiveNotifications();
                    }

                    public final NotificationListenerService.RankingMap getRankingMap() {
                        return NotificationListenerWithPlugins.super.getCurrentRanking();
                    }

                    public final void removeNotification(StatusBarNotification statusBarNotification) {
                        NotificationListenerWithPlugins.this.onNotificationRemoved(statusBarNotification, getRankingMap());
                    }

                    public final void updateRanking() {
                        NotificationListenerWithPlugins.this.onNotificationRankingUpdate(getRankingMap());
                    }
                });
                return;
        }
    }
}
