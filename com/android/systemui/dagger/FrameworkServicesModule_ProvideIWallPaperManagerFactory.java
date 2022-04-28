package com.android.systemui.dagger;

import android.app.IWallpaperManager;
import android.os.ServiceManager;
import dagger.internal.Factory;

public final class FrameworkServicesModule_ProvideIWallPaperManagerFactory implements Factory<IWallpaperManager> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIWallPaperManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWallPaperManagerFactory();
    }

    public final Object get() {
        return IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
    }
}
