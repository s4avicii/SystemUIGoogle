package com.android.p012wm.shell.pip;

import android.graphics.Rect;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ Rect f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda6(PipTaskOrganizer pipTaskOrganizer, Rect rect, long j) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = rect;
        this.f$2 = j;
    }

    public final void run() {
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        Rect rect = this.f$1;
        long j = this.f$2;
        Objects.requireNonNull(pipTaskOrganizer);
        PipAnimationController.PipTransitionAnimator pipAnimationCallback = pipTaskOrganizer.mPipAnimationController.getAnimator(pipTaskOrganizer.mTaskInfo, pipTaskOrganizer.mLeash, rect, 0.0f, 1.0f).setTransitionDirection(2).setPipAnimationCallback(pipTaskOrganizer.mPipAnimationCallback);
        PipTaskOrganizer.C18902 r1 = pipTaskOrganizer.mPipTransactionHandler;
        Objects.requireNonNull(pipAnimationCallback);
        pipAnimationCallback.mPipTransactionHandler = r1;
        pipAnimationCallback.setDuration(j).start();
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mState = 3;
    }
}
