package com.android.systemui.controls.p004ui;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* renamed from: com.android.systemui.controls.ui.CornerDrawable */
/* compiled from: CornerDrawable.kt */
public final class CornerDrawable extends DrawableWrapper {
    public final float cornerRadius;
    public final Path path;

    public final void setBounds(int i, int i2, int i3, int i4) {
        RectF rectF = new RectF((float) i, (float) i2, (float) i3, (float) i4);
        this.path.reset();
        Path path2 = this.path;
        float f = this.cornerRadius;
        path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        super.setBounds(i, i2, i3, i4);
    }

    public final void draw(Canvas canvas) {
        canvas.clipPath(this.path);
        super.draw(canvas);
    }

    public CornerDrawable(Drawable drawable, float f) {
        super(drawable);
        this.cornerRadius = f;
        Path path2 = new Path();
        this.path = path2;
        RectF rectF = new RectF(getBounds());
        path2.reset();
        path2.addRoundRect(rectF, f, f, Path.Direction.CW);
    }

    public final void setBounds(Rect rect) {
        RectF rectF = new RectF(rect);
        this.path.reset();
        Path path2 = this.path;
        float f = this.cornerRadius;
        path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        super.setBounds(rect);
    }
}
