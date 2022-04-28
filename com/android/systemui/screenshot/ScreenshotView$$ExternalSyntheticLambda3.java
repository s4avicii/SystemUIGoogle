package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda3(ScreenshotView screenshotView) {
        this.f$0 = screenshotView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView screenshotView = this.f$0;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        screenshotView.mScrollingScrim.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }
}
