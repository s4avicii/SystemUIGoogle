package com.android.systemui.media.dream;

import com.android.systemui.media.dream.dagger.MediaComplicationComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaDreamComplication_Factory implements Factory<MediaDreamComplication> {
    public final Provider<MediaComplicationComponent$Factory> componentFactoryProvider;

    public final Object get() {
        return new MediaDreamComplication(this.componentFactoryProvider.get());
    }

    public MediaDreamComplication_Factory(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.C244618 r1) {
        this.componentFactoryProvider = r1;
    }
}
