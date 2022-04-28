package com.android.systemui.sensorprivacy.television;

import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TvUnblockSensorActivity$$ExternalSyntheticLambda0 implements IndividualSensorPrivacyController.Callback {
    public final /* synthetic */ TvUnblockSensorActivity f$0;

    public /* synthetic */ TvUnblockSensorActivity$$ExternalSyntheticLambda0(TvUnblockSensorActivity tvUnblockSensorActivity) {
        this.f$0 = tvUnblockSensorActivity;
    }

    public final void onSensorBlockedChanged(int i, boolean z) {
        TvUnblockSensorActivity tvUnblockSensorActivity = this.f$0;
        int i2 = TvUnblockSensorActivity.$r8$clinit;
        Objects.requireNonNull(tvUnblockSensorActivity);
        int i3 = tvUnblockSensorActivity.mSensor;
        if (i3 == Integer.MAX_VALUE) {
            if (!tvUnblockSensorActivity.mSensorPrivacyController.isSensorBlocked(2) && !tvUnblockSensorActivity.mSensorPrivacyController.isSensorBlocked(1)) {
                tvUnblockSensorActivity.finish();
            }
        } else if (i3 == i && !z) {
            tvUnblockSensorActivity.finish();
        }
    }
}
