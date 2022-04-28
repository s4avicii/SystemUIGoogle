package com.android.systemui.p006qs;

import android.os.Handler;
import android.os.UserManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.MultiUserSwitchController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.FooterActionsController_Factory */
public final class FooterActionsController_Factory implements Factory<FooterActionsController> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<QSFgsManagerFooter> fgsManagerFooterControllerProvider;
    public final Provider<GlobalActionsDialogLite> globalActionsDialogProvider;
    public final Provider<GlobalSettings> globalSettingProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<MultiUserSwitchController.Factory> multiUserSwitchControllerFactoryProvider;
    public final Provider<QSSecurityFooter> securityFooterControllerProvider;
    public final Provider<Boolean> showPMLiteButtonProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserInfoController> userInfoControllerProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<FooterActionsView> viewProvider;

    public FooterActionsController_Factory(Provider<FooterActionsView> provider, Provider<MultiUserSwitchController.Factory> provider2, Provider<ActivityStarter> provider3, Provider<UserManager> provider4, Provider<UserTracker> provider5, Provider<UserInfoController> provider6, Provider<DeviceProvisionedController> provider7, Provider<QSSecurityFooter> provider8, Provider<QSFgsManagerFooter> provider9, Provider<FalsingManager> provider10, Provider<MetricsLogger> provider11, Provider<GlobalActionsDialogLite> provider12, Provider<UiEventLogger> provider13, Provider<Boolean> provider14, Provider<GlobalSettings> provider15, Provider<Handler> provider16, Provider<FeatureFlags> provider17) {
        this.viewProvider = provider;
        this.multiUserSwitchControllerFactoryProvider = provider2;
        this.activityStarterProvider = provider3;
        this.userManagerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.userInfoControllerProvider = provider6;
        this.deviceProvisionedControllerProvider = provider7;
        this.securityFooterControllerProvider = provider8;
        this.fgsManagerFooterControllerProvider = provider9;
        this.falsingManagerProvider = provider10;
        this.metricsLoggerProvider = provider11;
        this.globalActionsDialogProvider = provider12;
        this.uiEventLoggerProvider = provider13;
        this.showPMLiteButtonProvider = provider14;
        this.globalSettingProvider = provider15;
        this.handlerProvider = provider16;
        this.featureFlagsProvider = provider17;
    }

    public static FooterActionsController_Factory create(Provider<FooterActionsView> provider, Provider<MultiUserSwitchController.Factory> provider2, Provider<ActivityStarter> provider3, Provider<UserManager> provider4, Provider<UserTracker> provider5, Provider<UserInfoController> provider6, Provider<DeviceProvisionedController> provider7, Provider<QSSecurityFooter> provider8, Provider<QSFgsManagerFooter> provider9, Provider<FalsingManager> provider10, Provider<MetricsLogger> provider11, Provider<GlobalActionsDialogLite> provider12, Provider<UiEventLogger> provider13, Provider<Boolean> provider14, Provider<GlobalSettings> provider15, Provider<Handler> provider16, Provider<FeatureFlags> provider17) {
        return new FooterActionsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public final Object get() {
        return new FooterActionsController(this.viewProvider.get(), this.multiUserSwitchControllerFactoryProvider.get(), this.activityStarterProvider.get(), this.userManagerProvider.get(), this.userTrackerProvider.get(), this.userInfoControllerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.securityFooterControllerProvider.get(), this.fgsManagerFooterControllerProvider.get(), this.falsingManagerProvider.get(), this.metricsLoggerProvider.get(), this.globalActionsDialogProvider.get(), this.uiEventLoggerProvider.get(), this.showPMLiteButtonProvider.get().booleanValue(), this.globalSettingProvider.get(), this.handlerProvider.get(), this.featureFlagsProvider.get());
    }
}
