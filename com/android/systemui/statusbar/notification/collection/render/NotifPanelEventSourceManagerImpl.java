package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSource;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;

/* compiled from: NotifPanelEventSource.kt */
public final class NotifPanelEventSourceManagerImpl implements NotifPanelEventSourceManager, NotifPanelEventSource.Callbacks {
    public final ListenerSet<NotifPanelEventSource.Callbacks> callbackSet = new ListenerSet<>();
    public NotifPanelEventSource eventSource;

    public final void onPanelCollapsingChanged(boolean z) {
        Iterator<NotifPanelEventSource.Callbacks> it = this.callbackSet.iterator();
        while (it.hasNext()) {
            it.next().onPanelCollapsingChanged(z);
        }
    }

    public final void registerCallbacks(NotifPanelEventSource.Callbacks callbacks) {
        this.callbackSet.addIfAbsent(callbacks);
    }

    public final void setEventSource(NotificationPanelViewController notificationPanelViewController) {
        NotifPanelEventSource notifPanelEventSource = this.eventSource;
        if (notifPanelEventSource != null) {
            notifPanelEventSource.unregisterCallbacks(this);
        }
        if (notificationPanelViewController != null) {
            notificationPanelViewController.registerCallbacks(this);
        }
        this.eventSource = notificationPanelViewController;
    }

    public final void unregisterCallbacks(NotifPanelEventSource.Callbacks callbacks) {
        this.callbackSet.remove(callbacks);
    }
}
