package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.Objects;

public final class ThresholdSensorImpl implements ThresholdSensor {
    public static final boolean DEBUG = Log.isLoggable("ThresholdSensor", 3);
    public final Execution mExecution;
    public Boolean mLastBelow;
    public ArrayList mListeners = new ArrayList();
    public boolean mPaused;
    public boolean mRegistered;
    public final Sensor mSensor;
    public int mSensorDelay;
    public C17081 mSensorEventListener = new SensorEventListener() {
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
            boolean z;
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            ThresholdSensorImpl thresholdSensorImpl = ThresholdSensorImpl.this;
            boolean z2 = true;
            if (f < thresholdSensorImpl.mThreshold) {
                z = true;
            } else {
                z = false;
            }
            if (fArr[0] < thresholdSensorImpl.mThresholdLatch) {
                z2 = false;
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Sensor value: ");
            m.append(sensorEvent.values[0]);
            thresholdSensorImpl.logDebug(m.toString());
            ThresholdSensorImpl thresholdSensorImpl2 = ThresholdSensorImpl.this;
            long j = sensorEvent.timestamp;
            Objects.requireNonNull(thresholdSensorImpl2);
            thresholdSensorImpl2.mExecution.assertIsMainThread();
            if (thresholdSensorImpl2.mRegistered) {
                Boolean bool = thresholdSensorImpl2.mLastBelow;
                if (bool != null) {
                    if (bool.booleanValue() && !z2) {
                        return;
                    }
                    if (!thresholdSensorImpl2.mLastBelow.booleanValue() && !z) {
                        return;
                    }
                }
                thresholdSensorImpl2.mLastBelow = Boolean.valueOf(z);
                thresholdSensorImpl2.logDebug("Alerting below: " + z);
                new ArrayList(thresholdSensorImpl2.mListeners).forEach(new ThresholdSensorImpl$$ExternalSyntheticLambda0(z, j));
            }
        }
    };
    public final AsyncSensorManager mSensorManager;
    public String mTag;
    public final float mThreshold;
    public final float mThresholdLatch;

    public static class Builder {
        public final Execution mExecution;
        public final Resources mResources;
        public Sensor mSensor;
        public int mSensorDelay = 3;
        public final AsyncSensorManager mSensorManager;
        public boolean mSensorSet;
        public float mThresholdLatchValue;
        public boolean mThresholdLatchValueSet;
        public boolean mThresholdSet;
        public float mThresholdValue;

        public final ThresholdSensorImpl build() {
            if (!this.mSensorSet) {
                throw new IllegalStateException("A sensor was not successfully set.");
            } else if (!this.mThresholdSet) {
                throw new IllegalStateException("A threshold was not successfully set.");
            } else if (this.mThresholdValue <= this.mThresholdLatchValue) {
                return new ThresholdSensorImpl(this.mSensorManager, this.mSensor, this.mExecution, this.mThresholdValue, this.mThresholdLatchValue, this.mSensorDelay);
            } else {
                throw new IllegalStateException("Threshold must be less than or equal to Threshold Latch");
            }
        }

        public Builder(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }

        @VisibleForTesting
        public Sensor findSensorByType(String str, boolean z) {
            Sensor sensor = null;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            for (Sensor next : this.mSensorManager.getSensorList(-1)) {
                if (str.equals(next.getStringType())) {
                    if (!z || next.isWakeUpSensor()) {
                        return next;
                    }
                    sensor = next;
                }
            }
            return sensor;
        }
    }

    public final String toString() {
        return String.format("{isLoaded=%s, registered=%s, paused=%s, threshold=%s, sensor=%s}", new Object[]{Boolean.valueOf(isLoaded()), Boolean.valueOf(this.mRegistered), Boolean.valueOf(this.mPaused), Float.valueOf(this.mThreshold), this.mSensor});
    }

    public static class BuilderFactory {
        public final Execution mExecution;
        public final Resources mResources;
        public final AsyncSensorManager mSensorManager;

        public BuilderFactory(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }
    }

    public final boolean isLoaded() {
        if (this.mSensor != null) {
            return true;
        }
        return false;
    }

    public final void logDebug(String str) {
        String str2;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            if (this.mTag != null) {
                str2 = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("["), this.mTag, "] ");
            } else {
                str2 = "";
            }
            sb.append(str2);
            sb.append(str);
            Log.d("ThresholdSensor", sb.toString());
        }
    }

    public final void pause() {
        this.mExecution.assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    public final void register(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
        registerInternal();
    }

    public final void registerInternal() {
        this.mExecution.assertIsMainThread();
        if (!this.mRegistered && !this.mPaused && !this.mListeners.isEmpty()) {
            logDebug("Registering sensor listener");
            this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, this.mSensorDelay);
            this.mRegistered = true;
        }
    }

    public final void resume() {
        this.mExecution.assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    public final void setDelay() {
        if (1 != this.mSensorDelay) {
            this.mSensorDelay = 1;
            if (isLoaded()) {
                unregisterInternal();
                registerInternal();
            }
        }
    }

    public final void unregister(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        this.mListeners.remove(listener);
        unregisterInternal();
    }

    public final void unregisterInternal() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered) {
            logDebug("Unregister sensor listener");
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mRegistered = false;
            this.mLastBelow = null;
        }
    }

    public ThresholdSensorImpl(AsyncSensorManager asyncSensorManager, Sensor sensor, Execution execution, float f, float f2, int i) {
        this.mSensorManager = asyncSensorManager;
        this.mExecution = execution;
        this.mSensor = sensor;
        this.mThreshold = f;
        this.mThresholdLatch = f2;
        this.mSensorDelay = i;
    }

    public final void setTag(String str) {
        this.mTag = str;
    }

    @VisibleForTesting
    public boolean isRegistered() {
        return this.mRegistered;
    }
}
