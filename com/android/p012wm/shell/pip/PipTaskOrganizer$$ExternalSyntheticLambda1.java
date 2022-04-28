package com.android.p012wm.shell.pip;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.pip.PipAnimationController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ Rect f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda1(PipTaskOrganizer pipTaskOrganizer, Rect rect, int i, int i2) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = rect;
        this.f$2 = i;
        this.f$3 = i2;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        Rect rect = this.f$1;
        int i = this.f$2;
        int i2 = this.f$3;
        Objects.requireNonNull(pipTaskOrganizer);
        PipTaskOrganizer pipTaskOrganizer2 = pipTaskOrganizer;
        PipAnimationController.PipTransitionAnimator<?> animateResizePip = pipTaskOrganizer2.animateResizePip(pipTaskOrganizer.mPipBoundsState.getBounds(), rect, PipBoundsAlgorithm.getValidSourceHintRect(pipTaskOrganizer.mPictureInPictureParams, rect), i, i2, 0.0f);
        if (animateResizePip != null) {
            animateResizePip.applySurfaceControlTransaction(pipTaskOrganizer.mLeash, transaction, 0.0f);
        }
    }
}
