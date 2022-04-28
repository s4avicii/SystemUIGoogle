package kotlinx.coroutines.flow.internal;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: SafeCollector.kt */
public final class SafeCollector<T> extends ContinuationImpl implements FlowCollector<T> {
    public final CoroutineContext collectContext;
    public final int collectContextSize;
    public final FlowCollector<T> collector;
    private Continuation<? super Unit> completion;
    private CoroutineContext lastEmissionContext;

    public final Object emit(T t, Continuation<? super Unit> continuation) {
        try {
            Object emit = emit(continuation, t);
            return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
        } catch (Throwable th) {
            this.lastEmissionContext = new DownstreamExceptionElement(th);
            throw th;
        }
    }

    public final StackTraceElement getStackTraceElement() {
        return null;
    }

    public SafeCollector(FlowCollector<? super T> flowCollector, CoroutineContext coroutineContext) {
        super(NoOpContinuation.INSTANCE, EmptyCoroutineContext.INSTANCE);
        this.collector = flowCollector;
        this.collectContext = coroutineContext;
        this.collectContextSize = ((Number) coroutineContext.fold(0, SafeCollector$collectContextSize$1.INSTANCE)).intValue();
    }

    public final CoroutineStackFrame getCallerFrame() {
        Continuation<? super Unit> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public final CoroutineContext getContext() {
        CoroutineContext coroutineContext;
        Continuation<? super Unit> continuation = this.completion;
        if (continuation == null) {
            coroutineContext = null;
        } else {
            coroutineContext = continuation.getContext();
        }
        if (coroutineContext == null) {
            return EmptyCoroutineContext.INSTANCE;
        }
        return coroutineContext;
    }

    public final Object invokeSuspend(Object obj) {
        Throwable r0 = Result.m320exceptionOrNullimpl(obj);
        if (r0 != null) {
            this.lastEmissionContext = new DownstreamExceptionElement(r0);
        }
        Continuation<? super Unit> continuation = this.completion;
        if (continuation != null) {
            continuation.resumeWith(obj);
        }
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object emit(Continuation<? super Unit> continuation, T t) {
        CoroutineContext context = continuation.getContext();
        JobKt.ensureActive(context);
        CoroutineContext coroutineContext = this.lastEmissionContext;
        if (coroutineContext != context) {
            if (coroutineContext instanceof DownstreamExceptionElement) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception ");
                m.append(((DownstreamExceptionElement) coroutineContext).f159e);
                m.append(", but then emission attempt of value '");
                m.append(t);
                m.append("' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ");
                throw new IllegalStateException(StringsKt__IndentKt.trimIndent(m.toString()).toString());
            } else if (((Number) context.fold(0, new SafeCollector_commonKt$checkContext$result$1(this))).intValue() == this.collectContextSize) {
                this.lastEmissionContext = context;
            } else {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Flow invariant is violated:\n\t\tFlow was collected in ");
                m2.append(this.collectContext);
                m2.append(",\n\t\tbut emission happened in ");
                m2.append(context);
                m2.append(".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead");
                throw new IllegalStateException(m2.toString().toString());
            }
        }
        this.completion = continuation;
        return SafeCollectorKt.emitFun.invoke(this.collector, t, this);
    }

    public final void releaseIntercepted() {
        super.releaseIntercepted();
    }
}
