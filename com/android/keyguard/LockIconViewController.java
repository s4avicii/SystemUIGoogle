package com.android.keyguard;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.preference.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.R$anim;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class LockIconViewController extends ViewController<LockIconView> implements Dumpable {
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public static final int sLockIconRadiusPx = ((int) ((((float) DisplayMetrics.DENSITY_DEVICE_STABLE) / 160.0f) * 36.0f));
    public final C05551 mAccessibilityDelegate = new View.AccessibilityDelegate() {
        public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityAuthenticateHint;
        public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityEnterHint;

        {
            this.mAccessibilityAuthenticateHint = new AccessibilityNodeInfo.AccessibilityAction(16, LockIconViewController.this.getResources().getString(C1777R.string.accessibility_authenticate_hint));
            this.mAccessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, LockIconViewController.this.getResources().getString(C1777R.string.accessibility_enter_hint));
        }

        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            boolean z;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            if (lockIconViewController.mUdfpsSupported || lockIconViewController.mShowUnlockIcon) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                LockIconViewController lockIconViewController2 = LockIconViewController.this;
                if (lockIconViewController2.mShowLockIcon) {
                    accessibilityNodeInfo.addAction(this.mAccessibilityAuthenticateHint);
                } else if (lockIconViewController2.mShowUnlockIcon) {
                    accessibilityNodeInfo.addAction(this.mAccessibilityEnterHint);
                }
            }
        }
    };
    public int mActivePointerId = -1;
    public final AuthController mAuthController;
    public final C05606 mAuthControllerCallback = new AuthController.Callback() {
        public final void onAllAuthenticatorsRegistered() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            lockIconViewController.mExecutor.execute(new LockIconViewController$$ExternalSyntheticLambda0(lockIconViewController, 0));
        }

        public final void onEnrollmentsChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            lockIconViewController.mExecutor.execute(new LockIconViewController$$ExternalSyntheticLambda0(lockIconViewController, 0));
        }
    };
    public final AuthRippleController mAuthRippleController;
    public int mBottomPaddingPx;
    public boolean mCanDismissLockScreen;
    public Runnable mCancelDelayedUpdateVisibilityRunnable;
    public final ConfigurationController mConfigurationController;
    public final C05595 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            LockIconViewController.this.updateConfiguration();
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            ((LockIconView) lockIconViewController.mView).updateColorAndBackgroundVisibility();
        }

        public final void onThemeChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            ((LockIconView) lockIconViewController.mView).updateColorAndBackgroundVisibility();
        }

        public final void onUiModeChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            Objects.requireNonNull(lockIconViewController);
            ((LockIconView) lockIconViewController.mView).updateColorAndBackgroundVisibility();
        }
    };
    public boolean mDownDetected;
    public final DelayableExecutor mExecutor;
    public final FalsingManager mFalsingManager;
    public float mHeightPixels;
    public final AnimatedStateListDrawable mIcon;
    public float mInterpolatedDarkAmount;
    public boolean mIsBouncerShowing;
    public boolean mIsDozing;
    public boolean mIsKeyguardShowing;
    public final C05584 mKeyguardStateCallback = new KeyguardStateController.Callback() {
        public final void onKeyguardFadingAwayChanged() {
            LockIconViewController.this.updateKeyguardShowing();
            LockIconViewController.this.updateVisibility();
        }

        public final void onKeyguardShowingChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
            LockIconViewController.this.updateKeyguardShowing();
            LockIconViewController lockIconViewController2 = LockIconViewController.this;
            if (lockIconViewController2.mIsKeyguardShowing) {
                lockIconViewController2.mUserUnlockedWithBiometric = lockIconViewController2.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            }
            LockIconViewController.this.updateVisibility();
        }

        public final void onUnlockedChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
            LockIconViewController.this.updateKeyguardShowing();
            LockIconViewController.this.updateVisibility();
        }
    };
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final C05573 mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            boolean z2 = lockIconViewController.mRunningFPS;
            boolean z3 = lockIconViewController.mUserUnlockedWithBiometric;
            lockIconViewController.mUserUnlockedWithBiometric = lockIconViewController.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                LockIconViewController lockIconViewController2 = LockIconViewController.this;
                lockIconViewController2.mRunningFPS = z;
                if (z2 && !z) {
                    Runnable runnable = lockIconViewController2.mCancelDelayedUpdateVisibilityRunnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                    LockIconViewController lockIconViewController3 = LockIconViewController.this;
                    lockIconViewController3.mCancelDelayedUpdateVisibilityRunnable = lockIconViewController3.mExecutor.executeDelayed(new KeyguardStatusView$$ExternalSyntheticLambda0(this, 1), 50);
                    return;
                }
            }
            LockIconViewController lockIconViewController4 = LockIconViewController.this;
            if (z3 != lockIconViewController4.mUserUnlockedWithBiometric || z2 != lockIconViewController4.mRunningFPS) {
                lockIconViewController4.updateVisibility();
            }
        }

        public final void onKeyguardBouncerChanged(boolean z) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mIsBouncerShowing = z;
            lockIconViewController.updateVisibility();
        }

        public final void onKeyguardVisibilityChanged(boolean z) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mIsBouncerShowing = lockIconViewController.mKeyguardViewController.isBouncerShowing();
            LockIconViewController.this.updateVisibility();
        }
    };
    public final KeyguardViewController mKeyguardViewController;
    public String mLockedLabel;
    public Runnable mLongPressCancelRunnable;
    public final int mMaxBurnInOffsetX;
    public final int mMaxBurnInOffsetY;
    public Runnable mOnGestureDetectedRunnable;
    public boolean mQsExpanded;
    public boolean mRunningFPS;
    public final Rect mSensorTouchLocation = new Rect();
    public boolean mShowAodLockIcon;
    public boolean mShowAodUnlockedIcon;
    public boolean mShowLockIcon;
    public boolean mShowUnlockIcon;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public C05562 mStatusBarStateListener = new StatusBarStateController.StateListener() {
        public final void onDozeAmountChanged(float f, float f2) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mInterpolatedDarkAmount = f2;
            LockIconView lockIconView = (LockIconView) lockIconViewController.mView;
            Objects.requireNonNull(lockIconView);
            lockIconView.mDozeAmount = f2;
            lockIconView.updateColorAndBackgroundVisibility();
            LockIconViewController.this.updateBurnInOffsets();
        }

        public final void onDozingChanged(boolean z) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mIsDozing = z;
            lockIconViewController.updateBurnInOffsets();
            LockIconViewController.this.updateVisibility();
        }

        public final void onStateChanged(int i) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mStatusBarState = i;
            lockIconViewController.updateVisibility();
        }
    };
    public boolean mUdfpsEnrolled;
    public boolean mUdfpsSupported;
    public String mUnlockedLabel;
    public boolean mUserUnlockedWithBiometric;
    public VelocityTracker mVelocityTracker;
    public final VibratorHelper mVibrator;
    public float mWidthPixels;

    public final void cancelTouches() {
        this.mDownDetected = false;
        Runnable runnable = this.mLongPressCancelRunnable;
        if (runnable != null) {
            runnable.run();
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        VibratorHelper vibratorHelper = this.mVibrator;
        Objects.requireNonNull(vibratorHelper);
        if (vibratorHelper.hasVibrator()) {
            Executor executor = vibratorHelper.mExecutor;
            Vibrator vibrator = vibratorHelper.mVibrator;
            Objects.requireNonNull(vibrator);
            executor.execute(new BaseWifiTracker$$ExternalSyntheticLambda0(vibrator, 4));
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("mUdfpsSupported: "), this.mUdfpsSupported, printWriter, "mUdfpsEnrolled: "), this.mUdfpsEnrolled, printWriter, "mIsKeyguardShowing: ");
        m.append(this.mIsKeyguardShowing);
        printWriter.println(m.toString());
        printWriter.println(" mIcon: ");
        for (int i : this.mIcon.getState()) {
            printWriter.print(" " + i);
        }
        printWriter.println();
        StringBuilder sb = new StringBuilder();
        sb.append(" mShowUnlockIcon: ");
        StringBuilder m2 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb, this.mShowUnlockIcon, printWriter, " mShowLockIcon: "), this.mShowLockIcon, printWriter, " mShowAodUnlockedIcon: "), this.mShowAodUnlockedIcon, printWriter, "  mIsDozing: "), this.mIsDozing, printWriter, "  mIsBouncerShowing: "), this.mIsBouncerShowing, printWriter, "  mUserUnlockedWithBiometric: "), this.mUserUnlockedWithBiometric, printWriter, "  mRunningFPS: "), this.mRunningFPS, printWriter, "  mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, "  mStatusBarState: ");
        m2.append(R$id.toShortString(this.mStatusBarState));
        printWriter.println(m2.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  mQsExpanded: ");
        StringBuilder m3 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb2, this.mQsExpanded, printWriter, "  mInterpolatedDarkAmount: ");
        m3.append(this.mInterpolatedDarkAmount);
        printWriter.println(m3.toString());
        T t = this.mView;
        if (t != null) {
            ((LockIconView) t).dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final void onInit() {
        ((LockIconView) this.mView).setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (!this.mSensorTouchLocation.contains((int) motionEvent.getX(), (int) motionEvent.getY()) || ((LockIconView) this.mView).getVisibility() != 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            if (this.mUdfpsSupported || this.mShowUnlockIcon) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (motionEvent.getActionMasked() == 0) {
                    return true;
                }
                return this.mDownDetected;
            }
        }
        return false;
    }

    public final void onViewDetached() {
        AuthController authController = this.mAuthController;
        C05606 r1 = this.mAuthControllerCallback;
        Objects.requireNonNull(authController);
        authController.mCallbacks.remove(r1);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.removeCallback(this.mKeyguardStateCallback);
        Runnable runnable = this.mCancelDelayedUpdateVisibilityRunnable;
        if (runnable != null) {
            runnable.run();
            this.mCancelDelayedUpdateVisibilityRunnable = null;
        }
    }

    public final void updateBurnInOffsets() {
        float lerp = MathUtils.lerp(0.0f, (float) (R$anim.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX), this.mInterpolatedDarkAmount);
        float lerp2 = MathUtils.lerp(0.0f, (float) (R$anim.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY), this.mInterpolatedDarkAmount);
        MathUtils.lerp(0.0f, R$anim.zigzag(((float) System.currentTimeMillis()) / 60000.0f, 1.0f, 89.0f), this.mInterpolatedDarkAmount);
        ((LockIconView) this.mView).setTranslationX(lerp);
        ((LockIconView) this.mView).setTranslationY(lerp2);
    }

    public final void updateIsUdfpsEnrolled() {
        boolean z = this.mUdfpsSupported;
        boolean z2 = this.mUdfpsEnrolled;
        boolean isUdfpsSupported = this.mKeyguardUpdateMonitor.isUdfpsSupported();
        this.mUdfpsSupported = isUdfpsSupported;
        LockIconView lockIconView = (LockIconView) this.mView;
        Objects.requireNonNull(lockIconView);
        lockIconView.mUseBackground = isUdfpsSupported;
        lockIconView.updateColorAndBackgroundVisibility();
        boolean isUdfpsEnrolled = this.mKeyguardUpdateMonitor.isUdfpsEnrolled();
        this.mUdfpsEnrolled = isUdfpsEnrolled;
        if (z != this.mUdfpsSupported || z2 != isUdfpsEnrolled) {
            updateVisibility();
        }
    }

    public final void updateKeyguardShowing() {
        boolean z;
        if (!this.mKeyguardStateController.isShowing() || this.mKeyguardStateController.isKeyguardGoingAway()) {
            z = false;
        } else {
            z = true;
        }
        this.mIsKeyguardShowing = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0080 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x008f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateVisibility() {
        /*
            r9 = this;
            java.lang.Runnable r0 = r9.mCancelDelayedUpdateVisibilityRunnable
            r1 = 0
            if (r0 == 0) goto L_0x000a
            r0.run()
            r9.mCancelDelayedUpdateVisibilityRunnable = r1
        L_0x000a:
            boolean r0 = r9.mIsKeyguardShowing
            r2 = 4
            if (r0 != 0) goto L_0x001b
            boolean r0 = r9.mIsDozing
            if (r0 != 0) goto L_0x001b
            T r9 = r9.mView
            com.android.keyguard.LockIconView r9 = (com.android.keyguard.LockIconView) r9
            r9.setVisibility(r2)
            return
        L_0x001b:
            boolean r0 = r9.mShowUnlockIcon
            boolean r3 = r9.mUdfpsEnrolled
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0033
            if (r0 != 0) goto L_0x0033
            boolean r0 = r9.mShowLockIcon
            if (r0 != 0) goto L_0x0033
            boolean r0 = r9.mShowAodUnlockedIcon
            if (r0 != 0) goto L_0x0033
            boolean r0 = r9.mShowAodLockIcon
            if (r0 != 0) goto L_0x0033
            r0 = r5
            goto L_0x0034
        L_0x0033:
            r0 = r4
        L_0x0034:
            boolean r6 = r9.mCanDismissLockScreen
            if (r6 != 0) goto L_0x0059
            boolean r7 = r9.mUserUnlockedWithBiometric
            if (r7 != 0) goto L_0x0059
            boolean r7 = r9.mIsDozing
            if (r7 != 0) goto L_0x004e
            boolean r7 = r9.mIsBouncerShowing
            if (r7 != 0) goto L_0x004e
            boolean r7 = r9.mQsExpanded
            if (r7 != 0) goto L_0x004e
            int r7 = r9.mStatusBarState
            if (r7 != r5) goto L_0x004e
            r7 = r5
            goto L_0x004f
        L_0x004e:
            r7 = r4
        L_0x004f:
            if (r7 == 0) goto L_0x0059
            if (r3 == 0) goto L_0x0057
            boolean r7 = r9.mRunningFPS
            if (r7 != 0) goto L_0x0059
        L_0x0057:
            r7 = r5
            goto L_0x005a
        L_0x0059:
            r7 = r4
        L_0x005a:
            r9.mShowLockIcon = r7
            if (r6 != 0) goto L_0x0062
            boolean r7 = r9.mUserUnlockedWithBiometric
            if (r7 == 0) goto L_0x0079
        L_0x0062:
            boolean r7 = r9.mIsDozing
            if (r7 != 0) goto L_0x0074
            boolean r7 = r9.mIsBouncerShowing
            if (r7 != 0) goto L_0x0074
            boolean r7 = r9.mQsExpanded
            if (r7 != 0) goto L_0x0074
            int r7 = r9.mStatusBarState
            if (r7 != r5) goto L_0x0074
            r7 = r5
            goto L_0x0075
        L_0x0074:
            r7 = r4
        L_0x0075:
            if (r7 == 0) goto L_0x0079
            r7 = r5
            goto L_0x007a
        L_0x0079:
            r7 = r4
        L_0x007a:
            r9.mShowUnlockIcon = r7
            boolean r7 = r9.mIsDozing
            if (r7 == 0) goto L_0x008a
            if (r3 == 0) goto L_0x008a
            boolean r8 = r9.mRunningFPS
            if (r8 != 0) goto L_0x008a
            if (r6 == 0) goto L_0x008a
            r8 = r5
            goto L_0x008b
        L_0x008a:
            r8 = r4
        L_0x008b:
            r9.mShowAodUnlockedIcon = r8
            if (r7 == 0) goto L_0x0099
            if (r3 == 0) goto L_0x0099
            boolean r3 = r9.mRunningFPS
            if (r3 != 0) goto L_0x0099
            if (r6 != 0) goto L_0x0099
            r3 = r5
            goto L_0x009a
        L_0x0099:
            r3 = r4
        L_0x009a:
            r9.mShowAodLockIcon = r3
            T r3 = r9.mView
            com.android.keyguard.LockIconView r3 = (com.android.keyguard.LockIconView) r3
            java.lang.CharSequence r3 = r3.getContentDescription()
            boolean r6 = r9.mShowLockIcon
            if (r6 == 0) goto L_0x00c1
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.updateIcon(r4, r4)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.String r1 = r9.mLockedLabel
            r0.setContentDescription(r1)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setVisibility(r4)
            goto L_0x0138
        L_0x00c1:
            boolean r6 = r9.mShowUnlockIcon
            r7 = 2
            if (r6 == 0) goto L_0x00e7
            if (r0 == 0) goto L_0x00cf
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.updateIcon(r5, r4)
        L_0x00cf:
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.updateIcon(r7, r4)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.String r1 = r9.mUnlockedLabel
            r0.setContentDescription(r1)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setVisibility(r4)
            goto L_0x0138
        L_0x00e7:
            boolean r0 = r9.mShowAodUnlockedIcon
            if (r0 == 0) goto L_0x0103
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.updateIcon(r7, r5)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.String r1 = r9.mUnlockedLabel
            r0.setContentDescription(r1)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setVisibility(r4)
            goto L_0x0138
        L_0x0103:
            boolean r0 = r9.mShowAodLockIcon
            if (r0 == 0) goto L_0x011f
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.updateIcon(r4, r5)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.String r1 = r9.mLockedLabel
            r0.setContentDescription(r1)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setVisibility(r4)
            goto L_0x0138
        L_0x011f:
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.util.Objects.requireNonNull(r0)
            r5 = -1
            r0.updateIcon(r5, r4)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setVisibility(r2)
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            r0.setContentDescription(r1)
        L_0x0138:
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.CharSequence r0 = r0.getContentDescription()
            boolean r0 = java.util.Objects.equals(r3, r0)
            if (r0 != 0) goto L_0x015b
            T r0 = r9.mView
            com.android.keyguard.LockIconView r0 = (com.android.keyguard.LockIconView) r0
            java.lang.CharSequence r0 = r0.getContentDescription()
            if (r0 == 0) goto L_0x015b
            T r9 = r9.mView
            com.android.keyguard.LockIconView r9 = (com.android.keyguard.LockIconView) r9
            java.lang.CharSequence r0 = r9.getContentDescription()
            r9.announceForAccessibility(r0)
        L_0x015b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.LockIconViewController.updateVisibility():void");
    }

    public static void $r8$lambda$h98ceOtiS5JD1Nfnu1Y0fyk_1uo(LockIconViewController lockIconViewController) {
        AuthRippleController authRippleController;
        Objects.requireNonNull(lockIconViewController);
        lockIconViewController.cancelTouches();
        if (lockIconViewController.mFalsingManager.isFalseTouch(14)) {
            Log.v("LockIconViewController", "lock icon long-press rejected by the falsing manager.");
            return;
        }
        lockIconViewController.mIsBouncerShowing = true;
        if (lockIconViewController.mUdfpsSupported && lockIconViewController.mShowUnlockIcon && (authRippleController = lockIconViewController.mAuthRippleController) != null) {
            authRippleController.showRipple(BiometricSourceType.FINGERPRINT);
        }
        lockIconViewController.updateVisibility();
        Runnable runnable = lockIconViewController.mOnGestureDetectedRunnable;
        if (runnable != null) {
            runnable.run();
        }
        lockIconViewController.mVibrator.vibrate(Process.myUid(), lockIconViewController.getContext().getOpPackageName(), UdfpsController.EFFECT_CLICK, "lock-icon-device-entry", TOUCH_VIBRATION_ATTRIBUTES);
        lockIconViewController.mKeyguardViewController.showBouncer(true);
    }

    public LockIconViewController(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources) {
        super(lockIconView);
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAuthController = authController;
        this.mKeyguardViewController = keyguardViewController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mConfigurationController = configurationController;
        this.mExecutor = delayableExecutor;
        this.mVibrator = vibratorHelper;
        this.mAuthRippleController = authRippleController;
        this.mMaxBurnInOffsetX = resources.getDimensionPixelSize(C1777R.dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = resources.getDimensionPixelSize(C1777R.dimen.udfps_burn_in_offset_y);
        AnimatedStateListDrawable animatedStateListDrawable = (AnimatedStateListDrawable) resources.getDrawable(C1777R.C1778drawable.super_lock_icon, lockIconView.getContext().getTheme());
        this.mIcon = animatedStateListDrawable;
        lockIconView.mLockIcon.setImageDrawable(animatedStateListDrawable);
        if (lockIconView.mUseBackground) {
            if (animatedStateListDrawable == null) {
                lockIconView.mBgView.setVisibility(4);
            } else {
                lockIconView.mBgView.setVisibility(0);
            }
        }
        this.mUnlockedLabel = resources.getString(C1777R.string.accessibility_unlock_button);
        this.mLockedLabel = resources.getString(C1777R.string.accessibility_lock_icon);
        dumpManager.registerDumpable("LockIconViewController", this);
    }

    public final void onViewAttached() {
        updateIsUdfpsEnrolled();
        updateConfiguration();
        updateKeyguardShowing();
        this.mUserUnlockedWithBiometric = false;
        this.mIsBouncerShowing = this.mKeyguardViewController.isBouncerShowing();
        this.mIsDozing = this.mStatusBarStateController.isDozing();
        this.mInterpolatedDarkAmount = this.mStatusBarStateController.getDozeAmount();
        this.mRunningFPS = this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning();
        this.mCanDismissLockScreen = this.mKeyguardStateController.canDismissLockScreen();
        this.mStatusBarState = this.mStatusBarStateController.getState();
        ((LockIconView) this.mView).updateColorAndBackgroundVisibility();
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
        this.mDownDetected = false;
        updateBurnInOffsets();
        updateVisibility();
    }

    public final void updateConfiguration() {
        Rect bounds = ((WindowManager) getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        this.mWidthPixels = (float) bounds.right;
        this.mHeightPixels = (float) bounds.bottom;
        this.mBottomPaddingPx = getResources().getDimensionPixelSize(C1777R.dimen.lock_icon_margin_bottom);
        this.mUnlockedLabel = ((LockIconView) this.mView).getContext().getResources().getString(C1777R.string.accessibility_unlock_button);
        this.mLockedLabel = ((LockIconView) this.mView).getContext().getResources().getString(C1777R.string.accessibility_lock_icon);
        if (this.mUdfpsSupported) {
            AuthController authController = this.mAuthController;
            Objects.requireNonNull(authController);
            SensorLocationInternal location = ((FingerprintSensorPropertiesInternal) authController.mUdfpsProps.get(0)).getLocation();
            ((LockIconView) this.mView).setCenterLocation(new PointF((float) location.sensorLocationX, (float) location.sensorLocationY), location.sensorRadius);
        } else {
            float f = this.mHeightPixels - ((float) this.mBottomPaddingPx);
            int i = sLockIconRadiusPx;
            ((LockIconView) this.mView).setCenterLocation(new PointF(this.mWidthPixels / 2.0f, f - ((float) i)), i);
        }
        ((LockIconView) this.mView).getHitRect(this.mSensorTouchLocation);
    }
}
