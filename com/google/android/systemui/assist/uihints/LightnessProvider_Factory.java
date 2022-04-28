package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class LightnessProvider_Factory implements Factory<LightnessProvider> {

    public static final class InstanceHolder {
        public static final LightnessProvider_Factory INSTANCE = new LightnessProvider_Factory();
    }

    public final Object get() {
        return new LightnessProvider();
    }
}
