package com.android.systemui.dagger;

import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideIWindowManagerFactory implements Factory<IWindowManager> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIWindowManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWindowManagerFactory();
    }

    public final Object get() {
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        Objects.requireNonNull(windowManagerService, "Cannot return null from a non-@Nullable @Provides method");
        return windowManagerService;
    }
}
