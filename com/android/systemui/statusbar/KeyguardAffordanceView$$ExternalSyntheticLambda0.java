package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardAffordanceView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ KeyguardAffordanceView f$0;
    public final /* synthetic */ Drawable f$1;

    public /* synthetic */ KeyguardAffordanceView$$ExternalSyntheticLambda0(KeyguardAffordanceView keyguardAffordanceView, Drawable drawable) {
        this.f$0 = keyguardAffordanceView;
        this.f$1 = drawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        KeyguardAffordanceView keyguardAffordanceView = this.f$0;
        Drawable drawable = this.f$1;
        int i = KeyguardAffordanceView.$r8$clinit;
        Objects.requireNonNull(keyguardAffordanceView);
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (drawable != null) {
            drawable.mutate().setAlpha(intValue);
        }
        keyguardAffordanceView.setImageAlpha(intValue);
    }
}
