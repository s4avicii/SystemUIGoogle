package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.HandlerExecutor;
import com.android.p012wm.shell.common.ShellExecutor;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainExecutorFactory */
public final class WMShellConcurrencyModule_ProvideShellMainExecutorFactory implements Factory<ShellExecutor> {
    public final Provider<Context> contextProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<ShellExecutor> sysuiMainExecutorProvider;

    public final Object get() {
        Handler handler = this.mainHandlerProvider.get();
        Object obj = this.sysuiMainExecutorProvider.get();
        if (this.contextProvider.get().getResources().getBoolean(C1777R.bool.config_enableShellMainThread)) {
            obj = new HandlerExecutor(handler);
        }
        Objects.requireNonNull(obj, "Cannot return null from a non-@Nullable @Provides method");
        return obj;
    }

    public WMShellConcurrencyModule_ProvideShellMainExecutorFactory(Provider<Context> provider, Provider<Handler> provider2, Provider<ShellExecutor> provider3) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.sysuiMainExecutorProvider = provider3;
    }
}
