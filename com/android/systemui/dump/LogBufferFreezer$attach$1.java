package com.android.systemui.dump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.log.LogBuffer;
import java.util.Objects;

/* compiled from: LogBufferFreezer.kt */
public final class LogBufferFreezer$attach$1 extends BroadcastReceiver {
    public final /* synthetic */ LogBufferFreezer this$0;

    public LogBufferFreezer$attach$1(LogBufferFreezer logBufferFreezer) {
        this.this$0 = logBufferFreezer;
    }

    public final void onReceive(Context context, Intent intent) {
        LogBufferFreezer logBufferFreezer = this.this$0;
        Objects.requireNonNull(logBufferFreezer);
        Runnable runnable = logBufferFreezer.pendingToken;
        if (runnable != null) {
            runnable.run();
        }
        Log.i("LogBufferFreezer", "Freezing log buffers");
        DumpManager dumpManager = logBufferFreezer.dumpManager;
        Objects.requireNonNull(dumpManager);
        synchronized (dumpManager) {
            for (RegisteredDumpable registeredDumpable : dumpManager.buffers.values()) {
                Objects.requireNonNull(registeredDumpable);
                ((LogBuffer) registeredDumpable.dumpable).freeze();
            }
        }
        logBufferFreezer.pendingToken = logBufferFreezer.executor.executeDelayed(new LogBufferFreezer$onBugreportStarted$1(logBufferFreezer), logBufferFreezer.freezeDuration);
    }
}
