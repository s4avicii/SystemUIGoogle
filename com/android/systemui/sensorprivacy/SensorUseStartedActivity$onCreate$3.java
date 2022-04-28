package com.android.systemui.sensorprivacy;

import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;

/* compiled from: SensorUseStartedActivity.kt */
public final class SensorUseStartedActivity$onCreate$3 implements IndividualSensorPrivacyController.Callback {
    public final /* synthetic */ SensorUseStartedActivity this$0;

    public SensorUseStartedActivity$onCreate$3(SensorUseStartedActivity sensorUseStartedActivity) {
        this.this$0 = sensorUseStartedActivity;
    }

    public final void onSensorBlockedChanged(int i, boolean z) {
        SensorUseStartedActivity sensorUseStartedActivity = this.this$0;
        if (i == sensorUseStartedActivity.sensor && !z) {
            sensorUseStartedActivity.finish();
        }
    }
}
