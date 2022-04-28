package com.android.p012wm.shell.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.animation.FlingAnimationUtils */
public final class FlingAnimationUtils {
    public AnimatorProperties mAnimatorProperties;
    public float mCachedStartGradient;
    public float mCachedVelocityFactor;
    public float mHighVelocityPxPerSecond;
    public PathInterpolator mInterpolator;
    public float mLinearOutSlowInX2;
    public float mMaxLengthSeconds;
    public float mMinVelocityPxPerSecond;
    public final float mSpeedUpFactor;
    public final float mY2;

    /* renamed from: com.android.wm.shell.animation.FlingAnimationUtils$AnimatorProperties */
    public static class AnimatorProperties {
        public Interpolator mInterpolator;
    }

    /* renamed from: com.android.wm.shell.animation.FlingAnimationUtils$Builder */
    public static class Builder {
        public final DisplayMetrics mDisplayMetrics;
        public float mMaxLengthSeconds;
        public float mSpeedUpFactor;
        public float mX2;
        public float mY2;

        public final Builder reset() {
            this.mMaxLengthSeconds = 0.0f;
            this.mSpeedUpFactor = 0.0f;
            this.mX2 = -1.0f;
            this.mY2 = 1.0f;
            return this;
        }

        public final FlingAnimationUtils build() {
            return new FlingAnimationUtils(this.mDisplayMetrics, this.mMaxLengthSeconds, this.mSpeedUpFactor, this.mX2, this.mY2);
        }

        public Builder(DisplayMetrics displayMetrics) {
            this.mDisplayMetrics = displayMetrics;
            reset();
        }
    }

    /* renamed from: com.android.wm.shell.animation.FlingAnimationUtils$InterpolatorInterpolator */
    public static final class InterpolatorInterpolator implements Interpolator {
        public Interpolator mCrossfader;
        public Interpolator mInterpolator1;
        public Interpolator mInterpolator2;

        public final float getInterpolation(float f) {
            float interpolation = this.mCrossfader.getInterpolation(f);
            float interpolation2 = this.mInterpolator1.getInterpolation(f);
            return (this.mInterpolator2.getInterpolation(f) * interpolation) + (interpolation2 * (1.0f - interpolation));
        }

        public InterpolatorInterpolator(VelocityInterpolator velocityInterpolator, PathInterpolator pathInterpolator, PathInterpolator pathInterpolator2) {
            this.mInterpolator1 = velocityInterpolator;
            this.mInterpolator2 = pathInterpolator;
            this.mCrossfader = pathInterpolator2;
        }
    }

    /* renamed from: com.android.wm.shell.animation.FlingAnimationUtils$VelocityInterpolator */
    public static final class VelocityInterpolator implements Interpolator {
        public float mDiff;
        public float mDurationSeconds;
        public float mVelocity;

        public final float getInterpolation(float f) {
            return ((f * this.mDurationSeconds) * this.mVelocity) / this.mDiff;
        }

        public VelocityInterpolator(float f, float f2, float f3) {
            this.mDurationSeconds = f;
            this.mVelocity = f2;
            this.mDiff = f3;
        }
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f) {
        this(displayMetrics, f, 0.0f, -1.0f, 1.0f);
    }

    public final void apply(ValueAnimator valueAnimator, float f, float f2, float f3) {
        apply(valueAnimator, f, f2, f3, Math.abs(f2 - f));
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f, float f2, float f3, float f4) {
        this.mAnimatorProperties = new AnimatorProperties();
        this.mCachedStartGradient = -1.0f;
        this.mCachedVelocityFactor = -1.0f;
        this.mMaxLengthSeconds = f;
        this.mSpeedUpFactor = f2;
        if (f3 < 0.0f) {
            this.mLinearOutSlowInX2 = (0.68f * f2) + ((1.0f - f2) * 0.35f);
        } else {
            this.mLinearOutSlowInX2 = f3;
        }
        this.mY2 = f4;
        float f5 = displayMetrics.density;
        this.mMinVelocityPxPerSecond = 250.0f * f5;
        this.mHighVelocityPxPerSecond = f5 * 3000.0f;
    }

    public final void apply(Animator animator, float f, float f2, float f3, float f4) {
        float f5;
        PathInterpolator pathInterpolator;
        float f6 = f2 - f;
        float sqrt = (float) (Math.sqrt((double) (Math.abs(f6) / f4)) * ((double) this.mMaxLengthSeconds));
        float abs = Math.abs(f6);
        float abs2 = Math.abs(f3);
        if (this.mSpeedUpFactor == 0.0f) {
            f5 = 1.0f;
        } else {
            f5 = Math.min(abs2 / 3000.0f, 1.0f);
        }
        float f7 = 1.0f - f5;
        float f8 = ((this.mY2 / this.mLinearOutSlowInX2) * f5) + (0.75f * f7);
        float f9 = (f8 * abs) / abs2;
        if (Float.isNaN(f5)) {
            Log.e("FlingAnimationUtils", "Invalid velocity factor", new Throwable());
            pathInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        } else {
            if (!(f8 == this.mCachedStartGradient && f5 == this.mCachedVelocityFactor)) {
                float f10 = f7 * this.mSpeedUpFactor;
                float f11 = f10 * f8;
                float f12 = this.mLinearOutSlowInX2;
                float f13 = this.mY2;
                try {
                    this.mInterpolator = new PathInterpolator(f10, f11, f12, f13);
                    this.mCachedStartGradient = f8;
                    this.mCachedVelocityFactor = f5;
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Illegal path with x1=" + f10 + " y1=" + f11 + " x2=" + f12 + " y2=" + f13, e);
                }
            }
            pathInterpolator = this.mInterpolator;
        }
        if (f9 <= sqrt) {
            this.mAnimatorProperties.mInterpolator = pathInterpolator;
            sqrt = f9;
        } else if (abs2 >= this.mMinVelocityPxPerSecond) {
            this.mAnimatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(sqrt, abs2, abs), pathInterpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            this.mAnimatorProperties.mInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        }
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        Objects.requireNonNull(animatorProperties);
        animator.setDuration((long) (sqrt * 1000.0f));
        animator.setInterpolator(animatorProperties.mInterpolator);
    }

    public final void applyDismissing(Animator animator, float f, float f2, float f3, float f4) {
        float f5 = f2 - f;
        float pow = (float) (Math.pow((double) (Math.abs(f5) / f4), 0.5d) * ((double) this.mMaxLengthSeconds));
        float abs = Math.abs(f5);
        float abs2 = Math.abs(f3);
        float f6 = this.mMinVelocityPxPerSecond;
        float max = Math.max(0.0f, Math.min(1.0f, (abs2 - f6) / (this.mHighVelocityPxPerSecond - f6)));
        float f7 = (max * 0.5f) + ((1.0f - max) * 0.4f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.0f, 0.0f, 0.5f, f7);
        float f8 = ((f7 / 0.5f) * abs) / abs2;
        if (f8 <= pow) {
            this.mAnimatorProperties.mInterpolator = pathInterpolator;
            pow = f8;
        } else if (abs2 >= this.mMinVelocityPxPerSecond) {
            this.mAnimatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(pow, abs2, abs), pathInterpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            this.mAnimatorProperties.mInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        }
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        Objects.requireNonNull(animatorProperties);
        animator.setDuration((long) (pow * 1000.0f));
        animator.setInterpolator(animatorProperties.mInterpolator);
    }
}
