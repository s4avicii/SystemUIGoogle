package com.android.systemui.controls.management;

import com.android.systemui.controls.ControlsServiceInfo;
import java.text.Collator;
import java.util.Comparator;

/* renamed from: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1 */
/* compiled from: Comparisons.kt */
public final class C0739xf045535a<T> implements Comparator {
    public final /* synthetic */ Comparator $comparator;

    public C0739xf045535a(Collator collator) {
        this.$comparator = collator;
    }

    public final int compare(T t, T t2) {
        return this.$comparator.compare(((ControlsServiceInfo) t).loadLabel(), ((ControlsServiceInfo) t2).loadLabel());
    }
}
