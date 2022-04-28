package com.android.p012wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda4 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda4(StageCoordinator stageCoordinator) {
        this.f$0 = stageCoordinator;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageCoordinator stageCoordinator = this.f$0;
        Objects.requireNonNull(stageCoordinator);
        transaction.setWindowCrop(stageCoordinator.mMainStage.mRootLeash, (Rect) null).setWindowCrop(stageCoordinator.mSideStage.mRootLeash, (Rect) null);
    }
}
