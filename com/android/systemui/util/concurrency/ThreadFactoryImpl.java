package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public final class ThreadFactoryImpl implements ThreadFactory {
    public final ExecutorImpl buildExecutorOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new ExecutorImpl(handlerThread.getLooper());
    }

    public final Handler buildHandlerOnNewThread() {
        return new Handler(buildLooperOnNewThread("ScreenDecorations"));
    }

    public final Looper buildLooperOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return handlerThread.getLooper();
    }

    public final ExecutorImpl buildDelayableExecutorOnHandler(Handler handler) {
        return new ExecutorImpl(handler.getLooper());
    }
}
