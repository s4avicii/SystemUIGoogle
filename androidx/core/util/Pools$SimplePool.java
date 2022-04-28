package androidx.core.util;

public class Pools$SimplePool<T> {
    public final Object[] mPool;
    public int mPoolSize;

    public boolean release(T t) {
        int i;
        boolean z;
        int i2 = 0;
        while (true) {
            i = this.mPoolSize;
            if (i2 >= i) {
                z = false;
                break;
            } else if (this.mPool[i2] == t) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            Object[] objArr = this.mPool;
            if (i >= objArr.length) {
                return false;
            }
            objArr[i] = t;
            this.mPoolSize = i + 1;
            return true;
        }
        throw new IllegalStateException("Already in the pool!");
    }

    public T acquire() {
        int i = this.mPoolSize;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        T[] tArr = this.mPool;
        T t = tArr[i2];
        tArr[i2] = null;
        this.mPoolSize = i - 1;
        return t;
    }

    public Pools$SimplePool(int i) {
        if (i > 0) {
            this.mPool = new Object[i];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
    }
}
