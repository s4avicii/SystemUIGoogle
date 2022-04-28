package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda5 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda5(ScreenshotView screenshotView) {
        this.f$0 = screenshotView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView screenshotView = this.f$0;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        screenshotView.mScrollingScrim.setImageTintList(ColorStateList.valueOf(Color.argb(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f, 0.0f, 0.0f)));
    }
}
