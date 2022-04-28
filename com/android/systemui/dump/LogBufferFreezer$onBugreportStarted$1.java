package com.android.systemui.dump;

import android.util.Log;
import com.android.systemui.log.LogBuffer;
import java.util.Objects;

/* compiled from: LogBufferFreezer.kt */
public final class LogBufferFreezer$onBugreportStarted$1 implements Runnable {
    public final /* synthetic */ LogBufferFreezer this$0;

    public LogBufferFreezer$onBugreportStarted$1(LogBufferFreezer logBufferFreezer) {
        this.this$0 = logBufferFreezer;
    }

    public final void run() {
        Log.i("LogBufferFreezer", "Unfreezing log buffers");
        LogBufferFreezer logBufferFreezer = this.this$0;
        logBufferFreezer.pendingToken = null;
        DumpManager dumpManager = logBufferFreezer.dumpManager;
        Objects.requireNonNull(dumpManager);
        synchronized (dumpManager) {
            for (RegisteredDumpable registeredDumpable : dumpManager.buffers.values()) {
                Objects.requireNonNull(registeredDumpable);
                ((LogBuffer) registeredDumpable.dumpable).unfreeze();
            }
        }
    }
}
