package com.android.settingslib.graph;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class BatteryMeterDrawableBase extends Drawable {
    public float getAspectRatio() {
        return 0.58f;
    }

    public final int getIntrinsicHeight() {
        return 0;
    }

    public final int getIntrinsicWidth() {
        return 0;
    }

    public final int getOpacity() {
        return 0;
    }

    public final void setAlpha(int i) {
    }

    public final void draw(Canvas canvas) {
        getBounds();
        float f = (float) 0;
        int aspectRatio = (0 - ((int) (getAspectRatio() * f))) / 2;
        Math.round(f * 0.0f);
        throw null;
    }

    public final void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        int i5 = getBounds().bottom;
        throw null;
    }

    public final boolean getPadding(Rect rect) {
        throw null;
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        throw null;
    }

    static {
        Class<BatteryMeterDrawableBase> cls = BatteryMeterDrawableBase.class;
    }
}
