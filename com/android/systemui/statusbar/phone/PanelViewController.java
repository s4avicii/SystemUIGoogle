package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.media.MediaControlPanel;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.media.MediaPlayerData;
import com.android.systemui.p006qs.QSPanelController$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.PanelView;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManagerKt;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Iterator;
import java.util.Objects;

public abstract class PanelViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AmbientState mAmbientState;
    public boolean mAnimateAfterExpanding;
    public boolean mAnimatingOnDown;
    public BounceInterpolator mBounceInterpolator;
    public boolean mClosing;
    public boolean mCollapsedAndHeadsUpOnDown;
    public long mDownTime;
    public final DozeLog mDozeLog;
    public boolean mExpandLatencyTracking;
    public float mExpandedFraction = 0.0f;
    public float mExpandedHeight = 0.0f;
    public boolean mExpanding;
    public final FalsingManager mFalsingManager;
    public int mFixedDuration = -1;
    public FlingAnimationUtils mFlingAnimationUtils;
    public FlingAnimationUtils mFlingAnimationUtilsClosing;
    public FlingAnimationUtils mFlingAnimationUtilsDismissing;
    public final C15035 mFlingCollapseRunnable;
    public boolean mGestureWaitForTouchSlop;
    public boolean mHandlingPointerUp;
    public boolean mHasLayoutedSinceDown;
    public HeadsUpManagerPhone mHeadsUpManager;
    public ValueAnimator mHeightAnimator;
    public boolean mHintAnimationRunning;
    public float mHintDistance;
    public boolean mIgnoreXTouchSlop;
    public float mInitialOffsetOnTouch;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mInstantExpanding;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsLaunchAnimationRunning;
    public boolean mIsSpringBackAnimation;
    public KeyguardBottomAreaView mKeyguardBottomArea;
    public final KeyguardStateController mKeyguardStateController;
    public float mLastGesturedOverExpansion = -1.0f;
    public final LatencyTracker mLatencyTracker;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public float mMinExpandHeight;
    public boolean mMotionAborted;
    public float mNextCollapseSpeedUpFactor = 1.0f;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public boolean mNotificationsDragEnabled;
    public float mOverExpansion;
    public boolean mPanelClosedOnDown;
    public final PanelExpansionStateManager mPanelExpansionStateManager;
    public float mPanelFlingOvershootAmount;
    public boolean mPanelUpdateWhenAnimatorEnds;
    public final Resources mResources;
    public float mSlopMultiplier;
    public StatusBar mStatusBar;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public boolean mTouchAboveFalsingThreshold;
    public boolean mTouchDisabled;
    public final NotificationPanelViewController.C148217 mTouchHandler;
    public int mTouchSlop;
    public boolean mTouchSlopExceeded;
    public boolean mTouchSlopExceededBeforeDown;
    public boolean mTouchStartedInEmptyArea;
    public boolean mTracking;
    public int mTrackingPointer;
    public int mUnlockFalsingThreshold;
    public boolean mUpdateFlingOnLayout;
    public float mUpdateFlingVelocity;
    public boolean mUpwardsWhenThresholdReached;
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public boolean mVibrateOnOpening;
    public final VibratorHelper mVibratorHelper;
    public final PanelView mView;

    public class OnConfigurationChangedListener implements PanelView.OnConfigurationChangedListener {
        public OnConfigurationChangedListener() {
        }
    }

    public class OnLayoutChangeListener implements View.OnLayoutChangeListener {
        public OnLayoutChangeListener() {
        }

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            PanelViewController.this.requestPanelHeightUpdate();
            PanelViewController panelViewController = PanelViewController.this;
            panelViewController.mHasLayoutedSinceDown = true;
            if (panelViewController.mUpdateFlingOnLayout) {
                panelViewController.cancelHeightAnimator();
                panelViewController.mView.removeCallbacks(panelViewController.mFlingCollapseRunnable);
                PanelViewController panelViewController2 = PanelViewController.this;
                float f = panelViewController2.mUpdateFlingVelocity;
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) panelViewController2;
                Objects.requireNonNull(notificationPanelViewController.mStatusBar);
                notificationPanelViewController.fling(f, true, 1.0f, false);
                PanelViewController.this.mUpdateFlingOnLayout = false;
            }
        }
    }

    public class TouchHandler implements View.OnTouchListener {
        public TouchHandler() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:140:0x027b  */
        /* JADX WARNING: Removed duplicated region for block: B:141:0x027d  */
        /* JADX WARNING: Removed duplicated region for block: B:148:0x028b  */
        /* JADX WARNING: Removed duplicated region for block: B:149:0x028d  */
        /* JADX WARNING: Removed duplicated region for block: B:153:0x02a0  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x0091  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouch(android.view.View r9, android.view.MotionEvent r10) {
            /*
                r8 = this;
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r0 = r9.mInstantExpanding
                r1 = 1
                r2 = 0
                if (r0 != 0) goto L_0x033f
                boolean r9 = r9.mTouchDisabled
                r0 = 3
                if (r9 == 0) goto L_0x0013
                int r9 = r10.getActionMasked()
                if (r9 != r0) goto L_0x033f
            L_0x0013:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r9 = r9.mMotionAborted
                if (r9 == 0) goto L_0x0021
                int r9 = r10.getActionMasked()
                if (r9 == 0) goto L_0x0021
                goto L_0x033f
            L_0x0021:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r3 = r9.mNotificationsDragEnabled
                if (r3 != 0) goto L_0x002f
                boolean r8 = r9.mTracking
                if (r8 == 0) goto L_0x002e
                r9.onTrackingStopped(r1)
            L_0x002e:
                return r2
            L_0x002f:
                boolean r9 = r9.isFullyCollapsed()
                if (r9 == 0) goto L_0x0049
                r9 = 8194(0x2002, float:1.1482E-41)
                boolean r9 = r10.isFromSource(r9)
                if (r9 == 0) goto L_0x0049
                int r9 = r10.getAction()
                if (r9 != r1) goto L_0x0048
                com.android.systemui.statusbar.phone.PanelViewController r8 = com.android.systemui.statusbar.phone.PanelViewController.this
                r8.expand(r1)
            L_0x0048:
                return r1
            L_0x0049:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                int r9 = r9.mTrackingPointer
                int r9 = r10.findPointerIndex(r9)
                if (r9 >= 0) goto L_0x005c
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                int r3 = r10.getPointerId(r2)
                r9.mTrackingPointer = r3
                r9 = r2
            L_0x005c:
                float r3 = r10.getX(r9)
                float r9 = r10.getY(r9)
                int r4 = r10.getActionMasked()
                if (r4 != 0) goto L_0x00ba
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                r5 = r4
                com.android.systemui.statusbar.phone.NotificationPanelViewController r5 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r5
                java.util.Objects.requireNonNull(r5)
                boolean r6 = r5.mExpectingSynthesizedDown
                if (r6 == 0) goto L_0x0079
                r5.mExpectingSynthesizedDown = r2
                goto L_0x0084
            L_0x0079:
                boolean r6 = r5.isFullyCollapsed()
                if (r6 != 0) goto L_0x0086
                int r5 = r5.mBarState
                if (r5 == 0) goto L_0x0084
                goto L_0x0086
            L_0x0084:
                r5 = r2
                goto L_0x0087
            L_0x0086:
                r5 = r1
            L_0x0087:
                r4.mGestureWaitForTouchSlop = r5
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r5 = r4.isFullyCollapsed()
                if (r5 != 0) goto L_0x00b7
                com.android.systemui.statusbar.phone.PanelViewController r5 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.NotificationPanelViewController r5 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r5
                java.util.Objects.requireNonNull(r5)
                com.android.systemui.statusbar.phone.KeyguardAffordanceHelper r5 = r5.mAffordanceHelper
                java.util.Objects.requireNonNull(r5)
                com.android.systemui.statusbar.KeyguardAffordanceView r6 = r5.mLeftIcon
                boolean r6 = r5.isOnIcon(r6, r3, r9)
                if (r6 != 0) goto L_0x00b0
                com.android.systemui.statusbar.KeyguardAffordanceView r6 = r5.mRightIcon
                boolean r5 = r5.isOnIcon(r6, r3, r9)
                if (r5 == 0) goto L_0x00ae
                goto L_0x00b0
            L_0x00ae:
                r5 = r2
                goto L_0x00b1
            L_0x00b0:
                r5 = r1
            L_0x00b1:
                r5 = r5 ^ r1
                if (r5 == 0) goto L_0x00b5
                goto L_0x00b7
            L_0x00b5:
                r5 = r2
                goto L_0x00b8
            L_0x00b7:
                r5 = r1
            L_0x00b8:
                r4.mIgnoreXTouchSlop = r5
            L_0x00ba:
                int r4 = r10.getActionMasked()
                r5 = 2
                r6 = 0
                if (r4 == 0) goto L_0x022f
                if (r4 == r1) goto L_0x01f1
                if (r4 == r5) goto L_0x0118
                if (r4 == r0) goto L_0x01f1
                r0 = 5
                if (r4 == r0) goto L_0x0106
                r9 = 6
                if (r4 == r9) goto L_0x00d0
                goto L_0x0334
            L_0x00d0:
                int r9 = r10.getActionIndex()
                int r9 = r10.getPointerId(r9)
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                int r0 = r0.mTrackingPointer
                if (r0 != r9) goto L_0x0334
                int r0 = r10.getPointerId(r2)
                if (r0 == r9) goto L_0x00e6
                r9 = r2
                goto L_0x00e7
            L_0x00e6:
                r9 = r1
            L_0x00e7:
                float r0 = r10.getY(r9)
                float r3 = r10.getX(r9)
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                int r9 = r10.getPointerId(r9)
                r4.mTrackingPointer = r9
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.mHandlingPointerUp = r1
                float r10 = r9.mExpandedHeight
                r9.startExpandMotion(r3, r0, r1, r10)
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.mHandlingPointerUp = r2
                goto L_0x0334
            L_0x0106:
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r0.mStatusBarStateController
                int r0 = r0.getState()
                if (r0 != r1) goto L_0x0334
                com.android.systemui.statusbar.phone.PanelViewController r8 = com.android.systemui.statusbar.phone.PanelViewController.this
                r8.mMotionAborted = r1
                com.android.systemui.statusbar.phone.PanelViewController.m245$$Nest$mendMotionEvent(r8, r10, r3, r9, r1)
                return r2
            L_0x0118:
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.PanelViewController.m244$$Nest$maddMovement(r0, r10)
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r0 = r0.mInitialTouchY
                float r0 = r9 - r0
                float r4 = java.lang.Math.abs(r0)
                com.android.systemui.statusbar.phone.PanelViewController r5 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r10 = r5.getTouchSlop(r10)
                int r10 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
                if (r10 <= 0) goto L_0x016f
                float r10 = java.lang.Math.abs(r0)
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r4 = r4.mInitialTouchX
                float r4 = r3 - r4
                float r4 = java.lang.Math.abs(r4)
                int r10 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
                if (r10 > 0) goto L_0x0149
                com.android.systemui.statusbar.phone.PanelViewController r10 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r10 = r10.mIgnoreXTouchSlop
                if (r10 == 0) goto L_0x016f
            L_0x0149:
                com.android.systemui.statusbar.phone.PanelViewController r10 = com.android.systemui.statusbar.phone.PanelViewController.this
                r10.mTouchSlopExceeded = r1
                boolean r4 = r10.mGestureWaitForTouchSlop
                if (r4 == 0) goto L_0x016f
                boolean r4 = r10.mTracking
                if (r4 != 0) goto L_0x016f
                boolean r4 = r10.mCollapsedAndHeadsUpOnDown
                if (r4 != 0) goto L_0x016f
                float r4 = r10.mInitialOffsetOnTouch
                int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x0165
                float r0 = r10.mExpandedHeight
                r10.startExpandMotion(r3, r9, r2, r0)
                r0 = r6
            L_0x0165:
                com.android.systemui.statusbar.phone.PanelViewController r10 = com.android.systemui.statusbar.phone.PanelViewController.this
                r10.cancelHeightAnimator()
                com.android.systemui.statusbar.phone.PanelViewController r10 = com.android.systemui.statusbar.phone.PanelViewController.this
                r10.onTrackingStarted()
            L_0x016f:
                com.android.systemui.statusbar.phone.PanelViewController r10 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r10 = r10.mInitialOffsetOnTouch
                float r10 = r10 + r0
                float r10 = java.lang.Math.max(r6, r10)
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r4 = r4.mMinExpandHeight
                float r10 = java.lang.Math.max(r10, r4)
                float r4 = -r0
                com.android.systemui.statusbar.phone.PanelViewController r5 = com.android.systemui.statusbar.phone.PanelViewController.this
                java.util.Objects.requireNonNull(r5)
                com.android.systemui.statusbar.phone.StatusBar r7 = r5.mStatusBar
                java.util.Objects.requireNonNull(r7)
                boolean r7 = r7.mWakeUpComingFromTouch
                if (r7 == 0) goto L_0x0192
                r7 = 1069547520(0x3fc00000, float:1.5)
                goto L_0x0194
            L_0x0192:
                r7 = 1065353216(0x3f800000, float:1.0)
            L_0x0194:
                int r5 = r5.mUnlockFalsingThreshold
                float r5 = (float) r5
                float r5 = r5 * r7
                int r5 = (int) r5
                float r5 = (float) r5
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 < 0) goto L_0x01be
                com.android.systemui.statusbar.phone.PanelViewController r4 = com.android.systemui.statusbar.phone.PanelViewController.this
                r4.mTouchAboveFalsingThreshold = r1
                float r5 = r4.mInitialTouchX
                float r3 = r3 - r5
                float r5 = r4.mInitialTouchY
                float r9 = r9 - r5
                int r5 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                if (r5 < 0) goto L_0x01ad
                goto L_0x01bb
            L_0x01ad:
                float r9 = java.lang.Math.abs(r9)
                float r3 = java.lang.Math.abs(r3)
                int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r9 < 0) goto L_0x01bb
                r9 = r1
                goto L_0x01bc
            L_0x01bb:
                r9 = r2
            L_0x01bc:
                r4.mUpwardsWhenThresholdReached = r9
            L_0x01be:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r3 = r9.mGestureWaitForTouchSlop
                if (r3 == 0) goto L_0x01c8
                boolean r3 = r9.mTracking
                if (r3 == 0) goto L_0x0334
            L_0x01c8:
                r3 = r9
                com.android.systemui.statusbar.phone.NotificationPanelViewController r3 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r3
                boolean r4 = r3.mConflictingQsExpansionGesture
                if (r4 == 0) goto L_0x01d3
                boolean r4 = r3.mQsExpanded
                if (r4 != 0) goto L_0x01d7
            L_0x01d3:
                boolean r3 = r3.mBlockingExpansionForCurrentTouch
                if (r3 == 0) goto L_0x01d9
            L_0x01d7:
                r3 = r1
                goto L_0x01da
            L_0x01d9:
                r3 = r2
            L_0x01da:
                if (r3 != 0) goto L_0x0334
                com.android.systemui.statusbar.notification.stack.AmbientState r9 = r9.mAmbientState
                int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r0 > 0) goto L_0x01e4
                r0 = r1
                goto L_0x01e5
            L_0x01e4:
                r0 = r2
            L_0x01e5:
                java.util.Objects.requireNonNull(r9)
                r9.mIsSwipingUp = r0
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.setExpandedHeightInternal(r10)
                goto L_0x0334
            L_0x01f1:
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.PanelViewController.m244$$Nest$maddMovement(r0, r10)
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.PanelViewController.m245$$Nest$mendMotionEvent(r0, r10, r3, r9, r2)
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                android.animation.ValueAnimator r9 = r9.mHeightAnimator
                if (r9 != 0) goto L_0x0334
                int r9 = r10.getActionMasked()
                if (r9 != r1) goto L_0x021b
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                java.util.Objects.requireNonNull(r9)
                com.android.internal.jank.InteractionJankMonitor r9 = r9.mInteractionJankMonitor
                if (r9 != 0) goto L_0x0212
                goto L_0x0334
            L_0x0212:
                com.android.internal.jank.InteractionJankMonitor r9 = com.android.internal.jank.InteractionJankMonitor.getInstance()
                r9.end(r2)
                goto L_0x0334
            L_0x021b:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                java.util.Objects.requireNonNull(r9)
                com.android.internal.jank.InteractionJankMonitor r9 = r9.mInteractionJankMonitor
                if (r9 != 0) goto L_0x0226
                goto L_0x0334
            L_0x0226:
                com.android.internal.jank.InteractionJankMonitor r9 = com.android.internal.jank.InteractionJankMonitor.getInstance()
                r9.cancel(r2)
                goto L_0x0334
            L_0x022f:
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                float r4 = r0.mExpandedHeight
                r0.startExpandMotion(r3, r9, r2, r4)
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.mMinExpandHeight = r6
                boolean r0 = r9.isFullyCollapsed()
                r9.mPanelClosedOnDown = r0
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.mHasLayoutedSinceDown = r2
                r9.mUpdateFlingOnLayout = r2
                r9.mMotionAborted = r2
                long r3 = android.os.SystemClock.uptimeMillis()
                r9.mDownTime = r3
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.mTouchAboveFalsingThreshold = r2
                boolean r0 = r9.isFullyCollapsed()
                if (r0 == 0) goto L_0x0265
                com.android.systemui.statusbar.phone.PanelViewController r0 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.HeadsUpManagerPhone r0 = r0.mHeadsUpManager
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mHasPinnedNotification
                if (r0 == 0) goto L_0x0265
                r0 = r1
                goto L_0x0266
            L_0x0265:
                r0 = r2
            L_0x0266:
                r9.mCollapsedAndHeadsUpOnDown = r0
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.PanelViewController.m244$$Nest$maddMovement(r9, r10)
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                android.animation.ValueAnimator r0 = r9.mHeightAnimator
                if (r0 == 0) goto L_0x027d
                boolean r0 = r9.mHintAnimationRunning
                if (r0 != 0) goto L_0x027d
                boolean r0 = r9.mIsSpringBackAnimation
                if (r0 != 0) goto L_0x027d
                r0 = r1
                goto L_0x027e
            L_0x027d:
                r0 = r2
            L_0x027e:
                boolean r3 = r9.mGestureWaitForTouchSlop
                if (r3 == 0) goto L_0x0284
                if (r0 == 0) goto L_0x0298
            L_0x0284:
                if (r0 != 0) goto L_0x028d
                boolean r0 = r9.mTouchSlopExceededBeforeDown
                if (r0 == 0) goto L_0x028b
                goto L_0x028d
            L_0x028b:
                r0 = r2
                goto L_0x028e
            L_0x028d:
                r0 = r1
            L_0x028e:
                r9.mTouchSlopExceeded = r0
                r9.cancelHeightAnimator()
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                r9.onTrackingStarted()
            L_0x0298:
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r9 = r9.isFullyCollapsed()
                if (r9 == 0) goto L_0x0334
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.HeadsUpManagerPhone r9 = r9.mHeadsUpManager
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mHasPinnedNotification
                if (r9 != 0) goto L_0x0334
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                com.android.systemui.statusbar.phone.StatusBar r9 = r9.mStatusBar
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mBouncerShowing
                if (r9 != 0) goto L_0x0334
                com.android.systemui.statusbar.phone.PanelViewController r9 = com.android.systemui.statusbar.phone.PanelViewController.this
                java.util.Objects.requireNonNull(r9)
                r9.updatePanelExpansionAndVisibility()
                boolean r0 = r9.mVibrateOnOpening
                if (r0 == 0) goto L_0x02c7
                com.android.systemui.statusbar.VibratorHelper r0 = r9.mVibratorHelper
                r0.vibrate(r5)
            L_0x02c7:
                com.android.systemui.statusbar.phone.StatusBar r0 = r9.mStatusBar
                java.util.Objects.requireNonNull(r0)
                android.util.DisplayMetrics r0 = r0.mDisplayMetrics
                int r0 = r0.widthPixels
                float r0 = (float) r0
                com.android.systemui.statusbar.phone.StatusBar r3 = r9.mStatusBar
                java.util.Objects.requireNonNull(r3)
                android.util.DisplayMetrics r3 = r3.mDisplayMetrics
                int r3 = r3.heightPixels
                float r3 = (float) r3
                com.android.systemui.statusbar.phone.StatusBar r4 = r9.mStatusBar
                java.util.Objects.requireNonNull(r4)
                android.view.Display r4 = r4.mDisplay
                int r4 = r4.getRotation()
                com.android.systemui.statusbar.phone.LockscreenGestureLogger r5 = r9.mLockscreenGestureLogger
                r6 = 1328(0x530, float:1.861E-42)
                float r7 = r10.getX()
                float r7 = r7 / r0
                r0 = 1120403456(0x42c80000, float:100.0)
                float r7 = r7 * r0
                int r7 = (int) r7
                float r10 = r10.getY()
                float r10 = r10 / r3
                float r10 = r10 * r0
                int r10 = (int) r10
                java.util.Objects.requireNonNull(r5)
                com.android.internal.logging.MetricsLogger r0 = r5.mMetricsLogger
                android.metrics.LogMaker r3 = new android.metrics.LogMaker
                r3.<init>(r6)
                r5 = 4
                android.metrics.LogMaker r3 = r3.setType(r5)
                java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
                r6 = 1326(0x52e, float:1.858E-42)
                android.metrics.LogMaker r3 = r3.addTaggedData(r6, r5)
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
                r5 = 1327(0x52f, float:1.86E-42)
                android.metrics.LogMaker r10 = r3.addTaggedData(r5, r10)
                java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
                r4 = 1329(0x531, float:1.862E-42)
                android.metrics.LogMaker r10 = r10.addTaggedData(r4, r3)
                r0.write(r10)
                com.android.systemui.statusbar.phone.LockscreenGestureLogger r9 = r9.mLockscreenGestureLogger
                com.android.systemui.statusbar.phone.LockscreenGestureLogger$LockscreenUiEvent r10 = com.android.systemui.statusbar.phone.LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND
                java.util.Objects.requireNonNull(r9)
                com.android.systemui.statusbar.phone.LockscreenGestureLogger.log(r10)
            L_0x0334:
                com.android.systemui.statusbar.phone.PanelViewController r8 = com.android.systemui.statusbar.phone.PanelViewController.this
                boolean r9 = r8.mGestureWaitForTouchSlop
                if (r9 == 0) goto L_0x0340
                boolean r8 = r8.mTracking
                if (r8 == 0) goto L_0x033f
                goto L_0x0340
            L_0x033f:
                r1 = r2
            L_0x0340:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PanelViewController.TouchHandler.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e5, code lost:
        if ((android.os.SystemClock.uptimeMillis() - r5.mDownTime) <= 300) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e8, code lost:
        if (r9 > 0) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ef, code lost:
        if (r5.mQsExpansionAnimator == null) goto L_0x010d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0181  */
    /* renamed from: -$$Nest$mendMotionEvent  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m245$$Nest$mendMotionEvent(com.android.systemui.statusbar.phone.PanelViewController r15, android.view.MotionEvent r16, float r17, float r18, boolean r19) {
        /*
            r0 = r15
            r1 = r17
            r2 = r18
            java.util.Objects.requireNonNull(r15)
            r3 = -1
            r0.mTrackingPointer = r3
            boolean r3 = r0.mTracking
            r4 = 1
            r5 = 3
            r6 = 0
            if (r3 == 0) goto L_0x0016
            boolean r3 = r0.mTouchSlopExceeded
            if (r3 != 0) goto L_0x005e
        L_0x0016:
            float r3 = r0.mInitialTouchX
            float r3 = r1 - r3
            float r3 = java.lang.Math.abs(r3)
            int r7 = r0.mTouchSlop
            float r7 = (float) r7
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 > 0) goto L_0x005e
            float r3 = r0.mInitialTouchY
            float r3 = r2 - r3
            float r3 = java.lang.Math.abs(r3)
            int r7 = r0.mTouchSlop
            float r7 = (float) r7
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 > 0) goto L_0x005e
            int r3 = r16.getActionMasked()
            if (r3 == r5) goto L_0x005e
            if (r19 == 0) goto L_0x003d
            goto L_0x005e
        L_0x003d:
            com.android.systemui.statusbar.phone.StatusBar r1 = r0.mStatusBar
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mBouncerShowing
            if (r1 != 0) goto L_0x0183
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r1 = r0.mStatusBarKeyguardViewManager
            boolean r1 = r1.isShowingAlternateAuthOrAnimating()
            if (r1 != 0) goto L_0x0183
            com.android.systemui.statusbar.policy.KeyguardStateController r1 = r0.mKeyguardStateController
            boolean r1 = r1.isKeyguardGoingAway()
            if (r1 != 0) goto L_0x0183
            r15.onEmptySpaceClick()
            r15.onTrackingStopped(r4)
            goto L_0x0183
        L_0x005e:
            android.view.VelocityTracker r3 = r0.mVelocityTracker
            r7 = 1000(0x3e8, float:1.401E-42)
            r3.computeCurrentVelocity(r7)
            android.view.VelocityTracker r3 = r0.mVelocityTracker
            float r3 = r3.getYVelocity()
            android.view.VelocityTracker r7 = r0.mVelocityTracker
            float r7 = r7.getXVelocity()
            double r7 = (double) r7
            android.view.VelocityTracker r9 = r0.mVelocityTracker
            float r9 = r9.getYVelocity()
            double r9 = (double) r9
            double r7 = java.lang.Math.hypot(r7, r9)
            float r7 = (float) r7
            com.android.systemui.statusbar.SysuiStatusBarStateController r8 = r0.mStatusBarStateController
            int r8 = r8.getState()
            if (r8 != r4) goto L_0x0088
            r8 = r4
            goto L_0x0089
        L_0x0088:
            r8 = r6
        L_0x0089:
            int r9 = r16.getActionMasked()
            r10 = 0
            if (r9 == r5) goto L_0x00f2
            if (r19 == 0) goto L_0x0094
            goto L_0x00f2
        L_0x0094:
            r5 = r0
            com.android.systemui.statusbar.phone.NotificationPanelViewController r5 = (com.android.systemui.statusbar.phone.NotificationPanelViewController) r5
            com.android.systemui.plugins.FalsingManager r9 = r5.mFalsingManager
            boolean r9 = r9.isUnlockingDisabled()
            if (r9 == 0) goto L_0x00a0
            goto L_0x00ea
        L_0x00a0:
            int r9 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x00a6
            r13 = r6
            goto L_0x00b2
        L_0x00a6:
            com.android.systemui.statusbar.policy.KeyguardStateController r13 = r5.mKeyguardStateController
            boolean r13 = r13.canDismissLockScreen()
            if (r13 == 0) goto L_0x00b0
            r13 = 4
            goto L_0x00b2
        L_0x00b0:
            r13 = 8
        L_0x00b2:
            boolean r13 = r5.isFalseTouch(r1, r2, r13)
            if (r13 == 0) goto L_0x00b9
            goto L_0x00ea
        L_0x00b9:
            float r7 = java.lang.Math.abs(r7)
            com.android.wm.shell.animation.FlingAnimationUtils r13 = r5.mFlingAnimationUtils
            java.util.Objects.requireNonNull(r13)
            float r13 = r13.mMinVelocityPxPerSecond
            int r7 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r7 >= 0) goto L_0x00e8
            float r7 = r5.mExpandedFraction
            r9 = 1056964608(0x3f000000, float:0.5)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x00d2
            r7 = r4
            goto L_0x00d3
        L_0x00d2:
            r7 = r6
        L_0x00d3:
            if (r7 == 0) goto L_0x00d6
            goto L_0x00ea
        L_0x00d6:
            boolean r7 = r5.mAllowExpandForSmallExpansion
            if (r7 == 0) goto L_0x00ec
            long r13 = android.os.SystemClock.uptimeMillis()
            long r11 = r5.mDownTime
            long r13 = r13 - r11
            r11 = 300(0x12c, double:1.48E-321)
            int r9 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r9 > 0) goto L_0x00ec
            goto L_0x00ea
        L_0x00e8:
            if (r9 <= 0) goto L_0x00ec
        L_0x00ea:
            r9 = r4
            goto L_0x00ed
        L_0x00ec:
            r9 = r6
        L_0x00ed:
            android.animation.ValueAnimator r5 = r5.mQsExpansionAnimator
            if (r5 == 0) goto L_0x010d
            goto L_0x00fd
        L_0x00f2:
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = r0.mKeyguardStateController
            boolean r5 = r5.isKeyguardFadingAway()
            if (r5 == 0) goto L_0x00fb
            goto L_0x0107
        L_0x00fb:
            if (r8 == 0) goto L_0x00ff
        L_0x00fd:
            r9 = r4
            goto L_0x010d
        L_0x00ff:
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = r0.mKeyguardStateController
            boolean r5 = r5.isKeyguardFadingAway()
            if (r5 == 0) goto L_0x0109
        L_0x0107:
            r9 = r6
            goto L_0x010d
        L_0x0109:
            boolean r5 = r0.mPanelClosedOnDown
            r9 = r5 ^ 1
        L_0x010d:
            com.android.systemui.doze.DozeLog r5 = r0.mDozeLog
            boolean r11 = r0.mTouchAboveFalsingThreshold
            com.android.systemui.statusbar.phone.StatusBar r12 = r0.mStatusBar
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.phone.StatusBar r12 = r0.mStatusBar
            java.util.Objects.requireNonNull(r12)
            boolean r12 = r12.mWakeUpComingFromTouch
            r5.traceFling(r9, r11, r12)
            if (r9 != 0) goto L_0x014f
            if (r8 == 0) goto L_0x014f
            com.android.systemui.statusbar.phone.StatusBar r5 = r0.mStatusBar
            java.util.Objects.requireNonNull(r5)
            android.util.DisplayMetrics r5 = r5.mDisplayMetrics
            float r5 = r5.density
            float r8 = r0.mInitialTouchY
            float r8 = r2 - r8
            float r8 = r8 / r5
            float r8 = java.lang.Math.abs(r8)
            int r8 = (int) r8
            float r5 = r3 / r5
            float r5 = java.lang.Math.abs(r5)
            int r5 = (int) r5
            com.android.systemui.statusbar.phone.LockscreenGestureLogger r11 = r0.mLockscreenGestureLogger
            r12 = 186(0xba, float:2.6E-43)
            r11.write(r12, r8, r5)
            com.android.systemui.statusbar.phone.LockscreenGestureLogger r5 = r0.mLockscreenGestureLogger
            com.android.systemui.statusbar.phone.LockscreenGestureLogger$LockscreenUiEvent r8 = com.android.systemui.statusbar.phone.LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCK
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.phone.LockscreenGestureLogger.log(r8)
        L_0x014f:
            int r5 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r5 != 0) goto L_0x0155
            r11 = 7
            goto L_0x0165
        L_0x0155:
            if (r5 <= 0) goto L_0x0159
            r11 = r6
            goto L_0x0165
        L_0x0159:
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = r0.mKeyguardStateController
            boolean r5 = r5.canDismissLockScreen()
            if (r5 == 0) goto L_0x0163
            r11 = 4
            goto L_0x0165
        L_0x0163:
            r11 = 8
        L_0x0165:
            boolean r1 = r15.isFalseTouch(r1, r2, r11)
            r2 = 1065353216(0x3f800000, float:1.0)
            r15.fling(r3, r9, r2, r1)
            r15.onTrackingStopped(r9)
            if (r9 == 0) goto L_0x017c
            boolean r1 = r0.mPanelClosedOnDown
            if (r1 == 0) goto L_0x017c
            boolean r1 = r0.mHasLayoutedSinceDown
            if (r1 != 0) goto L_0x017c
            goto L_0x017d
        L_0x017c:
            r4 = r6
        L_0x017d:
            r0.mUpdateFlingOnLayout = r4
            if (r4 == 0) goto L_0x0183
            r0.mUpdateFlingVelocity = r3
        L_0x0183:
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r0.mAmbientState
            java.util.Objects.requireNonNull(r1)
            r1.mIsSwipingUp = r6
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r0.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PanelViewController.m245$$Nest$mendMotionEvent(com.android.systemui.statusbar.phone.PanelViewController, android.view.MotionEvent, float, float, boolean):void");
    }

    public PanelViewController(PanelView panelView, FalsingManager falsingManager, DozeLog dozeLog, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, VibratorHelper vibratorHelper, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LatencyTracker latencyTracker, FlingAnimationUtils.Builder builder, StatusBarTouchableRegionManager statusBarTouchableRegionManager, LockscreenGestureLogger lockscreenGestureLogger, PanelExpansionStateManager panelExpansionStateManager, AmbientState ambientState, InteractionJankMonitor interactionJankMonitor) {
        PanelView panelView2 = panelView;
        FlingAnimationUtils.Builder builder2 = builder;
        final NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
        this.mFlingCollapseRunnable = new Runnable() {
            public final void run() {
                PanelViewController panelViewController = notificationPanelViewController;
                panelViewController.fling(0.0f, false, panelViewController.mNextCollapseSpeedUpFactor, false);
            }
        };
        keyguardStateController.addCallback(new KeyguardStateController.Callback() {
            public final void onKeyguardFadingAwayChanged() {
                notificationPanelViewController.requestPanelHeightUpdate();
            }
        });
        this.mAmbientState = ambientState;
        this.mView = panelView2;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLockscreenGestureLogger = lockscreenGestureLogger;
        this.mPanelExpansionStateManager = panelExpansionStateManager;
        NotificationPanelViewController.C148217 r4 = new TouchHandler() {
            public long mLastTouchDownTime = -1;

            /* JADX WARNING: Removed duplicated region for block: B:327:0x0559 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:32:0x0075  */
            /* JADX WARNING: Removed duplicated region for block: B:53:0x00c7 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:54:0x00c8  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final boolean onTouch(android.view.View r18, android.view.MotionEvent r19) {
                /*
                    r17 = this;
                    r0 = r17
                    r1 = r19
                    int r2 = r19.getAction()
                    r3 = 1
                    if (r2 != 0) goto L_0x0025
                    long r4 = r19.getDownTime()
                    long r6 = r0.mLastTouchDownTime
                    int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    if (r2 != 0) goto L_0x001f
                    int r0 = com.android.systemui.statusbar.phone.PanelViewController.$r8$clinit
                    java.lang.String r0 = "PanelView"
                    java.lang.String r1 = "Duplicate down event detected... ignoring"
                    android.util.Log.w(r0, r1)
                    return r3
                L_0x001f:
                    long r4 = r19.getDownTime()
                    r0.mLastTouchDownTime = r4
                L_0x0025:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r4 = r2.mBlockTouches
                    r5 = 0
                    if (r4 != 0) goto L_0x05c2
                    boolean r4 = r2.mQsFullyExpanded
                    if (r4 == 0) goto L_0x003c
                    com.android.systemui.plugins.qs.QS r2 = r2.mQs
                    if (r2 == 0) goto L_0x003c
                    boolean r2 = r2.disallowPanelTouches()
                    if (r2 == 0) goto L_0x003c
                    goto L_0x05c2
                L_0x003c:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBar r2 = r2.mStatusBar
                    java.util.Objects.requireNonNull(r2)
                    boolean r4 = r2.mBouncerShowing
                    if (r4 == 0) goto L_0x0051
                    com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r2 = r2.mStatusBarKeyguardViewManager
                    boolean r2 = r2.bouncerNeedsScrimming()
                    if (r2 == 0) goto L_0x0051
                    r2 = r3
                    goto L_0x0052
                L_0x0051:
                    r2 = r5
                L_0x0052:
                    if (r2 != 0) goto L_0x05c2
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBar r2 = r2.mStatusBar
                    java.util.Objects.requireNonNull(r2)
                    boolean r4 = r2.mBouncerShowing
                    if (r4 == 0) goto L_0x0070
                    com.android.systemui.dreams.DreamOverlayStateController r2 = r2.mDreamOverlayStateController
                    java.util.Objects.requireNonNull(r2)
                    int r2 = r2.mState
                    r2 = r2 & r3
                    if (r2 == 0) goto L_0x006b
                    r2 = r3
                    goto L_0x006c
                L_0x006b:
                    r2 = r5
                L_0x006c:
                    if (r2 == 0) goto L_0x0070
                    r2 = r3
                    goto L_0x0071
                L_0x0070:
                    r2 = r5
                L_0x0071:
                    if (r2 == 0) goto L_0x0075
                    goto L_0x05c2
                L_0x0075:
                    int r2 = r19.getAction()
                    r4 = 3
                    if (r2 == r3) goto L_0x0082
                    int r2 = r19.getAction()
                    if (r2 != r4) goto L_0x0086
                L_0x0082:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    r2.mBlockingExpansionForCurrentTouch = r5
                L_0x0086:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r2 = r2.mLastEventSynthesizedDown
                    if (r2 == 0) goto L_0x0097
                    int r2 = r19.getAction()
                    if (r2 != r3) goto L_0x0097
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    r2.expand(r3)
                L_0x0097:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.NotificationPanelViewController.m239$$Nest$minitDownStates(r2, r1)
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r6 = r2.mIsExpanding
                    r7 = 0
                    if (r6 != 0) goto L_0x00ad
                    float r6 = r2.mDownX
                    float r8 = r2.mDownY
                    boolean r2 = r2.shouldQuickSettingsIntercept(r6, r8, r7)
                    if (r2 == 0) goto L_0x00b8
                L_0x00ad:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.PulseExpansionHandler r2 = r2.mPulseExpansionHandler
                    java.util.Objects.requireNonNull(r2)
                    boolean r2 = r2.isExpanding
                    if (r2 == 0) goto L_0x00ba
                L_0x00b8:
                    r2 = r3
                    goto L_0x00bb
                L_0x00ba:
                    r2 = r5
                L_0x00bb:
                    if (r2 == 0) goto L_0x00c8
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.PulseExpansionHandler r2 = r2.mPulseExpansionHandler
                    boolean r2 = r2.onTouchEvent(r1)
                    if (r2 == 0) goto L_0x00c8
                    return r3
                L_0x00c8:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r6 = r2.mListenForHeadsUp
                    if (r6 == 0) goto L_0x00fa
                    com.android.systemui.statusbar.phone.HeadsUpTouchHelper r2 = r2.mHeadsUpTouchHelper
                    java.util.Objects.requireNonNull(r2)
                    boolean r2 = r2.mTrackingHeadsUp
                    if (r2 != 0) goto L_0x00fa
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = r2.mNotificationStackScrollLayoutController
                    java.util.Objects.requireNonNull(r2)
                    android.view.View r2 = r2.mLongPressedView
                    if (r2 == 0) goto L_0x00e4
                    r2 = r3
                    goto L_0x00e5
                L_0x00e4:
                    r2 = r5
                L_0x00e5:
                    if (r2 != 0) goto L_0x00fa
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.HeadsUpTouchHelper r2 = r2.mHeadsUpTouchHelper
                    boolean r2 = r2.onInterceptTouchEvent(r1)
                    if (r2 == 0) goto L_0x00fa
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.internal.logging.MetricsLogger r2 = r2.mMetricsLogger
                    java.lang.String r6 = "panel_open_peek"
                    r2.count(r6, r3)
                L_0x00fa:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r6 = r2.mIsExpanding
                    r8 = 6
                    r9 = 5
                    r11 = 2
                    if (r6 == 0) goto L_0x0107
                    boolean r6 = r2.mHintAnimationRunning
                    if (r6 == 0) goto L_0x02ae
                L_0x0107:
                    boolean r6 = r2.mQsExpanded
                    if (r6 != 0) goto L_0x02ae
                    int r6 = r2.mBarState
                    if (r6 == 0) goto L_0x02ae
                    boolean r6 = r2.mDozing
                    if (r6 != 0) goto L_0x02ae
                    com.android.systemui.statusbar.phone.KeyguardAffordanceHelper r2 = r2.mAffordanceHelper
                    java.util.Objects.requireNonNull(r2)
                    int r6 = r19.getActionMasked()
                    boolean r12 = r2.mMotionCancelled
                    if (r12 == 0) goto L_0x0124
                    if (r6 == 0) goto L_0x0124
                    goto L_0x02ab
                L_0x0124:
                    float r12 = r19.getY()
                    float r13 = r19.getX()
                    if (r6 == 0) goto L_0x0242
                    if (r6 == r3) goto L_0x0180
                    if (r6 == r11) goto L_0x0141
                    if (r6 == r4) goto L_0x013f
                    if (r6 == r9) goto L_0x0138
                    goto L_0x02a7
                L_0x0138:
                    r2.mMotionCancelled = r3
                    r2.endMotion(r3, r13, r12)
                    goto L_0x02a7
                L_0x013f:
                    r9 = r5
                    goto L_0x0181
                L_0x0141:
                    android.view.VelocityTracker r6 = r2.mVelocityTracker
                    if (r6 == 0) goto L_0x0148
                    r6.addMovement(r1)
                L_0x0148:
                    float r6 = r2.mInitialTouchX
                    float r13 = r13 - r6
                    float r6 = r2.mInitialTouchY
                    float r12 = r12 - r6
                    double r13 = (double) r13
                    double r9 = (double) r12
                    double r9 = java.lang.Math.hypot(r13, r9)
                    float r9 = (float) r9
                    boolean r10 = r2.mTouchSlopExeeded
                    if (r10 != 0) goto L_0x0162
                    int r10 = r2.mTouchSlop
                    float r10 = (float) r10
                    int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                    if (r10 <= 0) goto L_0x0162
                    r2.mTouchSlopExeeded = r3
                L_0x0162:
                    boolean r10 = r2.mSwipingInProgress
                    if (r10 == 0) goto L_0x02a7
                    android.view.View r10 = r2.mTargetedView
                    com.android.systemui.statusbar.KeyguardAffordanceView r12 = r2.mRightIcon
                    if (r10 != r12) goto L_0x0174
                    float r10 = r2.mTranslationOnDown
                    float r10 = r10 - r9
                    float r9 = java.lang.Math.min(r7, r10)
                    goto L_0x017b
                L_0x0174:
                    float r10 = r2.mTranslationOnDown
                    float r10 = r10 + r9
                    float r9 = java.lang.Math.max(r7, r10)
                L_0x017b:
                    r2.setTranslation(r9, r5, r5)
                    goto L_0x02a7
                L_0x0180:
                    r9 = r3
                L_0x0181:
                    android.view.View r10 = r2.mTargetedView
                    com.android.systemui.statusbar.KeyguardAffordanceView r14 = r2.mRightIcon
                    if (r10 != r14) goto L_0x0189
                    r10 = r3
                    goto L_0x018a
                L_0x0189:
                    r10 = r5
                L_0x018a:
                    android.view.VelocityTracker r14 = r2.mVelocityTracker
                    if (r14 == 0) goto L_0x0191
                    r14.addMovement(r1)
                L_0x0191:
                    r14 = r9 ^ 1
                    r2.endMotion(r14, r13, r12)
                    boolean r12 = r2.mTouchSlopExeeded
                    if (r12 != 0) goto L_0x02a7
                    if (r9 == 0) goto L_0x02a7
                    com.android.systemui.statusbar.phone.KeyguardAffordanceHelper$Callback r2 = r2.mCallback
                    com.android.systemui.statusbar.phone.NotificationPanelViewController$KeyguardAffordanceHelperCallback r2 = (com.android.systemui.statusbar.phone.NotificationPanelViewController.KeyguardAffordanceHelperCallback) r2
                    java.util.Objects.requireNonNull(r2)
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r9 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r12 = r9.mHintAnimationRunning
                    if (r12 == 0) goto L_0x01ab
                    goto L_0x02a7
                L_0x01ab:
                    r9.mHintAnimationRunning = r3
                    com.android.systemui.statusbar.phone.KeyguardAffordanceHelper r9 = r9.mAffordanceHelper
                    com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0 r12 = new com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0
                    r12.<init>(r2, r8)
                    java.util.Objects.requireNonNull(r9)
                    android.animation.Animator r13 = r9.mSwipeAnimator
                    if (r13 == 0) goto L_0x01be
                    r13.cancel()
                L_0x01be:
                    if (r10 == 0) goto L_0x01c3
                    com.android.systemui.statusbar.KeyguardAffordanceView r13 = r9.mRightIcon
                    goto L_0x01c5
                L_0x01c3:
                    com.android.systemui.statusbar.KeyguardAffordanceView r13 = r9.mLeftIcon
                L_0x01c5:
                    int r14 = r9.mHintGrowAmount
                    android.animation.ValueAnimator r14 = r9.getAnimatorToRadius(r10, r14)
                    com.android.systemui.statusbar.phone.KeyguardAffordanceHelper$3 r6 = new com.android.systemui.statusbar.phone.KeyguardAffordanceHelper$3
                    r6.<init>(r12, r10)
                    r14.addListener(r6)
                    android.view.animation.PathInterpolator r6 = com.android.systemui.animation.Interpolators.LINEAR_OUT_SLOW_IN
                    r14.setInterpolator(r6)
                    r7 = 200(0xc8, double:9.9E-322)
                    r14.setDuration(r7)
                    r14.start()
                    r9.mSwipeAnimator = r14
                    r9.mTargetedView = r13
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.NotificationPanelView r7 = r7.mView
                    int r7 = r7.getLayoutDirection()
                    if (r7 != r3) goto L_0x01f3
                    if (r10 != 0) goto L_0x01f2
                    r10 = r3
                    goto L_0x01f3
                L_0x01f2:
                    r10 = r5
                L_0x01f3:
                    if (r10 == 0) goto L_0x020b
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBar r2 = r2.mStatusBar
                    java.util.Objects.requireNonNull(r2)
                    com.android.systemui.classifier.FalsingCollector r7 = r2.mFalsingCollector
                    r7.onCameraHintStarted()
                    com.android.systemui.statusbar.KeyguardIndicationController r2 = r2.mKeyguardIndicationController
                    r7 = 2131952090(0x7f1301da, float:1.9540613E38)
                    r2.showTransientIndication((int) r7)
                    goto L_0x02a7
                L_0x020b:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.KeyguardBottomAreaView r7 = r7.mKeyguardBottomArea
                    java.util.Objects.requireNonNull(r7)
                    boolean r7 = r7.mLeftIsVoiceAssist
                    if (r7 == 0) goto L_0x022c
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBar r2 = r2.mStatusBar
                    java.util.Objects.requireNonNull(r2)
                    com.android.systemui.classifier.FalsingCollector r7 = r2.mFalsingCollector
                    r7.onLeftAffordanceHintStarted()
                    com.android.systemui.statusbar.KeyguardIndicationController r2 = r2.mKeyguardIndicationController
                    r7 = 2131953497(0x7f130759, float:1.9543467E38)
                    r2.showTransientIndication((int) r7)
                    goto L_0x02a7
                L_0x022c:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBar r2 = r2.mStatusBar
                    java.util.Objects.requireNonNull(r2)
                    com.android.systemui.classifier.FalsingCollector r7 = r2.mFalsingCollector
                    r7.onLeftAffordanceHintStarted()
                    com.android.systemui.statusbar.KeyguardIndicationController r2 = r2.mKeyguardIndicationController
                    r7 = 2131952969(0x7f130549, float:1.9542396E38)
                    r2.showTransientIndication((int) r7)
                    goto L_0x02a7
                L_0x0242:
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mLeftIcon
                    int r7 = r7.getVisibility()
                    if (r7 != 0) goto L_0x024c
                    r7 = r3
                    goto L_0x024d
                L_0x024c:
                    r7 = r5
                L_0x024d:
                    if (r7 == 0) goto L_0x025a
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mLeftIcon
                    boolean r7 = r2.isOnIcon(r7, r13, r12)
                    if (r7 == 0) goto L_0x025a
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mLeftIcon
                    goto L_0x0273
                L_0x025a:
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mRightIcon
                    int r7 = r7.getVisibility()
                    if (r7 != 0) goto L_0x0264
                    r7 = r3
                    goto L_0x0265
                L_0x0264:
                    r7 = r5
                L_0x0265:
                    if (r7 == 0) goto L_0x0272
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mRightIcon
                    boolean r7 = r2.isOnIcon(r7, r13, r12)
                    if (r7 == 0) goto L_0x0272
                    com.android.systemui.statusbar.KeyguardAffordanceView r7 = r2.mRightIcon
                    goto L_0x0273
                L_0x0272:
                    r7 = 0
                L_0x0273:
                    if (r7 == 0) goto L_0x02a9
                    android.view.View r8 = r2.mTargetedView
                    if (r8 == 0) goto L_0x027c
                    if (r8 == r7) goto L_0x027c
                    goto L_0x02a9
                L_0x027c:
                    if (r8 == 0) goto L_0x0286
                    android.animation.Animator r8 = r2.mSwipeAnimator
                    if (r8 == 0) goto L_0x0288
                    r8.cancel()
                    goto L_0x0288
                L_0x0286:
                    r2.mTouchSlopExeeded = r5
                L_0x0288:
                    r2.startSwiping(r7)
                    r2.mInitialTouchX = r13
                    r2.mInitialTouchY = r12
                    float r7 = r2.mTranslation
                    r2.mTranslationOnDown = r7
                    android.view.VelocityTracker r7 = r2.mVelocityTracker
                    if (r7 == 0) goto L_0x029a
                    r7.recycle()
                L_0x029a:
                    android.view.VelocityTracker r7 = android.view.VelocityTracker.obtain()
                    r2.mVelocityTracker = r7
                    if (r7 == 0) goto L_0x02a5
                    r7.addMovement(r1)
                L_0x02a5:
                    r2.mMotionCancelled = r5
                L_0x02a7:
                    r2 = r3
                    goto L_0x02ac
                L_0x02a9:
                    r2.mMotionCancelled = r3
                L_0x02ab:
                    r2 = r5
                L_0x02ac:
                    r2 = r2 | r5
                    goto L_0x02af
                L_0x02ae:
                    r2 = r5
                L_0x02af:
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r8 = r7.mOnlyAffordanceInThisMotion
                    if (r8 == 0) goto L_0x02b6
                    return r3
                L_0x02b6:
                    com.android.systemui.statusbar.phone.HeadsUpTouchHelper r7 = r7.mHeadsUpTouchHelper
                    boolean r7 = r7.onTouchEvent(r1)
                    r2 = r2 | r7
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.HeadsUpTouchHelper r7 = r7.mHeadsUpTouchHelper
                    java.util.Objects.requireNonNull(r7)
                    boolean r7 = r7.mTrackingHeadsUp
                    if (r7 != 0) goto L_0x055a
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    java.util.Objects.requireNonNull(r7)
                    boolean r8 = r7.mShouldUseSplitNotificationShade
                    if (r8 == 0) goto L_0x02dd
                    float r8 = r19.getX()
                    boolean r8 = r7.touchXOutsideOfQs(r8)
                    if (r8 == 0) goto L_0x02dd
                    goto L_0x0556
                L_0x02dd:
                    int r8 = r19.getActionMasked()
                    r9 = 1065353216(0x3f800000, float:1.0)
                    if (r8 != 0) goto L_0x0313
                    float r10 = r7.mExpandedFraction
                    int r10 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
                    if (r10 != 0) goto L_0x0313
                    int r10 = r7.mBarState
                    if (r10 == r3) goto L_0x0313
                    boolean r10 = r7.mQsExpanded
                    if (r10 != 0) goto L_0x0313
                    boolean r10 = r7.isQsExpansionEnabled()
                    if (r10 == 0) goto L_0x0313
                    r7.mQsTracking = r3
                    r7.traceQsJank(r3, r5)
                    r7.mConflictingQsExpansionGesture = r3
                    r7.onQsExpansionStarted()
                    float r10 = r7.mQsExpansionHeight
                    r7.mInitialHeightOnTouch = r10
                    float r10 = r19.getX()
                    r7.mInitialTouchY = r10
                    float r10 = r19.getY()
                    r7.mInitialTouchX = r10
                L_0x0313:
                    boolean r10 = r7.isFullyCollapsed()
                    if (r10 != 0) goto L_0x034c
                    int r10 = r19.getActionMasked()
                    if (r10 != 0) goto L_0x034c
                    float r10 = r19.getX()
                    float r12 = r19.getY()
                    r13 = -1082130432(0xffffffffbf800000, float:-1.0)
                    boolean r10 = r7.shouldQuickSettingsIntercept(r10, r12, r13)
                    if (r10 == 0) goto L_0x034c
                    com.android.systemui.classifier.FalsingCollector r10 = r7.mFalsingCollector
                    r10.onQsDown()
                    r7.mQsTracking = r3
                    r7.onQsExpansionStarted()
                    float r10 = r7.mQsExpansionHeight
                    r7.mInitialHeightOnTouch = r10
                    float r10 = r19.getX()
                    r7.mInitialTouchY = r10
                    float r10 = r19.getY()
                    r7.mInitialTouchX = r10
                    r7.notifyExpandingFinished()
                L_0x034c:
                    boolean r10 = r7.mQsExpandImmediate
                    if (r10 != 0) goto L_0x04d6
                    boolean r10 = r7.mQsTracking
                    if (r10 == 0) goto L_0x04d6
                    int r10 = r7.mTrackingPointer
                    int r10 = r1.findPointerIndex(r10)
                    if (r10 >= 0) goto L_0x0363
                    int r10 = r1.getPointerId(r5)
                    r7.mTrackingPointer = r10
                    r10 = r5
                L_0x0363:
                    float r12 = r1.getY(r10)
                    float r10 = r1.getX(r10)
                    float r13 = r7.mInitialTouchY
                    float r13 = r12 - r13
                    int r14 = r19.getActionMasked()
                    if (r14 == 0) goto L_0x04af
                    if (r14 == r3) goto L_0x03cf
                    if (r14 == r11) goto L_0x03ad
                    if (r14 == r4) goto L_0x03cf
                    r9 = 6
                    if (r14 == r9) goto L_0x0380
                    goto L_0x04cf
                L_0x0380:
                    int r6 = r19.getActionIndex()
                    int r6 = r1.getPointerId(r6)
                    int r9 = r7.mTrackingPointer
                    if (r9 != r6) goto L_0x04cf
                    int r9 = r1.getPointerId(r5)
                    if (r9 == r6) goto L_0x0394
                    r6 = r5
                    goto L_0x0395
                L_0x0394:
                    r6 = r3
                L_0x0395:
                    float r9 = r1.getY(r6)
                    float r10 = r1.getX(r6)
                    int r6 = r1.getPointerId(r6)
                    r7.mTrackingPointer = r6
                    float r6 = r7.mQsExpansionHeight
                    r7.mInitialHeightOnTouch = r6
                    r7.mInitialTouchY = r9
                    r7.mInitialTouchX = r10
                    goto L_0x04cf
                L_0x03ad:
                    float r6 = r7.mInitialHeightOnTouch
                    float r6 = r6 + r13
                    r7.setQsExpansion(r6)
                    com.android.systemui.statusbar.phone.StatusBar r6 = r7.mStatusBar
                    java.util.Objects.requireNonNull(r6)
                    boolean r6 = r6.mWakeUpComingFromTouch
                    if (r6 == 0) goto L_0x03be
                    r9 = 1069547520(0x3fc00000, float:1.5)
                L_0x03be:
                    int r6 = r7.mQsFalsingThreshold
                    float r6 = (float) r6
                    float r6 = r6 * r9
                    int r6 = (int) r6
                    float r6 = (float) r6
                    int r6 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
                    if (r6 < 0) goto L_0x03ca
                    r7.mQsTouchAboveFalsingThreshold = r3
                L_0x03ca:
                    r7.trackMovement(r1)
                    goto L_0x04cf
                L_0x03cf:
                    r7.mQsTracking = r5
                    r9 = -1
                    r7.mTrackingPointer = r9
                    r7.trackMovement(r1)
                    float r9 = r7.computeQsExpansionFraction()
                    r6 = 0
                    int r9 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                    if (r9 != 0) goto L_0x03f6
                    float r9 = r7.mInitialTouchY
                    int r9 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
                    if (r9 < 0) goto L_0x03e7
                    goto L_0x03f6
                L_0x03e7:
                    int r6 = r19.getActionMasked()
                    if (r6 != r4) goto L_0x03ef
                    r6 = r3
                    goto L_0x03f0
                L_0x03ef:
                    r6 = r5
                L_0x03f0:
                    r7.traceQsJank(r5, r6)
                    r9 = 0
                    goto L_0x04a5
                L_0x03f6:
                    int r9 = r19.getActionMasked()
                    if (r9 != r4) goto L_0x03fe
                    r9 = r3
                    goto L_0x03ff
                L_0x03fe:
                    r9 = r5
                L_0x03ff:
                    android.view.VelocityTracker r10 = r7.mQsVelocityTracker
                    r13 = 1000(0x3e8, float:1.401E-42)
                    if (r10 != 0) goto L_0x0407
                    r10 = 0
                    goto L_0x0410
                L_0x0407:
                    r10.computeCurrentVelocity(r13)
                    android.view.VelocityTracker r10 = r7.mQsVelocityTracker
                    float r10 = r10.getYVelocity()
                L_0x0410:
                    float r14 = java.lang.Math.abs(r10)
                    com.android.wm.shell.animation.FlingAnimationUtils r6 = r7.mFlingAnimationUtils
                    java.util.Objects.requireNonNull(r6)
                    float r6 = r6.mMinVelocityPxPerSecond
                    int r6 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
                    if (r6 >= 0) goto L_0x042a
                    float r6 = r7.computeQsExpansionFraction()
                    r14 = 1056964608(0x3f000000, float:0.5)
                    int r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
                    if (r6 <= 0) goto L_0x0431
                    goto L_0x042f
                L_0x042a:
                    r6 = 0
                    int r14 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                    if (r14 <= 0) goto L_0x0431
                L_0x042f:
                    r14 = r3
                    goto L_0x0432
                L_0x0431:
                    r14 = r5
                L_0x0432:
                    if (r14 == 0) goto L_0x048e
                    com.android.systemui.plugins.FalsingManager r6 = r7.mFalsingManager
                    boolean r6 = r6.isUnlockingDisabled()
                    if (r6 != 0) goto L_0x048c
                    com.android.systemui.plugins.FalsingManager r6 = r7.mFalsingManager
                    boolean r6 = r6.isClassifierEnabled()
                    if (r6 == 0) goto L_0x044b
                    com.android.systemui.plugins.FalsingManager r6 = r7.mFalsingManager
                    boolean r6 = r6.isFalseTouch(r5)
                    goto L_0x044e
                L_0x044b:
                    boolean r6 = r7.mQsTouchAboveFalsingThreshold
                    r6 = r6 ^ r3
                L_0x044e:
                    if (r6 == 0) goto L_0x0451
                    goto L_0x048c
                L_0x0451:
                    android.view.VelocityTracker r6 = r7.mQsVelocityTracker
                    if (r6 != 0) goto L_0x0458
                    r16 = 0
                    goto L_0x0463
                L_0x0458:
                    r6.computeCurrentVelocity(r13)
                    android.view.VelocityTracker r6 = r7.mQsVelocityTracker
                    float r6 = r6.getYVelocity()
                    r16 = r6
                L_0x0463:
                    int r6 = r7.mBarState
                    if (r6 != r3) goto L_0x046a
                    r6 = 193(0xc1, float:2.7E-43)
                    goto L_0x046c
                L_0x046a:
                    r6 = 194(0xc2, float:2.72E-43)
                L_0x046c:
                    com.android.systemui.statusbar.phone.LockscreenGestureLogger r13 = r7.mLockscreenGestureLogger
                    float r15 = r7.mInitialTouchY
                    float r12 = r12 - r15
                    com.android.systemui.statusbar.phone.StatusBar r15 = r7.mStatusBar
                    java.util.Objects.requireNonNull(r15)
                    android.util.DisplayMetrics r15 = r15.mDisplayMetrics
                    float r15 = r15.density
                    float r12 = r12 / r15
                    int r12 = (int) r12
                    com.android.systemui.statusbar.phone.StatusBar r15 = r7.mStatusBar
                    java.util.Objects.requireNonNull(r15)
                    android.util.DisplayMetrics r15 = r15.mDisplayMetrics
                    float r15 = r15.density
                    float r15 = r16 / r15
                    int r15 = (int) r15
                    r13.write(r6, r12, r15)
                    goto L_0x049a
                L_0x048c:
                    r14 = r5
                    goto L_0x049a
                L_0x048e:
                    r6 = 0
                    int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                    if (r6 >= 0) goto L_0x049a
                    com.android.systemui.plugins.FalsingManager r6 = r7.mFalsingManager
                    r12 = 12
                    r6.isFalseTouch(r12)
                L_0x049a:
                    if (r14 == 0) goto L_0x04a0
                    if (r9 != 0) goto L_0x04a0
                    r6 = r5
                    goto L_0x04a1
                L_0x04a0:
                    r6 = r3
                L_0x04a1:
                    r9 = 0
                    r7.flingSettings(r10, r6, r9, r5)
                L_0x04a5:
                    android.view.VelocityTracker r6 = r7.mQsVelocityTracker
                    if (r6 == 0) goto L_0x04cf
                    r6.recycle()
                    r7.mQsVelocityTracker = r9
                    goto L_0x04cf
                L_0x04af:
                    r7.mQsTracking = r3
                    r7.traceQsJank(r3, r5)
                    r7.mInitialTouchY = r12
                    r7.mInitialTouchX = r10
                    r7.onQsExpansionStarted()
                    float r6 = r7.mQsExpansionHeight
                    r7.mInitialHeightOnTouch = r6
                    android.view.VelocityTracker r6 = r7.mQsVelocityTracker
                    if (r6 == 0) goto L_0x04c6
                    r6.recycle()
                L_0x04c6:
                    android.view.VelocityTracker r6 = android.view.VelocityTracker.obtain()
                    r7.mQsVelocityTracker = r6
                    r7.trackMovement(r1)
                L_0x04cf:
                    boolean r6 = r7.mConflictingQsExpansionGesture
                    if (r6 != 0) goto L_0x04d6
                    r4 = r3
                    goto L_0x0557
                L_0x04d6:
                    if (r8 == r4) goto L_0x04da
                    if (r8 != r3) goto L_0x04dc
                L_0x04da:
                    r7.mConflictingQsExpansionGesture = r5
                L_0x04dc:
                    if (r8 != 0) goto L_0x04ec
                    boolean r4 = r7.isFullyCollapsed()
                    if (r4 == 0) goto L_0x04ec
                    boolean r4 = r7.isQsExpansionEnabled()
                    if (r4 == 0) goto L_0x04ec
                    r7.mTwoFingerQsExpandPossible = r3
                L_0x04ec:
                    boolean r4 = r7.mTwoFingerQsExpandPossible
                    if (r4 == 0) goto L_0x0556
                    int r4 = r19.getPointerCount()
                    int r6 = r19.getActionMasked()
                    r8 = 5
                    if (r6 != r8) goto L_0x04ff
                    if (r4 != r11) goto L_0x04ff
                    r4 = r3
                    goto L_0x0500
                L_0x04ff:
                    r4 = r5
                L_0x0500:
                    if (r6 != 0) goto L_0x0514
                    r8 = 32
                    boolean r8 = r1.isButtonPressed(r8)
                    if (r8 != 0) goto L_0x0512
                    r8 = 64
                    boolean r8 = r1.isButtonPressed(r8)
                    if (r8 == 0) goto L_0x0514
                L_0x0512:
                    r8 = r3
                    goto L_0x0515
                L_0x0514:
                    r8 = r5
                L_0x0515:
                    if (r6 != 0) goto L_0x0526
                    boolean r6 = r1.isButtonPressed(r11)
                    if (r6 != 0) goto L_0x0524
                    r6 = 4
                    boolean r6 = r1.isButtonPressed(r6)
                    if (r6 == 0) goto L_0x0526
                L_0x0524:
                    r6 = r3
                    goto L_0x0527
                L_0x0526:
                    r6 = r5
                L_0x0527:
                    if (r4 != 0) goto L_0x0530
                    if (r8 != 0) goto L_0x0530
                    if (r6 == 0) goto L_0x052e
                    goto L_0x0530
                L_0x052e:
                    r4 = r5
                    goto L_0x0531
                L_0x0530:
                    r4 = r3
                L_0x0531:
                    if (r4 == 0) goto L_0x0556
                    int r4 = r19.getActionIndex()
                    float r4 = r1.getY(r4)
                    int r6 = r7.mStatusBarMinHeight
                    float r6 = (float) r6
                    int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    if (r4 >= 0) goto L_0x0556
                    com.android.internal.logging.MetricsLogger r4 = r7.mMetricsLogger
                    java.lang.String r6 = "panel_open_qs"
                    r4.count(r6, r3)
                    r7.mQsExpandImmediate = r3
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = r7.mNotificationStackScrollLayoutController
                    r4.setShouldShowShelfOnly(r3)
                    r7.requestPanelHeightUpdate()
                    r7.setListening(r3)
                L_0x0556:
                    r4 = r5
                L_0x0557:
                    if (r4 == 0) goto L_0x055a
                    return r3
                L_0x055a:
                    int r4 = r19.getActionMasked()
                    if (r4 != 0) goto L_0x0572
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r4 = r4.isFullyCollapsed()
                    if (r4 == 0) goto L_0x0572
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r2 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.internal.logging.MetricsLogger r2 = r2.mMetricsLogger
                    java.lang.String r4 = "panel_open"
                    r2.count(r4, r3)
                    r2 = r3
                L_0x0572:
                    int r4 = r19.getActionMasked()
                    if (r4 != 0) goto L_0x05ae
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r4 = r4.isFullyExpanded()
                    if (r4 == 0) goto L_0x05ae
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r4 = r4.mStatusBarKeyguardViewManager
                    java.util.Objects.requireNonNull(r4)
                    boolean r4 = r4.mShowing
                    if (r4 == 0) goto L_0x05ae
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r4 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r4 = r4.mStatusBarKeyguardViewManager
                    float r6 = r19.getX()
                    java.util.Objects.requireNonNull(r4)
                    com.android.systemui.statusbar.phone.KeyguardBouncer r4 = r4.mBouncer
                    if (r4 == 0) goto L_0x05ae
                    com.android.keyguard.KeyguardHostViewController r4 = r4.mKeyguardViewController
                    if (r4 == 0) goto L_0x05ae
                    com.android.keyguard.KeyguardSecurityContainerController r4 = r4.mKeyguardSecurityContainerController
                    if (r4 == 0) goto L_0x05ae
                    T r4 = r4.mView
                    com.android.keyguard.KeyguardSecurityContainer r4 = (com.android.keyguard.KeyguardSecurityContainer) r4
                    java.util.Objects.requireNonNull(r4)
                    com.android.keyguard.KeyguardSecurityContainer$ViewMode r4 = r4.mViewMode
                    r4.updatePositionByTouchX(r6)
                L_0x05ae:
                    boolean r1 = super.onTouch(r18, r19)
                    r1 = r1 | r2
                    com.android.systemui.statusbar.phone.NotificationPanelViewController r0 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
                    boolean r2 = r0.mDozing
                    if (r2 == 0) goto L_0x05c1
                    boolean r0 = r0.mPulsing
                    if (r0 != 0) goto L_0x05c1
                    if (r1 == 0) goto L_0x05c0
                    goto L_0x05c1
                L_0x05c0:
                    r3 = r5
                L_0x05c1:
                    return r3
                L_0x05c2:
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationPanelViewController.C148217.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        };
        this.mTouchHandler = r4;
        panelView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public final void onViewDetachedFromWindow(View view) {
            }

            public final void onViewAttachedToWindow(View view) {
                PanelViewController panelViewController = notificationPanelViewController;
                panelViewController.mResources.getResourceName(panelViewController.mView.getId());
                Objects.requireNonNull(panelViewController);
            }
        });
        panelView.addOnLayoutChangeListener(new NotificationPanelViewController.OnLayoutChangeListener());
        panelView.setOnTouchListener(r4);
        panelView2.mOnConfigurationChangedListener = new NotificationPanelViewController.OnConfigurationChangedListener();
        Resources resources = panelView.getResources();
        this.mResources = resources;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        builder.reset();
        builder2.mMaxLengthSeconds = 0.6f;
        builder2.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtils = builder.build();
        builder.reset();
        builder2.mMaxLengthSeconds = 0.6f;
        builder2.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtilsClosing = builder.build();
        builder.reset();
        builder2.mMaxLengthSeconds = 0.5f;
        builder2.mSpeedUpFactor = 0.6f;
        builder2.mX2 = 0.6f;
        builder2.mY2 = 0.84f;
        this.mFlingAnimationUtilsDismissing = builder.build();
        this.mLatencyTracker = latencyTracker;
        this.mBounceInterpolator = new BounceInterpolator();
        this.mFalsingManager = falsingManager;
        this.mDozeLog = dozeLog;
        this.mNotificationsDragEnabled = resources.getBoolean(C1777R.bool.config_enableNotificationShadeDrag);
        this.mVibratorHelper = vibratorHelper;
        this.mVibrateOnOpening = resources.getBoolean(C1777R.bool.config_vibrateOnIconAnimation);
        this.mStatusBarTouchableRegionManager = statusBarTouchableRegionManager;
        this.mInteractionJankMonitor = interactionJankMonitor;
    }

    public abstract void expand(boolean z);

    public abstract int getMaxPanelHeight();

    public abstract void loadDimens();

    public abstract void onExpandingStarted();

    public abstract void onFlingEnd(boolean z);

    public abstract void onTrackingStarted();

    public abstract void onTrackingStopped(boolean z);

    public abstract void setIsClosing(boolean z);

    public abstract void setOverExpansion(float f);

    public final void beginJankMonitoring() {
        String str;
        if (this.mInteractionJankMonitor != null) {
            InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(0, this.mView);
            if (isFullyCollapsed()) {
                str = "Expand";
            } else {
                str = "Collapse";
            }
            this.mInteractionJankMonitor.begin(withView.setTag(str));
        }
    }

    public final void cancelHeightAnimator() {
        ValueAnimator valueAnimator = this.mHeightAnimator;
        if (valueAnimator != null) {
            if (valueAnimator.isRunning()) {
                this.mPanelUpdateWhenAnimatorEnds = false;
            }
            this.mHeightAnimator.cancel();
        }
        endClosing();
    }

    public final ValueAnimator createHeightAnimator(float f, float f2) {
        float f3 = this.mOverExpansion;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mExpandedHeight, f});
        ofFloat.addUpdateListener(new PanelViewController$$ExternalSyntheticLambda1(this, f2, f, f3, ofFloat));
        return ofFloat;
    }

    public final void endClosing() {
        if (this.mClosing) {
            setIsClosing(false);
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
            notificationPanelViewController.mStatusBar.onClosingFinished();
            notificationPanelViewController.mClosingWithAlphaFadeOut = false;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mForceNoOverlappingRendering = false;
            MediaHierarchyManager mediaHierarchyManager = notificationPanelViewController.mMediaHierarchyManager;
            Objects.requireNonNull(mediaHierarchyManager);
            Objects.requireNonNull(mediaHierarchyManager.mediaCarouselController);
            Objects.requireNonNull(MediaPlayerData.INSTANCE);
            for (MediaControlPanel closeGuts : MediaPlayerData.players()) {
                closeGuts.closeGuts(true);
            }
        }
    }

    public final void fling(float f, boolean z, float f2, boolean z2) {
        float f3;
        boolean z3;
        boolean z4;
        final boolean z5;
        float f4;
        float f5;
        if (z) {
            f3 = (float) getMaxPanelHeight();
        } else {
            f3 = 0.0f;
        }
        boolean z6 = true;
        if (!z) {
            setIsClosing(true);
        }
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
        HeadsUpTouchHelper headsUpTouchHelper = notificationPanelViewController.mHeadsUpTouchHelper;
        boolean z7 = !z;
        Objects.requireNonNull(headsUpTouchHelper);
        if (z7 && headsUpTouchHelper.mCollapseSnoozes) {
            headsUpTouchHelper.mHeadsUpManager.snooze();
        }
        headsUpTouchHelper.mCollapseSnoozes = false;
        notificationPanelViewController.mKeyguardStateController.notifyPanelFlingStart(z7);
        if (z || notificationPanelViewController.isOnKeyguard() || notificationPanelViewController.getFadeoutAlpha() != 1.0f) {
            z3 = false;
        } else {
            z3 = true;
        }
        notificationPanelViewController.mClosingWithAlphaFadeOut = z3;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mForceNoOverlappingRendering = z3;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        notificationStackScrollLayout2.mIsFlinging = true;
        if (f3 == notificationPanelViewController.mExpandedHeight && notificationPanelViewController.mOverExpansion == 0.0f) {
            if (notificationPanelViewController.mInteractionJankMonitor != null) {
                InteractionJankMonitor.getInstance().end(0);
            }
            notificationPanelViewController.mKeyguardStateController.notifyPanelFlingEnd();
            notificationPanelViewController.notifyExpandingFinished();
            return;
        }
        if (!z || notificationPanelViewController.mStatusBarStateController.getState() == 1 || notificationPanelViewController.mOverExpansion != 0.0f || f < 0.0f) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (z4 || (notificationPanelViewController.mOverExpansion != 0.0f && z)) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z4) {
            FlingAnimationUtils flingAnimationUtils = notificationPanelViewController.mFlingAnimationUtils;
            Objects.requireNonNull(flingAnimationUtils);
            f4 = (notificationPanelViewController.mOverExpansion / notificationPanelViewController.mPanelFlingOvershootAmount) + MathUtils.lerp(0.2f, 1.0f, MathUtils.saturate(f / (flingAnimationUtils.mHighVelocityPxPerSecond * 0.5f)));
        } else {
            f4 = 0.0f;
        }
        ValueAnimator createHeightAnimator = notificationPanelViewController.createHeightAnimator(f3, f4);
        if (z) {
            if (!z2 || f >= 0.0f) {
                f5 = f;
            } else {
                f5 = 0.0f;
            }
            notificationPanelViewController.mFlingAnimationUtils.apply(createHeightAnimator, notificationPanelViewController.mExpandedHeight, (f4 * notificationPanelViewController.mPanelFlingOvershootAmount) + f3, f5, (float) notificationPanelViewController.mView.getHeight());
            if (f5 == 0.0f) {
                createHeightAnimator.setDuration(350);
            }
        } else {
            if (notificationPanelViewController.mBarState == 0 || (!notificationPanelViewController.mKeyguardStateController.canDismissLockScreen() && notificationPanelViewController.mTracking)) {
                z6 = false;
            }
            if (!z6) {
                notificationPanelViewController.mFlingAnimationUtilsClosing.apply(createHeightAnimator, notificationPanelViewController.mExpandedHeight, f3, f, (float) notificationPanelViewController.mView.getHeight());
            } else if (f == 0.0f) {
                createHeightAnimator.setInterpolator(Interpolators.PANEL_CLOSE_ACCELERATED);
                createHeightAnimator.setDuration((long) (((notificationPanelViewController.mExpandedHeight / ((float) notificationPanelViewController.mView.getHeight())) * 100.0f) + 200.0f));
            } else {
                notificationPanelViewController.mFlingAnimationUtilsDismissing.apply(createHeightAnimator, notificationPanelViewController.mExpandedHeight, f3, f, (float) notificationPanelViewController.mView.getHeight());
            }
            if (f == 0.0f) {
                createHeightAnimator.setDuration((long) (((float) createHeightAnimator.getDuration()) / f2));
            }
            int i = notificationPanelViewController.mFixedDuration;
            if (i != -1) {
                createHeightAnimator.setDuration((long) i);
            }
        }
        createHeightAnimator.addListener(new AnimatorListenerAdapter() {
            public boolean mCancelled;

            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                if (!z5 || this.mCancelled) {
                    PanelViewController.this.onFlingEnd(this.mCancelled);
                    return;
                }
                PanelViewController panelViewController = PanelViewController.this;
                Objects.requireNonNull(panelViewController);
                float f = panelViewController.mOverExpansion;
                if (f == 0.0f) {
                    panelViewController.onFlingEnd(false);
                    return;
                }
                panelViewController.mIsSpringBackAnimation = true;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, 0.0f});
                ofFloat.addUpdateListener(new PanelViewController$$ExternalSyntheticLambda0(panelViewController));
                ofFloat.setDuration(400);
                ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public boolean mCancelled;

                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        PanelViewController panelViewController = PanelViewController.this;
                        panelViewController.mIsSpringBackAnimation = false;
                        panelViewController.onFlingEnd(this.mCancelled);
                    }
                });
                panelViewController.setAnimator(ofFloat);
                ofFloat.start();
            }

            public final void onAnimationStart(Animator animator) {
                PanelViewController.this.beginJankMonitoring();
            }
        });
        notificationPanelViewController.setAnimator(createHeightAnimator);
        createHeightAnimator.start();
    }

    public final boolean isCollapsing() {
        if (this.mClosing || this.mIsLaunchAnimationRunning) {
            return true;
        }
        return false;
    }

    public final boolean isExpanded() {
        boolean z;
        if (this.mExpandedFraction <= 0.0f && !this.mInstantExpanding) {
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
            HeadsUpManagerPhone headsUpManagerPhone = notificationPanelViewController.mHeadsUpManager;
            Objects.requireNonNull(headsUpManagerPhone);
            if ((headsUpManagerPhone.mHasPinnedNotification || notificationPanelViewController.mHeadsUpAnimatingAway) && notificationPanelViewController.mBarState == 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z && !this.mTracking && (this.mHeightAnimator == null || this.mIsSpringBackAnimation)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isFalseTouch(float f, float f2, int i) {
        Objects.requireNonNull(this.mStatusBar);
        if (this.mFalsingManager.isClassifierEnabled()) {
            return this.mFalsingManager.isFalseTouch(i);
        }
        if (!this.mTouchAboveFalsingThreshold) {
            return true;
        }
        boolean z = false;
        if (this.mUpwardsWhenThresholdReached) {
            return false;
        }
        float f3 = f - this.mInitialTouchX;
        float f4 = f2 - this.mInitialTouchY;
        if (f4 < 0.0f && Math.abs(f4) >= Math.abs(f3)) {
            z = true;
        }
        return !z;
    }

    public final boolean isFullyCollapsed() {
        if (this.mExpandedFraction <= 0.0f) {
            return true;
        }
        return false;
    }

    public final boolean isFullyExpanded() {
        if (this.mExpandedHeight >= ((float) getMaxPanelHeight())) {
            return true;
        }
        return false;
    }

    public final void notifyExpandingStarted() {
        if (!this.mExpanding) {
            this.mExpanding = true;
            onExpandingStarted();
        }
    }

    public final void onEmptySpaceClick() {
        boolean z;
        if (!this.mHintAnimationRunning) {
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
            int i = notificationPanelViewController.mBarState;
            if (i != 1) {
                if (i == 2 && !notificationPanelViewController.mQsExpanded) {
                    notificationPanelViewController.mStatusBarStateController.setState(1);
                }
            } else if (!notificationPanelViewController.mDozingOnDown) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = notificationPanelViewController.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                if (keyguardUpdateMonitor.mIsFaceEnrolled) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = notificationPanelViewController.mUpdateMonitor;
                    Objects.requireNonNull(keyguardUpdateMonitor2);
                    if (keyguardUpdateMonitor2.mFaceRunningState == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && !notificationPanelViewController.mUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
                        notificationPanelViewController.mUpdateMonitor.requestFaceAuth(true);
                        return;
                    }
                }
                notificationPanelViewController.mLockscreenGestureLogger.write(188, 0, 0);
                LockscreenGestureLogger lockscreenGestureLogger = notificationPanelViewController.mLockscreenGestureLogger;
                LockscreenGestureLogger.LockscreenUiEvent lockscreenUiEvent = LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_LOCK_SHOW_HINT;
                Objects.requireNonNull(lockscreenGestureLogger);
                LockscreenGestureLogger.log(lockscreenUiEvent);
                if (notificationPanelViewController.mPowerManager.isPowerSaveMode()) {
                    StatusBar statusBar = notificationPanelViewController.mStatusBar;
                    Objects.requireNonNull(statusBar);
                    statusBar.mFalsingCollector.onUnlockHintStarted();
                    statusBar.mKeyguardIndicationController.showActionToUnlock();
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    Objects.requireNonNull(notificationStackScrollLayout);
                    AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
                    Objects.requireNonNull(ambientState);
                    ambientState.mUnlockHintRunning = true;
                    StatusBar statusBar2 = notificationPanelViewController.mStatusBar;
                    Objects.requireNonNull(statusBar2);
                    KeyguardIndicationController keyguardIndicationController = statusBar2.mKeyguardIndicationController;
                    Objects.requireNonNull(keyguardIndicationController);
                    KeyguardIndicationController.C11455 r0 = keyguardIndicationController.mHandler;
                    r0.sendMessageDelayed(r0.obtainMessage(1), 1200);
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController.mNotificationStackScrollLayoutController;
                    Objects.requireNonNull(notificationStackScrollLayoutController2);
                    NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                    Objects.requireNonNull(notificationStackScrollLayout2);
                    AmbientState ambientState2 = notificationStackScrollLayout2.mAmbientState;
                    Objects.requireNonNull(ambientState2);
                    ambientState2.mUnlockHintRunning = false;
                } else if (notificationPanelViewController.mHeightAnimator == null && !notificationPanelViewController.mTracking) {
                    notificationPanelViewController.notifyExpandingStarted();
                    final StatusBar$$ExternalSyntheticLambda19 statusBar$$ExternalSyntheticLambda19 = new StatusBar$$ExternalSyntheticLambda19(notificationPanelViewController, 4);
                    ValueAnimator createHeightAnimator = notificationPanelViewController.createHeightAnimator(Math.max(0.0f, ((float) notificationPanelViewController.getMaxPanelHeight()) - notificationPanelViewController.mHintDistance), 0.0f);
                    createHeightAnimator.setDuration(250);
                    createHeightAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    createHeightAnimator.addListener(new AnimatorListenerAdapter() {
                        public boolean mCancelled;

                        public final void onAnimationCancel(Animator animator) {
                            this.mCancelled = true;
                        }

                        public final void onAnimationEnd(Animator animator) {
                            if (this.mCancelled) {
                                PanelViewController.this.setAnimator((ValueAnimator) null);
                                statusBar$$ExternalSyntheticLambda19.run();
                                return;
                            }
                            PanelViewController panelViewController = PanelViewController.this;
                            Runnable runnable = statusBar$$ExternalSyntheticLambda19;
                            Objects.requireNonNull(panelViewController);
                            ValueAnimator createHeightAnimator = panelViewController.createHeightAnimator((float) panelViewController.getMaxPanelHeight(), 0.0f);
                            createHeightAnimator.setDuration(450);
                            createHeightAnimator.setInterpolator(panelViewController.mBounceInterpolator);
                            createHeightAnimator.addListener(new AnimatorListenerAdapter(runnable) {
                                public final /* synthetic */ Runnable val$onAnimationFinished;

                                {
                                    this.val$onAnimationFinished = r2;
                                }

                                public final void onAnimationEnd(Animator animator) {
                                    PanelViewController.this.setAnimator((ValueAnimator) null);
                                    this.val$onAnimationFinished.run();
                                    PanelViewController.this.updatePanelExpansionAndVisibility();
                                }
                            });
                            createHeightAnimator.start();
                            panelViewController.setAnimator(createHeightAnimator);
                        }
                    });
                    createHeightAnimator.start();
                    notificationPanelViewController.setAnimator(createHeightAnimator);
                    KeyguardBottomAreaView keyguardBottomAreaView = notificationPanelViewController.mKeyguardBottomArea;
                    Objects.requireNonNull(keyguardBottomAreaView);
                    StatusBar statusBar3 = notificationPanelViewController.mStatusBar;
                    Objects.requireNonNull(statusBar3);
                    View[] viewArr = {keyguardBottomAreaView.mIndicationArea, statusBar3.mAmbientIndicationContainer};
                    for (int i2 = 0; i2 < 2; i2++) {
                        View view = viewArr[i2];
                        if (view != null) {
                            view.animate().translationY(-notificationPanelViewController.mHintDistance).setDuration(250).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).withEndAction(new QSPanelController$$ExternalSyntheticLambda0(notificationPanelViewController, view, 1)).start();
                        }
                    }
                    StatusBar statusBar4 = notificationPanelViewController.mStatusBar;
                    Objects.requireNonNull(statusBar4);
                    statusBar4.mFalsingCollector.onUnlockHintStarted();
                    statusBar4.mKeyguardIndicationController.showActionToUnlock();
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationPanelViewController.mNotificationStackScrollLayoutController;
                    Objects.requireNonNull(notificationStackScrollLayoutController3);
                    NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController3.mView;
                    Objects.requireNonNull(notificationStackScrollLayout3);
                    AmbientState ambientState3 = notificationStackScrollLayout3.mAmbientState;
                    Objects.requireNonNull(ambientState3);
                    ambientState3.mUnlockHintRunning = true;
                    notificationPanelViewController.mHintAnimationRunning = true;
                }
            }
        }
    }

    public final void setAnimator(ValueAnimator valueAnimator) {
        this.mHeightAnimator = valueAnimator;
        if (valueAnimator == null && this.mPanelUpdateWhenAnimatorEnds) {
            this.mPanelUpdateWhenAnimatorEnds = false;
            requestPanelHeightUpdate();
        }
    }

    public final void setOverExpansionInternal(float f, boolean z) {
        if (!z) {
            this.mLastGesturedOverExpansion = -1.0f;
            setOverExpansion(f);
        } else if (this.mLastGesturedOverExpansion != f) {
            this.mLastGesturedOverExpansion = f;
            float saturate = MathUtils.saturate(f / (((float) this.mView.getHeight()) / 3.0f));
            PathInterpolator pathInterpolator = Interpolators.EMPHASIZED;
            setOverExpansion(MathUtils.max(0.0f, (float) (1.0d - Math.exp((double) (saturate * -4.0f)))) * this.mPanelFlingOvershootAmount * 2.0f);
        }
    }

    public final void startExpandMotion(float f, float f2, boolean z, float f3) {
        if (!this.mHandlingPointerUp) {
            beginJankMonitoring();
        }
        this.mInitialOffsetOnTouch = f3;
        this.mInitialTouchY = f2;
        this.mInitialTouchX = f;
        if (z) {
            this.mTouchSlopExceeded = true;
            setExpandedHeightInternal(f3);
            onTrackingStarted();
        }
    }

    public final void updatePanelExpansionAndVisibility() {
        boolean z;
        PanelExpansionStateManager panelExpansionStateManager = this.mPanelExpansionStateManager;
        float f = this.mExpandedFraction;
        boolean isExpanded = isExpanded();
        boolean z2 = this.mTracking;
        Objects.requireNonNull(panelExpansionStateManager);
        boolean z3 = true;
        if (!Float.isNaN(f)) {
            int i = panelExpansionStateManager.state;
            panelExpansionStateManager.fraction = f;
            panelExpansionStateManager.expanded = isExpanded;
            panelExpansionStateManager.tracking = z2;
            if (isExpanded) {
                if (i == 0) {
                    panelExpansionStateManager.updateStateInternal(1);
                }
                if (f < 1.0f) {
                    z3 = false;
                }
                z = false;
            } else {
                z = true;
                z3 = false;
            }
            if (z3 && !z2) {
                panelExpansionStateManager.updateStateInternal(2);
            } else if (z && !z2 && panelExpansionStateManager.state != 0) {
                panelExpansionStateManager.updateStateInternal(0);
            }
            PanelExpansionStateManagerKt.access$stateToString(i);
            PanelExpansionStateManagerKt.access$stateToString(panelExpansionStateManager.state);
            Iterator it = panelExpansionStateManager.expansionListeners.iterator();
            while (it.hasNext()) {
                ((PanelExpansionListener) it.next()).onPanelExpansionChanged(f, isExpanded, z2);
            }
            updateVisibility();
            return;
        }
        throw new IllegalArgumentException("fraction cannot be NaN".toString());
    }

    public final void updateVisibility() {
        boolean z;
        PanelView panelView = this.mView;
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
        boolean z2 = true;
        int i = 0;
        if (notificationPanelViewController.mHeadsUpAnimatingAway || notificationPanelViewController.mHeadsUpPinnedMode) {
            z = true;
        } else {
            z = false;
        }
        if (!z && !notificationPanelViewController.isExpanded() && !notificationPanelViewController.mBouncerShowing) {
            z2 = false;
        }
        if (!z2) {
            i = 4;
        }
        panelView.setVisibility(i);
    }

    /* renamed from: -$$Nest$maddMovement  reason: not valid java name */
    public static void m244$$Nest$maddMovement(PanelViewController panelViewController, MotionEvent motionEvent) {
        Objects.requireNonNull(panelViewController);
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        panelViewController.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    public final boolean canPanelBeCollapsed() {
        if (isFullyCollapsed() || this.mTracking || this.mClosing) {
            return false;
        }
        return true;
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        if (motionEvent.getClassification() == 1) {
            return ((float) this.mTouchSlop) * this.mSlopMultiplier;
        }
        return (float) this.mTouchSlop;
    }

    public final void notifyExpandingFinished() {
        endClosing();
        if (this.mExpanding) {
            this.mExpanding = false;
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
            ScrimController scrimController = notificationPanelViewController.mScrimController;
            Objects.requireNonNull(scrimController);
            scrimController.mTracking = false;
            scrimController.mUnOcclusionAnimationRunning = false;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mCheckForLeavebehind = false;
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mIsExpansionChanging = false;
            AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
            Objects.requireNonNull(ambientState);
            ambientState.mExpansionChanging = false;
            if (!notificationStackScrollLayout2.mIsExpanded) {
                notificationStackScrollLayout2.mScroller.abortAnimation();
                notificationStackScrollLayout2.setOwnScrollY(0, false);
                StatusBar statusBar = notificationStackScrollLayout2.mStatusBar;
                Objects.requireNonNull(statusBar);
                statusBar.mNotificationsController.resetUserExpandedStates();
                NotificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout2);
                for (int i = 0; i < notificationStackScrollLayout2.getChildCount(); i++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout2.getChildAt(i);
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                        Objects.requireNonNull(expandableNotificationRow);
                        NotificationStackScrollLayout.clearTemporaryViewsInGroup(expandableNotificationRow.mChildrenContainer);
                    }
                }
                for (int i2 = 0; i2 < notificationStackScrollLayout2.getChildCount(); i2++) {
                    ExpandableView expandableView2 = (ExpandableView) notificationStackScrollLayout2.getChildAt(i2);
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                    }
                }
                NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout2.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper);
                if (notificationSwipeHelper.mIsSwiping) {
                    NotificationSwipeHelper notificationSwipeHelper2 = notificationStackScrollLayout2.mSwipeHelper;
                    Objects.requireNonNull(notificationSwipeHelper2);
                    notificationSwipeHelper2.mTouchedView = null;
                    notificationSwipeHelper2.mIsSwiping = false;
                    notificationStackScrollLayout2.updateContinuousShadowDrawing();
                }
            }
            HeadsUpManagerPhone headsUpManagerPhone = notificationPanelViewController.mHeadsUpManager;
            Objects.requireNonNull(headsUpManagerPhone);
            if (headsUpManagerPhone.mReleaseOnExpandFinish) {
                headsUpManagerPhone.releaseAllImmediately();
                headsUpManagerPhone.mReleaseOnExpandFinish = false;
            } else {
                Iterator<NotificationEntry> it = headsUpManagerPhone.mEntriesToRemoveAfterExpand.iterator();
                while (it.hasNext()) {
                    NotificationEntry next = it.next();
                    Objects.requireNonNull(next);
                    if (headsUpManagerPhone.isAlerting(next.mKey)) {
                        headsUpManagerPhone.removeAlertEntry(next.mKey);
                    }
                }
            }
            headsUpManagerPhone.mEntriesToRemoveAfterExpand.clear();
            notificationPanelViewController.mConversationNotificationManager.onNotificationPanelExpandStateChanged(notificationPanelViewController.isFullyCollapsed());
            notificationPanelViewController.mIsExpanding = false;
            MediaHierarchyManager mediaHierarchyManager = notificationPanelViewController.mMediaHierarchyManager;
            Objects.requireNonNull(mediaHierarchyManager);
            if (mediaHierarchyManager.collapsingShadeFromQS) {
                mediaHierarchyManager.collapsingShadeFromQS = false;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
            }
            notificationPanelViewController.mMediaHierarchyManager.setQsExpanded(notificationPanelViewController.mQsExpanded);
            if (notificationPanelViewController.isFullyCollapsed()) {
                DejankUtils.postAfterTraversal(new Runnable() {
                    public final void run() {
                        NotificationPanelViewController.this.setListening(false);
                    }
                });
                notificationPanelViewController.mView.postOnAnimation(new Runnable() {
                    public final void run() {
                        NotificationPanelViewController.this.mView.getParent().invalidateChild(NotificationPanelViewController.this.mView, NotificationPanelViewController.M_DUMMY_DIRTY_RECT);
                    }
                });
            } else {
                notificationPanelViewController.setListening(true);
            }
            notificationPanelViewController.mQsExpandImmediate = false;
            notificationPanelViewController.mNotificationStackScrollLayoutController.setShouldShowShelfOnly(false);
            notificationPanelViewController.mTwoFingerQsExpandPossible = false;
            notificationPanelViewController.mTrackedHeadsUpNotification = null;
            for (int i3 = 0; i3 < notificationPanelViewController.mTrackingHeadsUpListeners.size(); i3++) {
                notificationPanelViewController.mTrackingHeadsUpListeners.get(i3).accept((Object) null);
            }
            notificationPanelViewController.mExpandingFromHeadsUp = false;
            notificationPanelViewController.setPanelScrimMinFraction(0.0f);
        }
    }

    public final void requestPanelHeightUpdate() {
        boolean z;
        float maxPanelHeight = (float) getMaxPanelHeight();
        if (!isFullyCollapsed() && maxPanelHeight != this.mExpandedHeight) {
            if (this.mTracking) {
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this;
                if ((!notificationPanelViewController.mConflictingQsExpansionGesture || !notificationPanelViewController.mQsExpanded) && !notificationPanelViewController.mBlockingExpansionForCurrentTouch) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return;
                }
            }
            if (this.mHeightAnimator == null || this.mIsSpringBackAnimation) {
                setExpandedHeightInternal(maxPanelHeight);
            } else {
                this.mPanelUpdateWhenAnimatorEnds = true;
            }
        }
    }

    public final void setExpandedHeightInternal(float f) {
        if (Float.isNaN(f)) {
            Log.wtf("PanelView", "ExpandedHeight set to NaN");
        }
        this.mNotificationShadeWindowController.batchApplyWindowLayoutParams(new PanelViewController$$ExternalSyntheticLambda2(this, f));
    }

    static {
        Class<PanelView> cls = PanelView.class;
    }
}
