package com.android.keyguard;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.R$id;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.util.Assert;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda0;
import com.google.android.collect.Lists;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.collections.ArrayDeque;

public final class KeyguardUpdateMonitor implements TrustManager.TrustListener, Dumpable {
    public static final boolean CORE_APPS_ONLY;
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public static final boolean DEBUG_ACTIVE_UNLOCK;
    public static final boolean DEBUG_FACE;
    public static final boolean DEBUG_FINGERPRINT;
    public static final ComponentName FALLBACK_HOME_COMPONENT = new ComponentName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.FallbackHome");
    public static int sCurrentUser;
    public int mActiveMobileDataSubscription = -1;
    public boolean mAssistantVisible;
    public final AuthController mAuthController;
    public boolean mAuthInterruptActive;
    public final Executor mBackgroundExecutor;
    @VisibleForTesting
    public BatteryStatus mBatteryStatus;
    public C05462 mBiometricEnabledCallback;
    public SparseBooleanArray mBiometricEnabledForUser;
    public boolean mBouncer;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastAllReceiver;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastReceiver;
    public final ArrayList<WeakReference<KeyguardUpdateMonitorCallback>> mCallbacks = Lists.newArrayList();
    public final Context mContext;
    public boolean mCredentialAttempted;
    public boolean mDeviceInteractive;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mDeviceProvisioned;
    public C054418 mDeviceProvisionedObserver;
    public final IDreamManager mDreamManager;
    public int mFaceAuthUserId;
    @VisibleForTesting
    public final FaceManager.AuthenticationCallback mFaceAuthenticationCallback;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda8 mFaceCancelNotReceived;
    public CancellationSignal mFaceCancelSignal;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda4 mFaceDetectionCallback;
    public boolean mFaceLockedOutPermanent;
    public final C053711 mFaceLockoutResetCallback;
    public FaceManager mFaceManager;
    public int mFaceRunningState = 0;
    public List<FaceSensorPropertiesInternal> mFaceSensorProperties;
    @VisibleForTesting
    public final FingerprintManager.AuthenticationCallback mFingerprintAuthenticationCallback;
    public CancellationSignal mFingerprintCancelSignal;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda5 mFingerprintDetectionCallback;
    public boolean mFingerprintLockedOut;
    public boolean mFingerprintLockedOutPermanent;
    public final C053610 mFingerprintLockoutResetCallback;
    public int mFingerprintRunningState = 0;
    public final TaskView$$ExternalSyntheticLambda5 mFpCancelNotReceived;
    public FingerprintManager mFpm;
    public boolean mGoingToSleep;
    public final C054014 mHandler;
    public int mHardwareFaceUnavailableRetryCount = 0;
    public int mHardwareFingerprintUnavailableRetryCount = 0;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final boolean mIsAutomotive;
    public boolean mIsDreaming;
    public boolean mIsFaceEnrolled;
    public final boolean mIsPrimaryUser;
    public KeyguardBypassController mKeyguardBypassController;
    public boolean mKeyguardGoingAway;
    public boolean mKeyguardIsVisible;
    public boolean mKeyguardOccluded;
    public final LatencyTracker mLatencyTracker;
    public final KeyguardListenQueue mListenModels;
    public LockPatternUtils mLockPatternUtils;
    public boolean mLogoutEnabled;
    public boolean mNeedsSlowUnlockTransition;
    public boolean mOccludingAppRequestingFace;
    public boolean mOccludingAppRequestingFp;
    public int mPhoneState;
    @VisibleForTesting
    public TelephonyCallback.ActiveDataSubscriptionIdListener mPhoneStateListener;
    public C05517 mRetryFaceAuthentication;
    public C05506 mRetryFingerprintAuthentication;
    public HashMap mSecondaryLockscreenRequirement;
    public boolean mSecureCameraLaunched;
    public SensorPrivacyManager mSensorPrivacyManager;
    public HashMap<Integer, ServiceState> mServiceStates = new HashMap<>();
    public HashMap<Integer, SimData> mSimDatas = new HashMap<>();
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final C05351 mStatusBarStateControllerListener;
    public StrongAuthTracker mStrongAuthTracker;
    public List<SubscriptionInfo> mSubscriptionInfo;
    public C05484 mSubscriptionListener;
    public SubscriptionManager mSubscriptionManager;
    public boolean mSwitchingUser;
    public final C054519 mTaskStackListener;
    @VisibleForTesting
    public boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public TelephonyManager mTelephonyManager;
    public C054216 mTimeFormatChangeObserver;
    public TrustManager mTrustManager;
    @VisibleForTesting
    public SparseArray<BiometricAuthenticated> mUserFaceAuthenticated;
    public SparseBooleanArray mUserFaceUnlockRunning;
    @VisibleForTesting
    public SparseArray<BiometricAuthenticated> mUserFingerprintAuthenticated;
    public SparseBooleanArray mUserHasTrust;
    public SparseBooleanArray mUserIsUnlocked;
    public UserManager mUserManager;
    public final C054317 mUserSwitchObserver;
    public SparseBooleanArray mUserTrustIsManaged;
    public SparseBooleanArray mUserTrustIsUsuallyManaged;

    public static class SimData {
        public int simState;
        public int slotId;
        public int subId;

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SimData{state=");
            m.append(this.simState);
            m.append(",slotId=");
            m.append(this.slotId);
            m.append(",subId=");
            m.append(this.subId);
            m.append("}");
            return m.toString();
        }

        public SimData(int i, int i2, int i3) {
            this.simState = i;
            this.slotId = i2;
            this.subId = i3;
        }
    }

    public static class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public final Consumer<Integer> mStrongAuthRequiredChangedCallback;

        public final void onStrongAuthRequiredChanged(int i) {
            this.mStrongAuthRequiredChangedCallback.accept(Integer.valueOf(i));
        }

