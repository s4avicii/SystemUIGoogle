package com.android.systemui.p006qs.dagger;

import android.content.Context;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import com.android.systemui.p006qs.AutoAddTracker;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceControlsController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.WalletController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSModule_ProvideAutoTileManagerFactory */
public final class QSModule_ProvideAutoTileManagerFactory implements Factory<AutoTileManager> {
    public final Provider<AutoAddTracker.Builder> autoAddTrackerBuilderProvider;
    public final Provider<CastController> castControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DataSaverController> dataSaverControllerProvider;
    public final Provider<DeviceControlsController> deviceControlsControllerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<QSTileHost> hostProvider;
    public final Provider<HotspotController> hotspotControllerProvider;
    public final Provider<Boolean> isReduceBrightColorsAvailableProvider;
    public final Provider<ManagedProfileController> managedProfileControllerProvider;
    public final Provider<NightDisplayListener> nightDisplayListenerProvider;
    public final Provider<ReduceBrightColorsController> reduceBrightColorsControllerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<WalletController> walletControllerProvider;

    public static QSModule_ProvideAutoTileManagerFactory create(Provider<Context> provider, Provider<AutoAddTracker.Builder> provider2, Provider<QSTileHost> provider3, Provider<Handler> provider4, Provider<SecureSettings> provider5, Provider<HotspotController> provider6, Provider<DataSaverController> provider7, Provider<ManagedProfileController> provider8, Provider<NightDisplayListener> provider9, Provider<CastController> provider10, Provider<ReduceBrightColorsController> provider11, Provider<DeviceControlsController> provider12, Provider<WalletController> provider13, Provider<Boolean> provider14) {
        return new QSModule_ProvideAutoTileManagerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public final Object get() {
        AutoTileManager autoTileManager = new AutoTileManager(this.contextProvider.get(), this.autoAddTrackerBuilderProvider.get(), this.hostProvider.get(), this.handlerProvider.get(), this.secureSettingsProvider.get(), this.hotspotControllerProvider.get(), this.dataSaverControllerProvider.get(), this.managedProfileControllerProvider.get(), this.nightDisplayListenerProvider.get(), this.castControllerProvider.get(), this.reduceBrightColorsControllerProvider.get(), this.deviceControlsControllerProvider.get(), this.walletControllerProvider.get(), this.isReduceBrightColorsAvailableProvider.get().booleanValue());
        autoTileManager.init();
        return autoTileManager;
    }

    public QSModule_ProvideAutoTileManagerFactory(Provider<Context> provider, Provider<AutoAddTracker.Builder> provider2, Provider<QSTileHost> provider3, Provider<Handler> provider4, Provider<SecureSettings> provider5, Provider<HotspotController> provider6, Provider<DataSaverController> provider7, Provider<ManagedProfileController> provider8, Provider<NightDisplayListener> provider9, Provider<CastController> provider10, Provider<ReduceBrightColorsController> provider11, Provider<DeviceControlsController> provider12, Provider<WalletController> provider13, Provider<Boolean> provider14) {
        this.contextProvider = provider;
        this.autoAddTrackerBuilderProvider = provider2;
        this.hostProvider = provider3;
        this.handlerProvider = provider4;
        this.secureSettingsProvider = provider5;
        this.hotspotControllerProvider = provider6;
        this.dataSaverControllerProvider = provider7;
        this.managedProfileControllerProvider = provider8;
        this.nightDisplayListenerProvider = provider9;
        this.castControllerProvider = provider10;
        this.reduceBrightColorsControllerProvider = provider11;
        this.deviceControlsControllerProvider = provider12;
        this.walletControllerProvider = provider13;
        this.isReduceBrightColorsAvailableProvider = provider14;
    }
}
