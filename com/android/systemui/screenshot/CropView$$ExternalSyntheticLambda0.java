package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CropView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ CropView f$0;

    public /* synthetic */ CropView$$ExternalSyntheticLambda0(CropView cropView) {
        this.f$0 = cropView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        CropView cropView = this.f$0;
        int i = CropView.$r8$clinit;
        Objects.requireNonNull(cropView);
        cropView.mEntranceInterpolation = valueAnimator.getAnimatedFraction();
        cropView.invalidate();
    }
}
