package com.android.systemui.statusbar;

import android.animation.AnimatorSet;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18;
import com.android.settingslib.Utils;
import com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0;
import com.android.systemui.DejankUtils;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.google.android.systemui.elmyra.actions.Action$$ExternalSyntheticLambda0;
import java.text.NumberFormat;
import java.util.Objects;

public class KeyguardIndicationController {
    public String mAlignmentIndication;
    public final DelayableExecutor mBackgroundExecutor;
    public final IBatteryStats mBatteryInfo;
    public int mBatteryLevel;
    public boolean mBatteryOverheated;
    public boolean mBatteryPresent = true;
    public CharSequence mBiometricMessage;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public C11433 mBroadcastReceiver;
    public int mChargingSpeed;
    public long mChargingTimeRemaining;
    public int mChargingWattage;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DockManager mDockManager;
    public boolean mDozing;
    public boolean mEnableBatteryDefender;
    public final DelayableExecutor mExecutor;
    public final FalsingManager mFalsingManager;
    public final C11455 mHandler;
    public final IActivityManager mIActivityManager;
    public ViewGroup mIndicationArea;
    public boolean mInited;
    public ColorStateList mInitialTextColorState;
    public final KeyguardBypassController mKeyguardBypassController;
    public C11477 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public KeyguardIndicationTextView mLockScreenIndicationView;
    public String mMessageToShowOnScreenOn;
    public boolean mOrganizationOwnedDevice;
    public boolean mPowerCharged;
    public boolean mPowerPluggedIn;
    public boolean mPowerPluggedInDock;
    public boolean mPowerPluggedInWired;
    public boolean mPowerPluggedInWireless;
    public KeyguardIndicationRotateTextViewController mRotateTextViewController;
    public ScreenLifecycle mScreenLifecycle;
    public final C11422 mScreenObserver;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public C11466 mStatusBarStateListener;
    public final C11444 mTickReceiver;
    public KeyguardIndicationTextView mTopIndicationView;
    public CharSequence mTransientIndication;
    public CharSequence mTrustGrantedIndication;
    public BaseKeyguardCallback mUpdateMonitorCallback;
    public final UserManager mUserManager;
    public boolean mVisible;
    public final SettableWakeLock mWakeLock;

