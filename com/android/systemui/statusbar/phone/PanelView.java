package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.PanelViewController;
import java.util.Objects;

public abstract class PanelView extends FrameLayout {
    public OnConfigurationChangedListener mOnConfigurationChangedListener;
    public PanelViewController.TouchHandler mTouchHandler;

    public interface OnConfigurationChangedListener {
    }

    public PanelView(Context context) {
        super(context);
    }

    public PanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX WARNING: Removed duplicated region for block: B:137:0x02c1  */
    /* JADX WARNING: Removed duplicated region for block: B:169:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x020e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r13) {
        /*
            r12 = this;
            com.android.systemui.statusbar.phone.PanelViewController$TouchHandler r12 = r12.mTouchHandler
            com.android.systemui.statusbar.phone.NotificationPanelViewController$17 r12 = (com.android.systemui.statusbar.phone.NotificationPanelViewController.C148217) r12
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            boolean r1 = r0.mBlockTouches
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0355
            com.android.systemui.plugins.qs.QS r0 = r0.mQs
            boolean r0 = r0.disallowPanelTouches()
            if (r0 == 0) goto L_0x0019
            goto L_0x0355
        L_0x0019:
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.phone.NotificationPanelViewController.m239$$Nest$minitDownStates(r0, r13)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.phone.StatusBar r0 = r0.mStatusBar
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mBouncerShowing
            if (r0 == 0) goto L_0x002b
            goto L_0x0356
        L_0x002b:
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.CommandQueue r0 = r0.mCommandQueue
            boolean r0 = r0.panelsEnabled()
            if (r0 == 0) goto L_0x0063
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            android.view.View r0 = r0.mLongPressedView
            if (r0 == 0) goto L_0x0042
            r0 = r2
            goto L_0x0043
        L_0x0042:
            r0 = r3
        L_0x0043:
            if (r0 != 0) goto L_0x0063
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.phone.HeadsUpTouchHelper r0 = r0.mHeadsUpTouchHelper
            boolean r0 = r0.onInterceptTouchEvent(r13)
            if (r0 == 0) goto L_0x0063
            com.android.systemui.statusbar.phone.NotificationPanelViewController r13 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.internal.logging.MetricsLogger r13 = r13.mMetricsLogger
            java.lang.String r0 = "panel_open"
            r13.count(r0, r2)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r12 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.internal.logging.MetricsLogger r12 = r12.mMetricsLogger
            java.lang.String r13 = "panel_open_peek"
            r12.count(r13, r2)
            goto L_0x0356
        L_0x0063:
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            float r1 = r0.mDownX
            float r4 = r0.mDownY
            r5 = 0
            boolean r0 = r0.shouldQuickSettingsIntercept(r1, r4, r5)
            if (r0 != 0) goto L_0x007c
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            com.android.systemui.statusbar.PulseExpansionHandler r0 = r0.mPulseExpansionHandler
            boolean r0 = r0.onInterceptTouchEvent(r13)
            if (r0 == 0) goto L_0x007c
            goto L_0x0356
        L_0x007c:
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            boolean r0 = r0.isFullyCollapsed()
            r1 = 6
            r4 = 3
            r6 = 2
            if (r0 != 0) goto L_0x0195
            com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            java.util.Objects.requireNonNull(r0)
            int r7 = r0.mTrackingPointer
            int r7 = r13.findPointerIndex(r7)
            if (r7 >= 0) goto L_0x009b
            int r7 = r13.getPointerId(r3)
            r0.mTrackingPointer = r7
            r7 = r3
        L_0x009b:
            float r8 = r13.getX(r7)
            float r7 = r13.getY(r7)
            int r9 = r13.getActionMasked()
            if (r9 == 0) goto L_0x014e
            if (r9 == r2) goto L_0x0148
            if (r9 == r6) goto L_0x00dc
            if (r9 == r4) goto L_0x0148
            if (r9 == r1) goto L_0x00b3
            goto L_0x0190
        L_0x00b3:
            int r7 = r13.getActionIndex()
            int r7 = r13.getPointerId(r7)
            int r8 = r0.mTrackingPointer
            if (r8 != r7) goto L_0x0190
            int r8 = r13.getPointerId(r3)
            if (r8 == r7) goto L_0x00c7
            r7 = r3
            goto L_0x00c8
        L_0x00c7:
            r7 = r2
        L_0x00c8:
            int r8 = r13.getPointerId(r7)
            r0.mTrackingPointer = r8
            float r8 = r13.getX(r7)
            r0.mInitialTouchX = r8
            float r7 = r13.getY(r7)
            r0.mInitialTouchY = r7
            goto L_0x0190
        L_0x00dc:
            float r9 = r0.mInitialTouchY
            float r9 = r7 - r9
            r0.trackMovement(r13)
            boolean r10 = r0.mQsTracking
            if (r10 == 0) goto L_0x00f1
            float r7 = r0.mInitialHeightOnTouch
            float r9 = r9 + r7
            r0.setQsExpansion(r9)
            r0.trackMovement(r13)
            goto L_0x0146
        L_0x00f1:
            float r10 = r0.getTouchSlop(r13)
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 > 0) goto L_0x0106
            float r10 = r0.getTouchSlop(r13)
            float r10 = -r10
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 >= 0) goto L_0x0190
            boolean r10 = r0.mQsExpanded
            if (r10 == 0) goto L_0x0190
        L_0x0106:
            float r10 = java.lang.Math.abs(r9)
            float r11 = r0.mInitialTouchX
            float r11 = r8 - r11
            float r11 = java.lang.Math.abs(r11)
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 <= 0) goto L_0x0190
            float r10 = r0.mInitialTouchX
            float r11 = r0.mInitialTouchY
            boolean r9 = r0.shouldQuickSettingsIntercept(r10, r11, r9)
            if (r9 == 0) goto L_0x0190
            com.android.systemui.statusbar.phone.NotificationPanelView r9 = r0.mView
            android.view.ViewParent r9 = r9.getParent()
            r9.requestDisallowInterceptTouchEvent(r2)
            r0.mQsTracking = r2
            r0.traceQsJank(r2, r3)
            r0.onQsExpansionStarted()
            r0.notifyExpandingFinished()
            float r9 = r0.mQsExpansionHeight
            r0.mInitialHeightOnTouch = r9
            r0.mInitialTouchY = r7
            r0.mInitialTouchX = r8
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            r0.cancelLongPress()
        L_0x0146:
            r0 = r2
            goto L_0x0191
        L_0x0148:
            r0.trackMovement(r13)
            r0.mQsTracking = r3
            goto L_0x0190
        L_0x014e:
            r0.mInitialTouchY = r7
            r0.mInitialTouchX = r8
            android.view.VelocityTracker r7 = r0.mQsVelocityTracker
            if (r7 == 0) goto L_0x0159
            r7.recycle()
        L_0x0159:
            android.view.VelocityTracker r7 = android.view.VelocityTracker.obtain()
            r0.mQsVelocityTracker = r7
            r0.trackMovement(r13)
            boolean r7 = r0.mKeyguardShowing
            if (r7 == 0) goto L_0x0179
            float r7 = r0.mInitialTouchX
            float r8 = r0.mInitialTouchY
            boolean r7 = r0.shouldQuickSettingsIntercept(r7, r8, r5)
            if (r7 == 0) goto L_0x0179
            com.android.systemui.statusbar.phone.NotificationPanelView r7 = r0.mView
            android.view.ViewParent r7 = r7.getParent()
            r7.requestDisallowInterceptTouchEvent(r2)
        L_0x0179:
            android.animation.ValueAnimator r7 = r0.mQsExpansionAnimator
            if (r7 == 0) goto L_0x0190
            float r7 = r0.mQsExpansionHeight
            r0.mInitialHeightOnTouch = r7
            r0.mQsTracking = r2
            r0.traceQsJank(r2, r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r0.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            r0.cancelLongPress()
        L_0x0190:
            r0 = r3
        L_0x0191:
            if (r0 == 0) goto L_0x0195
            goto L_0x0356
        L_0x0195:
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            boolean r7 = r0.mInstantExpanding
            if (r7 != 0) goto L_0x0355
            boolean r7 = r0.mNotificationsDragEnabled
            if (r7 == 0) goto L_0x0355
            boolean r7 = r0.mTouchDisabled
            if (r7 != 0) goto L_0x0355
            boolean r0 = r0.mMotionAborted
            if (r0 == 0) goto L_0x01af
            int r0 = r13.getActionMasked()
            if (r0 == 0) goto L_0x01af
            goto L_0x0355
        L_0x01af:
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            int r0 = r0.mTrackingPointer
            int r0 = r13.findPointerIndex(r0)
            if (r0 >= 0) goto L_0x01c2
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            int r7 = r13.getPointerId(r3)
            r0.mTrackingPointer = r7
            r0 = r3
        L_0x01c2:
            float r7 = r13.getX(r0)
            float r0 = r13.getY(r0)
            com.android.systemui.statusbar.phone.PanelViewController r8 = com.android.systemui.statusbar.phone.PanelViewController.this
            com.android.systemui.statusbar.phone.NotificationPanelViewController r8 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r8
            java.util.Objects.requireNonNull(r8)
            boolean r9 = r8.mQsExpanded
            if (r9 != 0) goto L_0x01da
            int r9 = r8.mBarState
            if (r9 != r2) goto L_0x01da
            goto L_0x0205
        L_0x01da:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = r8.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = r9.mView
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7 r9 = r9.mScrollAdapter
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r10 = r9.mOwnScrollY
            int r9 = r9.getScrollRange()
            if (r10 < r9) goto L_0x01f5
            r9 = r2
            goto L_0x01f6
        L_0x01f5:
            r9 = r3
        L_0x01f6:
            if (r9 == 0) goto L_0x01f9
            goto L_0x0205
        L_0x01f9:
            boolean r9 = r8.mShouldUseSplitNotificationShade
            if (r9 != 0) goto L_0x0207
            boolean r9 = r8.mQsExpanded
            if (r9 != 0) goto L_0x0205
            boolean r8 = r8.mIsPanelCollapseOnQQS
            if (r8 == 0) goto L_0x0207
        L_0x0205:
            r8 = r2
            goto L_0x0208
        L_0x0207:
            r8 = r3
        L_0x0208:
            int r9 = r13.getActionMasked()
            if (r9 == 0) goto L_0x02c1
            if (r9 == r2) goto L_0x02b8
            if (r9 == r6) goto L_0x025f
            if (r9 == r4) goto L_0x02b8
            r0 = 5
            if (r9 == r0) goto L_0x024a
            if (r9 == r1) goto L_0x021b
            goto L_0x0355
        L_0x021b:
            int r0 = r13.getActionIndex()
            int r0 = r13.getPointerId(r0)
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            int r1 = r1.mTrackingPointer
            if (r1 != r0) goto L_0x0355
            int r1 = r13.getPointerId(r3)
            if (r1 == r0) goto L_0x0230
            r2 = r3
        L_0x0230:
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            int r1 = r13.getPointerId(r2)
            r0.mTrackingPointer = r1
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r1 = r13.getX(r2)
            r0.mInitialTouchX = r1
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r13 = r13.getY(r2)
            r12.mInitialTouchY = r13
            goto L_0x0355
        L_0x024a:
            com.android.systemui.statusbar.phone.PanelViewController r13 = com.android.systemui.statusbar.phone.PanelViewController.this
            com.android.systemui.statusbar.SysuiStatusBarStateController r13 = r13.mStatusBarStateController
            int r13 = r13.getState()
            if (r13 != r2) goto L_0x0355
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            r12.mMotionAborted = r2
            android.view.VelocityTracker r12 = r12.mVelocityTracker
            r12.clear()
            goto L_0x0355
        L_0x025f:
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r4 = r1.mInitialTouchY
            float r4 = r0 - r4
            com.android.systemui.statusbar.phone.PanelViewController.m244$$Nest$maddMovement(r1, r13)
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            boolean r5 = r1.mPanelClosedOnDown
            if (r5 == 0) goto L_0x0274
            boolean r5 = r1.mCollapsedAndHeadsUpOnDown
            if (r5 != 0) goto L_0x0274
            r5 = r2
            goto L_0x0275
        L_0x0274:
            r5 = r3
        L_0x0275:
            if (r8 != 0) goto L_0x0281
            boolean r6 = r1.mTouchStartedInEmptyArea
            if (r6 != 0) goto L_0x0281
            boolean r1 = r1.mAnimatingOnDown
            if (r1 != 0) goto L_0x0281
            if (r5 == 0) goto L_0x0355
        L_0x0281:
            float r1 = java.lang.Math.abs(r4)
            com.android.systemui.statusbar.phone.PanelViewController r6 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r13 = r6.getTouchSlop(r13)
            float r6 = -r13
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x029c
            if (r5 != 0) goto L_0x0298
            com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
            boolean r4 = r4.mAnimatingOnDown
            if (r4 == 0) goto L_0x0355
        L_0x0298:
            int r13 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r13 <= 0) goto L_0x0355
        L_0x029c:
            com.android.systemui.statusbar.phone.PanelViewController r13 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r13 = r13.mInitialTouchX
            float r13 = r7 - r13
            float r13 = java.lang.Math.abs(r13)
            int r13 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r13 <= 0) goto L_0x0355
            com.android.systemui.statusbar.phone.PanelViewController r13 = com.android.systemui.statusbar.phone.PanelViewController.this
            r13.cancelHeightAnimator()
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            float r13 = r12.mExpandedHeight
            r12.startExpandMotion(r7, r0, r2, r13)
            goto L_0x0356
        L_0x02b8:
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            android.view.VelocityTracker r12 = r12.mVelocityTracker
            r12.clear()
            goto L_0x0355
        L_0x02c1:
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            com.android.systemui.statusbar.phone.StatusBar r1 = r1.mStatusBar
            java.util.Objects.requireNonNull(r1)
            int r4 = r1.mState
            if (r4 != r2) goto L_0x02d1
            com.android.keyguard.ViewMediatorCallback r1 = r1.mKeyguardViewMediatorCallback
            r1.userActivity()
        L_0x02d1:
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            android.animation.ValueAnimator r4 = r1.mHeightAnimator
            if (r4 == 0) goto L_0x02dd
            boolean r4 = r1.mIsSpringBackAnimation
            if (r4 != 0) goto L_0x02dd
            r4 = r2
            goto L_0x02de
        L_0x02dd:
            r4 = r3
        L_0x02de:
            r1.mAnimatingOnDown = r4
            r1.mMinExpandHeight = r5
            long r4 = android.os.SystemClock.uptimeMillis()
            r1.mDownTime = r4
            com.android.systemui.statusbar.phone.PanelViewController r1 = com.android.systemui.statusbar.phone.PanelViewController.this
            boolean r4 = r1.mAnimatingOnDown
            if (r4 == 0) goto L_0x02fe
            boolean r4 = r1.mClosing
            if (r4 == 0) goto L_0x02fe
            boolean r4 = r1.mHintAnimationRunning
            if (r4 != 0) goto L_0x02fe
            r1.cancelHeightAnimator()
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            r12.mTouchSlopExceeded = r2
            goto L_0x0356
        L_0x02fe:
            r1.mInitialTouchY = r0
            r1.mInitialTouchX = r7
            r4 = r1
            com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r4
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = r4.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
            float r5 = r5.getX()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = r4.mNotificationStackScrollLayoutController
            float r8 = r7 - r5
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
            boolean r0 = r6.isBelowLastNotification(r8, r0)
            if (r0 != 0) goto L_0x0336
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0336
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r4.mNotificationStackScrollLayoutController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
            int r0 = r0.getWidth()
            float r0 = (float) r0
            float r0 = r0 + r5
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0336
            r0 = r2
            goto L_0x0337
        L_0x0336:
            r0 = r3
        L_0x0337:
            r0 = r0 ^ r2
            r1.mTouchStartedInEmptyArea = r0
            com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
            boolean r1 = r0.mTouchSlopExceededBeforeDown
            r0.mTouchSlopExceeded = r1
            r0.mMotionAborted = r3
            boolean r1 = r0.isFullyCollapsed()
            r0.mPanelClosedOnDown = r1
            com.android.systemui.statusbar.phone.PanelViewController r12 = com.android.systemui.statusbar.phone.PanelViewController.this
            r12.mCollapsedAndHeadsUpOnDown = r3
            r12.mHasLayoutedSinceDown = r3
            r12.mUpdateFlingOnLayout = r3
            r12.mTouchAboveFalsingThreshold = r3
            com.android.systemui.statusbar.phone.PanelViewController.m244$$Nest$maddMovement(r12, r13)
        L_0x0355:
            r2 = r3
        L_0x0356:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PanelView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public PanelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void dispatchConfigurationChanged(Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        NotificationPanelViewController.OnConfigurationChangedListener onConfigurationChangedListener = (NotificationPanelViewController.OnConfigurationChangedListener) this.mOnConfigurationChangedListener;
        Objects.requireNonNull(onConfigurationChangedListener);
        PanelViewController.this.loadDimens();
        KeyguardAffordanceHelper keyguardAffordanceHelper = NotificationPanelViewController.this.mAffordanceHelper;
        Objects.requireNonNull(keyguardAffordanceHelper);
        keyguardAffordanceHelper.initDimens();
        keyguardAffordanceHelper.initIcons();
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        int i = configuration.orientation;
        Objects.requireNonNull(notificationPanelViewController);
    }

    public final void setOnTouchListener(NotificationPanelViewController.C148217 r1) {
        super.setOnTouchListener(r1);
        this.mTouchHandler = r1;
    }

    public PanelView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    static {
        Class<PanelView> cls = PanelView.class;
    }
}
