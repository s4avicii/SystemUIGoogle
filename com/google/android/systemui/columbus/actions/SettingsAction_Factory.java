package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SettingsAction_Factory implements Factory<SettingsAction> {
    public final Provider<Context> contextProvider;
    public final Provider<StatusBar> statusBarProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new SettingsAction(this.contextProvider.get(), this.statusBarProvider.get(), this.uiEventLoggerProvider.get());
    }

    public SettingsAction_Factory(Provider<Context> provider, Provider<StatusBar> provider2, Provider<UiEventLogger> provider3) {
        this.contextProvider = provider;
        this.statusBarProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }
}
