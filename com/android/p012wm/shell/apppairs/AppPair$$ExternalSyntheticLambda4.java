package com.android.p012wm.shell.apppairs;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SurfaceUtils;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda4 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda4(AppPair appPair) {
        this.f$0 = appPair;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair appPair = this.f$0;
        Objects.requireNonNull(appPair);
        appPair.mDimLayer2 = SurfaceUtils.makeDimLayer(transaction, appPair.mTaskLeash2, "Dim layer", appPair.mSurfaceSession);
    }
}
