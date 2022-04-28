package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.util.Objects;

public final class CircularProgressDrawable extends Drawable implements Animatable {
    public static final int[] COLORS = {-16777216};
    public static final LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final FastOutSlowInInterpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    public ValueAnimator mAnimator;
    public boolean mFinishing;
    public Resources mResources;
    public final Ring mRing;
    public float mRotation;
    public float mRotationCount;

    public final int getOpacity() {
        return -3;
    }

    public static class Ring {
        public int mAlpha;
        public Path mArrow;
        public int mArrowHeight;
        public final Paint mArrowPaint;
        public float mArrowScale;
        public int mArrowWidth;
        public final Paint mCirclePaint;
        public int mColorIndex;
        public int[] mColors;
        public int mCurrentColor;
        public float mEndTrim;
        public final Paint mPaint;
        public float mRingCenterRadius;
        public float mRotation;
        public boolean mShowArrow;
        public float mStartTrim;
        public float mStartingEndTrim;
        public float mStartingRotation;
        public float mStartingStartTrim;
        public float mStrokeWidth;
        public final RectF mTempBounds = new RectF();

        public Ring() {
            Paint paint = new Paint();
            this.mPaint = paint;
            Paint paint2 = new Paint();
            this.mArrowPaint = paint2;
            Paint paint3 = new Paint();
            this.mCirclePaint = paint3;
            this.mStartTrim = 0.0f;
            this.mEndTrim = 0.0f;
            this.mRotation = 0.0f;
            this.mStrokeWidth = 5.0f;
            this.mArrowScale = 1.0f;
            this.mAlpha = 255;
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAntiAlias(true);
            paint3.setColor(0);
        }
    }

