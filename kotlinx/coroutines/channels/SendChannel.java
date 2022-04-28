package kotlinx.coroutines.channels;

import java.io.Serializable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: Channel.kt */
public interface SendChannel<E> {
    boolean close(Throwable th);

    boolean offer(Boolean bool);

    Object send(E e, Continuation<? super Unit> continuation);

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    Object m328trySendJP2dKIU(Serializable serializable);
}
