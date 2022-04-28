package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;

public interface ThreadFactory {
    ExecutorImpl buildDelayableExecutorOnHandler(Handler handler);

    ExecutorImpl buildExecutorOnNewThread(String str);

    Handler buildHandlerOnNewThread();

    Looper buildLooperOnNewThread(String str);
}
