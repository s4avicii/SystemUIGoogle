package com.android.p012wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.split.SplitLayout;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda7 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ SplitLayout f$1;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda7(StageCoordinator stageCoordinator, SplitLayout splitLayout) {
        this.f$0 = stageCoordinator;
        this.f$1 = splitLayout;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageCoordinator stageCoordinator = this.f$0;
        SplitLayout splitLayout = this.f$1;
        Objects.requireNonNull(stageCoordinator);
        stageCoordinator.updateSurfaceBounds(splitLayout, transaction);
        stageCoordinator.mMainStage.onResizing(stageCoordinator.getMainStageBounds(), transaction);
        stageCoordinator.mSideStage.onResizing(stageCoordinator.getSideStageBounds(), transaction);
    }
}
