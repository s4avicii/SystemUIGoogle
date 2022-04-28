package androidx.concurrent.futures;

public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    public final boolean set(V v) {
        if (!AbstractResolvableFuture.ATOMIC_HELPER.casValue(this, (Object) null, AbstractResolvableFuture.NULL)) {
            return false;
        }
        AbstractResolvableFuture.complete(this);
        return true;
    }
}
