package com.android.p012wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ RemoteAnimationTarget[][] f$0;
    public final /* synthetic */ RemoteAnimationTarget[] f$2;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9(RemoteAnimationTarget[][] remoteAnimationTargetArr, boolean z, RemoteAnimationTarget[] remoteAnimationTargetArr2) {
        this.f$0 = remoteAnimationTargetArr;
        this.f$2 = remoteAnimationTargetArr2;
    }

    public final void accept(Object obj) {
        RemoteAnimationTarget[] remoteAnimationTargetArr;
        RemoteAnimationTarget[][] remoteAnimationTargetArr2 = this.f$0;
        RemoteAnimationTarget[] remoteAnimationTargetArr3 = this.f$2;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(splitScreenController);
        if (Transitions.ENABLE_SHELL_TRANSITIONS || remoteAnimationTargetArr3.length < 2) {
            remoteAnimationTargetArr = null;
        } else {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            SurfaceControl surfaceControl = splitScreenController.mSplitTasksContainerLayer;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
            }
            SurfaceControl.Builder callsite = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("RecentsAnimationSplitTasks").setHidden(false).setCallsite("SplitScreenController#onGoingtoRecentsLegacy");
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = splitScreenController.mRootTDAOrganizer;
            Objects.requireNonNull(rootTaskDisplayAreaOrganizer);
            callsite.setParent(rootTaskDisplayAreaOrganizer.mLeashes.get(0));
            splitScreenController.mSplitTasksContainerLayer = callsite.build();
            Arrays.sort(remoteAnimationTargetArr3, SplitScreenController$$ExternalSyntheticLambda0.INSTANCE);
            int length = remoteAnimationTargetArr3.length;
            int i = 0;
            int i2 = 1;
            while (i < length) {
                RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr3[i];
                transaction.reparent(remoteAnimationTarget.leash, splitScreenController.mSplitTasksContainerLayer);
                SurfaceControl surfaceControl2 = remoteAnimationTarget.leash;
                Rect rect = remoteAnimationTarget.screenSpaceBounds;
                transaction.setPosition(surfaceControl2, (float) rect.left, (float) rect.top);
                transaction.setLayer(remoteAnimationTarget.leash, i2);
                i++;
                i2++;
            }
            transaction.apply();
            transaction.close();
            remoteAnimationTargetArr = new RemoteAnimationTarget[]{splitScreenController.mStageCoordinator.getDividerBarLegacyTarget()};
        }
        remoteAnimationTargetArr2[0] = remoteAnimationTargetArr;
    }
}
