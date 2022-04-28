package com.android.keyguard;

import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardMessageAreaController_Factory_Factory implements Factory<KeyguardMessageAreaController.Factory> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;

    public final Object get() {
        return new KeyguardMessageAreaController.Factory(this.keyguardUpdateMonitorProvider.get(), this.configurationControllerProvider.get());
    }

    public KeyguardMessageAreaController_Factory_Factory(Provider<KeyguardUpdateMonitor> provider, Provider<ConfigurationController> provider2) {
        this.keyguardUpdateMonitorProvider = provider;
        this.configurationControllerProvider = provider2;
    }
}
