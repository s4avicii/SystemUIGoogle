package com.android.systemui.assist;

import com.android.systemui.BootCompleteCache;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhoneStateMonitor$$ExternalSyntheticLambda0 implements BootCompleteCache.BootCompleteListener {
    public final /* synthetic */ PhoneStateMonitor f$0;

    public /* synthetic */ PhoneStateMonitor$$ExternalSyntheticLambda0(PhoneStateMonitor phoneStateMonitor) {
        this.f$0 = phoneStateMonitor;
    }

    public final void onBootComplete() {
        PhoneStateMonitor phoneStateMonitor = this.f$0;
        Objects.requireNonNull(phoneStateMonitor);
        phoneStateMonitor.mDefaultHome = PhoneStateMonitor.getCurrentDefaultHome();
    }
}
