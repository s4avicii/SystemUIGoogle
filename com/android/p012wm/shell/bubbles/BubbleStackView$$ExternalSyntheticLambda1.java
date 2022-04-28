package com.android.p012wm.shell.bubbles;

import android.animation.ValueAnimator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda1(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        BubbleStackView bubbleStackView = this.f$0;
        Objects.requireNonNull(bubbleStackView);
        if (!bubbleStackView.mExpandedViewTemporarilyHidden) {
            bubbleStackView.mAnimatingOutSurfaceView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }
}
