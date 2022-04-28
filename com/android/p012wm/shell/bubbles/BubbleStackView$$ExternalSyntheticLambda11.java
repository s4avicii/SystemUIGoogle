package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.bubbles.animation.AnimatableScaleMatrix;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda11 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda11 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda11(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    public final void onAnimationUpdateForProperty(Object obj) {
        BubbleStackView bubbleStackView = this.f$0;
        AnimatableScaleMatrix animatableScaleMatrix = (AnimatableScaleMatrix) obj;
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
    }
}
