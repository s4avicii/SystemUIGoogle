package com.android.systemui.animation;

import android.util.MathUtils;

/* compiled from: ShadeInterpolation.kt */
public final class ShadeInterpolation {
    public static final float getContentAlpha(float f) {
        return interpolateEaseInOut(MathUtils.constrainedMap(0.0f, 1.0f, 0.3f, 1.0f, f));
    }

    public static final float getNotificationScrimAlpha(float f) {
        return interpolateEaseInOut(MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.5f, f));
    }

    public static float interpolateEaseInOut(float f) {
        float f2 = (f * 1.2f) - 0.2f;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        float f3 = 1.0f - f2;
        double d = (double) 1.0f;
        return (float) (d - ((d - Math.cos((double) ((3.14159f * f3) * f3))) * ((double) 0.5f)));
    }
}
