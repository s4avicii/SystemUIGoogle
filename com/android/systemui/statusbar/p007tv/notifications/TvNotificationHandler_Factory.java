package com.android.systemui.statusbar.p007tv.notifications;

import android.content.Context;
import com.android.systemui.statusbar.NotificationListener;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.tv.notifications.TvNotificationHandler_Factory */
public final class TvNotificationHandler_Factory implements Factory<TvNotificationHandler> {
    public final Provider<Context> contextProvider;
    public final Provider<NotificationListener> notificationListenerProvider;

    public final Object get() {
        return new TvNotificationHandler(this.contextProvider.get(), this.notificationListenerProvider.get());
    }

    public TvNotificationHandler_Factory(Provider<Context> provider, Provider<NotificationListener> provider2) {
        this.contextProvider = provider;
        this.notificationListenerProvider = provider2;
    }
}
