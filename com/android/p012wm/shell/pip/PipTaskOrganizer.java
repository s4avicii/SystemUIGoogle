package com.android.p012wm.shell.pip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.Rational;
import android.util.RotationUtils;
import android.util.Size;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.apppairs.AppPair$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ScreenshotUtils;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer */
public final class PipTaskOrganizer implements ShellTaskOrganizer.TaskListener, DisplayController.OnDisplaysChangedListener, ShellTaskOrganizer.FocusListener {
    public final Context mContext;
    public final int mCrossFadeAnimationDuration;
    public int mCurrentRotation;
    public SurfaceControl.Transaction mDeferredAnimEndTransaction;
    public ActivityManager.RunningTaskInfo mDeferredTaskInfo;
    public final int mEnterAnimationDuration;
    public final int mExitAnimationDuration;
    public boolean mHasFadeOut;
    public long mLastOneShotAlphaAnimationTime;
    public SurfaceControl mLeash;
    public final Optional<LegacySplitScreenController> mLegacySplitScreenOptional;
    public final ShellExecutor mMainExecutor;
    public int mNextRotation;
    public IntConsumer mOnDisplayIdChangeCallback;
    public int mOneShotAnimationType = 0;
    public PictureInPictureParams mPictureInPictureParams;
    public final C18891 mPipAnimationCallback = new PipAnimationController.PipAnimationCallback() {
        public final void onPipAnimationCancel(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = pipTransitionAnimator.mContentOverlay) != null) {
                PipTaskOrganizer.this.fadeOutAndRemoveOverlay(surfaceControl, new QSTileImpl$$ExternalSyntheticLambda0(pipTransitionAnimator, 8), true);
            }
            PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
            Objects.requireNonNull(pipTaskOrganizer);
            pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionCancelled(transitionDirection);
        }

        public final void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            boolean z;
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            int animationType = pipTransitionAnimator.getAnimationType();
            Rect rect = pipTransitionAnimator.mDestinationBounds;
            boolean z2 = true;
            if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = pipTransitionAnimator.mContentOverlay) != null) {
                PipTaskOrganizer.this.fadeOutAndRemoveOverlay(surfaceControl, new QSTileImpl$$ExternalSyntheticLambda1(pipTransitionAnimator, 5), true);
            }
            if (PipTaskOrganizer.this.mWaitForFixedRotation && animationType == 0 && transitionDirection == 2) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.scheduleFinishEnterPip(PipTaskOrganizer.this.mToken, rect);
                PipTaskOrganizer.this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                pipTaskOrganizer.mSurfaceTransactionHelper.round(transaction, pipTaskOrganizer.mLeash, pipTaskOrganizer.isInPip());
                PipTaskOrganizer.this.mDeferredAnimEndTransaction = transaction;
                return;
            }
            if (!PipAnimationController.isOutPipDirection(transitionDirection)) {
                if (transitionDirection == 5) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    z2 = false;
                }
            }
            PipTransitionState pipTransitionState = PipTaskOrganizer.this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState);
            if (pipTransitionState.mState != 5 || z2) {
                PipTaskOrganizer.this.finishResize(transaction, rect, transitionDirection, animationType);
                PipTaskOrganizer.this.sendOnPipTransitionFinished(transitionDirection);
            }
        }

        public final void onPipAnimationStart(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
            Objects.requireNonNull(pipTaskOrganizer);
            if (transitionDirection == 2) {
                PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                Objects.requireNonNull(pipTransitionState);
                pipTransitionState.mState = 3;
            }
            pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionStarted(transitionDirection);
        }
    };
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipMenuController mPipMenuController;
    public final C18902 mPipTransactionHandler = new PipAnimationController.PipTransactionHandler() {
    };
    public final PipTransitionController mPipTransitionController;
    public PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLoggerLogger;
    public final Optional<SplitScreenController> mSplitScreenOptional;
    public DialogFragment$$ExternalSyntheticOutline0 mSurfaceControlTransactionFactory;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;
    public SurfaceControl mSwipePipToHomeOverlay;
    public final SyncTransactionQueue mSyncTransactionQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public WindowContainerToken mToken;
    public boolean mWaitForFixedRotation;

    public PipTaskOrganizer(Context context, SyncTransactionQueue syncTransactionQueue, PipTransitionState pipTransitionState, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, PipMenuController pipMenuController, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, PipTransitionController pipTransitionController, Optional<LegacySplitScreenController> optional, Optional<SplitScreenController> optional2, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        PipTransitionController pipTransitionController2 = pipTransitionController;
        ShellTaskOrganizer shellTaskOrganizer2 = shellTaskOrganizer;
        ShellExecutor shellExecutor2 = shellExecutor;
        this.mContext = context;
        this.mSyncTransactionQueue = syncTransactionQueue;
        this.mPipTransitionState = pipTransitionState;
        this.mPipBoundsState = pipBoundsState;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipMenuController = pipMenuController;
        this.mPipTransitionController = pipTransitionController2;
        this.mEnterAnimationDuration = context.getResources().getInteger(C1777R.integer.config_pipEnterAnimationDuration);
        this.mExitAnimationDuration = context.getResources().getInteger(C1777R.integer.config_pipExitAnimationDuration);
        this.mCrossFadeAnimationDuration = context.getResources().getInteger(C1777R.integer.config_pipCrossfadeAnimationDuration);
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        this.mPipAnimationController = pipAnimationController;
        this.mPipUiEventLoggerLogger = pipUiEventLogger;
        this.mSurfaceControlTransactionFactory = DialogFragment$$ExternalSyntheticOutline0.INSTANCE;
        this.mLegacySplitScreenOptional = optional;
        this.mSplitScreenOptional = optional2;
        this.mTaskOrganizer = shellTaskOrganizer2;
        this.mMainExecutor = shellExecutor2;
        shellExecutor2.execute(new WMShell$7$$ExternalSyntheticLambda0(this, 8));
        Objects.requireNonNull(shellTaskOrganizer);
        synchronized (shellTaskOrganizer2.mLock) {
            shellTaskOrganizer2.mFocusListeners.add(this);
            ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer2.mLastFocusedTaskInfo;
            if (runningTaskInfo != null) {
                onFocusTaskChanged(runningTaskInfo);
            }
        }
        Objects.requireNonNull(pipTransitionController);
        pipTransitionController2.mPipOrganizer = this;
        displayController.addDisplayWindowListener(this);
    }

    public final PipAnimationController.PipTransitionAnimator<?> animateResizePip(Rect rect, Rect rect2, Rect rect3, int i, int i2, float f) {
        int i3;
        Rect rect4;
        Rect rect5;
        Rect rect6 = rect2;
        Rect rect7 = rect3;
        int i4 = i;
        if (this.mToken == null || this.mLeash == null) {
            Log.w("PipTaskOrganizer", "Abort animation, invalid leash");
            return null;
        }
        boolean z = false;
        if (this.mWaitForFixedRotation) {
            i3 = RotationUtils.deltaRotation(this.mCurrentRotation, this.mNextRotation);
        } else {
            i3 = 0;
        }
        if (i3 != 0) {
            if (i4 == 2) {
                PipBoundsState pipBoundsState = this.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState);
                pipBoundsState.mDisplayLayout.rotateTo(this.mContext.getResources(), this.mNextRotation);
                Rect displayBounds = this.mPipBoundsState.getDisplayBounds();
                rect6.set(this.mPipBoundsAlgorithm.getEntryDestinationBounds());
                RotationUtils.rotateBounds(rect6, displayBounds, this.mNextRotation, this.mCurrentRotation);
                if (!(rect7 == null || (rect5 = this.mTaskInfo.displayCutoutInsets) == null || i3 != 3)) {
                    rect7.offset(rect5.left, rect5.top);
                }
            } else if (i4 == 3) {
                Rect rect8 = new Rect(rect6);
                RotationUtils.rotateBounds(rect8, this.mPipBoundsState.getDisplayBounds(), i3);
                rect7 = PipBoundsAlgorithm.getValidSourceHintRect(this.mPictureInPictureParams, rect8);
            }
        }
        Rect rect9 = rect7;
        if (i4 == 6) {
            rect4 = this.mPipBoundsState.getBounds();
        } else {
            rect4 = rect;
        }
        PipAnimationController pipAnimationController = this.mPipAnimationController;
        Objects.requireNonNull(pipAnimationController);
        if (pipAnimationController.mCurrentAnimator != null) {
            PipAnimationController pipAnimationController2 = this.mPipAnimationController;
            Objects.requireNonNull(pipAnimationController2);
            if (pipAnimationController2.mCurrentAnimator.isRunning()) {
                z = true;
            }
        }
        boolean z2 = z;
        PipAnimationController.PipTransitionAnimator<?> animator = this.mPipAnimationController.getAnimator(this.mTaskInfo, this.mLeash, rect4, rect, rect2, rect9, i, f, i3);
        PipAnimationController.PipTransitionAnimator<?> transitionDirection = animator.setTransitionDirection(i4);
        C18902 r3 = this.mPipTransactionHandler;
        Objects.requireNonNull(transitionDirection);
        transitionDirection.mPipTransactionHandler = r3;
        transitionDirection.setDuration((long) i2);
        if (!z2) {
            animator.setPipAnimationCallback(this.mPipAnimationCallback);
        }
        if (PipAnimationController.isInPipDirection(i)) {
            if (rect9 == null) {
                animator.setUseContentOverlay(this.mContext);
            }
            if (i3 != 0) {
                animator.setDestinationBounds(this.mPipBoundsAlgorithm.getEntryDestinationBounds());
            }
        }
        animator.start();
        return animator;
    }

    public final void applyFinishBoundsResize(WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        if (i == 4) {
            this.mSplitScreenOptional.ifPresent(new PipTaskOrganizer$$ExternalSyntheticLambda8(this, z, windowContainerTransaction));
        } else {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    public final void onExitPipFinished(TaskInfo taskInfo) {
        IntConsumer intConsumer;
        this.mWaitForFixedRotation = false;
        this.mDeferredAnimEndTransaction = null;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mInSwipePipToHomeTransition = false;
        this.mPictureInPictureParams = null;
        PipTransitionState pipTransitionState2 = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState2);
        pipTransitionState2.mState = 0;
        this.mPipBoundsState.setBounds(new Rect());
        this.mPipUiEventLoggerLogger.setTaskInfo((ActivityManager.RunningTaskInfo) null);
        this.mPipMenuController.detach();
        if (taskInfo.displayId != 0 && (intConsumer = this.mOnDisplayIdChangeCallback) != null) {
            intConsumer.accept(0);
        }
    }

    public final void scheduleAnimateResizePip(Rect rect, int i, int i2, QSTileHost$$ExternalSyntheticLambda1 qSTileHost$$ExternalSyntheticLambda1) {
        if (this.mWaitForFixedRotation) {
            Log.d("PipTaskOrganizer", "skip scheduleAnimateResizePip, entering pip deferred");
            return;
        }
        scheduleAnimateResizePip(this.mPipBoundsState.getBounds(), rect, 0.0f, (Rect) null, i2, i, qSTileHost$$ExternalSyntheticLambda1);
    }

    @VisibleForTesting
    public void sendOnPipTransitionFinished(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (i == 2) {
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState);
            pipTransitionState.mState = 4;
        }
        this.mPipTransitionController.sendOnPipTransitionFinished(i);
        if (i == 2 && (runningTaskInfo = this.mDeferredTaskInfo) != null) {
            onTaskInfoChanged(runningTaskInfo);
            this.mDeferredTaskInfo = null;
        }
    }

    public final boolean supportCompatUI() {
        return false;
    }

    public final void applyEnterPipSyncTransaction(Rect rect, Runnable runnable, SurfaceControl.Transaction transaction) {
        this.mPipMenuController.attach(this.mLeash);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
        windowContainerTransaction.setBounds(this.mToken, rect);
        if (transaction != null) {
            windowContainerTransaction.setBoundsChangeTransaction(this.mToken, transaction);
        }
        this.mSyncTransactionQueue.queue(windowContainerTransaction);
        this.mSyncTransactionQueue.runInSync(new AppPair$$ExternalSyntheticLambda1(runnable, 1));
    }

    public final SurfaceControl.Transaction createFinishResizeSurfaceTransaction(Rect rect) {
        Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.crop(transaction, this.mLeash, rect);
        pipSurfaceTransactionHelper.resetScale(transaction, this.mLeash, rect);
        pipSurfaceTransactionHelper.round(transaction, this.mLeash, this.mPipTransitionState.isInPip());
        return transaction;
    }

    public final void dump(PrintWriter printWriter, String str) {
        IBinder iBinder;
        String m = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, "  ");
        printWriter.println(str + "PipTaskOrganizer");
        printWriter.println(m + "mTaskInfo=" + this.mTaskInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(m);
        sb.append("mToken=");
        sb.append(this.mToken);
        sb.append(" binder=");
        WindowContainerToken windowContainerToken = this.mToken;
        if (windowContainerToken != null) {
            iBinder = windowContainerToken.asBinder();
        } else {
            iBinder = null;
        }
        sb.append(iBinder);
        printWriter.println(sb.toString());
        printWriter.println(m + "mLeash=" + this.mLeash);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m);
        sb2.append("mState=");
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        sb2.append(pipTransitionState.mState);
        printWriter.println(sb2.toString());
        printWriter.println(m + "mOneShotAnimationType=" + this.mOneShotAnimationType);
        printWriter.println(m + "mPictureInPictureParams=" + this.mPictureInPictureParams);
    }

    @VisibleForTesting
    public void enterPipWithAlphaAnimation(Rect rect, long j) {
        Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setAlpha(this.mLeash, 0.0f);
        transaction.apply();
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mState = 2;
        applyEnterPipSyncTransaction(rect, new PipTaskOrganizer$$ExternalSyntheticLambda6(this, rect, j), (SurfaceControl.Transaction) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0131  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void exitPip(int r17, boolean r18) {
        /*
            r16 = this;
            r0 = r16
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            boolean r1 = r1.isInPip()
            if (r1 == 0) goto L_0x0143
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mState
            r2 = 5
            if (r1 == r2) goto L_0x0143
            android.window.WindowContainerToken r1 = r0.mToken
            if (r1 != 0) goto L_0x001a
            goto L_0x0143
        L_0x001a:
            android.window.WindowContainerTransaction r1 = new android.window.WindowContainerTransaction
            r1.<init>()
            android.app.PictureInPictureParams r3 = r0.mPictureInPictureParams
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x002d
            boolean r3 = r3.isLaunchIntoPip()
            if (r3 == 0) goto L_0x002d
            r3 = r5
            goto L_0x002e
        L_0x002d:
            r3 = r4
        L_0x002e:
            r6 = 0
            if (r3 == 0) goto L_0x0041
            android.app.ActivityManager$RunningTaskInfo r2 = r0.mTaskInfo
            int r2 = r2.launchIntoPipHostTaskId
            r1.startTask(r2, r6)
            com.android.wm.shell.ShellTaskOrganizer r2 = r0.mTaskOrganizer
            r2.applyTransaction(r1)
            r16.removePip()
            return
        L_0x0041:
            boolean r3 = com.android.p012wm.shell.transition.Transitions.ENABLE_SHELL_TRANSITIONS
            if (r3 == 0) goto L_0x006e
            if (r18 == 0) goto L_0x006e
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r7 = r0.mSplitScreenOptional
            boolean r7 = r7.isPresent()
            if (r7 == 0) goto L_0x006e
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r2 = r0.mSplitScreenOptional
            java.lang.Object r2 = r2.get()
            com.android.wm.shell.splitscreen.SplitScreenController r2 = (com.android.p012wm.shell.splitscreen.SplitScreenController) r2
            android.app.ActivityManager$RunningTaskInfo r3 = r0.mTaskInfo
            boolean r4 = r16.isPipTopLeft()
            r4 = r4 ^ r5
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.splitscreen.StageCoordinator r2 = r2.mStageCoordinator
            r2.prepareEnterSplitScreen(r1, r3, r4)
            com.android.wm.shell.pip.PipTransitionController r0 = r0.mPipTransitionController
            r2 = 14
            r0.startExitTransition(r2, r1, r6)
            return
        L_0x006e:
            com.android.wm.shell.pip.PipBoundsState r7 = r0.mPipBoundsState
            android.graphics.Rect r7 = r7.getDisplayBounds()
            if (r18 == 0) goto L_0x009f
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r8 = r0.mSplitScreenOptional
            boolean r8 = r8.isPresent()
            if (r8 == 0) goto L_0x009f
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>()
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r10 = r0.mSplitScreenOptional
            java.lang.Object r10 = r10.get()
            com.android.wm.shell.splitscreen.SplitScreenController r10 = (com.android.p012wm.shell.splitscreen.SplitScreenController) r10
            r10.getStageBounds(r8, r9)
            boolean r10 = r16.isPipTopLeft()
            if (r10 == 0) goto L_0x009a
            goto L_0x009b
        L_0x009a:
            r8 = r9
        L_0x009b:
            r7.set(r8)
            goto L_0x00cb
        L_0x009f:
            java.util.Optional<com.android.wm.shell.legacysplitscreen.LegacySplitScreenController> r8 = r0.mLegacySplitScreenOptional
            boolean r8 = r8.isPresent()
            if (r8 != 0) goto L_0x00a8
            goto L_0x00b6
        L_0x00a8:
            java.util.Optional<com.android.wm.shell.legacysplitscreen.LegacySplitScreenController> r8 = r0.mLegacySplitScreenOptional
            java.lang.Object r8 = r8.get()
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r8 = (com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController) r8
            boolean r9 = r8.isDividerVisible()
            if (r9 != 0) goto L_0x00b8
        L_0x00b6:
            r8 = r4
            goto L_0x00cc
        L_0x00b8:
            com.android.wm.shell.legacysplitscreen.DividerView r8 = r8.mView
            java.util.Objects.requireNonNull(r8)
            android.graphics.Rect r9 = r8.mOtherTaskRect
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r10 = r8.mSplitLayout
            android.graphics.Rect r10 = r10.mSecondary
            r9.set(r10)
            android.graphics.Rect r8 = r8.mOtherTaskRect
            r7.set(r8)
        L_0x00cb:
            r8 = r5
        L_0x00cc:
            r9 = 3
            r14 = 4
            if (r8 == 0) goto L_0x00d2
            r15 = r14
            goto L_0x00d3
        L_0x00d2:
            r15 = r9
        L_0x00d3:
            if (r3 == 0) goto L_0x00e2
            if (r15 != r9) goto L_0x00e2
            android.window.WindowContainerToken r5 = r0.mToken
            r1.setWindowingMode(r5, r4)
            android.window.WindowContainerToken r4 = r0.mToken
            r1.setBounds(r4, r6)
            goto L_0x0120
        L_0x00e2:
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0 r4 = r0.mSurfaceControlTransactionFactory
            java.util.Objects.requireNonNull(r4)
            android.view.SurfaceControl$Transaction r4 = new android.view.SurfaceControl$Transaction
            r4.<init>()
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r8 = r0.mSurfaceTransactionHelper
            android.view.SurfaceControl r10 = r0.mLeash
            com.android.wm.shell.pip.PipBoundsState r6 = r0.mPipBoundsState
            android.graphics.Rect r12 = r6.getBounds()
            java.util.Objects.requireNonNull(r8)
            r13 = 0
            r9 = r4
            r11 = r7
            r8.scale(r9, r10, r11, r12, r13)
            android.view.SurfaceControl r6 = r0.mLeash
            int r8 = r7.width()
            int r9 = r7.height()
            r4.setWindowCrop(r6, r8, r9)
            android.window.WindowContainerToken r6 = r0.mToken
            if (r15 != r14) goto L_0x0113
            if (r18 != 0) goto L_0x0113
            r5 = r14
        L_0x0113:
            r1.setActivityWindowingMode(r6, r5)
            android.window.WindowContainerToken r5 = r0.mToken
            r1.setBounds(r5, r7)
            android.window.WindowContainerToken r5 = r0.mToken
            r1.setBoundsChangeTransaction(r5, r4)
        L_0x0120:
            com.android.wm.shell.pip.PipTransitionState r4 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r4)
            r4.mState = r2
            if (r3 == 0) goto L_0x0131
            com.android.wm.shell.pip.PipTransitionController r0 = r0.mPipTransitionController
            r2 = 13
            r0.startExitTransition(r2, r1, r7)
            return
        L_0x0131:
            com.android.wm.shell.common.SyncTransactionQueue r2 = r0.mSyncTransactionQueue
            r2.queue(r1)
            com.android.wm.shell.common.SyncTransactionQueue r1 = r0.mSyncTransactionQueue
            com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda1 r2 = new com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda1
            r3 = r17
            r2.<init>(r0, r7, r15, r3)
            r1.runInSync(r2)
            return
        L_0x0143:
            java.lang.String r1 = "Not allowed to exitPip in current state mState="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            com.android.wm.shell.pip.PipTransitionState r2 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mState
            r1.append(r2)
            java.lang.String r2 = " mToken="
            r1.append(r2)
            android.window.WindowContainerToken r0 = r0.mToken
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "PipTaskOrganizer"
            android.util.Log.wtf(r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.PipTaskOrganizer.exitPip(int, boolean):void");
    }

    public final void fadeExistingPip(boolean z) {
        float f;
        float f2;
        int i;
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid leash on fadeExistingPip: ");
            m.append(this.mLeash);
            Log.w("PipTaskOrganizer", m.toString());
            return;
        }
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (z) {
            f2 = 1.0f;
        } else {
            f2 = 0.0f;
        }
        PipAnimationController.PipTransitionAnimator transitionDirection = this.mPipAnimationController.getAnimator(this.mTaskInfo, this.mLeash, this.mPipBoundsState.getBounds(), f, f2).setTransitionDirection(1);
        C18902 r2 = this.mPipTransactionHandler;
        Objects.requireNonNull(transitionDirection);
        transitionDirection.mPipTransactionHandler = r2;
        if (z) {
            i = this.mEnterAnimationDuration;
        } else {
            i = this.mExitAnimationDuration;
        }
        transitionDirection.setDuration((long) i).start();
        this.mHasFadeOut = !z;
    }

    public final void fadeOutAndRemoveOverlay(final SurfaceControl surfaceControl, final Runnable runnable, boolean z) {
        long j;
        if (surfaceControl != null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            ofFloat.setDuration((long) this.mCrossFadeAnimationDuration);
            ofFloat.addUpdateListener(new PipTaskOrganizer$$ExternalSyntheticLambda0(this, surfaceControl));
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                    SurfaceControl surfaceControl = surfaceControl;
                    Runnable runnable = runnable;
                    Objects.requireNonNull(pipTaskOrganizer);
                    PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                    Objects.requireNonNull(pipTransitionState);
                    if (pipTransitionState.mState != 0) {
                        Objects.requireNonNull(pipTaskOrganizer.mSurfaceControlTransactionFactory);
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.remove(surfaceControl);
                        transaction.apply();
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
            });
            if (z) {
                j = 500;
            } else {
                j = 0;
            }
            ofFloat.setStartDelay(j);
            ofFloat.start();
        }
    }

    public final void finishResize(SurfaceControl.Transaction transaction, Rect rect, int i, int i2) {
        boolean z;
        PictureInPictureParams pictureInPictureParams;
        Rect rect2 = new Rect(this.mPipBoundsState.getBounds());
        boolean isPipTopLeft = isPipTopLeft();
        this.mPipBoundsState.setBounds(rect);
        boolean z2 = true;
        if (i == 5) {
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(this.mToken, (Rect) null);
                windowContainerTransaction.setWindowingMode(this.mToken, 0);
                windowContainerTransaction.reorder(this.mToken, false);
                this.mPipTransitionController.startExitTransition(15, windowContainerTransaction, (Rect) null);
                return;
            }
            try {
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.setBounds(this.mToken, (Rect) null);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
                ActivityTaskManager.getService().removeRootTasksInWindowingModes(new int[]{2});
            } catch (RemoteException e) {
                Log.e("PipTaskOrganizer", "Failed to remove PiP", e);
            }
        } else if (!PipAnimationController.isInPipDirection(i) || i2 != 1) {
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            prepareFinishResizeTransaction(rect, i, transaction, windowContainerTransaction3);
            if (i == 7 || i == 6 || i == 8) {
                z = true;
            } else {
                z = false;
            }
            if (!z || (pictureInPictureParams = this.mPictureInPictureParams) == null || pictureInPictureParams.isSeamlessResizeEnabled()) {
                z2 = false;
            }
            if (z2) {
                rect2.offsetTo(0, 0);
                Rect rect3 = new Rect(0, 0, rect.width(), rect.height());
                Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
                SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                SurfaceControl surfaceControl = this.mLeash;
                ScreenshotUtils.BufferConsumer bufferConsumer = new ScreenshotUtils.BufferConsumer(transaction2, surfaceControl);
                ScreenshotUtils.captureLayer(surfaceControl, rect2, bufferConsumer);
                SurfaceControl surfaceControl2 = bufferConsumer.mScreenshot;
                if (surfaceControl2 != null) {
                    this.mSyncTransactionQueue.queue(windowContainerTransaction3);
                    this.mSyncTransactionQueue.runInSync(new PipTaskOrganizer$$ExternalSyntheticLambda2(this, surfaceControl2, rect2, rect3));
                } else {
                    applyFinishBoundsResize(windowContainerTransaction3, i, isPipTopLeft);
                }
            } else {
                applyFinishBoundsResize(windowContainerTransaction3, i, isPipTopLeft);
            }
            finishResizeForMenu(rect);
        } else {
            finishResizeForMenu(rect);
        }
    }

    public final boolean isInPip() {
        return this.mPipTransitionState.isInPip();
    }

    public final boolean isPipTopLeft() {
        if (!this.mSplitScreenOptional.isPresent()) {
            return false;
        }
        Rect rect = new Rect();
        this.mSplitScreenOptional.get().getStageBounds(rect, new Rect());
        return rect.contains(this.mPipBoundsState.getBounds());
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        this.mCurrentRotation = configuration.windowConfiguration.getRotation();
    }

    public final void onEndOfSwipePipToHomeTransition() {
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mSwipePipToHomeOverlay = null;
            return;
        }
        Rect bounds = this.mPipBoundsState.getBounds();
        SurfaceControl surfaceControl = this.mSwipePipToHomeOverlay;
        Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.resetScale(transaction, this.mLeash, bounds);
        pipSurfaceTransactionHelper.crop(transaction, this.mLeash, bounds);
        pipSurfaceTransactionHelper.round(transaction, this.mLeash, isInPip());
        applyEnterPipSyncTransaction(bounds, new PipTaskOrganizer$$ExternalSyntheticLambda5(this, bounds, surfaceControl, 0), transaction);
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mInSwipePipToHomeTransition = false;
        this.mSwipePipToHomeOverlay = null;
    }

    public final void onFixedRotationFinished() {
        if (this.mWaitForFixedRotation) {
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                this.mWaitForFixedRotation = false;
                this.mDeferredAnimEndTransaction = null;
                return;
            }
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState);
            if (pipTransitionState.mState == 1) {
                PipTransitionState pipTransitionState2 = this.mPipTransitionState;
                Objects.requireNonNull(pipTransitionState2);
                if (pipTransitionState2.mInSwipePipToHomeTransition) {
                    onEndOfSwipePipToHomeTransition();
                } else {
                    enterPipWithAlphaAnimation(this.mPipBoundsAlgorithm.getEntryDestinationBounds(), (long) this.mEnterAnimationDuration);
                }
            } else {
                PipTransitionState pipTransitionState3 = this.mPipTransitionState;
                Objects.requireNonNull(pipTransitionState3);
                if (pipTransitionState3.mState != 4 || !this.mHasFadeOut) {
                    PipTransitionState pipTransitionState4 = this.mPipTransitionState;
                    Objects.requireNonNull(pipTransitionState4);
                    if (pipTransitionState4.mState == 3 && this.mDeferredAnimEndTransaction != null) {
                        PipAnimationController pipAnimationController = this.mPipAnimationController;
                        Objects.requireNonNull(pipAnimationController);
                        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
                        Objects.requireNonNull(pipTransitionAnimator);
                        Rect rect = pipTransitionAnimator.mDestinationBounds;
                        this.mPipBoundsState.setBounds(rect);
                        applyEnterPipSyncTransaction(rect, new PipTaskOrganizer$$ExternalSyntheticLambda4(this, rect, 0), this.mDeferredAnimEndTransaction);
                    }
                } else {
                    fadeExistingPip(true);
                }
            }
            this.mWaitForFixedRotation = false;
            this.mDeferredAnimEndTransaction = null;
        }
    }

    public final void onFixedRotationStarted(int i) {
        this.mNextRotation = i;
        this.mWaitForFixedRotation = true;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mPipTransitionController.onFixedRotationStarted();
        } else if (this.mPipTransitionState.isInPip()) {
            fadeExistingPip(false);
        }
    }

    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mPipMenuController.onFocusTaskChanged(runningTaskInfo);
    }

    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        IntConsumer intConsumer;
        Objects.requireNonNull(runningTaskInfo, "Requires RunningTaskInfo");
        this.mTaskInfo = runningTaskInfo;
        this.mToken = runningTaskInfo.token;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mState = 1;
        this.mLeash = surfaceControl;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        PictureInPictureParams pictureInPictureParams = runningTaskInfo2.pictureInPictureParams;
        this.mPictureInPictureParams = pictureInPictureParams;
        this.mPipBoundsState.setBoundsStateForEntry(runningTaskInfo2.topActivity, runningTaskInfo2.topActivityInfo, pictureInPictureParams, this.mPipBoundsAlgorithm);
        this.mPipUiEventLoggerLogger.setTaskInfo(this.mTaskInfo);
        this.mPipUiEventLoggerLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_ENTER);
        int i = runningTaskInfo.displayId;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        if (!(i == pipBoundsState.mDisplayId || (intConsumer = this.mOnDisplayIdChangeCallback) == null)) {
            intConsumer.accept(runningTaskInfo.displayId);
        }
        PipTransitionState pipTransitionState2 = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState2);
        if (!pipTransitionState2.mInSwipePipToHomeTransition) {
            if (this.mOneShotAnimationType == 1 && SystemClock.uptimeMillis() - this.mLastOneShotAlphaAnimationTime > 1000) {
                Log.d("PipTaskOrganizer", "Alpha animation is expired. Use bounds animation.");
                this.mOneShotAnimationType = 0;
            }
            if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                if (!this.mWaitForFixedRotation) {
                    Rect entryDestinationBounds = this.mPipBoundsAlgorithm.getEntryDestinationBounds();
                    Objects.requireNonNull(entryDestinationBounds, "Missing destination bounds");
                    Rect bounds = this.mTaskInfo.configuration.windowConfiguration.getBounds();
                    int i2 = this.mOneShotAnimationType;
                    if (i2 == 0) {
                        this.mPipMenuController.attach(this.mLeash);
                        scheduleAnimateResizePip(bounds, entryDestinationBounds, 0.0f, PipBoundsAlgorithm.getValidSourceHintRect(runningTaskInfo.pictureInPictureParams, bounds), 2, this.mEnterAnimationDuration, (Consumer<Rect>) null);
                        PipTransitionState pipTransitionState3 = this.mPipTransitionState;
                        Objects.requireNonNull(pipTransitionState3);
                        pipTransitionState3.mState = 3;
                    } else if (i2 == 1) {
                        enterPipWithAlphaAnimation(entryDestinationBounds, (long) this.mEnterAnimationDuration);
                        this.mOneShotAnimationType = 0;
                    } else {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unrecognized animation type: ");
                        m.append(this.mOneShotAnimationType);
                        throw new RuntimeException(m.toString());
                    }
                } else if (this.mOneShotAnimationType == 1) {
                    Log.d("PipTaskOrganizer", "Defer entering PiP alpha animation, fixed rotation is ongoing");
                    Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(this.mLeash, 0.0f);
                    transaction.show(this.mLeash);
                    transaction.apply();
                    this.mOneShotAnimationType = 0;
                } else {
                    Rect bounds2 = this.mTaskInfo.configuration.windowConfiguration.getBounds();
                    animateResizePip(bounds2, this.mPipBoundsAlgorithm.getEntryDestinationBounds(), PipBoundsAlgorithm.getValidSourceHintRect(this.mPictureInPictureParams, bounds2), 2, this.mEnterAnimationDuration, 0.0f);
                    PipTransitionState pipTransitionState4 = this.mPipTransitionState;
                    Objects.requireNonNull(pipTransitionState4);
                    pipTransitionState4.mState = 3;
                }
            }
        } else if (!this.mWaitForFixedRotation) {
            onEndOfSwipePipToHomeTransition();
        } else {
            Log.d("PipTaskOrganizer", "Defer onTaskAppeared-SwipePipToHome until end of fixed rotation.");
        }
    }

    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rational rational;
        Runnable runnable;
        Objects.requireNonNull(this.mToken, "onTaskInfoChanged requires valid existing mToken");
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        if (pipTransitionState.mState != 4) {
            PipTransitionState pipTransitionState2 = this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState2);
            if (pipTransitionState2.mState != 5) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Defer onTaskInfoChange in current state: ");
                PipTransitionState pipTransitionState3 = this.mPipTransitionState;
                Objects.requireNonNull(pipTransitionState3);
                KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, pipTransitionState3.mState, "PipTaskOrganizer");
                this.mDeferredTaskInfo = runningTaskInfo;
                return;
            }
        }
        this.mPipBoundsState.setLastPipComponentName(runningTaskInfo.topActivity);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Size minimalSize = this.mPipBoundsAlgorithm.getMinimalSize(runningTaskInfo.topActivityInfo);
        Objects.requireNonNull(pipBoundsState);
        boolean z = !Objects.equals(minimalSize, pipBoundsState.mOverrideMinSize);
        pipBoundsState.mOverrideMinSize = minimalSize;
        if (z && (runnable = pipBoundsState.mOnMinimalSizeChangeCallback) != null) {
            runnable.run();
        }
        PictureInPictureParams pictureInPictureParams = runningTaskInfo.pictureInPictureParams;
        if (pictureInPictureParams != null) {
            PictureInPictureParams pictureInPictureParams2 = this.mPictureInPictureParams;
            if (pictureInPictureParams2 != null) {
                rational = pictureInPictureParams2.getAspectRatioRational();
            } else {
                rational = null;
            }
            boolean z2 = !Objects.equals(rational, pictureInPictureParams.getAspectRatioRational());
            this.mPictureInPictureParams = pictureInPictureParams;
            if (z2) {
                PipBoundsState pipBoundsState2 = this.mPipBoundsState;
                float aspectRatio = pictureInPictureParams.getAspectRatio();
                Objects.requireNonNull(pipBoundsState2);
                pipBoundsState2.mAspectRatio = aspectRatio;
            }
            if (z2) {
                PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
                Rect bounds = this.mPipBoundsState.getBounds();
                PipBoundsState pipBoundsState3 = this.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState3);
                Rect adjustedDestinationBounds = pipBoundsAlgorithm.getAdjustedDestinationBounds(bounds, pipBoundsState3.mAspectRatio);
                Objects.requireNonNull(adjustedDestinationBounds, "Missing destination bounds");
                scheduleAnimateResizePip(adjustedDestinationBounds, this.mEnterAnimationDuration, 0, (QSTileHost$$ExternalSyntheticLambda1) null);
                return;
            }
        }
        Log.d("PipTaskOrganizer", "Ignored onTaskInfoChanged with PiP param: " + pictureInPictureParams);
    }

    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        if (pipTransitionState.mState != 0) {
            boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
            if (z) {
                PipTransitionState pipTransitionState2 = this.mPipTransitionState;
                Objects.requireNonNull(pipTransitionState2);
                if (pipTransitionState2.mState == 5) {
                    return;
                }
            }
            WindowContainerToken windowContainerToken = runningTaskInfo.token;
            Objects.requireNonNull(windowContainerToken, "Requires valid WindowContainerToken");
            if (windowContainerToken.asBinder() != this.mToken.asBinder()) {
                Log.wtf("PipTaskOrganizer", "Unrecognized token: " + windowContainerToken);
                return;
            }
            onExitPipFinished(runningTaskInfo);
            if (z) {
                this.mPipTransitionController.forceFinishTransition();
            }
            PipAnimationController pipAnimationController = this.mPipAnimationController;
            Objects.requireNonNull(pipAnimationController);
            PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
            if (pipTransitionAnimator != null) {
                SurfaceControl surfaceControl = pipTransitionAnimator.mContentOverlay;
                if (surfaceControl != null) {
                    PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3 = new PipTaskOrganizer$$ExternalSyntheticLambda3(pipTransitionAnimator, 0);
                    PipTransitionState pipTransitionState3 = this.mPipTransitionState;
                    Objects.requireNonNull(pipTransitionState3);
                    if (pipTransitionState3.mState != 0) {
                        Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.remove(surfaceControl);
                        transaction.apply();
                        pipTaskOrganizer$$ExternalSyntheticLambda3.run();
                    }
                }
                pipTransitionAnimator.removeAllUpdateListeners();
                pipTransitionAnimator.removeAllListeners();
                pipTransitionAnimator.cancel();
            }
        }
    }

    public final void removePip() {
        if (!this.mPipTransitionState.isInPip() || this.mToken == null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Not allowed to removePip in current state mState=");
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState);
            m.append(pipTransitionState.mState);
            m.append(" mToken=");
            m.append(this.mToken);
            Log.wtf("PipTaskOrganizer", m.toString());
            return;
        }
        PipAnimationController.PipTransitionAnimator transitionDirection = this.mPipAnimationController.getAnimator(this.mTaskInfo, this.mLeash, this.mPipBoundsState.getBounds(), 1.0f, 0.0f).setTransitionDirection(5);
        C18902 r2 = this.mPipTransactionHandler;
        Objects.requireNonNull(transitionDirection);
        transitionDirection.mPipTransactionHandler = r2;
        PipAnimationController.PipTransitionAnimator pipAnimationCallback = transitionDirection.setPipAnimationCallback(this.mPipAnimationCallback);
        pipAnimationCallback.setDuration((long) this.mExitAnimationDuration);
        pipAnimationCallback.setInterpolator(Interpolators.ALPHA_OUT);
        pipAnimationCallback.start();
        PipTransitionState pipTransitionState2 = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState2);
        pipTransitionState2.mState = 5;
    }

    public final void scheduleFinishResizePip(Rect rect, int i, Consumer<Rect> consumer) {
        boolean z;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        int i2 = pipTransitionState.mState;
        if (i2 < 3 || i2 == 5) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            finishResize(createFinishResizeSurfaceTransaction(rect), rect, i, -1);
            if (consumer != null) {
                consumer.accept(rect);
            }
        }
    }

    public final void scheduleUserResizePip(Rect rect, Rect rect2, float f, ShellTaskOrganizer$$ExternalSyntheticLambda1 shellTaskOrganizer$$ExternalSyntheticLambda1) {
        if (this.mToken == null || this.mLeash == null) {
            Log.w("PipTaskOrganizer", "Abort animation, invalid leash");
        } else if (rect.isEmpty() || rect2.isEmpty()) {
            Log.w("PipTaskOrganizer", "Attempted to user resize PIP to or from empty bounds, aborting.");
        } else {
            Objects.requireNonNull(this.mSurfaceControlTransactionFactory);
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
            pipSurfaceTransactionHelper.scale(transaction, this.mLeash, rect, rect2, f);
            pipSurfaceTransactionHelper.round(transaction, this.mLeash, rect, rect2);
            if (this.mPipMenuController.isMenuVisible()) {
                this.mPipMenuController.movePipMenu(this.mLeash, transaction, rect2);
            } else {
                transaction.apply();
            }
            if (shellTaskOrganizer$$ExternalSyntheticLambda1 != null) {
                shellTaskOrganizer$$ExternalSyntheticLambda1.accept(rect2);
            }
        }
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("PipTaskOrganizer", ":");
        m.append(ShellTaskOrganizer.taskListenerTypeToString(-4));
        return m.toString();
    }

    public final void finishResizeForMenu(Rect rect) {
        if (isInPip()) {
            this.mPipMenuController.movePipMenu((SurfaceControl) null, (SurfaceControl.Transaction) null, rect);
            this.mPipMenuController.updateMenuBounds(rect);
        }
    }

    public final void prepareFinishResizeTransaction(Rect rect, int i, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        if (PipAnimationController.isInPipDirection(i)) {
            windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
        } else if (PipAnimationController.isOutPipDirection(i)) {
            rect = null;
            windowContainerTransaction.setWindowingMode(this.mToken, 0);
            windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
            this.mLegacySplitScreenOptional.ifPresent(new PipTaskOrganizer$$ExternalSyntheticLambda7(this, i, windowContainerTransaction));
        }
        this.mSurfaceTransactionHelper.round(transaction, this.mLeash, isInPip());
        windowContainerTransaction.setBounds(this.mToken, rect);
        windowContainerTransaction.setBoundsChangeTransaction(this.mToken, transaction);
    }

    public final PipAnimationController.PipTransitionAnimator<?> scheduleAnimateResizePip(Rect rect, Rect rect2, float f, Rect rect3, int i, int i2, Consumer<Rect> consumer) {
        if (!this.mPipTransitionState.isInPip()) {
            return null;
        }
        PipAnimationController.PipTransitionAnimator<?> animateResizePip = animateResizePip(rect, rect2, rect3, i, i2, f);
        if (consumer != null) {
            consumer.accept(rect2);
        }
        return animateResizePip;
    }
}
