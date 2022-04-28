package com.android.systemui.dump;

import android.util.ArrayMap;
import com.android.systemui.Dumpable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DumpManager.kt */
public final class DumpManager {
    public final ArrayMap buffers = new ArrayMap();
    public final ArrayMap dumpables = new ArrayMap();

    public final synchronized void dumpBuffers(PrintWriter printWriter, int i) {
        for (RegisteredDumpable dumpBuffer : this.buffers.values()) {
            dumpBuffer(dumpBuffer, printWriter, i);
        }
    }

    public final synchronized void dumpDumpables(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (RegisteredDumpable dumpDumpable : this.dumpables.values()) {
            dumpDumpable(dumpDumpable, fileDescriptor, printWriter, strArr);
        }
    }

    public final synchronized void listBuffers(PrintWriter printWriter) {
        for (RegisteredDumpable registeredDumpable : this.buffers.values()) {
            Objects.requireNonNull(registeredDumpable);
            printWriter.println(registeredDumpable.name);
        }
    }

    public final synchronized void listDumpables(PrintWriter printWriter) {
        for (RegisteredDumpable registeredDumpable : this.dumpables.values()) {
            Objects.requireNonNull(registeredDumpable);
            printWriter.println(registeredDumpable.name);
        }
    }

    public final synchronized void registerDumpable(String str, Dumpable dumpable) {
        if (canAssignToNameLocked(str, dumpable)) {
            this.dumpables.put(str, new RegisteredDumpable(str, dumpable));
        } else {
            throw new IllegalArgumentException('\'' + str + "' is already registered");
        }
    }

    public final synchronized void unregisterDumpable(String str) {
        this.dumpables.remove(str);
    }

    public final boolean canAssignToNameLocked(String str, Object obj) {
        Object obj2;
        RegisteredDumpable registeredDumpable = (RegisteredDumpable) this.dumpables.get(str);
        Object obj3 = null;
        if (registeredDumpable == null) {
            obj2 = null;
        } else {
            obj2 = (Dumpable) registeredDumpable.dumpable;
        }
        if (obj2 == null) {
            RegisteredDumpable registeredDumpable2 = (RegisteredDumpable) this.buffers.get(str);
            if (registeredDumpable2 != null) {
                obj3 = (LogBuffer) registeredDumpable2.dumpable;
            }
        } else {
            obj3 = obj2;
        }
        if (obj3 == null || Intrinsics.areEqual(obj, obj3)) {
            return true;
        }
        return false;
    }

    public static void dumpBuffer(RegisteredDumpable registeredDumpable, PrintWriter printWriter, int i) {
        int i2;
        printWriter.println();
        printWriter.println();
        StringBuilder sb = new StringBuilder();
        sb.append("BUFFER ");
        Objects.requireNonNull(registeredDumpable);
        sb.append(registeredDumpable.name);
        sb.append(':');
        printWriter.println(sb.toString());
        printWriter.println("============================================================================");
        LogBuffer logBuffer = (LogBuffer) registeredDumpable.dumpable;
        Objects.requireNonNull(logBuffer);
        synchronized (logBuffer) {
            int i3 = 0;
            if (i <= 0) {
                i2 = 0;
            } else {
                i2 = logBuffer.buffer.size() - i;
            }
            Iterator<LogMessageImpl> it = logBuffer.buffer.iterator();
            while (it.hasNext()) {
                int i4 = i3 + 1;
                LogMessageImpl next = it.next();
                if (i3 >= i2) {
                    LogBuffer.dumpMessage(next, printWriter);
                }
                i3 = i4;
            }
        }
    }

    public static void dumpDumpable(RegisteredDumpable registeredDumpable, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println();
        Objects.requireNonNull(registeredDumpable);
        printWriter.println(Intrinsics.stringPlus(registeredDumpable.name, ":"));
        printWriter.println("----------------------------------------------------------------------------");
        ((Dumpable) registeredDumpable.dumpable).dump(fileDescriptor, printWriter, strArr);
    }

    public final synchronized void registerDumpable(Dumpable dumpable) {
        registerDumpable(dumpable.getClass().getSimpleName(), dumpable);
    }
}
