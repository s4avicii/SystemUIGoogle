package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

/* compiled from: SharedFlow.kt */
public final class SharedFlowSlot extends AbstractSharedFlowSlot<SharedFlowImpl<?>> {
    public CancellableContinuationImpl cont;
    public long index = -1;

    public final boolean allocateLocked(AbstractSharedFlow abstractSharedFlow) {
        SharedFlowImpl sharedFlowImpl = (SharedFlowImpl) abstractSharedFlow;
        if (this.index >= 0) {
            return false;
        }
        long j = sharedFlowImpl.replayIndex;
        if (j < sharedFlowImpl.minCollectorIndex) {
            sharedFlowImpl.minCollectorIndex = j;
        }
        this.index = j;
        return true;
    }

    public final Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow) {
        boolean z = DebugKt.DEBUG;
        long j = this.index;
        this.index = -1;
        this.cont = null;
        return ((SharedFlowImpl) abstractSharedFlow).mo21371xffe52cb8(j);
    }
}
