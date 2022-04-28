package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.systemui.screenshot.CropView;
import com.android.systemui.screenshot.LongScreenshotActivity;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LongScreenshotActivity$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ LongScreenshotActivity.C10711 f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ LongScreenshotActivity$1$$ExternalSyntheticLambda0(LongScreenshotActivity.C10711 r1, float f, float f2) {
        this.f$0 = r1;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void run() {
        LongScreenshotActivity.C10711 r0 = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        Objects.requireNonNull(r0);
        LongScreenshotActivity.this.mPreview.animate().alpha(1.0f);
        LongScreenshotActivity.this.mCropView.setBoundaryPosition(CropView.CropBoundary.TOP, f);
        LongScreenshotActivity.this.mCropView.setBoundaryPosition(CropView.CropBoundary.BOTTOM, f2);
        CropView cropView = LongScreenshotActivity.this.mCropView;
        Objects.requireNonNull(cropView);
        cropView.mEntranceInterpolation = 0.0f;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(new CropView$$ExternalSyntheticLambda0(cropView));
        valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        valueAnimator.setDuration(750);
        valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
        valueAnimator.start();
        LongScreenshotActivity.this.mCropView.setVisibility(0);
        LongScreenshotActivity.this.setButtonsEnabled(true);
    }
}
