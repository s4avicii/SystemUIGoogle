package com.google.android.systemui.columbus.actions;

import android.app.IActivityManager;
import android.content.Context;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SnoozeAlarm_Factory implements Factory<SnoozeAlarm> {
    public final Provider<IActivityManager> activityManagerServiceProvider;
    public final Provider<Context> contextProvider;
    public final Provider<SilenceAlertsDisabled> silenceAlertsDisabledProvider;

    public final Object get() {
        return new SnoozeAlarm(this.contextProvider.get(), this.silenceAlertsDisabledProvider.get(), this.activityManagerServiceProvider.get());
    }

    public SnoozeAlarm_Factory(Provider<Context> provider, Provider<SilenceAlertsDisabled> provider2, Provider<IActivityManager> provider3) {
        this.contextProvider = provider;
        this.silenceAlertsDisabledProvider = provider2;
        this.activityManagerServiceProvider = provider3;
    }
}
