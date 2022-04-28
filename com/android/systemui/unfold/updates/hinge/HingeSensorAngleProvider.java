package com.android.systemui.unfold.updates.hinge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: HingeSensorAngleProvider.kt */
public final class HingeSensorAngleProvider implements HingeAngleProvider {
    public final ArrayList listeners = new ArrayList();
    public final HingeAngleSensorListener sensorListener = new HingeAngleSensorListener();
    public final SensorManager sensorManager;

    /* compiled from: HingeSensorAngleProvider.kt */
    public final class HingeAngleSensorListener implements SensorEventListener {
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public HingeAngleSensorListener() {
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
            Iterator it = HingeSensorAngleProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Float.valueOf(sensorEvent.values[0]));
            }
        }
    }

    public final void addCallback(Object obj) {
        this.listeners.add((Consumer) obj);
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((Consumer) obj);
    }

    public final void start() {
        this.sensorManager.registerListener(this.sensorListener, this.sensorManager.getDefaultSensor(36), 0);
    }

    public final void stop() {
        this.sensorManager.unregisterListener(this.sensorListener);
    }

    public HingeSensorAngleProvider(SensorManager sensorManager2) {
        this.sensorManager = sensorManager2;
    }
}
