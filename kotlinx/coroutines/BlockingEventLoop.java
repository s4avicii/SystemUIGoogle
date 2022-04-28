package kotlinx.coroutines;

/* compiled from: EventLoop.kt */
public final class BlockingEventLoop extends EventLoopImplBase {
    public final Thread thread;

    public BlockingEventLoop(Thread thread2) {
        this.thread = thread2;
    }

    public final Thread getThread() {
        return this.thread;
    }
}
