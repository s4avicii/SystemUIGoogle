package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: AbstractChannel.kt */
public interface ReceiveOrClosed<E> {
    void completeResumeReceive(E e);

    Object getOfferResult();

    Symbol tryResumeReceive(Object obj);
}
