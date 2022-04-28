package com.android.systemui.statusbar.phone;

import android.os.PowerManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DozeServiceHost_Factory implements Factory<DozeServiceHost> {
    public final Provider<AssistManager> assistManagerLazyProvider;
    public final Provider<AuthController> authControllerProvider;
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<BiometricUnlockController> biometricUnlockControllerLazyProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<DozeScrimController> dozeScrimControllerProvider;
    public final Provider<HeadsUpManagerPhone> headsUpManagerPhoneProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<KeyguardViewMediator> keyguardViewMediatorProvider;
    public final Provider<NotificationIconAreaController> notificationIconAreaControllerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    public final Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
    public final Provider<ScrimController> scrimControllerProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public DozeServiceHost_Factory(Provider<DozeLog> provider, Provider<PowerManager> provider2, Provider<WakefulnessLifecycle> provider3, Provider<SysuiStatusBarStateController> provider4, Provider<DeviceProvisionedController> provider5, Provider<HeadsUpManagerPhone> provider6, Provider<BatteryController> provider7, Provider<ScrimController> provider8, Provider<BiometricUnlockController> provider9, Provider<KeyguardViewMediator> provider10, Provider<AssistManager> provider11, Provider<DozeScrimController> provider12, Provider<KeyguardUpdateMonitor> provider13, Provider<PulseExpansionHandler> provider14, Provider<NotificationShadeWindowController> provider15, Provider<NotificationWakeUpCoordinator> provider16, Provider<AuthController> provider17, Provider<NotificationIconAreaController> provider18) {
        this.dozeLogProvider = provider;
        this.powerManagerProvider = provider2;
        this.wakefulnessLifecycleProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
        this.headsUpManagerPhoneProvider = provider6;
        this.batteryControllerProvider = provider7;
        this.scrimControllerProvider = provider8;
        this.biometricUnlockControllerLazyProvider = provider9;
        this.keyguardViewMediatorProvider = provider10;
        this.assistManagerLazyProvider = provider11;
        this.dozeScrimControllerProvider = provider12;
        this.keyguardUpdateMonitorProvider = provider13;
        this.pulseExpansionHandlerProvider = provider14;
        this.notificationShadeWindowControllerProvider = provider15;
        this.notificationWakeUpCoordinatorProvider = provider16;
        this.authControllerProvider = provider17;
        this.notificationIconAreaControllerProvider = provider18;
    }

    public static DozeServiceHost_Factory create(Provider<DozeLog> provider, Provider<PowerManager> provider2, Provider<WakefulnessLifecycle> provider3, Provider<SysuiStatusBarStateController> provider4, Provider<DeviceProvisionedController> provider5, Provider<HeadsUpManagerPhone> provider6, Provider<BatteryController> provider7, Provider<ScrimController> provider8, Provider<BiometricUnlockController> provider9, Provider<KeyguardViewMediator> provider10, Provider<AssistManager> provider11, Provider<DozeScrimController> provider12, Provider<KeyguardUpdateMonitor> provider13, Provider<PulseExpansionHandler> provider14, Provider<NotificationShadeWindowController> provider15, Provider<NotificationWakeUpCoordinator> provider16, Provider<AuthController> provider17, Provider<NotificationIconAreaController> provider18) {
        return new DozeServiceHost_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18);
    }

    public final Object get() {
        Lazy<BiometricUnlockController> lazy = DoubleCheck.lazy(this.biometricUnlockControllerLazyProvider);
        KeyguardViewMediator keyguardViewMediator = this.keyguardViewMediatorProvider.get();
        return new DozeServiceHost(this.dozeLogProvider.get(), this.powerManagerProvider.get(), this.wakefulnessLifecycleProvider.get(), this.statusBarStateControllerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.headsUpManagerPhoneProvider.get(), this.batteryControllerProvider.get(), this.scrimControllerProvider.get(), lazy, DoubleCheck.lazy(this.assistManagerLazyProvider), this.dozeScrimControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.pulseExpansionHandlerProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.notificationWakeUpCoordinatorProvider.get(), this.authControllerProvider.get(), this.notificationIconAreaControllerProvider.get());
    }
}
