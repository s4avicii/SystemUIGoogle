package com.android.systemui.media;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class MediaResumeListener_Factory implements Factory<MediaResumeListener> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<ResumeMediaBrowserFactory> mediaBrowserFactoryProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<TunerService> tunerServiceProvider;

    public static MediaResumeListener_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, ResumeMediaBrowserFactory_Factory resumeMediaBrowserFactory_Factory, Provider provider5, Provider provider6) {
        return new MediaResumeListener_Factory(provider, provider2, provider3, provider4, resumeMediaBrowserFactory_Factory, provider5, provider6);
    }

    public final Object get() {
        return new MediaResumeListener(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), this.backgroundExecutorProvider.get(), this.tunerServiceProvider.get(), this.mediaBrowserFactoryProvider.get(), this.dumpManagerProvider.get(), this.systemClockProvider.get());
    }

    public MediaResumeListener_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, ResumeMediaBrowserFactory_Factory resumeMediaBrowserFactory_Factory, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.backgroundExecutorProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.mediaBrowserFactoryProvider = resumeMediaBrowserFactory_Factory;
        this.dumpManagerProvider = provider5;
        this.systemClockProvider = provider6;
    }
}
