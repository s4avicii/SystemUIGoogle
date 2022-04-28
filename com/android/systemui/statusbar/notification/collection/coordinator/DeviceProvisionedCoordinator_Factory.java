package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DeviceProvisionedCoordinator_Factory implements Factory<DeviceProvisionedCoordinator> {
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<IPackageManager> packageManagerProvider;

    public final Object get() {
        return new DeviceProvisionedCoordinator(this.deviceProvisionedControllerProvider.get(), this.packageManagerProvider.get());
    }

    public DeviceProvisionedCoordinator_Factory(Provider<DeviceProvisionedController> provider, Provider<IPackageManager> provider2) {
        this.deviceProvisionedControllerProvider = provider;
        this.packageManagerProvider = provider2;
    }
}
