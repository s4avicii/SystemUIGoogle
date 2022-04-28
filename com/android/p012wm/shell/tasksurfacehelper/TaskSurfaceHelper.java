package com.android.p012wm.shell.tasksurfacehelper;

import android.app.ActivityManager;
import android.graphics.Rect;
import com.google.android.systemui.gamedashboard.ShortcutBarView$$ExternalSyntheticLambda4;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelper */
public interface TaskSurfaceHelper {
    void screenshotTask(ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, Executor executor, ShortcutBarView$$ExternalSyntheticLambda4 shortcutBarView$$ExternalSyntheticLambda4) {
    }

    void setGameModeForTask(int i, int i2) {
    }
}
