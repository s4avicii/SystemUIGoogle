package com.android.systemui.theme;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ThemeOverlayController_Factory implements Factory<ThemeOverlayController> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<ThemeOverlayApplier> themeOverlayApplierProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    public final Provider<WallpaperManager> wallpaperManagerProvider;

    public static ThemeOverlayController_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Handler> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<ThemeOverlayApplier> provider6, Provider<SecureSettings> provider7, Provider<WallpaperManager> provider8, Provider<UserManager> provider9, Provider<DeviceProvisionedController> provider10, Provider<UserTracker> provider11, Provider<DumpManager> provider12, Provider<FeatureFlags> provider13, Provider<WakefulnessLifecycle> provider14) {
        return new ThemeOverlayController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public final Object get() {
        return new ThemeOverlayController(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), this.bgHandlerProvider.get(), this.mainExecutorProvider.get(), this.bgExecutorProvider.get(), this.themeOverlayApplierProvider.get(), this.secureSettingsProvider.get(), this.wallpaperManagerProvider.get(), this.userManagerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.userTrackerProvider.get(), this.dumpManagerProvider.get(), this.featureFlagsProvider.get(), this.wakefulnessLifecycleProvider.get());
    }

    public ThemeOverlayController_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Handler> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<ThemeOverlayApplier> provider6, Provider<SecureSettings> provider7, Provider<WallpaperManager> provider8, Provider<UserManager> provider9, Provider<DeviceProvisionedController> provider10, Provider<UserTracker> provider11, Provider<DumpManager> provider12, Provider<FeatureFlags> provider13, Provider<WakefulnessLifecycle> provider14) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.bgHandlerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.bgExecutorProvider = provider5;
        this.themeOverlayApplierProvider = provider6;
        this.secureSettingsProvider = provider7;
        this.wallpaperManagerProvider = provider8;
        this.userManagerProvider = provider9;
        this.deviceProvisionedControllerProvider = provider10;
        this.userTrackerProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.featureFlagsProvider = provider13;
        this.wakefulnessLifecycleProvider = provider14;
    }
}
