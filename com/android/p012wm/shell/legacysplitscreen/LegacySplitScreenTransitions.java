package com.android.p012wm.shell.legacysplitscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions */
public final class LegacySplitScreenTransitions implements Transitions.TransitionHandler {
    public IBinder mAnimatingTransition = null;
    public final ArrayList<Animator> mAnimations = new ArrayList<>();
    public boolean mDismissFromSnap = false;
    public Transitions.TransitionFinishCallback mFinishCallback = null;
    public SurfaceControl.Transaction mFinishTransaction;
    public final LegacySplitScreenTaskListener mListener;
    public IBinder mPendingDismiss = null;
    public IBinder mPendingEnter = null;
    public final LegacySplitScreenController mSplitScreen;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;

    public final void onFinish() {
        if (this.mAnimations.isEmpty()) {
            this.mFinishTransaction.apply();
            this.mTransactionPool.release(this.mFinishTransaction);
            this.mFinishTransaction = null;
            this.mFinishCallback.onTransitionFinished((WindowContainerTransaction) null);
            this.mFinishCallback = null;
            IBinder iBinder = this.mAnimatingTransition;
            if (iBinder == this.mPendingEnter) {
                this.mPendingEnter = null;
            }
            if (iBinder == this.mPendingDismiss) {
                this.mSplitScreen.onDismissSplit();
                this.mPendingDismiss = null;
            }
            this.mDismissFromSnap = false;
            this.mAnimatingTransition = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:71:0x020e, code lost:
        if (r10 == 4) goto L_0x0212;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startAnimation(android.os.IBinder r19, android.window.TransitionInfo r20, android.view.SurfaceControl.Transaction r21, android.view.SurfaceControl.Transaction r22, com.android.p012wm.shell.transition.Transitions.TransitionFinishCallback r23) {
        /*
            r18 = this;
            r6 = r18
            r7 = r19
            r8 = r21
            android.os.IBinder r0 = r6.mPendingDismiss
            r9 = 4
            r10 = 0
            r11 = 3
            r12 = 2
            r13 = 1
            if (r7 == r0) goto L_0x006a
            android.os.IBinder r0 = r6.mPendingEnter
            if (r7 == r0) goto L_0x006a
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r6.mSplitScreen
            boolean r0 = r0.isDividerVisible()
            if (r0 != 0) goto L_0x001c
            return r10
        L_0x001c:
            java.util.List r0 = r20.getChanges()
            int r0 = r0.size()
            int r0 = r0 - r13
        L_0x0025:
            if (r0 < 0) goto L_0x0069
            java.util.List r1 = r20.getChanges()
            java.lang.Object r1 = r1.get(r0)
            android.window.TransitionInfo$Change r1 = (android.window.TransitionInfo.Change) r1
            android.app.ActivityManager$RunningTaskInfo r2 = r1.getTaskInfo()
            if (r2 == 0) goto L_0x0066
            android.app.ActivityManager$RunningTaskInfo r2 = r1.getTaskInfo()
            int r2 = r2.getActivityType()
            if (r2 == r12) goto L_0x0042
            goto L_0x0066
        L_0x0042:
            int r2 = r1.getMode()
            if (r2 == r13) goto L_0x0061
            int r2 = r1.getMode()
            if (r2 != r11) goto L_0x004f
            goto L_0x0061
        L_0x004f:
            int r2 = r1.getMode()
            if (r2 == r12) goto L_0x005b
            int r1 = r1.getMode()
            if (r1 != r9) goto L_0x0066
        L_0x005b:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r1 = r6.mSplitScreen
            r1.ensureNormalSplit()
            goto L_0x0066
        L_0x0061:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r1 = r6.mSplitScreen
            r1.ensureMinimizedSplit()
        L_0x0066:
            int r0 = r0 + -1
            goto L_0x0025
        L_0x0069:
            return r10
        L_0x006a:
            r0 = r23
            r6.mFinishCallback = r0
            com.android.wm.shell.common.TransactionPool r0 = r6.mTransactionPool
            android.view.SurfaceControl$Transaction r0 = r0.acquire()
            r6.mFinishTransaction = r0
            r6.mAnimatingTransition = r7
            java.util.List r0 = r20.getChanges()
            int r0 = r0.size()
            int r0 = r0 - r13
            r14 = r0
        L_0x0082:
            r15 = 6
            if (r14 < 0) goto L_0x022b
            java.util.List r0 = r20.getChanges()
            java.lang.Object r0 = r0.get(r14)
            r16 = r0
            android.window.TransitionInfo$Change r16 = (android.window.TransitionInfo.Change) r16
            android.view.SurfaceControl r5 = r16.getLeash()
            java.util.List r0 = r20.getChanges()
            java.lang.Object r0 = r0.get(r14)
            android.window.TransitionInfo$Change r0 = (android.window.TransitionInfo.Change) r0
            int r4 = r0.getMode()
            if (r4 != r15) goto L_0x0196
            android.window.WindowContainerToken r0 = r16.getParent()
            if (r0 == 0) goto L_0x00f5
            android.window.WindowContainerToken r0 = r16.getParent()
            r3 = r20
            android.window.TransitionInfo$Change r0 = r3.getChange(r0)
            android.view.SurfaceControl r1 = r0.getLeash()
            r8.show(r1)
            android.view.SurfaceControl r1 = r0.getLeash()
            r2 = 1065353216(0x3f800000, float:1.0)
            r8.setAlpha(r1, r2)
            android.view.SurfaceControl r1 = r20.getRootLeash()
            r8.reparent(r5, r1)
            java.util.List r1 = r20.getChanges()
            int r1 = r1.size()
            int r1 = r1 - r14
            r8.setLayer(r5, r1)
            android.view.SurfaceControl$Transaction r1 = r6.mFinishTransaction
            android.view.SurfaceControl r0 = r0.getLeash()
            r1.reparent(r5, r0)
            android.view.SurfaceControl$Transaction r0 = r6.mFinishTransaction
            android.graphics.Point r1 = r16.getEndRelOffset()
            int r1 = r1.x
            float r1 = (float) r1
            android.graphics.Point r2 = r16.getEndRelOffset()
            int r2 = r2.y
            float r2 = (float) r2
            r0.setPosition(r5, r1, r2)
            goto L_0x00f7
        L_0x00f5:
            r3 = r20
        L_0x00f7:
            android.graphics.Rect r0 = new android.graphics.Rect
            android.graphics.Rect r1 = r16.getStartAbsBounds()
            r0.<init>(r1)
            android.app.ActivityManager$RunningTaskInfo r1 = r16.getTaskInfo()
            if (r1 == 0) goto L_0x0112
            android.app.ActivityManager$RunningTaskInfo r1 = r16.getTaskInfo()
            int r1 = r1.getActivityType()
            if (r1 != r12) goto L_0x0112
            r1 = r13
            goto L_0x0113
        L_0x0112:
            r1 = r10
        L_0x0113:
            android.os.IBinder r2 = r6.mPendingDismiss
            if (r2 != r7) goto L_0x0120
            boolean r2 = r6.mDismissFromSnap
            if (r2 == 0) goto L_0x0120
            if (r1 != 0) goto L_0x0120
            r0.offsetTo(r10, r10)
        L_0x0120:
            android.graphics.Rect r2 = new android.graphics.Rect
            android.graphics.Rect r1 = r16.getEndAbsBounds()
            r2.<init>(r1)
            android.graphics.Point r1 = r20.getRootOffset()
            int r1 = r1.x
            int r1 = -r1
            android.graphics.Point r10 = r20.getRootOffset()
            int r10 = r10.y
            int r10 = -r10
            r0.offset(r1, r10)
            android.graphics.Point r1 = r20.getRootOffset()
            int r1 = r1.x
            int r1 = -r1
            android.graphics.Point r10 = r20.getRootOffset()
            int r10 = r10.y
            int r10 = -r10
            r2.offset(r1, r10)
            com.android.wm.shell.common.TransactionPool r1 = r6.mTransactionPool
            android.view.SurfaceControl$Transaction r10 = r1.acquire()
            float[] r1 = new float[r12]
            r1 = {0, 1065353216} // fill-array
            android.animation.ValueAnimator r1 = android.animation.ValueAnimator.ofFloat(r1)
            r11 = 500(0x1f4, double:2.47E-321)
            r1.setDuration(r11)
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda1 r11 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda1
            r11.<init>(r10, r5, r0, r2)
            r1.addUpdateListener(r11)
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda3 r11 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$$ExternalSyntheticLambda3
            r0 = r11
            r12 = r1
            r1 = r18
            r17 = r2
            r2 = r10
            r3 = r5
            r10 = r4
            r4 = r17
            r9 = r5
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5)
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$2 r0 = new com.android.wm.shell.legacysplitscreen.LegacySplitScreenTransitions$2
            r0.<init>(r11)
            r12.addListener(r0)
            java.util.ArrayList<android.animation.Animator> r0 = r6.mAnimations
            r0.add(r12)
            com.android.wm.shell.transition.Transitions r0 = r6.mTransitions
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.common.ShellExecutor r0 = r0.mAnimExecutor
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7 r1 = new com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7
            r1.<init>(r12, r15)
            r0.execute(r1)
            goto L_0x0198
        L_0x0196:
            r10 = r4
            r9 = r5
        L_0x0198:
            android.window.WindowContainerToken r0 = r16.getParent()
            if (r0 == 0) goto L_0x01a2
        L_0x019e:
            r0 = 4
        L_0x019f:
            r1 = 0
            goto L_0x0223
        L_0x01a2:
            android.os.IBinder r0 = r6.mPendingEnter
            if (r7 != r0) goto L_0x01b6
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r6.mListener
            android.app.ActivityManager$RunningTaskInfo r0 = r0.mPrimary
            android.window.WindowContainerToken r0 = r0.token
            android.window.WindowContainerToken r1 = r16.getContainer()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01c6
        L_0x01b6:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r6.mListener
            android.app.ActivityManager$RunningTaskInfo r0 = r0.mSecondary
            android.window.WindowContainerToken r0 = r0.token
            android.window.WindowContainerToken r1 = r16.getContainer()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x01f5
        L_0x01c6:
            android.graphics.Rect r0 = r16.getStartAbsBounds()
            int r0 = r0.width()
            android.graphics.Rect r1 = r16.getStartAbsBounds()
            int r1 = r1.height()
            r8.setWindowCrop(r9, r0, r1)
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r6.mListener
            android.app.ActivityManager$RunningTaskInfo r0 = r0.mPrimary
            android.window.WindowContainerToken r0 = r0.token
            android.window.WindowContainerToken r1 = r16.getContainer()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x01f5
            java.util.List r0 = r20.getChanges()
            int r0 = r0.size()
            int r0 = r0 + r13
            r8.setLayer(r9, r0)
        L_0x01f5:
            int r0 = r20.getType()
            boolean r0 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r0)
            if (r0 == 0) goto L_0x0208
            if (r10 == r13) goto L_0x0204
            r1 = 3
            if (r10 != r1) goto L_0x0208
        L_0x0204:
            r6.startExampleAnimation(r9, r13)
            goto L_0x019e
        L_0x0208:
            if (r0 != 0) goto L_0x019e
            r0 = 2
            if (r10 == r0) goto L_0x0211
            r0 = 4
            if (r10 != r0) goto L_0x019f
            goto L_0x0212
        L_0x0211:
            r0 = 4
        L_0x0212:
            android.os.IBinder r1 = r6.mPendingDismiss
            if (r7 != r1) goto L_0x021f
            boolean r1 = r6.mDismissFromSnap
            if (r1 == 0) goto L_0x021f
            r1 = 0
            r8.setAlpha(r9, r1)
            goto L_0x019f
        L_0x021f:
            r1 = 0
            r6.startExampleAnimation(r9, r1)
        L_0x0223:
            int r14 = r14 + -1
            r9 = r0
            r10 = r1
            r11 = 3
            r12 = 2
            goto L_0x0082
        L_0x022b:
            r1 = r10
            android.os.IBinder r0 = r6.mPendingEnter
            if (r7 != r0) goto L_0x0298
            java.util.List r0 = r20.getChanges()
            int r0 = r0.size()
            int r0 = r0 - r13
        L_0x0239:
            if (r0 < 0) goto L_0x0272
            java.util.List r2 = r20.getChanges()
            java.lang.Object r2 = r2.get(r0)
            android.window.TransitionInfo$Change r2 = (android.window.TransitionInfo.Change) r2
            android.app.ActivityManager$RunningTaskInfo r3 = r2.getTaskInfo()
            if (r3 == 0) goto L_0x026d
            android.app.ActivityManager$RunningTaskInfo r3 = r2.getTaskInfo()
            int r3 = r3.getActivityType()
            r4 = 2
            if (r3 == r4) goto L_0x0258
            r3 = 3
            goto L_0x026f
        L_0x0258:
            int r0 = r2.getMode()
            if (r0 == r13) goto L_0x026b
            int r0 = r2.getMode()
            r3 = 3
            if (r0 == r3) goto L_0x026b
            int r0 = r2.getMode()
            if (r0 != r15) goto L_0x0272
        L_0x026b:
            r10 = r13
            goto L_0x0273
        L_0x026d:
            r3 = 3
            r4 = 2
        L_0x026f:
            int r0 = r0 + -1
            goto L_0x0239
        L_0x0272:
            r10 = r1
        L_0x0273:
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenController r0 = r6.mSplitScreen
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.common.DisplayController r1 = r0.mDisplayController
            android.content.Context r2 = r0.mContext
            int r2 = r2.getDisplayId()
            android.content.Context r1 = r1.getDisplayContext(r2)
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            r0.update(r1)
            if (r10 == 0) goto L_0x0295
            r0.ensureMinimizedSplit()
            goto L_0x0298
        L_0x0295:
            r0.ensureNormalSplit()
        L_0x0298:
            r21.apply()
            r18.onFinish()
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTransitions.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
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
        ofFloat.addUpdateListener(new LegacySplitScreenTransitions$$ExternalSyntheticLambda0(acquire, surfaceControl, f2, f));
        final LegacySplitScreenTransitions$$ExternalSyntheticLambda2 legacySplitScreenTransitions$$ExternalSyntheticLambda2 = new LegacySplitScreenTransitions$$ExternalSyntheticLambda2(this, acquire, surfaceControl, f, ofFloat);
        ofFloat.addListener(new Animator.AnimatorListener() {
            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationCancel(Animator animator) {
                legacySplitScreenTransitions$$ExternalSyntheticLambda2.run();
            }

            public final void onAnimationEnd(Animator animator) {
                legacySplitScreenTransitions$$ExternalSyntheticLambda2.run();
            }
        });
        this.mAnimations.add(ofFloat);
        Transitions transitions = this.mTransitions;
        Objects.requireNonNull(transitions);
        transitions.mAnimExecutor.execute(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(ofFloat, 7));
    }

    public LegacySplitScreenTransitions(TransactionPool transactionPool, Transitions transitions, LegacySplitScreenController legacySplitScreenController, LegacySplitScreenTaskListener legacySplitScreenTaskListener) {
        this.mTransactionPool = transactionPool;
        this.mTransitions = transitions;
        this.mSplitScreen = legacySplitScreenController;
        this.mListener = legacySplitScreenTaskListener;
    }

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        int type = transitionRequestInfo.getType();
        if (this.mSplitScreen.isDividerVisible()) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (triggerTask == null) {
                return windowContainerTransaction;
            }
            if (((type == 2 || type == 4) && triggerTask.parentTaskId == this.mListener.mPrimary.taskId) || ((type == 1 || type == 3) && !triggerTask.supportsMultiWindow)) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return windowContainerTransaction;
            }
            LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mListener;
            LegacySplitScreenController legacySplitScreenController = this.mSplitScreen;
            Objects.requireNonNull(legacySplitScreenController);
            WindowManagerProxy.buildDismissSplit(windowContainerTransaction, legacySplitScreenTaskListener, legacySplitScreenController.mSplitLayout, true);
            if (type == 1 || type == 3) {
                windowContainerTransaction.reorder(triggerTask.token, true);
            }
            this.mPendingDismiss = iBinder;
            return windowContainerTransaction;
        } else if (triggerTask == null || ((type != 1 && type != 3) || triggerTask.configuration.windowConfiguration.getWindowingMode() != 3)) {
            return null;
        } else {
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            LegacySplitScreenController legacySplitScreenController2 = this.mSplitScreen;
            Objects.requireNonNull(legacySplitScreenController2);
            WindowManagerProxy windowManagerProxy = legacySplitScreenController2.mWindowManagerProxy;
            LegacySplitScreenTaskListener legacySplitScreenTaskListener2 = legacySplitScreenController2.mSplits;
            LegacySplitDisplayLayout legacySplitDisplayLayout = legacySplitScreenController2.mRotateSplitLayout;
            if (legacySplitDisplayLayout == null) {
                legacySplitDisplayLayout = legacySplitScreenController2.mSplitLayout;
            }
            legacySplitScreenController2.mHomeStackResizable = windowManagerProxy.buildEnterSplit(windowContainerTransaction2, legacySplitScreenTaskListener2, legacySplitDisplayLayout);
            this.mPendingEnter = iBinder;
            return windowContainerTransaction2;
        }
    }
}
