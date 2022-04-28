package com.android.systemui.util.sensors;

import com.android.systemui.statusbar.policy.DevicePostureController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PostureDependentProximitySensor$$ExternalSyntheticLambda0 implements DevicePostureController.Callback {
    public final /* synthetic */ PostureDependentProximitySensor f$0;

    public /* synthetic */ PostureDependentProximitySensor$$ExternalSyntheticLambda0(PostureDependentProximitySensor postureDependentProximitySensor) {
        this.f$0 = postureDependentProximitySensor;
    }

    public final void onPostureChanged(int i) {
        PostureDependentProximitySensor postureDependentProximitySensor = this.f$0;
        Objects.requireNonNull(postureDependentProximitySensor);
        if (postureDependentProximitySensor.mDevicePosture != i) {
            postureDependentProximitySensor.mDevicePosture = i;
            postureDependentProximitySensor.chooseSensors();
        }
    }
}
