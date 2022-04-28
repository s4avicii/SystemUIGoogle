package com.android.p012wm.shell.onehanded;

import android.animation.ValueAnimator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedTutorialHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedTutorialHandler$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ OneHandedTutorialHandler f$0;

    public /* synthetic */ OneHandedTutorialHandler$$ExternalSyntheticLambda0(OneHandedTutorialHandler oneHandedTutorialHandler) {
        this.f$0 = oneHandedTutorialHandler;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        OneHandedTutorialHandler oneHandedTutorialHandler = this.f$0;
        Objects.requireNonNull(oneHandedTutorialHandler);
        oneHandedTutorialHandler.mTargetViewContainer.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
