package com.android.systemui.power;

import dagger.internal.Factory;

public final class EnhancedEstimatesImpl_Factory implements Factory<EnhancedEstimatesImpl> {

    public static final class InstanceHolder {
        public static final EnhancedEstimatesImpl_Factory INSTANCE = new EnhancedEstimatesImpl_Factory();
    }

    public final Object get() {
        return new EnhancedEstimatesImpl();
    }
}
