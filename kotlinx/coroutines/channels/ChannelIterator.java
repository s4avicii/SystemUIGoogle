package kotlinx.coroutines.channels;

import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: Channel.kt */
public interface ChannelIterator<E> {
    Object hasNext(ContinuationImpl continuationImpl);

    E next();
}
