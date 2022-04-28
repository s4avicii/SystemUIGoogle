package com.android.systemui.assist;

import android.content.Context;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class PhoneStateMonitor_Factory implements Factory<PhoneStateMonitor> {
    public final Provider<BootCompleteCache> bootCompleteCacheProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Optional<StatusBar>> statusBarOptionalLazyProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public final Object get() {
        return new PhoneStateMonitor(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.statusBarOptionalLazyProvider), this.bootCompleteCacheProvider.get(), this.statusBarStateControllerProvider.get());
    }

    public PhoneStateMonitor_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Optional<StatusBar>> provider3, Provider<BootCompleteCache> provider4, Provider<StatusBarStateController> provider5) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.statusBarOptionalLazyProvider = provider3;
        this.bootCompleteCacheProvider = provider4;
        this.statusBarStateControllerProvider = provider5;
    }
}
