package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: ThreadContext.kt */
public final class ThreadContextKt$updateState$1 extends Lambda implements Function2<ThreadState, CoroutineContext.Element, ThreadState> {
    public static final ThreadContextKt$updateState$1 INSTANCE = new ThreadContextKt$updateState$1();

    public ThreadContextKt$updateState$1() {
        super(2);
    }

    public final Object invoke(Object obj, Object obj2) {
        ThreadState threadState = (ThreadState) obj;
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        if (element instanceof ThreadContextElement) {
            ThreadContextElement<Object> threadContextElement = (ThreadContextElement) element;
            String updateThreadContext = threadContextElement.updateThreadContext(threadState.context);
            Object[] objArr = threadState.values;
            int i = threadState.f161i;
            objArr[i] = updateThreadContext;
            ThreadContextElement<Object>[] threadContextElementArr = threadState.elements;
            threadState.f161i = i + 1;
            threadContextElementArr[i] = threadContextElement;
        }
        return threadState;
    }
}
