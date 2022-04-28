package kotlinx.coroutines;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: DispatchedTask.kt */
public final class DispatchedTaskKt {
    public static final <T> void resume(DispatchedTask<? super T> dispatchedTask, Continuation<? super T> continuation, boolean z) {
        Object obj;
        UndispatchedCoroutine<?> undispatchedCoroutine;
        Object takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines = dispatchedTask.mo21206x99f628c6();
        Throwable exceptionalResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines = dispatchedTask.mo21196xd8447f2f(takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
        if (exceptionalResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines != null) {
            obj = new Result.Failure(exceptionalResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
        } else {
            obj = dispatchedTask.mo21198x119202a3(takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
        }
        if (z) {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
            Continuation<T> continuation2 = dispatchedContinuation.continuation;
            Object obj2 = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation2.getContext();
            Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
            if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
                undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext);
            } else {
                undispatchedCoroutine = null;
            }
            try {
                dispatchedContinuation.continuation.resumeWith(obj);
            } finally {
                if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                }
            }
        } else {
            continuation.resumeWith(obj);
        }
    }
}
