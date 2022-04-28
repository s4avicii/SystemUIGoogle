package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.os.PowerManager;
import android.os.Vibrator;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.DisableFlagsLogger;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class StatusBarCommandQueueCallbacks_Factory implements Factory<StatusBarCommandQueueCallbacks> {
    public final Provider<AssistManager> assistManagerProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DisableFlagsLogger> disableFlagsLoggerProvider;
    public final Provider<Integer> displayIdProvider;
    public final Provider<DozeServiceHost> dozeServiceHostProvider;
    public final Provider<HeadsUpManager> headsUpManagerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LightBarController> lightBarControllerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<NotificationPanelViewController> notificationPanelViewControllerProvider;
    public final Provider<NotificationShadeWindowView> notificationShadeWindowViewProvider;
    public final Provider<NotificationStackScrollLayoutController> notificationStackScrollLayoutControllerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<RemoteInputQuickSettingsDisabler> remoteInputQuickSettingsDisablerProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<Optional<LegacySplitScreen>> splitScreenOptionalProvider;
    public final Provider<StatusBarHideIconsForBouncerManager> statusBarHideIconsForBouncerManagerProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<StatusBar> statusBarProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<VibratorHelper> vibratorHelperProvider;
    public final Provider<Optional<Vibrator>> vibratorOptionalProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public StatusBarCommandQueueCallbacks_Factory(Provider<StatusBar> provider, Provider<Context> provider2, Provider<Resources> provider3, Provider<ShadeController> provider4, Provider<CommandQueue> provider5, Provider<NotificationPanelViewController> provider6, Provider<Optional<LegacySplitScreen>> provider7, Provider<RemoteInputQuickSettingsDisabler> provider8, Provider<MetricsLogger> provider9, Provider<KeyguardUpdateMonitor> provider10, Provider<KeyguardStateController> provider11, Provider<HeadsUpManager> provider12, Provider<WakefulnessLifecycle> provider13, Provider<DeviceProvisionedController> provider14, Provider<StatusBarKeyguardViewManager> provider15, Provider<AssistManager> provider16, Provider<DozeServiceHost> provider17, Provider<SysuiStatusBarStateController> provider18, Provider<NotificationShadeWindowView> provider19, Provider<NotificationStackScrollLayoutController> provider20, Provider<StatusBarHideIconsForBouncerManager> provider21, Provider<PowerManager> provider22, Provider<VibratorHelper> provider23, Provider<Optional<Vibrator>> provider24, Provider<LightBarController> provider25, Provider<DisableFlagsLogger> provider26, Provider<Integer> provider27) {
        this.statusBarProvider = provider;
        this.contextProvider = provider2;
        this.resourcesProvider = provider3;
        this.shadeControllerProvider = provider4;
        this.commandQueueProvider = provider5;
        this.notificationPanelViewControllerProvider = provider6;
        this.splitScreenOptionalProvider = provider7;
        this.remoteInputQuickSettingsDisablerProvider = provider8;
        this.metricsLoggerProvider = provider9;
        this.keyguardUpdateMonitorProvider = provider10;
        this.keyguardStateControllerProvider = provider11;
        this.headsUpManagerProvider = provider12;
        this.wakefulnessLifecycleProvider = provider13;
        this.deviceProvisionedControllerProvider = provider14;
        this.statusBarKeyguardViewManagerProvider = provider15;
        this.assistManagerProvider = provider16;
        this.dozeServiceHostProvider = provider17;
        this.statusBarStateControllerProvider = provider18;
        this.notificationShadeWindowViewProvider = provider19;
        this.notificationStackScrollLayoutControllerProvider = provider20;
        this.statusBarHideIconsForBouncerManagerProvider = provider21;
        this.powerManagerProvider = provider22;
        this.vibratorHelperProvider = provider23;
        this.vibratorOptionalProvider = provider24;
        this.lightBarControllerProvider = provider25;
        this.disableFlagsLoggerProvider = provider26;
        this.displayIdProvider = provider27;
    }

    public static StatusBarCommandQueueCallbacks_Factory create(Provider<StatusBar> provider, Provider<Context> provider2, Provider<Resources> provider3, Provider<ShadeController> provider4, Provider<CommandQueue> provider5, Provider<NotificationPanelViewController> provider6, Provider<Optional<LegacySplitScreen>> provider7, Provider<RemoteInputQuickSettingsDisabler> provider8, Provider<MetricsLogger> provider9, Provider<KeyguardUpdateMonitor> provider10, Provider<KeyguardStateController> provider11, Provider<HeadsUpManager> provider12, Provider<WakefulnessLifecycle> provider13, Provider<DeviceProvisionedController> provider14, Provider<StatusBarKeyguardViewManager> provider15, Provider<AssistManager> provider16, Provider<DozeServiceHost> provider17, Provider<SysuiStatusBarStateController> provider18, Provider<NotificationShadeWindowView> provider19, Provider<NotificationStackScrollLayoutController> provider20, Provider<StatusBarHideIconsForBouncerManager> provider21, Provider<PowerManager> provider22, Provider<VibratorHelper> provider23, Provider<Optional<Vibrator>> provider24, Provider<LightBarController> provider25, Provider<DisableFlagsLogger> provider26, Provider<Integer> provider27) {
        return new StatusBarCommandQueueCallbacks_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27);
    }

    public final Object get() {
        NotificationShadeWindowView notificationShadeWindowView = this.notificationShadeWindowViewProvider.get();
        return new StatusBarCommandQueueCallbacks(this.statusBarProvider.get(), this.contextProvider.get(), this.resourcesProvider.get(), this.shadeControllerProvider.get(), this.commandQueueProvider.get(), this.notificationPanelViewControllerProvider.get(), this.splitScreenOptionalProvider.get(), this.remoteInputQuickSettingsDisablerProvider.get(), this.metricsLoggerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.keyguardStateControllerProvider.get(), this.headsUpManagerProvider.get(), this.wakefulnessLifecycleProvider.get(), this.deviceProvisionedControllerProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.assistManagerProvider.get(), this.dozeServiceHostProvider.get(), this.statusBarStateControllerProvider.get(), this.notificationStackScrollLayoutControllerProvider.get(), this.statusBarHideIconsForBouncerManagerProvider.get(), this.powerManagerProvider.get(), this.vibratorHelperProvider.get(), this.vibratorOptionalProvider.get(), this.lightBarControllerProvider.get(), this.disableFlagsLoggerProvider.get(), this.displayIdProvider.get().intValue());
    }
}
