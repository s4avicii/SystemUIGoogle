package com.android.systemui.util.time;

import dagger.internal.Factory;

public final class SystemClockImpl_Factory implements Factory<SystemClockImpl> {

    public static final class InstanceHolder {
        public static final SystemClockImpl_Factory INSTANCE = new SystemClockImpl_Factory();
    }

    public final Object get() {
        return new SystemClockImpl();
    }
}
