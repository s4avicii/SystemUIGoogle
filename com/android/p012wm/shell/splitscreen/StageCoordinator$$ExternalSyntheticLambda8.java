package com.android.p012wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda8 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda8(StageCoordinator stageCoordinator, boolean z, boolean z2) {
        this.f$0 = stageCoordinator;
        this.f$1 = z;
        this.f$2 = z2;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageCoordinator stageCoordinator = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        Objects.requireNonNull(stageCoordinator);
        transaction.setVisibility(stageCoordinator.mSideStage.mRootLeash, z).setVisibility(stageCoordinator.mMainStage.mRootLeash, z2);
        stageCoordinator.setDividerVisibility(z2, transaction);
    }
}
