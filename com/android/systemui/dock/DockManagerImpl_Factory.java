package com.android.systemui.dock;

import dagger.internal.Factory;

public final class DockManagerImpl_Factory implements Factory<DockManagerImpl> {

    public static final class InstanceHolder {
        public static final DockManagerImpl_Factory INSTANCE = new DockManagerImpl_Factory();
    }

    public final Object get() {
        return new DockManagerImpl();
    }
}
