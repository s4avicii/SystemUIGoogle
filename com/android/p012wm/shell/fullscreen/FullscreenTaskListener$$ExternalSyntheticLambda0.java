package com.android.p012wm.shell.fullscreen;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;

/* renamed from: com.android.wm.shell.fullscreen.FullscreenTaskListener$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FullscreenTaskListener$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ SurfaceControl f$0;
    public final /* synthetic */ Point f$1;

    public /* synthetic */ FullscreenTaskListener$$ExternalSyntheticLambda0(SurfaceControl surfaceControl, Point point) {
        this.f$0 = surfaceControl;
        this.f$1 = point;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.f$0;
        Point point = this.f$1;
        transaction.setWindowCrop(surfaceControl, (Rect) null);
        transaction.setPosition(surfaceControl, (float) point.x, (float) point.y);
        transaction.setAlpha(surfaceControl, 1.0f);
        transaction.setMatrix(surfaceControl, 1.0f, 0.0f, 0.0f, 1.0f);
        transaction.show(surfaceControl);
    }
}
