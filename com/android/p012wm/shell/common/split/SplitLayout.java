package com.android.p012wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.view.Display;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.split.SplitWindowManager;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.split.SplitLayout */
public final class SplitLayout implements DisplayInsetsController.OnInsetsChangedListener {
    public final Rect mBounds1;
    public final Rect mBounds2;
    public Context mContext;
    public final DismissingEffectPolicy mDismissingEffectPolicy;
    public final DisplayImeController mDisplayImeController;
    public int mDividePosition;
    public final Rect mDividerBounds;
    public final int mDividerInsets;
    public final int mDividerSize;
    @VisibleForTesting
    public DividerSnapAlgorithm mDividerSnapAlgorithm;
    public final int mDividerWindowWidth;
    public final ImePositionProcessor mImePositionProcessor;
    public boolean mInitialized;
    public final InsetsState mInsetsState;
    public int mOrientation;
    public final Rect mRootBounds;
    public int mRotation;
    public final SplitLayoutHandler mSplitLayoutHandler;
    public final SplitWindowManager mSplitWindowManager;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTempRect = new Rect();
    public final Rect mWinBounds1;
    public final Rect mWinBounds2;
    public WindowContainerToken mWinToken1;
    public WindowContainerToken mWinToken2;

    /* renamed from: com.android.wm.shell.common.split.SplitLayout$DismissingEffectPolicy */
    public class DismissingEffectPolicy {
        public final boolean mApplyParallax;
        public float mDismissingDimValue = 0.0f;
        public final Point mDismissingParallaxOffset = new Point();
        public int mDismissingSide = -1;

        public DismissingEffectPolicy(boolean z) {
            this.mApplyParallax = z;
        }
    }

    /* renamed from: com.android.wm.shell.common.split.SplitLayout$ImePositionProcessor */
    public class ImePositionProcessor implements DisplayImeController.ImePositionProcessor {
        public float mDimValue1;
        public float mDimValue2;
        public final int mDisplayId;
        public int mEndImeTop;
        public boolean mImeShown;
        public float mLastDim1;
        public float mLastDim2;
        public int mLastYOffset;
        public int mStartImeTop;
        public float mTargetDim1;
        public float mTargetDim2;
        public int mTargetYOffset;
        public int mYOffsetForIme;

        public ImePositionProcessor(int i) {
            this.mDisplayId = i;
        }

        public final void onImeControlTargetChanged(int i, boolean z) {
            if (i == this.mDisplayId && !z && this.mImeShown) {
                this.mImeShown = false;
                this.mTargetYOffset = 0;
                this.mLastYOffset = 0;
                this.mYOffsetForIme = 0;
                this.mTargetDim1 = 0.0f;
                this.mLastDim1 = 0.0f;
                this.mDimValue1 = 0.0f;
                this.mTargetDim2 = 0.0f;
                this.mLastDim2 = 0.0f;
                this.mDimValue2 = 0.0f;
                SplitLayout.this.mSplitWindowManager.setInteractive(true);
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.mSplitLayoutHandler.onLayoutPositionChanging(splitLayout);
            }
        }

        public final void onImeEndPositioning(int i, boolean z, SurfaceControl.Transaction transaction) {
            if (i == this.mDisplayId && !z) {
                onProgress(1.0f);
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.mSplitLayoutHandler.onLayoutPositionChanging(splitLayout);
            }
        }

        public final void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
            if (i == this.mDisplayId) {
                float f = (float) i2;
                int i3 = this.mStartImeTop;
                onProgress((f - ((float) i3)) / ((float) (this.mEndImeTop - i3)));
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.mSplitLayoutHandler.onLayoutPositionChanging(splitLayout);
            }
        }

