package com.android.p012wm.shell.apppairs;

import android.graphics.Point;
import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.apppairs.AppPair$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppPair$$ExternalSyntheticLambda5 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ AppPair f$0;

    public /* synthetic */ AppPair$$ExternalSyntheticLambda5(AppPair appPair) {
        this.f$0 = appPair;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        AppPair appPair = this.f$0;
        Objects.requireNonNull(appPair);
        SurfaceControl.Transaction show = transaction.show(appPair.mRootTaskLeash).show(appPair.mTaskLeash1).show(appPair.mTaskLeash2);
        SurfaceControl surfaceControl = appPair.mTaskLeash1;
        Point point = appPair.mTaskInfo1.positionInParent;
        SurfaceControl.Transaction position = show.setPosition(surfaceControl, (float) point.x, (float) point.y);
        SurfaceControl surfaceControl2 = appPair.mTaskLeash2;
        Point point2 = appPair.mTaskInfo2.positionInParent;
        position.setPosition(surfaceControl2, (float) point2.x, (float) point2.y);
    }
}
