package kotlinx.coroutines.internal;

import androidx.preference.R$color;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CompletionStateKt;

/* compiled from: Scopes.kt */
public class ScopeCoroutine<T> extends AbstractCoroutine<T> implements CoroutineStackFrame {
    public final Continuation<T> uCont;

    public ScopeCoroutine(CoroutineContext coroutineContext, Continuation<? super T> continuation) {
        super(coroutineContext, true);
        this.uCont = continuation;
    }

    public final StackTraceElement getStackTraceElement() {
        return null;
    }

    public final boolean isScopedCoroutine() {
        return true;
    }

    public final void afterCompletion(Object obj) {
        DispatchedContinuationKt.resumeCancellableWith(R$color.intercepted(this.uCont), CompletionStateKt.recoverResult(obj, this.uCont), (Function1<? super Throwable, Unit>) null);
    }

    public void afterResume(Object obj) {
        Continuation<T> continuation = this.uCont;
        continuation.resumeWith(CompletionStateKt.recoverResult(obj, continuation));
    }

    public final CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.uCont;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }
}
