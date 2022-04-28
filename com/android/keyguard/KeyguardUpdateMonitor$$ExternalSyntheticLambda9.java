package com.android.keyguard;

import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ KeyguardUpdateMonitorCallback f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda9(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        this.f$0 = keyguardUpdateMonitorCallback;
    }

    public final boolean test(Object obj) {
        if (((WeakReference) obj).get() == this.f$0) {
            return true;
        }
        return false;
    }
}
