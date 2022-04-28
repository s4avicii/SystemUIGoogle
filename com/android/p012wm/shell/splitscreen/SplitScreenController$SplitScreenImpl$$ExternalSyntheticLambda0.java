package com.android.p012wm.shell.splitscreen;

import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(SplitScreenController.SplitScreenImpl splitScreenImpl, boolean z) {
        this.f$0 = splitScreenImpl;
        this.f$1 = z;
    }

    public final void run() {
        int i;
        StageTaskListener stageTaskListener;
        SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(splitScreenImpl);
        SplitScreenController splitScreenController = SplitScreenController.this;
        Objects.requireNonNull(splitScreenController);
        StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        MainStage mainStage = stageCoordinator.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            boolean z2 = Transitions.ENABLE_SHELL_TRANSITIONS;
            if (z2) {
                stageCoordinator.setDividerVisibility(!z, (SurfaceControl.Transaction) null);
            }
            if (!z && (i = stageCoordinator.mTopStageAfterFoldDismiss) != -1) {
                if (z2) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    stageCoordinator.prepareExitSplitScreen(stageCoordinator.mTopStageAfterFoldDismiss, windowContainerTransaction);
                    stageCoordinator.mSplitTransitions.startDismissTransition((IBinder) null, windowContainerTransaction, stageCoordinator, stageCoordinator.mTopStageAfterFoldDismiss, 3);
                    return;
                }
                if (i == 0) {
                    stageTaskListener = stageCoordinator.mMainStage;
                } else {
                    stageTaskListener = stageCoordinator.mSideStage;
                }
                stageCoordinator.exitSplitScreen(stageTaskListener, 3);
            }
        }
    }
}
