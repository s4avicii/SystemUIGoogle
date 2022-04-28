package com.google.android.systemui.reversecharging;

import android.app.AlarmManager;
import android.util.Log;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda2 implements AlarmManager.OnAlarmListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda2(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    public final void onAlarm() {
        ReverseChargingController reverseChargingController = this.f$0;
        if (ReverseChargingController.DEBUG) {
            Objects.requireNonNull(reverseChargingController);
            Log.w("ReverseChargingControl", "mReConnectedTimeoutAlarmAction() timeout");
        }
        reverseChargingController.mStartReconnected = false;
        reverseChargingController.onAlarmRtxFinish(6);
    }
}
