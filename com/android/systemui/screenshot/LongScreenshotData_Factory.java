package com.android.systemui.screenshot;

import dagger.internal.Factory;

public final class LongScreenshotData_Factory implements Factory<LongScreenshotData> {

    public static final class InstanceHolder {
        public static final LongScreenshotData_Factory INSTANCE = new LongScreenshotData_Factory();
    }

    public final Object get() {
        return new LongScreenshotData();
    }
}
