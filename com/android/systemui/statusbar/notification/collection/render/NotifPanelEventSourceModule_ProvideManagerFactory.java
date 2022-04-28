package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

public final class NotifPanelEventSourceModule_ProvideManagerFactory implements Factory<NotifPanelEventSourceManager> {

    public static final class InstanceHolder {
        public static final NotifPanelEventSourceModule_ProvideManagerFactory INSTANCE = new NotifPanelEventSourceModule_ProvideManagerFactory();
    }

    public final Object get() {
        return new NotifPanelEventSourceManagerImpl();
    }
}
