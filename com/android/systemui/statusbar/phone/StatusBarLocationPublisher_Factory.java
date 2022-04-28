package com.android.systemui.statusbar.phone;

import dagger.internal.Factory;

public final class StatusBarLocationPublisher_Factory implements Factory<StatusBarLocationPublisher> {

    public static final class InstanceHolder {
        public static final StatusBarLocationPublisher_Factory INSTANCE = new StatusBarLocationPublisher_Factory();
    }

    public final Object get() {
        return new StatusBarLocationPublisher();
    }
}
