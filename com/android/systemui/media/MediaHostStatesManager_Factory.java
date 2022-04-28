package com.android.systemui.media;

import dagger.internal.Factory;

public final class MediaHostStatesManager_Factory implements Factory<MediaHostStatesManager> {

    public static final class InstanceHolder {
        public static final MediaHostStatesManager_Factory INSTANCE = new MediaHostStatesManager_Factory();
    }

    public final Object get() {
        return new MediaHostStatesManager();
    }
}
