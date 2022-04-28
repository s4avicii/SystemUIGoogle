package com.airbnb.lottie;

import androidx.collection.ArraySet;
import java.util.HashMap;

public final class PerformanceTracker {
    public boolean enabled = false;
    public final ArraySet frameListeners = new ArraySet(0);
    public final HashMap layerRenderTimes = new HashMap();

    public interface FrameListener {
        void onFrameRendered();
    }
}
