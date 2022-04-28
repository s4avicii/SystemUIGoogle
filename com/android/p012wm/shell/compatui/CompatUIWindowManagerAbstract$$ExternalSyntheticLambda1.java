package com.android.p012wm.shell.compatui;

import android.util.Log;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ CompatUIWindowManagerAbstract f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1(CompatUIWindowManagerAbstract compatUIWindowManagerAbstract, int i, int i2) {
        this.f$0 = compatUIWindowManagerAbstract;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(compatUIWindowManagerAbstract);
        SurfaceControl surfaceControl = compatUIWindowManagerAbstract.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            Log.w(compatUIWindowManagerAbstract.getClass().getSimpleName(), "The leash has been released.");
        } else {
            transaction.setPosition(compatUIWindowManagerAbstract.mLeash, (float) i, (float) i2);
        }
    }
}
