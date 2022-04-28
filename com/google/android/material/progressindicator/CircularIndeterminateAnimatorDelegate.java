package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.leanback.R$string;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import java.util.Objects;

public final class CircularIndeterminateAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    public static final C20653 ANIMATION_FRACTION = new Property<CircularIndeterminateAnimatorDelegate, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = (CircularIndeterminateAnimatorDelegate) obj;
            Objects.requireNonNull(circularIndeterminateAnimatorDelegate);
            return Float.valueOf(circularIndeterminateAnimatorDelegate.animationFraction);
        }

        public final void set(Object obj, Object obj2) {
            ((CircularIndeterminateAnimatorDelegate) obj).setAnimationFraction(((Float) obj2).floatValue());
        }
    };
    public static final C20664 COMPLETE_END_FRACTION = new Property<CircularIndeterminateAnimatorDelegate, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = (CircularIndeterminateAnimatorDelegate) obj;
            Objects.requireNonNull(circularIndeterminateAnimatorDelegate);
            return Float.valueOf(circularIndeterminateAnimatorDelegate.completeEndFraction);
        }

        public final void set(Object obj, Object obj2) {
            CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = (CircularIndeterminateAnimatorDelegate) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(circularIndeterminateAnimatorDelegate);
            circularIndeterminateAnimatorDelegate.completeEndFraction = floatValue;
        }
    };
    public static final int[] DELAY_TO_COLLAPSE_IN_MS = {667, 2017, 3367, 4717};
    public static final int[] DELAY_TO_EXPAND_IN_MS = {0, 1350, 2700, 4050};
    public static final int[] DELAY_TO_FADE_IN_MS = {1000, 2350, 3700, 5050};
    public float animationFraction;
    public ObjectAnimator animator;
    public Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    public final CircularProgressIndicatorSpec baseSpec;
    public ObjectAnimator completeEndAnimator;
    public float completeEndFraction;
    public int indicatorColorIndexOffset = 0;
    public final FastOutSlowInInterpolator interpolator;

    public CircularIndeterminateAnimatorDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.baseSpec = circularProgressIndicatorSpec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    public void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        int[] iArr = this.segmentColors;
        int i = this.baseSpec.indicatorColors[0];
        IndeterminateDrawable indeterminateDrawable = this.drawable;
        Objects.requireNonNull(indeterminateDrawable);
        iArr[0] = R$string.compositeARGBWithAlpha(i, indeterminateDrawable.totalAlpha);
        this.completeEndFraction = 0.0f;
    }

    public final void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    public final void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public final void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.start();
            } else {
                cancelAnimatorImmediately();
            }
        }
    }

    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        int i = (int) (5400.0f * f);
        float[] fArr = this.segmentPositions;
        float f2 = f * 1520.0f;
        fArr[0] = -20.0f + f2;
        fArr[1] = f2;
        for (int i2 = 0; i2 < 4; i2++) {
            float f3 = (float) 667;
            float[] fArr2 = this.segmentPositions;
            fArr2[1] = (this.interpolator.getInterpolation(((float) (i - DELAY_TO_EXPAND_IN_MS[i2])) / f3) * 250.0f) + fArr2[1];
            float f4 = ((float) (i - DELAY_TO_COLLAPSE_IN_MS[i2])) / f3;
            float[] fArr3 = this.segmentPositions;
            fArr3[0] = (this.interpolator.getInterpolation(f4) * 250.0f) + fArr3[0];
        }
        float[] fArr4 = this.segmentPositions;
        fArr4[0] = ((fArr4[1] - fArr4[0]) * this.completeEndFraction) + fArr4[0];
        fArr4[0] = fArr4[0] / 360.0f;
        fArr4[1] = fArr4[1] / 360.0f;
        int i3 = 0;
        while (true) {
            if (i3 >= 4) {
                break;
            }
            float f5 = ((float) (i - DELAY_TO_FADE_IN_MS[i3])) / ((float) 333);
            if (f5 >= 0.0f && f5 <= 1.0f) {
                int i4 = i3 + this.indicatorColorIndexOffset;
                int[] iArr = this.baseSpec.indicatorColors;
                int length = i4 % iArr.length;
                int i5 = iArr[length];
                IndeterminateDrawable indeterminateDrawable = this.drawable;
                Objects.requireNonNull(indeterminateDrawable);
                int compositeARGBWithAlpha = R$string.compositeARGBWithAlpha(i5, indeterminateDrawable.totalAlpha);
                int i6 = this.baseSpec.indicatorColors[(length + 1) % iArr.length];
                IndeterminateDrawable indeterminateDrawable2 = this.drawable;
                Objects.requireNonNull(indeterminateDrawable2);
                this.segmentColors[0] = ArgbEvaluatorCompat.evaluate(this.interpolator.getInterpolation(f5), Integer.valueOf(compositeARGBWithAlpha), Integer.valueOf(R$string.compositeARGBWithAlpha(i6, indeterminateDrawable2.totalAlpha))).intValue();
                break;
            }
            i3++;
        }
        this.drawable.invalidateSelf();
    }

    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(5400);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
                    circularIndeterminateAnimatorDelegate.indicatorColorIndexOffset = (circularIndeterminateAnimatorDelegate.indicatorColorIndexOffset + 4) % circularIndeterminateAnimatorDelegate.baseSpec.indicatorColors.length;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, new float[]{0.0f, 1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(333);
            this.completeEndAnimator.setInterpolator(this.interpolator);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CircularIndeterminateAnimatorDelegate.this.cancelAnimatorImmediately();
                    CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
                    Animatable2Compat.AnimationCallback animationCallback = circularIndeterminateAnimatorDelegate.animatorCompleteCallback;
                    if (animationCallback != null) {
                        animationCallback.onAnimationEnd(circularIndeterminateAnimatorDelegate.drawable);
                    }
                }
            });
        }
        resetPropertiesForNewStart();
        this.animator.start();
    }

    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.C20613 r1) {
        this.animatorCompleteCallback = r1;
    }
}
