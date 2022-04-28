package com.android.launcher3.icons;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.DrawableWrapper;

public class RoundDrawableWrapper extends DrawableWrapper {
    public final Path mClipPath = new Path();
    public final float mRoundedCornersRadius;
    public final RectF mTempRect = new RectF();

    public final void onBoundsChange(Rect rect) {
        this.mTempRect.set(getBounds());
        this.mClipPath.reset();
        Path path = this.mClipPath;
        RectF rectF = this.mTempRect;
        float f = this.mRoundedCornersRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CCW);
        super.onBoundsChange(rect);
    }

    public RoundDrawableWrapper(ColorDrawable colorDrawable, float f) {
        super(colorDrawable);
        this.mRoundedCornersRadius = f;
    }

    public final void draw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(this.mClipPath);
        super.draw(canvas);
        canvas.restoreToCount(save);
    }
}
