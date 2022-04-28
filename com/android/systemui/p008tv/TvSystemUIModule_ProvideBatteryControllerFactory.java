package com.android.systemui.p008tv;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.util.WallpaperController_Factory;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.tv.TvSystemUIModule_ProvideBatteryControllerFactory */
public final class TvSystemUIModule_ProvideBatteryControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object bgHandlerProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider demoModeControllerProvider;
    public final Provider enhancedEstimatesProvider;
    public final Provider mainHandlerProvider;
    public final Provider powerManagerProvider;

    public /* synthetic */ TvSystemUIModule_ProvideBatteryControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.enhancedEstimatesProvider = provider2;
        this.powerManagerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.demoModeControllerProvider = provider5;
        this.mainHandlerProvider = provider6;
        this.bgHandlerProvider = provider7;
    }

    /* renamed from: get  reason: collision with other method in class */
    public final Object m283get() {
        switch (this.$r8$classId) {
            case 0:
                return get();
            case 1:
                return get();
            default:
                DumpManager dumpManager = (DumpManager) this.broadcastDispatcherProvider.get();
                Objects.requireNonNull((DependencyProvider) this.bgHandlerProvider);
                BroadcastDispatcher broadcastDispatcher = new BroadcastDispatcher((Context) this.contextProvider.get(), (Looper) this.enhancedEstimatesProvider.get(), (Executor) this.powerManagerProvider.get(), dumpManager, (BroadcastDispatcherLogger) this.demoModeControllerProvider.get(), (UserTracker) this.mainHandlerProvider.get());
                dumpManager.registerDumpable(BroadcastDispatcher.class.getName(), broadcastDispatcher);
                return broadcastDispatcher;
        }
    }

    public TvSystemUIModule_ProvideBatteryControllerFactory(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3, Provider provider4, WallpaperController_Factory wallpaperController_Factory, Provider provider5) {
        this.$r8$classId = 2;
        this.bgHandlerProvider = dependencyProvider;
        this.contextProvider = provider;
        this.enhancedEstimatesProvider = provider2;
        this.powerManagerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.demoModeControllerProvider = wallpaperController_Factory;
        this.mainHandlerProvider = provider5;
    }

    public static TvSystemUIModule_ProvideBatteryControllerFactory create(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3, Provider provider4, WallpaperController_Factory wallpaperController_Factory, Provider provider5) {
        return new TvSystemUIModule_ProvideBatteryControllerFactory(dependencyProvider, provider, provider2, provider3, provider4, wallpaperController_Factory, provider5);
    }

    public final BatteryController get() {
        switch (this.$r8$classId) {
            case 0:
                BatteryControllerImpl batteryControllerImpl = new BatteryControllerImpl((Context) this.contextProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) ((Provider) this.bgHandlerProvider).get());
                batteryControllerImpl.init();
                return batteryControllerImpl;
            default:
                BatteryControllerImpl batteryControllerImpl2 = new BatteryControllerImpl((Context) this.contextProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) ((Provider) this.bgHandlerProvider).get());
                batteryControllerImpl2.init();
                return batteryControllerImpl2;
        }
    }
}
