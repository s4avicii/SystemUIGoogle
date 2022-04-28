package com.android.p012wm.shell.compatui;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;

/* renamed from: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ SurfaceControl f$0;

    public /* synthetic */ CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0(SurfaceControl surfaceControl) {
        this.f$0 = surfaceControl;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        transaction.remove(this.f$0);
    }
}
