package com.android.systemui.navigationbar.gestural;

import android.animation.ValueAnimator;
import androidx.core.graphics.ColorUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBarEdgePanel$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ NavigationBarEdgePanel f$0;

    public /* synthetic */ NavigationBarEdgePanel$$ExternalSyntheticLambda1(NavigationBarEdgePanel navigationBarEdgePanel) {
        this.f$0 = navigationBarEdgePanel;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        NavigationBarEdgePanel navigationBarEdgePanel = this.f$0;
        Objects.requireNonNull(navigationBarEdgePanel);
        int blendARGB = ColorUtils.blendARGB(navigationBarEdgePanel.mArrowStartColor, navigationBarEdgePanel.mArrowColor, valueAnimator.getAnimatedFraction());
        navigationBarEdgePanel.mCurrentArrowColor = blendARGB;
        navigationBarEdgePanel.mPaint.setColor(blendARGB);
        navigationBarEdgePanel.invalidate();
    }
}
