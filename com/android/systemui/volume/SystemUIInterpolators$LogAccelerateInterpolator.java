package com.android.systemui.volume;

import android.animation.TimeInterpolator;

public final class SystemUIInterpolators$LogAccelerateInterpolator implements TimeInterpolator {
    public final float mLogScale = (1.0f / ((((float) 0) * 1.0f) + (((float) (-Math.pow((double) 100, (double) -1.0f))) + 1.0f)));

    public final float getInterpolation(float f) {
        float f2 = 1.0f - f;
        return 1.0f - (((((float) 0) * f2) + (((float) (-Math.pow((double) 100, (double) (-f2)))) + 1.0f)) * this.mLogScale);
    }
}
