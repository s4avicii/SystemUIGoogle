package com.android.p012wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.internal.policy.TaskResizingAlgorithm;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda33;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.pip.phone.PipResizeGestureHandler */
public final class PipResizeGestureHandler {
    public boolean mAllowGesture;
    public float mAngle = 0.0f;
    public final Context mContext;
    public int mCtrlType;
    public int mDelta;
    public final Rect mDisplayBounds = new Rect();
    public final int mDisplayId;
    public final Rect mDownBounds = new Rect();
    public final PointF mDownPoint = new PointF();
    public final PointF mDownSecondPoint = new PointF();
    public final Rect mDragCornerSize = new Rect();
    public boolean mEnableDragCornerResize;
    public boolean mEnablePinchResize;
    public int mFirstIndex = -1;
    public PipResizeInputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsAttached;
    public boolean mIsEnabled;
    public boolean mIsSysUiStateValid;
    public final PointF mLastPoint = new PointF();
    public final Rect mLastResizeBounds = new Rect();
    public final PointF mLastSecondPoint = new PointF();
    public final ShellExecutor mMainExecutor;
    public final Point mMaxSize = new Point();
    public final Point mMinSize = new Point();
    public final PipMotionHelper mMotionHelper;
    public final Function<Rect, Rect> mMovementBoundsSupplier;
    public int mOhmOffset;
    public boolean mOngoingPinchToResize = false;
    public final PhonePipMenuController mPhonePipMenuController;
    public final PipPinchResizingAlgorithm mPinchResizingAlgorithm;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDismissTargetHandler mPipDismissTargetHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipUiEventLogger mPipUiEventLogger;
    public int mSecondIndex = -1;
    public boolean mThresholdCrossed;
    public final Rect mTmpBottomLeftCorner = new Rect();
    public final Rect mTmpBottomRightCorner = new Rect();
    public final Region mTmpRegion = new Region();
    public final Rect mTmpTopLeftCorner = new Rect();
    public final Rect mTmpTopRightCorner = new Rect();
    public float mTouchSlop;
    public final Runnable mUpdateMovementBoundsRunnable;
    public final Rect mUserResizeBounds = new Rect();

    /* renamed from: com.android.wm.shell.pip.phone.PipResizeGestureHandler$PipResizeInputEventReceiver */
    public class PipResizeInputEventReceiver extends BatchedInputEventReceiver {
        public PipResizeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getSfInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) {
            PipResizeGestureHandler.this.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
    }

