package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.bubbles.animation.AnimatableScaleMatrix;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda25 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda25 implements Runnable {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda25(BubbleStackView bubbleStackView, boolean z, float f) {
        this.f$0 = bubbleStackView;
        this.f$1 = z;
        this.f$2 = f;
    }

    public final void run() {
        BubbleStackView bubbleStackView = this.f$0;
        boolean z = this.f$1;
        float f = this.f$2;
        Objects.requireNonNull(bubbleStackView);
        bubbleStackView.mExpandedViewAlphaAnimator.start();
        AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView.mExpandedViewContainerMatrix;
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        PhysicsAnimator.Companion.getInstance(animatableScaleMatrix).cancel();
        PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(bubbleStackView.mExpandedViewContainerMatrix);
        instance.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView.mScaleInSpringConfig);
        instance.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView.mScaleInSpringConfig);
        instance.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda14(bubbleStackView, z, f));
        instance.withEndActions(new BubbleStackView$$ExternalSyntheticLambda17(bubbleStackView, 0));
        instance.start();
    }
}
