package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.ThreadContextElement;

/* compiled from: ThreadContext.kt */
public final class ThreadState {
    public final CoroutineContext context;
    public final ThreadContextElement<Object>[] elements;

    /* renamed from: i */
    public int f161i;
    public final Object[] values;

    public ThreadState(CoroutineContext coroutineContext, int i) {
        this.context = coroutineContext;
        this.values = new Object[i];
        this.elements = new ThreadContextElement[i];
    }
}
