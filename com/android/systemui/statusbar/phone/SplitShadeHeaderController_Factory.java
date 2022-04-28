package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.p006qs.HeaderPrivacyIconsController;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SplitShadeHeaderController_Factory implements Factory<SplitShadeHeaderController> {
    public final Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<HeaderPrivacyIconsController> privacyIconsControllerProvider;
    public final Provider<QSCarrierGroupController.Builder> qsCarrierGroupControllerBuilderProvider;
    public final Provider<StatusBarIconController> statusBarIconControllerProvider;
    public final Provider<View> statusBarProvider;

    public static SplitShadeHeaderController_Factory create(Provider<View> provider, Provider<StatusBarIconController> provider2, Provider<HeaderPrivacyIconsController> provider3, Provider<QSCarrierGroupController.Builder> provider4, Provider<FeatureFlags> provider5, Provider<BatteryMeterViewController> provider6, Provider<DumpManager> provider7) {
        return new SplitShadeHeaderController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public final Object get() {
        return new SplitShadeHeaderController(this.statusBarProvider.get(), this.statusBarIconControllerProvider.get(), this.privacyIconsControllerProvider.get(), this.qsCarrierGroupControllerBuilderProvider.get(), this.featureFlagsProvider.get(), this.batteryMeterViewControllerProvider.get(), this.dumpManagerProvider.get());
    }

    public SplitShadeHeaderController_Factory(Provider<View> provider, Provider<StatusBarIconController> provider2, Provider<HeaderPrivacyIconsController> provider3, Provider<QSCarrierGroupController.Builder> provider4, Provider<FeatureFlags> provider5, Provider<BatteryMeterViewController> provider6, Provider<DumpManager> provider7) {
        this.statusBarProvider = provider;
        this.statusBarIconControllerProvider = provider2;
        this.privacyIconsControllerProvider = provider3;
        this.qsCarrierGroupControllerBuilderProvider = provider4;
        this.featureFlagsProvider = provider5;
        this.batteryMeterViewControllerProvider = provider6;
        this.dumpManagerProvider = provider7;
    }
}
