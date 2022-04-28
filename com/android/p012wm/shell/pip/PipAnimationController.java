package com.android.p012wm.shell.pip;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.RotationUtils;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipAnimationController */
public final class PipAnimationController {
    public PipTransitionAnimator mCurrentAnimator;
    public final ThreadLocal<AnimationHandler> mSfAnimationHandlerThreadLocal = ThreadLocal.withInitial(PipAnimationController$$ExternalSyntheticLambda0.INSTANCE);
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;

    /* renamed from: com.android.wm.shell.pip.PipAnimationController$PipAnimationCallback */
    public static class PipAnimationCallback {
        public void onPipAnimationCancel(PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }

        public void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }

        public void onPipAnimationStart(PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }
    }

    /* renamed from: com.android.wm.shell.pip.PipAnimationController$PipTransactionHandler */
    public static class PipTransactionHandler {
    }

    /* renamed from: com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator */
    public static abstract class PipTransitionAnimator<T> extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final int mAnimationType;
        public T mBaseValue;
        public SurfaceControl mContentOverlay;
        public T mCurrentValue;
        public final Rect mDestinationBounds;
        public T mEndValue;
        public final SurfaceControl mLeash;
        public PipAnimationCallback mPipAnimationCallback;
        public PipTransactionHandler mPipTransactionHandler;
        public T mStartValue;
        public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
        public PipSurfaceTransactionHelper mSurfaceTransactionHelper;
        public final TaskInfo mTaskInfo;
        public int mTransitionDirection;

        public PipTransitionAnimator() {
            throw null;
        }

        public PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Object obj, Object obj2, Object obj3) {
            Rect rect2 = new Rect();
            this.mDestinationBounds = rect2;
            this.mTaskInfo = taskInfo;
            this.mLeash = surfaceControl;
            this.mAnimationType = i;
            rect2.set(rect);
            this.mBaseValue = obj;
            this.mStartValue = obj2;
            this.mEndValue = obj3;
            addListener(this);
            addUpdateListener(this);
            this.mSurfaceControlTransactionFactory = DialogFragment$$ExternalSyntheticOutline0.INSTANCE;
            this.mTransitionDirection = 0;
        }

        public abstract void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f);

        public final void onAnimationRepeat(Animator animator) {
        }

        public void onEndTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
        }

        public void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
        }

        @VisibleForTesting
        public PipTransitionAnimator<T> setTransitionDirection(int i) {
            if (i != 1) {
                this.mTransitionDirection = i;
            }
            return this;
        }

        public static C18862 ofBounds(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2) {
            Rect rect5;
            Rect rect6;
            Rect rect7;
            Rect rect8;
            Rect rect9;
            Rect rect10;
            Rect rect11 = rect;
            Rect rect12 = rect3;
            Rect rect13 = rect4;
            int i3 = i2;
            boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
            if (isOutPipDirection) {
                rect5 = new Rect(rect12);
            } else {
                rect5 = new Rect(rect11);
            }
            Rect rect14 = rect5;
            if (i3 == 1 || i3 == 3) {
                Rect rect15 = new Rect(rect12);
                Rect rect16 = new Rect(rect12);
                RotationUtils.rotateBounds(rect16, rect14, i3);
                if (isOutPipDirection) {
                    rect10 = rect16;
                } else {
                    rect10 = rect14;
                }
                rect6 = rect15;
                rect8 = rect16;
                rect7 = rect10;
            } else {
                rect8 = null;
                rect6 = null;
                rect7 = rect14;
            }
            if (rect13 == null) {
                rect9 = null;
            } else {
                rect9 = new Rect(rect13.left - rect7.left, rect13.top - rect7.top, rect7.right - rect13.right, rect7.bottom - rect13.bottom);
            }
            Rect rect17 = r1;
            Rect rect18 = new Rect(0, 0, 0, 0);
            Rect rect19 = r2;
            Rect rect20 = new Rect(rect11);
            Rect rect21 = r0;
            Rect rect22 = new Rect(rect2);
            Rect rect23 = r0;
            Rect rect24 = new Rect(rect12);
            return new PipTransitionAnimator<Rect>(taskInfo, surfaceControl, rect3, rect19, rect21, rect23, f, rect8, f, rect4, isOutPipDirection, rect14, rect7, rect6, rect3, i2, rect9, rect17, i) {
                public final RectEvaluator mInsetsEvaluator = new RectEvaluator(new Rect());
                public final RectEvaluator mRectEvaluator = new RectEvaluator(new Rect());
                public final /* synthetic */ int val$direction;
                public final /* synthetic */ Rect val$endValue;
                public final /* synthetic */ Rect val$initialContainerRect;
                public final /* synthetic */ Rect val$initialSourceValue;
                public final /* synthetic */ boolean val$isOutPipDirection;
                public final /* synthetic */ Rect val$lastEndRect;
                public final /* synthetic */ Rect val$rotatedEndRect;
                public final /* synthetic */ int val$rotationDelta;
                public final /* synthetic */ Rect val$sourceHintRect;
                public final /* synthetic */ Rect val$sourceHintRectInsets;
                public final /* synthetic */ float val$startingAngle;
                public final /* synthetic */ Rect val$zeroInsets;

                {
                    this.val$rotatedEndRect = r11;
                    this.val$startingAngle = r12;
                    this.val$sourceHintRect = r13;
                    this.val$isOutPipDirection = r14;
                    this.val$initialSourceValue = r15;
                    this.val$initialContainerRect = r16;
                    this.val$lastEndRect = r17;
                    this.val$endValue = r18;
                    this.val$rotationDelta = r19;
                    this.val$sourceHintRectInsets = r20;
                    this.val$zeroInsets = r21;
                    this.val$direction = r22;
                }

                /* JADX WARNING: Removed duplicated region for block: B:54:0x0156  */
                /* JADX WARNING: Removed duplicated region for block: B:71:0x01e3  */
                /* JADX WARNING: Removed duplicated region for block: B:77:0x020c  */
                /* JADX WARNING: Removed duplicated region for block: B:81:0x0222  */
                /* JADX WARNING: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void applySurfaceControlTransaction(android.view.SurfaceControl r20, android.view.SurfaceControl.Transaction r21, float r22) {
                    /*
                        r19 = this;
                        r0 = r19
                        r12 = r20
                        r13 = r21
                        r1 = r22
                        T r2 = r0.mBaseValue
                        r7 = r2
                        android.graphics.Rect r7 = (android.graphics.Rect) r7
                        T r2 = r0.mStartValue
                        android.graphics.Rect r2 = (android.graphics.Rect) r2
                        java.lang.Object r3 = r19.getEndValue()
                        r4 = r3
                        android.graphics.Rect r4 = (android.graphics.Rect) r4
                        android.view.SurfaceControl r3 = r0.mContentOverlay
                        if (r3 == 0) goto L_0x002c
                        r5 = 1056964608(0x3f000000, float:0.5)
                        int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
                        if (r6 >= 0) goto L_0x0024
                        r5 = 0
                        goto L_0x0029
                    L_0x0024:
                        float r5 = r1 - r5
                        r6 = 1073741824(0x40000000, float:2.0)
                        float r5 = r5 * r6
                    L_0x0029:
                        r13.setAlpha(r3, r5)
                    L_0x002c:
                        android.graphics.Rect r3 = r0.val$rotatedEndRect
                        r5 = 1065353216(0x3f800000, float:1.0)
                        r8 = 0
                        r14 = 1
                        if (r3 == 0) goto L_0x0129
                        android.graphics.Rect r3 = r0.val$lastEndRect
                        boolean r3 = r4.equals(r3)
                        if (r3 != 0) goto L_0x0051
                        android.graphics.Rect r3 = r0.val$rotatedEndRect
                        android.graphics.Rect r6 = r0.val$endValue
                        r3.set(r6)
                        android.graphics.Rect r3 = r0.val$rotatedEndRect
                        android.graphics.Rect r6 = r0.val$initialSourceValue
                        int r7 = r0.val$rotationDelta
                        android.util.RotationUtils.rotateBounds(r3, r6, r7)
                        android.graphics.Rect r3 = r0.val$lastEndRect
                        r3.set(r4)
                    L_0x0051:
                        android.animation.RectEvaluator r3 = r0.mRectEvaluator
                        android.graphics.Rect r6 = r0.val$rotatedEndRect
                        android.graphics.Rect r15 = r3.evaluate(r1, r2, r6)
                        r0.mCurrentValue = r15
                        android.graphics.Rect r3 = r0.val$sourceHintRectInsets
                        if (r3 != 0) goto L_0x0063
                        android.graphics.Rect r3 = r0.val$zeroInsets
                    L_0x0061:
                        r6 = r3
                        goto L_0x0076
                    L_0x0063:
                        boolean r6 = r0.val$isOutPipDirection
                        if (r6 == 0) goto L_0x0069
                        r7 = r3
                        goto L_0x006b
                    L_0x0069:
                        android.graphics.Rect r7 = r0.val$zeroInsets
                    L_0x006b:
                        if (r6 == 0) goto L_0x006f
                        android.graphics.Rect r3 = r0.val$zeroInsets
                    L_0x006f:
                        android.animation.RectEvaluator r6 = r0.mInsetsEvaluator
                        android.graphics.Rect r3 = r6.evaluate(r1, r7, r3)
                        goto L_0x0061
                    L_0x0076:
                        boolean r3 = com.android.p012wm.shell.transition.Transitions.SHELL_TRANSITIONS_ROTATION
                        r7 = 1119092736(0x42b40000, float:90.0)
                        r9 = -1028390912(0xffffffffc2b40000, float:-90.0)
                        if (r3 == 0) goto L_0x00be
                        int r3 = r0.val$rotationDelta
                        if (r3 != r14) goto L_0x009f
                        float r5 = r5 - r1
                        float r7 = r7 * r5
                        int r3 = r4.left
                        int r9 = r2.left
                        int r3 = r3 - r9
                        float r3 = (float) r3
                        float r3 = r3 * r1
                        float r9 = (float) r9
                        float r3 = r3 + r9
                        int r9 = r2.width()
                        float r9 = (float) r9
                        float r9 = r9 * r5
                        float r9 = r9 + r3
                        int r3 = r4.top
                        int r2 = r2.top
                        int r3 = r3 - r2
                        float r3 = (float) r3
                        float r1 = r1 * r3
                        float r2 = (float) r2
                        float r1 = r1 + r2
                        r10 = r1
                        goto L_0x00e9
                    L_0x009f:
                        float r5 = r5 - r1
                        float r9 = r9 * r5
                        int r3 = r4.left
                        int r7 = r2.left
                        int r3 = r3 - r7
                        float r3 = (float) r3
                        float r3 = r3 * r1
                        float r7 = (float) r7
                        float r3 = r3 + r7
                        int r4 = r4.top
                        int r7 = r2.top
                        int r4 = r4 - r7
                        float r4 = (float) r4
                        float r1 = r1 * r4
                        float r4 = (float) r7
                        float r1 = r1 + r4
                        int r2 = r2.height()
                        float r2 = (float) r2
                        float r2 = r2 * r5
                        float r2 = r2 + r1
                        r10 = r2
                        r7 = r9
                        r9 = r3
                        goto L_0x00e9
                    L_0x00be:
                        int r3 = r0.val$rotationDelta
                        if (r3 != r14) goto L_0x00d2
                        float r3 = r1 * r7
                        int r5 = r4.right
                        int r7 = r2.left
                        int r5 = r5 - r7
                        float r5 = (float) r5
                        float r5 = r5 * r1
                        float r7 = (float) r7
                        float r5 = r5 + r7
                        int r4 = r4.top
                        int r2 = r2.top
                        goto L_0x00e1
                    L_0x00d2:
                        float r3 = r1 * r9
                        int r5 = r4.left
                        int r7 = r2.left
                        int r5 = r5 - r7
                        float r5 = (float) r5
                        float r5 = r5 * r1
                        float r7 = (float) r7
                        float r5 = r5 + r7
                        int r4 = r4.bottom
                        int r2 = r2.top
                    L_0x00e1:
                        int r4 = r4 - r2
                        float r4 = (float) r4
                        float r1 = r1 * r4
                        float r2 = (float) r2
                        float r1 = r1 + r2
                        r10 = r1
                        r7 = r3
                        r9 = r5
                    L_0x00e9:
                        android.graphics.Rect r11 = new android.graphics.Rect
                        android.graphics.Rect r1 = r0.val$initialContainerRect
                        r11.<init>(r1)
                        r11.inset(r6)
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r1 = r0.mSurfaceTransactionHelper
                        android.graphics.Rect r4 = r0.val$initialContainerRect
                        boolean r5 = r0.val$isOutPipDirection
                        int r2 = r0.val$rotationDelta
                        r3 = 3
                        if (r2 != r3) goto L_0x0101
                        r16 = r14
                        goto L_0x0103
                    L_0x0101:
                        r16 = r8
                    L_0x0103:
                        r2 = r21
                        r3 = r20
                        r17 = r5
                        r5 = r15
                        r8 = r9
                        r9 = r10
                        r10 = r17
                        r18 = r11
                        r11 = r16
                        r1.rotateAndScaleWithCrop(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                        int r1 = r0.mTransitionDirection
                        boolean r1 = com.android.p012wm.shell.pip.PipAnimationController.isOutPipDirection(r1)
                        r1 = r1 ^ r14
                        if (r1 == 0) goto L_0x0125
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r0 = r0.mSurfaceTransactionHelper
                        r1 = r18
                        r0.round(r13, r12, r1, r15)
                    L_0x0125:
                        r21.apply()
                        return
                    L_0x0129:
                        android.animation.RectEvaluator r3 = r0.mRectEvaluator
                        android.graphics.Rect r9 = r3.evaluate(r1, r2, r4)
                        float r5 = r5 - r1
                        float r2 = r0.val$startingAngle
                        float r6 = r5 * r2
                        r0.mCurrentValue = r9
                        int r2 = r0.mAnimationType
                        if (r2 == 0) goto L_0x013b
                        goto L_0x014d
                    L_0x013b:
                        int r2 = r19.getTransitionDirection()
                        boolean r3 = com.android.p012wm.shell.pip.PipAnimationController.isInPipDirection(r2)
                        if (r3 != 0) goto L_0x014d
                        boolean r2 = com.android.p012wm.shell.pip.PipAnimationController.isOutPipDirection(r2)
                        if (r2 != 0) goto L_0x014d
                        r2 = r14
                        goto L_0x014e
                    L_0x014d:
                        r2 = r8
                    L_0x014e:
                        if (r2 != 0) goto L_0x01e3
                        android.graphics.Rect r2 = r0.val$sourceHintRect
                        if (r2 != 0) goto L_0x0156
                        goto L_0x01e3
                    L_0x0156:
                        android.graphics.Rect r2 = r0.val$sourceHintRectInsets
                        if (r2 != 0) goto L_0x015d
                        android.graphics.Rect r1 = r0.val$zeroInsets
                        goto L_0x016f
                    L_0x015d:
                        boolean r3 = r0.val$isOutPipDirection
                        if (r3 == 0) goto L_0x0163
                        r4 = r2
                        goto L_0x0165
                    L_0x0163:
                        android.graphics.Rect r4 = r0.val$zeroInsets
                    L_0x0165:
                        if (r3 == 0) goto L_0x0169
                        android.graphics.Rect r2 = r0.val$zeroInsets
                    L_0x0169:
                        android.animation.RectEvaluator r3 = r0.mInsetsEvaluator
                        android.graphics.Rect r1 = r3.evaluate(r1, r4, r2)
                    L_0x016f:
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r2 = r0.mSurfaceTransactionHelper
                        android.graphics.Rect r3 = r0.val$initialSourceValue
                        java.util.Objects.requireNonNull(r2)
                        android.graphics.RectF r4 = r2.mTmpSourceRectF
                        r4.set(r3)
                        android.graphics.Rect r4 = r2.mTmpDestinationRect
                        r4.set(r3)
                        android.graphics.Rect r4 = r2.mTmpDestinationRect
                        r4.inset(r1)
                        int r4 = r3.width()
                        int r5 = r3.height()
                        if (r4 > r5) goto L_0x0199
                        int r4 = r9.width()
                        float r4 = (float) r4
                        int r3 = r3.width()
                        goto L_0x01a2
                    L_0x0199:
                        int r4 = r9.height()
                        float r4 = (float) r4
                        int r3 = r3.height()
                    L_0x01a2:
                        float r3 = (float) r3
                        float r4 = r4 / r3
                        int r3 = r9.left
                        float r3 = (float) r3
                        int r5 = r1.left
                        float r5 = (float) r5
                        float r5 = r5 * r4
                        float r3 = r3 - r5
                        int r5 = r9.top
                        float r5 = (float) r5
                        int r6 = r1.top
                        float r6 = (float) r6
                        float r6 = r6 * r4
                        float r5 = r5 - r6
                        android.graphics.Matrix r6 = r2.mTmpTransform
                        r6.setScale(r4, r4)
                        android.graphics.Matrix r4 = r2.mTmpTransform
                        float[] r6 = r2.mTmpFloat9
                        android.view.SurfaceControl$Transaction r4 = r13.setMatrix(r12, r4, r6)
                        android.graphics.Rect r2 = r2.mTmpDestinationRect
                        android.view.SurfaceControl$Transaction r2 = r4.setWindowCrop(r12, r2)
                        r2.setPosition(r12, r3, r5)
                        int r2 = r0.mTransitionDirection
                        boolean r2 = com.android.p012wm.shell.pip.PipAnimationController.isOutPipDirection(r2)
                        r2 = r2 ^ r14
                        if (r2 == 0) goto L_0x0208
                        android.graphics.Rect r2 = new android.graphics.Rect
                        android.graphics.Rect r3 = r0.val$initialContainerRect
                        r2.<init>(r3)
                        r2.inset(r1)
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r1 = r0.mSurfaceTransactionHelper
                        r1.round(r13, r12, r2, r9)
                        goto L_0x0208
                    L_0x01e3:
                        boolean r1 = r0.val$isOutPipDirection
                        if (r1 == 0) goto L_0x01f6
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r1 = r0.mSurfaceTransactionHelper
                        r1.crop(r13, r12, r4)
                        r6 = 0
                        r2 = r21
                        r3 = r20
                        r5 = r9
                        r1.scale(r2, r3, r4, r5, r6)
                        goto L_0x0208
                    L_0x01f6:
                        com.android.wm.shell.pip.PipSurfaceTransactionHelper r10 = r0.mSurfaceTransactionHelper
                        r10.crop(r13, r12, r7)
                        r1 = r10
                        r2 = r21
                        r3 = r20
                        r4 = r7
                        r5 = r9
                        r1.scale(r2, r3, r4, r5, r6)
                        r10.round(r13, r12, r7, r9)
                    L_0x0208:
                        com.android.wm.shell.pip.PipAnimationController$PipTransactionHandler r0 = r0.mPipTransactionHandler
                        if (r0 == 0) goto L_0x0220
                        com.android.wm.shell.pip.PipTaskOrganizer$2 r0 = (com.android.p012wm.shell.pip.PipTaskOrganizer.C18902) r0
                        com.android.wm.shell.pip.PipTaskOrganizer r1 = com.android.p012wm.shell.pip.PipTaskOrganizer.this
                        com.android.wm.shell.pip.PipMenuController r1 = r1.mPipMenuController
                        boolean r1 = r1.isMenuVisible()
                        if (r1 == 0) goto L_0x0220
                        com.android.wm.shell.pip.PipTaskOrganizer r0 = com.android.p012wm.shell.pip.PipTaskOrganizer.this
                        com.android.wm.shell.pip.PipMenuController r0 = r0.mPipMenuController
                        r0.movePipMenu(r12, r13, r9)
                        r8 = r14
                    L_0x0220:
                        if (r8 != 0) goto L_0x0225
                        r21.apply()
                    L_0x0225:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.PipAnimationController.PipTransitionAnimator.C18862.applySurfaceControlTransaction(android.view.SurfaceControl, android.view.SurfaceControl$Transaction, float):void");
                }

                public final void onEndTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
                    Rect rect = this.mDestinationBounds;
                    this.mSurfaceTransactionHelper.resetScale(transaction, surfaceControl, rect);
                    if (PipAnimationController.isOutPipDirection(i)) {
                        transaction.setMatrix(surfaceControl, 1.0f, 0.0f, 0.0f, 1.0f);
                        transaction.setPosition(surfaceControl, 0.0f, 0.0f);
                        transaction.setWindowCrop(surfaceControl, 0, 0);
                        return;
                    }
                    this.mSurfaceTransactionHelper.crop(transaction, surfaceControl, rect);
                }

                public final void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    Objects.requireNonNull(pipSurfaceTransactionHelper);
                    transaction.setAlpha(surfaceControl, 1.0f);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl, !PipAnimationController.isOutPipDirection(this.mTransitionDirection));
                    if (PipAnimationController.isInPipDirection(this.val$direction)) {
                        transaction.setWindowCrop(surfaceControl, (Rect) this.mStartValue);
                    }
                    transaction.show(surfaceControl);
                    transaction.apply();
                }

                public final void updateEndValue(Object obj) {
                    T t;
                    this.mEndValue = (Rect) obj;
                    T t2 = this.mStartValue;
                    if (t2 != null && (t = this.mCurrentValue) != null) {
                        ((Rect) t2).set((Rect) t);
                    }
                }
            };
        }

        public final SurfaceControl.Transaction newSurfaceControlTransaction() {
            SurfaceControl.Transaction transaction = this.mSurfaceControlTransactionFactory.getTransaction();
            transaction.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
            return transaction;
        }

        public final void onAnimationCancel(Animator animator) {
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationCancel(this);
            }
            this.mTransitionDirection = 0;
        }

        public final void onAnimationEnd(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            SurfaceControl.Transaction newSurfaceControlTransaction = newSurfaceControlTransaction();
            onEndTransaction(this.mLeash, newSurfaceControlTransaction, this.mTransitionDirection);
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationEnd(this.mTaskInfo, newSurfaceControlTransaction, this);
            }
            this.mTransitionDirection = 0;
        }

        public final void onAnimationStart(Animator animator) {
            this.mCurrentValue = this.mStartValue;
            onStartTransaction(this.mLeash, newSurfaceControlTransaction());
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationStart(this);
            }
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            applySurfaceControlTransaction(this.mLeash, newSurfaceControlTransaction(), valueAnimator.getAnimatedFraction());
        }

        public final void setDestinationBounds(Rect rect) {
            this.mDestinationBounds.set(rect);
            if (this.mAnimationType == 1) {
                onStartTransaction(this.mLeash, newSurfaceControlTransaction());
            }
        }

        /* JADX INFO: finally extract failed */
        public final PipTransitionAnimator<T> setUseContentOverlay(Context context) {
            SurfaceControl.Transaction newSurfaceControlTransaction = newSurfaceControlTransaction();
            SurfaceControl surfaceControl = this.mContentOverlay;
            if (surfaceControl != null) {
                newSurfaceControlTransaction.remove(surfaceControl);
                newSurfaceControlTransaction.apply();
            }
            SurfaceControl build = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("PipAnimation").setName("PipContentOverlay").setColorLayer().build();
            this.mContentOverlay = build;
            newSurfaceControlTransaction.show(build);
            newSurfaceControlTransaction.setLayer(this.mContentOverlay, Integer.MAX_VALUE);
            SurfaceControl surfaceControl2 = this.mContentOverlay;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16842801});
            try {
                int color = obtainStyledAttributes.getColor(0, 0);
                float[] fArr = {((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f};
                obtainStyledAttributes.recycle();
                newSurfaceControlTransaction.setColor(surfaceControl2, fArr);
                newSurfaceControlTransaction.setAlpha(this.mContentOverlay, 0.0f);
                newSurfaceControlTransaction.reparent(this.mContentOverlay, this.mLeash);
                newSurfaceControlTransaction.apply();
                return this;
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        }

        @VisibleForTesting
        public PipTransitionAnimator<T> setPipAnimationCallback(PipAnimationCallback pipAnimationCallback) {
            this.mPipAnimationCallback = pipAnimationCallback;
            return this;
        }

        @VisibleForTesting
        public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
            this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
        }

        public void updateEndValue(T t) {
            this.mEndValue = t;
        }

        @VisibleForTesting
        public int getAnimationType() {
            return this.mAnimationType;
        }

        @VisibleForTesting
        public T getEndValue() {
            return this.mEndValue;
        }

        @VisibleForTesting
        public int getTransitionDirection() {
            return this.mTransitionDirection;
        }
    }

    public static boolean isInPipDirection(int i) {
        return i == 2;
    }

    public static boolean isOutPipDirection(int i) {
        return i == 3 || i == 4;
    }

    @VisibleForTesting
    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, float f, float f2) {
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            int i = PipTransitionAnimator.$r8$clinit;
            PipTransitionAnimator.C18851 r1 = new PipTransitionAnimator<Float>(taskInfo, surfaceControl, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2)) {
                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f) {
                    float floatValue = (((Float) getEndValue()).floatValue() * f) + ((1.0f - f) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(floatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    Objects.requireNonNull(pipSurfaceTransactionHelper);
                    transaction.setAlpha(surfaceControl, floatValue);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl, !PipAnimationController.isOutPipDirection(this.mTransitionDirection));
                    transaction.apply();
                }

                public final void updateEndValue(Object obj) {
                    this.mEndValue = (Float) obj;
                    this.mStartValue = this.mCurrentValue;
                }

                public final void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
                    if (getTransitionDirection() != 5) {
                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                        pipSurfaceTransactionHelper.resetScale(transaction, surfaceControl, this.mDestinationBounds);
                        pipSurfaceTransactionHelper.crop(transaction, surfaceControl, this.mDestinationBounds);
                        pipSurfaceTransactionHelper.round(transaction, surfaceControl, !PipAnimationController.isOutPipDirection(this.mTransitionDirection));
                        transaction.show(surfaceControl);
                        transaction.apply();
                    }
                }
            };
            setupPipTransitionAnimator(r1);
            this.mCurrentAnimator = r1;
        } else {
            if (pipTransitionAnimator.getAnimationType() == 1) {
                PipTransitionAnimator pipTransitionAnimator2 = this.mCurrentAnimator;
                Objects.requireNonNull(pipTransitionAnimator2);
                if (Objects.equals(rect, pipTransitionAnimator2.mDestinationBounds) && this.mCurrentAnimator.isRunning()) {
                    this.mCurrentAnimator.updateEndValue(Float.valueOf(f2));
                }
            }
            this.mCurrentAnimator.cancel();
            PipTransitionAnimator.C18851 r12 = new PipTransitionAnimator<Float>(taskInfo, surfaceControl, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2)) {
                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f) {
                    float floatValue = (((Float) getEndValue()).floatValue() * f) + ((1.0f - f) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(floatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    Objects.requireNonNull(pipSurfaceTransactionHelper);
                    transaction.setAlpha(surfaceControl, floatValue);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl, !PipAnimationController.isOutPipDirection(this.mTransitionDirection));
                    transaction.apply();
                }

                public final void updateEndValue(Object obj) {
                    this.mEndValue = (Float) obj;
                    this.mStartValue = this.mCurrentValue;
                }

                public final void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
                    if (getTransitionDirection() != 5) {
                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                        pipSurfaceTransactionHelper.resetScale(transaction, surfaceControl, this.mDestinationBounds);
                        pipSurfaceTransactionHelper.crop(transaction, surfaceControl, this.mDestinationBounds);
                        pipSurfaceTransactionHelper.round(transaction, surfaceControl, !PipAnimationController.isOutPipDirection(this.mTransitionDirection));
                        transaction.show(surfaceControl);
                        transaction.apply();
                    }
                }
            };
            setupPipTransitionAnimator(r12);
            this.mCurrentAnimator = r12;
        }
        return this.mCurrentAnimator;
    }

    public final PipTransitionAnimator setupPipTransitionAnimator(PipTransitionAnimator pipTransitionAnimator) {
        pipTransitionAnimator.mSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipTransitionAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        pipTransitionAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        pipTransitionAnimator.setAnimationHandler(this.mSfAnimationHandlerThreadLocal.get());
        return pipTransitionAnimator;
    }

    public PipAnimationController(PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
    }

    @VisibleForTesting
    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2) {
        Rect rect5 = rect3;
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            PipTransitionAnimator.C18862 ofBounds = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect2, rect2, rect3, rect4, i, 0.0f, i2);
            setupPipTransitionAnimator(ofBounds);
            this.mCurrentAnimator = ofBounds;
        } else if (pipTransitionAnimator.getAnimationType() == 1 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
        } else if (this.mCurrentAnimator.getAnimationType() != 0 || !this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.cancel();
            PipTransitionAnimator.C18862 ofBounds2 = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2);
            setupPipTransitionAnimator(ofBounds2);
            this.mCurrentAnimator = ofBounds2;
        } else {
            this.mCurrentAnimator.setDestinationBounds(rect3);
            this.mCurrentAnimator.updateEndValue(new Rect(rect3));
        }
        return this.mCurrentAnimator;
    }
}
