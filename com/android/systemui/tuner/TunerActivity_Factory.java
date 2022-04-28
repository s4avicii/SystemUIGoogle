package com.android.systemui.tuner;

import com.android.systemui.demomode.DemoModeController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class TunerActivity_Factory implements Factory<TunerActivity> {
    public final Provider<DemoModeController> demoModeControllerProvider;
    public final Provider<TunerService> tunerServiceProvider;

    public final Object get() {
        return new TunerActivity(this.demoModeControllerProvider.get(), this.tunerServiceProvider.get());
    }

    public TunerActivity_Factory(Provider<DemoModeController> provider, Provider<TunerService> provider2) {
        this.demoModeControllerProvider = provider;
        this.tunerServiceProvider = provider2;
    }
}
