package com.android.systemui.dreams.complication;

import android.content.Context;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.DreamClockDateComplication;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamClockDateComplication_Registrant_Factory implements Factory<DreamClockDateComplication.Registrant> {
    public final Provider<Context> contextProvider;
    public final Provider<DreamClockDateComplication> dreamClockDateComplicationProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;

    public final Object get() {
        return new DreamClockDateComplication.Registrant(this.contextProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.dreamClockDateComplicationProvider.get());
    }

    public DreamClockDateComplication_Registrant_Factory(Provider<Context> provider, Provider<DreamOverlayStateController> provider2, Provider<DreamClockDateComplication> provider3) {
        this.contextProvider = provider;
        this.dreamOverlayStateControllerProvider = provider2;
        this.dreamClockDateComplicationProvider = provider3;
    }
}
