package com.android.p012wm.shell.dagger;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideMainHandlerFactory */
public final class WMShellConcurrencyModule_ProvideMainHandlerFactory implements Factory<Handler> {

    /* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideMainHandlerFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellConcurrencyModule_ProvideMainHandlerFactory INSTANCE = new WMShellConcurrencyModule_ProvideMainHandlerFactory();
    }

    public final Object get() {
        return new Handler(Looper.getMainLooper());
    }
}
