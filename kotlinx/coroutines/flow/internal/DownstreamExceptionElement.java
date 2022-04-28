package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: SafeCollector.kt */
public final class DownstreamExceptionElement implements CoroutineContext.Element {
    public static final Key Key = new Key();

    /* renamed from: e */
    public final Throwable f159e;

    /* compiled from: SafeCollector.kt */
    public static final class Key implements CoroutineContext.Key<DownstreamExceptionElement> {
    }

    public DownstreamExceptionElement(Throwable th) {
        this.f159e = th;
    }

    public final <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return function2.invoke(r, this);
    }

    public final <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        return CoroutineContext.Element.DefaultImpls.get(this, key);
    }

    public final CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        return CoroutineContext.Element.DefaultImpls.minusKey(this, key);
    }

    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public final CoroutineContext.Key<?> getKey() {
        return Key;
    }
}
