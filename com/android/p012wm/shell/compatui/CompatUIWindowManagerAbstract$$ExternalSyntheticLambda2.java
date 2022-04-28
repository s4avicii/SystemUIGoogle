package com.android.p012wm.shell.compatui;

import android.util.Log;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIWindowManagerAbstract$$ExternalSyntheticLambda2 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ CompatUIWindowManagerAbstract f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CompatUIWindowManagerAbstract$$ExternalSyntheticLambda2(CompatUIWindowManagerAbstract compatUIWindowManagerAbstract, SurfaceControl surfaceControl, int i) {
        this.f$0 = compatUIWindowManagerAbstract;
        this.f$1 = surfaceControl;
        this.f$2 = i;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        int i = this.f$2;
        if (surfaceControl != null) {
            Objects.requireNonNull(compatUIWindowManagerAbstract);
            if (surfaceControl.isValid()) {
                transaction.setLayer(surfaceControl, i);
                return;
            }
        }
        Objects.requireNonNull(compatUIWindowManagerAbstract);
        Log.w(compatUIWindowManagerAbstract.getClass().getSimpleName(), "The leash has been released.");
    }
}
