package com.android.systemui.screenshot;

import dagger.internal.Factory;

public final class ScreenshotSmartActions_Factory implements Factory<ScreenshotSmartActions> {

    public static final class InstanceHolder {
        public static final ScreenshotSmartActions_Factory INSTANCE = new ScreenshotSmartActions_Factory();
    }

    public final Object get() {
        return new ScreenshotSmartActions();
    }
}