        public final int onImeStartPositioning(int i, int i2, int i3, boolean z, boolean z2) {
            int i4;
            float f;
            int i5;
            int i6;
            int i7 = this.mDisplayId;
            boolean z3 = false;
            if (i != i7) {
                return 0;
            }
            int splitItemPosition = SplitLayout.this.mSplitLayoutHandler.getSplitItemPosition(SplitLayout.this.mTaskOrganizer.getImeTarget(i7));
            SplitLayout splitLayout = SplitLayout.this;
            if (!splitLayout.mInitialized || splitItemPosition == -1) {
                return 0;
            }
            if (z) {
                i4 = i2;
            } else {
                i4 = i3;
            }
            this.mStartImeTop = i4;
            if (z) {
                i2 = i3;
            }
            this.mEndImeTop = i2;
            this.mImeShown = z;
            this.mLastDim1 = this.mDimValue1;
            float f2 = 0.3f;
            if (splitItemPosition != 1 || !z) {
                f = 0.0f;
            } else {
                f = 0.3f;
            }
            this.mTargetDim1 = f;
            this.mLastDim2 = this.mDimValue2;
            if (splitItemPosition != 0 || !z) {
                f2 = 0.0f;
            }
            this.mTargetDim2 = f2;
            this.mLastYOffset = this.mYOffsetForIme;
            if (splitItemPosition != 1 || z2 || SplitLayout.isLandscape(splitLayout.mRootBounds) || !z) {
                i5 = 0;
            } else {
                i5 = 1;
            }
            if (i5 != 0) {
                i6 = -Math.min(Math.abs(this.mEndImeTop - this.mStartImeTop), (int) (((float) SplitLayout.this.mBounds1.height()) * 0.7f));
            } else {
                i6 = 0;
            }
            this.mTargetYOffset = i6;
            int i8 = this.mLastYOffset;
            if (i6 != i8) {
                if (i6 == 0) {
                    SplitLayout splitLayout2 = SplitLayout.this;
                    splitLayout2.mSplitLayoutHandler.setLayoutOffsetTarget(0, splitLayout2);
                } else {
                    SplitLayout splitLayout3 = SplitLayout.this;
                    splitLayout3.mSplitLayoutHandler.setLayoutOffsetTarget(i6 - i8, splitLayout3);
                }
            }
            SplitWindowManager splitWindowManager = SplitLayout.this.mSplitWindowManager;
            if (!z || splitItemPosition == -1) {
                z3 = true;
            }
            splitWindowManager.setInteractive(z3);
            return i5;
        }

