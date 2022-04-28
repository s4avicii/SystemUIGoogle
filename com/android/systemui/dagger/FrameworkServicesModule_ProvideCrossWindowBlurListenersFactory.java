package com.android.systemui.dagger;

import android.view.CrossWindowBlurListeners;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory implements Factory<CrossWindowBlurListeners> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory INSTANCE = new FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory();
    }

    public final Object get() {
        CrossWindowBlurListeners instance = CrossWindowBlurListeners.getInstance();
        Objects.requireNonNull(instance, "Cannot return null from a non-@Nullable @Provides method");
        return instance;
    }
}
