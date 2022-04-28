package com.android.p012wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0(int i) {
        this.f$0 = i;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(splitScreenController);
        StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        MainStage mainStage = stageCoordinator.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            StageTaskListener stageTaskListener = null;
            if (stageCoordinator.mMainStage.containsTask(i)) {
                stageTaskListener = stageCoordinator.mMainStage;
            } else if (stageCoordinator.mSideStage.containsTask(i)) {
                stageTaskListener = stageCoordinator.mSideStage;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (stageTaskListener != null && stageTaskListener.containsTask(i)) {
                windowContainerTransaction.reorder(stageTaskListener.mChildrenTaskInfo.get(i).token, true);
            }
            stageCoordinator.applyExitSplitScreen(stageTaskListener, windowContainerTransaction, 0);
        }
    }
}
