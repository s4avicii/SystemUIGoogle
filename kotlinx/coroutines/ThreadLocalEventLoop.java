package kotlinx.coroutines;

/* compiled from: EventLoop.common.kt */
public final class ThreadLocalEventLoop {
    public static final ThreadLocal<EventLoop> ref = new ThreadLocal<>();

    /* renamed from: getEventLoop$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public static EventLoop m129x4695df28() {
        ThreadLocal<EventLoop> threadLocal = ref;
        EventLoop eventLoop = threadLocal.get();
        if (eventLoop != null) {
            return eventLoop;
        }
        BlockingEventLoop blockingEventLoop = new BlockingEventLoop(Thread.currentThread());
        threadLocal.set(blockingEventLoop);
        return blockingEventLoop;
    }
}
