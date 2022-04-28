package com.android.p012wm.shell.apppairs;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.split.SplitLayout;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda7 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;
    public final /* synthetic */ SplitLayout f$1;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda7(AppPair appPair, SplitLayout splitLayout) {
        this.f$0 = appPair;
        this.f$1 = splitLayout;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair appPair = this.f$0;
        SplitLayout splitLayout = this.f$1;
        Objects.requireNonNull(appPair);
        splitLayout.applySurfaceChanges(transaction, appPair.mTaskLeash1, appPair.mTaskLeash2, appPair.mDimLayer1, appPair.mDimLayer2);
    }
}
