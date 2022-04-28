package com.google.android.systemui.assist.uihints;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlowController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ GlowController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ GlowController$$ExternalSyntheticLambda0(GlowController glowController, int i, int i2) {
        this.f$0 = glowController;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        GlowController glowController = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(glowController);
        GlowView glowView = glowController.mGlowView;
        int lerp = (int) MathUtils.lerp(i, i2, valueAnimator.getAnimatedFraction());
        Objects.requireNonNull(glowView);
        if (glowView.mBlurRadius != lerp) {
            glowView.setBlurredImageOnViews(lerp);
        }
    }
}
