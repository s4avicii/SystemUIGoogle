package com.android.p012wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda0 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ StackAnimationController f$0;
    public final /* synthetic */ DynamicAnimation.ViewProperty f$1;
    public final /* synthetic */ SpringForce f$2;
    public final /* synthetic */ Float f$3;
    public final /* synthetic */ float f$4;
    public final /* synthetic */ float f$5;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda0(StackAnimationController stackAnimationController, DynamicAnimation.ViewProperty viewProperty, SpringForce springForce, Float f, float f2, float f3) {
        this.f$0 = stackAnimationController;
        this.f$1 = viewProperty;
        this.f$2 = springForce;
        this.f$3 = f;
        this.f$4 = f2;
        this.f$5 = f3;
    }

    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        float f3;
        StackAnimationController stackAnimationController = this.f$0;
        DynamicAnimation.ViewProperty viewProperty = this.f$1;
        SpringForce springForce = this.f$2;
        Float f4 = this.f$3;
        float f5 = this.f$4;
        float f6 = this.f$5;
        if (!z) {
            stackAnimationController.mPositioner.setRestingPosition(stackAnimationController.mStackPosition);
            if (f4 != null) {
                f3 = f4.floatValue();
            } else {
                f3 = Math.max(f5, Math.min(f6, f));
            }
            stackAnimationController.springFirstBubbleWithStackFollowing(viewProperty, springForce, f2, f3, new Runnable[0]);
            return;
        }
        Objects.requireNonNull(stackAnimationController);
    }
}
