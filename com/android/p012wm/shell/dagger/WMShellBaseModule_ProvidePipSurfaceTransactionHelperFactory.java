package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory */
public final class WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory implements Factory<PipSurfaceTransactionHelper> {

    /* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory INSTANCE = new WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory();
    }

    public final Object get() {
        return new PipSurfaceTransactionHelper();
    }
}
