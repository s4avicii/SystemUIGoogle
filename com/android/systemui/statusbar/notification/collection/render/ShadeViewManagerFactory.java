package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* compiled from: ShadeViewManager.kt */
public interface ShadeViewManagerFactory {
    ShadeViewManager create(NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl);
}
