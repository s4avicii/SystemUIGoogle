package com.android.p012wm.shell;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.TaskViewTransitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskView$$ExternalSyntheticLambda14 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda14 implements Runnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ PendingIntent f$1;
    public final /* synthetic */ Intent f$2;
    public final /* synthetic */ ActivityOptions f$3;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda14(TaskView taskView, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions) {
        this.f$0 = taskView;
        this.f$1 = pendingIntent;
        this.f$2 = intent;
        this.f$3 = activityOptions;
    }

    public final void run() {
        TaskView taskView = this.f$0;
        PendingIntent pendingIntent = this.f$1;
        Intent intent = this.f$2;
        ActivityOptions activityOptions = this.f$3;
        int i = TaskView.$r8$clinit;
        Objects.requireNonNull(taskView);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent, activityOptions.toBundle());
        TaskViewTransitions taskViewTransitions = taskView.mTaskViewTransitions;
        Objects.requireNonNull(taskViewTransitions);
        taskViewTransitions.mPending.add(new TaskViewTransitions.PendingTransition(1, windowContainerTransaction, taskView));
        taskViewTransitions.startNextTransition();
    }
}
