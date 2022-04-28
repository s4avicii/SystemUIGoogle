package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.dagger.NightDisplayListenerModule;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NightDisplayListenerModule_Builder_Factory implements Factory<NightDisplayListenerModule.Builder> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new NightDisplayListenerModule.Builder(this.contextProvider.get(), this.bgHandlerProvider.get());
    }

    public NightDisplayListenerModule_Builder_Factory(Provider<Context> provider, Provider<Handler> provider2) {
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
    }
}
