package com.android.systemui.screenshot;

import android.content.Context;
import android.view.WindowManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ScreenshotNotificationsController_Factory implements Factory<ScreenshotNotificationsController> {
    public final Provider<Context> contextProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public final Object get() {
        return new ScreenshotNotificationsController(this.contextProvider.get(), this.windowManagerProvider.get());
    }

    public ScreenshotNotificationsController_Factory(Provider<Context> provider, Provider<WindowManager> provider2) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
    }
}
