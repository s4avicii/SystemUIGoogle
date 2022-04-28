package com.android.keyguard;

import android.app.ActivityTaskManager;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda4;
import java.util.Objects;

public final class EmergencyButtonController extends ViewController<EmergencyButton> {
    public final ActivityTaskManager mActivityTaskManager;
    public final ConfigurationController mConfigurationController;
    public final C04842 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            EmergencyButtonController.this.updateEmergencyCallButton();
        }
    };
    public EmergencyButtonCallback mEmergencyButtonCallback;
    public final C04831 mInfoCallback = new KeyguardUpdateMonitorCallback() {
        public final void onPhoneStateChanged() {
            EmergencyButtonController.this.updateEmergencyCallButton();
        }

        public final void onSimStateChanged(int i, int i2, int i3) {
            EmergencyButtonController.this.updateEmergencyCallButton();
        }
    };
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final MetricsLogger mMetricsLogger;
    public final PowerManager mPowerManager;
    public ShadeController mShadeController;
    public final TelecomManager mTelecomManager;
    public final TelephonyManager mTelephonyManager;

    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    public static class Factory {
        public final ActivityTaskManager mActivityTaskManager;
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final MetricsLogger mMetricsLogger;
        public final PowerManager mPowerManager;
        public ShadeController mShadeController;
        public final TelecomManager mTelecomManager;
        public final TelephonyManager mTelephonyManager;

        public Factory(ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
            this.mConfigurationController = configurationController;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mTelephonyManager = telephonyManager;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
        }
    }

    public final void onInit() {
        DejankUtils.whitelistIpcs((Runnable) new DozeUi$$ExternalSyntheticLambda1(this, 2));
    }

    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        ((EmergencyButton) this.mView).setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda4(this, 1));
    }

    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public final void updateEmergencyCallButton() {
        boolean z;
        int i;
        T t = this.mView;
        if (t != null) {
            EmergencyButton emergencyButton = (EmergencyButton) t;
            TelecomManager telecomManager = this.mTelecomManager;
            boolean z2 = true;
            if (telecomManager == null || !telecomManager.isInCall()) {
                z = false;
            } else {
                z = true;
            }
            boolean isVoiceCapable = this.mTelephonyManager.isVoiceCapable();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            boolean isSimPinSecure = keyguardUpdateMonitor.isSimPinSecure();
            Objects.requireNonNull(emergencyButton);
            if (!isVoiceCapable) {
                z2 = false;
            } else if (!z) {
                if (isSimPinSecure) {
                    z2 = emergencyButton.mEnableEmergencyCallWhileSimLocked;
                } else {
                    z2 = emergencyButton.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser());
                }
            }
            if (z2) {
                emergencyButton.setVisibility(0);
                if (z) {
                    i = 17040616;
                } else {
                    i = 17040589;
                }
                emergencyButton.setText(i);
                return;
            }
            emergencyButton.setVisibility(8);
        }
    }

    public EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
        super(emergencyButton);
        this.mConfigurationController = configurationController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
    }
}
