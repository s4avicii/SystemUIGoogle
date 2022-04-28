package com.android.systemui.statusbar.phone.dagger;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarViewModule_GetBatteryMeterViewControllerFactory implements Factory<BatteryMeterViewController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<BatteryMeterView> batteryMeterViewProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<TunerService> tunerServiceProvider;

    public static StatusBarViewModule_GetBatteryMeterViewControllerFactory create(Provider<BatteryMeterView> provider, Provider<ConfigurationController> provider2, Provider<TunerService> provider3, Provider<BroadcastDispatcher> provider4, Provider<Handler> provider5, Provider<ContentResolver> provider6, Provider<BatteryController> provider7) {
        return new StatusBarViewModule_GetBatteryMeterViewControllerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public final Object get() {
        return new BatteryMeterViewController(this.batteryMeterViewProvider.get(), this.configurationControllerProvider.get(), this.tunerServiceProvider.get(), this.broadcastDispatcherProvider.get(), this.mainHandlerProvider.get(), this.contentResolverProvider.get(), this.batteryControllerProvider.get());
    }

    public StatusBarViewModule_GetBatteryMeterViewControllerFactory(Provider<BatteryMeterView> provider, Provider<ConfigurationController> provider2, Provider<TunerService> provider3, Provider<BroadcastDispatcher> provider4, Provider<Handler> provider5, Provider<ContentResolver> provider6, Provider<BatteryController> provider7) {
        this.batteryMeterViewProvider = provider;
        this.configurationControllerProvider = provider2;
        this.tunerServiceProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.contentResolverProvider = provider6;
        this.batteryControllerProvider = provider7;
    }
}
