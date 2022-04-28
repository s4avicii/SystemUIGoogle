package com.android.systemui.statusbar.phone;

import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.service.dreams.IDreamManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda10;
import com.android.p012wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class BiometricUnlockController extends KeyguardUpdateMonitorCallback implements Dumpable {
    public static final UiEventLoggerImpl UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public BiometricModeListener mBiometricModeListener;
    public BiometricSourceType mBiometricType;
    public final int mConsecutiveFpFailureThreshold;
    public final DozeParameters mDozeParameters;
    public DozeScrimController mDozeScrimController;
    public boolean mFadedAwayAfterWakeAndUnlock;
    public final Handler mHandler;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStateController mKeyguardStateController;
    public KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public KeyguardViewController mKeyguardViewController;
    public KeyguardViewMediator mKeyguardViewMediator;
    public long mLastFpFailureUptimeMillis;
    public final LatencyTracker mLatencyTracker;
    public final NotificationMediaManager mMediaManager;
    public final MetricsLogger mMetricsLogger;
    public int mMode;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public int mNumConsecutiveFpFailures;
    public PendingAuthenticated mPendingAuthenticated = null;
    public boolean mPendingShowBouncer;
    public final PowerManager mPowerManager;
    public final C14061 mReleaseBiometricWakeLockRunnable = new Runnable() {
        public final void run() {
            Log.i("BiometricUnlockCtrl", "biometric wakelock: TIMEOUT!!");
            BiometricUnlockController.this.releaseBiometricWakeLock();
        }
    };
    public final C14094 mScreenObserver;
    public ScrimController mScrimController;
    public final SessionTracker mSessionTracker;
    public final ShadeController mShadeController;
    public final StatusBarStateController mStatusBarStateController;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public PowerManager.WakeLock mWakeLock;
    public final int mWakeUpDelay;
    @VisibleForTesting
    public final WakefulnessLifecycle.Observer mWakefulnessObserver;

    public interface BiometricModeListener {
    }

    @VisibleForTesting
    public enum BiometricUiEvent implements UiEventLogger.UiEventEnum {
        BIOMETRIC_BOUNCER_SHOWN(916);
        
        public static final Map<BiometricSourceType, BiometricUiEvent> ERROR_EVENT_BY_SOURCE_TYPE = null;
        public static final Map<BiometricSourceType, BiometricUiEvent> FAILURE_EVENT_BY_SOURCE_TYPE = null;
        public static final Map<BiometricSourceType, BiometricUiEvent> SUCCESS_EVENT_BY_SOURCE_TYPE = null;
        private final int mId;

        /* access modifiers changed from: public */
        static {
            BiometricUiEvent biometricUiEvent;
            BiometricUiEvent biometricUiEvent2;
            BiometricUiEvent biometricUiEvent3;
            BiometricUiEvent biometricUiEvent4;
            BiometricUiEvent biometricUiEvent5;
            BiometricUiEvent biometricUiEvent6;
            BiometricUiEvent biometricUiEvent7;
            BiometricUiEvent biometricUiEvent8;
            BiometricUiEvent biometricUiEvent9;
            ERROR_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, biometricUiEvent3, BiometricSourceType.FACE, biometricUiEvent6, BiometricSourceType.IRIS, biometricUiEvent9);
            SUCCESS_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, biometricUiEvent, BiometricSourceType.FACE, biometricUiEvent4, BiometricSourceType.IRIS, biometricUiEvent7);
            FAILURE_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, biometricUiEvent2, BiometricSourceType.FACE, biometricUiEvent5, BiometricSourceType.IRIS, biometricUiEvent8);
        }

        /* access modifiers changed from: public */
        BiometricUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public BiometricUnlockController(DozeScrimController dozeScrimController, KeyguardViewMediator keyguardViewMediator, ScrimController scrimController, ShadeController shadeController, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, Resources resources, KeyguardBypassController keyguardBypassController, DozeParameters dozeParameters, MetricsLogger metricsLogger, DumpManager dumpManager, PowerManager powerManager, NotificationMediaManager notificationMediaManager, WakefulnessLifecycle wakefulnessLifecycle, ScreenLifecycle screenLifecycle, AuthController authController, StatusBarStateController statusBarStateController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, SessionTracker sessionTracker, LatencyTracker latencyTracker) {
        Resources resources2 = resources;
        KeyguardBypassController keyguardBypassController2 = keyguardBypassController;
        C14083 r4 = new WakefulnessLifecycle.Observer() {
            public final void onFinishedWakingUp() {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                if (biometricUnlockController.mPendingShowBouncer) {
                    if (biometricUnlockController.mMode == 3) {
                        biometricUnlockController.mKeyguardViewController.showBouncer(true);
                    }
                    biometricUnlockController.mShadeController.animateCollapsePanels(0, true, false, 1.1f);
                    biometricUnlockController.mPendingShowBouncer = false;
                }
            }
        };
        this.mWakefulnessObserver = r4;
        C14094 r5 = new ScreenLifecycle.Observer() {
            public final void onScreenTurnedOn() {
                Objects.requireNonNull(BiometricUnlockController.this);
            }
        };
        this.mScreenObserver = r5;
        this.mPowerManager = powerManager;
        this.mShadeController = shadeController;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mDozeParameters = dozeParameters;
        keyguardUpdateMonitor.registerCallback(this);
        this.mMediaManager = notificationMediaManager;
        this.mLatencyTracker = latencyTracker;
        Objects.requireNonNull(wakefulnessLifecycle);
        wakefulnessLifecycle.mObservers.add(r4);
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle.mObservers.add(r5);
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDozeScrimController = dozeScrimController;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mScrimController = scrimController;
        this.mKeyguardStateController = keyguardStateController;
        this.mHandler = handler;
        this.mWakeUpDelay = resources2.getInteger(17694961);
        this.mConsecutiveFpFailureThreshold = resources2.getInteger(C1777R.integer.fp_consecutive_failure_time_ms);
        this.mKeyguardBypassController = keyguardBypassController2;
        Objects.requireNonNull(keyguardBypassController);
        keyguardBypassController2.unlockController = this;
        this.mMetricsLogger = metricsLogger;
        this.mAuthController = authController;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.mSessionTracker = sessionTracker;
        dumpManager.registerDumpable(BiometricUnlockController.class.getName(), this);
    }

    public final void resetMode() {
        this.mMode = 0;
        this.mBiometricType = null;
        this.mNotificationShadeWindowController.setForceDozeBrightness(false);
        BiometricModeListener biometricModeListener = this.mBiometricModeListener;
        if (biometricModeListener != null) {
            StatusBar.C15514 r1 = (StatusBar.C15514) biometricModeListener;
            Objects.requireNonNull(r1);
            r1.setWakeAndUnlocking(false);
            ((StatusBar.C15514) this.mBiometricModeListener).notifyBiometricAuthModeChanged();
        }
        this.mNumConsecutiveFpFailures = 0;
        this.mLastFpFailureUptimeMillis = 0;
    }

    public final void startWakeAndUnlock(int i) {
        IDreamManager iDreamManager;
        Log.v("BiometricUnlockCtrl", "startWakeAndUnlock(" + i + ")");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        boolean z = keyguardUpdateMonitor.mDeviceInteractive;
        this.mMode = i;
        if (i == 2) {
            ScrimController scrimController = this.mScrimController;
            Objects.requireNonNull(scrimController);
            ScrimState scrimState = scrimController.mState;
            if (scrimState == ScrimState.AOD || scrimState == ScrimState.PULSING) {
                this.mNotificationShadeWindowController.setForceDozeBrightness(true);
            }
        }
        boolean z2 = i == 1 && this.mDozeParameters.getAlwaysOn() && this.mWakeUpDelay > 0;
        BiometricUnlockController$$ExternalSyntheticLambda1 biometricUnlockController$$ExternalSyntheticLambda1 = new BiometricUnlockController$$ExternalSyntheticLambda1(this, z, z2);
        if (!z2 && this.mMode != 0) {
            biometricUnlockController$$ExternalSyntheticLambda1.run();
        }
        int i2 = this.mMode;
        switch (i2) {
            case 1:
            case 2:
            case FalsingManager.VERSION:
                if (i2 == 2) {
                    Trace.beginSection("MODE_WAKE_AND_UNLOCK_PULSING");
                    this.mMediaManager.updateMediaMetaData(false, true);
                } else if (i2 == 1) {
                    Trace.beginSection("MODE_WAKE_AND_UNLOCK");
                } else {
                    Trace.beginSection("MODE_WAKE_AND_UNLOCK_FROM_DREAM");
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.mUpdateMonitor;
                    Objects.requireNonNull(keyguardUpdateMonitor2);
                    if (keyguardUpdateMonitor2.mIsDreaming && (iDreamManager = keyguardUpdateMonitor2.mDreamManager) != null) {
                        try {
                            iDreamManager.awaken();
                        } catch (RemoteException unused) {
                            Log.e("KeyguardUpdateMonitor", "Unable to awaken from dream");
                        }
                    }
                }
                this.mNotificationShadeWindowController.setNotificationShadeFocusable(false);
                if (z2) {
                    this.mHandler.postDelayed(biometricUnlockController$$ExternalSyntheticLambda1, (long) this.mWakeUpDelay);
                } else {
                    this.mKeyguardViewMediator.onWakeAndUnlocking();
                }
                Trace.endSection();
                break;
            case 3:
                Trace.beginSection("MODE_SHOW_BOUNCER");
                if (!z) {
                    this.mPendingShowBouncer = true;
                } else {
                    if (this.mMode == 3) {
                        this.mKeyguardViewController.showBouncer(true);
                    }
                    this.mShadeController.animateCollapsePanels(0, true, false, 1.1f);
                    this.mPendingShowBouncer = false;
                }
                Trace.endSection();
                break;
            case 5:
                Trace.beginSection("MODE_UNLOCK_COLLAPSING");
                if (!z) {
                    this.mPendingShowBouncer = true;
                } else {
                    Objects.requireNonNull(this.mKeyguardUnlockAnimationController);
                    if (!KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation) {
                        this.mShadeController.animateCollapsePanels(0, true, false, 1.1f);
                    }
                    this.mPendingShowBouncer = false;
                    this.mKeyguardViewController.notifyKeyguardAuthenticated();
                }
                Trace.endSection();
                break;
            case 7:
            case 8:
                Trace.beginSection("MODE_DISMISS_BOUNCER or MODE_UNLOCK_FADING");
                this.mKeyguardViewController.notifyKeyguardAuthenticated();
                Trace.endSection();
                break;
        }
        int i3 = this.mMode;
        BiometricModeListener biometricModeListener = this.mBiometricModeListener;
        if (biometricModeListener != null) {
            StatusBar.C15514 r0 = (StatusBar.C15514) biometricModeListener;
            if (i3 == 1 || i3 == 2 || i3 == 6) {
                r0.setWakeAndUnlocking(true);
            }
        }
        BiometricModeListener biometricModeListener2 = this.mBiometricModeListener;
        if (biometricModeListener2 != null) {
            ((StatusBar.C15514) biometricModeListener2).notifyBiometricAuthModeChanged();
        }
        Trace.endSection();
    }

    /* renamed from: com.android.systemui.statusbar.phone.BiometricUnlockController$5 */
    public static /* synthetic */ class C14105 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                android.hardware.biometrics.BiometricSourceType[] r0 = android.hardware.biometrics.BiometricSourceType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$android$hardware$biometrics$BiometricSourceType = r0
                android.hardware.biometrics.BiometricSourceType r1 = android.hardware.biometrics.BiometricSourceType.FINGERPRINT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$android$hardware$biometrics$BiometricSourceType     // Catch:{ NoSuchFieldError -> 0x001d }
                android.hardware.biometrics.BiometricSourceType r1 = android.hardware.biometrics.BiometricSourceType.FACE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$android$hardware$biometrics$BiometricSourceType     // Catch:{ NoSuchFieldError -> 0x0028 }
                android.hardware.biometrics.BiometricSourceType r1 = android.hardware.biometrics.BiometricSourceType.IRIS     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.BiometricUnlockController.C14105.<clinit>():void");
        }
    }

    public static final class PendingAuthenticated {
        public final BiometricSourceType biometricSourceType;
        public final boolean isStrongBiometric;
        public final int userId;

        public PendingAuthenticated(int i, BiometricSourceType biometricSourceType2, boolean z) {
            this.userId = i;
            this.biometricSourceType = biometricSourceType2;
            this.isStrongBiometric = z;
        }
    }

    public static int toSubtype(BiometricSourceType biometricSourceType) {
        int i = C14105.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            return 3;
        }
        return 2;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" BiometricUnlockController:");
        printWriter.print("   mMode=");
        printWriter.println(this.mMode);
        printWriter.print("   mWakeLock=");
        printWriter.println(this.mWakeLock);
        if (this.mUpdateMonitor.isUdfpsSupported()) {
            printWriter.print("   mNumConsecutiveFpFailures=");
            printWriter.println(this.mNumConsecutiveFpFailures);
            printWriter.print("   time since last failure=");
            printWriter.println(SystemClock.uptimeMillis() - this.mLastFpFailureUptimeMillis);
        }
    }

    public final boolean isWakeAndUnlock() {
        int i = this.mMode;
        if (i == 1 || i == 2 || i == 6) {
            return true;
        }
        return false;
    }

    public final void onBiometricAcquired(BiometricSourceType biometricSourceType) {
        Trace.beginSection("BiometricUnlockController#onBiometricAcquired");
        releaseBiometricWakeLock();
        if (isWakeAndUnlock()) {
            if (this.mLatencyTracker.isEnabled()) {
                int i = 2;
                if (biometricSourceType == BiometricSourceType.FACE) {
                    i = 7;
                }
                this.mLatencyTracker.onActionStart(i);
            }
            this.mWakeLock = this.mPowerManager.newWakeLock(1, "wake-and-unlock:wakelock");
            Trace.beginSection("acquiring wake-and-unlock");
            this.mWakeLock.acquire();
            Trace.endSection();
            Log.i("BiometricUnlockCtrl", "biometric acquired, grabbing biometric wakelock");
            this.mHandler.postDelayed(this.mReleaseBiometricWakeLockRunnable, WifiTracker.MAX_SCAN_RESULT_AGE_MILLIS);
        }
        Trace.endSection();
    }

    public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
        int i;
        this.mMetricsLogger.write(new LogMaker(1697).setType(11).setSubtype(toSubtype(biometricSourceType)));
        Optional.ofNullable(BiometricUiEvent.FAILURE_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new ShellTaskOrganizer$$ExternalSyntheticLambda1(this, 1));
        if (this.mLatencyTracker.isEnabled()) {
            if (biometricSourceType == BiometricSourceType.FACE) {
                i = 7;
            } else {
                i = 2;
            }
            this.mLatencyTracker.onActionCancel(i);
        }
        if (biometricSourceType == BiometricSourceType.FINGERPRINT && this.mUpdateMonitor.isUdfpsSupported()) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.mLastFpFailureUptimeMillis < ((long) this.mConsecutiveFpFailureThreshold)) {
                this.mNumConsecutiveFpFailures++;
            } else {
                this.mNumConsecutiveFpFailures = 1;
            }
            this.mLastFpFailureUptimeMillis = uptimeMillis;
            if (this.mNumConsecutiveFpFailures >= 2) {
                startWakeAndUnlock(3);
                UiEventLoggerImpl uiEventLoggerImpl = UI_EVENT_LOGGER;
                BiometricUiEvent biometricUiEvent = BiometricUiEvent.BIOMETRIC_BOUNCER_SHOWN;
                SessionTracker sessionTracker = this.mSessionTracker;
                Objects.requireNonNull(sessionTracker);
                uiEventLoggerImpl.log(biometricUiEvent, (InstanceId) sessionTracker.mSessionToInstanceId.getOrDefault(1, (Object) null));
                this.mNumConsecutiveFpFailures = 0;
            }
        }
        releaseBiometricWakeLock();
    }

    public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
        boolean z2;
        Trace.beginSection("BiometricUnlockController#onBiometricAuthenticated");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        if (keyguardUpdateMonitor.mGoingToSleep) {
            this.mPendingAuthenticated = new PendingAuthenticated(i, biometricSourceType, z);
            Trace.endSection();
            return;
        }
        this.mBiometricType = biometricSourceType;
        this.mMetricsLogger.write(new LogMaker(1697).setType(10).setSubtype(toSubtype(biometricSourceType)));
        Optional.ofNullable(BiometricUiEvent.SUCCESS_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new PipMotionHelper$$ExternalSyntheticLambda1(this, 3));
        if (this.mKeyguardStateController.isOccluded() || this.mKeyguardBypassController.onBiometricAuthenticated(biometricSourceType, z)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.mKeyguardViewMediator.userActivity();
            startWakeAndUnlock(biometricSourceType, z);
            return;
        }
        Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated aborted by bypass controller");
    }

    public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
        this.mMetricsLogger.write(new LogMaker(1697).setType(15).setSubtype(toSubtype(biometricSourceType)).addTaggedData(1741, Integer.valueOf(i)));
        Optional.ofNullable(BiometricUiEvent.ERROR_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new BubbleController$$ExternalSyntheticLambda10(this, 1));
        if (biometricSourceType == BiometricSourceType.FINGERPRINT && ((i == 7 || i == 9) && this.mUpdateMonitor.isUdfpsSupported() && (this.mStatusBarStateController.getState() == 0 || this.mStatusBarStateController.getState() == 2))) {
            startWakeAndUnlock(3);
            UiEventLoggerImpl uiEventLoggerImpl = UI_EVENT_LOGGER;
            BiometricUiEvent biometricUiEvent = BiometricUiEvent.BIOMETRIC_BOUNCER_SHOWN;
            SessionTracker sessionTracker = this.mSessionTracker;
            Objects.requireNonNull(sessionTracker);
            uiEventLoggerImpl.log(biometricUiEvent, (InstanceId) sessionTracker.mSessionToInstanceId.getOrDefault(1, (Object) null));
        }
        releaseBiometricWakeLock();
    }

    public final void onFinishedGoingToSleep(int i) {
        Trace.beginSection("BiometricUnlockController#onFinishedGoingToSleep");
        PendingAuthenticated pendingAuthenticated = this.mPendingAuthenticated;
        if (pendingAuthenticated != null) {
            this.mHandler.post(new BiometricUnlockController$$ExternalSyntheticLambda0(this, pendingAuthenticated, 0));
            this.mPendingAuthenticated = null;
        }
        Trace.endSection();
    }

    public final void releaseBiometricWakeLock() {
        if (this.mWakeLock != null) {
            this.mHandler.removeCallbacks(this.mReleaseBiometricWakeLockRunnable);
            Log.i("BiometricUnlockCtrl", "releasing biometric wakelock");
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
    }

    public final void onStartedGoingToSleep$1() {
        resetMode();
        this.mFadedAwayAfterWakeAndUnlock = false;
        this.mPendingAuthenticated = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r4.mKeyguardStateController.isMethodSecure() != false) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006b, code lost:
        if (r4.mKeyguardViewController.isBouncerShowing() == false) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a2, code lost:
        if (r0 != false) goto L_0x0131;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a8, code lost:
        if (r0 != false) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c5, code lost:
        if (r0 != false) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f2, code lost:
        if (r6.altBouncerShowing != false) goto L_0x00f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0111, code lost:
        if (r1 != 0) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0128, code lost:
        if (r5 != false) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x012c, code lost:
        if (r0 != false) goto L_0x012e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void startWakeAndUnlock(android.hardware.biometrics.BiometricSourceType r5, boolean r6) {
        /*
            r4 = this;
            android.hardware.biometrics.BiometricSourceType r0 = android.hardware.biometrics.BiometricSourceType.FACE
            r1 = 1
            r2 = 0
            if (r5 == r0) goto L_0x006f
            android.hardware.biometrics.BiometricSourceType r0 = android.hardware.biometrics.BiometricSourceType.IRIS
            if (r5 != r0) goto L_0x000b
            goto L_0x006f
        L_0x000b:
            com.android.keyguard.KeyguardUpdateMonitor r5 = r4.mUpdateMonitor
            boolean r5 = r5.isUnlockingWithBiometricAllowed(r6)
            com.android.keyguard.KeyguardUpdateMonitor r6 = r4.mUpdateMonitor
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mIsDreaming
            com.android.keyguard.KeyguardUpdateMonitor r0 = r4.mUpdateMonitor
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mDeviceInteractive
            if (r0 != 0) goto L_0x0047
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.isShowing()
            if (r6 != 0) goto L_0x002b
            goto L_0x00cb
        L_0x002b:
            com.android.systemui.statusbar.phone.DozeScrimController r6 = r4.mDozeScrimController
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.doze.DozeHost$PulseCallback r6 = r6.mPulseCallback
            if (r6 == 0) goto L_0x0035
            r2 = r1
        L_0x0035:
            if (r2 == 0) goto L_0x003b
            if (r5 == 0) goto L_0x003b
            goto L_0x00be
        L_0x003b:
            if (r5 != 0) goto L_0x0131
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = r4.mKeyguardStateController
            boolean r5 = r5.isMethodSecure()
            if (r5 != 0) goto L_0x012e
            goto L_0x0131
        L_0x0047:
            if (r5 == 0) goto L_0x004d
            if (r6 == 0) goto L_0x004d
            goto L_0x00c7
        L_0x004d:
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.isShowing()
            if (r6 == 0) goto L_0x0130
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.bouncerIsOrWillBeShowing()
            if (r6 == 0) goto L_0x0061
            if (r5 == 0) goto L_0x0061
            goto L_0x0114
        L_0x0061:
            if (r5 == 0) goto L_0x0065
            goto L_0x00d8
        L_0x0065:
            com.android.keyguard.KeyguardViewController r5 = r4.mKeyguardViewController
            boolean r5 = r5.isBouncerShowing()
            if (r5 != 0) goto L_0x0130
            goto L_0x012e
        L_0x006f:
            com.android.keyguard.KeyguardUpdateMonitor r5 = r4.mUpdateMonitor
            boolean r5 = r5.isUnlockingWithBiometricAllowed(r6)
            com.android.keyguard.KeyguardUpdateMonitor r6 = r4.mUpdateMonitor
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mIsDreaming
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r4.mKeyguardBypassController
            boolean r0 = r0.getBypassEnabled()
            if (r0 != 0) goto L_0x0090
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r4.mKeyguardBypassController
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.userHasDeviceEntryIntent
            if (r0 == 0) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            r0 = r2
            goto L_0x0091
        L_0x0090:
            r0 = r1
        L_0x0091:
            com.android.keyguard.KeyguardUpdateMonitor r3 = r4.mUpdateMonitor
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mDeviceInteractive
            if (r3 != 0) goto L_0x00c1
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.isShowing()
            if (r6 != 0) goto L_0x00a6
            if (r0 == 0) goto L_0x00cb
            goto L_0x0131
        L_0x00a6:
            if (r5 != 0) goto L_0x00ac
            if (r0 == 0) goto L_0x0130
            goto L_0x012e
        L_0x00ac:
            com.android.systemui.statusbar.phone.DozeScrimController r5 = r4.mDozeScrimController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.doze.DozeHost$PulseCallback r5 = r5.mPulseCallback
            if (r5 == 0) goto L_0x00b6
            goto L_0x00b7
        L_0x00b6:
            r1 = r2
        L_0x00b7:
            if (r1 == 0) goto L_0x00bc
            if (r0 == 0) goto L_0x00cb
            goto L_0x00be
        L_0x00bc:
            if (r0 == 0) goto L_0x00cb
        L_0x00be:
            r1 = 2
            goto L_0x0131
        L_0x00c1:
            if (r5 == 0) goto L_0x00ce
            if (r6 == 0) goto L_0x00ce
            if (r0 == 0) goto L_0x00cb
        L_0x00c7:
            r5 = 6
            r1 = r5
            goto L_0x0131
        L_0x00cb:
            r1 = 4
            goto L_0x0131
        L_0x00ce:
            if (r5 == 0) goto L_0x00da
            com.android.systemui.statusbar.policy.KeyguardStateController r6 = r4.mKeyguardStateController
            boolean r6 = r6.isOccluded()
            if (r6 == 0) goto L_0x00da
        L_0x00d8:
            r1 = 5
            goto L_0x0131
        L_0x00da:
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.isShowing()
            if (r6 == 0) goto L_0x0130
            com.android.keyguard.KeyguardViewController r6 = r4.mKeyguardViewController
            boolean r6 = r6.bouncerIsOrWillBeShowing()
            r3 = 7
            if (r6 != 0) goto L_0x00f4
            com.android.systemui.statusbar.phone.KeyguardBypassController r6 = r4.mKeyguardBypassController
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.altBouncerShowing
            if (r6 == 0) goto L_0x0117
        L_0x00f4:
            if (r5 == 0) goto L_0x0117
            if (r0 == 0) goto L_0x0114
            com.android.systemui.statusbar.phone.KeyguardBypassController r5 = r4.mKeyguardBypassController
            java.util.Objects.requireNonNull(r5)
            boolean r6 = r5.getBypassEnabled()
            if (r6 == 0) goto L_0x0110
            com.android.systemui.plugins.statusbar.StatusBarStateController r6 = r5.statusBarStateController
            int r6 = r6.getState()
            if (r6 == r1) goto L_0x010c
            goto L_0x0110
        L_0x010c:
            boolean r5 = r5.qSExpanded
            if (r5 == 0) goto L_0x0111
        L_0x0110:
            r1 = r2
        L_0x0111:
            if (r1 == 0) goto L_0x0114
            goto L_0x012a
        L_0x0114:
            r1 = 8
            goto L_0x0131
        L_0x0117:
            if (r5 == 0) goto L_0x012c
            if (r0 != 0) goto L_0x012a
            com.android.systemui.biometrics.AuthController r5 = r4.mAuthController
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.biometrics.UdfpsController r5 = r5.mUdfpsController
            if (r5 != 0) goto L_0x0126
            r5 = r2
            goto L_0x0128
        L_0x0126:
            boolean r5 = r5.mOnFingerDown
        L_0x0128:
            if (r5 == 0) goto L_0x0130
        L_0x012a:
            r1 = r3
            goto L_0x0131
        L_0x012c:
            if (r0 == 0) goto L_0x0130
        L_0x012e:
            r1 = 3
            goto L_0x0131
        L_0x0130:
            r1 = r2
        L_0x0131:
            r4.startWakeAndUnlock(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.BiometricUnlockController.startWakeAndUnlock(android.hardware.biometrics.BiometricSourceType, boolean):void");
    }
}
