package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import android.view.LayoutInflater;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p012wm.shell.dagger.TvPipModule_ProvideTvPipBoundsStateFactory;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationStackScrollLayoutController_Factory implements Factory<NotificationStackScrollLayoutController> {
    public final Provider<Boolean> allowLongPressProvider;
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<ForegroundServiceDismissalFeatureController> fgFeatureControllerProvider;
    public final Provider<ForegroundServiceSectionController> fgServicesSectionControllerProvider;
    public final Provider<GroupExpansionManager> groupManagerProvider;
    public final Provider<HeadsUpManagerPhone> headsUpManagerProvider;
    public final Provider<IStatusBarService> iStatusBarServiceProvider;
    public final Provider<InteractionJankMonitor> jankMonitorProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardMediaController> keyguardMediaControllerProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<NotificationGroupManagerLegacy> legacyGroupManagerProvider;
    public final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    public final Provider<NotificationLockscreenUserManager> lockscreenUserManagerProvider;
    public final Provider<NotificationStackScrollLogger> loggerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<NotifCollection> notifCollectionProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
    public final Provider<NotifPipeline> notifPipelineProvider;
    public final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    public final Provider<NotificationGutsManager> notificationGutsManagerProvider;
    public final Provider<NotificationRoundnessManager> notificationRoundnessManagerProvider;
    public final Provider<NotificationSwipeHelper.Builder> notificationSwipeHelperBuilderProvider;
    public final Provider<NotificationRemoteInputManager> remoteInputManagerProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<ScrimController> scrimControllerProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<SectionHeaderController> silentHeaderControllerProvider;
    public final Provider<StackStateLogger> stackLoggerProvider;
    public final Provider<StatusBar> statusBarProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<NotificationVisibilityProvider> visibilityProvider;
    public final Provider<VisualStabilityManager> visualStabilityManagerProvider;
    public final Provider<ZenModeController> zenModeControllerProvider;

    public NotificationStackScrollLayoutController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, TvPipModule_ProvideTvPipBoundsStateFactory tvPipModule_ProvideTvPipBoundsStateFactory) {
        this.allowLongPressProvider = provider;
        this.notificationGutsManagerProvider = provider2;
        this.visibilityProvider = provider3;
        this.headsUpManagerProvider = provider4;
        this.notificationRoundnessManagerProvider = provider5;
        this.tunerServiceProvider = provider6;
        this.deviceProvisionedControllerProvider = provider7;
        this.dynamicPrivacyControllerProvider = provider8;
        this.configurationControllerProvider = provider9;
        this.statusBarStateControllerProvider = provider10;
        this.keyguardMediaControllerProvider = provider11;
        this.keyguardBypassControllerProvider = provider12;
        this.zenModeControllerProvider = provider13;
        this.colorExtractorProvider = provider14;
        this.lockscreenUserManagerProvider = provider15;
        this.metricsLoggerProvider = provider16;
        this.falsingCollectorProvider = provider17;
        this.falsingManagerProvider = provider18;
        this.resourcesProvider = provider19;
        this.notificationSwipeHelperBuilderProvider = provider20;
        this.statusBarProvider = provider21;
        this.scrimControllerProvider = provider22;
        this.legacyGroupManagerProvider = provider23;
        this.groupManagerProvider = provider24;
        this.silentHeaderControllerProvider = provider25;
        this.notifPipelineFlagsProvider = provider26;
        this.notifPipelineProvider = provider27;
        this.notifCollectionProvider = provider28;
        this.notificationEntryManagerProvider = provider29;
        this.lockscreenShadeTransitionControllerProvider = provider30;
        this.iStatusBarServiceProvider = provider31;
        this.uiEventLoggerProvider = provider32;
        this.fgFeatureControllerProvider = provider33;
        this.fgServicesSectionControllerProvider = provider34;
        this.layoutInflaterProvider = provider35;
        this.remoteInputManagerProvider = provider36;
        this.visualStabilityManagerProvider = provider37;
        this.shadeControllerProvider = provider38;
        this.jankMonitorProvider = provider39;
        this.stackLoggerProvider = provider40;
        this.loggerProvider = tvPipModule_ProvideTvPipBoundsStateFactory;
    }

    public static NotificationStackScrollLayoutController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, TvPipModule_ProvideTvPipBoundsStateFactory tvPipModule_ProvideTvPipBoundsStateFactory) {
        return new NotificationStackScrollLayoutController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, tvPipModule_ProvideTvPipBoundsStateFactory);
    }

    public final Object get() {
        SysuiColorExtractor sysuiColorExtractor = this.colorExtractorProvider.get();
        return new NotificationStackScrollLayoutController(this.allowLongPressProvider.get().booleanValue(), this.notificationGutsManagerProvider.get(), this.visibilityProvider.get(), this.headsUpManagerProvider.get(), this.notificationRoundnessManagerProvider.get(), this.tunerServiceProvider.get(), this.deviceProvisionedControllerProvider.get(), this.dynamicPrivacyControllerProvider.get(), this.configurationControllerProvider.get(), this.statusBarStateControllerProvider.get(), this.keyguardMediaControllerProvider.get(), this.keyguardBypassControllerProvider.get(), this.zenModeControllerProvider.get(), this.lockscreenUserManagerProvider.get(), this.metricsLoggerProvider.get(), this.falsingCollectorProvider.get(), this.falsingManagerProvider.get(), this.resourcesProvider.get(), this.notificationSwipeHelperBuilderProvider.get(), this.statusBarProvider.get(), this.scrimControllerProvider.get(), this.legacyGroupManagerProvider.get(), this.groupManagerProvider.get(), this.silentHeaderControllerProvider.get(), this.notifPipelineFlagsProvider.get(), this.notifPipelineProvider.get(), this.notifCollectionProvider.get(), this.notificationEntryManagerProvider.get(), this.lockscreenShadeTransitionControllerProvider.get(), this.iStatusBarServiceProvider.get(), this.uiEventLoggerProvider.get(), this.fgFeatureControllerProvider.get(), this.fgServicesSectionControllerProvider.get(), this.layoutInflaterProvider.get(), this.remoteInputManagerProvider.get(), this.visualStabilityManagerProvider.get(), this.shadeControllerProvider.get(), this.jankMonitorProvider.get(), this.stackLoggerProvider.get(), this.loggerProvider.get());
    }
}
