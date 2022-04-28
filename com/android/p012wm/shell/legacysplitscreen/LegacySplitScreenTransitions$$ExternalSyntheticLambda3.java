package com.android.p012wm.shell.legacysplitscreen;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda7;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacySplitScreenTransitions$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ LegacySplitScreenTransitions f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Rect f$3;
    public final /* synthetic */ ValueAnimator f$4;

    public /* synthetic */ LegacySplitScreenTransitions$$ExternalSyntheticLambda3(LegacySplitScreenTransitions legacySplitScreenTransitions, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, ValueAnimator valueAnimator) {
        this.f$0 = legacySplitScreenTransitions;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = rect;
        this.f$4 = valueAnimator;
    }

    public final void run() {
        LegacySplitScreenTransitions legacySplitScreenTransitions = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        Rect rect = this.f$3;
        ValueAnimator valueAnimator = this.f$4;
        Objects.requireNonNull(legacySplitScreenTransitions);
        transaction.setWindowCrop(surfaceControl, 0, 0);
        transaction.setPosition(surfaceControl, (float) rect.left, (float) rect.top);
        transaction.apply();
        legacySplitScreenTransitions.mTransactionPool.release(transaction);
        Transitions transitions = legacySplitScreenTransitions.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mMainExecutor.execute(new TaskView$$ExternalSyntheticLambda7(legacySplitScreenTransitions, valueAnimator, 5));
    }
}
