package com.android.p012wm.shell.legacysplitscreen;

import android.animation.AnimationHandler;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.provider.Settings;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda22;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.legacysplitscreen.ForcedResizableInfoActivityController;
import com.android.p012wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.splitscreen.StageCoordinator$1$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.splitscreen.StageCoordinator$2$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.p006qs.external.TileServices$$ExternalSyntheticLambda0;
import com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.NotificationIconAreaController$$ExternalSyntheticLambda0;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda1;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController */
public final class LegacySplitScreenController implements DisplayController.OnDisplaysChangedListener {
    public volatile boolean mAdjustedForIme = false;
    public final ArrayList<WeakReference<BiConsumer<Rect, Rect>>> mBoundsChangedListeners = new ArrayList<>();
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DividerState mDividerState = new DividerState();
    public final CopyOnWriteArrayList<WeakReference<Consumer<Boolean>>> mDockedStackExistsListeners = new CopyOnWriteArrayList<>();
    public final ForcedResizableInfoActivityController mForcedResizableController;
    public boolean mHomeStackResizable = false;
    public final DisplayImeController mImeController;
    public final DividerImeController mImePositionProcessor;
    public final SplitScreenImpl mImpl = new SplitScreenImpl();
    public boolean mIsKeyguardShowing;
    public final ShellExecutor mMainExecutor;
    public volatile boolean mMinimized = false;
    public LegacySplitDisplayLayout mRotateSplitLayout;
    public final LegacySplitScreenController$$ExternalSyntheticLambda0 mRotationController;
    public final AnimationHandler mSfVsyncAnimationHandler;
    public LegacySplitDisplayLayout mSplitLayout;
    public final LegacySplitScreenTaskListener mSplits;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TransactionPool mTransactionPool;
    public DividerView mView;
    public boolean mVisible = false;
    public DividerWindowManager mWindowManager;
    public final WindowManagerProxy mWindowManagerProxy;

    /* renamed from: com.android.wm.shell.legacysplitscreen.LegacySplitScreenController$SplitScreenImpl */
    public class SplitScreenImpl implements LegacySplitScreen {
        public final boolean isDividerVisible() {
            boolean[] zArr = new boolean[1];
            try {
                LegacySplitScreenController.this.mMainExecutor.executeBlocking$1(new TileServices$$ExternalSyntheticLambda0(this, zArr, 1));
            } catch (InterruptedException unused) {
                Slog.e("SplitScreenCtrl", "Failed to get divider visible");
            }
            return zArr[0];
        }

        public final boolean splitPrimaryTask() {
            boolean[] zArr = new boolean[1];
            try {
                LegacySplitScreenController.this.mMainExecutor.executeBlocking$1(new BubbleStackView$$ExternalSyntheticLambda22(this, zArr, 2));
            } catch (InterruptedException unused) {
                Slog.e("SplitScreenCtrl", "Failed to split primary task");
            }
            return zArr[0];
        }

        public SplitScreenImpl() {
        }

        public final DividerView getDividerView() {
            LegacySplitScreenController legacySplitScreenController = LegacySplitScreenController.this;
            Objects.requireNonNull(legacySplitScreenController);
            return legacySplitScreenController.mView;
        }

        public final boolean isHomeStackResizable() {
            return LegacySplitScreenController.this.mHomeStackResizable;
        }

        public final boolean isMinimized() {
            return LegacySplitScreenController.this.mMinimized;
        }

        public final void onAppTransitionFinished() {
            LegacySplitScreenController.this.mMainExecutor.execute(new OneHandedController$$ExternalSyntheticLambda1(this, 6));
        }

        public final void onKeyguardVisibilityChanged(boolean z) {
            LegacySplitScreenController.this.mMainExecutor.execute(new C1869x586845aa(this, z));
        }

        public final void onUndockingTask() {
            LegacySplitScreenController.this.mMainExecutor.execute(new WMShell$8$$ExternalSyntheticLambda0(this, 7));
        }

