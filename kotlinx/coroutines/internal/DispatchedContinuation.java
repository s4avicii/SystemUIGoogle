package kotlinx.coroutines.internal;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletedWithCancellation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

/* compiled from: DispatchedContinuation.kt */
public final class DispatchedContinuation<T> extends DispatchedTask<T> implements CoroutineStackFrame, Continuation<T> {
    public final AtomicRef<Object> _reusableCancellableContinuation;
    public Object _state = DispatchedContinuationKt.UNDEFINED;
    public final Continuation<T> continuation;
    public final Object countOrElement;
    public final CoroutineDispatcher dispatcher;

    public DispatchedContinuation(CoroutineDispatcher coroutineDispatcher, ContinuationImpl continuationImpl) {
        super(-1);
        this.dispatcher = coroutineDispatcher;
        this.continuation = continuationImpl;
        Object fold = getContext().fold(0, ThreadContextKt.countAll);
        Intrinsics.checkNotNull(fold);
        this.countOrElement = fold;
        this._reusableCancellableContinuation = new AtomicRef<>((Object) null);
    }

    public final CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    /* renamed from: getDelegate$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Continuation<T> mo21195xefbb4835() {
        return this;
    }

    public final StackTraceElement getStackTraceElement() {
        return null;
    }

    /* renamed from: cancelCompletedResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void mo21191xd43a9d22(Object obj, CancellationException cancellationException) {
        if (obj instanceof CompletedWithCancellation) {
            ((CompletedWithCancellation) obj).onCancellation.invoke(cancellationException);
        }
    }

    public final CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation2 = this.continuation;
        if (continuation2 instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation2;
        }
        return null;
    }

    public final void resumeWith(Object obj) {
        Object obj2;
        CoroutineContext context;
        Object updateThreadContext;
        CoroutineContext context2 = this.continuation.getContext();
        Throwable r1 = Result.m320exceptionOrNullimpl(obj);
        if (r1 == null) {
            obj2 = obj;
        } else {
            obj2 = new CompletedExceptionally(r1, false);
        }
        if (this.dispatcher.isDispatchNeeded()) {
            this._state = obj2;
            this.resumeMode = 0;
            this.dispatcher.dispatch(context2, this);
            return;
        }
        boolean z = DebugKt.DEBUG;
        EventLoop eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines = ThreadLocalEventLoop.m129x4695df28();
        if (eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.isUnconfinedLoopActive()) {
            this._state = obj2;
            this.resumeMode = 0;
            eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.dispatchUnconfined(this);
            return;
        }
        eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.incrementUseCount(true);
        try {
            context = getContext();
            updateThreadContext = ThreadContextKt.updateThreadContext(context, this.countOrElement);
            this.continuation.resumeWith(obj);
            ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            do {
            } while (eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.processUnconfinedEvent());
        } catch (Throwable th) {
            try {
                handleFatalException(th, (Throwable) null);
            } catch (Throwable th2) {
                eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.decrementUseCount(true);
                throw th2;
            }
        }
        eventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines.decrementUseCount(true);
    }

    /* renamed from: takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object mo21206x99f628c6() {
        Object obj = this._state;
        boolean z = DebugKt.DEBUG;
        this._state = DispatchedContinuationKt.UNDEFINED;
        return obj;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("DispatchedContinuation[");
        m.append(this.dispatcher);
        m.append(", ");
        m.append(DebugStringsKt.toDebugString(this.continuation));
        m.append(']');
        return m.toString();
    }
}
