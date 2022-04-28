package com.google.android.systemui.power;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerUI;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class PowerModuleGoogle_ProvideWarningsUiFactory implements Factory<PowerUI.WarningsUI> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new PowerNotificationWarningsGoogleImpl(this.contextProvider.get(), this.activityStarterProvider.get(), this.broadcastDispatcherProvider.get(), this.uiEventLoggerProvider.get());
    }

    public PowerModuleGoogle_ProvideWarningsUiFactory(Provider<Context> provider, Provider<ActivityStarter> provider2, Provider<BroadcastDispatcher> provider3, Provider<UiEventLogger> provider4) {
        this.contextProvider = provider;
        this.activityStarterProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
        this.uiEventLoggerProvider = provider4;
    }
}
