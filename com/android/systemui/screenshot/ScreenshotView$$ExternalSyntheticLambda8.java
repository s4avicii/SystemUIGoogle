package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda8 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;
    public final /* synthetic */ ArrayList f$2;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda8(ScreenshotView screenshotView, ArrayList arrayList) {
        this.f$0 = screenshotView;
        this.f$2 = arrayList;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        int i;
        float f2;
        ScreenshotView screenshotView = this.f$0;
        ArrayList arrayList = this.f$2;
        int i2 = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        screenshotView.mBackgroundProtection.setAlpha(animatedFraction);
        if (animatedFraction < 0.25f) {
            f = animatedFraction / 0.25f;
        } else {
            f = 1.0f;
        }
        screenshotView.mActionsContainer.setAlpha(f);
        screenshotView.mActionsContainerBackground.setAlpha(f);
        float f3 = (0.3f * animatedFraction) + 0.7f;
        screenshotView.mActionsContainer.setScaleX(f3);
        screenshotView.mActionsContainerBackground.setScaleX(f3);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            OverlayActionChip overlayActionChip = (OverlayActionChip) it.next();
            overlayActionChip.setAlpha(animatedFraction);
            overlayActionChip.setScaleX(1.0f / f3);
        }
        HorizontalScrollView horizontalScrollView = screenshotView.mActionsContainer;
        if (screenshotView.mDirectionLTR) {
            i = 0;
        } else {
            i = horizontalScrollView.getWidth();
        }
        horizontalScrollView.setScrollX(i);
        HorizontalScrollView horizontalScrollView2 = screenshotView.mActionsContainer;
        float f4 = 0.0f;
        if (screenshotView.mDirectionLTR) {
            f2 = 0.0f;
        } else {
            f2 = (float) horizontalScrollView2.getWidth();
        }
        horizontalScrollView2.setPivotX(f2);
        ImageView imageView = screenshotView.mActionsContainerBackground;
        if (!screenshotView.mDirectionLTR) {
            f4 = (float) imageView.getWidth();
        }
        imageView.setPivotX(f4);
    }
}
