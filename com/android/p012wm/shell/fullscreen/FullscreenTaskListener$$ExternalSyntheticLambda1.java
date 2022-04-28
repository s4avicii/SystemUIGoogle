package com.android.p012wm.shell.fullscreen;

import android.graphics.Point;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.fullscreen.FullscreenTaskListener;

/* renamed from: com.android.wm.shell.fullscreen.FullscreenTaskListener$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FullscreenTaskListener$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ FullscreenTaskListener.TaskData f$0;
    public final /* synthetic */ Point f$1;

    public /* synthetic */ FullscreenTaskListener$$ExternalSyntheticLambda1(FullscreenTaskListener.TaskData taskData, Point point) {
        this.f$0 = taskData;
        this.f$1 = point;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        FullscreenTaskListener.TaskData taskData = this.f$0;
        Point point = this.f$1;
        transaction.setPosition(taskData.surface, (float) point.x, (float) point.y);
    }
}
