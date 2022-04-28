package com.google.android.systemui.fingerprint;

import dagger.internal.Factory;

public final class UdfpsGhbmProvider_Factory implements Factory<UdfpsGhbmProvider> {

    public static final class InstanceHolder {
        public static final UdfpsGhbmProvider_Factory INSTANCE = new UdfpsGhbmProvider_Factory();
    }

    public final Object get() {
        return new UdfpsGhbmProvider();
    }
}
