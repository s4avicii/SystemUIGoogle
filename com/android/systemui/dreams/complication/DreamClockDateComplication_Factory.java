package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent$Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamClockDateComplication_Factory implements Factory<DreamClockDateComplication> {
    public final Provider<DreamClockDateComplicationComponent$Factory> componentFactoryProvider;

    public final Object get() {
        return new DreamClockDateComplication(this.componentFactoryProvider.get());
    }

    public DreamClockDateComplication_Factory(Provider<DreamClockDateComplicationComponent$Factory> provider) {
        this.componentFactoryProvider = provider;
    }
}
