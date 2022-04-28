package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainLooperFactory;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LocationControllerImpl_Factory implements Factory<LocationControllerImpl> {
    public final Provider<AppOpsController> appOpsControllerProvider;
    public final Provider<Handler> backgroundHandlerProvider;
    public final Provider<BootCompleteCache> bootCompleteCacheProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<Looper> mainLooperProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public LocationControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        GlobalConcurrencyModule_ProvideMainLooperFactory globalConcurrencyModule_ProvideMainLooperFactory = GlobalConcurrencyModule_ProvideMainLooperFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.appOpsControllerProvider = provider2;
        this.deviceConfigProxyProvider = provider3;
        this.mainLooperProvider = globalConcurrencyModule_ProvideMainLooperFactory;
        this.backgroundHandlerProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
        this.bootCompleteCacheProvider = provider6;
        this.userTrackerProvider = provider7;
        this.packageManagerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
        this.secureSettingsProvider = provider10;
    }

    public static LocationControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new LocationControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public final Object get() {
        return new LocationControllerImpl(this.contextProvider.get(), this.appOpsControllerProvider.get(), this.deviceConfigProxyProvider.get(), this.mainLooperProvider.get(), this.backgroundHandlerProvider.get(), this.broadcastDispatcherProvider.get(), this.bootCompleteCacheProvider.get(), this.userTrackerProvider.get(), this.packageManagerProvider.get(), this.uiEventLoggerProvider.get(), this.secureSettingsProvider.get());
    }
}
