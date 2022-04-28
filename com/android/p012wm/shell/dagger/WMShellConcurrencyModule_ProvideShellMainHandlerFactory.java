package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.dagger.WMShellConcurrencyModule_ProvideMainHandlerFactory;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainHandlerFactory */
public final class WMShellConcurrencyModule_ProvideShellMainHandlerFactory implements Factory<Handler> {
    public final Provider<Context> contextProvider;
    public final Provider<HandlerThread> mainThreadProvider;
    public final Provider<Handler> sysuiMainHandlerProvider;

    public WMShellConcurrencyModule_ProvideShellMainHandlerFactory(Provider provider, InstanceFactory instanceFactory) {
        WMShellConcurrencyModule_ProvideMainHandlerFactory wMShellConcurrencyModule_ProvideMainHandlerFactory = WMShellConcurrencyModule_ProvideMainHandlerFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.mainThreadProvider = instanceFactory;
        this.sysuiMainHandlerProvider = wMShellConcurrencyModule_ProvideMainHandlerFactory;
    }

    public final Object get() {
        HandlerThread handlerThread = this.mainThreadProvider.get();
        Handler handler = this.sysuiMainHandlerProvider.get();
        if (this.contextProvider.get().getResources().getBoolean(C1777R.bool.config_enableShellMainThread)) {
            if (handlerThread == null) {
                handlerThread = new HandlerThread("wmshell.main", -4);
                handlerThread.start();
            }
            if (Build.IS_DEBUGGABLE) {
                handlerThread.getLooper().setTraceTag(32);
                handlerThread.getLooper().setSlowLogThresholdMs(30, 30);
            }
            handler = Handler.createAsync(handlerThread.getLooper());
        }
        Objects.requireNonNull(handler, "Cannot return null from a non-@Nullable @Provides method");
        return handler;
    }
}
