package kotlinx.coroutines;

import androidx.preference.R$color;
import java.util.Objects;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: AbstractCoroutine.kt */
public abstract class AbstractCoroutine<T> extends JobSupport implements Continuation<T>, CoroutineScope {
    public final CoroutineContext context;

    /* renamed from: handleOnCompletionException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void mo21178x4a3c0b24(CompletionHandlerException completionHandlerException) {
        CoroutineExceptionHandlerKt.handleCoroutineException(this.context, completionHandlerException);
    }

    /* renamed from: nameString$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final String mo21179x29b568d4() {
        CoroutineId coroutineId;
        CoroutineContext coroutineContext = this.context;
        boolean z = CoroutineContextKt.useCoroutinesScheduler;
        String str = null;
        if (DebugKt.DEBUG && (coroutineId = (CoroutineId) coroutineContext.get(CoroutineId.Key)) != null) {
            CoroutineName coroutineName = (CoroutineName) coroutineContext.get(CoroutineName.Key);
            str = "coroutine" + '#' + coroutineId.f156id;
        }
        if (str == null) {
            return DebugStringsKt.getClassSimpleName(this);
        }
        return '\"' + str + "\":" + DebugStringsKt.getClassSimpleName(this);
    }

    public final void onCompletionInternal(Object obj) {
        if (obj instanceof CompletedExceptionally) {
            CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
            Throwable th = completedExceptionally.cause;
            Objects.requireNonNull(completedExceptionally._handled);
        }
    }

    public AbstractCoroutine(CoroutineContext coroutineContext, boolean z) {
        super(z);
        initParentJob((Job) coroutineContext.get(Job.Key.$$INSTANCE));
        this.context = coroutineContext.plus(this);
    }

    public final String cancellationExceptionMessage() {
        return Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " was cancelled");
    }

    public final boolean isActive() {
        return super.isActive();
    }

    public final void resumeWith(Object obj) {
        Throwable r0 = Result.m320exceptionOrNullimpl(obj);
        if (r0 != null) {
            obj = new CompletedExceptionally(r0, false);
        }
        Object makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines = mo21285xe12a6b8b(obj);
        if (makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            afterResume(makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines);
        }
    }

    public final void start(CoroutineStart coroutineStart, AbstractCoroutine abstractCoroutine, Function2 function2) {
        CoroutineContext coroutineContext;
        Object updateThreadContext;
        int ordinal = coroutineStart.ordinal();
        if (ordinal == 0) {
            try {
                DispatchedContinuationKt.resumeCancellableWith(R$color.intercepted(R$color.createCoroutineUnintercepted(function2, abstractCoroutine, this)), Unit.INSTANCE, (Function1<? super Throwable, Unit>) null);
            } catch (Throwable th) {
                resumeWith(new Result.Failure(th));
                throw th;
            }
        } else if (ordinal == 1) {
        } else {
            if (ordinal == 2) {
                R$color.intercepted(R$color.createCoroutineUnintercepted(function2, abstractCoroutine, this)).resumeWith(Unit.INSTANCE);
            } else if (ordinal == 3) {
                try {
                    coroutineContext = this.context;
                    updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, (Object) null);
                    TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2);
                    Object invoke = function2.invoke(abstractCoroutine, this);
                    ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
                    if (invoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                        resumeWith(invoke);
                    }
                } catch (Throwable th2) {
                    resumeWith(new Result.Failure(th2));
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    public void afterResume(Object obj) {
        afterCompletion(obj);
    }

    public final CoroutineContext getContext() {
        return this.context;
    }

    public final CoroutineContext getCoroutineContext() {
        return this.context;
    }
}
