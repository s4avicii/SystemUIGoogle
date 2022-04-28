package com.android.keyguard;

import android.view.LayoutInflater;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;

public final class KeyguardSecurityViewFlipperController extends ViewController<KeyguardSecurityViewFlipper> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final ArrayList mChildren = new ArrayList();
    public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final LayoutInflater mLayoutInflater;

    public static class NullKeyguardInputViewController extends KeyguardInputViewController<KeyguardInputView> {
        public NullKeyguardInputViewController(KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController) {
            super(null, securityMode, keyguardSecurityCallback, emergencyButtonController);
        }

        public final boolean needsInput() {
            return false;
        }

        public final void onStartingToHide() {
        }
    }

    public final void onViewAttached() {
    }

    public final void onViewDetached() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: com.android.keyguard.KeyguardPasswordViewController} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: com.android.keyguard.KeyguardPasswordViewController} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: com.android.keyguard.KeyguardPasswordViewController} */
    /* JADX WARNING: type inference failed for: r13v0 */
    /* JADX WARNING: type inference failed for: r1v22, types: [com.android.keyguard.KeyguardSimPukViewController] */
    /* JADX WARNING: type inference failed for: r1v23, types: [com.android.keyguard.KeyguardSimPinViewController] */
    /* JADX WARNING: type inference failed for: r1v24, types: [com.android.keyguard.KeyguardPinViewController] */
    /* JADX WARNING: type inference failed for: r1v25, types: [com.android.keyguard.KeyguardPatternViewController] */
    /* JADX WARNING: Multi-variable type inference failed */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.android.keyguard.KeyguardInputViewController<com.android.keyguard.KeyguardInputView> getSecurityView(com.android.keyguard.KeyguardSecurityModel.SecurityMode r27, com.android.keyguard.KeyguardSecurityCallback r28) {
        /*
            r26 = this;
            r0 = r26
            r14 = r27
            java.util.ArrayList r1 = r0.mChildren
            java.util.Iterator r1 = r1.iterator()
        L_0x000a:
            boolean r2 = r1.hasNext()
            r15 = 0
            if (r2 == 0) goto L_0x001f
            java.lang.Object r2 = r1.next()
            com.android.keyguard.KeyguardInputViewController r2 = (com.android.keyguard.KeyguardInputViewController) r2
            java.util.Objects.requireNonNull(r2)
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r3 = r2.mSecurityMode
            if (r3 != r14) goto L_0x000a
            goto L_0x0020
        L_0x001f:
            r2 = r15
        L_0x0020:
            if (r2 != 0) goto L_0x0199
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.None
            if (r14 == r1) goto L_0x0199
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Invalid
            if (r14 == r1) goto L_0x0199
            int r1 = r27.ordinal()
            r3 = 2
            r4 = 0
            if (r1 == r3) goto L_0x0050
            r3 = 3
            if (r1 == r3) goto L_0x004c
            r3 = 4
            if (r1 == r3) goto L_0x0048
            r3 = 5
            if (r1 == r3) goto L_0x0044
            r3 = 6
            if (r1 == r3) goto L_0x0040
            r1 = r4
            goto L_0x0053
        L_0x0040:
            r1 = 2131624169(0x7f0e00e9, float:1.887551E38)
            goto L_0x0053
        L_0x0044:
            r1 = 2131624168(0x7f0e00e8, float:1.8875508E38)
            goto L_0x0053
        L_0x0048:
            r1 = 2131624165(0x7f0e00e5, float:1.8875502E38)
            goto L_0x0053
        L_0x004c:
            r1 = 2131624163(0x7f0e00e3, float:1.8875498E38)
            goto L_0x0053
        L_0x0050:
            r1 = 2131624164(0x7f0e00e4, float:1.88755E38)
        L_0x0053:
            if (r1 == 0) goto L_0x0199
            boolean r2 = DEBUG
            if (r2 == 0) goto L_0x006f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "inflating id = "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "KeyguardSecurityView"
            android.util.Log.v(r3, r2)
        L_0x006f:
            android.view.LayoutInflater r2 = r0.mLayoutInflater
            T r3 = r0.mView
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            android.view.View r1 = r2.inflate(r1, r3, r4)
            com.android.keyguard.KeyguardInputView r1 = (com.android.keyguard.KeyguardInputView) r1
            T r2 = r0.mView
            com.android.keyguard.KeyguardSecurityViewFlipper r2 = (com.android.keyguard.KeyguardSecurityViewFlipper) r2
            r2.addView(r1)
            com.android.keyguard.KeyguardInputViewController$Factory r2 = r0.mKeyguardSecurityViewControllerFactory
            java.util.Objects.requireNonNull(r2)
            com.android.keyguard.EmergencyButtonController$Factory r3 = r2.mEmergencyButtonControllerFactory
            r4 = 2131427906(0x7f0b0242, float:1.8477441E38)
            android.view.View r4 = r1.findViewById(r4)
            r17 = r4
            com.android.keyguard.EmergencyButton r17 = (com.android.keyguard.EmergencyButton) r17
            java.util.Objects.requireNonNull(r3)
            com.android.keyguard.EmergencyButtonController r12 = new com.android.keyguard.EmergencyButtonController
            com.android.systemui.statusbar.policy.ConfigurationController r4 = r3.mConfigurationController
            com.android.keyguard.KeyguardUpdateMonitor r5 = r3.mKeyguardUpdateMonitor
            android.telephony.TelephonyManager r6 = r3.mTelephonyManager
            android.os.PowerManager r7 = r3.mPowerManager
            android.app.ActivityTaskManager r8 = r3.mActivityTaskManager
            com.android.systemui.statusbar.phone.ShadeController r9 = r3.mShadeController
            android.telecom.TelecomManager r10 = r3.mTelecomManager
            com.android.internal.logging.MetricsLogger r3 = r3.mMetricsLogger
            r16 = r12
            r18 = r4
            r19 = r5
            r20 = r6
            r21 = r7
            r22 = r8
            r23 = r9
            r24 = r10
            r25 = r3
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25)
            boolean r3 = r1 instanceof com.android.keyguard.KeyguardPatternView
            if (r3 == 0) goto L_0x00e0
            com.android.keyguard.KeyguardPatternViewController r13 = new com.android.keyguard.KeyguardPatternViewController
            r3 = r1
            com.android.keyguard.KeyguardPatternView r3 = (com.android.keyguard.KeyguardPatternView) r3
            com.android.keyguard.KeyguardUpdateMonitor r4 = r2.mKeyguardUpdateMonitor
            com.android.internal.widget.LockPatternUtils r5 = r2.mLockPatternUtils
            com.android.internal.util.LatencyTracker r7 = r2.mLatencyTracker
            com.android.systemui.classifier.FalsingCollector r8 = r2.mFalsingCollector
            com.android.keyguard.KeyguardMessageAreaController$Factory r10 = r2.mMessageAreaControllerFactory
            com.android.systemui.statusbar.policy.DevicePostureController r11 = r2.mDevicePostureController
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r27
            r6 = r28
            r9 = r12
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x0178
        L_0x00e0:
            boolean r3 = r1 instanceof com.android.keyguard.KeyguardPasswordView
            if (r3 == 0) goto L_0x010e
            com.android.keyguard.KeyguardPasswordViewController r16 = new com.android.keyguard.KeyguardPasswordViewController
            r3 = r1
            com.android.keyguard.KeyguardPasswordView r3 = (com.android.keyguard.KeyguardPasswordView) r3
            com.android.keyguard.KeyguardUpdateMonitor r4 = r2.mKeyguardUpdateMonitor
            com.android.internal.widget.LockPatternUtils r5 = r2.mLockPatternUtils
            com.android.keyguard.KeyguardMessageAreaController$Factory r7 = r2.mMessageAreaControllerFactory
            com.android.internal.util.LatencyTracker r8 = r2.mLatencyTracker
            android.view.inputmethod.InputMethodManager r9 = r2.mInputMethodManager
            com.android.systemui.util.concurrency.DelayableExecutor r11 = r2.mMainExecutor
            android.content.res.Resources r13 = r2.mResources
            com.android.systemui.classifier.FalsingCollector r10 = r2.mFalsingCollector
            r1 = r16
            r2 = r3
            r3 = r4
            r4 = r27
            r6 = r28
            r17 = r10
            r10 = r12
            r12 = r13
            r13 = r17
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r2 = r16
            goto L_0x0179
        L_0x010e:
            boolean r3 = r1 instanceof com.android.keyguard.KeyguardPINView
            if (r3 == 0) goto L_0x0135
            com.android.keyguard.KeyguardPinViewController r13 = new com.android.keyguard.KeyguardPinViewController
            r3 = r1
            com.android.keyguard.KeyguardPINView r3 = (com.android.keyguard.KeyguardPINView) r3
            com.android.keyguard.KeyguardUpdateMonitor r4 = r2.mKeyguardUpdateMonitor
            com.android.internal.widget.LockPatternUtils r5 = r2.mLockPatternUtils
            com.android.keyguard.KeyguardMessageAreaController$Factory r7 = r2.mMessageAreaControllerFactory
            com.android.internal.util.LatencyTracker r8 = r2.mLatencyTracker
            com.android.keyguard.LiftToActivateListener r9 = r2.mLiftToActivateListener
            com.android.systemui.classifier.FalsingCollector r11 = r2.mFalsingCollector
            com.android.systemui.statusbar.policy.DevicePostureController r10 = r2.mDevicePostureController
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r27
            r6 = r28
            r16 = r10
            r10 = r12
            r12 = r16
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto L_0x0178
        L_0x0135:
            boolean r3 = r1 instanceof com.android.keyguard.KeyguardSimPinView
            if (r3 == 0) goto L_0x0157
            com.android.keyguard.KeyguardSimPinViewController r13 = new com.android.keyguard.KeyguardSimPinViewController
            r3 = r1
            com.android.keyguard.KeyguardSimPinView r3 = (com.android.keyguard.KeyguardSimPinView) r3
            com.android.keyguard.KeyguardUpdateMonitor r4 = r2.mKeyguardUpdateMonitor
            com.android.internal.widget.LockPatternUtils r5 = r2.mLockPatternUtils
            com.android.keyguard.KeyguardMessageAreaController$Factory r7 = r2.mMessageAreaControllerFactory
            com.android.internal.util.LatencyTracker r8 = r2.mLatencyTracker
            com.android.keyguard.LiftToActivateListener r9 = r2.mLiftToActivateListener
            android.telephony.TelephonyManager r10 = r2.mTelephonyManager
            com.android.systemui.classifier.FalsingCollector r11 = r2.mFalsingCollector
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r27
            r6 = r28
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto L_0x0178
        L_0x0157:
            boolean r3 = r1 instanceof com.android.keyguard.KeyguardSimPukView
            if (r3 == 0) goto L_0x0182
            com.android.keyguard.KeyguardSimPukViewController r13 = new com.android.keyguard.KeyguardSimPukViewController
            r3 = r1
            com.android.keyguard.KeyguardSimPukView r3 = (com.android.keyguard.KeyguardSimPukView) r3
            com.android.keyguard.KeyguardUpdateMonitor r4 = r2.mKeyguardUpdateMonitor
            com.android.internal.widget.LockPatternUtils r5 = r2.mLockPatternUtils
            com.android.keyguard.KeyguardMessageAreaController$Factory r7 = r2.mMessageAreaControllerFactory
            com.android.internal.util.LatencyTracker r8 = r2.mLatencyTracker
            com.android.keyguard.LiftToActivateListener r9 = r2.mLiftToActivateListener
            android.telephony.TelephonyManager r10 = r2.mTelephonyManager
            com.android.systemui.classifier.FalsingCollector r11 = r2.mFalsingCollector
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r27
            r6 = r28
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
        L_0x0178:
            r2 = r13
        L_0x0179:
            r2.init()
            java.util.ArrayList r1 = r0.mChildren
            r1.add(r2)
            goto L_0x0199
        L_0x0182:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unable to find controller for "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x0199:
            if (r2 != 0) goto L_0x01be
            com.android.keyguard.KeyguardSecurityViewFlipperController$NullKeyguardInputViewController r2 = new com.android.keyguard.KeyguardSecurityViewFlipperController$NullKeyguardInputViewController
            com.android.keyguard.EmergencyButtonController$Factory r0 = r0.mEmergencyButtonControllerFactory
            java.util.Objects.requireNonNull(r0)
            com.android.keyguard.EmergencyButtonController r1 = new com.android.keyguard.EmergencyButtonController
            com.android.systemui.statusbar.policy.ConfigurationController r5 = r0.mConfigurationController
            com.android.keyguard.KeyguardUpdateMonitor r6 = r0.mKeyguardUpdateMonitor
            android.telephony.TelephonyManager r7 = r0.mTelephonyManager
            android.os.PowerManager r8 = r0.mPowerManager
            android.app.ActivityTaskManager r9 = r0.mActivityTaskManager
            com.android.systemui.statusbar.phone.ShadeController r10 = r0.mShadeController
            android.telecom.TelecomManager r11 = r0.mTelecomManager
            com.android.internal.logging.MetricsLogger r12 = r0.mMetricsLogger
            r3 = r1
            r4 = r15
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            r0 = r28
            r2.<init>(r14, r0, r1)
        L_0x01be:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityViewFlipperController.getSecurityView(com.android.keyguard.KeyguardSecurityModel$SecurityMode, com.android.keyguard.KeyguardSecurityCallback):com.android.keyguard.KeyguardInputViewController");
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2) {
        super(keyguardSecurityViewFlipper);
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mLayoutInflater = layoutInflater;
        this.mEmergencyButtonControllerFactory = factory2;
    }
}
