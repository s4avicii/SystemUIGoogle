package com.android.p012wm.shell.splitscreen;

import android.graphics.Rect;
import android.os.Bundle;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Bundle f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ RemoteTransition f$6;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(int i, Bundle bundle, int i2, Bundle bundle2, int i3, float f, RemoteTransition remoteTransition) {
        this.f$0 = i;
        this.f$1 = bundle;
        this.f$2 = i2;
        this.f$3 = bundle2;
        this.f$4 = i3;
        this.f$5 = f;
        this.f$6 = remoteTransition;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        Bundle bundle = this.f$1;
        int i2 = this.f$2;
        Bundle bundle2 = this.f$3;
        int i3 = this.f$4;
        float f = this.f$5;
        RemoteTransition remoteTransition = this.f$6;
        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        stageCoordinator.setSideStagePosition(i3, windowContainerTransaction);
        stageCoordinator.mSplitLayout.setDivideRatio(f);
        stageCoordinator.mMainStage.activate(stageCoordinator.getMainStageBounds(), windowContainerTransaction, false);
        SideStage sideStage = stageCoordinator.mSideStage;
        Rect sideStageBounds = stageCoordinator.getSideStageBounds();
        Objects.requireNonNull(sideStage);
        windowContainerTransaction.setBounds(sideStage.mRootTaskInfo.token, sideStageBounds);
        bundle.putParcelable("android.activity.launchRootTaskToken", stageCoordinator.mMainStage.mRootTaskInfo.token);
        bundle2.putParcelable("android.activity.launchRootTaskToken", stageCoordinator.mSideStage.mRootTaskInfo.token);
        windowContainerTransaction.startTask(i, bundle);
        windowContainerTransaction.startTask(i2, bundle2);
        stageCoordinator.mSplitTransitions.startEnterTransition(16, windowContainerTransaction, remoteTransition, stageCoordinator);
    }
}
