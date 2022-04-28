package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.Objects;

public final class KeyguardProximity extends Gate {
    public final C22501 mGateListener;
    public boolean mIsListening = false;
    public final KeyguardVisibility mKeyguardGate;
    public boolean mProximityBlocked = false;
    public final float mProximityThreshold;
    public final Sensor mSensor;
    public final C22512 mSensorListener = new SensorEventListener() {
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
            boolean z = false;
            float f = sensorEvent.values[0];
            KeyguardProximity keyguardProximity = KeyguardProximity.this;
            if (f < keyguardProximity.mProximityThreshold) {
                z = true;
            }
            if (keyguardProximity.mIsListening && z != keyguardProximity.mProximityBlocked) {
                keyguardProximity.mProximityBlocked = z;
                keyguardProximity.notifyListener();
            }
        }
    };
    public final SensorManager mSensorManager;

    public final boolean isBlocked() {
        if (!this.mIsListening || !this.mProximityBlocked) {
            return false;
        }
        return true;
    }

    public final void onActivate() {
        if (this.mSensor != null) {
            this.mKeyguardGate.activate();
            updateProximityListener();
        }
    }

    public final void onDeactivate() {
        if (this.mSensor != null) {
            this.mKeyguardGate.deactivate();
            updateProximityListener();
        }
    }

    public final String toString() {
        return super.toString() + " [mIsListening -> " + this.mIsListening + "]";
    }

    public final void updateProximityListener() {
        if (this.mProximityBlocked) {
            this.mProximityBlocked = false;
            notifyListener();
        }
        if (this.mActive) {
            KeyguardVisibility keyguardVisibility = this.mKeyguardGate;
            Objects.requireNonNull(keyguardVisibility);
            if (keyguardVisibility.mKeyguardStateController.isShowing()) {
                KeyguardVisibility keyguardVisibility2 = this.mKeyguardGate;
                Objects.requireNonNull(keyguardVisibility2);
                if (!keyguardVisibility2.mKeyguardStateController.isOccluded()) {
                    if (!this.mIsListening) {
                        this.mSensorManager.registerListener(this.mSensorListener, this.mSensor, 3);
                        this.mIsListening = true;
                        return;
                    }
                    return;
                }
            }
        }
        this.mSensorManager.unregisterListener(this.mSensorListener);
        this.mIsListening = false;
    }

    public KeyguardProximity(Context context) {
        super(context);
        C22501 r0 = new Gate.Listener() {
            public final void onGateChanged(Gate gate) {
                KeyguardProximity.this.updateProximityListener();
            }
        };
        this.mGateListener = r0;
        SensorManager sensorManager = (SensorManager) Dependency.get(AsyncSensorManager.class);
        this.mSensorManager = sensorManager;
        Sensor defaultSensor = sensorManager.getDefaultSensor(8);
        this.mSensor = defaultSensor;
        if (defaultSensor == null) {
            this.mProximityThreshold = 0.0f;
            this.mKeyguardGate = null;
            Log.e("Elmyra/KeyguardProximity", "Could not find any Sensor.TYPE_PROXIMITY");
            return;
        }
        this.mProximityThreshold = Math.min(defaultSensor.getMaximumRange(), (float) context.getResources().getInteger(C1777R.integer.elmyra_keyguard_proximity_threshold));
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        keyguardVisibility.mListener = r0;
        updateProximityListener();
    }
}
