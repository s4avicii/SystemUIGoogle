package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent$Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamWeatherComplication_Factory implements Factory<DreamWeatherComplication> {
    public final Provider<DreamWeatherComplicationComponent$Factory> componentFactoryProvider;

    public final Object get() {
        return new DreamWeatherComplication(this.componentFactoryProvider.get());
    }

    public DreamWeatherComplication_Factory(Provider<DreamWeatherComplicationComponent$Factory> provider) {
        this.componentFactoryProvider = provider;
    }
}
