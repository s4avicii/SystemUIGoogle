package com.android.systemui.dagger;

import android.app.ActivityManager;
import android.app.IActivityManager;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideIActivityManagerFactory implements Factory<IActivityManager> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIActivityManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIActivityManagerFactory();
    }

    public final Object get() {
        IActivityManager service = ActivityManager.getService();
        Objects.requireNonNull(service, "Cannot return null from a non-@Nullable @Provides method");
        return service;
    }
}
