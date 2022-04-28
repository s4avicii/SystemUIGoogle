package com.android.keyguard;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.util.MathUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.keyguard.KeyguardSecurityContainer;
import java.util.Objects;

/* renamed from: com.android.keyguard.KeyguardSecurityContainer$OneHandedViewMode$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0518xc47775e2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ KeyguardSecurityContainer.OneHandedViewMode f$0;
    public final /* synthetic */ Interpolator f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Interpolator f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ int f$6;
    public final /* synthetic */ Interpolator f$7;
    public final /* synthetic */ int f$8;
    public final /* synthetic */ boolean f$9;

    public /* synthetic */ C0518xc47775e2(KeyguardSecurityContainer.OneHandedViewMode oneHandedViewMode, Interpolator interpolator, int i, boolean z, PathInterpolator pathInterpolator, float f, int i2, PathInterpolator pathInterpolator2, int i3, boolean z2) {
        this.f$0 = oneHandedViewMode;
        this.f$1 = interpolator;
        this.f$2 = i;
        this.f$3 = z;
        this.f$4 = pathInterpolator;
        this.f$5 = f;
        this.f$6 = i2;
        this.f$7 = pathInterpolator2;
        this.f$8 = i3;
        this.f$9 = z2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z;
        KeyguardSecurityContainer.OneHandedViewMode oneHandedViewMode = this.f$0;
        Interpolator interpolator = this.f$1;
        int i = this.f$2;
        boolean z2 = this.f$3;
        Interpolator interpolator2 = this.f$4;
        float f = this.f$5;
        int i2 = this.f$6;
        Interpolator interpolator3 = this.f$7;
        int i3 = this.f$8;
        boolean z3 = this.f$9;
        Objects.requireNonNull(oneHandedViewMode);
        if (valueAnimator.getAnimatedFraction() < 0.2f) {
            z = true;
        } else {
            z = false;
        }
        int interpolation = (int) (interpolator.getInterpolation(valueAnimator.getAnimatedFraction()) * ((float) i));
        int i4 = i - interpolation;
        if (z2) {
            interpolation = -interpolation;
            i4 = -i4;
        }
        if (z) {
            oneHandedViewMode.mViewFlipper.setAlpha(interpolator2.getInterpolation(MathUtils.constrainedMap(1.0f, 0.0f, 0.0f, 0.2f, valueAnimator.getAnimatedFraction())) * f);
            oneHandedViewMode.mViewFlipper.setTranslationX((float) (i2 + interpolation));
        } else {
            oneHandedViewMode.mViewFlipper.setAlpha(interpolator3.getInterpolation(MathUtils.constrainedMap(0.0f, 1.0f, 0.2f, 1.0f, valueAnimator.getAnimatedFraction())));
            oneHandedViewMode.mViewFlipper.setTranslationX((float) (i3 - i4));
        }
        if (valueAnimator.getAnimatedFraction() == 1.0f && z3) {
            oneHandedViewMode.mViewFlipper.setLayerType(0, (Paint) null);
        }
    }
}
