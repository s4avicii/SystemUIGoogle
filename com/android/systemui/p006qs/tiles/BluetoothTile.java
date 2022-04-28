package com.android.systemui.p006qs.tiles;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BluetoothController;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.BluetoothTile */
public final class BluetoothTile extends QSTileImpl<QSTile.BooleanState> {
    public final C10381 mCallback;
    public final BluetoothController mController;

    public final int getMetricsCategory() {
        return 113;
    }

    /* renamed from: com.android.systemui.qs.tiles.BluetoothTile$BluetoothConnectedTileIcon */
    public class BluetoothConnectedTileIcon extends QSTile.Icon {
        public final Drawable getDrawable(Context context) {
            return context.getDrawable(C1777R.C1778drawable.ic_bluetooth_connected);
        }
    }

    static {
        new Intent("android.settings.BLUETOOTH_SETTINGS");
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.BLUETOOTH_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_bluetooth_label);
    }

    public final void handleClick(View view) {
        Object obj;
        boolean z = ((QSTile.BooleanState) this.mState).value;
        if (z) {
            obj = null;
        } else {
            obj = QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        }
        refreshState(obj);
        this.mController.setBluetoothEnabled(!z);
    }

    public final void handleSecondaryClick(View view) {
        if (!this.mController.canConfigBluetooth()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
        } else if (!((QSTile.BooleanState) this.mState).value) {
            this.mController.setBluetoothEnabled(true);
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        boolean z4;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING) {
            z = true;
        } else {
            z = false;
        }
        if (z || this.mController.isBluetoothEnabled()) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean isBluetoothConnected = this.mController.isBluetoothConnected();
        boolean isBluetoothConnecting = this.mController.isBluetoothConnecting();
        if (z || isBluetoothConnecting || this.mController.getBluetoothState() == 11) {
            z3 = true;
        } else {
            z3 = false;
        }
        booleanState.isTransient = z3;
        booleanState.dualTarget = true;
        booleanState.value = z2;
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        booleanState.slash.isSlashed = !z2;
        booleanState.label = this.mContext.getString(C1777R.string.quick_settings_bluetooth_label);
        boolean z5 = booleanState.isTransient;
        if (isBluetoothConnecting) {
            str = this.mContext.getString(C1777R.string.quick_settings_connecting);
        } else if (z5) {
            str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_transient);
        } else {
            ArrayList connectedDevices = this.mController.getConnectedDevices();
            if (z2 && isBluetoothConnected && !connectedDevices.isEmpty()) {
                if (connectedDevices.size() > 1) {
                    str = this.mContext.getResources().getQuantityString(C1777R.plurals.quick_settings_hotspot_secondary_label_num_devices, connectedDevices.size(), new Object[]{Integer.valueOf(connectedDevices.size())});
                } else {
                    CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) connectedDevices.get(0);
                    Objects.requireNonNull(cachedBluetoothDevice);
                    int batteryLevel = cachedBluetoothDevice.mDevice.getBatteryLevel();
                    if (batteryLevel > -1) {
                        str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_battery_level, new Object[]{NumberFormat.getPercentInstance().format(((double) batteryLevel) / 100.0d)});
                    } else {
                        BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
                        if (bluetoothClass != null) {
                            if (cachedBluetoothDevice.mHiSyncId != 0) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (z4) {
                                str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_hearing_aids);
                            } else if (bluetoothClass.doesClassMatch(1)) {
                                str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_audio);
                            } else if (bluetoothClass.doesClassMatch(0)) {
                                str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_headset);
                            } else if (bluetoothClass.doesClassMatch(3)) {
                                str = this.mContext.getString(C1777R.string.quick_settings_bluetooth_secondary_label_input);
                            }
                        }
                    }
                }
            }
            str = null;
        }
        booleanState.secondaryLabel = TextUtils.emptyIfNull(str);
        booleanState.contentDescription = this.mContext.getString(C1777R.string.accessibility_quick_settings_bluetooth);
        booleanState.stateDescription = "";
        if (z2) {
            if (isBluetoothConnected) {
                booleanState.icon = new BluetoothConnectedTileIcon();
                if (!TextUtils.isEmpty(this.mController.getConnectedDeviceName())) {
                    booleanState.label = this.mController.getConnectedDeviceName();
                }
                booleanState.stateDescription = this.mContext.getString(C1777R.string.accessibility_bluetooth_name, new Object[]{booleanState.label}) + ", " + booleanState.secondaryLabel;
            } else if (booleanState.isTransient) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(17302327);
                booleanState.stateDescription = booleanState.secondaryLabel;
            } else {
                booleanState.icon = QSTileImpl.ResourceIcon.get(17302823);
                booleanState.stateDescription = this.mContext.getString(C1777R.string.accessibility_not_connected);
            }
            booleanState.state = 2;
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(17302823);
            booleanState.state = 1;
        }
        booleanState.dualLabelContentDescription = this.mContext.getResources().getString(C1777R.string.accessibility_quick_settings_open_settings, new Object[]{getTileLabel()});
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    public final boolean isAvailable() {
        return this.mController.isBluetoothSupported();
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public BluetoothTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BluetoothController bluetoothController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        C10381 r1 = new BluetoothController.Callback() {
            public final void onBluetoothDevicesChanged() {
                BluetoothTile bluetoothTile = BluetoothTile.this;
                Objects.requireNonNull(bluetoothTile);
                bluetoothTile.refreshState((Object) null);
            }

            public final void onBluetoothStateChange() {
                BluetoothTile bluetoothTile = BluetoothTile.this;
                Objects.requireNonNull(bluetoothTile);
                bluetoothTile.refreshState((Object) null);
            }
        };
        this.mCallback = r1;
        this.mController = bluetoothController;
        bluetoothController.observe((Lifecycle) this.mLifecycle, r1);
    }
}
