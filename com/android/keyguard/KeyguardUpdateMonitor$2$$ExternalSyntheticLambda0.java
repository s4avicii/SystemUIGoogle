package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardUpdateMonitor.C05462 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(KeyguardUpdateMonitor.C05462 r1, int i, boolean z) {
        this.f$0 = r1;
        this.f$1 = i;
        this.f$2 = z;
    }

    public final void run() {
        KeyguardUpdateMonitor.C05462 r0 = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        Objects.requireNonNull(r0);
        KeyguardUpdateMonitor.this.mBiometricEnabledForUser.put(i, z);
        KeyguardUpdateMonitor.this.updateBiometricListeningState(2);
    }
}
