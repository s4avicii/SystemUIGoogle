package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import androidx.core.graphics.ColorUtils;
import androidx.leanback.R$string;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.resources.MaterialAttributes;

public final class ElevationOverlayProvider {
    public static final int OVERLAY_ACCENT_COLOR_ALPHA = ((int) Math.round(5.1000000000000005d));
    public final int colorSurface;
    public final float displayDensity;
    public final int elevationOverlayAccentColor;
    public final int elevationOverlayColor;
    public final boolean elevationOverlayEnabled;

    public final int compositeOverlayIfNeeded(int i, float f) {
        boolean z;
        float f2;
        int i2;
        if (this.elevationOverlayEnabled) {
            if (ColorUtils.setAlphaComponent(i, 255) == this.colorSurface) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                float f3 = this.displayDensity;
                if (f3 <= 0.0f || f <= 0.0f) {
                    f2 = 0.0f;
                } else {
                    f2 = Math.min(((((float) Math.log1p((double) (f / f3))) * 4.5f) + 2.0f) / 100.0f, 1.0f);
                }
                int alpha = Color.alpha(i);
                int layer = R$string.layer(ColorUtils.setAlphaComponent(i, 255), this.elevationOverlayColor, f2);
                if (f2 > 0.0f && (i2 = this.elevationOverlayAccentColor) != 0) {
                    layer = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, OVERLAY_ACCENT_COLOR_ALPHA), layer);
                }
                return ColorUtils.setAlphaComponent(layer, alpha);
            }
        }
        return i;
    }

    public ElevationOverlayProvider(Context context) {
        boolean z;
        int i;
        int i2;
        TypedValue resolve = MaterialAttributes.resolve(context, C1777R.attr.elevationOverlayEnabled);
        int i3 = 0;
        if (resolve == null || resolve.type != 18 || resolve.data == 0) {
            z = false;
        } else {
            z = true;
        }
        TypedValue resolve2 = MaterialAttributes.resolve(context, C1777R.attr.elevationOverlayColor);
        if (resolve2 != null) {
            i = resolve2.data;
        } else {
            i = 0;
        }
        TypedValue resolve3 = MaterialAttributes.resolve(context, C1777R.attr.elevationOverlayAccentColor);
        if (resolve3 != null) {
            i2 = resolve3.data;
        } else {
            i2 = 0;
        }
        TypedValue resolve4 = MaterialAttributes.resolve(context, C1777R.attr.colorSurface);
        i3 = resolve4 != null ? resolve4.data : i3;
        float f = context.getResources().getDisplayMetrics().density;
        this.elevationOverlayEnabled = z;
        this.elevationOverlayColor = i;
        this.elevationOverlayAccentColor = i2;
        this.colorSurface = i3;
        this.displayDensity = f;
    }
}
