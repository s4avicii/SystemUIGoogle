package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class FlingVelocityWrapper_Factory implements Factory<FlingVelocityWrapper> {

    public static final class InstanceHolder {
        public static final FlingVelocityWrapper_Factory INSTANCE = new FlingVelocityWrapper_Factory();
    }

    public final Object get() {
        return new FlingVelocityWrapper();
    }
}
