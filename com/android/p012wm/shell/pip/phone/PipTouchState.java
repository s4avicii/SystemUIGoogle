package com.android.p012wm.shell.pip.phone;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.common.ShellExecutor;

/* renamed from: com.android.wm.shell.pip.phone.PipTouchState */
public final class PipTouchState {
    @VisibleForTesting
    public static final long DOUBLE_TAP_TIMEOUT = 200;
    public int mActivePointerId;
    public boolean mAllowDraggingOffscreen = false;
    public boolean mAllowTouches = true;
    public final Runnable mDoubleTapTimeoutCallback;
    public final PointF mDownDelta = new PointF();
    public final PointF mDownTouch = new PointF();
    public long mDownTouchTime = 0;
    public final Runnable mHoverExitTimeoutCallback;
    public boolean mIsDoubleTap = false;
    public boolean mIsDragging = false;
    public boolean mIsUserInteracting = false;
    public boolean mIsWaitingForDoubleTap = false;
    public final PointF mLastDelta = new PointF();
    public long mLastDownTouchTime = 0;
    public final PointF mLastTouch = new PointF();
    public int mLastTouchDisplayId = -1;
    public final ShellExecutor mMainExecutor;
    public boolean mPreviouslyDragging = false;
    public boolean mStartedDragging = false;
    public long mUpTouchTime = 0;
    public final PointF mVelocity = new PointF();
    public VelocityTracker mVelocityTracker;
    public final ViewConfiguration mViewConfig;

    public final void reset() {
        this.mAllowDraggingOffscreen = false;
        this.mIsDragging = false;
        this.mStartedDragging = false;
        this.mIsUserInteracting = false;
        this.mLastTouchDisplayId = -1;
    }

    public final void addMovementToVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker != null) {
            float rawX = motionEvent.getRawX() - motionEvent.getX();
            float rawY = motionEvent.getRawY() - motionEvent.getY();
            motionEvent.offsetLocation(rawX, rawY);
            this.mVelocityTracker.addMovement(motionEvent);
            motionEvent.offsetLocation(-rawX, -rawY);
        }
    }

    @VisibleForTesting
    public long getDoubleTapTimeoutCallbackDelay() {
        if (this.mIsWaitingForDoubleTap) {
            return Math.max(0, 200 - (this.mUpTouchTime - this.mDownTouchTime));
        }
        return -1;
    }

    @VisibleForTesting
    public void scheduleHoverExitTimeoutCallback() {
        this.mMainExecutor.removeCallbacks(this.mHoverExitTimeoutCallback);
        this.mMainExecutor.executeDelayed(this.mHoverExitTimeoutCallback, 50);
    }

    public PipTouchState(ViewConfiguration viewConfiguration, CarrierTextManager$$ExternalSyntheticLambda0 carrierTextManager$$ExternalSyntheticLambda0, TaskView$$ExternalSyntheticLambda3 taskView$$ExternalSyntheticLambda3, ShellExecutor shellExecutor) {
        this.mViewConfig = viewConfiguration;
        this.mDoubleTapTimeoutCallback = carrierTextManager$$ExternalSyntheticLambda0;
        this.mHoverExitTimeoutCallback = taskView$$ExternalSyntheticLambda3;
        this.mMainExecutor = shellExecutor;
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        this.mLastTouchDisplayId = motionEvent.getDisplayId();
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = false;
        boolean z3 = true;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked != 6) {
                            if (actionMasked == 11) {
                                this.mMainExecutor.removeCallbacks(this.mHoverExitTimeoutCallback);
                                return;
                            }
                            return;
                        } else if (this.mIsUserInteracting) {
                            addMovementToVelocityTracker(motionEvent);
                            int actionIndex = motionEvent.getActionIndex();
                            if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                                if (actionIndex == 0) {
                                    z2 = true;
                                }
                                this.mActivePointerId = motionEvent.getPointerId(z2 ? 1 : 0);
                                this.mLastTouch.set(motionEvent.getRawX(z2), motionEvent.getRawY(z2));
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    }
                } else if (this.mIsUserInteracting) {
                    addMovementToVelocityTracker(motionEvent);
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid active pointer id on MOVE: ");
                        m.append(this.mActivePointerId);
                        Log.e("PipTouchState", m.toString());
                        return;
                    }
                    float rawX = motionEvent.getRawX(findPointerIndex);
                    float rawY = motionEvent.getRawY(findPointerIndex);
                    PointF pointF = this.mLastDelta;
                    PointF pointF2 = this.mLastTouch;
                    pointF.set(rawX - pointF2.x, rawY - pointF2.y);
                    PointF pointF3 = this.mDownDelta;
                    PointF pointF4 = this.mDownTouch;
                    pointF3.set(rawX - pointF4.x, rawY - pointF4.y);
                    if (this.mDownDelta.length() > ((float) this.mViewConfig.getScaledTouchSlop())) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.mIsDragging) {
                        this.mStartedDragging = false;
                    } else if (z) {
                        this.mIsDragging = true;
                        this.mStartedDragging = true;
                    }
                    this.mLastTouch.set(rawX, rawY);
                    return;
                } else {
                    return;
                }
            } else if (this.mIsUserInteracting) {
                addMovementToVelocityTracker(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mViewConfig.getScaledMaximumFlingVelocity());
                this.mVelocity.set(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
                int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex2 == -1) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid active pointer id on UP: ");
                    m2.append(this.mActivePointerId);
                    Log.e("PipTouchState", m2.toString());
                    return;
                }
                this.mUpTouchTime = motionEvent.getEventTime();
                this.mLastTouch.set(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
                boolean z4 = this.mIsDragging;
                this.mPreviouslyDragging = z4;
                if (!this.mIsDoubleTap && !z4 && this.mUpTouchTime - this.mDownTouchTime < 200) {
                    z2 = true;
                }
                this.mIsWaitingForDoubleTap = z2;
            } else {
                return;
            }
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        } else if (this.mAllowTouches) {
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            } else {
                velocityTracker2.clear();
            }
            addMovementToVelocityTracker(motionEvent);
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mLastTouch.set(motionEvent.getRawX(), motionEvent.getRawY());
            this.mDownTouch.set(this.mLastTouch);
            this.mAllowDraggingOffscreen = true;
            this.mIsUserInteracting = true;
            long eventTime = motionEvent.getEventTime();
            this.mDownTouchTime = eventTime;
            if (this.mPreviouslyDragging || eventTime - this.mLastDownTouchTime >= 200) {
                z3 = false;
            }
            this.mIsDoubleTap = z3;
            this.mIsWaitingForDoubleTap = false;
            this.mIsDragging = false;
            this.mLastDownTouchTime = eventTime;
            Runnable runnable = this.mDoubleTapTimeoutCallback;
            if (runnable != null) {
                this.mMainExecutor.removeCallbacks(runnable);
            }
        }
    }
}
