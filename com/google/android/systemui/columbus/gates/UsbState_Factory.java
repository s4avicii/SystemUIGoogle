package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import com.google.android.systemui.columbus.ColumbusModule_ProvideTransientGateDurationFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class UsbState_Factory implements Factory<UsbState> {
    public final Provider<Context> contextProvider;
    public final Provider<Long> gateDurationProvider;
    public final Provider<Handler> handlerProvider;

    public UsbState_Factory(Provider provider, Provider provider2) {
        ColumbusModule_ProvideTransientGateDurationFactory columbusModule_ProvideTransientGateDurationFactory = ColumbusModule_ProvideTransientGateDurationFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.gateDurationProvider = columbusModule_ProvideTransientGateDurationFactory;
    }

    public final Object get() {
        return new UsbState(this.contextProvider.get(), this.handlerProvider.get(), this.gateDurationProvider.get().longValue());
    }
}
