package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.bubbles.animation.AnimatableScaleMatrix;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda14 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda14 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda14(BubbleStackView bubbleStackView, boolean z, float f) {
        this.f$0 = bubbleStackView;
        this.f$1 = z;
        this.f$2 = f;
    }

    public final void onAnimationUpdateForProperty(Object obj) {
        float f;
        BubbleStackView bubbleStackView = this.f$0;
        boolean z = this.f$1;
        float f2 = this.f$2;
        AnimatableScaleMatrix animatableScaleMatrix = (AnimatableScaleMatrix) obj;
        Objects.requireNonNull(bubbleStackView);
        BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
        if (bubbleViewProvider != null && bubbleViewProvider.getIconView$1() != null) {
            if (z) {
                f = bubbleStackView.mExpandedBubble.getIconView$1().getTranslationY();
            } else {
                f = bubbleStackView.mExpandedBubble.getIconView$1().getTranslationX();
            }
            bubbleStackView.mExpandedViewContainerMatrix.postTranslate(f - f2, 0.0f);
            bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
        }
    }
}
