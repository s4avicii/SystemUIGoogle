package com.android.systemui.p006qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.PipAnimationController$$ExternalSyntheticLambda0;
import com.android.systemui.DejankUtils;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* renamed from: com.android.systemui.qs.tiles.MicrophoneToggleTile */
public final class MicrophoneToggleTile extends SensorPrivacyToggleTile {
    public static final /* synthetic */ int $r8$clinit = 0;

    public MicrophoneToggleTile(QSHost qSHost, Looper looper, Handler handler, MetricsLogger metricsLogger, FalsingManager falsingManager, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, individualSensorPrivacyController, keyguardStateController);
    }

    public final int getIconRes(boolean z) {
        return z ? 17302769 : 17302768;
    }

    public final String getRestriction() {
        return "disallow_microphone_toggle";
    }

    public final int getSensorId() {
        return 1;
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_mic_label);
    }

    public final boolean isAvailable() {
        if (!this.mSensorPrivacyController.supportsSensorToggle(1) || !((Boolean) DejankUtils.whitelistIpcs(PipAnimationController$$ExternalSyntheticLambda0.INSTANCE$1)).booleanValue()) {
            return false;
        }
        return true;
    }
}
