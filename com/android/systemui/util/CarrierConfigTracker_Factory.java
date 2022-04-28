package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CarrierConfigTracker_Factory implements Factory<CarrierConfigTracker> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new CarrierConfigTracker(this.contextProvider.get(), this.broadcastDispatcherProvider.get());
    }

    public CarrierConfigTracker_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }
}
