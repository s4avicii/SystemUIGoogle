package com.android.systemui.statusbar.phone;

import android.app.AlarmManager;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.DelayedWakeLock_Builder_Factory;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ScrimController_Factory implements Factory<ScrimController> {
    public final Provider<AlarmManager> alarmManagerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<DelayedWakeLock.Builder> delayedWakeLockBuilderProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LightBarController> lightBarControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PanelExpansionStateManager> panelExpansionStateManagerProvider;
    public final Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;

    public static ScrimController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, DelayedWakeLock_Builder_Factory delayedWakeLock_Builder_Factory, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new ScrimController_Factory(provider, provider2, provider3, provider4, delayedWakeLock_Builder_Factory, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public final Object get() {
        return new ScrimController(this.lightBarControllerProvider.get(), this.dozeParametersProvider.get(), this.alarmManagerProvider.get(), this.keyguardStateControllerProvider.get(), this.delayedWakeLockBuilderProvider.get(), this.handlerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.dockManagerProvider.get(), this.configurationControllerProvider.get(), this.mainExecutorProvider.get(), this.screenOffAnimationControllerProvider.get(), this.panelExpansionStateManagerProvider.get());
    }

    public ScrimController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, DelayedWakeLock_Builder_Factory delayedWakeLock_Builder_Factory, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.lightBarControllerProvider = provider;
        this.dozeParametersProvider = provider2;
        this.alarmManagerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.delayedWakeLockBuilderProvider = delayedWakeLock_Builder_Factory;
        this.handlerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.dockManagerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.screenOffAnimationControllerProvider = provider10;
        this.panelExpansionStateManagerProvider = provider11;
    }
}
