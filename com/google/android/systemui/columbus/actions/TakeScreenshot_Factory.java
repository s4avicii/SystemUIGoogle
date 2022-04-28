package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class TakeScreenshot_Factory implements Factory<TakeScreenshot> {
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new TakeScreenshot(this.contextProvider.get(), this.handlerProvider.get(), this.uiEventLoggerProvider.get());
    }

    public TakeScreenshot_Factory(Provider<Context> provider, Provider<Handler> provider2, Provider<UiEventLogger> provider3) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }
}
