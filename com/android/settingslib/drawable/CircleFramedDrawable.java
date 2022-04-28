package com.android.settingslib.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;

public final class CircleFramedDrawable extends Drawable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Bitmap mBitmap;
    public RectF mDstRect;
    public Paint mIconPaint;
    public float mScale = 1.0f;
    public final int mSize;
    public Rect mSrcRect;

    public final int getOpacity() {
        return -3;
    }

    public final void setAlpha(int i) {
    }

    public final void draw(Canvas canvas) {
        float f = this.mScale;
        int i = this.mSize;
        float f2 = (((float) i) - (f * ((float) i))) / 2.0f;
        this.mDstRect.set(f2, f2, ((float) i) - f2, ((float) i) - f2);
        canvas.drawBitmap(this.mBitmap, this.mSrcRect, this.mDstRect, this.mIconPaint);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        if (this.mIconPaint == null) {
            this.mIconPaint = new Paint();
        }
        this.mIconPaint.setColorFilter(colorFilter);
    }

    public CircleFramedDrawable(Bitmap bitmap, int i) {
        this.mSize = i;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mBitmap = createBitmap;
        Canvas canvas = new Canvas(createBitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int min = Math.min(width, height);
        Rect rect = new Rect((width - min) / 2, (height - min) / 2, min, min);
        RectF rectF = new RectF(0.0f, 0.0f, (float) i, (float) i);
        Path path = new Path();
        path.addArc(rectF, 0.0f, 360.0f);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16777216);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        paint.setXfermode((Xfermode) null);
        this.mSrcRect = new Rect(0, 0, i, i);
        this.mDstRect = new RectF(0.0f, 0.0f, (float) i, (float) i);
    }

    public final int getIntrinsicHeight() {
        return this.mSize;
    }

    public final int getIntrinsicWidth() {
        return this.mSize;
    }
}
