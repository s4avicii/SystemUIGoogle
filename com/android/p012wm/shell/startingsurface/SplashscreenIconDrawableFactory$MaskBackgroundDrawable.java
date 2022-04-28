package com.android.p012wm.shell.startingsurface;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.PathParser;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$MaskBackgroundDrawable */
public class SplashscreenIconDrawableFactory$MaskBackgroundDrawable extends Drawable {
    public static Path sMask;
    public final Paint mBackgroundPaint;
    public final Matrix mMaskMatrix = new Matrix();
    public final Path mMaskScaleOnly = new Path(new Path(sMask));

    public final int getOpacity() {
        return 1;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void draw(Canvas canvas) {
        canvas.clipPath(this.mMaskScaleOnly);
        Paint paint = this.mBackgroundPaint;
        if (paint != null) {
            canvas.drawPath(this.mMaskScaleOnly, paint);
        }
    }

    public final void setAlpha(int i) {
        Paint paint = this.mBackgroundPaint;
        if (paint != null) {
            paint.setAlpha(i);
        }
    }

    public void updateLayerBounds(Rect rect) {
        this.mMaskMatrix.setScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskScaleOnly);
    }

    public SplashscreenIconDrawableFactory$MaskBackgroundDrawable(int i) {
        sMask = PathParser.createPathFromPathData(Resources.getSystem().getString(17039972));
        if (i != 0) {
            Paint paint = new Paint(7);
            this.mBackgroundPaint = paint;
            paint.setColor(i);
            paint.setStyle(Paint.Style.FILL);
            return;
        }
        this.mBackgroundPaint = null;
    }

    public final void onBoundsChange(Rect rect) {
        if (!rect.isEmpty()) {
            updateLayerBounds(rect);
        }
    }
}
