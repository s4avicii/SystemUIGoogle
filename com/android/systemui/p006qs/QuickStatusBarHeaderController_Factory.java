package com.android.systemui.p006qs;

import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.battery.BatteryMeterViewController_Factory;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QuickStatusBarHeaderController_Factory */
public final class QuickStatusBarHeaderController_Factory implements Factory<QuickStatusBarHeaderController> {
    public final Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<DemoModeController> demoModeControllerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
    public final Provider<QSCarrierGroupController.Builder> qsCarrierGroupControllerBuilderProvider;
    public final Provider<QSExpansionPathInterpolator> qsExpansionPathInterpolatorProvider;
    public final Provider<QuickQSPanelController> quickQSPanelControllerProvider;
    public final Provider<StatusBarContentInsetsProvider> statusBarContentInsetsProvider;
    public final Provider<StatusBarIconController> statusBarIconControllerProvider;
    public final Provider<VariableDateViewController.Factory> variableDateViewControllerFactoryProvider;
    public final Provider<QuickStatusBarHeader> viewProvider;

    public static QuickStatusBarHeaderController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, BatteryMeterViewController_Factory batteryMeterViewController_Factory, Provider provider11) {
        return new QuickStatusBarHeaderController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, batteryMeterViewController_Factory, provider11);
    }

    public final Object get() {
        return new QuickStatusBarHeaderController(this.viewProvider.get(), this.headerPrivacyIconsControllerProvider.get(), this.statusBarIconControllerProvider.get(), this.demoModeControllerProvider.get(), this.quickQSPanelControllerProvider.get(), this.qsCarrierGroupControllerBuilderProvider.get(), this.colorExtractorProvider.get(), this.qsExpansionPathInterpolatorProvider.get(), this.featureFlagsProvider.get(), this.variableDateViewControllerFactoryProvider.get(), this.batteryMeterViewControllerProvider.get(), this.statusBarContentInsetsProvider.get());
    }

    public QuickStatusBarHeaderController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, BatteryMeterViewController_Factory batteryMeterViewController_Factory, Provider provider11) {
        this.viewProvider = provider;
        this.headerPrivacyIconsControllerProvider = provider2;
        this.statusBarIconControllerProvider = provider3;
        this.demoModeControllerProvider = provider4;
        this.quickQSPanelControllerProvider = provider5;
        this.qsCarrierGroupControllerBuilderProvider = provider6;
        this.colorExtractorProvider = provider7;
        this.qsExpansionPathInterpolatorProvider = provider8;
        this.featureFlagsProvider = provider9;
        this.variableDateViewControllerFactoryProvider = provider10;
        this.batteryMeterViewControllerProvider = batteryMeterViewController_Factory;
        this.statusBarContentInsetsProvider = provider11;
    }
}
