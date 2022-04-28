package com.android.p012wm.shell.apppairs;

import android.app.ActivityManager;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda6 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda6(AppPair appPair, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.f$0 = appPair;
        this.f$1 = runningTaskInfo;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair appPair = this.f$0;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
        Objects.requireNonNull(appPair);
        if (runningTaskInfo.isVisible) {
            transaction.show(appPair.mRootTaskLeash);
        } else {
            transaction.hide(appPair.mRootTaskLeash);
        }
    }
}
