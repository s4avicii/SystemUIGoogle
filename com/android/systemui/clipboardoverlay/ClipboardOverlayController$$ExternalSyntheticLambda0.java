package com.android.systemui.clipboardoverlay;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ClipboardOverlayController f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ View f$2;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda0(ClipboardOverlayController clipboardOverlayController, View view, View view2) {
        this.f$0 = clipboardOverlayController;
        this.f$1 = view;
        this.f$2 = view2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ClipboardOverlayController clipboardOverlayController = this.f$0;
        View view = this.f$1;
        View view2 = this.f$2;
        Objects.requireNonNull(clipboardOverlayController);
        clipboardOverlayController.mContainer.setAlpha(valueAnimator.getAnimatedFraction());
        float animatedFraction = (valueAnimator.getAnimatedFraction() * 0.4f) + 0.6f;
        DraggableConstraintLayout draggableConstraintLayout = clipboardOverlayController.mView;
        draggableConstraintLayout.setPivotY(((float) draggableConstraintLayout.getHeight()) - (((float) view.getHeight()) / 2.0f));
        clipboardOverlayController.mView.setPivotX(((float) view2.getWidth()) / 2.0f);
        clipboardOverlayController.mView.setScaleX(animatedFraction);
        clipboardOverlayController.mView.setScaleY(animatedFraction);
    }
}
