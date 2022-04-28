package com.google.android.systemui.fingerprint;

import dagger.internal.Factory;

public final class UdfpsLhbmProvider_Factory implements Factory<UdfpsLhbmProvider> {

    public static final class InstanceHolder {
        public static final UdfpsLhbmProvider_Factory INSTANCE = new UdfpsLhbmProvider_Factory();
    }

    public final Object get() {
        return new UdfpsLhbmProvider();
    }
}
