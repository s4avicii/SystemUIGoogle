package com.android.p012wm.shell.startingsurface;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$SingleColorTester */
public final class SplashscreenContentDrawer$DrawableColorTester$SingleColorTester implements SplashscreenContentDrawer$DrawableColorTester$ColorTester {
    public final ColorDrawable mColorDrawable;

    public final boolean isComplexColor() {
        return false;
    }

    public final int getDominantColor() {
        return this.mColorDrawable.getColor();
    }

    public final boolean isGrayscale() {
        int color = this.mColorDrawable.getColor();
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        if (red == green && green == blue) {
            return true;
        }
        return false;
    }

    public final float passFilterRatio() {
        return (float) (this.mColorDrawable.getAlpha() / 255);
    }

    public SplashscreenContentDrawer$DrawableColorTester$SingleColorTester(ColorDrawable colorDrawable) {
        this.mColorDrawable = colorDrawable;
    }
}
