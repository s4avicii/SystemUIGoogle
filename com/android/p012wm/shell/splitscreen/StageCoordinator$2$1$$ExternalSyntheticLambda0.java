package com.android.p012wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$2$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$2$1$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ StageCoordinator.C19232.C19241 f$0;

    public /* synthetic */ StageCoordinator$2$1$$ExternalSyntheticLambda0(StageCoordinator.C19232.C19241 r1) {
        this.f$0 = r1;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageCoordinator.C19232.C19241 r1 = this.f$0;
        Objects.requireNonNull(r1);
        StageCoordinator.this.setDividerVisibility(true, transaction);
    }
}
