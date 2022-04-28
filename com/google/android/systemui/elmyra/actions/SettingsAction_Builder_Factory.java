package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.actions.SettingsAction;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SettingsAction_Builder_Factory implements Factory<SettingsAction.Builder> {
    public final Provider<Context> contextProvider;
    public final Provider<StatusBar> statusBarProvider;

    public final Object get() {
        return new SettingsAction.Builder(this.contextProvider.get(), this.statusBarProvider.get());
    }

    public SettingsAction_Builder_Factory(Provider<Context> provider, Provider<StatusBar> provider2) {
        this.contextProvider = provider;
        this.statusBarProvider = provider2;
    }
}