        public final void registerBoundsChangeListener(OverviewProxyService$$ExternalSyntheticLambda3 overviewProxyService$$ExternalSyntheticLambda3) {
            LegacySplitScreenController.this.mMainExecutor.execute(new NotificationIconAreaController$$ExternalSyntheticLambda0(this, overviewProxyService$$ExternalSyntheticLambda3, 2));
        }

        public final void registerInSplitScreenListener(Consumer<Boolean> consumer) {
            LegacySplitScreenController.this.mMainExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda0(this, consumer, 1));
        }

        public final void setMinimized(boolean z) {
            LegacySplitScreenController.this.mMainExecutor.execute(new C1870x586845ab(this, z));
        }
    }

    public final void ensureMinimizedSplit() {
        setHomeMinimized(true);
        if (this.mView != null && !isDividerVisible()) {
            updateVisibility(true);
        }
    }

    public final void ensureNormalSplit() {
        setHomeMinimized(false);
        if (this.mView != null && !isDividerVisible()) {
            updateVisibility(true);
        }
    }

    public final void onDismissSplit() {
        updateVisibility(false);
        this.mMinimized = false;
        this.mDividerState.mRatioPositionBeforeMinimized = 0.0f;
        removeDivider();
        DividerImeController dividerImeController = this.mImePositionProcessor;
        Objects.requireNonNull(dividerImeController);
        dividerImeController.mPaused = true;
        dividerImeController.mPausedTargetAdjusted = false;
        dividerImeController.mAnimation = null;
        dividerImeController.mTargetAdjusted = false;
        dividerImeController.mAdjusted = false;
        dividerImeController.mTargetShown = false;
        dividerImeController.mImeWasShown = false;
        dividerImeController.mLastSecondaryDim = 0.0f;
        dividerImeController.mLastPrimaryDim = 0.0f;
        dividerImeController.mTargetSecondaryDim = 0.0f;
        dividerImeController.mTargetPrimaryDim = 0.0f;
        dividerImeController.mSecondaryHasFocus = false;
        dividerImeController.mLastAdjustTop = -1;
    }

    public final boolean isDividerVisible() {
        DividerView dividerView = this.mView;
        if (dividerView == null || dividerView.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public final boolean isSplitActive() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mSplits;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = legacySplitScreenTaskListener.mPrimary;
        if (runningTaskInfo2 == null || (runningTaskInfo = legacySplitScreenTaskListener.mSecondary) == null || (runningTaskInfo2.topActivityType == 0 && runningTaskInfo.topActivityType == 0)) {
            return false;
        }
        return true;
    }

    public final void onDisplayAdded(int i) {
        LegacySplitScreenTaskListener legacySplitScreenTaskListener;
        if (i == 0) {
            this.mSplitLayout = new LegacySplitDisplayLayout(this.mDisplayController.getDisplayContext(i), this.mDisplayController.getDisplayLayout(i), this.mSplits);
            this.mImeController.addPositionProcessor(this.mImePositionProcessor);
            this.mDisplayController.addDisplayChangingController(this.mRotationController);
            if (!ActivityTaskManager.supportsSplitScreenMultiWindow(this.mContext)) {
                removeDivider();
                return;
            }
            try {
                legacySplitScreenTaskListener = this.mSplits;
                Objects.requireNonNull(legacySplitScreenTaskListener);
                synchronized (legacySplitScreenTaskListener) {
                    legacySplitScreenTaskListener.mTaskOrganizer.createRootTask(3, legacySplitScreenTaskListener);
                    legacySplitScreenTaskListener.mTaskOrganizer.createRootTask(4, legacySplitScreenTaskListener);
                }
            } catch (Exception e) {
                legacySplitScreenTaskListener.mTaskOrganizer.removeListener(legacySplitScreenTaskListener);
                throw e;
            } catch (Exception e2) {
                Slog.e("SplitScreenCtrl", "Failed to register docked stack listener", e2);
                removeDivider();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (i == 0) {
            LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mSplits;
            Objects.requireNonNull(legacySplitScreenTaskListener);
            if (legacySplitScreenTaskListener.mSplitScreenSupported) {
                LegacySplitDisplayLayout legacySplitDisplayLayout = new LegacySplitDisplayLayout(this.mDisplayController.getDisplayContext(i), this.mDisplayController.getDisplayLayout(i), this.mSplits);
                this.mSplitLayout = legacySplitDisplayLayout;
                if (this.mRotateSplitLayout == null) {
                    int i2 = legacySplitDisplayLayout.getSnapAlgorithm().getMiddleTarget().position;
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    this.mSplitLayout.resizeSplits(i2, windowContainerTransaction);
                    this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                } else {
                    DisplayLayout displayLayout = legacySplitDisplayLayout.mDisplayLayout;
                    Objects.requireNonNull(displayLayout);
                    int i3 = displayLayout.mRotation;
                    DisplayLayout displayLayout2 = this.mRotateSplitLayout.mDisplayLayout;
                    Objects.requireNonNull(displayLayout2);
                    if (i3 == displayLayout2.mRotation) {
                        this.mSplitLayout.mPrimary = new Rect(this.mRotateSplitLayout.mPrimary);
                        this.mSplitLayout.mSecondary = new Rect(this.mRotateSplitLayout.mSecondary);
                        this.mRotateSplitLayout = null;
                    }
                }
                if (isSplitActive()) {
                    update(configuration);
                }
            }
        }
    }

    public final void onUndockingTask() {
        boolean z;
        DividerSnapAlgorithm.SnapTarget snapTarget;
        DividerView dividerView = this.mView;
        if (dividerView != null) {
            int primarySplitSide = dividerView.mSplitLayout.getPrimarySplitSide();
            if (dividerView.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                dividerView.startDragging(false, false);
                if (DividerView.dockSideTopLeft(primarySplitSide)) {
                    snapTarget = dividerView.mSplitLayout.getSnapAlgorithm().getDismissEndTarget();
                } else {
                    snapTarget = dividerView.mSplitLayout.getSnapAlgorithm().getDismissStartTarget();
                }
                dividerView.mExitAnimationRunning = true;
                int currentPosition = dividerView.getCurrentPosition();
                dividerView.mExitStartPosition = currentPosition;
                dividerView.stopDragging(currentPosition, snapTarget, 336, 100, Interpolators.FAST_OUT_SLOW_IN);
            }
        }
    }

    public final void removeDivider() {
        DividerView dividerView = this.mView;
        if (dividerView != null) {
            dividerView.mRemoved = true;
            dividerView.mCallback = null;
        }
        DividerWindowManager dividerWindowManager = this.mWindowManager;
        Objects.requireNonNull(dividerWindowManager);
        View view = dividerWindowManager.mView;
        if (view != null) {
            SystemWindows systemWindows = dividerWindowManager.mSystemWindows;
            Objects.requireNonNull(systemWindows);
            systemWindows.mViewRoots.remove(view).release();
        }
        dividerWindowManager.mView = null;
    }

    public final void setHomeMinimized(boolean z) {
        boolean z2;
        int i;
        DividerSnapAlgorithm.SnapTarget snapTarget;
        boolean z3 = z;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (this.mMinimized != z3) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.mMinimized = z3;
        }
        windowContainerTransaction.setFocusable(this.mSplits.mPrimary.token, !this.mMinimized);
        DividerView dividerView = this.mView;
        if (dividerView != null) {
            if (dividerView.getDisplay() != null) {
                this.mView.getDisplay().getDisplayId();
            }
            if (this.mMinimized) {
                DividerImeController dividerImeController = this.mImePositionProcessor;
                Objects.requireNonNull(dividerImeController);
                dividerImeController.mMainExecutor.execute(new BaseWifiTracker$$ExternalSyntheticLambda0(dividerImeController, 5));
            }
            if (z2) {
                DividerView dividerView2 = this.mView;
                long j = (long) (Settings.Global.getFloat(this.mContext.getContentResolver(), "transition_animation_scale", this.mContext.getResources().getFloat(17105061)) * 336.0f);
                boolean z4 = this.mHomeStackResizable;
                Objects.requireNonNull(dividerView2);
                dividerView2.mHomeStackResizable = z4;
                dividerView2.updateDockSide();
                if (dividerView2.mDockedStackMinimized != z3) {
                    dividerView2.mIsInMinimizeInteraction = true;
                    dividerView2.mDockedStackMinimized = z3;
                    if (z3) {
                        i = dividerView2.mSnapTargetBeforeMinimized.position;
                    } else {
                        i = dividerView2.getCurrentPosition();
                    }
                    int i2 = i;
                    if (z3) {
                        snapTarget = dividerView2.mSplitLayout.getMinimizedSnapAlgorithm(dividerView2.mHomeStackResizable).getMiddleTarget();
                    } else {
                        snapTarget = dividerView2.mSnapTargetBeforeMinimized;
                    }
                    dividerView2.stopDragging(i2, snapTarget, j, 0, Interpolators.FAST_OUT_SLOW_IN);
                    dividerView2.setAdjustedForIme(false, j);
                }
                if (!z3) {
                    dividerView2.mBackground.animate().withEndAction(dividerView2.mResetBackgroundRunnable);
                }
                dividerView2.mBackground.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(j).start();
            }
            if (!this.mMinimized) {
                DividerImeController dividerImeController2 = this.mImePositionProcessor;
                Objects.requireNonNull(dividerImeController2);
                dividerImeController2.mMainExecutor.execute(new BaseWifiTracker$$ExternalSyntheticLambda1(dividerImeController2, 7));
            }
        }
        updateTouchable();
        if (!this.mWindowManagerProxy.queueSyncTransactionIfWaiting(windowContainerTransaction)) {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    public final void startDismissSplit(boolean z, boolean z2) {
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mSplits;
            Objects.requireNonNull(legacySplitScreenTaskListener);
            LegacySplitScreenTransitions legacySplitScreenTransitions = legacySplitScreenTaskListener.mSplitTransitions;
            Objects.requireNonNull(legacySplitScreenTransitions);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            WindowManagerProxy.buildDismissSplit(windowContainerTransaction, this.mSplits, this.mSplitLayout, !z);
            Transitions transitions = legacySplitScreenTransitions.mTransitions;
            Objects.requireNonNull(transitions);
            transitions.mMainExecutor.execute(new LegacySplitScreenTransitions$$ExternalSyntheticLambda4(legacySplitScreenTransitions, z2, windowContainerTransaction));
            return;
        }
        WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
        LegacySplitScreenTaskListener legacySplitScreenTaskListener2 = this.mSplits;
        Objects.requireNonNull(windowManagerProxy);
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        windowContainerTransaction2.setLaunchRoot(legacySplitScreenTaskListener2.mSecondary.token, (int[]) null, (int[]) null);
        WindowManagerProxy.buildDismissSplit(windowContainerTransaction2, legacySplitScreenTaskListener2, this.mSplitLayout, !z);
        windowManagerProxy.mSyncTransactionQueue.queue(windowContainerTransaction2);
        onDismissSplit();
    }

    public final void update(Configuration configuration) {
        boolean z;
        int i;
        boolean z2;
        int i2;
        if (this.mView == null || !this.mIsKeyguardShowing) {
            z = false;
        } else {
            z = true;
        }
        removeDivider();
        Context displayContext = this.mDisplayController.getDisplayContext(this.mContext.getDisplayId());
        DividerView dividerView = (DividerView) LayoutInflater.from(displayContext).inflate(C1777R.layout.docked_stack_divider, (ViewGroup) null);
        this.mView = dividerView;
        AnimationHandler animationHandler = this.mSfVsyncAnimationHandler;
        Objects.requireNonNull(dividerView);
        dividerView.mSfVsyncAnimationHandler = animationHandler;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mContext.getDisplayId());
        DividerView dividerView2 = this.mView;
        DividerWindowManager dividerWindowManager = this.mWindowManager;
        DividerState dividerState = this.mDividerState;
        ForcedResizableInfoActivityController forcedResizableInfoActivityController = this.mForcedResizableController;
        LegacySplitScreenTaskListener legacySplitScreenTaskListener = this.mSplits;
        LegacySplitDisplayLayout legacySplitDisplayLayout = this.mSplitLayout;
        DividerImeController dividerImeController = this.mImePositionProcessor;
        WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
        Objects.requireNonNull(dividerView2);
        dividerView2.mSplitScreenController = this;
        dividerView2.mWindowManager = dividerWindowManager;
        dividerView2.mState = dividerState;
        dividerView2.mCallback = forcedResizableInfoActivityController;
        dividerView2.mTiles = legacySplitScreenTaskListener;
        dividerView2.mSplitLayout = legacySplitDisplayLayout;
        dividerView2.mImeController = dividerImeController;
        dividerView2.mWindowManagerProxy = windowManagerProxy;
        if (dividerState.mRatioPositionBeforeMinimized == 0.0f) {
            dividerView2.mSnapTargetBeforeMinimized = legacySplitDisplayLayout.getSnapAlgorithm().getMiddleTarget();
        } else {
            dividerView2.repositionSnapTargetBeforeMinimized();
        }
        DividerView dividerView3 = this.mView;
        if (this.mVisible) {
            i = 0;
        } else {
            i = 4;
        }
        dividerView3.setVisibility(i);
        this.mView.setMinimizedDockStack(this.mMinimized, this.mHomeStackResizable, (SurfaceControl.Transaction) null);
        int dimensionPixelSize = displayContext.getResources().getDimensionPixelSize(17105203);
        if (configuration.orientation == 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            i2 = dimensionPixelSize;
        } else {
            Objects.requireNonNull(displayLayout);
            i2 = displayLayout.mWidth;
        }
        if (z2) {
            Objects.requireNonNull(displayLayout);
            dimensionPixelSize = displayLayout.mHeight;
        }
        int i3 = dimensionPixelSize;
        DividerWindowManager dividerWindowManager2 = this.mWindowManager;
        DividerView dividerView4 = this.mView;
        int displayId = this.mContext.getDisplayId();
        Objects.requireNonNull(dividerWindowManager2);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i2, i3, 2034, 545521704, -3);
        dividerWindowManager2.mLp = layoutParams;
        layoutParams.token = new Binder();
        dividerWindowManager2.mLp.setTitle("DockedStackDivider");
        WindowManager.LayoutParams layoutParams2 = dividerWindowManager2.mLp;
        layoutParams2.privateFlags |= 536870976;
        layoutParams2.layoutInDisplayCutoutMode = 3;
        dividerView4.setSystemUiVisibility(1792);
        dividerWindowManager2.mSystemWindows.addView(dividerView4, dividerWindowManager2.mLp, displayId, 0);
        dividerWindowManager2.mView = dividerView4;
        if (this.mMinimized) {
            this.mView.setMinimizedDockStack(true, this.mHomeStackResizable, (SurfaceControl.Transaction) null);
            updateTouchable();
        }
        DividerView dividerView5 = this.mView;
        Objects.requireNonNull(dividerView5);
        if (dividerView5.mSurfaceHidden != z) {
            dividerView5.mSurfaceHidden = z;
            dividerView5.post(new DividerView$$ExternalSyntheticLambda1(dividerView5, z));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateTouchable() {
        /*
            r7 = this;
            com.android.wm.shell.legacysplitscreen.DividerWindowManager r0 = r7.mWindowManager
            boolean r7 = r7.mAdjustedForIme
            r1 = 1
            r7 = r7 ^ r1
            java.util.Objects.requireNonNull(r0)
            android.view.View r2 = r0.mView
            if (r2 != 0) goto L_0x000e
            goto L_0x0037
        L_0x000e:
            r3 = 0
            if (r7 != 0) goto L_0x001e
            android.view.WindowManager$LayoutParams r4 = r0.mLp
            int r5 = r4.flags
            r6 = r5 & 16
            if (r6 != 0) goto L_0x001e
            r7 = r5 | 16
            r4.flags = r7
            goto L_0x002e
        L_0x001e:
            if (r7 == 0) goto L_0x002d
            android.view.WindowManager$LayoutParams r7 = r0.mLp
            int r4 = r7.flags
            r5 = r4 & 16
            if (r5 == 0) goto L_0x002d
            r3 = r4 & -17
            r7.flags = r3
            goto L_0x002e
        L_0x002d:
            r1 = r3
        L_0x002e:
            if (r1 == 0) goto L_0x0037
            com.android.wm.shell.common.SystemWindows r7 = r0.mSystemWindows
            android.view.WindowManager$LayoutParams r0 = r0.mLp
            r7.updateViewLayout(r2, r0)
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController.updateTouchable():void");
    }

    public final void updateVisibility(boolean z) {
        int i;
        if (this.mVisible != z) {
            this.mVisible = z;
            DividerView dividerView = this.mView;
            if (z) {
                i = 0;
            } else {
                i = 4;
            }
            dividerView.setVisibility(i);
            if (z) {
                DividerView dividerView2 = this.mView;
                boolean z2 = this.mHomeStackResizable;
                Objects.requireNonNull(dividerView2);
                if (dividerView2.mSurfaceHidden) {
                    dividerView2.mSurfaceHidden = false;
                    dividerView2.post(new DividerView$$ExternalSyntheticLambda1(dividerView2, false));
                }
                DividerSnapAlgorithm.SnapTarget middleTarget = dividerView2.mSplitLayout.getMinimizedSnapAlgorithm(z2).getMiddleTarget();
                if (dividerView2.mDockedStackMinimized) {
                    int i2 = middleTarget.position;
                    dividerView2.mDividerPositionX = i2;
                    dividerView2.mDividerPositionY = i2;
                }
                WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
                StageCoordinator$1$$ExternalSyntheticLambda0 stageCoordinator$1$$ExternalSyntheticLambda0 = new StageCoordinator$1$$ExternalSyntheticLambda0(this, 1);
                Objects.requireNonNull(windowManagerProxy);
                windowManagerProxy.mSyncTransactionQueue.runInSync(stageCoordinator$1$$ExternalSyntheticLambda0);
            } else {
                DividerView dividerView3 = this.mView;
                Objects.requireNonNull(dividerView3);
                SurfaceControl viewSurface = dividerView3.mWindowManager.mSystemWindows.getViewSurface(dividerView3);
                if (viewSurface != null) {
                    SurfaceControl.Transaction transaction = dividerView3.mTiles.getTransaction();
                    transaction.hide(viewSurface);
                    dividerView3.mImeController.setDimsHidden(transaction, true);
                    transaction.apply();
                    dividerView3.mTiles.releaseTransaction(transaction);
                    int i3 = dividerView3.mSplitLayout.getSnapAlgorithm().getMiddleTarget().position;
                    WindowManagerProxy windowManagerProxy2 = dividerView3.mWindowManagerProxy;
                    LegacySplitDisplayLayout legacySplitDisplayLayout = dividerView3.mSplitLayout;
                    Objects.requireNonNull(windowManagerProxy2);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    legacySplitDisplayLayout.resizeSplits(i3, windowContainerTransaction);
                    windowManagerProxy2.mSyncTransactionQueue.queue(windowContainerTransaction);
                }
                WindowManagerProxy windowManagerProxy3 = this.mWindowManagerProxy;
                StageCoordinator$2$$ExternalSyntheticLambda0 stageCoordinator$2$$ExternalSyntheticLambda0 = new StageCoordinator$2$$ExternalSyntheticLambda0(this, 1);
                Objects.requireNonNull(windowManagerProxy3);
                windowManagerProxy3.mSyncTransactionQueue.runInSync(stageCoordinator$2$$ExternalSyntheticLambda0);
            }
            synchronized (this.mDockedStackExistsListeners) {
                this.mDockedStackExistsListeners.removeIf(new LegacySplitScreenController$$ExternalSyntheticLambda2(z));
            }
        }
    }

    public LegacySplitScreenController(Context context, DisplayController displayController, SystemWindows systemWindows, DisplayImeController displayImeController, TransactionPool transactionPool, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, TaskStackListenerImpl taskStackListenerImpl, Transitions transitions, ShellExecutor shellExecutor, AnimationHandler animationHandler) {
        this.mContext = context;
        this.mDisplayController = displayController;
        this.mImeController = displayImeController;
        this.mMainExecutor = shellExecutor;
        this.mSfVsyncAnimationHandler = animationHandler;
        this.mForcedResizableController = new ForcedResizableInfoActivityController(context, this, shellExecutor);
        this.mTransactionPool = transactionPool;
        this.mWindowManagerProxy = new WindowManagerProxy(syncTransactionQueue, shellTaskOrganizer);
        this.mTaskOrganizer = shellTaskOrganizer;
        LegacySplitScreenTaskListener legacySplitScreenTaskListener = new LegacySplitScreenTaskListener(this, shellTaskOrganizer, transitions, syncTransactionQueue);
        this.mSplits = legacySplitScreenTaskListener;
        this.mImePositionProcessor = new DividerImeController(legacySplitScreenTaskListener, transactionPool, shellExecutor, shellTaskOrganizer);
        this.mRotationController = new LegacySplitScreenController$$ExternalSyntheticLambda0(this);
        this.mWindowManager = new DividerWindowManager(systemWindows);
        if (context.getResources().getBoolean(17891798)) {
            displayController.addDisplayWindowListener(this);
            taskStackListenerImpl.addListener(new TaskStackListenerCallback() {
                public final void onActivityDismissingDockedStack() {
                    ForcedResizableInfoActivityController forcedResizableInfoActivityController = LegacySplitScreenController.this.mForcedResizableController;
                    Objects.requireNonNull(forcedResizableInfoActivityController);
                    Toast.makeText(forcedResizableInfoActivityController.mContext, C1777R.string.dock_non_resizeble_failed_to_dock_text, 0).show();
                }

                public final void onActivityForcedResizable(String str, int i, int i2) {
                    boolean z;
                    ForcedResizableInfoActivityController forcedResizableInfoActivityController = LegacySplitScreenController.this.mForcedResizableController;
                    Objects.requireNonNull(forcedResizableInfoActivityController);
                    if (str == null) {
                        z = false;
                    } else if (ThemeOverlayApplier.SYSUI_PACKAGE.equals(str)) {
                        z = true;
                    } else {
                        boolean contains = forcedResizableInfoActivityController.mPackagesShownInSession.contains(str);
                        forcedResizableInfoActivityController.mPackagesShownInSession.add(str);
                        z = contains;
                    }
                    if (!z) {
                        forcedResizableInfoActivityController.mPendingTasks.add(new ForcedResizableInfoActivityController.PendingTaskRecord(i, i2));
                        forcedResizableInfoActivityController.mMainExecutor.removeCallbacks(forcedResizableInfoActivityController.mTimeoutRunnable);
                        forcedResizableInfoActivityController.mMainExecutor.executeDelayed(forcedResizableInfoActivityController.mTimeoutRunnable, 1000);
                    }
                }

                public final void onActivityLaunchOnSecondaryDisplayFailed() {
                    ForcedResizableInfoActivityController forcedResizableInfoActivityController = LegacySplitScreenController.this.mForcedResizableController;
                    Objects.requireNonNull(forcedResizableInfoActivityController);
                    Toast.makeText(forcedResizableInfoActivityController.mContext, C1777R.string.activity_launch_on_secondary_display_failed_text, 0).show();
                }

                public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                    if (z2 && runningTaskInfo.getWindowingMode() == 3) {
                        LegacySplitScreenTaskListener legacySplitScreenTaskListener = LegacySplitScreenController.this.mSplits;
                        Objects.requireNonNull(legacySplitScreenTaskListener);
                        if (legacySplitScreenTaskListener.mSplitScreenSupported) {
                            LegacySplitScreenController legacySplitScreenController = LegacySplitScreenController.this;
                            Objects.requireNonNull(legacySplitScreenController);
                            if (legacySplitScreenController.mMinimized) {
                                LegacySplitScreenController.this.onUndockingTask();
                            }
                        }
                    }
                }
            });
        }
    }

    public final void registerInSplitScreenListener(Consumer<Boolean> consumer) {
        consumer.accept(Boolean.valueOf(isDividerVisible()));
        synchronized (this.mDockedStackExistsListeners) {
            this.mDockedStackExistsListeners.add(new WeakReference(consumer));
        }
    }
}
