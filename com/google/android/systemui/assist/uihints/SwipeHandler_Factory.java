package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class SwipeHandler_Factory implements Factory<SwipeHandler> {

    public static final class InstanceHolder {
        public static final SwipeHandler_Factory INSTANCE = new SwipeHandler_Factory();
    }

    public final Object get() {
        return new SwipeHandler();
    }
}
