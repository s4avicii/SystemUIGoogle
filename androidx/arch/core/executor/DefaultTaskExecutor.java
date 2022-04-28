package androidx.arch.core.executor;

import android.os.Handler;
import androidx.fragment.app.FragmentContainer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class DefaultTaskExecutor extends FragmentContainer {
    public final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory() {
        public final AtomicInteger mThreadId = new AtomicInteger(0);

        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName(String.format("arch_disk_io_%d", new Object[]{Integer.valueOf(this.mThreadId.getAndIncrement())}));
            return thread;
        }
    });
    public final Object mLock = new Object();
    public volatile Handler mMainHandler;
}
