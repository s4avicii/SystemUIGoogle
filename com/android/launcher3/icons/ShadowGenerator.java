package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

public final class ShadowGenerator {
    public final Paint mBlurPaint = new Paint(3);
    public final BlurMaskFilter mDefaultBlurMaskFilter;
    public final Paint mDrawPaint = new Paint(3);
    public final int mIconSize;

    public final synchronized void recreateIcon(Bitmap bitmap, Canvas canvas) {
        recreateIcon(bitmap, this.mDefaultBlurMaskFilter, canvas);
    }

    public ShadowGenerator(int i) {
        this.mIconSize = i;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(((float) i) * 0.035f, BlurMaskFilter.Blur.NORMAL);
    }

    public final synchronized void recreateIcon(Bitmap bitmap, BlurMaskFilter blurMaskFilter, Canvas canvas) {
        int[] iArr = new int[2];
        this.mBlurPaint.setMaskFilter(blurMaskFilter);
        Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, iArr);
        this.mDrawPaint.setAlpha(25);
        canvas.drawBitmap(extractAlpha, (float) iArr[0], (float) iArr[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(7);
        canvas.drawBitmap(extractAlpha, (float) iArr[0], (((float) this.mIconSize) * 0.020833334f) + ((float) iArr[1]), this.mDrawPaint);
        this.mDrawPaint.setAlpha(255);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mDrawPaint);
    }
}
