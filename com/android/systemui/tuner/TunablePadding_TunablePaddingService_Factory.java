package com.android.systemui.tuner;

import com.android.systemui.tuner.TunablePadding;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class TunablePadding_TunablePaddingService_Factory implements Factory<TunablePadding.TunablePaddingService> {
    public final Provider<TunerService> tunerServiceProvider;

    public final Object get() {
        TunerService tunerService = this.tunerServiceProvider.get();
        return new TunablePadding.TunablePaddingService();
    }

    public TunablePadding_TunablePaddingService_Factory(Provider<TunerService> provider) {
        this.tunerServiceProvider = provider;
    }
}
