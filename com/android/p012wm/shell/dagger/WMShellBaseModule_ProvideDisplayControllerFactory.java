package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.view.IWindowManager;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ShellExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayControllerFactory */
public final class WMShellBaseModule_ProvideDisplayControllerFactory implements Factory<DisplayController> {
    public final Provider<Context> contextProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<IWindowManager> wmServiceProvider;

    public final Object get() {
        return new DisplayController(this.contextProvider.get(), this.wmServiceProvider.get(), this.mainExecutorProvider.get());
    }

    public WMShellBaseModule_ProvideDisplayControllerFactory(Provider<Context> provider, Provider<IWindowManager> provider2, Provider<ShellExecutor> provider3) {
        this.contextProvider = provider;
        this.wmServiceProvider = provider2;
        this.mainExecutorProvider = provider3;
    }
}
