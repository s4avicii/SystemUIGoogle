package kotlinx.coroutines;

import java.util.Objects;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.scheduling.LimitingDispatcher;

/* compiled from: Dispatchers.kt */
public final class Dispatchers {
    public static final ExecutorCoroutineDispatcher Default;

    /* renamed from: IO */
    public static final LimitingDispatcher f157IO = DefaultScheduler.f162IO;

    static {
        ExecutorCoroutineDispatcher executorCoroutineDispatcher;
        if (CoroutineContextKt.useCoroutinesScheduler) {
            executorCoroutineDispatcher = DefaultScheduler.INSTANCE;
        } else {
            executorCoroutineDispatcher = CommonPool.INSTANCE;
        }
        Default = executorCoroutineDispatcher;
        int i = Unconfined.$r8$clinit;
        Objects.requireNonNull(DefaultScheduler.INSTANCE);
    }
}
