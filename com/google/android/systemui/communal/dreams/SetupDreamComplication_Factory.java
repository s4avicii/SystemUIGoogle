package com.google.android.systemui.communal.dreams;

import com.google.android.systemui.communal.dreams.dagger.SetupDreamComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SetupDreamComplication_Factory implements Factory<SetupDreamComplication> {
    public final Provider<SetupDreamComponent$Factory> componentFactoryProvider;

    public final Object get() {
        return new SetupDreamComplication(this.componentFactoryProvider.get());
    }

    public SetupDreamComplication_Factory(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.C244113 r1) {
        this.componentFactoryProvider = r1;
    }
}
