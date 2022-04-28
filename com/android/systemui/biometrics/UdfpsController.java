package com.android.systemui.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.RectF;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda2;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.UdfpsSurfaceView;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.time.SystemClock;
import com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda0;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class UdfpsController implements DozeReceiver {
    public static final VibrationEffect EFFECT_CLICK = VibrationEffect.get(0);
    @VisibleForTesting
    public static final VibrationAttributes VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(65).build();
    public final AccessibilityManager mAccessibilityManager;
    public int mActivePointerId = -1;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public Runnable mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public final C07002 mBroadcastReceiver;
    public final HashSet mCallbacks = new HashSet();
    public Runnable mCancelAodTimeoutAction;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final SystemUIDialogManager mDialogManager;
    public final DumpManager mDumpManager;
    public final Execution mExecution;
    public final FalsingManager mFalsingManager;
    public final DelayableExecutor mFgExecutor;
    public final FingerprintManager mFingerprintManager;
    public boolean mGoodCaptureReceived;
    public final UdfpsHbmProvider mHbmProvider;
    public final LayoutInflater mInflater;
    public boolean mIsAodInterruptActive;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public final LatencyTracker mLatencyTracker;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public boolean mOnFingerDown;
    @VisibleForTesting
    public final BiometricDisplayListener mOrientationListener;
    public UdfpsControllerOverlay mOverlay;
    public final PanelExpansionStateManager mPanelExpansionStateManager;
    public final PowerManager mPowerManager;
    public final C06991 mScreenObserver;
    public boolean mScreenOn;
    @VisibleForTesting
    public final FingerprintSensorPropertiesInternal mSensorProps;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public long mTouchLogTime;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public VelocityTracker mVelocityTracker;
    public final VibratorHelper mVibrator;
    public final WindowManager mWindowManager;

    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    public class UdfpsOverlayController extends IUdfpsOverlayController.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public UdfpsOverlayController() {
        }

        public final void hideUdfpsOverlay(int i) {
            UdfpsController.this.mFgExecutor.execute(new TaskView$$ExternalSyntheticLambda2(this, 2));
        }

        public final void onAcquiredGood(int i) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1(this, i));
        }

        public final void onEnrollmentHelp(int i) {
            UdfpsController.this.mFgExecutor.execute(new StatusBar$$ExternalSyntheticLambda19(this, 1));
        }

        public final void onEnrollmentProgress(int i, int i2) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2(this, i2));
        }

        public final void setDebugMessage(int i, String str) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0(this, str, 0));
        }

        public final void showUdfpsOverlay(int i, int i2, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            UdfpsController.this.mFgExecutor.execute(new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda3(this, i2, iUdfpsOverlayControllerCallback));
        }
    }

    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, PanelExpansionStateManager panelExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, Optional<UdfpsHbmProvider> optional, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityLaunchAnimator activityLaunchAnimator) {
        boolean z;
        ScreenLifecycle screenLifecycle2 = screenLifecycle;
        C06991 r3 = new ScreenLifecycle.Observer() {
            public final void onScreenTurnedOff() {
                UdfpsController.this.mScreenOn = false;
            }

            public final void onScreenTurnedOn() {
                UdfpsController udfpsController = UdfpsController.this;
                udfpsController.mScreenOn = true;
                Runnable runnable = udfpsController.mAodInterruptRunnable;
                if (runnable != null) {
                    runnable.run();
                    UdfpsController.this.mAodInterruptRunnable = null;
                }
            }
        };
        this.mScreenObserver = r3;
        this.mBroadcastReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay != null) {
                    Objects.requireNonNull(udfpsControllerOverlay);
                    if (udfpsControllerOverlay.requestReason != 4 && "android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ACTION_CLOSE_SYSTEM_DIALOGS received, mRequestReason: ");
                        UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsController.this.mOverlay;
                        Objects.requireNonNull(udfpsControllerOverlay2);
                        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, udfpsControllerOverlay2.requestReason, "UdfpsController");
                        UdfpsControllerOverlay udfpsControllerOverlay3 = UdfpsController.this.mOverlay;
                        Objects.requireNonNull(udfpsControllerOverlay3);
                        try {
                            udfpsControllerOverlay3.controllerCallback.onUserCanceled();
                        } catch (RemoteException e) {
                            Log.e("UdfpsControllerOverlay", "Remote exception", e);
                        }
                        UdfpsController.this.hideUdfpsOverlay();
                    }
                }
            }
        };
        this.mContext = context;
        this.mExecution = execution;
        this.mVibrator = vibratorHelper;
        this.mInflater = layoutInflater;
        FingerprintManager fingerprintManager2 = (FingerprintManager) Preconditions.checkNotNull(fingerprintManager);
        this.mFingerprintManager = fingerprintManager2;
        this.mWindowManager = windowManager;
        this.mFgExecutor = delayableExecutor;
        this.mPanelExpansionStateManager = panelExpansionStateManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mDumpManager = dumpManager;
        this.mDialogManager = systemUIDialogManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFalsingManager = falsingManager;
        this.mPowerManager = powerManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = null;
        this.mHbmProvider = optional.orElse((Object) null);
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle2.mObservers.add(r3);
        boolean z2 = true;
        if (screenLifecycle2.mScreenState == 2) {
            z = true;
        } else {
            z = false;
        }
        this.mScreenOn = z;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mConfigurationController = configurationController;
        this.mSystemClock = systemClock;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLatencyTracker = latencyTracker;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        Iterator it = fingerprintManager2.getSensorPropertiesInternal().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal2 = (FingerprintSensorPropertiesInternal) it.next();
            if (fingerprintSensorPropertiesInternal2.isAnyUdfpsType()) {
                fingerprintSensorPropertiesInternal = fingerprintSensorPropertiesInternal2;
                break;
            }
        }
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        Preconditions.checkArgument(fingerprintSensorPropertiesInternal == null ? false : z2);
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, new BiometricDisplayListener.SensorType.UnderDisplayFingerprint(fingerprintSensorPropertiesInternal), new UdfpsController$$ExternalSyntheticLambda1(this));
        this.mFingerprintManager.setUdfpsOverlayController(new UdfpsOverlayController());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
        Objects.requireNonNull(udfpsHapticsSimulator);
        udfpsHapticsSimulator.udfpsController = this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.android.systemui.biometrics.UdfpsAnimationViewController<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.android.systemui.biometrics.UdfpsAnimationViewController<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.graphics.PointF} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.graphics.PointF} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.android.systemui.biometrics.UdfpsAnimationViewController<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.graphics.PointF} */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isWithinSensorArea(com.android.systemui.biometrics.UdfpsView r7, float r8, float r9, boolean r10) {
        /*
            r6 = this;
            r0 = 0
            r1 = 1
            r2 = 0
            if (r10 == 0) goto L_0x0064
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r6 = r7.animationViewController
            if (r6 != 0) goto L_0x000d
            goto L_0x0011
        L_0x000d:
            android.graphics.PointF r0 = r6.getTouchTranslation()
        L_0x0011:
            if (r0 != 0) goto L_0x0019
            android.graphics.PointF r0 = new android.graphics.PointF
            r6 = 0
            r0.<init>(r6, r6)
        L_0x0019:
            android.graphics.RectF r6 = r7.sensorRect
            float r6 = r6.centerX()
            float r10 = r0.x
            float r6 = r6 + r10
            android.graphics.RectF r10 = r7.sensorRect
            float r10 = r10.centerY()
            float r0 = r0.y
            float r10 = r10 + r0
            android.graphics.RectF r0 = r7.sensorRect
            float r3 = r0.right
            float r4 = r0.left
            float r3 = r3 - r4
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r4
            float r5 = r0.bottom
            float r0 = r0.top
            float r5 = r5 - r0
            float r5 = r5 / r4
            float r0 = r7.sensorTouchAreaCoefficient
            float r3 = r3 * r0
            float r4 = r6 - r3
            int r4 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0062
            float r3 = r3 + r6
            int r6 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x0062
            float r5 = r5 * r0
            float r6 = r10 - r5
            int r6 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0062
            float r5 = r5 + r10
            int r6 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r6 >= 0) goto L_0x0062
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r6 = r7.animationViewController
            if (r6 != 0) goto L_0x005b
            r6 = r2
            goto L_0x005f
        L_0x005b:
            boolean r6 = r6.shouldPauseAuth()
        L_0x005f:
            if (r6 != 0) goto L_0x0062
            goto L_0x0063
        L_0x0062:
            r1 = r2
        L_0x0063:
            return r1
        L_0x0064:
            com.android.systemui.biometrics.UdfpsControllerOverlay r7 = r6.mOverlay
            if (r7 == 0) goto L_0x0090
            com.android.systemui.biometrics.UdfpsView r10 = r7.overlayView
            if (r10 != 0) goto L_0x006e
            r10 = r0
            goto L_0x0070
        L_0x006e:
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r10 = r10.animationViewController
        L_0x0070:
            if (r10 != 0) goto L_0x0073
            goto L_0x0090
        L_0x0073:
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.biometrics.UdfpsView r7 = r7.overlayView
            if (r7 != 0) goto L_0x007b
            goto L_0x007d
        L_0x007b:
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r0 = r7.animationViewController
        L_0x007d:
            boolean r7 = r0.shouldPauseAuth()
            if (r7 != 0) goto L_0x008e
            android.graphics.RectF r6 = r6.getSensorLocation()
            boolean r6 = r6.contains(r8, r9)
            if (r6 == 0) goto L_0x008e
            goto L_0x008f
        L_0x008e:
            r1 = r2
        L_0x008f:
            return r1
        L_0x0090:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsController.isWithinSensorArea(com.android.systemui.biometrics.UdfpsView, float, float, boolean):boolean");
    }

    public final void dozeTimeTick() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            Objects.requireNonNull(udfpsControllerOverlay);
            UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
            if (udfpsView != null) {
                udfpsView.dozeTimeTick();
            }
        }
    }

    public final RectF getSensorLocation() {
        SensorLocationInternal location = this.mSensorProps.getLocation();
        int i = location.sensorLocationX;
        int i2 = location.sensorRadius;
        int i3 = location.sensorLocationY;
        return new RectF((float) (i - i2), (float) (i3 - i2), (float) (i + i2), (float) (i3 + i2));
    }

    public final void hideUdfpsOverlay() {
        boolean z;
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
            if (udfpsView != null) {
                onFingerUp(udfpsView);
            }
            UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
            Objects.requireNonNull(udfpsControllerOverlay2);
            UdfpsView udfpsView2 = udfpsControllerOverlay2.overlayView;
            if (udfpsView2 != null) {
                z = true;
            } else {
                z = false;
            }
            if (udfpsView2 != null) {
                if (udfpsView2.isIlluminationRequested) {
                    udfpsView2.stopIllumination();
                }
                udfpsControllerOverlay2.windowManager.removeView(udfpsView2);
                udfpsView2.setOnTouchListener((View.OnTouchListener) null);
                udfpsView2.setOnHoverListener((View.OnHoverListener) null);
                udfpsView2.animationViewController = null;
                UdfpsControllerOverlay$show$1$1 udfpsControllerOverlay$show$1$1 = udfpsControllerOverlay2.overlayTouchListener;
                if (udfpsControllerOverlay$show$1$1 != null) {
                    udfpsControllerOverlay2.accessibilityManager.removeTouchExplorationStateChangeListener(udfpsControllerOverlay$show$1$1);
                }
            }
            udfpsControllerOverlay2.overlayView = null;
            udfpsControllerOverlay2.overlayTouchListener = null;
            if (this.mKeyguardViewManager.isShowingAlternateAuth()) {
                this.mKeyguardViewManager.resetAlternateAuth(true);
            }
            Log.v("UdfpsController", "hideUdfpsOverlay | removing window: " + z);
        } else {
            Log.v("UdfpsController", "hideUdfpsOverlay | the overlay is already hidden");
        }
        this.mOverlay = null;
        this.mOrientationListener.disable();
    }

    public final void onCancelUdfps() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            Objects.requireNonNull(udfpsControllerOverlay);
            if (udfpsControllerOverlay.overlayView != null) {
                UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
                Objects.requireNonNull(udfpsControllerOverlay2);
                onFingerUp(udfpsControllerOverlay2.overlayView);
            }
        }
        if (this.mIsAodInterruptActive) {
            Runnable runnable = this.mCancelAodTimeoutAction;
            if (runnable != null) {
                runnable.run();
                this.mCancelAodTimeoutAction = null;
            }
            this.mIsAodInterruptActive = false;
        }
    }

    public final void onFingerDown(int i, int i2, float f, float f2) {
        UdfpsAnimationViewController<?> udfpsAnimationViewController;
        boolean z;
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "Null request in onFingerDown");
            return;
        }
        UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
        if (udfpsView == null) {
            udfpsAnimationViewController = null;
        } else {
            udfpsAnimationViewController = udfpsView.animationViewController;
        }
        if ((udfpsAnimationViewController instanceof UdfpsKeyguardViewController) && !this.mStatusBarStateController.isDozing()) {
            KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
            Objects.requireNonNull(keyguardBypassController);
            keyguardBypassController.userHasDeviceEntryIntent = true;
        }
        if (!this.mOnFingerDown) {
            playStartHaptic();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            if (keyguardUpdateMonitor.mFaceRunningState == 1) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                this.mKeyguardUpdateMonitor.requestFaceAuth(false);
            }
        }
        this.mOnFingerDown = true;
        this.mFingerprintManager.onPointerDown(this.mSensorProps.sensorId, i, i2, f, f2);
        Trace.endAsyncSection("UdfpsController.e2e.onPointerDown", 0);
        UdfpsControllerOverlay udfpsControllerOverlay2 = this.mOverlay;
        Objects.requireNonNull(udfpsControllerOverlay2);
        UdfpsView udfpsView2 = udfpsControllerOverlay2.overlayView;
        if (udfpsView2 != null) {
            Trace.beginAsyncSection("UdfpsController.e2e.startIllumination", 0);
            StandardWifiEntry$$ExternalSyntheticLambda0 standardWifiEntry$$ExternalSyntheticLambda0 = new StandardWifiEntry$$ExternalSyntheticLambda0(this, 2);
            udfpsView2.isIlluminationRequested = true;
            UdfpsAnimationViewController udfpsAnimationViewController2 = udfpsView2.animationViewController;
            if (udfpsAnimationViewController2 != null) {
                udfpsAnimationViewController2.getView().onIlluminationStarting();
                udfpsAnimationViewController2.getView().postInvalidate();
            }
            UdfpsSurfaceView udfpsSurfaceView = udfpsView2.ghbmView;
            if (udfpsSurfaceView != null) {
                udfpsSurfaceView.mGhbmIlluminationListener = new UdfpsView$startIllumination$1(udfpsView2);
                udfpsSurfaceView.setVisibility(0);
                UdfpsSurfaceView.GhbmIlluminationListener ghbmIlluminationListener = udfpsSurfaceView.mGhbmIlluminationListener;
                if (ghbmIlluminationListener == null) {
                    Log.e("UdfpsSurfaceView", "startIllumination | mGhbmIlluminationListener is null");
                } else if (!udfpsSurfaceView.mHasValidSurface) {
                    udfpsSurfaceView.mAwaitingSurfaceToStartIllumination = true;
                    udfpsSurfaceView.mOnIlluminatedRunnable = standardWifiEntry$$ExternalSyntheticLambda0;
                } else if (ghbmIlluminationListener == null) {
                    Log.e("UdfpsSurfaceView", "doIlluminate | mGhbmIlluminationListener is null");
                } else {
                    ghbmIlluminationListener.enableGhbm(udfpsSurfaceView.mHolder.getSurface(), standardWifiEntry$$ExternalSyntheticLambda0);
                }
            } else {
                udfpsView2.doIlluminate((Surface) null, standardWifiEntry$$ExternalSyntheticLambda0);
            }
        }
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onFingerDown();
        }
    }

    public final void onFingerUp(UdfpsView udfpsView) {
        this.mExecution.assertIsMainThread();
        this.mActivePointerId = -1;
        this.mGoodCaptureReceived = false;
        if (this.mOnFingerDown) {
            this.mFingerprintManager.onPointerUp(this.mSensorProps.sensorId);
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFingerUp();
            }
        }
        this.mOnFingerDown = false;
        Objects.requireNonNull(udfpsView);
        if (udfpsView.isIlluminationRequested) {
            udfpsView.stopIllumination();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouch(android.view.View r18, android.view.MotionEvent r19, boolean r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            r2 = r20
            r3 = r18
            com.android.systemui.biometrics.UdfpsView r3 = (com.android.systemui.biometrics.UdfpsView) r3
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.isIlluminationRequested
            int r5 = r19.getActionMasked()
            r6 = 0
            r7 = 0
            r8 = 1
            java.lang.String r9 = "UdfpsController"
            if (r5 == 0) goto L_0x01ce
            r10 = -1
            if (r5 == r8) goto L_0x01a8
            r11 = 4
            r12 = 2
            r13 = 3
            if (r5 == r12) goto L_0x003c
            if (r5 == r13) goto L_0x01a8
            if (r5 == r11) goto L_0x0033
            r14 = 7
            if (r5 == r14) goto L_0x003c
            r4 = 9
            if (r5 == r4) goto L_0x01ce
            r1 = 10
            if (r5 == r1) goto L_0x01a8
            goto L_0x0247
        L_0x0033:
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r0 = r3.animationViewController
            if (r0 != 0) goto L_0x0038
            goto L_0x003b
        L_0x0038:
            r0.onTouchOutsideView()
        L_0x003b:
            return r8
        L_0x003c:
            java.lang.String r5 = "UdfpsController.onTouch.ACTION_MOVE"
            android.os.Trace.beginSection(r5)
            int r5 = r0.mActivePointerId
            if (r5 != r10) goto L_0x004a
            int r5 = r1.getPointerId(r7)
            goto L_0x004e
        L_0x004a:
            int r5 = r1.findPointerIndex(r5)
        L_0x004e:
            int r10 = r19.getActionIndex()
            if (r5 != r10) goto L_0x01a3
            float r10 = r1.getX(r5)
            float r14 = r1.getY(r5)
            boolean r10 = r0.isWithinSensorArea(r3, r10, r14, r2)
            if (r2 != 0) goto L_0x0064
            if (r10 == 0) goto L_0x0099
        L_0x0064:
            com.android.systemui.biometrics.UdfpsControllerOverlay r2 = r0.mOverlay
            if (r2 == 0) goto L_0x0081
            com.android.systemui.biometrics.UdfpsView r2 = r2.overlayView
            if (r2 != 0) goto L_0x006d
            goto L_0x006f
        L_0x006d:
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r6 = r2.animationViewController
        L_0x006f:
            boolean r2 = r6 instanceof com.android.systemui.biometrics.UdfpsKeyguardViewController
            if (r2 == 0) goto L_0x0081
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r0.mKeyguardStateController
            boolean r2 = r2.canDismissLockScreen()
            if (r2 == 0) goto L_0x0081
            boolean r2 = r0.mAttemptedToDismissKeyguard
            if (r2 != 0) goto L_0x0081
            r2 = r8
            goto L_0x0082
        L_0x0081:
            r2 = r7
        L_0x0082:
            if (r2 == 0) goto L_0x0099
            java.lang.String r1 = "onTouch | dismiss keyguard ACTION_MOVE"
            android.util.Log.v(r9, r1)
            boolean r1 = r0.mOnFingerDown
            if (r1 != 0) goto L_0x0090
            r17.playStartHaptic()
        L_0x0090:
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r1 = r0.mKeyguardViewManager
            r1.notifyKeyguardAuthenticated()
            r0.mAttemptedToDismissKeyguard = r8
            goto L_0x0247
        L_0x0099:
            if (r10 == 0) goto L_0x019b
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            if (r2 != 0) goto L_0x00a5
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r2
        L_0x00a5:
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            r2.addMovement(r1)
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            r3 = 1000(0x3e8, float:1.401E-42)
            r2.computeCurrentVelocity(r3)
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            int r3 = r0.mActivePointerId
            float r6 = r2.getXVelocity(r3)
            float r2 = r2.getYVelocity(r3)
            double r14 = (double) r6
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r14 = java.lang.Math.pow(r14, r12)
            double r2 = (double) r2
            double r2 = java.lang.Math.pow(r2, r12)
            double r2 = r2 + r14
            double r2 = java.lang.Math.sqrt(r2)
            float r2 = (float) r2
            float r3 = r1.getTouchMinor(r5)
            float r5 = r1.getTouchMajor(r5)
            r6 = 1144750080(0x443b8000, float:750.0)
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x00e0
            r6 = r8
            goto L_0x00e1
        L_0x00e0:
            r6 = r7
        L_0x00e1:
            java.lang.Object[] r10 = new java.lang.Object[r11]
            java.lang.Float r11 = java.lang.Float.valueOf(r3)
            r10[r7] = r11
            java.lang.Float r11 = java.lang.Float.valueOf(r5)
            r10[r8] = r11
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r11 = 2
            r10[r11] = r2
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r6)
            r11 = 3
            r10[r11] = r2
            java.lang.String r2 = "minor: %.1f, major: %.1f, v: %.1f, exceedsVelocityThreshold: %b"
            java.lang.String r2 = java.lang.String.format(r2, r10)
            com.android.systemui.util.time.SystemClock r10 = r0.mSystemClock
            long r10 = r10.elapsedRealtime()
            long r12 = r0.mTouchLogTime
            long r10 = r10 - r12
            if (r4 != 0) goto L_0x0178
            boolean r4 = r0.mGoodCaptureReceived
            if (r4 != 0) goto L_0x0178
            if (r6 != 0) goto L_0x0178
            float r4 = r19.getRawX()
            int r4 = (int) r4
            float r1 = r19.getRawY()
            int r1 = (int) r1
            android.graphics.Point r6 = new android.graphics.Point
            r6.<init>()
            android.content.Context r10 = r0.mContext
            android.view.Display r10 = r10.getDisplay()
            r10.getRealSize(r6)
            android.content.Context r10 = r0.mContext
            android.view.Display r10 = r10.getDisplay()
            int r10 = r10.getRotation()
            if (r10 == r8) goto L_0x0146
            r11 = 3
            if (r10 == r11) goto L_0x013c
            goto L_0x014b
        L_0x013c:
            int r6 = r6.x
            int r4 = r6 - r4
            r16 = r4
            r4 = r1
            r1 = r16
            goto L_0x014b
        L_0x0146:
            int r6 = r6.y
            int r6 = r6 - r1
            r1 = r4
            r4 = r6
        L_0x014b:
            r0.onFingerDown(r4, r1, r3, r5)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "onTouch | finger down: "
            r1.append(r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r9, r1)
            com.android.systemui.util.time.SystemClock r1 = r0.mSystemClock
            long r1 = r1.elapsedRealtime()
            r0.mTouchLogTime = r1
            android.os.PowerManager r1 = r0.mPowerManager
            com.android.systemui.util.time.SystemClock r0 = r0.mSystemClock
            long r2 = r0.uptimeMillis()
            r0 = 2
            r1.userActivity(r2, r0, r7)
            r7 = r8
            goto L_0x01a3
        L_0x0178:
            r3 = 50
            int r1 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r1 < 0) goto L_0x01a3
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "onTouch | finger move: "
            r1.append(r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r9, r1)
            com.android.systemui.util.time.SystemClock r1 = r0.mSystemClock
            long r1 = r1.elapsedRealtime()
            r0.mTouchLogTime = r1
            goto L_0x01a3
        L_0x019b:
            java.lang.String r1 = "onTouch | finger outside"
            android.util.Log.v(r9, r1)
            r0.onFingerUp(r3)
        L_0x01a3:
            android.os.Trace.endSection()
            goto L_0x0247
        L_0x01a8:
            java.lang.String r1 = "UdfpsController.onTouch.ACTION_UP"
            android.os.Trace.beginSection(r1)
            r0.mActivePointerId = r10
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            if (r1 == 0) goto L_0x01b8
            r1.recycle()
            r0.mVelocityTracker = r6
        L_0x01b8:
            java.lang.String r1 = "onTouch | finger up"
            android.util.Log.v(r9, r1)
            r0.mAttemptedToDismissKeyguard = r7
            r0.onFingerUp(r3)
            com.android.systemui.plugins.FalsingManager r0 = r0.mFalsingManager
            r1 = 13
            r0.isFalseTouch(r1)
            android.os.Trace.endSection()
            goto L_0x0247
        L_0x01ce:
            java.lang.String r4 = "UdfpsController.onTouch.ACTION_DOWN"
            android.os.Trace.beginSection(r4)
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            if (r4 != 0) goto L_0x01de
            android.view.VelocityTracker r4 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r4
            goto L_0x01e1
        L_0x01de:
            r4.clear()
        L_0x01e1:
            float r4 = r19.getX()
            float r5 = r19.getY()
            boolean r3 = r0.isWithinSensorArea(r3, r4, r5, r2)
            if (r3 == 0) goto L_0x020d
            com.android.internal.util.LatencyTracker r4 = r0.mLatencyTracker
            r5 = 14
            r4.onActionStart(r5)
            java.lang.String r4 = "UdfpsController.e2e.onPointerDown"
            android.os.Trace.beginAsyncSection(r4, r7)
            java.lang.String r4 = "onTouch | action down"
            android.util.Log.v(r9, r4)
            int r4 = r1.getPointerId(r7)
            r0.mActivePointerId = r4
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            r4.addMovement(r1)
            r1 = r8
            goto L_0x020e
        L_0x020d:
            r1 = r7
        L_0x020e:
            if (r3 != 0) goto L_0x0212
            if (r2 == 0) goto L_0x0243
        L_0x0212:
            com.android.systemui.biometrics.UdfpsControllerOverlay r2 = r0.mOverlay
            if (r2 == 0) goto L_0x022e
            com.android.systemui.biometrics.UdfpsView r2 = r2.overlayView
            if (r2 != 0) goto L_0x021b
            goto L_0x021d
        L_0x021b:
            com.android.systemui.biometrics.UdfpsAnimationViewController<?> r6 = r2.animationViewController
        L_0x021d:
            boolean r2 = r6 instanceof com.android.systemui.biometrics.UdfpsKeyguardViewController
            if (r2 == 0) goto L_0x022e
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r0.mKeyguardStateController
            boolean r2 = r2.canDismissLockScreen()
            if (r2 == 0) goto L_0x022e
            boolean r2 = r0.mAttemptedToDismissKeyguard
            if (r2 != 0) goto L_0x022e
            r7 = r8
        L_0x022e:
            if (r7 == 0) goto L_0x0243
            java.lang.String r2 = "onTouch | dismiss keyguard ACTION_DOWN"
            android.util.Log.v(r9, r2)
            boolean r2 = r0.mOnFingerDown
            if (r2 != 0) goto L_0x023c
            r17.playStartHaptic()
        L_0x023c:
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r2 = r0.mKeyguardViewManager
            r2.notifyKeyguardAuthenticated()
            r0.mAttemptedToDismissKeyguard = r8
        L_0x0243:
            android.os.Trace.endSection()
            r7 = r1
        L_0x0247:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsController.onTouch(android.view.View, android.view.MotionEvent, boolean):boolean");
    }

    @VisibleForTesting
    public void playStartHaptic() {
        this.mVibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), EFFECT_CLICK, "udfps-onStart-click", VIBRATION_ATTRIBUTES);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043 A[Catch:{ RuntimeException -> 0x0075 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0066 A[Catch:{ RuntimeException -> 0x0075 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0067 A[Catch:{ RuntimeException -> 0x0075 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void showUdfpsOverlay(com.android.systemui.biometrics.UdfpsControllerOverlay r10) {
        /*
            r9 = this;
            com.android.systemui.util.concurrency.Execution r0 = r9.mExecution
            r0.assertIsMainThread()
            r9.mOverlay = r10
            com.android.systemui.biometrics.UdfpsView r0 = r10.overlayView
            java.lang.String r1 = "UdfpsControllerOverlay"
            java.lang.String r2 = "showUdfpsOverlay | the overlay is already showing"
            r3 = 0
            if (r0 != 0) goto L_0x007d
            r0 = 1
            android.view.LayoutInflater r4 = r10.inflater     // Catch:{ RuntimeException -> 0x0075 }
            r5 = 2131624641(0x7f0e02c1, float:1.8876467E38)
            r6 = 0
            android.view.View r4 = r4.inflate(r5, r6, r3)     // Catch:{ RuntimeException -> 0x0075 }
            if (r4 == 0) goto L_0x006d
            com.android.systemui.biometrics.UdfpsView r4 = (com.android.systemui.biometrics.UdfpsView) r4     // Catch:{ RuntimeException -> 0x0075 }
            android.hardware.fingerprint.FingerprintSensorPropertiesInternal r5 = r10.sensorProps     // Catch:{ RuntimeException -> 0x0075 }
            r4.sensorProperties = r5     // Catch:{ RuntimeException -> 0x0075 }
            com.android.systemui.biometrics.UdfpsHbmProvider r5 = r10.hbmProvider     // Catch:{ RuntimeException -> 0x0075 }
            r4.hbmProvider = r5     // Catch:{ RuntimeException -> 0x0075 }
            com.android.systemui.biometrics.UdfpsAnimationViewController r5 = r10.inflateUdfpsAnimation(r4, r9)     // Catch:{ RuntimeException -> 0x0075 }
            if (r5 == 0) goto L_0x0033
            r5.init()     // Catch:{ RuntimeException -> 0x0075 }
            r4.animationViewController = r5     // Catch:{ RuntimeException -> 0x0075 }
        L_0x0033:
            int r6 = r10.requestReason     // Catch:{ RuntimeException -> 0x0075 }
            r7 = 2
            if (r6 == r0) goto L_0x0040
            if (r6 == r7) goto L_0x0040
            r8 = 3
            if (r6 != r8) goto L_0x003e
            goto L_0x0040
        L_0x003e:
            r6 = r3
            goto L_0x0041
        L_0x0040:
            r6 = r0
        L_0x0041:
            if (r6 == 0) goto L_0x0046
            r4.setImportantForAccessibility(r7)     // Catch:{ RuntimeException -> 0x0075 }
        L_0x0046:
            android.view.WindowManager r6 = r10.windowManager     // Catch:{ RuntimeException -> 0x0075 }
            android.view.WindowManager$LayoutParams r7 = r10.coreLayoutParams     // Catch:{ RuntimeException -> 0x0075 }
            android.hardware.fingerprint.FingerprintSensorPropertiesInternal r8 = r10.sensorProps     // Catch:{ RuntimeException -> 0x0075 }
            android.hardware.biometrics.SensorLocationInternal r8 = r8.getLocation()     // Catch:{ RuntimeException -> 0x0075 }
            r10.updateForLocation(r7, r8, r5)     // Catch:{ RuntimeException -> 0x0075 }
            r6.addView(r4, r7)     // Catch:{ RuntimeException -> 0x0075 }
            com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1 r5 = new com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1     // Catch:{ RuntimeException -> 0x0075 }
            r5.<init>(r10, r4)     // Catch:{ RuntimeException -> 0x0075 }
            r10.overlayTouchListener = r5     // Catch:{ RuntimeException -> 0x0075 }
            android.view.accessibility.AccessibilityManager r6 = r10.accessibilityManager     // Catch:{ RuntimeException -> 0x0075 }
            r6.addTouchExplorationStateChangeListener(r5)     // Catch:{ RuntimeException -> 0x0075 }
            com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1 r5 = r10.overlayTouchListener     // Catch:{ RuntimeException -> 0x0075 }
            if (r5 != 0) goto L_0x0067
            goto L_0x006a
        L_0x0067:
            r5.onTouchExplorationStateChanged(r0)     // Catch:{ RuntimeException -> 0x0075 }
        L_0x006a:
            r10.overlayView = r4     // Catch:{ RuntimeException -> 0x0075 }
            goto L_0x0081
        L_0x006d:
            java.lang.NullPointerException r4 = new java.lang.NullPointerException     // Catch:{ RuntimeException -> 0x0075 }
            java.lang.String r5 = "null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsView"
            r4.<init>(r5)     // Catch:{ RuntimeException -> 0x0075 }
            throw r4     // Catch:{ RuntimeException -> 0x0075 }
        L_0x0075:
            r4 = move-exception
            java.lang.String r5 = "showUdfpsOverlay | failed to add window"
            android.util.Log.e(r1, r5, r4)
            goto L_0x0081
        L_0x007d:
            android.util.Log.v(r1, r2)
            r0 = r3
        L_0x0081:
            java.lang.String r1 = "UdfpsController"
            if (r0 == 0) goto L_0x00a2
            java.lang.String r0 = "showUdfpsOverlay | adding window reason="
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            int r10 = r10.requestReason
            r0.append(r10)
            java.lang.String r10 = r0.toString()
            android.util.Log.v(r1, r10)
            r9.mOnFingerDown = r3
            r9.mAttemptedToDismissKeyguard = r3
            com.android.systemui.biometrics.BiometricDisplayListener r9 = r9.mOrientationListener
            r9.enable()
            goto L_0x00a5
        L_0x00a2:
            android.util.Log.v(r1, r2)
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsController.showUdfpsOverlay(com.android.systemui.biometrics.UdfpsControllerOverlay):void");
    }
}
