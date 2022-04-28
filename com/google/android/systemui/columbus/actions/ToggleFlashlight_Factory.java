package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.policy.FlashlightController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ToggleFlashlight_Factory implements Factory<ToggleFlashlight> {
    public final Provider<Context> contextProvider;
    public final Provider<FlashlightController> flashlightControllerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new ToggleFlashlight(this.contextProvider.get(), this.flashlightControllerProvider.get(), this.handlerProvider.get(), this.uiEventLoggerProvider.get());
    }

    public ToggleFlashlight_Factory(Provider<Context> provider, Provider<FlashlightController> provider2, Provider<Handler> provider3, Provider<UiEventLogger> provider4) {
        this.contextProvider = provider;
        this.flashlightControllerProvider = provider2;
        this.handlerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
    }
}
