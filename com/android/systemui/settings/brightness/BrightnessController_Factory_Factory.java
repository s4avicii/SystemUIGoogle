package com.android.systemui.settings.brightness;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.brightness.BrightnessController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BrightnessController_Factory_Factory implements Factory<BrightnessController.Factory> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new BrightnessController.Factory(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), this.bgHandlerProvider.get());
    }

    public BrightnessController_Factory_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Handler> provider3) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.bgHandlerProvider = provider3;
    }
}
