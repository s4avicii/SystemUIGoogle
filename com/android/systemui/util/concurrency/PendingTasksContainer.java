package com.android.systemui.util.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: PendingTasksContainer.kt */
public final class PendingTasksContainer {
    public AtomicReference<Runnable> completionCallback = new AtomicReference<>();
    public AtomicInteger pendingTasksCount = new AtomicInteger(0);
}
