package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class GoBackHandler_Factory implements Factory<GoBackHandler> {

    public static final class InstanceHolder {
        public static final GoBackHandler_Factory INSTANCE = new GoBackHandler_Factory();
    }

    public final Object get() {
        return new GoBackHandler();
    }
}
