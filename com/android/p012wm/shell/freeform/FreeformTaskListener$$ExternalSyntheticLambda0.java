package com.android.p012wm.shell.freeform;

import android.app.ActivityManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;

/* renamed from: com.android.wm.shell.freeform.FreeformTaskListener$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FreeformTaskListener$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Rect f$2;

    public /* synthetic */ FreeformTaskListener$$ExternalSyntheticLambda0(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Rect rect) {
        this.f$0 = runningTaskInfo;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        Rect rect = this.f$2;
        Point point = runningTaskInfo.positionInParent;
        transaction.setPosition(surfaceControl, (float) point.x, (float) point.y).setWindowCrop(surfaceControl, rect.width(), rect.height()).show(surfaceControl);
    }
}
