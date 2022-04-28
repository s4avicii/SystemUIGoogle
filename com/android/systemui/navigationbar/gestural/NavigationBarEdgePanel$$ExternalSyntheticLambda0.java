package com.android.systemui.navigationbar.gestural;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBarEdgePanel$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NavigationBarEdgePanel f$0;

    public /* synthetic */ NavigationBarEdgePanel$$ExternalSyntheticLambda0(NavigationBarEdgePanel navigationBarEdgePanel) {
        this.f$0 = navigationBarEdgePanel;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        NavigationBarEdgePanel navigationBarEdgePanel = this.f$0;
        Objects.requireNonNull(navigationBarEdgePanel);
        navigationBarEdgePanel.mDisappearAmount = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        navigationBarEdgePanel.invalidate();
    }
}
