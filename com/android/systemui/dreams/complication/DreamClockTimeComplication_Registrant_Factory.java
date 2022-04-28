package com.android.systemui.dreams.complication;

import android.content.Context;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.DreamClockTimeComplication;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamClockTimeComplication_Registrant_Factory implements Factory<DreamClockTimeComplication.Registrant> {
    public final Provider<Context> contextProvider;
    public final Provider<DreamClockTimeComplication> dreamClockTimeComplicationProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;

    public final Object get() {
        return new DreamClockTimeComplication.Registrant(this.contextProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.dreamClockTimeComplicationProvider.get());
    }

    public DreamClockTimeComplication_Registrant_Factory(Provider<Context> provider, Provider<DreamOverlayStateController> provider2, Provider<DreamClockTimeComplication> provider3) {
        this.contextProvider = provider;
        this.dreamOverlayStateControllerProvider = provider2;
        this.dreamClockTimeComplicationProvider = provider3;
    }
}
