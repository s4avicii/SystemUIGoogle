package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;

/* compiled from: AbstractSharedFlow.kt */
public abstract class AbstractSharedFlowSlot<F> {
    public abstract boolean allocateLocked(AbstractSharedFlow abstractSharedFlow);

    public abstract Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow);
}
