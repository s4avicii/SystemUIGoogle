package com.android.p012wm.shell.startingsurface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$ImmobileIconDrawable */
public final class SplashscreenIconDrawableFactory$ImmobileIconDrawable extends Drawable {
    public Bitmap mIconBitmap;
    public final Matrix mMatrix;
    public final Paint mPaint = new Paint(7);

    public final int getOpacity() {
        return 1;
    }

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public final void draw(Canvas canvas) {
        synchronized (this.mPaint) {
            Bitmap bitmap = this.mIconBitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, this.mMatrix, this.mPaint);
            } else {
                invalidateSelf();
            }
        }
    }

    public SplashscreenIconDrawableFactory$ImmobileIconDrawable(Drawable drawable, int i, int i2, Handler handler) {
        Matrix matrix = new Matrix();
        this.mMatrix = matrix;
        float f = ((float) i2) / ((float) i);
        matrix.setScale(f, f);
        handler.post(new C1929xa76c91d0(this, drawable, i));
    }
}
