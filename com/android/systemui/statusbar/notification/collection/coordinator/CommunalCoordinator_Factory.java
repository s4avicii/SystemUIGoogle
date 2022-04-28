package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class CommunalCoordinator_Factory implements Factory<CommunalCoordinator> {
    public final Provider<CommunalStateController> communalStateControllerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    public final Provider<NotificationLockscreenUserManager> notificationLockscreenUserManagerProvider;

    public final Object get() {
        return new CommunalCoordinator(this.executorProvider.get(), this.notificationEntryManagerProvider.get(), this.notificationLockscreenUserManagerProvider.get(), this.communalStateControllerProvider.get());
    }

    public CommunalCoordinator_Factory(Provider<Executor> provider, Provider<NotificationEntryManager> provider2, Provider<NotificationLockscreenUserManager> provider3, Provider<CommunalStateController> provider4) {
        this.executorProvider = provider;
        this.notificationEntryManagerProvider = provider2;
        this.notificationLockscreenUserManagerProvider = provider3;
        this.communalStateControllerProvider = provider4;
    }
}
