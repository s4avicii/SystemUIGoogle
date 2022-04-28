package com.android.systemui.doze;

import android.hardware.Sensor;
import android.util.Log;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.statusbar.policy.DevicePostureController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeSensors$$ExternalSyntheticLambda0 implements DevicePostureController.Callback {
    public final /* synthetic */ DozeSensors f$0;

    public /* synthetic */ DozeSensors$$ExternalSyntheticLambda0(DozeSensors dozeSensors) {
        this.f$0 = dozeSensors;
    }

    public final void onPostureChanged(int i) {
        DozeSensors dozeSensors = this.f$0;
        Objects.requireNonNull(dozeSensors);
        if (dozeSensors.mDevicePosture != i) {
            dozeSensors.mDevicePosture = i;
            for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
                int i2 = dozeSensors.mDevicePosture;
                Objects.requireNonNull(triggerSensor);
                int i3 = triggerSensor.mPosture;
                if (i3 != i2) {
                    Sensor[] sensorArr = triggerSensor.mSensors;
                    if (sensorArr.length >= 2 && i2 < sensorArr.length) {
                        Sensor sensor = sensorArr[i3];
                        Sensor sensor2 = sensorArr[i2];
                        if (Objects.equals(sensor, sensor2)) {
                            triggerSensor.mPosture = i2;
                        } else {
                            if (triggerSensor.mRegistered) {
                                boolean cancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(triggerSensor, sensor);
                                if (DozeSensors.DEBUG) {
                                    Log.d("DozeSensors", "posture changed, cancelTriggerSensor[" + sensor + "] " + cancelTriggerSensor);
                                }
                                triggerSensor.mRegistered = false;
                            }
                            triggerSensor.mPosture = i2;
                            triggerSensor.updateListening();
                            DozeSensors.this.mDozeLog.tracePostureChanged(triggerSensor.mPosture, "DozeSensors swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + triggerSensor.mRegistered);
                        }
                    }
                }
            }
        }
    }
}
