package com.android.p012wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import androidx.exifinterface.media.C0155xe8491b12;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import com.android.p012wm.shell.transition.OneShotRemoteHandler;
import com.android.p012wm.shell.transition.Transitions;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions */
public final class SplitScreenTransitions {
    public OneShotRemoteHandler mActiveRemoteHandler = null;
    public IBinder mAnimatingTransition = null;
    public final ArrayList<Animator> mAnimations = new ArrayList<>();
    public Transitions.TransitionFinishCallback mFinishCallback = null;
    public SurfaceControl.Transaction mFinishTransaction;
    public final Runnable mOnFinish;
    public DismissTransition mPendingDismiss = null;
    public IBinder mPendingEnter = null;
    public IBinder mPendingRecent = null;
    public OneShotRemoteHandler mPendingRemoteHandler = null;
    public final SplitScreenTransitions$$ExternalSyntheticLambda2 mRemoteFinishCB = new SplitScreenTransitions$$ExternalSyntheticLambda2(this);
    public final StageCoordinator mStageCoordinator;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;

    public final IBinder startDismissTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, Transitions.TransitionHandler transitionHandler, int i, int i2) {
        int i3;
        String str;
        if (i2 == 4) {
            i3 = 18;
        } else {
            i3 = 19;
        }
        if (iBinder == null) {
            iBinder = this.mTransitions.startTransition(i3, windowContainerTransaction, transitionHandler);
        }
        this.mPendingDismiss = new DismissTransition(iBinder, i2, i);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            String valueOf = String.valueOf(SplitScreenController.exitReasonToString(i2));
            if (i == -1) {
                str = "UNDEFINED";
            } else if (i == 0) {
                str = "MAIN";
            } else if (i != 1) {
                str = C0155xe8491b12.m16m("UNKNOWN(", i, ")");
            } else {
                str = "SIDE";
            }
            String valueOf2 = String.valueOf(str);
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1852066478, 0, "  splitTransition  deduced Dismiss due to %s. toTop=%s", valueOf, valueOf2);
        }
        return iBinder;
    }

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$DismissTransition */
    public static class DismissTransition {
        public int mDismissTop;
        public int mReason;
        public IBinder mTransition;

        public DismissTransition(IBinder iBinder, int i, int i2) {
            this.mTransition = iBinder;
            this.mReason = i;
            this.mDismissTop = i2;
        }
    }

    public final void onFinish(WindowContainerTransaction windowContainerTransaction) {
        boolean z;
        if (this.mAnimations.isEmpty()) {
            this.mOnFinish.run();
            SurfaceControl.Transaction transaction = this.mFinishTransaction;
            if (transaction != null) {
                transaction.apply();
                this.mTransactionPool.release(this.mFinishTransaction);
                this.mFinishTransaction = null;
            }
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
            if (transitionFinishCallback != null) {
                transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                this.mFinishCallback = null;
            }
            IBinder iBinder = this.mAnimatingTransition;
            if (iBinder == this.mPendingEnter) {
                this.mPendingEnter = null;
            }
            DismissTransition dismissTransition = this.mPendingDismiss;
            if (dismissTransition != null && dismissTransition.mTransition == iBinder) {
                this.mPendingDismiss = null;
            }
            if (iBinder == this.mPendingRecent) {
                if (windowContainerTransaction == null) {
                    z = true;
                } else {
                    z = false;
                }
                StageCoordinator stageCoordinator = this.mStageCoordinator;
                Objects.requireNonNull(stageCoordinator);
                MainStage mainStage = stageCoordinator.mMainStage;
                Objects.requireNonNull(mainStage);
                if (!mainStage.mIsActive) {
                    StageCoordinator.StageListenerImpl stageListenerImpl = stageCoordinator.mMainStageListener;
                    StageCoordinator.StageListenerImpl stageListenerImpl2 = stageCoordinator.mSideStageListener;
                    stageListenerImpl2.mVisible = false;
                    stageListenerImpl.mVisible = false;
                    stageListenerImpl2.mHasChildren = false;
                    stageListenerImpl.mHasChildren = false;
                } else if (z) {
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    stageCoordinator.prepareExitSplitScreen(-1, windowContainerTransaction2);
                    stageCoordinator.mSplitTransitions.startDismissTransition((IBinder) null, windowContainerTransaction2, stageCoordinator, -1, 5);
                } else {
                    stageCoordinator.setDividerVisibility(true, (SurfaceControl.Transaction) null);
                }
                this.mPendingRecent = null;
            }
            this.mPendingRemoteHandler = null;
            this.mActiveRemoteHandler = null;
            this.mAnimatingTransition = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x014d, code lost:
        if (r23.equals(r12.getContainer()) != false) goto L_0x0152;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void playAnimation(android.os.IBinder r17, android.window.TransitionInfo r18, android.view.SurfaceControl.Transaction r19, android.view.SurfaceControl.Transaction r20, com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r21, android.window.WindowContainerToken r22, android.window.WindowContainerToken r23) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            r8 = r19
            r0 = r21
            r6.mFinishCallback = r0
            r6.mAnimatingTransition = r7
            com.android.wm.shell.transition.OneShotRemoteHandler r0 = r6.mPendingRemoteHandler
            r9 = 0
            if (r0 == 0) goto L_0x0025
            com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda2 r5 = r6.mRemoteFinishCB
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r0.startAnimation(r1, r2, r3, r4, r5)
            com.android.wm.shell.transition.OneShotRemoteHandler r0 = r6.mPendingRemoteHandler
            r6.mActiveRemoteHandler = r0
            r6.mPendingRemoteHandler = r9
            return
        L_0x0025:
            com.android.wm.shell.common.TransactionPool r0 = r6.mTransactionPool
            android.view.SurfaceControl$Transaction r0 = r0.acquire()
            r6.mFinishTransaction = r0
            java.util.List r0 = r18.getChanges()
            int r0 = r0.size()
            r10 = 1
            int r0 = r0 - r10
            r11 = r0
        L_0x0038:
            if (r11 < 0) goto L_0x01c3
            java.util.List r0 = r18.getChanges()
            java.lang.Object r0 = r0.get(r11)
            r12 = r0
            android.window.TransitionInfo$Change r12 = (android.window.TransitionInfo.Change) r12
            android.view.SurfaceControl r13 = r12.getLeash()
            java.util.List r0 = r18.getChanges()
            java.lang.Object r0 = r0.get(r11)
            android.window.TransitionInfo$Change r0 = (android.window.TransitionInfo.Change) r0
            int r14 = r0.getMode()
            r0 = 6
            r15 = 2
            if (r14 != r0) goto L_0x0126
            android.window.WindowContainerToken r0 = r12.getParent()
            if (r0 == 0) goto L_0x00ab
            android.window.WindowContainerToken r0 = r12.getParent()
            r5 = r18
            android.window.TransitionInfo$Change r0 = r5.getChange(r0)
            android.view.SurfaceControl r1 = r0.getLeash()
            r8.show(r1)
            android.view.SurfaceControl r1 = r0.getLeash()
            r2 = 1065353216(0x3f800000, float:1.0)
            r8.setAlpha(r1, r2)
            android.view.SurfaceControl r1 = r18.getRootLeash()
            r8.reparent(r13, r1)
            java.util.List r1 = r18.getChanges()
            int r1 = r1.size()
            int r1 = r1 - r11
            r8.setLayer(r13, r1)
            android.view.SurfaceControl$Transaction r1 = r6.mFinishTransaction
            android.view.SurfaceControl r0 = r0.getLeash()
            r1.reparent(r13, r0)
            android.view.SurfaceControl$Transaction r0 = r6.mFinishTransaction
            android.graphics.Point r1 = r12.getEndRelOffset()
            int r1 = r1.x
            float r1 = (float) r1
            android.graphics.Point r2 = r12.getEndRelOffset()
            int r2 = r2.y
            float r2 = (float) r2
            r0.setPosition(r13, r1, r2)
            goto L_0x00ad
        L_0x00ab:
            r5 = r18
        L_0x00ad:
            android.graphics.Rect r0 = new android.graphics.Rect
            android.graphics.Rect r1 = r12.getStartAbsBounds()
            r0.<init>(r1)
            android.graphics.Rect r4 = new android.graphics.Rect
            android.graphics.Rect r1 = r12.getEndAbsBounds()
            r4.<init>(r1)
            android.graphics.Point r1 = r18.getRootOffset()
            int r1 = r1.x
            int r1 = -r1
            android.graphics.Point r2 = r18.getRootOffset()
            int r2 = r2.y
            int r2 = -r2
            r0.offset(r1, r2)
            android.graphics.Point r1 = r18.getRootOffset()
            int r1 = r1.x
            int r1 = -r1
            android.graphics.Point r2 = r18.getRootOffset()
            int r2 = r2.y
            int r2 = -r2
            r4.offset(r1, r2)
            com.android.wm.shell.common.TransactionPool r1 = r6.mTransactionPool
            android.view.SurfaceControl$Transaction r2 = r1.acquire()
            float[] r1 = new float[r15]
            r1 = {0, 1065353216} // fill-array
            android.animation.ValueAnimator r3 = android.animation.ValueAnimator.ofFloat(r1)
            r9 = 500(0x1f4, double:2.47E-321)
            r3.setDuration(r9)
            com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda1 r1 = new com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda1
            r1.<init>(r2, r13, r0, r4)
            r3.addUpdateListener(r1)
            com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda4 r9 = new com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda4
            r0 = r9
            r1 = r16
            r10 = r3
            r3 = r13
            r5 = r10
            r0.<init>(r1, r2, r3, r4, r5)
            com.android.wm.shell.splitscreen.SplitScreenTransitions$2 r0 = new com.android.wm.shell.splitscreen.SplitScreenTransitions$2
            r0.<init>(r9)
            r10.addListener(r0)
            java.util.ArrayList<android.animation.Animator> r0 = r6.mAnimations
            r0.add(r10)
            com.android.wm.shell.transition.Transitions r0 = r6.mTransitions
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.common.ShellExecutor r0 = r0.mAnimExecutor
            com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0 r1 = new com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0
            r2 = 11
            r1.<init>(r10, r2)
            r0.execute(r1)
        L_0x0126:
            android.window.WindowContainerToken r0 = r12.getParent()
            if (r0 == 0) goto L_0x0133
            r1 = r22
            r2 = r23
            r4 = 1
            goto L_0x01bd
        L_0x0133:
            android.os.IBinder r0 = r6.mPendingEnter
            if (r7 != r0) goto L_0x0177
            android.window.WindowContainerToken r0 = r12.getContainer()
            r1 = r22
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0150
            android.window.WindowContainerToken r0 = r12.getContainer()
            r2 = r23
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x017b
            goto L_0x0152
        L_0x0150:
            r2 = r23
        L_0x0152:
            android.graphics.Rect r0 = r12.getEndAbsBounds()
            int r0 = r0.left
            float r0 = (float) r0
            android.graphics.Rect r3 = r12.getEndAbsBounds()
            int r3 = r3.top
            float r3 = (float) r3
            r8.setPosition(r13, r0, r3)
            android.graphics.Rect r0 = r12.getEndAbsBounds()
            int r0 = r0.width()
            android.graphics.Rect r3 = r12.getEndAbsBounds()
            int r3 = r3.height()
            r8.setWindowCrop(r13, r0, r3)
            goto L_0x017b
        L_0x0177:
            r1 = r22
            r2 = r23
        L_0x017b:
            int r0 = r18.getType()
            boolean r0 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r0)
            r3 = 0
            if (r0 != 0) goto L_0x0199
            int r0 = r18.getType()
            r4 = 17
            if (r0 == r4) goto L_0x0199
            int r0 = r18.getType()
            r4 = 16
            if (r0 != r4) goto L_0x0197
            goto L_0x0199
        L_0x0197:
            r0 = r3
            goto L_0x019a
        L_0x0199:
            r0 = 1
        L_0x019a:
            r4 = 1
            if (r0 == 0) goto L_0x01a6
            if (r14 == r4) goto L_0x01a2
            r5 = 3
            if (r14 != r5) goto L_0x01a6
        L_0x01a2:
            r6.startExampleAnimation(r13, r4)
            goto L_0x01bd
        L_0x01a6:
            if (r0 != 0) goto L_0x01bd
            if (r14 == r15) goto L_0x01ad
            r0 = 4
            if (r14 != r0) goto L_0x01bd
        L_0x01ad:
            int r0 = r18.getType()
            r5 = 18
            if (r0 != r5) goto L_0x01ba
            r0 = 0
            r8.setAlpha(r13, r0)
            goto L_0x01bd
        L_0x01ba:
            r6.startExampleAnimation(r13, r3)
        L_0x01bd:
            int r11 = r11 + -1
            r10 = r4
            r9 = 0
            goto L_0x0038
        L_0x01c3:
            r19.apply()
            r0 = 0
            r6.onFinish(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.splitscreen.SplitScreenTransitions.playAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1, android.window.WindowContainerToken, android.window.WindowContainerToken):void");
    }

    public final IBinder startEnterTransition(int i, WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition, StageCoordinator stageCoordinator) {
        IBinder startTransition = this.mTransitions.startTransition(i, windowContainerTransaction, stageCoordinator);
        this.mPendingEnter = startTransition;
        if (remoteTransition != null) {
            Transitions transitions = this.mTransitions;
            Objects.requireNonNull(transitions);
            OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition);
            this.mPendingRemoteHandler = oneShotRemoteHandler;
            oneShotRemoteHandler.mTransition = startTransition;
        }
        return startTransition;
    }

    public final void startExampleAnimation(SurfaceControl surfaceControl, boolean z) {
        float f;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        float f2 = 1.0f - f;
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f2, f});
        ofFloat.setDuration(500);
        ofFloat.addUpdateListener(new SplitScreenTransitions$$ExternalSyntheticLambda0(acquire, surfaceControl, f2, f));
        final SplitScreenTransitions$$ExternalSyntheticLambda3 splitScreenTransitions$$ExternalSyntheticLambda3 = new SplitScreenTransitions$$ExternalSyntheticLambda3(this, acquire, surfaceControl, f, ofFloat);
        ofFloat.addListener(new Animator.AnimatorListener() {
            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationCancel(Animator animator) {
                splitScreenTransitions$$ExternalSyntheticLambda3.run();
            }

            public final void onAnimationEnd(Animator animator) {
                splitScreenTransitions$$ExternalSyntheticLambda3.run();
            }
        });
        this.mAnimations.add(ofFloat);
        Transitions transitions = this.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mAnimExecutor.execute(new BaseWifiTracker$$ExternalSyntheticLambda1(ofFloat, 9));
    }

    public SplitScreenTransitions(TransactionPool transactionPool, Transitions transitions, Runnable runnable, StageCoordinator stageCoordinator) {
        this.mTransactionPool = transactionPool;
        this.mTransitions = transitions;
        this.mOnFinish = runnable;
        this.mStageCoordinator = stageCoordinator;
    }
}
