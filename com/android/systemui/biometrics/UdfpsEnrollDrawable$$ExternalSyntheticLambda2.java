package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsEnrollDrawable$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ UdfpsEnrollDrawable f$0;

    public /* synthetic */ UdfpsEnrollDrawable$$ExternalSyntheticLambda2(UdfpsEnrollDrawable udfpsEnrollDrawable) {
        this.f$0 = udfpsEnrollDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        UdfpsEnrollDrawable udfpsEnrollDrawable = this.f$0;
        Objects.requireNonNull(udfpsEnrollDrawable);
        udfpsEnrollDrawable.mCurrentScale = (((float) Math.sin((double) ((Float) valueAnimator.getAnimatedValue()).floatValue())) * 0.25f) + 1.0f;
        udfpsEnrollDrawable.invalidateSelf();
    }
}
