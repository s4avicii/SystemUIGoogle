package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.ContextThemeWrapper;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;

public final class NumPadAnimator {
    public AnimatorSet mAnimator = new AnimatorSet();
    public GradientDrawable mBackground;
    public ValueAnimator mContractAnimator;
    public ValueAnimator mExpandAnimator;
    public int mHighlightColor;
    public int mNormalColor;
    public RippleDrawable mRipple;
    public int mStyle;

    public final void onLayout(int i) {
        float f = (float) i;
        float f2 = f / 2.0f;
        float f3 = f / 4.0f;
        this.mBackground.setCornerRadius(f2);
        this.mExpandAnimator.setFloatValues(new float[]{f2, f3});
        this.mContractAnimator.setFloatValues(new float[]{f3, f2});
    }

    public final void reloadColors(Context context) {
        int i;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, this.mStyle);
        TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(new int[]{16843817, 16843820});
        if (obtainStyledAttributes.hasValue(0)) {
            i = obtainStyledAttributes.getColor(0, 0);
        } else {
            TypedArray obtainStyledAttributes2 = contextThemeWrapper.obtainStyledAttributes(new int[]{17956909});
            int color = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            i = color;
        }
        this.mNormalColor = i;
        this.mHighlightColor = obtainStyledAttributes.getColor(1, 0);
        obtainStyledAttributes.recycle();
        this.mBackground.setColor(this.mNormalColor);
        this.mRipple.setColor(ColorStateList.valueOf(this.mHighlightColor));
    }

    public NumPadAnimator(Context context, RippleDrawable rippleDrawable, int i) {
        this.mStyle = i;
        RippleDrawable rippleDrawable2 = (RippleDrawable) rippleDrawable.mutate();
        this.mRipple = rippleDrawable2;
        this.mBackground = (GradientDrawable) rippleDrawable2.findDrawableByLayerId(C1777R.C1779id.background);
        reloadColors(context);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mExpandAnimator = ofFloat;
        ofFloat.setDuration(50);
        this.mExpandAnimator.setInterpolator(Interpolators.LINEAR);
        this.mExpandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NumPadAnimator.this.mBackground.setCornerRadius(((Float) valueAnimator.getAnimatedValue()).floatValue());
                NumPadAnimator.this.mRipple.invalidateSelf();
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        this.mContractAnimator = ofFloat2;
        ofFloat2.setStartDelay(33);
        this.mContractAnimator.setDuration(417);
        this.mContractAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mContractAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NumPadAnimator.this.mBackground.setCornerRadius(((Float) valueAnimator.getAnimatedValue()).floatValue());
                NumPadAnimator.this.mRipple.invalidateSelf();
            }
        });
        this.mAnimator.playSequentially(new Animator[]{this.mExpandAnimator, this.mContractAnimator});
    }
}
