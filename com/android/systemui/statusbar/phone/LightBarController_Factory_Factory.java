package com.android.systemui.statusbar.phone;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.BatteryController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LightBarController_Factory_Factory implements Factory<LightBarController.Factory> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<DarkIconDispatcher> darkIconDispatcherProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<NavigationModeController> navModeControllerProvider;

    public final Object get() {
        return new LightBarController.Factory(this.darkIconDispatcherProvider.get(), this.batteryControllerProvider.get(), this.navModeControllerProvider.get(), this.dumpManagerProvider.get());
    }

    public LightBarController_Factory_Factory(Provider<DarkIconDispatcher> provider, Provider<BatteryController> provider2, Provider<NavigationModeController> provider3, Provider<DumpManager> provider4) {
        this.darkIconDispatcherProvider = provider;
        this.batteryControllerProvider = provider2;
        this.navModeControllerProvider = provider3;
        this.dumpManagerProvider = provider4;
    }
}
