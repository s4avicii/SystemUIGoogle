package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda12 implements PanelExpansionListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda12(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0068, code lost:
        if (r8.unlockingWithSmartspaceTransition != false) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onPanelExpansionChanged(float r7, boolean r8, boolean r9) {
        /*
            r6 = this;
            com.android.systemui.statusbar.phone.StatusBar r6 = r6.f$0
            long[] r8 = com.android.systemui.statusbar.phone.StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS
            java.util.Objects.requireNonNull(r6)
            boolean r8 = r6.isKeyguardShowing()
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L_0x0071
            com.android.systemui.statusbar.policy.KeyguardStateController r8 = r6.mKeyguardStateController
            boolean r8 = r8.canDismissLockScreen()
            if (r8 == 0) goto L_0x0071
            com.android.systemui.keyguard.KeyguardViewMediator r8 = r6.mKeyguardViewMediator
            java.util.Objects.requireNonNull(r8)
            r1 = 0
            r2 = r1
        L_0x001e:
            android.util.SparseIntArray r3 = r8.mLastSimStates
            int r3 = r3.size()
            r4 = 1
            if (r2 >= r3) goto L_0x0049
            android.util.SparseIntArray r3 = r8.mLastSimStates
            int r3 = r3.keyAt(r2)
            android.util.SparseIntArray r5 = r8.mLastSimStates
            int r3 = r5.get(r3)
            boolean r5 = com.android.keyguard.KeyguardUpdateMonitor.DEBUG
            r5 = 2
            if (r3 == r5) goto L_0x0041
            r5 = 3
            if (r3 == r5) goto L_0x0041
            r5 = 7
            if (r3 != r5) goto L_0x003f
            goto L_0x0041
        L_0x003f:
            r3 = r1
            goto L_0x0042
        L_0x0041:
            r3 = r4
        L_0x0042:
            if (r3 == 0) goto L_0x0046
            r1 = r4
            goto L_0x0049
        L_0x0046:
            int r2 = r2 + 1
            goto L_0x001e
        L_0x0049:
            if (r1 != 0) goto L_0x0071
            com.android.systemui.statusbar.phone.NotificationPanelViewController r8 = r6.mNotificationPanelViewController
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mQsExpanded
            if (r8 == 0) goto L_0x0057
            if (r9 == 0) goto L_0x0057
            goto L_0x0071
        L_0x0057:
            if (r9 != 0) goto L_0x006a
            com.android.systemui.keyguard.KeyguardViewMediator r8 = r6.mKeyguardViewMediator
            boolean r8 = r8.isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()
            if (r8 != 0) goto L_0x006a
            com.android.systemui.keyguard.KeyguardUnlockAnimationController r8 = r6.mKeyguardUnlockAnimationController
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.unlockingWithSmartspaceTransition
            if (r8 == 0) goto L_0x0071
        L_0x006a:
            com.android.systemui.statusbar.policy.KeyguardStateController r8 = r6.mKeyguardStateController
            float r1 = r0 - r7
            r8.notifyKeyguardDismissAmountChanged(r1, r9)
        L_0x0071:
            r8 = 0
            int r8 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r8 == 0) goto L_0x007a
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 != 0) goto L_0x0091
        L_0x007a:
            com.android.systemui.navigationbar.NavigationBarView r7 = r6.getNavigationBarView()
            if (r7 == 0) goto L_0x008a
            com.android.systemui.navigationbar.NavigationBarView r7 = r6.getNavigationBarView()
            java.util.Objects.requireNonNull(r7)
            r7.updateSlippery()
        L_0x008a:
            com.android.systemui.statusbar.phone.NotificationPanelViewController r6 = r6.mNotificationPanelViewController
            if (r6 == 0) goto L_0x0091
            r6.updateSystemUiStateFlags()
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda12.onPanelExpansionChanged(float, boolean, boolean):void");
    }
}
