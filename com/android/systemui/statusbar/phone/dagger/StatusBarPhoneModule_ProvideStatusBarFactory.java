package com.android.systemui.statusbar.phone.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.systemui.InitController;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.charging.WiredChargingRippleController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.dagger.StatusBarComponent;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.wmshell.BubblesManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class StatusBarPhoneModule_ProvideStatusBarFactory implements Factory<StatusBar> {
    public final Provider<AccessibilityFloatingMenuController> accessibilityFloatingMenuControllerProvider;
    public final Provider<ActivityLaunchAnimator> activityLaunchAnimatorProvider;
    public final Provider<AssistManager> assistManagerLazyProvider;
    public final Provider<AutoHideController> autoHideControllerProvider;
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<BiometricUnlockController> biometricUnlockControllerLazyProvider;
    public final Provider<BrightnessSliderController.Factory> brightnessSliderFactoryProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Optional<BubblesManager>> bubblesManagerOptionalProvider;
    public final Provider<Optional<Bubbles>> bubblesOptionalProvider;
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DelayableExecutor> delayableExecutorProvider;
    public final Provider<DemoModeController> demoModeControllerProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DeviceStateManager> deviceStateManagerProvider;
    public final Provider<DisplayMetrics> displayMetricsProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<DozeScrimController> dozeScrimControllerProvider;
    public final Provider<DozeServiceHost> dozeServiceHostProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
    public final Provider<ExtensionController> extensionControllerProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<FragmentService> fragmentServiceProvider;
    public final Provider<HeadsUpManagerPhone> headsUpManagerPhoneProvider;
    public final Provider<InitController> initControllerProvider;
    public final Provider<InteractionJankMonitor> jankMonitorProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
    public final Provider<KeyguardIndicationController> keyguardIndicationControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<KeyguardViewMediator> keyguardViewMediatorProvider;
    public final Provider<LightBarController> lightBarControllerProvider;
    public final Provider<NotificationLockscreenUserManager> lockScreenUserManagerProvider;
    public final Provider<LockscreenGestureLogger> lockscreenGestureLoggerProvider;
    public final Provider<LockscreenWallpaper> lockscreenWallpaperLazyProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MessageRouter> messageRouterProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<NavigationBarController> navigationBarControllerProvider;
    public final Provider<NetworkController> networkControllerProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
    public final Provider<NotifShadeEventSource> notifShadeEventSourceProvider;
    public final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    public final Provider<NotificationGutsManager> notificationGutsManagerProvider;
    public final Provider<NotificationIconAreaController> notificationIconAreaControllerProvider;
    public final Provider<NotificationInterruptStateProvider> notificationInterruptStateProvider;
    public final Provider<NotificationLogger> notificationLoggerProvider;
    public final Provider<NotificationMediaManager> notificationMediaManagerProvider;
    public final Provider<NotificationShadeDepthController> notificationShadeDepthControllerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    public final Provider<NotificationViewHierarchyManager> notificationViewHierarchyManagerProvider;
    public final Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
    public final Provider<NotificationsController> notificationsControllerProvider;
    public final Provider<OngoingCallController> ongoingCallControllerProvider;
    public final Provider<PanelExpansionStateManager> panelExpansionStateManagerProvider;
    public final Provider<PhoneStatusBarPolicy> phoneStatusBarPolicyProvider;
    public final Provider<PluginDependencyProvider> pluginDependencyProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
    public final Provider<NotificationRemoteInputManager> remoteInputManagerProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
    public final Provider<ScreenPinningRequest> screenPinningRequestProvider;
    public final Provider<ScrimController> scrimControllerProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<Optional<LegacySplitScreen>> splitScreenOptionalProvider;
    public final Provider<Optional<StartingSurface>> startingSurfaceOptionalProvider;
    public final Provider<StatusBarComponent.Factory> statusBarComponentFactoryProvider;
    public final Provider<StatusBarHideIconsForBouncerManager> statusBarHideIconsForBouncerManagerProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<StatusBarNotificationActivityStarter.Builder> statusBarNotificationActivityStarterBuilderProvider;
    public final Provider<StatusBarSignalPolicy> statusBarSignalPolicyProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<StatusBarTouchableRegionManager> statusBarTouchableRegionManagerProvider;
    public final Provider<StatusBarWindowController> statusBarWindowControllerProvider;
    public final Provider<StatusBarWindowStateController> statusBarWindowStateControllerProvider;
    public final Provider<Handler> timeTickHandlerProvider;
    public final Provider<LockscreenShadeTransitionController> transitionControllerProvider;
    public final Provider<Executor> uiBgExecutorProvider;
    public final Provider<UserInfoControllerImpl> userInfoControllerImplProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;
    public final Provider<ViewMediatorCallback> viewMediatorCallbackProvider;
    public final Provider<VisualStabilityManager> visualStabilityManagerProvider;
    public final Provider<VolumeComponent> volumeComponentProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    public final Provider<WallpaperController> wallpaperControllerProvider;
    public final Provider<WallpaperManager> wallpaperManagerProvider;
    public final Provider<WiredChargingRippleController> wiredChargingRippleControllerProvider;

    public StatusBarPhoneModule_ProvideStatusBarFactory(Provider<Context> provider, Provider<NotificationsController> provider2, Provider<FragmentService> provider3, Provider<LightBarController> provider4, Provider<AutoHideController> provider5, Provider<StatusBarWindowController> provider6, Provider<StatusBarWindowStateController> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<StatusBarSignalPolicy> provider9, Provider<PulseExpansionHandler> provider10, Provider<NotificationWakeUpCoordinator> provider11, Provider<KeyguardBypassController> provider12, Provider<KeyguardStateController> provider13, Provider<HeadsUpManagerPhone> provider14, Provider<DynamicPrivacyController> provider15, Provider<FalsingManager> provider16, Provider<FalsingCollector> provider17, Provider<BroadcastDispatcher> provider18, Provider<NotifShadeEventSource> provider19, Provider<NotificationEntryManager> provider20, Provider<NotificationGutsManager> provider21, Provider<NotificationLogger> provider22, Provider<NotificationInterruptStateProvider> provider23, Provider<NotificationViewHierarchyManager> provider24, Provider<PanelExpansionStateManager> provider25, Provider<KeyguardViewMediator> provider26, Provider<DisplayMetrics> provider27, Provider<MetricsLogger> provider28, Provider<Executor> provider29, Provider<NotificationMediaManager> provider30, Provider<NotificationLockscreenUserManager> provider31, Provider<NotificationRemoteInputManager> provider32, Provider<UserSwitcherController> provider33, Provider<NetworkController> provider34, Provider<BatteryController> provider35, Provider<SysuiColorExtractor> provider36, Provider<ScreenLifecycle> provider37, Provider<WakefulnessLifecycle> provider38, Provider<SysuiStatusBarStateController> provider39, Provider<Optional<BubblesManager>> provider40, Provider<Optional<Bubbles>> provider41, Provider<VisualStabilityManager> provider42, Provider<DeviceProvisionedController> provider43, Provider<NavigationBarController> provider44, Provider<AccessibilityFloatingMenuController> provider45, Provider<AssistManager> provider46, Provider<ConfigurationController> provider47, Provider<NotificationShadeWindowController> provider48, Provider<DozeParameters> provider49, Provider<ScrimController> provider50, Provider<LockscreenWallpaper> provider51, Provider<LockscreenGestureLogger> provider52, Provider<BiometricUnlockController> provider53, Provider<DozeServiceHost> provider54, Provider<PowerManager> provider55, Provider<ScreenPinningRequest> provider56, Provider<DozeScrimController> provider57, Provider<VolumeComponent> provider58, Provider<CommandQueue> provider59, Provider<StatusBarComponent.Factory> provider60, Provider<PluginManager> provider61, Provider<Optional<LegacySplitScreen>> provider62, Provider<StatusBarNotificationActivityStarter.Builder> provider63, Provider<ShadeController> provider64, Provider<StatusBarKeyguardViewManager> provider65, Provider<ViewMediatorCallback> provider66, Provider<InitController> provider67, Provider<Handler> provider68, Provider<PluginDependencyProvider> provider69, Provider<KeyguardDismissUtil> provider70, Provider<ExtensionController> provider71, Provider<UserInfoControllerImpl> provider72, Provider<PhoneStatusBarPolicy> provider73, Provider<KeyguardIndicationController> provider74, Provider<DemoModeController> provider75, Provider<NotificationShadeDepthController> provider76, Provider<StatusBarTouchableRegionManager> provider77, Provider<NotificationIconAreaController> provider78, Provider<BrightnessSliderController.Factory> provider79, Provider<ScreenOffAnimationController> provider80, Provider<WallpaperController> provider81, Provider<OngoingCallController> provider82, Provider<StatusBarHideIconsForBouncerManager> provider83, Provider<LockscreenShadeTransitionController> provider84, Provider<FeatureFlags> provider85, Provider<KeyguardUnlockAnimationController> provider86, Provider<Handler> provider87, Provider<DelayableExecutor> provider88, Provider<MessageRouter> provider89, Provider<WallpaperManager> provider90, Provider<Optional<StartingSurface>> provider91, Provider<ActivityLaunchAnimator> provider92, Provider<NotifPipelineFlags> provider93, Provider<InteractionJankMonitor> provider94, Provider<DeviceStateManager> provider95, Provider<DreamOverlayStateController> provider96, Provider<WiredChargingRippleController> provider97) {
        this.contextProvider = provider;
        this.notificationsControllerProvider = provider2;
        this.fragmentServiceProvider = provider3;
        this.lightBarControllerProvider = provider4;
        this.autoHideControllerProvider = provider5;
        this.statusBarWindowControllerProvider = provider6;
        this.statusBarWindowStateControllerProvider = provider7;
        this.keyguardUpdateMonitorProvider = provider8;
        this.statusBarSignalPolicyProvider = provider9;
        this.pulseExpansionHandlerProvider = provider10;
        this.notificationWakeUpCoordinatorProvider = provider11;
        this.keyguardBypassControllerProvider = provider12;
        this.keyguardStateControllerProvider = provider13;
        this.headsUpManagerPhoneProvider = provider14;
        this.dynamicPrivacyControllerProvider = provider15;
        this.falsingManagerProvider = provider16;
        this.falsingCollectorProvider = provider17;
        this.broadcastDispatcherProvider = provider18;
        this.notifShadeEventSourceProvider = provider19;
        this.notificationEntryManagerProvider = provider20;
        this.notificationGutsManagerProvider = provider21;
        this.notificationLoggerProvider = provider22;
        this.notificationInterruptStateProvider = provider23;
        this.notificationViewHierarchyManagerProvider = provider24;
        this.panelExpansionStateManagerProvider = provider25;
        this.keyguardViewMediatorProvider = provider26;
        this.displayMetricsProvider = provider27;
        this.metricsLoggerProvider = provider28;
        this.uiBgExecutorProvider = provider29;
        this.notificationMediaManagerProvider = provider30;
        this.lockScreenUserManagerProvider = provider31;
        this.remoteInputManagerProvider = provider32;
        this.userSwitcherControllerProvider = provider33;
        this.networkControllerProvider = provider34;
        this.batteryControllerProvider = provider35;
        this.colorExtractorProvider = provider36;
        this.screenLifecycleProvider = provider37;
        this.wakefulnessLifecycleProvider = provider38;
        this.statusBarStateControllerProvider = provider39;
        this.bubblesManagerOptionalProvider = provider40;
        this.bubblesOptionalProvider = provider41;
        this.visualStabilityManagerProvider = provider42;
        this.deviceProvisionedControllerProvider = provider43;
        this.navigationBarControllerProvider = provider44;
        this.accessibilityFloatingMenuControllerProvider = provider45;
        this.assistManagerLazyProvider = provider46;
        this.configurationControllerProvider = provider47;
        this.notificationShadeWindowControllerProvider = provider48;
        this.dozeParametersProvider = provider49;
        this.scrimControllerProvider = provider50;
        this.lockscreenWallpaperLazyProvider = provider51;
        this.lockscreenGestureLoggerProvider = provider52;
        this.biometricUnlockControllerLazyProvider = provider53;
        this.dozeServiceHostProvider = provider54;
        this.powerManagerProvider = provider55;
        this.screenPinningRequestProvider = provider56;
        this.dozeScrimControllerProvider = provider57;
        this.volumeComponentProvider = provider58;
        this.commandQueueProvider = provider59;
        this.statusBarComponentFactoryProvider = provider60;
        this.pluginManagerProvider = provider61;
        this.splitScreenOptionalProvider = provider62;
        this.statusBarNotificationActivityStarterBuilderProvider = provider63;
        this.shadeControllerProvider = provider64;
        this.statusBarKeyguardViewManagerProvider = provider65;
        this.viewMediatorCallbackProvider = provider66;
        this.initControllerProvider = provider67;
        this.timeTickHandlerProvider = provider68;
        this.pluginDependencyProvider = provider69;
        this.keyguardDismissUtilProvider = provider70;
        this.extensionControllerProvider = provider71;
        this.userInfoControllerImplProvider = provider72;
        this.phoneStatusBarPolicyProvider = provider73;
        this.keyguardIndicationControllerProvider = provider74;
        this.demoModeControllerProvider = provider75;
        this.notificationShadeDepthControllerProvider = provider76;
        this.statusBarTouchableRegionManagerProvider = provider77;
        this.notificationIconAreaControllerProvider = provider78;
        this.brightnessSliderFactoryProvider = provider79;
        this.screenOffAnimationControllerProvider = provider80;
        this.wallpaperControllerProvider = provider81;
        this.ongoingCallControllerProvider = provider82;
        this.statusBarHideIconsForBouncerManagerProvider = provider83;
        this.transitionControllerProvider = provider84;
        this.featureFlagsProvider = provider85;
        this.keyguardUnlockAnimationControllerProvider = provider86;
        this.mainHandlerProvider = provider87;
        this.delayableExecutorProvider = provider88;
        this.messageRouterProvider = provider89;
        this.wallpaperManagerProvider = provider90;
        this.startingSurfaceOptionalProvider = provider91;
        this.activityLaunchAnimatorProvider = provider92;
        this.notifPipelineFlagsProvider = provider93;
        this.jankMonitorProvider = provider94;
        this.deviceStateManagerProvider = provider95;
        this.dreamOverlayStateControllerProvider = provider96;
        this.wiredChargingRippleControllerProvider = provider97;
    }

    public static StatusBarPhoneModule_ProvideStatusBarFactory create(Provider<Context> provider, Provider<NotificationsController> provider2, Provider<FragmentService> provider3, Provider<LightBarController> provider4, Provider<AutoHideController> provider5, Provider<StatusBarWindowController> provider6, Provider<StatusBarWindowStateController> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<StatusBarSignalPolicy> provider9, Provider<PulseExpansionHandler> provider10, Provider<NotificationWakeUpCoordinator> provider11, Provider<KeyguardBypassController> provider12, Provider<KeyguardStateController> provider13, Provider<HeadsUpManagerPhone> provider14, Provider<DynamicPrivacyController> provider15, Provider<FalsingManager> provider16, Provider<FalsingCollector> provider17, Provider<BroadcastDispatcher> provider18, Provider<NotifShadeEventSource> provider19, Provider<NotificationEntryManager> provider20, Provider<NotificationGutsManager> provider21, Provider<NotificationLogger> provider22, Provider<NotificationInterruptStateProvider> provider23, Provider<NotificationViewHierarchyManager> provider24, Provider<PanelExpansionStateManager> provider25, Provider<KeyguardViewMediator> provider26, Provider<DisplayMetrics> provider27, Provider<MetricsLogger> provider28, Provider<Executor> provider29, Provider<NotificationMediaManager> provider30, Provider<NotificationLockscreenUserManager> provider31, Provider<NotificationRemoteInputManager> provider32, Provider<UserSwitcherController> provider33, Provider<NetworkController> provider34, Provider<BatteryController> provider35, Provider<SysuiColorExtractor> provider36, Provider<ScreenLifecycle> provider37, Provider<WakefulnessLifecycle> provider38, Provider<SysuiStatusBarStateController> provider39, Provider<Optional<BubblesManager>> provider40, Provider<Optional<Bubbles>> provider41, Provider<VisualStabilityManager> provider42, Provider<DeviceProvisionedController> provider43, Provider<NavigationBarController> provider44, Provider<AccessibilityFloatingMenuController> provider45, Provider<AssistManager> provider46, Provider<ConfigurationController> provider47, Provider<NotificationShadeWindowController> provider48, Provider<DozeParameters> provider49, Provider<ScrimController> provider50, Provider<LockscreenWallpaper> provider51, Provider<LockscreenGestureLogger> provider52, Provider<BiometricUnlockController> provider53, Provider<DozeServiceHost> provider54, Provider<PowerManager> provider55, Provider<ScreenPinningRequest> provider56, Provider<DozeScrimController> provider57, Provider<VolumeComponent> provider58, Provider<CommandQueue> provider59, Provider<StatusBarComponent.Factory> provider60, Provider<PluginManager> provider61, Provider<Optional<LegacySplitScreen>> provider62, Provider<StatusBarNotificationActivityStarter.Builder> provider63, Provider<ShadeController> provider64, Provider<StatusBarKeyguardViewManager> provider65, Provider<ViewMediatorCallback> provider66, Provider<InitController> provider67, Provider<Handler> provider68, Provider<PluginDependencyProvider> provider69, Provider<KeyguardDismissUtil> provider70, Provider<ExtensionController> provider71, Provider<UserInfoControllerImpl> provider72, Provider<PhoneStatusBarPolicy> provider73, Provider<KeyguardIndicationController> provider74, Provider<DemoModeController> provider75, Provider<NotificationShadeDepthController> provider76, Provider<StatusBarTouchableRegionManager> provider77, Provider<NotificationIconAreaController> provider78, Provider<BrightnessSliderController.Factory> provider79, Provider<ScreenOffAnimationController> provider80, Provider<WallpaperController> provider81, Provider<OngoingCallController> provider82, Provider<StatusBarHideIconsForBouncerManager> provider83, Provider<LockscreenShadeTransitionController> provider84, Provider<FeatureFlags> provider85, Provider<KeyguardUnlockAnimationController> provider86, Provider<Handler> provider87, Provider<DelayableExecutor> provider88, Provider<MessageRouter> provider89, Provider<WallpaperManager> provider90, Provider<Optional<StartingSurface>> provider91, Provider<ActivityLaunchAnimator> provider92, Provider<NotifPipelineFlags> provider93, Provider<InteractionJankMonitor> provider94, Provider<DeviceStateManager> provider95, Provider<DreamOverlayStateController> provider96, Provider<WiredChargingRippleController> provider97) {
        return new StatusBarPhoneModule_ProvideStatusBarFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47, provider48, provider49, provider50, provider51, provider52, provider53, provider54, provider55, provider56, provider57, provider58, provider59, provider60, provider61, provider62, provider63, provider64, provider65, provider66, provider67, provider68, provider69, provider70, provider71, provider72, provider73, provider74, provider75, provider76, provider77, provider78, provider79, provider80, provider81, provider82, provider83, provider84, provider85, provider86, provider87, provider88, provider89, provider90, provider91, provider92, provider93, provider94, provider95, provider96, provider97);
    }

    public final Object get() {
        Optional optional = this.bubblesManagerOptionalProvider.get();
        Handler handler = this.mainHandlerProvider.get();
        return new StatusBar(this.contextProvider.get(), this.notificationsControllerProvider.get(), this.fragmentServiceProvider.get(), this.lightBarControllerProvider.get(), this.autoHideControllerProvider.get(), this.statusBarWindowControllerProvider.get(), this.statusBarWindowStateControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.statusBarSignalPolicyProvider.get(), this.pulseExpansionHandlerProvider.get(), this.notificationWakeUpCoordinatorProvider.get(), this.keyguardBypassControllerProvider.get(), this.keyguardStateControllerProvider.get(), this.headsUpManagerPhoneProvider.get(), this.dynamicPrivacyControllerProvider.get(), this.falsingManagerProvider.get(), this.falsingCollectorProvider.get(), this.broadcastDispatcherProvider.get(), this.notifShadeEventSourceProvider.get(), this.notificationEntryManagerProvider.get(), this.notificationGutsManagerProvider.get(), this.notificationLoggerProvider.get(), this.notificationInterruptStateProvider.get(), this.notificationViewHierarchyManagerProvider.get(), this.panelExpansionStateManagerProvider.get(), this.keyguardViewMediatorProvider.get(), this.displayMetricsProvider.get(), this.metricsLoggerProvider.get(), this.uiBgExecutorProvider.get(), this.notificationMediaManagerProvider.get(), this.lockScreenUserManagerProvider.get(), this.remoteInputManagerProvider.get(), this.userSwitcherControllerProvider.get(), this.networkControllerProvider.get(), this.batteryControllerProvider.get(), this.colorExtractorProvider.get(), this.screenLifecycleProvider.get(), this.wakefulnessLifecycleProvider.get(), this.statusBarStateControllerProvider.get(), this.bubblesOptionalProvider.get(), this.visualStabilityManagerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.navigationBarControllerProvider.get(), this.accessibilityFloatingMenuControllerProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), this.configurationControllerProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.dozeParametersProvider.get(), this.scrimControllerProvider.get(), DoubleCheck.lazy(this.lockscreenWallpaperLazyProvider), this.lockscreenGestureLoggerProvider.get(), DoubleCheck.lazy(this.biometricUnlockControllerLazyProvider), this.dozeServiceHostProvider.get(), this.powerManagerProvider.get(), this.screenPinningRequestProvider.get(), this.dozeScrimControllerProvider.get(), this.volumeComponentProvider.get(), this.commandQueueProvider.get(), this.statusBarComponentFactoryProvider.get(), this.pluginManagerProvider.get(), this.splitScreenOptionalProvider.get(), this.statusBarNotificationActivityStarterBuilderProvider.get(), this.shadeControllerProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.viewMediatorCallbackProvider.get(), this.initControllerProvider.get(), this.timeTickHandlerProvider.get(), this.pluginDependencyProvider.get(), this.keyguardDismissUtilProvider.get(), this.extensionControllerProvider.get(), this.userInfoControllerImplProvider.get(), this.phoneStatusBarPolicyProvider.get(), this.keyguardIndicationControllerProvider.get(), this.demoModeControllerProvider.get(), DoubleCheck.lazy(this.notificationShadeDepthControllerProvider), this.statusBarTouchableRegionManagerProvider.get(), this.notificationIconAreaControllerProvider.get(), this.brightnessSliderFactoryProvider.get(), this.screenOffAnimationControllerProvider.get(), this.wallpaperControllerProvider.get(), this.ongoingCallControllerProvider.get(), this.statusBarHideIconsForBouncerManagerProvider.get(), this.transitionControllerProvider.get(), this.featureFlagsProvider.get(), this.keyguardUnlockAnimationControllerProvider.get(), this.delayableExecutorProvider.get(), this.messageRouterProvider.get(), this.wallpaperManagerProvider.get(), this.startingSurfaceOptionalProvider.get(), this.activityLaunchAnimatorProvider.get(), this.notifPipelineFlagsProvider.get(), this.jankMonitorProvider.get(), this.deviceStateManagerProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.wiredChargingRippleControllerProvider.get());
    }
}
