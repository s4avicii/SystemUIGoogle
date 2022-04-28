package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;

/* compiled from: ChargingState.kt */
public final class ChargingState extends TransientGate {
    public final long gateDuration;
    public final ChargingState$powerReceiver$1 powerReceiver = new ChargingState$powerReceiver$1(this);

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        this.context.registerReceiver(this.powerReceiver, intentFilter);
    }

    public final void onDeactivate() {
        this.context.unregisterReceiver(this.powerReceiver);
    }

    public ChargingState(Context context, Handler handler, long j) {
        super(context, handler);
        this.gateDuration = j;
    }
}
