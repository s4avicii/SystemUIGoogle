package com.google.android.systemui.columbus;

import dagger.internal.Factory;

public final class ColumbusModule_ProvideTransientGateDurationFactory implements Factory<Long> {

    public static final class InstanceHolder {
        public static final ColumbusModule_ProvideTransientGateDurationFactory INSTANCE = new ColumbusModule_ProvideTransientGateDurationFactory();
    }

    public final Object get() {
        return 500L;
    }
}
