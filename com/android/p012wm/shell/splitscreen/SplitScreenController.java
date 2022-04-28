package com.android.p012wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p012wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.pip.PipMediaController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.ISplitScreen;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.transition.LegacyTransitions$ILegacyTransition;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.plugins.FalsingManager;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController */
public final class SplitScreenController implements DragAndDropPolicy.Starter, RemoteCallable<SplitScreenController> {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final IconProvider mIconProvider;
    public final SplitScreenImpl mImpl = new SplitScreenImpl();
    public final SplitscreenEventLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final Optional<RecentTasksController> mRecentTasksOptional;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    public SurfaceControl mSplitTasksContainerLayer;
    public StageCoordinator mStageCoordinator;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;
    public final Provider<Optional<StageTaskUnfoldController>> mUnfoldControllerProvider;

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl */
    public class SplitScreenImpl implements SplitScreen {
        public final ArrayMap<SplitScreen.SplitScreenListener, Executor> mExecutors = new ArrayMap<>();
        public ISplitScreenImpl mISplitScreen;

        public SplitScreenImpl() {
        }

        public final ISplitScreen createExternalInterface() {
            ISplitScreenImpl iSplitScreenImpl = this.mISplitScreen;
            if (iSplitScreenImpl != null) {
                Objects.requireNonNull(iSplitScreenImpl);
                iSplitScreenImpl.mController = null;
            }
            ISplitScreenImpl iSplitScreenImpl2 = new ISplitScreenImpl(SplitScreenController.this);
            this.mISplitScreen = iSplitScreenImpl2;
            return iSplitScreenImpl2;
        }

        public final void onFinishedWakingUp() {
            SplitScreenController.this.mMainExecutor.execute(new LockIconViewController$$ExternalSyntheticLambda2(this, 10));
        }