    public class BaseKeyguardCallback extends KeyguardUpdateMonitorCallback {
        public BaseKeyguardCallback() {
        }

        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            KeyguardIndicationController.m225$$Nest$mhideBiometricMessage(KeyguardIndicationController.this);
            if (biometricSourceType == BiometricSourceType.FACE && !KeyguardIndicationController.this.mKeyguardBypassController.canBypass()) {
                KeyguardIndicationController.this.showActionToUnlock();
            }
        }

        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            boolean z;
            KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardIndicationController.this.mKeyguardUpdateMonitor;
            boolean z2 = false;
            if (biometricSourceType != BiometricSourceType.FINGERPRINT ? biometricSourceType != BiometricSourceType.FACE || ((keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) || i == 9) && i != 5) : !((!keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) && i != 9) || i == 5 || i == 10)) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    if (KeyguardIndicationController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning() && KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                        z2 = true;
                    }
                    if (z2 && !KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                        ScreenLifecycle screenLifecycle = KeyguardIndicationController.this.mScreenLifecycle;
                        Objects.requireNonNull(screenLifecycle);
                        if (screenLifecycle.mScreenState == 2) {
                            KeyguardIndicationController.m226$$Nest$mshowTryFingerprintMsg(KeyguardIndicationController.this, i, str);
                            return;
                        }
                    }
                }
                if (i == 3) {
                    if (!KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing() && KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUdfpsEnrolled() && KeyguardIndicationController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                        KeyguardIndicationController.m226$$Nest$mshowTryFingerprintMsg(KeyguardIndicationController.this, i, str);
                    } else if (KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isShowingAlternateAuth()) {
                        KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                        keyguardIndicationController.mStatusBarKeyguardViewManager.showBouncerMessage(keyguardIndicationController.mContext.getResources().getString(C1777R.string.keyguard_unlock_press), KeyguardIndicationController.this.mInitialTextColorState);
                    } else {
                        KeyguardIndicationController.this.showActionToUnlock();
                    }
                } else if (KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
                    keyguardIndicationController2.mStatusBarKeyguardViewManager.showBouncerMessage(str, keyguardIndicationController2.mInitialTextColorState);
                } else {
                    ScreenLifecycle screenLifecycle2 = KeyguardIndicationController.this.mScreenLifecycle;
                    Objects.requireNonNull(screenLifecycle2);
                    if (screenLifecycle2.mScreenState == 2) {
                        KeyguardIndicationController.this.showBiometricMessage((CharSequence) str);
                    } else {
                        KeyguardIndicationController.this.mMessageToShowOnScreenOn = str;
                    }
                }
            }
        }

        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            boolean z;
            boolean z2 = true;
            if (KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                if (i == -2) {
                    z = true;
                } else {
                    z = false;
                }
                if (KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                    keyguardIndicationController.mStatusBarKeyguardViewManager.showBouncerMessage(str, keyguardIndicationController.mInitialTextColorState);
                    return;
                }
                ScreenLifecycle screenLifecycle = KeyguardIndicationController.this.mScreenLifecycle;
                Objects.requireNonNull(screenLifecycle);
                if (screenLifecycle.mScreenState == 2) {
                    if (biometricSourceType == BiometricSourceType.FACE) {
                        if (!KeyguardIndicationController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning() || !KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                            z2 = false;
                        }
                        if (z2) {
                            KeyguardIndicationController.m226$$Nest$mshowTryFingerprintMsg(KeyguardIndicationController.this, i, str);
                            return;
                        }
                    }
                    KeyguardIndicationController.this.showBiometricMessage((CharSequence) str);
                } else if (z) {
                    C11455 r5 = KeyguardIndicationController.this.mHandler;
                    r5.sendMessageDelayed(r5.obtainMessage(2), 1300);
                }
            }
        }

        public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            if (z && biometricSourceType == BiometricSourceType.FACE) {
                KeyguardIndicationController.m225$$Nest$mhideBiometricMessage(KeyguardIndicationController.this);
                KeyguardIndicationController.this.mMessageToShowOnScreenOn = null;
            }
        }

        public final void onLogoutEnabledChanged() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.updateIndication(false);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x0034  */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0036  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x0044  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0046  */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x0068  */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x006a  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x008d  */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x008f  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x00a8  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x00aa  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x00b5  */
        /* JADX WARNING: Removed duplicated region for block: B:62:0x00b7  */
        /* JADX WARNING: Removed duplicated region for block: B:67:0x00c2 A[Catch:{ RemoteException -> 0x00cd }] */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x00c9 A[Catch:{ RemoteException -> 0x00cd }] */
        /* JADX WARNING: Removed duplicated region for block: B:79:0x00ec  */
        /* JADX WARNING: Removed duplicated region for block: B:88:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onRefreshBatteryInfo(com.android.settingslib.fuelgauge.BatteryStatus r11) {
            /*
                r10 = this;
                int r0 = r11.status
                r1 = 100
                r2 = 5
                r3 = 1
                r4 = 0
                r5 = 2
                if (r0 == r5) goto L_0x0019
                if (r0 == r2) goto L_0x0013
                int r0 = r11.level
                if (r0 < r1) goto L_0x0011
                goto L_0x0013
            L_0x0011:
                r0 = r4
                goto L_0x0014
            L_0x0013:
                r0 = r3
            L_0x0014:
                if (r0 == 0) goto L_0x0017
                goto L_0x0019
            L_0x0017:
                r0 = r4
                goto L_0x001a
            L_0x0019:
                r0 = r3
            L_0x001a:
                com.android.systemui.statusbar.KeyguardIndicationController r6 = com.android.systemui.statusbar.KeyguardIndicationController.this
                boolean r7 = r6.mPowerPluggedIn
                int r8 = r11.plugged
                if (r8 == r3) goto L_0x0027
                if (r8 != r5) goto L_0x0025
                goto L_0x0027
            L_0x0025:
                r9 = r4
                goto L_0x0028
            L_0x0027:
                r9 = r3
            L_0x0028:
                if (r9 == 0) goto L_0x002e
                if (r0 == 0) goto L_0x002e
                r9 = r3
                goto L_0x002f
            L_0x002e:
                r9 = r4
            L_0x002f:
                r6.mPowerPluggedInWired = r9
                r9 = 4
                if (r8 != r9) goto L_0x0036
                r9 = r3
                goto L_0x0037
            L_0x0036:
                r9 = r4
            L_0x0037:
                if (r9 == 0) goto L_0x003d
                if (r0 == 0) goto L_0x003d
                r9 = r3
                goto L_0x003e
            L_0x003d:
                r9 = r4
            L_0x003e:
                r6.mPowerPluggedInWireless = r9
                r9 = 8
                if (r8 != r9) goto L_0x0046
                r8 = r3
                goto L_0x0047
            L_0x0046:
                r8 = r4
            L_0x0047:
                if (r8 == 0) goto L_0x004d
                if (r0 == 0) goto L_0x004d
                r8 = r3
                goto L_0x004e
            L_0x004d:
                r8 = r4
            L_0x004e:
                r6.mPowerPluggedInDock = r8
                boolean r8 = r11.isPluggedIn()
                if (r8 == 0) goto L_0x005a
                if (r0 == 0) goto L_0x005a
                r0 = r3
                goto L_0x005b
            L_0x005a:
                r0 = r4
            L_0x005b:
                r6.mPowerPluggedIn = r0
                com.android.systemui.statusbar.KeyguardIndicationController r0 = com.android.systemui.statusbar.KeyguardIndicationController.this
                int r6 = r11.status
                if (r6 == r2) goto L_0x006a
                int r2 = r11.level
                if (r2 < r1) goto L_0x0068
                goto L_0x006a
            L_0x0068:
                r1 = r4
                goto L_0x006b
            L_0x006a:
                r1 = r3
            L_0x006b:
                r0.mPowerCharged = r1
                int r1 = r11.maxChargingWattage
                r0.mChargingWattage = r1
                android.content.Context r1 = r0.mContext
                android.content.res.Resources r2 = r1.getResources()
                r6 = 2131492886(0x7f0c0016, float:1.8609237E38)
                int r2 = r2.getInteger(r6)
                android.content.res.Resources r1 = r1.getResources()
                r6 = 2131492885(0x7f0c0015, float:1.8609235E38)
                int r1 = r1.getInteger(r6)
                int r6 = r11.maxChargingWattage
                if (r6 > 0) goto L_0x008f
                r5 = -1
                goto L_0x0097
            L_0x008f:
                if (r6 >= r2) goto L_0x0093
                r5 = r4
                goto L_0x0097
            L_0x0093:
                if (r6 <= r1) goto L_0x0096
                goto L_0x0097
            L_0x0096:
                r5 = r3
            L_0x0097:
                r0.mChargingSpeed = r5
                com.android.systemui.statusbar.KeyguardIndicationController r0 = com.android.systemui.statusbar.KeyguardIndicationController.this
                int r1 = r11.level
                r0.mBatteryLevel = r1
                boolean r1 = r11.present
                r0.mBatteryPresent = r1
                int r1 = r11.health
                r2 = 3
                if (r1 != r2) goto L_0x00aa
                r1 = r3
                goto L_0x00ab
            L_0x00aa:
                r1 = r4
            L_0x00ab:
                r0.mBatteryOverheated = r1
                if (r1 == 0) goto L_0x00b7
                boolean r11 = r11.isPluggedIn()
                if (r11 == 0) goto L_0x00b7
                r11 = r3
                goto L_0x00b8
            L_0x00b7:
                r11 = r4
            L_0x00b8:
                r0.mEnableBatteryDefender = r11
                r0 = -1
                com.android.systemui.statusbar.KeyguardIndicationController r11 = com.android.systemui.statusbar.KeyguardIndicationController.this     // Catch:{ RemoteException -> 0x00cd }
                boolean r2 = r11.mPowerPluggedIn     // Catch:{ RemoteException -> 0x00cd }
                if (r2 == 0) goto L_0x00c9
                com.android.internal.app.IBatteryStats r2 = r11.mBatteryInfo     // Catch:{ RemoteException -> 0x00cd }
                long r5 = r2.computeChargeTimeRemaining()     // Catch:{ RemoteException -> 0x00cd }
                goto L_0x00ca
            L_0x00c9:
                r5 = r0
            L_0x00ca:
                r11.mChargingTimeRemaining = r5     // Catch:{ RemoteException -> 0x00cd }
                goto L_0x00d9
            L_0x00cd:
                r11 = move-exception
                java.lang.String r2 = "KeyguardIndication"
                java.lang.String r5 = "Error calling IBatteryStats: "
                android.util.Log.e(r2, r5, r11)
                com.android.systemui.statusbar.KeyguardIndicationController r11 = com.android.systemui.statusbar.KeyguardIndicationController.this
                r11.mChargingTimeRemaining = r0
            L_0x00d9:
                com.android.systemui.statusbar.KeyguardIndicationController r11 = com.android.systemui.statusbar.KeyguardIndicationController.this
                if (r7 != 0) goto L_0x00e2
                boolean r0 = r11.mPowerPluggedInWired
                if (r0 == 0) goto L_0x00e2
                goto L_0x00e3
            L_0x00e2:
                r3 = r4
            L_0x00e3:
                r11.updateIndication(r3)
                com.android.systemui.statusbar.KeyguardIndicationController r10 = com.android.systemui.statusbar.KeyguardIndicationController.this
                boolean r11 = r10.mDozing
                if (r11 == 0) goto L_0x0103
                if (r7 != 0) goto L_0x00fa
                boolean r11 = r10.mPowerPluggedIn
                if (r11 == 0) goto L_0x00fa
                java.lang.String r11 = r10.computePowerIndication()
                r10.showTransientIndication((java.lang.String) r11)
                goto L_0x0103
            L_0x00fa:
                if (r7 == 0) goto L_0x0103
                boolean r11 = r10.mPowerPluggedIn
                if (r11 != 0) goto L_0x0103
                r10.hideTransientIndication()
            L_0x0103:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback.onRefreshBatteryInfo(com.android.settingslib.fuelgauge.BatteryStatus):void");
        }

        public final void onRequireUnlockForNfc() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.showTransientIndication(keyguardIndicationController.mContext.getString(C1777R.string.require_unlock_for_nfc));
            KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
            Objects.requireNonNull(keyguardIndicationController2);
            C11455 r3 = keyguardIndicationController2.mHandler;
            r3.sendMessageDelayed(r3.obtainMessage(1), 5000);
        }

        public final void onTrustAgentErrorMessage(CharSequence charSequence) {
            KeyguardIndicationController.this.showBiometricMessage(charSequence);
        }

        public final void onUserSwitchComplete(int i) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.updateIndication(false);
            }
        }

        public final void onUserUnlocked() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.updateIndication(false);
            }
        }

        public final void showTrustGrantedMessage(CharSequence charSequence) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.mTrustGrantedIndication = charSequence;
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            String trustGrantedIndication = KeyguardIndicationController.this.getTrustGrantedIndication();
            Objects.requireNonNull(KeyguardIndicationController.this);
            keyguardIndicationController.updateTrust(currentUser, trustGrantedIndication);
        }

        public final void onTrustChanged(int i) {
            if (KeyguardUpdateMonitor.getCurrentUser() == i) {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                String trustGrantedIndication = keyguardIndicationController.getTrustGrantedIndication();
                Objects.requireNonNull(KeyguardIndicationController.this);
                keyguardIndicationController.updateTrust(i, trustGrantedIndication);
            }
        }
    }

    public KeyguardIndicationController(Context context, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, IActivityManager iActivityManager, KeyguardBypassController keyguardBypassController) {
        ScreenLifecycle screenLifecycle2 = screenLifecycle;
        C11422 r2 = new ScreenLifecycle.Observer() {
            public final void onScreenTurnedOn() {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                String str = keyguardIndicationController.mMessageToShowOnScreenOn;
                if (str != null) {
                    keyguardIndicationController.showBiometricMessage((CharSequence) str);
                    KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
                    Objects.requireNonNull(keyguardIndicationController2);
                    C11455 r0 = keyguardIndicationController2.mHandler;
                    r0.sendMessageDelayed(r0.obtainMessage(3), 5000);
                    KeyguardIndicationController.this.mMessageToShowOnScreenOn = null;
                }
            }
        };
        this.mScreenObserver = r2;
        this.mTickReceiver = new KeyguardUpdateMonitorCallback() {
            public final void onTimeChanged() {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (keyguardIndicationController.mVisible) {
                    keyguardIndicationController.updateIndication(false);
                }
            }
        };
        this.mHandler = new Handler() {
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    KeyguardIndicationController.this.hideTransientIndication();
                } else if (i == 2) {
                    KeyguardIndicationController.this.showActionToUnlock();
                } else if (i == 3) {
                    KeyguardIndicationController.m225$$Nest$mhideBiometricMessage(KeyguardIndicationController.this);
                }
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() {
            public final void onDozingChanged(boolean z) {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (keyguardIndicationController.mDozing != z) {
                    keyguardIndicationController.mDozing = z;
                    if (z) {
                        KeyguardIndicationController.m225$$Nest$mhideBiometricMessage(keyguardIndicationController);
                    }
                    KeyguardIndicationController.this.updateIndication(false);
                }
            }

            public final void onStateChanged(int i) {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                keyguardIndicationController.setVisible(z);
            }
        };
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() {
            public final void onKeyguardShowingChanged() {
                if (!KeyguardIndicationController.this.mKeyguardStateController.isShowing()) {
                    KeyguardIndicationTextView keyguardIndicationTextView = KeyguardIndicationController.this.mTopIndicationView;
                    Objects.requireNonNull(keyguardIndicationTextView);
                    AnimatorSet animatorSet = keyguardIndicationTextView.mLastAnimator;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                    }
                    keyguardIndicationTextView.setText("");
                    KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = KeyguardIndicationController.this.mRotateTextViewController;
                    Objects.requireNonNull(keyguardIndicationRotateTextViewController);
                    keyguardIndicationRotateTextViewController.mCurrIndicationType = -1;
                    keyguardIndicationRotateTextViewController.mIndicationQueue.clear();
                    keyguardIndicationRotateTextViewController.mIndicationMessages.clear();
                    KeyguardIndicationTextView keyguardIndicationTextView2 = (KeyguardIndicationTextView) keyguardIndicationRotateTextViewController.mView;
                    Objects.requireNonNull(keyguardIndicationTextView2);
                    AnimatorSet animatorSet2 = keyguardIndicationTextView2.mLastAnimator;
                    if (animatorSet2 != null) {
                        animatorSet2.cancel();
                    }
                    keyguardIndicationTextView2.setText("");
                    return;
                }
                KeyguardIndicationController.this.updatePersistentIndications(false, KeyguardUpdateMonitor.getCurrentUser());
            }

            public final void onUnlockedChanged() {
                KeyguardIndicationController.this.updateIndication(false);
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDockManager = dockManager;
        Objects.requireNonNull(builder);
        this.mWakeLock = new SettableWakeLock(WakeLock.createPartial$1(builder.mContext, "Doze:KeyguardIndication"), "KeyguardIndication");
        this.mBatteryInfo = iBatteryStats;
        this.mUserManager = userManager;
        this.mExecutor = delayableExecutor;
        this.mBackgroundExecutor = delayableExecutor2;
        this.mLockPatternUtils = lockPatternUtils;
        this.mIActivityManager = iActivityManager;
        this.mFalsingManager = falsingManager;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mScreenLifecycle = screenLifecycle2;
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle2.mObservers.add(r2);
    }

    public final void showBiometricMessage(int i) {
        showBiometricMessage((CharSequence) this.mContext.getResources().getString(i));
    }

    public final void showTransientIndication(int i) {
        showTransientIndication(this.mContext.getResources().getString(i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        if (r0 != false) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008b, code lost:
        if (r0 != false) goto L_0x008d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String computePowerIndication() {
        /*
            r10 = this;
            boolean r0 = r10.mBatteryOverheated
            r1 = 1120403456(0x42c80000, float:100.0)
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x0027
            r0 = 2131952553(0x7f1303a9, float:1.9541552E38)
            java.text.NumberFormat r4 = java.text.NumberFormat.getPercentInstance()
            int r5 = r10.mBatteryLevel
            float r5 = (float) r5
            float r5 = r5 / r1
            double r5 = (double) r5
            java.lang.String r1 = r4.format(r5)
            android.content.Context r10 = r10.mContext
            android.content.res.Resources r10 = r10.getResources()
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r2] = r1
            java.lang.String r10 = r10.getString(r0, r3)
            return r10
        L_0x0027:
            boolean r0 = r10.mPowerCharged
            if (r0 == 0) goto L_0x0039
            android.content.Context r10 = r10.mContext
            android.content.res.Resources r10 = r10.getResources()
            r0 = 2131952531(0x7f130393, float:1.9541507E38)
            java.lang.String r10 = r10.getString(r0)
            return r10
        L_0x0039:
            long r4 = r10.mChargingTimeRemaining
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x0043
            r0 = r3
            goto L_0x0044
        L_0x0043:
            r0 = r2
        L_0x0044:
            boolean r4 = r10.mPowerPluggedInWired
            r5 = 2131952537(0x7f130399, float:1.954152E38)
            r6 = 2131952551(0x7f1303a7, float:1.9541548E38)
            r7 = 2
            if (r4 == 0) goto L_0x006f
            int r4 = r10.mChargingSpeed
            if (r4 == 0) goto L_0x0065
            if (r4 == r7) goto L_0x005a
            if (r0 == 0) goto L_0x0058
            goto L_0x008d
        L_0x0058:
            r5 = r6
            goto L_0x008d
        L_0x005a:
            if (r0 == 0) goto L_0x0060
            r4 = 2131952539(0x7f13039b, float:1.9541524E38)
            goto L_0x0063
        L_0x0060:
            r4 = 2131952552(0x7f1303a8, float:1.954155E38)
        L_0x0063:
            r5 = r4
            goto L_0x008d
        L_0x0065:
            if (r0 == 0) goto L_0x006b
            r4 = 2131952540(0x7f13039c, float:1.9541526E38)
            goto L_0x0063
        L_0x006b:
            r4 = 2131952554(0x7f1303aa, float:1.9541554E38)
            goto L_0x0063
        L_0x006f:
            boolean r4 = r10.mPowerPluggedInWireless
            if (r4 == 0) goto L_0x007d
            if (r0 == 0) goto L_0x0079
            r4 = 2131952541(0x7f13039d, float:1.9541528E38)
            goto L_0x0063
        L_0x0079:
            r4 = 2131952556(0x7f1303ac, float:1.9541558E38)
            goto L_0x0063
        L_0x007d:
            boolean r4 = r10.mPowerPluggedInDock
            if (r4 == 0) goto L_0x008b
            if (r0 == 0) goto L_0x0087
            r4 = 2131952538(0x7f13039a, float:1.9541522E38)
            goto L_0x0063
        L_0x0087:
            r4 = 2131952555(0x7f1303ab, float:1.9541556E38)
            goto L_0x0063
        L_0x008b:
            if (r0 == 0) goto L_0x0058
        L_0x008d:
            java.text.NumberFormat r4 = java.text.NumberFormat.getPercentInstance()
            int r6 = r10.mBatteryLevel
            float r6 = (float) r6
            float r6 = r6 / r1
            double r8 = (double) r6
            java.lang.String r1 = r4.format(r8)
            if (r0 == 0) goto L_0x00b5
            android.content.Context r0 = r10.mContext
            long r8 = r10.mChargingTimeRemaining
            java.lang.String r0 = android.text.format.Formatter.formatShortElapsedTimeRoundingUpToMinutes(r0, r8)
            android.content.Context r10 = r10.mContext
            android.content.res.Resources r10 = r10.getResources()
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r2] = r0
            r4[r3] = r1
            java.lang.String r10 = r10.getString(r5, r4)
            return r10
        L_0x00b5:
            android.content.Context r10 = r10.mContext
            android.content.res.Resources r10 = r10.getResources()
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r0[r2] = r1
            java.lang.String r10 = r10.getString(r5, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardIndicationController.computePowerIndication():java.lang.String");
    }

    public KeyguardUpdateMonitorCallback getKeyguardCallback() {
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new BaseKeyguardCallback();
        }
        return this.mUpdateMonitorCallback;
    }

    @VisibleForTesting
    public String getTrustGrantedIndication() {
        if (TextUtils.isEmpty(this.mTrustGrantedIndication)) {
            return this.mContext.getString(C1777R.string.keyguard_indication_trust_unlocked);
        }
        return this.mTrustGrantedIndication.toString();
    }

    public final void hideTransientIndication() {
        if (this.mTransientIndication != null) {
            this.mTransientIndication = null;
            this.mHandler.removeMessages(1);
            updateTransient();
        }
    }

    public void init() {
        if (!this.mInited) {
            this.mInited = true;
            this.mDockManager.addAlignmentStateListener(new KeyguardIndicationController$$ExternalSyntheticLambda0(this));
            this.mKeyguardUpdateMonitor.registerCallback(getKeyguardCallback());
            this.mKeyguardUpdateMonitor.registerCallback(this.mTickReceiver);
            this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
            this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
            this.mStatusBarStateListener.onDozingChanged(this.mStatusBarStateController.isDozing());
        }
    }

    public final void setIndicationArea(KeyguardBottomAreaView keyguardBottomAreaView) {
        ColorStateList colorStateList;
        this.mIndicationArea = keyguardBottomAreaView;
        this.mTopIndicationView = (KeyguardIndicationTextView) keyguardBottomAreaView.findViewById(C1777R.C1779id.keyguard_indication_text);
        this.mLockScreenIndicationView = (KeyguardIndicationTextView) keyguardBottomAreaView.findViewById(C1777R.C1779id.keyguard_indication_text_bottom);
        KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
        if (keyguardIndicationTextView != null) {
            colorStateList = keyguardIndicationTextView.getTextColors();
        } else {
            colorStateList = ColorStateList.valueOf(-1);
        }
        this.mInitialTextColorState = colorStateList;
        this.mRotateTextViewController = new KeyguardIndicationRotateTextViewController(this.mLockScreenIndicationView, this.mExecutor, this.mStatusBarStateController);
        updateIndication(false);
        this.mOrganizationOwnedDevice = ((Boolean) DejankUtils.whitelistIpcs(new NavigationBarView$$ExternalSyntheticLambda3(this, 1))).booleanValue();
        updatePersistentIndications(false, KeyguardUpdateMonitor.getCurrentUser());
        if (this.mBroadcastReceiver == null) {
            this.mBroadcastReceiver = new BroadcastReceiver() {
                public final void onReceive(Context context, Intent intent) {
                    KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                    Objects.requireNonNull(keyguardIndicationController);
                    keyguardIndicationController.mOrganizationOwnedDevice = ((Boolean) DejankUtils.whitelistIpcs(new NavigationBarView$$ExternalSyntheticLambda3(keyguardIndicationController, 1))).booleanValue();
                    keyguardIndicationController.updatePersistentIndications(false, KeyguardUpdateMonitor.getCurrentUser());
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
    }

    public final void setVisible(boolean z) {
        int i;
        this.mVisible = z;
        ViewGroup viewGroup = this.mIndicationArea;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        viewGroup.setVisibility(i);
        if (z) {
            if (!this.mHandler.hasMessages(1)) {
                hideTransientIndication();
            }
            updateIndication(false);
        } else if (!z) {
            hideTransientIndication();
        }
    }

    public final void showActionToUnlock() {
        if (this.mDozing && !this.mKeyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
            return;
        }
        if (this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
            if (!this.mStatusBarKeyguardViewManager.isShowingAlternateAuth()) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                if (keyguardUpdateMonitor.mIsFaceEnrolled) {
                    this.mStatusBarKeyguardViewManager.showBouncerMessage(this.mContext.getString(C1777R.string.keyguard_retry), this.mInitialTextColorState);
                }
            }
        } else if (!this.mKeyguardUpdateMonitor.isUdfpsSupported() || !this.mKeyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
            showBiometricMessage((CharSequence) this.mContext.getString(C1777R.string.keyguard_unlock));
        } else {
            showBiometricMessage((CharSequence) this.mContext.getString(C1777R.string.keyguard_unlock_press));
        }
    }

    public final void showBiometricMessage(CharSequence charSequence) {
        this.mBiometricMessage = charSequence;
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        C11455 r4 = this.mHandler;
        r4.sendMessageDelayed(r4.obtainMessage(3), 5000);
        updateBiometricMessage();
    }

    public final void showTransientIndication(String str) {
        this.mTransientIndication = str;
        this.mHandler.removeMessages(1);
        C11455 r4 = this.mHandler;
        r4.sendMessageDelayed(r4.obtainMessage(1), 5000);
        updateTransient();
    }

    public final void updateBiometricMessage() {
        if (!TextUtils.isEmpty(this.mBiometricMessage)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
            CharSequence charSequence = this.mBiometricMessage;
            ColorStateList colorStateList = this.mInitialTextColorState;
            if (TextUtils.isEmpty(charSequence)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList != null) {
                keyguardIndicationRotateTextViewController.updateIndication(11, new KeyguardIndication(charSequence, colorStateList, (Drawable) null, (View.OnClickListener) null, (Drawable) null, 2600L), true);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            this.mRotateTextViewController.hideIndication(11);
        }
        if (this.mDozing) {
            updateIndication(false);
        }
    }

    public final void updateIndication(boolean z) {
        if (this.mVisible) {
            this.mIndicationArea.setVisibility(0);
            if (this.mDozing) {
                this.mLockScreenIndicationView.setVisibility(8);
                this.mTopIndicationView.setVisibility(0);
                this.mTopIndicationView.setTextColor(-1);
                if (!TextUtils.isEmpty(this.mBiometricMessage)) {
                    this.mWakeLock.setAcquired(true);
                    this.mTopIndicationView.switchIndication(this.mBiometricMessage, (KeyguardIndication) null, true, new StatusBar$$ExternalSyntheticLambda18(this, 6));
                } else if (!TextUtils.isEmpty(this.mTransientIndication)) {
                    this.mWakeLock.setAcquired(true);
                    this.mTopIndicationView.switchIndication(this.mTransientIndication, (KeyguardIndication) null, true, new BubbleStackView$$ExternalSyntheticLambda18(this, 3));
                } else if (!this.mBatteryPresent) {
                    this.mIndicationArea.setVisibility(8);
                } else if (!TextUtils.isEmpty(this.mAlignmentIndication)) {
                    this.mTopIndicationView.switchIndication(this.mAlignmentIndication, (KeyguardIndication) null, false, (Runnable) null);
                    this.mTopIndicationView.setTextColor(this.mContext.getColor(C1777R.color.misalignment_text_color));
                } else if (this.mPowerPluggedIn || this.mEnableBatteryDefender) {
                    String computePowerIndication = computePowerIndication();
                    if (z) {
                        this.mWakeLock.setAcquired(true);
                        this.mTopIndicationView.switchIndication(computePowerIndication, (KeyguardIndication) null, true, new Action$$ExternalSyntheticLambda0(this, 3));
                        return;
                    }
                    this.mTopIndicationView.switchIndication(computePowerIndication, (KeyguardIndication) null, false, (Runnable) null);
                } else {
                    this.mTopIndicationView.switchIndication(NumberFormat.getPercentInstance().format((double) (((float) this.mBatteryLevel) / 100.0f)), (KeyguardIndication) null, false, (Runnable) null);
                }
            } else {
                this.mTopIndicationView.setVisibility(8);
                this.mTopIndicationView.setText((CharSequence) null);
                this.mLockScreenIndicationView.setVisibility(0);
                updatePersistentIndications(z, KeyguardUpdateMonitor.getCurrentUser());
            }
        }
    }

    public final void updatePersistentIndications(boolean z, int i) {
        boolean z2;
        int i2 = i;
        boolean z3 = true;
        if (this.mOrganizationOwnedDevice) {
            this.mBackgroundExecutor.execute(new WMShell$7$$ExternalSyntheticLambda0(this, 4));
        } else {
            this.mRotateTextViewController.hideIndication(1);
        }
        this.mBackgroundExecutor.execute(new WMShell$7$$ExternalSyntheticLambda2(this, 5));
        if (this.mPowerPluggedIn || this.mEnableBatteryDefender) {
            String computePowerIndication = computePowerIndication();
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
            ColorStateList colorStateList = this.mInitialTextColorState;
            if (TextUtils.isEmpty(computePowerIndication)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList != null) {
                keyguardIndicationRotateTextViewController.updateIndication(3, new KeyguardIndication(computePowerIndication, colorStateList, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null), z);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            this.mRotateTextViewController.hideIndication(3);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        if (!keyguardUpdateMonitor.mUserIsUnlocked.get(i2)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController2 = this.mRotateTextViewController;
            CharSequence text = this.mContext.getResources().getText(17040624);
            ColorStateList colorStateList2 = this.mInitialTextColorState;
            if (TextUtils.isEmpty(text)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList2 != null) {
                KeyguardIndication keyguardIndication = r9;
                KeyguardIndication keyguardIndication2 = new KeyguardIndication(text, colorStateList2, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null);
                keyguardIndicationRotateTextViewController2.updateIndication(8, keyguardIndication2, false);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            this.mRotateTextViewController.hideIndication(8);
        }
        updateTrust(i2, getTrustGrantedIndication());
        if (!TextUtils.isEmpty(this.mAlignmentIndication)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController3 = this.mRotateTextViewController;
            String str = this.mAlignmentIndication;
            ColorStateList valueOf = ColorStateList.valueOf(this.mContext.getColor(C1777R.color.misalignment_text_color));
            if (TextUtils.isEmpty(str)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (valueOf != null) {
                keyguardIndicationRotateTextViewController3.updateIndication(4, new KeyguardIndication(str, valueOf, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null), true);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            this.mRotateTextViewController.hideIndication(4);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor2);
        if (!keyguardUpdateMonitor2.mLogoutEnabled || KeyguardUpdateMonitor.getCurrentUser() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController4 = this.mRotateTextViewController;
            String string = this.mContext.getResources().getString(17040386);
            ColorStateList colorAttr = Utils.getColorAttr(this.mContext, 17957103);
            Drawable drawable = this.mContext.getDrawable(C1777R.C1778drawable.logout_button_background);
            AvatarPickerActivity$$ExternalSyntheticLambda0 avatarPickerActivity$$ExternalSyntheticLambda0 = new AvatarPickerActivity$$ExternalSyntheticLambda0(this, 2);
            if (TextUtils.isEmpty(string)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorAttr != null) {
                keyguardIndicationRotateTextViewController4.updateIndication(2, new KeyguardIndication(string, colorAttr, (Drawable) null, avatarPickerActivity$$ExternalSyntheticLambda0, drawable, (Long) null), false);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            this.mRotateTextViewController.hideIndication(2);
        }
        if (!TextUtils.isEmpty((CharSequence) null)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController5 = this.mRotateTextViewController;
            Objects.requireNonNull(keyguardIndicationRotateTextViewController5);
            if (keyguardIndicationRotateTextViewController5.mIndicationMessages.keySet().size() <= 0) {
                z3 = false;
            }
            if (!z3) {
                KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController6 = this.mRotateTextViewController;
                ColorStateList colorStateList3 = this.mInitialTextColorState;
                if (TextUtils.isEmpty((CharSequence) null)) {
                    throw new IllegalStateException("message or icon must be set");
                } else if (colorStateList3 != null) {
                    keyguardIndicationRotateTextViewController6.updateIndication(7, new KeyguardIndication((CharSequence) null, colorStateList3, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null), false);
                    return;
                } else {
                    throw new IllegalStateException("text color must be set");
                }
            }
        }
        this.mRotateTextViewController.hideIndication(7);
    }

    public final void updateTransient() {
        if (!TextUtils.isEmpty(this.mTransientIndication)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
            CharSequence charSequence = this.mTransientIndication;
            Objects.requireNonNull(keyguardIndicationRotateTextViewController);
            ColorStateList colorStateList = keyguardIndicationRotateTextViewController.mInitialTextColorState;
            if (TextUtils.isEmpty(charSequence)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList != null) {
                keyguardIndicationRotateTextViewController.updateIndication(5, new KeyguardIndication(charSequence, colorStateList, (Drawable) null, (View.OnClickListener) null, (Drawable) null, 2600L), true);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController2 = this.mRotateTextViewController;
            Objects.requireNonNull(keyguardIndicationRotateTextViewController2);
            keyguardIndicationRotateTextViewController2.hideIndication(5);
        }
        if (this.mDozing) {
            updateIndication(false);
        }
    }

    /* renamed from: -$$Nest$mhideBiometricMessage  reason: not valid java name */
    public static void m225$$Nest$mhideBiometricMessage(KeyguardIndicationController keyguardIndicationController) {
        Objects.requireNonNull(keyguardIndicationController);
        if (keyguardIndicationController.mBiometricMessage != null) {
            keyguardIndicationController.mBiometricMessage = null;
            keyguardIndicationController.mHandler.removeMessages(3);
            keyguardIndicationController.updateBiometricMessage();
        }
    }

    /* renamed from: -$$Nest$mshowTryFingerprintMsg  reason: not valid java name */
    public static void m226$$Nest$mshowTryFingerprintMsg(KeyguardIndicationController keyguardIndicationController, int i, String str) {
        Objects.requireNonNull(keyguardIndicationController);
        if (keyguardIndicationController.mKeyguardUpdateMonitor.isUdfpsSupported()) {
            KeyguardBypassController keyguardBypassController = keyguardIndicationController.mKeyguardBypassController;
            Objects.requireNonNull(keyguardBypassController);
            if (keyguardBypassController.userHasDeviceEntryIntent) {
                keyguardIndicationController.showBiometricMessage((int) C1777R.string.keyguard_unlock_press);
            } else if (i == 9) {
                keyguardIndicationController.showBiometricMessage((int) C1777R.string.keyguard_try_fingerprint);
            } else {
                keyguardIndicationController.showBiometricMessage((int) C1777R.string.keyguard_face_failed_use_fp);
            }
        } else {
            keyguardIndicationController.showBiometricMessage((int) C1777R.string.keyguard_try_fingerprint);
        }
        if (!TextUtils.isEmpty(str)) {
            keyguardIndicationController.mLockScreenIndicationView.announceForAccessibility(str);
        }
    }

    public final void updateTrust(int i, String str) {
        if (!TextUtils.isEmpty(str) && this.mKeyguardUpdateMonitor.getUserHasTrust(i)) {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
            ColorStateList colorStateList = this.mInitialTextColorState;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList != null) {
                keyguardIndicationRotateTextViewController.updateIndication(6, new KeyguardIndication(str, colorStateList, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null), false);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        } else if (TextUtils.isEmpty((CharSequence) null) || !this.mKeyguardUpdateMonitor.getUserTrustIsManaged(i) || this.mKeyguardUpdateMonitor.getUserHasTrust(i)) {
            this.mRotateTextViewController.hideIndication(6);
        } else {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController2 = this.mRotateTextViewController;
            ColorStateList colorStateList2 = this.mInitialTextColorState;
            if (TextUtils.isEmpty((CharSequence) null)) {
                throw new IllegalStateException("message or icon must be set");
            } else if (colorStateList2 != null) {
                keyguardIndicationRotateTextViewController2.updateIndication(6, new KeyguardIndication((CharSequence) null, colorStateList2, (Drawable) null, (View.OnClickListener) null, (Drawable) null, (Long) null), false);
            } else {
                throw new IllegalStateException("text color must be set");
            }
        }
    }

    @VisibleForTesting
    public void setPowerPluggedIn(boolean z) {
        this.mPowerPluggedIn = z;
    }
}
