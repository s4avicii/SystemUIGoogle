package kotlinx.coroutines.channels;

import kotlin.p018io.CloseableKt;

/* compiled from: Channel.kt */
public interface Channel<E> extends SendChannel<E>, ReceiveChannel<E> {
    public static final Factory Factory = Factory.$$INSTANCE;

    /* compiled from: Channel.kt */
    public static final class Factory {
        public static final /* synthetic */ Factory $$INSTANCE = new Factory();
        public static final int CHANNEL_DEFAULT_CAPACITY = ((int) CloseableKt.systemProp("kotlinx.coroutines.channels.defaultBuffer", (long) 64, (long) 1, (long) 2147483646));
    }
}
