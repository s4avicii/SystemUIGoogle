package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class TaskStackNotifier_Factory implements Factory<TaskStackNotifier> {

    public static final class InstanceHolder {
        public static final TaskStackNotifier_Factory INSTANCE = new TaskStackNotifier_Factory();
    }

    public final Object get() {
        return new TaskStackNotifier();
    }
}
