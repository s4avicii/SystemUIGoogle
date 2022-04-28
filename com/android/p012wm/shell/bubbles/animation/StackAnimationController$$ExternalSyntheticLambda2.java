package com.android.p012wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda2 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    public final /* synthetic */ StackAnimationController f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda2(StackAnimationController stackAnimationController, float f) {
        this.f$0 = stackAnimationController;
        this.f$1 = f;
    }

    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        StackAnimationController stackAnimationController = this.f$0;
        float f = this.f$1;
        Objects.requireNonNull(stackAnimationController);
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 0.0f, new Runnable[0]);
        physicsPropertyAnimator.translationY(stackAnimationController.mLayout.getChildAt(i).getTranslationY() + f, new Runnable[0]);
        physicsPropertyAnimator.mStiffness = 10000.0f;
    }
}
