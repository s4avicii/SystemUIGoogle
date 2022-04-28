package kotlinx.coroutines.intrinsics;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

/* compiled from: Undispatched.kt */
public final class UndispatchedKt {
    public static final Object startUndispatchedOrReturn(ScopeCoroutine scopeCoroutine, ScopeCoroutine scopeCoroutine2, Function2 function2) {
        Object obj;
        Object makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines;
        try {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2);
            obj = function2.invoke(scopeCoroutine2, scopeCoroutine);
        } catch (Throwable th) {
            obj = new CompletedExceptionally(th, false);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (obj == coroutineSingletons || (makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines = scopeCoroutine.mo21285xe12a6b8b(obj)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return coroutineSingletons;
        }
        if (!(makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
        }
        Throwable th2 = ((CompletedExceptionally) makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines).cause;
        Continuation<T> continuation = scopeCoroutine.uCont;
        if (DebugKt.RECOVER_STACK_TRACES && (continuation instanceof CoroutineStackFrame)) {
            th2 = StackTraceRecoveryKt.access$recoverFromStackFrame(th2, (CoroutineStackFrame) continuation);
        }
        throw th2;
    }
}
