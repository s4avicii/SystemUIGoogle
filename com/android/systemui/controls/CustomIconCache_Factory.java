package com.android.systemui.controls;

import dagger.internal.Factory;

public final class CustomIconCache_Factory implements Factory<CustomIconCache> {

    public static final class InstanceHolder {
        public static final CustomIconCache_Factory INSTANCE = new CustomIconCache_Factory();
    }

    public final Object get() {
        return new CustomIconCache();
    }
}
