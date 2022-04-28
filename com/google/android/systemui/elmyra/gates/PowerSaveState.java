package com.google.android.systemui.elmyra.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

public final class PowerSaveState extends Gate {
    @GuardedBy({"mLock"})
    public boolean mBatterySaverEnabled;
    public BroadcastDispatcher mBroadcastDispatcher;
    @GuardedBy({"mLock"})
    public boolean mIsDeviceInteractive;
    public final Object mLock = new Object();
    public final PowerManager mPowerManager;
    public final C22571 mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            PowerSaveState powerSaveState = PowerSaveState.this;
            Objects.requireNonNull(powerSaveState);
            synchronized (powerSaveState.mLock) {
                powerSaveState.mBatterySaverEnabled = powerSaveState.mPowerManager.getPowerSaveState(13).batterySaverEnabled;
                powerSaveState.mIsDeviceInteractive = powerSaveState.mPowerManager.isInteractive();
            }
            PowerSaveState.this.notifyListener();
        }
    };

    public final boolean isBlocked() {
        boolean z;
        synchronized (this.mLock) {
            if (!this.mBatterySaverEnabled || this.mIsDeviceInteractive) {
                z = false;
            } else {
                z = true;
            }
        }
        return z;
    }

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
        synchronized (this.mLock) {
            this.mBatterySaverEnabled = this.mPowerManager.getPowerSaveState(13).batterySaverEnabled;
            this.mIsDeviceInteractive = this.mPowerManager.isInteractive();
        }
    }

    public final void onDeactivate() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
    }

    public PowerSaveState(Context context) {
        super(context);
        this.mPowerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
    }
}
