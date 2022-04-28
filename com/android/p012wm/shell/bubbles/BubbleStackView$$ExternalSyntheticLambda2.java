package com.android.p012wm.shell.bubbles;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda2(BubbleStackView bubbleStackView, float f) {
        this.f$0 = bubbleStackView;
        this.f$1 = f;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        BubbleStackView bubbleStackView = this.f$0;
        float f = this.f$1;
        Objects.requireNonNull(bubbleStackView);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        DismissView dismissView = bubbleStackView.mDismissView;
        if (dismissView != null) {
            dismissView.setPivotX(((float) (dismissView.getRight() - bubbleStackView.mDismissView.getLeft())) / 2.0f);
            DismissView dismissView2 = bubbleStackView.mDismissView;
            dismissView2.setPivotY(((float) (dismissView2.getBottom() - bubbleStackView.mDismissView.getTop())) / 2.0f);
            float max = Math.max(floatValue, f);
            DismissView dismissView3 = bubbleStackView.mDismissView;
            Objects.requireNonNull(dismissView3);
            dismissView3.circle.setScaleX(max);
            DismissView dismissView4 = bubbleStackView.mDismissView;
            Objects.requireNonNull(dismissView4);
            dismissView4.circle.setScaleY(max);
        }
        View view = bubbleStackView.mViewBeingDismissed;
        if (view != null) {
            view.setAlpha(Math.max(floatValue, 0.7f));
        }
    }
}
