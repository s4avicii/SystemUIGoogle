package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ViewTransformationHelper$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ViewTransformationHelper f$0;
    public final /* synthetic */ TransformableView f$1;

    public /* synthetic */ ViewTransformationHelper$$ExternalSyntheticLambda1(ViewTransformationHelper viewTransformationHelper, TransformableView transformableView) {
        this.f$0 = viewTransformationHelper;
        this.f$1 = transformableView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ViewTransformationHelper viewTransformationHelper = this.f$0;
        TransformableView transformableView = this.f$1;
        Objects.requireNonNull(viewTransformationHelper);
        viewTransformationHelper.transformFrom(transformableView, valueAnimator.getAnimatedFraction());
    }
}
