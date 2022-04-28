package kotlin.coroutines;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import java.io.Serializable;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: CoroutineContextImpl.kt */
public final class CombinedContext implements CoroutineContext, Serializable {
    private final CoroutineContext.Element element;
    private final CoroutineContext left;

    /* compiled from: CoroutineContextImpl.kt */
    public static final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final CoroutineContext[] elements;

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            CoroutineContext coroutineContext = EmptyCoroutineContext.INSTANCE;
            int length = coroutineContextArr.length;
            int i = 0;
            while (i < length) {
                CoroutineContext coroutineContext2 = coroutineContextArr[i];
                i++;
                coroutineContext = coroutineContext.plus(coroutineContext2);
            }
            return coroutineContext;
        }

        public Serialized(CoroutineContext[] coroutineContextArr) {
            this.elements = coroutineContextArr;
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (!(obj instanceof CombinedContext)) {
                return false;
            }
            CombinedContext combinedContext = (CombinedContext) obj;
            if (combinedContext.size() != size()) {
                return false;
            }
            while (true) {
                CoroutineContext.Element element2 = this.element;
                if (Intrinsics.areEqual(combinedContext.get(element2.getKey()), element2)) {
                    CoroutineContext coroutineContext = this.left;
                    if (!(coroutineContext instanceof CombinedContext)) {
                        CoroutineContext.Element element3 = (CoroutineContext.Element) coroutineContext;
                        z = Intrinsics.areEqual(combinedContext.get(element3.getKey()), element3);
                        break;
                    }
                    this = (CombinedContext) coroutineContext;
                } else {
                    z = false;
                    break;
                }
            }
            return z;
        }
    }

    public final int size() {
        int i = 2;
        while (true) {
            CoroutineContext coroutineContext = r2.left;
            if (coroutineContext instanceof CombinedContext) {
                r2 = (CombinedContext) coroutineContext;
            } else {
                r2 = null;
            }
            if (r2 == null) {
                return i;
            }
            i++;
        }
    }

    public final <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return function2.invoke(this.left.fold(r, function2), this.element);
    }

    public final <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        while (true) {
            E e = this.element.get(key);
            if (e != null) {
                return e;
            }
            CoroutineContext coroutineContext = this.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                return coroutineContext.get(key);
            }
            this = (CombinedContext) coroutineContext;
        }
    }

    public final int hashCode() {
        return this.element.hashCode() + this.left.hashCode();
    }

    public final CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        if (this.element.get(key) != null) {
            return this.left;
        }
        CoroutineContext minusKey = this.left.minusKey(key);
        if (minusKey == this.left) {
            return this;
        }
        if (minusKey == EmptyCoroutineContext.INSTANCE) {
            return this.element;
        }
        return new CombinedContext(minusKey, this.element);
    }

    public final String toString() {
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append((String) fold("", CombinedContext$toString$1.INSTANCE));
        m.append(']');
        return m.toString();
    }

    public CombinedContext(CoroutineContext coroutineContext, CoroutineContext.Element element2) {
        this.left = coroutineContext;
        this.element = element2;
    }

    private final Object writeReplace() {
        boolean z;
        int size = size();
        CoroutineContext[] coroutineContextArr = new CoroutineContext[size];
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        fold(Unit.INSTANCE, new CombinedContext$writeReplace$1(coroutineContextArr, ref$IntRef));
        if (ref$IntRef.element == size) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return new Serialized(coroutineContextArr);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }
}
