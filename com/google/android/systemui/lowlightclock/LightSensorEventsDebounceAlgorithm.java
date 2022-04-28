package com.google.android.systemui.lowlightclock;

import android.content.res.Resources;
import android.os.SystemClock;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.lowlightclock.AmbientLightModeMonitor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LightSensorEventsDebounceAlgorithm.kt */
public final class LightSensorEventsDebounceAlgorithm implements AmbientLightModeMonitor.DebounceAlgorithm {
    public static final boolean DEBUG = Log.isLoggable("LightDebounceAlgorithm", 3);
    public ArrayList<Float> bundleDarkMode = new ArrayList<>();
    public ArrayList<Float> bundleLightMode = new ArrayList<>();
    public final ArrayList<ArrayList<Float>> bundlesQueueDarkMode = new ArrayList<>();
    public final ArrayList<ArrayList<Float>> bundlesQueueLightMode = new ArrayList<>();
    public AmbientLightModeMonitor.Callback callback;
    public final int darkModeThreshold;
    public final int darkSamplingFrequencyMillis;
    public final int darkSamplingSpanMillis;
    public final LightSensorEventsDebounceAlgorithm$dequeueDarkModeBundle$1 dequeueDarkModeBundle = new LightSensorEventsDebounceAlgorithm$dequeueDarkModeBundle$1(this);
    public final LightSensorEventsDebounceAlgorithm$dequeueLightModeBundle$1 dequeueLightModeBundle = new LightSensorEventsDebounceAlgorithm$dequeueLightModeBundle$1(this);
    public final LightSensorEventsDebounceAlgorithm$enqueueDarkModeBundle$1 enqueueDarkModeBundle = new LightSensorEventsDebounceAlgorithm$enqueueDarkModeBundle$1(this);
    public final LightSensorEventsDebounceAlgorithm$enqueueLightModeBundle$1 enqueueLightModeBundle = new LightSensorEventsDebounceAlgorithm$enqueueLightModeBundle$1(this);
    public final DelayableExecutor executor;
    public boolean isDarkMode;
    public boolean isLightMode;
    public final int lightModeThreshold;
    public final int lightSamplingFrequencyMillis;
    public final int lightSamplingSpanMillis;
    public int mode = 2;

    public final void onUpdateLightSensorEvent(float f) {
        if (this.callback != null) {
            Iterator<ArrayList<Float>> it = this.bundlesQueueLightMode.iterator();
            while (it.hasNext()) {
                it.next().add(Float.valueOf(f));
            }
            Iterator<ArrayList<Float>> it2 = this.bundlesQueueDarkMode.iterator();
            while (it2.hasNext()) {
                it2.next().add(Float.valueOf(f));
            }
        } else if (DEBUG) {
            Log.w("LightDebounceAlgorithm", "ignore light sensor event because algorithm not started");
        }
    }

    public final void setMode(int i) {
        if (this.mode != i) {
            this.mode = i;
            if (DEBUG) {
                Log.d("LightDebounceAlgorithm", Intrinsics.stringPlus("ambient light mode changed to ", Integer.valueOf(i)));
            }
            AmbientLightModeMonitor.Callback callback2 = this.callback;
            if (callback2 != null) {
                LowLightDockManager lowLightDockManager = (LowLightDockManager) ((LowLightDockManager$$ExternalSyntheticLambda0) callback2).f$0;
                Objects.requireNonNull(lowLightDockManager);
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                lowLightDockManager.mIsLowLight = z;
                if (z) {
                    if (LowLightDockManager.DEBUG) {
                        Log.d("LowLightDockManager", "enter low light, start dozing");
                    }
                    lowLightDockManager.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 0, 0);
                    return;
                }
                if (LowLightDockManager.DEBUG) {
                    Log.d("LowLightDockManager", "exit low light, stop dozing");
                }
                lowLightDockManager.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 2, "Exit low light condition");
            }
        }
    }

    public final void start(LowLightDockManager$$ExternalSyntheticLambda0 lowLightDockManager$$ExternalSyntheticLambda0) {
        boolean z = DEBUG;
        if (z) {
            Log.d("LightDebounceAlgorithm", "start algorithm");
        }
        if (this.callback == null) {
            this.callback = lowLightDockManager$$ExternalSyntheticLambda0;
            this.executor.execute(this.enqueueLightModeBundle);
            this.executor.execute(this.enqueueDarkModeBundle);
        } else if (z) {
            Log.w("LightDebounceAlgorithm", "already started");
        }
    }

    public final void stop() {
        boolean z = DEBUG;
        if (z) {
            Log.d("LightDebounceAlgorithm", "stop algorithm");
        }
        if (this.callback != null) {
            this.callback = null;
            this.bundlesQueueLightMode.clear();
            this.bundlesQueueDarkMode.clear();
            setMode(2);
        } else if (z) {
            Log.w("LightDebounceAlgorithm", "haven't started");
        }
    }

    public LightSensorEventsDebounceAlgorithm(DelayableExecutor delayableExecutor, Resources resources) {
        this.executor = delayableExecutor;
        this.lightModeThreshold = resources.getInteger(C1777R.integer.config_ambientLightModeThreshold);
        this.darkModeThreshold = resources.getInteger(C1777R.integer.config_ambientDarkModeThreshold);
        this.lightSamplingSpanMillis = resources.getInteger(C1777R.integer.config_ambientLightModeSamplingSpanMillis);
        this.darkSamplingSpanMillis = resources.getInteger(C1777R.integer.config_ambientDarkModeSamplingSpanMillis);
        this.lightSamplingFrequencyMillis = resources.getInteger(C1777R.integer.config_ambientLightModeSamplingFrequencyMillis);
        this.darkSamplingFrequencyMillis = resources.getInteger(C1777R.integer.config_ambientDarkModeSamplingFrequencyMillis);
    }
}
