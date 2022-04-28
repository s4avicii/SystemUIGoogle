package com.android.keyguard;

import com.android.keyguard.AnimatableClockView;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4;

/* compiled from: AnimatableClockView.kt */
public final class AnimatableClockView$animateCharge$startAnimPhase2$1 implements Runnable {
    public final /* synthetic */ AnimatableClockView.DozeStateGetter $dozeStateGetter;
    public final /* synthetic */ AnimatableClockView this$0;

    public AnimatableClockView$animateCharge$startAnimPhase2$1(AnimatableClockView animatableClockView, StatusBar$$ExternalSyntheticLambda4 statusBar$$ExternalSyntheticLambda4) {
        this.this$0 = animatableClockView;
        this.$dozeStateGetter = statusBar$$ExternalSyntheticLambda4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003f, code lost:
        if (r2 != false) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002c, code lost:
        if (r2 != false) goto L_0x0041;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r9 = this;
            com.android.keyguard.AnimatableClockView r0 = r9.this$0
            com.android.keyguard.AnimatableClockView$DozeStateGetter r1 = r9.$dozeStateGetter
            com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4 r1 = (com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4) r1
            java.util.Objects.requireNonNull(r1)
            java.lang.Object r1 = r1.f$0
            com.android.systemui.plugins.statusbar.StatusBarStateController r1 = (com.android.systemui.plugins.statusbar.StatusBarStateController) r1
            boolean r1 = r1.isDozing()
            com.android.keyguard.AnimatableClockView r9 = r9.this$0
            r2 = 1
            r3 = 0
            r4 = 100
            java.util.Objects.requireNonNull(r9)
            if (r1 == 0) goto L_0x002f
            android.content.res.Resources r1 = r9.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            int r1 = r1.fontWeightAdjustment
            if (r1 <= r4) goto L_0x0029
            goto L_0x002a
        L_0x0029:
            r2 = r3
        L_0x002a:
            int r9 = r9.dozingWeightInternal
            if (r2 == 0) goto L_0x0043
            goto L_0x0041
        L_0x002f:
            android.content.res.Resources r1 = r9.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            int r1 = r1.fontWeightAdjustment
            if (r1 <= r4) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r2 = r3
        L_0x003d:
            int r9 = r9.lockScreenWeightInternal
            if (r2 == 0) goto L_0x0043
        L_0x0041:
            int r9 = r9 + 100
        L_0x0043:
            r1 = r9
            r2 = 0
            r3 = 1
            r4 = 1000(0x3e8, double:4.94E-321)
            r6 = 0
            r8 = 0
            int r9 = com.android.keyguard.AnimatableClockView.$r8$clinit
            r0.setTextStyle(r1, r2, r3, r4, r6, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.AnimatableClockView$animateCharge$startAnimPhase2$1.run():void");
    }
}
