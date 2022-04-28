package com.android.p012wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenTransitions$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ LegacySplitScreenTransitions f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ ValueAnimator f$4;

    public /* synthetic */ LegacySplitScreenTransitions$$ExternalSyntheticLambda2(LegacySplitScreenTransitions legacySplitScreenTransitions, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float f, ValueAnimator valueAnimator) {
        this.f$0 = legacySplitScreenTransitions;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = f;
        this.f$4 = valueAnimator;
    }

    public final void run() {
        LegacySplitScreenTransitions legacySplitScreenTransitions = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        float f = this.f$3;
        ValueAnimator valueAnimator = this.f$4;
        Objects.requireNonNull(legacySplitScreenTransitions);
        transaction.setAlpha(surfaceControl, f);
        transaction.apply();
        legacySplitScreenTransitions.mTransactionPool.release(transaction);
        Transitions transitions = legacySplitScreenTransitions.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(legacySplitScreenTransitions, valueAnimator, 4));
    }
}
