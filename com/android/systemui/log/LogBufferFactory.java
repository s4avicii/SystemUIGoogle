package com.android.systemui.log;

import android.app.ActivityManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.RegisteredDumpable;
import java.util.Objects;

/* compiled from: LogBufferFactory.kt */
public final class LogBufferFactory {
    public final DumpManager dumpManager;
    public final LogcatEchoTracker logcatEchoTracker;

    public final LogBuffer create(String str, int i) {
        return create(str, i, 10, true);
    }

    public final LogBuffer create(String str, int i, int i2, boolean z) {
        if (ActivityManager.isLowRamDeviceStatic()) {
            i = Math.min(i, 20);
        }
        LogBuffer logBuffer = new LogBuffer(str, i, i2, this.logcatEchoTracker, z);
        DumpManager dumpManager2 = this.dumpManager;
        Objects.requireNonNull(dumpManager2);
        synchronized (dumpManager2) {
            if (dumpManager2.canAssignToNameLocked(str, logBuffer)) {
                dumpManager2.buffers.put(str, new RegisteredDumpable(str, logBuffer));
            } else {
                throw new IllegalArgumentException('\'' + str + "' is already registered");
            }
        }
        return logBuffer;
    }

    public LogBufferFactory(DumpManager dumpManager2, LogcatEchoTracker logcatEchoTracker2) {
        this.dumpManager = dumpManager2;
        this.logcatEchoTracker = logcatEchoTracker2;
    }
}
