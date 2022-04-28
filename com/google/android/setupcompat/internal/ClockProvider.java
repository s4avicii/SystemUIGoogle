package com.google.android.setupcompat.internal;

public final class ClockProvider {
    public static Ticker ticker = ClockProvider$$ExternalSyntheticLambda1.INSTANCE;

    public interface Supplier<T> {
        T get();
    }

    public static void resetInstance() {
        ticker = ClockProvider$$ExternalSyntheticLambda1.INSTANCE;
    }

    public static void setInstance(Supplier<Long> supplier) {
        ticker = new ClockProvider$$ExternalSyntheticLambda0(supplier);
    }
}
