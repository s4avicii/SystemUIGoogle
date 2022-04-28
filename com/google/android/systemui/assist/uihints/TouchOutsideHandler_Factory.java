package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class TouchOutsideHandler_Factory implements Factory<TouchOutsideHandler> {

    public static final class InstanceHolder {
        public static final TouchOutsideHandler_Factory INSTANCE = new TouchOutsideHandler_Factory();
    }

    public final Object get() {
        return new TouchOutsideHandler();
    }
}
