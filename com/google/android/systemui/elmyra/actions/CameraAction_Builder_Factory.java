package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.actions.CameraAction;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CameraAction_Builder_Factory implements Factory<CameraAction.Builder> {
    public final Provider<Context> contextProvider;
    public final Provider<StatusBar> statusBarProvider;

    public final Object get() {
        return new CameraAction.Builder(this.contextProvider.get(), this.statusBarProvider.get());
    }

    public CameraAction_Builder_Factory(Provider<Context> provider, Provider<StatusBar> provider2) {
        this.contextProvider = provider;
        this.statusBarProvider = provider2;
    }
}