        public final void onProgress(float f) {
            float f2 = this.mLastDim1;
            this.mDimValue1 = MotionController$$ExternalSyntheticOutline0.m7m(this.mTargetDim1, f2, f, f2);
            float f3 = this.mLastDim2;
            this.mDimValue2 = MotionController$$ExternalSyntheticOutline0.m7m(this.mTargetDim2, f3, f, f3);
            float f4 = (float) this.mLastYOffset;
            this.mYOffsetForIme = (int) MotionController$$ExternalSyntheticOutline0.m7m((float) this.mTargetYOffset, f4, f, f4);
        }
    }

    /* renamed from: com.android.wm.shell.common.split.SplitLayout$SplitLayoutHandler */
    public interface SplitLayoutHandler {
        int getSplitItemPosition(WindowContainerToken windowContainerToken);

        void onDoubleTappedDivider() {
        }

        void onLayoutPositionChanging(SplitLayout splitLayout);

        void onLayoutSizeChanged(SplitLayout splitLayout);

        void onLayoutSizeChanging(SplitLayout splitLayout);

        void onSnappedToDismiss(boolean z);

        void setLayoutOffsetTarget(int i, SplitLayout splitLayout);
    }

    public static boolean isLandscape(Rect rect) {
        return rect.width() > rect.height();
    }

    public final void applyLayoutOffsetTarget(WindowContainerTransaction windowContainerTransaction, int i, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
        if (i == 0) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, this.mBounds1);
            windowContainerTransaction.setAppBounds(runningTaskInfo.token, (Rect) null);
            windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
            windowContainerTransaction.setBounds(runningTaskInfo2.token, this.mBounds2);
            windowContainerTransaction.setAppBounds(runningTaskInfo2.token, (Rect) null);
            windowContainerTransaction.setScreenSizeDp(runningTaskInfo2.token, 0, 0);
            return;
        }
        this.mTempRect.set(runningTaskInfo.configuration.windowConfiguration.getBounds());
        this.mTempRect.offset(0, i);
        windowContainerTransaction.setBounds(runningTaskInfo.token, this.mTempRect);
        this.mTempRect.set(runningTaskInfo.configuration.windowConfiguration.getAppBounds());
        this.mTempRect.offset(0, i);
        windowContainerTransaction.setAppBounds(runningTaskInfo.token, this.mTempRect);
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        Configuration configuration = runningTaskInfo.configuration;
        windowContainerTransaction.setScreenSizeDp(windowContainerToken, configuration.screenWidthDp, configuration.screenHeightDp);
        this.mTempRect.set(runningTaskInfo2.configuration.windowConfiguration.getBounds());
        this.mTempRect.offset(0, i);
        windowContainerTransaction.setBounds(runningTaskInfo2.token, this.mTempRect);
        this.mTempRect.set(runningTaskInfo2.configuration.windowConfiguration.getAppBounds());
        this.mTempRect.offset(0, i);
        windowContainerTransaction.setAppBounds(runningTaskInfo2.token, this.mTempRect);
        WindowContainerToken windowContainerToken2 = runningTaskInfo2.token;
        Configuration configuration2 = runningTaskInfo2.configuration;
        windowContainerTransaction.setScreenSizeDp(windowContainerToken2, configuration2.screenWidthDp, configuration2.screenHeightDp);
    }

    public final void applySurfaceChanges(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, SurfaceControl surfaceControl3, SurfaceControl surfaceControl4) {
        SurfaceControl surfaceControl5;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        SplitWindowManager splitWindowManager = this.mSplitWindowManager;
        if (splitWindowManager == null) {
            surfaceControl5 = null;
        } else {
            surfaceControl5 = splitWindowManager.mLeash;
        }
        if (surfaceControl5 != null) {
            Rect rect = this.mDividerBounds;
            transaction.setPosition(surfaceControl5, (float) rect.left, (float) rect.top);
            transaction.setLayer(surfaceControl5, 30000);
        }
        Rect rect2 = this.mBounds1;
        transaction.setPosition(surfaceControl, (float) rect2.left, (float) rect2.top).setWindowCrop(surfaceControl, this.mBounds1.width(), this.mBounds1.height());
        Rect rect3 = this.mBounds2;
        transaction.setPosition(surfaceControl2, (float) rect3.left, (float) rect3.top).setWindowCrop(surfaceControl2, this.mBounds2.width(), this.mBounds2.height());
        ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
        Objects.requireNonNull(imePositionProcessor);
        boolean z5 = false;
        if (imePositionProcessor.mDimValue1 > 0.001f || imePositionProcessor.mDimValue2 > 0.001f) {
            z = true;
        } else {
            z = false;
        }
        if (imePositionProcessor.mYOffsetForIme != 0) {
            if (surfaceControl5 != null) {
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.mTempRect.set(splitLayout.mDividerBounds);
                SplitLayout.this.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                Rect rect4 = SplitLayout.this.mTempRect;
                transaction.setPosition(surfaceControl5, (float) rect4.left, (float) rect4.top);
            }
            SplitLayout splitLayout2 = SplitLayout.this;
            splitLayout2.mTempRect.set(splitLayout2.mBounds1);
            SplitLayout.this.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
            Rect rect5 = SplitLayout.this.mTempRect;
            transaction.setPosition(surfaceControl, (float) rect5.left, (float) rect5.top);
            SplitLayout splitLayout3 = SplitLayout.this;
            splitLayout3.mTempRect.set(splitLayout3.mBounds2);
            SplitLayout.this.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
            Rect rect6 = SplitLayout.this.mTempRect;
            transaction.setPosition(surfaceControl2, (float) rect6.left, (float) rect6.top);
            z2 = true;
        } else {
            z2 = false;
        }
        if (z) {
            SurfaceControl.Transaction alpha = transaction.setAlpha(surfaceControl3, imePositionProcessor.mDimValue1);
            if (imePositionProcessor.mDimValue1 > 0.001f) {
                z3 = true;
            } else {
                z3 = false;
            }
            alpha.setVisibility(surfaceControl3, z3);
            SurfaceControl.Transaction alpha2 = transaction.setAlpha(surfaceControl4, imePositionProcessor.mDimValue2);
            if (imePositionProcessor.mDimValue2 > 0.001f) {
                z4 = true;
            } else {
                z4 = false;
            }
            alpha2.setVisibility(surfaceControl4, z4);
            z2 = true;
        }
        if (!z2) {
            DismissingEffectPolicy dismissingEffectPolicy = this.mDismissingEffectPolicy;
            Objects.requireNonNull(dismissingEffectPolicy);
            int i = dismissingEffectPolicy.mDismissingSide;
            if (i == 1 || i == 2) {
                SplitLayout splitLayout4 = SplitLayout.this;
                splitLayout4.mTempRect.set(splitLayout4.mBounds1);
            } else if (i == 3 || i == 4) {
                SplitLayout splitLayout5 = SplitLayout.this;
                splitLayout5.mTempRect.set(splitLayout5.mBounds2);
                surfaceControl = surfaceControl2;
                surfaceControl3 = surfaceControl4;
            } else {
                transaction.setAlpha(surfaceControl3, 0.0f).hide(surfaceControl3);
                transaction.setAlpha(surfaceControl4, 0.0f).hide(surfaceControl4);
                return;
            }
            if (dismissingEffectPolicy.mApplyParallax) {
                Rect rect7 = SplitLayout.this.mTempRect;
                int i2 = rect7.left;
                Point point = dismissingEffectPolicy.mDismissingParallaxOffset;
                transaction.setPosition(surfaceControl, (float) (i2 + point.x), (float) (rect7.top + point.y));
                Rect rect8 = SplitLayout.this.mTempRect;
                Point point2 = dismissingEffectPolicy.mDismissingParallaxOffset;
                rect8.offsetTo(-point2.x, -point2.y);
                transaction.setWindowCrop(surfaceControl, SplitLayout.this.mTempRect);
            }
            SurfaceControl.Transaction alpha3 = transaction.setAlpha(surfaceControl3, dismissingEffectPolicy.mDismissingDimValue);
            if (dismissingEffectPolicy.mDismissingDimValue > 0.001f) {
                z5 = true;
            }
            alpha3.setVisibility(surfaceControl3, z5);
        }
    }

    public final void applyTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
        ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        WindowContainerToken windowContainerToken2 = runningTaskInfo2.token;
        Objects.requireNonNull(imePositionProcessor);
        boolean z = false;
        if (imePositionProcessor.mYOffsetForIme != 0) {
            SplitLayout splitLayout = SplitLayout.this;
            splitLayout.mTempRect.set(splitLayout.mBounds1);
            SplitLayout.this.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
            windowContainerTransaction.setBounds(windowContainerToken, SplitLayout.this.mTempRect);
            SplitLayout splitLayout2 = SplitLayout.this;
            splitLayout2.mTempRect.set(splitLayout2.mBounds2);
            SplitLayout.this.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
            windowContainerTransaction.setBounds(windowContainerToken2, SplitLayout.this.mTempRect);
            z = true;
        }
        if (!z) {
            if (!this.mBounds1.equals(this.mWinBounds1) || !runningTaskInfo.token.equals(this.mWinToken1)) {
                windowContainerTransaction.setBounds(runningTaskInfo.token, this.mBounds1);
                this.mWinBounds1.set(this.mBounds1);
                this.mWinToken1 = runningTaskInfo.token;
            }
            if (!this.mBounds2.equals(this.mWinBounds2) || !runningTaskInfo2.token.equals(this.mWinToken2)) {
                windowContainerTransaction.setBounds(runningTaskInfo2.token, this.mBounds2);
                this.mWinBounds2.set(this.mBounds2);
                this.mWinToken2 = runningTaskInfo2.token;
            }
        }
    }

    @VisibleForTesting
    public void flingDividePosition(int i, final int i2, final Runnable runnable) {
        if (i == i2) {
            this.mSplitLayoutHandler.onLayoutSizeChanged(this);
            return;
        }
        ValueAnimator duration = ValueAnimator.ofInt(new int[]{i, i2}).setDuration(250);
        duration.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        duration.addUpdateListener(new SplitLayout$$ExternalSyntheticLambda0(this));
        duration.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationCancel(Animator animator) {
                SplitLayout splitLayout = SplitLayout.this;
                int i = i2;
                Objects.requireNonNull(splitLayout);
                splitLayout.mDividePosition = i;
                splitLayout.updateBounds(i);
                splitLayout.mSplitLayoutHandler.onLayoutSizeChanged(splitLayout);
            }

            public final void onAnimationEnd(Animator animator) {
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        duration.start();
    }

    public final Rect getDividerBounds() {
        return new Rect(this.mDividerBounds);
    }

    public final void init() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            SplitWindowManager splitWindowManager = this.mSplitWindowManager;
            InsetsState insetsState = this.mInsetsState;
            Objects.requireNonNull(splitWindowManager);
            if (splitWindowManager.mDividerView == null && splitWindowManager.mViewHost == null) {
                Context context = splitWindowManager.mContext;
                splitWindowManager.mViewHost = new SurfaceControlViewHost(context, context.getDisplay(), splitWindowManager);
                splitWindowManager.mDividerView = (DividerView) LayoutInflater.from(splitWindowManager.mContext).inflate(C1777R.layout.split_divider, (ViewGroup) null);
                Rect dividerBounds = getDividerBounds();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dividerBounds.width(), dividerBounds.height(), 2034, 545521704, -3);
                layoutParams.token = new Binder();
                layoutParams.setTitle(splitWindowManager.mWindowName);
                layoutParams.privateFlags |= 536870976;
                splitWindowManager.mViewHost.setView(splitWindowManager.mDividerView, layoutParams);
                DividerView dividerView = splitWindowManager.mDividerView;
                SurfaceControlViewHost surfaceControlViewHost = splitWindowManager.mViewHost;
                Objects.requireNonNull(dividerView);
                dividerView.mSplitLayout = this;
                dividerView.mSplitWindowManager = splitWindowManager;
                dividerView.mViewHost = surfaceControlViewHost;
                dividerView.mDividerBounds.set(getDividerBounds());
                dividerView.onInsetsChanged(insetsState, false);
                this.mDisplayImeController.addPositionProcessor(this.mImePositionProcessor);
                return;
            }
            throw new UnsupportedOperationException("Try to inflate divider view again without release first");
        }
    }

    public final void initDividerPosition(Rect rect) {
        int i;
        int i2;
        float f = (float) this.mDividePosition;
        if (isLandscape(rect)) {
            i = rect.width();
        } else {
            i = rect.height();
        }
        float f2 = f / ((float) i);
        if (isLandscape()) {
            i2 = this.mRootBounds.width();
        } else {
            i2 = this.mRootBounds.height();
        }
        int i3 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget((int) (((float) i2) * f2)).position;
        this.mDividePosition = i3;
        updateBounds(i3);
    }

    public final void insetsChanged(InsetsState insetsState) {
        this.mInsetsState.set(insetsState);
        if (this.mInitialized) {
            SplitWindowManager splitWindowManager = this.mSplitWindowManager;
            Objects.requireNonNull(splitWindowManager);
            DividerView dividerView = splitWindowManager.mDividerView;
            if (dividerView != null) {
                dividerView.onInsetsChanged(insetsState, true);
            }
        }
    }

    public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        if (!this.mInsetsState.equals(insetsState)) {
            insetsChanged(insetsState);
        }
    }

    public final boolean isLandscape() {
        return isLandscape(this.mRootBounds);
    }

    public final void release() {
        if (this.mInitialized) {
            this.mInitialized = false;
            SplitWindowManager splitWindowManager = this.mSplitWindowManager;
            Objects.requireNonNull(splitWindowManager);
            if (splitWindowManager.mDividerView != null) {
                splitWindowManager.mDividerView = null;
            }
            SurfaceControlViewHost surfaceControlViewHost = splitWindowManager.mViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.release();
                splitWindowManager.mViewHost = null;
            }
            if (splitWindowManager.mLeash != null) {
                new SurfaceControl.Transaction().remove(splitWindowManager.mLeash).apply();
                splitWindowManager.mLeash = null;
            }
            DisplayImeController displayImeController = this.mDisplayImeController;
            ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
            Objects.requireNonNull(displayImeController);
            synchronized (displayImeController.mPositionProcessors) {
                displayImeController.mPositionProcessors.remove(imePositionProcessor);
            }
            ImePositionProcessor imePositionProcessor2 = this.mImePositionProcessor;
            Objects.requireNonNull(imePositionProcessor2);
            imePositionProcessor2.mImeShown = false;
            imePositionProcessor2.mTargetYOffset = 0;
            imePositionProcessor2.mLastYOffset = 0;
            imePositionProcessor2.mYOffsetForIme = 0;
            imePositionProcessor2.mTargetDim1 = 0.0f;
            imePositionProcessor2.mLastDim1 = 0.0f;
            imePositionProcessor2.mDimValue1 = 0.0f;
            imePositionProcessor2.mTargetDim2 = 0.0f;
            imePositionProcessor2.mLastDim2 = 0.0f;
            imePositionProcessor2.mDimValue2 = 0.0f;
        }
    }

    public final void resetDividerPosition() {
        int i = this.mDividerSnapAlgorithm.getMiddleTarget().position;
        this.mDividePosition = i;
        updateBounds(i);
        this.mWinToken1 = null;
        this.mWinToken2 = null;
        this.mWinBounds1.setEmpty();
        this.mWinBounds2.setEmpty();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateBounds(int r8) {
        /*
            r7 = this;
            android.graphics.Rect r0 = r7.mDividerBounds
            android.graphics.Rect r1 = r7.mRootBounds
            r0.set(r1)
            android.graphics.Rect r0 = r7.mBounds1
            android.graphics.Rect r1 = r7.mRootBounds
            r0.set(r1)
            android.graphics.Rect r0 = r7.mBounds2
            android.graphics.Rect r1 = r7.mRootBounds
            r0.set(r1)
            android.graphics.Rect r0 = r7.mRootBounds
            boolean r0 = isLandscape(r0)
            if (r0 == 0) goto L_0x003b
            android.graphics.Rect r1 = r7.mRootBounds
            int r1 = r1.left
            int r8 = r8 + r1
            android.graphics.Rect r1 = r7.mDividerBounds
            int r2 = r7.mDividerInsets
            int r2 = r8 - r2
            r1.left = r2
            int r3 = r7.mDividerWindowWidth
            int r2 = r2 + r3
            r1.right = r2
            android.graphics.Rect r1 = r7.mBounds1
            r1.right = r8
            android.graphics.Rect r1 = r7.mBounds2
            int r2 = r7.mDividerSize
            int r2 = r2 + r8
            r1.left = r2
            goto L_0x0058
        L_0x003b:
            android.graphics.Rect r1 = r7.mRootBounds
            int r1 = r1.top
            int r8 = r8 + r1
            android.graphics.Rect r1 = r7.mDividerBounds
            int r2 = r7.mDividerInsets
            int r2 = r8 - r2
            r1.top = r2
            int r3 = r7.mDividerWindowWidth
            int r2 = r2 + r3
            r1.bottom = r2
            android.graphics.Rect r1 = r7.mBounds1
            r1.bottom = r8
            android.graphics.Rect r1 = r7.mBounds2
            int r2 = r7.mDividerSize
            int r2 = r2 + r8
            r1.top = r2
        L_0x0058:
            android.graphics.Rect r1 = r7.mBounds1
            r2 = 1
            com.android.internal.policy.DockedDividerUtils.sanitizeStackBounds(r1, r2)
            android.graphics.Rect r1 = r7.mBounds2
            r3 = 0
            com.android.internal.policy.DockedDividerUtils.sanitizeStackBounds(r1, r3)
            com.android.wm.shell.common.split.SplitLayout$DismissingEffectPolicy r7 = r7.mDismissingEffectPolicy
            java.util.Objects.requireNonNull(r7)
            r1 = -1
            r7.mDismissingSide = r1
            android.graphics.Point r4 = r7.mDismissingParallaxOffset
            r4.set(r3, r3)
            r4 = 0
            r7.mDismissingDimValue = r4
            com.android.wm.shell.common.split.SplitLayout r5 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r5 = r5.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r5 = r5.getFirstSplitTarget()
            int r5 = r5.position
            r6 = 2
            if (r8 >= r5) goto L_0x009c
            if (r0 == 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r2 = r6
        L_0x0085:
            r7.mDismissingSide = r2
            com.android.wm.shell.common.split.SplitLayout r2 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r2 = r2.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r2 = r2.getDismissStartTarget()
            int r2 = r2.position
            com.android.wm.shell.common.split.SplitLayout r3 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r3 = r3.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r3 = r3.getFirstSplitTarget()
            int r3 = r3.position
            goto L_0x00c3
        L_0x009c:
            com.android.wm.shell.common.split.SplitLayout r2 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r2 = r2.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r2 = r2.getLastSplitTarget()
            int r2 = r2.position
            if (r8 <= r2) goto L_0x00c5
            if (r0 == 0) goto L_0x00ac
            r2 = 3
            goto L_0x00ad
        L_0x00ac:
            r2 = 4
        L_0x00ad:
            r7.mDismissingSide = r2
            com.android.wm.shell.common.split.SplitLayout r2 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r2 = r2.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r2 = r2.getLastSplitTarget()
            int r2 = r2.position
            com.android.wm.shell.common.split.SplitLayout r3 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r3 = r3.mDividerSnapAlgorithm
            com.android.internal.policy.DividerSnapAlgorithm$SnapTarget r3 = r3.getDismissEndTarget()
            int r3 = r3.position
        L_0x00c3:
            int r3 = r2 - r3
        L_0x00c5:
            int r2 = r7.mDismissingSide
            if (r2 == r1) goto L_0x0104
            com.android.wm.shell.common.split.SplitLayout r1 = com.android.p012wm.shell.common.split.SplitLayout.this
            com.android.internal.policy.DividerSnapAlgorithm r1 = r1.mDividerSnapAlgorithm
            float r8 = r1.calculateDismissingFraction(r8)
            r1 = 1065353216(0x3f800000, float:1.0)
            float r8 = java.lang.Math.min(r8, r1)
            float r8 = java.lang.Math.max(r4, r8)
            android.view.animation.PathInterpolator r1 = com.android.p012wm.shell.animation.Interpolators.DIM_INTERPOLATOR
            float r1 = r1.getInterpolation(r8)
            r7.mDismissingDimValue = r1
            int r1 = r7.mDismissingSide
            android.view.animation.PathInterpolator r2 = com.android.p012wm.shell.animation.Interpolators.SLOWDOWN_INTERPOLATOR
            float r8 = r2.getInterpolation(r8)
            r2 = 1080033280(0x40600000, float:3.5)
            float r8 = r8 / r2
            if (r1 != r6) goto L_0x00f3
            r1 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r1
        L_0x00f3:
            if (r0 == 0) goto L_0x00fd
            android.graphics.Point r7 = r7.mDismissingParallaxOffset
            float r0 = (float) r3
            float r8 = r8 * r0
            int r8 = (int) r8
            r7.x = r8
            goto L_0x0104
        L_0x00fd:
            android.graphics.Point r7 = r7.mDismissingParallaxOffset
            float r0 = (float) r3
            float r8 = r8 * r0
            int r8 = (int) r8
            r7.y = r8
        L_0x0104:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.split.SplitLayout.updateBounds(int):void");
    }

    public final boolean updateConfiguration(Configuration configuration) {
        int rotation = configuration.windowConfiguration.getRotation();
        Rect bounds = configuration.windowConfiguration.getBounds();
        int i = configuration.orientation;
        if (this.mOrientation == i && rotation == this.mRotation && this.mRootBounds.equals(bounds)) {
            return false;
        }
        this.mContext = this.mContext.createConfigurationContext(configuration);
        this.mSplitWindowManager.setConfiguration(configuration);
        this.mOrientation = i;
        this.mTempRect.set(this.mRootBounds);
        this.mRootBounds.set(bounds);
        this.mRotation = rotation;
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, this.mRootBounds, (Rect) null);
        initDividerPosition(this.mTempRect);
        if (!this.mInitialized) {
            return true;
        }
        release();
        init();
        return true;
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, boolean z) {
        Rect rect = new Rect();
        this.mRootBounds = rect;
        this.mDividerBounds = new Rect();
        this.mBounds1 = new Rect();
        this.mBounds2 = new Rect();
        this.mWinBounds1 = new Rect();
        this.mWinBounds2 = new Rect();
        this.mInsetsState = new InsetsState();
        int i = 0;
        this.mInitialized = false;
        this.mContext = context.createConfigurationContext(configuration);
        this.mOrientation = configuration.orientation;
        this.mRotation = configuration.windowConfiguration.getRotation();
        this.mSplitLayoutHandler = splitLayoutHandler;
        this.mDisplayImeController = displayImeController;
        this.mSplitWindowManager = new SplitWindowManager(str, this.mContext, configuration, parentContainerCallbacks);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mImePositionProcessor = new ImePositionProcessor(this.mContext.getDisplayId());
        this.mDismissingEffectPolicy = new DismissingEffectPolicy(z);
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.split_divider_bar_width);
        this.mDividerSize = dimensionPixelSize;
        Display display = context.getDisplay();
        int dimensionPixelSize2 = resources.getDimensionPixelSize(17105202);
        RoundedCorner roundedCorner = display.getRoundedCorner(0);
        i = roundedCorner != null ? Math.max(0, roundedCorner.getRadius()) : i;
        RoundedCorner roundedCorner2 = display.getRoundedCorner(1);
        i = roundedCorner2 != null ? Math.max(i, roundedCorner2.getRadius()) : i;
        RoundedCorner roundedCorner3 = display.getRoundedCorner(2);
        i = roundedCorner3 != null ? Math.max(i, roundedCorner3.getRadius()) : i;
        RoundedCorner roundedCorner4 = display.getRoundedCorner(3);
        int max = Math.max(dimensionPixelSize2, roundedCorner4 != null ? Math.max(i, roundedCorner4.getRadius()) : i);
        this.mDividerInsets = max;
        this.mDividerWindowWidth = (max * 2) + dimensionPixelSize;
        rect.set(configuration.windowConfiguration.getBounds());
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect, (Rect) null);
        resetDividerPosition();
    }

    public final float getDividerPositionAsFraction() {
        int i;
        float f;
        if (isLandscape()) {
            int i2 = this.mBounds1.right;
            Rect rect = this.mBounds2;
            f = ((float) (i2 + rect.left)) / 2.0f;
            i = rect.right;
        } else {
            int i3 = this.mBounds1.bottom;
            Rect rect2 = this.mBounds2;
            f = ((float) (i3 + rect2.top)) / 2.0f;
            i = rect2.bottom;
        }
        return Math.min(1.0f, Math.max(0.0f, f / ((float) i)));
    }

    public final DividerSnapAlgorithm getSnapAlgorithm(Context context, Rect rect, Rect rect2) {
        int i;
        boolean isLandscape = isLandscape(rect);
        Resources resources = context.getResources();
        int width = rect.width();
        int height = rect.height();
        int i2 = this.mDividerSize;
        boolean z = !isLandscape;
        if (rect2 == null) {
            rect2 = ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()).toRect();
        }
        Rect rect3 = rect2;
        if (isLandscape) {
            i = 1;
        } else {
            i = 2;
        }
        return new DividerSnapAlgorithm(resources, width, height, i2, z, rect3, i);
    }

    public final void setDivideRatio(float f) {
        int i;
        int i2;
        if (isLandscape()) {
            Rect rect = this.mRootBounds;
            i = rect.left;
            i2 = rect.width();
        } else {
            Rect rect2 = this.mRootBounds;
            i = rect2.top;
            i2 = rect2.height();
        }
        int i3 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((float) i2) * f))).position;
        this.mDividePosition = i3;
        updateBounds(i3);
    }
}
