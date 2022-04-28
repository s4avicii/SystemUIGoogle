package com.android.p012wm.shell.pip;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda2 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ Rect f$3;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda2(PipTaskOrganizer pipTaskOrganizer, SurfaceControl surfaceControl, Rect rect, Rect rect2) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
        this.f$3 = rect2;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        Rect rect = this.f$2;
        Rect rect2 = this.f$3;
        Objects.requireNonNull(pipTaskOrganizer);
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = pipTaskOrganizer.mSurfaceTransactionHelper;
        Objects.requireNonNull(pipSurfaceTransactionHelper);
        pipSurfaceTransactionHelper.scale(transaction, surfaceControl, rect, rect2, 0.0f);
        pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, (Runnable) null, false);
    }
}
