package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public final class ExecutorImpl implements DelayableExecutor {
    public final Handler mHandler;

    public class ExecutionToken implements Runnable {
        public final Runnable runnable;

        public ExecutionToken(Runnable runnable2) {
            this.runnable = runnable2;
        }

        public final void run() {
            ExecutorImpl.this.mHandler.removeCallbacksAndMessages(this);
        }
    }

    public final void execute(Runnable runnable) {
        if (!this.mHandler.post(runnable)) {
            throw new RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }

    public final ExecutionToken executeAtTime(ClockManager$$ExternalSyntheticLambda1 clockManager$$ExternalSyntheticLambda1, long j) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        ExecutionToken executionToken = new ExecutionToken(clockManager$$ExternalSyntheticLambda1);
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(0, executionToken), timeUnit.toMillis(j));
        return executionToken;
    }

    public final ExecutionToken executeDelayed(Runnable runnable, long j, TimeUnit timeUnit) {
        ExecutionToken executionToken = new ExecutionToken(runnable);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, executionToken), timeUnit.toMillis(j));
        return executionToken;
    }

    public ExecutorImpl(Looper looper) {
        this.mHandler = new Handler(looper, new ExecutorImpl$$ExternalSyntheticLambda0(this));
    }
}
