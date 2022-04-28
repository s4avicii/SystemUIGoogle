package com.android.p012wm.shell.dagger;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.android.p012wm.shell.common.HandlerExecutor;
import com.android.p012wm.shell.common.ShellExecutor;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory */
public final class WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory implements Factory<ShellExecutor> {

    /* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory INSTANCE = new WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory();
    }

    public final Object get() {
        HandlerThread handlerThread = new HandlerThread("wmshell.anim", -4);
        handlerThread.start();
        if (Build.IS_DEBUGGABLE) {
            handlerThread.getLooper().setTraceTag(32);
            handlerThread.getLooper().setSlowLogThresholdMs(30, 30);
        }
        return new HandlerExecutor(Handler.createAsync(handlerThread.getLooper()));
    }
}
