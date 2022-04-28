package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.render.NotifPanelEventSourceModule_ProvideManagerFactory;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.dagger.StatusBarComponent;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarNotifPanelEventSourceModule_BindStartableFactory implements Factory<StatusBarComponent.Startable> {
    public final Provider<NotifPanelEventSourceManager> managerProvider = NotifPanelEventSourceModule_ProvideManagerFactory.InstanceHolder.INSTANCE;
    public final Provider<NotificationPanelViewController> notifPanelControllerProvider;

    public StatusBarNotifPanelEventSourceModule_BindStartableFactory(Provider provider) {
        this.notifPanelControllerProvider = provider;
    }

    public final Object get() {
        return new EventSourceStatusBarStartableImpl(this.managerProvider.get(), this.notifPanelControllerProvider.get());
    }
}
