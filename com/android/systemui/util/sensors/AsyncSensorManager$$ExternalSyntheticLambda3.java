package com.android.systemui.util.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ Sensor f$1;
    public final /* synthetic */ SensorEventListener f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda3(AsyncSensorManager asyncSensorManager, Sensor sensor, SensorEventListener sensorEventListener) {
        this.f$0 = asyncSensorManager;
        this.f$1 = sensor;
        this.f$2 = sensorEventListener;
    }

    public final void run() {
        AsyncSensorManager asyncSensorManager = this.f$0;
        Sensor sensor = this.f$1;
        SensorEventListener sensorEventListener = this.f$2;
        Objects.requireNonNull(asyncSensorManager);
        if (sensor == null) {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener);
        } else {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener, sensor);
        }
    }
}
