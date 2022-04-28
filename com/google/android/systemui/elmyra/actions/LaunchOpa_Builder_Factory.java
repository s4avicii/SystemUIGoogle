package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.actions.LaunchOpa;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LaunchOpa_Builder_Factory implements Factory<LaunchOpa.Builder> {
    public final Provider<Context> contextProvider;
    public final Provider<StatusBar> statusBarProvider;

    public final Object get() {
        return new LaunchOpa.Builder(this.contextProvider.get(), this.statusBarProvider.get());
    }

    public LaunchOpa_Builder_Factory(Provider<Context> provider, Provider<StatusBar> provider2) {
        this.contextProvider = provider;
        this.statusBarProvider = provider2;
    }
}
