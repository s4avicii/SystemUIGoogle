package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.OvershootInterpolator;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2;

public final class UdfpsEnrollProgressBarDrawable extends Drawable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAfterFirstTouch;
    public final Paint mBackgroundPaint;
    public ValueAnimator mCheckmarkAnimator;
    public final Drawable mCheckmarkDrawable;
    public final OvershootInterpolator mCheckmarkInterpolator;
    public float mCheckmarkScale = 0.0f;
    public final ScreenshotView$$ExternalSyntheticLambda2 mCheckmarkUpdateListener;
    public boolean mComplete = false;
    public ValueAnimator mFillColorAnimator;
    public final UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0 mFillColorUpdateListener;
    public final Paint mFillPaint;
    public final int mHelpColor;
    public float mProgress = 0.0f;
    public ValueAnimator mProgressAnimator;
    public final int mProgressColor;
    public final ScreenshotView$$ExternalSyntheticLambda1 mProgressUpdateListener;
    public int mRemainingSteps = 0;
    public boolean mShowingHelp = false;
    public final float mStrokeWidthPx;
    public int mTotalSteps = 0;

    public final int getOpacity() {
        return 0;
    }

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public final void updateState(int i, int i2, boolean z) {
        int i3;
        int i4;
        float f;
        if (!(this.mRemainingSteps == i && this.mTotalSteps == i2)) {
            this.mRemainingSteps = i;
            this.mTotalSteps = i2;
            int max = Math.max(0, i2 - i);
            boolean z2 = this.mAfterFirstTouch;
            if (z2) {
                max++;
            }
            if (z2) {
                i4 = this.mTotalSteps + 1;
            } else {
                i4 = this.mTotalSteps;
            }
            float min = Math.min(1.0f, ((float) max) / ((float) i4));
            ValueAnimator valueAnimator = this.mProgressAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mProgressAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mProgress, min});
            this.mProgressAnimator = ofFloat;
            ofFloat.setDuration(400);
            this.mProgressAnimator.addUpdateListener(this.mProgressUpdateListener);
            this.mProgressAnimator.start();
            if (i == 0) {
                if (!this.mComplete) {
                    this.mComplete = true;
                    ValueAnimator valueAnimator2 = this.mCheckmarkAnimator;
                    if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                        this.mCheckmarkAnimator.cancel();
                    }
                    ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{this.mCheckmarkScale, 1.0f});
                    this.mCheckmarkAnimator = ofFloat2;
                    ofFloat2.setStartDelay(200);
                    this.mCheckmarkAnimator.setDuration(300);
                    this.mCheckmarkAnimator.setInterpolator(this.mCheckmarkInterpolator);
                    this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
                    this.mCheckmarkAnimator.start();
                }
            } else if (i > 0 && this.mComplete) {
                this.mComplete = false;
                ValueAnimator valueAnimator3 = this.mCheckmarkAnimator;
                if (valueAnimator3 != null) {
                    f = valueAnimator3.getAnimatedFraction();
                } else {
                    f = 0.0f;
                }
                long round = (long) Math.round(f * 200.0f);
                ValueAnimator valueAnimator4 = this.mCheckmarkAnimator;
                if (valueAnimator4 != null && valueAnimator4.isRunning()) {
                    this.mCheckmarkAnimator.cancel();
                }
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{this.mCheckmarkScale, 0.0f});
                this.mCheckmarkAnimator = ofFloat3;
                ofFloat3.setDuration(round);
                this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
                this.mCheckmarkAnimator.start();
            }
        }
        if (this.mShowingHelp != z) {
            this.mShowingHelp = z;
            ValueAnimator valueAnimator5 = this.mFillColorAnimator;
            if (valueAnimator5 != null && valueAnimator5.isRunning()) {
                this.mFillColorAnimator.cancel();
            }
            if (z) {
                i3 = this.mHelpColor;
            } else {
                i3 = this.mProgressColor;
            }
            ValueAnimator ofArgb = ValueAnimator.ofArgb(new int[]{this.mFillPaint.getColor(), i3});
            this.mFillColorAnimator = ofArgb;
            ofArgb.setDuration(200);
            this.mFillColorAnimator.addUpdateListener(this.mFillColorUpdateListener);
            this.mFillColorAnimator.start();
        }
    }

    public UdfpsEnrollProgressBarDrawable(Context context) {
        float f = (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * 12.0f;
        this.mStrokeWidthPx = f;
        int color = context.getColor(C1777R.color.udfps_enroll_progress);
        this.mProgressColor = color;
        this.mHelpColor = context.getColor(C1777R.color.udfps_enroll_progress_help);
        Drawable drawable = context.getDrawable(C1777R.C1778drawable.udfps_enroll_checkmark);
        this.mCheckmarkDrawable = drawable;
        drawable.mutate();
        this.mCheckmarkInterpolator = new OvershootInterpolator();
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setStrokeWidth(f);
        paint.setColor(context.getColor(C1777R.color.udfps_moving_target_fill));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.mFillPaint = paint2;
        paint2.setStrokeWidth(f);
        paint2.setColor(color);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.mProgressUpdateListener = new ScreenshotView$$ExternalSyntheticLambda1(this, 1);
        this.mFillColorUpdateListener = new UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0(this);
        this.mCheckmarkUpdateListener = new ScreenshotView$$ExternalSyntheticLambda2(this, 1);
    }

    public final void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-90.0f, (float) getBounds().centerX(), (float) getBounds().centerY());
        float f = this.mStrokeWidthPx / 2.0f;
        if (this.mProgress < 1.0f) {
            canvas.drawArc(f, f, ((float) getBounds().right) - f, ((float) getBounds().bottom) - f, 0.0f, 360.0f, false, this.mBackgroundPaint);
        }
        if (this.mProgress > 0.0f) {
            canvas.drawArc(f, f, ((float) getBounds().right) - f, ((float) getBounds().bottom) - f, 0.0f, this.mProgress * 360.0f, false, this.mFillPaint);
        }
        canvas.restore();
        if (this.mCheckmarkScale > 0.0f) {
            float sqrt = ((float) Math.sqrt(2.0d)) / 2.0f;
            float width = ((((float) getBounds().width()) - this.mStrokeWidthPx) / 2.0f) * sqrt;
            float height = ((((float) getBounds().height()) - this.mStrokeWidthPx) / 2.0f) * sqrt;
            float centerX = ((float) getBounds().centerX()) + width;
            float centerY = ((float) getBounds().centerY()) + height;
            float intrinsicWidth = (((float) this.mCheckmarkDrawable.getIntrinsicWidth()) / 2.0f) * this.mCheckmarkScale;
            float intrinsicHeight = (((float) this.mCheckmarkDrawable.getIntrinsicHeight()) / 2.0f) * this.mCheckmarkScale;
            this.mCheckmarkDrawable.setBounds(Math.round(centerX - intrinsicWidth), Math.round(centerY - intrinsicHeight), Math.round(centerX + intrinsicWidth), Math.round(centerY + intrinsicHeight));
            this.mCheckmarkDrawable.draw(canvas);
        }
    }
}
