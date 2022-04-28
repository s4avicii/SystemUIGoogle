package com.android.p012wm.shell.splitscreen;

import android.app.ActivityManager;
import android.window.TransitionInfo;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreenTransitions;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda10 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ SplitScreenTransitions.DismissTransition f$1;
    public final /* synthetic */ TransitionInfo f$2;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda10(StageCoordinator stageCoordinator, SplitScreenTransitions.DismissTransition dismissTransition, TransitionInfo transitionInfo) {
        this.f$0 = stageCoordinator;
        this.f$1 = dismissTransition;
        this.f$2 = transitionInfo;
    }

    public final void accept(Object obj) {
        boolean z;
        StageCoordinator stageCoordinator = this.f$0;
        SplitScreenTransitions.DismissTransition dismissTransition = this.f$1;
        TransitionInfo transitionInfo = this.f$2;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        Objects.requireNonNull(stageCoordinator);
        int i = dismissTransition.mReason;
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 8 || i == 9) {
            z = true;
        } else {
            z = false;
        }
        if (z && stageCoordinator.mShouldUpdateRecents) {
            for (TransitionInfo.Change taskInfo : transitionInfo.getChanges()) {
                ActivityManager.RunningTaskInfo taskInfo2 = taskInfo.getTaskInfo();
                if (taskInfo2 != null && taskInfo2.getWindowingMode() == 1) {
                    recentTasksController.removeSplitPair(taskInfo2.taskId);
                }
            }
        }
    }
}
