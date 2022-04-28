package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.leanback.R$string;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import java.util.Arrays;
import java.util.Objects;

public final class LinearIndeterminateDisjointAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    public static final C20753 ANIMATION_FRACTION = new Property<LinearIndeterminateDisjointAnimatorDelegate, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = (LinearIndeterminateDisjointAnimatorDelegate) obj;
            Objects.requireNonNull(linearIndeterminateDisjointAnimatorDelegate);
            return Float.valueOf(linearIndeterminateDisjointAnimatorDelegate.animationFraction);
        }

        public final void set(Object obj, Object obj2) {
            ((LinearIndeterminateDisjointAnimatorDelegate) obj).setAnimationFraction(((Float) obj2).floatValue());
        }
    };
    public static final int[] DELAY_TO_MOVE_SEGMENT_ENDS = {1267, 1000, 333, 0};
    public static final int[] DURATION_TO_MOVE_SEGMENT_ENDS = {533, 567, 850, 750};
    public float animationFraction;
    public ObjectAnimator animator;
    public Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    public final LinearProgressIndicatorSpec baseSpec;
    public ObjectAnimator completeEndAnimator;
    public boolean dirtyColors;
    public int indicatorColorIndex = 0;
    public final Interpolator[] interpolatorArray;

    public LinearIndeterminateDisjointAnimatorDelegate(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(2);
        this.baseSpec = linearProgressIndicatorSpec;
        this.interpolatorArray = new Interpolator[]{AnimationUtils.loadInterpolator(context, C1777R.animator.linear_indeterminate_line1_head_interpolator), AnimationUtils.loadInterpolator(context, C1777R.animator.linear_indeterminate_line1_tail_interpolator), AnimationUtils.loadInterpolator(context, C1777R.animator.linear_indeterminate_line2_head_interpolator), AnimationUtils.loadInterpolator(context, C1777R.animator.linear_indeterminate_line2_tail_interpolator)};
    }

    public void resetPropertiesForNewStart() {
        this.indicatorColorIndex = 0;
        int i = this.baseSpec.indicatorColors[0];
        IndeterminateDrawable indeterminateDrawable = this.drawable;
        Objects.requireNonNull(indeterminateDrawable);
        int compositeARGBWithAlpha = R$string.compositeARGBWithAlpha(i, indeterminateDrawable.totalAlpha);
        int[] iArr = this.segmentColors;
        iArr[0] = compositeARGBWithAlpha;
        iArr[1] = compositeARGBWithAlpha;
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
            cancelAnimatorImmediately();
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.setFloatValues(new float[]{this.animationFraction, 1.0f});
                this.completeEndAnimator.setDuration((long) ((1.0f - this.animationFraction) * 1800.0f));
                this.completeEndAnimator.start();
            }
        }
    }

    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        int i = (int) (f * 1800.0f);
        for (int i2 = 0; i2 < 4; i2++) {
            this.segmentPositions[i2] = Math.max(0.0f, Math.min(1.0f, this.interpolatorArray[i2].getInterpolation(((float) (i - DELAY_TO_MOVE_SEGMENT_ENDS[i2])) / ((float) DURATION_TO_MOVE_SEGMENT_ENDS[i2]))));
        }
        if (this.dirtyColors) {
            int[] iArr = this.segmentColors;
            int i3 = this.baseSpec.indicatorColors[this.indicatorColorIndex];
            IndeterminateDrawable indeterminateDrawable = this.drawable;
            Objects.requireNonNull(indeterminateDrawable);
            Arrays.fill(iArr, R$string.compositeARGBWithAlpha(i3, indeterminateDrawable.totalAlpha));
            this.dirtyColors = false;
        }
        this.drawable.invalidateSelf();
    }

    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(1800);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
                    linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex = (linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex + 1) % linearIndeterminateDisjointAnimatorDelegate.baseSpec.indicatorColors.length;
                    linearIndeterminateDisjointAnimatorDelegate.dirtyColors = true;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(1800);
            this.completeEndAnimator.setInterpolator((TimeInterpolator) null);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    LinearIndeterminateDisjointAnimatorDelegate.this.cancelAnimatorImmediately();
                    LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
                    Animatable2Compat.AnimationCallback animationCallback = linearIndeterminateDisjointAnimatorDelegate.animatorCompleteCallback;
                    if (animationCallback != null) {
                        animationCallback.onAnimationEnd(linearIndeterminateDisjointAnimatorDelegate.drawable);
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
