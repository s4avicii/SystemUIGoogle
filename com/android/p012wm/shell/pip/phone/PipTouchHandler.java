package com.android.p012wm.shell.pip.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import androidx.transition.TransitionPropagation;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda2;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipTouchHandler */
public final class PipTouchHandler {
    public final AccessibilityManager mAccessibilityManager;
    public int mBottomOffsetBufferPx;
    public final PipAccessibilityInteractionConnection mConnection;
    public final Context mContext;
    public int mDeferResizeToNormalBoundsUntilRotation = -1;
    public int mDisplayRotation;
    public boolean mEnableResize;
    public boolean mEnableStash = true;
    public int mExpandedShortestEdgeSize;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public DefaultPipTouchGesture mGesture;
    public int mImeHeight;
    public int mImeOffset;
    public final Rect mInsetBounds = new Rect();
    public boolean mIsImeShowing;
    public boolean mIsShelfShowing;
    public final ShellExecutor mMainExecutor;
    public final PhonePipMenuController mMenuController;
    public int mMenuState = 0;
    public float mMinimumSizePercent;
    public PipMotionHelper mMotionHelper;
    public int mMovementBoundsExtraOffsets;
    public boolean mMovementWithinDismiss;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDismissTargetHandler mPipDismissTargetHandler;
    public PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipUiEventLogger mPipUiEventLogger;
    public float mSavedSnapFraction = -1.0f;
    public boolean mSendingHoverAccessibilityEvents;
    public int mShelfHeight;
    public float mStashVelocityThreshold;
    public final Rect mTmpBounds = new Rect();
    public final PipTouchState mTouchState;

    /* renamed from: com.android.wm.shell.pip.phone.PipTouchHandler$DefaultPipTouchGesture */
    public class DefaultPipTouchGesture extends TransitionPropagation {
        public final PointF mDelta = new PointF();
        public boolean mShouldHideMenuAfterFling;
        public final Point mStartPosition = new Point();