    public static void updateRingColor(float f, Ring ring) {
        if (f > 0.75f) {
            float f2 = (f - 0.75f) / 0.25f;
            Objects.requireNonNull(ring);
            int[] iArr = ring.mColors;
            int i = ring.mColorIndex;
            int i2 = iArr[i];
            int i3 = iArr[(i + 1) % iArr.length];
            int i4 = (i2 >> 24) & 255;
            int i5 = (i2 >> 16) & 255;
            int i6 = (i2 >> 8) & 255;
            int i7 = i2 & 255;
            ring.mCurrentColor = (i7 + ((int) (f2 * ((float) ((i3 & 255) - i7))))) | ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f2))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f2))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f2))) << 8);
            return;
        }
        Objects.requireNonNull(ring);
        ring.mCurrentColor = ring.mColors[ring.mColorIndex];
    }

    public final void applyTransformation(float f, Ring ring, boolean z) {
        float f2;
        float f3;
        if (this.mFinishing) {
            updateRingColor(f, ring);
            float f4 = ring.mStartingStartTrim;
            float f5 = ring.mStartingEndTrim;
            ring.mStartTrim = (((f5 - 0.01f) - f4) * f) + f4;
            ring.mEndTrim = f5;
            float f6 = ring.mStartingRotation;
            ring.mRotation = MotionController$$ExternalSyntheticOutline0.m7m((float) (Math.floor((double) (ring.mStartingRotation / 0.8f)) + 1.0d), f6, f, f6);
        } else if (f != 1.0f || z) {
            Objects.requireNonNull(ring);
            float f7 = ring.mStartingRotation;
            if (f < 0.5f) {
                f2 = ring.mStartingStartTrim;
                f3 = (MATERIAL_INTERPOLATOR.getInterpolation(f / 0.5f) * 0.79f) + 0.01f + f2;
            } else {
                float f8 = ring.mStartingStartTrim + 0.79f;
                float f9 = f8;
                f2 = f8 - (((1.0f - MATERIAL_INTERPOLATOR.getInterpolation((f - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
                f3 = f9;
            }
            ring.mStartTrim = f2;
            ring.mEndTrim = f3;
            ring.mRotation = (0.20999998f * f) + f7;
            this.mRotation = (f + this.mRotationCount) * 216.0f;
        }
    }

    public final int getAlpha() {
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        return ring.mAlpha;
    }

    public final boolean isRunning() {
        return this.mAnimator.isRunning();
    }

    public final void setAlpha(int i) {
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        ring.mAlpha = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        ring.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public final void start() {
        this.mAnimator.cancel();
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        ring.mStartingStartTrim = ring.mStartTrim;
        ring.mStartingEndTrim = ring.mEndTrim;
        ring.mStartingRotation = ring.mRotation;
        Ring ring2 = this.mRing;
        Objects.requireNonNull(ring2);
        float f = ring2.mEndTrim;
        Ring ring3 = this.mRing;
        Objects.requireNonNull(ring3);
        if (f != ring3.mStartTrim) {
            this.mFinishing = true;
            this.mAnimator.setDuration(666);
            this.mAnimator.start();
            return;
        }
        Ring ring4 = this.mRing;
        Objects.requireNonNull(ring4);
        ring4.mColorIndex = 0;
        ring4.mCurrentColor = ring4.mColors[0];
        Ring ring5 = this.mRing;
        Objects.requireNonNull(ring5);
        ring5.mStartingStartTrim = 0.0f;
        ring5.mStartingEndTrim = 0.0f;
        ring5.mStartingRotation = 0.0f;
        ring5.mStartTrim = 0.0f;
        ring5.mEndTrim = 0.0f;
        ring5.mRotation = 0.0f;
        this.mAnimator.setDuration(1332);
        this.mAnimator.start();
    }

    public final void stop() {
        this.mAnimator.cancel();
        this.mRotation = 0.0f;
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        if (ring.mShowArrow) {
            ring.mShowArrow = false;
        }
        Ring ring2 = this.mRing;
        Objects.requireNonNull(ring2);
        ring2.mColorIndex = 0;
        ring2.mCurrentColor = ring2.mColors[0];
        Ring ring3 = this.mRing;
        Objects.requireNonNull(ring3);
        ring3.mStartingStartTrim = 0.0f;
        ring3.mStartingEndTrim = 0.0f;
        ring3.mStartingRotation = 0.0f;
        ring3.mStartTrim = 0.0f;
        ring3.mEndTrim = 0.0f;
        ring3.mRotation = 0.0f;
        invalidateSelf();
    }

    public CircularProgressDrawable(Context context) {
        Objects.requireNonNull(context);
        this.mResources = context.getResources();
        final Ring ring = new Ring();
        this.mRing = ring;
        int[] iArr = COLORS;
        ring.mColors = iArr;
        ring.mColorIndex = 0;
        ring.mCurrentColor = iArr[0];
        ring.mStrokeWidth = 2.5f;
        ring.mPaint.setStrokeWidth(2.5f);
        invalidateSelf();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularProgressDrawable circularProgressDrawable = CircularProgressDrawable.this;
                Ring ring = ring;
                Objects.requireNonNull(circularProgressDrawable);
                CircularProgressDrawable.updateRingColor(floatValue, ring);
                CircularProgressDrawable.this.applyTransformation(floatValue, ring, false);
                CircularProgressDrawable.this.invalidateSelf();
            }
        });
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat.addListener(new Animator.AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.applyTransformation(1.0f, ring, true);
                Ring ring = ring;
                Objects.requireNonNull(ring);
                ring.mStartingStartTrim = ring.mStartTrim;
                ring.mStartingEndTrim = ring.mEndTrim;
                ring.mStartingRotation = ring.mRotation;
                Ring ring2 = ring;
                Objects.requireNonNull(ring2);
                int[] iArr = ring2.mColors;
                int length = (ring2.mColorIndex + 1) % iArr.length;
                ring2.mColorIndex = length;
                ring2.mCurrentColor = iArr[length];
                CircularProgressDrawable circularProgressDrawable = CircularProgressDrawable.this;
                if (circularProgressDrawable.mFinishing) {
                    circularProgressDrawable.mFinishing = false;
                    animator.cancel();
                    animator.setDuration(1332);
                    animator.start();
                    Ring ring3 = ring;
                    Objects.requireNonNull(ring3);
                    if (ring3.mShowArrow) {
                        ring3.mShowArrow = false;
                        return;
                    }
                    return;
                }
                circularProgressDrawable.mRotationCount += 1.0f;
            }

            public final void onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.mRotationCount = 0.0f;
            }
        });
        this.mAnimator = ofFloat;
    }

    public final void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        Ring ring = this.mRing;
        Objects.requireNonNull(ring);
        RectF rectF = ring.mTempBounds;
        float f = ring.mRingCenterRadius;
        float f2 = (ring.mStrokeWidth / 2.0f) + f;
        if (f <= 0.0f) {
            f2 = (((float) Math.min(bounds.width(), bounds.height())) / 2.0f) - Math.max((((float) ring.mArrowWidth) * ring.mArrowScale) / 2.0f, ring.mStrokeWidth / 2.0f);
        }
        rectF.set(((float) bounds.centerX()) - f2, ((float) bounds.centerY()) - f2, ((float) bounds.centerX()) + f2, ((float) bounds.centerY()) + f2);
        float f3 = ring.mStartTrim;
        float f4 = ring.mRotation;
        float f5 = (f3 + f4) * 360.0f;
        float f6 = ((ring.mEndTrim + f4) * 360.0f) - f5;
        ring.mPaint.setColor(ring.mCurrentColor);
        ring.mPaint.setAlpha(ring.mAlpha);
        float f7 = ring.mStrokeWidth / 2.0f;
        rectF.inset(f7, f7);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, ring.mCirclePaint);
        float f8 = -f7;
        rectF.inset(f8, f8);
        canvas.drawArc(rectF, f5, f6, false, ring.mPaint);
        if (ring.mShowArrow) {
            Path path = ring.mArrow;
            if (path == null) {
                Path path2 = new Path();
                ring.mArrow = path2;
                path2.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            ring.mArrow.moveTo(0.0f, 0.0f);
            ring.mArrow.lineTo(((float) ring.mArrowWidth) * ring.mArrowScale, 0.0f);
            Path path3 = ring.mArrow;
            float f9 = ring.mArrowScale;
            path3.lineTo((((float) ring.mArrowWidth) * f9) / 2.0f, ((float) ring.mArrowHeight) * f9);
            ring.mArrow.offset((rectF.centerX() + (Math.min(rectF.width(), rectF.height()) / 2.0f)) - ((((float) ring.mArrowWidth) * ring.mArrowScale) / 2.0f), (ring.mStrokeWidth / 2.0f) + rectF.centerY());
            ring.mArrow.close();
            ring.mArrowPaint.setColor(ring.mCurrentColor);
            ring.mArrowPaint.setAlpha(ring.mAlpha);
            canvas.save();
            canvas.rotate(f5 + f6, rectF.centerX(), rectF.centerY());
            canvas.drawPath(ring.mArrow, ring.mArrowPaint);
            canvas.restore();
        }
        canvas.restore();
    }
}
