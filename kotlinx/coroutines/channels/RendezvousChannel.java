package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: RendezvousChannel.kt */
public final class RendezvousChannel<E> extends AbstractChannel<E> {
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    public final boolean isBufferAlwaysFull() {
        return true;
    }

    public final boolean isBufferEmpty() {
        return true;
    }

    public final boolean isBufferFull() {
        return true;
    }

    public RendezvousChannel(Function1<? super E, Unit> function1) {
        super(function1);
    }
}
