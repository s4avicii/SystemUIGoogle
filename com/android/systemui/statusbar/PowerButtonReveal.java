package com.android.systemui.statusbar;

import com.android.systemui.animation.Interpolators;

/* compiled from: LightRevealScrim.kt */
public final class PowerButtonReveal implements LightRevealEffect {
    public final float powerButtonY;

    public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
        float interpolation = Interpolators.FAST_OUT_SLOW_IN_REVERSE.getInterpolation(f);
        float f2 = interpolation - 0.5f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - (2.0f * f2));
        lightRevealScrim.interpolatedRevealAmount = interpolation;
        lightRevealScrim.setRevealGradientBounds((((float) lightRevealScrim.getWidth()) * 1.05f) - ((((float) lightRevealScrim.getWidth()) * 1.25f) * interpolation), this.powerButtonY - (((float) lightRevealScrim.getHeight()) * interpolation), (((float) lightRevealScrim.getWidth()) * 1.25f * interpolation) + (((float) lightRevealScrim.getWidth()) * 1.05f), (((float) lightRevealScrim.getHeight()) * interpolation) + this.powerButtonY);
    }

    public PowerButtonReveal(float f) {
        this.powerButtonY = f;
    }
}
