package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import java.util.Objects;

public class SwipeHelper implements Gefingerpoken {
    public final Callback mCallback;
    public boolean mCanCurrViewBeDimissed;
    public float mDensityScale;
    public final ArrayMap<View, Animator> mDismissPendingMap = new ArrayMap<>();
    public final float[] mDownLocation = new float[2];
    public final boolean mFadeDependingOnAmountSwiped;
    public final FalsingManager mFalsingManager;
    public final int mFalsingThreshold;
    public final FeatureFlags mFeatureFlags;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final Handler mHandler;
    public float mInitialTouchPos;
    public boolean mIsSwiping;
    public boolean mLongPressSent;
    public final long mLongPressTimeout;
    public boolean mMenuRowIntercepting;
    public float mPagingTouchSlop;
    public final C06451 mPerformLongPress = new Runnable() {
        public final int[] mViewOffset = new int[2];

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0074, code lost:
            if (r0 == false) goto L_0x0078;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r7 = this;
                com.android.systemui.SwipeHelper r0 = com.android.systemui.SwipeHelper.this
                com.android.systemui.statusbar.notification.row.ExpandableView r1 = r0.mTouchedView
                if (r1 == 0) goto L_0x0089
                boolean r2 = r0.mLongPressSent
                if (r2 != 0) goto L_0x0089
                r2 = 1
                r0.mLongPressSent = r2
                boolean r0 = r1 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r0 == 0) goto L_0x0089
                int[] r0 = r7.mViewOffset
                r1.getLocationOnScreen(r0)
                com.android.systemui.SwipeHelper r0 = com.android.systemui.SwipeHelper.this
                float[] r1 = r0.mDownLocation
                r3 = 0
                r4 = r1[r3]
                int r4 = (int) r4
                int[] r5 = r7.mViewOffset
                r6 = r5[r3]
                int r4 = r4 - r6
                r1 = r1[r2]
                int r1 = (int) r1
                r5 = r5[r2]
                int r1 = r1 - r5
                com.android.systemui.statusbar.notification.row.ExpandableView r0 = r0.mTouchedView
                r5 = 2
                r0.sendAccessibilityEvent(r5)
                com.android.systemui.SwipeHelper r0 = com.android.systemui.SwipeHelper.this
                com.android.systemui.statusbar.notification.row.ExpandableView r0 = r0.mTouchedView
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r0
                r0.doLongClickCallback(r4, r1)
                com.android.systemui.SwipeHelper r0 = com.android.systemui.SwipeHelper.this
                com.android.systemui.statusbar.notification.row.ExpandableView r1 = r0.mTouchedView
                com.android.systemui.flags.FeatureFlags r0 = r0.mFeatureFlags
                com.android.systemui.flags.ResourceBooleanFlag r4 = com.android.systemui.flags.Flags.NOTIFICATION_DRAG_TO_CONTENTS
                boolean r0 = r0.isEnabled((com.android.systemui.flags.ResourceBooleanFlag) r4)
                if (r0 == 0) goto L_0x0077
                boolean r0 = r1 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r0 == 0) goto L_0x0077
                com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r1
                java.util.Objects.requireNonNull(r1)
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r1.mEntry
                java.util.Objects.requireNonNull(r0)
                android.service.notification.NotificationListenerService$Ranking r0 = r0.mRanking
                boolean r0 = r0.canBubble()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r1.mEntry
                java.util.Objects.requireNonNull(r1)
                android.service.notification.StatusBarNotification r1 = r1.mSbn
                android.app.Notification r1 = r1.getNotification()
                android.app.PendingIntent r4 = r1.contentIntent
                if (r4 == 0) goto L_0x006a
                goto L_0x006c
            L_0x006a:
                android.app.PendingIntent r4 = r1.fullScreenIntent
            L_0x006c:
                if (r4 == 0) goto L_0x0077
                boolean r1 = r4.isActivity()
                if (r1 == 0) goto L_0x0077
                if (r0 != 0) goto L_0x0077
                goto L_0x0078
            L_0x0077:
                r2 = r3
            L_0x0078:
                if (r2 == 0) goto L_0x0089
                com.android.systemui.SwipeHelper r7 = com.android.systemui.SwipeHelper.this
                com.android.systemui.SwipeHelper$Callback r0 = r7.mCallback
                com.android.systemui.statusbar.notification.row.ExpandableView r7 = r7.mTouchedView
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r0
                java.util.Objects.requireNonNull(r0)
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                r0.mLongPressedView = r7
            L_0x0089:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.C06451.run():void");
        }
    };
    public float mPerpendicularInitialTouchPos;
    public final float mSlopMultiplier;
    public boolean mSnappingChild;
    public final int mSwipeDirection;
    public boolean mTouchAboveFalsingThreshold;
    public int mTouchSlop;
    public float mTouchSlopMultiplier;
    public ExpandableView mTouchedView;
    public float mTranslation = 0.0f;
    public final VelocityTracker mVelocityTracker;

    public interface Callback {
    }

    public final void dismissChild(final View view, float f, NotificationStackScrollLayout$$ExternalSyntheticLambda2 notificationStackScrollLayout$$ExternalSyntheticLambda2, long j, boolean z, long j2, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        float f2;
        long j3;
        Animator animator;
        float f3;
        View view2 = view;
        long j4 = j;
        final boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.C13777) this.mCallback).canChildBeDismissed(view);
        boolean z6 = false;
        if (view.getLayoutDirection() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        if (i != 0 || ((getTranslation(view) != 0.0f && !z2) || this.mSwipeDirection != 1)) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (i != 0 || ((getTranslation(view) != 0.0f && !z2) || !z3)) {
            z5 = false;
        } else {
            z5 = true;
        }
        if ((Math.abs(f) > getEscapeVelocity() && f < 0.0f) || (getTranslation(view) < 0.0f && !z2)) {
            z6 = true;
        }
        if (z6 || z5 || z4) {
            NotificationStackScrollLayoutController.C13777 r2 = (NotificationStackScrollLayoutController.C13777) ((NotificationSwipeHelper) this).mCallback;
            Objects.requireNonNull(r2);
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if (!notificationStackScrollLayout.mDismissUsingRowTranslationX) {
                f3 = (float) view.getMeasuredWidth();
            } else {
                float measuredWidth = (float) notificationStackScrollLayout.getMeasuredWidth();
                f3 = measuredWidth - ((measuredWidth - ((float) view.getMeasuredWidth())) / 2.0f);
            }
            f2 = -f3;
        } else {
            NotificationStackScrollLayoutController.C13777 r22 = (NotificationStackScrollLayoutController.C13777) ((NotificationSwipeHelper) this).mCallback;
            Objects.requireNonNull(r22);
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            if (!notificationStackScrollLayout2.mDismissUsingRowTranslationX) {
                f2 = (float) view.getMeasuredWidth();
            } else {
                float measuredWidth2 = (float) notificationStackScrollLayout2.getMeasuredWidth();
                f2 = measuredWidth2 - ((measuredWidth2 - ((float) view.getMeasuredWidth())) / 2.0f);
            }
        }
        float f4 = f2;
        if (j2 != 0) {
            j3 = j2;
        } else if (i != 0) {
            j3 = Math.min(400, (long) ((int) ((Math.abs(f4 - getTranslation(view)) * 1000.0f) / Math.abs(f))));
        } else {
            j3 = 200;
        }
        view.setLayerType(2, (Paint) null);
        C06462 r4 = new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper swipeHelper = SwipeHelper.this;
                View view = view;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                boolean z = canChildBeDismissed;
                Objects.requireNonNull(swipeHelper);
                swipeHelper.updateSwipeProgressFromOffset(view, z, floatValue);
            }
        };
        NotificationSwipeHelper notificationSwipeHelper = (NotificationSwipeHelper) this;
        if (view2 instanceof ExpandableNotificationRow) {
            animator = ((ExpandableNotificationRow) view2).getTranslateViewAnimator(f4, r4);
        } else {
            animator = notificationSwipeHelper.superGetViewTranslationAnimator(view, f4, r4);
        }
        Animator animator2 = animator;
        if (animator2 == null) {
            InteractionJankMonitor.getInstance().end(4);
            return;
        }
        if (z) {
            animator2.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            animator2.setDuration(j3);
        } else {
            this.mFlingAnimationUtils.applyDismissing(animator2, getTranslation(view), f4, f, getSize(view));
        }
        if (j4 > 0) {
            animator2.setStartDelay(j4);
        }
        final NotificationStackScrollLayout$$ExternalSyntheticLambda2 notificationStackScrollLayout$$ExternalSyntheticLambda22 = notificationStackScrollLayout$$ExternalSyntheticLambda2;
        animator2.addListener(new AnimatorListenerAdapter() {
            public boolean mCancelled;

            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                boolean z;
                SwipeHelper swipeHelper = SwipeHelper.this;
                View view = view;
                boolean z2 = canChildBeDismissed;
                Objects.requireNonNull(swipeHelper);
                swipeHelper.updateSwipeProgressFromOffset(view, z2, swipeHelper.getTranslation(view));
                SwipeHelper.this.mDismissPendingMap.remove(view);
                View view2 = view;
                if (view2 instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view2;
                    Objects.requireNonNull(expandableNotificationRow);
                    z = expandableNotificationRow.mRemoved;
                } else {
                    z = false;
                }
                if (!this.mCancelled || z) {
                    Callback callback = SwipeHelper.this.mCallback;
                    View view3 = view;
                    NotificationStackScrollLayoutController.C13777 r6 = (NotificationStackScrollLayoutController.C13777) callback;
                    Objects.requireNonNull(r6);
                    if (view3 instanceof ActivatableNotificationView) {
                        ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) view3;
                        Objects.requireNonNull(activatableNotificationView);
                        if (!activatableNotificationView.mDismissed) {
                            r6.handleChildViewDismissed(view3);
                        }
                        activatableNotificationView.removeFromTransientContainer();
                    }
                    SwipeHelper swipeHelper2 = SwipeHelper.this;
                    Objects.requireNonNull(swipeHelper2);
                    swipeHelper2.mTouchedView = null;
                    swipeHelper2.mIsSwiping = false;
                }
                Runnable runnable = notificationStackScrollLayout$$ExternalSyntheticLambda22;
                if (runnable != null) {
                    runnable.run();
                }
                Objects.requireNonNull(SwipeHelper.this);
                view.setLayerType(0, (Paint) null);
                Objects.requireNonNull((NotificationSwipeHelper) SwipeHelper.this);
                InteractionJankMonitor.getInstance().end(4);
            }

            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                ((NotificationStackScrollLayoutController.C13777) SwipeHelper.this.mCallback).onBeginDrag(view);
            }
        });
        this.mDismissPendingMap.put(view, animator2);
        animator2.start();
    }

    public void dismissChild(View view, float f, boolean z) {
        throw null;
    }

    public float getEscapeVelocity() {
        throw null;
    }

    public float getTranslation(View view) {
        throw null;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        throw null;
    }

    public void snapChild(View view, float f, float f2) {
        throw null;
    }

    public boolean swipedFarEnough() {
        throw null;
    }

    public boolean swipedFastEnough() {
        throw null;
    }

    public final void cancelLongPress() {
        this.mHandler.removeCallbacks(this.mPerformLongPress);
    }

    public final float getPos(MotionEvent motionEvent) {
        if (this.mSwipeDirection == 0) {
            return motionEvent.getX();
        }
        return motionEvent.getY();
    }

    public final float getSize(View view) {
        int i;
        if (this.mSwipeDirection == 0) {
            i = view.getMeasuredWidth();
        } else {
            i = view.getMeasuredHeight();
        }
        return (float) i;
    }

    public final boolean isDismissGesture(MotionEvent motionEvent) {
        getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() == 1 && !this.mFalsingManager.isUnlockingDisabled() && !isFalseGesture() && (swipedFastEnough() || swipedFarEnough())) {
            Callback callback = this.mCallback;
            ExpandableView expandableView = this.mTouchedView;
            NotificationStackScrollLayoutController.C13777 r2 = (NotificationStackScrollLayoutController.C13777) callback;
            Objects.requireNonNull(r2);
            if (r2.canChildBeDismissed(expandableView)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isFalseGesture() {
        NotificationStackScrollLayoutController.C13777 r0 = (NotificationStackScrollLayoutController.C13777) this.mCallback;
        Objects.requireNonNull(r0);
        boolean onKeyguard = NotificationStackScrollLayoutController.this.mView.onKeyguard();
        if (this.mFalsingManager.isClassifierEnabled()) {
            if (!onKeyguard || !this.mFalsingManager.isFalseTouch(1)) {
                return false;
            }
        } else if (!onKeyguard || this.mTouchAboveFalsingThreshold) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003d, code lost:
        if (r0 != 4) goto L_0x019f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            boolean r0 = r8.mIsSwiping
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x002a
            boolean r0 = r8.mMenuRowIntercepting
            if (r0 != 0) goto L_0x002a
            boolean r0 = r8.mLongPressSent
            if (r0 != 0) goto L_0x002a
            com.android.systemui.SwipeHelper$Callback r0 = r8.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r0
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r0.getChildAtPosition(r9)
            if (r0 == 0) goto L_0x0026
            com.android.systemui.SwipeHelper$Callback r0 = r8.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r0
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r0.getChildAtPosition(r9)
            r8.mTouchedView = r0
            r8.onInterceptTouchEvent(r9)
            return r2
        L_0x0026:
            r8.cancelLongPress()
            return r1
        L_0x002a:
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            r0.addMovement(r9)
            int r0 = r9.getAction()
            r3 = 0
            if (r0 == r2) goto L_0x013d
            r4 = 2
            if (r0 == r4) goto L_0x0041
            r4 = 3
            if (r0 == r4) goto L_0x013d
            r4 = 4
            if (r0 == r4) goto L_0x0041
            goto L_0x019f
        L_0x0041:
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r8.mTouchedView
            if (r0 == 0) goto L_0x019f
            float r0 = r8.getPos(r9)
            float r4 = r8.mInitialTouchPos
            float r0 = r0 - r4
            float r4 = java.lang.Math.abs(r0)
            com.android.systemui.SwipeHelper$Callback r5 = r8.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r5 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r5
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.phone.StatusBar r5 = r5.mStatusBar
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mWakeUpComingFromTouch
            if (r5 == 0) goto L_0x0065
            r5 = 1069547520(0x3fc00000, float:1.5)
            goto L_0x0067
        L_0x0065:
            r5 = 1065353216(0x3f800000, float:1.0)
        L_0x0067:
            int r6 = r8.mFalsingThreshold
            float r6 = (float) r6
            float r6 = r6 * r5
            int r5 = (int) r6
            float r5 = (float) r5
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x0073
            r8.mTouchAboveFalsingThreshold = r2
        L_0x0073:
            boolean r5 = r8.mLongPressSent
            if (r5 == 0) goto L_0x00b2
            int r0 = r9.getClassification()
            if (r0 != r2) goto L_0x0084
            int r0 = r8.mTouchSlop
            float r0 = (float) r0
            float r1 = r8.mTouchSlopMultiplier
            float r0 = r0 * r1
            goto L_0x0087
        L_0x0084:
            int r0 = r8.mTouchSlop
            float r0 = (float) r0
        L_0x0087:
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x019f
            com.android.systemui.statusbar.notification.row.ExpandableView r8 = r8.mTouchedView
            boolean r0 = r8 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r0 == 0) goto L_0x019f
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            float r0 = r9.getX()
            float r9 = r9.getY()
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController r1 = r8.mDragController
            if (r1 == 0) goto L_0x019f
            android.graphics.Point r1 = new android.graphics.Point
            int r0 = (int) r0
            int r9 = (int) r9
            r1.<init>(r0, r9)
            r8.mTargetPoint = r1
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController r9 = r8.mDragController
            r9.startDragAndDrop(r8)
            goto L_0x019f
        L_0x00b2:
            com.android.systemui.SwipeHelper$Callback r9 = r8.mCallback
            com.android.systemui.statusbar.notification.row.ExpandableView r5 = r8.mTouchedView
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r9 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r9
            java.util.Objects.requireNonNull(r9)
            boolean r9 = r9.canChildBeDismissed(r5)
            if (r9 != 0) goto L_0x010d
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mTouchedView
            float r9 = r8.getSize(r9)
            r5 = 1050253722(0x3e99999a, float:0.3)
            float r5 = r5 * r9
            int r6 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r6 < 0) goto L_0x00d7
            if (r3 <= 0) goto L_0x00d5
            r0 = r5
            goto L_0x010d
        L_0x00d5:
            float r0 = -r5
            goto L_0x010d
        L_0x00d7:
            com.android.systemui.SwipeHelper$Callback r3 = r8.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r3 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r3 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r3 = r3.mSwipeHelper
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r3 = r3.getCurrentMenuRow()
            if (r3 == 0) goto L_0x00f0
            int r1 = r3.getMenuSnapTarget()
            int r1 = java.lang.Math.abs(r1)
        L_0x00f0:
            float r1 = (float) r1
            int r3 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x010d
            float r3 = java.lang.Math.signum(r0)
            float r3 = r3 * r1
            int r1 = (int) r3
            float r1 = (float) r1
            float r0 = r0 - r1
            float r0 = r0 / r9
            double r3 = (double) r0
            r6 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r3 = r3 * r6
            double r3 = java.lang.Math.sin(r3)
            float r9 = (float) r3
            float r5 = r5 * r9
            float r0 = r5 + r1
        L_0x010d:
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mTouchedView
            float r1 = r8.mTranslation
            float r1 = r1 + r0
            boolean r3 = r9 instanceof com.android.systemui.statusbar.notification.stack.SwipeableView
            if (r3 == 0) goto L_0x011b
            com.android.systemui.statusbar.notification.stack.SwipeableView r9 = (com.android.systemui.statusbar.notification.stack.SwipeableView) r9
            r9.setTranslation(r1)
        L_0x011b:
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mTouchedView
            boolean r1 = r8.mCanCurrViewBeDimissed
            float r3 = r8.getTranslation(r9)
            r8.updateSwipeProgressFromOffset(r9, r1, r3)
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r8 = (com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper) r8
            android.os.Handler r9 = r8.getHandler()
            java.lang.Runnable r1 = r8.getFalsingCheck()
            r9.removeCallbacks(r1)
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r8 = r8.getCurrentMenuRow()
            if (r8 == 0) goto L_0x019f
            r8.onTouchMove(r0)
            goto L_0x019f
        L_0x013d:
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r8.mTouchedView
            if (r0 != 0) goto L_0x0142
            goto L_0x019f
        L_0x0142:
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            r4 = 1000(0x3e8, float:1.401E-42)
            float r5 = r8.mDensityScale
            r6 = 1165623296(0x457a0000, float:4000.0)
            float r5 = r5 * r6
            r0.computeCurrentVelocity(r4, r5)
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            int r4 = r8.mSwipeDirection
            if (r4 != 0) goto L_0x0159
            float r0 = r0.getXVelocity()
            goto L_0x015d
        L_0x0159:
            float r0 = r0.getYVelocity()
        L_0x015d:
            com.android.systemui.statusbar.notification.row.ExpandableView r4 = r8.mTouchedView
            r8.getTranslation(r4)
            r5 = r8
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r5 = (com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper) r5
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r6 = r5.getCurrentMenuRow()
            if (r6 == 0) goto L_0x0173
            r6.onTouchEnd()
            r5.handleMenuRowSwipe(r9, r4, r0, r6)
            r4 = r2
            goto L_0x0174
        L_0x0173:
            r4 = r1
        L_0x0174:
            if (r4 != 0) goto L_0x019d
            boolean r9 = r8.isDismissGesture(r9)
            if (r9 == 0) goto L_0x0187
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mTouchedView
            boolean r3 = r8.swipedFastEnough()
            r3 = r3 ^ r2
            r8.dismissChild(r9, r0, r3)
            goto L_0x019a
        L_0x0187:
            com.android.systemui.SwipeHelper$Callback r9 = r8.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r9 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r9
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.classifier.FalsingCollector r9 = r9.mFalsingCollector
            r9.onNotificationStopDismissing()
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mTouchedView
            r8.snapChild(r9, r3, r0)
        L_0x019a:
            r9 = 0
            r8.mTouchedView = r9
        L_0x019d:
            r8.mIsSwiping = r1
        L_0x019f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public SwipeHelper(int i, Callback callback, Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags) {
        this.mCallback = callback;
        this.mHandler = new Handler();
        this.mSwipeDirection = i;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mPagingTouchSlop = (float) viewConfiguration.getScaledPagingTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
        this.mLongPressTimeout = (long) (((float) ViewConfiguration.getLongPressTimeout()) * 1.5f);
        this.mDensityScale = resources.getDisplayMetrics().density;
        this.mFalsingThreshold = resources.getDimensionPixelSize(C1777R.dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = resources.getBoolean(C1777R.bool.config_fadeDependingOnAmountSwiped);
        this.mFalsingManager = falsingManager;
        this.mFeatureFlags = featureFlags;
        this.mFlingAnimationUtils = new FlingAnimationUtils(resources.getDisplayMetrics(), ((float) 400) / 1000.0f);
    }

    public final void updateSwipeProgressFromOffset(View view, boolean z, float f) {
        float f2;
        float min = Math.min(Math.max(0.0f, Math.abs(f / getSize(view))), 1.0f);
        NotificationStackScrollLayoutController.C13777 r2 = (NotificationStackScrollLayoutController.C13777) this.mCallback;
        Objects.requireNonNull(r2);
        if (!(!NotificationStackScrollLayoutController.this.mFadeNotificationsOnDismiss) && z) {
            if (min == 0.0f || min == 1.0f) {
                view.setLayerType(0, (Paint) null);
            } else {
                view.setLayerType(2, (Paint) null);
            }
            if (this.mFadeDependingOnAmountSwiped) {
                f2 = Math.max(1.0f - min, 0.0f);
            } else {
                f2 = 1.0f - Math.max(0.0f, Math.min(1.0f, min / 0.5f));
            }
            view.setAlpha(f2);
        }
        RectF rectF = new RectF((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(rectF);
            view.invalidate((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
        }
    }
}
