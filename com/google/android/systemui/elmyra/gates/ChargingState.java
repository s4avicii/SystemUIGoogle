package com.google.android.systemui.elmyra.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.p012wm.shell.C1777R;

public final class ChargingState extends TransientGate {
    public final C22481 mPowerReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            ChargingState.this.block();
        }
    };

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        this.mContext.registerReceiver(this.mPowerReceiver, intentFilter);
    }

    public final void onDeactivate() {
        this.mContext.unregisterReceiver(this.mPowerReceiver);
    }

    public ChargingState(Context context) {
        super(context, (long) context.getResources().getInteger(C1777R.integer.elmyra_charging_gate_duration));
    }
}
