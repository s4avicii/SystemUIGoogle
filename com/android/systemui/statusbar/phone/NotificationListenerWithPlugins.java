package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginManager;
import java.util.ArrayList;
import java.util.Iterator;

public class NotificationListenerWithPlugins extends NotificationListenerService implements PluginListener<NotificationListenerController> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mConnected;
    public PluginManager mPluginManager;
    public ArrayList<NotificationListenerController> mPlugins = new ArrayList<>();

    public final void onPluginConnected(Plugin plugin, Context context) {
        NotificationListenerController notificationListenerController = (NotificationListenerController) plugin;
        this.mPlugins.add(notificationListenerController);
        if (this.mConnected) {
            notificationListenerController.onListenerConnected(new NotificationListenerController.NotificationProvider() {
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
        }
    }

    public final void onPluginDisconnected(Plugin plugin) {
        this.mPlugins.remove((NotificationListenerController) plugin);
    }

    public NotificationListenerWithPlugins(PluginManager pluginManager) {
        this.mPluginManager = pluginManager;
    }

    public final StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] activeNotifications = super.getActiveNotifications();
        Iterator<NotificationListenerController> it = this.mPlugins.iterator();
        while (it.hasNext()) {
            activeNotifications = it.next().getActiveNotifications(activeNotifications);
        }
        return activeNotifications;
    }

    public final NotificationListenerService.RankingMap getCurrentRanking() {
        NotificationListenerService.RankingMap currentRanking = super.getCurrentRanking();
        Iterator<NotificationListenerController> it = this.mPlugins.iterator();
        while (it.hasNext()) {
            currentRanking = it.next().getCurrentRanking(currentRanking);
        }
        return currentRanking;
    }

    public final void registerAsSystemService(Context context, ComponentName componentName, int i) throws RemoteException {
        super.registerAsSystemService(context, componentName, i);
        this.mPluginManager.addPluginListener(this, NotificationListenerController.class);
    }

    public final void unregisterAsSystemService() throws RemoteException {
        super.unregisterAsSystemService();
        this.mPluginManager.removePluginListener(this);
    }
}
