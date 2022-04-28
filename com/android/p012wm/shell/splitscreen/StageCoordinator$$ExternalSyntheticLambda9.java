package com.android.p012wm.shell.splitscreen;

import com.android.p012wm.shell.recents.RecentTasksController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda9(StageCoordinator stageCoordinator, int i) {
        this.f$0 = stageCoordinator;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        StageCoordinator stageCoordinator = this.f$0;
        int i = this.f$1;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        Objects.requireNonNull(stageCoordinator);
        boolean z = true;
        if (!(i == 1 || i == 2 || i == 3 || i == 4 || i == 8 || i == 9)) {
            z = false;
        }
        if (z && stageCoordinator.mShouldUpdateRecents) {
            recentTasksController.removeSplitPair(stageCoordinator.mMainStage.getTopVisibleChildTaskId());
            recentTasksController.removeSplitPair(stageCoordinator.mSideStage.getTopVisibleChildTaskId());
        }
    }
}
