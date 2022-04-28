package kotlinx.coroutines;

import java.util.Objects;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: CoroutineDispatcher.kt */
public abstract class CoroutineDispatcher extends AbstractCoroutineContextElement implements ContinuationInterceptor {
    public static final Key Key = new Key();

    /* compiled from: CoroutineDispatcher.kt */
    public static final class Key extends AbstractCoroutineContextKey<ContinuationInterceptor, CoroutineDispatcher> {
        public Key() {
            super(ContinuationInterceptor.Key.$$INSTANCE, C24991.INSTANCE);
        }
    }

    public abstract void dispatch(CoroutineContext coroutineContext, Runnable runnable);

    public boolean isDispatchNeeded() {
        return true;
    }

    public CoroutineDispatcher() {
        super(ContinuationInterceptor.Key.$$INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001a  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <E extends kotlin.coroutines.CoroutineContext.Element> E get(kotlin.coroutines.CoroutineContext.Key<E> r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof kotlin.coroutines.AbstractCoroutineContextKey
            r1 = 0
            if (r0 == 0) goto L_0x0027
            kotlin.coroutines.AbstractCoroutineContextKey r4 = (kotlin.coroutines.AbstractCoroutineContextKey) r4
            kotlin.coroutines.CoroutineContext$Key r0 = r3.getKey()
            if (r0 == r4) goto L_0x0014
            kotlin.coroutines.CoroutineContext$Key<?> r2 = r4.topmostKey
            if (r2 != r0) goto L_0x0012
            goto L_0x0017
        L_0x0012:
            r0 = 0
            goto L_0x0018
        L_0x0014:
            java.util.Objects.requireNonNull(r4)
        L_0x0017:
            r0 = 1
        L_0x0018:
            if (r0 == 0) goto L_0x002e
            kotlin.jvm.functions.Function1<kotlin.coroutines.CoroutineContext$Element, E> r4 = r4.safeCast
            java.lang.Object r3 = r4.invoke(r3)
            kotlin.coroutines.CoroutineContext$Element r3 = (kotlin.coroutines.CoroutineContext.Element) r3
            boolean r4 = r3 instanceof kotlin.coroutines.CoroutineContext.Element
            if (r4 == 0) goto L_0x002e
            goto L_0x002d
        L_0x0027:
            kotlin.coroutines.ContinuationInterceptor$Key r0 = kotlin.coroutines.ContinuationInterceptor.Key.$$INSTANCE
            if (r0 != r4) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            r3 = r1
        L_0x002d:
            r1 = r3
        L_0x002e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CoroutineDispatcher.get(kotlin.coroutines.CoroutineContext$Key):kotlin.coroutines.CoroutineContext$Element");
    }

    public final DispatchedContinuation interceptContinuation(ContinuationImpl continuationImpl) {
        return new DispatchedContinuation(this, continuationImpl);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0023  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.CoroutineContext minusKey(kotlin.coroutines.CoroutineContext.Key<?> r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof kotlin.coroutines.AbstractCoroutineContextKey
            if (r0 == 0) goto L_0x0026
            kotlin.coroutines.AbstractCoroutineContextKey r3 = (kotlin.coroutines.AbstractCoroutineContextKey) r3
            kotlin.coroutines.CoroutineContext$Key r0 = r2.getKey()
            if (r0 == r3) goto L_0x0013
            kotlin.coroutines.CoroutineContext$Key<?> r1 = r3.topmostKey
            if (r1 != r0) goto L_0x0011
            goto L_0x0016
        L_0x0011:
            r0 = 0
            goto L_0x0017
        L_0x0013:
            java.util.Objects.requireNonNull(r3)
        L_0x0016:
            r0 = 1
        L_0x0017:
            if (r0 == 0) goto L_0x002c
            kotlin.jvm.functions.Function1<kotlin.coroutines.CoroutineContext$Element, E> r3 = r3.safeCast
            java.lang.Object r3 = r3.invoke(r2)
            kotlin.coroutines.CoroutineContext$Element r3 = (kotlin.coroutines.CoroutineContext.Element) r3
            if (r3 == 0) goto L_0x002c
            kotlin.coroutines.EmptyCoroutineContext r2 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            goto L_0x002c
        L_0x0026:
            kotlin.coroutines.ContinuationInterceptor$Key r0 = kotlin.coroutines.ContinuationInterceptor.Key.$$INSTANCE
            if (r0 != r3) goto L_0x002c
            kotlin.coroutines.EmptyCoroutineContext r2 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
        L_0x002c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CoroutineDispatcher.minusKey(kotlin.coroutines.CoroutineContext$Key):kotlin.coroutines.CoroutineContext");
    }

    public final void releaseInterceptedContinuation(Continuation<?> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl;
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        AtomicRef<Object> atomicRef = dispatchedContinuation._reusableCancellableContinuation;
        do {
            Objects.requireNonNull(atomicRef);
        } while (atomicRef.value == DispatchedContinuationKt.REUSABLE_CLAIMED);
        AtomicRef<Object> atomicRef2 = dispatchedContinuation._reusableCancellableContinuation;
        Objects.requireNonNull(atomicRef2);
        T t = atomicRef2.value;
        if (t instanceof CancellableContinuationImpl) {
            cancellableContinuationImpl = (CancellableContinuationImpl) t;
        } else {
            cancellableContinuationImpl = null;
        }
        if (cancellableContinuationImpl != null) {
            cancellableContinuationImpl.mo21192x2343eba7();
        }
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this);
    }

    public void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        dispatch(coroutineContext, runnable);
    }
}
