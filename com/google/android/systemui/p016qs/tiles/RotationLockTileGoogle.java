package com.google.android.systemui.p016qs.tiles;

import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tiles.RotationLockTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.settings.SecureSettings;

/* renamed from: com.google.android.systemui.qs.tiles.RotationLockTileGoogle */
/* compiled from: RotationLockTileGoogle.kt */
public final class RotationLockTileGoogle extends RotationLockTile {
    public final DevicePostureController mDevicePostureController;
    public final boolean mIsPerDeviceStateRotationLockEnabled;

    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        super.handleUpdateState(booleanState, obj);
        if (this.mIsPerDeviceStateRotationLockEnabled) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getResources().getStringArray(C1777R.array.tile_states_rotation)[booleanState.state]);
            sb.append(" / ");
            if (this.mDevicePostureController.getDevicePosture() == 1) {
                sb.append(this.mContext.getString(C1777R.string.quick_settings_rotation_posture_folded));
            } else {
                sb.append(this.mContext.getString(C1777R.string.quick_settings_rotation_posture_unfolded));
            }
            String sb2 = sb.toString();
            booleanState.secondaryLabel = sb2;
            booleanState.stateDescription = sb2;
        }
    }

    public RotationLockTileGoogle(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings, String[] strArr, DevicePostureController devicePostureController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, rotationLockController, sensorPrivacyManager, batteryController, secureSettings);
        boolean z;
        this.mDevicePostureController = devicePostureController;
        if (strArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsPerDeviceStateRotationLockEnabled = !z;
    }
}
