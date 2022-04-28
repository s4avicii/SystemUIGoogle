package com.android.systemui.util.concurrency;

import com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public interface DelayableExecutor extends Executor {
    ExecutorImpl.ExecutionToken executeAtTime(ClockManager$$ExternalSyntheticLambda1 clockManager$$ExternalSyntheticLambda1, long j);

    /* renamed from: executeAtTime  reason: collision with other method in class */
    Runnable m284executeAtTime(ClockManager$$ExternalSyntheticLambda1 clockManager$$ExternalSyntheticLambda1, long j) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        return executeAtTime(clockManager$$ExternalSyntheticLambda1, j);
    }

    ExecutorImpl.ExecutionToken executeDelayed(Runnable runnable, long j, TimeUnit timeUnit);

    Runnable executeDelayed(Runnable runnable, long j) {
        return executeDelayed(runnable, j, TimeUnit.MILLISECONDS);
    }
}
