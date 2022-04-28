package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: FlowCoroutine.kt */
public final class FlowCoroutine<T> extends ScopeCoroutine<T> {
    public final boolean childCancelled(Throwable th) {
        return mo21272x7bd83b56(th);
    }

    public FlowCoroutine(CoroutineContext coroutineContext, Continuation<? super T> continuation) {
        super(coroutineContext, continuation);
    }
}
