package com.android.p012wm.shell.transition;

import android.os.RemoteException;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IWindowContainerTransactionCallback;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.LegacyTransitions$LegacyTransition */
public final class LegacyTransitions$LegacyTransition {
    public final RemoteAnimationAdapter mAdapter = new RemoteAnimationAdapter(new RemoteAnimationWrapper(), 0, 0);
    public RemoteAnimationTarget[] mApps;
    public boolean mCancelled = false;
    public IRemoteAnimationFinishedCallback mFinishCallback = null;
    public final LegacyTransitions$ILegacyTransition mLegacyTransition;
    public final SyncCallback mSyncCallback = new SyncCallback();
    public int mSyncId = -1;
    public SurfaceControl.Transaction mTransaction;
    public int mTransit;

    /* renamed from: com.android.wm.shell.transition.LegacyTransitions$LegacyTransition$RemoteAnimationWrapper */
    public class RemoteAnimationWrapper extends IRemoteAnimationRunner.Stub {
        public RemoteAnimationWrapper() {
        }

        public final void onAnimationCancelled() throws RemoteException {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mCancelled = true;
            legacyTransitions$LegacyTransition.mApps = null;
            LegacyTransitions$LegacyTransition.m297$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mTransit = i;
            legacyTransitions$LegacyTransition.mApps = remoteAnimationTargetArr;
            legacyTransitions$LegacyTransition.mFinishCallback = iRemoteAnimationFinishedCallback;
            LegacyTransitions$LegacyTransition.m297$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }
    }

    /* renamed from: com.android.wm.shell.transition.LegacyTransitions$LegacyTransition$SyncCallback */
    public class SyncCallback extends IWindowContainerTransactionCallback.Stub {
        public SyncCallback() {
        }

        public final void onTransactionReady(int i, SurfaceControl.Transaction transaction) throws RemoteException {
            LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = LegacyTransitions$LegacyTransition.this;
            legacyTransitions$LegacyTransition.mSyncId = i;
            legacyTransitions$LegacyTransition.mTransaction = transaction;
            LegacyTransitions$LegacyTransition.m297$$Nest$mcheckApply(legacyTransitions$LegacyTransition);
        }
    }

    /* renamed from: -$$Nest$mcheckApply  reason: not valid java name */
    public static void m297$$Nest$mcheckApply(LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition) {
        Objects.requireNonNull(legacyTransitions$LegacyTransition);
        if (legacyTransitions$LegacyTransition.mSyncId >= 0) {
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = legacyTransitions$LegacyTransition.mFinishCallback;
            if (iRemoteAnimationFinishedCallback != null || legacyTransitions$LegacyTransition.mCancelled) {
                LegacyTransitions$ILegacyTransition legacyTransitions$ILegacyTransition = legacyTransitions$LegacyTransition.mLegacyTransition;
                RemoteAnimationTarget[] remoteAnimationTargetArr = legacyTransitions$LegacyTransition.mApps;
                SurfaceControl.Transaction transaction = legacyTransitions$LegacyTransition.mTransaction;
                SplitScreenController.C19181 r1 = (SplitScreenController.C19181) legacyTransitions$ILegacyTransition;
                Objects.requireNonNull(r1);
                if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    transaction.apply();
                    return;
                }
                SplitScreenController.this.mStageCoordinator.updateSurfaceBounds((SplitLayout) null, transaction);
                for (int i = 0; i < remoteAnimationTargetArr.length; i++) {
                    if (remoteAnimationTargetArr[i].mode == 0) {
                        transaction.show(remoteAnimationTargetArr[i].leash);
                    }
                }
                transaction.apply();
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        Slog.e("SplitScreenController", "Error finishing legacy transition: ", e);
                    }
                }
                SplitScreenController.this.mSyncQueue.queue(windowContainerTransaction);
            }
        }
    }

    public LegacyTransitions$LegacyTransition(SplitScreenController.C19181 r8) {
        this.mLegacyTransition = r8;
        this.mTransit = 1;
    }
}
