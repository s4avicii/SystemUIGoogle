package com.android.p012wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.view.Choreographer;
import android.view.SurfaceControl;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.DividerImeController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DividerImeController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DividerImeController f$0;

    public /* synthetic */ DividerImeController$$ExternalSyntheticLambda0(DividerImeController dividerImeController) {
        this.f$0 = dividerImeController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        DividerImeController dividerImeController = this.f$0;
        Objects.requireNonNull(dividerImeController);
        SurfaceControl.Transaction acquire = dividerImeController.mTransactionPool.acquire();
        dividerImeController.onProgress(((Float) valueAnimator.getAnimatedValue()).floatValue(), acquire);
        acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
        acquire.apply();
        dividerImeController.mTransactionPool.release(acquire);
    }
}
