package com.android.p012wm.shell;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskView$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda1(TaskView taskView, int i) {
        this.f$0 = taskView;
        this.f$1 = i;
    }

    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        TaskView taskView = this.f$0;
        int i = this.f$1;
        int i2 = TaskView.$r8$clinit;
        Objects.requireNonNull(taskView);
        taskView.setResizeBackgroundColor(transaction, i);
    }
}
