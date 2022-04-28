package com.android.p012wm.shell.pip;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;
import java.util.Optional;

/* renamed from: com.android.wm.shell.pip.PipTransition */
public final class PipTransition extends PipTransitionController {
    public final Context mContext;
    public WindowContainerToken mCurrentPipTaskToken;
    public final int mEnterExitAnimationDuration;
    public final Rect mExitDestinationBounds = new Rect();
    public IBinder mExitTransition;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public int mFixedRotation;
    public boolean mHasFadeOut;
    public boolean mInFixedRotation;
    public int mOneShotAnimationType = 0;
    public final PipTransitionState mPipTransitionState;
    public final Optional<SplitScreenController> mSplitScreenOptional;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;

    public PipTransition(Context context, PipBoundsState pipBoundsState, PipTransitionState pipTransitionState, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipAnimationController pipAnimationController, Transitions transitions, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, Optional optional) {
        super(pipBoundsState, phonePipMenuController, pipBoundsAlgorithm, pipAnimationController, transitions);
        this.mContext = context;
        this.mPipTransitionState = pipTransitionState;
        this.mEnterExitAnimationDuration = context.getResources().getInteger(C1777R.integer.config_pipResizeAnimationDuration);
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        this.mSplitScreenOptional = optional;
    }

    public final void fadeExistingPip(boolean z) {
        float f;
        float f2;
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        Objects.requireNonNull(pipTaskOrganizer);
        SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
        PipTaskOrganizer pipTaskOrganizer2 = this.mPipOrganizer;
        Objects.requireNonNull(pipTaskOrganizer2);
        ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer2.mTaskInfo;
        if (surfaceControl == null || !surfaceControl.isValid() || runningTaskInfo == null) {
            Log.w("PipTransition", "Invalid leash on fadeExistingPip: " + surfaceControl);
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
        this.mPipAnimationController.getAnimator(runningTaskInfo, surfaceControl, this.mPipBoundsState.getBounds(), f, f2).setTransitionDirection(1).setPipAnimationCallback(this.mPipAnimationCallback).setDuration((long) this.mEnterExitAnimationDuration).start();
        this.mHasFadeOut = !z;
    }

    public final void forceFinishTransition() {
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        if (transitionFinishCallback != null) {
            transitionFinishCallback.onTransitionFinished((WindowContainerTransaction) null);
            this.mFinishCallback = null;
        }
    }

    public final void onFixedRotationStarted() {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        if (pipTransitionState.mState == 4 && !this.mHasFadeOut) {
            fadeExistingPip(false);
        }
    }

    public final void onTransitionMerged(IBinder iBinder) {
        if (iBinder == this.mExitTransition) {
            boolean z = false;
            PipAnimationController pipAnimationController = this.mPipAnimationController;
            Objects.requireNonNull(pipAnimationController);
            if (pipAnimationController.mCurrentAnimator != null) {
                PipAnimationController pipAnimationController2 = this.mPipAnimationController;
                Objects.requireNonNull(pipAnimationController2);
                pipAnimationController2.mCurrentAnimator.cancel();
                z = true;
            }
            this.mExitTransition = null;
            if (z) {
                PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
                Objects.requireNonNull(pipTaskOrganizer);
                ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
                if (runningTaskInfo != null) {
                    PipTaskOrganizer pipTaskOrganizer2 = this.mPipOrganizer;
                    Objects.requireNonNull(pipTaskOrganizer2);
                    startExpandAnimation(runningTaskInfo, pipTaskOrganizer2.mLeash, new Rect(this.mExitDestinationBounds));
                }
                this.mExitDestinationBounds.setEmpty();
                this.mCurrentPipTaskToken = null;
            }
        }
    }

    public final void setIsFullAnimation(boolean z) {
        this.mOneShotAnimationType = z ^ true ? 1 : 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0055 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x02b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startAnimation(android.os.IBinder r29, android.window.TransitionInfo r30, android.view.SurfaceControl.Transaction r31, android.view.SurfaceControl.Transaction r32, com.android.p012wm.shell.transition.Transitions.TransitionFinishCallback r33) {
        /*
            r28 = this;
            r0 = r28
            r12 = r31
            r13 = r32
            r7 = r33
            android.window.WindowContainerToken r1 = r0.mCurrentPipTaskToken
            r2 = 0
            r3 = -1
            if (r1 != 0) goto L_0x000f
            goto L_0x0035
        L_0x000f:
            java.util.List r1 = r30.getChanges()
            int r1 = r1.size()
            int r1 = r1 + r3
        L_0x0018:
            if (r1 < 0) goto L_0x0035
            java.util.List r4 = r30.getChanges()
            java.lang.Object r4 = r4.get(r1)
            android.window.TransitionInfo$Change r4 = (android.window.TransitionInfo.Change) r4
            android.window.WindowContainerToken r5 = r0.mCurrentPipTaskToken
            android.window.WindowContainerToken r6 = r4.getContainer()
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0032
            r14 = r4
            goto L_0x0036
        L_0x0032:
            int r1 = r1 + -1
            goto L_0x0018
        L_0x0035:
            r14 = r2
        L_0x0036:
            java.util.List r1 = r30.getChanges()
            int r1 = r1.size()
            int r1 = r1 + r3
        L_0x003f:
            if (r1 < 0) goto L_0x0055
            java.util.List r4 = r30.getChanges()
            java.lang.Object r4 = r4.get(r1)
            android.window.TransitionInfo$Change r4 = (android.window.TransitionInfo.Change) r4
            int r5 = r4.getEndFixedRotation()
            if (r5 == r3) goto L_0x0052
            goto L_0x0056
        L_0x0052:
            int r1 = r1 + -1
            goto L_0x003f
        L_0x0055:
            r4 = r2
        L_0x0056:
            r8 = 0
            r1 = 1
            if (r4 == 0) goto L_0x005c
            r5 = r1
            goto L_0x005d
        L_0x005c:
            r5 = r8
        L_0x005d:
            r0.mInFixedRotation = r5
            if (r5 == 0) goto L_0x0066
            int r4 = r4.getEndFixedRotation()
            goto L_0x0067
        L_0x0066:
            r4 = r3
        L_0x0067:
            r0.mFixedRotation = r4
            int r4 = r30.getType()
            android.os.IBinder r5 = r0.mExitTransition
            r6 = r29
            boolean r5 = r6.equals(r5)
            r9 = 1065353216(0x3f800000, float:1.0)
            r6 = 6
            r10 = 3
            if (r5 == 0) goto L_0x02b9
            android.graphics.Rect r5 = r0.mExitDestinationBounds
            r5.setEmpty()
            r0.mExitTransition = r2
            r0.mHasFadeOut = r8
            com.android.wm.shell.transition.Transitions$TransitionFinishCallback r5 = r0.mFinishCallback
            if (r5 != 0) goto L_0x02ac
            if (r14 == 0) goto L_0x02a4
            switch(r4) {
                case 13: goto L_0x0147;
                case 14: goto L_0x00c2;
                case 15: goto L_0x0097;
                default: goto L_0x008d;
            }
        L_0x008d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "mExitTransition with unexpected transit type="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            goto L_0x0295
        L_0x0097:
            r31.apply()
            java.util.List r1 = r30.getChanges()
            java.lang.Object r1 = r1.get(r8)
            android.window.TransitionInfo$Change r1 = (android.window.TransitionInfo.Change) r1
            android.view.SurfaceControl r1 = r1.getLeash()
            com.android.wm.shell.pip.PipBoundsState r3 = r0.mPipBoundsState
            android.graphics.Rect r3 = r3.getDisplayBounds()
            r13.setWindowCrop(r1, r3)
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r0.mPipOrganizer
            android.app.ActivityManager$RunningTaskInfo r3 = r14.getTaskInfo()
            r1.onExitPipFinished(r3)
            r1 = r7
            com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r1 = (com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1) r1
            r1.onTransitionFinished(r2)
            goto L_0x0291
        L_0x00c2:
            java.util.List r1 = r30.getChanges()
            int r1 = r1.size()
            r4 = 4
            if (r1 < r4) goto L_0x013f
            int r1 = r1 + r3
        L_0x00ce:
            if (r1 < 0) goto L_0x011b
            java.util.List r3 = r30.getChanges()
            java.lang.Object r3 = r3.get(r1)
            android.window.TransitionInfo$Change r3 = (android.window.TransitionInfo.Change) r3
            int r4 = r3.getMode()
            if (r4 != r6) goto L_0x00e7
            android.window.WindowContainerToken r5 = r3.getParent()
            if (r5 == 0) goto L_0x00e7
            goto L_0x0118
        L_0x00e7:
            boolean r4 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r4)
            if (r4 == 0) goto L_0x0118
            android.window.WindowContainerToken r4 = r3.getParent()
            if (r4 != 0) goto L_0x0118
            android.view.SurfaceControl r4 = r3.getLeash()
            android.graphics.Rect r3 = r3.getEndAbsBounds()
            android.view.SurfaceControl$Transaction r5 = r12.show(r4)
            android.view.SurfaceControl$Transaction r5 = r5.setAlpha(r4, r9)
            int r8 = r3.left
            float r8 = (float) r8
            int r10 = r3.top
            float r10 = (float) r10
            android.view.SurfaceControl$Transaction r5 = r5.setPosition(r4, r8, r10)
            int r8 = r3.width()
            int r3 = r3.height()
            r5.setWindowCrop(r4, r8, r3)
        L_0x0118:
            int r1 = r1 + -1
            goto L_0x00ce
        L_0x011b:
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r1 = r0.mSplitScreenOptional
            java.lang.Object r1 = r1.get()
            com.android.wm.shell.splitscreen.SplitScreenController r1 = (com.android.p012wm.shell.splitscreen.SplitScreenController) r1
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.splitscreen.StageCoordinator r1 = r1.mStageCoordinator
            r1.finishEnterSplitScreen(r12)
            r31.apply()
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r0.mPipOrganizer
            android.app.ActivityManager$RunningTaskInfo r3 = r14.getTaskInfo()
            r1.onExitPipFinished(r3)
            r1 = r7
            com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r1 = (com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1) r1
            r1.onTransitionFinished(r2)
            goto L_0x0291
        L_0x013f:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Got an exit-pip-to-split transition with unexpected change-list"
            r0.<init>(r1)
            throw r0
        L_0x0147:
            java.util.List r3 = r30.getChanges()
            int r3 = r3.size()
            int r3 = r3 - r1
        L_0x0150:
            if (r3 < 0) goto L_0x0179
            java.util.List r4 = r30.getChanges()
            java.lang.Object r4 = r4.get(r3)
            android.window.TransitionInfo$Change r4 = (android.window.TransitionInfo.Change) r4
            int r5 = r4.getMode()
            if (r5 != r6) goto L_0x0176
            int r5 = r4.getFlags()
            r5 = r5 & 32
            if (r5 == 0) goto L_0x0176
            int r5 = r4.getStartRotation()
            int r9 = r4.getEndRotation()
            if (r5 == r9) goto L_0x0176
            r2 = r4
            goto L_0x0179
        L_0x0176:
            int r3 = r3 + -1
            goto L_0x0150
        L_0x0179:
            if (r2 == 0) goto L_0x0245
            int r3 = r2.getStartRotation()
            int r4 = r2.getEndRotation()
            int r3 = android.util.RotationUtils.deltaRotation(r3, r4)
            com.android.wm.shell.transition.CounterRotatorHelper r15 = new com.android.wm.shell.transition.CounterRotatorHelper
            r15.<init>()
            r11 = r30
            r15.handleClosingChanges(r11, r12, r2)
            com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda1 r4 = new com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda1
            r5 = r7
            com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r5 = (com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1) r5
            r4.<init>(r0, r14, r5)
            r0.mFinishCallback = r4
            android.graphics.Rect r11 = new android.graphics.Rect
            android.graphics.Rect r4 = r14.getStartAbsBounds()
            r11.<init>(r4)
            android.graphics.Rect r2 = r2.getStartAbsBounds()
            android.util.RotationUtils.rotateBounds(r11, r2, r3)
            android.graphics.Rect r9 = new android.graphics.Rect
            android.graphics.Rect r2 = r14.getEndAbsBounds()
            r9.<init>(r2)
            android.graphics.Point r2 = r14.getEndRelOffset()
            int r4 = r2.x
            int r4 = -r4
            int r5 = r2.y
            int r5 = -r5
            r11.offset(r4, r5)
            int r4 = r2.x
            int r4 = -r4
            int r2 = r2.y
            int r2 = -r2
            r9.offset(r4, r2)
            int r7 = android.util.RotationUtils.deltaRotation(r3, r8)
            if (r7 != r1) goto L_0x01d7
            r2 = 90
            int r3 = r11.right
            int r4 = r11.top
            goto L_0x01dd
        L_0x01d7:
            r2 = -90
            int r3 = r11.left
            int r4 = r11.bottom
        L_0x01dd:
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r5 = r0.mSurfaceTransactionHelper
            android.view.SurfaceControl r6 = r14.getLeash()
            android.graphics.Rect r16 = new android.graphics.Rect
            r16.<init>()
            float r2 = (float) r2
            float r3 = (float) r3
            float r4 = (float) r4
            r17 = 1
            if (r7 != r10) goto L_0x01f2
            r18 = r1
            goto L_0x01f4
        L_0x01f2:
            r18 = r8
        L_0x01f4:
            r1 = r5
            r8 = r2
            r2 = r31
            r10 = r3
            r3 = r6
            r19 = r4
            r4 = r9
            r5 = r11
            r6 = r16
            r25 = r7
            r7 = r8
            r8 = r10
            r21 = r9
            r9 = r19
            r10 = r17
            r20 = r11
            r11 = r18
            r1.rotateAndScaleWithCrop(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r31.apply()
            r15.cleanUp(r13)
            com.android.wm.shell.pip.PipAnimationController r1 = r0.mPipAnimationController
            android.app.ActivityManager$RunningTaskInfo r17 = r14.getTaskInfo()
            android.view.SurfaceControl r18 = r14.getLeash()
            r22 = 0
            r23 = 3
            r24 = 0
            r16 = r1
            r19 = r20
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r16.getAnimator(r17, r18, r19, r20, r21, r22, r23, r24, r25)
            r2 = 3
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r1.setTransitionDirection(r2)
            com.android.wm.shell.pip.PipTransitionController$1 r2 = r0.mPipAnimationCallback
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r1.setPipAnimationCallback(r2)
            int r2 = r0.mEnterExitAnimationDuration
            long r2 = (long) r2
            android.animation.ValueAnimator r1 = r1.setDuration(r2)
            r1.start()
            goto L_0x0290
        L_0x0245:
            com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda0 r1 = new com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda0
            r2 = r7
            com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r2 = (com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1) r2
            r1.<init>(r0, r14, r2)
            r0.mFinishCallback = r1
            android.graphics.Rect r7 = new android.graphics.Rect
            android.graphics.Rect r1 = r14.getEndAbsBounds()
            r7.<init>(r1)
            android.graphics.Point r1 = r14.getEndRelOffset()
            int r2 = r1.x
            int r2 = -r2
            int r1 = r1.y
            int r1 = -r1
            r7.offset(r2, r1)
            android.view.SurfaceControl r1 = r14.getLeash()
            r12.setWindowCrop(r1, r7)
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r1 = r0.mSurfaceTransactionHelper
            android.view.SurfaceControl r3 = r14.getLeash()
            com.android.wm.shell.pip.PipBoundsState r2 = r0.mPipBoundsState
            android.graphics.Rect r5 = r2.getBounds()
            java.util.Objects.requireNonNull(r1)
            r6 = 0
            r2 = r31
            r4 = r7
            r1.scale(r2, r3, r4, r5, r6)
            r31.apply()
            android.app.ActivityManager$RunningTaskInfo r1 = r14.getTaskInfo()
            android.view.SurfaceControl r2 = r14.getLeash()
            r0.startExpandAnimation(r1, r2, r7)
        L_0x0290:
            r2 = 0
        L_0x0291:
            r0.mCurrentPipTaskToken = r2
            r0 = 1
            return r0
        L_0x0295:
            java.lang.String r2 = android.view.WindowManager.transitTypeToString(r4)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x02a4:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Cannot find the pip window for exit-pip transition."
            r0.<init>(r1)
            throw r0
        L_0x02ac:
            r5.onTransitionFinished(r2)
            r0.mFinishCallback = r2
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Previous callback not called, aborting exit PIP."
            r0.<init>(r1)
            throw r0
        L_0x02b9:
            r11 = r30
            r10 = 0
            r1 = 2
            if (r14 == 0) goto L_0x0330
            android.app.ActivityManager$RunningTaskInfo r2 = r14.getTaskInfo()
            int r2 = r2.getWindowingMode()
            if (r2 == r1) goto L_0x0330
            android.view.SurfaceControl r15 = r14.getLeash()
            android.graphics.Rect r6 = r14.getEndAbsBounds()
            android.graphics.Point r1 = r14.getEndRelOffset()
            int r2 = r1.x
            int r2 = -r2
            int r1 = r1.y
            int r1 = -r1
            r6.offset(r2, r1)
            r1 = 0
            r12.setWindowCrop(r15, r1)
            r3 = 1065353216(0x3f800000, float:1.0)
            r4 = 0
            r5 = 0
            r16 = 1065353216(0x3f800000, float:1.0)
            r1 = r31
            r2 = r15
            r8 = r6
            r6 = r16
            r1.setMatrix(r2, r3, r4, r5, r6)
            r12.setCornerRadius(r15, r10)
            int r1 = r8.left
            float r1 = (float) r1
            int r2 = r8.top
            float r2 = (float) r2
            r12.setPosition(r15, r1, r2)
            boolean r1 = r0.mHasFadeOut
            if (r1 == 0) goto L_0x0321
            android.app.ActivityManager$RunningTaskInfo r1 = r14.getTaskInfo()
            boolean r1 = r1.isVisible()
            if (r1 == 0) goto L_0x0321
            com.android.wm.shell.pip.PipAnimationController r1 = r0.mPipAnimationController
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r1.mCurrentAnimator
            if (r1 == 0) goto L_0x031e
            com.android.wm.shell.pip.PipAnimationController r1 = r0.mPipAnimationController
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r1.mCurrentAnimator
            r1.cancel()
        L_0x031e:
            r12.setAlpha(r15, r9)
        L_0x0321:
            r1 = 0
            r0.mHasFadeOut = r1
            r1 = 0
            r0.mCurrentPipTaskToken = r1
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r0.mPipOrganizer
            android.app.ActivityManager$RunningTaskInfo r2 = r14.getTaskInfo()
            r1.onExitPipFinished(r2)
        L_0x0330:
            android.window.WindowContainerToken r1 = r0.mCurrentPipTaskToken
            java.util.List r2 = r30.getChanges()
            int r2 = r2.size()
            int r2 = r2 + -1
        L_0x033c:
            if (r2 < 0) goto L_0x039b
            java.util.List r3 = r30.getChanges()
            java.lang.Object r3 = r3.get(r2)
            android.window.TransitionInfo$Change r3 = (android.window.TransitionInfo.Change) r3
            android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
            if (r4 == 0) goto L_0x0398
            android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
            int r4 = r4.getWindowingMode()
            r5 = 2
            if (r4 != r5) goto L_0x0398
            android.window.WindowContainerToken r3 = r3.getContainer()
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L_0x0398
            int r1 = r30.getType()
            r2 = 10
            if (r1 == r2) goto L_0x0396
            int r1 = r30.getType()
            r2 = 1
            if (r1 != r2) goto L_0x0373
            goto L_0x0396
        L_0x0373:
            int r1 = r30.getType()
            r2 = 6
            if (r1 != r2) goto L_0x037b
            goto L_0x0396
        L_0x037b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Entering PIP with unexpected transition type="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r2 = r30.getType()
            java.lang.String r2 = android.view.WindowManager.transitTypeToString(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0396:
            r1 = 1
            goto L_0x039d
        L_0x0398:
            int r2 = r2 + -1
            goto L_0x033c
        L_0x039b:
            r5 = 2
            r1 = 0
        L_0x039d:
            if (r1 == 0) goto L_0x05ba
            java.util.List r1 = r30.getChanges()
            int r1 = r1.size()
            int r1 = r1 + -1
            r2 = 0
            r3 = 0
        L_0x03ab:
            if (r1 < 0) goto L_0x03d4
            java.util.List r4 = r30.getChanges()
            java.lang.Object r4 = r4.get(r1)
            android.window.TransitionInfo$Change r4 = (android.window.TransitionInfo.Change) r4
            android.app.ActivityManager$RunningTaskInfo r6 = r4.getTaskInfo()
            if (r6 == 0) goto L_0x03c9
            android.app.ActivityManager$RunningTaskInfo r6 = r4.getTaskInfo()
            int r6 = r6.getWindowingMode()
            if (r6 != r5) goto L_0x03c9
            r2 = r4
            goto L_0x03d1
        L_0x03c9:
            int r6 = r4.getFlags()
            r6 = r6 & r5
            if (r6 == 0) goto L_0x03d1
            r3 = r4
        L_0x03d1:
            int r1 = r1 + -1
            goto L_0x03ab
        L_0x03d4:
            if (r2 != 0) goto L_0x03d9
            r8 = 0
            goto L_0x0596
        L_0x03d9:
            android.window.WindowContainerToken r1 = r2.getContainer()
            r0.mCurrentPipTaskToken = r1
            r1 = 0
            r0.mHasFadeOut = r1
            com.android.wm.shell.transition.Transitions$TransitionFinishCallback r1 = r0.mFinishCallback
            if (r1 != 0) goto L_0x05ac
            if (r3 == 0) goto L_0x03f6
            android.view.SurfaceControl r1 = r3.getLeash()
            r12.show(r1)
            android.view.SurfaceControl r1 = r3.getLeash()
            r12.setAlpha(r1, r9)
        L_0x03f6:
            java.util.List r1 = r30.getChanges()
            int r1 = r1.size()
            int r1 = r1 + -1
        L_0x0400:
            if (r1 < 0) goto L_0x0429
            java.util.List r4 = r30.getChanges()
            java.lang.Object r4 = r4.get(r1)
            android.window.TransitionInfo$Change r4 = (android.window.TransitionInfo.Change) r4
            if (r4 == r2) goto L_0x0426
            if (r4 != r3) goto L_0x0411
            goto L_0x0426
        L_0x0411:
            int r6 = r4.getMode()
            boolean r6 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r6)
            if (r6 == 0) goto L_0x0426
            android.view.SurfaceControl r4 = r4.getLeash()
            android.view.SurfaceControl$Transaction r6 = r12.show(r4)
            r6.setAlpha(r4, r9)
        L_0x0426:
            int r1 = r1 + -1
            goto L_0x0400
        L_0x0429:
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r1)
            r3 = 3
            r1.mState = r3
            r0.mFinishCallback = r7
            boolean r1 = r0.mInFixedRotation
            if (r1 == 0) goto L_0x043a
            int r1 = r0.mFixedRotation
            goto L_0x043e
        L_0x043a:
            int r1 = r2.getEndRotation()
        L_0x043e:
            android.app.ActivityManager$RunningTaskInfo r3 = r2.getTaskInfo()
            android.view.SurfaceControl r4 = r2.getLeash()
            int r2 = r2.getStartRotation()
            android.content.ComponentName r6 = r3.topActivity
            android.app.PictureInPictureParams r7 = r3.pictureInPictureParams
            android.content.pm.ActivityInfo r8 = r3.topActivityInfo
            com.android.wm.shell.pip.PipBoundsState r9 = r0.mPipBoundsState
            com.android.wm.shell.pip.PipBoundsAlgorithm r11 = r0.mPipBoundsAlgorithm
            r9.setBoundsStateForEntry(r6, r8, r7, r11)
            com.android.wm.shell.pip.PipBoundsAlgorithm r6 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r6 = r6.getEntryDestinationBounds()
            android.content.res.Configuration r7 = r3.configuration
            android.app.WindowConfiguration r7 = r7.windowConfiguration
            android.graphics.Rect r7 = r7.getBounds()
            int r8 = android.util.RotationUtils.deltaRotation(r2, r1)
            android.app.PictureInPictureParams r9 = r3.pictureInPictureParams
            android.graphics.Rect r9 = com.android.p012wm.shell.pip.PipBoundsAlgorithm.getValidSourceHintRect(r9, r7)
            if (r8 == 0) goto L_0x04a7
            boolean r11 = r0.mInFixedRotation
            if (r11 == 0) goto L_0x04a7
            com.android.wm.shell.pip.PipBoundsState r11 = r0.mPipBoundsState
            java.util.Objects.requireNonNull(r11)
            com.android.wm.shell.common.DisplayLayout r11 = r11.mDisplayLayout
            android.content.Context r14 = r0.mContext
            android.content.res.Resources r14 = r14.getResources()
            r11.rotateTo(r14, r1)
            com.android.wm.shell.pip.PipBoundsState r11 = r0.mPipBoundsState
            android.graphics.Rect r11 = r11.getDisplayBounds()
            com.android.wm.shell.pip.PipBoundsAlgorithm r14 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r14 = r14.getEntryDestinationBounds()
            r6.set(r14)
            android.util.RotationUtils.rotateBounds(r6, r11, r1, r2)
            if (r9 == 0) goto L_0x04a7
            android.graphics.Rect r1 = r3.displayCutoutInsets
            if (r1 == 0) goto L_0x04a7
            r2 = 3
            if (r8 != r2) goto L_0x04a7
            int r2 = r1.left
            int r1 = r1.top
            r9.offset(r2, r1)
        L_0x04a7:
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r1 = r0.mSurfaceTransactionHelper
            r1.crop(r13, r4, r6)
            r2 = 1
            r1.round(r13, r4, r2)
            com.android.wm.shell.pip.PipMenuController r1 = r0.mPipMenuController
            r1.attach(r4)
            android.app.PictureInPictureParams r1 = r3.pictureInPictureParams
            r2 = 9
            if (r1 == 0) goto L_0x051a
            boolean r1 = r1.isAutoEnterEnabled()
            if (r1 == 0) goto L_0x051a
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mInSwipePipToHomeTransition
            if (r1 == 0) goto L_0x051a
            r1 = 0
            r0.mOneShotAnimationType = r1
            android.view.SurfaceControl$Transaction r1 = new android.view.SurfaceControl$Transaction
            r1.<init>()
            android.graphics.Matrix r7 = android.graphics.Matrix.IDENTITY_MATRIX
            float[] r2 = new float[r2]
            android.view.SurfaceControl$Transaction r2 = r1.setMatrix(r4, r7, r2)
            int r7 = r6.left
            float r7 = (float) r7
            int r9 = r6.top
            float r9 = (float) r9
            android.view.SurfaceControl$Transaction r2 = r2.setPosition(r4, r7, r9)
            int r7 = r6.width()
            int r9 = r6.height()
            r2.setWindowCrop(r4, r7, r9)
            r12.merge(r1)
            r31.apply()
            if (r8 == 0) goto L_0x0504
            boolean r1 = r0.mInFixedRotation
            if (r1 == 0) goto L_0x0504
            com.android.wm.shell.pip.PipBoundsAlgorithm r1 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r1 = r1.getEntryDestinationBounds()
            r6.set(r1)
        L_0x0504:
            com.android.wm.shell.pip.PipBoundsState r1 = r0.mPipBoundsState
            r1.setBounds(r6)
            r1 = 0
            r0.onFinishResize(r3, r6, r5, r1)
            r0.sendOnPipTransitionFinished(r5)
            com.android.wm.shell.pip.PipTransitionState r0 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r0)
            r1 = 0
            r0.mInSwipePipToHomeTransition = r1
            goto L_0x0595
        L_0x051a:
            if (r8 == 0) goto L_0x052a
            android.graphics.Matrix r1 = new android.graphics.Matrix
            r1.<init>()
            float r11 = (float) r8
            r1.postRotate(r11)
            float[] r2 = new float[r2]
            r12.setMatrix(r4, r1, r2)
        L_0x052a:
            int r1 = r0.mOneShotAnimationType
            if (r1 != 0) goto L_0x0555
            r2 = 0
            int r8 = android.util.RotationUtils.deltaRotation(r8, r2)
            com.android.wm.shell.pip.PipAnimationController r1 = r0.mPipAnimationController
            r25 = 2
            r26 = 0
            r18 = r1
            r19 = r3
            r20 = r4
            r21 = r7
            r22 = r7
            r23 = r6
            r24 = r9
            r27 = r8
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r18.getAnimator(r19, r20, r21, r22, r23, r24, r25, r26, r27)
            if (r9 != 0) goto L_0x0570
            android.content.Context r2 = r0.mContext
            r1.setUseContentOverlay(r2)
            goto L_0x0570
        L_0x0555:
            r2 = 1
            if (r1 != r2) goto L_0x0597
            r12.setAlpha(r4, r10)
            com.android.wm.shell.pip.PipAnimationController r1 = r0.mPipAnimationController
            r22 = 0
            r23 = 1065353216(0x3f800000, float:1.0)
            r18 = r1
            r19 = r3
            r20 = r4
            r21 = r6
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r1 = r18.getAnimator(r19, r20, r21, r22, r23)
            r2 = 0
            r0.mOneShotAnimationType = r2
        L_0x0570:
            r31.apply()
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r2 = r1.setTransitionDirection(r5)
            com.android.wm.shell.pip.PipTransitionController$1 r3 = r0.mPipAnimationCallback
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r2 = r2.setPipAnimationCallback(r3)
            int r3 = r0.mEnterExitAnimationDuration
            long r3 = (long) r3
            r2.setDuration(r3)
            if (r8 == 0) goto L_0x0592
            boolean r2 = r0.mInFixedRotation
            if (r2 == 0) goto L_0x0592
            com.android.wm.shell.pip.PipBoundsAlgorithm r0 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r0 = r0.getEntryDestinationBounds()
            r1.setDestinationBounds(r0)
        L_0x0592:
            r1.start()
        L_0x0595:
            r8 = 1
        L_0x0596:
            return r8
        L_0x0597:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Unrecognized animation type: "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            int r0 = r0.mOneShotAnimationType
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x05ac:
            r2 = 0
            r1.onTransitionFinished(r2)
            r0.mFinishCallback = r2
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Previous callback not called, aborting entering PIP."
            r0.<init>(r1)
            throw r0
        L_0x05ba:
            if (r14 == 0) goto L_0x05dc
            android.view.SurfaceControl r1 = r14.getLeash()
            com.android.wm.shell.pip.PipBoundsState r2 = r0.mPipBoundsState
            android.graphics.Rect r2 = r2.getBounds()
            com.android.wm.shell.pip.PipTransitionState r3 = r0.mPipTransitionState
            boolean r3 = r3.isInPip()
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r4 = r0.mSurfaceTransactionHelper
            r4.crop(r12, r1, r2)
            r4.round(r12, r1, r3)
            com.android.wm.shell.pip.PipSurfaceTransactionHelper r4 = r0.mSurfaceTransactionHelper
            r4.crop(r13, r1, r2)
            r4.round(r13, r1, r3)
        L_0x05dc:
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            boolean r1 = r1.isInPip()
            if (r1 == 0) goto L_0x05f0
            boolean r1 = r0.mInFixedRotation
            if (r1 != 0) goto L_0x05f0
            boolean r1 = r0.mHasFadeOut
            if (r1 == 0) goto L_0x05f0
            r1 = 1
            r0.fadeExistingPip(r1)
        L_0x05f0:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.PipTransition.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    public final void startExitTransition(int i, WindowContainerTransaction windowContainerTransaction, Rect rect) {
        if (rect != null) {
            this.mExitDestinationBounds.set(rect);
        }
        this.mExitTransition = this.mTransitions.startTransition(i, windowContainerTransaction, this);
    }

    public final void startExpandAnimation(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Rect rect) {
        this.mPipAnimationController.getAnimator(runningTaskInfo, surfaceControl, this.mPipBoundsState.getBounds(), this.mPipBoundsState.getBounds(), rect, (Rect) null, 3, 0.0f, 0).setTransitionDirection(3).setPipAnimationCallback(this.mPipAnimationCallback).setDuration((long) this.mEnterExitAnimationDuration).start();
    }

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (transitionRequestInfo.getType() != 10) {
            return null;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (this.mOneShotAnimationType == 1) {
            windowContainerTransaction.setActivityWindowingMode(transitionRequestInfo.getTriggerTask().token, 0);
            windowContainerTransaction.setBounds(transitionRequestInfo.getTriggerTask().token, this.mPipBoundsAlgorithm.getEntryDestinationBounds());
        }
        return windowContainerTransaction;
    }

    public final void onFinishResize(TaskInfo taskInfo, Rect rect, int i, SurfaceControl.Transaction transaction) {
        Rect rect2;
        if (PipAnimationController.isInPipDirection(i)) {
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            Objects.requireNonNull(pipTransitionState);
            pipTransitionState.mState = 4;
        }
        if (this.mExitTransition == null && this.mFinishCallback != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (PipAnimationController.isInPipDirection(i)) {
                windowContainerTransaction.setActivityWindowingMode(taskInfo.token, 0);
                windowContainerTransaction.scheduleFinishEnterPip(taskInfo.token, rect);
                rect2 = rect;
            } else if (PipAnimationController.isOutPipDirection(i)) {
                if (i == 3) {
                    rect2 = null;
                } else {
                    rect2 = rect;
                }
                windowContainerTransaction.setWindowingMode(taskInfo.token, 0);
                windowContainerTransaction.setActivityWindowingMode(taskInfo.token, 0);
            } else {
                rect2 = null;
            }
            windowContainerTransaction.setBounds(taskInfo.token, rect2);
            if (transaction != null) {
                windowContainerTransaction.setBoundsChangeTransaction(taskInfo.token, transaction);
            }
            this.mFinishCallback.onTransitionFinished(windowContainerTransaction);
            this.mFinishCallback = null;
        }
        this.mPipMenuController.movePipMenu((SurfaceControl) null, (SurfaceControl.Transaction) null, rect);
        this.mPipMenuController.updateMenuBounds(rect);
    }
}
