package com.android.systemui.telephony;

import dagger.internal.Factory;

public final class TelephonyCallback_Factory implements Factory<TelephonyCallback> {

    public static final class InstanceHolder {
        public static final TelephonyCallback_Factory INSTANCE = new TelephonyCallback_Factory();
    }

    public final Object get() {
        return new TelephonyCallback();
    }
}
