package com.android.systemui.dreams;

import android.content.Context;
import android.net.ConnectivityManager;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.policy.BatteryController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamOverlayStatusBarViewController_Factory implements Factory<DreamOverlayStatusBarViewController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
    public final Provider<ConnectivityManager> connectivityManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DreamOverlayStatusBarView> viewProvider;

    public final Object get() {
        return new DreamOverlayStatusBarViewController(this.contextProvider.get(), this.viewProvider.get(), this.batteryControllerProvider.get(), this.batteryMeterViewControllerProvider.get(), this.connectivityManagerProvider.get());
    }

    public DreamOverlayStatusBarViewController_Factory(Provider<Context> provider, Provider<DreamOverlayStatusBarView> provider2, Provider<BatteryController> provider3, Provider<BatteryMeterViewController> provider4, Provider<ConnectivityManager> provider5) {
        this.contextProvider = provider;
        this.viewProvider = provider2;
        this.batteryControllerProvider = provider3;
        this.batteryMeterViewControllerProvider = provider4;
        this.connectivityManagerProvider = provider5;
    }
}
