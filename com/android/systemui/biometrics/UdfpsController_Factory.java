package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class UdfpsController_Factory implements Factory<UdfpsController> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<ActivityLaunchAnimator> activityLaunchAnimatorProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<SystemUIDialogManager> dialogManagerProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Execution> executionProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<DelayableExecutor> fgExecutorProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<Optional<UdfpsHbmProvider>> hbmProvider;
    public final Provider<LayoutInflater> inflaterProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<PanelExpansionStateManager> panelExpansionStateManagerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
    public final Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
    public final Provider<VibratorHelper> vibratorProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public UdfpsController_Factory(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<PanelExpansionStateManager> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FalsingManager> provider12, Provider<PowerManager> provider13, Provider<AccessibilityManager> provider14, Provider<LockscreenShadeTransitionController> provider15, Provider<ScreenLifecycle> provider16, Provider<VibratorHelper> provider17, Provider<UdfpsHapticsSimulator> provider18, Provider<Optional<UdfpsHbmProvider>> provider19, Provider<KeyguardStateController> provider20, Provider<KeyguardBypassController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26, Provider<SystemUIDialogManager> provider27, Provider<LatencyTracker> provider28, Provider<ActivityLaunchAnimator> provider29) {
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.inflaterProvider = provider3;
        this.fingerprintManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.fgExecutorProvider = provider7;
        this.panelExpansionStateManagerProvider = provider8;
        this.statusBarKeyguardViewManagerProvider = provider9;
        this.dumpManagerProvider = provider10;
        this.keyguardUpdateMonitorProvider = provider11;
        this.falsingManagerProvider = provider12;
        this.powerManagerProvider = provider13;
        this.accessibilityManagerProvider = provider14;
        this.lockscreenShadeTransitionControllerProvider = provider15;
        this.screenLifecycleProvider = provider16;
        this.vibratorProvider = provider17;
        this.udfpsHapticsSimulatorProvider = provider18;
        this.hbmProvider = provider19;
        this.keyguardStateControllerProvider = provider20;
        this.keyguardBypassControllerProvider = provider21;
        this.displayManagerProvider = provider22;
        this.mainHandlerProvider = provider23;
        this.configurationControllerProvider = provider24;
        this.systemClockProvider = provider25;
        this.unlockedScreenOffAnimationControllerProvider = provider26;
        this.dialogManagerProvider = provider27;
        this.latencyTrackerProvider = provider28;
        this.activityLaunchAnimatorProvider = provider29;
    }

    public static UdfpsController_Factory create(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<PanelExpansionStateManager> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FalsingManager> provider12, Provider<PowerManager> provider13, Provider<AccessibilityManager> provider14, Provider<LockscreenShadeTransitionController> provider15, Provider<ScreenLifecycle> provider16, Provider<VibratorHelper> provider17, Provider<UdfpsHapticsSimulator> provider18, Provider<Optional<UdfpsHbmProvider>> provider19, Provider<KeyguardStateController> provider20, Provider<KeyguardBypassController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26, Provider<SystemUIDialogManager> provider27, Provider<LatencyTracker> provider28, Provider<ActivityLaunchAnimator> provider29) {
        return new UdfpsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29);
    }

    public final Object get() {
        return new UdfpsController(this.contextProvider.get(), this.executionProvider.get(), this.inflaterProvider.get(), this.fingerprintManagerProvider.get(), this.windowManagerProvider.get(), this.statusBarStateControllerProvider.get(), this.fgExecutorProvider.get(), this.panelExpansionStateManagerProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.dumpManagerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.falsingManagerProvider.get(), this.powerManagerProvider.get(), this.accessibilityManagerProvider.get(), this.lockscreenShadeTransitionControllerProvider.get(), this.screenLifecycleProvider.get(), this.vibratorProvider.get(), this.udfpsHapticsSimulatorProvider.get(), this.hbmProvider.get(), this.keyguardStateControllerProvider.get(), this.keyguardBypassControllerProvider.get(), this.displayManagerProvider.get(), this.mainHandlerProvider.get(), this.configurationControllerProvider.get(), this.systemClockProvider.get(), this.unlockedScreenOffAnimationControllerProvider.get(), this.dialogManagerProvider.get(), this.latencyTrackerProvider.get(), this.activityLaunchAnimatorProvider.get());
    }
}
