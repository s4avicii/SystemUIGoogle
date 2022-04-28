package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.pip.phone.PipInputConsumer;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* renamed from: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda1 implements ExpandableNotificationRow.ExpansionLogger, Bubbles.SuppressionChangedListener, PipInputConsumer.InputListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda1(Object obj) {
        this.f$0 = obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0087, code lost:
        if (r0.mIsUserInteracting != false) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0136, code lost:
        if (r6.mGesture.onUp(r6.mTouchState) != false) goto L_0x015a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInputEvent(android.view.InputEvent r7) {
        /*
            r6 = this;
            java.lang.Object r6 = r6.f$0
            com.android.wm.shell.pip.phone.PipTouchHandler r6 = (com.android.p012wm.shell.pip.phone.PipTouchHandler) r6
            java.util.Objects.requireNonNull(r6)
            boolean r0 = r7 instanceof android.view.MotionEvent
            if (r0 != 0) goto L_0x000d
            goto L_0x01a9
        L_0x000d:
            android.view.MotionEvent r7 = (android.view.MotionEvent) r7
            com.android.wm.shell.pip.PipBoundsState r0 = r6.mPipBoundsState
            boolean r0 = r0.isStashed()
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L_0x0060
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r0 = r6.mPipResizeGestureHandler
            java.util.Objects.requireNonNull(r0)
            boolean r4 = r0.mIsSysUiStateValid
            if (r4 == 0) goto L_0x0051
            int r4 = r7.getActionMasked()
            if (r4 == 0) goto L_0x003f
            r5 = 5
            if (r4 == r5) goto L_0x002d
            goto L_0x0051
        L_0x002d:
            boolean r4 = r0.mEnablePinchResize
            if (r4 == 0) goto L_0x0051
            int r4 = r7.getPointerCount()
            if (r4 != r1) goto L_0x0051
            r0.onPinchResize(r7)
            boolean r4 = r0.mAllowGesture
            r0.mOngoingPinchToResize = r4
            goto L_0x0052
        L_0x003f:
            float r4 = r7.getRawX()
            int r4 = (int) r4
            float r5 = r7.getRawY()
            int r5 = (int) r5
            boolean r0 = r0.isWithinDragResizeRegion(r4, r5)
            if (r0 == 0) goto L_0x0051
            r4 = r2
            goto L_0x0052
        L_0x0051:
            r4 = r3
        L_0x0052:
            if (r4 == 0) goto L_0x0060
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            r0.onTouchEvent(r7)
            com.android.wm.shell.pip.phone.PipTouchState r6 = r6.mTouchState
            r6.reset()
            goto L_0x01a9
        L_0x0060:
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r0 = r6.mPipResizeGestureHandler
            java.util.Objects.requireNonNull(r0)
            int r4 = r0.mCtrlType
            if (r4 != 0) goto L_0x0070
            boolean r0 = r0.mOngoingPinchToResize
            if (r0 == 0) goto L_0x006e
            goto L_0x0070
        L_0x006e:
            r0 = r3
            goto L_0x0071
        L_0x0070:
            r0 = r2
        L_0x0071:
            if (r0 == 0) goto L_0x007a
            com.android.wm.shell.pip.phone.PipDismissTargetHandler r6 = r6.mPipDismissTargetHandler
            r6.hideDismissTargetMaybe()
            goto L_0x01a9
        L_0x007a:
            int r0 = r7.getAction()
            if (r0 == 0) goto L_0x0089
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsUserInteracting
            if (r0 == 0) goto L_0x00a8
        L_0x0089:
            com.android.wm.shell.pip.phone.PipDismissTargetHandler r0 = r6.mPipDismissTargetHandler
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.pip.phone.PipMotionHelper$3 r0 = r0.mMagnetizedPip
            boolean r0 = r0.maybeConsumeMotionEvent(r7)
            if (r0 == 0) goto L_0x00a8
            int r0 = r7.getAction()
            if (r0 != 0) goto L_0x00a1
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            r0.onTouchEvent(r7)
        L_0x00a1:
            com.android.wm.shell.pip.phone.PipTouchState r6 = r6.mTouchState
            r6.addMovementToVelocityTracker(r7)
            goto L_0x01a9
        L_0x00a8:
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            r0.onTouchEvent(r7)
            int r0 = r6.mMenuState
            if (r0 == 0) goto L_0x00b3
            r0 = r2
            goto L_0x00b4
        L_0x00b3:
            r0 = r3
        L_0x00b4:
            int r4 = r7.getAction()
            r5 = 3
            if (r4 == 0) goto L_0x0153
            if (r4 == r2) goto L_0x012b
            if (r4 == r1) goto L_0x0117
            if (r4 == r5) goto L_0x0139
            r1 = 7
            if (r4 == r1) goto L_0x0109
            r1 = 9
            if (r4 == r1) goto L_0x00ea
            r1 = 10
            if (r4 == r1) goto L_0x00ce
            goto L_0x015a
        L_0x00ce:
            android.view.accessibility.AccessibilityManager r1 = r6.mAccessibilityManager
            boolean r1 = r1.isTouchExplorationEnabled()
            if (r1 != 0) goto L_0x00db
            com.android.wm.shell.pip.phone.PipTouchState r1 = r6.mTouchState
            r1.scheduleHoverExitTimeoutCallback()
        L_0x00db:
            if (r0 != 0) goto L_0x015a
            boolean r1 = r6.mSendingHoverAccessibilityEvents
            if (r1 == 0) goto L_0x015a
            r1 = 256(0x100, float:3.59E-43)
            r6.sendAccessibilityHoverEvent(r1)
            r6.mSendingHoverAccessibilityEvents = r3
            goto L_0x015a
        L_0x00ea:
            android.view.accessibility.AccessibilityManager r1 = r6.mAccessibilityManager
            boolean r1 = r1.isTouchExplorationEnabled()
            if (r1 != 0) goto L_0x0109
            com.android.wm.shell.pip.phone.PipTouchState r1 = r6.mTouchState
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.common.ShellExecutor r4 = r1.mMainExecutor
            java.lang.Runnable r1 = r1.mHoverExitTimeoutCallback
            r4.removeCallbacks(r1)
            com.android.wm.shell.pip.phone.PhonePipMenuController r1 = r6.mMenuController
            com.android.wm.shell.pip.PipBoundsState r4 = r6.mPipBoundsState
            android.graphics.Rect r4 = r4.getBounds()
            r1.showMenu(r2, r4, r3, r3)
        L_0x0109:
            if (r0 != 0) goto L_0x015a
            boolean r1 = r6.mSendingHoverAccessibilityEvents
            if (r1 != 0) goto L_0x015a
            r1 = 128(0x80, float:1.794E-43)
            r6.sendAccessibilityHoverEvent(r1)
            r6.mSendingHoverAccessibilityEvents = r2
            goto L_0x015a
        L_0x0117:
            com.android.wm.shell.pip.phone.PipTouchHandler$DefaultPipTouchGesture r1 = r6.mGesture
            com.android.wm.shell.pip.phone.PipTouchState r3 = r6.mTouchState
            boolean r1 = r1.onMove(r3)
            if (r1 == 0) goto L_0x0122
            goto L_0x015a
        L_0x0122:
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsDragging
            r0 = r0 ^ r2
            goto L_0x015a
        L_0x012b:
            r6.updateMovementBounds()
            com.android.wm.shell.pip.phone.PipTouchHandler$DefaultPipTouchGesture r1 = r6.mGesture
            com.android.wm.shell.pip.phone.PipTouchState r4 = r6.mTouchState
            boolean r1 = r1.onUp(r4)
            if (r1 == 0) goto L_0x0139
            goto L_0x015a
        L_0x0139:
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mStartedDragging
            if (r0 != 0) goto L_0x014c
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsDragging
            if (r0 != 0) goto L_0x014c
            r3 = r2
        L_0x014c:
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            r0.reset()
            r0 = r3
            goto L_0x015a
        L_0x0153:
            com.android.wm.shell.pip.phone.PipTouchHandler$DefaultPipTouchGesture r1 = r6.mGesture
            com.android.wm.shell.pip.phone.PipTouchState r3 = r6.mTouchState
            r1.onDown(r3)
        L_0x015a:
            com.android.wm.shell.pip.PipBoundsState r1 = r6.mPipBoundsState
            boolean r1 = r1.isStashed()
            r1 = r1 ^ r2
            r0 = r0 & r1
            if (r0 == 0) goto L_0x01a9
            android.view.MotionEvent r7 = android.view.MotionEvent.obtain(r7)
            com.android.wm.shell.pip.phone.PipTouchState r0 = r6.mTouchState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mStartedDragging
            if (r0 == 0) goto L_0x018b
            r7.setAction(r5)
            com.android.wm.shell.pip.phone.PhonePipMenuController r0 = r6.mMenuController
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.isMenuVisible()
            if (r1 == 0) goto L_0x018b
            com.android.wm.shell.pip.phone.PipMenuView r0 = r0.mPipMenuView
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.common.ShellExecutor r1 = r0.mMainExecutor
            com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0 r0 = r0.mHideMenuRunnable
            r1.removeCallbacks(r0)
        L_0x018b:
            com.android.wm.shell.pip.phone.PhonePipMenuController r6 = r6.mMenuController
            java.util.Objects.requireNonNull(r6)
            com.android.wm.shell.pip.phone.PipMenuView r0 = r6.mPipMenuView
            if (r0 != 0) goto L_0x0195
            goto L_0x01a6
        L_0x0195:
            boolean r0 = r7.isTouchEvent()
            if (r0 == 0) goto L_0x01a1
            com.android.wm.shell.pip.phone.PipMenuView r6 = r6.mPipMenuView
            r6.dispatchTouchEvent(r7)
            goto L_0x01a6
        L_0x01a1:
            com.android.wm.shell.pip.phone.PipMenuView r6 = r6.mPipMenuView
            r6.dispatchGenericMotionEvent(r7)
        L_0x01a6:
            r7.recycle()
        L_0x01a9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda1.onInputEvent(android.view.InputEvent):void");
    }
}
