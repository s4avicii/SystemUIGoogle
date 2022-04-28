package com.google.android.systemui.lowlightclock;

import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: LightSensorEventsDebounceAlgorithm.kt */
public final class LightSensorEventsDebounceAlgorithm$enqueueLightModeBundle$1 implements Runnable {
    public final /* synthetic */ LightSensorEventsDebounceAlgorithm this$0;

    public LightSensorEventsDebounceAlgorithm$enqueueLightModeBundle$1(LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm) {
        this.this$0 = lightSensorEventsDebounceAlgorithm;
    }

    public final void run() {
        if (LightSensorEventsDebounceAlgorithm.DEBUG) {
            Log.d("LightDebounceAlgorithm", "enqueueing a light mode bundle");
        }
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm = this.this$0;
        Objects.requireNonNull(lightSensorEventsDebounceAlgorithm);
        lightSensorEventsDebounceAlgorithm.bundlesQueueLightMode.add(new ArrayList());
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm2 = this.this$0;
        lightSensorEventsDebounceAlgorithm2.executor.executeDelayed(lightSensorEventsDebounceAlgorithm2.dequeueLightModeBundle, (long) lightSensorEventsDebounceAlgorithm2.lightSamplingSpanMillis);
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm3 = this.this$0;
        lightSensorEventsDebounceAlgorithm3.executor.executeDelayed(this, (long) lightSensorEventsDebounceAlgorithm3.lightSamplingFrequencyMillis);
    }
}
