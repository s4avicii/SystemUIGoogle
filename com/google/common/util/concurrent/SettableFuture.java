package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

public final class SettableFuture<V> extends AbstractFuture.TrustedFuture<V> {
    @CanIgnoreReturnValue
    public final boolean set(V v) {
        if (!AbstractFuture.ATOMIC_HELPER.casValue(this, (Object) null, AbstractFuture.NULL)) {
            return false;
        }
        AbstractFuture.complete(this);
        return true;
    }

    @CanIgnoreReturnValue
    public final boolean setException(Throwable th) {
        return super.setException(th);
    }
}
