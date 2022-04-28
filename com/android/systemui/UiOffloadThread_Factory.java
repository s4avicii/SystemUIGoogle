package com.android.systemui;

import dagger.internal.Factory;

public final class UiOffloadThread_Factory implements Factory<UiOffloadThread> {

    public static final class InstanceHolder {
        public static final UiOffloadThread_Factory INSTANCE = new UiOffloadThread_Factory();
    }

    public final Object get() {
        return new UiOffloadThread();
    }
}
