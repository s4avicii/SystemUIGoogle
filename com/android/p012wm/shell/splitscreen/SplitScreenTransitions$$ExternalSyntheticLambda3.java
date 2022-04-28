package com.android.p012wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ SplitScreenTransitions f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ ValueAnimator f$4;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda3(SplitScreenTransitions splitScreenTransitions, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float f, ValueAnimator valueAnimator) {
        this.f$0 = splitScreenTransitions;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = f;
        this.f$4 = valueAnimator;
    }

    public final void run() {
        SplitScreenTransitions splitScreenTransitions = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        float f = this.f$3;
        ValueAnimator valueAnimator = this.f$4;
        Objects.requireNonNull(splitScreenTransitions);
        transaction.setAlpha(surfaceControl, f);
        transaction.apply();
        splitScreenTransitions.mTransactionPool.release(transaction);
        Transitions transitions = splitScreenTransitions.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mMainExecutor.execute(new NavBarTuner$$ExternalSyntheticLambda6(splitScreenTransitions, valueAnimator, 7));
    }
}
