package com.android.systemui.dump;

import dagger.internal.Factory;

public final class DumpManager_Factory implements Factory<DumpManager> {

    public static final class InstanceHolder {
        public static final DumpManager_Factory INSTANCE = new DumpManager_Factory();
    }

    public final Object get() {
        return new DumpManager();
    }
}
