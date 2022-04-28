package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.SettingObserver;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.settings.SecureSettings;

/* renamed from: com.android.systemui.qs.tiles.RotationLockTile */
public class RotationLockTile extends QSTileImpl<QSTile.BooleanState> implements BatteryController.BatteryStateChangeCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BatteryController mBatteryController;
    public final C10512 mCallback;
    public final RotationLockController mController;
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(17302821);
    public final SensorPrivacyManager mPrivacyManager;
    public final RotationLockTile$$ExternalSyntheticLambda0 mSensorPrivacyChangedListener;
    public final C10501 mSetting;

    public final int getMetricsCategory() {
        return 123;
    }

    public final void onPowerSaveChanged(boolean z) {
        refreshState((Object) null);
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.AUTO_ROTATE_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return ((QSTile.BooleanState) this.mState).label;
    }

    public final void handleClick(View view) {
        boolean z = !((QSTile.BooleanState) this.mState).value;
        this.mController.setRotationLocked(!z);
        refreshState(Boolean.valueOf(z));
    }

    public final void handleInitialize() {
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isRotationLocked = this.mController.isRotationLocked();
        boolean isPowerSave = this.mBatteryController.isPowerSave();
        int i = 2;
        boolean isSensorPrivacyEnabled = this.mPrivacyManager.isSensorPrivacyEnabled(2);
        boolean z = false;
        if (!isPowerSave && !isSensorPrivacyEnabled) {
            PackageManager packageManager = this.mContext.getPackageManager();
            String rotationResolverPackageName = packageManager.getRotationResolverPackageName();
            if ((rotationResolverPackageName != null && packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0) && this.mController.isCameraRotationEnabled()) {
                z = true;
            }
        }
        booleanState.value = !isRotationLocked;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_rotation_unlocked_label);
        booleanState.icon = this.mIcon;
        booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_rotation);
        if (isRotationLocked || !z) {
            booleanState.secondaryLabel = "";
        } else {
            booleanState.secondaryLabel = this.mContext.getResources().getString(C1777R.string.rotation_lock_camera_rotation_on);
        }
        booleanState.stateDescription = booleanState.secondaryLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (!booleanState.value) {
            i = 1;
        }
        booleanState.state = i;
    }

    public final void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
        handleRefreshState((Object) null);
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public RotationLockTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        C10512 r2 = new RotationLockController.RotationLockControllerCallback() {
            public final void onRotationLockStateChanged(boolean z) {
                RotationLockTile.this.refreshState(Boolean.valueOf(z));
            }
        };
        this.mCallback = r2;
        this.mSensorPrivacyChangedListener = new RotationLockTile$$ExternalSyntheticLambda0(this);
        this.mController = rotationLockController;
        rotationLockController.observe((LifecycleOwner) this, r2);
        this.mPrivacyManager = sensorPrivacyManager;
        this.mBatteryController = batteryController;
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, qSHost.getUserContext().getUserId()) {
            public final void handleValueChanged(int i, boolean z) {
                RotationLockTile.this.handleRefreshState((Object) null);
            }
        };
        batteryController.observe((Lifecycle) this.mLifecycle, this);
    }

    public final void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
    }
}
