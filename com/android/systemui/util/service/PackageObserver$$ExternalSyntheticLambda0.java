package com.android.systemui.util.service;

import com.android.systemui.util.service.Observer;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PackageObserver$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Observer.Callback f$0;

    public /* synthetic */ PackageObserver$$ExternalSyntheticLambda0(PersistentConnectionManager$$ExternalSyntheticLambda0 persistentConnectionManager$$ExternalSyntheticLambda0) {
        this.f$0 = persistentConnectionManager$$ExternalSyntheticLambda0;
    }

    public final boolean test(Object obj) {
        if (((WeakReference) obj).get() == this.f$0) {
            return true;
        }
        return false;
    }
}
