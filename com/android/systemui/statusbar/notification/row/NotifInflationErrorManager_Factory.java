package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

public final class NotifInflationErrorManager_Factory implements Factory<NotifInflationErrorManager> {

    public static final class InstanceHolder {
        public static final NotifInflationErrorManager_Factory INSTANCE = new NotifInflationErrorManager_Factory();
    }

    public final Object get() {
        return new NotifInflationErrorManager();
    }
}
