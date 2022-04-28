package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.common.FloatingContentCoordinator;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideFloatingContentCoordinatorFactory */
public final class WMShellBaseModule_ProvideFloatingContentCoordinatorFactory implements Factory<FloatingContentCoordinator> {

    /* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideFloatingContentCoordinatorFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellBaseModule_ProvideFloatingContentCoordinatorFactory INSTANCE = new WMShellBaseModule_ProvideFloatingContentCoordinatorFactory();
    }

    public final Object get() {
        return new FloatingContentCoordinator();
    }
}
