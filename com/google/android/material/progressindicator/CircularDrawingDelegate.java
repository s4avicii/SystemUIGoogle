package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.leanback.R$string;
import java.util.Objects;

public final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    public float adjustedRadius;
    public int arcDirectionFactor = 1;
    public float displayedCornerRadius;
    public float displayedTrackThickness;

    public final void adjustCanvas(Canvas canvas, float f) {
        int i;
        S s = this.spec;
        float f2 = (((float) ((CircularProgressIndicatorSpec) s).indicatorSize) / 2.0f) + ((float) ((CircularProgressIndicatorSpec) s).indicatorInset);
        canvas.translate(f2, f2);
        canvas.rotate(-90.0f);
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        S s2 = this.spec;
        if (((CircularProgressIndicatorSpec) s2).indicatorDirection == 0) {
            i = 1;
        } else {
            i = -1;
        }
        this.arcDirectionFactor = i;
        this.displayedTrackThickness = ((float) ((CircularProgressIndicatorSpec) s2).trackThickness) * f;
        this.displayedCornerRadius = ((float) ((CircularProgressIndicatorSpec) s2).trackCornerRadius) * f;
        this.adjustedRadius = ((float) (((CircularProgressIndicatorSpec) s2).indicatorSize - ((CircularProgressIndicatorSpec) s2).trackThickness)) / 2.0f;
        if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
            this.adjustedRadius = (((1.0f - f) * ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f) + this.adjustedRadius;
        } else if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 1) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 2)) {
            this.adjustedRadius -= ((1.0f - f) * ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f;
        }
    }

    public final void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i) {
        if (f != f2) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setAntiAlias(true);
            paint.setColor(i);
            paint.setStrokeWidth(this.displayedTrackThickness);
            float f3 = (float) this.arcDirectionFactor;
            float f4 = f * 360.0f * f3;
            if (f2 < f) {
                f2 += 1.0f;
            }
            float f5 = (f2 - f) * 360.0f * f3;
            float f6 = this.adjustedRadius;
            float f7 = -f6;
            canvas.drawArc(new RectF(f7, f7, f6, f6), f4, f5, false, paint);
            if (this.displayedCornerRadius > 0.0f && Math.abs(f5) < 360.0f) {
                paint.setStyle(Paint.Style.FILL);
                float f8 = this.displayedTrackThickness;
                float f9 = this.displayedCornerRadius;
                canvas.save();
                canvas.rotate(f4);
                float f10 = this.adjustedRadius;
                float f11 = f8 / 2.0f;
                canvas.drawRoundRect(new RectF(f10 - f11, f9, f10 + f11, -f9), f9, f9, paint);
                canvas.restore();
                float f12 = this.displayedTrackThickness;
                float f13 = this.displayedCornerRadius;
                canvas.save();
                canvas.rotate(f4 + f5);
                float f14 = this.adjustedRadius;
                float f15 = f12 / 2.0f;
                canvas.drawRoundRect(new RectF(f14 - f15, f13, f14 + f15, -f13), f13, f13, paint);
                canvas.restore();
            }
        }
    }

    public final void fillTrack(Canvas canvas, Paint paint) {
        int i = ((CircularProgressIndicatorSpec) this.spec).trackColor;
        DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = this.drawable;
        Objects.requireNonNull(drawableWithAnimatedVisibilityChange);
        int compositeARGBWithAlpha = R$string.compositeARGBWithAlpha(i, drawableWithAnimatedVisibilityChange.totalAlpha);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(compositeARGBWithAlpha);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f = this.adjustedRadius;
        canvas.drawArc(new RectF(-f, -f, f, f), 0.0f, 360.0f, false, paint);
    }

    public final int getPreferredHeight() {
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = (CircularProgressIndicatorSpec) this.spec;
        return (circularProgressIndicatorSpec.indicatorInset * 2) + circularProgressIndicatorSpec.indicatorSize;
    }

    public final int getPreferredWidth() {
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = (CircularProgressIndicatorSpec) this.spec;
        return (circularProgressIndicatorSpec.indicatorInset * 2) + circularProgressIndicatorSpec.indicatorSize;
    }

    public CircularDrawingDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }
}
