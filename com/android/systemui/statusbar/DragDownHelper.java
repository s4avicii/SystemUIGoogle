package com.android.systemui.statusbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class DragDownHelper implements Gefingerpoken {
    public float dragDownAmountOnStart;
    public final LockscreenShadeTransitionController dragDownCallback;
    public boolean draggedFarEnough;
    public ExpandHelper.Callback expandCallback;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public View host;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isDraggingDown;
    public float lastHeight;
    public int minDragDistance;
    public float slopMultiplier;
    public ExpandableView startingChild;
    public final int[] temp2 = new int[2];
    public float touchSlop;

    public final void captureStartingChild(float f, float f2) {
        if (this.startingChild == null) {
            View view = this.host;
            NotificationStackScrollLayout.C135711 r1 = null;
            if (view == null) {
                view = null;
            }
            view.getLocationOnScreen(this.temp2);
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            int[] iArr = this.temp2;
            NotificationStackScrollLayout.C135711 r0 = (NotificationStackScrollLayout.C135711) callback;
            Objects.requireNonNull(r0);
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(f + ((float) iArr[0]), f2 + ((float) iArr[1]));
            this.startingChild = childAtRawPosition;
            if (childAtRawPosition == null) {
                return;
            }
            if (this.dragDownCallback.mo11566x229f8ab3(childAtRawPosition)) {
                ExpandHelper.Callback callback2 = this.expandCallback;
                if (callback2 != null) {
                    r1 = callback2;
                }
                r1.setUserLockedChild(this.startingChild, true);
                return;
            }
            this.startingChild = null;
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        float f;
        if (!this.isDraggingDown) {
            return false;
        }
        motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        float f2 = 0.0f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = null;
        if (actionMasked == 1) {
            if (!this.falsingManager.isUnlockingDisabled()) {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
                Objects.requireNonNull(lockscreenShadeTransitionController);
                if (lockscreenShadeTransitionController.statusBarStateController.getState() == 1) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && (this.falsingManager.isFalseTouch(2) || !this.draggedFarEnough)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2 && this.dragDownCallback.mo11560x90582e2c()) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.dragDownCallback;
                    ExpandableView expandableView = this.startingChild;
                    int i = (int) (y - this.initialTouchY);
                    Objects.requireNonNull(lockscreenShadeTransitionController2);
                    if (lockscreenShadeTransitionController2.mo11560x90582e2c()) {
                        C1153xbbc01eb0 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 = new C1153xbbc01eb0(lockscreenShadeTransitionController2);
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController2.nsslController;
                        if (notificationStackScrollLayoutController2 == null) {
                            notificationStackScrollLayoutController2 = null;
                        }
                        Objects.requireNonNull(notificationStackScrollLayoutController2);
                        if (notificationStackScrollLayoutController2.mDynamicPrivacyController.isInLockedDownShade()) {
                            lockscreenShadeTransitionController2.logger.logDraggedDownLockDownShade(expandableView);
                            lockscreenShadeTransitionController2.statusBarStateController.setLeaveOpenOnKeyguardHide(true);
                            StatusBar statusBar = lockscreenShadeTransitionController2.statusbar;
                            if (statusBar == null) {
                                statusBar = null;
                            }
                            statusBar.dismissKeyguardThenExecute(new LockscreenShadeTransitionController$onDraggedDown$1(lockscreenShadeTransitionController2), lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1, false);
                        } else {
                            lockscreenShadeTransitionController2.logger.logDraggedDown(expandableView, i);
                            AmbientState ambientState = lockscreenShadeTransitionController2.ambientState;
                            Objects.requireNonNull(ambientState);
                            if (!ambientState.mDozing || expandableView != null) {
                                lockscreenShadeTransitionController2.goToLockedShadeInternal(expandableView, new C1152x21fef5f(expandableView, lockscreenShadeTransitionController2), lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1);
                            }
                        }
                    } else {
                        lockscreenShadeTransitionController2.logger.logUnSuccessfulDragDown(expandableView);
                        lockscreenShadeTransitionController2.setDragDownAmountAnimated(0.0f, 0, (Function0<Unit>) null);
                    }
                    ExpandableView expandableView2 = this.startingChild;
                    if (expandableView2 != null) {
                        ExpandHelper.Callback callback = this.expandCallback;
                        if (callback == null) {
                            callback = null;
                        }
                        ((NotificationStackScrollLayout.C135711) callback).setUserLockedChild(expandableView2, false);
                        this.startingChild = null;
                    }
                    this.isDraggingDown = false;
                }
            }
            stopDragging();
            return false;
        } else if (actionMasked == 2) {
            float f3 = this.initialTouchY;
            this.lastHeight = y - f3;
            captureStartingChild(this.initialTouchX, f3);
            this.dragDownCallback.mo11568xcfc05636(this.lastHeight + this.dragDownAmountOnStart);
            ExpandableView expandableView3 = this.startingChild;
            if (expandableView3 != null) {
                float f4 = this.lastHeight;
                if (f4 >= 0.0f) {
                    f2 = f4;
                }
                boolean isContentExpandable = expandableView3.isContentExpandable();
                if (isContentExpandable) {
                    f = 0.5f;
                } else {
                    f = 0.15f;
                }
                float f5 = f2 * f;
                if (isContentExpandable && ((float) expandableView3.getCollapsedHeight()) + f5 > ((float) expandableView3.getMaxContentHeight())) {
                    f5 -= ((((float) expandableView3.getCollapsedHeight()) + f5) - ((float) expandableView3.getMaxContentHeight())) * 0.85f;
                }
                expandableView3.setActualHeight((int) (((float) expandableView3.getCollapsedHeight()) + f5), true);
            }
            if (this.lastHeight > ((float) this.minDragDistance)) {
                if (!this.draggedFarEnough) {
                    this.draggedFarEnough = true;
                    LockscreenShadeTransitionController lockscreenShadeTransitionController3 = this.dragDownCallback;
                    Objects.requireNonNull(lockscreenShadeTransitionController3);
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = lockscreenShadeTransitionController3.nsslController;
                    if (notificationStackScrollLayoutController3 != null) {
                        notificationStackScrollLayoutController = notificationStackScrollLayoutController3;
                    }
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    notificationStackScrollLayoutController.mView.setDimmed(false, true);
                }
            } else if (this.draggedFarEnough) {
                this.draggedFarEnough = false;
                LockscreenShadeTransitionController lockscreenShadeTransitionController4 = this.dragDownCallback;
                Objects.requireNonNull(lockscreenShadeTransitionController4);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = lockscreenShadeTransitionController4.nsslController;
                if (notificationStackScrollLayoutController4 != null) {
                    notificationStackScrollLayoutController = notificationStackScrollLayoutController4;
                }
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationStackScrollLayoutController.mView.setDimmed(true, true);
            }
            return true;
        } else if (actionMasked == 3) {
            stopDragging();
            return false;
        }
        return false;
    }

    public final void stopDragging() {
        this.falsingCollector.onNotificationStopDraggingDown();
        ExpandableView expandableView = this.startingChild;
        if (expandableView != null) {
            if (expandableView.mActualHeight == expandableView.getCollapsedHeight()) {
                ExpandHelper.Callback callback = this.expandCallback;
                if (callback == null) {
                    callback = null;
                }
                ((NotificationStackScrollLayout.C135711) callback).setUserLockedChild(expandableView, false);
            } else {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(expandableView, "actualHeight", new int[]{expandableView.mActualHeight, expandableView.getCollapsedHeight()});
                ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                ofInt.setDuration(375);
                ofInt.addListener(new DragDownHelper$cancelChildExpansion$1(this, expandableView));
                ofInt.start();
            }
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        Objects.requireNonNull(lockscreenShadeTransitionController);
        lockscreenShadeTransitionController.logger.logDragDownAborted();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController);
        notificationStackScrollLayoutController.mView.setDimmed(true, true);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController2 == null) {
            notificationStackScrollLayoutController2 = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mScroller.abortAnimation();
        notificationStackScrollLayout.setOwnScrollY(0, false);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController3 == null) {
            notificationStackScrollLayoutController3 = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController3);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController3.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        notificationStackScrollLayout2.mCheckForLeavebehind = true;
        lockscreenShadeTransitionController.setDragDownAmountAnimated(0.0f, 0, (Function0<Unit>) null);
    }

    public DragDownHelper(FalsingManager falsingManager2, FalsingCollector falsingCollector2, LockscreenShadeTransitionController lockscreenShadeTransitionController, Context context) {
        this.falsingManager = falsingManager2;
        this.falsingCollector = falsingCollector2;
        this.dragDownCallback = lockscreenShadeTransitionController;
        updateResources(context);
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = null;
        if (actionMasked == 0) {
            this.draggedFarEnough = false;
            this.isDraggingDown = false;
            this.startingChild = null;
            this.initialTouchY = y;
            this.initialTouchX = x;
        } else if (actionMasked == 2) {
            float f2 = y - this.initialTouchY;
            if (motionEvent.getClassification() == 1) {
                f = this.touchSlop * this.slopMultiplier;
            } else {
                f = this.touchSlop;
            }
            if (f2 > f && f2 > Math.abs(x - this.initialTouchX)) {
                this.falsingCollector.onNotificationStartDraggingDown();
                this.isDraggingDown = true;
                captureStartingChild(this.initialTouchX, this.initialTouchY);
                this.initialTouchY = y;
                this.initialTouchX = x;
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
                ExpandableView expandableView = this.startingChild;
                Objects.requireNonNull(lockscreenShadeTransitionController);
                lockscreenShadeTransitionController.logger.logDragDownStarted(expandableView);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
                if (notificationStackScrollLayoutController2 == null) {
                    notificationStackScrollLayoutController2 = null;
                }
                Objects.requireNonNull(notificationStackScrollLayoutController2);
                notificationStackScrollLayoutController2.mView.cancelLongPress();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = lockscreenShadeTransitionController.nsslController;
                if (notificationStackScrollLayoutController3 != null) {
                    notificationStackScrollLayoutController = notificationStackScrollLayoutController3;
                }
                notificationStackScrollLayoutController.checkSnoozeLeavebehind();
                ValueAnimator valueAnimator = lockscreenShadeTransitionController.dragDownAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    lockscreenShadeTransitionController.logger.logAnimationCancelled(false);
                    valueAnimator.cancel();
                }
                LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.dragDownCallback;
                Objects.requireNonNull(lockscreenShadeTransitionController2);
                this.dragDownAmountOnStart = lockscreenShadeTransitionController2.dragDownAmount;
                if (this.startingChild != null || this.dragDownCallback.mo11565x7adb072c()) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final void updateResources(Context context) {
        this.minDragDistance = context.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_drag_down_min_distance);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = (float) viewConfiguration.getScaledTouchSlop();
        this.slopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
    }
}
