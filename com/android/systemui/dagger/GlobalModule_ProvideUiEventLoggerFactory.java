package com.android.systemui.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import dagger.internal.Factory;

public final class GlobalModule_ProvideUiEventLoggerFactory implements Factory<UiEventLogger> {

    public static final class InstanceHolder {
        public static final GlobalModule_ProvideUiEventLoggerFactory INSTANCE = new GlobalModule_ProvideUiEventLoggerFactory();
    }

    public final Object get() {
        return new UiEventLoggerImpl();
    }
}
