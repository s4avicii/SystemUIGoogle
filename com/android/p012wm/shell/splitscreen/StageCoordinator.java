package com.android.p012wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowManager;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.FrameworkStatsLog;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.common.split.SplitWindowManager;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.splitscreen.SplitScreenTransitions;
import com.android.p012wm.shell.splitscreen.StageTaskListener;
import com.android.p012wm.shell.transition.OneShotRemoteHandler;
import com.android.p012wm.shell.transition.Transitions;
import com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda5;
import com.android.systemui.settings.CurrentUserTracker$$ExternalSyntheticLambda0;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.splitscreen.StageCoordinator */
public final class StageCoordinator implements SplitLayout.SplitLayoutHandler, RootTaskDisplayAreaOrganizer.RootTaskDisplayAreaListener, DisplayController.OnDisplaysChangedListener, Transitions.TransitionHandler {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final int mDisplayId;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DisplayLayout mDisplayLayout;
    public boolean mDividerVisible;
    public boolean mExitSplitScreenOnHide;
    public boolean mIsDividerRemoteAnimating;
    public final ArrayList mListeners;
    public final SplitscreenEventLogger mLogger;
    public final MainStage mMainStage;
    public final StageListenerImpl mMainStageListener;
    public final StageTaskUnfoldController mMainUnfoldController;
    public final C19221 mParentContainerCallbacks;
    public final Optional<RecentTasksController> mRecentTasks;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    public boolean mShouldUpdateRecents;
    public final SideStage mSideStage;
    public final StageListenerImpl mSideStageListener;
    public int mSideStagePosition;
    public final StageTaskUnfoldController mSideUnfoldController;
    public SplitLayout mSplitLayout;
    public final SplitScreenTransitions mSplitTransitions;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public int mTopStageAfterFoldDismiss;
    public final TransactionPool mTransactionPool;

    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$StageListenerImpl */
    public class StageListenerImpl implements StageTaskListener.StageListenerCallbacks {
        public boolean mHasChildren = false;
        public boolean mHasRootTask = false;
        public boolean mVisible = false;

        public StageListenerImpl() {
        }

        public final void dump(PrintWriter printWriter, String str) {
            StringBuilder m = RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(RegionSamplingHelper$$ExternalSyntheticOutline0.m66m(DebugInfo$$ExternalSyntheticOutline0.m2m(str, "mHasRootTask="), this.mHasRootTask, printWriter, str, "mVisible="), this.mVisible, printWriter, str, "mHasChildren=");
            m.append(this.mHasChildren);
            printWriter.println(m.toString());
        }

