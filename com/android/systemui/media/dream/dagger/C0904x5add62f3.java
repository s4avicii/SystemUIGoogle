package com.android.systemui.media.dream.dagger;

import android.content.Context;
import android.widget.FrameLayout;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.media.dream.dagger.MediaComplicationComponent_MediaComplicationModule_ProvideComplicationContainerFactory */
public final class C0904x5add62f3 implements Factory<FrameLayout> {
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new FrameLayout(this.contextProvider.get());
    }

    public C0904x5add62f3(Provider<Context> provider) {
        this.contextProvider = provider;
    }
}
