package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ViewConfigCoordinator_Factory implements Factory<ViewConfigCoordinator> {
    public final Provider<ConfigurationController> mConfigurationControllerProvider;
    public final Provider<NotificationGutsManager> mGutsManagerProvider;
    public final Provider<KeyguardUpdateMonitor> mKeyguardUpdateMonitorProvider;
    public final Provider<NotificationLockscreenUserManagerImpl> mLockscreenUserManagerProvider;

    public final Object get() {
        return new ViewConfigCoordinator(this.mConfigurationControllerProvider.get(), this.mLockscreenUserManagerProvider.get(), this.mGutsManagerProvider.get(), this.mKeyguardUpdateMonitorProvider.get());
    }

    public ViewConfigCoordinator_Factory(Provider<ConfigurationController> provider, Provider<NotificationLockscreenUserManagerImpl> provider2, Provider<NotificationGutsManager> provider3, Provider<KeyguardUpdateMonitor> provider4) {
        this.mConfigurationControllerProvider = provider;
        this.mLockscreenUserManagerProvider = provider2;
        this.mGutsManagerProvider = provider3;
        this.mKeyguardUpdateMonitorProvider = provider4;
    }
}
