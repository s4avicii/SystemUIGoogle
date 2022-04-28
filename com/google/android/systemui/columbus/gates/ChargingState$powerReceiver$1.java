package com.google.android.systemui.columbus.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: ChargingState.kt */
public final class ChargingState$powerReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ChargingState this$0;

    public ChargingState$powerReceiver$1(ChargingState chargingState) {
        this.this$0 = chargingState;
    }

    public final void onReceive(Context context, Intent intent) {
        ChargingState chargingState = this.this$0;
        chargingState.blockForMillis(chargingState.gateDuration);
    }
}
