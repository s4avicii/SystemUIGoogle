package com.android.systemui.controls;

import dagger.internal.Factory;

public final class ControlsMetricsLoggerImpl_Factory implements Factory<ControlsMetricsLoggerImpl> {

    public static final class InstanceHolder {
        public static final ControlsMetricsLoggerImpl_Factory INSTANCE = new ControlsMetricsLoggerImpl_Factory();
    }

    public final Object get() {
        return new ControlsMetricsLoggerImpl();
    }
}