    public final void finishResize() {
        int i;
        if (!this.mLastResizeBounds.isEmpty()) {
            StatusBar$$ExternalSyntheticLambda33 statusBar$$ExternalSyntheticLambda33 = new StatusBar$$ExternalSyntheticLambda33(this, 4);
            if (this.mOngoingPinchToResize) {
                Rect rect = new Rect(this.mLastResizeBounds);
                if (((float) this.mLastResizeBounds.width()) >= ((float) this.mMaxSize.x) * 0.9f || ((float) this.mLastResizeBounds.height()) >= ((float) this.mMaxSize.y) * 0.9f) {
                    Rect rect2 = this.mLastResizeBounds;
                    Point point = this.mMaxSize;
                    int i2 = point.x;
                    int i3 = point.y;
                    int centerX = rect2.centerX() - (i2 / 2);
                    int centerY = rect2.centerY() - (i3 / 2);
                    rect2.set(centerX, centerY, i2 + centerX, i3 + centerY);
                }
                Rect rect3 = this.mLastResizeBounds;
                int i4 = rect3.left;
                PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
                Objects.requireNonNull(pipBoundsAlgorithm);
                Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect3, true);
                if (Math.abs(i4 - movementBounds.left) < Math.abs(movementBounds.right - i4)) {
                    i = movementBounds.left;
                } else {
                    i = movementBounds.right;
                }
                Rect rect4 = this.mLastResizeBounds;
                rect4.offsetTo(i, rect4.top);
                PipBoundsAlgorithm pipBoundsAlgorithm2 = this.mPipBoundsAlgorithm;
                Rect rect5 = this.mLastResizeBounds;
                Objects.requireNonNull(pipBoundsAlgorithm2);
                PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm2.mSnapAlgorithm;
                Objects.requireNonNull(pipSnapAlgorithm);
                float snapFraction = pipSnapAlgorithm.getSnapFraction(rect5, movementBounds, 0);
                PipBoundsAlgorithm pipBoundsAlgorithm3 = this.mPipBoundsAlgorithm;
                Rect rect6 = this.mLastResizeBounds;
                Objects.requireNonNull(pipBoundsAlgorithm3);
                Rect movementBounds2 = pipBoundsAlgorithm3.getMovementBounds(rect6, true);
                Objects.requireNonNull(pipBoundsAlgorithm3.mSnapAlgorithm);
                PipSnapAlgorithm.applySnapFraction(rect6, movementBounds2, snapFraction);
                PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
                Rect rect7 = this.mLastResizeBounds;
                float f = this.mAngle;
                Objects.requireNonNull(pipTaskOrganizer);
                if (pipTaskOrganizer.mWaitForFixedRotation) {
                    Log.d("PipTaskOrganizer", "skip scheduleAnimateResizePip, entering pip deferred");
                } else {
                    pipTaskOrganizer.scheduleAnimateResizePip(rect, rect7, f, (Rect) null, 6, 250, statusBar$$ExternalSyntheticLambda33);
                }
            } else {
                this.mPipTaskOrganizer.scheduleFinishResizePip(this.mLastResizeBounds, 7, statusBar$$ExternalSyntheticLambda33);
            }
            float width = (((float) this.mLastResizeBounds.width()) / ((float) this.mMinSize.x)) / 2.0f;
            PipDismissTargetHandler pipDismissTargetHandler = this.mPipDismissTargetHandler;
            Objects.requireNonNull(pipDismissTargetHandler);
            pipDismissTargetHandler.mMagneticFieldRadiusPercent = width;
            MagnetizedObject.MagneticTarget magneticTarget = pipDismissTargetHandler.mMagneticTarget;
            Objects.requireNonNull(magneticTarget);
            magneticTarget.magneticFieldRadiusPx = (int) (width * ((float) pipDismissTargetHandler.mTargetSize) * 1.25f);
            this.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_RESIZE);
            return;
        }
        this.mCtrlType = 0;
        this.mAngle = 0.0f;
        this.mOngoingPinchToResize = false;
        this.mAllowGesture = false;
        this.mThresholdCrossed = false;
    }

    public final boolean isWithinDragResizeRegion(int i, int i2) {
        if (!this.mEnableDragCornerResize) {
            return false;
        }
        Rect bounds = this.mPipBoundsState.getBounds();
        Rect rect = this.mDragCornerSize;
        int i3 = this.mDelta;
        rect.set(0, 0, i3, i3);
        this.mTmpTopLeftCorner.set(this.mDragCornerSize);
        this.mTmpTopRightCorner.set(this.mDragCornerSize);
        this.mTmpBottomLeftCorner.set(this.mDragCornerSize);
        this.mTmpBottomRightCorner.set(this.mDragCornerSize);
        Rect rect2 = this.mTmpTopLeftCorner;
        int i4 = bounds.left;
        int i5 = this.mDelta;
        rect2.offset(i4 - (i5 / 2), bounds.top - (i5 / 2));
        Rect rect3 = this.mTmpTopRightCorner;
        int i6 = bounds.right;
        int i7 = this.mDelta;
        rect3.offset(i6 - (i7 / 2), bounds.top - (i7 / 2));
        Rect rect4 = this.mTmpBottomLeftCorner;
        int i8 = bounds.left;
        int i9 = this.mDelta;
        rect4.offset(i8 - (i9 / 2), bounds.bottom - (i9 / 2));
        Rect rect5 = this.mTmpBottomRightCorner;
        int i10 = bounds.right;
        int i11 = this.mDelta;
        rect5.offset(i10 - (i11 / 2), bounds.bottom - (i11 / 2));
        this.mTmpRegion.setEmpty();
        this.mTmpRegion.op(this.mTmpTopLeftCorner, Region.Op.UNION);
        this.mTmpRegion.op(this.mTmpTopRightCorner, Region.Op.UNION);
        this.mTmpRegion.op(this.mTmpBottomLeftCorner, Region.Op.UNION);
        this.mTmpRegion.op(this.mTmpBottomRightCorner, Region.Op.UNION);
        return this.mTmpRegion.contains(i, i2);
    }

    public void onInputEvent(InputEvent inputEvent) {
        boolean z;
        InputEvent inputEvent2 = inputEvent;
        if ((this.mEnableDragCornerResize || this.mEnablePinchResize) && !this.mPipBoundsState.isStashed() && (inputEvent2 instanceof MotionEvent)) {
            MotionEvent motionEvent = (MotionEvent) inputEvent2;
            int actionMasked = motionEvent.getActionMasked();
            Rect bounds = this.mPipBoundsState.getBounds();
            if ((actionMasked == 1 || actionMasked == 3) && !bounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY()) && this.mPhonePipMenuController.isMenuVisible()) {
                this.mPhonePipMenuController.hideMenu();
            }
            if (this.mEnablePinchResize && this.mOngoingPinchToResize) {
                onPinchResize(motionEvent);
            } else if (this.mEnableDragCornerResize) {
                int actionMasked2 = motionEvent.getActionMasked();
                float x = motionEvent.getX();
                float y = motionEvent.getY() - ((float) this.mOhmOffset);
                boolean z2 = false;
                if (actionMasked2 == 0) {
                    this.mLastResizeBounds.setEmpty();
                    if (this.mIsSysUiStateValid && isWithinDragResizeRegion((int) x, (int) y)) {
                        z2 = true;
                    }
                    this.mAllowGesture = z2;
                    if (z2) {
                        int i = (int) x;
                        int i2 = (int) y;
                        Rect bounds2 = this.mPipBoundsState.getBounds();
                        Rect apply = this.mMovementBoundsSupplier.apply(bounds2);
                        this.mDisplayBounds.set(apply.left, apply.top, bounds2.width() + apply.right, bounds2.height() + apply.bottom);
                        if (this.mTmpTopLeftCorner.contains(i, i2)) {
                            int i3 = bounds2.top;
                            Rect rect = this.mDisplayBounds;
                            if (!(i3 == rect.top || bounds2.left == rect.left)) {
                                this.mCtrlType = this.mCtrlType | 1 | 4;
                            }
                        }
                        if (this.mTmpTopRightCorner.contains(i, i2)) {
                            int i4 = bounds2.top;
                            Rect rect2 = this.mDisplayBounds;
                            if (!(i4 == rect2.top || bounds2.right == rect2.right)) {
                                this.mCtrlType = this.mCtrlType | 2 | 4;
                            }
                        }
                        if (this.mTmpBottomRightCorner.contains(i, i2)) {
                            int i5 = bounds2.bottom;
                            Rect rect3 = this.mDisplayBounds;
                            if (!(i5 == rect3.bottom || bounds2.right == rect3.right)) {
                                this.mCtrlType = 2 | this.mCtrlType | 8;
                            }
                        }
                        if (this.mTmpBottomLeftCorner.contains(i, i2)) {
                            int i6 = bounds2.bottom;
                            Rect rect4 = this.mDisplayBounds;
                            if (!(i6 == rect4.bottom || bounds2.left == rect4.left)) {
                                this.mCtrlType = this.mCtrlType | 1 | 8;
                            }
                        }
                        this.mDownPoint.set(x, y);
                        this.mDownBounds.set(this.mPipBoundsState.getBounds());
                    }
                } else if (this.mAllowGesture) {
                    if (actionMasked2 != 1) {
                        if (actionMasked2 == 2) {
                            if (!this.mThresholdCrossed) {
                                PointF pointF = this.mDownPoint;
                                if (Math.hypot((double) (x - pointF.x), (double) (y - pointF.y)) > ((double) this.mTouchSlop)) {
                                    this.mThresholdCrossed = true;
                                    this.mDownPoint.set(x, y);
                                    this.mInputMonitor.pilferPointers();
                                }
                            }
                            if (this.mThresholdCrossed) {
                                if (this.mPhonePipMenuController.isMenuVisible()) {
                                    this.mPhonePipMenuController.hideMenu(0);
                                }
                                Rect bounds3 = this.mPipBoundsState.getBounds();
                                Rect rect5 = this.mLastResizeBounds;
                                PointF pointF2 = this.mDownPoint;
                                float f = pointF2.x;
                                float f2 = pointF2.y;
                                int i7 = this.mCtrlType;
                                Point point = this.mMinSize;
                                int i8 = point.x;
                                int i9 = point.y;
                                Point point2 = this.mMaxSize;
                                if (this.mDownBounds.width() > this.mDownBounds.height()) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                rect5.set(TaskResizingAlgorithm.resizeDrag(x, y, f, f2, bounds3, i7, i8, i9, point2, true, z));
                                PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
                                Rect rect6 = this.mLastResizeBounds;
                                PipBoundsState pipBoundsState = this.mPipBoundsState;
                                Objects.requireNonNull(pipBoundsState);
                                pipBoundsAlgorithm.transformBoundsToAspectRatio(rect6, pipBoundsState.mAspectRatio, false, true);
                                PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
                                Rect rect7 = this.mDownBounds;
                                Rect rect8 = this.mLastResizeBounds;
                                Objects.requireNonNull(pipTaskOrganizer);
                                pipTaskOrganizer.scheduleUserResizePip(rect7, rect8, 0.0f, (ShellTaskOrganizer$$ExternalSyntheticLambda1) null);
                                PipBoundsState pipBoundsState2 = this.mPipBoundsState;
                                Objects.requireNonNull(pipBoundsState2);
                                pipBoundsState2.mHasUserResizedPip = true;
                                return;
                            }
                            return;
                        } else if (actionMasked2 != 3) {
                            if (actionMasked2 == 5) {
                                this.mAllowGesture = false;
                                return;
                            }
                            return;
                        }
                    }
                    finishResize();
                }
            }
        }
    }

    public void pilferPointers() {
        this.mInputMonitor.pilferPointers();
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mDelta = resources.getDimensionPixelSize(C1777R.dimen.pip_resize_edge_size);
        this.mEnableDragCornerResize = resources.getBoolean(C1777R.bool.config_pipEnableDragCornerResize);
        this.mTouchSlop = (float) ViewConfiguration.get(this.mContext).getScaledTouchSlop();
    }

    public final void updateIsEnabled() {
        boolean z = this.mIsAttached;
        if (z != this.mIsEnabled) {
            this.mIsEnabled = z;
            PipResizeInputEventReceiver pipResizeInputEventReceiver = this.mInputEventReceiver;
            if (pipResizeInputEventReceiver != null) {
                pipResizeInputEventReceiver.dispose();
                this.mInputEventReceiver = null;
            }
            InputMonitor inputMonitor = this.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                this.mInputMonitor = null;
            }
            if (this.mIsEnabled) {
                this.mInputMonitor = InputManager.getInstance().monitorGestureInput("pip-resize", this.mDisplayId);
                try {
                    this.mMainExecutor.executeBlocking$1(new ScreenDecorations$$ExternalSyntheticLambda1(this, 10));
                } catch (InterruptedException e) {
                    throw new RuntimeException("Failed to create input event receiver", e);
                }
            }
        }
    }

    public void updateMaxSize(int i, int i2) {
        this.mMaxSize.set(i, i2);
    }

    public void updateMinSize(int i, int i2) {
        this.mMinSize.set(i, i2);
    }

    public PipResizeGestureHandler(Context context, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipMotionHelper pipMotionHelper, PipTaskOrganizer pipTaskOrganizer, PipDismissTargetHandler pipDismissTargetHandler, PeopleSpaceUtils$$ExternalSyntheticLambda2 peopleSpaceUtils$$ExternalSyntheticLambda2, StatusBar$$ExternalSyntheticLambda19 statusBar$$ExternalSyntheticLambda19, PipUiEventLogger pipUiEventLogger, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mMainExecutor = shellExecutor;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mMotionHelper = pipMotionHelper;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipDismissTargetHandler = pipDismissTargetHandler;
        this.mMovementBoundsSupplier = peopleSpaceUtils$$ExternalSyntheticLambda2;
        this.mUpdateMovementBoundsRunnable = statusBar$$ExternalSyntheticLambda19;
        this.mPhonePipMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mPinchResizingAlgorithm = new PipPinchResizingAlgorithm();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00f0, code lost:
        if (((float) java.lang.Math.hypot((double) (r0.x - r15.x), (double) (r0.y - r15.y))) > r14.mTouchSlop) goto L_0x00f2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPinchResize(android.view.MotionEvent r15) {
        /*
            r14 = this;
            int r0 = r15.getActionMasked()
            r1 = 0
            r2 = -1
            r3 = 1
            if (r0 == r3) goto L_0x000c
            r4 = 3
            if (r0 != r4) goto L_0x0015
        L_0x000c:
            r14.mFirstIndex = r2
            r14.mSecondIndex = r2
            r14.mAllowGesture = r1
            r14.finishResize()
        L_0x0015:
            int r4 = r15.getPointerCount()
            r5 = 2
            if (r4 == r5) goto L_0x001d
            return
        L_0x001d:
            com.android.wm.shell.pip.PipBoundsState r4 = r14.mPipBoundsState
            android.graphics.Rect r4 = r4.getBounds()
            r6 = 5
            if (r0 != r6) goto L_0x008c
            int r6 = r14.mFirstIndex
            if (r6 != r2) goto L_0x008c
            int r6 = r14.mSecondIndex
            if (r6 != r2) goto L_0x008c
            float r6 = r15.getRawX(r1)
            int r6 = (int) r6
            float r7 = r15.getRawY(r1)
            int r7 = (int) r7
            boolean r6 = r4.contains(r6, r7)
            if (r6 == 0) goto L_0x008c
            float r6 = r15.getRawX(r3)
            int r6 = (int) r6
            float r7 = r15.getRawY(r3)
            int r7 = (int) r7
            boolean r6 = r4.contains(r6, r7)
            if (r6 == 0) goto L_0x008c
            r14.mAllowGesture = r3
            r14.mFirstIndex = r1
            r14.mSecondIndex = r3
            android.graphics.PointF r6 = r14.mDownPoint
            float r1 = r15.getRawX(r1)
            int r7 = r14.mFirstIndex
            float r7 = r15.getRawY(r7)
            r6.set(r1, r7)
            android.graphics.PointF r1 = r14.mDownSecondPoint
            int r6 = r14.mSecondIndex
            float r6 = r15.getRawX(r6)
            int r7 = r14.mSecondIndex
            float r7 = r15.getRawY(r7)
            r1.set(r6, r7)
            android.graphics.Rect r1 = r14.mDownBounds
            r1.set(r4)
            android.graphics.PointF r1 = r14.mLastPoint
            android.graphics.PointF r4 = r14.mDownPoint
            r1.set(r4)
            android.graphics.PointF r1 = r14.mLastSecondPoint
            r1.set(r1)
            android.graphics.Rect r1 = r14.mLastResizeBounds
            android.graphics.Rect r4 = r14.mDownBounds
            r1.set(r4)
        L_0x008c:
            if (r0 != r5) goto L_0x027b
            int r0 = r14.mFirstIndex
            if (r0 == r2) goto L_0x027b
            int r1 = r14.mSecondIndex
            if (r1 != r2) goto L_0x0098
            goto L_0x027b
        L_0x0098:
            float r0 = r15.getRawX(r0)
            int r1 = r14.mFirstIndex
            float r1 = r15.getRawY(r1)
            int r2 = r14.mSecondIndex
            float r2 = r15.getRawX(r2)
            int r4 = r14.mSecondIndex
            float r15 = r15.getRawY(r4)
            android.graphics.PointF r4 = r14.mLastPoint
            r4.set(r0, r1)
            android.graphics.PointF r0 = r14.mLastSecondPoint
            r0.set(r2, r15)
            boolean r15 = r14.mThresholdCrossed
            if (r15 != 0) goto L_0x0112
            android.graphics.PointF r15 = r14.mDownSecondPoint
            android.graphics.PointF r0 = r14.mLastSecondPoint
            float r1 = r0.x
            float r2 = r15.x
            float r1 = r1 - r2
            double r1 = (double) r1
            float r0 = r0.y
            float r15 = r15.y
            float r0 = r0 - r15
            double r4 = (double) r0
            double r0 = java.lang.Math.hypot(r1, r4)
            float r15 = (float) r0
            float r0 = r14.mTouchSlop
            int r15 = (r15 > r0 ? 1 : (r15 == r0 ? 0 : -1))
            if (r15 > 0) goto L_0x00f2
            android.graphics.PointF r15 = r14.mDownPoint
            android.graphics.PointF r0 = r14.mLastPoint
            float r1 = r0.x
            float r2 = r15.x
            float r1 = r1 - r2
            double r1 = (double) r1
            float r0 = r0.y
            float r15 = r15.y
            float r0 = r0 - r15
            double r4 = (double) r0
            double r0 = java.lang.Math.hypot(r1, r4)
            float r15 = (float) r0
            float r0 = r14.mTouchSlop
            int r15 = (r15 > r0 ? 1 : (r15 == r0 ? 0 : -1))
            if (r15 <= 0) goto L_0x0112
        L_0x00f2:
            r14.pilferPointers()
            r14.mThresholdCrossed = r3
            android.graphics.PointF r15 = r14.mDownPoint
            android.graphics.PointF r0 = r14.mLastPoint
            r15.set(r0)
            android.graphics.PointF r15 = r14.mDownSecondPoint
            android.graphics.PointF r0 = r14.mLastSecondPoint
            r15.set(r0)
            com.android.wm.shell.pip.phone.PhonePipMenuController r15 = r14.mPhonePipMenuController
            boolean r15 = r15.isMenuVisible()
            if (r15 == 0) goto L_0x0112
            com.android.wm.shell.pip.phone.PhonePipMenuController r15 = r14.mPhonePipMenuController
            r15.hideMenu()
        L_0x0112:
            boolean r15 = r14.mThresholdCrossed
            if (r15 == 0) goto L_0x027b
            com.android.wm.shell.pip.phone.PipPinchResizingAlgorithm r15 = r14.mPinchResizingAlgorithm
            android.graphics.PointF r0 = r14.mDownPoint
            android.graphics.PointF r1 = r14.mDownSecondPoint
            android.graphics.PointF r2 = r14.mLastPoint
            android.graphics.PointF r4 = r14.mLastSecondPoint
            android.graphics.Point r5 = r14.mMinSize
            android.graphics.Point r6 = r14.mMaxSize
            android.graphics.Rect r7 = r14.mDownBounds
            android.graphics.Rect r8 = r14.mLastResizeBounds
            java.util.Objects.requireNonNull(r15)
            float r9 = r1.x
            float r10 = r0.x
            float r9 = r9 - r10
            double r9 = (double) r9
            float r11 = r1.y
            float r12 = r0.y
            float r11 = r11 - r12
            double r11 = (double) r11
            double r9 = java.lang.Math.hypot(r9, r11)
            float r9 = (float) r9
            float r10 = r4.x
            float r11 = r2.x
            float r10 = r10 - r11
            double r10 = (double) r10
            float r12 = r4.y
            float r13 = r2.y
            float r12 = r12 - r13
            double r12 = (double) r12
            double r10 = java.lang.Math.hypot(r10, r12)
            float r10 = (float) r10
            int r11 = r5.x
            float r11 = (float) r11
            int r12 = r7.width()
            float r12 = (float) r12
            float r11 = r11 / r12
            int r5 = r5.y
            float r5 = (float) r5
            int r12 = r7.height()
            float r12 = (float) r12
            float r5 = r5 / r12
            float r5 = java.lang.Math.max(r11, r5)
            int r11 = r6.x
            float r11 = (float) r11
            int r12 = r7.width()
            float r12 = (float) r12
            float r11 = r11 / r12
            int r6 = r6.y
            float r6 = (float) r6
            int r12 = r7.height()
            float r12 = (float) r12
            float r6 = r6 / r12
            float r6 = java.lang.Math.min(r11, r6)
            float r10 = r10 / r9
            float r9 = r5 - r10
            r11 = 0
            int r12 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r12 <= 0) goto L_0x0182
            goto L_0x0183
        L_0x0182:
            r9 = r11
        L_0x0183:
            float r12 = r10 - r6
            int r13 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            if (r13 <= 0) goto L_0x018a
            goto L_0x018b
        L_0x018a:
            r12 = r11
        L_0x018b:
            r13 = 1048576000(0x3e800000, float:0.25)
            float r9 = r9 * r13
            float r5 = r5 - r9
            float r12 = r12 * r13
            float r12 = r12 + r6
            float r6 = java.lang.Math.min(r12, r10)
            float r5 = java.lang.Math.max(r5, r6)
            r8.set(r7)
            r6 = 1065353216(0x3f800000, float:1.0)
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 == 0) goto L_0x01b5
            int r7 = r8.centerX()
            int r9 = r8.centerY()
            int r10 = -r7
            int r12 = -r9
            r8.offset(r10, r12)
            r8.scale(r5)
            r8.offset(r7, r9)
        L_0x01b5:
            android.graphics.PointF r5 = r15.mTmpDownCentroid
            float r7 = r1.x
            float r9 = r0.x
            float r7 = r7 + r9
            r9 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r9
            float r10 = r1.y
            float r12 = r0.y
            float r10 = r10 + r12
            float r10 = r10 / r9
            r5.set(r7, r10)
            android.graphics.PointF r5 = r15.mTmpLastCentroid
            float r7 = r4.x
            float r10 = r2.x
            float r7 = r7 + r10
            float r7 = r7 / r9
            float r10 = r4.y
            float r12 = r2.y
            float r10 = r10 + r12
            float r10 = r10 / r9
            r5.set(r7, r10)
            android.graphics.PointF r5 = r15.mTmpLastCentroid
            float r7 = r5.x
            android.graphics.PointF r9 = r15.mTmpDownCentroid
            float r10 = r9.x
            float r7 = r7 - r10
            int r7 = (int) r7
            float r5 = r5.y
            float r9 = r9.y
            float r5 = r5 - r9
            int r5 = (int) r5
            r8.offset(r7, r5)
            android.graphics.PointF r5 = r15.mTmpDownVector
            float r7 = r1.x
            float r8 = r0.x
            float r7 = r7 - r8
            float r1 = r1.y
            float r0 = r0.y
            float r1 = r1 - r0
            r5.set(r7, r1)
            android.graphics.PointF r0 = r15.mTmpLastVector
            float r1 = r4.x
            float r5 = r2.x
            float r1 = r1 - r5
            float r4 = r4.y
            float r2 = r2.y
            float r4 = r4 - r2
            r0.set(r1, r4)
            android.graphics.PointF r0 = r15.mTmpDownVector
            android.graphics.PointF r15 = r15.mTmpLastVector
            float r1 = r0.x
            float r2 = r15.y
            float r4 = r1 * r2
            float r0 = r0.y
            float r15 = r15.x
            float r5 = r0 * r15
            float r4 = r4 - r5
            double r4 = (double) r4
            float r1 = r1 * r15
            float r0 = r0 * r2
            float r0 = r0 + r1
            double r0 = (double) r0
            double r0 = java.lang.Math.atan2(r4, r0)
            float r15 = (float) r0
            double r0 = (double) r15
            double r0 = java.lang.Math.toDegrees(r0)
            float r15 = (float) r0
            float r0 = java.lang.Math.signum(r15)
            int r1 = java.lang.Float.compare(r15, r11)
            if (r1 != 0) goto L_0x0237
            r4 = r11
            goto L_0x025c
        L_0x0237:
            r1 = 1110704128(0x42340000, float:45.0)
            float r15 = r15 / r1
            float r2 = java.lang.Math.abs(r15)
            float r2 = r15 / r2
            float r15 = java.lang.Math.abs(r15)
            float r15 = r15 - r6
            float r4 = r15 * r15
            float r4 = r4 * r15
            float r4 = r4 + r6
            float r4 = r4 * r2
            float r15 = java.lang.Math.abs(r4)
            int r15 = (r15 > r6 ? 1 : (r15 == r6 ? 0 : -1))
            if (r15 < 0) goto L_0x0257
            float r15 = java.lang.Math.abs(r4)
            float r4 = r4 / r15
        L_0x0257:
            r15 = 1053609165(0x3ecccccd, float:0.4)
            float r4 = r4 * r15
            float r4 = r4 * r1
        L_0x025c:
            float r15 = java.lang.Math.abs(r4)
            r1 = 1084227584(0x40a00000, float:5.0)
            float r15 = r15 - r1
            float r15 = java.lang.Math.max(r11, r15)
            float r15 = r15 * r0
            r14.mAngle = r15
            com.android.wm.shell.pip.PipTaskOrganizer r0 = r14.mPipTaskOrganizer
            android.graphics.Rect r1 = r14.mDownBounds
            android.graphics.Rect r2 = r14.mLastResizeBounds
            r4 = 0
            r0.scheduleUserResizePip(r1, r2, r15, r4)
            com.android.wm.shell.pip.PipBoundsState r14 = r14.mPipBoundsState
            java.util.Objects.requireNonNull(r14)
            r14.mHasUserResizedPip = r3
        L_0x027b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.phone.PipResizeGestureHandler.onPinchResize(android.view.MotionEvent):void");
    }

    public Rect getLastResizeBounds() {
        return this.mLastResizeBounds;
    }
}
