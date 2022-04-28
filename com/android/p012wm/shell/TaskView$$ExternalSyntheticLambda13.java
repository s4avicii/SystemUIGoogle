package com.android.p012wm.shell;

import android.content.ComponentName;
import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskView$$ExternalSyntheticLambda13 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ComponentName f$2;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda13(TaskView taskView, int i, ComponentName componentName) {
        this.f$0 = taskView;
        this.f$1 = i;
        this.f$2 = componentName;
    }

    public final void run() {
        TaskView taskView = this.f$0;
        int i = this.f$1;
        int i2 = TaskView.$r8$clinit;
        Objects.requireNonNull(taskView);
        taskView.mListener.onTaskCreated(i);
    }
}
