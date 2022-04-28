package com.google.android.systemui.columbus.sensors;

import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Objects;

/* compiled from: GestureSensorImpl.kt */
public final class GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1 implements Runnable {
    public final /* synthetic */ GestureSensorImpl this$0;

    public GestureSensorImpl$GestureSensorEventListener$onSensorChanged$1$1(GestureSensorImpl gestureSensorImpl) {
        this.this$0 = gestureSensorImpl;
    }

    public final void run() {
        GestureSensorImpl gestureSensorImpl = this.this$0;
        GestureSensor.DetectionProperties detectionProperties = new GestureSensor.DetectionProperties(true);
        Objects.requireNonNull(gestureSensorImpl);
        GestureSensor.Listener listener = gestureSensorImpl.listener;
        if (listener != null) {
            listener.onGestureDetected(gestureSensorImpl, 2, detectionProperties);
        }
    }
}
