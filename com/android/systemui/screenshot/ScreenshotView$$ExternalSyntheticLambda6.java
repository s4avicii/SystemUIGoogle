package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.MathUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda6 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ Rect f$3;
    public final /* synthetic */ float f$4;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda6(ScreenshotView screenshotView, float f, float f2, Rect rect, float f3) {
        this.f$0 = screenshotView;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = rect;
        this.f$4 = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView screenshotView = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        Rect rect = this.f$3;
        float f3 = this.f$4;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        float lerp = MathUtils.lerp(1.0f, f, animatedFraction);
        screenshotView.mScrollablePreview.setScaleX(lerp);
        screenshotView.mScrollablePreview.setScaleY(lerp);
        screenshotView.mScrollablePreview.setX(MathUtils.lerp(f2, (float) rect.left, animatedFraction));
        screenshotView.mScrollablePreview.setY(MathUtils.lerp(f3, (float) rect.top, animatedFraction));
    }
}
