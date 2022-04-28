package com.android.p012wm.shell.compatui;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.window.TaskAppearedInfo;
import com.android.internal.util.FrameworkStatsLog;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.compatui.CompatUIController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUILayout$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ CompatUILayout f$0;

    public /* synthetic */ CompatUILayout$$ExternalSyntheticLambda2(CompatUILayout compatUILayout) {
        this.f$0 = compatUILayout;
    }

    public final void onClick(View view) {
        TaskAppearedInfo taskAppearedInfo;
        CompatUILayout compatUILayout = this.f$0;
        int i = CompatUILayout.$r8$clinit;
        Objects.requireNonNull(compatUILayout);
        CompatUIWindowManager compatUIWindowManager = compatUILayout.mWindowManager;
        Objects.requireNonNull(compatUIWindowManager);
        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager.mCallback;
        int i2 = compatUIWindowManager.mTaskId;
        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
        Objects.requireNonNull(shellTaskOrganizer);
        synchronized (shellTaskOrganizer.mLock) {
            taskAppearedInfo = shellTaskOrganizer.mTasks.get(i2);
        }
        if (taskAppearedInfo != null) {
            ActivityInfo activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo;
            if (activityInfo != null) {
                FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 2);
            }
            shellTaskOrganizer.restartTaskTopActivityProcessIfVisible(taskAppearedInfo.getTaskInfo().token);
        }
    }
}
