package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1;
import com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda33;
import com.android.systemui.statusbar.policy.BatteryController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BatteryControllerImpl extends BroadcastReceiver implements BatteryController {
    public static final boolean DEBUG = Log.isLoggable("BatteryController", 3);
    public boolean mAodPowerSave;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ArrayList<BatteryController.BatteryStateChangeCallback> mChangeCallbacks = new ArrayList<>();
    public boolean mCharged;
    public boolean mCharging;
    public final Context mContext;
    public final DemoModeController mDemoModeController;
    public Estimate mEstimate;
    public final EnhancedEstimates mEstimates;
    public final ArrayList<BatteryController.EstimateFetchCompletion> mFetchCallbacks = new ArrayList<>();
    public boolean mFetchingEstimate = false;
    @VisibleForTesting
    public boolean mHasReceivedBattery = false;
    public int mLevel;
    public final Handler mMainHandler;
    public boolean mPluggedIn;
    public boolean mPluggedInWireless;
    public final PowerManager mPowerManager;
    public boolean mPowerSave;
    public boolean mStateUnknown = false;
    public boolean mTestMode = false;
    public boolean mWirelessCharging;

    public void addCallback(BatteryController.BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        if (this.mHasReceivedBattery) {
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            batteryStateChangeCallback.onPowerSaveChanged(this.mPowerSave);
            batteryStateChangeCallback.onBatteryUnknownStateChanged(this.mStateUnknown);
            batteryStateChangeCallback.onWirelessChargingChanged(this.mWirelessCharging);
        }
    }

    public final List<String> demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("battery");
        return arrayList;
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        DemoModeController demoModeController = this.mDemoModeController;
        Objects.requireNonNull(demoModeController);
        if (demoModeController.isInDemoMode) {
            String string = bundle.getString("level");
            String string2 = bundle.getString("plugged");
            String string3 = bundle.getString("powersave");
            String string4 = bundle.getString("present");
            if (string != null) {
                this.mLevel = Math.min(Math.max(Integer.parseInt(string), 0), 100);
            }
            if (string2 != null) {
                this.mPluggedIn = Boolean.parseBoolean(string2);
            }
            if (string3 != null) {
                this.mPowerSave = string3.equals("true");
                firePowerSaveChanged();
            }
            if (string4 != null) {
                this.mStateUnknown = !string4.equals("true");
                fireBatteryUnknownStateChanged();
            }
            fireBatteryLevelChanged();
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("BatteryController state:");
        printWriter.print("  mLevel=");
        printWriter.println(this.mLevel);
        printWriter.print("  mPluggedIn=");
        printWriter.println(this.mPluggedIn);
        printWriter.print("  mCharging=");
        printWriter.println(this.mCharging);
        printWriter.print("  mCharged=");
        printWriter.println(this.mCharged);
        printWriter.print("  mPowerSave=");
        printWriter.println(this.mPowerSave);
        printWriter.print("  mStateUnknown=");
        printWriter.println(this.mStateUnknown);
    }

    public final void fireBatteryLevelChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                this.mChangeCallbacks.get(i).onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            }
        }
    }

    public final void fireBatteryUnknownStateChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                this.mChangeCallbacks.get(i).onBatteryUnknownStateChanged(this.mStateUnknown);
            }
        }
    }

    public final void firePowerSaveChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                this.mChangeCallbacks.get(i).onPowerSaveChanged(this.mPowerSave);
            }
        }
    }

    public final void getEstimatedTimeRemainingString(BatteryMeterView$$ExternalSyntheticLambda0 batteryMeterView$$ExternalSyntheticLambda0) {
        synchronized (this.mFetchCallbacks) {
            this.mFetchCallbacks.add(batteryMeterView$$ExternalSyntheticLambda0);
        }
        if (!this.mFetchingEstimate) {
            this.mFetchingEstimate = true;
            this.mBgHandler.post(new ScreenDecorations$$ExternalSyntheticLambda1(this, 7));
        }
    }

    public final void onDemoModeStarted() {
        this.mBroadcastDispatcher.unregisterReceiver(this);
    }

    public final void registerReceiver$1() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("com.android.systemui.BATTERY_LEVEL_TEST");
        this.mBroadcastDispatcher.registerReceiver(this, intentFilter);
    }

    public final void removeCallback(Object obj) {
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = (BatteryController.BatteryStateChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }

    public final void setPowerSaveMode(boolean z) {
        BatterySaverUtils.setPowerSaveMode(this.mContext, z, true);
    }

    public final void updateEstimate() {
        Estimate estimate;
        boolean z;
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        if (Duration.between(Instant.ofEpochMilli(Settings.Global.getLong(context.getContentResolver(), "battery_estimates_last_update_time", -1)), Instant.now()).compareTo(Duration.ofMinutes(1)) > 0) {
            estimate = null;
        } else {
            long j = Settings.Global.getLong(contentResolver, "time_remaining_estimate_millis", -1);
            if (Settings.Global.getInt(contentResolver, "time_remaining_estimate_based_on_usage", 0) == 1) {
                z = true;
            } else {
                z = false;
            }
            estimate = new Estimate(j, z, Settings.Global.getLong(contentResolver, "average_time_to_discharge", -1));
        }
        this.mEstimate = estimate;
        if (estimate == null) {
            Estimate estimate2 = this.mEstimates.getEstimate();
            this.mEstimate = estimate2;
            if (estimate2 != null) {
                ContentResolver contentResolver2 = this.mContext.getContentResolver();
                Settings.Global.putLong(contentResolver2, "time_remaining_estimate_millis", estimate2.estimateMillis);
                Settings.Global.putInt(contentResolver2, "time_remaining_estimate_based_on_usage", estimate2.isBasedOnUsage ? 1 : 0);
                Settings.Global.putLong(contentResolver2, "average_time_to_discharge", estimate2.averageDischargeTime);
                Settings.Global.putLong(contentResolver2, "battery_estimates_last_update_time", System.currentTimeMillis());
            }
        }
    }

    public final void updatePowerSave() {
        String str;
        boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
        if (isPowerSaveMode != this.mPowerSave) {
            this.mPowerSave = isPowerSaveMode;
            this.mAodPowerSave = this.mPowerManager.getPowerSaveState(14).batterySaverEnabled;
            if (DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Power save is ");
                if (this.mPowerSave) {
                    str = "on";
                } else {
                    str = "off";
                }
                ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, "BatteryController");
            }
            firePowerSaveChanged();
        }
    }

    @VisibleForTesting
    public BatteryControllerImpl(Context context, EnhancedEstimates enhancedEstimates, PowerManager powerManager, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, Handler handler, Handler handler2) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mPowerManager = powerManager;
        this.mEstimates = enhancedEstimates;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDemoModeController = demoModeController;
    }

    public void init() {
        Intent registerReceiver;
        registerReceiver$1();
        if (!this.mHasReceivedBattery && (registerReceiver = this.mContext.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null && !this.mHasReceivedBattery) {
            onReceive(this.mContext, registerReceiver);
        }
        this.mDemoModeController.addCallback((DemoMode) this);
        updatePowerSave();
        updateEstimate();
    }

    public final void onDemoModeFinished() {
        registerReceiver$1();
        updatePowerSave();
    }

    public void onReceive(final Context context, Intent intent) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        String action = intent.getAction();
        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
            boolean z5 = false;
            if (!this.mTestMode || intent.getBooleanExtra("testmode", false)) {
                this.mHasReceivedBattery = true;
                this.mLevel = (int) ((((float) intent.getIntExtra("level", 0)) * 100.0f) / ((float) intent.getIntExtra("scale", 100)));
                if (intent.getIntExtra("plugged", 0) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                this.mPluggedIn = z;
                if (intent.getIntExtra("plugged", 0) == 4) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.mPluggedInWireless = z2;
                int intExtra = intent.getIntExtra("status", 1);
                if (intExtra == 5) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mCharged = z3;
                if (z3 || intExtra == 2) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                this.mCharging = z4;
                boolean z6 = this.mWirelessCharging;
                if (z4 && intent.getIntExtra("plugged", 0) == 4) {
                    z5 = true;
                }
                if (z6 != z5) {
                    this.mWirelessCharging = !this.mWirelessCharging;
                    synchronized (this.mChangeCallbacks) {
                        this.mChangeCallbacks.forEach(new StatusBar$$ExternalSyntheticLambda33(this, 2));
                    }
                }
                boolean z7 = !intent.getBooleanExtra("present", true);
                if (z7 != this.mStateUnknown) {
                    this.mStateUnknown = z7;
                    fireBatteryUnknownStateChanged();
                }
                fireBatteryLevelChanged();
            }
        } else if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
            updatePowerSave();
        } else if (action.equals("com.android.systemui.BATTERY_LEVEL_TEST")) {
            this.mTestMode = true;
            this.mMainHandler.post(new Runnable() {
                public int mCurrentLevel = 0;
                public int mIncrement = 1;
                public int mSavedLevel;
                public boolean mSavedPluggedIn;
                public Intent mTestIntent;

                {
                    this.mSavedLevel = BatteryControllerImpl.this.mLevel;
                    this.mSavedPluggedIn = BatteryControllerImpl.this.mPluggedIn;
                    this.mTestIntent = new Intent("android.intent.action.BATTERY_CHANGED");
                }

                public final void run() {
                    int i = this.mCurrentLevel;
                    int i2 = 0;
                    if (i < 0) {
                        BatteryControllerImpl.this.mTestMode = false;
                        this.mTestIntent.putExtra("level", this.mSavedLevel);
                        this.mTestIntent.putExtra("plugged", this.mSavedPluggedIn);
                        this.mTestIntent.putExtra("testmode", false);
                    } else {
                        this.mTestIntent.putExtra("level", i);
                        Intent intent = this.mTestIntent;
                        if (this.mIncrement > 0) {
                            i2 = 1;
                        }
                        intent.putExtra("plugged", i2);
                        this.mTestIntent.putExtra("testmode", true);
                    }
                    context.sendBroadcast(this.mTestIntent);
                    BatteryControllerImpl batteryControllerImpl = BatteryControllerImpl.this;
                    if (batteryControllerImpl.mTestMode) {
                        int i3 = this.mCurrentLevel;
                        int i4 = this.mIncrement;
                        int i5 = i3 + i4;
                        this.mCurrentLevel = i5;
                        if (i5 == 100) {
                            this.mIncrement = i4 * -1;
                        }
                        batteryControllerImpl.mMainHandler.postDelayed(this, 200);
                    }
                }
            });
        }
    }

    public final boolean isAodPowerSave() {
        return this.mAodPowerSave;
    }

    public final boolean isPluggedIn() {
        return this.mPluggedIn;
    }

    public final boolean isPluggedInWireless() {
        return this.mPluggedInWireless;
    }

    public final boolean isPowerSave() {
        return this.mPowerSave;
    }

    public final boolean isWirelessCharging() {
        return this.mWirelessCharging;
    }
}
