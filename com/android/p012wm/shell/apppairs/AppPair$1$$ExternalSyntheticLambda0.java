package com.android.p012wm.shell.apppairs;

import android.view.SurfaceControl;
import com.android.p012wm.shell.apppairs.AppPair;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$1$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair.C17851 f$0;
    public final /* synthetic */ SurfaceControl f$1;

    public /* synthetic */ AppPair$1$$ExternalSyntheticLambda0(AppPair.C17851 r1, SurfaceControl surfaceControl) {
        this.f$0 = r1;
        this.f$1 = surfaceControl;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair.C17851 r0 = this.f$0;
        SurfaceControl surfaceControl = this.f$1;
        Objects.requireNonNull(r0);
        transaction.show(surfaceControl).setLayer(surfaceControl, 30000).setPosition(surfaceControl, (float) AppPair.this.mSplitLayout.getDividerBounds().left, (float) AppPair.this.mSplitLayout.getDividerBounds().top);
    }
}
