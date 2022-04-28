package com.google.android.systemui.p016qs.tiles;

import android.content.Intent;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.Looper;
import android.os.RemoteException;
import android.os.Temperature;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Prefs;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.theme.ThemeOverlayApplier;

/* renamed from: com.google.android.systemui.qs.tiles.ReverseChargingTile */
public final class ReverseChargingTile extends QSTileImpl<QSTile.BooleanState> implements BatteryController.BatteryStateChangeCallback {
    public static final boolean DEBUG = Log.isLoggable("ReverseChargingTile", 3);
    public final BatteryController mBatteryController;
    public int mBatteryLevel;
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_reverse_charging);
    public boolean mListening;
    public boolean mOverHeat;
    public boolean mPowerSave;
    public boolean mReverse;
    public final C23032 mSettingsObserver = new ContentObserver(this.mHandler) {
        public final void onChange(boolean z) {
            ReverseChargingTile.this.updateThresholdLevel();
        }
    };
    public final C23021 mThermalEventListener = new IThermalEventListener.Stub() {
        public final void notifyThrottling(Temperature temperature) {
            boolean z;
            int status = temperature.getStatus();
            ReverseChargingTile reverseChargingTile = ReverseChargingTile.this;
            if (status >= 5) {
                z = true;
            } else {
                z = false;
            }
            reverseChargingTile.mOverHeat = z;
            if (ReverseChargingTile.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("notifyThrottling(): status=", status, "ReverseChargingTile");
            }
        }
    };
    public final IThermalService mThermalService;
    public int mThresholdLevel;

    public final int getMetricsCategory() {
        return 0;
    }

    public final Intent getLongClickIntent() {
        Intent intent = new Intent("android.settings.REVERSE_CHARGING_SETTINGS");
        intent.setPackage(ThemeOverlayApplier.SETTINGS_PACKAGE);
        return intent;
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.reverse_charging_title);
    }

    public final void handleClick(View view) {
        if (((QSTile.BooleanState) this.mState).state != 0) {
            this.mReverse = !this.mReverse;
            if (DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleClick(): rtx=");
                m.append(this.mReverse ? 1 : 0);
                m.append(",this=");
                m.append(this);
                Log.d("ReverseChargingTile", m.toString());
            }
            this.mBatteryController.setReverseState(this.mReverse);
            if (!Prefs.getBoolean(this.mHost.getUserContext(), "HasSeenReverseBottomSheet")) {
                Intent intent = new Intent("android.settings.REVERSE_CHARGING_BOTTOM_SHEET");
                intent.setPackage(ThemeOverlayApplier.SETTINGS_PACKAGE);
                this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
                Prefs.putBoolean(this.mHost.getUserContext(), "HasSeenReverseBottomSheet", true);
            }
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        boolean z;
        String str;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean isWirelessCharging = this.mBatteryController.isWirelessCharging();
        int i2 = 1;
        if (this.mBatteryLevel <= this.mThresholdLevel) {
            i = 1;
        } else {
            i = 0;
        }
        boolean z2 = this.mOverHeat;
        if (z2 || this.mPowerSave || isWirelessCharging || i != 0 || !this.mReverse) {
            z = false;
        } else {
            z = true;
        }
        booleanState.value = z;
        if (z2 || this.mPowerSave || isWirelessCharging || i != 0) {
            i2 = 0;
        } else if (this.mReverse) {
            i2 = 2;
        }
        booleanState.state = i2;
        booleanState.icon = this.mIcon;
        CharSequence tileLabel = getTileLabel();
        booleanState.label = tileLabel;
        booleanState.contentDescription = tileLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (this.mOverHeat) {
            str = this.mContext.getString(C1777R.string.too_hot_label);
        } else if (this.mPowerSave) {
            str = this.mContext.getString(C1777R.string.quick_settings_dark_mode_secondary_label_battery_saver);
        } else if (isWirelessCharging) {
            str = this.mContext.getString(C1777R.string.wireless_charging_label);
        } else if (i != 0) {
            str = this.mContext.getString(C1777R.string.low_battery_label);
        } else {
            str = null;
        }
        booleanState.secondaryLabel = str;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleUpdateState(): ps=");
            m.append(this.mPowerSave ? 1 : 0);
            m.append(",wlc=");
            m.append(isWirelessCharging ? 1 : 0);
            m.append(",low=");
            m.append(i);
            m.append(",over=");
            m.append(this.mOverHeat ? 1 : 0);
            m.append(",rtx=");
            m.append(this.mReverse ? 1 : 0);
            m.append(",this=");
            m.append(this);
            Log.d("ReverseChargingTile", m.toString());
        }
    }

    public final boolean isAvailable() {
        return this.mBatteryController.isReverseSupported();
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        this.mBatteryLevel = i;
        this.mReverse = this.mBatteryController.isReverseOn();
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onBatteryLevelChanged(): rtx=");
            m.append(this.mReverse ? 1 : 0);
            m.append(",level=");
            m.append(this.mBatteryLevel);
            m.append(",threshold=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, this.mThresholdLevel, "ReverseChargingTile");
        }
        refreshState((Object) null);
    }

    public final void onPowerSaveChanged(boolean z) {
        this.mPowerSave = z;
        refreshState((Object) null);
    }

    public final void onReverseChanged(boolean z, int i, String str) {
        if (DEBUG) {
            StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("onReverseChanged(): rtx=", z ? 1 : 0, ",level=", i, ",name=");
            m.append(str);
            m.append(",this=");
            m.append(this);
            Log.d("ReverseChargingTile", m.toString());
        }
        this.mReverse = z;
        refreshState((Object) null);
    }

    public final void updateThresholdLevel() {
        this.mThresholdLevel = Settings.Global.getInt(this.mContext.getContentResolver(), "advanced_battery_usage_amount", 2) * 5;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("updateThresholdLevel(): rtx=");
            m.append(this.mReverse ? 1 : 0);
            m.append(",level=");
            m.append(this.mBatteryLevel);
            m.append(",threshold=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, this.mThresholdLevel, "ReverseChargingTile");
        }
    }

    public ReverseChargingTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BatteryController batteryController, IThermalService iThermalService) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mBatteryController = batteryController;
        batteryController.observe((Lifecycle) this.mLifecycle, this);
        this.mThermalService = iThermalService;
    }

    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening != z) {
            this.mListening = z;
            if (z) {
                updateThresholdLevel();
                boolean z2 = false;
                this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("advanced_battery_usage_amount"), false, this.mSettingsObserver);
                try {
                    this.mThermalService.registerThermalEventListenerWithType(this.mThermalEventListener, 3);
                } catch (RemoteException e) {
                    Log.e("ReverseChargingTile", "Could not register thermal event listener, exception: " + e);
                }
                try {
                    Temperature[] currentTemperaturesWithType = this.mThermalService.getCurrentTemperaturesWithType(3);
                    int length = currentTemperaturesWithType.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Temperature temperature = currentTemperaturesWithType[i];
                        if (temperature.getStatus() >= 5) {
                            Log.w("ReverseChargingTile", "isOverHeat(): current skin status = " + temperature.getStatus() + ", temperature = " + temperature.getValue());
                            z2 = true;
                            break;
                        }
                        i++;
                    }
                } catch (RemoteException e2) {
                    Log.w("ReverseChargingTile", "isOverHeat(): " + e2);
                }
                this.mOverHeat = z2;
            } else {
                this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
                try {
                    this.mThermalService.unregisterThermalEventListener(this.mThermalEventListener);
                } catch (RemoteException e3) {
                    Log.e("ReverseChargingTile", "Could not unregister thermal event listener, exception: " + e3);
                }
            }
            if (DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleSetListening(): rtx=");
                m.append(this.mReverse ? 1 : 0);
                m.append(",level=");
                m.append(this.mBatteryLevel);
                m.append(",threshold=");
                m.append(this.mThresholdLevel);
                m.append(",listening=");
                m.append(z);
                Log.d("ReverseChargingTile", m.toString());
            }
        }
    }

    public final void handleDestroy() {
        super.handleDestroy();
    }
}
