package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.common.DisplayChangeController;

/* renamed from: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda2 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda2(PipController pipController) {
        this.f$0 = pipController;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onRotateDisplay(int r18, int r19, int r20, android.window.WindowContainerTransaction r21) {
        /*
            r17 = this;
            r0 = r20
            r1 = r17
            com.android.wm.shell.pip.phone.PipController r6 = r1.f$0
            java.util.Objects.requireNonNull(r6)
            com.android.wm.shell.pip.PipBoundsState r1 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.common.DisplayLayout r1 = r1.mDisplayLayout
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mRotation
            if (r1 != r0) goto L_0x0023
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r0 = r6
            r5 = r21
            r0.updateMovementBounds(r1, r2, r3, r4, r5)
            goto L_0x01ad
        L_0x0023:
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r6.mPipTaskOrganizer
            boolean r1 = r1.isInPip()
            r7 = 1
            r8 = 0
            r9 = 2
            if (r1 == 0) goto L_0x016d
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r6.mPipTaskOrganizer
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.pip.PipTransitionState r1 = r1.mPipTransitionState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mState
            if (r1 != r9) goto L_0x003e
            r1 = r7
            goto L_0x003f
        L_0x003e:
            r1 = r8
        L_0x003f:
            if (r1 == 0) goto L_0x0043
            goto L_0x016d
        L_0x0043:
            com.android.wm.shell.pip.PipTaskOrganizer r1 = r6.mPipTaskOrganizer
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.pip.PipAnimationController r2 = r1.mPipAnimationController
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r2 = r2.mCurrentAnimator
            if (r2 == 0) goto L_0x005f
            boolean r3 = r2.isRunning()
            if (r3 == 0) goto L_0x005f
            android.graphics.Rect r1 = new android.graphics.Rect
            android.graphics.Rect r2 = r2.mDestinationBounds
            r1.<init>(r2)
            goto L_0x0065
        L_0x005f:
            com.android.wm.shell.pip.PipBoundsState r1 = r1.mPipBoundsState
            android.graphics.Rect r1 = r1.getBounds()
        L_0x0065:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            android.content.Context r3 = r6.mContext
            android.graphics.Rect r4 = r6.mTmpInsetBounds
            com.android.wm.shell.pip.PipBoundsState r5 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mDisplayId
            r10 = r18
            if (r10 != r5) goto L_0x0102
            r5 = r19
            if (r5 != r0) goto L_0x007f
            goto L_0x0102
        L_0x007f:
            android.app.IActivityTaskManager r5 = android.app.ActivityTaskManager.getService()     // Catch:{ RemoteException -> 0x00f7 }
            android.app.ActivityTaskManager$RootTaskInfo r5 = r5.getRootTaskInfo(r9, r8)     // Catch:{ RemoteException -> 0x00f7 }
            if (r5 != 0) goto L_0x008b
            goto L_0x0102
        L_0x008b:
            com.android.wm.shell.pip.PipBoundsAlgorithm r9 = r6.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r9)
            com.android.wm.shell.pip.PipSnapAlgorithm r9 = r9.mSnapAlgorithm
            android.graphics.Rect r15 = new android.graphics.Rect
            r15.<init>(r1)
            com.android.wm.shell.pip.PipBoundsAlgorithm r1 = r6.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r1 = r1.getMovementBounds(r15, r7)
            com.android.wm.shell.pip.PipBoundsState r10 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.mStashedState
            float r12 = r9.getSnapFraction(r15, r1, r10)
            com.android.wm.shell.pip.PipBoundsState r1 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.common.DisplayLayout r1 = r1.mDisplayLayout
            android.content.res.Resources r3 = r3.getResources()
            r1.rotateTo(r3, r0)
            com.android.wm.shell.pip.PipBoundsAlgorithm r0 = r6.mPipBoundsAlgorithm
            android.graphics.Rect r11 = r0.getMovementBounds(r15, r8)
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r0)
            int r13 = r0.mStashedState
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r0)
            int r14 = r0.mStashOffset
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            android.graphics.Rect r0 = r0.getDisplayBounds()
            com.android.wm.shell.pip.PipBoundsState r1 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.common.DisplayLayout r1 = r1.mDisplayLayout
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r1 = r1.mStableInsets
            r10 = r15
            r3 = r15
            r15 = r0
            r16 = r1
            com.android.p012wm.shell.pip.PipSnapAlgorithm.applySnapFraction(r10, r11, r12, r13, r14, r15, r16)
            com.android.wm.shell.pip.PipBoundsAlgorithm r0 = r6.mPipBoundsAlgorithm
            r0.getInsetBounds(r4)
            r2.set(r3)
            android.window.WindowContainerToken r0 = r5.token
            r5 = r21
            r5.setBounds(r0, r2)
            goto L_0x0105
        L_0x00f7:
            r0 = move-exception
            r5 = r21
            java.lang.String r1 = "PipController"
            java.lang.String r3 = "Failed to get RootTaskInfo for pinned task"
            android.util.Log.e(r1, r3, r0)
            goto L_0x0104
        L_0x0102:
            r5 = r21
        L_0x0104:
            r7 = r8
        L_0x0105:
            if (r7 == 0) goto L_0x01ad
            com.android.wm.shell.pip.phone.PipTouchHandler r0 = r6.mTouchHandler
            com.android.wm.shell.pip.PipBoundsState r1 = r6.mPipBoundsState
            android.graphics.Rect r1 = r1.getBounds()
            android.graphics.Rect r3 = r6.mTmpInsetBounds
            java.util.Objects.requireNonNull(r0)
            android.graphics.Rect r4 = new android.graphics.Rect
            r4.<init>()
            com.android.wm.shell.pip.PipBoundsAlgorithm r7 = r0.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r7)
            com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r2, r3, r4, r8)
            com.android.wm.shell.pip.PipBoundsState r3 = r0.mPipBoundsState
            java.util.Objects.requireNonNull(r3)
            android.graphics.Rect r3 = r3.mMovementBounds
            int r3 = r3.bottom
            int r7 = r0.mMovementBoundsExtraOffsets
            int r3 = r3 - r7
            int r0 = r0.mBottomOffsetBufferPx
            int r3 = r3 - r0
            int r0 = r1.top
            if (r3 > r0) goto L_0x013b
            int r0 = r2.left
            int r1 = r4.bottom
            r2.offsetTo(r0, r1)
        L_0x013b:
            boolean r0 = r6.mIsInFixedRotation
            if (r0 != 0) goto L_0x015f
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            r0.setShelfVisibility(r8, r8, r8)
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r0)
            r0.mIsImeShowing = r8
            r0.mImeHeight = r8
            com.android.wm.shell.pip.phone.PipTouchHandler r0 = r6.mTouchHandler
            java.util.Objects.requireNonNull(r0)
            r0.mIsShelfShowing = r8
            r0.mShelfHeight = r8
            com.android.wm.shell.pip.phone.PipTouchHandler r0 = r6.mTouchHandler
            java.util.Objects.requireNonNull(r0)
            r0.mIsImeShowing = r8
            r0.mImeHeight = r8
        L_0x015f:
            r3 = 1
            r4 = 0
            r7 = 0
            r0 = r6
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r7
            r5 = r21
            r0.updateMovementBounds(r1, r2, r3, r4, r5)
            goto L_0x01ad
        L_0x016d:
            r5 = r21
            android.content.Context r1 = r6.mContext
            com.android.wm.shell.pip.PipBoundsState r2 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.common.DisplayLayout r2 = r2.mDisplayLayout
            android.content.res.Resources r1 = r1.getResources()
            r2.rotateTo(r1, r0)
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            java.util.Objects.requireNonNull(r0)
            android.graphics.Rect r1 = r0.mNormalBounds
            r2 = 1
            r3 = 0
            r4 = 0
            r0 = r6
            r5 = r21
            r0.updateMovementBounds(r1, r2, r3, r4, r5)
            com.android.wm.shell.pip.PipTaskOrganizer r0 = r6.mPipTaskOrganizer
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.pip.PipTransitionState r1 = r0.mPipTransitionState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mState
            if (r1 != r9) goto L_0x019e
            goto L_0x019f
        L_0x019e:
            r7 = r8
        L_0x019f:
            if (r7 == 0) goto L_0x01ad
            com.android.wm.shell.pip.PipBoundsAlgorithm r1 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r1 = r1.getEntryDestinationBounds()
            int r2 = r0.mEnterAnimationDuration
            long r2 = (long) r2
            r0.enterPipWithAlphaAnimation(r1, r2)
        L_0x01ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda2.onRotateDisplay(int, int, int, android.window.WindowContainerTransaction):void");
    }
}
