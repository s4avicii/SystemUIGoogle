package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda4 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda4(ScreenshotView screenshotView) {
        this.f$0 = screenshotView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView screenshotView = this.f$0;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        screenshotView.mDismissButton.setAlpha(animatedFraction);
        screenshotView.mActionsContainerBackground.setAlpha(animatedFraction);
        screenshotView.mActionsContainer.setAlpha(animatedFraction);
        screenshotView.mBackgroundProtection.setAlpha(animatedFraction);
        screenshotView.mScreenshotPreviewBorder.setAlpha(animatedFraction);
    }
}
