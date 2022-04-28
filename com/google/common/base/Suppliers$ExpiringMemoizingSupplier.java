package com.google.common.base;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.io.Serializable;

class Suppliers$ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    public final Supplier<T> delegate;
    public final long durationNanos;

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Suppliers.memoizeWithExpiration(");
        m.append(this.delegate);
        m.append(", ");
        m.append(this.durationNanos);
        m.append(", NANOS)");
        return m.toString();
    }
}
