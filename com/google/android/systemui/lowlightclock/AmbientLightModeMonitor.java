package com.google.android.systemui.lowlightclock;

import android.hardware.Sensor;
import android.util.Log;
import com.android.systemui.util.sensors.AsyncSensorManager;

/* compiled from: AmbientLightModeMonitor.kt */
public final class AmbientLightModeMonitor {
    public static final boolean DEBUG = Log.isLoggable("AmbientLightModeMonitor", 3);
    public final DebounceAlgorithm algorithm;
    public final Sensor lightSensor;
    public final AmbientLightModeMonitor$mSensorEventListener$1 mSensorEventListener = new AmbientLightModeMonitor$mSensorEventListener$1(this);
    public final AsyncSensorManager sensorManager;

    /* compiled from: AmbientLightModeMonitor.kt */
    public interface Callback {
    }

    /* compiled from: AmbientLightModeMonitor.kt */
    public interface DebounceAlgorithm {
        void onUpdateLightSensorEvent(float f);

        void start(LowLightDockManager$$ExternalSyntheticLambda0 lowLightDockManager$$ExternalSyntheticLambda0);

        void stop();
    }

    public AmbientLightModeMonitor(DebounceAlgorithm debounceAlgorithm, AsyncSensorManager asyncSensorManager) {
        this.algorithm = debounceAlgorithm;
        this.sensorManager = asyncSensorManager;
        this.lightSensor = asyncSensorManager.getDefaultSensor(5);
    }
}
