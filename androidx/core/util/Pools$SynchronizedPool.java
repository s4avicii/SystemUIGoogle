package androidx.core.util;

public final class Pools$SynchronizedPool<T> extends Pools$SimplePool<T> {
    public final Object mLock = new Object();

    public final T acquire() {
        T acquire;
        synchronized (this.mLock) {
            acquire = super.acquire();
        }
        return acquire;
    }

    public final boolean release(T t) {
        boolean release;
        synchronized (this.mLock) {
            release = super.release(t);
        }
        return release;
    }

    public Pools$SynchronizedPool(int i) {
        super(i);
    }
}
