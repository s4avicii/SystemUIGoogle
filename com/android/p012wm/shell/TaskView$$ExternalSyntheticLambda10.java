package com.android.p012wm.shell;

import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskView$$ExternalSyntheticLambda10 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda10(TaskView taskView, int i) {
        this.f$0 = taskView;
        this.f$1 = i;
    }

    public final void run() {
        TaskView taskView = this.f$0;
        int i = TaskView.$r8$clinit;
        Objects.requireNonNull(taskView);
        taskView.mListener.onTaskRemovalStarted();
    }
}
