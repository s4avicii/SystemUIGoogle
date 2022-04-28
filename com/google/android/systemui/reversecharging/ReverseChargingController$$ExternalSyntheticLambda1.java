package com.google.android.systemui.reversecharging;

import android.app.AlarmManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda1 implements AlarmManager.OnAlarmListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda1(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    public final void onAlarm() {
        ReverseChargingController reverseChargingController = this.f$0;
        Objects.requireNonNull(reverseChargingController);
        reverseChargingController.onAlarmRtxFinish(103);
    }
}
