package com.android.systemui.media;

import com.android.systemui.media.MediaHost;
import dagger.internal.Factory;

public final class MediaHost_MediaHostStateHolder_Factory implements Factory<MediaHost.MediaHostStateHolder> {

    public static final class InstanceHolder {
        public static final MediaHost_MediaHostStateHolder_Factory INSTANCE = new MediaHost_MediaHostStateHolder_Factory();
    }

    public final Object get() {
        return new MediaHost.MediaHostStateHolder();
    }
}
