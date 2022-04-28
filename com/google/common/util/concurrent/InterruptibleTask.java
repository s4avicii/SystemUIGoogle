package com.google.common.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {

    public static final class Blocker extends AbstractOwnableSynchronizer implements Runnable {
        private final InterruptibleTask<?> task;

        public final void run() {
        }

        public final String toString() {
            throw null;
        }
    }
}
