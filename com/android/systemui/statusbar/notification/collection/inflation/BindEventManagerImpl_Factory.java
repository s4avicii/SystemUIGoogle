package com.android.systemui.statusbar.notification.collection.inflation;

import dagger.internal.Factory;

public final class BindEventManagerImpl_Factory implements Factory<BindEventManagerImpl> {

    public static final class InstanceHolder {
        public static final BindEventManagerImpl_Factory INSTANCE = new BindEventManagerImpl_Factory();
    }

    public final Object get() {
        return new BindEventManagerImpl();
    }
}
