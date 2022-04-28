package com.android.systemui.statusbar;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class NotificationListener_Factory implements Factory<NotificationListener> {
    public final Provider<Context> contextProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<NotificationManager> notificationManagerProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public final Object get() {
        return new NotificationListener(this.contextProvider.get(), this.notificationManagerProvider.get(), this.systemClockProvider.get(), this.mainExecutorProvider.get(), this.pluginManagerProvider.get());
    }

    public NotificationListener_Factory(Provider<Context> provider, Provider<NotificationManager> provider2, Provider<SystemClock> provider3, Provider<Executor> provider4, Provider<PluginManager> provider5) {
        this.contextProvider = provider;
        this.notificationManagerProvider = provider2;
        this.systemClockProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.pluginManagerProvider = provider5;
    }
}
