package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.dagger.StatusBarComponent;

/* compiled from: NotifPanelEventSource.kt */
public final class EventSourceStatusBarStartableImpl implements StatusBarComponent.Startable {
    public final NotifPanelEventSourceManager manager;
    public final NotificationPanelViewController notifPanelController;

    public final void start() {
        this.manager.setEventSource(this.notifPanelController);
    }

    public final void stop() {
        this.manager.setEventSource((NotificationPanelViewController) null);
    }

    public EventSourceStatusBarStartableImpl(NotifPanelEventSourceManager notifPanelEventSourceManager, NotificationPanelViewController notificationPanelViewController) {
        this.manager = notifPanelEventSourceManager;
        this.notifPanelController = notificationPanelViewController;
    }
}
