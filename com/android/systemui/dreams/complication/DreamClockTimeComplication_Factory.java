package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent$Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamClockTimeComplication_Factory implements Factory<DreamClockTimeComplication> {
    public final Provider<DreamClockTimeComplicationComponent$Factory> componentFactoryProvider;

    public final Object get() {
        return new DreamClockTimeComplication(this.componentFactoryProvider.get());
    }

    public DreamClockTimeComplication_Factory(Provider<DreamClockTimeComplicationComponent$Factory> provider) {
        this.componentFactoryProvider = provider;
    }
}
