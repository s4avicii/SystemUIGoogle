package com.android.keyguard;

import android.app.ActivityTaskManager;
import android.os.PowerManager;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class EmergencyButtonController_Factory_Factory implements Factory<EmergencyButtonController.Factory> {
    public final Provider<ActivityTaskManager> activityTaskManagerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<TelecomManager> telecomManagerProvider;
    public final Provider<TelephonyManager> telephonyManagerProvider;

    public static EmergencyButtonController_Factory_Factory create(Provider<ConfigurationController> provider, Provider<KeyguardUpdateMonitor> provider2, Provider<TelephonyManager> provider3, Provider<PowerManager> provider4, Provider<ActivityTaskManager> provider5, Provider<ShadeController> provider6, Provider<TelecomManager> provider7, Provider<MetricsLogger> provider8) {
        return new EmergencyButtonController_Factory_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public final Object get() {
        return new EmergencyButtonController.Factory(this.configurationControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.telephonyManagerProvider.get(), this.powerManagerProvider.get(), this.activityTaskManagerProvider.get(), this.shadeControllerProvider.get(), this.telecomManagerProvider.get(), this.metricsLoggerProvider.get());
    }

    public EmergencyButtonController_Factory_Factory(Provider<ConfigurationController> provider, Provider<KeyguardUpdateMonitor> provider2, Provider<TelephonyManager> provider3, Provider<PowerManager> provider4, Provider<ActivityTaskManager> provider5, Provider<ShadeController> provider6, Provider<TelecomManager> provider7, Provider<MetricsLogger> provider8) {
        this.configurationControllerProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
        this.telephonyManagerProvider = provider3;
        this.powerManagerProvider = provider4;
        this.activityTaskManagerProvider = provider5;
        this.shadeControllerProvider = provider6;
        this.telecomManagerProvider = provider7;
        this.metricsLoggerProvider = provider8;
    }
}
