package com.android.systemui.dagger;

import android.app.ActivityTaskManager;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideActivityTaskManagerFactory implements Factory<ActivityTaskManager> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideActivityTaskManagerFactory INSTANCE = new FrameworkServicesModule_ProvideActivityTaskManagerFactory();
    }

    public final Object get() {
        ActivityTaskManager instance = ActivityTaskManager.getInstance();
        Objects.requireNonNull(instance, "Cannot return null from a non-@Nullable @Provides method");
        return instance;
    }
}
