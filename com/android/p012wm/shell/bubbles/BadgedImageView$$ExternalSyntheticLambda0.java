package com.android.p012wm.shell.bubbles;

import android.animation.ValueAnimator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BadgedImageView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BadgedImageView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ BadgedImageView f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BadgedImageView$$ExternalSyntheticLambda0(BadgedImageView badgedImageView, boolean z) {
        this.f$0 = badgedImageView;
        this.f$1 = z;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        BadgedImageView badgedImageView = this.f$0;
        boolean z = this.f$1;
        int i = BadgedImageView.$r8$clinit;
        Objects.requireNonNull(badgedImageView);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (!z) {
            animatedFraction = 1.0f - animatedFraction;
        }
        badgedImageView.mDotScale = animatedFraction;
        badgedImageView.invalidate();
    }
}
