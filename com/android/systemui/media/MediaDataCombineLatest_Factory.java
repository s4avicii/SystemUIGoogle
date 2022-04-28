package com.android.systemui.media;

import dagger.internal.Factory;

public final class MediaDataCombineLatest_Factory implements Factory<MediaDataCombineLatest> {

    public static final class InstanceHolder {
        public static final MediaDataCombineLatest_Factory INSTANCE = new MediaDataCombineLatest_Factory();
    }

    public final Object get() {
        return new MediaDataCombineLatest();
    }
}
