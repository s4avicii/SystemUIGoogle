package com.android.p012wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$2$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$2$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageCoordinator$2$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        switch (this.$r8$classId) {
            case 0:
                StageCoordinator.C19232 r2 = (StageCoordinator.C19232) this.f$0;
                Objects.requireNonNull(r2);
                StageCoordinator.this.setDividerVisibility(true, transaction);
                return;
            default:
                LegacySplitScreenController legacySplitScreenController = (LegacySplitScreenController) this.f$0;
                Objects.requireNonNull(legacySplitScreenController);
                legacySplitScreenController.mView.setMinimizedDockStack(false, legacySplitScreenController.mHomeStackResizable, transaction);
                return;
        }
    }
}
