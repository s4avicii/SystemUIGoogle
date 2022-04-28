package com.android.p012wm.shell.legacysplitscreen;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.internal.policy.DockedDividerUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.common.split.DividerHandleView;
import com.android.systemui.p006qs.tiles.CastTile$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.legacysplitscreen.DividerView */
public class DividerView extends FrameLayout implements View.OnTouchListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final PathInterpolator IME_ADJUST_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.1f, 1.0f);
    public boolean mAdjustedForIme;
    public View mBackground;
    public boolean mBackgroundLifted;
    public DividerCallbacks mCallback;
    public ValueAnimator mCurrentAnimator;
    public final Display mDefaultDisplay;
    public int mDividerInsets;
    public int mDividerPositionX;
    public int mDividerPositionY;
    public int mDividerSize;
    public int mDockSide;
    public final Rect mDockedInsetRect;
    public final Rect mDockedRect;
    public boolean mDockedStackMinimized;
    public final Rect mDockedTaskRect;
    public boolean mExitAnimationRunning;
    public int mExitStartPosition;
    public boolean mFirstLayout;
    public FlingAnimationUtils mFlingAnimationUtils;
    public DividerHandleView mHandle;
    public final C18641 mHandleDelegate;
    public boolean mHomeStackResizable;
    public DividerImeController mImeController;
    public boolean mIsInMinimizeInteraction;
    public final Rect mLastResizeRect;
    public MinimizedDockShadow mMinimizedShadow;
    public boolean mMoving;
    public final Rect mOtherInsetRect;
    public final Rect mOtherRect;
    public final Rect mOtherTaskRect;
    public boolean mRemoved;
    public final C18652 mResetBackgroundRunnable;
    public AnimationHandler mSfVsyncAnimationHandler;
    public DividerSnapAlgorithm.SnapTarget mSnapTargetBeforeMinimized;
    public LegacySplitDisplayLayout mSplitLayout;
    public LegacySplitScreenController mSplitScreenController;
    public int mStartPosition;
    public int mStartX;
    public int mStartY;
    public DividerState mState;
    public boolean mSurfaceHidden;
    public LegacySplitScreenTaskListener mTiles;
    public final Matrix mTmpMatrix;
    public final Rect mTmpRect;
    public final float[] mTmpValues;
    public int mTouchElevation;
    public int mTouchSlop;
    public StatusBar$$ExternalSyntheticLambda19 mUpdateEmbeddedMatrix;
    public VelocityTracker mVelocityTracker;
    public DividerWindowManager mWindowManager;
    public WindowManagerProxy mWindowManagerProxy;

    /* renamed from: com.android.wm.shell.legacysplitscreen.DividerView$DividerCallbacks */
    public interface DividerCallbacks {
    }

    public DividerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static boolean dockSideTopLeft(int i) {
        return i == 2 || i == 1;
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom());
        internalInsetsInfo.touchableRegion.op(this.mBackground.getLeft(), this.mBackground.getTop(), this.mBackground.getRight(), this.mBackground.getBottom(), Region.Op.UNION);
    }

    public final void stopDragging(int i, float f) {
        int i2 = 0;
        this.mHandle.setTouching(false, true);
        DividerSnapAlgorithm.SnapTarget calculateSnapTarget = getSnapAlgorithm().calculateSnapTarget(i, f);
        if (calculateSnapTarget == this.mSplitLayout.getSnapAlgorithm().getDismissStartTarget()) {
            MetricsLogger.action(this.mContext, 390, dockSideTopLeft(this.mDockSide) ? 1 : 0);
        } else if (calculateSnapTarget == this.mSplitLayout.getSnapAlgorithm().getDismissEndTarget()) {
            Context context = this.mContext;
            int i3 = this.mDockSide;
            if (i3 == 4 || i3 == 3) {
                i2 = 1;
            }
            MetricsLogger.action(context, 390, i2);
        } else if (calculateSnapTarget == this.mSplitLayout.getSnapAlgorithm().getMiddleTarget()) {
            MetricsLogger.action(this.mContext, 389, 0);
        } else {
            int i4 = 2;
            if (calculateSnapTarget == this.mSplitLayout.getSnapAlgorithm().getFirstSplitTarget()) {
                Context context2 = this.mContext;
                if (dockSideTopLeft(this.mDockSide)) {
                    i4 = 1;
                }
                MetricsLogger.action(context2, 389, i4);
            } else if (calculateSnapTarget == this.mSplitLayout.getSnapAlgorithm().getLastSplitTarget()) {
                Context context3 = this.mContext;
                if (!dockSideTopLeft(this.mDockSide)) {
                    i4 = 1;
                }
                MetricsLogger.action(context3, 389, i4);
            }
        }
        ValueAnimator flingAnimator = getFlingAnimator(i, calculateSnapTarget);
        this.mFlingAnimationUtils.apply(flingAnimator, (float) i, (float) calculateSnapTarget.position, f);
        flingAnimator.start();
        this.mWindowManager.setSlippery(true);
        releaseBackground();
    }

    public DividerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void applyDismissingParallax(Rect rect, int i, int i2, int i3) {
        DividerSnapAlgorithm.SnapTarget snapTarget;
        boolean z;
        float min = Math.min(1.0f, Math.max(0.0f, this.mSplitLayout.getSnapAlgorithm().calculateDismissingFraction(i2)));
        boolean z2 = false;
        DividerSnapAlgorithm.SnapTarget snapTarget2 = null;
        if (i2 > this.mSplitLayout.getSnapAlgorithm().getLastSplitTarget().position || !dockSideTopLeft(i)) {
            if (i2 >= this.mSplitLayout.getSnapAlgorithm().getLastSplitTarget().position) {
                if (i == 4 || i == 3) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    snapTarget2 = this.mSplitLayout.getSnapAlgorithm().getDismissEndTarget();
                    DividerSnapAlgorithm.SnapTarget lastSplitTarget = this.mSplitLayout.getSnapAlgorithm().getLastSplitTarget();
                    snapTarget = lastSplitTarget;
                    i3 = lastSplitTarget.position;
                }
            }
            i3 = 0;
            snapTarget = null;
        } else {
            snapTarget2 = this.mSplitLayout.getSnapAlgorithm().getDismissStartTarget();
            snapTarget = this.mSplitLayout.getSnapAlgorithm().getFirstSplitTarget();
        }
        if (snapTarget2 != null && min > 0.0f) {
            if (i == 2 || i == 1 ? i2 < snapTarget.position : i2 > snapTarget.position) {
                z2 = true;
            }
            if (z2) {
                float interpolation = Interpolators.SLOWDOWN_INTERPOLATOR.getInterpolation(min) / 3.5f;
                if (i == 2) {
                    interpolation /= 2.0f;
                }
                int i4 = (int) ((interpolation * ((float) (snapTarget2.position - snapTarget.position))) + ((float) i3));
                int width = rect.width();
                int height = rect.height();
                if (i == 1) {
                    rect.left = i4 - width;
                    rect.right = i4;
                } else if (i == 2) {
                    rect.top = i4 - height;
                    rect.bottom = i4;
                } else if (i == 3) {
                    int i5 = this.mDividerSize;
                    rect.left = i4 + i5;
                    rect.right = i4 + width + i5;
                } else if (i == 4) {
                    int i6 = this.mDividerSize;
                    rect.top = i4 + i6;
                    rect.bottom = i4 + height + i6;
                }
            }
        }
    }

    public final void calculateBoundsForPosition(int i, int i2, Rect rect) {
        DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
        Objects.requireNonNull(displayLayout);
        int i3 = displayLayout.mWidth;
        DisplayLayout displayLayout2 = this.mSplitLayout.mDisplayLayout;
        Objects.requireNonNull(displayLayout2);
        DockedDividerUtils.calculateBoundsForPosition(i, i2, rect, i3, displayLayout2.mHeight, this.mDividerSize);
    }

    public final ValueAnimator getFlingAnimator(int i, DividerSnapAlgorithm.SnapTarget snapTarget) {
        boolean z;
        ValueAnimator valueAnimator = this.mCurrentAnimator;
        if (valueAnimator != null) {
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            updateDockSide();
        }
        if (snapTarget.flag == 0) {
            z = true;
        } else {
            z = false;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, snapTarget.position});
        ofInt.addUpdateListener(new DividerView$$ExternalSyntheticLambda0(this, z, snapTarget));
        final ThemeOverlayApplier$$ExternalSyntheticLambda1 themeOverlayApplier$$ExternalSyntheticLambda1 = new ThemeOverlayApplier$$ExternalSyntheticLambda1(this, snapTarget, 1);
        ofInt.addListener(new AnimatorListenerAdapter() {
            public static final /* synthetic */ int $r8$clinit = 0;
            public boolean mCancelled;
            public final /* synthetic */ long val$endDelay = 0;

            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                long j = this.val$endDelay;
                if (j == 0) {
                    j = 0;
                }
                if (j == 0) {
                    themeOverlayApplier$$ExternalSyntheticLambda1.accept(Boolean.valueOf(this.mCancelled));
                    return;
                }
                DividerView.this.getHandler().postDelayed(new CastTile$$ExternalSyntheticLambda1(themeOverlayApplier$$ExternalSyntheticLambda1, Boolean.valueOf(this.mCancelled), 4), j);
            }
        });
        this.mCurrentAnimator = ofInt;
        ofInt.setAnimationHandler(this.mSfVsyncAnimationHandler);
        return ofInt;
    }

    public final DividerSnapAlgorithm getSnapAlgorithm() {
        if (this.mDockedStackMinimized) {
            return this.mSplitLayout.getMinimizedSnapAlgorithm(this.mHomeStackResizable);
        }
        return this.mSplitLayout.getSnapAlgorithm();
    }

    public final void notifySplitScreenBoundsChanged() {
        Rect rect;
        LegacySplitDisplayLayout legacySplitDisplayLayout = this.mSplitLayout;
        if (legacySplitDisplayLayout.mPrimary != null && (rect = legacySplitDisplayLayout.mSecondary) != null) {
            this.mOtherTaskRect.set(rect);
            this.mTmpRect.set(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom());
            if (isHorizontalDivision()) {
                this.mTmpRect.offsetTo(this.mHandle.getLeft(), this.mDividerPositionY);
            } else {
                this.mTmpRect.offsetTo(this.mDividerPositionX, this.mHandle.getTop());
            }
            WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
            Rect rect2 = this.mTmpRect;
            Objects.requireNonNull(windowManagerProxy);
            try {
                synchronized (windowManagerProxy.mDockedRect) {
                    windowManagerProxy.mTouchableRegion.set(rect2);
                }
                WindowManagerGlobal.getWindowManagerService().setDockedTaskDividerTouchRegion(windowManagerProxy.mTouchableRegion);
            } catch (RemoteException e) {
                Log.w("WindowManagerProxy", "Failed to set touchable region: " + e);
            }
            Rect rect3 = this.mTmpRect;
            DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            rect3.set(displayLayout.mStableInsets);
            int primarySplitSide = this.mSplitLayout.getPrimarySplitSide();
            if (primarySplitSide == 1) {
                this.mTmpRect.left = 0;
            } else if (primarySplitSide == 2) {
                this.mTmpRect.top = 0;
            } else if (primarySplitSide == 3) {
                this.mTmpRect.right = 0;
            }
            LegacySplitScreenController legacySplitScreenController = this.mSplitScreenController;
            Rect rect4 = this.mOtherTaskRect;
            Rect rect5 = this.mTmpRect;
            Objects.requireNonNull(legacySplitScreenController);
            synchronized (legacySplitScreenController.mBoundsChangedListeners) {
                legacySplitScreenController.mBoundsChangedListeners.removeIf(new LegacySplitScreenController$$ExternalSyntheticLambda1(rect4, rect5));
            }
        }
    }

    public final void releaseBackground() {
        if (this.mBackgroundLifted) {
            ViewPropertyAnimator animate = this.mBackground.animate();
            PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            animate.setInterpolator(pathInterpolator).setDuration(200).translationZ(0.0f).scaleX(1.0f).scaleY(1.0f).start();
            this.mHandle.animate().setInterpolator(pathInterpolator).setDuration(200).translationZ(0.0f).start();
            this.mBackgroundLifted = false;
        }
    }

    public final void repositionSnapTargetBeforeMinimized() {
        int i;
        float f = this.mState.mRatioPositionBeforeMinimized;
        if (isHorizontalDivision()) {
            DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            i = displayLayout.mHeight;
        } else {
            DisplayLayout displayLayout2 = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout2);
            i = displayLayout2.mWidth;
        }
        this.mSnapTargetBeforeMinimized = this.mSplitLayout.getSnapAlgorithm().calculateNonDismissingSnapTarget((int) (f * ((float) i)));
    }

    public final void resetBackground() {
        View view = this.mBackground;
        view.setPivotX((float) (view.getWidth() / 2));
        View view2 = this.mBackground;
        view2.setPivotY((float) (view2.getHeight() / 2));
        this.mBackground.setScaleX(1.0f);
        this.mBackground.setScaleY(1.0f);
        this.mMinimizedShadow.setAlpha(0.0f);
    }

    public final void resizeSplitSurfaces(SurfaceControl.Transaction transaction, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        int i;
        if (rect2 == null) {
            rect2 = rect;
        }
        if (rect4 == null) {
            rect4 = rect3;
        }
        if (this.mSplitLayout.getPrimarySplitSide() == 3) {
            i = rect3.right;
        } else {
            i = rect.right;
        }
        this.mDividerPositionX = i;
        this.mDividerPositionY = rect.bottom;
        transaction.setPosition(this.mTiles.mPrimarySurface, (float) rect2.left, (float) rect2.top);
        Rect rect5 = new Rect(rect);
        rect5.offsetTo(-Math.min(rect2.left - rect.left, 0), -Math.min(rect2.top - rect.top, 0));
        transaction.setWindowCrop(this.mTiles.mPrimarySurface, rect5);
        transaction.setPosition(this.mTiles.mSecondarySurface, (float) rect4.left, (float) rect4.top);
        rect5.set(rect3);
        rect5.offsetTo(-(rect4.left - rect3.left), -(rect4.top - rect3.top));
        transaction.setWindowCrop(this.mTiles.mSecondarySurface, rect5);
        SurfaceControl viewSurface = this.mWindowManager.mSystemWindows.getViewSurface(this);
        if (viewSurface != null) {
            if (isHorizontalDivision()) {
                transaction.setPosition(viewSurface, 0.0f, (float) (this.mDividerPositionY - this.mDividerInsets));
            } else {
                transaction.setPosition(viewSurface, (float) (this.mDividerPositionX - this.mDividerInsets), 0.0f);
            }
        }
        if (getViewRootImpl() != null) {
            getHandler().removeCallbacks(this.mUpdateEmbeddedMatrix);
            getHandler().post(this.mUpdateEmbeddedMatrix);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0225, code lost:
        if (r0 != false) goto L_0x0227;
     */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resizeStackSurfaces(int r17, int r18, com.android.internal.policy.DividerSnapAlgorithm.SnapTarget r19, android.view.SurfaceControl.Transaction r20) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            r0 = r18
            r1 = r19
            boolean r2 = r6.mRemoved
            if (r2 == 0) goto L_0x000d
            return
        L_0x000d:
            int r2 = r6.mDockSide
            android.graphics.Rect r3 = r6.mDockedRect
            r6.calculateBoundsForPosition(r7, r2, r3)
            int r2 = r6.mDockSide
            int r2 = com.android.internal.policy.DockedDividerUtils.invertDockSide(r2)
            android.graphics.Rect r3 = r6.mOtherRect
            r6.calculateBoundsForPosition(r7, r2, r3)
            android.graphics.Rect r2 = r6.mDockedRect
            android.graphics.Rect r3 = r6.mLastResizeRect
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x002a
            return
        L_0x002a:
            android.view.View r2 = r6.mBackground
            float r2 = r2.getZ()
            r8 = 0
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x003a
            android.view.View r2 = r6.mBackground
            r2.invalidate()
        L_0x003a:
            r9 = 0
            r10 = 1
            if (r20 != 0) goto L_0x0040
            r11 = r10
            goto L_0x0041
        L_0x0040:
            r11 = r9
        L_0x0041:
            if (r11 == 0) goto L_0x004b
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r2 = r6.mTiles
            android.view.SurfaceControl$Transaction r2 = r2.getTransaction()
            r12 = r2
            goto L_0x004d
        L_0x004b:
            r12 = r20
        L_0x004d:
            android.graphics.Rect r2 = r6.mLastResizeRect
            android.graphics.Rect r3 = r6.mDockedRect
            r2.set(r3)
            boolean r2 = r6.mIsInMinimizeInteraction
            r13 = 3
            if (r2 == 0) goto L_0x00af
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r0 = r6.mSnapTargetBeforeMinimized
            int r0 = r0.position
            int r1 = r6.mDockSide
            android.graphics.Rect r2 = r6.mDockedTaskRect
            r6.calculateBoundsForPosition(r0, r1, r2)
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r0 = r6.mSnapTargetBeforeMinimized
            int r0 = r0.position
            int r1 = r6.mDockSide
            int r1 = com.android.internal.policy.DockedDividerUtils.invertDockSide(r1)
            android.graphics.Rect r2 = r6.mOtherTaskRect
            r6.calculateBoundsForPosition(r0, r1, r2)
            int r0 = r6.mDockSide
            if (r0 != r13) goto L_0x008b
            android.graphics.Rect r0 = r6.mDockedTaskRect
            int r1 = r6.mDividerSize
            int r1 = -r1
            int r1 = java.lang.Math.max(r7, r1)
            android.graphics.Rect r2 = r6.mDockedTaskRect
            int r2 = r2.left
            int r1 = r1 - r2
            int r2 = r6.mDividerSize
            int r1 = r1 + r2
            r0.offset(r1, r9)
        L_0x008b:
            android.graphics.Rect r2 = r6.mDockedRect
            android.graphics.Rect r3 = r6.mDockedTaskRect
            android.graphics.Rect r4 = r6.mOtherRect
            android.graphics.Rect r5 = r6.mOtherTaskRect
            r0 = r16
            r1 = r12
            r0.resizeSplitSurfaces(r1, r2, r3, r4, r5)
            if (r11 == 0) goto L_0x00ae
            android.view.Choreographer r0 = android.view.Choreographer.getSfInstance()
            long r0 = r0.getVsyncId()
            r12.setFrameTimelineVsync(r0)
            r12.apply()
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r6.mTiles
            r0.releaseTransaction(r12)
        L_0x00ae:
            return
        L_0x00af:
            r14 = 2
            r2 = 2147483647(0x7fffffff, float:NaN)
            boolean r3 = r6.mExitAnimationRunning
            if (r3 == 0) goto L_0x0122
            if (r0 == r2) goto L_0x0122
            int r1 = r6.mDockSide
            android.graphics.Rect r2 = r6.mDockedTaskRect
            r6.calculateBoundsForPosition(r0, r1, r2)
            android.graphics.Rect r0 = r6.mDockedInsetRect
            android.graphics.Rect r1 = r6.mDockedTaskRect
            r0.set(r1)
            int r0 = r6.mExitStartPosition
            int r1 = r6.mDockSide
            int r1 = com.android.internal.policy.DockedDividerUtils.invertDockSide(r1)
            android.graphics.Rect r2 = r6.mOtherTaskRect
            r6.calculateBoundsForPosition(r0, r1, r2)
            android.graphics.Rect r0 = r6.mOtherInsetRect
            android.graphics.Rect r1 = r6.mOtherTaskRect
            r0.set(r1)
            android.graphics.Rect r0 = r6.mOtherTaskRect
            int r1 = r6.mDockSide
            r2 = 1048576000(0x3e800000, float:0.25)
            if (r1 != r14) goto L_0x00ee
            int r1 = r6.mExitStartPosition
            int r1 = r7 - r1
            float r1 = (float) r1
            float r1 = r1 * r2
            int r1 = (int) r1
            r0.offset(r9, r1)
            goto L_0x0106
        L_0x00ee:
            if (r1 != r10) goto L_0x00fb
            int r1 = r6.mExitStartPosition
            int r1 = r7 - r1
            float r1 = (float) r1
            float r1 = r1 * r2
            int r1 = (int) r1
            r0.offset(r1, r9)
            goto L_0x0106
        L_0x00fb:
            if (r1 != r13) goto L_0x0106
            int r1 = r6.mExitStartPosition
            int r1 = r1 - r7
            float r1 = (float) r1
            float r1 = r1 * r2
            int r1 = (int) r1
            r0.offset(r1, r9)
        L_0x0106:
            int r0 = r6.mDockSide
            if (r0 != r13) goto L_0x0112
            android.graphics.Rect r0 = r6.mDockedTaskRect
            int r1 = r6.mDividerSize
            int r1 = r1 + r7
            r0.offset(r1, r9)
        L_0x0112:
            android.graphics.Rect r2 = r6.mDockedRect
            android.graphics.Rect r3 = r6.mDockedTaskRect
            android.graphics.Rect r4 = r6.mOtherRect
            android.graphics.Rect r5 = r6.mOtherTaskRect
            r0 = r16
            r1 = r12
            r0.resizeSplitSurfaces(r1, r2, r3, r4, r5)
            goto L_0x01ea
        L_0x0122:
            if (r0 == r2) goto L_0x01de
            int r2 = r6.mDockSide
            int r2 = com.android.internal.policy.DockedDividerUtils.invertDockSide(r2)
            android.graphics.Rect r3 = r6.mOtherRect
            r6.calculateBoundsForPosition(r7, r2, r3)
            int r2 = r6.mDockSide
            int r2 = com.android.internal.policy.DockedDividerUtils.invertDockSide(r2)
            int r3 = r6.mDockSide
            int r3 = r6.restrictDismissingTaskPosition(r0, r3, r1)
            int r0 = r6.restrictDismissingTaskPosition(r0, r2, r1)
            int r1 = r6.mDockSide
            android.graphics.Rect r4 = r6.mDockedTaskRect
            r6.calculateBoundsForPosition(r3, r1, r4)
            android.graphics.Rect r1 = r6.mOtherTaskRect
            r6.calculateBoundsForPosition(r0, r2, r1)
            android.graphics.Rect r1 = r6.mTmpRect
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r4 = r6.mSplitLayout
            com.android.wm.shell.common.DisplayLayout r4 = r4.mDisplayLayout
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mWidth
            com.android.wm.shell.legacysplitscreen.LegacySplitDisplayLayout r5 = r6.mSplitLayout
            com.android.wm.shell.common.DisplayLayout r5 = r5.mDisplayLayout
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mHeight
            r1.set(r9, r9, r4, r5)
            android.graphics.Rect r1 = r6.mDockedRect
            android.graphics.Rect r4 = r6.mDockedTaskRect
            alignTopLeft(r1, r4)
            android.graphics.Rect r1 = r6.mOtherRect
            android.graphics.Rect r4 = r6.mOtherTaskRect
            alignTopLeft(r1, r4)
            android.graphics.Rect r1 = r6.mDockedInsetRect
            android.graphics.Rect r4 = r6.mDockedTaskRect
            r1.set(r4)
            android.graphics.Rect r1 = r6.mOtherInsetRect
            android.graphics.Rect r4 = r6.mOtherTaskRect
            r1.set(r4)
            int r1 = r6.mDockSide
            boolean r1 = dockSideTopLeft(r1)
            if (r1 == 0) goto L_0x01a5
            android.graphics.Rect r1 = r6.mTmpRect
            android.graphics.Rect r4 = r6.mDockedInsetRect
            alignTopLeft(r1, r4)
            android.graphics.Rect r1 = r6.mTmpRect
            android.graphics.Rect r4 = r6.mOtherInsetRect
            int r5 = r4.width()
            int r15 = r4.height()
            int r9 = r1.right
            int r5 = r9 - r5
            int r1 = r1.bottom
            int r15 = r1 - r15
            r4.set(r5, r15, r9, r1)
            goto L_0x01c3
        L_0x01a5:
            android.graphics.Rect r1 = r6.mTmpRect
            android.graphics.Rect r4 = r6.mDockedInsetRect
            int r5 = r4.width()
            int r9 = r4.height()
            int r15 = r1.right
            int r5 = r15 - r5
            int r1 = r1.bottom
            int r9 = r1 - r9
            r4.set(r5, r9, r15, r1)
            android.graphics.Rect r1 = r6.mTmpRect
            android.graphics.Rect r4 = r6.mOtherInsetRect
            alignTopLeft(r1, r4)
        L_0x01c3:
            android.graphics.Rect r1 = r6.mDockedTaskRect
            int r4 = r6.mDockSide
            r6.applyDismissingParallax(r1, r4, r7, r3)
            android.graphics.Rect r1 = r6.mOtherTaskRect
            r6.applyDismissingParallax(r1, r2, r7, r0)
            android.graphics.Rect r2 = r6.mDockedRect
            android.graphics.Rect r3 = r6.mDockedTaskRect
            android.graphics.Rect r4 = r6.mOtherRect
            android.graphics.Rect r5 = r6.mOtherTaskRect
            r0 = r16
            r1 = r12
            r0.resizeSplitSurfaces(r1, r2, r3, r4, r5)
            goto L_0x01ea
        L_0x01de:
            android.graphics.Rect r2 = r6.mDockedRect
            r3 = 0
            android.graphics.Rect r4 = r6.mOtherRect
            r5 = 0
            r0 = r16
            r1 = r12
            r0.resizeSplitSurfaces(r1, r2, r3, r4, r5)
        L_0x01ea:
            com.android.internal.policy.DividerSnapAlgorithm r0 = r16.getSnapAlgorithm()
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r0 = r0.getClosestDismissTarget(r7)
            com.android.internal.policy.DividerSnapAlgorithm r1 = r16.getSnapAlgorithm()
            float r1 = r1.calculateDismissingFraction(r7)
            r2 = 1065353216(0x3f800000, float:1.0)
            float r1 = java.lang.Math.min(r1, r2)
            float r1 = java.lang.Math.max(r8, r1)
            android.view.animation.PathInterpolator r2 = com.android.p012wm.shell.animation.Interpolators.DIM_INTERPOLATOR
            float r1 = r2.getInterpolation(r1)
            int r2 = r0.flag
            if (r2 != r10) goto L_0x0216
            int r2 = r6.mDockSide
            boolean r2 = dockSideTopLeft(r2)
            if (r2 != 0) goto L_0x0227
        L_0x0216:
            int r0 = r0.flag
            if (r0 != r14) goto L_0x0229
            int r0 = r6.mDockSide
            r2 = 4
            if (r0 == r2) goto L_0x0224
            if (r0 != r13) goto L_0x0222
            goto L_0x0224
        L_0x0222:
            r0 = 0
            goto L_0x0225
        L_0x0224:
            r0 = r10
        L_0x0225:
            if (r0 == 0) goto L_0x0229
        L_0x0227:
            r9 = r10
            goto L_0x022a
        L_0x0229:
            r9 = 0
        L_0x022a:
            r6.setResizeDimLayer(r12, r9, r1)
            if (r11 == 0) goto L_0x0237
            r12.apply()
            com.android.wm.shell.legacysplitscreen.LegacySplitScreenTaskListener r0 = r6.mTiles
            r0.releaseTransaction(r12)
        L_0x0237:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.DividerView.resizeStackSurfaces(int, int, com.android.internal.policy.DividerSnapAlgorithm$SnapTarget, android.view.SurfaceControl$Transaction):void");
    }

    public final int restrictDismissingTaskPosition(int i, int i2, DividerSnapAlgorithm.SnapTarget snapTarget) {
        boolean z = true;
        if (snapTarget.flag == 1 && dockSideTopLeft(i2)) {
            return Math.max(this.mSplitLayout.getSnapAlgorithm().getFirstSplitTarget().position, this.mStartPosition);
        }
        if (snapTarget.flag == 2) {
            if (!(i2 == 4 || i2 == 3)) {
                z = false;
            }
            if (z) {
                return Math.min(this.mSplitLayout.getSnapAlgorithm().getLastSplitTarget().position, this.mStartPosition);
            }
        }
        return i;
    }

    public final void saveSnapTargetBeforeMinimized(DividerSnapAlgorithm.SnapTarget snapTarget) {
        int i;
        this.mSnapTargetBeforeMinimized = snapTarget;
        DividerState dividerState = this.mState;
        float f = (float) snapTarget.position;
        if (isHorizontalDivision()) {
            DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            i = displayLayout.mHeight;
        } else {
            DisplayLayout displayLayout2 = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout2);
            i = displayLayout2.mWidth;
        }
        dividerState.mRatioPositionBeforeMinimized = f / ((float) i);
    }

    public final void setAdjustedForIme(boolean z, long j) {
        float f;
        if (this.mAdjustedForIme != z) {
            updateDockSide();
            ViewPropertyAnimator animate = this.mHandle.animate();
            PathInterpolator pathInterpolator = IME_ADJUST_INTERPOLATOR;
            ViewPropertyAnimator duration = animate.setInterpolator(pathInterpolator).setDuration(j);
            float f2 = 1.0f;
            if (z) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            duration.alpha(f).start();
            if (this.mDockSide == 2) {
                this.mBackground.setPivotY(0.0f);
                ViewPropertyAnimator animate2 = this.mBackground.animate();
                if (z) {
                    f2 = 0.5f;
                }
                animate2.scaleY(f2);
            }
            if (!z) {
                this.mBackground.animate().withEndAction(this.mResetBackgroundRunnable);
            }
            this.mBackground.animate().setInterpolator(pathInterpolator).setDuration(j).start();
            this.mAdjustedForIme = z;
        }
    }

    public final void setMinimizedDockStack(boolean z, boolean z2, SurfaceControl.Transaction transaction) {
        float f;
        this.mHomeStackResizable = z2;
        updateDockSide();
        if (!z) {
            resetBackground();
        }
        MinimizedDockShadow minimizedDockShadow = this.mMinimizedShadow;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        minimizedDockShadow.setAlpha(f);
        if (this.mDockedStackMinimized != z) {
            this.mDockedStackMinimized = z;
            DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
            Objects.requireNonNull(displayLayout);
            if (displayLayout.mRotation != this.mDefaultDisplay.getRotation()) {
                repositionSnapTargetBeforeMinimized();
            }
            if (this.mIsInMinimizeInteraction != z || this.mCurrentAnimator != null) {
                ValueAnimator valueAnimator = this.mCurrentAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (z) {
                    requestLayout();
                    this.mIsInMinimizeInteraction = true;
                    DividerSnapAlgorithm.SnapTarget middleTarget = this.mSplitLayout.getMinimizedSnapAlgorithm(this.mHomeStackResizable).getMiddleTarget();
                    int i = middleTarget.position;
                    resizeStackSurfaces(i, i, middleTarget, transaction);
                    return;
                }
                DividerSnapAlgorithm.SnapTarget snapTarget = this.mSnapTargetBeforeMinimized;
                int i2 = snapTarget.position;
                resizeStackSurfaces(i2, i2, snapTarget, transaction);
                this.mIsInMinimizeInteraction = false;
            }
        }
    }

    public final void setResizeDimLayer(SurfaceControl.Transaction transaction, boolean z, float f) {
        SurfaceControl surfaceControl;
        LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mTiles;
        if (z) {
            surfaceControl = legacySplitScreenTaskListener.mPrimaryDim;
        } else {
            surfaceControl = legacySplitScreenTaskListener.mSecondaryDim;
        }
        if (f <= 0.001f) {
            transaction.hide(surfaceControl);
            return;
        }
        transaction.setAlpha(surfaceControl, f);
        transaction.show(surfaceControl);
    }

    public final boolean startDragging(boolean z, boolean z2) {
        ValueAnimator valueAnimator = this.mCurrentAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (z2) {
            this.mHandle.setTouching(true, z);
        }
        this.mDockSide = this.mSplitLayout.getPrimarySplitSide();
        Objects.requireNonNull(this.mWindowManagerProxy);
        WindowManagerProxy.setResizing(true);
        if (z2) {
            this.mWindowManager.setSlippery(false);
            if (!this.mBackgroundLifted) {
                if (isHorizontalDivision()) {
                    this.mBackground.animate().scaleY(1.4f);
                } else {
                    this.mBackground.animate().scaleX(1.4f);
                }
                ViewPropertyAnimator animate = this.mBackground.animate();
                PathInterpolator pathInterpolator = Interpolators.TOUCH_RESPONSE;
                animate.setInterpolator(pathInterpolator).setDuration(150).translationZ((float) this.mTouchElevation).start();
                this.mHandle.animate().setInterpolator(pathInterpolator).setDuration(150).translationZ((float) this.mTouchElevation).start();
                this.mBackgroundLifted = true;
            }
        }
        DividerCallbacks dividerCallbacks = this.mCallback;
        if (dividerCallbacks != null) {
            ForcedResizableInfoActivityController forcedResizableInfoActivityController = (ForcedResizableInfoActivityController) dividerCallbacks;
            forcedResizableInfoActivityController.mDividerDragging = true;
            forcedResizableInfoActivityController.mMainExecutor.removeCallbacks(forcedResizableInfoActivityController.mTimeoutRunnable);
        }
        if (getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public final void updateDockSide() {
        int primarySplitSide = this.mSplitLayout.getPrimarySplitSide();
        this.mDockSide = primarySplitSide;
        MinimizedDockShadow minimizedDockShadow = this.mMinimizedShadow;
        Objects.requireNonNull(minimizedDockShadow);
        if (primarySplitSide != minimizedDockShadow.mDockSide) {
            minimizedDockShadow.mDockSide = primarySplitSide;
            minimizedDockShadow.updatePaint(minimizedDockShadow.getLeft(), minimizedDockShadow.getTop(), minimizedDockShadow.getRight(), minimizedDockShadow.getBottom());
            minimizedDockShadow.invalidate();
        }
    }

    public DividerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public static void alignTopLeft(Rect rect, Rect rect2) {
        int width = rect2.width();
        int height = rect2.height();
        int i = rect.left;
        int i2 = rect.top;
        rect2.set(i, i2, width + i, height + i2);
    }

    public final int getCurrentPosition() {
        if (isHorizontalDivision()) {
            return this.mDividerPositionY;
        }
        return this.mDividerPositionX;
    }

    public final boolean isHorizontalDivision() {
        if (getResources().getConfiguration().orientation == 1) {
            return true;
        }
        return false;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDockSide != -1 && !this.mIsInMinimizeInteraction) {
            saveSnapTargetBeforeMinimized(this.mSnapTargetBeforeMinimized);
        }
        this.mFirstLayout = true;
    }

    public final void onFinishInflate() {
        boolean z;
        int i;
        super.onFinishInflate();
        this.mHandle = (DividerHandleView) findViewById(C1777R.C1779id.docked_divider_handle);
        this.mBackground = findViewById(C1777R.C1779id.docked_divider_background);
        this.mMinimizedShadow = (MinimizedDockShadow) findViewById(C1777R.C1779id.minimized_dock_shadow);
        this.mHandle.setOnTouchListener(this);
        int dimensionPixelSize = getResources().getDimensionPixelSize(17105203);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(17105202);
        this.mDividerInsets = dimensionPixelSize2;
        this.mDividerSize = dimensionPixelSize - (dimensionPixelSize2 * 2);
        this.mTouchElevation = getResources().getDimensionPixelSize(C1777R.dimen.docked_stack_divider_lift_elevation);
        getResources().getInteger(C1777R.integer.long_press_dock_anim_duration);
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        this.mFlingAnimationUtils = new FlingAnimationUtils(getResources().getDisplayMetrics(), 0.3f);
        if (getResources().getConfiguration().orientation == 2) {
            z = true;
        } else {
            z = false;
        }
        DividerHandleView dividerHandleView = this.mHandle;
        Context context = getContext();
        if (z) {
            i = 1014;
        } else {
            i = 1015;
        }
        dividerHandleView.setPointerIcon(PointerIcon.getSystemIcon(context, i));
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        this.mHandle.setAccessibilityDelegate(this.mHandleDelegate);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int right;
        Rect rect;
        super.onLayout(z, i, i2, i3, i4);
        int i6 = 0;
        if (this.mFirstLayout) {
            this.mSplitLayout.resizeSplits(this.mSplitLayout.getSnapAlgorithm().getMiddleTarget().position);
            SurfaceControl.Transaction transaction = this.mTiles.getTransaction();
            if (this.mDockedStackMinimized) {
                int i7 = this.mSplitLayout.getMinimizedSnapAlgorithm(this.mHomeStackResizable).getMiddleTarget().position;
                calculateBoundsForPosition(i7, this.mDockSide, this.mDockedRect);
                calculateBoundsForPosition(i7, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherRect);
                this.mDividerPositionY = i7;
                this.mDividerPositionX = i7;
                Rect rect2 = this.mDockedRect;
                LegacySplitDisplayLayout legacySplitDisplayLayout = this.mSplitLayout;
                resizeSplitSurfaces(transaction, rect2, legacySplitDisplayLayout.mPrimary, this.mOtherRect, legacySplitDisplayLayout.mSecondary);
            } else {
                LegacySplitDisplayLayout legacySplitDisplayLayout2 = this.mSplitLayout;
                resizeSplitSurfaces(transaction, legacySplitDisplayLayout2.mPrimary, (Rect) null, legacySplitDisplayLayout2.mSecondary, (Rect) null);
            }
            setResizeDimLayer(transaction, true, 0.0f);
            setResizeDimLayer(transaction, false, 0.0f);
            transaction.apply();
            this.mTiles.releaseTransaction(transaction);
            if (isHorizontalDivision()) {
                int i8 = this.mDividerInsets;
                DisplayLayout displayLayout = this.mSplitLayout.mDisplayLayout;
                Objects.requireNonNull(displayLayout);
                rect = new Rect(0, i8, displayLayout.mWidth, this.mDividerInsets + this.mDividerSize);
            } else {
                int i9 = this.mDividerInsets;
                DisplayLayout displayLayout2 = this.mSplitLayout.mDisplayLayout;
                Objects.requireNonNull(displayLayout2);
                rect = new Rect(i9, 0, this.mDividerSize + i9, displayLayout2.mHeight);
            }
            Region region = new Region(rect);
            region.union(new Rect(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom()));
            DividerWindowManager dividerWindowManager = this.mWindowManager;
            Objects.requireNonNull(dividerWindowManager);
            View view = dividerWindowManager.mView;
            if (view != null) {
                SystemWindows systemWindows = dividerWindowManager.mSystemWindows;
                Objects.requireNonNull(systemWindows);
                SurfaceControlViewHost surfaceControlViewHost = systemWindows.mViewRoots.get(view);
                if (surfaceControlViewHost != null) {
                    SystemWindows.SysUiWindowManager windowlessWM = surfaceControlViewHost.getWindowlessWM();
                    if (windowlessWM instanceof SystemWindows.SysUiWindowManager) {
                        windowlessWM.setTouchableRegionForWindow(view, region);
                    }
                }
            }
            this.mFirstLayout = false;
        }
        int i10 = this.mDockSide;
        if (i10 == 2) {
            i5 = this.mBackground.getTop();
        } else {
            if (i10 == 1) {
                right = this.mBackground.getLeft();
            } else if (i10 == 3) {
                right = this.mBackground.getRight() - this.mMinimizedShadow.getWidth();
            } else {
                i5 = 0;
            }
            i6 = right;
            i5 = 0;
        }
        MinimizedDockShadow minimizedDockShadow = this.mMinimizedShadow;
        minimizedDockShadow.layout(i6, i5, minimizedDockShadow.getMeasuredWidth() + i6, this.mMinimizedShadow.getMeasuredHeight() + i5);
        if (z) {
            notifySplitScreenBoundsChanged();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
        if (r6 != 3) goto L_0x00ec;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            float r6 = r7.getRawX()
            float r0 = r7.getRawY()
            r7.setLocation(r6, r0)
            int r6 = r7.getAction()
            r6 = r6 & 255(0xff, float:3.57E-43)
            r0 = 1
            r1 = 0
            if (r6 == 0) goto L_0x00ed
            if (r6 == r0) goto L_0x0090
            r2 = 2
            if (r6 == r2) goto L_0x001f
            r2 = 3
            if (r6 == r2) goto L_0x0090
            goto L_0x00ec
        L_0x001f:
            android.view.VelocityTracker r6 = r5.mVelocityTracker
            r6.addMovement(r7)
            float r6 = r7.getX()
            int r6 = (int) r6
            float r7 = r7.getY()
            int r7 = (int) r7
            boolean r2 = r5.isHorizontalDivision()
            if (r2 == 0) goto L_0x0040
            int r2 = r5.mStartY
            int r2 = r7 - r2
            int r2 = java.lang.Math.abs(r2)
            int r3 = r5.mTouchSlop
            if (r2 > r3) goto L_0x0052
        L_0x0040:
            boolean r2 = r5.isHorizontalDivision()
            if (r2 != 0) goto L_0x0054
            int r2 = r5.mStartX
            int r2 = r6 - r2
            int r2 = java.lang.Math.abs(r2)
            int r3 = r5.mTouchSlop
            if (r2 <= r3) goto L_0x0054
        L_0x0052:
            r2 = r0
            goto L_0x0055
        L_0x0054:
            r2 = r1
        L_0x0055:
            boolean r3 = r5.mMoving
            if (r3 != 0) goto L_0x0061
            if (r2 == 0) goto L_0x0061
            r5.mStartX = r6
            r5.mStartY = r7
            r5.mMoving = r0
        L_0x0061:
            boolean r2 = r5.mMoving
            if (r2 == 0) goto L_0x00ec
            int r2 = r5.mDockSide
            r3 = -1
            if (r2 == r3) goto L_0x00ec
            com.android.internal.policy.DividerSnapAlgorithm r2 = r5.getSnapAlgorithm()
            int r3 = r5.mStartPosition
            r4 = 0
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r1 = r2.calculateSnapTarget(r3, r4, r1)
            boolean r2 = r5.isHorizontalDivision()
            if (r2 == 0) goto L_0x0082
            int r6 = r5.mStartPosition
            int r6 = r6 + r7
            int r7 = r5.mStartY
            int r6 = r6 - r7
            goto L_0x0089
        L_0x0082:
            int r7 = r5.mStartPosition
            int r7 = r7 + r6
            int r6 = r5.mStartX
            int r6 = r7 - r6
        L_0x0089:
            int r7 = r5.mStartPosition
            r2 = 0
            r5.resizeStackSurfaces(r6, r7, r1, r2)
            goto L_0x00ec
        L_0x0090:
            boolean r6 = r5.mMoving
            if (r6 != 0) goto L_0x00aa
            com.android.wm.shell.common.split.DividerHandleView r6 = r5.mHandle
            r6.setTouching(r1, r0)
            com.android.wm.shell.legacysplitscreen.DividerWindowManager r6 = r5.mWindowManager
            r6.setSlippery(r0)
            com.android.wm.shell.legacysplitscreen.WindowManagerProxy r6 = r5.mWindowManagerProxy
            java.util.Objects.requireNonNull(r6)
            com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy.setResizing(r1)
            r5.releaseBackground()
            goto L_0x00ec
        L_0x00aa:
            float r6 = r7.getRawX()
            int r6 = (int) r6
            float r2 = r7.getRawY()
            int r2 = (int) r2
            android.view.VelocityTracker r3 = r5.mVelocityTracker
            r3.addMovement(r7)
            android.view.VelocityTracker r7 = r5.mVelocityTracker
            r3 = 1000(0x3e8, float:1.401E-42)
            r7.computeCurrentVelocity(r3)
            boolean r7 = r5.isHorizontalDivision()
            if (r7 == 0) goto L_0x00cd
            int r6 = r5.mStartPosition
            int r6 = r6 + r2
            int r7 = r5.mStartY
            int r6 = r6 - r7
            goto L_0x00d4
        L_0x00cd:
            int r7 = r5.mStartPosition
            int r7 = r7 + r6
            int r6 = r5.mStartX
            int r6 = r7 - r6
        L_0x00d4:
            boolean r7 = r5.isHorizontalDivision()
            if (r7 == 0) goto L_0x00e1
            android.view.VelocityTracker r7 = r5.mVelocityTracker
            float r7 = r7.getYVelocity()
            goto L_0x00e7
        L_0x00e1:
            android.view.VelocityTracker r7 = r5.mVelocityTracker
            float r7 = r7.getXVelocity()
        L_0x00e7:
            r5.stopDragging(r6, r7)
            r5.mMoving = r1
        L_0x00ec:
            return r0
        L_0x00ed:
            android.view.VelocityTracker r6 = android.view.VelocityTracker.obtain()
            r5.mVelocityTracker = r6
            r6.addMovement(r7)
            float r6 = r7.getX()
            int r6 = (int) r6
            r5.mStartX = r6
            float r6 = r7.getY()
            int r6 = (int) r6
            r5.mStartY = r6
            boolean r6 = r5.startDragging(r0, r0)
            if (r6 != 0) goto L_0x011f
            com.android.wm.shell.common.split.DividerHandleView r7 = r5.mHandle
            r7.setTouching(r1, r0)
            com.android.wm.shell.legacysplitscreen.DividerWindowManager r7 = r5.mWindowManager
            r7.setSlippery(r0)
            com.android.wm.shell.legacysplitscreen.WindowManagerProxy r7 = r5.mWindowManagerProxy
            java.util.Objects.requireNonNull(r7)
            com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy.setResizing(r1)
            r5.releaseBackground()
        L_0x011f:
            int r7 = r5.getCurrentPosition()
            r5.mStartPosition = r7
            r5.mMoving = r1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.DividerView.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public DividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDockedRect = new Rect();
        this.mDockedTaskRect = new Rect();
        this.mOtherTaskRect = new Rect();
        this.mOtherRect = new Rect();
        this.mDockedInsetRect = new Rect();
        this.mOtherInsetRect = new Rect();
        this.mLastResizeRect = new Rect();
        this.mTmpRect = new Rect();
        this.mFirstLayout = true;
        this.mTmpMatrix = new Matrix();
        this.mTmpValues = new float[9];
        this.mSurfaceHidden = false;
        this.mHandleDelegate = new View.AccessibilityDelegate() {
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                DividerSnapAlgorithm.SnapTarget snapTarget;
                int currentPosition = DividerView.this.getCurrentPosition();
                DividerSnapAlgorithm snapAlgorithm = DividerView.this.mSplitLayout.getSnapAlgorithm();
                if (i == C1777R.C1779id.action_move_tl_full) {
                    snapTarget = snapAlgorithm.getDismissEndTarget();
                } else if (i == C1777R.C1779id.action_move_tl_70) {
                    snapTarget = snapAlgorithm.getLastSplitTarget();
                } else if (i == C1777R.C1779id.action_move_tl_50) {
                    snapTarget = snapAlgorithm.getMiddleTarget();
                } else if (i == C1777R.C1779id.action_move_tl_30) {
                    snapTarget = snapAlgorithm.getFirstSplitTarget();
                } else if (i == C1777R.C1779id.action_move_rb_full) {
                    snapTarget = snapAlgorithm.getDismissStartTarget();
                } else {
                    snapTarget = null;
                }
                DividerSnapAlgorithm.SnapTarget snapTarget2 = snapTarget;
                if (snapTarget2 == null) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                DividerView.this.startDragging(true, false);
                DividerView dividerView = DividerView.this;
                PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
                Objects.requireNonNull(dividerView);
                dividerView.stopDragging(currentPosition, snapTarget2, 250, 0, pathInterpolator);
                return true;
            }

            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                DividerSnapAlgorithm snapAlgorithm = DividerView.this.getSnapAlgorithm();
                if (DividerView.this.isHorizontalDivision()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_full, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_top_full)));
                    if (snapAlgorithm.isFirstSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_70, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_top_70)));
                    }
                    if (snapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_50, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_top_50)));
                    }
                    if (snapAlgorithm.isLastSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_30, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_top_30)));
                    }
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_rb_full, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_bottom_full)));
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_full, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_left_full)));
                if (snapAlgorithm.isFirstSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_70, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_left_70)));
                }
                if (snapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_50, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_left_50)));
                }
                if (snapAlgorithm.isLastSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_tl_30, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_left_30)));
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_move_rb_full, DividerView.this.mContext.getString(C1777R.string.accessibility_action_divider_right_full)));
            }
        };
        this.mResetBackgroundRunnable = new Runnable() {
            public final void run() {
                DividerView dividerView = DividerView.this;
                PathInterpolator pathInterpolator = DividerView.IME_ADJUST_INTERPOLATOR;
                dividerView.resetBackground();
            }
        };
        this.mUpdateEmbeddedMatrix = new StatusBar$$ExternalSyntheticLambda19(this, 6);
        this.mDefaultDisplay = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(0);
    }

    public final void stopDragging(int i, DividerSnapAlgorithm.SnapTarget snapTarget, long j, long j2, PathInterpolator pathInterpolator) {
        this.mHandle.setTouching(false, true);
        ValueAnimator flingAnimator = getFlingAnimator(i, snapTarget);
        flingAnimator.setDuration(j);
        flingAnimator.setStartDelay(j2);
        flingAnimator.setInterpolator(pathInterpolator);
        flingAnimator.start();
        this.mWindowManager.setSlippery(true);
        releaseBackground();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
