package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PanelViewController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ PanelViewController f$0;

    public /* synthetic */ PanelViewController$$ExternalSyntheticLambda0(PanelViewController panelViewController) {
        this.f$0 = panelViewController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        PanelViewController panelViewController = this.f$0;
        Objects.requireNonNull(panelViewController);
        panelViewController.setOverExpansionInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
    }
}
