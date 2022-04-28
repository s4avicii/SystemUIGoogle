package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScrollCaptureController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda5 implements ScreenshotController.TransitionDestination {
    public final /* synthetic */ ScreenshotController f$0;
    public final /* synthetic */ ScrollCaptureController.LongScreenshot f$1;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda5(ScreenshotController screenshotController, ScrollCaptureController.LongScreenshot longScreenshot) {
        this.f$0 = screenshotController;
        this.f$1 = longScreenshot;
    }

    public final void setTransitionDestination(Rect rect, LongScreenshotActivity$1$$ExternalSyntheticLambda0 longScreenshotActivity$1$$ExternalSyntheticLambda0) {
        ScreenshotController screenshotController = this.f$0;
        ScrollCaptureController.LongScreenshot longScreenshot = this.f$1;
        Objects.requireNonNull(screenshotController);
        ScreenshotView screenshotView = screenshotController.mScreenshotView;
        Objects.requireNonNull(screenshotView);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda3(screenshotView));
        if (screenshotView.mShowScrollablePreview) {
            screenshotView.mScrollablePreview.setImageBitmap(longScreenshot.toBitmap());
            float x = screenshotView.mScrollablePreview.getX();
            float y = screenshotView.mScrollablePreview.getY();
            int[] locationOnScreen = screenshotView.mScrollablePreview.getLocationOnScreen();
            rect.offset(((int) x) - locationOnScreen[0], ((int) y) - locationOnScreen[1]);
            screenshotView.mScrollablePreview.setPivotX(0.0f);
            screenshotView.mScrollablePreview.setPivotY(0.0f);
            screenshotView.mScrollablePreview.setAlpha(1.0f);
            float width = ((float) screenshotView.mScrollablePreview.getWidth()) / ((float) longScreenshot.mImageTileSet.getWidth());
            Matrix matrix = new Matrix();
            matrix.setScale(width, width);
            ImageTileSet imageTileSet = longScreenshot.mImageTileSet;
            Objects.requireNonNull(imageTileSet);
            ImageTileSet imageTileSet2 = longScreenshot.mImageTileSet;
            Objects.requireNonNull(imageTileSet2);
            matrix.postTranslate(((float) imageTileSet.mRegion.getBounds().left) * width, ((float) imageTileSet2.mRegion.getBounds().top) * width);
            screenshotView.mScrollablePreview.setImageMatrix(matrix);
            float width2 = ((float) rect.width()) / ((float) screenshotView.mScrollablePreview.getWidth());
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat2.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda6(screenshotView, width2, x, rect, y));
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            ofFloat3.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda1(screenshotView, 0));
            animatorSet.play(ofFloat2).with(ofFloat).before(ofFloat3);
            ofFloat2.addListener(new AnimatorListenerAdapter(longScreenshotActivity$1$$ExternalSyntheticLambda0) {
                public final /* synthetic */ Runnable val$onTransitionEnd;

                {
                    this.val$onTransitionEnd = r1;
                }

                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    this.val$onTransitionEnd.run();
                }
            });
        } else {
            animatorSet.play(ofFloat);
            animatorSet.addListener(new AnimatorListenerAdapter(longScreenshotActivity$1$$ExternalSyntheticLambda0) {
                public final /* synthetic */ Runnable val$onTransitionEnd;

                {
                    this.val$onTransitionEnd = r1;
                }

                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    this.val$onTransitionEnd.run();
                }
            });
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ScreenshotController.C10753 r0 = (ScreenshotController.C10753) ScreenshotView.this.mCallbacks;
                Objects.requireNonNull(r0);
                ScreenshotController.this.finishDismiss();
            }
        });
        animatorSet.start();
    }
}
