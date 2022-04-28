package com.android.keyguard;

import android.app.admin.DevicePolicyManager;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda11 implements Supplier {
    public final /* synthetic */ KeyguardUpdateMonitor f$0;
    public final /* synthetic */ DevicePolicyManager f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda11(KeyguardUpdateMonitor keyguardUpdateMonitor, DevicePolicyManager devicePolicyManager, int i) {
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = devicePolicyManager;
        this.f$2 = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if ((r1.getKeyguardDisabledFeatures((android.content.ComponentName) null, r3) & 128) == 0) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object get() {
        /*
            r3 = this;
            com.android.keyguard.KeyguardUpdateMonitor r0 = r3.f$0
            android.app.admin.DevicePolicyManager r1 = r3.f$1
            int r3 = r3.f$2
            if (r1 == 0) goto L_0x0014
            java.util.Objects.requireNonNull(r0)
            r2 = 0
            int r3 = r1.getKeyguardDisabledFeatures(r2, r3)
            r3 = r3 & 128(0x80, float:1.794E-43)
            if (r3 != 0) goto L_0x001a
        L_0x0014:
            boolean r3 = r0.isSimPinSecure()
            if (r3 == 0) goto L_0x001c
        L_0x001a:
            r3 = 1
            goto L_0x001d
        L_0x001c:
            r3 = 0
        L_0x001d:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda11.get():java.lang.Object");
    }
}
