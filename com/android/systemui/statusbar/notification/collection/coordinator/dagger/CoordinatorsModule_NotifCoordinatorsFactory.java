package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class CoordinatorsModule_NotifCoordinatorsFactory implements Factory<NotifCoordinators> {
    public final Provider<CoordinatorsSubcomponent.Factory> factoryProvider;

    public final Object get() {
        NotifCoordinators notifCoordinators = this.factoryProvider.get().create().getNotifCoordinators();
        Objects.requireNonNull(notifCoordinators, "Cannot return null from a non-@Nullable @Provides method");
        return notifCoordinators;
    }

    public CoordinatorsModule_NotifCoordinatorsFactory(Provider<CoordinatorsSubcomponent.Factory> provider) {
        this.factoryProvider = provider;
    }
}
