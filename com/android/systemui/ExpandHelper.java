package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.FloatProperty;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.policy.ScrollAdapter;
import java.util.Objects;

public final class ExpandHelper implements Gefingerpoken {
    public static final C06301 VIEW_SCALER_HEIGHT_PROPERTY = new FloatProperty<ViewScaler>() {
        public final Object get(Object obj) {
            ViewScaler viewScaler = (ViewScaler) obj;
            Objects.requireNonNull(viewScaler);
            ExpandableView expandableView = viewScaler.mView;
            Objects.requireNonNull(expandableView);
            return Float.valueOf((float) expandableView.mActualHeight);
        }

        public final void setValue(Object obj, float f) {
            ((ViewScaler) obj).setHeight(f);
        }
    };
    public Callback mCallback;
    public Context mContext;
    public float mCurrentHeight;
    public boolean mEnabled = true;
    public View mEventSource;
    public boolean mExpanding;
    public int mExpansionStyle = 0;
    public FlingAnimationUtils mFlingAnimationUtils;
    public int mGravity;
    public boolean mHasPopped;
    public float mInitialTouchFocusY;
    public float mInitialTouchSpan;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public float mLastFocusY;
    public float mLastMotionY;
    public float mLastSpanY;
    public float mNaturalHeight;
    public float mOldHeight;
    public boolean mOnlyMovements;
    public float mPullGestureMinXSpan;
    public ExpandableView mResizedView;
    public ScaleGestureDetector mSGD;
    public ObjectAnimator mScaleAnimation;
    public C06312 mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        public final void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }

        public final boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            ExpandHelper expandHelper = ExpandHelper.this;
            if (!expandHelper.mOnlyMovements) {
                expandHelper.startExpanding(expandHelper.mResizedView, 4);
            }
            return ExpandHelper.this.mExpanding;
        }
    };
    public ViewScaler mScaler;
    public ScrollAdapter mScrollAdapter;
    public final float mSlopMultiplier;
    public int mSmallSize;
    public final int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public boolean mWatchingForPull;

    public interface Callback {
    }

    public class ViewScaler {
        public ExpandableView mView;

        public ViewScaler() {
        }

        public final void setHeight(float f) {
            ExpandableView expandableView = this.mView;
            Objects.requireNonNull(expandableView);
            expandableView.setActualHeight((int) f, true);
            ExpandHelper.this.mCurrentHeight = f;
        }
    }

    @VisibleForTesting
    public void finishExpanding(boolean z, float f) {
        finishExpanding(z, f, true);
    }

    public final boolean isInside(NotificationStackScrollLayout notificationStackScrollLayout, float f, float f2) {
        boolean z;
        boolean z2;
        if (notificationStackScrollLayout == null) {
            return false;
        }
        View view = this.mEventSource;
        if (view != null) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            f += (float) iArr[0];
            f2 += (float) iArr[1];
        }
        int[] iArr2 = new int[2];
        notificationStackScrollLayout.getLocationOnScreen(iArr2);
        float f3 = f - ((float) iArr2[0]);
        float f4 = f2 - ((float) iArr2[1]);
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return false;
        }
        if (f3 < ((float) notificationStackScrollLayout.getWidth())) {
            z = true;
        } else {
            z = false;
        }
        if (f4 < ((float) notificationStackScrollLayout.getHeight())) {
            z2 = true;
        } else {
            z2 = false;
        }
        return z & z2;
    }

    public final ExpandableView findView(float f, float f2) {
        View view = this.mEventSource;
        if (view != null) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            NotificationStackScrollLayout.C135711 r3 = (NotificationStackScrollLayout.C135711) this.mCallback;
            Objects.requireNonNull(r3);
            return NotificationStackScrollLayout.this.getChildAtRawPosition(f + ((float) iArr[0]), f2 + ((float) iArr[1]));
        }
        NotificationStackScrollLayout.C135711 r32 = (NotificationStackScrollLayout.C135711) this.mCallback;
        Objects.requireNonNull(r32);
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
        boolean z = NotificationStackScrollLayout.SPEW;
        Objects.requireNonNull(notificationStackScrollLayout);
        return notificationStackScrollLayout.getChildAtPosition(f, f2, true, true);
    }

    public final void finishExpanding(boolean z, float f, boolean z2) {
        final boolean z3;
        if (this.mExpanding) {
            ViewScaler viewScaler = this.mScaler;
            Objects.requireNonNull(viewScaler);
            ExpandableView expandableView = viewScaler.mView;
            Objects.requireNonNull(expandableView);
            float f2 = (float) expandableView.mActualHeight;
            float f3 = this.mOldHeight;
            float f4 = (float) this.mSmallSize;
            boolean z4 = true;
            final boolean z5 = f3 == f4;
            if (!z) {
                int i = (f2 > f3 ? 1 : (f2 == f3 ? 0 : -1));
                z3 = (!z5 ? i >= 0 || f > 0.0f : i > 0 && f >= 0.0f) | (this.mNaturalHeight == f4);
            } else {
                z3 = !z5;
            }
            if (this.mScaleAnimation.isRunning()) {
                this.mScaleAnimation.cancel();
            }
            ((NotificationStackScrollLayout.C135711) this.mCallback).expansionStateChanged(false);
            ViewScaler viewScaler2 = this.mScaler;
            Objects.requireNonNull(viewScaler2);
            Callback callback = ExpandHelper.this.mCallback;
            ExpandableView expandableView2 = viewScaler2.mView;
            Objects.requireNonNull((NotificationStackScrollLayout.C135711) callback);
            int maxContentHeight = expandableView2.getMaxContentHeight();
            if (!z3) {
                maxContentHeight = this.mSmallSize;
            }
            float f5 = (float) maxContentHeight;
            int i2 = (f5 > f2 ? 1 : (f5 == f2 ? 0 : -1));
            if (i2 == 0 || !this.mEnabled || !z2) {
                if (i2 != 0) {
                    this.mScaler.setHeight(f5);
                }
                ((NotificationStackScrollLayout.C135711) this.mCallback).setUserExpandedChild(this.mResizedView, z3);
                ((NotificationStackScrollLayout.C135711) this.mCallback).setUserLockedChild(this.mResizedView, false);
                ViewScaler viewScaler3 = this.mScaler;
                Objects.requireNonNull(viewScaler3);
                viewScaler3.mView = null;
                if (z5) {
                    InteractionJankMonitor.getInstance().end(3);
                }
            } else {
                this.mScaleAnimation.setFloatValues(new float[]{f5});
                this.mScaleAnimation.setupStartValues();
                final ExpandableView expandableView3 = this.mResizedView;
                this.mScaleAnimation.addListener(new AnimatorListenerAdapter() {
                    public boolean mCancelled;

                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        if (!this.mCancelled) {
                            ((NotificationStackScrollLayout.C135711) ExpandHelper.this.mCallback).setUserExpandedChild(expandableView3, z3);
                            ExpandHelper expandHelper = ExpandHelper.this;
                            if (!expandHelper.mExpanding) {
                                ViewScaler viewScaler = expandHelper.mScaler;
                                Objects.requireNonNull(viewScaler);
                                viewScaler.mView = null;
                            }
                        } else {
                            Callback callback = ExpandHelper.this.mCallback;
                            View view = expandableView3;
                            Objects.requireNonNull((NotificationStackScrollLayout.C135711) callback);
                            if (view instanceof ExpandableNotificationRow) {
                                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                                Objects.requireNonNull(expandableNotificationRow);
                                expandableNotificationRow.mGroupExpansionChanging = false;
                            }
                        }
                        ((NotificationStackScrollLayout.C135711) ExpandHelper.this.mCallback).setUserLockedChild(expandableView3, false);
                        ExpandHelper.this.mScaleAnimation.removeListener(this);
                        if (z5) {
                            InteractionJankMonitor.getInstance().end(3);
                        }
                    }
                });
                if (f < 0.0f) {
                    z4 = false;
                }
                if (z3 != z4) {
                    f = 0.0f;
                }
                this.mFlingAnimationUtils.apply(this.mScaleAnimation, f2, f5, f);
                this.mScaleAnimation.start();
            }
            this.mExpanding = false;
            this.mExpansionStyle = 0;
        }
    }

    public final void maybeRecycleVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            return;
        }
        if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0053, code lost:
        if (r0 != 3) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x011c, code lost:
        if (r0 != false) goto L_0x0120;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.mEnabled
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r7.trackVelocity(r8)
            int r0 = r8.getAction()
            android.view.ScaleGestureDetector r2 = r7.mSGD
            r2.onTouchEvent(r8)
            android.view.ScaleGestureDetector r2 = r7.mSGD
            float r2 = r2.getFocusX()
            int r2 = (int) r2
            android.view.ScaleGestureDetector r3 = r7.mSGD
            float r3 = r3.getFocusY()
            int r3 = (int) r3
            float r3 = (float) r3
            r7.mInitialTouchFocusY = r3
            android.view.ScaleGestureDetector r4 = r7.mSGD
            float r4 = r4.getCurrentSpan()
            r7.mInitialTouchSpan = r4
            float r5 = r7.mInitialTouchFocusY
            r7.mLastFocusY = r5
            r7.mLastSpanY = r4
            boolean r4 = r7.mExpanding
            r5 = 1
            if (r4 == 0) goto L_0x0040
            float r0 = r8.getRawY()
            r7.mLastMotionY = r0
            r7.maybeRecycleVelocityTracker(r8)
            return r5
        L_0x0040:
            r4 = 2
            if (r0 != r4) goto L_0x0049
            int r6 = r7.mExpansionStyle
            r6 = r6 & r5
            if (r6 == 0) goto L_0x0049
            return r5
        L_0x0049:
            r0 = r0 & 255(0xff, float:3.57E-43)
            r6 = 0
            if (r0 == 0) goto L_0x00fd
            r2 = 3
            if (r0 == r5) goto L_0x00df
            if (r0 == r4) goto L_0x0057
            if (r0 == r2) goto L_0x00df
            goto L_0x0145
        L_0x0057:
            android.view.ScaleGestureDetector r0 = r7.mSGD
            float r0 = r0.getCurrentSpanX()
            float r2 = r7.mPullGestureMinXSpan
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0078
            android.view.ScaleGestureDetector r2 = r7.mSGD
            float r2 = r2.getCurrentSpanY()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0078
            boolean r0 = r7.mExpanding
            if (r0 != 0) goto L_0x0078
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r7.mResizedView
            r7.startExpanding(r0, r4)
            r7.mWatchingForPull = r1
        L_0x0078:
            boolean r0 = r7.mWatchingForPull
            if (r0 == 0) goto L_0x0145
            float r0 = r8.getRawY()
            float r2 = r7.mInitialTouchY
            float r0 = r0 - r2
            float r2 = r8.getRawX()
            float r3 = r7.mInitialTouchX
            float r2 = r2 - r3
            int r3 = r8.getClassification()
            if (r3 != r5) goto L_0x0097
            int r3 = r7.mTouchSlop
            float r3 = (float) r3
            float r4 = r7.mSlopMultiplier
            float r3 = r3 * r4
            goto L_0x009a
        L_0x0097:
            int r3 = r7.mTouchSlop
            float r3 = (float) r3
        L_0x009a:
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0145
            float r2 = java.lang.Math.abs(r2)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0145
            r7.mWatchingForPull = r1
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r7.mResizedView
            if (r0 == 0) goto L_0x0145
            int r2 = r0.getIntrinsicHeight()
            int r3 = r0.getMaxContentHeight()
            if (r2 != r3) goto L_0x00c4
            boolean r2 = r0.isSummaryWithChildren()
            if (r2 == 0) goto L_0x00c2
            boolean r0 = r0.areChildrenExpanded()
            if (r0 == 0) goto L_0x00c4
        L_0x00c2:
            r0 = r5
            goto L_0x00c5
        L_0x00c4:
            r0 = r1
        L_0x00c5:
            if (r0 != 0) goto L_0x0145
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r7.mResizedView
            boolean r0 = r7.startExpanding(r0, r5)
            if (r0 == 0) goto L_0x0145
            float r0 = r8.getRawY()
            r7.mLastMotionY = r0
            float r0 = r8.getRawY()
            r7.mInitialTouchY = r0
            r7.mHasPopped = r1
            goto L_0x0145
        L_0x00df:
            int r0 = r8.getActionMasked()
            if (r0 != r2) goto L_0x00e6
            r1 = r5
        L_0x00e6:
            android.view.VelocityTracker r0 = r7.mVelocityTracker
            if (r0 == 0) goto L_0x00f6
            r2 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r2)
            android.view.VelocityTracker r0 = r7.mVelocityTracker
            float r0 = r0.getYVelocity()
            goto L_0x00f7
        L_0x00f6:
            r0 = 0
        L_0x00f7:
            r7.finishExpanding(r1, r0)
            r7.mResizedView = r6
            goto L_0x0145
        L_0x00fd:
            com.android.systemui.statusbar.policy.ScrollAdapter r0 = r7.mScrollAdapter
            if (r0 == 0) goto L_0x011f
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13637) r0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            float r4 = (float) r2
            boolean r0 = r7.isInside(r0, r4, r3)
            if (r0 == 0) goto L_0x011f
            com.android.systemui.statusbar.policy.ScrollAdapter r0 = r7.mScrollAdapter
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13637) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r0 = r0.mOwnScrollY
            if (r0 != 0) goto L_0x011b
            r0 = r5
            goto L_0x011c
        L_0x011b:
            r0 = r1
        L_0x011c:
            if (r0 == 0) goto L_0x011f
            goto L_0x0120
        L_0x011f:
            r5 = r1
        L_0x0120:
            r7.mWatchingForPull = r5
            float r0 = (float) r2
            com.android.systemui.statusbar.notification.row.ExpandableView r0 = r7.findView(r0, r3)
            r7.mResizedView = r0
            if (r0 == 0) goto L_0x0139
            com.android.systemui.ExpandHelper$Callback r2 = r7.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$11 r2 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C135711) r2
            boolean r0 = r2.canChildBeExpanded(r0)
            if (r0 != 0) goto L_0x0139
            r7.mResizedView = r6
            r7.mWatchingForPull = r1
        L_0x0139:
            float r0 = r8.getRawY()
            r7.mInitialTouchY = r0
            float r0 = r8.getRawX()
            r7.mInitialTouchX = r0
        L_0x0145:
            float r0 = r8.getRawY()
            r7.mLastMotionY = r0
            r7.maybeRecycleVelocityTracker(r8)
            boolean r7 = r7.mExpanding
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ExpandHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        float f;
        boolean z3;
        float f2;
        boolean z4;
        if (!this.mEnabled && !this.mExpanding) {
            return false;
        }
        trackVelocity(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        this.mSGD.onTouchEvent(motionEvent);
        int focusX = (int) this.mSGD.getFocusX();
        int focusY = (int) this.mSGD.getFocusY();
        if (this.mOnlyMovements) {
            this.mLastMotionY = motionEvent.getRawY();
            return false;
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.mWatchingForPull) {
                        float rawY = motionEvent.getRawY() - this.mInitialTouchY;
                        float rawX = motionEvent.getRawX() - this.mInitialTouchX;
                        if (motionEvent.getClassification() == 1) {
                            f2 = ((float) this.mTouchSlop) * this.mSlopMultiplier;
                        } else {
                            f2 = (float) this.mTouchSlop;
                        }
                        if (rawY > f2 && rawY > Math.abs(rawX)) {
                            this.mWatchingForPull = false;
                            ExpandableView expandableView = this.mResizedView;
                            if (expandableView != null) {
                                if (expandableView.getIntrinsicHeight() != expandableView.getMaxContentHeight() || (expandableView.isSummaryWithChildren() && !expandableView.areChildrenExpanded())) {
                                    z4 = false;
                                } else {
                                    z4 = true;
                                }
                                if (!z4 && startExpanding(this.mResizedView, 1)) {
                                    this.mInitialTouchY = motionEvent.getRawY();
                                    this.mLastMotionY = motionEvent.getRawY();
                                    this.mHasPopped = false;
                                }
                            }
                        }
                    }
                    boolean z5 = this.mExpanding;
                    if (z5 && (this.mExpansionStyle & 1) != 0) {
                        float rawY2 = (motionEvent.getRawY() - this.mLastMotionY) + this.mCurrentHeight;
                        int i = this.mSmallSize;
                        float f3 = (float) i;
                        if (rawY2 >= f3) {
                            f3 = rawY2;
                        }
                        float f4 = this.mNaturalHeight;
                        if (f3 > f4) {
                            f3 = f4;
                        }
                        if (rawY2 > f4) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (rawY2 < ((float) i)) {
                            z3 = true;
                        }
                        if (!this.mHasPopped) {
                            View view = this.mEventSource;
                            if (view != null) {
                                view.performHapticFeedback(1);
                            }
                            this.mHasPopped = true;
                        }
                        this.mScaler.setHeight(f3);
                        this.mLastMotionY = motionEvent.getRawY();
                        if (z3) {
                            ((NotificationStackScrollLayout.C135711) this.mCallback).expansionStateChanged(false);
                        } else {
                            ((NotificationStackScrollLayout.C135711) this.mCallback).expansionStateChanged(true);
                        }
                        return true;
                    } else if (z5) {
                        updateExpansion();
                        this.mLastMotionY = motionEvent.getRawY();
                        return true;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5 || actionMasked == 6) {
                        this.mInitialTouchY = (this.mSGD.getFocusY() - this.mLastFocusY) + this.mInitialTouchY;
                        this.mInitialTouchSpan = (this.mSGD.getCurrentSpan() - this.mLastSpanY) + this.mInitialTouchSpan;
                    }
                }
            }
            if (!this.mEnabled || motionEvent.getActionMasked() == 3) {
                z2 = true;
            } else {
                z2 = false;
            }
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(1000);
                f = this.mVelocityTracker.getYVelocity();
            } else {
                f = 0.0f;
            }
            finishExpanding(z2, f);
            this.mResizedView = null;
        } else {
            ScrollAdapter scrollAdapter = this.mScrollAdapter;
            if (scrollAdapter == null || !isInside(NotificationStackScrollLayout.this, (float) focusX, (float) focusY)) {
                z = false;
            } else {
                z = true;
            }
            this.mWatchingForPull = z;
            this.mResizedView = findView((float) focusX, (float) focusY);
            this.mInitialTouchX = motionEvent.getRawX();
            this.mInitialTouchY = motionEvent.getRawY();
        }
        this.mLastMotionY = motionEvent.getRawY();
        maybeRecycleVelocityTracker(motionEvent);
        if (this.mResizedView != null) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean startExpanding(ExpandableView expandableView, int i) {
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        this.mExpansionStyle = i;
        if (this.mExpanding && expandableView == this.mResizedView) {
            return true;
        }
        this.mExpanding = true;
        ((NotificationStackScrollLayout.C135711) this.mCallback).expansionStateChanged(true);
        ((NotificationStackScrollLayout.C135711) this.mCallback).setUserLockedChild(expandableView, true);
        ViewScaler viewScaler = this.mScaler;
        Objects.requireNonNull(viewScaler);
        viewScaler.mView = expandableView;
        ViewScaler viewScaler2 = this.mScaler;
        Objects.requireNonNull(viewScaler2);
        ExpandableView expandableView2 = viewScaler2.mView;
        Objects.requireNonNull(expandableView2);
        float f = (float) expandableView2.mActualHeight;
        this.mOldHeight = f;
        this.mCurrentHeight = f;
        if (((NotificationStackScrollLayout.C135711) this.mCallback).canChildBeExpanded(expandableView)) {
            ViewScaler viewScaler3 = this.mScaler;
            Objects.requireNonNull(viewScaler3);
            Callback callback = ExpandHelper.this.mCallback;
            ExpandableView expandableView3 = viewScaler3.mView;
            Objects.requireNonNull((NotificationStackScrollLayout.C135711) callback);
            this.mNaturalHeight = (float) expandableView3.getMaxContentHeight();
            this.mSmallSize = expandableView.getCollapsedHeight();
        } else {
            this.mNaturalHeight = this.mOldHeight;
        }
        InteractionJankMonitor.getInstance().begin(expandableView, 3);
        return true;
    }

    @VisibleForTesting
    public void updateExpansion() {
        float f;
        float currentSpan = (this.mSGD.getCurrentSpan() - this.mInitialTouchSpan) * 1.0f;
        float focusY = (this.mSGD.getFocusY() - this.mInitialTouchFocusY) * 1.0f;
        if (this.mGravity == 80) {
            f = -1.0f;
        } else {
            f = 1.0f;
        }
        float f2 = focusY * f;
        float abs = Math.abs(currentSpan) + Math.abs(f2) + 1.0f;
        float abs2 = ((Math.abs(currentSpan) * currentSpan) / abs) + ((Math.abs(f2) * f2) / abs) + this.mOldHeight;
        float f3 = (float) this.mSmallSize;
        if (abs2 < f3) {
            abs2 = f3;
        }
        float f4 = this.mNaturalHeight;
        if (abs2 > f4) {
            abs2 = f4;
        }
        this.mScaler.setHeight(abs2);
        this.mLastFocusY = this.mSGD.getFocusY();
        this.mLastSpanY = this.mSGD.getCurrentSpan();
    }

    public ExpandHelper(Context context, Callback callback, int i) {
        this.mSmallSize = i;
        this.mContext = context;
        this.mCallback = callback;
        ViewScaler viewScaler = new ViewScaler();
        this.mScaler = viewScaler;
        this.mGravity = 48;
        this.mScaleAnimation = ObjectAnimator.ofFloat(viewScaler, VIEW_SCALER_HEIGHT_PROPERTY, new float[]{0.0f});
        this.mPullGestureMinXSpan = this.mContext.getResources().getDimension(C1777R.dimen.pull_span_min);
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        this.mSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
        this.mSGD = new ScaleGestureDetector(context, this.mScaleGestureListener);
        this.mFlingAnimationUtils = new FlingAnimationUtils(this.mContext.getResources().getDisplayMetrics(), 0.3f);
    }

    public final void trackVelocity(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            } else {
                velocityTracker.clear();
            }
            this.mVelocityTracker.addMovement(motionEvent);
        } else if (actionMasked == 2) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
        }
    }

    @VisibleForTesting
    public ObjectAnimator getScaleAnimation() {
        return this.mScaleAnimation;
    }
}
