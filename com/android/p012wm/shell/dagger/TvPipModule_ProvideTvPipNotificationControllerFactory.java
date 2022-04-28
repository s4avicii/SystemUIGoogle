package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.p013tv.TvPipNotificationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvideTvPipNotificationControllerFactory */
public final class TvPipModule_ProvideTvPipNotificationControllerFactory implements Factory<TvPipNotificationController> {
    public final Provider<Context> contextProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<PipMediaController> pipMediaControllerProvider;

    public final Object get() {
        return new TvPipNotificationController(this.contextProvider.get(), this.pipMediaControllerProvider.get(), this.mainHandlerProvider.get());
    }

    public TvPipModule_ProvideTvPipNotificationControllerFactory(Provider<Context> provider, Provider<PipMediaController> provider2, Provider<Handler> provider3) {
        this.contextProvider = provider;
        this.pipMediaControllerProvider = provider2;
        this.mainHandlerProvider = provider3;
    }
}
