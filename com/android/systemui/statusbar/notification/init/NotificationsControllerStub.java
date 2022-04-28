package com.android.systemui.statusbar.notification.init;

import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import java.util.Optional;

/* compiled from: NotificationsControllerStub.kt */
public final class NotificationsControllerStub implements NotificationsController {
    public final NotificationListener notificationListener;

    public final int getActiveNotificationsCount() {
        return 0;
    }

    public final void requestNotificationUpdate(String str) {
    }

    public final void resetUserExpandedStates() {
    }

    public final void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
    }

    public final void initialize(StatusBar statusBar, Optional optional, StatusBarNotificationPresenter statusBarNotificationPresenter, NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter, StatusBarNotificationPresenter statusBarNotificationPresenter2) {
        this.notificationListener.registerAsSystemService();
    }

    public NotificationsControllerStub(NotificationListener notificationListener2) {
        this.notificationListener = notificationListener2;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("Notification handling disabled");
        indentingPrintWriter.println();
    }
}
