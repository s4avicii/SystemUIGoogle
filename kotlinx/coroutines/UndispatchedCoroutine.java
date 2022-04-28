package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: CoroutineContext.kt */
public final class UndispatchedCoroutine<T> extends ScopeCoroutine<T> {
    public CoroutineContext savedContext;
    public Object savedOldValue;

    public final void afterResume(Object obj) {
        CoroutineContext coroutineContext = this.savedContext;
        UndispatchedCoroutine<?> undispatchedCoroutine = null;
        if (coroutineContext != null) {
            ThreadContextKt.restoreThreadContext(coroutineContext, this.savedOldValue);
            this.savedContext = undispatchedCoroutine;
            this.savedOldValue = undispatchedCoroutine;
        }
        Object recoverResult = CompletionStateKt.recoverResult(obj, this.uCont);
        Continuation<T> continuation = this.uCont;
        CoroutineContext context = continuation.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, undispatchedCoroutine);
        if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
        }
        try {
            this.uCont.resumeWith(recoverResult);
        } finally {
            if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
        }
    }

    public final boolean clearThreadContext() {
        if (this.savedContext == null) {
            return false;
        }
        this.savedContext = null;
        this.savedOldValue = null;
        return true;
    }
}
