package com.android.systemui.statusbar.notification;

import android.graphics.Bitmap;
import android.graphics.Rect;
import androidx.palette.graphics.Palette;
import java.util.Collections;
import java.util.Objects;

public final class MediaNotificationProcessor {
    public static boolean isWhiteOrBlack(float[] fArr) {
        boolean z;
        boolean z2;
        if (fArr[2] <= 0.08f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return true;
        }
        if (fArr[2] >= 0.9f) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        return false;
    }

    public static Palette.Swatch findBackgroundSwatch(Bitmap bitmap) {
        Palette.Builder builder = new Palette.Builder(bitmap);
        int width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight();
        if (builder.mBitmap != null) {
            if (builder.mRegion == null) {
                builder.mRegion = new Rect();
            }
            builder.mRegion.set(0, 0, builder.mBitmap.getWidth(), builder.mBitmap.getHeight());
            if (!builder.mRegion.intersect(0, 0, width, height)) {
                throw new IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
            }
        }
        builder.mFilters.clear();
        builder.mResizeArea = 22500;
        builder.mResizeMaxDimension = -1;
        Palette generate = builder.generate();
        Palette.Swatch swatch = generate.mDominantSwatch;
        if (swatch == null) {
            return new Palette.Swatch(-1, 100);
        }
        if (!isWhiteOrBlack(swatch.getHsl())) {
            return swatch;
        }
        float f = -1.0f;
        Palette.Swatch swatch2 = null;
        for (T t : Collections.unmodifiableList(generate.mSwatches)) {
            if (t != swatch) {
                Objects.requireNonNull(t);
                if (((float) t.mPopulation) > f && !isWhiteOrBlack(t.getHsl())) {
                    f = (float) t.mPopulation;
                    swatch2 = t;
                }
            }
        }
        if (swatch2 != null && ((float) swatch.mPopulation) / f <= 2.5f) {
            return swatch2;
        }
        return swatch;
    }
}