        public DefaultPipTouchGesture() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x006c, code lost:
            if (r2.mStashedState == 2) goto L_0x006e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x007f, code lost:
            if (r2.mStashedState != 1) goto L_0x0081;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0081, code lost:
            r2 = true;
         */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x0168  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x0171  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x01c5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onUp(com.android.p012wm.shell.pip.phone.PipTouchState r9) {
            /*
                r8 = this;
                com.android.wm.shell.pip.PipUiEventLogger$PipUiEventEnum r0 = com.android.p012wm.shell.pip.PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED
                com.android.wm.shell.pip.phone.PipTouchHandler r1 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipDismissTargetHandler r1 = r1.mPipDismissTargetHandler
                r1.hideDismissTargetMaybe()
                com.android.wm.shell.pip.phone.PipTouchHandler r1 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipDismissTargetHandler r1 = r1.mPipDismissTargetHandler
                java.util.Objects.requireNonNull(r1)
                r2 = 0
                r1.mTaskLeash = r2
                java.util.Objects.requireNonNull(r9)
                boolean r1 = r9.mIsUserInteracting
                r3 = 0
                if (r1 != 0) goto L_0x001c
                return r3
            L_0x001c:
                android.graphics.PointF r1 = r9.mVelocity
                boolean r9 = r9.mIsDragging
                r4 = 1
                if (r9 == 0) goto L_0x0105
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                int r2 = r9.mMenuState
                if (r2 == 0) goto L_0x003f
                com.android.wm.shell.pip.phone.PhonePipMenuController r5 = r9.mMenuController
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                android.graphics.Rect r9 = r9.getBounds()
                com.android.wm.shell.pip.phone.PipTouchHandler r6 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                boolean r6 = r6.willResizeMenu()
                com.android.wm.shell.pip.phone.PipTouchHandler r7 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                java.util.Objects.requireNonNull(r7)
                r5.showMenu(r2, r9, r4, r6)
            L_0x003f:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                int r2 = r9.mMenuState
                if (r2 != 0) goto L_0x0047
                r2 = r4
                goto L_0x0048
            L_0x0047:
                r2 = r3
            L_0x0048:
                r8.mShouldHideMenuAfterFling = r2
                com.android.wm.shell.pip.phone.PipTouchState r9 = r9.mTouchState
                r9.reset()
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                boolean r2 = r9.mEnableStash
                if (r2 == 0) goto L_0x00d6
                android.graphics.Rect r9 = r9.getPossiblyMotionBounds()
                float r2 = r1.x
                com.android.wm.shell.pip.phone.PipTouchHandler r5 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                float r6 = r5.mStashVelocityThreshold
                float r6 = -r6
                int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                r6 = 2
                if (r2 >= 0) goto L_0x006e
                com.android.wm.shell.pip.PipBoundsState r2 = r5.mPipBoundsState
                java.util.Objects.requireNonNull(r2)
                int r2 = r2.mStashedState
                if (r2 != r6) goto L_0x0081
            L_0x006e:
                float r2 = r1.x
                com.android.wm.shell.pip.phone.PipTouchHandler r5 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                float r7 = r5.mStashVelocityThreshold
                int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                if (r2 <= 0) goto L_0x0083
                com.android.wm.shell.pip.PipBoundsState r2 = r5.mPipBoundsState
                java.util.Objects.requireNonNull(r2)
                int r2 = r2.mStashedState
                if (r2 == r4) goto L_0x0083
            L_0x0081:
                r2 = r4
                goto L_0x0084
            L_0x0083:
                r2 = r3
            L_0x0084:
                int r5 = r9.width()
                int r5 = r5 / r6
                int r6 = r9.right
                com.android.wm.shell.pip.phone.PipTouchHandler r7 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r7 = r7.mPipBoundsState
                android.graphics.Rect r7 = r7.getDisplayBounds()
                int r7 = r7.right
                int r7 = r7 + r5
                if (r6 > r7) goto L_0x00aa
                int r9 = r9.left
                com.android.wm.shell.pip.phone.PipTouchHandler r6 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r6 = r6.mPipBoundsState
                android.graphics.Rect r6 = r6.getDisplayBounds()
                int r6 = r6.left
                int r6 = r6 - r5
                if (r9 >= r6) goto L_0x00a8
                goto L_0x00aa
            L_0x00a8:
                r9 = r3
                goto L_0x00ab
            L_0x00aa:
                r9 = r4
            L_0x00ab:
                if (r2 != 0) goto L_0x00b2
                if (r9 == 0) goto L_0x00b0
                goto L_0x00b2
            L_0x00b0:
                r9 = r3
                goto L_0x00b3
            L_0x00b2:
                r9 = r4
            L_0x00b3:
                if (r9 == 0) goto L_0x00d6
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipMotionHelper r9 = r9.mMotionHelper
                float r0 = r1.x
                float r1 = r1.y
                com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2 r2 = new com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2
                r3 = 8
                r2.<init>(r8, r3)
                java.util.Objects.requireNonNull(r9)
                com.android.wm.shell.pip.PipBoundsState r8 = r9.mPipBoundsState
                java.util.Objects.requireNonNull(r8)
                int r8 = r8.mStashedState
                if (r8 != 0) goto L_0x00d1
                r1 = 0
            L_0x00d1:
                r9.movetoTarget(r0, r1, r2, r4)
                goto L_0x0267
            L_0x00d6:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                boolean r9 = r9.isStashed()
                if (r9 == 0) goto L_0x00ee
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipUiEventLogger r9 = r9.mPipUiEventLogger
                r9.log(r0)
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                r9.setStashed(r3)
            L_0x00ee:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipMotionHelper r9 = r9.mMotionHelper
                float r0 = r1.x
                float r1 = r1.y
                com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1 r2 = new com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1
                r5 = 10
                r2.<init>(r8, r5)
                java.util.Objects.requireNonNull(r9)
                r9.movetoTarget(r0, r1, r2, r3)
                goto L_0x0267
            L_0x0105:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipTouchState r9 = r9.mTouchState
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mIsDoubleTap
                if (r9 == 0) goto L_0x01f4
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                boolean r9 = r9.isStashed()
                if (r9 != 0) goto L_0x01f4
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                int r1 = r9.mMenuState
                if (r1 == r4) goto L_0x01f4
                com.android.wm.shell.pip.phone.PipResizeGestureHandler r9 = r9.mPipResizeGestureHandler
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mEnablePinchResize
                if (r9 == 0) goto L_0x01d6
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                android.graphics.Rect r9 = r9.getBounds()
                int r9 = r9.width()
                com.android.wm.shell.pip.phone.PipTouchHandler r0 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r0 = r0.mPipBoundsState
                java.util.Objects.requireNonNull(r0)
                android.graphics.Point r0 = r0.mMaxSize
                int r0 = r0.x
                if (r9 >= r0) goto L_0x015d
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                android.graphics.Rect r9 = r9.getBounds()
                int r9 = r9.height()
                com.android.wm.shell.pip.phone.PipTouchHandler r0 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r0 = r0.mPipBoundsState
                java.util.Objects.requireNonNull(r0)
                android.graphics.Point r0 = r0.mMaxSize
                int r0 = r0.y
                if (r9 >= r0) goto L_0x015d
                r9 = r4
                goto L_0x015e
            L_0x015d:
                r9 = r3
            L_0x015e:
                com.android.wm.shell.pip.phone.PipTouchHandler r0 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PhonePipMenuController r0 = r0.mMenuController
                boolean r0 = r0.isMenuVisible()
                if (r0 == 0) goto L_0x016f
                com.android.wm.shell.pip.phone.PipTouchHandler r0 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PhonePipMenuController r0 = r0.mMenuController
                r0.hideMenu(r3)
            L_0x016f:
                if (r9 == 0) goto L_0x01c5
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipResizeGestureHandler r0 = r9.mPipResizeGestureHandler
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                android.graphics.Rect r9 = r9.getBounds()
                java.util.Objects.requireNonNull(r0)
                android.graphics.Rect r0 = r0.mUserResizeBounds
                r0.set(r9)
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                java.util.Objects.requireNonNull(r8)
                android.graphics.Rect r9 = new android.graphics.Rect
                r9.<init>()
                android.graphics.Rect r0 = new android.graphics.Rect
                com.android.wm.shell.pip.PipBoundsState r1 = r8.mPipBoundsState
                java.util.Objects.requireNonNull(r1)
                android.graphics.Point r1 = r1.mMaxSize
                int r1 = r1.x
                com.android.wm.shell.pip.PipBoundsState r5 = r8.mPipBoundsState
                java.util.Objects.requireNonNull(r5)
                android.graphics.Point r5 = r5.mMaxSize
                int r5 = r5.y
                r0.<init>(r3, r3, r1, r5)
                com.android.wm.shell.pip.PipBoundsAlgorithm r1 = r8.mPipBoundsAlgorithm
                android.graphics.Rect r5 = r8.mInsetBounds
                boolean r6 = r8.mIsImeShowing
                if (r6 == 0) goto L_0x01ae
                int r3 = r8.mImeHeight
            L_0x01ae:
                java.util.Objects.requireNonNull(r1)
                com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r0, r5, r9, r3)
                com.android.wm.shell.pip.phone.PipMotionHelper r1 = r8.mMotionHelper
                com.android.wm.shell.pip.PipBoundsState r3 = r8.mPipBoundsState
                java.util.Objects.requireNonNull(r3)
                android.graphics.Rect r3 = r3.mMovementBounds
                float r9 = r1.animateToExpandedState(r0, r3, r9, r2)
                r8.mSavedSnapFraction = r9
                goto L_0x0267
            L_0x01c5:
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                java.util.Objects.requireNonNull(r8)
                com.android.wm.shell.pip.phone.PipResizeGestureHandler r9 = r8.mPipResizeGestureHandler
                java.util.Objects.requireNonNull(r9)
                android.graphics.Rect r9 = r9.mUserResizeBounds
                r8.animateToUnexpandedState(r9)
                goto L_0x0267
            L_0x01d6:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                java.util.Objects.requireNonNull(r9)
                com.android.wm.shell.pip.phone.PipTouchState r9 = r9.mTouchState
                java.util.Objects.requireNonNull(r9)
                r9.mAllowTouches = r3
                boolean r0 = r9.mIsUserInteracting
                if (r0 == 0) goto L_0x01e9
                r9.reset()
            L_0x01e9:
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipMotionHelper r8 = r8.mMotionHelper
                java.util.Objects.requireNonNull(r8)
                r8.expandLeavePip(r3, r3)
                goto L_0x0267
            L_0x01f4:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                int r1 = r9.mMenuState
                if (r1 == r4) goto L_0x0267
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                boolean r9 = r9.isStashed()
                if (r9 == 0) goto L_0x0226
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                r9.animateToUnStashedState()
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipUiEventLogger r9 = r9.mPipUiEventLogger
                r9.log(r0)
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                r9.setStashed(r3)
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipTouchState r8 = r8.mTouchState
                java.util.Objects.requireNonNull(r8)
                r8.mIsWaitingForDoubleTap = r3
                com.android.wm.shell.common.ShellExecutor r9 = r8.mMainExecutor
                java.lang.Runnable r8 = r8.mDoubleTapTimeoutCallback
                r9.removeCallbacks(r8)
                goto L_0x0267
            L_0x0226:
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipTouchState r9 = r9.mTouchState
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mIsWaitingForDoubleTap
                if (r9 != 0) goto L_0x024a
                com.android.wm.shell.pip.phone.PipTouchHandler r9 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PhonePipMenuController r0 = r9.mMenuController
                com.android.wm.shell.pip.PipBoundsState r9 = r9.mPipBoundsState
                android.graphics.Rect r9 = r9.getBounds()
                com.android.wm.shell.pip.phone.PipTouchHandler r1 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                boolean r1 = r1.willResizeMenu()
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                java.util.Objects.requireNonNull(r8)
                r0.showMenu(r4, r9, r4, r1)
                goto L_0x0267
            L_0x024a:
                com.android.wm.shell.pip.phone.PipTouchHandler r8 = com.android.p012wm.shell.pip.phone.PipTouchHandler.this
                com.android.wm.shell.pip.phone.PipTouchState r8 = r8.mTouchState
                java.util.Objects.requireNonNull(r8)
                boolean r9 = r8.mIsWaitingForDoubleTap
                if (r9 == 0) goto L_0x0267
                long r0 = r8.getDoubleTapTimeoutCallbackDelay()
                com.android.wm.shell.common.ShellExecutor r9 = r8.mMainExecutor
                java.lang.Runnable r2 = r8.mDoubleTapTimeoutCallback
                r9.removeCallbacks(r2)
                com.android.wm.shell.common.ShellExecutor r9 = r8.mMainExecutor
                java.lang.Runnable r8 = r8.mDoubleTapTimeoutCallback
                r9.executeDelayed(r8, r0)
            L_0x0267:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.phone.PipTouchHandler.DefaultPipTouchGesture.onUp(com.android.wm.shell.pip.phone.PipTouchState):boolean");
        }

        public final void onDown(PipTouchState pipTouchState) {
            boolean z;
            Objects.requireNonNull(pipTouchState);
            if (pipTouchState.mIsUserInteracting) {
                Rect possiblyMotionBounds = PipTouchHandler.this.getPossiblyMotionBounds();
                this.mDelta.set(0.0f, 0.0f);
                this.mStartPosition.set(possiblyMotionBounds.left, possiblyMotionBounds.top);
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                float f = pipTouchState.mDownTouch.y;
                PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState);
                if (f >= ((float) pipBoundsState.mMovementBounds.bottom)) {
                    z = true;
                } else {
                    z = false;
                }
                pipTouchHandler.mMovementWithinDismiss = z;
                PipMotionHelper pipMotionHelper = PipTouchHandler.this.mMotionHelper;
                Objects.requireNonNull(pipMotionHelper);
                pipMotionHelper.mSpringingToTouch = false;
                PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler2.mPipDismissTargetHandler;
                PipTaskOrganizer pipTaskOrganizer = pipTouchHandler2.mPipTaskOrganizer;
                Objects.requireNonNull(pipTaskOrganizer);
                SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                Objects.requireNonNull(pipDismissTargetHandler);
                pipDismissTargetHandler.mTaskLeash = surfaceControl;
                PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
                if (pipTouchHandler3.mMenuState != 0 && !pipTouchHandler3.mPipBoundsState.isStashed()) {
                    PhonePipMenuController phonePipMenuController = PipTouchHandler.this.mMenuController;
                    Objects.requireNonNull(phonePipMenuController);
                    if (phonePipMenuController.isMenuVisible()) {
                        PipMenuView pipMenuView = phonePipMenuController.mPipMenuView;
                        Objects.requireNonNull(pipMenuView);
                        pipMenuView.mMainExecutor.removeCallbacks(pipMenuView.mHideMenuRunnable);
                    }
                }
            }
        }

        public final boolean onMove(PipTouchState pipTouchState) {
            Objects.requireNonNull(pipTouchState);
            boolean z = false;
            if (!pipTouchState.mIsUserInteracting) {
                return false;
            }
            if (pipTouchState.mStartedDragging) {
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                pipTouchHandler.mSavedSnapFraction = -1.0f;
                pipTouchHandler.mPipDismissTargetHandler.showDismissTargetMaybe();
            }
            if (!pipTouchState.mIsDragging) {
                return false;
            }
            PointF pointF = pipTouchState.mLastDelta;
            Point point = this.mStartPosition;
            PointF pointF2 = this.mDelta;
            float f = pointF2.x;
            float f2 = ((float) point.x) + f;
            float f3 = pointF2.y;
            float f4 = ((float) point.y) + f3;
            float f5 = pointF.x + f2;
            float f6 = pointF.y + f4;
            pointF2.x = (f5 - f2) + f;
            pointF2.y = (f6 - f4) + f3;
            PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
            pipTouchHandler2.mTmpBounds.set(pipTouchHandler2.getPossiblyMotionBounds());
            PipTouchHandler.this.mTmpBounds.offsetTo((int) f5, (int) f6);
            PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
            pipTouchHandler3.mMotionHelper.movePip(pipTouchHandler3.mTmpBounds, true);
            PointF pointF3 = pipTouchState.mLastTouch;
            PipTouchHandler pipTouchHandler4 = PipTouchHandler.this;
            if (pipTouchHandler4.mMovementWithinDismiss) {
                float f7 = pointF3.y;
                PipBoundsState pipBoundsState = pipTouchHandler4.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState);
                if (f7 >= ((float) pipBoundsState.mMovementBounds.bottom)) {
                    z = true;
                }
                pipTouchHandler4.mMovementWithinDismiss = z;
            }
            return true;
        }
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipTouchHandler$PipMenuListener */
    public class PipMenuListener implements PhonePipMenuController.Listener {
        public PipMenuListener() {
        }

        public final void onEnterSplit() {
            PipMotionHelper pipMotionHelper = PipTouchHandler.this.mMotionHelper;
            Objects.requireNonNull(pipMotionHelper);
            pipMotionHelper.expandLeavePip(false, true);
        }

        public final void onPipDismiss() {
            PipTouchState pipTouchState = PipTouchHandler.this.mTouchState;
            Objects.requireNonNull(pipTouchState);
            pipTouchState.mIsWaitingForDoubleTap = false;
            pipTouchState.mMainExecutor.removeCallbacks(pipTouchState.mDoubleTapTimeoutCallback);
            PipTouchHandler.this.mMotionHelper.dismissPip();
        }

        public final void onPipExpand() {
            PipMotionHelper pipMotionHelper = PipTouchHandler.this.mMotionHelper;
            Objects.requireNonNull(pipMotionHelper);
            pipMotionHelper.expandLeavePip(false, false);
        }

        public final void onPipMenuStateChangeFinish(int i) {
            boolean z;
            PipTouchHandler pipTouchHandler = PipTouchHandler.this;
            Objects.requireNonNull(pipTouchHandler);
            pipTouchHandler.mMenuState = i;
            pipTouchHandler.updateMovementBounds();
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            pipTouchHandler.onRegistrationChanged(z);
            if (i == 0) {
                pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_HIDE_MENU);
            } else if (i == 1) {
                pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_SHOW_MENU);
            }
        }

