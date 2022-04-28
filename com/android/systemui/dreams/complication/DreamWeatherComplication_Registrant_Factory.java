package com.android.systemui.dreams.complication;

import android.content.Context;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.DreamWeatherComplication;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DreamWeatherComplication_Registrant_Factory implements Factory<DreamWeatherComplication.Registrant> {
    public final Provider<Context> contextProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<DreamWeatherComplication> dreamWeatherComplicationProvider;
    public final Provider<LockscreenSmartspaceController> smartspaceControllerProvider;

    public final Object get() {
        return new DreamWeatherComplication.Registrant(this.contextProvider.get(), this.smartspaceControllerProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.dreamWeatherComplicationProvider.get());
    }

    public DreamWeatherComplication_Registrant_Factory(Provider<Context> provider, Provider<LockscreenSmartspaceController> provider2, Provider<DreamOverlayStateController> provider3, Provider<DreamWeatherComplication> provider4) {
        this.contextProvider = provider;
        this.smartspaceControllerProvider = provider2;
        this.dreamOverlayStateControllerProvider = provider3;
        this.dreamWeatherComplicationProvider = provider4;
    }
}
