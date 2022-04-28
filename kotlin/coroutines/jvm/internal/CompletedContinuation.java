package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

/* compiled from: ContinuationImpl.kt */
public final class CompletedContinuation implements Continuation<Object> {
    public static final CompletedContinuation INSTANCE = new CompletedContinuation();

    public final String toString() {
        return "This continuation is already complete";
    }

    public final CoroutineContext getContext() {
        throw new IllegalStateException("This continuation is already complete".toString());
    }

    public final void resumeWith(Object obj) {
        throw new IllegalStateException("This continuation is already complete".toString());
    }
}
