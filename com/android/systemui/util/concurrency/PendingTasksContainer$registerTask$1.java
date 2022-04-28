package com.android.systemui.util.concurrency;

/* compiled from: PendingTasksContainer.kt */
public final class PendingTasksContainer$registerTask$1 implements Runnable {
    public final /* synthetic */ PendingTasksContainer this$0;

    public PendingTasksContainer$registerTask$1(PendingTasksContainer pendingTasksContainer) {
        this.this$0 = pendingTasksContainer;
    }

    public final void run() {
        Runnable andSet;
        if (this.this$0.pendingTasksCount.decrementAndGet() == 0 && (andSet = this.this$0.completionCallback.getAndSet((Object) null)) != null) {
            andSet.run();
        }
    }
}
