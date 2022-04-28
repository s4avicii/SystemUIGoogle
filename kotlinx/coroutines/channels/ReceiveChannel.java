package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.channels.AbstractChannel;

/* compiled from: Channel.kt */
public interface ReceiveChannel<E> {
    void cancel(CancellationException cancellationException);

    AbstractChannel.Itr iterator();

    /* renamed from: receiveCatching-JP2dKIU  reason: not valid java name */
    Object m326receiveCatchingJP2dKIU(Continuation<? super ChannelResult<? extends E>> continuation);

    /* renamed from: tryReceive-PtdJZtk  reason: not valid java name */
    Object m327tryReceivePtdJZtk();
}
