package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsEnrollDrawable$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ UdfpsEnrollDrawable f$0;

    public /* synthetic */ UdfpsEnrollDrawable$$ExternalSyntheticLambda1(UdfpsEnrollDrawable udfpsEnrollDrawable) {
        this.f$0 = udfpsEnrollDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        UdfpsEnrollDrawable udfpsEnrollDrawable = this.f$0;
        Objects.requireNonNull(udfpsEnrollDrawable);
        udfpsEnrollDrawable.mCurrentY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        udfpsEnrollDrawable.invalidateSelf();
    }
}
