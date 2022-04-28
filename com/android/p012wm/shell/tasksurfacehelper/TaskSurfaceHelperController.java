package com.android.p012wm.shell.tasksurfacehelper;

import android.app.ActivityManager;
import android.graphics.Rect;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.google.android.systemui.gamedashboard.ShortcutBarView$$ExternalSyntheticLambda4;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController */
public final class TaskSurfaceHelperController {
    public final TaskSurfaceHelperImpl mImpl = new TaskSurfaceHelperImpl();
    public final ShellExecutor mMainExecutor;
    public final ShellTaskOrganizer mTaskOrganizer;

    /* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController$TaskSurfaceHelperImpl */
    public class TaskSurfaceHelperImpl implements TaskSurfaceHelper {
        public TaskSurfaceHelperImpl() {
        }

        public final void screenshotTask(ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, Executor executor, ShortcutBarView$$ExternalSyntheticLambda4 shortcutBarView$$ExternalSyntheticLambda4) {
            TaskSurfaceHelperController.this.mMainExecutor.execute(new C1935x3c1cf254(this, runningTaskInfo, rect, executor, shortcutBarView$$ExternalSyntheticLambda4));
        }

        public final void setGameModeForTask(int i, int i2) {
            TaskSurfaceHelperController.this.mMainExecutor.execute(new C1934x3c1cf253(this, i, i2));
        }
    }

    public TaskSurfaceHelperController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mMainExecutor = shellExecutor;
    }
}
