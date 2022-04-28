package com.google.android.systemui.columbus;

import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ColumbusContentObserver_Factory_Factory implements Factory<ColumbusContentObserver.Factory> {
    public final Provider<ContentResolverWrapper> contentResolverProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public final Object get() {
        return new ColumbusContentObserver.Factory(this.contentResolverProvider.get(), this.userTrackerProvider.get(), this.handlerProvider.get(), this.executorProvider.get());
    }

    public ColumbusContentObserver_Factory_Factory(Provider<ContentResolverWrapper> provider, Provider<UserTracker> provider2, Provider<Handler> provider3, Provider<Executor> provider4) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
        this.handlerProvider = provider3;
        this.executorProvider = provider4;
    }
}
