package com.android.p012wm.shell.bubbles;

import androidx.dynamicanimation.animation.DynamicAnimation;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda9 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda9(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        BubbleStackView bubbleStackView = this.f$0;
        Objects.requireNonNull(bubbleStackView);
        if (bubbleStackView.mFlyoutDragDeltaX == 0.0f) {
            bubbleStackView.mFlyout.postDelayed(bubbleStackView.mHideFlyout, 5000);
            return;
        }
        BubbleFlyoutView bubbleFlyoutView = bubbleStackView.mFlyout;
        Objects.requireNonNull(bubbleFlyoutView);
        Runnable runnable = bubbleFlyoutView.mOnHide;
        if (runnable != null) {
            runnable.run();
            bubbleFlyoutView.mOnHide = null;
        }
        bubbleFlyoutView.setVisibility(8);
    }
}
