package com.android.systemui.statusbar;

import android.view.animation.PathInterpolator;
import com.android.systemui.animation.Interpolators;

/* compiled from: LightRevealScrim.kt */
public final class LiftReveal implements LightRevealEffect {
    public static final LiftReveal INSTANCE = new LiftReveal();
    public static final PathInterpolator INTERPOLATOR = Interpolators.FAST_OUT_SLOW_IN_REVERSE;

    public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
        float interpolation = INTERPOLATOR.getInterpolation(f);
        float f2 = interpolation - 0.35f;
        float f3 = 0.0f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        float f4 = 1.5384616f * f2;
        float f5 = f - 0.85f;
        if (f5 >= 0.0f) {
            f3 = f5;
        }
        lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - (6.666668f * f3));
        lightRevealScrim.setRevealGradientBounds((((float) (-lightRevealScrim.getWidth())) * f4) + (((float) lightRevealScrim.getWidth()) * 0.25f), (((float) lightRevealScrim.getHeight()) * 1.1f) - (((float) lightRevealScrim.getHeight()) * interpolation), (((float) lightRevealScrim.getWidth()) * f4) + (((float) lightRevealScrim.getWidth()) * 0.75f), (((float) lightRevealScrim.getHeight()) * interpolation) + (((float) lightRevealScrim.getHeight()) * 1.2f));
    }
}
