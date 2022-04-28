package com.android.p012wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda1 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ StackAnimationController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Runnable[] f$2;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda1(StackAnimationController stackAnimationController, boolean z, Runnable[] runnableArr) {
        this.f$0 = stackAnimationController;
        this.f$1 = z;
        this.f$2 = runnableArr;
    }

    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        StackAnimationController stackAnimationController = this.f$0;
        boolean z2 = this.f$1;
        Runnable[] runnableArr = this.f$2;
        Objects.requireNonNull(stackAnimationController);
        if (!z2) {
            stackAnimationController.mPositioner.setRestingPosition(stackAnimationController.mStackPosition);
        }
        Runnable runnable = stackAnimationController.mOnStackAnimationFinished;
        if (runnable != null) {
            runnable.run();
        }
        if (runnableArr != null) {
            for (Runnable run : runnableArr) {
                run.run();
            }
        }
    }
}
