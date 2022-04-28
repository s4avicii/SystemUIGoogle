package com.android.systemui.biometrics;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Point;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsControllerOverlay.kt */
public final class UdfpsControllerOverlay {
    public final AccessibilityManager accessibilityManager;
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final ConfigurationController configurationController;
    public final Context context;
    public final IUdfpsOverlayControllerCallback controllerCallback;
    public final WindowManager.LayoutParams coreLayoutParams;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final UdfpsEnrollHelper enrollHelper;
    public UdfpsHbmProvider hbmProvider;
    public final LayoutInflater inflater;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Function3<View, MotionEvent, Boolean, Boolean> onTouch;
    public UdfpsControllerOverlay$show$1$1 overlayTouchListener;
    public UdfpsView overlayView;
    public final PanelExpansionStateManager panelExpansionStateManager;
    public final int requestReason;
    public final FingerprintSensorPropertiesInternal sensorProps;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final StatusBarStateController statusBarStateController;
    public final SystemClock systemClock;
    public final LockscreenShadeTransitionController transitionController;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final WindowManager windowManager;

    public UdfpsControllerOverlay(Context context2, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager2, AccessibilityManager accessibilityManager2, StatusBarStateController statusBarStateController2, PanelExpansionStateManager panelExpansionStateManager2, StatusBarKeyguardViewManager statusBarKeyguardViewManager2, KeyguardUpdateMonitor keyguardUpdateMonitor2, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager2, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController2, SystemClock systemClock2, KeyguardStateController keyguardStateController2, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController2, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, UdfpsHbmProvider udfpsHbmProvider, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4 udfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4, ActivityLaunchAnimator activityLaunchAnimator2) {
        UdfpsEnrollHelper udfpsEnrollHelper;
        int i2 = i;
        this.context = context2;
        this.inflater = layoutInflater;
        this.windowManager = windowManager2;
        this.accessibilityManager = accessibilityManager2;
        this.statusBarStateController = statusBarStateController2;
        this.panelExpansionStateManager = panelExpansionStateManager2;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager2;
        this.transitionController = lockscreenShadeTransitionController;
        this.configurationController = configurationController2;
        this.systemClock = systemClock2;
        this.keyguardStateController = keyguardStateController2;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController2;
        this.sensorProps = fingerprintSensorPropertiesInternal;
        this.hbmProvider = udfpsHbmProvider;
        this.requestReason = i2;
        this.controllerCallback = iUdfpsOverlayControllerCallback;
        this.onTouch = udfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4;
        this.activityLaunchAnimator = activityLaunchAnimator2;
        boolean z = false;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2009, 0, -3);
        layoutParams.setTitle("UdfpsControllerOverlay");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870912;
        this.coreLayoutParams = layoutParams;
        if ((i2 == 1 || i2 == 2) ? true : z) {
            FingerprintManager fingerprintManager2 = fingerprintManager;
            udfpsEnrollHelper = new UdfpsEnrollHelper(context2, fingerprintManager, i2);
        } else {
            udfpsEnrollHelper = null;
        }
        this.enrollHelper = udfpsEnrollHelper;
    }

    public final WindowManager.LayoutParams updateForLocation(WindowManager.LayoutParams layoutParams, SensorLocationInternal sensorLocationInternal, UdfpsAnimationViewController<?> udfpsAnimationViewController) {
        int i;
        int i2;
        boolean z = false;
        if (udfpsAnimationViewController == null) {
            i = 0;
        } else {
            i = udfpsAnimationViewController.getPaddingX();
        }
        if (udfpsAnimationViewController == null) {
            i2 = 0;
        } else {
            i2 = udfpsAnimationViewController.getPaddingY();
        }
        layoutParams.flags = 25166120;
        if (udfpsAnimationViewController != null && (udfpsAnimationViewController instanceof UdfpsKeyguardViewController)) {
            layoutParams.flags = 25428264;
        }
        int i3 = sensorLocationInternal.sensorLocationX;
        int i4 = sensorLocationInternal.sensorRadius;
        layoutParams.x = (i3 - i4) - i;
        layoutParams.y = (sensorLocationInternal.sensorLocationY - i4) - i2;
        int i5 = i4 * 2;
        layoutParams.height = (i * 2) + i5;
        layoutParams.width = (i2 * 2) + i5;
        Point point = new Point();
        Display display = this.context.getDisplay();
        Intrinsics.checkNotNull(display);
        display.getRealSize(point);
        Display display2 = this.context.getDisplay();
        Intrinsics.checkNotNull(display2);
        int rotation = display2.getRotation();
        boolean z2 = true;
        if (rotation == 1) {
            if (udfpsAnimationViewController instanceof UdfpsKeyguardViewController) {
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.keyguardUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor2);
                if (!keyguardUpdateMonitor2.mGoingToSleep && this.keyguardStateController.isOccluded()) {
                    z = true;
                }
                z2 = z;
            }
            if (!z2) {
                Log.v("UdfpsControllerOverlay", "skip rotating udfps location ROTATION_90");
            } else {
                Log.v("UdfpsControllerOverlay", "rotate udfps location ROTATION_90");
                int i6 = sensorLocationInternal.sensorLocationY;
                int i7 = sensorLocationInternal.sensorRadius;
                layoutParams.x = (i6 - i7) - i;
                layoutParams.y = ((point.y - sensorLocationInternal.sensorLocationX) - i7) - i2;
            }
        } else if (rotation == 3) {
            if (udfpsAnimationViewController instanceof UdfpsKeyguardViewController) {
                KeyguardUpdateMonitor keyguardUpdateMonitor3 = this.keyguardUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor3);
                if (!keyguardUpdateMonitor3.mGoingToSleep && this.keyguardStateController.isOccluded()) {
                    z = true;
                }
                z2 = z;
            }
            if (!z2) {
                Log.v("UdfpsControllerOverlay", "skip rotating udfps location ROTATION_270");
            } else {
                Log.v("UdfpsControllerOverlay", "rotate udfps location ROTATION_270");
                int i8 = point.x - sensorLocationInternal.sensorLocationY;
                int i9 = sensorLocationInternal.sensorRadius;
                layoutParams.x = (i8 - i9) - i;
                layoutParams.y = (sensorLocationInternal.sensorLocationX - i9) - i2;
            }
        }
        layoutParams.accessibilityTitle = " ";
        return layoutParams;
    }

    public final UdfpsAnimationViewController<?> inflateUdfpsAnimation(UdfpsView udfpsView, UdfpsController udfpsController) {
        UdfpsView udfpsView2 = udfpsView;
        switch (this.requestReason) {
            case 1:
            case 2:
                View inflate = this.inflater.inflate(C1777R.layout.udfps_enroll_view, (ViewGroup) null);
                Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsEnrollView");
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) inflate;
                udfpsView2.addView(udfpsEnrollView);
                FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.sensorProps;
                View findViewById = udfpsEnrollView.findViewById(C1777R.C1779id.udfps_enroll_accessibility_view);
                int i = fingerprintSensorPropertiesInternal.getLocation().sensorRadius * 2;
                ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
                layoutParams.width = i;
                layoutParams.height = i;
                findViewById.setLayoutParams(layoutParams);
                findViewById.requestLayout();
                UdfpsEnrollHelper udfpsEnrollHelper = this.enrollHelper;
                if (udfpsEnrollHelper != null) {
                    return new UdfpsEnrollViewController(udfpsEnrollView, udfpsEnrollHelper, this.statusBarStateController, this.panelExpansionStateManager, this.dialogManager, this.dumpManager);
                }
                throw new IllegalStateException("no enrollment helper");
            case 3:
                View inflate2 = this.inflater.inflate(C1777R.layout.udfps_bp_view, (ViewGroup) null);
                Objects.requireNonNull(inflate2, "null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsBpView");
                UdfpsBpView udfpsBpView = (UdfpsBpView) inflate2;
                udfpsView2.addView(udfpsBpView);
                return new UdfpsBpViewController(udfpsBpView, this.statusBarStateController, this.panelExpansionStateManager, this.dialogManager, this.dumpManager);
            case 4:
                View inflate3 = this.inflater.inflate(C1777R.layout.udfps_keyguard_view, (ViewGroup) null);
                Objects.requireNonNull(inflate3, "null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsKeyguardView");
                UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) inflate3;
                udfpsView2.addView(udfpsKeyguardView);
                return new UdfpsKeyguardViewController(udfpsKeyguardView, this.statusBarStateController, this.panelExpansionStateManager, this.statusBarKeyguardViewManager, this.keyguardUpdateMonitor, this.dumpManager, this.transitionController, this.configurationController, this.systemClock, this.keyguardStateController, this.unlockedScreenOffAnimationController, this.dialogManager, udfpsController, this.activityLaunchAnimator);
            case 5:
            case FalsingManager.VERSION /*6*/:
                View inflate4 = this.inflater.inflate(C1777R.layout.udfps_fpm_other_view, (ViewGroup) null);
                Objects.requireNonNull(inflate4, "null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsFpmOtherView");
                UdfpsFpmOtherView udfpsFpmOtherView = (UdfpsFpmOtherView) inflate4;
                udfpsView2.addView(udfpsFpmOtherView);
                return new UdfpsFpmOtherViewController(udfpsFpmOtherView, this.statusBarStateController, this.panelExpansionStateManager, this.dialogManager, this.dumpManager);
            default:
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Animation for reason ");
                m.append(this.requestReason);
                m.append(" not supported yet");
                Log.e("UdfpsControllerOverlay", m.toString());
                return null;
        }
    }
}
