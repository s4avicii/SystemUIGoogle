package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class IndividualSensorPrivacyControllerImpl$$ExternalSyntheticLambda0 implements SensorPrivacyManager.OnSensorPrivacyChangedListener {
    public final /* synthetic */ IndividualSensorPrivacyControllerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ IndividualSensorPrivacyControllerImpl$$ExternalSyntheticLambda0(IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl, int i) {
        this.f$0 = individualSensorPrivacyControllerImpl;
        this.f$1 = i;
    }

    public final void onSensorPrivacyChanged(int i, boolean z) {
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = this.f$0;
        int i2 = this.f$1;
        Objects.requireNonNull(individualSensorPrivacyControllerImpl);
        individualSensorPrivacyControllerImpl.mState.put(i2, z);
        Iterator it = individualSensorPrivacyControllerImpl.mCallbacks.iterator();
        while (it.hasNext()) {
            ((IndividualSensorPrivacyController.Callback) it.next()).onSensorBlockedChanged(i2, z);
        }
    }
}
