package com.android.p012wm.shell.dagger;

import android.content.pm.PackageManager;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipUiEventLoggerFactory */
public final class WMShellBaseModule_ProvidePipUiEventLoggerFactory implements Factory<PipUiEventLogger> {
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new PipUiEventLogger(this.uiEventLoggerProvider.get(), this.packageManagerProvider.get());
    }

    public WMShellBaseModule_ProvidePipUiEventLoggerFactory(Provider<UiEventLogger> provider, Provider<PackageManager> provider2) {
        this.uiEventLoggerProvider = provider;
        this.packageManagerProvider = provider2;
    }
}
