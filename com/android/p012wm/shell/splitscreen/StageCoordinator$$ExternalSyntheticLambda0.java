package com.android.p012wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda0 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda0(StageCoordinator stageCoordinator) {
        this.f$0 = stageCoordinator;
    }

    public final void onRotateDisplay(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        StageCoordinator stageCoordinator = this.f$0;
        Objects.requireNonNull(stageCoordinator);
        MainStage mainStage = stageCoordinator.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive && Transitions.ENABLE_SHELL_TRANSITIONS) {
            SurfaceControl.Transaction acquire = stageCoordinator.mTransactionPool.acquire();
            boolean z = false;
            stageCoordinator.setDividerVisibility(false, acquire);
            stageCoordinator.mDisplayLayout.rotateTo(stageCoordinator.mContext.getResources(), i3);
            SplitLayout splitLayout = stageCoordinator.mSplitLayout;
            DisplayLayout displayLayout = stageCoordinator.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            Rect rect = displayLayout.mStableInsets;
            Objects.requireNonNull(splitLayout);
            if ((((i3 - splitLayout.mRotation) + 4) % 4) % 2 != 0) {
                z = true;
            }
            splitLayout.mRotation = i3;
            Rect rect2 = new Rect(splitLayout.mRootBounds);
            if (z) {
                Rect rect3 = splitLayout.mRootBounds;
                rect2.set(rect3.top, rect3.left, rect3.bottom, rect3.right);
            }
            splitLayout.mTempRect.set(splitLayout.mRootBounds);
            splitLayout.mRootBounds.set(rect2);
            splitLayout.mDividerSnapAlgorithm = splitLayout.getSnapAlgorithm(splitLayout.mContext, splitLayout.mRootBounds, rect);
            splitLayout.initDividerPosition(splitLayout.mTempRect);
            stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction);
            stageCoordinator.updateUnfoldBounds();
            acquire.apply();
            stageCoordinator.mTransactionPool.release(acquire);
        }
    }
}
