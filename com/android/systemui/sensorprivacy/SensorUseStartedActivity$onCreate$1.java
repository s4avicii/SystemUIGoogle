package com.android.systemui.sensorprivacy;

import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;

/* compiled from: SensorUseStartedActivity.kt */
public final class SensorUseStartedActivity$onCreate$1 implements IndividualSensorPrivacyController.Callback {
    public final /* synthetic */ SensorUseStartedActivity this$0;

    public SensorUseStartedActivity$onCreate$1(SensorUseStartedActivity sensorUseStartedActivity) {
        this.this$0 = sensorUseStartedActivity;
    }

    public final void onSensorBlockedChanged(int i, boolean z) {
        if (!this.this$0.sensorPrivacyController.isSensorBlocked(1) && !this.this$0.sensorPrivacyController.isSensorBlocked(2)) {
            this.this$0.finish();
        }
    }
}
