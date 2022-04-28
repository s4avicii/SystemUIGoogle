package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ UdfpsController.UdfpsOverlayController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IUdfpsOverlayControllerCallback f$2;

    public /* synthetic */ UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda3(UdfpsController.UdfpsOverlayController udfpsOverlayController, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
        this.f$0 = udfpsOverlayController;
        this.f$1 = i;
        this.f$2 = iUdfpsOverlayControllerCallback;
    }

    public final void run() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.f$0;
        IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback = this.f$2;
        Objects.requireNonNull(udfpsOverlayController);
        UdfpsController udfpsController = UdfpsController.this;
        Context context = udfpsController.mContext;
        FingerprintManager fingerprintManager = udfpsController.mFingerprintManager;
        LayoutInflater layoutInflater = udfpsController.mInflater;
        WindowManager windowManager = udfpsController.mWindowManager;
        AccessibilityManager accessibilityManager = udfpsController.mAccessibilityManager;
        StatusBarStateController statusBarStateController = udfpsController.mStatusBarStateController;
        PanelExpansionStateManager panelExpansionStateManager = udfpsController.mPanelExpansionStateManager;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = udfpsController.mKeyguardViewManager;
        KeyguardUpdateMonitor keyguardUpdateMonitor = udfpsController.mKeyguardUpdateMonitor;
        SystemUIDialogManager systemUIDialogManager = udfpsController.mDialogManager;
        DumpManager dumpManager = udfpsController.mDumpManager;
        LockscreenShadeTransitionController lockscreenShadeTransitionController = udfpsController.mLockscreenShadeTransitionController;
        ConfigurationController configurationController = udfpsController.mConfigurationController;
        UdfpsControllerOverlay udfpsControllerOverlay = r2;
        SystemClock systemClock = udfpsController.mSystemClock;
        KeyguardStateController keyguardStateController = udfpsController.mKeyguardStateController;
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = udfpsController.mUnlockedScreenOffAnimationController;
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = udfpsController.mSensorProps;
        UdfpsHbmProvider udfpsHbmProvider = udfpsController.mHbmProvider;
        UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4 udfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4 = r1;
        UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4 udfpsController$UdfpsOverlayController$$ExternalSyntheticLambda42 = new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4(udfpsController);
        UdfpsControllerOverlay udfpsControllerOverlay2 = new UdfpsControllerOverlay(context, fingerprintManager, layoutInflater, windowManager, accessibilityManager, statusBarStateController, panelExpansionStateManager, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, systemClock, keyguardStateController, unlockedScreenOffAnimationController, fingerprintSensorPropertiesInternal, udfpsHbmProvider, this.f$1, iUdfpsOverlayControllerCallback, udfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4, udfpsController.mActivityLaunchAnimator);
        udfpsController.showUdfpsOverlay(udfpsControllerOverlay);
    }
}
