package com.android.systemui.util.concurrency;

import dagger.internal.Factory;
import java.lang.Thread;
import java.util.Objects;
import java.util.Optional;

public final class GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory implements Factory<Optional<Thread.UncaughtExceptionHandler>> {

    public static final class InstanceHolder {
        public static final GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory INSTANCE = new GlobalConcurrencyModule_ProvidesUncaughtExceptionHandlerFactory();
    }

    public final Object get() {
        Optional ofNullable = Optional.ofNullable(Thread.getUncaughtExceptionPreHandler());
        Objects.requireNonNull(ofNullable, "Cannot return null from a non-@Nullable @Provides method");
        return ofNullable;
    }
}