        public StrongAuthTracker(Context context, DozeTriggers$$ExternalSyntheticLambda2 dozeTriggers$$ExternalSyntheticLambda2) {
            super(context);
            this.mStrongAuthRequiredChangedCallback = dozeTriggers$$ExternalSyntheticLambda2;
        }
    }

    @VisibleForTesting
    public KeyguardUpdateMonitor(Context context, Looper looper, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker) {
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        Executor executor3 = executor;
        StatusBarStateController statusBarStateController2 = statusBarStateController;
        C05351 r5 = new StatusBarStateController.StateListener() {
            public final void onExpandedChanged(boolean z) {
                for (int i = 0; i < KeyguardUpdateMonitor.this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) KeyguardUpdateMonitor.this.mCallbacks.get(i).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onShadeExpandedChanged(z);
                    }
                }
            }

            public final void onStateChanged(int i) {
                KeyguardUpdateMonitor.this.mStatusBarState = i;
            }
        };
        this.mStatusBarStateControllerListener = r5;
        boolean z = true;
        this.mFpCancelNotReceived = new TaskView$$ExternalSyntheticLambda5(this, 1);
        this.mFaceCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda8(this, 0);
        this.mBiometricEnabledForUser = new SparseBooleanArray();
        this.mBiometricEnabledCallback = new IBiometricEnabledOnKeyguardCallback.Stub() {
            public final void onChanged(boolean z, int i) throws RemoteException {
                KeyguardUpdateMonitor.this.mHandler.post(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(this, i, z));
            }
        };
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() {
            public final void onActiveDataSubscriptionIdChanged(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mActiveMobileDataSubscription = i;
                keyguardUpdateMonitor.mHandler.sendEmptyMessage(328);
            }
        };
        this.mSubscriptionListener = new SubscriptionManager.OnSubscriptionsChangedListener() {
            public final void onSubscriptionsChanged() {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(328);
            }
        };
        this.mUserIsUnlocked = new SparseBooleanArray();
        this.mUserHasTrust = new SparseBooleanArray();
        this.mUserTrustIsManaged = new SparseBooleanArray();
        this.mUserTrustIsUsuallyManaged = new SparseBooleanArray();
        this.mUserFaceUnlockRunning = new SparseBooleanArray();
        this.mSecondaryLockscreenRequirement = new HashMap();
        this.mUserFingerprintAuthenticated = new SparseArray<>();
        this.mUserFaceAuthenticated = new SparseArray<>();
        this.mListenModels = new KeyguardListenQueue();
        this.mRetryFingerprintAuthentication = new Runnable() {
            public final void run() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Retrying fingerprint after HW unavailable, attempt ");
                m.append(KeyguardUpdateMonitor.this.mHardwareFingerprintUnavailableRetryCount);
                Log.w("KeyguardUpdateMonitor", m.toString());
                if (KeyguardUpdateMonitor.this.mFpm.isHardwareDetected()) {
                    KeyguardUpdateMonitor.this.updateFingerprintListeningState(2);
                    return;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i = keyguardUpdateMonitor.mHardwareFingerprintUnavailableRetryCount;
                if (i < 20) {
                    keyguardUpdateMonitor.mHardwareFingerprintUnavailableRetryCount = i + 1;
                    keyguardUpdateMonitor.mHandler.postDelayed(keyguardUpdateMonitor.mRetryFingerprintAuthentication, 500);
                }
            }
        };
        this.mRetryFaceAuthentication = new Runnable() {
            public final void run() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Retrying face after HW unavailable, attempt ");
                m.append(KeyguardUpdateMonitor.this.mHardwareFaceUnavailableRetryCount);
                Log.w("KeyguardUpdateMonitor", m.toString());
                KeyguardUpdateMonitor.this.updateFaceListeningState(2);
            }
        };
        C05528 r8 = new BroadcastReceiver() {
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f6, code lost:
                if ("IMSI".equals(r2) == false) goto L_0x00f9;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onReceive(android.content.Context r12, android.content.Intent r13) {
                /*
                    r11 = this;
                    java.lang.String r12 = r13.getAction()
                    boolean r0 = com.android.keyguard.KeyguardUpdateMonitor.DEBUG
                    java.lang.String r1 = "KeyguardUpdateMonitor"
                    if (r0 == 0) goto L_0x0010
                    java.lang.String r2 = "received broadcast "
                    androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m17m(r2, r12, r1)
                L_0x0010:
                    java.lang.String r2 = "android.intent.action.TIME_TICK"
                    boolean r2 = r2.equals(r12)
                    if (r2 != 0) goto L_0x01ef
                    java.lang.String r2 = "android.intent.action.TIME_SET"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x0022
                    goto L_0x01ef
                L_0x0022:
                    java.lang.String r2 = "android.intent.action.TIMEZONE_CHANGED"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x0044
                    com.android.keyguard.KeyguardUpdateMonitor r12 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r12 = r12.mHandler
                    r0 = 339(0x153, float:4.75E-43)
                    java.lang.String r1 = "time-zone"
                    java.lang.String r13 = r13.getStringExtra(r1)
                    android.os.Message r12 = r12.obtainMessage(r0, r13)
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r11.sendMessage(r12)
                    goto L_0x01f8
                L_0x0044:
                    java.lang.String r2 = "android.intent.action.BATTERY_CHANGED"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x0064
                    com.android.keyguard.KeyguardUpdateMonitor r12 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r12 = r12.mHandler
                    r0 = 302(0x12e, float:4.23E-43)
                    com.android.settingslib.fuelgauge.BatteryStatus r1 = new com.android.settingslib.fuelgauge.BatteryStatus
                    r1.<init>(r13)
                    android.os.Message r12 = r12.obtainMessage(r0, r1)
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r11.sendMessage(r12)
                    goto L_0x01f8
                L_0x0064:
                    java.lang.String r2 = "android.intent.action.SIM_STATE_CHANGED"
                    boolean r3 = r2.equals(r12)
                    java.lang.String r4 = "action "
                    java.lang.String r5 = "android.telephony.extra.SUBSCRIPTION_INDEX"
                    r6 = -1
                    r7 = 0
                    if (r3 == 0) goto L_0x0154
                    java.lang.String r0 = r13.getAction()
                    boolean r0 = r2.equals(r0)
                    if (r0 == 0) goto L_0x014c
                    java.lang.String r0 = "ss"
                    java.lang.String r2 = r13.getStringExtra(r0)
                    java.lang.String r3 = "android.telephony.extra.SLOT_INDEX"
                    int r3 = r13.getIntExtra(r3, r7)
                    int r5 = r13.getIntExtra(r5, r6)
                    java.lang.String r6 = "ABSENT"
                    boolean r6 = r6.equals(r2)
                    r8 = 5
                    java.lang.String r9 = "reason"
                    r10 = 1
                    if (r6 == 0) goto L_0x00aa
                    java.lang.String r2 = r13.getStringExtra(r9)
                    java.lang.String r6 = "PERM_DISABLED"
                    boolean r2 = r6.equals(r2)
                    if (r2 == 0) goto L_0x00a8
                    r8 = 7
                    goto L_0x00fa
                L_0x00a8:
                    r8 = r10
                    goto L_0x00fa
                L_0x00aa:
                    java.lang.String r6 = "READY"
                    boolean r6 = r6.equals(r2)
                    if (r6 == 0) goto L_0x00b3
                    goto L_0x00fa
                L_0x00b3:
                    java.lang.String r6 = "LOCKED"
                    boolean r6 = r6.equals(r2)
                    if (r6 == 0) goto L_0x00d3
                    java.lang.String r2 = r13.getStringExtra(r9)
                    java.lang.String r6 = "PIN"
                    boolean r6 = r6.equals(r2)
                    if (r6 == 0) goto L_0x00c9
                    r8 = 2
                    goto L_0x00fa
                L_0x00c9:
                    java.lang.String r6 = "PUK"
                    boolean r2 = r6.equals(r2)
                    if (r2 == 0) goto L_0x00f9
                    r8 = 3
                    goto L_0x00fa
                L_0x00d3:
                    java.lang.String r6 = "NETWORK"
                    boolean r6 = r6.equals(r2)
                    if (r6 == 0) goto L_0x00dd
                    r8 = 4
                    goto L_0x00fa
                L_0x00dd:
                    java.lang.String r6 = "CARD_IO_ERROR"
                    boolean r6 = r6.equals(r2)
                    if (r6 == 0) goto L_0x00e8
                    r8 = 8
                    goto L_0x00fa
                L_0x00e8:
                    java.lang.String r6 = "LOADED"
                    boolean r6 = r6.equals(r2)
                    if (r6 != 0) goto L_0x00fa
                    java.lang.String r6 = "IMSI"
                    boolean r2 = r6.equals(r2)
                    if (r2 == 0) goto L_0x00f9
                    goto L_0x00fa
                L_0x00f9:
                    r8 = r7
                L_0x00fa:
                    java.lang.String r2 = "rebroadcastOnUnlock"
                    boolean r2 = r13.getBooleanExtra(r2, r7)
                    if (r2 == 0) goto L_0x0115
                    if (r8 != r10) goto L_0x0114
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 338(0x152, float:4.74E-43)
                    java.lang.Boolean r13 = java.lang.Boolean.TRUE
                    android.os.Message r11 = r11.obtainMessage(r12, r13)
                    r11.sendToTarget()
                L_0x0114:
                    return
                L_0x0115:
                    java.lang.String r2 = " state: "
                    java.lang.StringBuilder r12 = androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m(r4, r12, r2)
                    java.lang.String r13 = r13.getStringExtra(r0)
                    r12.append(r13)
                    java.lang.String r13 = " slotId: "
                    r12.append(r13)
                    r12.append(r3)
                    java.lang.String r13 = " subid: "
                    r12.append(r13)
                    r12.append(r5)
                    java.lang.String r12 = r12.toString()
                    android.util.Log.v(r1, r12)
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 304(0x130, float:4.26E-43)
                    java.lang.Integer r13 = java.lang.Integer.valueOf(r8)
                    android.os.Message r11 = r11.obtainMessage(r12, r5, r3, r13)
                    r11.sendToTarget()
                    goto L_0x01f8
                L_0x014c:
                    java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
                    java.lang.String r12 = "only handles intent ACTION_SIM_STATE_CHANGED"
                    r11.<init>(r12)
                    throw r11
                L_0x0154:
                    java.lang.String r2 = "android.intent.action.PHONE_STATE"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x0172
                    java.lang.String r12 = "state"
                    java.lang.String r12 = r13.getStringExtra(r12)
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r13 = 306(0x132, float:4.29E-43)
                    android.os.Message r12 = r11.obtainMessage(r13, r12)
                    r11.sendMessage(r12)
                    goto L_0x01f8
                L_0x0172:
                    java.lang.String r2 = "android.intent.action.AIRPLANE_MODE"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x0185
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 329(0x149, float:4.61E-43)
                    r11.sendEmptyMessage(r12)
                    goto L_0x01f8
                L_0x0185:
                    java.lang.String r2 = "android.intent.action.SERVICE_STATE"
                    boolean r2 = r2.equals(r12)
                    if (r2 == 0) goto L_0x01cb
                    android.os.Bundle r2 = r13.getExtras()
                    android.telephony.ServiceState r2 = android.telephony.ServiceState.newFromBundle(r2)
                    int r13 = r13.getIntExtra(r5, r6)
                    if (r0 == 0) goto L_0x01bd
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    r0.append(r4)
                    r0.append(r12)
                    java.lang.String r12 = " serviceState="
                    r0.append(r12)
                    r0.append(r2)
                    java.lang.String r12 = " subId="
                    r0.append(r12)
                    r0.append(r13)
                    java.lang.String r12 = r0.toString()
                    android.util.Log.v(r1, r12)
                L_0x01bd:
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 330(0x14a, float:4.62E-43)
                    android.os.Message r12 = r11.obtainMessage(r12, r13, r7, r2)
                    r11.sendMessage(r12)
                    goto L_0x01f8
                L_0x01cb:
                    java.lang.String r13 = "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"
                    boolean r13 = r13.equals(r12)
                    if (r13 == 0) goto L_0x01dd
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 328(0x148, float:4.6E-43)
                    r11.sendEmptyMessage(r12)
                    goto L_0x01f8
                L_0x01dd:
                    java.lang.String r13 = "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"
                    boolean r12 = r13.equals(r12)
                    if (r12 == 0) goto L_0x01f8
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 337(0x151, float:4.72E-43)
                    r11.sendEmptyMessage(r12)
                    goto L_0x01f8
                L_0x01ef:
                    com.android.keyguard.KeyguardUpdateMonitor r11 = com.android.keyguard.KeyguardUpdateMonitor.this
                    com.android.keyguard.KeyguardUpdateMonitor$14 r11 = r11.mHandler
                    r12 = 301(0x12d, float:4.22E-43)
                    r11.sendEmptyMessage(r12)
                L_0x01f8:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.C05528.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        this.mBroadcastReceiver = r8;
        C05539 r10 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(301);
                } else if ("android.intent.action.USER_INFO_CHANGED".equals(action)) {
                    C054014 r5 = KeyguardUpdateMonitor.this.mHandler;
                    r5.sendMessage(r5.obtainMessage(317, intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId()), 0));
                } else if ("com.android.facelock.FACE_UNLOCK_STARTED".equals(action)) {
                    Trace.beginSection("KeyguardUpdateMonitor.mBroadcastAllReceiver#onReceive ACTION_FACE_UNLOCK_STARTED");
                    C054014 r52 = KeyguardUpdateMonitor.this.mHandler;
                    r52.sendMessage(r52.obtainMessage(327, 1, getSendingUserId()));
                    Trace.endSection();
                } else if ("com.android.facelock.FACE_UNLOCK_STOPPED".equals(action)) {
                    C054014 r53 = KeyguardUpdateMonitor.this.mHandler;
                    r53.sendMessage(r53.obtainMessage(327, 0, getSendingUserId()));
                } else if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                    C054014 r54 = KeyguardUpdateMonitor.this.mHandler;
                    r54.sendMessage(r54.obtainMessage(309, Integer.valueOf(getSendingUserId())));
                } else if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    C054014 r55 = KeyguardUpdateMonitor.this.mHandler;
                    r55.sendMessage(r55.obtainMessage(334, getSendingUserId(), 0));
                } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                    C054014 r4 = KeyguardUpdateMonitor.this.mHandler;
                    r4.sendMessage(r4.obtainMessage(340, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    C054014 r42 = KeyguardUpdateMonitor.this.mHandler;
                    r42.sendMessage(r42.obtainMessage(341, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(345);
                }
            }
        };
        this.mBroadcastAllReceiver = r10;
        this.mFingerprintLockoutResetCallback = new FingerprintManager.LockoutResetCallback() {
            public final void onLockoutReset(int i) {
                boolean z;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                if (keyguardUpdateMonitor.mFingerprintLockedOut || keyguardUpdateMonitor.mFingerprintLockedOutPermanent) {
                    z = true;
                } else {
                    z = false;
                }
                keyguardUpdateMonitor.mFingerprintLockedOut = false;
                keyguardUpdateMonitor.mFingerprintLockedOutPermanent = false;
                if (keyguardUpdateMonitor.isUdfpsEnrolled()) {
                    keyguardUpdateMonitor.mHandler.postDelayed(new ScrimView$$ExternalSyntheticLambda0(keyguardUpdateMonitor, 2), 600);
                } else {
                    keyguardUpdateMonitor.updateFingerprintListeningState(2);
                }
                if (z) {
                    keyguardUpdateMonitor.notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
                }
            }
        };
        this.mFaceLockoutResetCallback = new FaceManager.LockoutResetCallback() {
            public final void onLockoutReset(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                boolean z = keyguardUpdateMonitor.mFaceLockedOutPermanent;
                keyguardUpdateMonitor.mFaceLockedOutPermanent = false;
                keyguardUpdateMonitor.mHandler.postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda7(keyguardUpdateMonitor, 0), 600);
                if (z) {
                    keyguardUpdateMonitor.notifyLockedOutStateChanged(BiometricSourceType.FACE);
                }
            }
        };
        this.mFingerprintDetectionCallback = new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(this);
        this.mFingerprintAuthenticationCallback = new FingerprintManager.AuthenticationCallback() {
            public final void onAuthenticationAcquired(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                if (i == 0) {
                    for (int i2 = 0; i2 < keyguardUpdateMonitor.mCallbacks.size(); i2++) {
                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i2).get();
                        if (keyguardUpdateMonitorCallback != null) {
                            keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT);
                        }
                    }
                }
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                boolean z;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                String charSequence2 = charSequence.toString();
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                if (keyguardUpdateMonitor.mHandler.hasCallbacks(keyguardUpdateMonitor.mFpCancelNotReceived)) {
                    keyguardUpdateMonitor.mHandler.removeCallbacks(keyguardUpdateMonitor.mFpCancelNotReceived);
                }
                keyguardUpdateMonitor.mFingerprintCancelSignal = null;
                if (i == 5 && keyguardUpdateMonitor.mFingerprintRunningState == 3) {
                    keyguardUpdateMonitor.setFingerprintRunningState(0);
                    keyguardUpdateMonitor.updateFingerprintListeningState(2);
                } else {
                    keyguardUpdateMonitor.setFingerprintRunningState(0);
                }
                if (i == 1) {
                    keyguardUpdateMonitor.mHandler.postDelayed(keyguardUpdateMonitor.mRetryFingerprintAuthentication, 500);
                }
                if (i == 9) {
                    z = (!keyguardUpdateMonitor.mFingerprintLockedOutPermanent) | false;
                    keyguardUpdateMonitor.mFingerprintLockedOutPermanent = true;
                    Log.d("KeyguardUpdateMonitor", "Fingerprint locked out - requiring strong auth");
                    keyguardUpdateMonitor.mLockPatternUtils.requireStrongAuth(8, KeyguardUpdateMonitor.getCurrentUser());
                } else {
                    z = false;
                }
                if (i == 7 || i == 9) {
                    z |= !keyguardUpdateMonitor.mFingerprintLockedOut;
                    keyguardUpdateMonitor.mFingerprintLockedOut = true;
                    if (keyguardUpdateMonitor.isUdfpsEnrolled()) {
                        keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    }
                    keyguardUpdateMonitor.stopListeningForFace();
                }
                for (int i2 = 0; i2 < keyguardUpdateMonitor.mCallbacks.size(); i2++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i2).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onBiometricError(i, charSequence2, BiometricSourceType.FINGERPRINT);
                    }
                }
                if (z) {
                    keyguardUpdateMonitor.notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
                }
            }

            public final void onAuthenticationFailed() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                for (int i = 0; i < keyguardUpdateMonitor.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
                    }
                }
                if (keyguardUpdateMonitor.isUdfpsSupported()) {
                    keyguardUpdateMonitor.handleFingerprintHelp(-1, keyguardUpdateMonitor.mContext.getString(17040347));
                } else {
                    keyguardUpdateMonitor.handleFingerprintHelp(-1, keyguardUpdateMonitor.mContext.getString(17040333));
                }
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFingerprintHelp(i, charSequence.toString());
            }

            public final void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                KeyguardUpdateMonitor.this.handleFingerprintAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                Trace.endSection();
            }

            public final void onUdfpsPointerDown(int i) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("onUdfpsPointerDown, sensorId: ", i, "KeyguardUpdateMonitor");
            }

            public final void onUdfpsPointerUp(int i) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("onUdfpsPointerUp, sensorId: ", i, "KeyguardUpdateMonitor");
            }
        };
        this.mFaceDetectionCallback = new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this);
        this.mFaceAuthenticationCallback = new FaceManager.AuthenticationCallback() {
            public final void onAuthenticationAcquired(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                if (i == 0) {
                    if (KeyguardUpdateMonitor.DEBUG_FACE) {
                        Log.d("KeyguardUpdateMonitor", "Face acquired");
                    }
                    for (int i2 = 0; i2 < keyguardUpdateMonitor.mCallbacks.size(); i2++) {
                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i2).get();
                        if (keyguardUpdateMonitorCallback != null) {
                            keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FACE);
                        }
                    }
                }
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                boolean z;
                boolean z2;
                boolean z3;
                int i2;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                String charSequence2 = charSequence.toString();
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                if (KeyguardUpdateMonitor.DEBUG_FACE) {
                    DialogFragment$$ExternalSyntheticOutline0.m17m("Face error received: ", charSequence2, "KeyguardUpdateMonitor");
                }
                if (keyguardUpdateMonitor.mHandler.hasCallbacks(keyguardUpdateMonitor.mFaceCancelNotReceived)) {
                    keyguardUpdateMonitor.mHandler.removeCallbacks(keyguardUpdateMonitor.mFaceCancelNotReceived);
                }
                keyguardUpdateMonitor.mFaceCancelSignal = null;
                SensorPrivacyManager sensorPrivacyManager = keyguardUpdateMonitor.mSensorPrivacyManager;
                if (sensorPrivacyManager != null) {
                    z = sensorPrivacyManager.isSensorPrivacyEnabled(2, keyguardUpdateMonitor.mFaceAuthUserId);
                } else {
                    z = false;
                }
                if (i == 5 && keyguardUpdateMonitor.mFaceRunningState == 3) {
                    keyguardUpdateMonitor.setFaceRunningState(0);
                    keyguardUpdateMonitor.updateFaceListeningState(2);
                } else {
                    keyguardUpdateMonitor.setFaceRunningState(0);
                }
                if (i == 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if ((z2 || i == 2) && (i2 = keyguardUpdateMonitor.mHardwareFaceUnavailableRetryCount) < 20) {
                    keyguardUpdateMonitor.mHardwareFaceUnavailableRetryCount = i2 + 1;
                    keyguardUpdateMonitor.mHandler.removeCallbacks(keyguardUpdateMonitor.mRetryFaceAuthentication);
                    keyguardUpdateMonitor.mHandler.postDelayed(keyguardUpdateMonitor.mRetryFaceAuthentication, 500);
                }
                if (i == 9) {
                    z3 = !keyguardUpdateMonitor.mFaceLockedOutPermanent;
                    keyguardUpdateMonitor.mFaceLockedOutPermanent = true;
                } else {
                    z3 = false;
                }
                if (z2 && z) {
                    charSequence2 = keyguardUpdateMonitor.mContext.getString(C1777R.string.kg_face_sensor_privacy_enabled);
                }
                for (int i3 = 0; i3 < keyguardUpdateMonitor.mCallbacks.size(); i3++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i3).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onBiometricError(i, charSequence2, BiometricSourceType.FACE);
                    }
                }
                if (z3) {
                    keyguardUpdateMonitor.notifyLockedOutStateChanged(BiometricSourceType.FACE);
                }
                KeyguardBypassController keyguardBypassController = KeyguardUpdateMonitor.this.mKeyguardBypassController;
                if (keyguardBypassController != null) {
                    keyguardBypassController.userHasDeviceEntryIntent = false;
                }
            }

            public final void onAuthenticationFailed() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                Objects.requireNonNull(keyguardUpdateMonitor);
                Assert.isMainThread();
                keyguardUpdateMonitor.mFaceCancelSignal = null;
                keyguardUpdateMonitor.setFaceRunningState(0);
                for (int i = 0; i < keyguardUpdateMonitor.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FACE);
                    }
                }
                keyguardUpdateMonitor.handleFaceHelp(-2, keyguardUpdateMonitor.mContext.getString(C1777R.string.kg_face_not_recognized));
                KeyguardBypassController keyguardBypassController = KeyguardUpdateMonitor.this.mKeyguardBypassController;
                if (keyguardBypassController != null) {
                    Objects.requireNonNull(keyguardBypassController);
                    keyguardBypassController.userHasDeviceEntryIntent = false;
                }
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceHelp(i, charSequence.toString());
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                KeyguardUpdateMonitor.this.handleFaceAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                Trace.endSection();
                KeyguardBypassController keyguardBypassController = KeyguardUpdateMonitor.this.mKeyguardBypassController;
                if (keyguardBypassController != null) {
                    Objects.requireNonNull(keyguardBypassController);
                    keyguardBypassController.userHasDeviceEntryIntent = false;
                }
            }
        };
        C054317 r11 = new UserSwitchObserver() {
            public final void onUserSwitchComplete(int i) throws RemoteException {
                C054014 r2 = KeyguardUpdateMonitor.this.mHandler;
                r2.sendMessage(r2.obtainMessage(314, i, 0));
            }

            public final void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                C054014 r2 = KeyguardUpdateMonitor.this.mHandler;
                r2.sendMessage(r2.obtainMessage(310, i, 0, iRemoteCallback));
            }
        };
        this.mUserSwitchObserver = r11;
        this.mTaskStackListener = new TaskStackChangeListener() {
            public final void onTaskStackChangedBackground() {
                try {
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(0, 4);
                    if (rootTaskInfo != null) {
                        C054014 r3 = KeyguardUpdateMonitor.this.mHandler;
                        r3.sendMessage(r3.obtainMessage(335, Boolean.valueOf(rootTaskInfo.visible)));
                    }
                } catch (RemoteException e) {
                    Log.e("KeyguardUpdateMonitor", "unable to check task stack", e);
                }
            }
        };
        this.mContext = context;
        this.mSubscriptionManager = SubscriptionManager.from(context);
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb();
        this.mStrongAuthTracker = new StrongAuthTracker(context, new DozeTriggers$$ExternalSyntheticLambda2(this, 1));
        this.mBackgroundExecutor = executor3;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mStatusBarStateController = statusBarStateController2;
        statusBarStateController2.addCallback(r5);
        this.mStatusBarState = statusBarStateController.getState();
        this.mLockPatternUtils = lockPatternUtils;
        this.mAuthController = authController;
        dumpManager.registerDumpable(KeyguardUpdateMonitor.class.getName(), this);
        this.mSensorPrivacyManager = (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class);
        C054014 r4 = new Handler(looper) {
            public final void handleMessage(Message message) {
                boolean z;
                boolean z2 = true;
                int i = 0;
                switch (message.what) {
                    case 301:
                        KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Log.d("KeyguardUpdateMonitor", "handleTimeUpdate");
                        }
                        while (i < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback != null) {
                                keyguardUpdateMonitorCallback.onTimeChanged();
                            }
                            i++;
                        }
                        return;
                    case 302:
                        KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                        BatteryStatus batteryStatus = (BatteryStatus) message.obj;
                        Objects.requireNonNull(keyguardUpdateMonitor2);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Log.d("KeyguardUpdateMonitor", "handleBatteryUpdate");
                        }
                        BatteryStatus batteryStatus2 = keyguardUpdateMonitor2.mBatteryStatus;
                        boolean isPluggedIn = batteryStatus.isPluggedIn();
                        boolean isPluggedIn2 = batteryStatus2.isPluggedIn();
                        if (!isPluggedIn2 || !isPluggedIn || batteryStatus2.status == batteryStatus.status) {
                            z = false;
                        } else {
                            z = true;
                        }
                        boolean z3 = batteryStatus.present;
                        boolean z4 = batteryStatus2.present;
                        if (isPluggedIn2 == isPluggedIn && !z && batteryStatus2.level == batteryStatus.level && ((!isPluggedIn || batteryStatus.maxChargingWattage == batteryStatus2.maxChargingWattage) && z4 == z3 && batteryStatus.health == batteryStatus2.health)) {
                            z2 = false;
                        }
                        keyguardUpdateMonitor2.mBatteryStatus = batteryStatus;
                        if (z2) {
                            while (i < keyguardUpdateMonitor2.mCallbacks.size()) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor2.mCallbacks.get(i).get();
                                if (keyguardUpdateMonitorCallback2 != null) {
                                    keyguardUpdateMonitorCallback2.onRefreshBatteryInfo(batteryStatus);
                                }
                                i++;
                            }
                            return;
                        }
                        return;
                    case 304:
                        KeyguardUpdateMonitor.this.handleSimStateChange(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        return;
                    case 306:
                        KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                        String str = (String) message.obj;
                        Objects.requireNonNull(keyguardUpdateMonitor3);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Log.d("KeyguardUpdateMonitor", "handlePhoneStateChanged(" + str + ")");
                        }
                        if (TelephonyManager.EXTRA_STATE_IDLE.equals(str)) {
                            keyguardUpdateMonitor3.mPhoneState = 0;
                        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(str)) {
                            keyguardUpdateMonitor3.mPhoneState = 2;
                        } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(str)) {
                            keyguardUpdateMonitor3.mPhoneState = 1;
                        }
                        while (i < keyguardUpdateMonitor3.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback3 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor3.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback3 != null) {
                                keyguardUpdateMonitorCallback3.onPhoneStateChanged();
                            }
                            i++;
                        }
                        return;
                    case 308:
                        KeyguardUpdateMonitor keyguardUpdateMonitor4 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor4);
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor4.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback4 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor4.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback4 != null) {
                                keyguardUpdateMonitorCallback4.onDeviceProvisioned();
                            }
                            i++;
                        }
                        if (keyguardUpdateMonitor4.mDeviceProvisionedObserver != null) {
                            keyguardUpdateMonitor4.mContext.getContentResolver().unregisterContentObserver(keyguardUpdateMonitor4.mDeviceProvisionedObserver);
                            keyguardUpdateMonitor4.mDeviceProvisionedObserver = null;
                            return;
                        }
                        return;
                    case 309:
                        KeyguardUpdateMonitor keyguardUpdateMonitor5 = KeyguardUpdateMonitor.this;
                        int i2 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor5);
                        Assert.isMainThread();
                        keyguardUpdateMonitor5.updateFingerprintListeningState(2);
                        keyguardUpdateMonitor5.updateSecondaryLockscreenRequirement(i2);
                        return;
                    case 310:
                        KeyguardUpdateMonitor.this.handleUserSwitching(message.arg1, (IRemoteCallback) message.obj);
                        return;
                    case 312:
                        KeyguardUpdateMonitor keyguardUpdateMonitor6 = KeyguardUpdateMonitor.this;
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Objects.requireNonNull(keyguardUpdateMonitor6);
                            Log.d("KeyguardUpdateMonitor", "handleKeyguardReset");
                        }
                        keyguardUpdateMonitor6.updateBiometricListeningState(2);
                        keyguardUpdateMonitor6.mNeedsSlowUnlockTransition = keyguardUpdateMonitor6.resolveNeedsSlowUnlockTransition();
                        return;
                    case 314:
                        KeyguardUpdateMonitor.this.handleUserSwitchComplete(message.arg1);
                        return;
                    case 317:
                        KeyguardUpdateMonitor keyguardUpdateMonitor7 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor7);
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor7.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback5 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor7.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback5 != null) {
                                keyguardUpdateMonitorCallback5.onUserInfoChanged();
                            }
                            i++;
                        }
                        return;
                    case 318:
                        KeyguardUpdateMonitor.this.handleReportEmergencyCallAction();
                        return;
                    case 319:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_STARTED_WAKING_UP");
                        KeyguardUpdateMonitor keyguardUpdateMonitor8 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor8);
                        Trace.beginSection("KeyguardUpdateMonitor#handleStartedWakingUp");
                        Assert.isMainThread();
                        keyguardUpdateMonitor8.updateBiometricListeningState(2);
                        keyguardUpdateMonitor8.requestActiveUnlock();
                        while (i < keyguardUpdateMonitor8.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback6 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor8.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback6 != null) {
                                keyguardUpdateMonitorCallback6.onStartedWakingUp();
                            }
                            i++;
                        }
                        Trace.endSection();
                        Trace.endSection();
                        return;
                    case 320:
                        KeyguardUpdateMonitor keyguardUpdateMonitor9 = KeyguardUpdateMonitor.this;
                        int i3 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor9);
                        Assert.isMainThread();
                        keyguardUpdateMonitor9.mGoingToSleep = false;
                        while (i < keyguardUpdateMonitor9.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback7 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor9.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback7 != null) {
                                keyguardUpdateMonitorCallback7.onFinishedGoingToSleep(i3);
                            }
                            i++;
                        }
                        keyguardUpdateMonitor9.updateBiometricListeningState(2);
                        return;
                    case 321:
                        KeyguardUpdateMonitor keyguardUpdateMonitor10 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor10);
                        Assert.isMainThread();
                        keyguardUpdateMonitor10.clearBiometricRecognized(-10000);
                        while (i < keyguardUpdateMonitor10.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback8 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor10.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback8 != null) {
                                keyguardUpdateMonitorCallback8.onStartedGoingToSleep$1();
                            }
                            i++;
                        }
                        keyguardUpdateMonitor10.mGoingToSleep = true;
                        keyguardUpdateMonitor10.updateBiometricListeningState(2);
                        return;
                    case 322:
                        KeyguardUpdateMonitor keyguardUpdateMonitor11 = KeyguardUpdateMonitor.this;
                        int i4 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor11);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Log.d("KeyguardUpdateMonitor", "handleKeyguardBouncerChanged(" + i4 + ")");
                        }
                        if (i4 != 1) {
                            z2 = false;
                        }
                        keyguardUpdateMonitor11.mBouncer = z2;
                        if (z2) {
                            keyguardUpdateMonitor11.mSecureCameraLaunched = false;
                        } else {
                            keyguardUpdateMonitor11.mCredentialAttempted = false;
                        }
                        while (i < keyguardUpdateMonitor11.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback9 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor11.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback9 != null) {
                                keyguardUpdateMonitorCallback9.onKeyguardBouncerChanged(keyguardUpdateMonitor11.mBouncer);
                            }
                            i++;
                        }
                        keyguardUpdateMonitor11.updateBiometricListeningState(2);
                        return;
                    case 327:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_FACE_UNLOCK_STATE_CHANGED");
                        KeyguardUpdateMonitor keyguardUpdateMonitor12 = KeyguardUpdateMonitor.this;
                        if (message.arg1 == 0) {
                            z2 = false;
                        }
                        int i5 = message.arg2;
                        Objects.requireNonNull(keyguardUpdateMonitor12);
                        Assert.isMainThread();
                        keyguardUpdateMonitor12.mUserFaceUnlockRunning.put(i5, z2);
                        while (i < keyguardUpdateMonitor12.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback10 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor12.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback10 != null) {
                                keyguardUpdateMonitorCallback10.onFaceUnlockStateChanged();
                            }
                            i++;
                        }
                        Trace.endSection();
                        return;
                    case 328:
                        KeyguardUpdateMonitor keyguardUpdateMonitor13 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor13);
                        Assert.isMainThread();
                        Log.v("KeyguardUpdateMonitor", "onSubscriptionInfoChanged()");
                        List<SubscriptionInfo> completeActiveSubscriptionInfoList = keyguardUpdateMonitor13.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
                        if (completeActiveSubscriptionInfoList != null) {
                            for (SubscriptionInfo subscriptionInfo : completeActiveSubscriptionInfoList) {
                                Log.v("KeyguardUpdateMonitor", "SubInfo:" + subscriptionInfo);
                            }
                        } else {
                            Log.v("KeyguardUpdateMonitor", "onSubscriptionInfoChanged: list is null");
                        }
                        ArrayList subscriptionInfo2 = keyguardUpdateMonitor13.getSubscriptionInfo(true);
                        ArrayList arrayList = new ArrayList();
                        HashSet hashSet = new HashSet();
                        for (int i6 = 0; i6 < subscriptionInfo2.size(); i6++) {
                            SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) subscriptionInfo2.get(i6);
                            hashSet.add(Integer.valueOf(subscriptionInfo3.getSubscriptionId()));
                            if (keyguardUpdateMonitor13.refreshSimState(subscriptionInfo3.getSubscriptionId(), subscriptionInfo3.getSimSlotIndex())) {
                                arrayList.add(subscriptionInfo3);
                            }
                        }
                        Iterator<Map.Entry<Integer, SimData>> it = keyguardUpdateMonitor13.mSimDatas.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry next = it.next();
                            if (!hashSet.contains(next.getKey())) {
                                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Previously active sub id ");
                                m.append(next.getKey());
                                m.append(" is now invalid, will remove");
                                Log.i("KeyguardUpdateMonitor", m.toString());
                                it.remove();
                                SimData simData = (SimData) next.getValue();
                                for (int i7 = 0; i7 < keyguardUpdateMonitor13.mCallbacks.size(); i7++) {
                                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback11 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor13.mCallbacks.get(i7).get();
                                    if (keyguardUpdateMonitorCallback11 != null) {
                                        keyguardUpdateMonitorCallback11.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
                                    }
                                }
                            }
                        }
                        for (int i8 = 0; i8 < arrayList.size(); i8++) {
                            SimData simData2 = keyguardUpdateMonitor13.mSimDatas.get(Integer.valueOf(((SubscriptionInfo) arrayList.get(i8)).getSubscriptionId()));
                            for (int i9 = 0; i9 < keyguardUpdateMonitor13.mCallbacks.size(); i9++) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback12 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor13.mCallbacks.get(i9).get();
                                if (keyguardUpdateMonitorCallback12 != null) {
                                    keyguardUpdateMonitorCallback12.onSimStateChanged(simData2.subId, simData2.slotId, simData2.simState);
                                }
                            }
                        }
                        keyguardUpdateMonitor13.callbacksRefreshCarrierInfo();
                        return;
                    case 329:
                        KeyguardUpdateMonitor keyguardUpdateMonitor14 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor14);
                        keyguardUpdateMonitor14.callbacksRefreshCarrierInfo();
                        return;
                    case 330:
                        KeyguardUpdateMonitor.this.handleServiceStateChange(message.arg1, (ServiceState) message.obj);
                        return;
                    case 332:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_OFF");
                        KeyguardUpdateMonitor keyguardUpdateMonitor15 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor15);
                        Assert.isMainThread();
                        keyguardUpdateMonitor15.mHardwareFingerprintUnavailableRetryCount = 0;
                        keyguardUpdateMonitor15.mHardwareFaceUnavailableRetryCount = 0;
                        Trace.endSection();
                        return;
                    case 333:
                        KeyguardUpdateMonitor keyguardUpdateMonitor16 = KeyguardUpdateMonitor.this;
                        int i10 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor16);
                        Assert.isMainThread();
                        if (i10 != 1) {
                            z2 = false;
                        }
                        keyguardUpdateMonitor16.mIsDreaming = z2;
                        while (i < keyguardUpdateMonitor16.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback13 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor16.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback13 != null) {
                                keyguardUpdateMonitorCallback13.onDreamingStateChanged(keyguardUpdateMonitor16.mIsDreaming);
                            }
                            i++;
                        }
                        keyguardUpdateMonitor16.updateBiometricListeningState(2);
                        return;
                    case 334:
                        KeyguardUpdateMonitor keyguardUpdateMonitor17 = KeyguardUpdateMonitor.this;
                        int i11 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor17);
                        Assert.isMainThread();
                        keyguardUpdateMonitor17.mUserIsUnlocked.put(i11, true);
                        keyguardUpdateMonitor17.mNeedsSlowUnlockTransition = keyguardUpdateMonitor17.resolveNeedsSlowUnlockTransition();
                        while (i < keyguardUpdateMonitor17.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback14 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor17.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback14 != null) {
                                keyguardUpdateMonitorCallback14.onUserUnlocked();
                            }
                            i++;
                        }
                        return;
                    case 335:
                        KeyguardUpdateMonitor.this.setAssistantVisible(((Boolean) message.obj).booleanValue());
                        return;
                    case 336:
                        KeyguardUpdateMonitor.this.updateBiometricListeningState(2);
                        return;
                    case 337:
                        KeyguardUpdateMonitor keyguardUpdateMonitor18 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor18);
                        Assert.isMainThread();
                        boolean isLogoutEnabled = keyguardUpdateMonitor18.mDevicePolicyManager.isLogoutEnabled();
                        if (keyguardUpdateMonitor18.mLogoutEnabled != isLogoutEnabled) {
                            keyguardUpdateMonitor18.mLogoutEnabled = isLogoutEnabled;
                            while (i < keyguardUpdateMonitor18.mCallbacks.size()) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback15 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor18.mCallbacks.get(i).get();
                                if (keyguardUpdateMonitorCallback15 != null) {
                                    keyguardUpdateMonitorCallback15.onLogoutEnabledChanged();
                                }
                                i++;
                            }
                            return;
                        }
                        return;
                    case 338:
                        KeyguardUpdateMonitor.this.updateTelephonyCapable(((Boolean) message.obj).booleanValue());
                        return;
                    case 339:
                        KeyguardUpdateMonitor keyguardUpdateMonitor19 = KeyguardUpdateMonitor.this;
                        String str2 = (String) message.obj;
                        Objects.requireNonNull(keyguardUpdateMonitor19);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            Log.d("KeyguardUpdateMonitor", "handleTimeZoneUpdate");
                        }
                        while (i < keyguardUpdateMonitor19.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback16 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor19.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback16 != null) {
                                keyguardUpdateMonitorCallback16.onTimeZoneChanged(TimeZone.getTimeZone(str2));
                                keyguardUpdateMonitorCallback16.onTimeChanged();
                            }
                            i++;
                        }
                        return;
                    case 340:
                        KeyguardUpdateMonitor keyguardUpdateMonitor20 = KeyguardUpdateMonitor.this;
                        int i12 = message.arg1;
                        Objects.requireNonNull(keyguardUpdateMonitor20);
                        Assert.isMainThread();
                        keyguardUpdateMonitor20.mUserIsUnlocked.put(i12, keyguardUpdateMonitor20.mUserManager.isUserUnlocked(i12));
                        return;
                    case 341:
                        KeyguardUpdateMonitor.this.handleUserRemoved(message.arg1);
                        return;
                    case 342:
                        KeyguardUpdateMonitor keyguardUpdateMonitor21 = KeyguardUpdateMonitor.this;
                        boolean booleanValue = ((Boolean) message.obj).booleanValue();
                        Objects.requireNonNull(keyguardUpdateMonitor21);
                        Assert.isMainThread();
                        keyguardUpdateMonitor21.mKeyguardGoingAway = booleanValue;
                        keyguardUpdateMonitor21.updateBiometricListeningState(2);
                        return;
                    case 344:
                        KeyguardUpdateMonitor keyguardUpdateMonitor22 = KeyguardUpdateMonitor.this;
                        String str3 = (String) message.obj;
                        Objects.requireNonNull(keyguardUpdateMonitor22);
                        Assert.isMainThread();
                        if (KeyguardUpdateMonitor.DEBUG) {
                            DialogFragment$$ExternalSyntheticOutline0.m17m("handleTimeFormatUpdate timeFormat=", str3, "KeyguardUpdateMonitor");
                        }
                        while (i < keyguardUpdateMonitor22.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback17 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor22.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback17 != null) {
                                keyguardUpdateMonitorCallback17.onTimeFormatChanged(str3);
                            }
                            i++;
                        }
                        return;
                    case 345:
                        KeyguardUpdateMonitor keyguardUpdateMonitor23 = KeyguardUpdateMonitor.this;
                        Objects.requireNonNull(keyguardUpdateMonitor23);
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor23.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback18 = (KeyguardUpdateMonitorCallback) keyguardUpdateMonitor23.mCallbacks.get(i).get();
                            if (keyguardUpdateMonitorCallback18 != null) {
                                keyguardUpdateMonitorCallback18.onRequireUnlockForNfc();
                            }
                            i++;
                        }
                        return;
                    default:
                        super.handleMessage(message);
                        return;
                }
            }
        };
        this.mHandler = r4;
        if (!this.mDeviceProvisioned) {
            this.mDeviceProvisionedObserver = new ContentObserver(r4) {
                public final void onChange(boolean z) {
                    super.onChange(z);
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mDeviceProvisioned = keyguardUpdateMonitor.isDeviceProvisionedInSettingsDb();
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                    if (keyguardUpdateMonitor2.mDeviceProvisioned) {
                        keyguardUpdateMonitor2.mHandler.sendEmptyMessage(308);
                    }
                    if (KeyguardUpdateMonitor.DEBUG) {
                        KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("DEVICE_PROVISIONED state = "), KeyguardUpdateMonitor.this.mDeviceProvisioned, "KeyguardUpdateMonitor");
                    }
                }
            };
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
            boolean isDeviceProvisionedInSettingsDb = isDeviceProvisionedInSettingsDb();
            if (isDeviceProvisionedInSettingsDb != this.mDeviceProvisioned) {
                this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb;
                if (isDeviceProvisionedInSettingsDb) {
                    r4.sendEmptyMessage(308);
                }
            }
        }
        this.mBatteryStatus = new BatteryStatus();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        broadcastDispatcher2.registerReceiverWithHandler(r8, intentFilter, r4);
        executor3.execute(new TaskView$$ExternalSyntheticLambda4(this, 2));
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter2.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        intentFilter2.addAction("com.android.facelock.FACE_UNLOCK_STARTED");
        intentFilter2.addAction("com.android.facelock.FACE_UNLOCK_STOPPED");
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC");
        broadcastDispatcher2.registerReceiverWithHandler(r10, intentFilter2, r4, UserHandle.ALL);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        try {
            ActivityManager.getService().registerUserSwitchObserver(r11, "KeyguardUpdateMonitor");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        TrustManager trustManager = (TrustManager) context.getSystemService(TrustManager.class);
        this.mTrustManager = trustManager;
        trustManager.registerTrustListener(this);
        setStrongAuthTracker(this.mStrongAuthTracker);
        this.mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            this.mFpm = (FingerprintManager) context.getSystemService("fingerprint");
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            FaceManager faceManager = (FaceManager) context.getSystemService("face");
            this.mFaceManager = faceManager;
            this.mFaceSensorProperties = faceManager.getSensorPropertiesInternal();
        }
        if (!(this.mFpm == null && this.mFaceManager == null)) {
            ((BiometricManager) context.getSystemService(BiometricManager.class)).registerEnabledOnKeyguardCallback(this.mBiometricEnabledCallback);
        }
        final Executor executor4 = executor2;
        this.mAuthController.addCallback(new AuthController.Callback() {
            public final void onAllAuthenticatorsRegistered() {
                executor4.execute(new WifiEntry$$ExternalSyntheticLambda0(this, 1));
            }

            public final void onEnrollmentsChanged() {
                executor4.execute(new BubbleStackView$$ExternalSyntheticLambda15(this, 2));
            }
        });
        updateBiometricListeningState(2);
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager != null) {
            fingerprintManager.addLockoutResetCallback(this.mFingerprintLockoutResetCallback);
        }
        FaceManager faceManager2 = this.mFaceManager;
        if (faceManager2 != null) {
            faceManager2.addLockoutResetCallback(this.mFaceLockoutResetCallback);
        }
        this.mIsAutomotive = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(this.mTaskStackListener);
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        this.mIsPrimaryUser = userManager.isPrimaryUser();
        int currentUser = ActivityManager.getCurrentUser();
        this.mUserIsUnlocked.put(currentUser, this.mUserManager.isUserUnlocked(currentUser));
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mDevicePolicyManager = devicePolicyManager;
        this.mLogoutEnabled = devicePolicyManager.isLogoutEnabled();
        updateSecondaryLockscreenRequirement(currentUser);
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            SparseBooleanArray sparseBooleanArray = this.mUserTrustIsUsuallyManaged;
            int i = userInfo.id;
            sparseBooleanArray.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        }
        if ((Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 0 ? false : z) && !this.mHandler.hasMessages(329)) {
            this.mHandler.sendEmptyMessage(329);
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        if (telephonyManager != null) {
            TelephonyListenerManager telephonyListenerManager2 = this.mTelephonyListenerManager;
            TelephonyCallback.ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener = this.mPhoneStateListener;
            Objects.requireNonNull(telephonyListenerManager2);
            com.android.systemui.telephony.TelephonyCallback telephonyCallback = telephonyListenerManager2.mTelephonyCallback;
            Objects.requireNonNull(telephonyCallback);
            telephonyCallback.mActiveDataSubscriptionIdListeners.add(activeDataSubscriptionIdListener);
            telephonyListenerManager2.updateListening();
            for (int i2 = 0; i2 < this.mTelephonyManager.getActiveModemCount(); i2++) {
                int simState = this.mTelephonyManager.getSimState(i2);
                int[] subscriptionIds = this.mSubscriptionManager.getSubscriptionIds(i2);
                if (subscriptionIds != null) {
                    for (int obtainMessage : subscriptionIds) {
                        this.mHandler.obtainMessage(304, obtainMessage, i2, Integer.valueOf(simState)).sendToTarget();
                    }
                }
            }
        }
        this.mTimeFormatChangeObserver = new ContentObserver(this.mHandler) {
            public final void onChange(boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                C054014 r2 = keyguardUpdateMonitor.mHandler;
                r2.sendMessage(r2.obtainMessage(344, Settings.System.getString(keyguardUpdateMonitor.mContext.getContentResolver(), "time_12_24")));
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), false, this.mTimeFormatChangeObserver, -1);
    }

    public static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public final ArrayList getFilteredSubscriptionInfo() {
        ArrayList subscriptionInfo = getSubscriptionInfo(false);
        if (subscriptionInfo.size() == 2) {
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) subscriptionInfo.get(0);
            SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) subscriptionInfo.get(1);
            if (subscriptionInfo2.getGroupUuid() == null || !subscriptionInfo2.getGroupUuid().equals(subscriptionInfo3.getGroupUuid()) || (!subscriptionInfo2.isOpportunistic() && !subscriptionInfo3.isOpportunistic())) {
                return subscriptionInfo;
            }
            if (CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean")) {
                if (!subscriptionInfo2.isOpportunistic()) {
                    subscriptionInfo2 = subscriptionInfo3;
                }
                subscriptionInfo.remove(subscriptionInfo2);
            } else {
                if (subscriptionInfo2.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                    subscriptionInfo2 = subscriptionInfo3;
                }
                subscriptionInfo.remove(subscriptionInfo2);
            }
        }
        return subscriptionInfo;
    }

    public final int getNextSubIdForState(int i) {
        ArrayList subscriptionInfo = getSubscriptionInfo(false);
        int i2 = -1;
        int i3 = Integer.MAX_VALUE;
        for (int i4 = 0; i4 < subscriptionInfo.size(); i4++) {
            int subscriptionId = ((SubscriptionInfo) subscriptionInfo.get(i4)).getSubscriptionId();
            int slotId = getSlotId(subscriptionId);
            if (i == getSimState(subscriptionId) && i3 > slotId) {
                i2 = subscriptionId;
                i3 = slotId;
            }
        }
        return i2;
    }

    public final SubscriptionInfo getSubscriptionInfoForSubId(int i) {
        ArrayList subscriptionInfo = getSubscriptionInfo(false);
        for (int i2 = 0; i2 < subscriptionInfo.size(); i2++) {
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) subscriptionInfo.get(i2);
            if (i == subscriptionInfo2.getSubscriptionId()) {
                return subscriptionInfo2;
            }
        }
        return null;
    }

    public final boolean isSimPinSecure() {
        boolean z;
        Iterator it = getSubscriptionInfo(false).iterator();
        while (it.hasNext()) {
            int simState = getSimState(((SubscriptionInfo) it.next()).getSubscriptionId());
            if (simState == 2 || simState == 3 || simState == 7) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    public void resetBiometricListeningState() {
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
    }

    @VisibleForTesting
    public static class BiometricAuthenticated {
        public final boolean mAuthenticated = true;
        public final boolean mIsStrongBiometric;

        public BiometricAuthenticated(boolean z) {
            this.mIsStrongBiometric = z;
        }
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
        DEBUG_FACE = z;
        DEBUG_FINGERPRINT = z;
        DEBUG_ACTIVE_UNLOCK = z;
        try {
            CORE_APPS_ONLY = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isOnlyCoreApps();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static synchronized int getCurrentUser() {
        int i;
        synchronized (KeyguardUpdateMonitor.class) {
            i = sCurrentUser;
        }
        return i;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        PrintWriter printWriter2 = printWriter;
        printWriter2.println("KeyguardUpdateMonitor state:");
        printWriter2.println("  SIM States:");
        for (SimData simData : this.mSimDatas.values()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    ");
            m.append(simData.toString());
            printWriter2.println(m.toString());
        }
        printWriter2.println("  Subs:");
        if (this.mSubscriptionInfo != null) {
            for (int i = 0; i < this.mSubscriptionInfo.size(); i++) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    ");
                m2.append(this.mSubscriptionInfo.get(i));
                printWriter2.println(m2.toString());
            }
        }
        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Current active data subId=");
        m3.append(this.mActiveMobileDataSubscription);
        printWriter2.println(m3.toString());
        printWriter2.println("  Service states:");
        for (Integer intValue : this.mServiceStates.keySet()) {
            int intValue2 = intValue.intValue();
            StringBuilder m4 = ExifInterface$$ExternalSyntheticOutline0.m13m("    ", intValue2, "=");
            m4.append(this.mServiceStates.get(Integer.valueOf(intValue2)));
            printWriter2.println(m4.toString());
        }
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            str = "    enabledByUser=";
        } else {
            int currentUser = ActivityManager.getCurrentUser();
            int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(currentUser);
            BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(currentUser);
            StringBuilder sb = new StringBuilder();
            String str2 = "    enabledByUser=";
            sb.append("  Fingerprint state (user=");
            sb.append(currentUser);
            sb.append(")");
            printWriter2.println(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("    areAllAuthenticatorsRegistered=");
            AuthController authController = this.mAuthController;
            Objects.requireNonNull(authController);
            StringBuilder m5 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb2, authController.mAllAuthenticatorsRegistered, printWriter2, "    allowed=");
            if (biometricAuthenticated == null || !isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) {
                z4 = false;
            } else {
                z4 = true;
            }
            StringBuilder m6 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(m5, z4, printWriter2, "    auth'd=");
            if (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated) {
                z5 = false;
            } else {
                z5 = true;
            }
            StringBuilder m7 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(m6, z5, printWriter2, "    authSinceBoot=");
            StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
            Objects.requireNonNull(strongAuthTracker);
            if ((strongAuthTracker.getStrongAuthForUser(getCurrentUser()) & 1) == 0) {
                z6 = true;
            } else {
                z6 = false;
            }
            StringBuilder m8 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(m7, z6, printWriter2, "    disabled(DPM)=");
            m8.append(isFingerprintDisabled(currentUser));
            printWriter2.println(m8.toString());
            printWriter2.println("    possible=" + isUnlockWithFingerprintPossible(currentUser));
            printWriter2.println("    listening: actual=" + this.mFingerprintRunningState + " expected=" + (shouldListenForFingerprint(isUdfpsEnrolled()) ? 1 : 0));
            StringBuilder sb3 = new StringBuilder();
            sb3.append("    strongAuthFlags=");
            sb3.append(Integer.toHexString(strongAuthForUser));
            printWriter2.println(sb3.toString());
            printWriter2.println("    trustManaged=" + getUserTrustIsManaged(currentUser));
            StringBuilder sb4 = new StringBuilder();
            sb4.append("    mFingerprintLockedOut=");
            str = str2;
            StringBuilder m9 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb4, this.mFingerprintLockedOut, printWriter2, "    mFingerprintLockedOutPermanent="), this.mFingerprintLockedOutPermanent, printWriter2, str);
            m9.append(this.mBiometricEnabledForUser.get(currentUser));
            printWriter2.println(m9.toString());
            if (isUdfpsSupported()) {
                StringBuilder m10 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("        udfpsEnrolled=");
                m10.append(isUdfpsEnrolled());
                printWriter2.println(m10.toString());
                printWriter2.println("        shouldListenForUdfps=" + shouldListenForFingerprint(true));
                StringBuilder sb5 = new StringBuilder();
                sb5.append("        bouncerVisible=");
                StringBuilder m11 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb5, this.mBouncer, printWriter2, "        mStatusBarState=");
                m11.append(R$id.toShortString(this.mStatusBarState));
                printWriter2.println(m11.toString());
            }
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null && faceManager.isHardwareDetected()) {
            int currentUser2 = ActivityManager.getCurrentUser();
            int strongAuthForUser2 = this.mStrongAuthTracker.getStrongAuthForUser(currentUser2);
            BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(currentUser2);
            StringBuilder sb6 = new StringBuilder();
            String str3 = str;
            sb6.append("  Face authentication state (user=");
            sb6.append(currentUser2);
            sb6.append(")");
            printWriter2.println(sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append("    allowed=");
            if (biometricAuthenticated2 == null || !isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric)) {
                z = false;
            } else {
                z = true;
            }
            StringBuilder m12 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb7, z, printWriter2, "    auth'd=");
            if (biometricAuthenticated2 == null || !biometricAuthenticated2.mAuthenticated) {
                z2 = false;
            } else {
                z2 = true;
            }
            StringBuilder m13 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(m12, z2, printWriter2, "    authSinceBoot=");
            StrongAuthTracker strongAuthTracker2 = this.mStrongAuthTracker;
            Objects.requireNonNull(strongAuthTracker2);
            boolean z7 = true;
            if ((strongAuthTracker2.getStrongAuthForUser(getCurrentUser()) & 1) == 0) {
                z3 = true;
            } else {
                z3 = false;
            }
            StringBuilder m14 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(m13, z3, printWriter2, "    disabled(DPM)=");
            m14.append(isFaceDisabled(currentUser2));
            printWriter2.println(m14.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("    possible=");
            boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardUpdateMonitor$$ExternalSyntheticLambda10(this, currentUser2))).booleanValue();
            this.mIsFaceEnrolled = booleanValue;
            if (!booleanValue || isFaceDisabled(currentUser2)) {
                z7 = false;
            }
            StringBuilder m15 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb8, z7, printWriter2, "    listening: actual=");
            m15.append(this.mFaceRunningState);
            m15.append(" expected=(");
            m15.append(shouldListenForFace() ? 1 : 0);
            printWriter2.println(m15.toString());
            printWriter2.println("    strongAuthFlags=" + Integer.toHexString(strongAuthForUser2));
            printWriter2.println("    trustManaged=" + getUserTrustIsManaged(currentUser2));
            StringBuilder sb9 = new StringBuilder();
            sb9.append("    mFaceLockedOutPermanent=");
            StringBuilder m16 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb9, this.mFaceLockedOutPermanent, printWriter2, str3);
            m16.append(this.mBiometricEnabledForUser.get(currentUser2));
            printWriter2.println(m16.toString());
            printWriter2.println("    mSecureCameraLaunched=" + this.mSecureCameraLaunched);
        }
        KeyguardListenQueue keyguardListenQueue = this.mListenModels;
        Objects.requireNonNull(keyguardListenQueue);
        KeyguardListenQueue$print$stringify$1 keyguardListenQueue$print$stringify$1 = new KeyguardListenQueue$print$stringify$1(KeyguardListenQueueKt.DEFAULT_FORMATTING);
        StringBuilder m17 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Face listen results (last ");
        ArrayDeque<KeyguardFaceListenModel> arrayDeque = keyguardListenQueue.faceQueue;
        Objects.requireNonNull(arrayDeque);
        m17.append(arrayDeque.size);
        m17.append(" calls):");
        printWriter2.println(m17.toString());
        Iterator<KeyguardFaceListenModel> it = keyguardListenQueue.faceQueue.iterator();
        while (it.hasNext()) {
            printWriter2.println((String) keyguardListenQueue$print$stringify$1.invoke(it.next()));
        }
        StringBuilder m18 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Fingerprint listen results (last ");
        ArrayDeque<KeyguardFingerprintListenModel> arrayDeque2 = keyguardListenQueue.fingerprintQueue;
        Objects.requireNonNull(arrayDeque2);
        m18.append(arrayDeque2.size);
        m18.append(" calls):");
        printWriter2.println(m18.toString());
        Iterator<KeyguardFingerprintListenModel> it2 = keyguardListenQueue.fingerprintQueue.iterator();
        while (it2.hasNext()) {
            printWriter2.println((String) keyguardListenQueue$print$stringify$1.invoke(it2.next()));
        }
        StringBuilder m19 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Active unlock triggers (last ");
        ArrayDeque<KeyguardActiveUnlockModel> arrayDeque3 = keyguardListenQueue.activeUnlockQueue;
        Objects.requireNonNull(arrayDeque3);
        m19.append(arrayDeque3.size);
        m19.append(" calls):");
        printWriter2.println(m19.toString());
        Iterator<KeyguardActiveUnlockModel> it3 = keyguardListenQueue.activeUnlockQueue.iterator();
        while (it3.hasNext()) {
            printWriter2.println((String) keyguardListenQueue$print$stringify$1.invoke(it3.next()));
        }
        if (this.mIsAutomotive) {
            printWriter2.println("  Running on Automotive build");
        }
    }

    public final int getSimState(int i) {
        if (this.mSimDatas.containsKey(Integer.valueOf(i))) {
            return this.mSimDatas.get(Integer.valueOf(i)).simState;
        }
        return 0;
    }

    public final int getSlotId(int i) {
        if (!this.mSimDatas.containsKey(Integer.valueOf(i))) {
            refreshSimState(i, SubscriptionManager.getSlotIndex(i));
        }
        return this.mSimDatas.get(Integer.valueOf(i)).slotId;
    }

    public final ArrayList getSubscriptionInfo(boolean z) {
        List<SubscriptionInfo> list = this.mSubscriptionInfo;
        if (list == null || z) {
            list = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        }
        if (list == null) {
            this.mSubscriptionInfo = new ArrayList();
        } else {
            this.mSubscriptionInfo = list;
        }
        return new ArrayList(this.mSubscriptionInfo);
    }

    public final boolean getUserTrustIsManaged(int i) {
        if (!this.mUserTrustIsManaged.get(i) || isSimPinSecure()) {
            return false;
        }
        return true;
    }

    public final boolean getUserUnlockedWithBiometric(int i) {
        boolean z;
        boolean z2;
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(i);
        if (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated || !isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) {
            z = false;
        } else {
            z = true;
        }
        if (biometricAuthenticated2 == null || !biometricAuthenticated2.mAuthenticated || !isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z || z2) {
            return true;
        }
        return false;
    }

    public final boolean getUserUnlockedWithBiometricAndIsBypassing(int i) {
        boolean z;
        boolean z2;
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(i);
        if (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated || !isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) {
            z = false;
        } else {
            z = true;
        }
        if (biometricAuthenticated2 == null || !biometricAuthenticated2.mAuthenticated || !isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z) {
            return true;
        }
        if (!z2 || !this.mKeyguardBypassController.canBypass()) {
            return false;
        }
        return true;
    }

    public final void handleFaceAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFaceAuthenticated");
        try {
            if (this.mGoingToSleep) {
                Log.d("KeyguardUpdateMonitor", "Aborted successful auth because device is going to sleep.");
                return;
            }
            int i2 = ActivityManager.getService().getCurrentUser().id;
            if (i2 != i) {
                Log.d("KeyguardUpdateMonitor", "Face authenticated for wrong user: " + i);
            } else if (isFaceDisabled(i2)) {
                Log.d("KeyguardUpdateMonitor", "Face authentication disabled by DPM for userId: " + i2);
                setFaceRunningState(0);
            } else {
                if (DEBUG_FACE) {
                    Log.d("KeyguardUpdateMonitor", "Face auth succeeded for user " + i2);
                }
                onFaceAuthenticated(i2, z);
                setFaceRunningState(0);
                Trace.endSection();
            }
        } catch (RemoteException e) {
            Log.e("KeyguardUpdateMonitor", "Failed to get current user id: ", e);
        } finally {
            setFaceRunningState(0);
        }
    }

    public final void handleFingerprintAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        try {
            int i2 = ActivityManager.getService().getCurrentUser().id;
            if (i2 != i) {
                Log.d("KeyguardUpdateMonitor", "Fingerprint authenticated for wrong user: " + i);
            } else if (isFingerprintDisabled(i2)) {
                Log.d("KeyguardUpdateMonitor", "Fingerprint disabled by DPM for userId: " + i2);
            } else {
                onFingerprintAuthenticated(i2, z);
                setFingerprintRunningState(0);
                Trace.endSection();
            }
        } catch (RemoteException e) {
            Log.e("KeyguardUpdateMonitor", "Failed to get current user id: ", e);
        } finally {
            setFingerprintRunningState(0);
        }
    }

    @VisibleForTesting
    public void handleServiceStateChange(int i, ServiceState serviceState) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleServiceStateChange(subId=" + i + ", serviceState=" + serviceState);
        }
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            Log.w("KeyguardUpdateMonitor", "invalid subId in handleServiceStateChange()");
            return;
        }
        updateTelephonyCapable(true);
        this.mServiceStates.put(Integer.valueOf(i), serviceState);
        callbacksRefreshCarrierInfo();
    }

    public final boolean isDeviceProvisionedInSettingsDb() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isEncryptedOrLockdown(int i) {
        boolean z;
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(i);
        if (containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32)) {
            z = true;
        } else {
            z = false;
        }
        if (containsFlag(strongAuthForUser, 1) || z) {
            return true;
        }
        return false;
    }

    public final boolean isFaceDisabled(int i) {
        return ((Boolean) DejankUtils.whitelistIpcs(new KeyguardUpdateMonitor$$ExternalSyntheticLambda11(this, (DevicePolicyManager) this.mContext.getSystemService("device_policy"), i))).booleanValue();
    }

    public final boolean isFingerprintDetectionRunning() {
        if (this.mFingerprintRunningState == 1) {
            return true;
        }
        return false;
    }

    public final boolean isFingerprintDisabled(int i) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if ((devicePolicyManager == null || (devicePolicyManager.getKeyguardDisabledFeatures((ComponentName) null, i) & 32) == 0) && !isSimPinSecure()) {
            return false;
        }
        return true;
    }

    public final boolean isUdfpsEnrolled() {
        return this.mAuthController.isUdfpsEnrolled(getCurrentUser());
    }

    public final boolean isUdfpsSupported() {
        AuthController authController = this.mAuthController;
        Objects.requireNonNull(authController);
        if (authController.mUdfpsProps != null) {
            AuthController authController2 = this.mAuthController;
            Objects.requireNonNull(authController2);
            if (!authController2.mUdfpsProps.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUnlockWithFingerprintPossible(int i) {
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected() || isFingerprintDisabled(i) || !this.mFpm.hasEnrolledTemplates(i)) {
            return false;
        }
        return true;
    }

    public final boolean isUnlockingWithBiometricAllowed(boolean z) {
        StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
        Objects.requireNonNull(strongAuthTracker);
        return strongAuthTracker.isBiometricAllowedForUser(z, getCurrentUser());
    }

    public final void maybeLogListenerModelData(KeyguardListenModel keyguardListenModel) {
        boolean z;
        if (!DEBUG_ACTIVE_UNLOCK || !(keyguardListenModel instanceof KeyguardActiveUnlockModel) || !keyguardListenModel.getListening()) {
            boolean z2 = DEBUG_FACE;
            boolean z3 = false;
            if ((!z2 || !(keyguardListenModel instanceof KeyguardFaceListenModel) || this.mFaceRunningState == 1) && (!DEBUG_FINGERPRINT || !(keyguardListenModel instanceof KeyguardFingerprintListenModel) || this.mFingerprintRunningState == 1)) {
                z = false;
            } else {
                z = true;
            }
            if ((z2 && (keyguardListenModel instanceof KeyguardFaceListenModel) && this.mFaceRunningState == 1) || (DEBUG_FINGERPRINT && (keyguardListenModel instanceof KeyguardFingerprintListenModel) && this.mFingerprintRunningState == 1)) {
                z3 = true;
            }
            if ((z && keyguardListenModel.getListening()) || (z3 && !keyguardListenModel.getListening())) {
                this.mListenModels.add(keyguardListenModel);
                return;
            }
            return;
        }
        this.mListenModels.add(keyguardListenModel);
    }

    @VisibleForTesting
    public void onFaceAuthenticated(final int i, final boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#onFaceAuthenticated");
        Assert.isMainThread();
        this.mUserFaceAuthenticated.put(i, new BiometricAuthenticated(z));
        if (getUserCanSkipBouncer(i)) {
            this.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FACE);
        }
        this.mFaceCancelSignal = null;
        updateBiometricListeningState(2);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FACE, z);
            }
        }
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new Runnable() {
            public final void run() {
                KeyguardUpdateMonitor.this.mLockPatternUtils.reportSuccessfulBiometricUnlock(r7, r6);
            }
        });
        Trace.endSection();
    }

    public final boolean refreshSimState(int i, int i2) {
        int i3;
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        boolean z = false;
        if (telephonyManager != null) {
            i3 = telephonyManager.getSimState(i2);
        } else {
            i3 = 0;
        }
        SimData simData = this.mSimDatas.get(Integer.valueOf(i));
        if (simData == null) {
            this.mSimDatas.put(Integer.valueOf(i), new SimData(i3, i2, i));
            return true;
        }
        if (simData.simState != i3) {
            z = true;
        }
        simData.simState = i3;
        return z;
    }

    public final void reportSimUnlocked(int i) {
        Log.v("KeyguardUpdateMonitor", "reportSimUnlocked(subId=" + i + ")");
        handleSimStateChange(i, getSlotId(i), 5);
    }

    public final void requestActiveUnlock() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (!this.mHandler.hasMessages(336)) {
            boolean z7 = false;
            if (!this.mAssistantVisible || !this.mKeyguardOccluded || this.mUserHasTrust.get(getCurrentUser(), false)) {
                z = false;
            } else {
                z = true;
            }
            if (!this.mKeyguardIsVisible || !this.mDeviceInteractive || this.mGoingToSleep || this.mStatusBarState == 2) {
                z2 = false;
            } else {
                z2 = true;
            }
            int currentUser = getCurrentUser();
            if (getUserCanSkipBouncer(currentUser) || !this.mLockPatternUtils.isSecure(currentUser)) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (this.mFingerprintLockedOut || this.mFingerprintLockedOutPermanent) {
                z4 = true;
            } else {
                z4 = false;
            }
            int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(currentUser);
            if (containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32)) {
                z5 = true;
            } else {
                z5 = false;
            }
            if (containsFlag(strongAuthForUser, 1) || containsFlag(strongAuthForUser, 16)) {
                z6 = true;
            } else {
                z6 = false;
            }
            if ((this.mAuthInterruptActive || z || z2) && !this.mSwitchingUser && !z3 && !z4 && !z5 && !z6 && !this.mKeyguardGoingAway && !this.mSecureCameraLaunched) {
                z7 = true;
            }
            if (DEBUG_ACTIVE_UNLOCK) {
                maybeLogListenerModelData(new KeyguardActiveUnlockModel(System.currentTimeMillis(), currentUser, z7, this.mAuthInterruptActive, z6, z4, z5, this.mSwitchingUser, z, z3));
            }
            if (z7) {
                this.mTrustManager.reportUserRequestedUnlock(getCurrentUser());
            }
        }
    }

    public final void requestFaceAuth(boolean z) {
        if (DEBUG) {
            ViewCompat$$ExternalSyntheticLambda0.m12m("requestFaceAuth() userInitiated=", z, "KeyguardUpdateMonitor");
        }
        updateFaceListeningState(2);
    }

    @VisibleForTesting
    public void setAssistantVisible(boolean z) {
        this.mAssistantVisible = z;
        updateBiometricListeningState(2);
        if (this.mAssistantVisible) {
            requestActiveUnlock();
        }
    }

    public final void setFaceRunningState(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.mFaceRunningState == 1) {
            z = true;
        } else {
            z = false;
        }
        if (i == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mFaceRunningState = i;
        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("faceRunningState: "), this.mFaceRunningState, "KeyguardUpdateMonitor");
        if (z != z2) {
            Assert.isMainThread();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
                if (keyguardUpdateMonitorCallback != null) {
                    if (this.mFaceRunningState == 1) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(z3, BiometricSourceType.FACE);
                }
            }
        }
    }

    public final void setFingerprintRunningState(int i) {
        boolean z;
        boolean z2 = true;
        if (this.mFingerprintRunningState == 1) {
            z = true;
        } else {
            z = false;
        }
        if (i != 1) {
            z2 = false;
        }
        this.mFingerprintRunningState = i;
        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("fingerprintRunningState: "), this.mFingerprintRunningState, "KeyguardUpdateMonitor");
        if (z != z2) {
            Assert.isMainThread();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(isFingerprintDetectionRunning(), BiometricSourceType.FINGERPRINT);
                }
            }
        }
    }

    @VisibleForTesting
    public void setStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        StrongAuthTracker strongAuthTracker2 = this.mStrongAuthTracker;
        if (strongAuthTracker2 != null) {
            this.mLockPatternUtils.unregisterStrongAuthTracker(strongAuthTracker2);
        }
        this.mStrongAuthTracker = strongAuthTracker;
        this.mLockPatternUtils.registerStrongAuthTracker(strongAuthTracker);
    }

    public final boolean shouldListenForFace() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13 = false;
        if (this.mFaceManager == null) {
            return false;
        }
        if (this.mStatusBarState == 2) {
            z = true;
        } else {
            z = false;
        }
        if (!this.mKeyguardIsVisible || !this.mDeviceInteractive || this.mGoingToSleep || z) {
            z2 = false;
        } else {
            z2 = true;
        }
        int currentUser = getCurrentUser();
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(currentUser);
        if (containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32)) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (containsFlag(strongAuthForUser, 1) || containsFlag(strongAuthForUser, 16)) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (this.mFingerprintLockedOut || this.mFingerprintLockedOutPermanent) {
            z5 = true;
        } else {
            z5 = false;
        }
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (keyguardBypassController == null || !keyguardBypassController.canBypass()) {
            z6 = false;
        } else {
            z6 = true;
        }
        if (!getUserCanSkipBouncer(currentUser) || z6) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (!z4 || (z6 && !this.mBouncer)) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (this.mFaceSensorProperties.isEmpty() || !this.mFaceSensorProperties.get(0).supportsFaceDetection) {
            z9 = false;
        } else {
            z9 = true;
        }
        if (!z3 || z9) {
            z10 = z8;
        } else {
            z10 = false;
        }
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(getCurrentUser());
        if (biometricAuthenticated != null) {
            z11 = biometricAuthenticated.mAuthenticated;
        } else {
            z11 = false;
        }
        boolean isFaceDisabled = isFaceDisabled(currentUser);
        boolean z14 = this.mBiometricEnabledForUser.get(currentUser);
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(getCurrentUser());
        if (!this.mAssistantVisible || !this.mKeyguardOccluded || ((biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated) || this.mUserHasTrust.get(getCurrentUser(), false))) {
            z12 = false;
        } else {
            z12 = true;
        }
        if ((this.mBouncer || this.mAuthInterruptActive || this.mOccludingAppRequestingFace || z2 || z12) && !this.mSwitchingUser && !isFaceDisabled && z7 && !this.mKeyguardGoingAway && z14 && z10 && this.mIsPrimaryUser && ((!this.mSecureCameraLaunched || this.mOccludingAppRequestingFace) && !z11 && !z5)) {
            z13 = true;
        }
        if (DEBUG_FACE) {
            maybeLogListenerModelData(new KeyguardFaceListenModel(System.currentTimeMillis(), currentUser, z13, this.mAuthInterruptActive, z7, z14, this.mBouncer, z11, isFaceDisabled, z2, this.mKeyguardGoingAway, z12, false, this.mOccludingAppRequestingFace, this.mIsPrimaryUser, z10, this.mSecureCameraLaunched, this.mSwitchingUser));
        }
        return z13;
    }

    @VisibleForTesting
    public boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        int currentUser = getCurrentUser();
        boolean z9 = !getUserHasTrust(currentUser);
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(getCurrentUser());
        if (!this.mAssistantVisible || !this.mKeyguardOccluded || ((biometricAuthenticated != null && biometricAuthenticated.mAuthenticated) || this.mUserHasTrust.get(getCurrentUser(), false))) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (this.mKeyguardIsVisible || !this.mDeviceInteractive || ((this.mBouncer && !this.mKeyguardGoingAway) || this.mGoingToSleep || z2 || (((z8 = this.mKeyguardOccluded) && this.mIsDreaming) || (z8 && z9 && (this.mOccludingAppRequestingFp || z))))) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z10 = this.mBiometricEnabledForUser.get(currentUser);
        boolean userCanSkipBouncer = getUserCanSkipBouncer(currentUser);
        boolean isFingerprintDisabled = isFingerprintDisabled(currentUser);
        if (this.mSwitchingUser || isFingerprintDisabled || ((this.mKeyguardGoingAway && this.mDeviceInteractive) || !this.mIsPrimaryUser || !z10)) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (!this.mFingerprintLockedOut || !this.mBouncer || !this.mCredentialAttempted) {
            z5 = true;
        } else {
            z5 = false;
        }
        boolean isEncryptedOrLockdown = isEncryptedOrLockdown(currentUser);
        if (!z || (!userCanSkipBouncer && !isEncryptedOrLockdown && z9 && !this.mFingerprintLockedOut)) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (!z3 || !z4 || !z5 || !z6) {
            z7 = false;
        } else {
            z7 = true;
        }
        if (DEBUG_FINGERPRINT) {
            KeyguardFingerprintListenModel keyguardFingerprintListenModel = r1;
            KeyguardFingerprintListenModel keyguardFingerprintListenModel2 = new KeyguardFingerprintListenModel(System.currentTimeMillis(), currentUser, z7, z10, this.mBouncer, userCanSkipBouncer, this.mCredentialAttempted, this.mDeviceInteractive, this.mIsDreaming, isEncryptedOrLockdown, isFingerprintDisabled, this.mFingerprintLockedOut, this.mGoingToSleep, this.mKeyguardGoingAway, this.mKeyguardIsVisible, this.mKeyguardOccluded, this.mOccludingAppRequestingFp, this.mIsPrimaryUser, z2, this.mSwitchingUser, z, z9);
            maybeLogListenerModelData(keyguardFingerprintListenModel);
        }
        return z7;
    }

    public final void stopListeningForFace() {
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "stopListeningForFace()");
        }
        if (this.mFaceRunningState == 1) {
            CancellationSignal cancellationSignal = this.mFaceCancelSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mFaceCancelSignal = null;
                this.mHandler.removeCallbacks(this.mFaceCancelNotReceived);
                this.mHandler.postDelayed(this.mFaceCancelNotReceived, 3000);
            }
            setFaceRunningState(2);
        }
        if (this.mFaceRunningState == 3) {
            setFaceRunningState(2);
        }
    }

    public final void updateFaceListeningState(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        if (!this.mHandler.hasMessages(336)) {
            this.mHandler.removeCallbacks(this.mRetryFaceAuthentication);
            boolean shouldListenForFace = shouldListenForFace();
            int i2 = this.mFaceRunningState;
            if (i2 != 1 || shouldListenForFace) {
                if (i2 != 1 && shouldListenForFace) {
                    if (i == 1) {
                        Log.v("KeyguardUpdateMonitor", "Ignoring startListeningForFace()");
                        return;
                    }
                    int currentUser = getCurrentUser();
                    boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardUpdateMonitor$$ExternalSyntheticLambda10(this, currentUser))).booleanValue();
                    this.mIsFaceEnrolled = booleanValue;
                    if (!booleanValue || isFaceDisabled(currentUser)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (this.mFaceCancelSignal != null) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cancellation signal is not null, high chance of bug in face auth lifecycle management. Face state: ");
                        m.append(this.mFaceRunningState);
                        m.append(", unlockPossible: ");
                        m.append(z);
                        Log.e("KeyguardUpdateMonitor", m.toString());
                    }
                    int i3 = this.mFaceRunningState;
                    if (i3 == 2) {
                        setFaceRunningState(3);
                    } else if (i3 != 3) {
                        if (DEBUG) {
                            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("startListeningForFace(): ");
                            m2.append(this.mFaceRunningState);
                            Log.v("KeyguardUpdateMonitor", m2.toString());
                        }
                        if (z) {
                            this.mFaceCancelSignal = new CancellationSignal();
                            if (this.mFaceSensorProperties.isEmpty() || !this.mFaceSensorProperties.get(0).supportsFaceDetection) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            this.mFaceAuthUserId = currentUser;
                            if (!isEncryptedOrLockdown(currentUser) || !z2) {
                                KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
                                if (keyguardBypassController == null || !keyguardBypassController.getBypassEnabled()) {
                                    z3 = false;
                                } else {
                                    z3 = true;
                                }
                                this.mFaceManager.authenticate((CryptoObject) null, this.mFaceCancelSignal, this.mFaceAuthenticationCallback, (Handler) null, currentUser, z3);
                            } else {
                                this.mFaceManager.detectFace(this.mFaceCancelSignal, this.mFaceDetectionCallback, currentUser);
                            }
                            setFaceRunningState(1);
                        }
                    }
                }
            } else if (i == 0) {
                Log.v("KeyguardUpdateMonitor", "Ignoring stopListeningForFace()");
            } else {
                stopListeningForFace();
            }
        }
    }

    public final void updateFingerprintListeningState(int i) {
        boolean z;
        if (!this.mHandler.hasMessages(336)) {
            AuthController authController = this.mAuthController;
            Objects.requireNonNull(authController);
            if (authController.mAllAuthenticatorsRegistered) {
                boolean shouldListenForFingerprint = shouldListenForFingerprint(isUdfpsSupported());
                int i2 = this.mFingerprintRunningState;
                if (i2 == 1 || i2 == 3) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z || shouldListenForFingerprint) {
                    if (!z && shouldListenForFingerprint) {
                        if (i == 1) {
                            Log.v("KeyguardUpdateMonitor", "Ignoring startListeningForFingerprint()");
                            return;
                        }
                        int currentUser = getCurrentUser();
                        boolean isUnlockWithFingerprintPossible = isUnlockWithFingerprintPossible(currentUser);
                        if (this.mFingerprintCancelSignal != null) {
                            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cancellation signal is not null, high chance of bug in fp auth lifecycle management. FP state: ");
                            m.append(this.mFingerprintRunningState);
                            m.append(", unlockPossible: ");
                            m.append(isUnlockWithFingerprintPossible);
                            Log.e("KeyguardUpdateMonitor", m.toString());
                        }
                        int i3 = this.mFingerprintRunningState;
                        if (i3 == 2) {
                            setFingerprintRunningState(3);
                        } else if (i3 != 3) {
                            if (DEBUG) {
                                Log.v("KeyguardUpdateMonitor", "startListeningForFingerprint()");
                            }
                            if (isUnlockWithFingerprintPossible) {
                                this.mFingerprintCancelSignal = new CancellationSignal();
                                if (isEncryptedOrLockdown(currentUser)) {
                                    this.mFpm.detectFingerprint(this.mFingerprintCancelSignal, this.mFingerprintDetectionCallback, currentUser);
                                } else {
                                    this.mFpm.authenticate((FingerprintManager.CryptoObject) null, this.mFingerprintCancelSignal, this.mFingerprintAuthenticationCallback, (Handler) null, -1, currentUser, 0);
                                }
                                setFingerprintRunningState(1);
                            }
                        }
                    }
                } else if (i == 0) {
                    Log.v("KeyguardUpdateMonitor", "Ignoring stopListeningForFingerprint()");
                } else {
                    if (DEBUG) {
                        Log.v("KeyguardUpdateMonitor", "stopListeningForFingerprint()");
                    }
                    if (this.mFingerprintRunningState == 1) {
                        CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
                        if (cancellationSignal != null) {
                            cancellationSignal.cancel();
                            this.mFingerprintCancelSignal = null;
                            this.mHandler.removeCallbacks(this.mFpCancelNotReceived);
                            this.mHandler.postDelayed(this.mFpCancelNotReceived, 3000);
                        }
                        setFingerprintRunningState(2);
                    }
                    if (this.mFingerprintRunningState == 3) {
                        setFingerprintRunningState(2);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x007a A[LOOP:0: B:17:0x007a->B:22:0x0095, LOOP_START, PHI: r2 
      PHI: (r2v2 int) = (r2v1 int), (r2v3 int) binds: [B:16:0x0078, B:22:0x0095] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateSecondaryLockscreenRequirement(int r5) {
        /*
            r4 = this;
            java.util.HashMap r0 = r4.mSecondaryLockscreenRequirement
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.Object r0 = r0.get(r1)
            android.content.Intent r0 = (android.content.Intent) r0
            android.app.admin.DevicePolicyManager r1 = r4.mDevicePolicyManager
            android.os.UserHandle r2 = android.os.UserHandle.of(r5)
            boolean r1 = r1.isSecondaryLockscreenEnabled(r2)
            r2 = 0
            if (r1 == 0) goto L_0x0067
            if (r0 != 0) goto L_0x0067
            android.app.admin.DevicePolicyManager r0 = r4.mDevicePolicyManager
            android.os.UserHandle r1 = android.os.UserHandle.of(r5)
            android.content.ComponentName r0 = r0.getProfileOwnerOrDeviceOwnerSupervisionComponent(r1)
            if (r0 != 0) goto L_0x002f
            java.lang.String r0 = "No Profile Owner or Device Owner supervision app found for User "
            java.lang.String r1 = "KeyguardUpdateMonitor"
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m(r0, r5, r1)
            goto L_0x0077
        L_0x002f:
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r3 = "android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE"
            r1.<init>(r3)
            java.lang.String r0 = r0.getPackageName()
            android.content.Intent r0 = r1.setPackage(r0)
            android.content.Context r1 = r4.mContext
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            android.content.pm.ResolveInfo r0 = r1.resolveService(r0, r2)
            if (r0 == 0) goto L_0x0077
            android.content.pm.ServiceInfo r1 = r0.serviceInfo
            if (r1 == 0) goto L_0x0077
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            android.content.pm.ServiceInfo r0 = r0.serviceInfo
            android.content.ComponentName r0 = r0.getComponentName()
            android.content.Intent r0 = r1.setComponent(r0)
            java.util.HashMap r1 = r4.mSecondaryLockscreenRequirement
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
            r1.put(r3, r0)
            goto L_0x0075
        L_0x0067:
            if (r1 != 0) goto L_0x0077
            if (r0 == 0) goto L_0x0077
            java.util.HashMap r0 = r4.mSecondaryLockscreenRequirement
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            r3 = 0
            r0.put(r1, r3)
        L_0x0075:
            r0 = 1
            goto L_0x0078
        L_0x0077:
            r0 = r2
        L_0x0078:
            if (r0 == 0) goto L_0x0098
        L_0x007a:
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r0 = r4.mCallbacks
            int r0 = r0.size()
            if (r2 >= r0) goto L_0x0098
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r0 = r4.mCallbacks
            java.lang.Object r0 = r0.get(r2)
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
            java.lang.Object r0 = r0.get()
            com.android.keyguard.KeyguardUpdateMonitorCallback r0 = (com.android.keyguard.KeyguardUpdateMonitorCallback) r0
            if (r0 == 0) goto L_0x0095
            r0.onSecondaryLockscreenRequirementChanged(r5)
        L_0x0095:
            int r2 = r2 + 1
            goto L_0x007a
        L_0x0098:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.updateSecondaryLockscreenRequirement(int):void");
    }

    public final void callbacksRefreshCarrierInfo() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onRefreshCarrierInfo();
            }
        }
    }

    public final void clearBiometricRecognized(int i) {
        Assert.isMainThread();
        this.mUserFingerprintAuthenticated.clear();
        this.mUserFaceAuthenticated.clear();
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FINGERPRINT, i);
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FACE, i);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricsCleared();
            }
        }
    }

    public final boolean getUserCanSkipBouncer(int i) {
        if (getUserHasTrust(i) || getUserUnlockedWithBiometric(i)) {
            return true;
        }
        return false;
    }

    public final boolean getUserHasTrust(int i) {
        if (isSimPinSecure() || !this.mUserHasTrust.get(i)) {
            return false;
        }
        return true;
    }

    public final void handleFaceHelp(int i, String str) {
        Assert.isMainThread();
        if (DEBUG_FACE) {
            DialogFragment$$ExternalSyntheticOutline0.m17m("Face help received: ", str, "KeyguardUpdateMonitor");
        }
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FACE);
            }
        }
    }

    public final void handleFingerprintHelp(int i, String str) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FINGERPRINT);
            }
        }
    }

    public final void handleReportEmergencyCallAction() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onEmergencyCallAction();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a8  */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleSimStateChange(int r7, int r8, int r9) {
        /*
            r6 = this;
            com.android.systemui.util.Assert.isMainThread()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "handleSimStateChange(subId="
            r0.append(r1)
            r0.append(r7)
            java.lang.String r1 = ", slotId="
            r0.append(r1)
            r0.append(r8)
            java.lang.String r1 = ", state="
            r0.append(r1)
            r0.append(r9)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "KeyguardUpdateMonitor"
            android.util.Log.d(r1, r0)
            boolean r0 = android.telephony.SubscriptionManager.isValidSubscriptionId(r7)
            r2 = 0
            r3 = 1
            if (r0 != 0) goto L_0x0068
            java.lang.String r0 = "invalid subId in handleSimStateChange()"
            android.util.Log.w(r1, r0)
            if (r9 != r3) goto L_0x005f
            r6.updateTelephonyCapable(r3)
            java.util.HashMap<java.lang.Integer, com.android.keyguard.KeyguardUpdateMonitor$SimData> r0 = r6.mSimDatas
            java.util.Collection r0 = r0.values()
            java.util.Iterator r0 = r0.iterator()
        L_0x004a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005d
            java.lang.Object r1 = r0.next()
            com.android.keyguard.KeyguardUpdateMonitor$SimData r1 = (com.android.keyguard.KeyguardUpdateMonitor.SimData) r1
            int r4 = r1.slotId
            if (r4 != r8) goto L_0x004a
            r1.simState = r3
            goto L_0x004a
        L_0x005d:
            r0 = r3
            goto L_0x0069
        L_0x005f:
            r0 = 8
            if (r9 != r0) goto L_0x0067
            r6.updateTelephonyCapable(r3)
            goto L_0x0068
        L_0x0067:
            return
        L_0x0068:
            r0 = r2
        L_0x0069:
            java.util.HashMap<java.lang.Integer, com.android.keyguard.KeyguardUpdateMonitor$SimData> r1 = r6.mSimDatas
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            java.lang.Object r1 = r1.get(r4)
            com.android.keyguard.KeyguardUpdateMonitor$SimData r1 = (com.android.keyguard.KeyguardUpdateMonitor.SimData) r1
            if (r1 != 0) goto L_0x0086
            com.android.keyguard.KeyguardUpdateMonitor$SimData r1 = new com.android.keyguard.KeyguardUpdateMonitor$SimData
            r1.<init>(r9, r8, r7)
            java.util.HashMap<java.lang.Integer, com.android.keyguard.KeyguardUpdateMonitor$SimData> r4 = r6.mSimDatas
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r4.put(r5, r1)
            goto L_0x009a
        L_0x0086:
            int r4 = r1.simState
            if (r4 != r9) goto L_0x0094
            int r4 = r1.subId
            if (r4 != r7) goto L_0x0094
            int r4 = r1.slotId
            if (r4 == r8) goto L_0x0093
            goto L_0x0094
        L_0x0093:
            r3 = r2
        L_0x0094:
            r1.simState = r9
            r1.subId = r7
            r1.slotId = r8
        L_0x009a:
            if (r3 != 0) goto L_0x009e
            if (r0 == 0) goto L_0x00be
        L_0x009e:
            if (r9 == 0) goto L_0x00be
        L_0x00a0:
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r0 = r6.mCallbacks
            int r0 = r0.size()
            if (r2 >= r0) goto L_0x00be
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.keyguard.KeyguardUpdateMonitorCallback>> r0 = r6.mCallbacks
            java.lang.Object r0 = r0.get(r2)
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
            java.lang.Object r0 = r0.get()
            com.android.keyguard.KeyguardUpdateMonitorCallback r0 = (com.android.keyguard.KeyguardUpdateMonitorCallback) r0
            if (r0 == 0) goto L_0x00bb
            r0.onSimStateChanged(r7, r8, r9)
        L_0x00bb:
            int r2 = r2 + 1
            goto L_0x00a0
        L_0x00be:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.handleSimStateChange(int, int, int):void");
    }

    @VisibleForTesting
    public void handleUserRemoved(int i) {
        Assert.isMainThread();
        this.mUserIsUnlocked.delete(i);
        this.mUserTrustIsUsuallyManaged.delete(i);
    }

    @VisibleForTesting
    public void handleUserSwitchComplete(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitchComplete(i);
            }
        }
        this.mInteractionJankMonitor.end(37);
        this.mLatencyTracker.onActionEnd(12);
    }

    @VisibleForTesting
    public void handleUserSwitching(int i, IRemoteCallback iRemoteCallback) {
        Assert.isMainThread();
        clearBiometricRecognized(-10000);
        this.mUserTrustIsUsuallyManaged.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitching(i);
            }
        }
        try {
            iRemoteCallback.sendResult((Bundle) null);
        } catch (RemoteException unused) {
        }
    }

    public final void notifyLockedOutStateChanged(BiometricSourceType biometricSourceType) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onLockedOutStateChanged(biometricSourceType);
            }
        }
    }

    @VisibleForTesting
    public void onFingerprintAuthenticated(final int i, final boolean z) {
        Assert.isMainThread();
        Trace.beginSection("KeyGuardUpdateMonitor#onFingerPrintAuthenticated");
        this.mUserFingerprintAuthenticated.put(i, new BiometricAuthenticated(z));
        if (getUserCanSkipBouncer(i)) {
            this.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FINGERPRINT);
        }
        this.mFingerprintCancelSignal = null;
        updateBiometricListeningState(2);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FINGERPRINT, z);
            }
        }
        C054014 r1 = this.mHandler;
        r1.sendMessageDelayed(r1.obtainMessage(336), 500);
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new Runnable() {
            public final void run() {
                KeyguardUpdateMonitor.this.mLockPatternUtils.reportSuccessfulBiometricUnlock(z, i);
            }
        });
        Trace.endSection();
    }

    public final void onTrustChanged(boolean z, int i, int i2, List<String> list) {
        Assert.isMainThread();
        boolean z2 = this.mUserHasTrust.get(i, false);
        this.mUserHasTrust.put(i, z);
        if (z2 == z) {
            updateBiometricListeningState(1);
        } else if (!z) {
            updateBiometricListeningState(0);
        }
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i3).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustChanged(i);
                if (z && i2 != 0) {
                    keyguardUpdateMonitorCallback.onTrustGrantedWithFlags(i2, i);
                }
            }
        }
        if (getCurrentUser() == i && getUserHasTrust(i)) {
            CharSequence charSequence = null;
            if (list != null && list.size() > 0) {
                charSequence = list.get(0);
            }
            for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i4).get();
                if (keyguardUpdateMonitorCallback2 != null) {
                    keyguardUpdateMonitorCallback2.showTrustGrantedMessage(charSequence);
                }
            }
        }
    }

    public final void onTrustError(CharSequence charSequence) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustAgentErrorMessage(charSequence);
            }
        }
    }

    public final void onTrustManagedChanged(boolean z, int i) {
        Assert.isMainThread();
        this.mUserTrustIsManaged.put(i, z);
        this.mUserTrustIsUsuallyManaged.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustManagedChanged();
            }
        }
    }

    public final void registerCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "*** register callback for " + keyguardUpdateMonitorCallback);
        }
        int i = 0;
        while (i < this.mCallbacks.size()) {
            if (this.mCallbacks.get(i).get() != keyguardUpdateMonitorCallback) {
                i++;
            } else if (DEBUG) {
                Log.e("KeyguardUpdateMonitor", "Object tried to add another callback", new Exception("Called by"));
                return;
            } else {
                return;
            }
        }
        this.mCallbacks.add(new WeakReference(keyguardUpdateMonitorCallback));
        removeCallback((KeyguardUpdateMonitorCallback) null);
        keyguardUpdateMonitorCallback.onRefreshBatteryInfo(this.mBatteryStatus);
        keyguardUpdateMonitorCallback.onTimeChanged();
        keyguardUpdateMonitorCallback.onPhoneStateChanged();
        keyguardUpdateMonitorCallback.onRefreshCarrierInfo();
        keyguardUpdateMonitorCallback.onClockVisibilityChanged();
        keyguardUpdateMonitorCallback.onKeyguardOccludedChanged(this.mKeyguardOccluded);
        boolean z = this.mKeyguardIsVisible;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (z != keyguardUpdateMonitorCallback.mShowing || elapsedRealtime - keyguardUpdateMonitorCallback.mVisibilityChangedCalled >= 1000) {
            keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(z);
            keyguardUpdateMonitorCallback.mVisibilityChangedCalled = elapsedRealtime;
            keyguardUpdateMonitorCallback.mShowing = z;
        }
        keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
        for (Map.Entry<Integer, SimData> value : this.mSimDatas.entrySet()) {
            SimData simData = (SimData) value.getValue();
            keyguardUpdateMonitorCallback.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
        }
    }

    public final void removeCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "*** unregister callback for " + keyguardUpdateMonitorCallback);
        }
        this.mCallbacks.removeIf(new KeyguardUpdateMonitor$$ExternalSyntheticLambda9(keyguardUpdateMonitorCallback));
    }

    public final boolean resolveNeedsSlowUnlockTransition() {
        if (this.mUserIsUnlocked.get(getCurrentUser())) {
            return false;
        }
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0, getCurrentUser());
        if (resolveActivityAsUser != null) {
            return FALLBACK_HOME_COMPONENT.equals(resolveActivityAsUser.getComponentInfo().getComponentName());
        }
        Log.w("KeyguardUpdateMonitor", "resolveNeedsSlowUnlockTransition: returning false since activity could not be resolved.");
        return false;
    }

    public final void updateBiometricListeningState(int i) {
        updateFingerprintListeningState(i);
        updateFaceListeningState(i);
    }

    @VisibleForTesting
    public void updateTelephonyCapable(boolean z) {
        Assert.isMainThread();
        if (z != this.mTelephonyCapable) {
            this.mTelephonyCapable = z;
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) this.mCallbacks.get(i).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
                }
            }
        }
    }
}
