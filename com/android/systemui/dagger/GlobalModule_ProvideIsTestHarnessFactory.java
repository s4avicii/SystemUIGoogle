package com.android.systemui.dagger;

import android.app.ActivityManager;
import dagger.internal.Factory;

public final class GlobalModule_ProvideIsTestHarnessFactory implements Factory<Boolean> {

    public static final class InstanceHolder {
        public static final GlobalModule_ProvideIsTestHarnessFactory INSTANCE = new GlobalModule_ProvideIsTestHarnessFactory();
    }

    public final Object get() {
        return Boolean.valueOf(ActivityManager.isRunningInUserTestHarness());
    }
}
