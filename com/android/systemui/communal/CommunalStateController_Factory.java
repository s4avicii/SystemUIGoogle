package com.android.systemui.communal;

import dagger.internal.Factory;

public final class CommunalStateController_Factory implements Factory<CommunalStateController> {

    public static final class InstanceHolder {
        public static final CommunalStateController_Factory INSTANCE = new CommunalStateController_Factory();
    }

    public final Object get() {
        return new CommunalStateController();
    }
}
