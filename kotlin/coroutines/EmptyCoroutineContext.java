package kotlin.coroutines;

import java.io.Serializable;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: CoroutineContextImpl.kt */
public final class EmptyCoroutineContext implements CoroutineContext, Serializable {
    public static final EmptyCoroutineContext INSTANCE = new EmptyCoroutineContext();
    private static final long serialVersionUID = 0;

    public final <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return r;
    }

    public final <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        return null;
    }

    public final int hashCode() {
        return 0;
    }

    public final CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        return this;
    }

    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return coroutineContext;
    }

    public final String toString() {
        return "EmptyCoroutineContext";
    }

    private EmptyCoroutineContext() {
    }

    private final Object readResolve() {
        return INSTANCE;
    }
}
