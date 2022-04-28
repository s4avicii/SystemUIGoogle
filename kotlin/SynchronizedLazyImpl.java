package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LazyJVM.kt */
final class SynchronizedLazyImpl<T> implements Lazy<T>, Serializable {
    private volatile Object _value;
    private Function0<? extends T> initializer;
    private final Object lock;

    public SynchronizedLazyImpl() {
        throw null;
    }

    public SynchronizedLazyImpl(Function0 function0) {
        this.initializer = function0;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        this.lock = this;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    public final T getValue() {
        T t;
        T t2 = this._value;
        T t3 = UNINITIALIZED_VALUE.INSTANCE;
        if (t2 != t3) {
            return t2;
        }
        synchronized (this.lock) {
            t = this._value;
            if (t == t3) {
                Function0 function0 = this.initializer;
                Intrinsics.checkNotNull(function0);
                t = function0.invoke();
                this._value = t;
                this.initializer = null;
            }
        }
        return t;
    }

    public final String toString() {
        boolean z;
        if (this._value != UNINITIALIZED_VALUE.INSTANCE) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return String.valueOf(getValue());
        }
        return "Lazy value not initialized yet.";
    }
}
