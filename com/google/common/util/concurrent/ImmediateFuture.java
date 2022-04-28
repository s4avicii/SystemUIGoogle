package com.google.common.util.concurrent;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ImmediateFuture<V> implements ListenableFuture<V> {
    public static final ImmediateFuture NULL = new ImmediateFuture();
    public static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());
    public final V value = null;

    public final boolean cancel(boolean z) {
        return false;
    }

    public final V get() {
        return this.value;
    }

    public final boolean isCancelled() {
        return false;
    }

    public final boolean isDone() {
        return true;
    }

    public final void addListener(Runnable runnable, Executor executor) {
        Objects.requireNonNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, e);
        }
    }

    public final V get(long j, TimeUnit timeUnit) throws ExecutionException {
        Objects.requireNonNull(timeUnit);
        return this.value;
    }

    public final String toString() {
        return super.toString() + "[status=SUCCESS, result=[" + this.value + "]]";
    }
}
