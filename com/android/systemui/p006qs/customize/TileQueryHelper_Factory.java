package com.android.systemui.p006qs.customize;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.customize.TileQueryHelper_Factory */
public final class TileQueryHelper_Factory implements Factory<TileQueryHelper> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public final Object get() {
        return new TileQueryHelper(this.contextProvider.get(), this.userTrackerProvider.get(), this.mainExecutorProvider.get(), this.bgExecutorProvider.get());
    }

    public TileQueryHelper_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<Executor> provider3, Provider<Executor> provider4) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.bgExecutorProvider = provider4;
    }
}
