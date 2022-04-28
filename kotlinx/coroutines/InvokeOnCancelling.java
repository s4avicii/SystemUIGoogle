package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicInt;

/* compiled from: JobSupport.kt */
public final class InvokeOnCancelling extends JobCancellingNode {
    public final AtomicInt _invoked = new AtomicInt();
    public final Function1<Throwable, Unit> handler;

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        if (this._invoked.compareAndSet(0, 1)) {
            this.handler.invoke(th);
        }
    }

    public InvokeOnCancelling(JobNode jobNode) {
        this.handler = jobNode;
    }
}
