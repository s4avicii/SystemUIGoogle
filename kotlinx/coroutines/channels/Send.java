package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: AbstractChannel.kt */
public abstract class Send extends LockFreeLinkedListNode {
    public abstract void completeResumeSend();

    public abstract Object getPollResult();

    public abstract void resumeSendClosed(Closed<?> closed);

    public abstract Symbol tryResumeSend();

    public void undeliveredElement() {
    }
}
