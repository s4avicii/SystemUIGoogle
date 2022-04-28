package com.android.systemui.p006qs.external;

import android.os.Looper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainLooperFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.external.TileServices_Factory */
public final class TileServices_Factory implements Factory<TileServices> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<QSTileHost> hostProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Looper> looperProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public TileServices_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory = GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE;
        this.hostProvider = provider;
        this.looperProvider = globalConcurrencyModule_ProvideMainLooperFactory;
        this.broadcastDispatcherProvider = provider2;
        this.userTrackerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
    }

    public final Object get() {
        return new TileServices(this.hostProvider.get(), this.looperProvider.get(), this.broadcastDispatcherProvider.get(), this.userTrackerProvider.get(), this.keyguardStateControllerProvider.get());
    }
}
