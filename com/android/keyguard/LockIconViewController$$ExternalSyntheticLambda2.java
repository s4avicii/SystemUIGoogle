package com.android.keyguard;

import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.onehanded.OneHandedTouchHandler;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuView;
import com.android.p012wm.shell.splitscreen.MainStage;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import com.android.p012wm.shell.splitscreen.StageTaskListener;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.media.dialog.MediaOutputBaseDialog;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.volume.VolumeDialogImpl;
import com.google.android.systemui.assist.OpaLayout;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LockIconViewController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LockIconViewController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                LockIconViewController.$r8$lambda$h98ceOtiS5JD1Nfnu1Y0fyk_1uo((LockIconViewController) this.f$0);
                return;
            case 1:
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.f$0;
                int i = MediaOutputBaseDialog.$r8$clinit;
                Objects.requireNonNull(mediaOutputBaseDialog);
                mediaOutputBaseDialog.refresh();
                return;
            case 2:
                int i2 = QuickStatusBarHeader.$r8$clinit;
                ((QuickStatusBarHeader) this.f$0).updateAnimators();
                return;
            case 3:
                InternetDialog internetDialog = (InternetDialog) this.f$0;
                boolean z2 = InternetDialog.DEBUG;
                Objects.requireNonNull(internetDialog);
                internetDialog.updateDialog(true);
                return;
            case 4:
                ((NetworkControllerImpl) this.f$0).handleConfigurationChanged();
                return;
            case 5:
                AutoTileManager.C14002 r9 = (AutoTileManager.C14002) this.f$0;
                Objects.requireNonNull(r9);
                AutoTileManager autoTileManager = AutoTileManager.this;
                autoTileManager.mDataSaverController.removeCallback(autoTileManager.mDataSaverListener);
                return;
            case FalsingManager.VERSION /*6*/:
                VolumeDialogImpl volumeDialogImpl = (VolumeDialogImpl) this.f$0;
                String str = VolumeDialogImpl.TAG;
                Objects.requireNonNull(volumeDialogImpl);
                volumeDialogImpl.getDrawerIconViewForMode(volumeDialogImpl.mState.ringerModeInternal).setVisibility(0);
                return;
            case 7:
                SyncTransactionQueue syncTransactionQueue = (SyncTransactionQueue) this.f$0;
                Objects.requireNonNull(syncTransactionQueue);
                synchronized (syncTransactionQueue.mQueue) {
                    SyncTransactionQueue.SyncCallback syncCallback = syncTransactionQueue.mInFlight;
                    if (syncCallback != null && syncTransactionQueue.mQueue.contains(syncCallback)) {
                        Slog.w("SyncTransactionQueue", "Sync Transaction timed-out: " + syncTransactionQueue.mInFlight.mWCT);
                        SyncTransactionQueue.SyncCallback syncCallback2 = syncTransactionQueue.mInFlight;
                        syncCallback2.onTransactionReady(syncCallback2.mId, new SurfaceControl.Transaction());
                    }
                }
                return;
            case 8:
                OneHandedTouchHandler oneHandedTouchHandler = (OneHandedTouchHandler) this.f$0;
                Objects.requireNonNull(oneHandedTouchHandler);
                oneHandedTouchHandler.mInputEventReceiver = new OneHandedTouchHandler.EventReceiver(oneHandedTouchHandler.mInputMonitor.getInputChannel(), Looper.myLooper());
                return;
            case 9:
                TvPipMenuController tvPipMenuController = (TvPipMenuController) this.f$0;
                Objects.requireNonNull(tvPipMenuController);
                TvPipMenuView tvPipMenuView = tvPipMenuController.mPipMenuView;
                if (tvPipMenuView != null && tvPipMenuView.getViewRootImpl() != null) {
                    tvPipMenuController.mMoveTransform.getValues(tvPipMenuController.mTmpValues);
                    try {
                        tvPipMenuController.mPipMenuView.getViewRootImpl().getAccessibilityEmbeddedConnection().setScreenMatrix(tvPipMenuController.mTmpValues);
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                } else {
                    return;
                }
            case 10:
                SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) this.f$0;
                Objects.requireNonNull(splitScreenImpl);
                SplitScreenController splitScreenController = SplitScreenController.this;
                Objects.requireNonNull(splitScreenController);
                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                Objects.requireNonNull(stageCoordinator);
                MainStage mainStage = stageCoordinator.mMainStage;
                Objects.requireNonNull(mainStage);
                if (mainStage.mIsActive) {
                    StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                    boolean z3 = stageTaskListener.mRootTaskInfo.isVisible;
                    StageTaskListener stageTaskListener2 = stageCoordinator.mSideStage;
                    if (z3 != stageTaskListener2.mRootTaskInfo.isVisible) {
                        z = true;
                    }
                    if (!z) {
                        return;
                    }
                    if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                        if (!z3) {
                            stageTaskListener = stageTaskListener2;
                        }
                        stageCoordinator.exitSplitScreen(stageTaskListener, 8);
                        return;
                    }
                    boolean z4 = !z3;
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    stageCoordinator.prepareExitSplitScreen(z4 ? 1 : 0, windowContainerTransaction);
                    stageCoordinator.mSplitTransitions.startDismissTransition((IBinder) null, windowContainerTransaction, stageCoordinator, z4, 8);
                    return;
                }
                return;
            default:
                ((OpaLayout) this.f$0).getOpaEnabled();
                return;
        }
    }
}
