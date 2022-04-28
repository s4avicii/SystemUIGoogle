package com.android.keyguard;

import android.app.AlertDialog;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowInsetsAnimation;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.DejankUtils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Iterator;
import java.util.Objects;

public final class KeyguardSecurityContainerController extends ViewController<KeyguardSecurityContainer> implements KeyguardSecurityView {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final AdminSecondaryLockScreenController mAdminSecondaryLockScreenController;
    public final ConfigurationController mConfigurationController;
    public C05234 mConfigurationListener;
    public KeyguardSecurityModel.SecurityMode mCurrentSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    @VisibleForTesting
    public final Gefingerpoken mGlobalTouchListener = new Gefingerpoken() {
        public MotionEvent mTouchDown;

        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onTouchEvent(MotionEvent motionEvent) {
            boolean isOneHandedModeLeftAligned;
            if (motionEvent.getActionMasked() == 0) {
                KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView;
                Objects.requireNonNull(keyguardSecurityContainer);
                if (keyguardSecurityContainer.mCurrentMode == 1 && ((isOneHandedModeLeftAligned && motionEvent.getX() > ((float) ((KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView).getWidth()) / 2.0f) || (!(isOneHandedModeLeftAligned = ((KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView).isOneHandedModeLeftAligned()) && motionEvent.getX() <= ((float) ((KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView).getWidth()) / 2.0f))) {
                    KeyguardSecurityContainerController.this.mFalsingCollector.avoidGesture();
                }
                MotionEvent motionEvent2 = this.mTouchDown;
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                    this.mTouchDown = null;
                }
                this.mTouchDown = MotionEvent.obtain(motionEvent);
                return false;
            } else if (this.mTouchDown == null) {
                return false;
            } else {
                if (motionEvent.getActionMasked() != 1 && motionEvent.getActionMasked() != 3) {
                    return false;
                }
                this.mTouchDown.recycle();
                this.mTouchDown = null;
                return false;
            }
        }
    };
    public C05212 mKeyguardSecurityCallback;
    public final KeyguardStateController mKeyguardStateController;
    public int mLastOrientation = 0;
    public final LockPatternUtils mLockPatternUtils;
    public final MetricsLogger mMetricsLogger;
    public final KeyguardSecurityContainer.SecurityCallback mSecurityCallback;
    public final KeyguardSecurityModel mSecurityModel;
    public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
    public final SessionTracker mSessionTracker;
    public C05223 mSwipeListener;
    public final UiEventLogger mUiEventLogger;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;

    public static class Factory {
        public final AdminSecondaryLockScreenController.Factory mAdminSecondaryLockScreenControllerFactory;
        public final ConfigurationController mConfigurationController;
        public final FalsingCollector mFalsingCollector;
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public final GlobalSettings mGlobalSettings;
        public final KeyguardSecurityModel mKeyguardSecurityModel;
        public final KeyguardStateController mKeyguardStateController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LockPatternUtils mLockPatternUtils;
        public final MetricsLogger mMetricsLogger;
        public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
        public final SessionTracker mSessionTracker;
        public final UiEventLogger mUiEventLogger;
        public final UserSwitcherController mUserSwitcherController;
        public final KeyguardSecurityContainer mView;

        public Factory(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker) {
            this.mView = keyguardSecurityContainer;
            this.mAdminSecondaryLockScreenControllerFactory = factory;
            this.mLockPatternUtils = lockPatternUtils;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mKeyguardSecurityModel = keyguardSecurityModel;
            this.mMetricsLogger = metricsLogger;
            this.mUiEventLogger = uiEventLogger;
            this.mKeyguardStateController = keyguardStateController;
            this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
            this.mConfigurationController = configurationController;
            this.mFalsingCollector = falsingCollector;
            this.mFalsingManager = falsingManager;
            this.mFeatureFlags = featureFlags;
            this.mGlobalSettings = globalSettings;
            this.mUserSwitcherController = userSwitcherController;
            this.mSessionTracker = sessionTracker;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardSecurityContainerController(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardHostViewController.C04992 r16, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker) {
        super(keyguardSecurityContainer);
        AdminSecondaryLockScreenController.Factory factory2 = factory;
        C05212 r2 = new KeyguardSecurityCallback() {
            public final void dismiss(int i) {
                dismiss(i, false);
            }

            public final void dismiss(int i, boolean z) {
                ((KeyguardHostViewController.C04992) KeyguardSecurityContainerController.this.mSecurityCallback).dismiss(true, i, z);
            }

            public final void onCancelClicked() {
                KeyguardHostViewController.C04992 r0 = (KeyguardHostViewController.C04992) KeyguardSecurityContainerController.this.mSecurityCallback;
                Objects.requireNonNull(r0);
                KeyguardHostViewController.this.mViewMediatorCallback.onCancelClicked();
            }

            public final void onUserInput() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardSecurityContainerController.this.mUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                keyguardUpdateMonitor.stopListeningForFace();
            }

            /* JADX WARNING: Code restructure failed: missing block: B:24:0x0071, code lost:
                if (r5 != -10000) goto L_0x0075;
             */
            /* JADX WARNING: Removed duplicated region for block: B:27:0x0077  */
            /* JADX WARNING: Removed duplicated region for block: B:28:0x007f  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void reportUnlockAttempt(int r10, boolean r11, int r12) {
                /*
                    r9 = this;
                    com.android.keyguard.KeyguardSecurityContainerController r0 = com.android.keyguard.KeyguardSecurityContainerController.this
                    T r0 = r0.mView
                    com.android.keyguard.KeyguardSecurityContainer r0 = (com.android.keyguard.KeyguardSecurityContainer) r0
                    java.util.Objects.requireNonNull(r0)
                    int r0 = r0.mCurrentMode
                    r1 = 1
                    r2 = 2
                    if (r0 != r1) goto L_0x001f
                    com.android.keyguard.KeyguardSecurityContainerController r0 = com.android.keyguard.KeyguardSecurityContainerController.this
                    T r0 = r0.mView
                    com.android.keyguard.KeyguardSecurityContainer r0 = (com.android.keyguard.KeyguardSecurityContainer) r0
                    boolean r0 = r0.isOneHandedModeLeftAligned()
                    if (r0 == 0) goto L_0x001d
                    r0 = r1
                    goto L_0x0020
                L_0x001d:
                    r0 = r2
                    goto L_0x0020
                L_0x001f:
                    r0 = 0
                L_0x0020:
                    r3 = 64
                    r4 = 0
                    if (r11 == 0) goto L_0x0036
                    com.android.systemui.shared.system.SysUiStatsLog.write(r3, r2, r0)
                    com.android.keyguard.KeyguardSecurityContainerController r12 = com.android.keyguard.KeyguardSecurityContainerController.this
                    com.android.internal.widget.LockPatternUtils r12 = r12.mLockPatternUtils
                    r12.reportSuccessfulPasswordAttempt(r10)
                    com.android.keyguard.KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0 r10 = com.android.keyguard.KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0.INSTANCE
                    androidx.recyclerview.R$dimen.postOnBackgroundThread(r10)
                    goto L_0x00ba
                L_0x0036:
                    com.android.systemui.shared.system.SysUiStatsLog.write(r3, r1, r0)
                    com.android.keyguard.KeyguardSecurityContainerController r0 = com.android.keyguard.KeyguardSecurityContainerController.this
                    java.util.Objects.requireNonNull(r0)
                    com.android.internal.widget.LockPatternUtils r3 = r0.mLockPatternUtils
                    int r3 = r3.getCurrentFailedPasswordAttempts(r10)
                    int r3 = r3 + r1
                    boolean r5 = com.android.keyguard.KeyguardSecurityContainerController.DEBUG
                    java.lang.String r6 = "KeyguardSecurityView"
                    if (r5 == 0) goto L_0x0051
                    java.lang.String r5 = "reportFailedPatternAttempt: #"
                    androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r5, r3, r6)
                L_0x0051:
                    com.android.internal.widget.LockPatternUtils r5 = r0.mLockPatternUtils
                    android.app.admin.DevicePolicyManager r5 = r5.getDevicePolicyManager()
                    int r7 = r5.getMaximumFailedPasswordsForWipe(r4, r10)
                    if (r7 <= 0) goto L_0x005f
                    int r7 = r7 - r3
                    goto L_0x0062
                L_0x005f:
                    r7 = 2147483647(0x7fffffff, float:NaN)
                L_0x0062:
                    r8 = 5
                    if (r7 >= r8) goto L_0x009f
                    int r5 = r5.getProfileWithMinimumFailedPasswordsForWipe(r10)
                    if (r5 != r10) goto L_0x006f
                    if (r5 == 0) goto L_0x0074
                    r2 = 3
                    goto L_0x0075
                L_0x006f:
                    r8 = -10000(0xffffffffffffd8f0, float:NaN)
                    if (r5 == r8) goto L_0x0074
                    goto L_0x0075
                L_0x0074:
                    r2 = r1
                L_0x0075:
                    if (r7 <= 0) goto L_0x007f
                    T r5 = r0.mView
                    com.android.keyguard.KeyguardSecurityContainer r5 = (com.android.keyguard.KeyguardSecurityContainer) r5
                    r5.showAlmostAtWipeDialog(r3, r7, r2)
                    goto L_0x009f
                L_0x007f:
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "Too many unlock attempts; user "
                    r7.append(r8)
                    r7.append(r5)
                    java.lang.String r5 = " will be wiped!"
                    r7.append(r5)
                    java.lang.String r5 = r7.toString()
                    android.util.Slog.i(r6, r5)
                    T r5 = r0.mView
                    com.android.keyguard.KeyguardSecurityContainer r5 = (com.android.keyguard.KeyguardSecurityContainer) r5
                    r5.showWipeDialog(r3, r2)
                L_0x009f:
                    com.android.internal.widget.LockPatternUtils r2 = r0.mLockPatternUtils
                    r2.reportFailedPasswordAttempt(r10)
                    if (r12 <= 0) goto L_0x00ba
                    com.android.internal.widget.LockPatternUtils r2 = r0.mLockPatternUtils
                    r2.reportPasswordLockout(r12, r10)
                    T r2 = r0.mView
                    com.android.keyguard.KeyguardSecurityContainer r2 = (com.android.keyguard.KeyguardSecurityContainer) r2
                    com.android.internal.widget.LockPatternUtils r3 = r0.mLockPatternUtils
                    com.android.keyguard.KeyguardSecurityModel r0 = r0.mSecurityModel
                    com.android.keyguard.KeyguardSecurityModel$SecurityMode r0 = r0.getSecurityMode(r10)
                    r2.showTimeoutDialog(r10, r12, r3, r0)
                L_0x00ba:
                    com.android.keyguard.KeyguardSecurityContainerController r10 = com.android.keyguard.KeyguardSecurityContainerController.this
                    com.android.internal.logging.MetricsLogger r10 = r10.mMetricsLogger
                    android.metrics.LogMaker r12 = new android.metrics.LogMaker
                    r0 = 197(0xc5, float:2.76E-43)
                    r12.<init>(r0)
                    if (r11 == 0) goto L_0x00ca
                    r0 = 10
                    goto L_0x00cc
                L_0x00ca:
                    r0 = 11
                L_0x00cc:
                    android.metrics.LogMaker r12 = r12.setType(r0)
                    r10.write(r12)
                    com.android.keyguard.KeyguardSecurityContainerController r9 = com.android.keyguard.KeyguardSecurityContainerController.this
                    com.android.internal.logging.UiEventLogger r10 = r9.mUiEventLogger
                    if (r11 == 0) goto L_0x00dc
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r11 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_SUCCESS
                    goto L_0x00de
                L_0x00dc:
                    com.android.keyguard.KeyguardSecurityContainer$BouncerUiEvent r11 = com.android.keyguard.KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_FAILURE
                L_0x00de:
                    com.android.systemui.log.SessionTracker r9 = r9.mSessionTracker
                    java.util.Objects.requireNonNull(r9)
                    java.util.HashMap r9 = r9.mSessionToInstanceId
                    java.lang.Integer r12 = java.lang.Integer.valueOf(r1)
                    java.lang.Object r9 = r9.getOrDefault(r12, r4)
                    com.android.internal.logging.InstanceId r9 = (com.android.internal.logging.InstanceId) r9
                    r10.log(r11, r9)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainerController.C05212.reportUnlockAttempt(int, boolean, int):void");
            }

            public final void reset() {
                KeyguardHostViewController.C04992 r0 = (KeyguardHostViewController.C04992) KeyguardSecurityContainerController.this.mSecurityCallback;
                Objects.requireNonNull(r0);
                KeyguardHostViewController.this.mViewMediatorCallback.resetKeyguard();
            }

            public final void userActivity() {
                KeyguardSecurityContainer.SecurityCallback securityCallback = KeyguardSecurityContainerController.this.mSecurityCallback;
                if (securityCallback != null) {
                    KeyguardHostViewController.C04992 r0 = (KeyguardHostViewController.C04992) securityCallback;
                    Objects.requireNonNull(r0);
                    KeyguardHostViewController.this.mViewMediatorCallback.userActivity();
                }
            }
        };
        this.mKeyguardSecurityCallback = r2;
        this.mSwipeListener = new KeyguardSecurityContainer.SwipeListener() {
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() {
            public final void onThemeChanged() {
                KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = KeyguardSecurityContainerController.this.mSecurityViewFlipperController;
                Objects.requireNonNull(keyguardSecurityViewFlipperController);
                Iterator it = keyguardSecurityViewFlipperController.mChildren.iterator();
                while (it.hasNext()) {
                    ((KeyguardInputViewController) it.next()).reloadColors();
                }
            }

            public final void onUiModeChanged() {
                KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = KeyguardSecurityContainerController.this.mSecurityViewFlipperController;
                Objects.requireNonNull(keyguardSecurityViewFlipperController);
                Iterator it = keyguardSecurityViewFlipperController.mChildren.iterator();
                while (it.hasNext()) {
                    ((KeyguardInputViewController) it.next()).reloadColors();
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSecurityModel = keyguardSecurityModel;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mSecurityCallback = r16;
        this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
        Objects.requireNonNull(factory);
        this.mAdminSecondaryLockScreenController = new AdminSecondaryLockScreenController(factory2.mContext, factory2.mParent, factory2.mUpdateMonitor, r2, factory2.mHandler);
        this.mConfigurationController = configurationController;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mFeatureFlags = featureFlags;
        this.mGlobalSettings = globalSettings;
        this.mSessionTracker = sessionTracker;
    }

    public final void configureMode() {
        boolean z;
        boolean z2;
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        int i = 0;
        if (securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk) {
            z = true;
        } else {
            z = false;
        }
        if (!this.mFeatureFlags.isEnabled(Flags.BOUNCER_USER_SWITCHER) || z) {
            KeyguardSecurityModel.SecurityMode securityMode2 = this.mCurrentSecurityMode;
            if (securityMode2 == KeyguardSecurityModel.SecurityMode.Pattern || securityMode2 == KeyguardSecurityModel.SecurityMode.PIN) {
                z2 = getResources().getBoolean(C1777R.bool.can_use_one_handed_bouncer);
            } else {
                z2 = false;
            }
            if (z2) {
                i = 1;
            }
        } else {
            i = 2;
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        GlobalSettings globalSettings = this.mGlobalSettings;
        FalsingManager falsingManager = this.mFalsingManager;
        UserSwitcherController userSwitcherController = this.mUserSwitcherController;
        Objects.requireNonNull(keyguardSecurityContainer);
        if (keyguardSecurityContainer.mCurrentMode != i) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Switching mode from ");
            m.append(KeyguardSecurityContainer.modeToString(keyguardSecurityContainer.mCurrentMode));
            m.append(" to ");
            m.append(KeyguardSecurityContainer.modeToString(i));
            Log.i("KeyguardSecurityView", m.toString());
            keyguardSecurityContainer.mCurrentMode = i;
            if (i == 1) {
                keyguardSecurityContainer.mViewMode = new KeyguardSecurityContainer.OneHandedViewMode();
            } else if (i != 2) {
                keyguardSecurityContainer.mViewMode = new KeyguardSecurityContainer.DefaultViewMode();
            } else {
                keyguardSecurityContainer.mViewMode = new KeyguardSecurityContainer.UserSwitcherViewMode();
            }
            keyguardSecurityContainer.mGlobalSettings = globalSettings;
            keyguardSecurityContainer.mFalsingManager = falsingManager;
            keyguardSecurityContainer.mUserSwitcherController = userSwitcherController;
            KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = keyguardSecurityContainer.mSecurityViewFlipper;
            if (keyguardSecurityViewFlipper != null && globalSettings != null && falsingManager != null && userSwitcherController != null) {
                keyguardSecurityContainer.mViewMode.init(keyguardSecurityContainer, globalSettings, keyguardSecurityViewFlipper, falsingManager, userSwitcherController);
            }
        }
    }

    public final KeyguardInputViewController<KeyguardInputView> getCurrentSecurityController() {
        return this.mSecurityViewFlipperController.getSecurityView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback);
    }

    public final void onInit() {
        this.mSecurityViewFlipperController.init();
    }

    public final void onPause() {
        this.mAdminSecondaryLockScreenController.hide();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController().onPause();
        }
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        Objects.requireNonNull(keyguardSecurityContainer);
        AlertDialog alertDialog = keyguardSecurityContainer.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            keyguardSecurityContainer.mAlertDialog = null;
        }
        keyguardSecurityContainer.mSecurityViewFlipper.setWindowInsetsAnimationCallback((WindowInsetsAnimation.Callback) null);
        keyguardSecurityContainer.mViewMode.reset();
    }

    public final void onStartingToHide() {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController().onStartingToHide();
        }
    }

    public final void onViewAttached() {
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        C05223 r1 = this.mSwipeListener;
        Objects.requireNonNull(keyguardSecurityContainer);
        keyguardSecurityContainer.mSwipeListener = r1;
        KeyguardSecurityContainer keyguardSecurityContainer2 = (KeyguardSecurityContainer) this.mView;
        Gefingerpoken gefingerpoken = this.mGlobalTouchListener;
        Objects.requireNonNull(keyguardSecurityContainer2);
        keyguardSecurityContainer2.mMotionEventListeners.add(gefingerpoken);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
    }

    public final void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        Gefingerpoken gefingerpoken = this.mGlobalTouchListener;
        Objects.requireNonNull(keyguardSecurityContainer);
        keyguardSecurityContainer.mMotionEventListeners.remove(gefingerpoken);
    }

    public final void showPrimarySecurityScreen(boolean z) {
        KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) DejankUtils.whitelistIpcs(new KeyguardSecurityContainerController$$ExternalSyntheticLambda0(this));
        if (DEBUG) {
            Log.v("KeyguardSecurityView", "showPrimarySecurityScreen(turningOff=" + z + ")");
        }
        showSecurityScreen(securityMode);
    }

    @VisibleForTesting
    public void showSecurityScreen(KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z;
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showSecurityScreen(" + securityMode + ")");
        }
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != this.mCurrentSecurityMode) {
            KeyguardInputViewController<KeyguardInputView> currentSecurityController = getCurrentSecurityController();
            if (currentSecurityController != null) {
                currentSecurityController.onPause();
            }
            this.mCurrentSecurityMode = securityMode;
            KeyguardInputViewController<KeyguardInputView> currentSecurityController2 = getCurrentSecurityController();
            if (currentSecurityController2 != null) {
                currentSecurityController2.onResume(2);
                KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = this.mSecurityViewFlipperController;
                Objects.requireNonNull(keyguardSecurityViewFlipperController);
                int indexOfChild = ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).indexOfChild(currentSecurityController2.mView);
                if (indexOfChild != -1) {
                    ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).setDisplayedChild(indexOfChild);
                }
                configureMode();
            }
            KeyguardSecurityContainer.SecurityCallback securityCallback = this.mSecurityCallback;
            if (currentSecurityController2 == null || !currentSecurityController2.needsInput()) {
                z = false;
            } else {
                z = true;
            }
            KeyguardHostViewController.C04992 r3 = (KeyguardHostViewController.C04992) securityCallback;
            Objects.requireNonNull(r3);
            KeyguardHostViewController.this.mViewMediatorCallback.setNeedsInput(z);
        }
    }

    public final boolean needsInput() {
        return getCurrentSecurityController().needsInput();
    }
}
