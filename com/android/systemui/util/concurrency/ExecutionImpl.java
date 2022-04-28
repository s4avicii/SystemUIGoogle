package com.android.systemui.util.concurrency;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Looper;

/* compiled from: Execution.kt */
public final class ExecutionImpl implements Execution {
    public final Looper mainLooper = Looper.getMainLooper();

    public final void assertIsMainThread() {
        if (!this.mainLooper.isCurrentThread()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("should be called from the main thread. Main thread name=");
            m.append(this.mainLooper.getThread().getName());
            m.append(" Thread.currentThread()=");
            m.append(Thread.currentThread().getName());
            throw new IllegalStateException(m.toString());
        }
    }

    public final boolean isMainThread() {
        return this.mainLooper.isCurrentThread();
    }
}
