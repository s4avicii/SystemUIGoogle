package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

    public /* synthetic */ UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable) {
        this.f$0 = udfpsEnrollProgressBarDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.f$0;
        Objects.requireNonNull(udfpsEnrollProgressBarDrawable);
        udfpsEnrollProgressBarDrawable.mFillPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        udfpsEnrollProgressBarDrawable.invalidateSelf();
    }
}
