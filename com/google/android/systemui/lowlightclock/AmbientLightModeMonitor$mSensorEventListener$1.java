package com.google.android.systemui.lowlightclock;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/* compiled from: AmbientLightModeMonitor.kt */
public final class AmbientLightModeMonitor$mSensorEventListener$1 implements SensorEventListener {
    public final /* synthetic */ AmbientLightModeMonitor this$0;

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    public AmbientLightModeMonitor$mSensorEventListener$1(AmbientLightModeMonitor ambientLightModeMonitor) {
        this.this$0 = ambientLightModeMonitor;
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        boolean z;
        float[] fArr = sensorEvent.values;
        if (fArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            this.this$0.algorithm.onUpdateLightSensorEvent(fArr[0]);
        } else if (AmbientLightModeMonitor.DEBUG) {
            Log.w("AmbientLightModeMonitor", "SensorEvent doesn't have any value");
        }
    }
}
