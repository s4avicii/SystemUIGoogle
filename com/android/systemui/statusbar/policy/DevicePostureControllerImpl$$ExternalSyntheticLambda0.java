package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.tuner.LockscreenFragment$$ExternalSyntheticLambda1;
import com.android.systemui.util.Assert;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DevicePostureControllerImpl$$ExternalSyntheticLambda0 implements DeviceStateManager.DeviceStateCallback {
    public final /* synthetic */ DevicePostureControllerImpl f$0;

    public /* synthetic */ DevicePostureControllerImpl$$ExternalSyntheticLambda0(DevicePostureControllerImpl devicePostureControllerImpl) {
        this.f$0 = devicePostureControllerImpl;
    }

    public final void onStateChanged(int i) {
        DevicePostureControllerImpl devicePostureControllerImpl = this.f$0;
        Objects.requireNonNull(devicePostureControllerImpl);
        Assert.isMainThread();
        devicePostureControllerImpl.mCurrentDevicePosture = devicePostureControllerImpl.mDeviceStateToPostureMap.get(i, 0);
        devicePostureControllerImpl.mListeners.forEach(new LockscreenFragment$$ExternalSyntheticLambda1(devicePostureControllerImpl, 1));
    }
}
