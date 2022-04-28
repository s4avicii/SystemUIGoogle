package com.google.android.systemui.lowlightclock;

import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: LightSensorEventsDebounceAlgorithm.kt */
public final class LightSensorEventsDebounceAlgorithm$enqueueDarkModeBundle$1 implements Runnable {
    public final /* synthetic */ LightSensorEventsDebounceAlgorithm this$0;

    public LightSensorEventsDebounceAlgorithm$enqueueDarkModeBundle$1(LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm) {
        this.this$0 = lightSensorEventsDebounceAlgorithm;
    }

    public final void run() {
        if (LightSensorEventsDebounceAlgorithm.DEBUG) {
            Log.d("LightDebounceAlgorithm", "enqueueing a dark mode bundle");
        }
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm = this.this$0;
        Objects.requireNonNull(lightSensorEventsDebounceAlgorithm);
        lightSensorEventsDebounceAlgorithm.bundlesQueueDarkMode.add(new ArrayList());
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm2 = this.this$0;
        lightSensorEventsDebounceAlgorithm2.executor.executeDelayed(lightSensorEventsDebounceAlgorithm2.dequeueDarkModeBundle, (long) lightSensorEventsDebounceAlgorithm2.darkSamplingSpanMillis);
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm3 = this.this$0;
        lightSensorEventsDebounceAlgorithm3.executor.executeDelayed(this, (long) lightSensorEventsDebounceAlgorithm3.darkSamplingFrequencyMillis);
    }
}
