package com.android.systemui.statusbar;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda12;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;

@SuppressLint({"OverrideAbstract"})
public final class NotificationListener extends NotificationListenerWithPlugins {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final BubbleStackView$$ExternalSyntheticLambda19 mDispatchRankingUpdateRunnable = new BubbleStackView$$ExternalSyntheticLambda19(this, 4);
    public final Executor mMainExecutor;
    public final ArrayList mNotificationHandlers = new ArrayList();
    public final NotificationManager mNotificationManager;
    public final ConcurrentLinkedDeque mRankingMapQueue = new ConcurrentLinkedDeque();
    public final ArrayList<NotificationSettingsListener> mSettingsListeners = new ArrayList<>();
    public long mSkippingRankingUpdatesSince = -1;
    public final SystemClock mSystemClock;

    public interface NotificationHandler {
        void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }

        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();
    }

    public interface NotificationSettingsListener {
        void onStatusBarIconsBehaviorChanged(boolean z) {
        }
    }

    public final void onListenerConnected() {
        this.mConnected = true;
        this.mPlugins.forEach(new NavigationBar$$ExternalSyntheticLambda12(this, 1));
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
            return;
        }
        this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda2(this, activeNotifications, getCurrentRanking()));
        onSilentStatusBarIconsVisibilityChanged(this.mNotificationManager.shouldHideSilentStatusBarIcons());
    }

    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        boolean z;
        if (statusBarNotification != null) {
            Iterator<NotificationListenerController> it = this.mPlugins.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().onNotificationRemoved(statusBarNotification, rankingMap)) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda0(this, statusBarNotification, rankingMap, i));
            }
        }
    }

    public final void addNotificationHandler(NotificationHandler notificationHandler) {
        if (!this.mNotificationHandlers.contains(notificationHandler)) {
            this.mNotificationHandlers.add(notificationHandler);
            return;
        }
        throw new IllegalArgumentException("Listener is already added");
    }

    public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        boolean z;
        Iterator<NotificationListenerController> it = this.mPlugins.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().onNotificationChannelModified(str, userHandle, notificationChannel, i)) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda1(this, str, userHandle, notificationChannel, i));
        }
    }

    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        boolean z;
        if (statusBarNotification != null) {
            Iterator<NotificationListenerController> it = this.mPlugins.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().onNotificationPosted(statusBarNotification, rankingMap)) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                this.mMainExecutor.execute(new OverviewProxyService$1$$ExternalSyntheticLambda0(this, statusBarNotification, rankingMap, 1));
            }
        }
    }

    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        if (rankingMap != null) {
            Iterator<NotificationListenerController> it = this.mPlugins.iterator();
            while (it.hasNext()) {
                rankingMap = it.next().getCurrentRanking(rankingMap);
            }
            this.mRankingMapQueue.addLast(rankingMap);
            this.mMainExecutor.execute(this.mDispatchRankingUpdateRunnable);
        }
    }

    public final void onSilentStatusBarIconsVisibilityChanged(boolean z) {
        Iterator<NotificationSettingsListener> it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusBarIconsBehaviorChanged(z);
        }
    }

    public final void registerAsSystemService() {
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), NotificationListener.class.getCanonicalName()), -1);
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }

    public NotificationListener(Context context, NotificationManager notificationManager, SystemClock systemClock, Executor executor, PluginManager pluginManager) {
        super(pluginManager);
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mSystemClock = systemClock;
        this.mMainExecutor = executor;
    }

    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification, rankingMap, 0);
    }
}
