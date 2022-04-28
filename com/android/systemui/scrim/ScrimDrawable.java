package com.android.systemui.scrim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;

public final class ScrimDrawable extends Drawable {
    public int mAlpha = 255;
    public int mBottomEdgePosition;
    public ValueAnimator mColorAnimation;
    public ConcaveInfo mConcaveInfo;
    public float mCornerRadius;
    public boolean mCornerRadiusEnabled;
    public int mMainColor;
    public int mMainColorTo;
    public final Paint mPaint;

    public static class ConcaveInfo {
        public final float[] mCornerRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        public final Path mPath = new Path();
        public float mPathOverlap;
    }

    public final int getOpacity() {
        return -3;
    }

    public final void draw(Canvas canvas) {
        this.mPaint.setColor(this.mMainColor);
        this.mPaint.setAlpha(this.mAlpha);
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo != null) {
            canvas.clipOutPath(concaveInfo.mPath);
            canvas.drawRect((float) getBounds().left, (float) getBounds().top, (float) getBounds().right, ((float) this.mBottomEdgePosition) + this.mConcaveInfo.mPathOverlap, this.mPaint);
        } else if (!this.mCornerRadiusEnabled || this.mCornerRadius <= 0.0f) {
            canvas.drawRect((float) getBounds().left, (float) getBounds().top, (float) getBounds().right, (float) getBounds().bottom, this.mPaint);
        } else {
            float f = this.mCornerRadius;
            canvas.drawRoundRect((float) getBounds().left, (float) getBounds().top, (float) getBounds().right, ((float) getBounds().bottom) + f, f, f, this.mPaint);
        }
    }

    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public final void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    public final void setColor(int i, boolean z) {
        if (i != this.mMainColorTo) {
            ValueAnimator valueAnimator = this.mColorAnimation;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mColorAnimation.cancel();
            }
            this.mMainColorTo = i;
            if (z) {
                int i2 = this.mMainColor;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setDuration(2000);
                ofFloat.addUpdateListener(new ScrimDrawable$$ExternalSyntheticLambda0(this, i2, i));
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator, boolean z) {
                        ScrimDrawable scrimDrawable = ScrimDrawable.this;
                        if (scrimDrawable.mColorAnimation == animator) {
                            scrimDrawable.mColorAnimation = null;
                        }
                    }
                });
                ofFloat.setInterpolator(new DecelerateInterpolator());
                ofFloat.start();
                this.mColorAnimation = ofFloat;
                return;
            }
            this.mMainColor = i;
            invalidateSelf();
        }
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public final void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public final void updatePath() {
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo != null) {
            concaveInfo.mPath.reset();
            int i = this.mBottomEdgePosition;
            ConcaveInfo concaveInfo2 = this.mConcaveInfo;
            concaveInfo2.mPath.addRoundRect((float) getBounds().left, (float) i, (float) getBounds().right, ((float) i) + concaveInfo2.mPathOverlap, this.mConcaveInfo.mCornerRadii, Path.Direction.CW);
        }
    }

    public ScrimDrawable() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    public final void onBoundsChange(Rect rect) {
        updatePath();
    }

    public final int getAlpha() {
        return this.mAlpha;
    }

    @VisibleForTesting
    public int getMainColor() {
        return this.mMainColor;
    }
}
