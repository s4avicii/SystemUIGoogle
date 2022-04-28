package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import com.android.systemui.animation.Interpolators;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PanelViewController$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ PanelViewController f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ ValueAnimator f$4;

    public /* synthetic */ PanelViewController$$ExternalSyntheticLambda1(PanelViewController panelViewController, float f, float f2, float f3, ValueAnimator valueAnimator) {
        this.f$0 = panelViewController;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = f3;
        this.f$4 = valueAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        PanelViewController panelViewController = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        float f3 = this.f$3;
        ValueAnimator valueAnimator2 = this.f$4;
        Objects.requireNonNull(panelViewController);
        if (f > 0.0f || (f2 == 0.0f && f3 != 0.0f)) {
            panelViewController.setOverExpansionInternal(MathUtils.lerp(f3, panelViewController.mPanelFlingOvershootAmount * f, Interpolators.FAST_OUT_SLOW_IN.getInterpolation(valueAnimator2.getAnimatedFraction())), false);
        }
        panelViewController.setExpandedHeightInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
