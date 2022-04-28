package com.google.android.systemui.columbus.gates;

import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Proximity.kt */
public final class Proximity$proximityListener$1 implements ThresholdSensor.Listener {
    public final /* synthetic */ Proximity this$0;

    public Proximity$proximityListener$1(Proximity proximity) {
        this.this$0 = proximity;
    }

    public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
        Proximity proximity = this.this$0;
        Objects.requireNonNull(proximity);
        proximity.setBlocking(!Intrinsics.areEqual(proximity.proximitySensor.isNear(), Boolean.FALSE));
    }
}
