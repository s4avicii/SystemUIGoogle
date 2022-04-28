package com.android.systemui.util.sensors;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;

public final class PostureDependentProximitySensor extends ProximitySensorImpl {
    public final PostureDependentProximitySensor$$ExternalSyntheticLambda0 mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final ThresholdSensor[] mPostureToPrimaryProxSensorMap;
    public final ThresholdSensor[] mPostureToSecondaryProxSensorMap;

    public PostureDependentProximitySensor(ThresholdSensor[] thresholdSensorArr, ThresholdSensor[] thresholdSensorArr2, DelayableExecutor delayableExecutor, Execution execution, DevicePostureController devicePostureController) {
        super(thresholdSensorArr[0], thresholdSensorArr2[0], delayableExecutor, execution);
        PostureDependentProximitySensor$$ExternalSyntheticLambda0 postureDependentProximitySensor$$ExternalSyntheticLambda0 = new PostureDependentProximitySensor$$ExternalSyntheticLambda0(this);
        this.mDevicePostureCallback = postureDependentProximitySensor$$ExternalSyntheticLambda0;
        this.mPostureToPrimaryProxSensorMap = thresholdSensorArr;
        this.mPostureToSecondaryProxSensorMap = thresholdSensorArr2;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = devicePostureController.getDevicePosture();
        devicePostureController.addCallback(postureDependentProximitySensor$$ExternalSyntheticLambda0);
        chooseSensors();
    }

    public final String toString() {
        return String.format("{posture=%s, proximitySensor=%s}", new Object[]{DevicePostureController.devicePostureToString(this.mDevicePosture), super.toString()});
    }

    public final void chooseSensors() {
        int i = this.mDevicePosture;
        ThresholdSensor[] thresholdSensorArr = this.mPostureToPrimaryProxSensorMap;
        if (i < thresholdSensorArr.length) {
            ThresholdSensor[] thresholdSensorArr2 = this.mPostureToSecondaryProxSensorMap;
            if (i < thresholdSensorArr2.length) {
                ThresholdSensor thresholdSensor = thresholdSensorArr[i];
                ThresholdSensor thresholdSensor2 = thresholdSensorArr2[i];
                if (thresholdSensor != this.mPrimaryThresholdSensor || thresholdSensor2 != this.mSecondaryThresholdSensor) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Register new proximity sensors newPosture=");
                    m.append(DevicePostureController.devicePostureToString(this.mDevicePosture));
                    logDebug(m.toString());
                    unregisterInternal();
                    ThresholdSensor thresholdSensor3 = this.mPrimaryThresholdSensor;
                    if (thresholdSensor3 != null) {
                        thresholdSensor3.unregister(this.mPrimaryEventListener);
                    }
                    ThresholdSensor thresholdSensor4 = this.mSecondaryThresholdSensor;
                    if (thresholdSensor4 != null) {
                        thresholdSensor4.unregister(this.mSecondaryEventListener);
                    }
                    this.mPrimaryThresholdSensor = thresholdSensor;
                    this.mSecondaryThresholdSensor = thresholdSensor2;
                    this.mInitializedListeners = false;
                    registerInternal();
                    return;
                }
                return;
            }
        }
        StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("unsupported devicePosture=");
        m2.append(this.mDevicePosture);
        Log.e("PostureDependProxSensor", m2.toString());
    }

    public final void destroy() {
        pause();
        this.mDevicePostureController.removeCallback(this.mDevicePostureCallback);
    }
}
