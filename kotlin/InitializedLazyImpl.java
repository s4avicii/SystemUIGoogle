package kotlin;

import java.io.Serializable;

/* compiled from: Lazy.kt */
public final class InitializedLazyImpl<T> implements Lazy<T>, Serializable {
    private final T value;

    public final String toString() {
        return String.valueOf(this.value);
    }

    public InitializedLazyImpl(T t) {
        this.value = t;
    }

    public final T getValue() {
        return this.value;
    }
}
