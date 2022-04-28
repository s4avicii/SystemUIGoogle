package kotlinx.coroutines.scheduling;

import kotlin.p018io.CloseableKt;
import kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt;

/* compiled from: Dispatcher.kt */
public final class DefaultScheduler extends ExperimentalCoroutineDispatcher {
    public static final DefaultScheduler INSTANCE;

    /* renamed from: IO */
    public static final LimitingDispatcher f162IO;

    public final String toString() {
        return "Dispatchers.Default";
    }

    static {
        DefaultScheduler defaultScheduler = new DefaultScheduler();
        INSTANCE = defaultScheduler;
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        if (64 >= i) {
            i = 64;
        }
        f162IO = new LimitingDispatcher(defaultScheduler, CloseableKt.systemProp$default("kotlinx.coroutines.io.parallelism", i, 0, 0, 12));
    }

    public final void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }
}
