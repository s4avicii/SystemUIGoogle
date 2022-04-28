package com.android.p012wm.shell.apppairs;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SurfaceUtils;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.splitscreen.StageTaskListener;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        switch (this.$r8$classId) {
            case 0:
                AppPair appPair = (AppPair) this.f$0;
                Objects.requireNonNull(appPair);
                transaction.remove(appPair.mDimLayer2);
                return;
            case 1:
                ((Runnable) this.f$0).run();
                return;
            default:
                StageTaskListener stageTaskListener = (StageTaskListener) this.f$0;
                Objects.requireNonNull(stageTaskListener);
                stageTaskListener.mDimLayer = SurfaceUtils.makeDimLayer(transaction, stageTaskListener.mRootLeash, "Dim layer", stageTaskListener.mSurfaceSession);
                return;
        }
    }
}
