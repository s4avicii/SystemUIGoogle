package com.android.keyguard;

import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Iterator;
import java.util.Objects;

public final class KeyguardHostViewController extends ViewController<KeyguardHostView> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final AudioManager mAudioManager;
    public Runnable mCancelAction;
    public ActivityStarter.OnDismissAction mDismissAction;
    public final KeyguardSecurityContainerController mKeyguardSecurityContainerController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public KeyguardHostViewController$$ExternalSyntheticLambda0 mOnKeyListener;
    public final C04992 mSecurityCallback;
    public final TelephonyManager mTelephonyManager;
    public final C04981 mUpdateCallback = new KeyguardUpdateMonitorCallback() {
        public final void onUserSwitchComplete(int i) {
            KeyguardHostViewController.this.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
        }

        public final void onTrustGrantedWithFlags(int i, int i2) {
            boolean z;
            if (i2 == KeyguardUpdateMonitor.getCurrentUser()) {
                boolean isVisibleToUser = ((KeyguardHostView) KeyguardHostViewController.this.mView).isVisibleToUser();
                boolean z2 = true;
                if ((i & 1) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if ((i & 2) == 0) {
                    z2 = false;
                }
                if (!z && !z2) {
                    return;
                }
                if (!KeyguardHostViewController.this.mViewMediatorCallback.isScreenOn() || (!isVisibleToUser && !z2)) {
                    KeyguardHostViewController.this.mViewMediatorCallback.playTrustedSound();
                    return;
                }
                if (!isVisibleToUser) {
                    Log.i("KeyguardViewBase", "TrustAgent dismissed Keyguard.");
                }
                KeyguardHostViewController.this.mSecurityCallback.dismiss(false, i2, false);
            }
        }
    };
    public final ViewMediatorCallback mViewMediatorCallback;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardHostViewController(KeyguardHostView keyguardHostView, KeyguardUpdateMonitor keyguardUpdateMonitor, AudioManager audioManager, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, KeyguardSecurityContainerController.Factory factory) {
        super(keyguardHostView);
        KeyguardSecurityContainerController.Factory factory2 = factory;
        C04992 r12 = r2;
        C04992 r2 = new KeyguardSecurityContainer.SecurityCallback() {
            /* JADX WARNING: Removed duplicated region for block: B:38:0x00cf  */
            /* JADX WARNING: Removed duplicated region for block: B:45:0x00f1  */
            /* JADX WARNING: Removed duplicated region for block: B:47:0x0107  */
            /* JADX WARNING: Removed duplicated region for block: B:49:0x0120  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final boolean dismiss(boolean r12, int r13, boolean r14) {
                /*
                    r11 = this;
                    com.android.keyguard.KeyguardHostViewController r11 = com.android.keyguard.KeyguardHostViewController.this
                    com.android.keyguard.KeyguardSecurityContainerController r11 = r11.mKeyguardSecurityContainerController
                    boolean r0 = com.android.keyguard.KeyguardSecurityContainerController.DEBUG
                    java.lang.String r1 = "KeyguardSecurityView"
                    if (r0 == 0) goto L_0x0027
                    java.util.Objects.requireNonNull(r11)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r2 = "showNextSecurityScreenOrFinish("
                    r0.append(r2)
                    r0.append(r12)
                    java.lang.String r2 = ")"
                    r0.append(r2)
                    java.lang.String r0 = r0.toString()
                    android.util.Log.d(r1, r0)
                L_0x0027:
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r0 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.UNKNOWN
                    com.android.keyguard.KeyguardUpdateMonitor r2 = r11.mUpdateMonitor
                    boolean r2 = r2.getUserHasTrust(r13)
                    r3 = 5
                    r4 = 4
                    r5 = 2
                    r6 = 3
                    r7 = -1
                    r8 = 0
                    r9 = 1
                    if (r2 == 0) goto L_0x003d
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r12 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_EXTENDED_ACCESS
                    r4 = r6
                    goto L_0x00a6
                L_0x003d:
                    com.android.keyguard.KeyguardUpdateMonitor r2 = r11.mUpdateMonitor
                    boolean r2 = r2.getUserUnlockedWithBiometric(r13)
                    if (r2 == 0) goto L_0x0049
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r12 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_BIOMETRIC
                    r4 = r5
                    goto L_0x00a6
                L_0x0049:
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r2 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.None
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r10 = r11.mCurrentSecurityMode
                    if (r2 != r10) goto L_0x0064
                    com.android.keyguard.KeyguardSecurityModel r12 = r11.mSecurityModel
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r12 = r12.getSecurityMode(r13)
                    if (r2 != r12) goto L_0x005c
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r12 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_NONE_SECURITY
                    r2 = r8
                    r1 = r9
                    goto L_0x0062
                L_0x005c:
                    r11.showSecurityScreen(r12)
                    r12 = r0
                    r2 = r7
                    r1 = r8
                L_0x0062:
                    r4 = r8
                    goto L_0x00b8
                L_0x0064:
                    if (r12 == 0) goto L_0x00b4
                    int r12 = r10.ordinal()
                    if (r12 == r5) goto L_0x00ae
                    if (r12 == r6) goto L_0x00ae
                    if (r12 == r4) goto L_0x00ae
                    if (r12 == r3) goto L_0x0090
                    r5 = 6
                    if (r12 == r5) goto L_0x0090
                    java.lang.String r12 = "Bad security screen "
                    java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r2 = r11.mCurrentSecurityMode
                    r12.append(r2)
                    java.lang.String r2 = ", fail safe"
                    r12.append(r2)
                    java.lang.String r12 = r12.toString()
                    android.util.Log.v(r1, r12)
                    r11.showPrimarySecurityScreen(r8)
                    goto L_0x00b4
                L_0x0090:
                    com.android.keyguard.KeyguardSecurityModel r12 = r11.mSecurityModel
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r12 = r12.getSecurityMode(r13)
                    if (r12 != r2) goto L_0x00aa
                    com.android.internal.widget.LockPatternUtils r1 = r11.mLockPatternUtils
                    int r2 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
                    boolean r1 = r1.isLockScreenDisabled(r2)
                    if (r1 == 0) goto L_0x00aa
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r12 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_SIM
                L_0x00a6:
                    r2 = r4
                    r4 = r8
                    r1 = r9
                    goto L_0x00b8
                L_0x00aa:
                    r11.showSecurityScreen(r12)
                    goto L_0x00b4
                L_0x00ae:
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r12 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_PASSWORD
                    r1 = r9
                    r2 = r1
                    r4 = r2
                    goto L_0x00b8
                L_0x00b4:
                    r12 = r0
                    r2 = r7
                    r1 = r8
                    r4 = r1
                L_0x00b8:
                    if (r1 == 0) goto L_0x00ef
                    if (r14 != 0) goto L_0x00ef
                    com.android.keyguard.KeyguardUpdateMonitor r14 = r11.mUpdateMonitor
                    java.util.Objects.requireNonNull(r14)
                    java.util.HashMap r14 = r14.mSecondaryLockscreenRequirement
                    java.lang.Integer r5 = java.lang.Integer.valueOf(r13)
                    java.lang.Object r14 = r14.get(r5)
                    android.content.Intent r14 = (android.content.Intent) r14
                    if (r14 == 0) goto L_0x00ef
                    com.android.keyguard.AdminSecondaryLockScreenController r11 = r11.mAdminSecondaryLockScreenController
                    java.util.Objects.requireNonNull(r11)
                    android.app.admin.IKeyguardClient r12 = r11.mClient
                    if (r12 != 0) goto L_0x00df
                    android.content.Context r12 = r11.mContext
                    com.android.keyguard.AdminSecondaryLockScreenController$1 r13 = r11.mConnection
                    r12.bindService(r14, r13, r9)
                L_0x00df:
                    com.android.keyguard.AdminSecondaryLockScreenController$AdminSecurityView r12 = r11.mView
                    boolean r12 = r12.isAttachedToWindow()
                    if (r12 != 0) goto L_0x0128
                    com.android.keyguard.KeyguardSecurityContainer r12 = r11.mParent
                    com.android.keyguard.AdminSecondaryLockScreenController$AdminSecurityView r11 = r11.mView
                    r12.addView(r11)
                    goto L_0x0128
                L_0x00ef:
                    if (r2 == r7) goto L_0x0105
                    com.android.internal.logging.MetricsLogger r14 = r11.mMetricsLogger
                    android.metrics.LogMaker r5 = new android.metrics.LogMaker
                    r6 = 197(0xc5, float:2.76E-43)
                    r5.<init>(r6)
                    android.metrics.LogMaker r3 = r5.setType(r3)
                    android.metrics.LogMaker r2 = r3.setSubtype(r2)
                    r14.write(r2)
                L_0x0105:
                    if (r12 == r0) goto L_0x011e
                    com.android.internal.logging.UiEventLogger r14 = r11.mUiEventLogger
                    com.android.systemui.log.SessionTracker r0 = r11.mSessionTracker
                    java.util.Objects.requireNonNull(r0)
                    java.util.HashMap r0 = r0.mSessionToInstanceId
                    java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
                    r3 = 0
                    java.lang.Object r0 = r0.getOrDefault(r2, r3)
                    com.android.internal.logging.InstanceId r0 = (com.android.internal.logging.InstanceId) r0
                    r14.log(r12, r0)
                L_0x011e:
                    if (r1 == 0) goto L_0x0127
                    com.android.keyguard.KeyguardSecurityContainer$SecurityCallback r11 = r11.mSecurityCallback
                    com.android.keyguard.KeyguardHostViewController$2 r11 = (com.android.keyguard.KeyguardHostViewController.C04992) r11
                    r11.finish(r4, r13)
                L_0x0127:
                    r8 = r1
                L_0x0128:
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardHostViewController.C04992.dismiss(boolean, int, boolean):boolean");
            }

            public final void finish(boolean z, int i) {
                boolean z2;
                ActivityStarter.OnDismissAction onDismissAction = KeyguardHostViewController.this.mDismissAction;
                if (onDismissAction != null) {
                    z2 = onDismissAction.onDismiss();
                    KeyguardHostViewController keyguardHostViewController = KeyguardHostViewController.this;
                    keyguardHostViewController.mDismissAction = null;
                    keyguardHostViewController.mCancelAction = null;
                } else {
                    z2 = false;
                }
                ViewMediatorCallback viewMediatorCallback = KeyguardHostViewController.this.mViewMediatorCallback;
                if (viewMediatorCallback == null) {
                    return;
                }
                if (z2) {
                    viewMediatorCallback.keyguardDonePending(i);
                } else {
                    viewMediatorCallback.keyguardDone(i);
                }
            }
        };
        this.mSecurityCallback = r2;
        this.mOnKeyListener = new KeyguardHostViewController$$ExternalSyntheticLambda0(this);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAudioManager = audioManager;
        this.mTelephonyManager = telephonyManager;
        this.mViewMediatorCallback = viewMediatorCallback;
        Objects.requireNonNull(factory);
        this.mKeyguardSecurityContainerController = new KeyguardSecurityContainerController(factory2.mView, factory2.mAdminSecondaryLockScreenControllerFactory, factory2.mLockPatternUtils, factory2.mKeyguardUpdateMonitor, factory2.mKeyguardSecurityModel, factory2.mMetricsLogger, factory2.mUiEventLogger, factory2.mKeyguardStateController, r12, factory2.mSecurityViewFlipperController, factory2.mConfigurationController, factory2.mFalsingCollector, factory2.mFalsingManager, factory2.mUserSwitcherController, factory2.mFeatureFlags, factory2.mGlobalSettings, factory2.mSessionTracker);
    }

    public final void onInit() {
        this.mKeyguardSecurityContainerController.init();
        updateResources();
    }

    public final void onResume() {
        GlobalSettings globalSettings;
        FalsingManager falsingManager;
        UserSwitcherController userSwitcherController;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("screen on, instance ");
            m.append(Integer.toHexString(hashCode()));
            Log.d("KeyguardViewBase", m.toString());
        }
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.mKeyguardSecurityContainerController;
        Objects.requireNonNull(keyguardSecurityContainerController);
        KeyguardSecurityModel.SecurityMode securityMode = keyguardSecurityContainerController.mCurrentSecurityMode;
        KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
        boolean z = true;
        if (securityMode != securityMode2) {
            int i = 2;
            KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecurityContainerController.mView;
            Objects.requireNonNull(keyguardSecurityContainer);
            if (keyguardSecurityContainer.mCurrentMode == 1) {
                if (((KeyguardSecurityContainer) keyguardSecurityContainerController.mView).isOneHandedModeLeftAligned()) {
                    i = 3;
                } else {
                    i = 4;
                }
            }
            SysUiStatsLog.write(63, i);
            keyguardSecurityContainerController.getCurrentSecurityController().onResume(1);
        }
        KeyguardSecurityContainer keyguardSecurityContainer2 = (KeyguardSecurityContainer) keyguardSecurityContainerController.mView;
        KeyguardSecurityModel.SecurityMode securityMode3 = keyguardSecurityContainerController.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        boolean isFaceAuthEnabled = keyguardSecurityContainerController.mKeyguardStateController.isFaceAuthEnabled();
        Objects.requireNonNull(keyguardSecurityContainer2);
        keyguardSecurityContainer2.mSecurityViewFlipper.setWindowInsetsAnimationCallback(keyguardSecurityContainer2.mWindowInsetsAnimationCallback);
        if (!isFaceAuthEnabled || securityMode3 == KeyguardSecurityModel.SecurityMode.SimPin || securityMode3 == KeyguardSecurityModel.SecurityMode.SimPuk || securityMode3 == securityMode2) {
            z = false;
        }
        keyguardSecurityContainer2.mSwipeUpToRetry = z;
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = keyguardSecurityContainer2.mSecurityViewFlipper;
        if (!(keyguardSecurityViewFlipper == null || (globalSettings = keyguardSecurityContainer2.mGlobalSettings) == null || (falsingManager = keyguardSecurityContainer2.mFalsingManager) == null || (userSwitcherController = keyguardSecurityContainer2.mUserSwitcherController) == null)) {
            keyguardSecurityContainer2.mViewMode.init(keyguardSecurityContainer2, globalSettings, keyguardSecurityViewFlipper, falsingManager, userSwitcherController);
        }
        ((KeyguardHostView) this.mView).requestFocus();
    }

    public final void onViewAttached() {
        KeyguardHostView keyguardHostView = (KeyguardHostView) this.mView;
        ViewMediatorCallback viewMediatorCallback = this.mViewMediatorCallback;
        Objects.requireNonNull(keyguardHostView);
        keyguardHostView.mViewMediatorCallback = viewMediatorCallback;
        this.mViewMediatorCallback.setNeedsInput(this.mKeyguardSecurityContainerController.needsInput());
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        ((KeyguardHostView) this.mView).setOnKeyListener(this.mOnKeyListener);
        this.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
    }

    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateCallback);
        ((KeyguardHostView) this.mView).setOnKeyListener((View.OnKeyListener) null);
    }

    public final void resetSecurityContainer() {
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.mKeyguardSecurityContainerController;
        Objects.requireNonNull(keyguardSecurityContainerController);
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecurityContainerController.mView;
        Objects.requireNonNull(keyguardSecurityContainer);
        keyguardSecurityContainer.mDisappearAnimRunning = false;
        KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = keyguardSecurityContainerController.mSecurityViewFlipperController;
        Objects.requireNonNull(keyguardSecurityViewFlipperController);
        Iterator it = keyguardSecurityViewFlipperController.mChildren.iterator();
        while (it.hasNext()) {
            ((KeyguardInputViewController) it.next()).reset();
        }
    }

    public final void updateResources() {
        int i;
        Resources resources = ((KeyguardHostView) this.mView).getResources();
        if (resources.getBoolean(C1777R.bool.can_use_one_handed_bouncer)) {
            i = resources.getInteger(C1777R.integer.keyguard_host_view_one_handed_gravity);
        } else {
            i = resources.getInteger(C1777R.integer.keyguard_host_view_gravity);
        }
        if (((KeyguardHostView) this.mView).getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ((KeyguardHostView) this.mView).getLayoutParams();
            if (layoutParams.gravity != i) {
                layoutParams.gravity = i;
                ((KeyguardHostView) this.mView).setLayoutParams(layoutParams);
            }
        }
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.mKeyguardSecurityContainerController;
        if (keyguardSecurityContainerController != null) {
            Objects.requireNonNull(keyguardSecurityContainerController);
            int i2 = keyguardSecurityContainerController.getResources().getConfiguration().orientation;
            if (i2 != keyguardSecurityContainerController.mLastOrientation) {
                keyguardSecurityContainerController.mLastOrientation = i2;
                keyguardSecurityContainerController.configureMode();
            }
        }
    }

    public final boolean interceptMediaKey(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() == 0) {
            if (!(keyCode == 79 || keyCode == 130 || keyCode == 222)) {
                if (!(keyCode == 126 || keyCode == 127)) {
                    switch (keyCode) {
                        case 85:
                            break;
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                            break;
                        default:
                            return false;
                    }
                }
                TelephonyManager telephonyManager = this.mTelephonyManager;
                if (!(telephonyManager == null || telephonyManager.getCallState() == 0)) {
                    return true;
                }
            }
            this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        } else if (keyEvent.getAction() != 1) {
            return false;
        } else {
            if (!(keyCode == 79 || keyCode == 130 || keyCode == 222 || keyCode == 126 || keyCode == 127)) {
                switch (keyCode) {
                    case 85:
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                        break;
                    default:
                        return false;
                }
            }
            this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        }
    }
}