        public final void onPipMenuStateChangeStart(int i, boolean z, Runnable runnable) {
            int rotation;
            boolean z2;
            boolean z3;
            int i2;
            int i3;
            PipTouchHandler pipTouchHandler = PipTouchHandler.this;
            Objects.requireNonNull(pipTouchHandler);
            int i4 = pipTouchHandler.mMenuState;
            if (i4 == i && !z) {
                return;
            }
            if (i != 1 || i4 == 1) {
                if (i == 0 && i4 == 1) {
                    if (z) {
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        Objects.requireNonNull(pipResizeGestureHandler);
                        if (!pipResizeGestureHandler.mAllowGesture) {
                            if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == -1 && pipTouchHandler.mDisplayRotation != (rotation = pipTouchHandler.mContext.getDisplay().getRotation())) {
                                pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation = rotation;
                            }
                            if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == -1) {
                                PipResizeGestureHandler pipResizeGestureHandler2 = pipTouchHandler.mPipResizeGestureHandler;
                                Objects.requireNonNull(pipResizeGestureHandler2);
                                pipTouchHandler.animateToUnexpandedState(pipResizeGestureHandler2.mUserResizeBounds);
                                return;
                            }
                            return;
                        }
                    }
                    pipTouchHandler.mSavedSnapFraction = -1.0f;
                }
            } else if (z) {
                PipResizeGestureHandler pipResizeGestureHandler3 = pipTouchHandler.mPipResizeGestureHandler;
                Rect bounds = pipTouchHandler.mPipBoundsState.getBounds();
                Objects.requireNonNull(pipResizeGestureHandler3);
                pipResizeGestureHandler3.mUserResizeBounds.set(bounds);
                Size estimatedMinMenuSize = pipTouchHandler.mMenuController.getEstimatedMinMenuSize();
                PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState);
                Rect rect = pipBoundsState.mNormalBounds;
                PipBoundsAlgorithm pipBoundsAlgorithm = pipTouchHandler.mPipBoundsAlgorithm;
                Objects.requireNonNull(pipBoundsAlgorithm);
                int i5 = 0;
                if (estimatedMinMenuSize != null && (rect.width() < estimatedMinMenuSize.getWidth() || rect.height() < estimatedMinMenuSize.getHeight())) {
                    Rect rect2 = new Rect();
                    if (estimatedMinMenuSize.getWidth() > rect.width()) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (estimatedMinMenuSize.getHeight() > rect.height()) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (!z2 || !z3) {
                        if (z2) {
                            i3 = estimatedMinMenuSize.getWidth();
                            PipBoundsState pipBoundsState2 = pipBoundsAlgorithm.mPipBoundsState;
                            Objects.requireNonNull(pipBoundsState2);
                            i2 = Math.round(((float) i3) / pipBoundsState2.mAspectRatio);
                        } else {
                            i2 = estimatedMinMenuSize.getHeight();
                            PipBoundsState pipBoundsState3 = pipBoundsAlgorithm.mPipBoundsState;
                            Objects.requireNonNull(pipBoundsState3);
                            i3 = Math.round(((float) i2) * pipBoundsState3.mAspectRatio);
                        }
                    } else if (((float) estimatedMinMenuSize.getWidth()) / ((float) rect.width()) > ((float) estimatedMinMenuSize.getHeight()) / ((float) rect.height())) {
                        i3 = estimatedMinMenuSize.getWidth();
                        PipBoundsState pipBoundsState4 = pipBoundsAlgorithm.mPipBoundsState;
                        Objects.requireNonNull(pipBoundsState4);
                        i2 = Math.round(((float) i3) / pipBoundsState4.mAspectRatio);
                    } else {
                        i2 = estimatedMinMenuSize.getHeight();
                        PipBoundsState pipBoundsState5 = pipBoundsAlgorithm.mPipBoundsState;
                        Objects.requireNonNull(pipBoundsState5);
                        i3 = Math.round(((float) i2) * pipBoundsState5.mAspectRatio);
                    }
                    rect2.set(0, 0, i3, i2);
                    PipBoundsState pipBoundsState6 = pipBoundsAlgorithm.mPipBoundsState;
                    Objects.requireNonNull(pipBoundsState6);
                    pipBoundsAlgorithm.transformBoundsToAspectRatio(rect2, pipBoundsState6.mAspectRatio, true, true);
                    rect = rect2;
                }
                Rect rect3 = new Rect();
                PipBoundsAlgorithm pipBoundsAlgorithm2 = pipTouchHandler.mPipBoundsAlgorithm;
                Rect rect4 = pipTouchHandler.mInsetBounds;
                if (pipTouchHandler.mIsImeShowing) {
                    i5 = pipTouchHandler.mImeHeight;
                }
                Objects.requireNonNull(pipBoundsAlgorithm2);
                PipBoundsAlgorithm.getMovementBounds(rect, rect4, rect3, i5);
                PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
                PipBoundsState pipBoundsState7 = pipTouchHandler.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState7);
                pipTouchHandler.mSavedSnapFraction = pipMotionHelper.animateToExpandedState(rect, pipBoundsState7.mMovementBounds, rect3, runnable);
            }
        }

        public final void onPipShowMenu() {
            PipTouchHandler pipTouchHandler = PipTouchHandler.this;
            PhonePipMenuController phonePipMenuController = pipTouchHandler.mMenuController;
            Rect bounds = pipTouchHandler.mPipBoundsState.getBounds();
            boolean willResizeMenu = PipTouchHandler.this.willResizeMenu();
            Objects.requireNonNull(PipTouchHandler.this);
            phonePipMenuController.showMenu(1, bounds, true, willResizeMenu);
        }
    }

    @SuppressLint({"InflateParams"})
    public PipTouchHandler(Context context, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipTaskOrganizer pipTaskOrganizer, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor) {
        Context context2 = context;
        PhonePipMenuController phonePipMenuController2 = phonePipMenuController;
        PipMotionHelper pipMotionHelper2 = pipMotionHelper;
        PipUiEventLogger pipUiEventLogger2 = pipUiEventLogger;
        ShellExecutor shellExecutor2 = shellExecutor;
        this.mContext = context2;
        this.mMainExecutor = shellExecutor2;
        this.mAccessibilityManager = (AccessibilityManager) context2.getSystemService(AccessibilityManager.class);
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mMenuController = phonePipMenuController2;
        this.mPipUiEventLogger = pipUiEventLogger2;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        PipMenuListener pipMenuListener = new PipMenuListener();
        Objects.requireNonNull(phonePipMenuController);
        if (!phonePipMenuController2.mListeners.contains(pipMenuListener)) {
            phonePipMenuController2.mListeners.add(pipMenuListener);
        }
        this.mGesture = new DefaultPipTouchGesture();
        this.mMotionHelper = pipMotionHelper2;
        PipDismissTargetHandler pipDismissTargetHandler = new PipDismissTargetHandler(context2, pipUiEventLogger2, pipMotionHelper2, shellExecutor2);
        this.mPipDismissTargetHandler = pipDismissTargetHandler;
        PipMotionHelper pipMotionHelper3 = this.mMotionHelper;
        PeopleSpaceUtils$$ExternalSyntheticLambda2 peopleSpaceUtils$$ExternalSyntheticLambda2 = new PeopleSpaceUtils$$ExternalSyntheticLambda2(this, 1);
        PeopleSpaceUtils$$ExternalSyntheticLambda2 peopleSpaceUtils$$ExternalSyntheticLambda22 = peopleSpaceUtils$$ExternalSyntheticLambda2;
        PipResizeGestureHandler pipResizeGestureHandler = r1;
        PipResizeGestureHandler pipResizeGestureHandler2 = new PipResizeGestureHandler(context, pipBoundsAlgorithm, pipBoundsState, pipMotionHelper3, pipTaskOrganizer, pipDismissTargetHandler, peopleSpaceUtils$$ExternalSyntheticLambda22, new StatusBar$$ExternalSyntheticLambda19(this, 8), pipUiEventLogger, phonePipMenuController, shellExecutor);
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
        this.mTouchState = new PipTouchState(ViewConfiguration.get(context), new CarrierTextManager$$ExternalSyntheticLambda0(this, 9), new TaskView$$ExternalSyntheticLambda3(phonePipMenuController2, 9), shellExecutor2);
        PipMotionHelper pipMotionHelper4 = this.mMotionHelper;
        Objects.requireNonNull(pipBoundsAlgorithm);
        this.mConnection = new PipAccessibilityInteractionConnection(context, pipBoundsState, pipMotionHelper4, pipTaskOrganizer, pipBoundsAlgorithm.mSnapAlgorithm, new PipTouchHandler$$ExternalSyntheticLambda2(this), new ScrimView$$ExternalSyntheticLambda0(this, 7), new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 10), shellExecutor);
    }

    public final void animateToUnStashedState() {
        boolean z;
        int i;
        int i2;
        Rect bounds = this.mPipBoundsState.getBounds();
        if (bounds.left < this.mPipBoundsState.getDisplayBounds().left) {
            z = true;
        } else {
            z = false;
        }
        Rect rect = new Rect(0, bounds.top, 0, bounds.bottom);
        if (z) {
            i = this.mInsetBounds.left;
        } else {
            i = this.mInsetBounds.right - bounds.width();
        }
        rect.left = i;
        if (z) {
            i2 = bounds.width() + this.mInsetBounds.left;
        } else {
            i2 = this.mInsetBounds.right;
        }
        rect.right = i2;
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        Objects.requireNonNull(pipMotionHelper);
        pipMotionHelper.mPipTaskOrganizer.scheduleAnimateResizePip(rect, 250, 8, (QSTileHost$$ExternalSyntheticLambda1) null);
        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
        Objects.requireNonNull(motionBoundsState);
        motionBoundsState.mAnimatingToBounds.set(rect);
        pipMotionHelper.mFloatingContentCoordinator.onContentMoved(pipMotionHelper);
    }

    public final void animateToUnexpandedState(Rect rect) {
        int i;
        Rect rect2 = new Rect();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect rect3 = this.mInsetBounds;
        if (this.mIsImeShowing) {
            i = this.mImeHeight;
        } else {
            i = 0;
        }
        Objects.requireNonNull(pipBoundsAlgorithm);
        PipBoundsAlgorithm.getMovementBounds(rect, rect3, rect2, i);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        float f = this.mSavedSnapFraction;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        pipMotionHelper.animateToUnexpandedState(rect, f, rect2, pipBoundsState.mMovementBounds, false);
        this.mSavedSnapFraction = -1.0f;
    }

    public final Rect getPossiblyMotionBounds() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
        Objects.requireNonNull(motionBoundsState);
        if (!(!motionBoundsState.mBoundsInMotion.isEmpty())) {
            return this.mPipBoundsState.getBounds();
        }
        PipBoundsState pipBoundsState2 = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState2);
        PipBoundsState.MotionBoundsState motionBoundsState2 = pipBoundsState2.mMotionBoundsState;
        Objects.requireNonNull(motionBoundsState2);
        return motionBoundsState2.mBoundsInMotion;
    }

    public final void onRegistrationChanged(boolean z) {
        if (z) {
            PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = this.mConnection;
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            Objects.requireNonNull(pipAccessibilityInteractionConnection);
            accessibilityManager.setPictureInPictureActionReplacingConnection(pipAccessibilityInteractionConnection.mConnectionImpl);
        } else {
            this.mAccessibilityManager.setPictureInPictureActionReplacingConnection((IAccessibilityInteractionConnection) null);
        }
        if (!z) {
            PipTouchState pipTouchState = this.mTouchState;
            Objects.requireNonNull(pipTouchState);
            if (pipTouchState.mIsUserInteracting) {
                PipDismissTargetHandler pipDismissTargetHandler = this.mPipDismissTargetHandler;
                Objects.requireNonNull(pipDismissTargetHandler);
                if (pipDismissTargetHandler.mTargetViewContainer.isAttachedToWindow()) {
                    pipDismissTargetHandler.mWindowManager.removeViewImmediate(pipDismissTargetHandler.mTargetViewContainer);
                }
            }
        }
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mBottomOffsetBufferPx = resources.getDimensionPixelSize(C1777R.dimen.pip_bottom_offset_buffer);
        this.mExpandedShortestEdgeSize = resources.getDimensionPixelSize(C1777R.dimen.pip_expanded_shortest_edge_size);
        this.mImeOffset = resources.getDimensionPixelSize(C1777R.dimen.pip_ime_offset);
        this.mMinimumSizePercent = resources.getFraction(C1777R.fraction.config_pipShortestEdgePercent, 1, 1);
        this.mPipDismissTargetHandler.updateMagneticTargetSize();
    }

    public final void sendAccessibilityHoverEvent(int i) {
        if (this.mAccessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
            obtain.setImportantForAccessibility(true);
            obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
            obtain.setWindowId(-3);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public final void updateMovementBounds() {
        int i;
        int i2;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect bounds = this.mPipBoundsState.getBounds();
        Rect rect = this.mInsetBounds;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        Rect rect2 = pipBoundsState.mMovementBounds;
        boolean z = false;
        if (this.mIsImeShowing) {
            i = this.mImeHeight;
        } else {
            i = 0;
        }
        Objects.requireNonNull(pipBoundsAlgorithm);
        PipBoundsAlgorithm.getMovementBounds(bounds, rect, rect2, i);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        Objects.requireNonNull(pipMotionHelper);
        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState2);
        PipBoundsState pipBoundsState3 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState3);
        pipMotionHelper.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, (float) pipBoundsState2.mMovementBounds.left, (float) pipBoundsState3.mMovementBounds.right, 0.0f);
        PipBoundsState pipBoundsState4 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState4);
        PipBoundsState pipBoundsState5 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState5);
        pipMotionHelper.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, (float) pipBoundsState4.mMovementBounds.top, (float) pipBoundsState5.mMovementBounds.bottom, 0.0f);
        PipBoundsState pipBoundsState6 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState6);
        DisplayLayout displayLayout = pipBoundsState6.mDisplayLayout;
        Objects.requireNonNull(displayLayout);
        Rect rect3 = displayLayout.mStableInsets;
        PipBoundsState pipBoundsState7 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState7);
        int i3 = pipMotionHelper.mPipBoundsState.getDisplayBounds().right;
        PipBoundsState pipBoundsState8 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState8);
        pipMotionHelper.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, (float) ((pipBoundsState7.mStashOffset - pipMotionHelper.mPipBoundsState.getBounds().width()) + rect3.left), (float) ((i3 - pipBoundsState8.mStashOffset) - rect3.right), 0.0f);
        Rect rect4 = pipMotionHelper.mFloatingAllowedArea;
        PipBoundsState pipBoundsState9 = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState9);
        rect4.set(pipBoundsState9.mMovementBounds);
        Rect rect5 = pipMotionHelper.mFloatingAllowedArea;
        rect5.right = pipMotionHelper.getBounds().width() + rect5.right;
        Rect rect6 = pipMotionHelper.mFloatingAllowedArea;
        rect6.bottom = pipMotionHelper.getBounds().height() + rect6.bottom;
        if (this.mMenuState == 1) {
            z = true;
        }
        PipBoundsState pipBoundsState10 = this.mPipBoundsState;
        if (!z || !willResizeMenu()) {
            PipBoundsAlgorithm pipBoundsAlgorithm2 = this.mPipBoundsAlgorithm;
            Objects.requireNonNull(pipBoundsAlgorithm2);
            i2 = pipBoundsAlgorithm2.mDefaultMinSize;
        } else {
            i2 = this.mExpandedShortestEdgeSize;
        }
        Objects.requireNonNull(pipBoundsState10);
        pipBoundsState10.mMinEdgeSize = i2;
    }

    public final boolean willResizeMenu() {
        if (!this.mEnableResize) {
            return false;
        }
        Size estimatedMinMenuSize = this.mMenuController.getEstimatedMinMenuSize();
        if (estimatedMinMenuSize == null) {
            Log.wtf("PipTouchHandler", "Failed to get estimated menu size");
            return false;
        }
        Rect bounds = this.mPipBoundsState.getBounds();
        if (bounds.width() < estimatedMinMenuSize.getWidth() || bounds.height() < estimatedMinMenuSize.getHeight()) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public void setPipMotionHelper(PipMotionHelper pipMotionHelper) {
        this.mMotionHelper = pipMotionHelper;
    }

    @VisibleForTesting
    public void setPipResizeGestureHandler(PipResizeGestureHandler pipResizeGestureHandler) {
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
    }

    @VisibleForTesting
    public PipResizeGestureHandler getPipResizeGestureHandler() {
        return this.mPipResizeGestureHandler;
    }
}
