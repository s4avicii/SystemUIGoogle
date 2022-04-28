package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.leanback.R$string;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import java.util.Arrays;
import java.util.Objects;

public final class LinearIndeterminateContiguousAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    public static final C20722 ANIMATION_FRACTION = new Property<LinearIndeterminateContiguousAnimatorDelegate, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            LinearIndeterminateContiguousAnimatorDelegate linearIndeterminateContiguousAnimatorDelegate = (LinearIndeterminateContiguousAnimatorDelegate) obj;
            Objects.requireNonNull(linearIndeterminateContiguousAnimatorDelegate);
            return Float.valueOf(linearIndeterminateContiguousAnimatorDelegate.animationFraction);
        }

        public final void set(Object obj, Object obj2) {
            ((LinearIndeterminateContiguousAnimatorDelegate) obj).setAnimationFraction(((Float) obj2).floatValue());
        }
    };
    public float animationFraction;
    public ObjectAnimator animator;
    public final LinearProgressIndicatorSpec baseSpec;
    public boolean dirtyColors;
    public FastOutSlowInInterpolator interpolator;
    public int newIndicatorColorIndex = 1;

    public LinearIndeterminateContiguousAnimatorDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(3);
        this.baseSpec = linearProgressIndicatorSpec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.C20613 r1) {
    }

    public final void requestCancelAnimatorAfterCurrentCycle() {
    }

    public void resetPropertiesForNewStart() {
        this.dirtyColors = true;
        this.newIndicatorColorIndex = 1;
        int[] iArr = this.segmentColors;
        int i = this.baseSpec.indicatorColors[0];
        IndeterminateDrawable indeterminateDrawable = this.drawable;
        Objects.requireNonNull(indeterminateDrawable);
        Arrays.fill(iArr, R$string.compositeARGBWithAlpha(i, indeterminateDrawable.totalAlpha));
    }

    public final void unregisterAnimatorsCompleteCallback() {
    }

    public final void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        float[] fArr = this.segmentPositions;
        fArr[0] = 0.0f;
        float f2 = ((float) (((int) (f * 333.0f)) - 0)) / ((float) 667);
        float interpolation = this.interpolator.getInterpolation(f2);
        fArr[2] = interpolation;
        fArr[1] = interpolation;
        float[] fArr2 = this.segmentPositions;
        float interpolation2 = this.interpolator.getInterpolation(f2 + 0.49925038f);
        fArr2[4] = interpolation2;
        fArr2[3] = interpolation2;
        float[] fArr3 = this.segmentPositions;
        fArr3[5] = 1.0f;
        if (this.dirtyColors && fArr3[3] < 1.0f) {
            int[] iArr = this.segmentColors;
            iArr[2] = iArr[1];
            iArr[1] = iArr[0];
            int i = this.baseSpec.indicatorColors[this.newIndicatorColorIndex];
            IndeterminateDrawable indeterminateDrawable = this.drawable;
            Objects.requireNonNull(indeterminateDrawable);
            iArr[0] = R$string.compositeARGBWithAlpha(i, indeterminateDrawable.totalAlpha);
            this.dirtyColors = false;
        }
        this.drawable.invalidateSelf();
    }

    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(333);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    LinearIndeterminateContiguousAnimatorDelegate linearIndeterminateContiguousAnimatorDelegate = LinearIndeterminateContiguousAnimatorDelegate.this;
                    linearIndeterminateContiguousAnimatorDelegate.newIndicatorColorIndex = (linearIndeterminateContiguousAnimatorDelegate.newIndicatorColorIndex + 1) % linearIndeterminateContiguousAnimatorDelegate.baseSpec.indicatorColors.length;
                    linearIndeterminateContiguousAnimatorDelegate.dirtyColors = true;
                }
            });
        }
        resetPropertiesForNewStart();
        this.animator.start();
    }
}
