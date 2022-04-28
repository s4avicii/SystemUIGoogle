package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

public final class RowInflaterTask_Factory implements Factory<RowInflaterTask> {

    public static final class InstanceHolder {
        public static final RowInflaterTask_Factory INSTANCE = new RowInflaterTask_Factory();
    }

    public final Object get() {
        return new RowInflaterTask();
    }
}
