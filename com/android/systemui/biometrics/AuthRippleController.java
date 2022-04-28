package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.DisplayMetrics;
import android.view.Display;
import androidx.preference.R$id;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.charging.DwellRippleShader;
import com.android.systemui.statusbar.charging.RippleShader;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Provider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController extends ViewController<AuthRippleView> implements KeyguardStateController.Callback, WakefulnessLifecycle.Observer {
    public final AuthController authController;
    public final AuthRippleController$authControllerCallback$1 authControllerCallback = new AuthRippleController$authControllerCallback$1(this);
    public final BiometricUnlockController biometricUnlockController;
    public final KeyguardBypassController bypassController;
    public CircleReveal circleReveal;
    public final CommandRegistry commandRegistry;
    public final AuthRippleController$configurationChangedListener$1 configurationChangedListener = new AuthRippleController$configurationChangedListener$1(this);
    public final ConfigurationController configurationController;
    public PointF faceSensorLocation;
    public PointF fingerprintSensorLocation;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final AuthRippleController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback = new AuthRippleController$keyguardUpdateMonitorCallback$1(this);
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean startLightRevealScrimOnKeyguardFadingAway;
    public final StatusBar statusBar;
    public final StatusBarStateController statusBarStateController;
    public final Context sysuiContext;
    public UdfpsController udfpsController;
    public final AuthRippleController$udfpsControllerCallback$1 udfpsControllerCallback = new AuthRippleController$udfpsControllerCallback$1(this);
    public final Provider<UdfpsController> udfpsControllerProvider;
    public float udfpsRadius = -1.0f;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: AuthRippleController.kt */
    public final class AuthRippleCommand implements Command {
        public AuthRippleCommand() {
        }

        public final void invalidCommand(PrintWriter printWriter) {
            printWriter.println("invalid command");
            printWriter.println("Usage: adb shell cmd statusbar auth-ripple <command>");
            printWriter.println("Available commands:");
            printWriter.println("  dwell");
            printWriter.println("  fingerprint");
            printWriter.println("  face");
            printWriter.println("  custom <x-location: int> <y-location: int>");
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x00a5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void execute(java.io.PrintWriter r6, java.util.List<java.lang.String> r7) {
            /*
                r5 = this;
                boolean r0 = r7.isEmpty()
                if (r0 == 0) goto L_0x000b
                r5.invalidCommand(r6)
                goto L_0x0140
            L_0x000b:
                r0 = 0
                java.lang.Object r0 = r7.get(r0)
                java.lang.String r0 = (java.lang.String) r0
                int r1 = r0.hashCode()
                switch(r1) {
                    case -1375934236: goto L_0x0117;
                    case -1349088399: goto L_0x0078;
                    case 3135069: goto L_0x0053;
                    case 95997746: goto L_0x001b;
                    default: goto L_0x0019;
                }
            L_0x0019:
                goto L_0x013d
            L_0x001b:
                java.lang.String r7 = "dwell"
                boolean r7 = r0.equals(r7)
                if (r7 != 0) goto L_0x0025
                goto L_0x013d
            L_0x0025:
                com.android.systemui.biometrics.AuthRippleController r7 = com.android.systemui.biometrics.AuthRippleController.this
                com.android.systemui.biometrics.AuthRippleController.access$showDwellRipple(r7)
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r0 = "lock screen dwell ripple: \n\tsensorLocation="
                r7.append(r0)
                com.android.systemui.biometrics.AuthRippleController r0 = com.android.systemui.biometrics.AuthRippleController.this
                java.util.Objects.requireNonNull(r0)
                android.graphics.PointF r0 = r0.fingerprintSensorLocation
                r7.append(r0)
                java.lang.String r0 = "\n\tudfpsRadius="
                r7.append(r0)
                com.android.systemui.biometrics.AuthRippleController r5 = com.android.systemui.biometrics.AuthRippleController.this
                float r5 = r5.udfpsRadius
                r7.append(r5)
                java.lang.String r5 = r7.toString()
                r6.println(r5)
                goto L_0x0140
            L_0x0053:
                java.lang.String r7 = "face"
                boolean r7 = r0.equals(r7)
                if (r7 != 0) goto L_0x005d
                goto L_0x013d
            L_0x005d:
                com.android.systemui.biometrics.AuthRippleController r7 = com.android.systemui.biometrics.AuthRippleController.this
                r7.updateSensorLocation()
                com.android.systemui.biometrics.AuthRippleController r7 = com.android.systemui.biometrics.AuthRippleController.this
                android.graphics.PointF r7 = r7.faceSensorLocation
                java.lang.String r0 = "face ripple sensorLocation="
                java.lang.String r7 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r7)
                r6.println(r7)
                com.android.systemui.biometrics.AuthRippleController r5 = com.android.systemui.biometrics.AuthRippleController.this
                android.hardware.biometrics.BiometricSourceType r6 = android.hardware.biometrics.BiometricSourceType.FACE
                r5.showRipple(r6)
                goto L_0x0140
            L_0x0078:
                java.lang.String r1 = "custom"
                boolean r0 = r0.equals(r1)
                if (r0 != 0) goto L_0x0082
                goto L_0x013d
            L_0x0082:
                int r0 = r7.size()
                r1 = 3
                if (r0 != r1) goto L_0x0113
                r0 = 1
                java.lang.Object r1 = r7.get(r0)
                java.lang.String r1 = (java.lang.String) r1
                r2 = 0
                kotlin.text.Regex r3 = kotlin.text.ScreenFloatValueRegEx.value     // Catch:{ NumberFormatException -> 0x00a2 }
                boolean r3 = r3.matches(r1)     // Catch:{ NumberFormatException -> 0x00a2 }
                if (r3 == 0) goto L_0x00a2
                float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ NumberFormatException -> 0x00a2 }
                java.lang.Float r1 = java.lang.Float.valueOf(r1)     // Catch:{ NumberFormatException -> 0x00a2 }
                goto L_0x00a3
            L_0x00a2:
                r1 = r2
            L_0x00a3:
                if (r1 == 0) goto L_0x0113
                r1 = 2
                java.lang.Object r3 = r7.get(r1)
                java.lang.String r3 = (java.lang.String) r3
                kotlin.text.Regex r4 = kotlin.text.ScreenFloatValueRegEx.value     // Catch:{ NumberFormatException -> 0x00bc }
                boolean r4 = r4.matches(r3)     // Catch:{ NumberFormatException -> 0x00bc }
                if (r4 == 0) goto L_0x00bc
                float r3 = java.lang.Float.parseFloat(r3)     // Catch:{ NumberFormatException -> 0x00bc }
                java.lang.Float r2 = java.lang.Float.valueOf(r3)     // Catch:{ NumberFormatException -> 0x00bc }
            L_0x00bc:
                if (r2 != 0) goto L_0x00bf
                goto L_0x0113
            L_0x00bf:
                java.lang.String r2 = "custom ripple sensorLocation="
                java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
                java.lang.Object r3 = r7.get(r0)
                java.lang.String r3 = (java.lang.String) r3
                float r3 = java.lang.Float.parseFloat(r3)
                r2.append(r3)
                java.lang.String r3 = ", "
                r2.append(r3)
                java.lang.Object r3 = r7.get(r1)
                java.lang.String r3 = (java.lang.String) r3
                float r3 = java.lang.Float.parseFloat(r3)
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r6.println(r2)
                com.android.systemui.biometrics.AuthRippleController r6 = com.android.systemui.biometrics.AuthRippleController.this
                T r6 = r6.mView
                com.android.systemui.biometrics.AuthRippleView r6 = (com.android.systemui.biometrics.AuthRippleView) r6
                android.graphics.PointF r2 = new android.graphics.PointF
                java.lang.Object r0 = r7.get(r0)
                java.lang.String r0 = (java.lang.String) r0
                float r0 = java.lang.Float.parseFloat(r0)
                java.lang.Object r7 = r7.get(r1)
                java.lang.String r7 = (java.lang.String) r7
                float r7 = java.lang.Float.parseFloat(r7)
                r2.<init>(r0, r7)
                r6.setSensorLocation(r2)
                com.android.systemui.biometrics.AuthRippleController r5 = com.android.systemui.biometrics.AuthRippleController.this
                r5.showUnlockedRipple()
                goto L_0x0140
            L_0x0113:
                r5.invalidCommand(r6)
                return
            L_0x0117:
                java.lang.String r7 = "fingerprint"
                boolean r7 = r0.equals(r7)
                if (r7 != 0) goto L_0x0120
                goto L_0x013d
            L_0x0120:
                com.android.systemui.biometrics.AuthRippleController r7 = com.android.systemui.biometrics.AuthRippleController.this
                r7.updateSensorLocation()
                com.android.systemui.biometrics.AuthRippleController r7 = com.android.systemui.biometrics.AuthRippleController.this
                java.util.Objects.requireNonNull(r7)
                android.graphics.PointF r7 = r7.fingerprintSensorLocation
                java.lang.String r0 = "fingerprint ripple sensorLocation="
                java.lang.String r7 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r7)
                r6.println(r7)
                com.android.systemui.biometrics.AuthRippleController r5 = com.android.systemui.biometrics.AuthRippleController.this
                android.hardware.biometrics.BiometricSourceType r6 = android.hardware.biometrics.BiometricSourceType.FINGERPRINT
                r5.showRipple(r6)
                goto L_0x0140
            L_0x013d:
                r5.invalidCommand(r6)
            L_0x0140:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.AuthRippleController.AuthRippleCommand.execute(java.io.PrintWriter, java.util.List):void");
        }
    }

    /* renamed from: getStartLightRevealScrimOnKeyguardFadingAway$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m33x6de90581() {
    }

    public final void onStartedGoingToSleep() {
        this.startLightRevealScrimOnKeyguardFadingAway = false;
    }

    public final void onInit() {
        AuthRippleView authRippleView = (AuthRippleView) this.mView;
        Objects.requireNonNull(authRippleView);
        authRippleView.alphaInDuration = (long) this.sysuiContext.getResources().getInteger(C1777R.integer.auth_ripple_alpha_in_duration);
    }

    public final void onKeyguardFadingAwayChanged() {
        if (this.keyguardStateController.isKeyguardFadingAway()) {
            StatusBar statusBar2 = this.statusBar;
            Objects.requireNonNull(statusBar2);
            LightRevealScrim lightRevealScrim = statusBar2.mLightRevealScrim;
            if (this.startLightRevealScrimOnKeyguardFadingAway && lightRevealScrim != null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.1f, 1.0f});
                ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                ofFloat.setDuration(1533);
                ofFloat.setStartDelay(this.keyguardStateController.getKeyguardFadingAwayDelay());
                ofFloat.addUpdateListener(new AuthRippleController$onKeyguardFadingAwayChanged$1$1(lightRevealScrim, this));
                ofFloat.addListener(new AuthRippleController$onKeyguardFadingAwayChanged$1$2(lightRevealScrim, this));
                ofFloat.start();
                this.startLightRevealScrimOnKeyguardFadingAway = false;
            }
        }
    }

    public void onViewAttached() {
        this.authController.addCallback(this.authControllerCallback);
        updateRippleColor();
        updateSensorLocation();
        updateUdfpsDependentParams();
        UdfpsController udfpsController2 = this.udfpsController;
        if (udfpsController2 != null) {
            udfpsController2.mCallbacks.add(this.udfpsControllerCallback);
        }
        this.configurationController.addCallback(this.configurationChangedListener);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        this.keyguardStateController.addCallback(this);
        WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle2);
        wakefulnessLifecycle2.mObservers.add(this);
        this.commandRegistry.registerCommand("auth-ripple", new AuthRippleController$onViewAttached$1(this));
    }

    public void onViewDetached() {
        UdfpsController udfpsController2 = this.udfpsController;
        if (udfpsController2 != null) {
            udfpsController2.mCallbacks.remove(this.udfpsControllerCallback);
        }
        AuthController authController2 = this.authController;
        AuthRippleController$authControllerCallback$1 authRippleController$authControllerCallback$1 = this.authControllerCallback;
        Objects.requireNonNull(authController2);
        authController2.mCallbacks.remove(authRippleController$authControllerCallback$1);
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        this.configurationController.removeCallback(this.configurationChangedListener);
        this.keyguardStateController.removeCallback(this);
        WakefulnessLifecycle wakefulnessLifecycle2 = this.wakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle2);
        wakefulnessLifecycle2.mObservers.remove(this);
        CommandRegistry commandRegistry2 = this.commandRegistry;
        Objects.requireNonNull(commandRegistry2);
        synchronized (commandRegistry2) {
            commandRegistry2.commandMap.remove("auth-ripple");
        }
        this.notificationShadeWindowController.setForcePluginOpen(false, this);
    }

    public final void showRipple(BiometricSourceType biometricSourceType) {
        boolean z;
        PointF pointF;
        KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.keyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor2);
        if (keyguardUpdateMonitor2.mKeyguardIsVisible) {
            KeyguardUpdateMonitor keyguardUpdateMonitor3 = this.keyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor3);
            if (keyguardUpdateMonitor3.mStrongAuthTracker.getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                updateSensorLocation();
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && (pointF = this.fingerprintSensorLocation) != null) {
                    ((AuthRippleView) this.mView).setFingerprintSensorLocation(pointF, this.udfpsRadius);
                    showUnlockedRipple();
                } else if (biometricSourceType == BiometricSourceType.FACE && this.faceSensorLocation != null && this.bypassController.canBypass()) {
                    PointF pointF2 = this.faceSensorLocation;
                    Intrinsics.checkNotNull(pointF2);
                    ((AuthRippleView) this.mView).setSensorLocation(pointF2);
                    showUnlockedRipple();
                }
            }
        }
    }

    public final void showUnlockedRipple() {
        CircleReveal circleReveal2;
        this.notificationShadeWindowController.setForcePluginOpen(true, this);
        StatusBar statusBar2 = this.statusBar;
        Objects.requireNonNull(statusBar2);
        LightRevealScrim lightRevealScrim = statusBar2.mLightRevealScrim;
        if ((this.statusBarStateController.isDozing() || this.biometricUnlockController.isWakeAndUnlock()) && (circleReveal2 = this.circleReveal) != null) {
            if (lightRevealScrim != null) {
                lightRevealScrim.setRevealEffect(circleReveal2);
            }
            this.startLightRevealScrimOnKeyguardFadingAway = true;
        }
        AuthRippleView authRippleView = (AuthRippleView) this.mView;
        AuthRippleController$showUnlockedRipple$2 authRippleController$showUnlockedRipple$2 = new AuthRippleController$showUnlockedRipple$2(this);
        Objects.requireNonNull(authRippleView);
        if (!authRippleView.unlockedRippleInProgress) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.setDuration(1533);
            ofFloat.addUpdateListener(new AuthRippleView$startUnlockedRipple$rippleAnimator$1$1(authRippleView));
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 255});
            ofInt.setDuration(authRippleView.alphaInDuration);
            ofInt.addUpdateListener(new AuthRippleView$startUnlockedRipple$alphaInAnimator$1$1(authRippleView));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ofFloat, ofInt});
            animatorSet.addListener(new AuthRippleView$startUnlockedRipple$animatorSet$1$1(authRippleView, authRippleController$showUnlockedRipple$2));
            animatorSet.start();
        }
    }

    public final void updateRippleColor() {
        AuthRippleView authRippleView = (AuthRippleView) this.mView;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.sysuiContext, C1777R.attr.wallpaperTextColorAccent);
        Objects.requireNonNull(authRippleView);
        authRippleView.lockScreenColorVal = colorAttrDefaultColor;
        authRippleView.rippleShader.setColor(colorAttrDefaultColor);
        RippleShader rippleShader = authRippleView.rippleShader;
        Objects.requireNonNull(rippleShader);
        rippleShader.setColor(ColorUtils.setAlphaComponent(rippleShader.color, 255));
    }

    public final void updateSensorLocation() {
        PointF pointF;
        PointF pointF2;
        PointF pointF3;
        PointF pointF4;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = this.sysuiContext.getDisplay();
        if (display != null) {
            display.getRealMetrics(displayMetrics);
        }
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        AuthController authController2 = this.authController;
        Objects.requireNonNull(authController2);
        if (authController2.getUdfpsSensorLocation() != null) {
            pointF = authController2.getUdfpsSensorLocation();
        } else {
            pointF = authController2.mFingerprintLocation;
        }
        if (pointF != null) {
            int rotation = R$id.getRotation(this.sysuiContext);
            if (rotation != 1) {
                if (rotation == 2) {
                    pointF3 = new PointF(((float) i) - pointF.x, ((float) i2) - pointF.y);
                } else if (rotation != 3) {
                    pointF3 = new PointF(pointF.x, pointF.y);
                } else {
                    float f = (float) i;
                    float f2 = (float) i2;
                    pointF4 = new PointF((((float) 1) - (pointF.y / f)) * f, f2 * (pointF.x / f2));
                }
                this.fingerprintSensorLocation = pointF3;
            } else {
                float f3 = (float) i;
                float f4 = (float) i2;
                pointF4 = new PointF(f3 * (pointF.y / f3), (((float) 1) - (pointF.x / f4)) * f4);
            }
            pointF3 = pointF4;
            this.fingerprintSensorLocation = pointF3;
        }
        AuthController authController3 = this.authController;
        Objects.requireNonNull(authController3);
        if (authController3.mFaceProps == null || authController3.mFaceAuthSensorLocation == null) {
            pointF2 = null;
        } else {
            PointF pointF5 = authController3.mFaceAuthSensorLocation;
            pointF2 = new PointF(pointF5.x, pointF5.y);
        }
        this.faceSensorLocation = pointF2;
        PointF pointF6 = this.fingerprintSensorLocation;
        if (pointF6 != null) {
            float f5 = pointF6.x;
            float f6 = pointF6.y;
            StatusBar statusBar2 = this.statusBar;
            Objects.requireNonNull(statusBar2);
            float max = Math.max(f5, ((float) statusBar2.mDisplayMetrics.widthPixels) - pointF6.x);
            float f7 = pointF6.y;
            StatusBar statusBar3 = this.statusBar;
            Objects.requireNonNull(statusBar3);
            this.circleReveal = new CircleReveal(f5, f6, Math.max(max, Math.max(f7, ((float) statusBar3.mDisplayMetrics.heightPixels) - pointF6.y)));
        }
    }

    public final void updateUdfpsDependentParams() {
        UdfpsController udfpsController2;
        AuthController authController2 = this.authController;
        Objects.requireNonNull(authController2);
        ArrayList arrayList = authController2.mUdfpsProps;
        if (arrayList != null && arrayList.size() > 0) {
            this.udfpsRadius = (float) ((FingerprintSensorPropertiesInternal) arrayList.get(0)).getLocation().sensorRadius;
            this.udfpsController = this.udfpsControllerProvider.get();
            if (((AuthRippleView) this.mView).isAttachedToWindow() && (udfpsController2 = this.udfpsController) != null) {
                udfpsController2.mCallbacks.add(this.udfpsControllerCallback);
            }
        }
    }

    public AuthRippleController(StatusBar statusBar2, Context context, AuthController authController2, ConfigurationController configurationController2, KeyguardUpdateMonitor keyguardUpdateMonitor2, KeyguardStateController keyguardStateController2, WakefulnessLifecycle wakefulnessLifecycle2, CommandRegistry commandRegistry2, NotificationShadeWindowController notificationShadeWindowController2, KeyguardBypassController keyguardBypassController, BiometricUnlockController biometricUnlockController2, Provider<UdfpsController> provider, StatusBarStateController statusBarStateController2, AuthRippleView authRippleView) {
        super(authRippleView);
        this.statusBar = statusBar2;
        this.sysuiContext = context;
        this.authController = authController2;
        this.configurationController = configurationController2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.keyguardStateController = keyguardStateController2;
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        this.commandRegistry = commandRegistry2;
        this.notificationShadeWindowController = notificationShadeWindowController2;
        this.bypassController = keyguardBypassController;
        this.biometricUnlockController = biometricUnlockController2;
        this.udfpsControllerProvider = provider;
        this.statusBarStateController = statusBarStateController2;
    }

    public static final void access$showDwellRipple(AuthRippleController authRippleController) {
        boolean z;
        Objects.requireNonNull(authRippleController);
        AuthRippleView authRippleView = (AuthRippleView) authRippleController.mView;
        boolean isDozing = authRippleController.statusBarStateController.isDozing();
        Objects.requireNonNull(authRippleView);
        if (!authRippleView.unlockedRippleInProgress) {
            AnimatorSet animatorSet = authRippleView.dwellPulseOutAnimator;
            if (animatorSet != null && animatorSet.isRunning()) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (isDozing) {
                    DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
                    Objects.requireNonNull(dwellRippleShader);
                    dwellRippleShader.color = -1;
                    dwellRippleShader.setColorUniform("in_color", -1);
                } else {
                    DwellRippleShader dwellRippleShader2 = authRippleView.dwellShader;
                    int i = authRippleView.lockScreenColorVal;
                    Objects.requireNonNull(dwellRippleShader2);
                    dwellRippleShader2.color = i;
                    dwellRippleShader2.setColorUniform("in_color", i);
                }
                DwellRippleShader dwellRippleShader3 = authRippleView.dwellShader;
                Objects.requireNonNull(dwellRippleShader3);
                int alphaComponent = ColorUtils.setAlphaComponent(dwellRippleShader3.color, 255);
                dwellRippleShader3.color = alphaComponent;
                dwellRippleShader3.setColorUniform("in_color", alphaComponent);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 0.8f});
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ofFloat.setDuration(authRippleView.dwellPulseDuration);
                ofFloat.addUpdateListener(new AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1(authRippleView));
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.8f, 1.0f});
                ofFloat2.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                ofFloat2.setDuration(authRippleView.dwellExpandDuration);
                ofFloat2.addUpdateListener(new AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1(authRippleView));
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playSequentially(new Animator[]{ofFloat, ofFloat2});
                animatorSet2.addListener(new AuthRippleView$startDwellRipple$1$1(authRippleView));
                animatorSet2.start();
                authRippleView.dwellPulseOutAnimator = animatorSet2;
            }
        }
    }
}
