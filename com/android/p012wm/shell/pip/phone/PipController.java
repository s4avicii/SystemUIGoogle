package com.android.p012wm.shell.pip.phone;

import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Size;
import android.util.Slog;
import android.window.WindowContainerTransaction;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.IPip;
import com.android.p012wm.shell.pip.IPipAnimationListener;
import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.systemui.p006qs.QSFgsManagerFooter$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController */
public final class PipController implements PipTransitionController.PipTransitionCallback, RemoteCallable<PipController> {
    public PipAppOpsListener mAppOpsListener;
    public Context mContext;
    public DisplayController mDisplayController;
    @VisibleForTesting
    public final DisplayController.OnDisplaysChangedListener mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() {
        public final void onDisplayAdded(int i) {
            PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            if (i == pipBoundsState.mDisplayId) {
                PipController pipController = PipController.this;
                pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), false);
            }
        }

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            if (i == pipBoundsState.mDisplayId) {
                PipController pipController = PipController.this;
                pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), true);
            }
        }

        public final void onFixedRotationFinished() {
            PipController.this.mIsInFixedRotation = false;
        }

        public final void onFixedRotationStarted(int i) {
            PipController.this.mIsInFixedRotation = true;
        }
    };
    public final PipImpl mImpl;
    public boolean mIsInFixedRotation;
    public ShellExecutor mMainExecutor;
    public PipMediaController mMediaController;
    public PhonePipMenuController mMenuController;
    public Optional<OneHandedController> mOneHandedController;
    public PipAnimationListener mPinnedStackAnimationRecentsCallback;
    public PipControllerPinnedTaskListener mPinnedTaskListener = new PipControllerPinnedTaskListener();
    public PipBoundsAlgorithm mPipBoundsAlgorithm;
    public PipBoundsState mPipBoundsState;
    public PipInputConsumer mPipInputConsumer;
    public PipTaskOrganizer mPipTaskOrganizer;
    public PipTransitionController mPipTransitionController;
    public final PipController$$ExternalSyntheticLambda2 mRotationController = new PipController$$ExternalSyntheticLambda2(this);
    public TaskStackListenerImpl mTaskStackListener;
    public final Rect mTmpInsetBounds = new Rect();
    public PipTouchHandler mTouchHandler;
    public WindowManagerShellWrapper mWindowManagerShellWrapper;

    /* renamed from: com.android.wm.shell.pip.phone.PipController$PipAnimationListener */
    public interface PipAnimationListener {
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipController$PipControllerPinnedTaskListener */
    public class PipControllerPinnedTaskListener extends PinnedStackListenerForwarder.PinnedTaskListener {
        public PipControllerPinnedTaskListener() {
        }

        public final void onActionsChanged(ParceledListSlice<RemoteAction> parceledListSlice) {
            PhonePipMenuController phonePipMenuController = PipController.this.mMenuController;
            Objects.requireNonNull(phonePipMenuController);
            phonePipMenuController.mAppActions = parceledListSlice;
            phonePipMenuController.updateMenuActions();
        }

        public final void onActivityHidden(ComponentName componentName) {
            PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            if (componentName.equals(pipBoundsState.mLastPipComponentName)) {
                PipController.this.mPipBoundsState.setLastPipComponentName((ComponentName) null);
            }
        }

        public final void onAspectRatioChanged(float f) {
            PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            pipBoundsState.mAspectRatio = f;
            PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
            Objects.requireNonNull(pipTouchHandler);
            PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
            Objects.requireNonNull(pipResizeGestureHandler);
            pipResizeGestureHandler.mUserResizeBounds.setEmpty();
        }

        public final void onImeVisibilityChanged(boolean z, int i) {
            PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            pipBoundsState.mIsImeShowing = z;
            pipBoundsState.mImeHeight = i;
            PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
            Objects.requireNonNull(pipTouchHandler);
            pipTouchHandler.mIsImeShowing = z;
            pipTouchHandler.mImeHeight = i;
        }

        public final void onMovementBoundsChanged(boolean z) {
            PipController.this.updateMovementBounds((Rect) null, false, z, false, (WindowContainerTransaction) null);
        }
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipController$PipImpl */
    public class PipImpl implements Pip {
        public IPipImpl mIPip;

        public PipImpl() {
        }

        public final void addPipExclusionBoundsChangeListener(Consumer<Rect> consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 0));
        }

        public final IPip createExternalInterface() {
            IPipImpl iPipImpl = this.mIPip;
            if (iPipImpl != null) {
                Objects.requireNonNull(iPipImpl);
                iPipImpl.mController = null;
            }
            IPipImpl iPipImpl2 = new IPipImpl(PipController.this);
            this.mIPip = iPipImpl2;
            return iPipImpl2;
        }

        public final void dump(PrintWriter printWriter) {
            try {
                PipController.this.mMainExecutor.executeBlocking$1(new BubblesManager$5$$ExternalSyntheticLambda3(this, printWriter, 2));
            } catch (InterruptedException unused) {
                Slog.e("PipController", "Failed to dump PipController in 2s");
            }
        }

        public final void hidePipMenu() {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda2(this));
        }

        public final void onConfigurationChanged(Configuration configuration) {
            PipController.this.mMainExecutor.execute(new Monitor$$ExternalSyntheticLambda0(this, configuration, 4));
        }

        public final void onDensityOrFontScaleChanged() {
            PipController.this.mMainExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda7(this, 7));
        }

        public final void onOverlayChanged() {
            PipController.this.mMainExecutor.execute(new StatusBar$$ExternalSyntheticLambda19(this, 7));
        }

        public final void onSystemUiStateChanged(boolean z, int i) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda3(this, z, i));
        }

        public final void registerSessionListenerForCurrentUser() {
            PipController.this.mMainExecutor.execute(new ScrimView$$ExternalSyntheticLambda0(this, 6));
        }

        public final void removePipExclusionBoundsChangeListener(Consumer<Rect> consumer) {
            PipController.this.mMainExecutor.execute(new ScrimView$$ExternalSyntheticLambda1(this, consumer, 5));
        }

        public final void setPinnedStackAnimationType() {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda1(this, 1, 0));
        }

        public final void showPictureInPictureMenu() {
            PipController.this.mMainExecutor.execute(new CreateUserActivity$$ExternalSyntheticLambda2(this, 7));
        }
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipController$IPipImpl */
    public static class IPipImpl extends IPip.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public PipController mController;
        public final SingleInstanceRemoteListener<PipController, IPipAnimationListener> mListener;
        public final C19041 mPipAnimationListener = new PipAnimationListener() {
        };

        public IPipImpl(PipController pipController) {
            this.mController = pipController;
            this.mListener = new SingleInstanceRemoteListener<>(pipController, new PipController$IPipImpl$$ExternalSyntheticLambda0(this), BubbleStackView$$ExternalSyntheticLambda28.INSTANCE$2);
        }
    }

    public final void onDisplayChanged(DisplayLayout displayLayout, boolean z) {
        boolean z2;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        DisplayLayout displayLayout2 = pipBoundsState.mDisplayLayout;
        Objects.requireNonNull(displayLayout2);
        if (displayLayout2.mWidth == displayLayout.mWidth && displayLayout2.mHeight == displayLayout.mHeight && displayLayout2.mRotation == displayLayout.mRotation && displayLayout2.mDensityDpi == displayLayout.mDensityDpi && Objects.equals(displayLayout2.mCutout, displayLayout.mCutout)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3 = new PipController$$ExternalSyntheticLambda3(this, displayLayout, 0);
            if (!this.mPipTaskOrganizer.isInPip() || !z) {
                pipController$$ExternalSyntheticLambda3.run();
                return;
            }
            PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
            Objects.requireNonNull(pipBoundsAlgorithm);
            PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
            Rect rect = new Rect(this.mPipBoundsState.getBounds());
            PipBoundsAlgorithm pipBoundsAlgorithm2 = this.mPipBoundsAlgorithm;
            Objects.requireNonNull(pipBoundsAlgorithm2);
            Rect movementBounds = pipBoundsAlgorithm2.getMovementBounds(rect, true);
            PipBoundsState pipBoundsState2 = this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState2);
            float snapFraction = pipSnapAlgorithm.getSnapFraction(rect, movementBounds, pipBoundsState2.mStashedState);
            pipController$$ExternalSyntheticLambda3.run();
            Rect movementBounds2 = this.mPipBoundsAlgorithm.getMovementBounds(rect, false);
            PipBoundsState pipBoundsState3 = this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState3);
            int i = pipBoundsState3.mStashedState;
            PipBoundsState pipBoundsState4 = this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState4);
            int i2 = pipBoundsState4.mStashOffset;
            Rect displayBounds = this.mPipBoundsState.getDisplayBounds();
            PipBoundsState pipBoundsState5 = this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState5);
            DisplayLayout displayLayout3 = pipBoundsState5.mDisplayLayout;
            Objects.requireNonNull(displayLayout3);
            PipSnapAlgorithm.applySnapFraction(rect, movementBounds2, snapFraction, i, i2, displayBounds, displayLayout3.mStableInsets);
            PipTouchHandler pipTouchHandler = this.mTouchHandler;
            Objects.requireNonNull(pipTouchHandler);
            PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
            Objects.requireNonNull(pipMotionHelper);
            pipMotionHelper.movePip(rect, false);
        }
    }

    public final void onPipCornerRadiusChanged() {
        if (this.mPinnedStackAnimationRecentsCallback != null) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.pip_corner_radius);
            IPipImpl.C19041 r2 = (IPipImpl.C19041) this.mPinnedStackAnimationRecentsCallback;
            Objects.requireNonNull(r2);
            SingleInstanceRemoteListener<PipController, IPipAnimationListener> singleInstanceRemoteListener = IPipImpl.this.mListener;
            Objects.requireNonNull(singleInstanceRemoteListener);
            L l = singleInstanceRemoteListener.mListener;
            if (l == null) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                return;
            }
            try {
                ((IPipAnimationListener) l).onPipCornerRadiusChanged(dimensionPixelSize);
            } catch (RemoteException e) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
            }
        }
    }

    public final void onPipTransitionStarted(int i, Rect rect) {
        String str;
        Context context = this.mContext;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        Objects.requireNonNull(pipTaskOrganizer);
        InteractionJankMonitor.Configuration.Builder withSurface = InteractionJankMonitor.Configuration.Builder.withSurface(35, context, pipTaskOrganizer.mLeash);
        switch (i) {
            case 2:
                str = "TRANSITION_TO_PIP";
                break;
            case 3:
                str = "TRANSITION_LEAVE_PIP";
                break;
            case 4:
                str = "TRANSITION_LEAVE_PIP_TO_SPLIT_SCREEN";
                break;
            case 5:
                str = "TRANSITION_REMOVE_STACK";
                break;
            case FalsingManager.VERSION:
                str = "TRANSITION_SNAP_AFTER_RESIZE";
                break;
            case 7:
                str = "TRANSITION_USER_RESIZE";
                break;
            case 8:
                str = "TRANSITION_EXPAND_OR_UNEXPAND";
                break;
            default:
                str = "TRANSITION_LEAVE_UNKNOWN";
                break;
        }
        InteractionJankMonitor.getInstance().begin(withSurface.setTag(str).setTimeout(2000));
        if (PipAnimationController.isOutPipDirection(i)) {
            PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
            Objects.requireNonNull(pipBoundsAlgorithm);
            Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect, true);
            PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
            Objects.requireNonNull(pipSnapAlgorithm);
            float snapFraction = pipSnapAlgorithm.getSnapFraction(rect, movementBounds, 0);
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            if (pipBoundsState.mHasUserResizedPip) {
                PipTouchHandler pipTouchHandler = this.mTouchHandler;
                Objects.requireNonNull(pipTouchHandler);
                PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                Objects.requireNonNull(pipResizeGestureHandler);
                Rect rect2 = pipResizeGestureHandler.mUserResizeBounds;
                Size size = new Size(rect2.width(), rect2.height());
                PipBoundsState pipBoundsState2 = this.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState2);
                pipBoundsState2.mPipReentryState = new PipBoundsState.PipReentryState(size, snapFraction);
            } else {
                PipBoundsState pipBoundsState3 = this.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState3);
                pipBoundsState3.mPipReentryState = new PipBoundsState.PipReentryState((Size) null, snapFraction);
            }
        }
        PipTouchHandler pipTouchHandler2 = this.mTouchHandler;
        Objects.requireNonNull(pipTouchHandler2);
        PipTouchState pipTouchState = pipTouchHandler2.mTouchState;
        Objects.requireNonNull(pipTouchState);
        pipTouchState.mAllowTouches = false;
        if (pipTouchState.mIsUserInteracting) {
            pipTouchState.reset();
        }
        PipAnimationListener pipAnimationListener = this.mPinnedStackAnimationRecentsCallback;
        if (pipAnimationListener != null) {
            SingleInstanceRemoteListener<PipController, IPipAnimationListener> singleInstanceRemoteListener = IPipImpl.this.mListener;
            Objects.requireNonNull(singleInstanceRemoteListener);
            L l = singleInstanceRemoteListener.mListener;
            if (l == null) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                return;
            }
            try {
                ((IPipAnimationListener) l).onPipAnimationStarted();
            } catch (RemoteException e) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00dd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateMovementBounds(android.graphics.Rect r18, boolean r19, boolean r20, boolean r21, android.window.WindowContainerTransaction r22) {
        /*
            r17 = this;
            r0 = r17
            android.graphics.Rect r1 = new android.graphics.Rect
            r2 = r18
            r1.<init>(r2)
            com.android.wm.shell.pip.PipBoundsState r2 = r0.mPipBoundsState
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.common.DisplayLayout r2 = r2.mDisplayLayout
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mRotation
            com.android.wm.shell.pip.PipBoundsAlgorithm r3 = r0.mPipBoundsAlgorithm
            android.graphics.Rect r4 = r0.mTmpInsetBounds
            r3.getInsetBounds(r4)
            com.android.wm.shell.pip.PipBoundsState r3 = r0.mPipBoundsState
            com.android.wm.shell.pip.PipBoundsAlgorithm r4 = r0.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r4)
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            r6 = 0
            android.graphics.Rect r7 = r4.getDefaultBounds(r5, r6)
            com.android.wm.shell.pip.PipBoundsState r8 = r4.mPipBoundsState
            java.util.Objects.requireNonNull(r8)
            float r8 = r8.mAspectRatio
            r9 = 0
            android.graphics.Rect r4 = r4.transformBoundsToAspectRatioIfValid(r7, r8, r9, r9)
            java.util.Objects.requireNonNull(r3)
            android.graphics.Rect r3 = r3.mNormalBounds
            r3.set(r4)
            boolean r3 = r1.isEmpty()
            if (r3 == 0) goto L_0x0050
            com.android.wm.shell.pip.PipBoundsAlgorithm r3 = r0.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r3)
            android.graphics.Rect r3 = r3.getDefaultBounds(r5, r6)
            r1.set(r3)
        L_0x0050:
            com.android.wm.shell.pip.PipTaskOrganizer r3 = r0.mPipTaskOrganizer
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.mWaitForFixedRotation
            r6 = 1
            if (r4 == 0) goto L_0x0066
            com.android.wm.shell.pip.PipTransitionState r4 = r3.mPipTransitionState
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mState
            r7 = 4
            if (r4 == r7) goto L_0x0066
            r4 = r6
            goto L_0x0067
        L_0x0066:
            r4 = r9
        L_0x0067:
            com.android.wm.shell.pip.PipTransitionState r7 = r3.mPipTransitionState
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mInSwipePipToHomeTransition
            if (r7 != 0) goto L_0x0072
            if (r4 == 0) goto L_0x0076
        L_0x0072:
            if (r19 == 0) goto L_0x0076
            goto L_0x015b
        L_0x0076:
            com.android.wm.shell.pip.PipAnimationController r4 = r3.mPipAnimationController
            java.util.Objects.requireNonNull(r4)
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r4 = r4.mCurrentAnimator
            if (r4 == 0) goto L_0x00dd
            boolean r7 = r4.isRunning()
            if (r7 == 0) goto L_0x00dd
            int r7 = r4.getTransitionDirection()
            r8 = 2
            if (r7 == r8) goto L_0x008d
            goto L_0x00dd
        L_0x008d:
            android.graphics.Rect r7 = r4.mDestinationBounds
            r1.set(r7)
            if (r20 != 0) goto L_0x00a4
            if (r21 != 0) goto L_0x00a4
            com.android.wm.shell.pip.PipBoundsState r8 = r3.mPipBoundsState
            android.graphics.Rect r8 = r8.getDisplayBounds()
            boolean r8 = r8.contains(r7)
            if (r8 == 0) goto L_0x00a4
            goto L_0x015b
        L_0x00a4:
            com.android.wm.shell.pip.PipBoundsAlgorithm r8 = r3.mPipBoundsAlgorithm
            android.graphics.Rect r8 = r8.getEntryDestinationBounds()
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x00b2
            goto L_0x015b
        L_0x00b2:
            int r7 = r4.getAnimationType()
            if (r7 != 0) goto L_0x00d5
            boolean r7 = r3.mWaitForFixedRotation
            if (r7 == 0) goto L_0x00d2
            com.android.wm.shell.pip.PipBoundsState r7 = r3.mPipBoundsState
            android.graphics.Rect r7 = r7.getDisplayBounds()
            android.graphics.Rect r10 = new android.graphics.Rect
            r10.<init>(r8)
            int r11 = r3.mNextRotation
            int r3 = r3.mCurrentRotation
            android.util.RotationUtils.rotateBounds(r10, r7, r11, r3)
            r4.updateEndValue(r10)
            goto L_0x00d5
        L_0x00d2:
            r4.updateEndValue(r8)
        L_0x00d5:
            r4.setDestinationBounds(r8)
            r1.set(r8)
            goto L_0x015b
        L_0x00dd:
            com.android.wm.shell.pip.PipTransitionState r7 = r3.mPipTransitionState
            boolean r7 = r7.isInPip()
            if (r7 == 0) goto L_0x00e9
            if (r19 == 0) goto L_0x00e9
            r7 = r6
            goto L_0x00ea
        L_0x00e9:
            r7 = r9
        L_0x00ea:
            if (r7 == 0) goto L_0x00f6
            boolean r8 = com.android.p012wm.shell.transition.Transitions.ENABLE_SHELL_TRANSITIONS
            if (r8 == 0) goto L_0x00f6
            com.android.wm.shell.pip.PipBoundsState r3 = r3.mPipBoundsState
            r3.setBounds(r1)
            goto L_0x015b
        L_0x00f6:
            if (r7 == 0) goto L_0x0106
            boolean r8 = r3.mWaitForFixedRotation
            if (r8 == 0) goto L_0x0106
            boolean r8 = r3.mHasFadeOut
            if (r8 == 0) goto L_0x0106
            com.android.wm.shell.pip.PipBoundsState r3 = r3.mPipBoundsState
            r3.setBounds(r1)
            goto L_0x015b
        L_0x0106:
            if (r7 == 0) goto L_0x0130
            com.android.wm.shell.pip.PipBoundsState r7 = r3.mPipBoundsState
            r7.setBounds(r1)
            if (r4 == 0) goto L_0x0125
            int r7 = r4.getTransitionDirection()
            r4.removeAllUpdateListeners()
            r4.removeAllListeners()
            r4.cancel()
            com.android.wm.shell.pip.PipTransitionController r4 = r3.mPipTransitionController
            r4.sendOnPipTransitionCancelled(r7)
            r3.sendOnPipTransitionFinished(r7)
            goto L_0x0126
        L_0x0125:
            r7 = r9
        L_0x0126:
            android.view.SurfaceControl$Transaction r4 = r3.createFinishResizeSurfaceTransaction(r1)
            r8 = r22
            r3.prepareFinishResizeTransaction(r1, r7, r4, r8)
            goto L_0x015b
        L_0x0130:
            if (r4 == 0) goto L_0x0146
            boolean r7 = r4.isRunning()
            if (r7 == 0) goto L_0x0146
            android.graphics.Rect r3 = r4.mDestinationBounds
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x015b
            android.graphics.Rect r3 = r4.mDestinationBounds
            r1.set(r3)
            goto L_0x015b
        L_0x0146:
            com.android.wm.shell.pip.PipBoundsState r4 = r3.mPipBoundsState
            android.graphics.Rect r4 = r4.getBounds()
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x015b
            com.android.wm.shell.pip.PipBoundsState r3 = r3.mPipBoundsState
            android.graphics.Rect r3 = r3.getBounds()
            r1.set(r3)
        L_0x015b:
            com.android.wm.shell.pip.PipTaskOrganizer r3 = r0.mPipTaskOrganizer
            r3.finishResizeForMenu(r1)
            com.android.wm.shell.pip.phone.PipTouchHandler r3 = r0.mTouchHandler
            android.graphics.Rect r4 = r0.mTmpInsetBounds
            com.android.wm.shell.pip.PipBoundsState r0 = r0.mPipBoundsState
            java.util.Objects.requireNonNull(r0)
            android.graphics.Rect r0 = r0.mNormalBounds
            java.util.Objects.requireNonNull(r3)
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r7 = r3.mPipResizeGestureHandler
            java.util.Objects.requireNonNull(r7)
            android.graphics.Rect r7 = r7.mUserResizeBounds
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L_0x0185
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r7 = r3.mPipResizeGestureHandler
            java.util.Objects.requireNonNull(r7)
            android.graphics.Rect r7 = r7.mUserResizeBounds
            r7.set(r0)
        L_0x0185:
            boolean r7 = r3.mIsImeShowing
            if (r7 == 0) goto L_0x018c
            int r7 = r3.mImeHeight
            goto L_0x018d
        L_0x018c:
            r7 = r9
        L_0x018d:
            int r8 = r3.mDisplayRotation
            if (r8 == r2) goto L_0x0193
            r8 = r6
            goto L_0x0194
        L_0x0193:
            r8 = r9
        L_0x0194:
            if (r8 == 0) goto L_0x019b
            com.android.wm.shell.pip.phone.PipTouchState r8 = r3.mTouchState
            r8.reset()
        L_0x019b:
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            com.android.wm.shell.pip.PipBoundsAlgorithm r10 = r3.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r10)
            com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r0, r4, r8, r7)
            com.android.wm.shell.pip.PipBoundsState r10 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r10)
            android.graphics.Rect r10 = r10.mMovementBounds
            boolean r10 = r10.isEmpty()
            if (r10 == 0) goto L_0x01c4
            com.android.wm.shell.pip.PipBoundsAlgorithm r10 = r3.mPipBoundsAlgorithm
            com.android.wm.shell.pip.PipBoundsState r11 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r11)
            android.graphics.Rect r11 = r11.mMovementBounds
            java.util.Objects.requireNonNull(r10)
            com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r1, r4, r11, r9)
        L_0x01c4:
            int r10 = r0.width()
            float r10 = (float) r10
            int r11 = r0.height()
            float r11 = (float) r11
            float r10 = r10 / r11
            android.graphics.Point r11 = new android.graphics.Point
            r11.<init>()
            android.content.Context r12 = r3.mContext
            android.view.Display r12 = r12.getDisplay()
            r12.getRealSize(r11)
            com.android.wm.shell.pip.PipBoundsAlgorithm r12 = r3.mPipBoundsAlgorithm
            int r13 = r3.mExpandedShortestEdgeSize
            float r13 = (float) r13
            int r14 = r11.x
            int r11 = r11.y
            android.util.Size r11 = r12.getSizeForAspectRatio(r10, r13, r14, r11)
            com.android.wm.shell.pip.PipBoundsState r12 = r3.mPipBoundsState
            android.graphics.Rect r13 = new android.graphics.Rect
            int r14 = r11.getWidth()
            int r11 = r11.getHeight()
            r13.<init>(r9, r9, r14, r11)
            java.util.Objects.requireNonNull(r12)
            android.graphics.Rect r11 = r12.mExpandedBounds
            r11.set(r13)
            android.graphics.Rect r11 = new android.graphics.Rect
            r11.<init>()
            com.android.wm.shell.pip.PipBoundsAlgorithm r12 = r3.mPipBoundsAlgorithm
            com.android.wm.shell.pip.PipBoundsState r13 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r13)
            android.graphics.Rect r13 = r13.mExpandedBounds
            java.util.Objects.requireNonNull(r12)
            com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r13, r4, r11, r7)
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r7 = r3.mPipResizeGestureHandler
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mEnablePinchResize
            if (r7 == 0) goto L_0x02bd
            com.android.wm.shell.pip.PipBoundsState r7 = r3.mPipBoundsState
            android.graphics.Rect r7 = r7.getDisplayBounds()
            int r7 = r7.width()
            com.android.wm.shell.pip.PipBoundsState r12 = r3.mPipBoundsState
            android.graphics.Rect r12 = r12.getDisplayBounds()
            int r12 = r12.height()
            int r7 = java.lang.Math.min(r7, r12)
            int r12 = r4.left
            com.android.wm.shell.pip.PipBoundsState r13 = r3.mPipBoundsState
            android.graphics.Rect r13 = r13.getDisplayBounds()
            int r13 = r13.width()
            int r14 = r4.right
            int r13 = r13 - r14
            int r13 = r13 + r12
            int r12 = r4.top
            com.android.wm.shell.pip.PipBoundsState r14 = r3.mPipBoundsState
            android.graphics.Rect r14 = r14.getDisplayBounds()
            int r14 = r14.height()
            int r15 = r4.bottom
            int r14 = r14 - r15
            int r14 = r14 + r12
            r12 = 1065353216(0x3f800000, float:1.0)
            int r12 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r12 <= 0) goto L_0x027a
            int r12 = r0.width()
            float r12 = (float) r12
            float r14 = (float) r7
            float r15 = r3.mMinimumSizePercent
            float r14 = r14 * r15
            float r12 = java.lang.Math.min(r12, r14)
            int r12 = (int) r12
            float r14 = (float) r12
            float r14 = r14 / r10
            int r14 = (int) r14
            int r15 = r0.width()
            int r7 = r7 - r13
            int r7 = java.lang.Math.max(r15, r7)
            float r13 = (float) r7
            float r13 = r13 / r10
            int r10 = (int) r13
            goto L_0x029e
        L_0x027a:
            int r12 = r0.height()
            float r12 = (float) r12
            float r13 = (float) r7
            float r15 = r3.mMinimumSizePercent
            float r13 = r13 * r15
            float r12 = java.lang.Math.min(r12, r13)
            int r12 = (int) r12
            float r13 = (float) r12
            float r13 = r13 * r10
            int r13 = (int) r13
            int r15 = r0.height()
            int r7 = r7 - r14
            int r7 = java.lang.Math.max(r15, r7)
            float r14 = (float) r7
            float r14 = r14 * r10
            int r10 = (int) r14
            r14 = r12
            r12 = r13
            r16 = r10
            r10 = r7
            r7 = r16
        L_0x029e:
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r13 = r3.mPipResizeGestureHandler
            r13.updateMinSize(r12, r14)
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r13 = r3.mPipResizeGestureHandler
            r13.updateMaxSize(r7, r10)
            com.android.wm.shell.pip.PipBoundsState r13 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r13)
            android.graphics.Point r13 = r13.mMaxSize
            r13.set(r7, r10)
            com.android.wm.shell.pip.PipBoundsState r7 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r7)
            android.graphics.Point r7 = r7.mMinSize
            r7.set(r12, r14)
            goto L_0x02e5
        L_0x02bd:
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r7 = r3.mPipResizeGestureHandler
            int r10 = r0.width()
            int r12 = r0.height()
            r7.updateMinSize(r10, r12)
            com.android.wm.shell.pip.phone.PipResizeGestureHandler r7 = r3.mPipResizeGestureHandler
            com.android.wm.shell.pip.PipBoundsState r10 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r10)
            android.graphics.Rect r10 = r10.mExpandedBounds
            int r10 = r10.width()
            com.android.wm.shell.pip.PipBoundsState r12 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r12)
            android.graphics.Rect r12 = r12.mExpandedBounds
            int r12 = r12.height()
            r7.updateMaxSize(r10, r12)
        L_0x02e5:
            boolean r7 = r3.mIsImeShowing
            if (r7 == 0) goto L_0x02ec
            int r10 = r3.mImeOffset
            goto L_0x02ed
        L_0x02ec:
            r10 = r9
        L_0x02ed:
            if (r7 != 0) goto L_0x02f6
            boolean r7 = r3.mIsShelfShowing
            if (r7 == 0) goto L_0x02f6
            int r7 = r3.mShelfHeight
            goto L_0x02f7
        L_0x02f6:
            r7 = r9
        L_0x02f7:
            int r7 = java.lang.Math.max(r10, r7)
            if (r20 != 0) goto L_0x02ff
            if (r21 == 0) goto L_0x0378
        L_0x02ff:
            com.android.wm.shell.pip.phone.PipTouchState r10 = r3.mTouchState
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.mIsUserInteracting
            if (r10 == 0) goto L_0x030a
            goto L_0x0378
        L_0x030a:
            int r10 = r3.mMenuState
            if (r10 != r6) goto L_0x0315
            boolean r10 = r3.willResizeMenu()
            if (r10 == 0) goto L_0x0315
            goto L_0x0316
        L_0x0315:
            r6 = r9
        L_0x0316:
            android.graphics.Rect r10 = new android.graphics.Rect
            r10.<init>()
            com.android.wm.shell.pip.PipBoundsAlgorithm r12 = r3.mPipBoundsAlgorithm
            boolean r13 = r3.mIsImeShowing
            if (r13 == 0) goto L_0x0323
            int r9 = r3.mImeHeight
        L_0x0323:
            java.util.Objects.requireNonNull(r12)
            com.android.p012wm.shell.pip.PipBoundsAlgorithm.getMovementBounds(r1, r4, r10, r9)
            com.android.wm.shell.pip.PipBoundsState r9 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r9)
            android.graphics.Rect r9 = r9.mMovementBounds
            int r9 = r9.bottom
            int r12 = r3.mMovementBoundsExtraOffsets
            int r9 = r9 - r12
            int r12 = r10.bottom
            int r13 = r10.top
            if (r12 >= r13) goto L_0x033c
            goto L_0x033d
        L_0x033c:
            int r12 = r12 - r7
        L_0x033d:
            if (r6 == 0) goto L_0x0358
            com.android.wm.shell.pip.PipBoundsState r6 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r6)
            android.graphics.Rect r6 = r6.mExpandedBounds
            r1.set(r6)
            com.android.wm.shell.pip.PipBoundsAlgorithm r6 = r3.mPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r6)
            com.android.wm.shell.pip.PipSnapAlgorithm r6 = r6.mSnapAlgorithm
            float r13 = r3.mSavedSnapFraction
            java.util.Objects.requireNonNull(r6)
            com.android.p012wm.shell.pip.PipSnapAlgorithm.applySnapFraction(r1, r10, r13)
        L_0x0358:
            if (r9 >= r12) goto L_0x0368
            int r6 = r1.top
            int r10 = r3.mBottomOffsetBufferPx
            int r9 = r9 - r10
            if (r6 <= r9) goto L_0x0378
            com.android.wm.shell.pip.phone.PipMotionHelper r9 = r3.mMotionHelper
            int r12 = r12 - r6
            r9.animateToOffset(r1, r12)
            goto L_0x0378
        L_0x0368:
            if (r9 <= r12) goto L_0x0378
            int r6 = r1.top
            int r9 = r3.mBottomOffsetBufferPx
            int r9 = r12 - r9
            if (r6 <= r9) goto L_0x0378
            com.android.wm.shell.pip.phone.PipMotionHelper r9 = r3.mMotionHelper
            int r12 = r12 - r6
            r9.animateToOffset(r1, r12)
        L_0x0378:
            com.android.wm.shell.pip.PipBoundsState r1 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r1 = r1.mNormalMovementBounds
            r1.set(r8)
            com.android.wm.shell.pip.PipBoundsState r1 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r1 = r1.mExpandedMovementBounds
            r1.set(r11)
            r3.mDisplayRotation = r2
            android.graphics.Rect r1 = r3.mInsetBounds
            r1.set(r4)
            r3.updateMovementBounds()
            r3.mMovementBoundsExtraOffsets = r7
            com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection r1 = r3.mConnection
            com.android.wm.shell.pip.PipBoundsState r4 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r4)
            android.graphics.Rect r4 = r4.mExpandedBounds
            com.android.wm.shell.pip.PipBoundsState r6 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r6)
            android.graphics.Rect r6 = r6.mNormalMovementBounds
            com.android.wm.shell.pip.PipBoundsState r7 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r7)
            android.graphics.Rect r7 = r7.mExpandedMovementBounds
            java.util.Objects.requireNonNull(r1)
            android.graphics.Rect r8 = r1.mNormalBounds
            r8.set(r0)
            android.graphics.Rect r8 = r1.mExpandedBounds
            r8.set(r4)
            android.graphics.Rect r4 = r1.mNormalMovementBounds
            r4.set(r6)
            android.graphics.Rect r1 = r1.mExpandedMovementBounds
            r1.set(r7)
            int r1 = r3.mDeferResizeToNormalBoundsUntilRotation
            if (r1 != r2) goto L_0x03f1
            com.android.wm.shell.pip.phone.PipMotionHelper r1 = r3.mMotionHelper
            float r2 = r3.mSavedSnapFraction
            com.android.wm.shell.pip.PipBoundsState r4 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r4)
            android.graphics.Rect r4 = r4.mNormalMovementBounds
            com.android.wm.shell.pip.PipBoundsState r6 = r3.mPipBoundsState
            java.util.Objects.requireNonNull(r6)
            android.graphics.Rect r6 = r6.mMovementBounds
            r7 = 1
            r17 = r1
            r18 = r0
            r19 = r2
            r20 = r4
            r21 = r6
            r22 = r7
            r17.animateToUnexpandedState(r18, r19, r20, r21, r22)
            r3.mSavedSnapFraction = r5
            r0 = -1
            r3.mDeferResizeToNormalBoundsUntilRotation = r0
        L_0x03f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.phone.PipController.updateMovementBounds(android.graphics.Rect, boolean, boolean, boolean, android.window.WindowContainerTransaction):void");
    }

    public PipController(Context context, DisplayController displayController, PipAppOpsListener pipAppOpsListener, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipMediaController pipMediaController, PhonePipMenuController phonePipMenuController, PipTaskOrganizer pipTaskOrganizer, PipTouchHandler pipTouchHandler, PipTransitionController pipTransitionController, WindowManagerShellWrapper windowManagerShellWrapper, TaskStackListenerImpl taskStackListenerImpl, Optional<OneHandedController> optional, ShellExecutor shellExecutor) {
        if (UserManager.get(context).getProcessUserId() == 0) {
            this.mContext = context;
            this.mImpl = new PipImpl();
            this.mWindowManagerShellWrapper = windowManagerShellWrapper;
            this.mDisplayController = displayController;
            this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
            this.mPipBoundsState = pipBoundsState;
            this.mPipTaskOrganizer = pipTaskOrganizer;
            this.mMainExecutor = shellExecutor;
            this.mMediaController = pipMediaController;
            this.mMenuController = phonePipMenuController;
            this.mTouchHandler = pipTouchHandler;
            this.mAppOpsListener = pipAppOpsListener;
            this.mOneHandedController = optional;
            this.mPipTransitionController = pipTransitionController;
            this.mTaskStackListener = taskStackListenerImpl;
            shellExecutor.execute(new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 9));
            return;
        }
        throw new IllegalStateException("Non-primary Pip component not currently supported.");
    }

    public final void onPipTransitionFinishedOrCanceled(int i) {
        InteractionJankMonitor.getInstance().end(35);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        Objects.requireNonNull(pipTouchHandler);
        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
        Objects.requireNonNull(pipTouchState);
        pipTouchState.mAllowTouches = true;
        if (pipTouchState.mIsUserInteracting) {
            pipTouchState.reset();
        }
        PipTouchHandler pipTouchHandler2 = this.mTouchHandler;
        Objects.requireNonNull(pipTouchHandler2);
        pipTouchHandler2.mMotionHelper.synchronizePinnedStackBounds();
        pipTouchHandler2.updateMovementBounds();
        if (i == 2) {
            PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
            Rect bounds = pipTouchHandler2.mPipBoundsState.getBounds();
            Objects.requireNonNull(pipResizeGestureHandler);
            pipResizeGestureHandler.mUserResizeBounds.set(bounds);
        }
    }

    public final void onPipTransitionCanceled(int i) {
        onPipTransitionFinishedOrCanceled(i);
    }

    public final void onPipTransitionFinished(int i) {
        onPipTransitionFinishedOrCanceled(i);
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }
}
