package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

enum DirectExecutor implements Executor {
    ;

    public final String toString() {
        return "MoreExecutors.directExecutor()";
    }

    /* access modifiers changed from: public */
    DirectExecutor() {
    }

    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
