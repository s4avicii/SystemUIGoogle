package com.android.systemui.volume;

import android.animation.TimeInterpolator;

public final class SystemUIInterpolators$LogDecelerateInterpolator implements TimeInterpolator {
    public final float mOutputScale = (1.0f / ((1.0f - ((float) Math.pow((double) 400.0f, (double) -0.71428573f))) + 0.0f));

    public final float getInterpolation(float f) {
        return ((f * 0.0f) + (1.0f - ((float) Math.pow((double) 400.0f, (double) ((-f) * 0.71428573f))))) * this.mOutputScale;
    }
}
