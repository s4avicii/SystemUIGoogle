package com.android.p012wm.shell.tasksurfacehelper;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.window.TaskAppearedInfo;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.ScreenshotUtils;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController;
import com.google.android.systemui.gamedashboard.ShortcutBarView$$ExternalSyntheticLambda4;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController$TaskSurfaceHelperImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1935x3c1cf254 implements Runnable {
    public final /* synthetic */ TaskSurfaceHelperController.TaskSurfaceHelperImpl f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ Executor f$3;
    public final /* synthetic */ Consumer f$4;

    public /* synthetic */ C1935x3c1cf254(TaskSurfaceHelperController.TaskSurfaceHelperImpl taskSurfaceHelperImpl, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, Executor executor, ShortcutBarView$$ExternalSyntheticLambda4 shortcutBarView$$ExternalSyntheticLambda4) {
        this.f$0 = taskSurfaceHelperImpl;
        this.f$1 = runningTaskInfo;
        this.f$2 = rect;
        this.f$3 = executor;
        this.f$4 = shortcutBarView$$ExternalSyntheticLambda4;
    }

    public final void run() {
        TaskSurfaceHelperController.TaskSurfaceHelperImpl taskSurfaceHelperImpl = this.f$0;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
        Rect rect = this.f$2;
        Executor executor = this.f$3;
        Consumer consumer = this.f$4;
        Objects.requireNonNull(taskSurfaceHelperImpl);
        TaskSurfaceHelperController taskSurfaceHelperController = TaskSurfaceHelperController.this;
        C1936x3c1cf255 taskSurfaceHelperController$TaskSurfaceHelperImpl$$ExternalSyntheticLambda2 = new C1936x3c1cf255(executor, consumer);
        Objects.requireNonNull(taskSurfaceHelperController);
        ShellTaskOrganizer shellTaskOrganizer = taskSurfaceHelperController.mTaskOrganizer;
        Objects.requireNonNull(shellTaskOrganizer);
        TaskAppearedInfo taskAppearedInfo = shellTaskOrganizer.mTasks.get(runningTaskInfo.taskId);
        if (taskAppearedInfo != null) {
            ScreenshotUtils.captureLayer(taskAppearedInfo.getLeash(), rect, taskSurfaceHelperController$TaskSurfaceHelperImpl$$ExternalSyntheticLambda2);
        }
    }
}
