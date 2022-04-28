package com.android.systemui.statusbar.events;

import android.content.Context;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SystemEventCoordinator_Factory implements Factory<SystemEventCoordinator> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<PrivacyItemController> privacyControllerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public final Object get() {
        return new SystemEventCoordinator(this.systemClockProvider.get(), this.batteryControllerProvider.get(), this.privacyControllerProvider.get(), this.contextProvider.get());
    }

    public SystemEventCoordinator_Factory(Provider<SystemClock> provider, Provider<BatteryController> provider2, Provider<PrivacyItemController> provider3, Provider<Context> provider4) {
        this.systemClockProvider = provider;
        this.batteryControllerProvider = provider2;
        this.privacyControllerProvider = provider3;
        this.contextProvider = provider4;
    }
}
