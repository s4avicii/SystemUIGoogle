package com.android.systemui.util;

import dagger.internal.Factory;

public final class DeviceConfigProxy_Factory implements Factory<DeviceConfigProxy> {

    public static final class InstanceHolder {
        public static final DeviceConfigProxy_Factory INSTANCE = new DeviceConfigProxy_Factory();
    }

    public final Object get() {
        return new DeviceConfigProxy();
    }
}