        public final void onChildTaskStatusChanged(int i, boolean z, boolean z2) {
            int i2;
            boolean z3 = z2;
            StageCoordinator stageCoordinator = StageCoordinator.this;
            Objects.requireNonNull(stageCoordinator);
            if (!z) {
                i2 = -1;
            } else if (this == stageCoordinator.mSideStageListener) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (i2 == 0) {
                SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                int mainStagePosition = stageCoordinator.getMainStagePosition();
                int topChildTaskUid = stageCoordinator.mMainStage.getTopChildTaskUid();
                boolean isLandscape = stageCoordinator.mSplitLayout.isLandscape();
                Objects.requireNonNull(splitscreenEventLogger);
                if (splitscreenEventLogger.mLoggerSessionId != null && splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(mainStagePosition, isLandscape), topChildTaskUid)) {
                    FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, 0, 0, 0, splitscreenEventLogger.mLoggerSessionId.getId());
                }
            } else {
                SplitscreenEventLogger splitscreenEventLogger2 = stageCoordinator.mLogger;
                int i3 = stageCoordinator.mSideStagePosition;
                int topChildTaskUid2 = stageCoordinator.mSideStage.getTopChildTaskUid();
                boolean isLandscape2 = stageCoordinator.mSplitLayout.isLandscape();
                Objects.requireNonNull(splitscreenEventLogger2);
                if (splitscreenEventLogger2.mLoggerSessionId != null && splitscreenEventLogger2.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, isLandscape2), topChildTaskUid2)) {
                    FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, 0, 0, splitscreenEventLogger2.mLastSideStagePosition, splitscreenEventLogger2.mLastSideStageUid, 0, splitscreenEventLogger2.mLoggerSessionId.getId());
                }
            }
            if (z && z3) {
                stageCoordinator.updateRecentTasksSplitPair();
            }
            for (int size = stageCoordinator.mListeners.size() - 1; size >= 0; size--) {
                ((SplitScreen.SplitScreenListener) stageCoordinator.mListeners.get(size)).onTaskStageChanged(i, i2, z3);
            }
        }
    }

    public StageCoordinator(Context context, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Transitions transitions, TransactionPool transactionPool, SplitscreenEventLogger splitscreenEventLogger, IconProvider iconProvider, Optional optional, Provider provider) {
        RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer2 = rootTaskDisplayAreaOrganizer;
        DisplayController displayController2 = displayController;
        Transitions transitions2 = transitions;
        TransactionPool transactionPool2 = transactionPool;
        SurfaceSession surfaceSession = new SurfaceSession();
        StageListenerImpl stageListenerImpl = new StageListenerImpl();
        this.mMainStageListener = stageListenerImpl;
        StageListenerImpl stageListenerImpl2 = new StageListenerImpl();
        this.mSideStageListener = stageListenerImpl2;
        this.mSideStagePosition = 1;
        this.mListeners = new ArrayList();
        this.mTopStageAfterFoldDismiss = -1;
        this.mParentContainerCallbacks = new SplitWindowManager.ParentContainerCallbacks() {
            public final void attachToParentSurface(SurfaceControl.Builder builder) {
                StageCoordinator stageCoordinator = StageCoordinator.this;
                RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = stageCoordinator.mRootTDAOrganizer;
                int i = stageCoordinator.mDisplayId;
                Objects.requireNonNull(rootTaskDisplayAreaOrganizer);
                builder.setParent(rootTaskDisplayAreaOrganizer.mLeashes.get(i));
            }

            public final void onLeashReady(SurfaceControl surfaceControl) {
                StageCoordinator.this.mSyncQueue.runInSync(new StageCoordinator$1$$ExternalSyntheticLambda0(this, 0));
            }
        };
        this.mContext = context;
        this.mDisplayId = 0;
        this.mSyncQueue = syncTransactionQueue;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer2;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mLogger = splitscreenEventLogger;
        this.mRecentTasks = optional;
        StageTaskUnfoldController stageTaskUnfoldController = (StageTaskUnfoldController) ((Optional) provider.get()).orElse((Object) null);
        this.mMainUnfoldController = stageTaskUnfoldController;
        StageTaskUnfoldController stageTaskUnfoldController2 = (StageTaskUnfoldController) ((Optional) provider.get()).orElse((Object) null);
        this.mSideUnfoldController = stageTaskUnfoldController2;
        MainStage mainStage = r1;
        Context context2 = context;
        StageTaskUnfoldController stageTaskUnfoldController3 = stageTaskUnfoldController2;
        ShellTaskOrganizer shellTaskOrganizer2 = shellTaskOrganizer;
        StageTaskUnfoldController stageTaskUnfoldController4 = stageTaskUnfoldController;
        SyncTransactionQueue syncTransactionQueue2 = syncTransactionQueue;
        SurfaceSession surfaceSession2 = surfaceSession;
        IconProvider iconProvider2 = iconProvider;
        MainStage mainStage2 = new MainStage(context2, shellTaskOrganizer2, stageListenerImpl, syncTransactionQueue2, surfaceSession2, iconProvider2, stageTaskUnfoldController4);
        this.mMainStage = mainStage;
        this.mSideStage = new SideStage(context2, shellTaskOrganizer2, stageListenerImpl2, syncTransactionQueue2, surfaceSession2, iconProvider2, stageTaskUnfoldController3);
        this.mDisplayController = displayController2;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool2;
        rootTaskDisplayAreaOrganizer2.registerListener(0, this);
        ((DeviceStateManager) context2.getSystemService(DeviceStateManager.class)).registerCallback(shellTaskOrganizer.getExecutor(), new DeviceStateManager.FoldStateListener(context2, new QSTileHost$$ExternalSyntheticLambda1(this, 5)));
        Transitions transitions3 = transitions;
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool2, transitions3, new AccessPoint$$ExternalSyntheticLambda0(this, 12), this);
        displayController2.addDisplayWindowListener(this);
        this.mDisplayLayout = new DisplayLayout(displayController2.getDisplayLayout(0));
        Objects.requireNonNull(transitions);
        transitions3.mHandlers.add(this);
    }

    public final int getSplitItemPosition(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return -1;
        }
        if (windowContainerToken.equals(this.mMainStage.mRootTaskInfo.getToken())) {
            return getMainStagePosition();
        }
        if (windowContainerToken.equals(this.mSideStage.mRootTaskInfo.getToken())) {
            return this.mSideStagePosition;
        }
        return -1;
    }

    public final void onSnappedToDismiss(boolean z) {
        int i;
        StageTaskListener stageTaskListener;
        if (!z ? this.mSideStagePosition != 0 : this.mSideStagePosition != 1) {
            i = 0;
        } else {
            i = 1;
        }
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            if (i != 0) {
                stageTaskListener = this.mMainStage;
            } else {
                stageTaskListener = this.mSideStage;
            }
            exitSplitScreen(stageTaskListener, 4);
            return;
        }
        int i2 = i ^ 1;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        prepareExitSplitScreen(i2, windowContainerTransaction);
        this.mSplitTransitions.startDismissTransition((IBinder) null, windowContainerTransaction, this, i2, 4);
    }

    public final Bundle resolveStartStage(int i, int i2, Bundle bundle, WindowContainerTransaction windowContainerTransaction) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        int i3 = 0;
        if (i != -1) {
            if (i == 0) {
                if (i2 != -1) {
                    if (i2 == 0) {
                        i3 = 1;
                    } else if (i2 != 1) {
                        i3 = -1;
                    }
                    setSideStagePosition(i3, windowContainerTransaction);
                } else {
                    i2 = getMainStagePosition();
                }
                if (bundle == null) {
                    bundle = new Bundle();
                }
                if (i2 == this.mSideStagePosition) {
                    stageTaskListener = this.mSideStage;
                } else {
                    stageTaskListener = this.mMainStage;
                }
                bundle.putParcelable("android.activity.launchRootTaskToken", stageTaskListener.mRootTaskInfo.token);
                return bundle;
            } else if (i == 1) {
                if (i2 != -1) {
                    setSideStagePosition(i2, windowContainerTransaction);
                } else {
                    i2 = this.mSideStagePosition;
                }
                if (bundle == null) {
                    bundle = new Bundle();
                }
                if (i2 == this.mSideStagePosition) {
                    stageTaskListener2 = this.mSideStage;
                } else {
                    stageTaskListener2 = this.mMainStage;
                }
                bundle.putParcelable("android.activity.launchRootTaskToken", stageTaskListener2.mRootTaskInfo.token);
                return bundle;
            } else {
                throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unknown stage=", i));
            }
        } else if (i2 != -1) {
            MainStage mainStage = this.mMainStage;
            Objects.requireNonNull(mainStage);
            if (!mainStage.mIsActive) {
                return resolveStartStage(1, i2, bundle, windowContainerTransaction);
            }
            if (i2 == this.mSideStagePosition) {
                i3 = 1;
            }
            return resolveStartStage(i3, i2, bundle, windowContainerTransaction);
        } else {
            Slog.w("StageCoordinator", "No stage type nor split position specified to resolve start stage");
            return bundle;
        }
    }

    public final void startWithLegacyTransition(int i, int i2, PendingIntent pendingIntent, Intent intent, Bundle bundle, Bundle bundle2, int i3, float f, RemoteAnimationAdapter remoteAnimationAdapter) {
        Bundle bundle3;
        Bundle bundle4;
        int i4 = i;
        PendingIntent pendingIntent2 = pendingIntent;
        Intent intent2 = intent;
        this.mSplitLayout.init();
        this.mShouldUpdateRecents = false;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        final WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        prepareEvictChildTasks(0, windowContainerTransaction2);
        prepareEvictChildTasks(1, windowContainerTransaction2);
        final RemoteAnimationAdapter remoteAnimationAdapter2 = remoteAnimationAdapter;
        RemoteAnimationAdapter remoteAnimationAdapter3 = new RemoteAnimationAdapter(new IRemoteAnimationRunner.Stub() {
            public final void onAnimationCancelled() {
                StageCoordinator stageCoordinator = StageCoordinator.this;
                stageCoordinator.mIsDividerRemoteAnimating = false;
                stageCoordinator.mShouldUpdateRecents = true;
                stageCoordinator.mSyncQueue.queue(windowContainerTransaction2);
                StageCoordinator.this.mSyncQueue.runInSync(new StageCoordinator$2$$ExternalSyntheticLambda0(this, 0));
                try {
                    remoteAnimationAdapter2.getRunner().onAnimationCancelled();
                } catch (RemoteException e) {
                    Slog.e("StageCoordinator", "Error starting remote animation", e);
                }
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                StageCoordinator.this.mIsDividerRemoteAnimating = true;
                int length = remoteAnimationTargetArr3.length + 1;
                RemoteAnimationTarget[] remoteAnimationTargetArr4 = new RemoteAnimationTarget[length];
                for (int i2 = 0; i2 < remoteAnimationTargetArr3.length; i2++) {
                    remoteAnimationTargetArr4[i2] = remoteAnimationTargetArr3[i2];
                }
                remoteAnimationTargetArr4[length - 1] = StageCoordinator.this.getDividerBarLegacyTarget();
                C19241 r8 = new IRemoteAnimationFinishedCallback.Stub() {
                    public final void onAnimationFinished() throws RemoteException {
                        C19232 r0 = C19232.this;
                        StageCoordinator stageCoordinator = StageCoordinator.this;
                        stageCoordinator.mIsDividerRemoteAnimating = false;
                        stageCoordinator.mShouldUpdateRecents = true;
                        stageCoordinator.mSyncQueue.queue(windowContainerTransaction2);
                        StageCoordinator.this.mSyncQueue.runInSync(new StageCoordinator$2$1$$ExternalSyntheticLambda0(this));
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    }
                };
                try {
                    ActivityTaskManager.getService().setRunningRemoteTransitionDelegate(remoteAnimationAdapter2.getCallingApplication());
                } catch (SecurityException unused) {
                    try {
                        Slog.e("StageCoordinator", "Unable to boost animation thread. This should only happen during unit tests");
                    } catch (RemoteException e) {
                        Slog.e("StageCoordinator", "Error starting remote animation", e);
                        return;
                    }
                }
                remoteAnimationAdapter2.getRunner().onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr4, r8);
            }
        }, remoteAnimationAdapter.getDuration(), remoteAnimationAdapter.getStatusBarTransitionDelay());
        if (bundle == null) {
            bundle3 = ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter3).toBundle();
        } else {
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            fromBundle.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter3));
            bundle3 = fromBundle.toBundle();
        }
        if (bundle2 != null) {
            bundle4 = bundle2;
        } else {
            bundle4 = new Bundle();
        }
        setSideStagePosition(i3, windowContainerTransaction);
        this.mSplitLayout.setDivideRatio(f);
        MainStage mainStage = this.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            MainStage mainStage2 = this.mMainStage;
            Rect mainStageBounds = getMainStageBounds();
            Objects.requireNonNull(mainStage2);
            WindowContainerToken windowContainerToken = mainStage2.mRootTaskInfo.token;
            windowContainerTransaction.setBounds(windowContainerToken, mainStageBounds).reorder(windowContainerToken, true);
        } else {
            this.mMainStage.activate(getMainStageBounds(), windowContainerTransaction, false);
        }
        SideStage sideStage = this.mSideStage;
        Rect sideStageBounds = getSideStageBounds();
        Objects.requireNonNull(sideStage);
        WindowContainerToken windowContainerToken2 = sideStage.mRootTaskInfo.token;
        windowContainerTransaction.setBounds(windowContainerToken2, sideStageBounds).reorder(windowContainerToken2, true);
        bundle3.putParcelable("android.activity.launchRootTaskToken", this.mMainStage.mRootTaskInfo.token);
        bundle4.putParcelable("android.activity.launchRootTaskToken", this.mSideStage.mRootTaskInfo.token);
        if (pendingIntent2 == null || intent2 == null) {
            windowContainerTransaction.startTask(i, bundle3);
            windowContainerTransaction.startTask(i2, bundle4);
        } else {
            windowContainerTransaction.startTask(i, bundle3);
            windowContainerTransaction.sendPendingIntent(pendingIntent2, intent2, bundle4);
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(this));
    }

    public final void addDividerBarToTransition(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl;
        int i;
        SplitLayout splitLayout = this.mSplitLayout;
        Objects.requireNonNull(splitLayout);
        SplitWindowManager splitWindowManager = splitLayout.mSplitWindowManager;
        if (splitWindowManager == null) {
            surfaceControl = null;
        } else {
            surfaceControl = splitWindowManager.mLeash;
        }
        TransitionInfo.Change change = new TransitionInfo.Change((WindowContainerToken) null, surfaceControl);
        Rect dividerBounds = this.mSplitLayout.getDividerBounds();
        change.setStartAbsBounds(dividerBounds);
        change.setEndAbsBounds(dividerBounds);
        if (z) {
            i = 3;
        } else {
            i = 4;
        }
        change.setMode(i);
        change.setFlags(256);
        transitionInfo.addChange(change);
        if (z) {
            transaction.setAlpha(surfaceControl, 1.0f);
            transaction.setLayer(surfaceControl, 30000);
            transaction.setPosition(surfaceControl, (float) dividerBounds.left, (float) dividerBounds.top);
            transaction.show(surfaceControl);
        }
    }

    public final void applyDividerVisibility(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl;
        SplitLayout splitLayout = this.mSplitLayout;
        Objects.requireNonNull(splitLayout);
        SplitWindowManager splitWindowManager = splitLayout.mSplitWindowManager;
        if (splitWindowManager == null) {
            surfaceControl = null;
        } else {
            surfaceControl = splitWindowManager.mLeash;
        }
        if (!this.mIsDividerRemoteAnimating && surfaceControl != null) {
            if (this.mDividerVisible) {
                transaction.show(surfaceControl);
                transaction.setAlpha(surfaceControl, 1.0f);
                transaction.setLayer(surfaceControl, 30000);
                transaction.setPosition(surfaceControl, (float) this.mSplitLayout.getDividerBounds().left, (float) this.mSplitLayout.getDividerBounds().top);
                return;
            }
            transaction.hide(surfaceControl);
        }
    }

    public final void applyExitSplitScreen(StageTaskListener stageTaskListener, WindowContainerTransaction windowContainerTransaction, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda9(this, i));
        boolean z4 = false;
        this.mShouldUpdateRecents = false;
        if (i == 9) {
            z = true;
        } else {
            z = false;
        }
        SideStage sideStage = this.mSideStage;
        if (z || stageTaskListener != sideStage) {
            z2 = false;
        } else {
            z2 = true;
        }
        sideStage.removeAllTasks(windowContainerTransaction, z2);
        MainStage mainStage = this.mMainStage;
        if (z || stageTaskListener != mainStage) {
            z3 = false;
        } else {
            z3 = true;
        }
        mainStage.deactivate(windowContainerTransaction, z3);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda4(this));
        this.mSplitLayout.resetDividerPosition();
        this.mSplitLayout.release();
        this.mTopStageAfterFoldDismiss = -1;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("applyExitSplitScreen, reason = ");
        m.append(SplitScreenController.exitReasonToString(i));
        Slog.i("StageCoordinator", m.toString());
        if (stageTaskListener != null) {
            if (stageTaskListener == this.mMainStage) {
                z4 = true;
            }
            logExitToStage(i, z4);
            return;
        }
        this.mLogger.logExit(i, -1, 0, -1, 0, this.mSplitLayout.isLandscape());
    }

    public final void exitSplitScreen(StageTaskListener stageTaskListener, int i) {
        MainStage mainStage = this.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            applyExitSplitScreen(stageTaskListener, new WindowContainerTransaction(), i);
        }
    }

    public final void finishEnterSplitScreen(SurfaceControl.Transaction transaction) {
        this.mSplitLayout.init();
        boolean z = true;
        setDividerVisibility(true, transaction);
        StageListenerImpl stageListenerImpl = this.mMainStageListener;
        StageListenerImpl stageListenerImpl2 = this.mSideStageListener;
        stageListenerImpl2.mVisible = true;
        stageListenerImpl.mVisible = true;
        stageListenerImpl2.mHasChildren = true;
        stageListenerImpl.mHasChildren = true;
        this.mShouldUpdateRecents = true;
        updateRecentTasksSplitPair();
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        Objects.requireNonNull(splitscreenEventLogger);
        if (splitscreenEventLogger.mLoggerSessionId == null) {
            z = false;
        }
        if (!z) {
            this.mLogger.logEnter(this.mSplitLayout.getDividerPositionAsFraction(), getMainStagePosition(), this.mMainStage.getTopChildTaskUid(), this.mSideStagePosition, this.mSideStage.getTopChildTaskUid(), this.mSplitLayout.isLandscape());
        }
    }

    public final RemoteAnimationTarget getDividerBarLegacyTarget() {
        SurfaceControl surfaceControl;
        Rect dividerBounds = this.mSplitLayout.getDividerBounds();
        SplitLayout splitLayout = this.mSplitLayout;
        Objects.requireNonNull(splitLayout);
        SplitWindowManager splitWindowManager = splitLayout.mSplitWindowManager;
        if (splitWindowManager == null) {
            surfaceControl = null;
        } else {
            surfaceControl = splitWindowManager.mLeash;
        }
        SurfaceControl surfaceControl2 = surfaceControl;
        Point point = r0;
        Point point2 = new Point(0, 0);
        WindowConfiguration windowConfiguration = r0;
        WindowConfiguration windowConfiguration2 = new WindowConfiguration();
        return new RemoteAnimationTarget(-1, -1, surfaceControl2, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, point, dividerBounds, dividerBounds, windowConfiguration, true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
    }

    public final Rect getMainStageBounds() {
        if (this.mSideStagePosition == 0) {
            SplitLayout splitLayout = this.mSplitLayout;
            Objects.requireNonNull(splitLayout);
            return new Rect(splitLayout.mBounds2);
        }
        SplitLayout splitLayout2 = this.mSplitLayout;
        Objects.requireNonNull(splitLayout2);
        return new Rect(splitLayout2.mBounds1);
    }

    public final int getMainStagePosition() {
        int i = this.mSideStagePosition;
        if (i == 0) {
            return 1;
        }
        if (i != 1) {
            return -1;
        }
        return 0;
    }

    public final Rect getSideStageBounds() {
        if (this.mSideStagePosition == 0) {
            SplitLayout splitLayout = this.mSplitLayout;
            Objects.requireNonNull(splitLayout);
            return new Rect(splitLayout.mBounds1);
        }
        SplitLayout splitLayout2 = this.mSplitLayout;
        Objects.requireNonNull(splitLayout2);
        return new Rect(splitLayout2.mBounds2);
    }

    public final StageTaskListener getStageOfTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MainStage mainStage = this.mMainStage;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = mainStage.mRootTaskInfo;
        if (runningTaskInfo2 != null && runningTaskInfo.parentTaskId == runningTaskInfo2.taskId) {
            return mainStage;
        }
        SideStage sideStage = this.mSideStage;
        ActivityManager.RunningTaskInfo runningTaskInfo3 = sideStage.mRootTaskInfo;
        if (runningTaskInfo3 == null || runningTaskInfo.parentTaskId != runningTaskInfo3.taskId) {
            return null;
        }
        return sideStage;
    }

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        int activityType;
        int i;
        IBinder iBinder2 = iBinder;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        if (triggerTask == null) {
            MainStage mainStage = this.mMainStage;
            Objects.requireNonNull(mainStage);
            if (mainStage.mIsActive) {
                return new WindowContainerTransaction();
            }
            return null;
        } else if (triggerTask.displayId != this.mDisplayId) {
            return null;
        } else {
            int type = transitionRequestInfo.getType();
            boolean isOpeningType = Transitions.isOpeningType(type);
            if (triggerTask.getWindowingMode() == 1) {
                z = true;
            } else {
                z = false;
            }
            if (isOpeningType && z) {
                this.mRecentTasks.ifPresent(new CurrentUserTracker$$ExternalSyntheticLambda0(triggerTask, 2));
            }
            MainStage mainStage2 = this.mMainStage;
            Objects.requireNonNull(mainStage2);
            if (mainStage2.mIsActive) {
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    long j = (long) triggerTask.taskId;
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 165317020, 81, "  split is active so using splitTransition to handle request. triggerTask=%d type=%s mainChildren=%d sideChildren=%d", Long.valueOf(j), String.valueOf(WindowManager.transitTypeToString(type)), Long.valueOf((long) this.mMainStage.getChildCount()), Long.valueOf((long) this.mSideStage.getChildCount()));
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                StageTaskListener stageOfTask = getStageOfTask(triggerTask);
                if (stageOfTask != null) {
                    if (Transitions.isClosingType(type) && stageOfTask.getChildCount() == 1) {
                        if (stageOfTask == this.mMainStage) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        int i2 = i ^ 1;
                        prepareExitSplitScreen(i2, windowContainerTransaction);
                        this.mSplitTransitions.startDismissTransition(iBinder, windowContainerTransaction, this, i2, 2);
                    }
                } else if (isOpeningType && z && (activityType = triggerTask.getActivityType()) != 4) {
                    if (activityType == 2 || activityType == 3) {
                        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
                        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
                        Objects.requireNonNull(splitScreenTransitions);
                        if (iBinder2 == null) {
                            iBinder2 = splitScreenTransitions.mTransitions.startTransition(1, windowContainerTransaction, this);
                        }
                        splitScreenTransitions.mPendingRecent = iBinder2;
                        if (remoteTransition != null) {
                            Transitions transitions = splitScreenTransitions.mTransitions;
                            Objects.requireNonNull(transitions);
                            OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition);
                            splitScreenTransitions.mPendingRemoteHandler = oneShotRemoteHandler;
                            oneShotRemoteHandler.mTransition = iBinder2;
                        }
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 822752903, 0, "  splitTransition  deduced Enter recent panel", (Object[]) null);
                        }
                    } else {
                        prepareExitSplitScreen(-1, windowContainerTransaction);
                        this.mSplitTransitions.startDismissTransition(iBinder, windowContainerTransaction, this, -1, 0);
                    }
                }
                return windowContainerTransaction;
            } else if (!isOpeningType || getStageOfTask(triggerTask) == null) {
                return null;
            } else {
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                prepareEnterSplitScreen(windowContainerTransaction2, (ActivityManager.RunningTaskInfo) null, -1);
                this.mSplitTransitions.mPendingEnter = iBinder2;
                return windowContainerTransaction2;
            }
        }
    }

    public final void logExitToStage(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        int i5 = -1;
        if (z) {
            i2 = getMainStagePosition();
        } else {
            i2 = -1;
        }
        if (z) {
            i3 = this.mMainStage.getTopChildTaskUid();
        } else {
            i3 = 0;
        }
        if (!z) {
            i5 = this.mSideStagePosition;
        }
        int i6 = i5;
        if (!z) {
            i4 = this.mSideStage.getTopChildTaskUid();
        } else {
            i4 = 0;
        }
        splitscreenEventLogger.logExit(i, i2, i3, i6, i4, this.mSplitLayout.isLandscape());
    }

    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions$$ExternalSyntheticLambda0 transitions$$ExternalSyntheticLambda0) {
        OneShotRemoteHandler oneShotRemoteHandler;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        Objects.requireNonNull(splitScreenTransitions);
        if (iBinder2 == splitScreenTransitions.mAnimatingTransition && (oneShotRemoteHandler = splitScreenTransitions.mActiveRemoteHandler) != null) {
            oneShotRemoteHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitions$$ExternalSyntheticLambda0);
        }
    }

    public final void onDisplayAdded(int i) {
        if (i == 0) {
            this.mDisplayController.addDisplayChangingController(new StageCoordinator$$ExternalSyntheticLambda0(this));
        }
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo) {
        if (this.mSplitLayout == null) {
            SplitLayout splitLayout = new SplitLayout("StageCoordinatorSplitDivider", this.mContext, displayAreaInfo.configuration, this, this.mParentContainerCallbacks, this.mDisplayImeController, this.mTaskOrganizer, false);
            this.mSplitLayout = splitLayout;
            this.mDisplayInsetsController.addInsetsChangedListener(this.mDisplayId, splitLayout);
            StageTaskUnfoldController stageTaskUnfoldController = this.mMainUnfoldController;
            if (stageTaskUnfoldController != null && this.mSideUnfoldController != null) {
                stageTaskUnfoldController.mUnfoldProgressProvider.addListener(stageTaskUnfoldController.mExecutor, stageTaskUnfoldController);
                stageTaskUnfoldController.mDisplayInsetsController.addInsetsChangedListener(0, stageTaskUnfoldController);
                StageTaskUnfoldController stageTaskUnfoldController2 = this.mSideUnfoldController;
                Objects.requireNonNull(stageTaskUnfoldController2);
                stageTaskUnfoldController2.mUnfoldProgressProvider.addListener(stageTaskUnfoldController2.mExecutor, stageTaskUnfoldController2);
                stageTaskUnfoldController2.mDisplayInsetsController.addInsetsChangedListener(0, stageTaskUnfoldController2);
            }
        }
    }

    public final void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null && splitLayout.updateConfiguration(displayAreaInfo.configuration)) {
            MainStage mainStage = this.mMainStage;
            Objects.requireNonNull(mainStage);
            if (!mainStage.mIsActive) {
                return;
            }
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                updateUnfoldBounds();
            } else {
                onLayoutSizeChanged(this.mSplitLayout);
            }
        }
    }

    public final void onDisplayAreaVanished() {
        throw new IllegalStateException("Well that was unexpected...");
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (i == 0) {
            this.mDisplayLayout.set(this.mDisplayController.getDisplayLayout(i));
        }
    }

    public final void onDoubleTappedDivider() {
        int i = this.mSideStagePosition;
        int i2 = 1;
        if (i != 0) {
            if (i != 1) {
                i2 = -1;
            } else {
                i2 = 0;
            }
        }
        setSideStagePosition(i2, (WindowContainerTransaction) null);
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        int mainStagePosition = getMainStagePosition();
        int topChildTaskUid = this.mMainStage.getTopChildTaskUid();
        int i3 = this.mSideStagePosition;
        int topChildTaskUid2 = this.mSideStage.getTopChildTaskUid();
        boolean isLandscape = this.mSplitLayout.isLandscape();
        Objects.requireNonNull(splitscreenEventLogger);
        if (splitscreenEventLogger.mLoggerSessionId != null) {
            splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(mainStagePosition, isLandscape), topChildTaskUid);
            splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, isLandscape), topChildTaskUid2);
            FrameworkStatsLog.write(388, 5, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
        }
    }

    public final void onLayoutPositionChanging(SplitLayout splitLayout) {
        this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda5(this, splitLayout));
    }

    public final void onLayoutSizeChanged(SplitLayout splitLayout) {
        boolean z;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        updateWindowBounds(splitLayout, windowContainerTransaction);
        updateUnfoldBounds();
        this.mSyncQueue.queue(windowContainerTransaction);
        this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda6(this, splitLayout));
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        float dividerPositionAsFraction = this.mSplitLayout.getDividerPositionAsFraction();
        Objects.requireNonNull(splitscreenEventLogger);
        if (splitscreenEventLogger.mLoggerSessionId != null && dividerPositionAsFraction > 0.0f && dividerPositionAsFraction < 1.0f) {
            boolean z2 = true;
            if (Float.compare(splitscreenEventLogger.mLastSplitRatio, dividerPositionAsFraction) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                z2 = false;
            } else {
                splitscreenEventLogger.mLastSplitRatio = dividerPositionAsFraction;
            }
            if (z2) {
                FrameworkStatsLog.write(388, 4, 0, 0, splitscreenEventLogger.mLastSplitRatio, 0, 0, 0, 0, 0, splitscreenEventLogger.mLoggerSessionId.getId());
            }
        }
    }

    public final void onLayoutSizeChanging(SplitLayout splitLayout) {
        this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda7(this, splitLayout));
    }

    public final void onTransitionMerged(IBinder iBinder) {
        if (iBinder == this.mSplitTransitions.mPendingEnter) {
            finishEnterSplitScreen((SurfaceControl.Transaction) null);
            this.mSplitTransitions.mPendingEnter = null;
        }
    }

    public final void prepareEnterSplitScreen(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        MainStage mainStage = this.mMainStage;
        Objects.requireNonNull(mainStage);
        if (!mainStage.mIsActive) {
            if (runningTaskInfo != null) {
                setSideStagePosition(i, windowContainerTransaction);
                SideStage sideStage = this.mSideStage;
                Objects.requireNonNull(sideStage);
                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0).setBounds(runningTaskInfo.token, (Rect) null);
                windowContainerTransaction.reparent(runningTaskInfo.token, sideStage.mRootTaskInfo.token, true);
            }
            this.mMainStage.activate(getMainStageBounds(), windowContainerTransaction, true);
            SideStage sideStage2 = this.mSideStage;
            Rect sideStageBounds = getSideStageBounds();
            Objects.requireNonNull(sideStage2);
            WindowContainerToken windowContainerToken = sideStage2.mRootTaskInfo.token;
            windowContainerTransaction.setBounds(windowContainerToken, sideStageBounds).reorder(windowContainerToken, true);
        }
    }

    public final void prepareEvictChildTasks(int i, WindowContainerTransaction windowContainerTransaction) {
        if (i == this.mSideStagePosition) {
            this.mSideStage.evictAllChildren(windowContainerTransaction);
        } else {
            this.mMainStage.evictAllChildren(windowContainerTransaction);
        }
    }

    public final void prepareExitSplitScreen(int i, WindowContainerTransaction windowContainerTransaction) {
        boolean z;
        MainStage mainStage = this.mMainStage;
        Objects.requireNonNull(mainStage);
        if (mainStage.mIsActive) {
            SideStage sideStage = this.mSideStage;
            boolean z2 = false;
            if (i == 1) {
                z = true;
            } else {
                z = false;
            }
            sideStage.removeAllTasks(windowContainerTransaction, z);
            MainStage mainStage2 = this.mMainStage;
            if (i == 0) {
                z2 = true;
            }
            mainStage2.deactivate(windowContainerTransaction, z2);
        }
    }

    public final void setDividerVisibility(boolean z, SurfaceControl.Transaction transaction) {
        this.mDividerVisible = z;
        int size = this.mListeners.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            ((SplitScreen.SplitScreenListener) this.mListeners.get(size)).onSplitVisibilityChanged();
        }
        StageTaskUnfoldController stageTaskUnfoldController = this.mMainUnfoldController;
        if (!(stageTaskUnfoldController == null || this.mSideUnfoldController == null)) {
            boolean z2 = this.mDividerVisible;
            stageTaskUnfoldController.mBothStagesVisible = z2;
            if (!z2) {
                stageTaskUnfoldController.resetTransformations();
            }
            StageTaskUnfoldController stageTaskUnfoldController2 = this.mSideUnfoldController;
            boolean z3 = this.mDividerVisible;
            Objects.requireNonNull(stageTaskUnfoldController2);
            stageTaskUnfoldController2.mBothStagesVisible = z3;
            if (!z3) {
                stageTaskUnfoldController2.resetTransformations();
            }
            updateUnfoldBounds();
        }
        if (transaction != null) {
            applyDividerVisibility(transaction);
        } else {
            this.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda1(this));
        }
    }

    public final void setLayoutOffsetTarget(int i, SplitLayout splitLayout) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        int i2 = this.mSideStagePosition;
        if (i2 == 0) {
            stageTaskListener = this.mSideStage;
        } else {
            stageTaskListener = this.mMainStage;
        }
        if (i2 == 0) {
            stageTaskListener2 = this.mMainStage;
        } else {
            stageTaskListener2 = this.mSideStage;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        splitLayout.applyLayoutOffsetTarget(windowContainerTransaction, i, stageTaskListener.mRootTaskInfo, stageTaskListener2.mRootTaskInfo);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void setSideStagePosition(int i, WindowContainerTransaction windowContainerTransaction) {
        if (this.mSideStagePosition != i) {
            this.mSideStagePosition = i;
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                SplitScreen.SplitScreenListener splitScreenListener = (SplitScreen.SplitScreenListener) this.mListeners.get(size);
                splitScreenListener.onStagePositionChanged(0, getMainStagePosition());
                splitScreenListener.onStagePositionChanged(1, this.mSideStagePosition);
            }
            if (!this.mSideStageListener.mVisible) {
                return;
            }
            if (windowContainerTransaction == null) {
                onLayoutSizeChanged(this.mSplitLayout);
                return;
            }
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction);
            updateUnfoldBounds();
        }
    }

    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        String str;
        String str2;
        boolean z2;
        SplitScreenTransitions.DismissTransition dismissTransition;
        StageTaskListener stageOfTask;
        IBinder iBinder2 = iBinder;
        TransitionInfo transitionInfo2 = transitionInfo;
        SurfaceControl.Transaction transaction3 = transaction;
        SurfaceControl.Transaction transaction4 = transaction2;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        IBinder iBinder3 = splitScreenTransitions.mPendingEnter;
        boolean z3 = true;
        if (iBinder2 == iBinder3 || iBinder2 == splitScreenTransitions.mPendingRecent || ((dismissTransition = splitScreenTransitions.mPendingDismiss) != null && dismissTransition.mTransition == iBinder2)) {
            TransitionInfo.Change change = null;
            if (iBinder3 == iBinder2) {
                TransitionInfo.Change change2 = null;
                for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                    TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                    ActivityManager.RunningTaskInfo taskInfo = change3.getTaskInfo();
                    if (taskInfo != null && taskInfo.hasParentTask()) {
                        if (getStageOfTask(taskInfo) == this.mMainStage) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        if (!z2) {
                            change = change3;
                        } else if (z2) {
                            change2 = change3;
                        }
                    }
                }
                if (transitionInfo.getType() == 17) {
                    if (change == null && change2 == null) {
                        throw new IllegalStateException("Launched a task in split, but didn't receive any task in transition.");
                    }
                } else if (change == null || change2 == null) {
                    throw new IllegalStateException("Launched 2 tasks in split, but didn't receive 2 tasks in transition. Possibly one of them failed to launch");
                }
                if (change != null && !this.mMainStage.containsTask(change.getTaskInfo().taskId)) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected onTaskAppeared on ");
                    m.append(this.mMainStage);
                    m.append(" to have been called with ");
                    m.append(change.getTaskInfo().taskId);
                    m.append(" before startAnimation().");
                    Log.w("StageCoordinator", m.toString());
                }
                if (change2 != null && !this.mSideStage.containsTask(change2.getTaskInfo().taskId)) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected onTaskAppeared on ");
                    m2.append(this.mSideStage);
                    m2.append(" to have been called with ");
                    m2.append(change2.getTaskInfo().taskId);
                    m2.append(" before startAnimation().");
                    Log.w("StageCoordinator", m2.toString());
                }
                finishEnterSplitScreen(transaction3);
                addDividerBarToTransition(transitionInfo2, transaction3, true);
            } else if (splitScreenTransitions.mPendingRecent == iBinder2) {
                setDividerVisibility(false, transaction3);
            } else {
                SplitScreenTransitions.DismissTransition dismissTransition2 = splitScreenTransitions.mPendingDismiss;
                if (dismissTransition2 != null && dismissTransition2.mTransition == iBinder2) {
                    if (this.mMainStage.getChildCount() != 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i2 = 0; i2 < this.mMainStage.getChildCount(); i2++) {
                            if (i2 != 0) {
                                str2 = ", ";
                            } else {
                                str2 = "";
                            }
                            sb.append(str2);
                            sb.append(this.mMainStage.mChildrenTaskInfo.keyAt(i2));
                        }
                        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected onTaskVanished on ");
                        m3.append(this.mMainStage);
                        m3.append(" to have been called with [");
                        m3.append(sb.toString());
                        m3.append("] before startAnimation().");
                        Log.w("StageCoordinator", m3.toString());
                    }
                    if (this.mSideStage.getChildCount() != 0) {
                        StringBuilder sb2 = new StringBuilder();
                        for (int i3 = 0; i3 < this.mSideStage.getChildCount(); i3++) {
                            if (i3 != 0) {
                                str = ", ";
                            } else {
                                str = "";
                            }
                            sb2.append(str);
                            sb2.append(this.mSideStage.mChildrenTaskInfo.keyAt(i3));
                        }
                        StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expected onTaskVanished on ");
                        m4.append(this.mSideStage);
                        m4.append(" to have been called with [");
                        m4.append(sb2.toString());
                        m4.append("] before startAnimation().");
                        Log.w("StageCoordinator", m4.toString());
                    }
                    this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda10(this, dismissTransition2, transitionInfo2));
                    this.mShouldUpdateRecents = false;
                    StageListenerImpl stageListenerImpl = this.mMainStageListener;
                    StageListenerImpl stageListenerImpl2 = this.mSideStageListener;
                    stageListenerImpl2.mVisible = false;
                    stageListenerImpl.mVisible = false;
                    stageListenerImpl2.mHasChildren = false;
                    stageListenerImpl.mHasChildren = false;
                    transaction3.setWindowCrop(this.mMainStage.mRootLeash, (Rect) null);
                    transaction3.setWindowCrop(this.mSideStage.mRootLeash, (Rect) null);
                    int i4 = dismissTransition2.mDismissTop;
                    if (i4 == -1) {
                        this.mLogger.logExit(dismissTransition2.mReason, -1, 0, -1, 0, this.mSplitLayout.isLandscape());
                        z3 = false;
                        setDividerVisibility(false, transaction3);
                        this.mSplitLayout.release();
                        this.mSplitTransitions.mPendingDismiss = null;
                    } else {
                        int i5 = dismissTransition2.mReason;
                        if (i4 == 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        logExitToStage(i5, z);
                        addDividerBarToTransition(transitionInfo2, transaction3, false);
                        setDividerVisibility(false, transaction4);
                        transaction4.hide(this.mMainStage.mDimLayer);
                        transaction4.hide(this.mSideStage.mDimLayer);
                    }
                }
                z3 = true;
            }
            if (!z3) {
                return false;
            }
            this.mSplitTransitions.playAnimation(iBinder, transitionInfo, transaction, transaction2, (Transitions$$ExternalSyntheticLambda1) transitionFinishCallback, this.mMainStage.mRootTaskInfo.token, this.mSideStage.mRootTaskInfo.token);
            return true;
        }
        MainStage mainStage = this.mMainStage;
        Objects.requireNonNull(mainStage);
        if (!mainStage.mIsActive) {
            return false;
        }
        for (int i6 = 0; i6 < transitionInfo.getChanges().size(); i6++) {
            TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(i6);
            ActivityManager.RunningTaskInfo taskInfo2 = change4.getTaskInfo();
            if (!(taskInfo2 == null || !taskInfo2.hasParentTask() || (stageOfTask = getStageOfTask(taskInfo2)) == null)) {
                if (Transitions.isOpeningType(change4.getMode())) {
                    if (!stageOfTask.containsTask(taskInfo2.taskId)) {
                        Log.w("StageCoordinator", "Expected onTaskAppeared on " + stageOfTask + " to have been called with " + taskInfo2.taskId + " before startAnimation().");
                    }
                } else if (Transitions.isClosingType(change4.getMode())) {
                    if (stageOfTask.containsTask(taskInfo2.taskId)) {
                        Log.w("StageCoordinator", "Expected onTaskVanished on " + stageOfTask + " to have been called with " + taskInfo2.taskId + " before startAnimation().");
                    }
                } else if (transitionInfo.getType() == 6 && change4.getStartRotation() != change4.getEndRotation()) {
                    setDividerVisibility(true, transaction4);
                }
            }
        }
        if (this.mMainStage.getChildCount() != 0 && this.mSideStage.getChildCount() != 0) {
            return false;
        }
        throw new IllegalStateException("Somehow removed the last task in a stage outside of a proper transition");
    }

    public final void updateRecentTasksSplitPair() {
        if (this.mShouldUpdateRecents) {
            this.mRecentTasks.ifPresent(new OverviewProxyService$$ExternalSyntheticLambda5(this, 2));
        }
    }

    public final void updateSurfaceBounds(SplitLayout splitLayout, SurfaceControl.Transaction transaction) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        int i = this.mSideStagePosition;
        if (i == 0) {
            stageTaskListener = this.mSideStage;
        } else {
            stageTaskListener = this.mMainStage;
        }
        if (i == 0) {
            stageTaskListener2 = this.mMainStage;
        } else {
            stageTaskListener2 = this.mSideStage;
        }
        if (splitLayout == null) {
            splitLayout = this.mSplitLayout;
        }
        splitLayout.applySurfaceChanges(transaction, stageTaskListener.mRootLeash, stageTaskListener2.mRootLeash, stageTaskListener.mDimLayer, stageTaskListener2.mDimLayer);
    }

    public final void updateUnfoldBounds() {
        StageTaskUnfoldController stageTaskUnfoldController = this.mMainUnfoldController;
        if (stageTaskUnfoldController != null && this.mSideUnfoldController != null) {
            stageTaskUnfoldController.onLayoutChanged(getMainStageBounds(), getMainStagePosition(), this.mSplitLayout.isLandscape());
            this.mSideUnfoldController.onLayoutChanged(getSideStageBounds(), this.mSideStagePosition, this.mSplitLayout.isLandscape());
        }
    }

    public final void updateWindowBounds(SplitLayout splitLayout, WindowContainerTransaction windowContainerTransaction) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        int i = this.mSideStagePosition;
        if (i == 0) {
            stageTaskListener = this.mSideStage;
        } else {
            stageTaskListener = this.mMainStage;
        }
        if (i == 0) {
            stageTaskListener2 = this.mMainStage;
        } else {
            stageTaskListener2 = this.mSideStage;
        }
        splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener.mRootTaskInfo, stageTaskListener2.mRootTaskInfo);
    }

    @VisibleForTesting
    public StageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, ShellTaskOrganizer shellTaskOrganizer, MainStage mainStage, SideStage sideStage, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, SplitLayout splitLayout, Transitions transitions, TransactionPool transactionPool, SplitscreenEventLogger splitscreenEventLogger, Optional<RecentTasksController> optional, Provider<Optional<StageTaskUnfoldController>> provider) {
        Transitions transitions2 = transitions;
        TransactionPool transactionPool2 = transactionPool;
        new SurfaceSession();
        this.mMainStageListener = new StageListenerImpl();
        this.mSideStageListener = new StageListenerImpl();
        this.mSideStagePosition = 1;
        this.mListeners = new ArrayList();
        this.mTopStageAfterFoldDismiss = -1;
        this.mParentContainerCallbacks = new SplitWindowManager.ParentContainerCallbacks() {
            public final void attachToParentSurface(SurfaceControl.Builder builder) {
                StageCoordinator stageCoordinator = StageCoordinator.this;
                RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = stageCoordinator.mRootTDAOrganizer;
                int i = stageCoordinator.mDisplayId;
                Objects.requireNonNull(rootTaskDisplayAreaOrganizer);
                builder.setParent(rootTaskDisplayAreaOrganizer.mLeashes.get(i));
            }

            public final void onLeashReady(SurfaceControl surfaceControl) {
                StageCoordinator.this.mSyncQueue.runInSync(new StageCoordinator$1$$ExternalSyntheticLambda0(this, 0));
            }
        };
        this.mContext = context;
        this.mDisplayId = i;
        this.mSyncQueue = syncTransactionQueue;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mMainStage = mainStage;
        this.mSideStage = sideStage;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool2;
        rootTaskDisplayAreaOrganizer.registerListener(i, this);
        this.mSplitLayout = splitLayout;
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool2, transitions2, new BubbleStackView$$ExternalSyntheticLambda15(this, 9), this);
        this.mMainUnfoldController = (StageTaskUnfoldController) provider.get().orElse((Object) null);
        this.mSideUnfoldController = (StageTaskUnfoldController) provider.get().orElse((Object) null);
        this.mLogger = splitscreenEventLogger;
        this.mRecentTasks = optional;
        displayController.addDisplayWindowListener(this);
        this.mDisplayLayout = new DisplayLayout();
        Objects.requireNonNull(transitions);
        transitions2.mHandlers.add(this);
    }

    @VisibleForTesting
    public SplitScreenTransitions getSplitTransitions() {
        return this.mSplitTransitions;
    }
}
