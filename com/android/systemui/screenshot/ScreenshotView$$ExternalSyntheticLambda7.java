package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.MathUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotView$$ExternalSyntheticLambda7 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScreenshotView f$0;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ PointF f$4;
    public final /* synthetic */ PointF f$5;

    public /* synthetic */ ScreenshotView$$ExternalSyntheticLambda7(ScreenshotView screenshotView, float f, PointF pointF, PointF pointF2) {
        this.f$0 = screenshotView;
        this.f$2 = f;
        this.f$4 = pointF;
        this.f$5 = pointF2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScreenshotView screenshotView = this.f$0;
        float f = this.f$2;
        PointF pointF = this.f$4;
        PointF pointF2 = this.f$5;
        int i = ScreenshotView.$r8$clinit;
        Objects.requireNonNull(screenshotView);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        int i2 = (animatedFraction > 0.468f ? 1 : (animatedFraction == 0.468f ? 0 : -1));
        if (i2 < 0) {
            float lerp = MathUtils.lerp(f, 1.0f, screenshotView.mFastOutSlowIn.getInterpolation(animatedFraction / 0.468f));
            screenshotView.mScreenshotPreview.setScaleX(lerp);
            screenshotView.mScreenshotPreview.setScaleY(lerp);
        } else {
            screenshotView.mScreenshotPreview.setScaleX(1.0f);
            screenshotView.mScreenshotPreview.setScaleY(1.0f);
        }
        if (i2 < 0) {
            float lerp2 = MathUtils.lerp(pointF.x, pointF2.x, screenshotView.mFastOutSlowIn.getInterpolation(animatedFraction / 0.468f));
            ImageView imageView = screenshotView.mScreenshotPreview;
            imageView.setX(lerp2 - (((float) imageView.getWidth()) / 2.0f));
        } else {
            ImageView imageView2 = screenshotView.mScreenshotPreview;
            imageView2.setX(pointF2.x - (((float) imageView2.getWidth()) / 2.0f));
        }
        float lerp3 = MathUtils.lerp(pointF.y, pointF2.y, screenshotView.mFastOutSlowIn.getInterpolation(animatedFraction));
        ImageView imageView3 = screenshotView.mScreenshotPreview;
        imageView3.setY(lerp3 - (((float) imageView3.getHeight()) / 2.0f));
        if (animatedFraction >= 0.4f) {
            screenshotView.mDismissButton.setAlpha((animatedFraction - 0.4f) / 0.6f);
            float x = screenshotView.mScreenshotPreview.getX();
            float y = screenshotView.mScreenshotPreview.getY();
            FrameLayout frameLayout = screenshotView.mDismissButton;
            frameLayout.setY(y - (((float) frameLayout.getHeight()) / 2.0f));
            if (screenshotView.mDirectionLTR) {
                screenshotView.mDismissButton.setX((x + ((float) screenshotView.mScreenshotPreview.getWidth())) - (((float) screenshotView.mDismissButton.getWidth()) / 2.0f));
                return;
            }
            FrameLayout frameLayout2 = screenshotView.mDismissButton;
            frameLayout2.setX(x - (((float) frameLayout2.getWidth()) / 2.0f));
        }
    }
}
