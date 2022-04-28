package com.android.systemui;

import dagger.internal.Factory;

public final class ImageWallpaper_Factory implements Factory<ImageWallpaper> {

    public static final class InstanceHolder {
        public static final ImageWallpaper_Factory INSTANCE = new ImageWallpaper_Factory();
    }

    public final Object get() {
        return new ImageWallpaper();
    }
}
