package com.android.systemui.statusbar;

import dagger.internal.Factory;

public final class DisableFlagsLogger_Factory implements Factory<DisableFlagsLogger> {

    public static final class InstanceHolder {
        public static final DisableFlagsLogger_Factory INSTANCE = new DisableFlagsLogger_Factory();
    }

    public final Object get() {
        return new DisableFlagsLogger();
    }
}
