package com.google.android.systemui.lowlightclock;

import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LightSensorEventsDebounceAlgorithm.kt */
public final class LightSensorEventsDebounceAlgorithm$dequeueDarkModeBundle$1 implements Runnable {
    public final /* synthetic */ LightSensorEventsDebounceAlgorithm this$0;

    public LightSensorEventsDebounceAlgorithm$dequeueDarkModeBundle$1(LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm) {
        this.this$0 = lightSensorEventsDebounceAlgorithm;
    }

    public final void run() {
        boolean z;
        LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm = this.this$0;
        Objects.requireNonNull(lightSensorEventsDebounceAlgorithm);
        if (!lightSensorEventsDebounceAlgorithm.bundlesQueueDarkMode.isEmpty()) {
            LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm2 = this.this$0;
            Objects.requireNonNull(lightSensorEventsDebounceAlgorithm2);
            int i = 0;
            ArrayList<Float> remove = lightSensorEventsDebounceAlgorithm2.bundlesQueueDarkMode.remove(0);
            lightSensorEventsDebounceAlgorithm2.bundleDarkMode = remove;
            double averageOfFloat = CollectionsKt___CollectionsKt.averageOfFloat(remove);
            if (!Double.isNaN(averageOfFloat)) {
                boolean z2 = LightSensorEventsDebounceAlgorithm.DEBUG;
                if (z2) {
                    Log.d("LightDebounceAlgorithm", Intrinsics.stringPlus("dark mode average: ", Double.valueOf(averageOfFloat)));
                }
                if (averageOfFloat < ((double) lightSensorEventsDebounceAlgorithm2.darkModeThreshold)) {
                    z = true;
                } else {
                    z = false;
                }
                if (lightSensorEventsDebounceAlgorithm2.isDarkMode != z) {
                    lightSensorEventsDebounceAlgorithm2.isDarkMode = z;
                    if (z2) {
                        Log.d("LightDebounceAlgorithm", Intrinsics.stringPlus("isDarkMode: ", Boolean.valueOf(z)));
                    }
                    if (z) {
                        i = 1;
                    } else if (!lightSensorEventsDebounceAlgorithm2.isLightMode) {
                        i = 2;
                    }
                    lightSensorEventsDebounceAlgorithm2.setMode(i);
                }
            }
            if (LightSensorEventsDebounceAlgorithm.DEBUG) {
                LightSensorEventsDebounceAlgorithm lightSensorEventsDebounceAlgorithm3 = this.this$0;
                Objects.requireNonNull(lightSensorEventsDebounceAlgorithm3);
                Log.d("LightDebounceAlgorithm", Intrinsics.stringPlus("dequeued a dark mode bundle of size ", Integer.valueOf(lightSensorEventsDebounceAlgorithm3.bundleDarkMode.size())));
            }
        }
    }
}
