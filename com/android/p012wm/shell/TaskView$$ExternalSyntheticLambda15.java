package com.android.p012wm.shell;

import android.content.ComponentName;
import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskView$$ExternalSyntheticLambda15 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ ComponentName f$3;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda15(TaskView taskView, boolean z, int i, ComponentName componentName) {
        this.f$0 = taskView;
        this.f$1 = z;
        this.f$2 = i;
        this.f$3 = componentName;
    }

    public final void run() {
        TaskView taskView = this.f$0;
        boolean z = this.f$1;
        int i = this.f$2;
        int i2 = TaskView.$r8$clinit;
        Objects.requireNonNull(taskView);
        if (z) {
            taskView.mListener.onTaskCreated(i);
        }
        if (!z || !taskView.mSurfaceCreated) {
            taskView.mListener.onTaskVisibilityChanged(taskView.mSurfaceCreated);
        }
    }
}
