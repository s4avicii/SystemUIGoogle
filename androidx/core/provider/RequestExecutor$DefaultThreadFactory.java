package androidx.core.provider;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

public final class RequestExecutor$DefaultThreadFactory implements ThreadFactory {
    public int mPriority = 10;
    public String mThreadName = "fonts-androidx";

    public static class ProcessPriorityThread extends Thread {
        public final int mPriority;

        public final void run() {
            Process.setThreadPriority(this.mPriority);
            super.run();
        }

        public ProcessPriorityThread(Runnable runnable, String str, int i) {
            super(runnable, str);
            this.mPriority = i;
        }
    }

    public final Thread newThread(Runnable runnable) {
        return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
    }
}
