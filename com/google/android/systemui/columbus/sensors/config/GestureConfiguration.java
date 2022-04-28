package com.google.android.systemui.columbus.sensors.config;

import android.util.Range;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: GestureConfiguration.kt */
public final class GestureConfiguration {
    public static final Range<Float> SENSITIVITY_RANGE = Range.create(Float.valueOf(0.0f), Float.valueOf(1.0f));
    public final Function1<Adjustment, Unit> adjustmentCallback = new GestureConfiguration$adjustmentCallback$1(this);
    public final List<Adjustment> adjustments;
    public Listener listener;
    public float sensitivity;
    public final SensorConfiguration sensorConfiguration;

    /* compiled from: GestureConfiguration.kt */
    public interface Listener {
        void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration);
    }

    public final void updateSensitivity() {
        float f = this.sensorConfiguration.defaultSensitivityValue;
        for (Adjustment adjustSensitivity : this.adjustments) {
            f = SENSITIVITY_RANGE.clamp(Float.valueOf(adjustSensitivity.adjustSensitivity(f))).floatValue();
        }
        if (Math.abs(this.sensitivity - f) >= 0.05f) {
            this.sensitivity = f;
            Listener listener2 = this.listener;
            if (listener2 != null) {
                listener2.onGestureConfigurationChanged(this);
            }
        }
    }

    public GestureConfiguration(List<Adjustment> list, SensorConfiguration sensorConfiguration2) {
        this.adjustments = list;
        this.sensorConfiguration = sensorConfiguration2;
        this.sensitivity = sensorConfiguration2.defaultSensitivityValue;
        for (Adjustment adjustment : list) {
            Function1<Adjustment, Unit> function1 = this.adjustmentCallback;
            Objects.requireNonNull(adjustment);
            adjustment.callback = function1;
        }
        updateSensitivity();
    }
}
