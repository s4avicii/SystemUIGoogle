package com.android.systemui.dreams;

import android.content.Context;
import com.android.systemui.dreams.SmartSpaceComplication;
import com.android.systemui.statusbar.VibratorHelper_Factory;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SmartSpaceComplication_Registrant_Factory implements Factory<SmartSpaceComplication.Registrant> {
    public final Provider<Context> contextProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<SmartSpaceComplication> smartSpaceComplicationProvider;
    public final Provider<LockscreenSmartspaceController> smartSpaceControllerProvider;

    public final Object get() {
        return new SmartSpaceComplication.Registrant(this.contextProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.smartSpaceComplicationProvider.get(), this.smartSpaceControllerProvider.get());
    }

    public SmartSpaceComplication_Registrant_Factory(Provider provider, Provider provider2, VibratorHelper_Factory vibratorHelper_Factory, Provider provider3) {
        this.contextProvider = provider;
        this.dreamOverlayStateControllerProvider = provider2;
        this.smartSpaceComplicationProvider = vibratorHelper_Factory;
        this.smartSpaceControllerProvider = provider3;
    }
}