        public final void onKeyguardVisibilityChanged(boolean z) {
            SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(this, z));
        }
    }

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl */
    public static class ISplitScreenImpl extends ISplitScreen.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public SplitScreenController mController;
        public final SingleInstanceRemoteListener<SplitScreenController, ISplitScreenListener> mListener;
        public final C19191 mSplitScreenListener = new SplitScreen.SplitScreenListener() {
            public final void onStagePositionChanged(int i, int i2) {
                SingleInstanceRemoteListener<SplitScreenController, ISplitScreenListener> singleInstanceRemoteListener = ISplitScreenImpl.this.mListener;
                Objects.requireNonNull(singleInstanceRemoteListener);
                L l = singleInstanceRemoteListener.mListener;
                if (l == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((ISplitScreenListener) l).onStagePositionChanged(i, i2);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            public final void onTaskStageChanged(int i, int i2, boolean z) {
                SingleInstanceRemoteListener<SplitScreenController, ISplitScreenListener> singleInstanceRemoteListener = ISplitScreenImpl.this.mListener;
                Objects.requireNonNull(singleInstanceRemoteListener);
                L l = singleInstanceRemoteListener.mListener;
                if (l == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((ISplitScreenListener) l).onTaskStageChanged(i, i2, z);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };

        public ISplitScreenImpl(SplitScreenController splitScreenController) {
            this.mController = splitScreenController;
            this.mListener = new SingleInstanceRemoteListener<>(splitScreenController, new PipMediaController$$ExternalSyntheticLambda2(this, 1), new PipMediaController$$ExternalSyntheticLambda1(this, 3));
        }
    }

    public final void getStageBounds(Rect rect, Rect rect2) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        SplitLayout splitLayout = stageCoordinator.mSplitLayout;
        Objects.requireNonNull(splitLayout);
        rect.set(new Rect(splitLayout.mBounds1));
        SplitLayout splitLayout2 = stageCoordinator.mSplitLayout;
        Objects.requireNonNull(splitLayout2);
        rect2.set(new Rect(splitLayout2.mBounds2));
    }

    public final boolean isSplitScreenVisible() {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        if (!stageCoordinator.mSideStageListener.mVisible || !stageCoordinator.mMainStageListener.mVisible) {
            return false;
        }
        return true;
    }

    public final void moveToStage(int i, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            StageCoordinator stageCoordinator = this.mStageCoordinator;
            Objects.requireNonNull(stageCoordinator);
            if (i2 == 0) {
                stageTaskListener = stageCoordinator.mMainStage;
                if (i3 == 0) {
                    i3 = 1;
                } else if (i3 != 1) {
                    i3 = -1;
                } else {
                    i3 = 0;
                }
            } else if (i2 == 1) {
                stageTaskListener = stageCoordinator.mSideStage;
            } else {
                MainStage mainStage = stageCoordinator.mMainStage;
                Objects.requireNonNull(mainStage);
                if (mainStage.mIsActive) {
                    int i4 = stageCoordinator.mSideStagePosition;
                    if (i3 == i4) {
                        stageTaskListener2 = stageCoordinator.mSideStage;
                    } else {
                        stageTaskListener2 = stageCoordinator.mMainStage;
                    }
                    StageTaskListener stageTaskListener3 = stageTaskListener2;
                    i3 = i4;
                    stageTaskListener = stageTaskListener3;
                } else {
                    stageTaskListener = stageCoordinator.mSideStage;
                }
            }
            stageCoordinator.setSideStagePosition(i3, windowContainerTransaction);
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            stageTaskListener.evictAllChildren(windowContainerTransaction2);
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0).setBounds(runningTaskInfo.token, (Rect) null);
            windowContainerTransaction.reparent(runningTaskInfo.token, stageTaskListener.mRootTaskInfo.token, true);
            if (!windowContainerTransaction2.isEmpty()) {
                windowContainerTransaction.merge(windowContainerTransaction2, true);
            }
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, (ActivityManager.RunningTaskInfo) null, -1);
                stageCoordinator.mSplitTransitions.startEnterTransition(17, windowContainerTransaction, (RemoteTransition) null, stageCoordinator);
                return;
            }
            stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unknown taskId", i));
    }

    public final boolean removeFromSideStage(int i) {
        WindowContainerToken windowContainerToken;
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        SideStage sideStage = stageCoordinator.mSideStage;
        MainStage mainStage = stageCoordinator.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            windowContainerToken = stageCoordinator.mMainStage.mRootTaskInfo.token;
        } else {
            windowContainerToken = null;
        }
        Objects.requireNonNull(sideStage);
        ActivityManager.RunningTaskInfo runningTaskInfo = sideStage.mChildrenTaskInfo.get(i);
        boolean z = false;
        if (runningTaskInfo != null) {
            windowContainerTransaction.reparent(runningTaskInfo.token, windowContainerToken, false);
            z = true;
        }
        stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        return z;
    }

    public final void setSideStageVisibility(boolean z) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        if (stageCoordinator.mSideStageListener.mVisible != z) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            SideStage sideStage = stageCoordinator.mSideStage;
            Objects.requireNonNull(sideStage);
            windowContainerTransaction.reorder(sideStage.mRootTaskInfo.token, z);
            stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    public final void startIntent(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle) {
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            final WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            this.mStageCoordinator.prepareEvictChildTasks(i, windowContainerTransaction);
            C19181 r2 = new LegacyTransitions$ILegacyTransition() {
            };
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            windowContainerTransaction2.sendPendingIntent(pendingIntent, intent, this.mStageCoordinator.resolveStartStage(-1, i, bundle, windowContainerTransaction2));
            SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
            Objects.requireNonNull(syncTransactionQueue);
            if (!windowContainerTransaction2.isEmpty()) {
                SyncTransactionQueue.SyncCallback syncCallback = new SyncTransactionQueue.SyncCallback(r2, windowContainerTransaction2);
                synchronized (syncTransactionQueue.mQueue) {
                    syncTransactionQueue.mQueue.add(syncCallback);
                    if (syncTransactionQueue.mQueue.size() == 1) {
                        syncCallback.send();
                    }
                }
                return;
            }
            return;
        }
        try {
            PendingIntent pendingIntent2 = pendingIntent;
            pendingIntent2.send(this.mContext, 0, intent, (PendingIntent.OnFinished) null, (Handler) null, (String) null, this.mStageCoordinator.resolveStartStage(-1, i, bundle, (WindowContainerTransaction) null));
        } catch (PendingIntent.CanceledException e) {
            Slog.e("SplitScreenController", "Failed to launch task", e);
        }
    }

    public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
        Bundle resolveStartStage = this.mStageCoordinator.resolveStartStage(-1, i, bundle, (WindowContainerTransaction) null);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        this.mStageCoordinator.prepareEvictChildTasks(i, windowContainerTransaction);
        try {
            ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, (Rect) null, resolveStartStage, userHandle);
            this.mSyncQueue.queue(windowContainerTransaction);
        } catch (ActivityNotFoundException e) {
            Slog.e("SplitScreenController", "Failed to launch shortcut", e);
        }
    }

    public final void startTask(int i, int i2, Bundle bundle) {
        Bundle resolveStartStage = this.mStageCoordinator.resolveStartStage(-1, i2, bundle, (WindowContainerTransaction) null);
        try {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            this.mStageCoordinator.prepareEvictChildTasks(i2, windowContainerTransaction);
            int startActivityFromRecents = ActivityTaskManager.getService().startActivityFromRecents(i, resolveStartStage);
            if (startActivityFromRecents == 0 || startActivityFromRecents == 2) {
                this.mSyncQueue.queue(windowContainerTransaction);
            }
        } catch (RemoteException e) {
            Slog.e("SplitScreenController", "Failed to launch task", e);
        }
    }

    public SplitScreenController(ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, Context context, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, ShellExecutor shellExecutor, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, Optional<RecentTasksController> optional, Provider<Optional<StageTaskUnfoldController>> provider) {
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mContext = context;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mMainExecutor = shellExecutor;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransitions = transitions;
        this.mTransactionPool = transactionPool;
        this.mUnfoldControllerProvider = provider;
        this.mLogger = new SplitscreenEventLogger();
        this.mIconProvider = iconProvider;
        this.mRecentTasksOptional = optional;
    }

    public static String exitReasonToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN_EXIT";
            case 1:
                return "APP_DOES_NOT_SUPPORT_MULTIWINDOW";
            case 2:
                return "APP_FINISHED";
            case 3:
                return "DEVICE_FOLDED";
            case 4:
                return "DRAG_DIVIDER";
            case 5:
                return "RETURN_HOME";
            case FalsingManager.VERSION:
                return "ROOT_TASK_VANISHED";
            case 7:
                return "SCREEN_LOCKED";
            case 8:
                return "SCREEN_LOCKED_SHOW_ON_TOP";
            case 9:
                return "CHILD_TASK_ENTER_PIP";
            default:
                return VendorAtomValue$$ExternalSyntheticOutline0.m0m("unknown reason, reason int = ", i);
        }
    }

    public final ActivityManager.RunningTaskInfo getTaskInfo(int i) {
        int i2;
        if (!isSplitScreenVisible()) {
            return null;
        }
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        if (stageCoordinator.mSideStagePosition == i) {
            i2 = stageCoordinator.mSideStage.getTopVisibleChildTaskId();
        } else {
            i2 = stageCoordinator.mMainStage.getTopVisibleChildTaskId();
        }
        return this.mTaskOrganizer.getRunningTaskInfo(i2);
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }
}
