package com.android.systemui.settings.brightness;

import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BrightnessDialog_Factory implements Factory<BrightnessDialog> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<BrightnessSliderController.Factory> factoryProvider;

    public final Object get() {
        return new BrightnessDialog(this.broadcastDispatcherProvider.get(), this.factoryProvider.get(), this.bgHandlerProvider.get());
    }

    public BrightnessDialog_Factory(Provider<BroadcastDispatcher> provider, Provider<BrightnessSliderController.Factory> provider2, Provider<Handler> provider3) {
        this.broadcastDispatcherProvider = provider;
        this.factoryProvider = provider2;
        this.bgHandlerProvider = provider3;
    }
}
