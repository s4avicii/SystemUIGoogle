package com.android.systemui.util.animation;

import android.animation.ValueAnimator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TransitionLayoutController.kt */
public final class TransitionLayoutController$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ TransitionLayoutController this$0;

    public TransitionLayoutController$1$1(TransitionLayoutController transitionLayoutController) {
        this.this$0 = transitionLayoutController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        TransitionLayoutController transitionLayoutController = this.this$0;
        Objects.requireNonNull(transitionLayoutController);
        if (transitionLayoutController.animationStartState != null && transitionLayoutController.animator.isRunning()) {
            TransitionViewState transitionViewState = transitionLayoutController.animationStartState;
            Intrinsics.checkNotNull(transitionViewState);
            TransitionViewState interpolatedState = transitionLayoutController.getInterpolatedState(transitionViewState, transitionLayoutController.state, transitionLayoutController.animator.getAnimatedFraction(), transitionLayoutController.currentState);
            transitionLayoutController.currentState = interpolatedState;
            transitionLayoutController.applyStateToLayout(interpolatedState);
        }
    }
}
