package com.android.p012wm.shell.dagger;

import android.os.HandlerThread;
import com.android.p012wm.shell.common.HandlerExecutor;
import com.android.p012wm.shell.common.ShellExecutor;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory */
public final class WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory implements Factory<ShellExecutor> {

    /* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory INSTANCE = new WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory();
    }

    public final Object get() {
        HandlerThread handlerThread = new HandlerThread("wmshell.splashscreen", -10);
        handlerThread.start();
        return new HandlerExecutor(handlerThread.getThreadHandler());
    }
}
