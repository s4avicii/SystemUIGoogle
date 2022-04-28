package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;

public final class GroupCountCoordinator_Factory implements Factory<GroupCountCoordinator> {

    public static final class InstanceHolder {
        public static final GroupCountCoordinator_Factory INSTANCE = new GroupCountCoordinator_Factory();
    }

    public final Object get() {
        return new GroupCountCoordinator();
    }
}
