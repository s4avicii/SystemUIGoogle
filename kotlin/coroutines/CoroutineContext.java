package kotlin.coroutines;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CoroutineContext.kt */
public interface CoroutineContext {

    /* compiled from: CoroutineContext.kt */
    public static final class DefaultImpls {
        public static CoroutineContext plus(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
            if (coroutineContext2 == EmptyCoroutineContext.INSTANCE) {
                return coroutineContext;
            }
            return (CoroutineContext) coroutineContext2.fold(coroutineContext, CoroutineContext$plus$1.INSTANCE);
        }
    }

    /* compiled from: CoroutineContext.kt */
    public interface Element extends CoroutineContext {
        <R> R fold(R r, Function2<? super R, ? super Element, ? extends R> function2);

        <E extends Element> E get(Key<E> key);

        Key<?> getKey();

        CoroutineContext minusKey(Key<?> key);

        /* compiled from: CoroutineContext.kt */
        public static final class DefaultImpls {
            public static <E extends Element> E get(Element element, Key<E> key) {
                if (Intrinsics.areEqual(element.getKey(), key)) {
                    return element;
                }
                return null;
            }

            public static CoroutineContext minusKey(Element element, Key<?> key) {
                if (Intrinsics.areEqual(element.getKey(), key)) {
                    return EmptyCoroutineContext.INSTANCE;
                }
                return element;
            }
        }
    }

    /* compiled from: CoroutineContext.kt */
    public interface Key<E extends Element> {
    }

    <R> R fold(R r, Function2<? super R, ? super Element, ? extends R> function2);

    <E extends Element> E get(Key<E> key);

    CoroutineContext minusKey(Key<?> key);

    CoroutineContext plus(CoroutineContext coroutineContext);
}
