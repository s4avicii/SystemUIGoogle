package com.android.systemui.util.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.util.Log;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ SensorEventListener f$1;
    public final /* synthetic */ Sensor f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ Handler f$5;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda4(AsyncSensorManager asyncSensorManager, SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        this.f$0 = asyncSensorManager;
        this.f$1 = sensorEventListener;
        this.f$2 = sensor;
        this.f$3 = i;
        this.f$4 = i2;
        this.f$5 = handler;
    }

    public final void run() {
        AsyncSensorManager asyncSensorManager = this.f$0;
        SensorEventListener sensorEventListener = this.f$1;
        Sensor sensor = this.f$2;
        int i = this.f$3;
        int i2 = this.f$4;
        Handler handler = this.f$5;
        Objects.requireNonNull(asyncSensorManager);
        if (!asyncSensorManager.mInner.registerListener(sensorEventListener, sensor, i, i2, handler)) {
            Log.e("AsyncSensorManager", "Registering " + sensorEventListener + " for " + sensor + " failed.");
        }
    }
}
