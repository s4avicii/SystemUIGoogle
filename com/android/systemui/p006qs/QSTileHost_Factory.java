package com.android.systemui.p006qs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.external.CustomTileStatePersister;
import com.android.systemui.p006qs.external.TileLifecycleManager;
import com.android.systemui.p006qs.external.TileServiceRequestController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.p005qs.QSFactory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSTileHost_Factory */
public final class QSTileHost_Factory implements Factory<QSTileHost> {
    public final Provider<AutoTileManager> autoTilesProvider;
    public final Provider<Looper> bgLooperProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<CustomTileStatePersister> customTileStatePersisterProvider;
    public final Provider<QSFactory> defaultFactoryProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<StatusBarIconController> iconControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<Optional<StatusBar>> statusBarOptionalProvider;
    public final Provider<TileLifecycleManager.Factory> tileLifecycleManagerFactoryProvider;
    public final Provider<TileServiceRequestController.Builder> tileServiceRequestControllerBuilderProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public QSTileHost_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, InstanceFactory instanceFactory) {
        this.contextProvider = provider;
        this.iconControllerProvider = provider2;
        this.defaultFactoryProvider = provider3;
        this.mainHandlerProvider = provider4;
        this.bgLooperProvider = provider5;
        this.pluginManagerProvider = provider6;
        this.tunerServiceProvider = provider7;
        this.autoTilesProvider = provider8;
        this.dumpManagerProvider = provider9;
        this.broadcastDispatcherProvider = provider10;
        this.statusBarOptionalProvider = provider11;
        this.qsLoggerProvider = provider12;
        this.uiEventLoggerProvider = provider13;
        this.userTrackerProvider = provider14;
        this.secureSettingsProvider = provider15;
        this.customTileStatePersisterProvider = provider16;
        this.tileServiceRequestControllerBuilderProvider = provider17;
        this.tileLifecycleManagerFactoryProvider = instanceFactory;
    }

    public static QSTileHost_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, InstanceFactory instanceFactory) {
        return new QSTileHost_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, instanceFactory);
    }

    public final Object get() {
        Looper looper = this.bgLooperProvider.get();
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcherProvider.get();
        return new QSTileHost(this.contextProvider.get(), this.iconControllerProvider.get(), this.defaultFactoryProvider.get(), this.mainHandlerProvider.get(), this.pluginManagerProvider.get(), this.tunerServiceProvider.get(), this.autoTilesProvider, this.dumpManagerProvider.get(), this.statusBarOptionalProvider.get(), this.qsLoggerProvider.get(), this.uiEventLoggerProvider.get(), this.userTrackerProvider.get(), this.secureSettingsProvider.get(), this.customTileStatePersisterProvider.get(), this.tileServiceRequestControllerBuilderProvider.get(), this.tileLifecycleManagerFactoryProvider.get());
    }
}
