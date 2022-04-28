package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.content.Context;
import com.android.systemui.dagger.FrameworkServicesModule_ProvideIWallPaperManagerFactory;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class WakefulnessLifecycle_Factory implements Factory<WakefulnessLifecycle> {
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<IWallpaperManager> wallpaperManagerServiceProvider;

    public WakefulnessLifecycle_Factory(Provider provider, Provider provider2) {
        FrameworkServicesModule_ProvideIWallPaperManagerFactory frameworkServicesModule_ProvideIWallPaperManagerFactory = FrameworkServicesModule_ProvideIWallPaperManagerFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.wallpaperManagerServiceProvider = frameworkServicesModule_ProvideIWallPaperManagerFactory;
        this.dumpManagerProvider = provider2;
    }

    public final Object get() {
        return new WakefulnessLifecycle(this.contextProvider.get(), this.wallpaperManagerServiceProvider.get(), this.dumpManagerProvider.get());
    }
}
