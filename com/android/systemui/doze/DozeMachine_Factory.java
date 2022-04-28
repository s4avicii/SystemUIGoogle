package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.dagger.DozeModule_ProvidesDozeMachinePartesFactory;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DozeMachine_Factory implements Factory<DozeMachine> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<AmbientDisplayConfiguration> configProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<DozeMachine.Part[]> partsProvider;
    public final Provider<DozeMachine.Service> serviceProvider;
    public final Provider<WakeLock> wakeLockProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public static DozeMachine_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, DozeModule_ProvidesDozeMachinePartesFactory dozeModule_ProvidesDozeMachinePartesFactory) {
        return new DozeMachine_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, dozeModule_ProvidesDozeMachinePartesFactory);
    }

    public final Object get() {
        return new DozeMachine(this.serviceProvider.get(), this.configProvider.get(), this.wakeLockProvider.get(), this.wakefulnessLifecycleProvider.get(), this.batteryControllerProvider.get(), this.dozeLogProvider.get(), this.dockManagerProvider.get(), this.dozeHostProvider.get(), this.partsProvider.get());
    }

    public DozeMachine_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, DozeModule_ProvidesDozeMachinePartesFactory dozeModule_ProvidesDozeMachinePartesFactory) {
        this.serviceProvider = provider;
        this.configProvider = provider2;
        this.wakeLockProvider = provider3;
        this.wakefulnessLifecycleProvider = provider4;
        this.batteryControllerProvider = provider5;
        this.dozeLogProvider = provider6;
        this.dockManagerProvider = provider7;
        this.dozeHostProvider = provider8;
        this.partsProvider = dozeModule_ProvidesDozeMachinePartesFactory;
    }
}
