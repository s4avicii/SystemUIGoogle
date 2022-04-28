package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: AbstractChannel.kt */
public abstract class Receive<E> extends LockFreeLinkedListNode implements ReceiveOrClosed<E> {
    public Function1<Throwable, Unit> resumeOnCancellationFun(E e) {
        return null;
    }

    public abstract void resumeReceiveClosed(Closed<?> closed);

    public final /* bridge */ /* synthetic */ Object getOfferResult() {
        return AbstractChannelKt.OFFER_SUCCESS;
    }
}
