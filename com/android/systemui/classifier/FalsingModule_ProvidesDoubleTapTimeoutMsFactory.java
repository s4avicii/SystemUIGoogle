package com.android.systemui.classifier;

import dagger.internal.Factory;

public final class FalsingModule_ProvidesDoubleTapTimeoutMsFactory implements Factory<Long> {

    public static final class InstanceHolder {
        public static final FalsingModule_ProvidesDoubleTapTimeoutMsFactory INSTANCE = new FalsingModule_ProvidesDoubleTapTimeoutMsFactory();
    }

    public final Object get() {
        return 1200L;
    }
}
