package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarTouchableRegionManager_Factory implements Factory<StatusBarTouchableRegionManager> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<HeadsUpManagerPhone> headsUpManagerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;

    public final Object get() {
        return new StatusBarTouchableRegionManager(this.contextProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.configurationControllerProvider.get(), this.headsUpManagerProvider.get());
    }

    public StatusBarTouchableRegionManager_Factory(Provider<Context> provider, Provider<NotificationShadeWindowController> provider2, Provider<ConfigurationController> provider3, Provider<HeadsUpManagerPhone> provider4) {
        this.contextProvider = provider;
        this.notificationShadeWindowControllerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.headsUpManagerProvider = provider4;
    }
}
