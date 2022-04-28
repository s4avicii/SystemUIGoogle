package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.fragment.R$id;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda1;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.ExpandAnimationParameters;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.FooterView;
import com.android.systemui.statusbar.notification.row.ForegroundServiceDungeonView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpTouchHelper;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ScrollAdapter;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Utils;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda1;
import com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda2;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NotificationStackScrollLayout extends ViewGroup implements Dumpable {
    public static final boolean SPEW = Log.isLoggable("StackScroller", 2);
    public boolean mActivateNeedsAnimation;
    public int mActivePointerId = -1;
    public ArrayList<View> mAddedHeadsUpChildren = new ArrayList<>();
    public final AmbientState mAmbientState;
    public boolean mAnimateBottomOnLayout;
    public boolean mAnimateNextBackgroundBottom;
    public boolean mAnimateNextBackgroundTop;
    public boolean mAnimateNextSectionBoundsChange;
    public boolean mAnimateNextTopPaddingChange;
    public boolean mAnimateStackYForContentHeightChange = false;
    public ArrayList<AnimationEvent> mAnimationEvents = new ArrayList<>();
    public HashSet<Runnable> mAnimationFinishedRunnables = new HashSet<>();
    public boolean mAnimationRunning;
    public boolean mAnimationsEnabled;
    public final Rect mBackgroundAnimationRect = new Rect();
    public final Paint mBackgroundPaint = new Paint();
    public NotificationStackScrollLayout$$ExternalSyntheticLambda1 mBackgroundUpdater = new NotificationStackScrollLayout$$ExternalSyntheticLambda1(this);
    public float mBackgroundXFactor = 1.0f;
    public boolean mBackwardScrollable;
    public int mBgColor;
    public float[] mBgCornerRadii = new float[8];
    public int mBottomInset = 0;
    public int mBottomPadding;
    public int mCachedBackgroundColor;
    public boolean mChangePositionInProgress;
    public boolean mCheckForLeavebehind;
    public boolean mChildTransferInProgress;
    public ArrayList<ExpandableView> mChildrenChangingPositions = new ArrayList<>();
    public HashSet<ExpandableView> mChildrenToAddAnimated = new HashSet<>();
    public ArrayList<ExpandableView> mChildrenToRemoveAnimated = new ArrayList<>();
    public boolean mChildrenUpdateRequested;
    public C13551 mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() {
        /* JADX WARNING: type inference failed for: r7v8, types: [android.widget.TextView, android.view.View] */
        /* JADX WARNING: type inference failed for: r7v12, types: [android.widget.TextView] */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x02b6, code lost:
            if (r4 == false) goto L_0x02ba;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:499:0x0974  */
        /* JADX WARNING: Removed duplicated region for block: B:578:0x09a4 A[SYNTHETIC] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onPreDraw() {
            /*
                r21 = this;
                r0 = r21
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
                boolean r2 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.SPEW
                java.util.Objects.requireNonNull(r1)
                android.view.View r2 = r1.mForcedScroll
                r3 = 0
                if (r2 == 0) goto L_0x001e
                boolean r2 = r2.hasFocus()
                if (r2 == 0) goto L_0x001c
                android.view.View r2 = r1.mForcedScroll
                boolean r2 = r2.isAttachedToWindow()
                if (r2 != 0) goto L_0x001e
            L_0x001c:
                r1.mForcedScroll = r3
            L_0x001e:
                android.view.View r2 = r1.mForcedScroll
                r4 = 0
                if (r2 == 0) goto L_0x0047
                com.android.systemui.statusbar.notification.row.ExpandableView r2 = (com.android.systemui.statusbar.notification.row.ExpandableView) r2
                int r5 = r1.getPositionInLinearLayout(r2)
                int r6 = r1.targetScrollForView(r2, r5)
                int r2 = r2.getIntrinsicHeight()
                int r2 = r2 + r5
                int r5 = r1.getScrollRange()
                int r5 = java.lang.Math.min(r6, r5)
                int r5 = java.lang.Math.max(r4, r5)
                int r6 = r1.mOwnScrollY
                if (r6 < r5) goto L_0x0044
                if (r2 >= r6) goto L_0x0047
            L_0x0044:
                r1.setOwnScrollY(r5, r4)
            L_0x0047:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
                java.util.Objects.requireNonNull(r1)
                java.util.HashSet<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r1.mChildrenToAddAnimated
                boolean r2 = r2.isEmpty()
                if (r2 == 0) goto L_0x0055
                goto L_0x008c
            L_0x0055:
                r2 = r4
            L_0x0056:
                int r5 = r1.getChildCount()
                if (r2 >= r5) goto L_0x0089
                android.view.View r5 = r1.getChildAt(r2)
                com.android.systemui.statusbar.notification.row.ExpandableView r5 = (com.android.systemui.statusbar.notification.row.ExpandableView) r5
                java.util.HashSet<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r1.mChildrenToAddAnimated
                boolean r6 = r6.contains(r5)
                if (r6 == 0) goto L_0x0086
                int r6 = r1.getPositionInLinearLayout(r5)
                boolean r7 = r5 instanceof com.android.systemui.statusbar.notification.row.ExpandableView
                if (r7 == 0) goto L_0x0077
                int r5 = r5.getIntrinsicHeight()
                goto L_0x007b
            L_0x0077:
                int r5 = r5.getHeight()
            L_0x007b:
                int r7 = r1.mPaddingBetweenElements
                int r5 = r5 + r7
                int r7 = r1.mOwnScrollY
                if (r6 >= r7) goto L_0x0086
                int r7 = r7 + r5
                r1.setOwnScrollY(r7, r4)
            L_0x0086:
                int r2 = r2 + 1
                goto L_0x0056
            L_0x0089:
                r1.clampScrollPosition()
            L_0x008c:
                com.android.systemui.statusbar.notification.stack.AmbientState r2 = r1.mAmbientState
                android.widget.OverScroller r5 = r1.mScroller
                boolean r5 = r5.isFinished()
                r6 = 0
                if (r5 == 0) goto L_0x0099
                r5 = r6
                goto L_0x009f
            L_0x0099:
                android.widget.OverScroller r5 = r1.mScroller
                float r5 = r5.getCurrVelocity()
            L_0x009f:
                java.util.Objects.requireNonNull(r2)
                r2.mCurrentScrollVelocity = r5
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm r2 = r1.mStackScrollAlgorithm
                com.android.systemui.statusbar.notification.stack.AmbientState r5 = r1.mAmbientState
                int r7 = r1.getSpeedBumpIndex()
                java.util.Objects.requireNonNull(r2)
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$StackScrollAlgorithmState r8 = r2.mTempAlgorithmState
                android.view.ViewGroup r9 = r2.mHostView
                int r9 = r9.getChildCount()
                r10 = r4
            L_0x00b8:
                if (r10 >= r9) goto L_0x00c8
                android.view.ViewGroup r11 = r2.mHostView
                android.view.View r11 = r11.getChildAt(r10)
                com.android.systemui.statusbar.notification.row.ExpandableView r11 = (com.android.systemui.statusbar.notification.row.ExpandableView) r11
                r11.resetViewState()
                int r10 = r10 + 1
                goto L_0x00b8
            L_0x00c8:
                java.util.Objects.requireNonNull(r5)
                int r9 = r5.mScrollY
                java.util.Objects.requireNonNull(r8)
                int r9 = -r9
                r8.mCurrentYPosition = r9
                r8.mCurrentExpandedYPosition = r9
                android.view.ViewGroup r9 = r2.mHostView
                int r9 = r9.getChildCount()
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r10 = r8.visibleChildren
                r10.clear()
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r10 = r8.visibleChildren
                r10.ensureCapacity(r9)
                r10 = r4
                r11 = r10
            L_0x00e7:
                r12 = 8
                if (r10 >= r9) goto L_0x0139
                android.view.ViewGroup r13 = r2.mHostView
                android.view.View r13 = r13.getChildAt(r10)
                com.android.systemui.statusbar.notification.row.ExpandableView r13 = (com.android.systemui.statusbar.notification.row.ExpandableView) r13
                int r14 = r13.getVisibility()
                if (r14 == r12) goto L_0x0136
                com.android.systemui.statusbar.NotificationShelf r14 = r5.mShelf
                if (r13 != r14) goto L_0x00fe
                goto L_0x0136
            L_0x00fe:
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r14 = r13.mViewState
                r14.notGoneIndex = r11
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r14 = r8.visibleChildren
                r14.add(r13)
                int r11 = r11 + 1
                boolean r14 = r13 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r14 == 0) goto L_0x0136
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r13 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r13
                java.util.ArrayList r14 = r13.getAttachedChildren()
                boolean r13 = r13.mIsSummaryWithChildren
                if (r13 == 0) goto L_0x0136
                if (r14 == 0) goto L_0x0136
                java.util.Iterator r13 = r14.iterator()
            L_0x011d:
                boolean r14 = r13.hasNext()
                if (r14 == 0) goto L_0x0136
                java.lang.Object r14 = r13.next()
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r14 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r14
                int r15 = r14.getVisibility()
                if (r15 == r12) goto L_0x011d
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r14 = r14.mViewState
                r14.notGoneIndex = r11
                int r11 = r11 + 1
                goto L_0x011d
            L_0x0136:
                int r10 = r10 + 1
                goto L_0x00e7
            L_0x0139:
                int r9 = r5.mScrollY
                int r9 = -r9
                float r9 = (float) r9
                boolean r10 = r5.isOnKeyguard()
                if (r10 == 0) goto L_0x0151
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$BypassController r10 = r5.mBypassController
                boolean r10 = r10.isBypassEnabled()
                if (r10 == 0) goto L_0x0154
                boolean r10 = r5.isPulseExpanding()
                if (r10 == 0) goto L_0x0154
            L_0x0151:
                float r10 = r2.mNotificationScrimPadding
                float r9 = r9 + r10
            L_0x0154:
                r8.firstViewInShelf = r3
                r10 = r4
            L_0x0157:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r11 = r8.visibleChildren
                int r11 = r11.size()
                if (r10 >= r11) goto L_0x01a8
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r11 = r8.visibleChildren
                java.lang.Object r11 = r11.get(r10)
                com.android.systemui.statusbar.notification.row.ExpandableView r11 = (com.android.systemui.statusbar.notification.row.ExpandableView) r11
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$SectionProvider r13 = r5.mSectionProvider
                if (r10 <= 0) goto L_0x0176
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r14 = r8.visibleChildren
                int r15 = r10 + -1
                java.lang.Object r14 = r14.get(r15)
                com.android.systemui.statusbar.notification.row.ExpandableView r14 = (com.android.systemui.statusbar.notification.row.ExpandableView) r14
                goto L_0x0177
            L_0x0176:
                r14 = r3
            L_0x0177:
                boolean r13 = com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm.childNeedsGapHeight(r13, r10, r11, r14)
                if (r13 == 0) goto L_0x0181
                int r13 = r2.mGapHeight
                float r13 = (float) r13
                float r9 = r9 + r13
            L_0x0181:
                com.android.systemui.statusbar.NotificationShelf r13 = r5.mShelf
                if (r13 == 0) goto L_0x019b
                float r14 = r5.mStackEndHeight
                int r13 = r13.getHeight()
                float r13 = (float) r13
                float r14 = r14 - r13
                int r13 = (r9 > r14 ? 1 : (r9 == r14 ? 0 : -1))
                if (r13 < 0) goto L_0x019b
                boolean r13 = r11 instanceof com.android.systemui.statusbar.notification.row.FooterView
                if (r13 != 0) goto L_0x019b
                com.android.systemui.statusbar.notification.row.ExpandableView r13 = r8.firstViewInShelf
                if (r13 != 0) goto L_0x019b
                r8.firstViewInShelf = r11
            L_0x019b:
                int r11 = r2.getMaxAllowedChildHeight(r11)
                float r11 = (float) r11
                float r9 = r9 + r11
                int r11 = r2.mPaddingBetweenElements
                float r11 = (float) r11
                float r9 = r9 + r11
                int r10 = r10 + 1
                goto L_0x0157
            L_0x01a8:
                boolean r9 = r5.isOnKeyguard()
                if (r9 == 0) goto L_0x01bc
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$BypassController r9 = r5.mBypassController
                boolean r9 = r9.isBypassEnabled()
                if (r9 == 0) goto L_0x01cc
                boolean r9 = r5.isPulseExpanding()
                if (r9 == 0) goto L_0x01cc
            L_0x01bc:
                int r9 = r8.mCurrentYPosition
                float r9 = (float) r9
                float r10 = r2.mNotificationScrimPadding
                float r9 = r9 + r10
                int r9 = (int) r9
                r8.mCurrentYPosition = r9
                int r9 = r8.mCurrentExpandedYPosition
                float r9 = (float) r9
                float r9 = r9 + r10
                int r9 = (int) r9
                r8.mCurrentExpandedYPosition = r9
            L_0x01cc:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r9 = r8.visibleChildren
                int r9 = r9.size()
                r10 = r4
            L_0x01d3:
                r11 = 1073741824(0x40000000, float:2.0)
                r13 = 1065353216(0x3f800000, float:1.0)
                r14 = 1
                if (r10 >= r9) goto L_0x0395
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r15 = r8.visibleChildren
                java.lang.Object r15 = r15.get(r10)
                com.android.systemui.statusbar.notification.row.ExpandableView r15 = (com.android.systemui.statusbar.notification.row.ExpandableView) r15
                java.util.Objects.requireNonNull(r15)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r15.mViewState
                r3.location = r4
                boolean r12 = r5.mShadeExpanded
                if (r12 == 0) goto L_0x01f5
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r12 = r5.getTrackedHeadsUpRow()
                if (r15 != r12) goto L_0x01f5
                r12 = r14
                goto L_0x01f6
            L_0x01f5:
                r12 = r4
            L_0x01f6:
                if (r12 == 0) goto L_0x01f9
                goto L_0x0211
            L_0x01f9:
                boolean r12 = r5.isOnKeyguard()
                if (r12 == 0) goto L_0x0205
                float r12 = r5.mHideAmount
                float r13 = r13 - r12
                r3.alpha = r13
                goto L_0x0211
            L_0x0205:
                boolean r12 = r5.mExpansionChanging
                if (r12 == 0) goto L_0x0211
                float r12 = r5.mExpansionFraction
                float r12 = com.android.systemui.animation.ShadeInterpolation.getContentAlpha(r12)
                r3.alpha = r12
            L_0x0211:
                boolean r12 = r5.mShadeExpanded
                if (r12 == 0) goto L_0x0233
                boolean r12 = r15.mustStayOnScreen()
                if (r12 == 0) goto L_0x0233
                float r12 = r3.yTranslation
                int r13 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
                if (r13 < 0) goto L_0x0233
                int r13 = r3.height
                float r13 = (float) r13
                float r12 = r12 + r13
                float r13 = r5.mStackY
                float r12 = r12 + r13
                float r13 = r5.mMaxHeadsUpTranslation
                int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
                if (r12 >= 0) goto L_0x0230
                r12 = r14
                goto L_0x0231
            L_0x0230:
                r12 = r4
            L_0x0231:
                r3.headsUpIsVisible = r12
            L_0x0233:
                float r12 = r2.getExpansionFractionWithoutShelf(r8, r5)
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$SectionProvider r13 = r5.mSectionProvider
                if (r10 <= 0) goto L_0x0246
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                int r4 = r10 + -1
                java.lang.Object r4 = r6.get(r4)
                com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
                goto L_0x0247
            L_0x0246:
                r4 = 0
            L_0x0247:
                boolean r4 = com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm.childNeedsGapHeight(r13, r10, r15, r4)
                if (r4 == 0) goto L_0x025d
                int r4 = r8.mCurrentYPosition
                float r4 = (float) r4
                int r6 = r2.mGapHeight
                float r13 = (float) r6
                float r13 = r13 * r12
                float r13 = r13 + r4
                int r4 = (int) r13
                r8.mCurrentYPosition = r4
                int r4 = r8.mCurrentExpandedYPosition
                int r4 = r4 + r6
                r8.mCurrentExpandedYPosition = r4
            L_0x025d:
                int r4 = r8.mCurrentYPosition
                float r4 = (float) r4
                r3.yTranslation = r4
                boolean r4 = r15 instanceof com.android.systemui.statusbar.notification.row.FooterView
                if (r4 == 0) goto L_0x02be
                boolean r4 = r5.mShadeExpanded
                r4 = r4 ^ r14
                com.android.systemui.statusbar.notification.row.ExpandableView r6 = r8.firstViewInShelf
                if (r6 == 0) goto L_0x026f
                r6 = r14
                goto L_0x0270
            L_0x026f:
                r6 = 0
            L_0x0270:
                if (r4 == 0) goto L_0x0276
                r3.hidden = r14
                goto L_0x035a
            L_0x0276:
                int r4 = r8.mCurrentExpandedYPosition
                int r11 = r15.getIntrinsicHeight()
                int r11 = r11 + r4
                float r4 = (float) r11
                float r11 = r5.mStackEndHeight
                int r4 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
                if (r4 <= 0) goto L_0x0286
                r4 = r14
                goto L_0x0287
            L_0x0286:
                r4 = 0
            L_0x0287:
                r11 = r3
                com.android.systemui.statusbar.notification.row.FooterView$FooterViewState r11 = (com.android.systemui.statusbar.notification.row.FooterView.FooterViewState) r11
                if (r6 != 0) goto L_0x02ba
                if (r4 != 0) goto L_0x02ba
                boolean r4 = r5.mClearAllInProgress
                if (r4 == 0) goto L_0x02b9
                r4 = 0
            L_0x0293:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                int r6 = r6.size()
                if (r4 >= r6) goto L_0x02b5
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                java.lang.Object r6 = r6.get(r4)
                android.view.View r6 = (android.view.View) r6
                boolean r13 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r13 != 0) goto L_0x02a8
                goto L_0x02b2
            L_0x02a8:
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
                boolean r6 = r6.canViewBeDismissed()
                if (r6 != 0) goto L_0x02b2
                r4 = r14
                goto L_0x02b6
            L_0x02b2:
                int r4 = r4 + 1
                goto L_0x0293
            L_0x02b5:
                r4 = 0
            L_0x02b6:
                if (r4 != 0) goto L_0x02b9
                goto L_0x02ba
            L_0x02b9:
                r14 = 0
            L_0x02ba:
                r11.hideContent = r14
                goto L_0x035a
            L_0x02be:
                boolean r4 = r15 instanceof com.android.systemui.statusbar.EmptyShadeView
                if (r4 == 0) goto L_0x02d6
                int r4 = r5.mLayoutMaxHeight
                int r6 = r2.mMarginBottom
                int r4 = r4 + r6
                float r4 = (float) r4
                float r6 = r5.mStackY
                float r4 = r4 - r6
                int r6 = r2.getMaxAllowedChildHeight(r15)
                float r6 = (float) r6
                float r4 = r4 - r6
                float r4 = r4 / r11
                r3.yTranslation = r4
                goto L_0x0351
            L_0x02d6:
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = r5.getTrackedHeadsUpRow()
                if (r15 == r4) goto L_0x0351
                boolean r4 = r5.mExpansionChanging
                if (r4 == 0) goto L_0x02f4
                r4 = 0
                r3.hidden = r4
                com.android.systemui.statusbar.notification.row.ExpandableView r4 = r8.firstViewInShelf
                if (r4 == 0) goto L_0x02f0
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                int r4 = r6.indexOf(r4)
                if (r10 < r4) goto L_0x02f0
                goto L_0x02f1
            L_0x02f0:
                r14 = 0
            L_0x02f1:
                r3.inShelf = r14
                goto L_0x0351
            L_0x02f4:
                com.android.systemui.statusbar.NotificationShelf r4 = r5.mShelf
                if (r4 == 0) goto L_0x0351
                com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm$BypassController r4 = r5.mBypassController
                boolean r4 = r4.isBypassEnabled()
                if (r4 == 0) goto L_0x030e
                boolean r4 = r5.isOnKeyguard()
                if (r4 == 0) goto L_0x030e
                boolean r4 = r5.isPulseExpanding()
                if (r4 != 0) goto L_0x030e
                r4 = r14
                goto L_0x030f
            L_0x030e:
                r4 = 0
            L_0x030f:
                boolean r6 = r5.mShadeExpanded
                if (r6 == 0) goto L_0x031e
                boolean r6 = r5.mDozing
                if (r6 != 0) goto L_0x031e
                if (r4 == 0) goto L_0x031a
                goto L_0x031e
            L_0x031a:
                float r4 = r5.mStackHeight
                int r4 = (int) r4
                goto L_0x0324
            L_0x031e:
                r4 = 0
                int r6 = r5.getInnerHeight(r4)
                r4 = r6
            L_0x0324:
                com.android.systemui.statusbar.NotificationShelf r6 = r5.mShelf
                java.util.Objects.requireNonNull(r6)
                int r6 = r6.getHeight()
                int r4 = r4 - r6
                float r6 = r3.yTranslation
                float r4 = (float) r4
                float r6 = java.lang.Math.min(r6, r4)
                r3.yTranslation = r6
                int r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r4 < 0) goto L_0x0351
                boolean r4 = r15.isExpandAnimationRunning()
                if (r4 != 0) goto L_0x0349
                boolean r4 = r15.hasExpandingChild()
                if (r4 != 0) goto L_0x0349
                r4 = r14
                goto L_0x034a
            L_0x0349:
                r4 = 0
            L_0x034a:
                r3.hidden = r4
                r3.inShelf = r14
                r4 = 0
                r3.headsUpIsVisible = r4
            L_0x0351:
                int r4 = r2.getMaxAllowedChildHeight(r15)
                float r4 = (float) r4
                float r4 = r4 * r12
                int r4 = (int) r4
                r3.height = r4
            L_0x035a:
                int r4 = r8.mCurrentYPosition
                float r4 = (float) r4
                int r6 = r3.height
                float r6 = (float) r6
                int r11 = r2.mPaddingBetweenElements
                float r11 = (float) r11
                float r12 = r12 * r11
                float r12 = r12 + r6
                float r12 = r12 + r4
                int r4 = (int) r12
                r8.mCurrentYPosition = r4
                int r4 = r8.mCurrentExpandedYPosition
                int r6 = r15.getIntrinsicHeight()
                int r11 = r2.mPaddingBetweenElements
                int r6 = r6 + r11
                int r6 = r6 + r4
                r8.mCurrentExpandedYPosition = r6
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r4 = r15.mViewState
                int r6 = r8.mCurrentYPosition
                float r6 = (float) r6
                r11 = 4
                r4.location = r11
                r11 = 0
                int r6 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
                if (r6 > 0) goto L_0x0385
                r6 = 2
                r4.location = r6
            L_0x0385:
                float r4 = r3.yTranslation
                float r6 = r5.mStackY
                float r4 = r4 + r6
                r3.yTranslation = r4
                int r10 = r10 + 1
                r3 = 0
                r4 = 0
                r6 = 0
                r12 = 8
                goto L_0x01d3
            L_0x0395:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                int r3 = r3.size()
                r4 = 0
            L_0x039c:
                if (r4 >= r3) goto L_0x03ba
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r9 = r8.visibleChildren
                java.lang.Object r9 = r9.get(r4)
                com.android.systemui.statusbar.notification.row.ExpandableView r9 = (com.android.systemui.statusbar.notification.row.ExpandableView) r9
                boolean r10 = r9 instanceof com.android.systemui.statusbar.notification.row.ActivatableNotificationView
                if (r10 == 0) goto L_0x03b7
                boolean r10 = r9.isAboveShelf()
                if (r10 != 0) goto L_0x03bb
                boolean r9 = r9.showingPulsing()
                if (r9 == 0) goto L_0x03b7
                goto L_0x03bb
            L_0x03b7:
                int r4 = r4 + 1
                goto L_0x039c
            L_0x03ba:
                r4 = -1
            L_0x03bb:
                int r3 = r3 - r14
                r9 = 0
            L_0x03bd:
                if (r3 < 0) goto L_0x046b
                if (r3 != r4) goto L_0x03c3
                r10 = r14
                goto L_0x03c4
            L_0x03c3:
                r10 = 0
            L_0x03c4:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r12 = r8.visibleChildren
                java.lang.Object r12 = r12.get(r3)
                com.android.systemui.statusbar.notification.row.ExpandableView r12 = (com.android.systemui.statusbar.notification.row.ExpandableView) r12
                java.util.Objects.requireNonNull(r12)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r15 = r12.mViewState
                int r6 = r5.mZDistanceBetweenElements
                r11 = 0
                float r14 = (float) r11
                boolean r11 = r12.mustStayOnScreen()
                if (r11 == 0) goto L_0x040d
                boolean r11 = r15.headsUpIsVisible
                if (r11 != 0) goto L_0x040d
                boolean r11 = r5.isDozingAndNotPulsing(r12)
                if (r11 != 0) goto L_0x040d
                float r11 = r15.yTranslation
                int r13 = r5.mTopPadding
                float r13 = (float) r13
                r20 = r4
                float r4 = r5.mStackTranslation
                float r13 = r13 + r4
                int r4 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r4 >= 0) goto L_0x040f
                r4 = 0
                int r10 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
                r4 = 1065353216(0x3f800000, float:1.0)
                if (r10 == 0) goto L_0x03fc
                float r9 = r9 + r4
                goto L_0x0407
            L_0x03fc:
                float r13 = r13 - r11
                int r10 = r15.height
                float r10 = (float) r10
                float r13 = r13 / r10
                float r10 = java.lang.Math.min(r4, r13)
                float r10 = r10 + r9
                r9 = r10
            L_0x0407:
                float r4 = (float) r6
                float r4 = r4 * r9
                float r4 = r4 + r14
                r15.zTranslation = r4
                goto L_0x043b
            L_0x040d:
                r20 = r4
            L_0x040f:
                if (r10 == 0) goto L_0x044d
                com.android.systemui.statusbar.NotificationShelf r4 = r5.mShelf
                if (r4 != 0) goto L_0x0417
                r4 = 0
                goto L_0x041b
            L_0x0417:
                int r4 = r4.getHeight()
            L_0x041b:
                r10 = 0
                int r11 = r5.getInnerHeight(r10)
                int r11 = r11 - r4
                float r10 = (float) r11
                int r11 = r5.mTopPadding
                float r11 = (float) r11
                float r10 = r10 + r11
                float r11 = r5.mStackTranslation
                float r10 = r10 + r11
                float r11 = r15.yTranslation
                int r13 = r12.getIntrinsicHeight()
                float r13 = (float) r13
                float r11 = r11 + r13
                int r13 = r2.mPaddingBetweenElements
                float r13 = (float) r13
                float r11 = r11 + r13
                int r13 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
                if (r13 <= 0) goto L_0x043e
                r15.zTranslation = r14
            L_0x043b:
                r4 = 1065353216(0x3f800000, float:1.0)
                goto L_0x0451
            L_0x043e:
                float r11 = r11 - r10
                float r4 = (float) r4
                float r11 = r11 / r4
                r4 = 1065353216(0x3f800000, float:1.0)
                float r10 = java.lang.Math.min(r11, r4)
                float r6 = (float) r6
                float r10 = r10 * r6
                float r10 = r10 + r14
                r15.zTranslation = r10
                goto L_0x0451
            L_0x044d:
                r4 = 1065353216(0x3f800000, float:1.0)
                r15.zTranslation = r14
            L_0x0451:
                float r6 = r15.zTranslation
                float r10 = r12.getHeaderVisibleAmount()
                float r13 = r4 - r10
                int r4 = r2.mPinnedZTranslationExtra
                float r4 = (float) r4
                float r13 = r13 * r4
                float r13 = r13 + r6
                r15.zTranslation = r13
                int r3 = r3 + -1
                r4 = r20
                r11 = 1073741824(0x40000000, float:2.0)
                r13 = 1065353216(0x3f800000, float:1.0)
                r14 = 1
                goto L_0x03bd
            L_0x046b:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                int r3 = r3.size()
                float r4 = r2.mHeadsUpInset
                int r6 = r5.mStackTopMargin
                float r6 = (float) r6
                float r4 = r4 - r6
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = r5.getTrackedHeadsUpRow()
                if (r6 == 0) goto L_0x048e
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r6 = r6.mViewState
                if (r6 == 0) goto L_0x048e
                float r9 = r6.yTranslation
                float r10 = r5.mStackTranslation
                float r9 = r9 - r10
                float r10 = r5.mAppearFraction
                float r9 = android.util.MathUtils.lerp(r4, r9, r10)
                r6.yTranslation = r9
            L_0x048e:
                r6 = 0
                r9 = 0
            L_0x0490:
                if (r6 >= r3) goto L_0x05b0
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r10 = r8.visibleChildren
                java.lang.Object r10 = r10.get(r6)
                android.view.View r10 = (android.view.View) r10
                boolean r11 = r10 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r11 != 0) goto L_0x049f
                goto L_0x04ac
            L_0x049f:
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r10 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r10
                java.util.Objects.requireNonNull(r10)
                boolean r11 = r10.mIsHeadsUp
                if (r11 != 0) goto L_0x04b0
                boolean r11 = r10.mHeadsupDisappearRunning
                if (r11 != 0) goto L_0x04b0
            L_0x04ac:
                r20 = r3
                goto L_0x05aa
            L_0x04b0:
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r11 = r10.mViewState
                if (r9 != 0) goto L_0x04c2
                boolean r12 = r10.mustStayOnScreen()
                if (r12 == 0) goto L_0x04c2
                boolean r12 = r11.headsUpIsVisible
                if (r12 != 0) goto L_0x04c2
                r12 = 1
                r11.location = r12
                r9 = r10
            L_0x04c2:
                if (r9 != r10) goto L_0x04c6
                r12 = 1
                goto L_0x04c7
            L_0x04c6:
                r12 = 0
            L_0x04c7:
                float r13 = r11.yTranslation
                int r14 = r11.height
                float r14 = (float) r14
                float r13 = r13 + r14
                boolean r14 = r2.mIsExpanded
                if (r14 == 0) goto L_0x053e
                boolean r14 = r10.mustStayOnScreen()
                if (r14 == 0) goto L_0x053e
                boolean r14 = r11.headsUpIsVisible
                if (r14 != 0) goto L_0x053e
                boolean r14 = r10.showingPulsing()
                if (r14 != 0) goto L_0x053e
                int r14 = r5.mTopPadding
                float r14 = (float) r14
                float r15 = r5.mStackTranslation
                float r14 = r14 + r15
                float r15 = r11.yTranslation
                float r14 = java.lang.Math.max(r14, r15)
                int r15 = r11.height
                float r15 = (float) r15
                r20 = r3
                float r3 = r11.yTranslation
                float r3 = r14 - r3
                float r15 = r15 - r3
                int r3 = r10.getCollapsedHeight()
                float r3 = (float) r3
                float r3 = java.lang.Math.max(r15, r3)
                int r3 = (int) r3
                r11.height = r3
                r11.yTranslation = r14
                if (r12 == 0) goto L_0x0540
                boolean r3 = r10.isAboveShelf()
                if (r3 == 0) goto L_0x0540
                float r3 = r5.mMaxHeadsUpTranslation
                r14 = 0
                int r15 = r5.getInnerHeight(r14)
                float r14 = (float) r15
                int r15 = r5.mTopPadding
                float r15 = (float) r15
                float r14 = r14 + r15
                float r15 = r5.mStackTranslation
                float r14 = r14 + r15
                float r3 = java.lang.Math.min(r3, r14)
                int r14 = r10.getCollapsedHeight()
                float r14 = (float) r14
                float r14 = r3 - r14
                float r15 = r11.yTranslation
                float r14 = java.lang.Math.min(r15, r14)
                int r15 = r11.height
                float r15 = (float) r15
                float r3 = r3 - r14
                float r3 = java.lang.Math.min(r15, r3)
                int r3 = (int) r3
                r11.height = r3
                r11.yTranslation = r14
                r3 = 0
                r11.hidden = r3
                goto L_0x0540
            L_0x053e:
                r20 = r3
            L_0x0540:
                boolean r3 = r10.mIsPinned
                if (r3 == 0) goto L_0x0599
                float r3 = r11.yTranslation
                float r3 = java.lang.Math.max(r3, r4)
                r11.yTranslation = r3
                int r3 = r10.getIntrinsicHeight()
                int r14 = r11.height
                int r3 = java.lang.Math.max(r3, r14)
                r11.height = r3
                r3 = 0
                r11.hidden = r3
                if (r9 != 0) goto L_0x055f
                r3 = 0
                goto L_0x0561
            L_0x055f:
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r9.mViewState
            L_0x0561:
                if (r3 == 0) goto L_0x0589
                if (r12 != 0) goto L_0x0589
                boolean r14 = r2.mIsExpanded
                if (r14 == 0) goto L_0x0573
                float r14 = r3.yTranslation
                int r15 = r3.height
                float r15 = (float) r15
                float r14 = r14 + r15
                int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
                if (r13 <= 0) goto L_0x0589
            L_0x0573:
                int r13 = r10.getIntrinsicHeight()
                r11.height = r13
                float r14 = r3.yTranslation
                int r3 = r3.height
                float r3 = (float) r3
                float r14 = r14 + r3
                float r3 = (float) r13
                float r14 = r14 - r3
                float r3 = r11.yTranslation
                float r3 = java.lang.Math.min(r14, r3)
                r11.yTranslation = r3
            L_0x0589:
                boolean r3 = r2.mIsExpanded
                if (r3 != 0) goto L_0x0599
                if (r12 == 0) goto L_0x0599
                int r3 = r5.mScrollY
                if (r3 <= 0) goto L_0x0599
                float r12 = r11.yTranslation
                float r3 = (float) r3
                float r12 = r12 - r3
                r11.yTranslation = r12
            L_0x0599:
                boolean r3 = r10.mHeadsupDisappearRunning
                if (r3 == 0) goto L_0x05aa
                float r3 = r11.yTranslation
                float r10 = r2.mHeadsUpInset
                float r3 = java.lang.Math.max(r3, r10)
                r11.yTranslation = r3
                r3 = 0
                r11.hidden = r3
            L_0x05aa:
                int r6 = r6 + 1
                r3 = r20
                goto L_0x0490
            L_0x05b0:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                int r3 = r3.size()
                r4 = 0
            L_0x05b7:
                if (r4 >= r3) goto L_0x05df
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                java.lang.Object r6 = r6.get(r4)
                android.view.View r6 = (android.view.View) r6
                boolean r9 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r9 != 0) goto L_0x05c6
                goto L_0x05dc
            L_0x05c6:
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
                boolean r9 = r6.showingPulsing()
                if (r9 == 0) goto L_0x05dc
                if (r4 != 0) goto L_0x05d7
                boolean r9 = r5.isPulseExpanding()
                if (r9 == 0) goto L_0x05d7
                goto L_0x05dc
            L_0x05d7:
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r6 = r6.mViewState
                r9 = 0
                r6.hidden = r9
            L_0x05dc:
                int r4 = r4 + 1
                goto L_0x05b7
            L_0x05df:
                boolean r3 = r5.mDimmed
                if (r3 == 0) goto L_0x05f3
                boolean r3 = r5.isPulseExpanding()
                if (r3 == 0) goto L_0x05f1
                float r3 = r5.mDozeAmount
                r4 = 1065353216(0x3f800000, float:1.0)
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 == 0) goto L_0x05f3
            L_0x05f1:
                r3 = 1
                goto L_0x05f4
            L_0x05f3:
                r3 = 0
            L_0x05f4:
                boolean r4 = r5.mHideSensitive
                com.android.systemui.statusbar.notification.row.ActivatableNotificationView r6 = r5.mActivatedChild
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r9 = r8.visibleChildren
                int r9 = r9.size()
                r10 = 0
            L_0x05ff:
                if (r10 >= r9) goto L_0x062d
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r11 = r8.visibleChildren
                java.lang.Object r11 = r11.get(r10)
                com.android.systemui.statusbar.notification.row.ExpandableView r11 = (com.android.systemui.statusbar.notification.row.ExpandableView) r11
                java.util.Objects.requireNonNull(r11)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r12 = r11.mViewState
                java.util.Objects.requireNonNull(r12)
                r12.hideSensitive = r4
                if (r6 != r11) goto L_0x0617
                r11 = 1
                goto L_0x0618
            L_0x0617:
                r11 = 0
            L_0x0618:
                if (r3 == 0) goto L_0x0628
                if (r11 == 0) goto L_0x0628
                float r11 = r12.zTranslation
                int r13 = r5.mZDistanceBetweenElements
                float r13 = (float) r13
                r14 = 1073741824(0x40000000, float:2.0)
                float r13 = r13 * r14
                float r13 = r13 + r11
                r12.zTranslation = r13
                goto L_0x062a
            L_0x0628:
                r14 = 1073741824(0x40000000, float:2.0)
            L_0x062a:
                int r10 = r10 + 1
                goto L_0x05ff
            L_0x062d:
                boolean r3 = r5.isOnKeyguard()
                if (r3 == 0) goto L_0x0635
                r3 = 0
                goto L_0x063b
            L_0x0635:
                float r3 = r5.mStackY
                int r4 = r5.mScrollY
                float r4 = (float) r4
                float r3 = r3 - r4
            L_0x063b:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r4 = r8.visibleChildren
                int r4 = r4.size()
                r6 = 0
                r9 = 0
                r10 = 1
            L_0x0644:
                if (r9 >= r4) goto L_0x06bb
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r11 = r8.visibleChildren
                java.lang.Object r11 = r11.get(r9)
                com.android.systemui.statusbar.notification.row.ExpandableView r11 = (com.android.systemui.statusbar.notification.row.ExpandableView) r11
                java.util.Objects.requireNonNull(r11)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r12 = r11.mViewState
                boolean r13 = r11.mustStayOnScreen()
                if (r13 == 0) goto L_0x065d
                boolean r13 = r12.headsUpIsVisible
                if (r13 == 0) goto L_0x0661
            L_0x065d:
                float r6 = java.lang.Math.max(r3, r6)
            L_0x0661:
                float r13 = r12.yTranslation
                int r14 = r12.height
                float r14 = (float) r14
                float r14 = r14 + r13
                boolean r15 = r11 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r15 == 0) goto L_0x0675
                boolean r15 = r11.isPinned()
                if (r15 == 0) goto L_0x0675
                r16 = r3
                r15 = 1
                goto L_0x0678
            L_0x0675:
                r16 = r3
                r15 = 0
            L_0x0678:
                boolean r3 = r5.mIsShadeOpening
                if (r3 == 0) goto L_0x0684
                boolean r3 = r5.mShadeExpanded
                if (r3 != 0) goto L_0x0684
                r18 = r4
                r3 = 1
                goto L_0x0687
            L_0x0684:
                r18 = r4
                r3 = 0
            L_0x0687:
                boolean r4 = r2.mClipNotificationScrollToTop
                if (r4 == 0) goto L_0x069f
                boolean r4 = r12.inShelf
                if (r4 == 0) goto L_0x0693
                if (r15 == 0) goto L_0x069f
                if (r10 != 0) goto L_0x069f
            L_0x0693:
                int r4 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
                if (r4 >= 0) goto L_0x069f
                if (r3 == 0) goto L_0x069f
                float r3 = r6 - r13
                int r3 = (int) r3
                r12.clipTopAmount = r3
                goto L_0x06a2
            L_0x069f:
                r3 = 0
                r12.clipTopAmount = r3
            L_0x06a2:
                if (r15 == 0) goto L_0x06a5
                r10 = 0
            L_0x06a5:
                boolean r3 = r11.isTransparent()
                if (r3 != 0) goto L_0x06b4
                if (r15 == 0) goto L_0x06ae
                goto L_0x06af
            L_0x06ae:
                r13 = r14
            L_0x06af:
                float r3 = java.lang.Math.max(r6, r13)
                r6 = r3
            L_0x06b4:
                int r9 = r9 + 1
                r3 = r16
                r4 = r18
                goto L_0x0644
            L_0x06bb:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r8.visibleChildren
                int r2 = r2.size()
                r3 = 0
            L_0x06c2:
                if (r3 >= r2) goto L_0x06db
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r4 = r8.visibleChildren
                java.lang.Object r4 = r4.get(r3)
                com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
                java.util.Objects.requireNonNull(r4)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r4 = r4.mViewState
                if (r3 < r7) goto L_0x06d5
                r6 = 1
                goto L_0x06d6
            L_0x06d5:
                r6 = 0
            L_0x06d6:
                r4.belowSpeedBump = r6
                int r3 = r3 + 1
                goto L_0x06c2
            L_0x06db:
                com.android.systemui.statusbar.NotificationShelf r2 = r5.mShelf
                if (r2 != 0) goto L_0x06e1
                goto L_0x07c8
            L_0x06e1:
                com.android.systemui.statusbar.notification.row.ExpandableView r3 = r5.mLastVisibleBackgroundChild
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r4 = r2.mViewState
                com.android.systemui.statusbar.NotificationShelf$ShelfState r4 = (com.android.systemui.statusbar.NotificationShelf.ShelfState) r4
                boolean r6 = r2.mShowNotificationShelf
                if (r6 == 0) goto L_0x0794
                if (r3 == 0) goto L_0x0794
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r3.mViewState
                r4.copyFrom(r3)
                int r6 = r2.getHeight()
                r4.height = r6
                r6 = 0
                float r7 = (float) r6
                r4.zTranslation = r7
                r4.clipTopAmount = r6
                boolean r6 = r5.mExpansionChanging
                if (r6 == 0) goto L_0x0711
                boolean r6 = r5.isOnKeyguard()
                if (r6 != 0) goto L_0x0711
                float r6 = r5.mExpansionFraction
                float r6 = com.android.systemui.animation.ShadeInterpolation.getContentAlpha(r6)
                r4.alpha = r6
                goto L_0x0719
            L_0x0711:
                float r6 = r5.mHideAmount
                r7 = 1065353216(0x3f800000, float:1.0)
                float r13 = r7 - r6
                r4.alpha = r13
            L_0x0719:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = r2.mHostLayoutController
                java.util.Objects.requireNonNull(r6)
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                int r6 = r6.getSpeedBumpIndex()
                if (r6 != 0) goto L_0x0728
                r6 = 1
                goto L_0x0729
            L_0x0728:
                r6 = 0
            L_0x0729:
                r4.belowSpeedBump = r6
                r6 = 0
                r4.hideSensitive = r6
                float r6 = r2.getTranslationX()
                r4.xTranslation = r6
                boolean r3 = r3.inShelf
                r4.hasItemsInStableShelf = r3
                com.android.systemui.statusbar.notification.row.ExpandableView r3 = r8.firstViewInShelf
                r4.firstViewInShelf = r3
                int r3 = r2.mNotGoneIndex
                r6 = -1
                if (r3 == r6) goto L_0x0749
                int r6 = r4.notGoneIndex
                int r3 = java.lang.Math.min(r6, r3)
                r4.notGoneIndex = r3
            L_0x0749:
                com.android.systemui.statusbar.notification.stack.AmbientState r3 = r2.mAmbientState
                java.util.Objects.requireNonNull(r3)
                boolean r3 = r3.mShadeExpanded
                if (r3 == 0) goto L_0x0759
                com.android.systemui.statusbar.notification.row.ExpandableView r3 = r8.firstViewInShelf
                if (r3 != 0) goto L_0x0757
                goto L_0x0759
            L_0x0757:
                r3 = 0
                goto L_0x075a
            L_0x0759:
                r3 = 1
            L_0x075a:
                r4.hidden = r3
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                com.android.systemui.statusbar.notification.row.ExpandableView r6 = r8.firstViewInShelf
                int r3 = r3.indexOf(r6)
                com.android.systemui.statusbar.notification.stack.AmbientState r6 = r2.mAmbientState
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mExpansionChanging
                if (r6 == 0) goto L_0x0788
                com.android.systemui.statusbar.notification.row.ExpandableView r6 = r8.firstViewInShelf
                if (r6 == 0) goto L_0x0788
                if (r3 <= 0) goto L_0x0788
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r6 = r8.visibleChildren
                r7 = 1
                int r3 = r3 - r7
                java.lang.Object r3 = r6.get(r3)
                com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
                java.util.Objects.requireNonNull(r3)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r3.mViewState
                boolean r3 = r3.hidden
                if (r3 == 0) goto L_0x0788
                r4.hidden = r7
            L_0x0788:
                float r3 = r5.mStackY
                float r5 = r5.mStackHeight
                float r3 = r3 + r5
                int r5 = r4.height
                float r5 = (float) r5
                float r3 = r3 - r5
                r4.yTranslation = r3
                goto L_0x079e
            L_0x0794:
                r3 = 1
                r4.hidden = r3
                r3 = 64
                r4.location = r3
                r3 = 0
                r4.hasItemsInStableShelf = r3
            L_0x079e:
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r2 = r2.mViewState
                boolean r3 = r2.hidden
                if (r3 == 0) goto L_0x07a5
                goto L_0x07c8
            L_0x07a5:
                float r2 = r2.yTranslation
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                java.util.Iterator r3 = r3.iterator()
            L_0x07ad:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L_0x07c8
                java.lang.Object r4 = r3.next()
                com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
                java.util.Objects.requireNonNull(r4)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r4 = r4.mViewState
                float r5 = r4.yTranslation
                int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
                if (r5 < 0) goto L_0x07ad
                r5 = 0
                r4.alpha = r5
                goto L_0x07ad
            L_0x07c8:
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r8.visibleChildren
                int r2 = r2.size()
                r4 = 0
            L_0x07cf:
                if (r4 >= r2) goto L_0x09ac
                java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r3 = r8.visibleChildren
                java.lang.Object r3 = r3.get(r4)
                com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
                boolean r5 = r3 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r5 == 0) goto L_0x099d
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
                java.util.Objects.requireNonNull(r3)
                boolean r5 = r3.mIsSummaryWithChildren
                if (r5 == 0) goto L_0x099d
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r5 = r3.mViewState
                com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r3 = r3.mChildrenContainer
                java.util.Objects.requireNonNull(r3)
                java.util.ArrayList r6 = r3.mAttachedChildren
                int r6 = r6.size()
                int r7 = r3.mNotificationHeaderMargin
                int r9 = r3.mCurrentHeaderTranslation
                int r7 = r7 + r9
                int r9 = r3.getMaxAllowedVisibleChildren()
                r10 = 1
                int r9 = r9 - r10
                int r10 = r9 + 1
                boolean r11 = r3.mUserLocked
                if (r11 == 0) goto L_0x080c
                boolean r11 = r3.showingAsLowPriority()
                if (r11 != 0) goto L_0x080c
                r11 = 1
                goto L_0x080d
            L_0x080c:
                r11 = 0
            L_0x080d:
                boolean r12 = r3.mUserLocked
                if (r12 == 0) goto L_0x081d
                float r10 = r3.getGroupExpandFraction()
                r12 = 1
                int r13 = r3.getMaxAllowedVisibleChildren(r12)
                r12 = r10
                r10 = r13
                goto L_0x081e
            L_0x081d:
                r12 = 0
            L_0x081e:
                boolean r13 = r3.mChildrenExpanded
                if (r13 == 0) goto L_0x082c
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r13 = r3.mContainingNotification
                boolean r13 = r13.isGroupExpansionChanging()
                if (r13 != 0) goto L_0x082c
                r13 = 1
                goto L_0x082d
            L_0x082c:
                r13 = 0
            L_0x082d:
                r14 = 0
                r15 = 1
            L_0x082f:
                if (r14 >= r6) goto L_0x08f7
                r16 = r2
                java.util.ArrayList r2 = r3.mAttachedChildren
                java.lang.Object r2 = r2.get(r14)
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
                if (r15 != 0) goto L_0x0862
                if (r11 == 0) goto L_0x0851
                float r7 = (float) r7
                r17 = r8
                int r8 = r3.mChildPadding
                float r8 = (float) r8
                r18 = r15
                int r15 = r3.mDividerHeight
                float r15 = (float) r15
                float r8 = com.android.systemui.R$array.interpolate(r8, r15, r12)
                float r8 = r8 + r7
                int r7 = (int) r8
                goto L_0x085f
            L_0x0851:
                r17 = r8
                r18 = r15
                boolean r8 = r3.mChildrenExpanded
                if (r8 == 0) goto L_0x085c
                int r8 = r3.mDividerHeight
                goto L_0x085e
            L_0x085c:
                int r8 = r3.mChildPadding
            L_0x085e:
                int r7 = r7 + r8
            L_0x085f:
                r15 = r18
                goto L_0x0882
            L_0x0862:
                r17 = r8
                if (r11 == 0) goto L_0x0875
                float r7 = (float) r7
                int r8 = r3.mNotificationTopPadding
                int r15 = r3.mDividerHeight
                int r8 = r8 + r15
                float r8 = (float) r8
                r15 = 0
                float r8 = com.android.systemui.R$array.interpolate(r15, r8, r12)
                float r8 = r8 + r7
                int r7 = (int) r8
                goto L_0x0881
            L_0x0875:
                boolean r8 = r3.mChildrenExpanded
                if (r8 == 0) goto L_0x087f
                int r8 = r3.mNotificationTopPadding
                int r15 = r3.mDividerHeight
                int r8 = r8 + r15
                goto L_0x0880
            L_0x087f:
                r8 = 0
            L_0x0880:
                int r7 = r7 + r8
            L_0x0881:
                r15 = 0
            L_0x0882:
                java.util.Objects.requireNonNull(r2)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r8 = r2.mViewState
                int r2 = r2.getIntrinsicHeight()
                r8.height = r2
                r18 = r11
                int r11 = r7 + 0
                float r11 = (float) r11
                r8.yTranslation = r11
                r20 = r15
                r15 = 0
                r8.hidden = r15
                if (r13 == 0) goto L_0x08a2
                boolean r15 = r3.mEnableShadowOnChildNotifications
                if (r15 == 0) goto L_0x08a2
                float r15 = r5.zTranslation
                goto L_0x08a3
            L_0x08a2:
                r15 = 0
            L_0x08a3:
                r8.zTranslation = r15
                java.util.Objects.requireNonNull(r5)
                boolean r15 = r5.hideSensitive
                r8.hideSensitive = r15
                boolean r15 = r5.belowSpeedBump
                r8.belowSpeedBump = r15
                r15 = 0
                r8.clipTopAmount = r15
                r15 = 0
                r8.alpha = r15
                if (r14 >= r10) goto L_0x08c5
                boolean r11 = r3.showingAsLowPriority()
                if (r11 == 0) goto L_0x08c0
                r11 = r12
                goto L_0x08c2
            L_0x08c0:
                r11 = 1065353216(0x3f800000, float:1.0)
            L_0x08c2:
                r8.alpha = r11
                goto L_0x08e2
            L_0x08c5:
                r15 = 1065353216(0x3f800000, float:1.0)
                int r19 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
                if (r19 != 0) goto L_0x08e2
                if (r14 > r9) goto L_0x08e2
                int r15 = r3.mActualHeight
                float r15 = (float) r15
                float r15 = r15 - r11
                float r11 = (float) r2
                float r15 = r15 / r11
                r8.alpha = r15
                r11 = 1065353216(0x3f800000, float:1.0)
                float r15 = java.lang.Math.min(r11, r15)
                r11 = 0
                float r15 = java.lang.Math.max(r11, r15)
                r8.alpha = r15
            L_0x08e2:
                int r11 = r5.location
                r8.location = r11
                boolean r11 = r5.inShelf
                r8.inShelf = r11
                int r7 = r7 + r2
                int r14 = r14 + 1
                r2 = r16
                r8 = r17
                r11 = r18
                r15 = r20
                goto L_0x082f
            L_0x08f7:
                r16 = r2
                r17 = r8
                android.widget.TextView r2 = r3.mOverflowNumber
                if (r2 == 0) goto L_0x096d
                java.util.ArrayList r2 = r3.mAttachedChildren
                r7 = 1
                int r8 = r3.getMaxAllowedVisibleChildren(r7)
                int r6 = java.lang.Math.min(r8, r6)
                int r6 = r6 - r7
                java.lang.Object r2 = r2.get(r6)
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
                com.android.systemui.statusbar.notification.stack.ViewState r6 = r3.mGroupOverFlowState
                java.util.Objects.requireNonNull(r2)
                com.android.systemui.statusbar.notification.stack.ExpandableViewState r7 = r2.mViewState
                r6.copyFrom(r7)
                boolean r6 = r3.mChildrenExpanded
                if (r6 != 0) goto L_0x095d
                com.android.systemui.statusbar.notification.row.NotificationContentView r6 = r2.mPrivateLayout
                java.util.Objects.requireNonNull(r6)
                com.android.systemui.statusbar.notification.row.HybridNotificationView r6 = r6.mSingleLineView
                if (r6 == 0) goto L_0x096d
                android.widget.TextView r7 = r6.mTextView
                int r8 = r7.getVisibility()
                r9 = 8
                if (r8 != r9) goto L_0x0934
                android.widget.TextView r7 = r6.mTitleView
            L_0x0934:
                int r8 = r7.getVisibility()
                if (r8 != r9) goto L_0x093b
                goto L_0x093c
            L_0x093b:
                r6 = r7
            L_0x093c:
                com.android.systemui.statusbar.notification.stack.ViewState r7 = r3.mGroupOverFlowState
                float r8 = r6.getAlpha()
                r7.alpha = r8
                com.android.systemui.statusbar.notification.stack.ViewState r7 = r3.mGroupOverFlowState
                float r8 = r7.yTranslation
                int[] r10 = com.android.systemui.R$array.sLocationBase
                r2.getLocationOnScreen(r10)
                int[] r2 = com.android.systemui.R$array.sLocationOffset
                r6.getLocationOnScreen(r2)
                r6 = 1
                r2 = r2[r6]
                r10 = r10[r6]
                int r2 = r2 - r10
                float r2 = (float) r2
                float r8 = r8 + r2
                r7.yTranslation = r8
                goto L_0x096f
            L_0x095d:
                r9 = 8
                com.android.systemui.statusbar.notification.stack.ViewState r2 = r3.mGroupOverFlowState
                float r6 = r2.yTranslation
                int r7 = r3.mNotificationHeaderMargin
                float r7 = (float) r7
                float r6 = r6 + r7
                r2.yTranslation = r6
                r11 = 0
                r2.alpha = r11
                goto L_0x0970
            L_0x096d:
                r9 = 8
            L_0x096f:
                r11 = 0
            L_0x0970:
                android.view.NotificationHeaderView r2 = r3.mNotificationHeader
                if (r2 == 0) goto L_0x09a4
                com.android.systemui.statusbar.notification.stack.ViewState r2 = r3.mHeaderViewState
                if (r2 != 0) goto L_0x097f
                com.android.systemui.statusbar.notification.stack.ViewState r2 = new com.android.systemui.statusbar.notification.stack.ViewState
                r2.<init>()
                r3.mHeaderViewState = r2
            L_0x097f:
                com.android.systemui.statusbar.notification.stack.ViewState r2 = r3.mHeaderViewState
                android.view.NotificationHeaderView r6 = r3.mNotificationHeader
                r2.initFrom(r6)
                com.android.systemui.statusbar.notification.stack.ViewState r2 = r3.mHeaderViewState
                if (r13 == 0) goto L_0x098d
                float r5 = r5.zTranslation
                goto L_0x098e
            L_0x098d:
                r5 = r11
            L_0x098e:
                r2.zTranslation = r5
                int r5 = r3.mCurrentHeaderTranslation
                float r5 = (float) r5
                r2.yTranslation = r5
                float r3 = r3.mHeaderVisibleAmount
                r2.alpha = r3
                r3 = 0
                r2.hidden = r3
                goto L_0x09a4
            L_0x099d:
                r16 = r2
                r17 = r8
                r9 = 8
                r11 = 0
            L_0x09a4:
                int r4 = r4 + 1
                r2 = r16
                r8 = r17
                goto L_0x07cf
            L_0x09ac:
                com.android.systemui.statusbar.notification.stack.StackStateAnimator r2 = r1.mStateAnimator
                java.util.Objects.requireNonNull(r2)
                java.util.HashSet<android.animation.Animator> r2 = r2.mAnimatorSet
                boolean r2 = r2.isEmpty()
                r3 = 1
                r2 = r2 ^ r3
                if (r2 != 0) goto L_0x09c3
                boolean r2 = r1.mNeedsAnimation
                if (r2 != 0) goto L_0x09c3
                r1.applyCurrentState()
                goto L_0x09c6
            L_0x09c3:
                r1.startAnimationToState$1()
            L_0x09c6:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
                r2 = 0
                r1.mChildrenUpdateRequested = r2
                android.view.ViewTreeObserver r1 = r1.getViewTreeObserver()
                r1.removeOnPreDrawListener(r0)
                r0 = 1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13551.onPreDraw():boolean");
        }
    };
    public ClearAllAnimationListener mClearAllAnimationListener;
    public boolean mClearAllEnabled;
    public boolean mClearAllInProgress;
    public ClearAllListener mClearAllListener;
    public HashSet<ExpandableView> mClearTransientViewsWhenFinished = new HashSet<>();
    public final Rect mClipRect = new Rect();
    public int mContentHeight;
    public boolean mContinuousBackgroundUpdate;
    public boolean mContinuousShadowUpdate;
    public NotificationStackScrollLayoutController mController;
    public int mCornerRadius;
    public int mCurrentStackHeight = Integer.MAX_VALUE;
    public final boolean mDebugLines;
    public Paint mDebugPaint;
    public final boolean mDebugRemoveAnimation;
    public HashSet mDebugTextUsedYPositions;
    public float mDimAmount;
    public ValueAnimator mDimAnimator;
    public final C13593 mDimEndListener = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            NotificationStackScrollLayout.this.mDimAnimator = null;
        }
    };
    public C13604 mDimUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            boolean z = NotificationStackScrollLayout.SPEW;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mDimAmount = floatValue;
            notificationStackScrollLayout.updateBackgroundDimming();
        }
    };
    public boolean mDimmedNeedsAnimation;
    public boolean mDisallowDismissInThisMotion;
    public boolean mDisallowScrollingInThisMotion;
    public boolean mDismissUsingRowTranslationX = true;
    public boolean mDontClampNextScroll;
    public boolean mDontReportNextOverScroll;
    public int mDownX;
    public EmptyShadeView mEmptyShadeView;
    public boolean mEverythingNeedsAnimation;
    public ExpandHelper mExpandHelper;
    public C135711 mExpandHelperCallback = new ExpandHelper.Callback() {
        public final boolean canChildBeExpanded(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandable() && !expandableNotificationRow.areGutsExposed()) {
                    if (!NotificationStackScrollLayout.this.mIsExpanded) {
                        Objects.requireNonNull(expandableNotificationRow);
                        if (!expandableNotificationRow.mIsPinned) {
                            return true;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        public final void expansionStateChanged(boolean z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            notificationStackScrollLayout.mExpandingNotification = z;
            if (!notificationStackScrollLayout.mExpandedInThisMotion) {
                notificationStackScrollLayout.mMaxScrollAfterExpand = notificationStackScrollLayout.mOwnScrollY;
                notificationStackScrollLayout.mExpandedInThisMotion = true;
            }
        }

        public final void setUserExpandedChild(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (!z || !NotificationStackScrollLayout.this.onKeyguard()) {
                    expandableNotificationRow.setUserExpanded(z, true);
                    expandableNotificationRow.onExpandedByGesture(z);
                    return;
                }
                expandableNotificationRow.setUserLocked(false);
                NotificationStackScrollLayout.this.updateContentHeight();
                NotificationStackScrollLayout.this.notifyHeightChangeListener(expandableNotificationRow);
            }
        }

        public final void setUserLockedChild(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) view).setUserLocked(z);
            }
            NotificationStackScrollLayout.this.cancelLongPress();
            NotificationStackScrollLayout.this.requestDisallowInterceptTouchEvent(true);
        }
    };
    public ExpandableNotificationRow mExpandedGroupView;
    public float mExpandedHeight;
    public ArrayList<BiConsumer<Float, Float>> mExpandedHeightListeners = new ArrayList<>();
    public boolean mExpandedInThisMotion;
    public boolean mExpandingNotification;
    public ExpandableNotificationRow mExpandingNotificationRow;
    public float mExtraTopInsetForFullShadeTransition;
    public ForegroundServiceDungeonView mFgsSectionView;
    public Runnable mFinishScrollingCallback;
    public boolean mFlingAfterUpEvent;
    public FooterClearAllListener mFooterClearAllListener;
    public FooterView mFooterView;
    public boolean mForceNoOverlappingRendering;
    public View mForcedScroll;
    public boolean mForwardScrollable;
    public HashSet<View> mFromMoreCardAdditions = new HashSet<>();
    public int mGapHeight;
    public boolean mGenerateChildOrderChangedEvent;
    public long mGoToFullShadeDelay;
    public boolean mGoToFullShadeNeedsAnimation;
    public GroupExpansionManager mGroupExpansionManager;
    public GroupMembershipManager mGroupMembershipManager;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final C13659 mHeadsUpCallback = new HeadsUpTouchHelper.Callback() {
    };
    public HashSet<Pair<ExpandableNotificationRow, Boolean>> mHeadsUpChangeAnimations = new HashSet<>();
    public boolean mHeadsUpGoingAwayAnimationsAllowed = true;
    public int mHeadsUpInset;
    public boolean mHideSensitiveNeedsAnimation;
    public PathInterpolator mHideXInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    public boolean mHighPriorityBeforeSpeedBump;
    public boolean mInHeadsUpPinnedMode;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public float mInterpolatedHideAmount = 0.0f;
    public int mIntrinsicContentHeight;
    public int mIntrinsicPadding;
    public boolean mIsBeingDragged;
    public boolean mIsClipped;
    public boolean mIsCurrentUserSetup;
    public boolean mIsExpanded = true;
    public boolean mIsExpansionChanging;
    public boolean mIsFlinging;
    public boolean mIsRemoteInputActive;
    public float mKeyguardBottomPadding = -1.0f;
    public boolean mKeyguardBypassEnabled;
    public int mLastMotionY;
    public float mLastSentAppear;
    public float mLastSentExpandedHeight;
    public ExpandAnimationParameters mLaunchAnimationParams;
    public final Path mLaunchedNotificationClipPath = new Path();
    public float[] mLaunchedNotificationRadii = new float[8];
    public boolean mLaunchingNotification;
    public boolean mLaunchingNotificationNeedsToBeClipped;
    public float mLinearHideAmount = 0.0f;
    public NotificationLogger.OnChildLocationsChangedListener mListener;
    public NotificationStackScrollLogger mLogger;
    public View.OnClickListener mManageButtonClickListener;
    public int mMaxDisplayedNotifications = -1;
    public int mMaxLayoutHeight;
    public float mMaxOverScroll;
    public int mMaxScrollAfterExpand;
    public int mMaxTopPadding;
    public int mMaximumVelocity;
    public int mMinInteractionHeight;
    public float mMinTopOverScrollToEscape;
    public int mMinimumPaddings;
    public int mMinimumVelocity;
    public boolean mNeedViewResizeAnimation;
    public boolean mNeedsAnimation;
    public long mNumHeadsUp;
    public final C13626 mOnChildHeightChangedListener = new ExpandableView.OnHeightChangedListener() {
        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayout.this.onChildHeightChanged(expandableView, z);
        }

        public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
            boolean z;
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            Objects.requireNonNull(notificationStackScrollLayout);
            if ((notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow))) {
                z = true;
            } else {
                z = false;
            }
            boolean z2 = expandableNotificationRow instanceof ExpandableNotificationRow;
            if (z2) {
                expandableNotificationRow.setIconAnimationRunning(z);
            }
            if (z2) {
                expandableNotificationRow.setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
            }
        }
    };
    public OnEmptySpaceClickListener mOnEmptySpaceClickListener;
    public ExpandableView.OnHeightChangedListener mOnHeightChangedListener;
    public Consumer<Boolean> mOnStackYChanged;
    public boolean mOnlyScrollingInThisMotion;
    public final C13615 mOutlineProvider = new ViewOutlineProvider() {
        public final void getOutline(View view, Outline outline) {
            if (NotificationStackScrollLayout.this.mAmbientState.isHiddenAtAll()) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                float interpolation = notificationStackScrollLayout.mHideXInterpolator.getInterpolation((1.0f - notificationStackScrollLayout.mLinearHideAmount) * notificationStackScrollLayout.mBackgroundXFactor);
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                Rect rect = notificationStackScrollLayout2.mBackgroundAnimationRect;
                float f = (float) notificationStackScrollLayout2.mCornerRadius;
                outline.setRoundRect(rect, MathUtils.lerp(f / 2.0f, f, interpolation));
                AmbientState ambientState = NotificationStackScrollLayout.this.mAmbientState;
                Objects.requireNonNull(ambientState);
                outline.setAlpha(1.0f - ambientState.mHideAmount);
                return;
            }
            ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
        }
    };
    public float mOverScrolledBottomPixels;
    public float mOverScrolledTopPixels;
    public int mOverflingDistance;
    public OnOverscrollTopChangedListener mOverscrollTopChangedListener;
    public int mOwnScrollY;
    public int mPaddingBetweenElements;
    public boolean mPanelTracking;
    public boolean mPulsing;
    public ViewGroup mQsContainer;
    public boolean mQsExpanded;
    public float mQsExpansionFraction;
    public int mQsScrollBoundaryPosition;
    public int mQsTilePadding;
    public C13648 mReclamp = new Runnable() {
        public final void run() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            boolean z = NotificationStackScrollLayout.SPEW;
            int scrollRange = notificationStackScrollLayout.getScrollRange();
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
            OverScroller overScroller = notificationStackScrollLayout2.mScroller;
            int access$000 = notificationStackScrollLayout2.mScrollX;
            int i = NotificationStackScrollLayout.this.mOwnScrollY;
            overScroller.startScroll(access$000, i, 0, scrollRange - i);
            NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayout.this;
            notificationStackScrollLayout3.mDontReportNextOverScroll = true;
            notificationStackScrollLayout3.mDontClampNextScroll = true;
            notificationStackScrollLayout3.animateScroll();
        }
    };
    public WMShell$7$$ExternalSyntheticLambda2 mReflingAndAnimateScroll = new WMShell$7$$ExternalSyntheticLambda2(this, 6);
    public Rect mRequestedClipBounds;
    public final Path mRoundedClipPath = new Path();
    public int mRoundedRectClippingBottom;
    public int mRoundedRectClippingLeft;
    public int mRoundedRectClippingRight;
    public int mRoundedRectClippingTop;
    public C13582 mRunningAnimationUpdater = new ViewTreeObserver.OnPreDrawListener() {
        public final boolean onPreDraw() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            boolean z = NotificationStackScrollLayout.SPEW;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mShelf.updateAppearance();
            if (notificationStackScrollLayout.mNeedsAnimation || notificationStackScrollLayout.mChildrenUpdateRequested) {
                return true;
            }
            notificationStackScrollLayout.updateBackground();
            return true;
        }
    };
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final C13637 mScrollAdapter = new ScrollAdapter() {
    };
    public Consumer<Integer> mScrollListener;
    public boolean mScrollable;
    public boolean mScrolledToTopOnFirstDown;
    public OverScroller mScroller;
    public boolean mScrollingEnabled;
    public NotificationSection[] mSections;
    public final NotificationSectionsManager mSectionsManager;
    public ShadeController mShadeController;
    public boolean mShadeNeedsToClose = false;
    public NotificationStackScrollLayout$$ExternalSyntheticLambda0 mShadowUpdater = new NotificationStackScrollLayout$$ExternalSyntheticLambda0(this);
    public NotificationShelf mShelf;
    public final boolean mShouldDrawNotificationBackground;
    public boolean mShouldShowShelfOnly;
    public boolean mShouldUseRoundedRectClipping = false;
    public boolean mShouldUseSplitNotificationShade;
    public int mSidePaddings;
    public boolean mSkinnyNotifsInLandscape;
    public float mSlopMultiplier;
    public int mSpeedBumpIndex = -1;
    public boolean mSpeedBumpIndexDirty = true;
    public final int mSplitShadeMinContentHeight;
    public final StackScrollAlgorithm mStackScrollAlgorithm;
    public float mStackTranslation;
    public final StackStateAnimator mStateAnimator = new StackStateAnimator(this);
    public StatusBar mStatusBar;
    @VisibleForTesting
    public int mStatusBarHeight;
    public int mStatusBarState;
    public NotificationSwipeHelper mSwipeHelper;
    public ArrayList<View> mSwipedOutViews = new ArrayList<>();
    public int[] mTempInt2 = new int[2];
    public final ArrayList<Pair<ExpandableNotificationRow, Boolean>> mTmpList = new ArrayList<>();
    public final Rect mTmpRect = new Rect();
    public ArrayList<ExpandableView> mTmpSortedChildren = new ArrayList<>();
    public NotificationEntry mTopHeadsUpEntry;
    public int mTopPadding;
    public boolean mTopPaddingNeedsAnimation;
    public float mTopPaddingOverflow;
    public NotificationStackScrollLayoutController.TouchHandler mTouchHandler;
    public boolean mTouchIsClick;
    public int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public NotificationStackScrollLayout$$ExternalSyntheticLambda3 mViewPositionComparator = NotificationStackScrollLayout$$ExternalSyntheticLambda3.INSTANCE;
    public int mWaterfallTopInset;
    public boolean mWillExpand;

    public static class AnimationEvent {
        public static AnimationFilter[] FILTERS;
        public static int[] LENGTHS = {464, 464, 360, 360, 220, 220, 360, 448, 360, 360, 360, 400, 400, 400, 360, 360};
        public final int animationType;
        public final AnimationFilter filter;
        public boolean headsUpFromBottom;
        public final long length;
        public final ExpandableView mChangingView;
        public View viewAfterChangingView;

        public AnimationEvent(ExpandableView expandableView, int i) {
            this(expandableView, i, (long) LENGTHS[i]);
        }

        static {
            AnimationFilter animationFilter = new AnimationFilter();
            animationFilter.animateAlpha = true;
            animationFilter.animateHeight = true;
            animationFilter.animateTopInset = true;
            animationFilter.animateY = true;
            animationFilter.animateZ = true;
            animationFilter.hasDelays = true;
            AnimationFilter animationFilter2 = new AnimationFilter();
            animationFilter2.animateAlpha = true;
            animationFilter2.animateHeight = true;
            animationFilter2.animateTopInset = true;
            animationFilter2.animateY = true;
            animationFilter2.animateZ = true;
            animationFilter2.hasDelays = true;
            AnimationFilter animationFilter3 = new AnimationFilter();
            animationFilter3.animateHeight = true;
            animationFilter3.animateTopInset = true;
            animationFilter3.animateY = true;
            animationFilter3.animateZ = true;
            animationFilter3.hasDelays = true;
            AnimationFilter animationFilter4 = new AnimationFilter();
            animationFilter4.animateHeight = true;
            animationFilter4.animateTopInset = true;
            animationFilter4.animateY = true;
            animationFilter4.animateDimmed = true;
            animationFilter4.animateZ = true;
            AnimationFilter animationFilter5 = new AnimationFilter();
            animationFilter5.animateZ = true;
            AnimationFilter animationFilter6 = new AnimationFilter();
            animationFilter6.animateDimmed = true;
            AnimationFilter animationFilter7 = new AnimationFilter();
            animationFilter7.animateAlpha = true;
            animationFilter7.animateHeight = true;
            animationFilter7.animateTopInset = true;
            animationFilter7.animateY = true;
            animationFilter7.animateZ = true;
            AnimationFilter animationFilter8 = new AnimationFilter();
            animationFilter8.animateHeight = true;
            animationFilter8.animateTopInset = true;
            animationFilter8.animateY = true;
            animationFilter8.animateDimmed = true;
            animationFilter8.animateZ = true;
            animationFilter8.hasDelays = true;
            AnimationFilter animationFilter9 = new AnimationFilter();
            animationFilter9.animateHideSensitive = true;
            AnimationFilter animationFilter10 = new AnimationFilter();
            animationFilter10.animateHeight = true;
            animationFilter10.animateTopInset = true;
            animationFilter10.animateY = true;
            animationFilter10.animateZ = true;
            AnimationFilter animationFilter11 = new AnimationFilter();
            animationFilter11.animateAlpha = true;
            animationFilter11.animateHeight = true;
            animationFilter11.animateTopInset = true;
            animationFilter11.animateY = true;
            animationFilter11.animateZ = true;
            AnimationFilter animationFilter12 = new AnimationFilter();
            animationFilter12.animateHeight = true;
            animationFilter12.animateTopInset = true;
            animationFilter12.animateY = true;
            animationFilter12.animateZ = true;
            AnimationFilter animationFilter13 = new AnimationFilter();
            animationFilter13.animateHeight = true;
            animationFilter13.animateTopInset = true;
            animationFilter13.animateY = true;
            animationFilter13.animateZ = true;
            animationFilter13.hasDelays = true;
            AnimationFilter animationFilter14 = new AnimationFilter();
            animationFilter14.animateHeight = true;
            animationFilter14.animateTopInset = true;
            animationFilter14.animateY = true;
            animationFilter14.animateZ = true;
            animationFilter14.hasDelays = true;
            AnimationFilter animationFilter15 = new AnimationFilter();
            animationFilter15.animateHeight = true;
            animationFilter15.animateTopInset = true;
            animationFilter15.animateY = true;
            animationFilter15.animateZ = true;
            AnimationFilter animationFilter16 = new AnimationFilter();
            animationFilter16.animateAlpha = true;
            animationFilter16.animateDimmed = true;
            animationFilter16.animateHideSensitive = true;
            animationFilter16.animateHeight = true;
            animationFilter16.animateTopInset = true;
            animationFilter16.animateY = true;
            animationFilter16.animateZ = true;
            FILTERS = new AnimationFilter[]{animationFilter, animationFilter2, animationFilter3, animationFilter4, animationFilter5, animationFilter6, animationFilter7, animationFilter8, animationFilter9, animationFilter10, animationFilter11, animationFilter12, animationFilter13, animationFilter14, animationFilter15, animationFilter16};
        }

        public AnimationEvent(ExpandableView expandableView, int i, long j) {
            AnimationFilter animationFilter = FILTERS[i];
            AnimationUtils.currentAnimationTimeMillis();
            this.mChangingView = expandableView;
            this.animationType = i;
            this.length = j;
            this.filter = animationFilter;
        }
    }

    public interface ClearAllAnimationListener {
    }

    public interface ClearAllListener {
    }

    public interface FooterClearAllListener {
    }

    public interface OnEmptySpaceClickListener {
    }

    public interface OnOverscrollTopChangedListener {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationStackScrollLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        boolean z = false;
        Resources resources = getResources();
        FeatureFlags featureFlags = (FeatureFlags) Dependency.get(FeatureFlags.class);
        this.mDebugLines = featureFlags.isEnabled(Flags.NSSL_DEBUG_LINES);
        this.mDebugRemoveAnimation = featureFlags.isEnabled(Flags.NSSL_DEBUG_REMOVE_ANIMATION);
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) Dependency.get(NotificationSectionsManager.class);
        this.mSectionsManager = notificationSectionsManager;
        this.mScreenOffAnimationController = (ScreenOffAnimationController) Dependency.get(ScreenOffAnimationController.class);
        boolean shouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(getResources());
        if (shouldUseSplitNotificationShade != this.mShouldUseSplitNotificationShade) {
            this.mShouldUseSplitNotificationShade = shouldUseSplitNotificationShade;
            updateDismissBehavior();
            updateUseRoundedRectClipping();
        }
        Objects.requireNonNull(notificationSectionsManager);
        if (!notificationSectionsManager.initialized) {
            notificationSectionsManager.initialized = true;
            notificationSectionsManager.parent = this;
            notificationSectionsManager.reinflateViews();
            notificationSectionsManager.configurationController.addCallback(notificationSectionsManager.configurationListener);
            int[] notificationBuckets = notificationSectionsManager.sectionsFeatureManager.getNotificationBuckets();
            ArrayList arrayList = new ArrayList(notificationBuckets.length);
            int length = notificationBuckets.length;
            int i = 0;
            while (i < length) {
                int i2 = notificationBuckets[i];
                i++;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationSectionsManager.parent;
                if (notificationStackScrollLayout == null) {
                    notificationStackScrollLayout = null;
                }
                arrayList.add(new NotificationSection(notificationStackScrollLayout, i2));
            }
            Object[] array = arrayList.toArray(new NotificationSection[0]);
            Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            this.mSections = (NotificationSection[]) array;
            this.mAmbientState = (AmbientState) Dependency.get(AmbientState.class);
            this.mBgColor = com.android.settingslib.Utils.getColorAttr(this.mContext, 16844002).getDefaultColor();
            int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.notification_min_height);
            resources.getDimensionPixelSize(C1777R.dimen.notification_max_height);
            this.mSplitShadeMinContentHeight = resources.getDimensionPixelSize(C1777R.dimen.nssl_split_shade_min_content_height);
            ExpandHelper expandHelper = new ExpandHelper(getContext(), this.mExpandHelperCallback, dimensionPixelSize);
            this.mExpandHelper = expandHelper;
            expandHelper.mEventSource = this;
            C13637 r2 = this.mScrollAdapter;
            Objects.requireNonNull(expandHelper);
            expandHelper.mScrollAdapter = r2;
            this.mStackScrollAlgorithm = new StackScrollAlgorithm(context, this);
            boolean z2 = resources.getBoolean(C1777R.bool.config_drawNotificationBackground);
            this.mShouldDrawNotificationBackground = z2;
            setOutlineProvider(this.mOutlineProvider);
            setWillNotDraw(!((z2 || this.mDebugLines) ? true : z));
            this.mBackgroundPaint.setAntiAlias(true);
            if (this.mDebugLines) {
                Paint paint = new Paint();
                this.mDebugPaint = paint;
                paint.setColor(-65536);
                this.mDebugPaint.setStrokeWidth(2.0f);
                this.mDebugPaint.setStyle(Paint.Style.STROKE);
                this.mDebugPaint.setTextSize(25.0f);
            }
            this.mClearAllEnabled = resources.getBoolean(C1777R.bool.config_enableNotificationsClearAll);
            this.mGroupMembershipManager = (GroupMembershipManager) Dependency.get(GroupMembershipManager.class);
            this.mGroupExpansionManager = (GroupExpansionManager) Dependency.get(GroupExpansionManager.class);
            setImportantForAccessibility(1);
            return;
        }
        throw new IllegalStateException("NotificationSectionsManager already initialized".toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void customOverScrollBy(int r3, int r4, int r5, int r6) {
        /*
            r2 = this;
            int r4 = r4 + r3
            int r3 = -r6
            int r6 = r6 + r5
            r5 = 0
            r0 = 1
            if (r4 <= r6) goto L_0x000a
            r4 = r6
        L_0x0008:
            r3 = r0
            goto L_0x000f
        L_0x000a:
            if (r4 >= r3) goto L_0x000e
            r4 = r3
            goto L_0x0008
        L_0x000e:
            r3 = r5
        L_0x000f:
            android.widget.OverScroller r6 = r2.mScroller
            boolean r6 = r6.isFinished()
            if (r6 != 0) goto L_0x0069
            r2.setOwnScrollY(r4, r5)
            if (r3 == 0) goto L_0x004f
            int r3 = r2.getScrollRange()
            int r4 = r2.mOwnScrollY
            if (r4 > 0) goto L_0x0026
            r6 = r0
            goto L_0x0027
        L_0x0026:
            r6 = r5
        L_0x0027:
            if (r4 < r3) goto L_0x002b
            r1 = r0
            goto L_0x002c
        L_0x002b:
            r1 = r5
        L_0x002c:
            if (r6 != 0) goto L_0x0030
            if (r1 == 0) goto L_0x006c
        L_0x0030:
            if (r6 == 0) goto L_0x003b
            int r3 = -r4
            float r3 = (float) r3
            r2.setOwnScrollY(r5, r5)
            r2.mDontReportNextOverScroll = r0
            r4 = r0
            goto L_0x0042
        L_0x003b:
            int r4 = r4 - r3
            float r4 = (float) r4
            r2.setOwnScrollY(r3, r5)
            r3 = r4
            r4 = r5
        L_0x0042:
            r2.setOverScrollAmount(r3, r4, r5, r0)
            r3 = 0
            r2.setOverScrollAmount(r3, r4, r0, r0)
            android.widget.OverScroller r2 = r2.mScroller
            r2.forceFinished(r0)
            goto L_0x006c
        L_0x004f:
            float r3 = r2.getCurrentOverScrollAmount(r0)
            int r4 = r2.mOwnScrollY
            if (r4 >= 0) goto L_0x0061
            int r3 = -r4
            float r3 = (float) r3
            boolean r4 = r2.isRubberbanded(r0)
            r2.notifyOverscrollTopListener(r3, r4)
            goto L_0x006c
        L_0x0061:
            boolean r4 = r2.isRubberbanded(r0)
            r2.notifyOverscrollTopListener(r3, r4)
            goto L_0x006c
        L_0x0069:
            r2.setOwnScrollY(r4, r5)
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.customOverScrollBy(int, int, int, int):void");
    }

    public final void endDrag() {
        setIsBeingDragged(false);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (getCurrentOverScrollAmount(true) > 0.0f) {
            setOverScrollAmount(0.0f, true, true, true);
        }
        if (getCurrentOverScrollAmount(false) > 0.0f) {
            setOverScrollAmount(0.0f, false, true, true);
        }
    }

    public final void notifyHeightChangeListener(ExpandableView expandableView) {
        notifyHeightChangeListener(expandableView, false);
    }

    public final boolean scrollTo(View view) {
        ExpandableView expandableView = (ExpandableView) view;
        int positionInLinearLayout = getPositionInLinearLayout(view);
        int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
        int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
        int i = this.mOwnScrollY;
        if (i >= targetScrollForView && intrinsicHeight >= i) {
            return false;
        }
        this.mScroller.startScroll(this.mScrollX, i, 0, targetScrollForView - i);
        this.mDontReportNextOverScroll = true;
        animateScroll();
        return true;
    }

    public final void setOverScrollAmount(float f, boolean z, boolean z2, boolean z3) {
        setOverScrollAmount(f, z, z2, z3, isRubberbanded(z));
    }

    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void updateViewShadows() {
        float f;
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                this.mTmpSortedChildren.add(expandableView);
            }
        }
        Collections.sort(this.mTmpSortedChildren, this.mViewPositionComparator);
        ExpandableView expandableView2 = null;
        int i2 = 0;
        while (i2 < this.mTmpSortedChildren.size()) {
            ExpandableView expandableView3 = this.mTmpSortedChildren.get(i2);
            float translationZ = expandableView3.getTranslationZ();
            if (expandableView2 == null) {
                f = translationZ;
            } else {
                f = expandableView2.getTranslationZ();
            }
            float f2 = f - translationZ;
            if (f2 <= 0.0f || f2 >= 0.1f) {
                expandableView3.setFakeShadowIntensity(0.0f, 0.0f, 0, 0);
            } else {
                expandableView2.getExtraBottomPadding();
                expandableView3.setFakeShadowIntensity(f2 / 0.1f, expandableView2.getOutlineAlpha(), (int) (((expandableView2.getTranslationY() + ((float) expandableView2.mActualHeight)) - expandableView3.getTranslationY()) - ((float) 0)), (int) (expandableView2.getTranslation() + ((float) expandableView2.getOutlineTranslation())));
            }
            i2++;
            expandableView2 = expandableView3;
        }
        this.mTmpSortedChildren.clear();
    }

    public static boolean canChildBeCleared(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.areGutsExposed() || !expandableNotificationRow.mEntry.hasFinishedInitialization() || !expandableNotificationRow.mEntry.isClearable()) {
                return false;
            }
            if (!expandableNotificationRow.shouldShowPublic() || !expandableNotificationRow.mSensitiveHiddenInGeneral) {
                return true;
            }
            return false;
        }
        if (view instanceof PeopleHubView) {
            Objects.requireNonNull((PeopleHubView) view);
        }
        return false;
    }

    public static void clearTemporaryViewsInGroup(ViewGroup viewGroup) {
        while (viewGroup != null && viewGroup.getTransientViewCount() != 0) {
            View transientView = viewGroup.getTransientView(0);
            viewGroup.removeTransientView(transientView);
            if (transientView instanceof ExpandableView) {
                ExpandableView expandableView = (ExpandableView) transientView;
                Objects.requireNonNull(expandableView);
                expandableView.mTransientContainer = null;
            }
        }
    }

    public static boolean isPinnedHeadsUp(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        Objects.requireNonNull(expandableNotificationRow);
        if (!expandableNotificationRow.mIsHeadsUp || !expandableNotificationRow.mIsPinned) {
            return false;
        }
        return true;
    }

    public final void abortBackgroundAnimators() {
        for (NotificationSection notificationSection : this.mSections) {
            Objects.requireNonNull(notificationSection);
            ObjectAnimator objectAnimator = notificationSection.mBottomAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator objectAnimator2 = notificationSection.mTopAnimator;
            if (objectAnimator2 != null) {
                objectAnimator2.cancel();
            }
        }
    }

    public final void animateScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int i = this.mOwnScrollY;
            int currY = this.mScroller.getCurrY();
            if (i != currY) {
                int scrollRange = getScrollRange();
                if ((currY < 0 && i >= 0) || (currY > scrollRange && i <= scrollRange)) {
                    float currVelocity = this.mScroller.getCurrVelocity();
                    if (currVelocity >= ((float) this.mMinimumVelocity)) {
                        this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * ((float) this.mOverflingDistance);
                    }
                }
                if (this.mDontClampNextScroll) {
                    scrollRange = Math.max(scrollRange, i);
                }
                customOverScrollBy(currY - i, i, scrollRange, (int) this.mMaxOverScroll);
            }
            postOnAnimation(this.mReflingAndAnimateScroll);
            return;
        }
        this.mDontClampNextScroll = false;
        Runnable runnable = this.mFinishScrollingCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void cancelLongPress() {
        this.mSwipeHelper.cancelLongPress();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
        if (includeChildInClearAll(r8, r1) != false) goto L_0x0038;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x007e A[SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clearNotifications(int r20, boolean r21) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            int r2 = r19.getChildCount()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r2)
            r4 = 0
            r5 = r4
        L_0x000f:
            r6 = 1
            if (r5 >= r2) goto L_0x0081
            android.view.View r7 = r0.getChildAt(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = r0.mController
            r9 = 2
            boolean r8 = r8.hasNotifications(r9, r4)
            r8 = r8 ^ r6
            boolean r9 = r7 instanceof com.android.systemui.statusbar.notification.stack.SectionHeaderView
            if (r9 == 0) goto L_0x0025
            if (r8 == 0) goto L_0x0025
            goto L_0x0038
        L_0x0025:
            boolean r8 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r8 == 0) goto L_0x003a
            r8 = r7
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            boolean r9 = r0.isVisible(r8)
            if (r9 == 0) goto L_0x003a
            boolean r8 = includeChildInClearAll(r8, r1)
            if (r8 == 0) goto L_0x003a
        L_0x0038:
            r8 = r6
            goto L_0x003b
        L_0x003a:
            r8 = r4
        L_0x003b:
            if (r8 == 0) goto L_0x0040
            r3.add(r7)
        L_0x0040:
            boolean r8 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r8 == 0) goto L_0x007e
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
            java.util.ArrayList r8 = r7.getAttachedChildren()
            boolean r9 = r0.isVisible(r7)
            if (r9 == 0) goto L_0x0057
            if (r8 == 0) goto L_0x0057
            boolean r8 = r7.mChildrenExpanded
            if (r8 == 0) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r6 = r4
        L_0x0058:
            if (r6 == 0) goto L_0x007e
            java.util.ArrayList r6 = r7.getAttachedChildren()
            java.util.Iterator r6 = r6.iterator()
        L_0x0062:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x007e
            java.lang.Object r7 = r6.next()
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
            boolean r8 = r0.isVisible(r7)
            if (r8 == 0) goto L_0x0062
            boolean r8 = includeChildInClearAll(r7, r1)
            if (r8 == 0) goto L_0x0062
            r3.add(r7)
            goto L_0x0062
        L_0x007e:
            int r5 = r5 + 1
            goto L_0x000f
        L_0x0081:
            int r2 = r19.getChildCount()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>(r2)
            r7 = r4
        L_0x008b:
            if (r7 >= r2) goto L_0x00ca
            android.view.View r8 = r0.getChildAt(r7)
            boolean r9 = r8 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r9 != 0) goto L_0x0096
            goto L_0x00c7
        L_0x0096:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            boolean r9 = includeChildInClearAll(r8, r1)
            if (r9 == 0) goto L_0x00a1
            r5.add(r8)
        L_0x00a1:
            java.util.ArrayList r9 = r8.getAttachedChildren()
            boolean r10 = r0.isVisible(r8)
            if (r10 == 0) goto L_0x00c7
            if (r9 == 0) goto L_0x00c7
            java.util.Iterator r9 = r9.iterator()
        L_0x00b1:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00c7
            java.lang.Object r10 = r9.next()
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r10 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r10
            boolean r11 = includeChildInClearAll(r8, r1)
            if (r11 == 0) goto L_0x00b1
            r5.add(r10)
            goto L_0x00b1
        L_0x00c7:
            int r7 = r7 + 1
            goto L_0x008b
        L_0x00ca:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$ClearAllListener r2 = r0.mClearAllListener
            if (r2 == 0) goto L_0x00d7
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0 r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0) r2
            java.lang.Object r2 = r2.f$0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController) r2
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.$r8$lambda$a_N8g0656__oN00XIQFjVt0r6vI(r2, r1)
        L_0x00d7:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2 r2 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2
            r2.<init>(r0, r5, r1)
            boolean r1 = r3.isEmpty()
            if (r1 == 0) goto L_0x00e6
            r2.run()
            return
        L_0x00e6:
            r0.setClearAllInProgress(r6)
            r1 = r21
            r0.mShadeNeedsToClose = r1
            r1 = 60
            int r5 = r3.size()
            int r5 = r5 - r6
            r7 = r4
        L_0x00f5:
            if (r5 < 0) goto L_0x0126
            java.lang.Object r8 = r3.get(r5)
            r10 = r8
            android.view.View r10 = (android.view.View) r10
            r8 = 0
            if (r5 != 0) goto L_0x0103
            r12 = r2
            goto L_0x0104
        L_0x0103:
            r12 = r8
        L_0x0104:
            r16 = 200(0xc8, double:9.9E-322)
            boolean r8 = r10 instanceof com.android.systemui.statusbar.notification.stack.SectionHeaderView
            if (r8 == 0) goto L_0x0110
            com.android.systemui.statusbar.notification.row.StackScrollerDecorView r10 = (com.android.systemui.statusbar.notification.row.StackScrollerDecorView) r10
            r10.setContentVisible(r4, r6, r12)
            goto L_0x011a
        L_0x0110:
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r9 = r0.mSwipeHelper
            r11 = 0
            long r13 = (long) r7
            r15 = 1
            r18 = 1
            r9.dismissChild(r10, r11, r12, r13, r15, r16, r18)
        L_0x011a:
            r8 = 30
            int r1 = r1 + -5
            int r1 = java.lang.Math.max(r8, r1)
            int r7 = r7 + r1
            int r5 = r5 + -1
            goto L_0x00f5
        L_0x0126:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.clearNotifications(int, boolean):void");
    }

    public final void dispatchDraw(Canvas canvas) {
        if (this.mShouldUseRoundedRectClipping && !this.mLaunchingNotification) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        super.dispatchDraw(canvas);
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        Path path;
        if (!this.mShouldUseRoundedRectClipping || !this.mLaunchingNotification) {
            return super.drawChild(canvas, view, j);
        }
        canvas.save();
        ExpandableView expandableView = (ExpandableView) view;
        if (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) {
            path = null;
        } else {
            path = this.mRoundedClipPath;
        }
        if (path != null) {
            canvas.clipPath(path);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return drawChild;
    }

    public final void drawDebugInfo(Canvas canvas, int i, int i2, String str) {
        this.mDebugPaint.setColor(i2);
        float f = (float) i;
        canvas.drawLine(0.0f, f, (float) getWidth(), f, this.mDebugPaint);
        while (this.mDebugTextUsedYPositions.contains(Integer.valueOf(i))) {
            i = (int) (this.mDebugPaint.getTextSize() + ((float) i));
        }
        this.mDebugTextUsedYPositions.add(Integer.valueOf(i));
        canvas.drawText(str, 0.0f, (float) i, this.mDebugPaint);
    }

    public final void generateAddAnimation(ExpandableView expandableView, boolean z) {
        boolean z2;
        if (this.mIsExpanded && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mChildrenToAddAnimated.add(expandableView);
            if (z) {
                this.mFromMoreCardAdditions.add(expandableView);
            }
            this.mNeedsAnimation = true;
        }
        if (expandableView instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            Objects.requireNonNull(expandableNotificationRow);
            z2 = expandableNotificationRow.mIsHeadsUp;
        } else {
            z2 = false;
        }
        if (z2 && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mAddedHeadsUpChildren.add(expandableView);
            this.mChildrenToAddAnimated.remove(expandableView);
        }
    }

    public final void generateHeadsUpAnimation(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        boolean z2;
        if (!this.mAnimationsEnabled || (!z && !this.mHeadsUpGoingAwayAnimationsAllowed)) {
            z2 = false;
        } else {
            z2 = true;
        }
        boolean z3 = SPEW;
        if (z3) {
            StringBuilder sb = new StringBuilder();
            sb.append("generateHeadsUpAnimation: willAdd=");
            sb.append(z2);
            sb.append(" isHeadsUp=");
            sb.append(z);
            sb.append(" row=");
            Objects.requireNonNull(expandableNotificationRow);
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            Objects.requireNonNull(notificationEntry);
            sb.append(notificationEntry.mKey);
            Log.v("StackScroller", sb.toString());
        }
        if (!z2) {
            return;
        }
        if (z || !this.mHeadsUpChangeAnimations.remove(new Pair(expandableNotificationRow, Boolean.TRUE))) {
            this.mHeadsUpChangeAnimations.add(new Pair(expandableNotificationRow, Boolean.valueOf(z)));
            this.mNeedsAnimation = true;
            if (!this.mIsExpanded && !this.mWillExpand && !z) {
                expandableNotificationRow.setHeadsUpAnimatingAway(true);
            }
            requestChildrenUpdate();
            return;
        }
        if (z3) {
            Log.v("StackScroller", "generateHeadsUpAnimation: previous hun appear animation cancelled");
        }
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry2);
        logHunAnimationSkipped(notificationEntry2.mKey, "previous hun appear animation cancelled");
    }

    public final boolean generateRemoveAnimation(ExpandableView expandableView) {
        boolean z;
        boolean z2;
        String str = "";
        if (this.mDebugRemoveAnimation) {
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                Objects.requireNonNull(expandableNotificationRow);
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                Objects.requireNonNull(notificationEntry);
                str = notificationEntry.mKey;
            }
            DialogFragment$$ExternalSyntheticOutline0.m17m("generateRemoveAnimation ", str, "StackScroller");
        }
        Iterator<Pair<ExpandableNotificationRow, Boolean>> it = this.mHeadsUpChangeAnimations.iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            Pair next = it.next();
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) next.first;
            boolean booleanValue = ((Boolean) next.second).booleanValue();
            if (expandableView == expandableNotificationRow2) {
                this.mTmpList.add(next);
                z3 |= booleanValue;
            }
        }
        if (z3) {
            this.mHeadsUpChangeAnimations.removeAll(this.mTmpList);
            ((ExpandableNotificationRow) expandableView).setHeadsUpAnimatingAway(false);
        }
        this.mTmpList.clear();
        if (!z3 || !this.mAddedHeadsUpChildren.contains(expandableView)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            if (this.mDebugRemoveAnimation) {
                DialogFragment$$ExternalSyntheticOutline0.m17m("removedBecauseOfHeadsUp ", str, "StackScroller");
            }
            this.mAddedHeadsUpChildren.remove(expandableView);
            return false;
        }
        Boolean bool = (Boolean) expandableView.getTag(C1777R.C1779id.is_clicked_heads_up_tag);
        if (bool == null || !bool.booleanValue()) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            this.mClearTransientViewsWhenFinished.add(expandableView);
            return true;
        }
        if (this.mDebugRemoveAnimation) {
            StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("generateRemove ", str, "\nmIsExpanded ");
            m.append(this.mIsExpanded);
            m.append("\nmAnimationsEnabled ");
            m.append(this.mAnimationsEnabled);
            m.append("\n!invisible group ");
            KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(m, !isChildInInvisibleGroup(expandableView), "StackScroller");
        }
        if (this.mIsExpanded && this.mAnimationsEnabled && !isChildInInvisibleGroup(expandableView)) {
            if (!this.mChildrenToAddAnimated.contains(expandableView)) {
                if (this.mDebugRemoveAnimation) {
                    DialogFragment$$ExternalSyntheticOutline0.m17m("needsAnimation = true ", str, "StackScroller");
                }
                this.mChildrenToRemoveAnimated.add(expandableView);
                this.mNeedsAnimation = true;
                return true;
            }
            this.mChildrenToAddAnimated.remove(expandableView);
            this.mFromMoreCardAdditions.remove(expandableView);
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0024, code lost:
        if (r1.mDozing == false) goto L_0x003a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float getAppearEndPosition() {
        /*
            r4 = this;
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r4.mController
            int r0 = r0.getVisibleNotificationCount()
            com.android.systemui.statusbar.EmptyShadeView r1 = r4.mEmptyShadeView
            int r1 = r1.getVisibility()
            r2 = 8
            r3 = 0
            if (r1 != r2) goto L_0x0063
            if (r0 <= 0) goto L_0x0063
            boolean r1 = r4.isHeadsUpTransition()
            if (r1 != 0) goto L_0x003a
            boolean r1 = r4.mInHeadsUpPinnedMode
            if (r1 == 0) goto L_0x0027
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r4.mAmbientState
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mDozing
            if (r1 != 0) goto L_0x0027
            goto L_0x003a
        L_0x0027:
            com.android.systemui.statusbar.NotificationShelf r0 = r4.mShelf
            int r0 = r0.getVisibility()
            if (r0 == r2) goto L_0x0069
            com.android.systemui.statusbar.NotificationShelf r0 = r4.mShelf
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.getHeight()
            int r3 = r3 + r0
            goto L_0x0069
        L_0x003a:
            com.android.systemui.statusbar.NotificationShelf r1 = r4.mShelf
            int r1 = r1.getVisibility()
            if (r1 == r2) goto L_0x0052
            r1 = 1
            if (r0 <= r1) goto L_0x0052
            com.android.systemui.statusbar.NotificationShelf r0 = r4.mShelf
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.getHeight()
            int r1 = r4.mPaddingBetweenElements
            int r0 = r0 + r1
            int r3 = r3 + r0
        L_0x0052:
            int r0 = r4.getTopHeadsUpPinnedHeight()
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r4.mAmbientState
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = r1.getTrackedHeadsUpRow()
            int r1 = r4.getPositionInLinearLayout(r1)
            int r1 = r1 + r0
            int r3 = r3 + r1
            goto L_0x0069
        L_0x0063:
            com.android.systemui.statusbar.EmptyShadeView r0 = r4.mEmptyShadeView
            int r3 = r0.getHeight()
        L_0x0069:
            boolean r0 = r4.onKeyguard()
            if (r0 == 0) goto L_0x0072
            int r4 = r4.mTopPadding
            goto L_0x0074
        L_0x0072:
            int r4 = r4.mIntrinsicPadding
        L_0x0074:
            int r3 = r3 + r4
            float r4 = (float) r3
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.getAppearEndPosition():float");
    }

    public final ExpandableView getChildAtRawPosition(float f, float f2) {
        getLocationOnScreen(this.mTempInt2);
        int[] iArr = this.mTempInt2;
        return getChildAtPosition(f - ((float) iArr[0]), f2 - ((float) iArr[1]), true, true);
    }

    public final ArrayList getChildrenWithBackground() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (!(expandableView.getVisibility() == 8 || (expandableView instanceof StackScrollerDecorView) || expandableView == this.mShelf)) {
                arrayList.add(expandableView);
            }
        }
        return arrayList;
    }

    public final float getCurrentOverScrollAmount(boolean z) {
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (z) {
            return ambientState.mOverScrollTopAmount;
        }
        return ambientState.mOverScrollBottomAmount;
    }

    public final int getEmptyBottomMargin() {
        int i;
        if (this.mShouldUseSplitNotificationShade) {
            i = Math.max(this.mSplitShadeMinContentHeight, this.mContentHeight);
        } else {
            i = this.mContentHeight;
        }
        return Math.max(this.mMaxLayoutHeight - i, 0);
    }

    public final NotificationSection getFirstVisibleSection() {
        for (NotificationSection notificationSection : this.mSections) {
            Objects.requireNonNull(notificationSection);
            if (notificationSection.mFirstVisibleChild != null) {
                return notificationSection;
            }
        }
        return null;
    }

    public final NotificationSection getLastVisibleSection() {
        for (int length = this.mSections.length - 1; length >= 0; length--) {
            NotificationSection notificationSection = this.mSections[length];
            Objects.requireNonNull(notificationSection);
            if (notificationSection.mLastVisibleChild != null) {
                return notificationSection;
            }
        }
        return null;
    }

    public final int getMinExpansionHeight() {
        NotificationShelf notificationShelf = this.mShelf;
        Objects.requireNonNull(notificationShelf);
        int height = notificationShelf.getHeight();
        NotificationShelf notificationShelf2 = this.mShelf;
        Objects.requireNonNull(notificationShelf2);
        return (height - Math.max(0, ((notificationShelf2.getHeight() - this.mStatusBarHeight) + this.mWaterfallTopInset) / 2)) + this.mWaterfallTopInset;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: android.view.View} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: android.view.View} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getPositionInLinearLayout(android.view.View r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0018
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r0 = r10.mGroupMembershipManager
            r3 = r11
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.mEntry
            boolean r0 = r0.isChildInGroup(r3)
            if (r0 == 0) goto L_0x0018
            r0 = r1
            goto L_0x0019
        L_0x0018:
            r0 = r2
        L_0x0019:
            r3 = 0
            if (r0 == 0) goto L_0x0027
            r3 = r11
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = r3.mNotificationParent
            r0 = r3
            r3 = r11
            goto L_0x0028
        L_0x0027:
            r0 = r3
        L_0x0028:
            r4 = r2
            r5 = r4
        L_0x002a:
            int r6 = r10.getChildCount()
            if (r4 >= r6) goto L_0x009c
            android.view.View r6 = r10.getChildAt(r4)
            com.android.systemui.statusbar.notification.row.ExpandableView r6 = (com.android.systemui.statusbar.notification.row.ExpandableView) r6
            int r7 = r6.getVisibility()
            r8 = 8
            if (r7 == r8) goto L_0x0040
            r7 = r1
            goto L_0x0041
        L_0x0040:
            r7 = r2
        L_0x0041:
            if (r7 == 0) goto L_0x004c
            boolean r9 = r6 instanceof com.android.systemui.statusbar.NotificationShelf
            if (r9 != 0) goto L_0x004c
            if (r5 == 0) goto L_0x004c
            int r9 = r10.mPaddingBetweenElements
            int r5 = r5 + r9
        L_0x004c:
            if (r6 != r11) goto L_0x0091
            if (r3 == 0) goto L_0x0090
            boolean r10 = r3.mIsSummaryWithChildren
            if (r10 == 0) goto L_0x008f
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r10 = r3.mChildrenContainer
            java.util.Objects.requireNonNull(r10)
            int r11 = r10.mNotificationHeaderMargin
            int r3 = r10.mCurrentHeaderTranslation
            int r11 = r11 + r3
            int r3 = r10.mNotificationTopPadding
            int r11 = r11 + r3
            r3 = r2
        L_0x0062:
            java.util.ArrayList r4 = r10.mAttachedChildren
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x008f
            java.util.ArrayList r4 = r10.mAttachedChildren
            java.lang.Object r4 = r4.get(r3)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r4
            int r6 = r4.getVisibility()
            if (r6 == r8) goto L_0x007a
            r6 = r1
            goto L_0x007b
        L_0x007a:
            r6 = r2
        L_0x007b:
            if (r6 == 0) goto L_0x0080
            int r7 = r10.mDividerHeight
            int r11 = r11 + r7
        L_0x0080:
            if (r4 != r0) goto L_0x0084
            r2 = r11
            goto L_0x008f
        L_0x0084:
            if (r6 == 0) goto L_0x008c
            int r4 = r4.getIntrinsicHeight()
            int r4 = r4 + r11
            r11 = r4
        L_0x008c:
            int r3 = r3 + 1
            goto L_0x0062
        L_0x008f:
            int r5 = r5 + r2
        L_0x0090:
            return r5
        L_0x0091:
            if (r7 == 0) goto L_0x0099
            int r6 = r6.getIntrinsicHeight()
            int r6 = r6 + r5
            r5 = r6
        L_0x0099:
            int r4 = r4 + 1
            goto L_0x002a
        L_0x009c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.getPositionInLinearLayout(android.view.View):int");
    }

    public final int getScrollRange() {
        int i;
        int i2 = this.mContentHeight;
        if (!this.mIsExpanded && this.mInHeadsUpPinnedMode) {
            i2 = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int max = Math.max(0, i2 - this.mMaxLayoutHeight);
        int max2 = Math.max(0, this.mBottomInset - (getRootView().getHeight() - getHeight()));
        int min = Math.min(max2, Math.max(0, i2 - (getHeight() - max2))) + max;
        if (min <= 0) {
            return min;
        }
        if (this.mShouldUseSplitNotificationShade) {
            i = this.mSidePaddings;
        } else {
            i = this.mTopPadding - this.mQsScrollBoundaryPosition;
        }
        return Math.max(i, min);
    }

    public final int getSpeedBumpIndex() {
        if (this.mSpeedBumpIndexDirty) {
            this.mSpeedBumpIndexDirty = false;
            int childCount = getChildCount();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() != 8 && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    i2++;
                    boolean z = true;
                    if (this.mHighPriorityBeforeSpeedBump) {
                        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                        Objects.requireNonNull(notificationEntry);
                        if (notificationEntry.mBucket >= 6) {
                            z = false;
                        }
                    } else {
                        z = true ^ expandableNotificationRow.mEntry.isAmbient();
                    }
                    if (z) {
                        i = i2;
                    }
                }
            }
            this.mSpeedBumpIndex = i;
        }
        return this.mSpeedBumpIndex;
    }

    public final int getTopHeadsUpPinnedHeight() {
        NotificationEntry groupSummary;
        NotificationEntry notificationEntry = this.mTopHeadsUpEntry;
        if (notificationEntry == null) {
            return 0;
        }
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow.isChildInGroup() && (groupSummary = this.mGroupMembershipManager.getGroupSummary(expandableNotificationRow.mEntry)) != null) {
            expandableNotificationRow = groupSummary.row;
        }
        Objects.requireNonNull(expandableNotificationRow);
        return expandableNotificationRow.getPinnedHeadsUpHeight(true);
    }

    public final boolean hasOverlappingRendering() {
        if (this.mForceNoOverlappingRendering || !super.hasOverlappingRendering()) {
            return false;
        }
        return true;
    }

    public final void inflateEmptyShadeView() {
        int i;
        EmptyShadeView emptyShadeView = (EmptyShadeView) LayoutInflater.from(this.mContext).inflate(C1777R.layout.status_bar_no_notifications, this, false);
        Objects.requireNonNull(emptyShadeView);
        emptyShadeView.mText = C1777R.string.empty_shade_text;
        emptyShadeView.mEmptyText.setText(C1777R.string.empty_shade_text);
        emptyShadeView.setOnClickListener(new GameMenuActivity$$ExternalSyntheticLambda1(this, 1));
        EmptyShadeView emptyShadeView2 = this.mEmptyShadeView;
        if (emptyShadeView2 != null) {
            i = indexOfChild(emptyShadeView2);
            removeView(this.mEmptyShadeView);
        } else {
            i = -1;
        }
        this.mEmptyShadeView = emptyShadeView;
        addView(emptyShadeView, i);
    }

    @VisibleForTesting
    public void inflateFooterView() {
        int i;
        FooterView footerView = (FooterView) LayoutInflater.from(this.mContext).inflate(C1777R.layout.status_bar_notification_footer, this, false);
        GameMenuActivity$$ExternalSyntheticLambda2 gameMenuActivity$$ExternalSyntheticLambda2 = new GameMenuActivity$$ExternalSyntheticLambda2(this, footerView, 1);
        Objects.requireNonNull(footerView);
        footerView.mClearAllButton.setOnClickListener(gameMenuActivity$$ExternalSyntheticLambda2);
        FooterView footerView2 = this.mFooterView;
        if (footerView2 != null) {
            i = indexOfChild(footerView2);
            removeView(this.mFooterView);
        } else {
            i = -1;
        }
        this.mFooterView = footerView;
        addView(footerView, i);
        View.OnClickListener onClickListener = this.mManageButtonClickListener;
        if (onClickListener != null) {
            FooterView footerView3 = this.mFooterView;
            Objects.requireNonNull(footerView3);
            footerView3.mManageButton.setOnClickListener(onClickListener);
        }
    }

    public final void initView(Context context, NotificationSwipeHelper notificationSwipeHelper) {
        this.mScroller = new OverScroller(getContext());
        this.mSwipeHelper = notificationSwipeHelper;
        setDescendantFocusability(262144);
        setClipChildren(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        Resources resources = context.getResources();
        resources.getDimensionPixelSize(C1777R.dimen.notification_min_height);
        this.mGapHeight = resources.getDimensionPixelSize(C1777R.dimen.notification_section_divider_height);
        this.mStackScrollAlgorithm.initView(context);
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mZDistanceBetweenElements = Math.max(1, context.getResources().getDimensionPixelSize(C1777R.dimen.z_distance_between_notifications));
        this.mPaddingBetweenElements = Math.max(1, resources.getDimensionPixelSize(C1777R.dimen.notification_divider_height));
        this.mMinTopOverScrollToEscape = (float) resources.getDimensionPixelSize(C1777R.dimen.min_top_overscroll_to_qs);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
        this.mBottomPadding = resources.getDimensionPixelSize(C1777R.dimen.notification_panel_padding_bottom);
        this.mMinimumPaddings = resources.getDimensionPixelSize(C1777R.dimen.notification_side_paddings);
        this.mQsTilePadding = resources.getDimensionPixelOffset(C1777R.dimen.qs_tile_margin_horizontal);
        this.mSkinnyNotifsInLandscape = resources.getBoolean(C1777R.bool.config_skinnyNotifsInLandscape);
        this.mSidePaddings = this.mMinimumPaddings;
        this.mMinInteractionHeight = resources.getDimensionPixelSize(C1777R.dimen.notification_min_interaction_height);
        this.mCornerRadius = resources.getDimensionPixelSize(C1777R.dimen.notification_corner_radius);
        this.mHeadsUpInset = resources.getDimensionPixelSize(C1777R.dimen.heads_up_status_bar_padding) + this.mStatusBarHeight;
        this.mQsScrollBoundaryPosition = SystemBarUtils.getQuickQsOffsetHeight(this.mContext);
    }

    public final boolean isChildInInvisibleGroup(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        GroupMembershipManager groupMembershipManager = this.mGroupMembershipManager;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry groupSummary = groupMembershipManager.getGroupSummary(expandableNotificationRow.mEntry);
        if (groupSummary == null || groupSummary.row == expandableNotificationRow || expandableNotificationRow.getVisibility() != 4) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public boolean isDimmed() {
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (!ambientState.mDimmed || (ambientState.isPulseExpanding() && ambientState.mDozeAmount == 1.0f)) {
            return false;
        }
        return true;
    }

    public final boolean isHeadsUpTransition() {
        if (this.mAmbientState.getTrackedHeadsUpRow() != null) {
            return true;
        }
        return false;
    }

    public final boolean isRubberbanded(boolean z) {
        if (!z || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown) {
            return true;
        }
        return false;
    }

    public final boolean isVisible(ExpandableNotificationRow expandableNotificationRow) {
        boolean clipBounds = expandableNotificationRow.getClipBounds(this.mTmpRect);
        if (expandableNotificationRow.getVisibility() != 0 || (clipBounds && this.mTmpRect.height() <= 0)) {
            return false;
        }
        return true;
    }

    public final void logHunAnimationSkipped(String str, String str2) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null) {
            LogBuffer logBuffer = notificationStackScrollLogger.buffer;
            LogLevel logLevel = LogLevel.INFO;
            NotificationStackScrollLogger$hunAnimationSkipped$2 notificationStackScrollLogger$hunAnimationSkipped$2 = NotificationStackScrollLogger$hunAnimationSkipped$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$hunAnimationSkipped$2);
                obtain.str1 = str;
                obtain.str2 = str2;
                logBuffer.push(obtain);
            }
        }
    }

    public final void notifyAppearChangedListeners() {
        float f;
        float f2;
        if (!this.mKeyguardBypassEnabled || !onKeyguard()) {
            float f3 = this.mExpandedHeight;
            float appearEndPosition = getAppearEndPosition();
            float appearStartPosition = getAppearStartPosition();
            f2 = MathUtils.saturate((f3 - appearStartPosition) / (appearEndPosition - appearStartPosition));
            f = this.mExpandedHeight;
        } else {
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            float f4 = ambientState.mPulseHeight;
            f = 0.0f;
            if (f4 == 100000.0f) {
                f4 = 0.0f;
            }
            f2 = MathUtils.smoothStep(0.0f, (float) this.mIntrinsicPadding, f4);
            AmbientState ambientState2 = this.mAmbientState;
            Objects.requireNonNull(ambientState2);
            float f5 = ambientState2.mPulseHeight;
            if (f5 != 100000.0f) {
                f = f5;
            }
        }
        if (f2 != this.mLastSentAppear || f != this.mLastSentExpandedHeight) {
            this.mLastSentAppear = f2;
            this.mLastSentExpandedHeight = f;
            for (int i = 0; i < this.mExpandedHeightListeners.size(); i++) {
                this.mExpandedHeightListeners.get(i).accept(Float.valueOf(f), Float.valueOf(f2));
            }
        }
    }

    public final void notifyHeightChangeListener(ExpandableView expandableView, boolean z) {
        ExpandableView.OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onHeightChanged(expandableView, z);
        }
    }

    public final void notifyOverscrollTopListener(float f, boolean z) {
        boolean z2;
        boolean z3;
        ExpandHelper expandHelper = this.mExpandHelper;
        boolean z4 = true;
        if (f > 1.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        Objects.requireNonNull(expandHelper);
        expandHelper.mOnlyMovements = z2;
        if (this.mDontReportNextOverScroll) {
            this.mDontReportNextOverScroll = false;
            return;
        }
        OnOverscrollTopChangedListener onOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (onOverscrollTopChangedListener != null) {
            NotificationPanelViewController.OnOverscrollTopChangedListener onOverscrollTopChangedListener2 = (NotificationPanelViewController.OnOverscrollTopChangedListener) onOverscrollTopChangedListener;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (!notificationPanelViewController.mShouldUseSplitNotificationShade) {
                ValueAnimator valueAnimator = notificationPanelViewController.mQsExpansionAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (!NotificationPanelViewController.this.isQsExpansionEnabled()) {
                    f = 0.0f;
                }
                if (f < 1.0f) {
                    f = 0.0f;
                }
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i == 0 || !z) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                Objects.requireNonNull(notificationPanelViewController2);
                notificationPanelViewController2.mStackScrollerOverscrolling = z3;
                C0961QS qs = notificationPanelViewController2.mQs;
                if (qs != null) {
                    qs.setOverscrolling(z3);
                }
                NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                if (i == 0) {
                    z4 = false;
                }
                notificationPanelViewController3.mQsExpansionFromOverscroll = z4;
                notificationPanelViewController3.mLastOverscroll = f;
                notificationPanelViewController3.updateQsState();
                NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                notificationPanelViewController4.setQsExpansion(((float) notificationPanelViewController4.mQsMinExpansionHeight) + f);
            }
        }
    }

    public final void onChildHeightChanged(ExpandableView expandableView, boolean z) {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableView expandableView2;
        boolean z2 = this.mAnimateStackYForContentHeightChange;
        if (z) {
            this.mAnimateStackYForContentHeightChange = true;
        }
        updateContentHeight();
        boolean z3 = expandableView instanceof ExpandableNotificationRow;
        ExpandableView expandableView3 = null;
        if (z3 && !onKeyguard()) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView;
            Objects.requireNonNull(expandableNotificationRow2);
            if (expandableNotificationRow2.mUserLocked && expandableNotificationRow2 != getFirstChildNotGone() && !expandableNotificationRow2.mIsSummaryWithChildren) {
                float translationY = expandableNotificationRow2.getTranslationY() + ((float) expandableNotificationRow2.mActualHeight);
                if (expandableNotificationRow2.isChildInGroup()) {
                    translationY += expandableNotificationRow2.mNotificationParent.getTranslationY();
                }
                int i = this.mMaxLayoutHeight + ((int) this.mStackTranslation);
                NotificationSection lastVisibleSection = getLastVisibleSection();
                if (lastVisibleSection == null) {
                    expandableView2 = null;
                } else {
                    expandableView2 = lastVisibleSection.mLastVisibleChild;
                }
                if (!(expandableNotificationRow2 == expandableView2 || this.mShelf.getVisibility() == 8)) {
                    NotificationShelf notificationShelf = this.mShelf;
                    Objects.requireNonNull(notificationShelf);
                    i -= notificationShelf.getHeight() + this.mPaddingBetweenElements;
                }
                float f = (float) i;
                if (translationY > f) {
                    setOwnScrollY((int) ((((float) this.mOwnScrollY) + translationY) - f), false);
                    this.mDisallowScrollingInThisMotion = true;
                }
            }
        }
        clampScrollPosition();
        notifyHeightChangeListener(expandableView, z);
        if (z3) {
            expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        } else {
            expandableNotificationRow = null;
        }
        NotificationSection firstVisibleSection = getFirstVisibleSection();
        if (firstVisibleSection != null) {
            expandableView3 = firstVisibleSection.mFirstVisibleChild;
        }
        if (expandableNotificationRow != null && (expandableNotificationRow == expandableView3 || expandableNotificationRow.mNotificationParent == expandableView3)) {
            updateAlgorithmLayoutMinHeight();
        }
        if (z && this.mAnimationsEnabled && (this.mIsExpanded || (expandableNotificationRow != null && expandableNotificationRow.mIsPinned))) {
            this.mNeedViewResizeAnimation = true;
            this.mNeedsAnimation = true;
        }
        requestChildrenUpdate();
        this.mAnimateStackYForContentHeightChange = z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x002b, code lost:
        if (r1.mDozing != false) goto L_0x002d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDraw(android.graphics.Canvas r23) {
        /*
            r22 = this;
            r0 = r22
            r9 = r23
            boolean r1 = r0.mShouldDrawNotificationBackground
            r11 = 0
            if (r1 == 0) goto L_0x016c
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r1 = r0.mSections
            r1 = r1[r11]
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r1 = r1.mCurrentBounds
            int r1 = r1.top
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r2 = r0.mSections
            int r3 = r2.length
            r12 = 1
            int r3 = r3 - r12
            r2 = r2[r3]
            java.util.Objects.requireNonNull(r2)
            android.graphics.Rect r2 = r2.mCurrentBounds
            int r2 = r2.bottom
            if (r1 < r2) goto L_0x002d
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r0.mAmbientState
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mDozing
            if (r1 == 0) goto L_0x016c
        L_0x002d:
            int r1 = r0.mSidePaddings
            int r2 = r22.getWidth()
            int r3 = r0.mSidePaddings
            int r2 = r2 - r3
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r3 = r0.mSections
            r3 = r3[r11]
            java.util.Objects.requireNonNull(r3)
            android.graphics.Rect r3 = r3.mCurrentBounds
            int r3 = r3.top
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r4 = r0.mSections
            int r5 = r4.length
            int r5 = r5 - r12
            r4 = r4[r5]
            java.util.Objects.requireNonNull(r4)
            android.graphics.Rect r4 = r4.mCurrentBounds
            int r4 = r4.bottom
            int r5 = r22.getWidth()
            int r5 = r5 / 2
            int r6 = r0.mTopPadding
            float r7 = r0.mInterpolatedHideAmount
            r8 = 1065353216(0x3f800000, float:1.0)
            float r7 = r8 - r7
            android.view.animation.PathInterpolator r13 = r0.mHideXInterpolator
            float r14 = r0.mLinearHideAmount
            float r8 = r8 - r14
            float r14 = r0.mBackgroundXFactor
            float r8 = r8 * r14
            float r8 = r13.getInterpolation(r8)
            float r1 = android.util.MathUtils.lerp(r5, r1, r8)
            int r13 = (int) r1
            float r1 = android.util.MathUtils.lerp(r5, r2, r8)
            int r14 = (int) r1
            float r1 = android.util.MathUtils.lerp(r6, r3, r7)
            int r1 = (int) r1
            float r2 = android.util.MathUtils.lerp(r6, r4, r7)
            int r2 = (int) r2
            android.graphics.Rect r4 = r0.mBackgroundAnimationRect
            r4.set(r13, r1, r14, r2)
            int r15 = r1 - r3
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r2 = r0.mSections
            int r3 = r2.length
            r4 = r11
        L_0x0087:
            if (r4 >= r3) goto L_0x00a0
            r5 = r2[r4]
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.row.ExpandableView r6 = r5.mFirstVisibleChild
            if (r6 == 0) goto L_0x0098
            int r5 = r5.mBucket
            if (r5 == r12) goto L_0x0098
            r5 = r12
            goto L_0x0099
        L_0x0098:
            r5 = r11
        L_0x0099:
            if (r5 == 0) goto L_0x009d
            r2 = r12
            goto L_0x00a1
        L_0x009d:
            int r4 = r4 + 1
            goto L_0x0087
        L_0x00a0:
            r2 = r11
        L_0x00a1:
            boolean r3 = r0.mKeyguardBypassEnabled
            if (r3 == 0) goto L_0x00b2
            boolean r3 = r22.onKeyguard()
            if (r3 == 0) goto L_0x00b2
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r0.mAmbientState
            boolean r2 = r2.isPulseExpanding()
            goto L_0x00c1
        L_0x00b2:
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r0.mAmbientState
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mDozing
            if (r3 == 0) goto L_0x00c0
            if (r2 == 0) goto L_0x00be
            goto L_0x00c0
        L_0x00be:
            r2 = r11
            goto L_0x00c1
        L_0x00c0:
            r2 = r12
        L_0x00c1:
            if (r2 == 0) goto L_0x0167
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r2 = r0.mSections
            r2 = r2[r11]
            java.util.Objects.requireNonNull(r2)
            android.graphics.Rect r2 = r2.mCurrentBounds
            int r2 = r2.bottom
            int r2 = r2 + r15
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r8 = r0.mSections
            int r7 = r8.length
            r6 = r11
            r5 = r12
            r3 = r13
            r4 = r14
        L_0x00d6:
            if (r6 >= r7) goto L_0x0155
            r11 = r8[r6]
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.row.ExpandableView r10 = r11.mFirstVisibleChild
            if (r10 == 0) goto L_0x00e7
            int r10 = r11.mBucket
            if (r10 == r12) goto L_0x00e7
            r10 = r12
            goto L_0x00e8
        L_0x00e7:
            r10 = 0
        L_0x00e8:
            if (r10 != 0) goto L_0x00f1
            r17 = r6
            r18 = r7
            r20 = r8
            goto L_0x014d
        L_0x00f1:
            android.graphics.Rect r10 = r11.mCurrentBounds
            int r12 = r10.top
            int r12 = r12 + r15
            int r10 = r10.left
            int r10 = java.lang.Math.max(r13, r10)
            int r10 = java.lang.Math.min(r10, r14)
            r17 = r6
            android.graphics.Rect r6 = r11.mCurrentBounds
            int r6 = r6.right
            int r6 = java.lang.Math.min(r14, r6)
            int r6 = java.lang.Math.max(r6, r10)
            r18 = r7
            int r7 = r12 - r2
            r19 = r12
            r12 = 1
            if (r7 > r12) goto L_0x0123
            if (r3 != r10) goto L_0x011b
            if (r4 == r6) goto L_0x011e
        L_0x011b:
            if (r5 != 0) goto L_0x011e
            goto L_0x0123
        L_0x011e:
            r21 = r6
            r20 = r8
            goto L_0x0144
        L_0x0123:
            float r3 = (float) r3
            float r5 = (float) r1
            float r4 = (float) r4
            float r7 = (float) r2
            int r1 = r0.mCornerRadius
            float r2 = (float) r1
            android.graphics.Paint r1 = r0.mBackgroundPaint
            r16 = r1
            r1 = r23
            r20 = r2
            r2 = r3
            r3 = r5
            r5 = r7
            r21 = r6
            r6 = r20
            r7 = r20
            r20 = r8
            r8 = r16
            r1.drawRoundRect(r2, r3, r4, r5, r6, r7, r8)
            r1 = r19
        L_0x0144:
            android.graphics.Rect r2 = r11.mCurrentBounds
            int r2 = r2.bottom
            int r2 = r2 + r15
            r3 = r10
            r4 = r21
            r5 = 0
        L_0x014d:
            int r6 = r17 + 1
            r7 = r18
            r8 = r20
            r11 = 0
            goto L_0x00d6
        L_0x0155:
            float r3 = (float) r3
            float r5 = (float) r1
            float r4 = (float) r4
            float r6 = (float) r2
            int r1 = r0.mCornerRadius
            float r7 = (float) r1
            android.graphics.Paint r8 = r0.mBackgroundPaint
            r1 = r23
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r1.drawRoundRect(r2, r3, r4, r5, r6, r7, r8)
        L_0x0167:
            r22.updateClipping()
            goto L_0x01df
        L_0x016c:
            boolean r1 = r0.mInHeadsUpPinnedMode
            if (r1 != 0) goto L_0x0174
            boolean r1 = r0.mHeadsUpAnimatingAway
            if (r1 == 0) goto L_0x01df
        L_0x0174:
            int r1 = r0.mSidePaddings
            int r2 = r22.getWidth()
            int r3 = r0.mSidePaddings
            int r2 = r2 - r3
            int r3 = r22.getHeight()
            float r3 = (float) r3
            int r4 = r22.getChildCount()
            r5 = 0
            r11 = 0
        L_0x0188:
            if (r11 >= r4) goto L_0x01cc
            android.view.View r6 = r0.getChildAt(r11)
            int r7 = r6.getVisibility()
            r8 = 8
            if (r7 == r8) goto L_0x01c9
            boolean r7 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r7 == 0) goto L_0x01c9
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
            boolean r7 = r6.mIsPinned
            if (r7 != 0) goto L_0x01a4
            boolean r7 = r6.mHeadsupDisappearRunning
            if (r7 == 0) goto L_0x01c9
        L_0x01a4:
            float r7 = r6.getTranslation()
            r8 = 0
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 >= 0) goto L_0x01c9
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r7 = r6.mMenuRow
            boolean r7 = r7.shouldShowGutsOnSnapOpen()
            if (r7 == 0) goto L_0x01c9
            float r7 = r6.getTranslationY()
            float r3 = java.lang.Math.min(r3, r7)
            float r7 = r6.getTranslationY()
            int r6 = r6.mActualHeight
            float r6 = (float) r6
            float r7 = r7 + r6
            float r5 = java.lang.Math.max(r5, r7)
        L_0x01c9:
            int r11 = r11 + 1
            goto L_0x0188
        L_0x01cc:
            int r4 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r4 >= 0) goto L_0x01df
            float r4 = (float) r1
            float r6 = (float) r2
            int r1 = r0.mCornerRadius
            float r7 = (float) r1
            android.graphics.Paint r8 = r0.mBackgroundPaint
            r1 = r23
            r2 = r4
            r4 = r6
            r6 = r7
            r1.drawRoundRect(r2, r3, r4, r5, r6, r7, r8)
        L_0x01df:
            boolean r1 = r0.mDebugLines
            if (r1 == 0) goto L_0x0264
            java.util.HashSet r1 = r0.mDebugTextUsedYPositions
            if (r1 != 0) goto L_0x01ef
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            r0.mDebugTextUsedYPositions = r1
            goto L_0x01f2
        L_0x01ef:
            r1.clear()
        L_0x01f2:
            int r1 = r0.mTopPadding
            r2 = -65536(0xffffffffffff0000, float:NaN)
            java.lang.String r3 = "mTopPadding"
            r0.drawDebugInfo(r9, r1, r2, r3)
            int r1 = r0.mMaxLayoutHeight
            int r2 = r0.mCurrentStackHeight
            int r1 = java.lang.Math.min(r1, r2)
            r2 = -256(0xffffffffffffff00, float:NaN)
            java.lang.String r3 = "getLayoutHeight()"
            r0.drawDebugInfo(r9, r1, r2, r3)
            int r1 = r0.mMaxLayoutHeight
            r2 = -65281(0xffffffffffff00ff, float:NaN)
            java.lang.String r3 = "mMaxLayoutHeight"
            r0.drawDebugInfo(r9, r1, r2, r3)
            float r1 = r0.mKeyguardBottomPadding
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x022b
            int r1 = r22.getHeight()
            float r2 = r0.mKeyguardBottomPadding
            int r2 = (int) r2
            int r1 = r1 - r2
            r2 = -7829368(0xffffffffff888888, float:NaN)
            java.lang.String r3 = "getHeight() - mKeyguardBottomPadding"
            r0.drawDebugInfo(r9, r1, r2, r3)
        L_0x022b:
            int r1 = r22.getHeight()
            int r2 = r22.getEmptyBottomMargin()
            int r1 = r1 - r2
            r2 = -16711936(0xffffffffff00ff00, float:-1.7146522E38)
            java.lang.String r3 = "getHeight() - getEmptyBottomMargin()"
            r0.drawDebugInfo(r9, r1, r2, r3)
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r0.mAmbientState
            java.util.Objects.requireNonNull(r1)
            float r1 = r1.mStackY
            int r1 = (int) r1
            r2 = -16711681(0xffffffffff00ffff, float:-1.714704E38)
            java.lang.String r3 = "mAmbientState.getStackY()"
            r0.drawDebugInfo(r9, r1, r2, r3)
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r0.mAmbientState
            java.util.Objects.requireNonNull(r1)
            float r1 = r1.mStackY
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r0.mAmbientState
            java.util.Objects.requireNonNull(r2)
            float r2 = r2.mStackHeight
            float r1 = r1 + r2
            int r1 = (int) r1
            r2 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            java.lang.String r3 = "mAmbientState.getStackY() + mAmbientState.getStackHeight()"
            r0.drawDebugInfo(r9, r1, r2, r3)
        L_0x0264:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onDraw(android.graphics.Canvas):void");
    }

    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mScrollingEnabled && this.mIsExpanded) {
            NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
            Objects.requireNonNull(notificationSwipeHelper);
            if (!notificationSwipeHelper.mIsSwiping && !this.mExpandingNotification && !this.mDisallowScrollingInThisMotion) {
                if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
                    float axisValue = motionEvent.getAxisValue(9);
                    if (axisValue != 0.0f) {
                        int scrollRange = getScrollRange();
                        int i = this.mOwnScrollY;
                        int verticalScrollFactor = i - ((int) (axisValue * getVerticalScrollFactor()));
                        if (verticalScrollFactor < 0) {
                            scrollRange = 0;
                        } else if (verticalScrollFactor <= scrollRange) {
                            scrollRange = verticalScrollFactor;
                        }
                        if (scrollRange != i) {
                            setOwnScrollY(scrollRange, false);
                            return true;
                        }
                    }
                }
                return super.onGenericMotionEvent(motionEvent);
            }
        }
        return false;
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    public final boolean onInterceptTouchEventScroll(MotionEvent motionEvent) {
        boolean z;
        if (!this.mScrollingEnabled) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1) {
                        int findPointerIndex = motionEvent.findPointerIndex(i2);
                        if (findPointerIndex == -1) {
                            Log.e("StackScroller", "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                        } else {
                            int y = (int) motionEvent.getY(findPointerIndex);
                            int x = (int) motionEvent.getX(findPointerIndex);
                            int abs = Math.abs(y - this.mLastMotionY);
                            int abs2 = Math.abs(x - this.mDownX);
                            if (((float) abs) > getTouchSlop(motionEvent) && abs > abs2) {
                                setIsBeingDragged(true);
                                this.mLastMotionY = y;
                                this.mDownX = x;
                                if (this.mVelocityTracker == null) {
                                    this.mVelocityTracker = VelocityTracker.obtain();
                                }
                                this.mVelocityTracker.addMovement(motionEvent);
                            }
                        }
                    }
                } else if (i != 3) {
                    if (i == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            setIsBeingDragged(false);
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            if (this.mScroller.springBack(this.mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                animateScroll();
            }
        } else {
            int y2 = (int) motionEvent.getY();
            C13637 r2 = this.mScrollAdapter;
            Objects.requireNonNull(r2);
            if (NotificationStackScrollLayout.this.mOwnScrollY == 0) {
                z = true;
            } else {
                z = false;
            }
            this.mScrolledToTopOnFirstDown = z;
            if (getChildAtPosition(motionEvent.getX(), (float) y2, false, false) == null) {
                setIsBeingDragged(false);
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.mVelocityTracker = null;
                }
            } else {
                this.mLastMotionY = y2;
                this.mDownX = (int) motionEvent.getX();
                this.mActivePointerId = motionEvent.getPointerId(0);
                VelocityTracker velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker3.clear();
                }
                this.mVelocityTracker.addMovement(motionEvent);
                setIsBeingDragged(!this.mScroller.isFinished());
            }
        }
        return this.mIsBeingDragged;
    }

    public final boolean onKeyguard() {
        if (this.mStatusBarState == 1) {
            return true;
        }
        return false;
    }

    public final void onOverScrollFling(boolean z, int i) {
        int i2;
        OnOverscrollTopChangedListener onOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (onOverscrollTopChangedListener != null) {
            float f = (float) i;
            NotificationPanelViewController.OnOverscrollTopChangedListener onOverscrollTopChangedListener2 = (NotificationPanelViewController.OnOverscrollTopChangedListener) onOverscrollTopChangedListener;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (!notificationPanelViewController.mShouldUseSplitNotificationShade || !notificationPanelViewController.touchXOutsideOfQs(notificationPanelViewController.mInitialTouchX)) {
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.mLastOverscroll = 0.0f;
                notificationPanelViewController2.mQsExpansionFromOverscroll = false;
                if (z) {
                    notificationPanelViewController2.mStackScrollerOverscrolling = false;
                    C0961QS qs = notificationPanelViewController2.mQs;
                    if (qs != null) {
                        qs.setOverscrolling(false);
                    }
                }
                NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                notificationPanelViewController3.setQsExpansion(notificationPanelViewController3.mQsExpansionHeight);
                boolean isQsExpansionEnabled = NotificationPanelViewController.this.isQsExpansionEnabled();
                NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                if (!isQsExpansionEnabled && z) {
                    f = 0.0f;
                }
                if (!z || !isQsExpansionEnabled) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                notificationPanelViewController4.flingSettings(f, i2, new WMShell$7$$ExternalSyntheticLambda1(onOverscrollTopChangedListener2, 5), false);
            }
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:83:0x01ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onScrollTouch(android.view.MotionEvent r29) {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            boolean r2 = r0.mScrollingEnabled
            r3 = 0
            if (r2 != 0) goto L_0x000a
            return r3
        L_0x000a:
            float r2 = r29.getY()
            android.view.ViewGroup r4 = r0.mQsContainer
            int r4 = r4.getBottom()
            float r4 = (float) r4
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            r4 = 1
            if (r2 >= 0) goto L_0x001c
            r2 = r4
            goto L_0x001d
        L_0x001c:
            r2 = r3
        L_0x001d:
            if (r2 == 0) goto L_0x0024
            boolean r2 = r0.mIsBeingDragged
            if (r2 != 0) goto L_0x0024
            return r3
        L_0x0024:
            r2 = 0
            r0.mForcedScroll = r2
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            if (r2 != 0) goto L_0x0031
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r2
        L_0x0031:
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            r2.addMovement(r1)
            int r2 = r29.getActionMasked()
            int r5 = r0.mActivePointerId
            int r5 = r1.findPointerIndex(r5)
            java.lang.String r6 = "Invalid pointerId="
            java.lang.String r7 = "StackScroller"
            r8 = -1
            if (r5 != r8) goto L_0x006a
            if (r2 == 0) goto L_0x006a
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r6)
            int r0 = r0.mActivePointerId
            r2.append(r0)
            java.lang.String r0 = " in onTouchEvent "
            r2.append(r0)
            int r0 = r29.getActionMasked()
            java.lang.String r0 = android.view.MotionEvent.actionToString(r0)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r7, r0)
            return r4
        L_0x006a:
            if (r2 == 0) goto L_0x02d1
            r5 = 5
            r9 = 0
            if (r2 == r4) goto L_0x01bd
            r10 = 2
            if (r2 == r10) goto L_0x00dd
            r3 = 3
            if (r2 == r3) goto L_0x00b6
            if (r2 == r5) goto L_0x009c
            r3 = 6
            if (r2 == r3) goto L_0x007d
            goto L_0x031a
        L_0x007d:
            r28.onSecondaryPointerUp(r29)
            int r2 = r0.mActivePointerId
            int r2 = r1.findPointerIndex(r2)
            float r2 = r1.getY(r2)
            int r2 = (int) r2
            r0.mLastMotionY = r2
            int r2 = r0.mActivePointerId
            int r2 = r1.findPointerIndex(r2)
            float r1 = r1.getX(r2)
            int r1 = (int) r1
            r0.mDownX = r1
            goto L_0x031a
        L_0x009c:
            int r2 = r29.getActionIndex()
            float r3 = r1.getY(r2)
            int r3 = (int) r3
            r0.mLastMotionY = r3
            float r3 = r1.getX(r2)
            int r3 = (int) r3
            r0.mDownX = r3
            int r1 = r1.getPointerId(r2)
            r0.mActivePointerId = r1
            goto L_0x031a
        L_0x00b6:
            boolean r1 = r0.mIsBeingDragged
            if (r1 == 0) goto L_0x031a
            int r1 = r28.getChildCount()
            if (r1 <= 0) goto L_0x031a
            android.widget.OverScroller r9 = r0.mScroller
            int r10 = r0.mScrollX
            int r11 = r0.mOwnScrollY
            r12 = 0
            r13 = 0
            r14 = 0
            int r15 = r28.getScrollRange()
            boolean r1 = r9.springBack(r10, r11, r12, r13, r14, r15)
            if (r1 == 0) goto L_0x00d6
            r28.animateScroll()
        L_0x00d6:
            r0.mActivePointerId = r8
            r28.endDrag()
            goto L_0x031a
        L_0x00dd:
            int r2 = r0.mActivePointerId
            int r2 = r1.findPointerIndex(r2)
            if (r2 != r8) goto L_0x00fc
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r6)
            int r0 = r0.mActivePointerId
            r1.append(r0)
            java.lang.String r0 = " in onTouchEvent"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.e(r7, r0)
            goto L_0x031a
        L_0x00fc:
            float r5 = r1.getY(r2)
            int r5 = (int) r5
            float r2 = r1.getX(r2)
            int r2 = (int) r2
            int r6 = r0.mLastMotionY
            int r6 = r6 - r5
            int r7 = r0.mDownX
            int r2 = r2 - r7
            int r2 = java.lang.Math.abs(r2)
            int r7 = java.lang.Math.abs(r6)
            float r1 = r28.getTouchSlop(r29)
            boolean r8 = r0.mIsBeingDragged
            if (r8 != 0) goto L_0x012d
            float r8 = (float) r7
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 <= 0) goto L_0x012d
            if (r7 <= r2) goto L_0x012d
            r0.setIsBeingDragged(r4)
            float r2 = (float) r6
            if (r6 <= 0) goto L_0x012b
            float r2 = r2 - r1
            goto L_0x012c
        L_0x012b:
            float r2 = r2 + r1
        L_0x012c:
            int r6 = (int) r2
        L_0x012d:
            boolean r1 = r0.mIsBeingDragged
            if (r1 == 0) goto L_0x031a
            r0.mLastMotionY = r5
            int r1 = r28.getScrollRange()
            boolean r2 = r0.mExpandedInThisMotion
            if (r2 == 0) goto L_0x0141
            int r2 = r0.mMaxScrollAfterExpand
            int r1 = java.lang.Math.min(r1, r2)
        L_0x0141:
            if (r6 >= 0) goto L_0x0171
            int r2 = java.lang.Math.min(r6, r3)
            float r5 = r0.getCurrentOverScrollAmount(r3)
            float r2 = (float) r2
            float r2 = r2 + r5
            int r5 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r5 <= 0) goto L_0x0154
            r0.setOverScrollAmount(r2, r3, r3, r4)
        L_0x0154:
            int r5 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r5 >= 0) goto L_0x0159
            goto L_0x015a
        L_0x0159:
            r2 = r9
        L_0x015a:
            int r5 = r0.mOwnScrollY
            float r5 = (float) r5
            float r5 = r5 + r2
            int r6 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r6 >= 0) goto L_0x01a7
            float r2 = r0.mOverScrolledTopPixels
            float r2 = r2 - r5
            float r5 = r0.getRubberBandFactor(r4)
            float r5 = r5 * r2
            r0.setOverScrollAmount(r5, r4, r3, r4)
            r0.setOwnScrollY(r3, r3)
            goto L_0x01a6
        L_0x0171:
            int r2 = java.lang.Math.max(r6, r3)
            float r5 = r0.getCurrentOverScrollAmount(r4)
            float r2 = (float) r2
            float r2 = r5 - r2
            int r5 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r5 <= 0) goto L_0x0183
            r0.setOverScrollAmount(r2, r4, r3, r4)
        L_0x0183:
            int r5 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r5 >= 0) goto L_0x0189
            float r2 = -r2
            goto L_0x018a
        L_0x0189:
            r2 = r9
        L_0x018a:
            int r5 = r0.mOwnScrollY
            float r5 = (float) r5
            float r5 = r5 + r2
            float r6 = (float) r1
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 <= 0) goto L_0x01a7
            boolean r2 = r0.mExpandedInThisMotion
            if (r2 != 0) goto L_0x01a3
            float r2 = r0.mOverScrolledBottomPixels
            float r2 = r2 + r5
            float r2 = r2 - r6
            float r5 = r0.getRubberBandFactor(r3)
            float r5 = r5 * r2
            r0.setOverScrollAmount(r5, r3, r3, r4)
        L_0x01a3:
            r0.setOwnScrollY(r1, r3)
        L_0x01a6:
            r2 = r9
        L_0x01a7:
            int r3 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r3 == 0) goto L_0x031a
            int r2 = (int) r2
            int r3 = r0.mOwnScrollY
            int r5 = r28.getHeight()
            int r5 = r5 / r10
            r0.customOverScrollBy(r2, r3, r1, r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r0.mController
            r0.checkSnoozeLeavebehind()
            goto L_0x031a
        L_0x01bd:
            boolean r1 = r0.mIsBeingDragged
            if (r1 == 0) goto L_0x031a
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            r2 = 1000(0x3e8, float:1.401E-42)
            int r6 = r0.mMaximumVelocity
            float r6 = (float) r6
            r1.computeCurrentVelocity(r2, r6)
            int r2 = r0.mActivePointerId
            float r1 = r1.getYVelocity(r2)
            int r1 = (int) r1
            float r2 = r0.getCurrentOverScrollAmount(r4)
            boolean r6 = r0.mScrolledToTopOnFirstDown
            if (r6 == 0) goto L_0x01ec
            boolean r6 = r0.mExpandedInThisMotion
            if (r6 != 0) goto L_0x01ec
            int r6 = r0.mMinimumVelocity
            if (r1 > r6) goto L_0x01ea
            float r6 = r0.mMinTopOverScrollToEscape
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x01ec
            if (r1 <= 0) goto L_0x01ec
        L_0x01ea:
            r2 = r4
            goto L_0x01ed
        L_0x01ec:
            r2 = r3
        L_0x01ed:
            if (r2 == 0) goto L_0x01f4
            r0.onOverScrollFling(r4, r1)
            goto L_0x02cb
        L_0x01f4:
            int r2 = r28.getChildCount()
            if (r2 <= 0) goto L_0x02cb
            int r2 = java.lang.Math.abs(r1)
            int r6 = r0.mMinimumVelocity
            if (r2 <= r6) goto L_0x02ac
            float r2 = r0.getCurrentOverScrollAmount(r4)
            int r2 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x0212
            if (r1 <= 0) goto L_0x020d
            goto L_0x0212
        L_0x020d:
            r0.onOverScrollFling(r3, r1)
            goto L_0x02cb
        L_0x0212:
            r0.mFlingAfterUpEvent = r4
            com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1 r2 = new com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1
            r2.<init>(r0, r5)
            r0.mFinishScrollingCallback = r2
            int r14 = -r1
            int r1 = r28.getChildCount()
            if (r1 <= 0) goto L_0x02cb
            float r1 = r0.getCurrentOverScrollAmount(r4)
            float r2 = r0.getCurrentOverScrollAmount(r3)
            r5 = 1148846080(0x447a0000, float:1000.0)
            if (r14 >= 0) goto L_0x0251
            int r6 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r6 <= 0) goto L_0x0251
            int r2 = r0.mOwnScrollY
            int r6 = (int) r1
            int r2 = r2 - r6
            r0.setOwnScrollY(r2, r3)
            r0.mDontReportNextOverScroll = r4
            r0.setOverScrollAmount(r9, r4, r3, r4)
            int r2 = java.lang.Math.abs(r14)
            float r2 = (float) r2
            float r2 = r2 / r5
            float r5 = r0.getRubberBandFactor(r4)
            float r5 = r5 * r2
            int r2 = r0.mOverflingDistance
            float r2 = (float) r2
            float r5 = r5 * r2
            float r5 = r5 + r1
            r0.mMaxOverScroll = r5
            goto L_0x0277
        L_0x0251:
            if (r14 <= 0) goto L_0x0275
            int r1 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r1 <= 0) goto L_0x0275
            int r1 = r0.mOwnScrollY
            float r1 = (float) r1
            float r1 = r1 + r2
            int r1 = (int) r1
            r0.setOwnScrollY(r1, r3)
            r0.setOverScrollAmount(r9, r3, r3, r4)
            int r1 = java.lang.Math.abs(r14)
            float r1 = (float) r1
            float r1 = r1 / r5
            float r5 = r0.getRubberBandFactor(r3)
            float r5 = r5 * r1
            int r1 = r0.mOverflingDistance
            float r1 = (float) r1
            float r5 = r5 * r1
            float r5 = r5 + r2
            r0.mMaxOverScroll = r5
            goto L_0x0277
        L_0x0275:
            r0.mMaxOverScroll = r9
        L_0x0277:
            int r1 = r28.getScrollRange()
            int r1 = java.lang.Math.max(r3, r1)
            boolean r2 = r0.mExpandedInThisMotion
            if (r2 == 0) goto L_0x0289
            int r2 = r0.mMaxScrollAfterExpand
            int r1 = java.lang.Math.min(r1, r2)
        L_0x0289:
            r18 = r1
            android.widget.OverScroller r10 = r0.mScroller
            int r11 = r0.mScrollX
            int r12 = r0.mOwnScrollY
            r13 = 1
            r15 = 0
            r16 = 0
            r17 = 0
            r19 = 0
            boolean r1 = r0.mExpandedInThisMotion
            if (r1 == 0) goto L_0x02a0
            if (r12 < 0) goto L_0x02a0
            goto L_0x02a3
        L_0x02a0:
            r3 = 1073741823(0x3fffffff, float:1.9999999)
        L_0x02a3:
            r20 = r3
            r10.fling(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r28.animateScroll()
            goto L_0x02cb
        L_0x02ac:
            android.widget.OverScroller r1 = r0.mScroller
            int r2 = r0.mScrollX
            int r3 = r0.mOwnScrollY
            r24 = 0
            r25 = 0
            r26 = 0
            int r27 = r28.getScrollRange()
            r21 = r1
            r22 = r2
            r23 = r3
            boolean r1 = r21.springBack(r22, r23, r24, r25, r26, r27)
            if (r1 == 0) goto L_0x02cb
            r28.animateScroll()
        L_0x02cb:
            r0.mActivePointerId = r8
            r28.endDrag()
            goto L_0x031a
        L_0x02d1:
            int r2 = r28.getChildCount()
            if (r2 == 0) goto L_0x031b
            float r2 = r29.getY()
            int r5 = r28.getHeight()
            int r6 = r28.getEmptyBottomMargin()
            int r5 = r5 - r6
            float r5 = (float) r5
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 >= 0) goto L_0x02eb
            r2 = r4
            goto L_0x02ec
        L_0x02eb:
            r2 = r3
        L_0x02ec:
            if (r2 != 0) goto L_0x02ef
            goto L_0x031b
        L_0x02ef:
            android.widget.OverScroller r2 = r0.mScroller
            boolean r2 = r2.isFinished()
            r2 = r2 ^ r4
            r0.setIsBeingDragged(r2)
            android.widget.OverScroller r2 = r0.mScroller
            boolean r2 = r2.isFinished()
            if (r2 != 0) goto L_0x0306
            android.widget.OverScroller r2 = r0.mScroller
            r2.forceFinished(r4)
        L_0x0306:
            float r2 = r29.getY()
            int r2 = (int) r2
            r0.mLastMotionY = r2
            float r2 = r29.getX()
            int r2 = (int) r2
            r0.mDownX = r2
            int r1 = r1.getPointerId(r3)
            r0.mActivePointerId = r1
        L_0x031a:
            return r4
        L_0x031b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onScrollTouch(android.view.MotionEvent):boolean");
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public final void onUpdateRowStates() {
        ForegroundServiceDungeonView foregroundServiceDungeonView = this.mFgsSectionView;
        int i = 1;
        if (foregroundServiceDungeonView != null) {
            changeViewPosition(foregroundServiceDungeonView, getChildCount() - 1);
            i = 2;
        }
        int i2 = i + 1;
        changeViewPosition(this.mFooterView, getChildCount() - i);
        changeViewPosition(this.mEmptyShadeView, getChildCount() - i2);
        changeViewPosition(this.mShelf, getChildCount() - (i2 + 1));
    }

    public final void onViewAddedInternal(ExpandableView expandableView) {
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        expandableView.setHideSensitiveForIntrinsicHeight(ambientState.mHideSensitive);
        expandableView.mOnHeightChangedListener = this.mOnChildHeightChangedListener;
        boolean z = false;
        generateAddAnimation(expandableView, false);
        if ((this.mAnimationsEnabled || this.mPulsing) && (this.mIsExpanded || isPinnedHeadsUp(expandableView))) {
            z = true;
        }
        boolean z2 = expandableView instanceof ExpandableNotificationRow;
        if (z2) {
            ((ExpandableNotificationRow) expandableView).setIconAnimationRunning(z);
        }
        if (z2) {
            ((ExpandableNotificationRow) expandableView).setChronometerRunning(this.mIsExpanded);
        }
        if (z2) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            boolean z3 = this.mDismissUsingRowTranslationX;
            if (z3 != expandableNotificationRow.mDismissUsingRowTranslationX) {
                float translation = expandableNotificationRow.getTranslation();
                int i = (translation > 0.0f ? 1 : (translation == 0.0f ? 0 : -1));
                if (i != 0) {
                    expandableNotificationRow.setTranslation(0.0f);
                }
                expandableNotificationRow.mDismissUsingRowTranslationX = z3;
                if (i != 0) {
                    expandableNotificationRow.setTranslation(translation);
                }
            }
        }
    }

    public final void onViewRemovedInternal(ExpandableView expandableView, ViewGroup viewGroup) {
        int i;
        float f;
        if (!this.mChangePositionInProgress) {
            Objects.requireNonNull(expandableView);
            expandableView.mOnHeightChangedListener = null;
            int positionInLinearLayout = getPositionInLinearLayout(expandableView);
            int intrinsicHeight = expandableView.getIntrinsicHeight() + this.mPaddingBetweenElements;
            int i2 = positionInLinearLayout + intrinsicHeight;
            if (this.mShouldUseSplitNotificationShade) {
                i = this.mSidePaddings;
            } else {
                i = this.mTopPadding - this.mQsScrollBoundaryPosition;
            }
            this.mAnimateStackYForContentHeightChange = true;
            int i3 = this.mOwnScrollY;
            int i4 = i3 - i;
            boolean z = false;
            if (i2 <= i4) {
                setOwnScrollY(i3 - intrinsicHeight, false);
            } else if (positionInLinearLayout < i4) {
                setOwnScrollY(positionInLinearLayout + i, false);
            }
            if (!generateRemoveAnimation(expandableView)) {
                this.mSwipedOutViews.remove(expandableView);
            } else if (!this.mSwipedOutViews.contains(expandableView) || !isFullySwipedOut(expandableView)) {
                viewGroup.addTransientView(expandableView, 0);
                expandableView.mTransientContainer = viewGroup;
            }
            boolean z2 = expandableView instanceof ExpandableNotificationRow;
            if (z2) {
                ((ExpandableNotificationRow) expandableView).setIconAnimationRunning(false);
            }
            if (z2) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (expandableNotificationRow.mRefocusOnDismiss || expandableNotificationRow.isAccessibilityFocused()) {
                    z = true;
                }
                if (z) {
                    View view = expandableNotificationRow.mChildAfterViewWhenDismissed;
                    if (view == null) {
                        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mGroupParentWhenDismissed;
                        if (expandableNotificationRow2 != null) {
                            f = expandableNotificationRow2.getTranslationY();
                        } else {
                            f = expandableView.getTranslationY();
                        }
                        view = getFirstChildBelowTranlsationY(f, true);
                    }
                    if (view != null) {
                        view.requestAccessibilityFocus();
                    }
                }
            }
        }
    }

    public final void requestChildrenUpdate() {
        if (!this.mChildrenUpdateRequested) {
            getViewTreeObserver().addOnPreDrawListener(this.mChildrenUpdater);
            this.mChildrenUpdateRequested = true;
            invalidate();
        }
    }

    public final void setAnimationRunning(boolean z) {
        if (z != this.mAnimationRunning) {
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mRunningAnimationUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mRunningAnimationUpdater);
            }
            this.mAnimationRunning = z;
            updateContinuousShadowDrawing();
        }
    }

    public final void setClearAllInProgress(boolean z) {
        this.mClearAllInProgress = z;
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mClearAllInProgress = z;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayoutController.mNotificationRoundnessManager;
        Objects.requireNonNull(notificationRoundnessManager);
        notificationRoundnessManager.mIsClearAllInProgress = z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                canChildBeCleared(expandableView);
            }
        }
    }

    @VisibleForTesting
    public void setIsBeingDragged(boolean z) {
        this.mIsBeingDragged = z;
        if (z) {
            requestDisallowInterceptTouchEvent(true);
            cancelLongPress();
            this.mSwipeHelper.resetExposedMenuView(true, true);
        }
    }

    public final void setOverScrollAmount(float f, boolean z, boolean z2, boolean z3, boolean z4) {
        if (z3) {
            StackStateAnimator stackStateAnimator = this.mStateAnimator;
            Objects.requireNonNull(stackStateAnimator);
            ValueAnimator valueAnimator = z ? stackStateAnimator.mTopOverScrollAnimator : stackStateAnimator.mBottomOverScrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
        }
        float max = Math.max(0.0f, f);
        if (z2) {
            StackStateAnimator stackStateAnimator2 = this.mStateAnimator;
            Objects.requireNonNull(stackStateAnimator2);
            float currentOverScrollAmount = stackStateAnimator2.mHostLayout.getCurrentOverScrollAmount(z);
            if (max != currentOverScrollAmount) {
                ValueAnimator valueAnimator2 = z ? stackStateAnimator2.mTopOverScrollAnimator : stackStateAnimator2.mBottomOverScrollAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{currentOverScrollAmount, max});
                ofFloat.setDuration(360);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(z, z4) {
                    public final /* synthetic */ boolean val$isRubberbanded;
                    public final /* synthetic */ boolean val$onTop;

                    {
                        this.val$onTop = r2;
                        this.val$isRubberbanded = r3;
                    }

                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), this.val$onTop, false, false, this.val$isRubberbanded);
                    }
                });
                ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                ofFloat.addListener(new AnimatorListenerAdapter(z) {
                    public final /* synthetic */ boolean val$onTop;

                    {
                        this.val$onTop = r2;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        if (this.val$onTop) {
                            StackStateAnimator.this.mTopOverScrollAnimator = null;
                        } else {
                            StackStateAnimator.this.mBottomOverScrollAnimator = null;
                        }
                    }
                });
                ofFloat.start();
                if (z) {
                    stackStateAnimator2.mTopOverScrollAnimator = ofFloat;
                } else {
                    stackStateAnimator2.mBottomOverScrollAnimator = ofFloat;
                }
            }
        } else {
            float rubberBandFactor = max / getRubberBandFactor(z);
            if (z) {
                this.mOverScrolledTopPixels = rubberBandFactor;
            } else {
                this.mOverScrolledBottomPixels = rubberBandFactor;
            }
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            if (z) {
                ambientState.mOverScrollTopAmount = max;
            } else {
                ambientState.mOverScrollBottomAmount = max;
            }
            if (z) {
                notifyOverscrollTopListener(max, z4);
            }
            updateStackPosition(false);
            requestChildrenUpdate();
        }
    }

    public final void setOwnScrollY(int i, boolean z) {
        int i2 = this.mOwnScrollY;
        if (i != i2) {
            int i3 = this.mScrollX;
            onScrollChanged(i3, i, i3, i2);
            this.mOwnScrollY = i;
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            ambientState.mScrollY = Math.max(i, 0);
            Consumer<Integer> consumer = this.mScrollListener;
            if (consumer != null) {
                consumer.accept(Integer.valueOf(this.mOwnScrollY));
            }
            updateForwardAndBackwardScrollability();
            requestChildrenUpdate();
            updateStackPosition(z);
        }
    }

    public final float setPulseHeight(float f) {
        float f2;
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (f != ambientState.mPulseHeight) {
            ambientState.mPulseHeight = f;
            Runnable runnable = ambientState.mOnPulseHeightChangedListener;
            if (runnable != null) {
                runnable.run();
            }
        }
        if (this.mKeyguardBypassEnabled) {
            notifyAppearChangedListeners();
            f2 = Math.max(0.0f, f - ((float) this.mIntrinsicPadding));
        } else {
            f2 = Math.max(0.0f, f - ((float) this.mAmbientState.getInnerHeight(true)));
        }
        requestChildrenUpdate();
        return f2;
    }

    @VisibleForTesting
    public void setStatusBarState(int i) {
        this.mStatusBarState = i;
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mStatusBarState = i;
        this.mSpeedBumpIndexDirty = true;
        updateDismissBehavior();
    }

    public final boolean shouldSkipHeightUpdate() {
        if (this.mAmbientState.isOnKeyguard()) {
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            if (!ambientState.mUnlockHintRunning) {
                AmbientState ambientState2 = this.mAmbientState;
                Objects.requireNonNull(ambientState2);
                if (ambientState2.mIsSwipingUp || this.mIsFlinging) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:122:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0278  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0311  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x0394  */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x05ae  */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x0602  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x062a  */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x0655  */
    /* JADX WARNING: Removed duplicated region for block: B:299:0x0672  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x06be  */
    /* JADX WARNING: Removed duplicated region for block: B:406:0x08ae  */
    /* JADX WARNING: Removed duplicated region for block: B:417:0x0013 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:433:0x0323 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:441:0x066c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void startAnimationToState$1() {
        /*
            r33 = this;
            r0 = r33
            com.android.systemui.log.LogLevel r1 = com.android.systemui.log.LogLevel.INFO
            boolean r2 = r0.mNeedsAnimation
            r5 = 5
            r9 = 8
            r10 = 0
            r12 = 0
            if (r2 == 0) goto L_0x035c
            java.util.HashSet<android.util.Pair<com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, java.lang.Boolean>> r2 = r0.mHeadsUpChangeAnimations
            java.util.Iterator r2 = r2.iterator()
        L_0x0013:
            boolean r14 = r2.hasNext()
            java.lang.String r15 = "StackScroller"
            if (r14 == 0) goto L_0x0154
            java.lang.Object r14 = r2.next()
            android.util.Pair r14 = (android.util.Pair) r14
            java.lang.Object r3 = r14.first
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r3.mEntry
            java.util.Objects.requireNonNull(r11)
            java.lang.String r11 = r11.mKey
            java.lang.Object r14 = r14.second
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r14 = r14.booleanValue()
            boolean r13 = r3.mIsHeadsUp
            java.lang.String r4 = "NotificationStackScroll"
            if (r14 == r13) goto L_0x005b
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger r3 = r0.mLogger
            if (r3 != 0) goto L_0x0042
            goto L_0x0013
        L_0x0042:
            com.android.systemui.log.LogBuffer r3 = r3.buffer
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunSkippedForUnexpectedState$2 r15 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunSkippedForUnexpectedState$2.INSTANCE
            java.util.Objects.requireNonNull(r3)
            boolean r7 = r3.frozen
            if (r7 != 0) goto L_0x0013
            com.android.systemui.log.LogMessageImpl r4 = r3.obtain(r4, r1, r15)
            r4.str1 = r11
            r4.bool1 = r14
            r4.bool2 = r13
            r3.push(r4)
            goto L_0x0013
        L_0x005b:
            boolean r7 = r3.mIsPinned
            if (r7 == 0) goto L_0x0065
            boolean r7 = r0.mIsExpanded
            if (r7 != 0) goto L_0x0065
            r7 = 1
            goto L_0x0066
        L_0x0065:
            r7 = r12
        L_0x0066:
            boolean r13 = r0.mIsExpanded
            if (r13 == 0) goto L_0x007b
            boolean r13 = r0.mKeyguardBypassEnabled
            if (r13 == 0) goto L_0x0079
            boolean r13 = r33.onKeyguard()
            if (r13 == 0) goto L_0x0079
            boolean r13 = r0.mInHeadsUpPinnedMode
            if (r13 == 0) goto L_0x0079
            goto L_0x007b
        L_0x0079:
            r13 = r12
            goto L_0x007c
        L_0x007b:
            r13 = 1
        L_0x007c:
            if (r13 == 0) goto L_0x009a
            if (r14 != 0) goto L_0x009a
            boolean r7 = r3.mJustClicked
            if (r7 == 0) goto L_0x0087
            r7 = 13
            goto L_0x0089
        L_0x0087:
            r7 = 12
        L_0x0089:
            boolean r13 = r3.isChildInGroup()
            if (r13 == 0) goto L_0x00d9
            r3.setHeadsUpAnimatingAway(r12)
            java.lang.String r3 = "row is child in group"
            r0.logHunAnimationSkipped(r11, r3)
            goto L_0x0013
        L_0x009a:
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r13 = r3.mViewState
            if (r13 != 0) goto L_0x00a6
            java.lang.String r3 = "row has no viewState"
            r0.logHunAnimationSkipped(r11, r3)
            goto L_0x0013
        L_0x00a6:
            if (r14 == 0) goto L_0x00d7
            java.util.ArrayList<android.view.View> r6 = r0.mAddedHeadsUpChildren
            boolean r6 = r6.contains(r3)
            if (r6 != 0) goto L_0x00b2
            if (r7 == 0) goto L_0x00d7
        L_0x00b2:
            if (r7 != 0) goto L_0x00cd
            float r6 = r13.yTranslation
            int r13 = r13.height
            float r13 = (float) r13
            float r6 = r6 + r13
            com.android.systemui.statusbar.notification.stack.AmbientState r13 = r0.mAmbientState
            java.util.Objects.requireNonNull(r13)
            float r13 = r13.mMaxHeadsUpTranslation
            int r6 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r6 >= 0) goto L_0x00c7
            r6 = r12
            goto L_0x00c8
        L_0x00c7:
            r6 = 1
        L_0x00c8:
            if (r6 == 0) goto L_0x00cb
            goto L_0x00cd
        L_0x00cb:
            r6 = r12
            goto L_0x00cf
        L_0x00cd:
            r6 = 11
        L_0x00cf:
            r7 = r7 ^ 1
            r32 = r7
            r7 = r6
            r6 = r32
            goto L_0x00da
        L_0x00d7:
            r7 = 14
        L_0x00d9:
            r6 = r12
        L_0x00da:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r13 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r13.<init>(r3, r7)
            r13.headsUpFromBottom = r6
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r8 = r0.mAnimationEvents
            r8.add(r13)
            boolean r8 = SPEW
            if (r8 == 0) goto L_0x011d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r13 = "Generating HUN animation event:  isHeadsUp="
            r8.append(r13)
            r8.append(r14)
            java.lang.String r13 = " type="
            r8.append(r13)
            r8.append(r7)
            java.lang.String r13 = " onBottom="
            r8.append(r13)
            r8.append(r6)
            java.lang.String r6 = " row="
            r8.append(r6)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.mEntry
            java.util.Objects.requireNonNull(r3)
            java.lang.String r3 = r3.mKey
            r8.append(r3)
            java.lang.String r3 = r8.toString()
            android.util.Log.v(r15, r3)
        L_0x011d:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger r3 = r0.mLogger
            if (r3 != 0) goto L_0x0123
            goto L_0x0013
        L_0x0123:
            if (r7 == 0) goto L_0x013a
            switch(r7) {
                case 11: goto L_0x0132;
                case 12: goto L_0x012f;
                case 13: goto L_0x012c;
                case 14: goto L_0x0129;
                default: goto L_0x0128;
            }
        L_0x0128:
            goto L_0x0135
        L_0x0129:
            java.lang.String r6 = "HEADS_UP_OTHER"
            goto L_0x013c
        L_0x012c:
            java.lang.String r6 = "HEADS_UP_DISAPPEAR_CLICK"
            goto L_0x013c
        L_0x012f:
            java.lang.String r6 = "HEADS_UP_DISAPPEAR"
            goto L_0x013c
        L_0x0132:
            java.lang.String r6 = "HEADS_UP_APPEAR"
            goto L_0x013c
        L_0x0135:
            java.lang.String r6 = java.lang.String.valueOf(r7)
            goto L_0x013c
        L_0x013a:
            java.lang.String r6 = "ADD"
        L_0x013c:
            com.android.systemui.log.LogBuffer r3 = r3.buffer
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunAnimationEventAdded$2 r7 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunAnimationEventAdded$2.INSTANCE
            java.util.Objects.requireNonNull(r3)
            boolean r8 = r3.frozen
            if (r8 != 0) goto L_0x0013
            com.android.systemui.log.LogMessageImpl r4 = r3.obtain(r4, r1, r7)
            r4.str1 = r11
            r4.str2 = r6
            r3.push(r4)
            goto L_0x0013
        L_0x0154:
            java.util.HashSet<android.util.Pair<com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, java.lang.Boolean>> r2 = r0.mHeadsUpChangeAnimations
            r2.clear()
            java.util.ArrayList<android.view.View> r2 = r0.mAddedHeadsUpChildren
            r2.clear()
            java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenToRemoveAnimated
            java.util.Iterator r2 = r2.iterator()
        L_0x0164:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x01fa
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            java.util.ArrayList<android.view.View> r4 = r0.mSwipedOutViews
            boolean r4 = r4.contains(r3)
            float r6 = r3.getTranslationY()
            boolean r7 = r3 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r7 == 0) goto L_0x0194
            r8 = r3
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            boolean r11 = r8.mRemoved
            if (r11 == 0) goto L_0x018d
            boolean r11 = r8.mWasChildInGroupWhenRemoved
            if (r11 == 0) goto L_0x018d
            float r6 = r8.mTranslationWhenRemoved
            r11 = r12
            goto L_0x018e
        L_0x018d:
            r11 = 1
        L_0x018e:
            boolean r8 = r0.isFullySwipedOut(r8)
            r4 = r4 | r8
            goto L_0x019a
        L_0x0194:
            boolean r8 = r3 instanceof com.android.systemui.statusbar.notification.stack.MediaContainerView
            if (r8 == 0) goto L_0x0199
            r4 = 1
        L_0x0199:
            r11 = 1
        L_0x019a:
            if (r4 != 0) goto L_0x01b0
            android.graphics.Rect r4 = r3.getClipBounds()
            if (r4 == 0) goto L_0x01aa
            int r4 = r4.height()
            if (r4 != 0) goto L_0x01aa
            r4 = 1
            goto L_0x01ab
        L_0x01aa:
            r4 = r12
        L_0x01ab:
            if (r4 == 0) goto L_0x01b0
            r3.removeFromTransientContainer()
        L_0x01b0:
            if (r4 == 0) goto L_0x01b4
            r8 = 2
            goto L_0x01b5
        L_0x01b4:
            r8 = 1
        L_0x01b5:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r13 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r13.<init>(r3, r8)
            android.view.View r6 = r0.getFirstChildBelowTranlsationY(r6, r11)
            r13.viewAfterChangingView = r6
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r6 = r0.mAnimationEvents
            r6.add(r13)
            java.util.ArrayList<android.view.View> r6 = r0.mSwipedOutViews
            r6.remove(r3)
            boolean r6 = r0.mDebugRemoveAnimation
            if (r6 == 0) goto L_0x0164
            if (r7 == 0) goto L_0x01da
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.mEntry
            java.util.Objects.requireNonNull(r3)
            java.lang.String r3 = r3.mKey
            goto L_0x01dc
        L_0x01da:
            java.lang.String r3 = ""
        L_0x01dc:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "created Remove Event - SwipedOut: "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r4 = " "
            r6.append(r4)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            android.util.Log.d(r15, r3)
            goto L_0x0164
        L_0x01fa:
            java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenToRemoveAnimated
            r2.clear()
            java.util.HashSet<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenToAddAnimated
            java.util.Iterator r2 = r2.iterator()
        L_0x0205:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0231
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            java.util.HashSet<android.view.View> r4 = r0.mFromMoreCardAdditions
            boolean r4 = r4.contains(r3)
            if (r4 == 0) goto L_0x0226
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r4 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r7 = 360(0x168, double:1.78E-321)
            r6.<init>(r3, r12, r7)
            r4.add(r6)
            goto L_0x0205
        L_0x0226:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r4 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r6.<init>(r3, r12)
            r4.add(r6)
            goto L_0x0205
        L_0x0231:
            java.util.HashSet<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenToAddAnimated
            r2.clear()
            java.util.HashSet<android.view.View> r2 = r0.mFromMoreCardAdditions
            r2.clear()
            java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenChangingPositions
            java.util.Iterator r2 = r2.iterator()
        L_0x0241:
            boolean r3 = r2.hasNext()
            r4 = 6
            if (r3 == 0) goto L_0x0288
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            boolean r6 = r3 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r6 == 0) goto L_0x026f
            r6 = r3
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r7 = r6.mEntry
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mIsMarkedForUserTriggeredMovement
            if (r7 == 0) goto L_0x026f
            r7 = 500(0x1f4, float:7.0E-43)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r6.mEntry
            java.util.Objects.requireNonNull(r6)
            r6.mIsMarkedForUserTriggeredMovement = r12
            goto L_0x0270
        L_0x026f:
            r7 = r10
        L_0x0270:
            if (r7 != 0) goto L_0x0278
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r6.<init>(r3, r4)
            goto L_0x0282
        L_0x0278:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            int r7 = r7.intValue()
            long r7 = (long) r7
            r6.<init>(r3, r4, r7)
        L_0x0282:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r3 = r0.mAnimationEvents
            r3.add(r6)
            goto L_0x0241
        L_0x0288:
            java.util.ArrayList<com.android.systemui.statusbar.notification.row.ExpandableView> r2 = r0.mChildrenChangingPositions
            r2.clear()
            boolean r2 = r0.mGenerateChildOrderChangedEvent
            if (r2 == 0) goto L_0x029d
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r3.<init>(r10, r4)
            r2.add(r3)
            r0.mGenerateChildOrderChangedEvent = r12
        L_0x029d:
            boolean r2 = r0.mTopPaddingNeedsAnimation
            if (r2 == 0) goto L_0x02bd
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r0.mAmbientState
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mDozing
            r3 = 3
            if (r2 == 0) goto L_0x02b3
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r2 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r6 = 550(0x226, double:2.717E-321)
            r2.<init>(r10, r3, r6)
            goto L_0x02b8
        L_0x02b3:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r2 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r2.<init>(r10, r3)
        L_0x02b8:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r3 = r0.mAnimationEvents
            r3.add(r2)
        L_0x02bd:
            r0.mTopPaddingNeedsAnimation = r12
            boolean r2 = r0.mActivateNeedsAnimation
            if (r2 == 0) goto L_0x02ce
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r4 = 4
            r3.<init>(r10, r4)
            r2.add(r3)
        L_0x02ce:
            r0.mActivateNeedsAnimation = r12
            boolean r2 = r0.mDimmedNeedsAnimation
            if (r2 == 0) goto L_0x02de
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r3.<init>(r10, r5)
            r2.add(r3)
        L_0x02de:
            r0.mDimmedNeedsAnimation = r12
            boolean r2 = r0.mHideSensitiveNeedsAnimation
            if (r2 == 0) goto L_0x02ee
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r3.<init>(r10, r9)
            r2.add(r3)
        L_0x02ee:
            r0.mHideSensitiveNeedsAnimation = r12
            boolean r2 = r0.mGoToFullShadeNeedsAnimation
            if (r2 == 0) goto L_0x02ff
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r4 = 7
            r3.<init>(r10, r4)
            r2.add(r3)
        L_0x02ff:
            r0.mGoToFullShadeNeedsAnimation = r12
            boolean r2 = r0.mNeedViewResizeAnimation
            if (r2 == 0) goto L_0x0332
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            java.util.Iterator r2 = r2.iterator()
        L_0x030b:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0323
            java.lang.Object r3 = r2.next()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r3
            int r3 = r3.animationType
            r4 = 13
            if (r3 == r4) goto L_0x0321
            r4 = 12
            if (r3 != r4) goto L_0x030b
        L_0x0321:
            r2 = 1
            goto L_0x0324
        L_0x0323:
            r2 = r12
        L_0x0324:
            if (r2 != 0) goto L_0x0332
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r4 = 9
            r3.<init>(r10, r4)
            r2.add(r3)
        L_0x0332:
            r0.mNeedViewResizeAnimation = r12
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = r0.mExpandedGroupView
            if (r2 == 0) goto L_0x0348
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = r0.mExpandedGroupView
            r6 = 10
            r3.<init>(r4, r6)
            r2.add(r3)
            r0.mExpandedGroupView = r10
        L_0x0348:
            boolean r2 = r0.mEverythingNeedsAnimation
            if (r2 == 0) goto L_0x0358
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r3 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r4 = 15
            r3.<init>(r10, r4)
            r2.add(r3)
        L_0x0358:
            r0.mEverythingNeedsAnimation = r12
            r0.mNeedsAnimation = r12
        L_0x035c:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r2 = r0.mAnimationEvents
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x037b
            com.android.systemui.statusbar.notification.stack.StackStateAnimator r2 = r0.mStateAnimator
            java.util.Objects.requireNonNull(r2)
            java.util.HashSet<android.animation.Animator> r2 = r2.mAnimatorSet
            boolean r2 = r2.isEmpty()
            r6 = 1
            r2 = r2 ^ r6
            if (r2 == 0) goto L_0x0374
            goto L_0x037c
        L_0x0374:
            r33.applyCurrentState()
        L_0x0377:
            r1 = 0
            goto L_0x08d2
        L_0x037b:
            r6 = 1
        L_0x037c:
            r0.setAnimationRunning(r6)
            com.android.systemui.statusbar.notification.stack.StackStateAnimator r2 = r0.mStateAnimator
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r6 = r0.mAnimationEvents
            long r7 = r0.mGoToFullShadeDelay
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.log.LogLevel r11 = com.android.systemui.log.LogLevel.ERROR
            java.util.Iterator r6 = r6.iterator()
        L_0x038e:
            boolean r13 = r6.hasNext()
            if (r13 == 0) goto L_0x0613
            java.lang.Object r13 = r6.next()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r13 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r13
            com.android.systemui.statusbar.notification.row.ExpandableView r14 = r13.mChangingView
            boolean r15 = r14 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r15 == 0) goto L_0x03b6
            com.android.systemui.statusbar.notification.stack.StackStateLogger r15 = r2.mLogger
            if (r15 == 0) goto L_0x03b6
            r15 = r14
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r15 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r15
            java.util.Objects.requireNonNull(r15)
            boolean r10 = r15.mIsHeadsUp
            com.android.systemui.statusbar.notification.collection.NotificationEntry r15 = r15.mEntry
            java.util.Objects.requireNonNull(r15)
            java.lang.String r15 = r15.mKey
            r28 = 1
            goto L_0x03ba
        L_0x03b6:
            r10 = r12
            r28 = r10
            r15 = 0
        L_0x03ba:
            int r3 = r13.animationType
            java.lang.String r4 = "StackScroll"
            if (r3 != 0) goto L_0x03f8
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r14.mViewState
            if (r3 == 0) goto L_0x03f3
            boolean r5 = r3.gone
            if (r5 == 0) goto L_0x03cc
            goto L_0x03f3
        L_0x03cc:
            if (r28 == 0) goto L_0x03e9
            if (r10 == 0) goto L_0x03e9
            com.android.systemui.statusbar.notification.stack.StackStateLogger r5 = r2.mLogger
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.log.LogBuffer r5 = r5.buffer
            com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearingWithAddEvent$2 r10 = com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearingWithAddEvent$2.INSTANCE
            java.util.Objects.requireNonNull(r5)
            boolean r9 = r5.frozen
            if (r9 != 0) goto L_0x03e9
            com.android.systemui.log.LogMessageImpl r4 = r5.obtain(r4, r11, r10)
            r4.str1 = r15
            r5.push(r4)
        L_0x03e9:
            r3.applyToView(r14)
            java.util.ArrayList<android.view.View> r3 = r2.mNewAddChildren
            r3.add(r14)
            goto L_0x0607
        L_0x03f3:
            r5 = 5
            r9 = 8
            r10 = 0
            goto L_0x038e
        L_0x03f8:
            r5 = 1
            if (r3 != r5) goto L_0x0494
            int r3 = r14.getVisibility()
            if (r3 == 0) goto L_0x0406
            r14.removeFromTransientContainer()
            goto L_0x060c
        L_0x0406:
            android.view.View r3 = r13.viewAfterChangingView
            if (r3 == 0) goto L_0x0454
            float r3 = r14.getTranslationY()
            boolean r9 = r14 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r9 == 0) goto L_0x042d
            android.view.View r9 = r13.viewAfterChangingView
            boolean r12 = r9 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r12 == 0) goto L_0x042d
            r12 = r14
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r12 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r12
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r9 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r9
            boolean r5 = r12.mRemoved
            if (r5 == 0) goto L_0x042d
            boolean r5 = r12.mWasChildInGroupWhenRemoved
            if (r5 == 0) goto L_0x042d
            boolean r5 = r9.isChildInGroup()
            if (r5 != 0) goto L_0x042d
            float r3 = r12.mTranslationWhenRemoved
        L_0x042d:
            int r5 = r14.mActualHeight
            android.view.View r9 = r13.viewAfterChangingView
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = (com.android.systemui.statusbar.notification.row.ExpandableView) r9
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r9 = r9.mViewState
            float r9 = r9.yTranslation
            float r5 = (float) r5
            r12 = 1073741824(0x40000000, float:2.0)
            float r23 = r5 / r12
            float r23 = r23 + r3
            float r9 = r9 - r23
            float r9 = r9 * r12
            float r9 = r9 / r5
            r3 = 1065353216(0x3f800000, float:1.0)
            float r3 = java.lang.Math.min(r9, r3)
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r3 = java.lang.Math.max(r3, r5)
            r27 = r3
            goto L_0x0458
        L_0x0454:
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            r27 = r5
        L_0x0458:
            com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1 r3 = new com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1
            r5 = 2
            r3.<init>(r14, r5)
            if (r28 == 0) goto L_0x0481
            if (r10 == 0) goto L_0x0481
            com.android.systemui.statusbar.notification.stack.StackStateLogger r3 = r2.mLogger
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.log.LogBuffer r3 = r3.buffer
            com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearingWithRemoveEvent$2 r5 = com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearingWithRemoveEvent$2.INSTANCE
            java.util.Objects.requireNonNull(r3)
            boolean r9 = r3.frozen
            if (r9 != 0) goto L_0x047b
            com.android.systemui.log.LogMessageImpl r4 = r3.obtain(r4, r11, r5)
            r4.str1 = r15
            r3.push(r4)
        L_0x047b:
            com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda1 r3 = new com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda1
            r4 = 1
            r3.<init>(r2, r15, r14, r4)
        L_0x0481:
            r30 = r3
            r23 = 464(0x1d0, double:2.29E-321)
            r25 = 0
            r28 = 0
            r29 = 0
            r31 = 0
            r22 = r14
            r22.performRemoveAnimation(r23, r25, r27, r28, r29, r30, r31)
            goto L_0x0607
        L_0x0494:
            r5 = 2
            if (r3 != r5) goto L_0x04a4
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r2.mHostLayout
            boolean r3 = r3.isFullySwipedOut(r14)
            if (r3 == 0) goto L_0x0607
            r14.removeFromTransientContainer()
            goto L_0x0607
        L_0x04a4:
            r5 = 10
            if (r3 != r5) goto L_0x04ba
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = r13.mChangingView
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.mIsSummaryWithChildren
            if (r4 == 0) goto L_0x0607
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r3 = r3.mChildrenContainer
            java.util.Objects.requireNonNull(r3)
            goto L_0x0607
        L_0x04ba:
            r9 = 11
            if (r3 != r9) goto L_0x0506
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r14.mViewState
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r10 = r2.mTmpState
            r10.copyFrom(r3)
            boolean r3 = r13.headsUpFromBottom
            if (r3 == 0) goto L_0x04d4
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r2.mTmpState
            int r10 = r2.mHeadsUpAppearHeightBottom
            float r10 = (float) r10
            r3.yTranslation = r10
            goto L_0x04df
        L_0x04d4:
            r23 = 0
            r25 = 400(0x190, double:1.976E-321)
            r27 = 1
            r22 = r14
            r22.performAddAnimation(r23, r25, r27)
        L_0x04df:
            java.util.HashSet<android.view.View> r3 = r2.mHeadsUpAppearChildren
            r3.add(r14)
            if (r28 == 0) goto L_0x04ff
            com.android.systemui.statusbar.notification.stack.StackStateLogger r3 = r2.mLogger
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.log.LogBuffer r3 = r3.buffer
            com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearing$2 r10 = com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearing$2.INSTANCE
            java.util.Objects.requireNonNull(r3)
            boolean r12 = r3.frozen
            if (r12 != 0) goto L_0x04ff
            com.android.systemui.log.LogMessageImpl r4 = r3.obtain(r4, r1, r10)
            r4.str1 = r15
            r3.push(r4)
        L_0x04ff:
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r2.mTmpState
            r3.applyToView(r14)
            goto L_0x0607
        L_0x0506:
            r10 = 12
            r12 = 13
            if (r3 == r10) goto L_0x050e
            if (r3 != r12) goto L_0x0607
        L_0x050e:
            java.util.HashSet<android.view.View> r3 = r2.mHeadsUpDisappearChildren
            r3.add(r14)
            android.view.ViewParent r3 = r14.getParent()
            if (r3 != 0) goto L_0x0530
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r2.mHostLayout
            r5 = 0
            r3.addTransientView(r14, r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r2.mHostLayout
            r14.mTransientContainer = r3
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r2.mTmpState
            r3.initFrom(r14)
            com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1 r3 = new com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1
            r5 = 8
            r3.<init>(r14, r5)
            goto L_0x0531
        L_0x0530:
            r3 = 0
        L_0x0531:
            boolean r5 = r14 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r5 == 0) goto L_0x05a8
            r5 = r14
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r5 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r5
            boolean r9 = r5.mDismissed
            r17 = 1
            r9 = r9 ^ 1
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.mEntry
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.icon.IconPack r10 = r5.mIcons
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.StatusBarIconView r10 = r10.mStatusBarIcon
            com.android.systemui.statusbar.notification.icon.IconPack r5 = r5.mIcons
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.StatusBarIconView r5 = r5.mCenteredIcon
            if (r5 == 0) goto L_0x055a
            android.view.ViewParent r20 = r5.getParent()
            if (r20 == 0) goto L_0x055a
            r10 = r5
        L_0x055a:
            android.view.ViewParent r5 = r10.getParent()
            if (r5 == 0) goto L_0x05a9
            int[] r5 = r2.mTmpLocation
            r10.getLocationOnScreen(r5)
            int[] r5 = r2.mTmpLocation
            r20 = 0
            r5 = r5[r20]
            float r5 = (float) r5
            float r20 = r10.getTranslationX()
            float r5 = r5 - r20
            int r12 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_X
            java.lang.Object r12 = r10.getTag(r12)
            android.animation.ValueAnimator r12 = (android.animation.ValueAnimator) r12
            if (r12 != 0) goto L_0x0581
            float r12 = r10.getTranslationX()
            goto L_0x058d
        L_0x0581:
            int r12 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_END_TRANSLATION_X
            java.lang.Object r12 = r10.getTag(r12)
            java.lang.Float r12 = (java.lang.Float) r12
            float r12 = r12.floatValue()
        L_0x058d:
            float r5 = r5 + r12
            int r10 = r10.getWidth()
            float r10 = (float) r10
            r12 = 1048576000(0x3e800000, float:0.25)
            float r10 = r10 * r12
            float r10 = r10 + r5
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r2.mHostLayout
            int[] r12 = r2.mTmpLocation
            r5.getLocationOnScreen(r12)
            int[] r5 = r2.mTmpLocation
            r12 = 0
            r5 = r5[r12]
            float r5 = (float) r5
            float r10 = r10 - r5
            r29 = r10
            goto L_0x05ac
        L_0x05a8:
            r9 = 1
        L_0x05a9:
            r5 = 0
            r29 = r5
        L_0x05ac:
            if (r9 == 0) goto L_0x0602
            if (r28 == 0) goto L_0x05d2
            com.android.systemui.statusbar.notification.stack.StackStateLogger r5 = r2.mLogger
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.log.LogBuffer r5 = r5.buffer
            com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearing$2 r9 = com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearing$2.INSTANCE
            java.util.Objects.requireNonNull(r5)
            boolean r10 = r5.frozen
            if (r10 != 0) goto L_0x05c9
            com.android.systemui.log.LogMessageImpl r4 = r5.obtain(r4, r1, r9)
            r4.str1 = r15
            r5.push(r4)
        L_0x05c9:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0 r4 = new com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
            r5 = 0
            r4.<init>(r2, r15, r3, r5)
            r30 = r4
            goto L_0x05d4
        L_0x05d2:
            r30 = r3
        L_0x05d4:
            r23 = 400(0x190, double:1.976E-321)
            r25 = 0
            r27 = 0
            r28 = 1
            java.util.Stack<android.animation.AnimatorListenerAdapter> r3 = r2.mAnimationListenerPool
            boolean r3 = r3.empty()
            if (r3 != 0) goto L_0x05ed
            java.util.Stack<android.animation.AnimatorListenerAdapter> r3 = r2.mAnimationListenerPool
            java.lang.Object r3 = r3.pop()
            android.animation.AnimatorListenerAdapter r3 = (android.animation.AnimatorListenerAdapter) r3
            goto L_0x05f2
        L_0x05ed:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$2 r3 = new com.android.systemui.statusbar.notification.stack.StackStateAnimator$2
            r3.<init>()
        L_0x05f2:
            r31 = r3
            r22 = r14
            long r3 = r22.performRemoveAnimation(r23, r25, r27, r28, r29, r30, r31)
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r5 = r2.mAnimationProperties
            long r9 = r5.delay
            long r9 = r9 + r3
            r5.delay = r9
            goto L_0x0607
        L_0x0602:
            if (r3 == 0) goto L_0x0607
            r3.run()
        L_0x0607:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r3 = r2.mNewEvents
            r3.add(r13)
        L_0x060c:
            r5 = 5
            r9 = 8
            r10 = 0
            r12 = 0
            goto L_0x038e
        L_0x0613:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r2.mHostLayout
            int r1 = r1.getChildCount()
            com.android.systemui.statusbar.notification.stack.AnimationFilter r3 = r2.mAnimationFilter
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r4 = r2.mNewEvents
            java.util.Objects.requireNonNull(r3)
            r3.reset()
            int r5 = r4.size()
            r6 = 0
        L_0x0628:
            if (r6 >= r5) goto L_0x0646
            java.lang.Object r9 = r4.get(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r9 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r9
            java.lang.Object r10 = r4.get(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r10 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r10
            com.android.systemui.statusbar.notification.stack.AnimationFilter r10 = r10.filter
            r3.combineFilter(r10)
            int r9 = r9.animationType
            r10 = 7
            if (r9 != r10) goto L_0x0643
            r9 = 1
            r3.hasGoToFullShadeEvent = r9
        L_0x0643:
            int r6 = r6 + 1
            goto L_0x0628
        L_0x0646:
            r2.mCurrentAdditionalDelay = r7
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r3 = r2.mNewEvents
            com.android.systemui.statusbar.notification.stack.AnimationFilter[] r4 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent.FILTERS
            int r4 = r3.size()
            r5 = 0
            r7 = 0
        L_0x0653:
            if (r7 >= r4) goto L_0x066c
            java.lang.Object r8 = r3.get(r7)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r8 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r8
            long r9 = r8.length
            long r5 = java.lang.Math.max(r5, r9)
            int r9 = r8.animationType
            r10 = 7
            if (r9 != r10) goto L_0x0669
            long r5 = r8.length
            goto L_0x066c
        L_0x0669:
            int r7 = r7 + 1
            goto L_0x0653
        L_0x066c:
            r2.mCurrentLength = r5
            r3 = 0
            r5 = 0
        L_0x0670:
            if (r5 >= r1) goto L_0x08a4
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r2.mHostLayout
            android.view.View r4 = r4.getChildAt(r5)
            com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r6 = r4.mViewState
            if (r6 == 0) goto L_0x089a
            int r7 = r4.getVisibility()
            r8 = 8
            if (r7 == r8) goto L_0x089a
            boolean r7 = r2.mShadeExpanded
            if (r7 == 0) goto L_0x068e
            goto L_0x06b9
        L_0x068e:
            int r7 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Y
            java.lang.Object r7 = r4.getTag(r7)
            if (r7 == 0) goto L_0x0698
            r7 = 1
            goto L_0x0699
        L_0x0698:
            r7 = 0
        L_0x0699:
            if (r7 == 0) goto L_0x069c
            goto L_0x06b9
        L_0x069c:
            java.util.HashSet<android.view.View> r7 = r2.mHeadsUpDisappearChildren
            boolean r7 = r7.contains(r4)
            if (r7 != 0) goto L_0x06b9
            java.util.HashSet<android.view.View> r7 = r2.mHeadsUpAppearChildren
            boolean r7 = r7.contains(r4)
            if (r7 == 0) goto L_0x06ad
            goto L_0x06b9
        L_0x06ad:
            boolean r7 = isPinnedHeadsUp(r4)
            if (r7 == 0) goto L_0x06b4
            goto L_0x06b9
        L_0x06b4:
            r6.applyToView(r4)
            r7 = 1
            goto L_0x06ba
        L_0x06b9:
            r7 = 0
        L_0x06ba:
            if (r7 == 0) goto L_0x06be
            goto L_0x089a
        L_0x06be:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r7 = r2.mAnimationProperties
            boolean r7 = r7.wasAdded(r4)
            if (r7 == 0) goto L_0x06cc
            r7 = 5
            if (r3 >= r7) goto L_0x06cd
            int r3 = r3 + 1
            goto L_0x06cd
        L_0x06cc:
            r7 = 5
        L_0x06cd:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r8 = r2.mAnimationProperties
            boolean r8 = r8.wasAdded(r4)
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r9 = r2.mAnimationProperties
            long r10 = r2.mCurrentLength
            r9.duration = r10
            boolean r9 = r4 instanceof com.android.systemui.statusbar.notification.row.StackScrollerDecorView
            if (r8 != 0) goto L_0x06e2
            if (r9 == 0) goto L_0x06e0
            goto L_0x06e2
        L_0x06e0:
            r10 = 0
            goto L_0x06e3
        L_0x06e2:
            r10 = 1
        L_0x06e3:
            r11 = 4604480258916220928(0x3fe6666660000000, double:0.699999988079071)
            if (r10 == 0) goto L_0x070f
            com.android.systemui.statusbar.notification.stack.AnimationFilter r10 = r2.mAnimationFilter
            boolean r10 = r10.hasGoToFullShadeEvent
            if (r10 == 0) goto L_0x070f
            if (r9 != 0) goto L_0x0707
            int r9 = r2.mGoToFullShadeAppearingTranslation
            double r13 = (double) r3
            double r13 = java.lang.Math.pow(r13, r11)
            float r10 = (float) r13
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r13 = r2.mAnimationProperties
            r14 = 514(0x202, double:2.54E-321)
            r16 = 1120403456(0x42c80000, float:100.0)
            float r10 = r10 * r16
            long r11 = (long) r10
            long r11 = r11 + r14
            r13.duration = r11
            goto L_0x0708
        L_0x0707:
            r9 = 0
        L_0x0708:
            float r10 = r6.yTranslation
            float r9 = (float) r9
            float r10 = r10 + r9
            r4.setTranslationY(r10)
        L_0x070f:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r9 = r2.mAnimationProperties
            r10 = 0
            r9.delay = r10
            if (r8 != 0) goto L_0x0750
            com.android.systemui.statusbar.notification.stack.AnimationFilter r8 = r2.mAnimationFilter
            boolean r8 = r8.hasDelays
            if (r8 == 0) goto L_0x0748
            float r8 = r6.yTranslation
            float r9 = r4.getTranslationY()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0750
            float r8 = r6.zTranslation
            float r9 = r4.getTranslationZ()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0750
            float r8 = r6.alpha
            float r9 = r4.getAlpha()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0750
            int r8 = r6.height
            int r9 = r4.mActualHeight
            if (r8 != r9) goto L_0x0750
            int r8 = r6.clipTopAmount
            int r9 = r4.mClipTopAmount
            if (r8 == r9) goto L_0x0748
            goto L_0x0750
        L_0x0748:
            r21 = r1
            r16 = r3
            r7 = 2
            r8 = 0
            goto L_0x0892
        L_0x0750:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r8 = r2.mAnimationProperties
            long r9 = r2.mCurrentAdditionalDelay
            com.android.systemui.statusbar.notification.stack.AnimationFilter r11 = r2.mAnimationFilter
            boolean r12 = r11.hasGoToFullShadeEvent
            if (r12 == 0) goto L_0x079c
            com.android.systemui.statusbar.NotificationShelf r11 = r2.mShelf
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mNotGoneIndex
            int r12 = r6.notGoneIndex
            float r12 = (float) r12
            float r11 = (float) r11
            int r13 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            r14 = 1111490560(0x42400000, float:48.0)
            if (r13 <= 0) goto L_0x0785
            double r12 = (double) r3
            r15 = r8
            r7 = 4604480258916220928(0x3fe6666660000000, double:0.699999988079071)
            double r12 = java.lang.Math.pow(r12, r7)
            float r12 = (float) r12
            float r12 = r12 * r14
            double r12 = (double) r12
            r18 = 4598175219545276416(0x3fd0000000000000, double:0.25)
            double r12 = r12 * r18
            long r12 = (long) r12
            r18 = 0
            long r12 = r12 + r18
            r18 = r15
            goto L_0x0790
        L_0x0785:
            r15 = r8
            r7 = 4604480258916220928(0x3fe6666660000000, double:0.699999988079071)
            r11 = r12
            r18 = r15
            r12 = 0
        L_0x0790:
            double r14 = (double) r11
            double r7 = java.lang.Math.pow(r14, r7)
            float r7 = (float) r7
            r8 = 1111490560(0x42400000, float:48.0)
            float r7 = r7 * r8
            long r7 = (long) r7
            long r12 = r12 + r7
            goto L_0x07a6
        L_0x079c:
            r18 = r8
            long r12 = r11.customDelay
            r7 = -1
            int r7 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x07ae
        L_0x07a6:
            r21 = r1
            r16 = r3
            r7 = 2
            r8 = 0
            goto L_0x088d
        L_0x07ae:
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r7 = r2.mNewEvents
            java.util.Iterator r7 = r7.iterator()
            r11 = 0
        L_0x07b6:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0886
            java.lang.Object r8 = r7.next()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r8 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnimationEvent) r8
            int r13 = r8.animationType
            if (r13 == 0) goto L_0x0851
            r14 = 1
            if (r13 == r14) goto L_0x07d9
            r14 = 2
            if (r13 == r14) goto L_0x07d6
            r21 = r1
            r16 = r3
            r22 = r7
            r7 = 8
            goto L_0x084e
        L_0x07d6:
            r14 = 32
            goto L_0x07db
        L_0x07d9:
            r14 = 80
        L_0x07db:
            int r13 = r6.notGoneIndex
            android.view.View r8 = r8.viewAfterChangingView
            if (r8 != 0) goto L_0x07e4
            r16 = 1
            goto L_0x07e6
        L_0x07e4:
            r16 = 0
        L_0x07e6:
            if (r16 == 0) goto L_0x0821
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r2.mHostLayout
            java.util.Objects.requireNonNull(r8)
            int r16 = r8.getChildCount()
        L_0x07f1:
            r21 = r1
            int r1 = r16 + -1
            if (r1 < 0) goto L_0x0819
            r16 = r3
            android.view.View r3 = r8.getChildAt(r1)
            r19 = r1
            int r1 = r3.getVisibility()
            r22 = r7
            r7 = 8
            if (r1 == r7) goto L_0x0810
            com.android.systemui.statusbar.NotificationShelf r1 = r8.mShelf
            if (r3 == r1) goto L_0x0810
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
            goto L_0x082c
        L_0x0810:
            r3 = r16
            r16 = r19
            r1 = r21
            r7 = r22
            goto L_0x07f1
        L_0x0819:
            r16 = r3
            r22 = r7
            r7 = 8
            r3 = 0
            goto L_0x082c
        L_0x0821:
            r21 = r1
            r16 = r3
            r22 = r7
            r7 = 8
            r3 = r8
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = (com.android.systemui.statusbar.notification.row.ExpandableView) r3
        L_0x082c:
            if (r3 != 0) goto L_0x082f
            goto L_0x084e
        L_0x082f:
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r1 = r3.mViewState
            int r1 = r1.notGoneIndex
            if (r13 < r1) goto L_0x0837
            int r13 = r13 + 1
        L_0x0837:
            int r13 = r13 - r1
            int r1 = java.lang.Math.abs(r13)
            r3 = 1
            int r1 = r1 - r3
            r3 = 2
            int r1 = java.lang.Math.min(r3, r1)
            r3 = 0
            int r1 = java.lang.Math.max(r3, r1)
            long r7 = (long) r1
            long r7 = r7 * r14
            long r11 = java.lang.Math.max(r7, r11)
        L_0x084e:
            r7 = 2
            r8 = 0
            goto L_0x087e
        L_0x0851:
            r21 = r1
            r16 = r3
            r22 = r7
            int r1 = r6.notGoneIndex
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = r8.mChangingView
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r3 = r3.mViewState
            int r3 = r3.notGoneIndex
            int r1 = r1 - r3
            int r1 = java.lang.Math.abs(r1)
            r3 = 1
            int r1 = r1 - r3
            r7 = 2
            int r1 = java.lang.Math.min(r7, r1)
            r8 = 0
            int r1 = java.lang.Math.max(r8, r1)
            int r1 = 2 - r1
            long r13 = (long) r1
            r19 = 80
            long r13 = r13 * r19
            long r11 = java.lang.Math.max(r13, r11)
        L_0x087e:
            r3 = r16
            r1 = r21
            r7 = r22
            goto L_0x07b6
        L_0x0886:
            r21 = r1
            r16 = r3
            r7 = 2
            r8 = 0
            r12 = r11
        L_0x088d:
            long r9 = r9 + r12
            r1 = r18
            r1.delay = r9
        L_0x0892:
            com.android.systemui.statusbar.notification.stack.StackStateAnimator$1 r1 = r2.mAnimationProperties
            r6.animateTo(r4, r1)
            r3 = r16
            goto L_0x089e
        L_0x089a:
            r21 = r1
            r7 = 2
            r8 = 0
        L_0x089e:
            int r5 = r5 + 1
            r1 = r21
            goto L_0x0670
        L_0x08a4:
            java.util.HashSet<android.animation.Animator> r1 = r2.mAnimatorSet
            boolean r1 = r1.isEmpty()
            r3 = 1
            r1 = r1 ^ r3
            if (r1 != 0) goto L_0x08b1
            r2.onAnimationFinished()
        L_0x08b1:
            java.util.HashSet<android.view.View> r1 = r2.mHeadsUpAppearChildren
            r1.clear()
            java.util.HashSet<android.view.View> r1 = r2.mHeadsUpDisappearChildren
            r1.clear()
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r1 = r2.mNewEvents
            r1.clear()
            java.util.ArrayList<android.view.View> r1 = r2.mNewAddChildren
            r1.clear()
            java.util.ArrayList<com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent> r1 = r0.mAnimationEvents
            r1.clear()
            r33.updateBackground()
            r33.updateViewShadows()
            goto L_0x0377
        L_0x08d2:
            r0.mGoToFullShadeDelay = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.startAnimationToState$1():void");
    }

    public final void updateAlgorithmHeightAndPadding() {
        AmbientState ambientState = this.mAmbientState;
        int min = Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
        Objects.requireNonNull(ambientState);
        ambientState.mLayoutHeight = min;
        AmbientState ambientState2 = this.mAmbientState;
        int i = this.mMaxLayoutHeight;
        Objects.requireNonNull(ambientState2);
        ambientState2.mLayoutMaxHeight = i;
        updateAlgorithmLayoutMinHeight();
        AmbientState ambientState3 = this.mAmbientState;
        int i2 = this.mTopPadding;
        Objects.requireNonNull(ambientState3);
        ambientState3.mTopPadding = i2;
    }

    public final void updateAlgorithmLayoutMinHeight() {
        int i;
        AmbientState ambientState = this.mAmbientState;
        if (this.mQsExpanded || isHeadsUpTransition()) {
            i = getLayoutMinHeight();
        } else {
            i = 0;
        }
        Objects.requireNonNull(ambientState);
        ambientState.mLayoutMinHeight = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0225  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00fc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateBackground() {
        /*
            r17 = this;
            r0 = r17
            boolean r1 = r0.mShouldDrawNotificationBackground
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            int r1 = r0.mSidePaddings
            int r2 = r17.getWidth()
            int r3 = r0.mSidePaddings
            int r2 = r2 - r3
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r3 = r0.mSections
            int r4 = r3.length
            r5 = 0
            r6 = r5
        L_0x0015:
            if (r6 >= r4) goto L_0x0025
            r7 = r3[r6]
            java.util.Objects.requireNonNull(r7)
            android.graphics.Rect r7 = r7.mBounds
            r7.left = r1
            r7.right = r2
            int r6 = r6 + 1
            goto L_0x0015
        L_0x0025:
            boolean r1 = r0.mIsExpanded
            r2 = 1
            if (r1 != 0) goto L_0x003e
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r1 = r0.mSections
            int r3 = r1.length
            r4 = r5
        L_0x002e:
            if (r4 >= r3) goto L_0x00a3
            r6 = r1[r4]
            java.util.Objects.requireNonNull(r6)
            android.graphics.Rect r6 = r6.mBounds
            r6.top = r5
            r6.bottom = r5
            int r4 = r4 + 1
            goto L_0x002e
        L_0x003e:
            com.android.systemui.statusbar.notification.stack.NotificationSection r1 = r17.getLastVisibleSection()
            int r3 = r0.mStatusBarState
            if (r3 != r2) goto L_0x0048
            r3 = r2
            goto L_0x0049
        L_0x0048:
            r3 = r5
        L_0x0049:
            if (r3 != 0) goto L_0x0053
            int r4 = r0.mTopPadding
            float r4 = (float) r4
            float r6 = r0.mStackTranslation
            float r4 = r4 + r6
            int r4 = (int) r4
            goto L_0x0063
        L_0x0053:
            if (r1 != 0) goto L_0x0058
            int r4 = r0.mTopPadding
            goto L_0x0063
        L_0x0058:
            com.android.systemui.statusbar.notification.stack.NotificationSection r4 = r17.getFirstVisibleSection()
            r4.updateBounds(r5, r5, r5)
            android.graphics.Rect r4 = r4.mBounds
            int r4 = r4.top
        L_0x0063:
            long r6 = r0.mNumHeadsUp
            r8 = 1
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 > 0) goto L_0x007c
            com.android.systemui.statusbar.notification.stack.AmbientState r6 = r0.mAmbientState
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mDozing
            if (r6 != 0) goto L_0x007a
            boolean r6 = r0.mKeyguardBypassEnabled
            if (r6 == 0) goto L_0x007c
            if (r3 == 0) goto L_0x007c
        L_0x007a:
            r3 = r2
            goto L_0x007d
        L_0x007c:
            r3 = r5
        L_0x007d:
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r6 = r0.mSections
            int r7 = r6.length
            r8 = r5
        L_0x0081:
            if (r8 >= r7) goto L_0x00a3
            r9 = r6[r8]
            if (r9 != r1) goto L_0x009a
            com.android.systemui.statusbar.NotificationShelf r10 = r0.mShelf
            float r10 = com.android.systemui.statusbar.notification.stack.ViewState.getFinalTranslationY(r10)
            com.android.systemui.statusbar.NotificationShelf r11 = r0.mShelf
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.getHeight()
            float r11 = (float) r11
            float r10 = r10 + r11
            int r10 = (int) r10
            goto L_0x009b
        L_0x009a:
            r10 = r4
        L_0x009b:
            int r4 = r9.updateBounds(r4, r10, r3)
            int r8 = r8 + 1
            r3 = r5
            goto L_0x0081
        L_0x00a3:
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r1 = r0.mSections
            int r3 = r1.length
            r4 = r5
        L_0x00a7:
            if (r4 >= r3) goto L_0x00be
            r6 = r1[r4]
            java.util.Objects.requireNonNull(r6)
            android.graphics.Rect r7 = r6.mCurrentBounds
            android.graphics.Rect r6 = r6.mBounds
            boolean r6 = r7.equals(r6)
            r6 = r6 ^ r2
            if (r6 == 0) goto L_0x00bb
            r1 = r2
            goto L_0x00bf
        L_0x00bb:
            int r4 = r4 + 1
            goto L_0x00a7
        L_0x00be:
            r1 = r5
        L_0x00bf:
            if (r1 == 0) goto L_0x023e
            boolean r1 = r0.mAnimateNextSectionBoundsChange
            if (r1 != 0) goto L_0x00f1
            boolean r1 = r0.mAnimateNextBackgroundTop
            if (r1 != 0) goto L_0x00f1
            boolean r1 = r0.mAnimateNextBackgroundBottom
            if (r1 != 0) goto L_0x00f1
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r1 = r0.mSections
            int r3 = r1.length
            r4 = r5
        L_0x00d1:
            if (r4 >= r3) goto L_0x00eb
            r6 = r1[r4]
            java.util.Objects.requireNonNull(r6)
            android.animation.ObjectAnimator r7 = r6.mBottomAnimator
            if (r7 != 0) goto L_0x00e3
            android.animation.ObjectAnimator r6 = r6.mTopAnimator
            if (r6 == 0) goto L_0x00e1
            goto L_0x00e3
        L_0x00e1:
            r6 = r5
            goto L_0x00e4
        L_0x00e3:
            r6 = r2
        L_0x00e4:
            if (r6 == 0) goto L_0x00e8
            r1 = r2
            goto L_0x00ec
        L_0x00e8:
            int r4 = r4 + 1
            goto L_0x00d1
        L_0x00eb:
            r1 = r5
        L_0x00ec:
            if (r1 == 0) goto L_0x00ef
            goto L_0x00f1
        L_0x00ef:
            r1 = r5
            goto L_0x00f2
        L_0x00f1:
            r1 = r2
        L_0x00f2:
            boolean r3 = r0.mIsExpanded
            if (r3 != 0) goto L_0x00fa
            r17.abortBackgroundAnimators()
            r1 = r5
        L_0x00fa:
            if (r1 == 0) goto L_0x0225
            com.android.systemui.statusbar.notification.stack.NotificationSection r1 = r17.getFirstVisibleSection()
            com.android.systemui.statusbar.notification.stack.NotificationSection r3 = r17.getLastVisibleSection()
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r4 = r0.mSections
            int r6 = r4.length
            r7 = r5
        L_0x0108:
            if (r7 >= r6) goto L_0x0241
            r8 = r4[r7]
            if (r8 != r1) goto L_0x0111
            boolean r9 = r0.mAnimateNextBackgroundTop
            goto L_0x0113
        L_0x0111:
            boolean r9 = r0.mAnimateNextSectionBoundsChange
        L_0x0113:
            if (r8 != r3) goto L_0x0118
            boolean r10 = r0.mAnimateNextBackgroundBottom
            goto L_0x011a
        L_0x0118:
            boolean r10 = r0.mAnimateNextSectionBoundsChange
        L_0x011a:
            java.util.Objects.requireNonNull(r8)
            android.graphics.Rect r11 = r8.mCurrentBounds
            android.graphics.Rect r12 = r8.mBounds
            int r13 = r12.left
            r11.left = r13
            int r13 = r12.right
            r11.right = r13
            android.graphics.Rect r13 = r8.mStartAnimationRect
            int r13 = r13.bottom
            android.graphics.Rect r14 = r8.mEndAnimationRect
            int r14 = r14.bottom
            int r12 = r12.bottom
            android.animation.ObjectAnimator r15 = r8.mBottomAnimator
            r16 = r3
            r2 = 360(0x168, double:1.78E-321)
            if (r15 == 0) goto L_0x013e
            if (r14 != r12) goto L_0x013e
            goto L_0x01a3
        L_0x013e:
            if (r10 != 0) goto L_0x016b
            if (r15 == 0) goto L_0x0163
            android.animation.PropertyValuesHolder[] r10 = r15.getValues()
            r10 = r10[r5]
            r11 = 2
            int[] r14 = new int[r11]
            r14[r5] = r13
            r11 = 1
            r14[r11] = r12
            r10.setIntValues(r14)
            android.graphics.Rect r10 = r8.mStartAnimationRect
            r10.bottom = r13
            android.graphics.Rect r10 = r8.mEndAnimationRect
            r10.bottom = r12
            long r10 = r15.getCurrentPlayTime()
            r15.setCurrentPlayTime(r10)
            goto L_0x01a3
        L_0x0163:
            r11.bottom = r12
            android.view.View r10 = r8.mOwningView
            r10.invalidate()
            goto L_0x01a3
        L_0x016b:
            if (r15 == 0) goto L_0x0170
            r15.cancel()
        L_0x0170:
            r10 = 2
            int[] r11 = new int[r10]
            android.graphics.Rect r10 = r8.mCurrentBounds
            int r10 = r10.bottom
            r11[r5] = r10
            r10 = 1
            r11[r10] = r12
            java.lang.String r10 = "backgroundBottom"
            android.animation.ObjectAnimator r10 = android.animation.ObjectAnimator.ofInt(r8, r10, r11)
            android.view.animation.PathInterpolator r11 = com.android.systemui.animation.Interpolators.FAST_OUT_SLOW_IN
            r10.setInterpolator(r11)
            r10.setDuration(r2)
            com.android.systemui.statusbar.notification.stack.NotificationSection$2 r11 = new com.android.systemui.statusbar.notification.stack.NotificationSection$2
            r11.<init>()
            r10.addListener(r11)
            r10.start()
            android.graphics.Rect r11 = r8.mStartAnimationRect
            android.graphics.Rect r13 = r8.mCurrentBounds
            int r13 = r13.bottom
            r11.bottom = r13
            android.graphics.Rect r11 = r8.mEndAnimationRect
            r11.bottom = r12
            r8.mBottomAnimator = r10
        L_0x01a3:
            android.graphics.Rect r10 = r8.mEndAnimationRect
            int r10 = r10.top
            android.graphics.Rect r11 = r8.mBounds
            int r11 = r11.top
            android.animation.ObjectAnimator r12 = r8.mTopAnimator
            if (r12 == 0) goto L_0x01b2
            if (r10 != r11) goto L_0x01b2
            goto L_0x01e4
        L_0x01b2:
            if (r9 != 0) goto L_0x01e6
            if (r12 == 0) goto L_0x01db
            android.graphics.Rect r2 = r8.mStartAnimationRect
            int r2 = r2.top
            android.animation.PropertyValuesHolder[] r3 = r12.getValues()
            r3 = r3[r5]
            r9 = 2
            int[] r9 = new int[r9]
            r9[r5] = r2
            r10 = 1
            r9[r10] = r11
            r3.setIntValues(r9)
            android.graphics.Rect r3 = r8.mStartAnimationRect
            r3.top = r2
            android.graphics.Rect r2 = r8.mEndAnimationRect
            r2.top = r11
            long r2 = r12.getCurrentPlayTime()
            r12.setCurrentPlayTime(r2)
            goto L_0x01e4
        L_0x01db:
            android.graphics.Rect r2 = r8.mCurrentBounds
            r2.top = r11
            android.view.View r2 = r8.mOwningView
            r2.invalidate()
        L_0x01e4:
            r10 = 1
            goto L_0x021e
        L_0x01e6:
            if (r12 == 0) goto L_0x01eb
            r12.cancel()
        L_0x01eb:
            r9 = 2
            int[] r9 = new int[r9]
            android.graphics.Rect r10 = r8.mCurrentBounds
            int r10 = r10.top
            r9[r5] = r10
            r10 = 1
            r9[r10] = r11
            java.lang.String r12 = "backgroundTop"
            android.animation.ObjectAnimator r9 = android.animation.ObjectAnimator.ofInt(r8, r12, r9)
            android.view.animation.PathInterpolator r12 = com.android.systemui.animation.Interpolators.FAST_OUT_SLOW_IN
            r9.setInterpolator(r12)
            r9.setDuration(r2)
            com.android.systemui.statusbar.notification.stack.NotificationSection$1 r2 = new com.android.systemui.statusbar.notification.stack.NotificationSection$1
            r2.<init>()
            r9.addListener(r2)
            r9.start()
            android.graphics.Rect r2 = r8.mStartAnimationRect
            android.graphics.Rect r3 = r8.mCurrentBounds
            int r3 = r3.top
            r2.top = r3
            android.graphics.Rect r2 = r8.mEndAnimationRect
            r2.top = r11
            r8.mTopAnimator = r9
        L_0x021e:
            int r7 = r7 + 1
            r2 = r10
            r3 = r16
            goto L_0x0108
        L_0x0225:
            com.android.systemui.statusbar.notification.stack.NotificationSection[] r1 = r0.mSections
            int r2 = r1.length
            r3 = r5
        L_0x0229:
            if (r3 >= r2) goto L_0x023a
            r4 = r1[r3]
            java.util.Objects.requireNonNull(r4)
            android.graphics.Rect r6 = r4.mCurrentBounds
            android.graphics.Rect r4 = r4.mBounds
            r6.set(r4)
            int r3 = r3 + 1
            goto L_0x0229
        L_0x023a:
            r17.invalidate()
            goto L_0x0241
        L_0x023e:
            r17.abortBackgroundAnimators()
        L_0x0241:
            r0.mAnimateNextBackgroundTop = r5
            r0.mAnimateNextBackgroundBottom = r5
            r0.mAnimateNextSectionBoundsChange = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateBackground():void");
    }

    public final void updateBackgroundDimming() {
        int blendARGB;
        if (this.mShouldDrawNotificationBackground && this.mCachedBackgroundColor != (blendARGB = ColorUtils.blendARGB(this.mBgColor, -1, MathUtils.smoothStep(0.4f, 1.0f, this.mLinearHideAmount)))) {
            this.mCachedBackgroundColor = blendARGB;
            this.mBackgroundPaint.setColor(blendARGB);
            invalidate();
        }
    }

    public final void updateBgColor() {
        this.mBgColor = com.android.settingslib.Utils.getColorAttr(this.mContext, 16844002).getDefaultColor();
        updateBackgroundDimming();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActivatableNotificationView) {
                ((ActivatableNotificationView) childAt).updateBackgroundColors();
            }
        }
    }

    public final void updateClipping() {
        boolean z;
        if (this.mRequestedClipBounds == null || this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) {
            z = false;
        } else {
            z = true;
        }
        if (this.mIsClipped != z) {
            this.mIsClipped = z;
        }
        if (this.mAmbientState.isHiddenAtAll()) {
            invalidateOutline();
            if (this.mAmbientState.isFullyHidden()) {
                setClipBounds((Rect) null);
            }
        } else if (z) {
            setClipBounds(this.mRequestedClipBounds);
        } else {
            setClipBounds((Rect) null);
        }
        setClipToOutline(false);
    }

    public final void updateContentHeight() {
        float f;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        float f2;
        float f3;
        if (this.mAmbientState.isOnKeyguard()) {
            f = 0.0f;
        } else {
            f = (float) this.mMinimumPaddings;
        }
        int i = (int) f;
        int i2 = this.mMaxDisplayedNotifications;
        ExpandableView expandableView = null;
        int i3 = 0;
        int i4 = 0;
        boolean z5 = false;
        while (true) {
            z = true;
            if (i3 >= getChildCount()) {
                break;
            }
            ExpandableView expandableView2 = (ExpandableView) getChildAt(i3);
            if (expandableView2 != this.mFooterView || !onKeyguard()) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (expandableView2.getVisibility() != 8 && !(expandableView2 instanceof NotificationShelf) && !z2) {
                if (i2 == -1 || i4 < i2) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z3) {
                    NotificationShelf notificationShelf = this.mShelf;
                    Objects.requireNonNull(notificationShelf);
                    f2 = (float) notificationShelf.getHeight();
                    z4 = true;
                } else {
                    z4 = z5;
                    f2 = (float) expandableView2.getIntrinsicHeight();
                }
                if (i != 0) {
                    i += this.mPaddingBetweenElements;
                }
                StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
                NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
                Objects.requireNonNull(stackScrollAlgorithm);
                if (StackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, i4, expandableView2, expandableView)) {
                    f3 = (float) stackScrollAlgorithm.mGapHeight;
                } else {
                    f3 = 0.0f;
                }
                i = (int) (((float) ((int) (((float) i) + f3))) + f2);
                if (f2 > 0.0f || !(expandableView2 instanceof MediaContainerView)) {
                    i4++;
                }
                if (z4) {
                    break;
                }
                expandableView = expandableView2;
                z5 = z4;
            }
            i3++;
        }
        this.mIntrinsicContentHeight = i;
        this.mContentHeight = Math.max(this.mIntrinsicPadding, this.mTopPadding) + i + this.mBottomPadding;
        if (this.mQsExpanded || getScrollRange() <= 0) {
            z = false;
        }
        if (z != this.mScrollable) {
            this.mScrollable = z;
            setFocusable(z);
            updateForwardAndBackwardScrollability();
        }
        clampScrollPosition();
        updateStackPosition(false);
        AmbientState ambientState = this.mAmbientState;
        int i5 = this.mContentHeight;
        Objects.requireNonNull(ambientState);
        ambientState.mContentHeight = i5;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        if (r0.mIsSwiping != false) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateContinuousBackgroundDrawing() {
        /*
            r3 = this;
            com.android.systemui.statusbar.notification.stack.AmbientState r0 = r3.mAmbientState
            java.util.Objects.requireNonNull(r0)
            float r0 = r0.mDozeAmount
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0010
            r0 = r1
            goto L_0x0011
        L_0x0010:
            r0 = r2
        L_0x0011:
            if (r0 != 0) goto L_0x001d
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r0 = r3.mSwipeHelper
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsSwiping
            if (r0 == 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r1 = r2
        L_0x001e:
            boolean r0 = r3.mContinuousBackgroundUpdate
            if (r1 == r0) goto L_0x0039
            r3.mContinuousBackgroundUpdate = r1
            if (r1 == 0) goto L_0x0030
            android.view.ViewTreeObserver r0 = r3.getViewTreeObserver()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1 r3 = r3.mBackgroundUpdater
            r0.addOnPreDrawListener(r3)
            goto L_0x0039
        L_0x0030:
            android.view.ViewTreeObserver r0 = r3.getViewTreeObserver()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1 r3 = r3.mBackgroundUpdater
            r0.removeOnPreDrawListener(r3)
        L_0x0039:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateContinuousBackgroundDrawing():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0015  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateContinuousShadowDrawing() {
        /*
            r3 = this;
            boolean r0 = r3.mAnimationRunning
            if (r0 != 0) goto L_0x0010
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r0 = r3.mSwipeHelper
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsSwiping
            if (r0 == 0) goto L_0x000e
            goto L_0x0010
        L_0x000e:
            r0 = 0
            goto L_0x0011
        L_0x0010:
            r0 = 1
        L_0x0011:
            boolean r1 = r3.mContinuousShadowUpdate
            if (r0 == r1) goto L_0x002c
            if (r0 == 0) goto L_0x0021
            android.view.ViewTreeObserver r1 = r3.getViewTreeObserver()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda0 r2 = r3.mShadowUpdater
            r1.addOnPreDrawListener(r2)
            goto L_0x002a
        L_0x0021:
            android.view.ViewTreeObserver r1 = r3.getViewTreeObserver()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda0 r2 = r3.mShadowUpdater
            r1.removeOnPreDrawListener(r2)
        L_0x002a:
            r3.mContinuousShadowUpdate = r0
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateContinuousShadowDrawing():void");
    }

    public final void updateDecorViews() {
        int colorAttrDefaultColor = com.android.settingslib.Utils.getColorAttrDefaultColor(this.mContext, 16842806);
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        Objects.requireNonNull(notificationSectionsManager);
        SectionHeaderView peopleHeaderView = notificationSectionsManager.getPeopleHeaderView();
        if (peopleHeaderView != null) {
            peopleHeaderView.mLabelView.setTextColor(colorAttrDefaultColor);
            peopleHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        SectionHeaderView silentHeaderView = notificationSectionsManager.getSilentHeaderView();
        if (silentHeaderView != null) {
            silentHeaderView.mLabelView.setTextColor(colorAttrDefaultColor);
            silentHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        SectionHeaderView alertingHeaderView = notificationSectionsManager.getAlertingHeaderView();
        if (alertingHeaderView != null) {
            alertingHeaderView.mLabelView.setTextColor(colorAttrDefaultColor);
            alertingHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        this.mFooterView.updateColors();
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        Objects.requireNonNull(emptyShadeView);
        emptyShadeView.mEmptyText.setTextColor(colorAttrDefaultColor);
    }

    public final void updateDismissBehavior() {
        boolean z = true;
        if (this.mShouldUseSplitNotificationShade && (this.mStatusBarState == 1 || !this.mIsExpanded)) {
            z = false;
        }
        if (this.mDismissUsingRowTranslationX != z) {
            this.mDismissUsingRowTranslationX = z;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    Objects.requireNonNull(expandableNotificationRow);
                    if (z != expandableNotificationRow.mDismissUsingRowTranslationX) {
                        float translation = expandableNotificationRow.getTranslation();
                        int i2 = (translation > 0.0f ? 1 : (translation == 0.0f ? 0 : -1));
                        if (i2 != 0) {
                            expandableNotificationRow.setTranslation(0.0f);
                        }
                        expandableNotificationRow.mDismissUsingRowTranslationX = z;
                        if (i2 != 0) {
                            expandableNotificationRow.setTranslation(translation);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x005d A[EDGE_INSN: B:45:0x005d->B:28:0x005d ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateFooter() {
        /*
            r7 = this;
            com.android.systemui.statusbar.notification.row.FooterView r0 = r7.mFooterView
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            boolean r0 = r7.mClearAllEnabled
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0018
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r7.mController
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.hasNotifications(r1, r2)
            if (r0 == 0) goto L_0x0018
            r0 = r2
            goto L_0x0019
        L_0x0018:
            r0 = r1
        L_0x0019:
            if (r0 != 0) goto L_0x0023
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r3 = r7.mController
            int r3 = r3.getVisibleNotificationCount()
            if (r3 <= 0) goto L_0x0066
        L_0x0023:
            boolean r3 = r7.mIsCurrentUserSetup
            if (r3 == 0) goto L_0x0066
            int r3 = r7.mStatusBarState
            if (r3 == r2) goto L_0x0066
            float r3 = r7.mQsExpansionFraction
            r4 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x0066
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r3 = r7.mScreenOffAnimationController
            java.util.Objects.requireNonNull(r3)
            java.util.ArrayList r3 = r3.animations
            boolean r4 = r3 instanceof java.util.Collection
            if (r4 == 0) goto L_0x0045
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L_0x0045
            goto L_0x005d
        L_0x0045:
            java.util.Iterator r3 = r3.iterator()
        L_0x0049:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x005d
            java.lang.Object r4 = r3.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r4 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r4
            boolean r4 = r4.isAnimationPlaying()
            if (r4 == 0) goto L_0x0049
            r3 = r2
            goto L_0x005e
        L_0x005d:
            r3 = r1
        L_0x005e:
            if (r3 != 0) goto L_0x0066
            boolean r3 = r7.mIsRemoteInputActive
            if (r3 != 0) goto L_0x0066
            r3 = r2
            goto L_0x0067
        L_0x0066:
            r3 = r1
        L_0x0067:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = r7.mController
            boolean r4 = r4.isHistoryEnabled()
            com.android.systemui.statusbar.notification.row.FooterView r5 = r7.mFooterView
            if (r5 != 0) goto L_0x0072
            goto L_0x0092
        L_0x0072:
            boolean r6 = r7.mIsExpanded
            if (r6 == 0) goto L_0x007b
            boolean r6 = r7.mAnimationsEnabled
            if (r6 == 0) goto L_0x007b
            r1 = r2
        L_0x007b:
            r5.setVisible(r3, r1)
            com.android.systemui.statusbar.notification.row.FooterView r2 = r7.mFooterView
            r2.setSecondaryVisible(r0, r1)
            com.android.systemui.statusbar.notification.row.FooterView r7 = r7.mFooterView
            java.util.Objects.requireNonNull(r7)
            boolean r0 = r7.mShowHistory
            if (r0 != r4) goto L_0x008d
            goto L_0x0092
        L_0x008d:
            r7.mShowHistory = r4
            r7.updateText()
        L_0x0092:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateFooter():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateForwardAndBackwardScrollability() {
        /*
            r5 = this;
            boolean r0 = r5.mScrollable
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x001c
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7 r0 = r5.mScrollAdapter
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r0.mOwnScrollY
            int r0 = r0.getScrollRange()
            if (r3 < r0) goto L_0x0017
            r0 = r2
            goto L_0x0018
        L_0x0017:
            r0 = r1
        L_0x0018:
            if (r0 != 0) goto L_0x001c
            r0 = r2
            goto L_0x001d
        L_0x001c:
            r0 = r1
        L_0x001d:
            boolean r3 = r5.mScrollable
            if (r3 == 0) goto L_0x0033
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7 r3 = r5.mScrollAdapter
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r3.mOwnScrollY
            if (r3 != 0) goto L_0x002e
            r3 = r2
            goto L_0x002f
        L_0x002e:
            r3 = r1
        L_0x002f:
            if (r3 != 0) goto L_0x0033
            r3 = r2
            goto L_0x0034
        L_0x0033:
            r3 = r1
        L_0x0034:
            boolean r4 = r5.mForwardScrollable
            if (r0 != r4) goto L_0x003c
            boolean r4 = r5.mBackwardScrollable
            if (r3 == r4) goto L_0x003d
        L_0x003c:
            r1 = r2
        L_0x003d:
            r5.mForwardScrollable = r0
            r5.mBackwardScrollable = r3
            if (r1 == 0) goto L_0x0048
            r0 = 2048(0x800, float:2.87E-42)
            r5.sendAccessibilityEvent(r0)
        L_0x0048:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateForwardAndBackwardScrollability():void");
    }

    public final void updateLaunchedNotificationClipPath() {
        if (this.mLaunchingNotificationNeedsToBeClipped && this.mLaunchingNotification && this.mExpandingNotificationRow != null) {
            ExpandAnimationParameters expandAnimationParameters = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters);
            int min = Math.min(expandAnimationParameters.left, this.mRoundedRectClippingLeft);
            ExpandAnimationParameters expandAnimationParameters2 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters2);
            int max = Math.max(expandAnimationParameters2.right, this.mRoundedRectClippingRight);
            ExpandAnimationParameters expandAnimationParameters3 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters3);
            int max2 = Math.max(expandAnimationParameters3.bottom, this.mRoundedRectClippingBottom);
            PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            ExpandAnimationParameters expandAnimationParameters4 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters4);
            PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
            float interpolation = pathInterpolator.getInterpolation(LaunchAnimator.Companion.getProgress(ActivityLaunchAnimator.TIMINGS, expandAnimationParameters4.linearProgress, 0, 100));
            int i = this.mRoundedRectClippingTop;
            ExpandAnimationParameters expandAnimationParameters5 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters5);
            ExpandAnimationParameters expandAnimationParameters6 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters6);
            float f = expandAnimationParameters6.topCornerRadius;
            ExpandAnimationParameters expandAnimationParameters7 = this.mLaunchAnimationParams;
            Objects.requireNonNull(expandAnimationParameters7);
            float f2 = expandAnimationParameters7.bottomCornerRadius;
            float[] fArr = this.mLaunchedNotificationRadii;
            fArr[0] = f;
            fArr[1] = f;
            fArr[2] = f;
            fArr[3] = f;
            fArr[4] = f2;
            fArr[5] = f2;
            fArr[6] = f2;
            fArr[7] = f2;
            this.mLaunchedNotificationClipPath.reset();
            this.mLaunchedNotificationClipPath.addRoundRect((float) min, (float) ((int) Math.min(MathUtils.lerp(i, expandAnimationParameters5.top, interpolation), (float) this.mRoundedRectClippingTop)), (float) max, (float) max2, this.mLaunchedNotificationRadii, Path.Direction.CW);
            ExpandableNotificationRow expandableNotificationRow = this.mExpandingNotificationRow;
            Objects.requireNonNull(expandableNotificationRow);
            ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow = expandableNotificationRow2;
            }
            this.mLaunchedNotificationClipPath.offset(((float) (-expandableNotificationRow.getLeft())) - expandableNotificationRow.getTranslationX(), ((float) (-expandableNotificationRow.getTop())) - expandableNotificationRow.getTranslationY());
            expandableNotificationRow.mExpandingClipPath = this.mLaunchedNotificationClipPath;
            expandableNotificationRow.invalidate();
            if (this.mShouldUseRoundedRectClipping) {
                invalidate();
            }
        }
    }

    public final void updateNotificationAnimationStates() {
        boolean z;
        boolean z2;
        if (this.mAnimationsEnabled || this.mPulsing) {
            z = true;
        } else {
            z = false;
        }
        NotificationShelf notificationShelf = this.mShelf;
        Objects.requireNonNull(notificationShelf);
        notificationShelf.mAnimationsEnabled = z;
        if (!z) {
            notificationShelf.mShelfIcons.setAnimationsEnabled(false);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (this.mIsExpanded || isPinnedHeadsUp(childAt)) {
                z2 = true;
            } else {
                z2 = false;
            }
            z &= z2;
            if (childAt instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) childAt).setIconAnimationRunning(z);
            }
        }
    }

    public final void updateOwnTranslationZ() {
        float f;
        ExpandableView firstChildNotGone;
        if (!this.mKeyguardBypassEnabled || !this.mAmbientState.isHiddenAtAll() || (firstChildNotGone = getFirstChildNotGone()) == null || !firstChildNotGone.showingPulsing()) {
            f = 0.0f;
        } else {
            f = firstChildNotGone.getTranslationZ();
        }
        setTranslationZ(f);
    }

    public final void updateSensitiveness(boolean z, boolean z2) {
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (z2 != ambientState.mHideSensitive) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((ExpandableView) getChildAt(i)).setHideSensitiveForIntrinsicHeight(z2);
            }
            AmbientState ambientState2 = this.mAmbientState;
            Objects.requireNonNull(ambientState2);
            ambientState2.mHideSensitive = z2;
            if (z && this.mAnimationsEnabled) {
                this.mHideSensitiveNeedsAnimation = true;
                this.mNeedsAnimation = true;
            }
            updateContentHeight();
            requestChildrenUpdate();
        }
    }

    public final void updateStackPosition(boolean z) {
        float f = ((float) this.mTopPadding) + this.mExtraTopInsetForFullShadeTransition;
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        float currentOverScrollAmount = (f + ambientState.mOverExpansion) - getCurrentOverScrollAmount(false);
        AmbientState ambientState2 = this.mAmbientState;
        Objects.requireNonNull(ambientState2);
        float f2 = ambientState2.mExpansionFraction;
        float lerp = MathUtils.lerp(0.0f, currentOverScrollAmount, f2);
        AmbientState ambientState3 = this.mAmbientState;
        Objects.requireNonNull(ambientState3);
        ambientState3.mStackY = lerp;
        Consumer<Boolean> consumer = this.mOnStackYChanged;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        if (this.mQsExpansionFraction <= 0.0f && !shouldSkipHeightUpdate()) {
            float max = Math.max(0.0f, (((float) getHeight()) - ((float) getEmptyBottomMargin())) - ((float) this.mTopPadding));
            AmbientState ambientState4 = this.mAmbientState;
            Objects.requireNonNull(ambientState4);
            ambientState4.mStackEndHeight = max;
            AmbientState ambientState5 = this.mAmbientState;
            Objects.requireNonNull(ambientState5);
            float f3 = ambientState5.mDozeAmount;
            if (0.0f < f3 && f3 < 1.0f) {
                f2 = 1.0f - f3;
            }
            AmbientState ambientState6 = this.mAmbientState;
            float lerp2 = MathUtils.lerp(0.5f * max, max, f2);
            Objects.requireNonNull(ambientState6);
            ambientState6.mStackHeight = lerp2;
        }
    }

    public final void updateUseRoundedRectClipping() {
        boolean z;
        boolean z2 = false;
        if (this.mQsExpansionFraction < 0.5f || this.mShouldUseSplitNotificationShade) {
            z = true;
        } else {
            z = false;
        }
        if (this.mIsExpanded && z) {
            z2 = true;
        }
        if (z2 != this.mShouldUseRoundedRectClipping) {
            this.mShouldUseRoundedRectClipping = z2;
            invalidate();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        if (r4.mBucket < 6) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        if (r4.mBucket == 6) goto L_0x0038;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4, int r5) {
        /*
            boolean r0 = canChildBeCleared(r4)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x003c
            if (r5 == 0) goto L_0x0038
            r0 = 6
            if (r5 == r1) goto L_0x0029
            r3 = 2
            if (r5 != r3) goto L_0x001d
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mBucket
            if (r4 != r0) goto L_0x0036
            goto L_0x0038
        L_0x001d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Unknown selection: "
            java.lang.String r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r0, r5)
            r4.<init>(r5)
            throw r4
        L_0x0029:
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mBucket
            if (r4 >= r0) goto L_0x0036
            goto L_0x0038
        L_0x0036:
            r4 = r2
            goto L_0x0039
        L_0x0038:
            r4 = r1
        L_0x0039:
            if (r4 == 0) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r1 = r2
        L_0x003d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, int):boolean");
    }

    public final void applyCurrentState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            Objects.requireNonNull(expandableView);
            ExpandableViewState expandableViewState = expandableView.mViewState;
            if (!expandableViewState.gone) {
                expandableViewState.applyToView(expandableView);
            }
        }
        NotificationLogger.OnChildLocationsChangedListener onChildLocationsChangedListener = this.mListener;
        if (onChildLocationsChangedListener != null) {
            NotificationLogger.C12971 r0 = (NotificationLogger.C12971) onChildLocationsChangedListener;
            NotificationLogger notificationLogger = NotificationLogger.this;
            if (!notificationLogger.mHandler.hasCallbacks(notificationLogger.mVisibilityReporter)) {
                NotificationLogger notificationLogger2 = NotificationLogger.this;
                notificationLogger2.mHandler.postAtTime(notificationLogger2.mVisibilityReporter, notificationLogger2.mLastVisibilityReportUptimeMs + 500);
            }
        }
        Iterator<Runnable> it = this.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        this.mAnimationFinishedRunnables.clear();
        setAnimationRunning(false);
        updateBackground();
        updateViewShadows();
    }

    public final void changeViewPosition(ExpandableView expandableView, int i) {
        String str;
        Assert.isMainThread();
        if (!this.mChangePositionInProgress) {
            int indexOfChild = indexOfChild(expandableView);
            boolean z = false;
            if (indexOfChild == -1) {
                if (expandableView instanceof ExpandableNotificationRow) {
                    Objects.requireNonNull(expandableView);
                    if (expandableView.mTransientContainer != null) {
                        z = true;
                    }
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Attempting to re-position ");
                if (z) {
                    str = "transient";
                } else {
                    str = "";
                }
                m.append(str);
                m.append(" view {");
                m.append(expandableView);
                m.append("}");
                Log.e("StackScroller", m.toString());
            } else if (expandableView != null && expandableView.getParent() == this && indexOfChild != i) {
                this.mChangePositionInProgress = true;
                expandableView.mChangingPosition = true;
                removeView(expandableView);
                addView(expandableView, i);
                expandableView.mChangingPosition = false;
                this.mChangePositionInProgress = false;
                if (this.mIsExpanded && this.mAnimationsEnabled && expandableView.getVisibility() != 8) {
                    this.mChildrenChangingPositions.add(expandableView);
                    this.mNeedsAnimation = true;
                }
            }
        } else {
            throw new IllegalStateException("Reentrant call to changeViewPosition");
        }
    }

    public final void clampScrollPosition() {
        int i;
        int scrollRange = getScrollRange();
        if (scrollRange < this.mOwnScrollY) {
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            if (!ambientState.mClearAllInProgress) {
                boolean z = false;
                if (this.mShouldUseSplitNotificationShade) {
                    i = this.mSidePaddings;
                } else {
                    i = this.mTopPadding - this.mQsScrollBoundaryPosition;
                }
                if (scrollRange < i && this.mAnimateStackYForContentHeightChange) {
                    z = true;
                }
                setOwnScrollY(scrollRange, z);
            }
        }
    }

    public final void clearChildFocus(View view) {
        super.clearChildFocus(view);
        if (this.mForcedScroll == view) {
            this.mForcedScroll = null;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        IndentingPrintWriter asIndenting = R$id.asIndenting(printWriter);
        StringBuilder sb = new StringBuilder("[");
        sb.append(getClass().getSimpleName());
        sb.append(":");
        sb.append(" pulsing=");
        String str6 = "T";
        if (this.mPulsing) {
            str = str6;
        } else {
            str = "f";
        }
        sb.append(str);
        sb.append(" expanded=");
        if (this.mIsExpanded) {
            str2 = str6;
        } else {
            str2 = "f";
        }
        sb.append(str2);
        sb.append(" headsUpPinned=");
        if (this.mInHeadsUpPinnedMode) {
            str3 = str6;
        } else {
            str3 = "f";
        }
        sb.append(str3);
        sb.append(" qsClipping=");
        if (this.mShouldUseRoundedRectClipping) {
            str4 = str6;
        } else {
            str4 = "f";
        }
        sb.append(str4);
        sb.append(" qsClipDismiss=");
        if (this.mDismissUsingRowTranslationX) {
            str5 = str6;
        } else {
            str5 = "f";
        }
        sb.append(str5);
        sb.append(" visibility=");
        sb.append(R$id.visibilityString(getVisibility()));
        sb.append(" alpha=");
        sb.append(getAlpha());
        sb.append(" scrollY=");
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        sb.append(ambientState.mScrollY);
        sb.append(" maxTopPadding=");
        sb.append(this.mMaxTopPadding);
        sb.append(" showShelfOnly=");
        if (!this.mShouldShowShelfOnly) {
            str6 = "f";
        }
        sb.append(str6);
        sb.append(" qsExpandFraction=");
        sb.append(this.mQsExpansionFraction);
        sb.append(" isCurrentUserSetup=");
        sb.append(this.mIsCurrentUserSetup);
        sb.append(" hideAmount=");
        AmbientState ambientState2 = this.mAmbientState;
        Objects.requireNonNull(ambientState2);
        sb.append(ambientState2.mHideAmount);
        sb.append("]");
        asIndenting.println(sb.toString());
        R$id.withIncreasedIndent(asIndenting, new DefaultTransitionHandler$$ExternalSyntheticLambda1(this, asIndenting, fileDescriptor, strArr, 1));
    }

    public final float getAppearStartPosition() {
        int minExpansionHeight;
        int i;
        if (isHeadsUpTransition()) {
            NotificationSection firstVisibleSection = getFirstVisibleSection();
            if (firstVisibleSection != null) {
                i = firstVisibleSection.mFirstVisibleChild.getPinnedHeadsUpHeight();
            } else {
                i = 0;
            }
            minExpansionHeight = this.mHeadsUpInset + i;
        } else {
            minExpansionHeight = getMinExpansionHeight();
        }
        return (float) minExpansionHeight;
    }

    public final ExpandableView getChildAtPosition(float f, float f2, boolean z, boolean z2) {
        ExpandableNotificationRow expandableNotificationRow;
        float translationY;
        ExpandableNotificationRow expandableNotificationRow2;
        int childCount = getChildCount();
        int i = 0;
        int i2 = 0;
        while (true) {
            expandableNotificationRow = null;
            if (i2 >= childCount) {
                return null;
            }
            ExpandableView expandableView = (ExpandableView) getChildAt(i2);
            if (expandableView.getVisibility() == 0 && (!z2 || !(expandableView instanceof StackScrollerDecorView))) {
                translationY = expandableView.getTranslationY();
                float f3 = ((float) expandableView.mClipTopAmount) + translationY;
                float f4 = (((float) expandableView.mActualHeight) + translationY) - ((float) expandableView.mClipBottomAmount);
                int width = getWidth();
                if ((f4 - f3 >= ((float) this.mMinInteractionHeight) || !z) && f2 >= f3 && f2 <= f4 && f >= ((float) 0) && f <= ((float) width)) {
                    if (expandableView instanceof ExpandableNotificationRow) {
                        expandableNotificationRow2 = (ExpandableNotificationRow) expandableView;
                        NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
                        if (this.mIsExpanded || !expandableNotificationRow2.mIsHeadsUp || !expandableNotificationRow2.mIsPinned) {
                            break;
                        }
                        NotificationEntry notificationEntry2 = this.mTopHeadsUpEntry;
                        Objects.requireNonNull(notificationEntry2);
                        if (notificationEntry2.row == expandableNotificationRow2 || this.mGroupMembershipManager.getGroupSummary(this.mTopHeadsUpEntry) == notificationEntry) {
                            break;
                        }
                    } else {
                        return expandableView;
                    }
                }
            }
            i2++;
        }
        float f5 = f2 - translationY;
        if (!expandableNotificationRow2.mIsSummaryWithChildren || !expandableNotificationRow2.mChildrenExpanded) {
            return expandableNotificationRow2;
        }
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
        Objects.requireNonNull(notificationChildrenContainer);
        int size = notificationChildrenContainer.mAttachedChildren.size();
        while (true) {
            if (i >= size) {
                break;
            }
            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) notificationChildrenContainer.mAttachedChildren.get(i);
            float translationY2 = expandableNotificationRow3.getTranslationY();
            float f6 = ((float) expandableNotificationRow3.mClipTopAmount) + translationY2;
            float f7 = translationY2 + ((float) expandableNotificationRow3.mActualHeight);
            if (f5 >= f6 && f5 <= f7) {
                expandableNotificationRow = expandableNotificationRow3;
                break;
            }
            i++;
        }
        if (expandableNotificationRow == null) {
            return expandableNotificationRow2;
        }
        return expandableNotificationRow;
    }

    public final View getFirstChildBelowTranlsationY(float f, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                float translationY = childAt.getTranslationY();
                if (translationY >= f) {
                    return childAt;
                }
                if (!z && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    if (expandableNotificationRow.mIsSummaryWithChildren && expandableNotificationRow.mChildrenExpanded) {
                        ArrayList attachedChildren = expandableNotificationRow.getAttachedChildren();
                        for (int i2 = 0; i2 < attachedChildren.size(); i2++) {
                            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) attachedChildren.get(i2);
                            if (expandableNotificationRow2.getTranslationY() + translationY >= f) {
                                return expandableNotificationRow2;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return null;
    }

    public final ExpandableView getFirstChildNotGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8 && childAt != this.mShelf) {
                return (ExpandableView) childAt;
            }
        }
        return null;
    }

    public final ExpandableView getFirstChildWithBackground() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8 && !(expandableView instanceof StackScrollerDecorView) && expandableView != this.mShelf) {
                return expandableView;
            }
        }
        return null;
    }

    public final int getLayoutMinHeight() {
        if (isHeadsUpTransition()) {
            ExpandableNotificationRow trackedHeadsUpRow = this.mAmbientState.getTrackedHeadsUpRow();
            if (!trackedHeadsUpRow.isAboveShelf()) {
                return getTopHeadsUpPinnedHeight();
            }
            int positionInLinearLayout = getPositionInLinearLayout(trackedHeadsUpRow);
            AmbientState ambientState = this.mAmbientState;
            Objects.requireNonNull(ambientState);
            return getTopHeadsUpPinnedHeight() + ((int) MathUtils.lerp(0, positionInLinearLayout, ambientState.mAppearFraction));
        } else if (this.mShelf.getVisibility() == 8) {
            return 0;
        } else {
            NotificationShelf notificationShelf = this.mShelf;
            Objects.requireNonNull(notificationShelf);
            return notificationShelf.getHeight();
        }
    }

    public final float getRubberBandFactor(boolean z) {
        if (!z) {
            return 0.35f;
        }
        if (this.mExpandedInThisMotion) {
            return 0.15f;
        }
        if (this.mIsExpansionChanging || this.mPanelTracking) {
            return 0.21f;
        }
        return this.mScrolledToTopOnFirstDown ? 1.0f : 0.35f;
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        if (motionEvent.getClassification() == 1) {
            return ((float) this.mTouchSlop) * this.mSlopMultiplier;
        }
        return (float) this.mTouchSlop;
    }

    public final void handleEmptySpaceClick(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                float touchSlop = getTouchSlop(motionEvent);
                if (!this.mTouchIsClick) {
                    return;
                }
                if (Math.abs(motionEvent.getY() - this.mInitialTouchY) > touchSlop || Math.abs(motionEvent.getX() - this.mInitialTouchX) > touchSlop) {
                    this.mTouchIsClick = false;
                }
            }
        } else if (this.mStatusBarState != 1 && this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY)) {
            NotificationPanelViewController.OnEmptySpaceClickListener onEmptySpaceClickListener = (NotificationPanelViewController.OnEmptySpaceClickListener) this.mOnEmptySpaceClickListener;
            Objects.requireNonNull(onEmptySpaceClickListener);
            NotificationPanelViewController.this.onEmptySpaceClick();
        }
    }

    public final boolean isBelowLastNotification(float f, float f2) {
        boolean z;
        boolean z2;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ExpandableView expandableView = (ExpandableView) getChildAt(childCount);
            if (expandableView.getVisibility() != 8) {
                float y = expandableView.getY();
                if (y > f2) {
                    return false;
                }
                if (f2 > (((float) expandableView.mActualHeight) + y) - ((float) expandableView.mClipBottomAmount)) {
                    z = true;
                } else {
                    z = false;
                }
                FooterView footerView = this.mFooterView;
                if (expandableView == footerView) {
                    if (!z) {
                        float x = f - footerView.getX();
                        float f3 = f2 - y;
                        Objects.requireNonNull(footerView);
                        if (x < footerView.mContent.getX() || x > footerView.mContent.getX() + ((float) footerView.mContent.getWidth()) || f3 < footerView.mContent.getY() || f3 > footerView.mContent.getY() + ((float) footerView.mContent.getHeight())) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            return false;
                        }
                    } else {
                        continue;
                    }
                } else if (expandableView == this.mEmptyShadeView) {
                    return true;
                } else {
                    if (!z) {
                        return false;
                    }
                }
            }
        }
        if (f2 > ((float) this.mTopPadding) + this.mStackTranslation) {
            return true;
        }
        return false;
    }

    public final boolean isFullySwipedOut(ExpandableView expandableView) {
        float f;
        float abs = Math.abs(expandableView.getTranslation());
        if (!this.mDismissUsingRowTranslationX) {
            f = (float) expandableView.getMeasuredWidth();
        } else {
            float measuredWidth = (float) getMeasuredWidth();
            f = measuredWidth - ((measuredWidth - ((float) expandableView.getMeasuredWidth())) / 2.0f);
        }
        if (abs >= Math.abs(f)) {
            return true;
        }
        return false;
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mBottomInset = windowInsets.getSystemWindowInsetBottom();
        this.mWaterfallTopInset = 0;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            this.mWaterfallTopInset = displayCutout.getWaterfallInsets().top;
        }
        if (this.mOwnScrollY > getScrollRange()) {
            removeCallbacks(this.mReclamp);
            postDelayed(this.mReclamp, 50);
        } else {
            View view = this.mForcedScroll;
            if (view != null) {
                scrollTo(view);
            }
        }
        return windowInsets;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        boolean shouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(getResources());
        if (shouldUseSplitNotificationShade != this.mShouldUseSplitNotificationShade) {
            this.mShouldUseSplitNotificationShade = shouldUseSplitNotificationShade;
            updateDismissBehavior();
            updateUseRoundedRectClipping();
        }
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
        float f = resources.getDisplayMetrics().density;
        NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
        Objects.requireNonNull(notificationSwipeHelper);
        notificationSwipeHelper.mDensityScale = f;
        NotificationSwipeHelper notificationSwipeHelper2 = this.mSwipeHelper;
        Objects.requireNonNull(notificationSwipeHelper2);
        notificationSwipeHelper2.mPagingTouchSlop = (float) ViewConfiguration.get(getContext()).getScaledPagingTouchSlop();
        initView(getContext(), this.mSwipeHelper);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005a, code lost:
        if (r6.getTranslation(r7) != 0.0f) goto L_0x005c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry r7) {
        /*
            r6 = this;
            boolean r0 = r7.rowExists()
            if (r0 == 0) goto L_0x0077
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            boolean r0 = r0.isClearable()
            if (r0 != 0) goto L_0x0077
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r7.row
            boolean r0 = r6.mIsExpanded
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001f
            boolean r0 = isPinnedHeadsUp(r7)
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = r1
            goto L_0x0020
        L_0x001f:
            r0 = r2
        L_0x0020:
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r3 = r7.mMenuRow
            if (r3 == 0) goto L_0x0077
            boolean r3 = r3.isMenuVisible()
            r4 = 0
            if (r3 == 0) goto L_0x0033
            float r3 = r7.getTranslation()
            goto L_0x0034
        L_0x0033:
            r3 = r4
        L_0x0034:
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r6 = r6.mSwipeHelper
            java.util.Objects.requireNonNull(r6)
            boolean r5 = r6.mIsSwiping
            if (r5 == 0) goto L_0x0041
            com.android.systemui.statusbar.notification.row.ExpandableView r5 = r6.mTouchedView
            if (r5 == r7) goto L_0x0077
        L_0x0041:
            boolean r5 = r6.mSnappingChild
            if (r5 == 0) goto L_0x0046
            goto L_0x0077
        L_0x0046:
            android.util.ArrayMap<android.view.View, android.animation.Animator> r5 = r6.mDismissPendingMap
            java.lang.Object r5 = r5.get(r7)
            android.animation.Animator r5 = (android.animation.Animator) r5
            if (r5 == 0) goto L_0x0054
            r5.cancel()
            goto L_0x005c
        L_0x0054:
            float r5 = r6.getTranslation(r7)
            int r5 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x005d
        L_0x005c:
            r1 = r2
        L_0x005d:
            if (r1 == 0) goto L_0x0077
            if (r0 == 0) goto L_0x0065
            r6.snapChild(r7, r3, r4)
            goto L_0x0077
        L_0x0065:
            com.android.systemui.SwipeHelper$Callback r0 = r6.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r0
            boolean r0 = r0.canChildBeDismissed(r7)
            r7.setTranslation(r4)
            float r1 = r6.getTranslation(r7)
            r6.updateSwipeProgressFromOffset(r7, r0, r1)
        L_0x0077:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        inflateEmptyShadeView();
        inflateFooterView();
    }

    public final void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(this.mScrollable);
        accessibilityEvent.setMaxScrollX(this.mScrollX);
        accessibilityEvent.setScrollY(this.mOwnScrollY);
        accessibilityEvent.setMaxScrollY(getScrollRange());
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mScrollable) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.mBackwardScrollable) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mForwardScrollable) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
            }
        }
        accessibilityNodeInfo.setClassName(ScrollView.class.getName());
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float width = ((float) getWidth()) / 2.0f;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            float measuredWidth = ((float) childAt.getMeasuredWidth()) / 2.0f;
            childAt.layout((int) (width - measuredWidth), 0, (int) (measuredWidth + width), (int) ((float) childAt.getMeasuredHeight()));
        }
        this.mMaxLayoutHeight = getHeight();
        updateAlgorithmHeightAndPadding();
        updateContentHeight();
        clampScrollPosition();
        requestChildrenUpdate();
        updateFirstAndLastBackgroundViews();
        updateAlgorithmLayoutMinHeight();
        updateOwnTranslationZ();
        this.mAnimateStackYForContentHeightChange = false;
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        if (size == 0 || !this.mSkinnyNotifsInLandscape) {
            this.mSidePaddings = this.mMinimumPaddings;
        } else if (getResources().getConfiguration().orientation == 1) {
            this.mSidePaddings = this.mMinimumPaddings;
        } else {
            int i3 = this.mMinimumPaddings;
            int i4 = this.mQsTilePadding;
            this.mSidePaddings = (((size - (i3 * 2)) - (i4 * 3)) / 4) + i3 + i4;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - (this.mSidePaddings * 2), View.MeasureSpec.getMode(i));
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            measureChild(getChildAt(i5), makeMeasureSpec, makeMeasureSpec2);
        }
    }

    public final void onSecondaryPointerUp(MotionEvent motionEvent) {
        int i;
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            if (action == 0) {
                i = 1;
            } else {
                i = 0;
            }
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof ExpandableView) {
            onViewAddedInternal((ExpandableView) view);
        }
    }

    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (!this.mChildTransferInProgress) {
            onViewRemovedInternal((ExpandableView) view, this);
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z) {
            cancelLongPress();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        if (r5 != 16908346) goto L_0x005c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0050  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performAccessibilityActionInternal(int r5, android.os.Bundle r6) {
        /*
            r4 = this;
            boolean r6 = super.performAccessibilityActionInternal(r5, r6)
            r0 = 1
            if (r6 == 0) goto L_0x0008
            return r0
        L_0x0008:
            boolean r6 = r4.isEnabled()
            r1 = 0
            if (r6 != 0) goto L_0x0010
            return r1
        L_0x0010:
            r6 = -1
            r2 = 4096(0x1000, float:5.74E-42)
            if (r5 == r2) goto L_0x0024
            r2 = 8192(0x2000, float:1.14794E-41)
            if (r5 == r2) goto L_0x0025
            r2 = 16908344(0x1020038, float:2.3877386E-38)
            if (r5 == r2) goto L_0x0025
            r6 = 16908346(0x102003a, float:2.3877392E-38)
            if (r5 == r6) goto L_0x0024
            goto L_0x005c
        L_0x0024:
            r6 = r0
        L_0x0025:
            int r5 = r4.getHeight()
            int r2 = r4.mPaddingBottom
            int r5 = r5 - r2
            int r2 = r4.mTopPadding
            int r5 = r5 - r2
            int r2 = r4.mPaddingTop
            int r5 = r5 - r2
            com.android.systemui.statusbar.NotificationShelf r2 = r4.mShelf
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.getHeight()
            int r5 = r5 - r2
            int r2 = r4.mOwnScrollY
            int r6 = r6 * r5
            int r6 = r6 + r2
            int r5 = r4.getScrollRange()
            int r5 = java.lang.Math.min(r6, r5)
            int r5 = java.lang.Math.max(r1, r5)
            int r6 = r4.mOwnScrollY
            if (r5 == r6) goto L_0x005c
            android.widget.OverScroller r2 = r4.mScroller
            int r3 = r4.mScrollX
            int r5 = r5 - r6
            r2.startScroll(r3, r6, r1, r5)
            r4.animateScroll()
            return r0
        L_0x005c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.performAccessibilityActionInternal(int, android.os.Bundle):boolean");
    }

    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            cancelLongPress();
        }
    }

    public final void setDimmed(boolean z, boolean z2) {
        boolean onKeyguard = z & onKeyguard();
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mDimmed = onKeyguard;
        float f = 1.0f;
        if (!z2 || !this.mAnimationsEnabled) {
            if (!onKeyguard) {
                f = 0.0f;
            }
            this.mDimAmount = f;
            updateBackgroundDimming();
        } else {
            this.mDimmedNeedsAnimation = true;
            this.mNeedsAnimation = true;
            ValueAnimator valueAnimator = this.mDimAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (!onKeyguard) {
                f = 0.0f;
            }
            float f2 = this.mDimAmount;
            if (f != f2) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f2, f});
                this.mDimAnimator = ofFloat;
                ofFloat.setDuration(220);
                this.mDimAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mDimAnimator.addListener(this.mDimEndListener);
                this.mDimAnimator.addUpdateListener(this.mDimUpdateListener);
                this.mDimAnimator.start();
            }
        }
        requestChildrenUpdate();
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x0187  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setExpandedHeight(float r10) {
        /*
            r9 = this;
            int r0 = r9.getHeight()
            int r1 = r9.getEmptyBottomMargin()
            int r0 = r0 - r1
            float r0 = (float) r0
            boolean r1 = r9.shouldSkipHeightUpdate()
            if (r1 != 0) goto L_0x001d
            float r0 = r10 / r0
            float r0 = android.util.MathUtils.saturate(r0)
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r9.mAmbientState
            java.util.Objects.requireNonNull(r2)
            r2.mExpansionFraction = r0
        L_0x001d:
            r0 = 0
            r9.updateStackPosition(r0)
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x00c1
            r9.mExpandedHeight = r10
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x002d
            r4 = r3
            goto L_0x002e
        L_0x002d:
            r4 = r0
        L_0x002e:
            boolean r5 = r9.mIsExpanded
            if (r4 == r5) goto L_0x0034
            r5 = r3
            goto L_0x0035
        L_0x0034:
            r5 = r0
        L_0x0035:
            r9.mIsExpanded = r4
            com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm r6 = r9.mStackScrollAlgorithm
            java.util.Objects.requireNonNull(r6)
            r6.mIsExpanded = r4
            com.android.systemui.statusbar.notification.stack.AmbientState r6 = r9.mAmbientState
            java.util.Objects.requireNonNull(r6)
            r6.mShadeExpanded = r4
            com.android.systemui.statusbar.notification.stack.StackStateAnimator r6 = r9.mStateAnimator
            java.util.Objects.requireNonNull(r6)
            r6.mShadeExpanded = r4
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r6 = r9.mSwipeHelper
            java.util.Objects.requireNonNull(r6)
            r6.mIsExpanded = r4
            r4 = 0
            if (r5 == 0) goto L_0x009b
            r9.mWillExpand = r0
            boolean r5 = r9.mIsExpanded
            if (r5 != 0) goto L_0x0076
            com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager r5 = r9.mGroupExpansionManager
            r5.collapseGroups()
            com.android.systemui.ExpandHelper r5 = r9.mExpandHelper
            java.util.Objects.requireNonNull(r5)
            r5.finishExpanding(r3, r2, r0)
            r5.mResizedView = r4
            android.view.ScaleGestureDetector r6 = new android.view.ScaleGestureDetector
            android.content.Context r7 = r5.mContext
            com.android.systemui.ExpandHelper$2 r8 = r5.mScaleGestureListener
            r6.<init>(r7, r8)
            r5.mSGD = r6
        L_0x0076:
            r9.updateNotificationAnimationStates()
            int r5 = r9.getChildCount()
            r6 = r0
        L_0x007e:
            if (r6 >= r5) goto L_0x0092
            android.view.View r7 = r9.getChildAt(r6)
            boolean r8 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r8 == 0) goto L_0x008f
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
            boolean r8 = r9.mIsExpanded
            r7.setChronometerRunning(r8)
        L_0x008f:
            int r6 = r6 + 1
            goto L_0x007e
        L_0x0092:
            r9.requestChildrenUpdate()
            r9.updateUseRoundedRectClipping()
            r9.updateDismissBehavior()
        L_0x009b:
            int r5 = r9.getMinExpansionHeight()
            float r5 = (float) r5
            int r6 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r6 >= 0) goto L_0x00bc
            android.graphics.Rect r4 = r9.mClipRect
            r4.left = r0
            int r6 = r9.getWidth()
            r4.right = r6
            android.graphics.Rect r4 = r9.mClipRect
            r4.top = r0
            int r10 = (int) r10
            r4.bottom = r10
            r9.mRequestedClipBounds = r4
            r9.updateClipping()
            r10 = r5
            goto L_0x00c1
        L_0x00bc:
            r9.mRequestedClipBounds = r4
            r9.updateClipping()
        L_0x00c1:
            float r4 = r9.getAppearEndPosition()
            float r5 = r9.getAppearStartPosition()
            r6 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x00d0
            goto L_0x00d1
        L_0x00d0:
            r3 = r0
        L_0x00d1:
            com.android.systemui.statusbar.notification.stack.AmbientState r4 = r9.mAmbientState
            java.util.Objects.requireNonNull(r4)
            if (r3 != 0) goto L_0x011b
            boolean r0 = r9.mShouldShowShelfOnly
            if (r0 == 0) goto L_0x00ea
            int r10 = r9.mTopPadding
            com.android.systemui.statusbar.NotificationShelf r0 = r9.mShelf
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.getHeight()
            int r0 = r0 + r10
            goto L_0x016c
        L_0x00ea:
            boolean r0 = r9.mQsExpanded
            if (r0 == 0) goto L_0x0115
            int r0 = r9.mContentHeight
            int r3 = r9.mTopPadding
            int r0 = r0 - r3
            int r3 = r9.mIntrinsicPadding
            int r0 = r0 + r3
            int r3 = r9.mMaxTopPadding
            com.android.systemui.statusbar.NotificationShelf r4 = r9.mShelf
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.getHeight()
            int r3 = r3 + r4
            if (r0 > r3) goto L_0x0106
            r0 = r3
            goto L_0x016c
        L_0x0106:
            boolean r4 = r9.mShouldUseSplitNotificationShade
            if (r4 == 0) goto L_0x010b
            goto L_0x0113
        L_0x010b:
            float r10 = (float) r0
            float r0 = (float) r3
            float r3 = r9.mQsExpansionFraction
            float r10 = com.android.systemui.R$array.interpolate(r10, r0, r3)
        L_0x0113:
            int r10 = (int) r10
            goto L_0x016b
        L_0x0115:
            if (r1 == 0) goto L_0x0119
            float r10 = r9.mExpandedHeight
        L_0x0119:
            int r0 = (int) r10
            goto L_0x016c
        L_0x011b:
            float r3 = r9.getAppearEndPosition()
            float r4 = r9.getAppearStartPosition()
            float r6 = r10 - r4
            float r3 = r3 - r4
            float r6 = r6 / r3
            int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r3 < 0) goto L_0x0143
            int r3 = r9.mTopPadding
            int r3 = -r3
            int r4 = r9.getMinExpansionHeight()
            int r4 = r4 + r3
            com.android.systemui.statusbar.NotificationShelf r3 = r9.mShelf
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.getHeight()
            int r4 = r4 - r3
            float r3 = (float) r4
            float r2 = com.android.systemui.R$array.interpolate(r3, r2, r6)
            goto L_0x0159
        L_0x0143:
            float r2 = r10 - r5
            int r3 = r9.mTopPadding
            int r3 = -r3
            int r4 = r9.getMinExpansionHeight()
            int r4 = r4 + r3
            com.android.systemui.statusbar.NotificationShelf r3 = r9.mShelf
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.getHeight()
            int r4 = r4 - r3
            float r3 = (float) r4
            float r2 = r2 + r3
        L_0x0159:
            float r10 = r10 - r2
            int r10 = (int) r10
            boolean r3 = r9.isHeadsUpTransition()
            if (r3 == 0) goto L_0x016b
            int r2 = r9.mHeadsUpInset
            int r3 = r9.mTopPadding
            int r2 = r2 - r3
            float r0 = android.util.MathUtils.lerp(r2, r0, r6)
            r2 = r0
        L_0x016b:
            r0 = r10
        L_0x016c:
            com.android.systemui.statusbar.notification.stack.AmbientState r10 = r9.mAmbientState
            java.util.Objects.requireNonNull(r10)
            r10.mAppearFraction = r6
            int r10 = r9.mCurrentStackHeight
            if (r0 == r10) goto L_0x0181
            if (r1 != 0) goto L_0x0181
            r9.mCurrentStackHeight = r0
            r9.updateAlgorithmHeightAndPadding()
            r9.requestChildrenUpdate()
        L_0x0181:
            float r10 = r9.mStackTranslation
            int r10 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r10 == 0) goto L_0x0193
            r9.mStackTranslation = r2
            com.android.systemui.statusbar.notification.stack.AmbientState r10 = r9.mAmbientState
            java.util.Objects.requireNonNull(r10)
            r10.mStackTranslation = r2
            r9.requestChildrenUpdate()
        L_0x0193:
            r9.notifyAppearChangedListeners()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.setExpandedHeight(float):void");
    }

    public final int targetScrollForView(ExpandableView expandableView, int i) {
        int i2;
        int max = (Math.max(0, this.mBottomInset - (getRootView().getHeight() - getHeight())) + (expandableView.getIntrinsicHeight() + i)) - getHeight();
        if (this.mIsExpanded || !isPinnedHeadsUp(expandableView)) {
            i2 = this.mTopPadding;
        } else {
            i2 = this.mHeadsUpInset;
        }
        return max + i2;
    }

    public final void updateFirstAndLastBackgroundViews() {
        ExpandableView expandableView;
        ExpandableView expandableView2;
        boolean z;
        boolean z2;
        NotificationSection firstVisibleSection = getFirstVisibleSection();
        NotificationSection lastVisibleSection = getLastVisibleSection();
        ExpandableView expandableView3 = null;
        if (firstVisibleSection == null) {
            expandableView = null;
        } else {
            expandableView = firstVisibleSection.mFirstVisibleChild;
        }
        if (lastVisibleSection == null) {
            expandableView2 = null;
        } else {
            expandableView2 = lastVisibleSection.mLastVisibleChild;
        }
        ExpandableView firstChildWithBackground = getFirstChildWithBackground();
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                break;
            }
            ExpandableView expandableView4 = (ExpandableView) getChildAt(childCount);
            if (expandableView4.getVisibility() != 8 && !(expandableView4 instanceof StackScrollerDecorView) && expandableView4 != this.mShelf) {
                expandableView3 = expandableView4;
                break;
            }
        }
        boolean updateFirstAndLastViewsForAllSections = this.mSectionsManager.updateFirstAndLastViewsForAllSections(this.mSections, getChildrenWithBackground());
        if (!this.mAnimationsEnabled || !this.mIsExpanded) {
            this.mAnimateNextBackgroundTop = false;
            this.mAnimateNextBackgroundBottom = false;
            this.mAnimateNextSectionBoundsChange = false;
        } else {
            if (firstChildWithBackground != expandableView) {
                z = true;
            } else {
                z = false;
            }
            this.mAnimateNextBackgroundTop = z;
            if (expandableView3 != expandableView2 || this.mAnimateBottomOnLayout) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.mAnimateNextBackgroundBottom = z2;
            this.mAnimateNextSectionBoundsChange = updateFirstAndLastViewsForAllSections;
        }
        AmbientState ambientState = this.mAmbientState;
        Objects.requireNonNull(ambientState);
        ambientState.mLastVisibleBackgroundChild = expandableView3;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayoutController.mNotificationRoundnessManager;
        NotificationSection[] notificationSectionArr = this.mSections;
        Objects.requireNonNull(notificationRoundnessManager);
        for (int i = 0; i < notificationSectionArr.length; i++) {
            ExpandableView[] expandableViewArr = notificationRoundnessManager.mTmpFirstInSectionViews;
            ExpandableView[] expandableViewArr2 = notificationRoundnessManager.mFirstInSectionViews;
            expandableViewArr[i] = expandableViewArr2[i];
            notificationRoundnessManager.mTmpLastInSectionViews[i] = notificationRoundnessManager.mLastInSectionViews[i];
            NotificationSection notificationSection = notificationSectionArr[i];
            Objects.requireNonNull(notificationSection);
            expandableViewArr2[i] = notificationSection.mFirstVisibleChild;
            ExpandableView[] expandableViewArr3 = notificationRoundnessManager.mLastInSectionViews;
            NotificationSection notificationSection2 = notificationSectionArr[i];
            Objects.requireNonNull(notificationSection2);
            expandableViewArr3[i] = notificationSection2.mLastVisibleChild;
        }
        if (notificationRoundnessManager.handleAddedNewViews(notificationSectionArr, notificationRoundnessManager.mTmpLastInSectionViews, false) || (((notificationRoundnessManager.handleRemovedOldViews(notificationSectionArr, notificationRoundnessManager.mTmpFirstInSectionViews, true) | false) | notificationRoundnessManager.handleRemovedOldViews(notificationSectionArr, notificationRoundnessManager.mTmpLastInSectionViews, false)) | notificationRoundnessManager.handleAddedNewViews(notificationSectionArr, notificationRoundnessManager.mTmpFirstInSectionViews, true))) {
            notificationRoundnessManager.mRoundingChangedCallback.run();
        }
        this.mAnimateBottomOnLayout = false;
        invalidate();
    }
}
