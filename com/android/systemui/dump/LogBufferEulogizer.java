package com.android.systemui.dump;

import android.content.Context;
import com.android.systemui.util.p010io.Files;
import com.android.systemui.util.time.SystemClock;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

/* compiled from: LogBufferEulogizer.kt */
public final class LogBufferEulogizer {
    public final DumpManager dumpManager;
    public final Files files;
    public final Path logPath;
    public final long maxLogAgeToDump;
    public final long minWriteGap;
    public final SystemClock systemClock;

    public final long getMillisSinceLastWrite(Path path) {
        BasicFileAttributes basicFileAttributes;
        FileTime lastModifiedTime;
        try {
            Objects.requireNonNull(this.files);
            basicFileAttributes = java.nio.file.Files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
        } catch (IOException unused) {
            basicFileAttributes = null;
        }
        long currentTimeMillis = this.systemClock.currentTimeMillis();
        long j = 0;
        if (!(basicFileAttributes == null || (lastModifiedTime = basicFileAttributes.lastModifiedTime()) == null)) {
            j = lastModifiedTime.toMillis();
        }
        return currentTimeMillis - j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a1, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r7, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a5, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Exception record(java.lang.IllegalStateException r15) {
        /*
            r14 = this;
            java.lang.String r0 = "ms"
            java.lang.String r1 = "Buffer eulogy took "
            com.android.systemui.util.time.SystemClock r2 = r14.systemClock
            long r2 = r2.uptimeMillis()
            java.lang.String r4 = "BufferEulogizer"
            java.lang.String r5 = "Performing emergency dump of log buffers"
            android.util.Log.i(r4, r5)
            java.nio.file.Path r5 = r14.logPath
            long r5 = r14.getMillisSinceLastWrite(r5)
            long r7 = r14.minWriteGap
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x0037
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r0 = "Cannot dump logs, last write was only "
            r14.append(r0)
            r14.append(r5)
            java.lang.String r0 = " ms ago"
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            android.util.Log.w(r4, r14)
            return r15
        L_0x0037:
            r5 = 0
            com.android.systemui.util.io.Files r7 = r14.files     // Catch:{ Exception -> 0x00a6 }
            java.nio.file.Path r8 = r14.logPath     // Catch:{ Exception -> 0x00a6 }
            r9 = 2
            java.nio.file.OpenOption[] r9 = new java.nio.file.OpenOption[r9]     // Catch:{ Exception -> 0x00a6 }
            java.nio.file.StandardOpenOption r10 = java.nio.file.StandardOpenOption.CREATE     // Catch:{ Exception -> 0x00a6 }
            r11 = 0
            r9[r11] = r10     // Catch:{ Exception -> 0x00a6 }
            r10 = 1
            java.nio.file.StandardOpenOption r12 = java.nio.file.StandardOpenOption.TRUNCATE_EXISTING     // Catch:{ Exception -> 0x00a6 }
            r9[r10] = r12     // Catch:{ Exception -> 0x00a6 }
            java.util.Objects.requireNonNull(r7)     // Catch:{ Exception -> 0x00a6 }
            java.nio.charset.Charset r7 = java.nio.charset.StandardCharsets.UTF_8     // Catch:{ Exception -> 0x00a6 }
            java.io.BufferedWriter r7 = java.nio.file.Files.newBufferedWriter(r8, r7, r9)     // Catch:{ Exception -> 0x00a6 }
            r8 = 0
            java.io.PrintWriter r9 = new java.io.PrintWriter     // Catch:{ all -> 0x009f }
            r9.<init>(r7)     // Catch:{ all -> 0x009f }
            java.text.SimpleDateFormat r10 = com.android.systemui.dump.LogBufferEulogizerKt.DATE_FORMAT     // Catch:{ all -> 0x009f }
            com.android.systemui.util.time.SystemClock r12 = r14.systemClock     // Catch:{ all -> 0x009f }
            long r12 = r12.currentTimeMillis()     // Catch:{ all -> 0x009f }
            java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x009f }
            java.lang.String r10 = r10.format(r12)     // Catch:{ all -> 0x009f }
            r9.println(r10)     // Catch:{ all -> 0x009f }
            r9.println()     // Catch:{ all -> 0x009f }
            java.lang.String r10 = "Dump triggered by exception:"
            r9.println(r10)     // Catch:{ all -> 0x009f }
            r15.printStackTrace(r9)     // Catch:{ all -> 0x009f }
            com.android.systemui.dump.DumpManager r10 = r14.dumpManager     // Catch:{ all -> 0x009f }
            r10.dumpBuffers(r9, r11)     // Catch:{ all -> 0x009f }
            com.android.systemui.util.time.SystemClock r14 = r14.systemClock     // Catch:{ all -> 0x009f }
            long r5 = r14.uptimeMillis()     // Catch:{ all -> 0x009f }
            long r5 = r5 - r2
            r9.println()     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x009f }
            r14.<init>()     // Catch:{ all -> 0x009f }
            r14.append(r1)     // Catch:{ all -> 0x009f }
            r14.append(r5)     // Catch:{ all -> 0x009f }
            r14.append(r0)     // Catch:{ all -> 0x009f }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x009f }
            r9.println(r14)     // Catch:{ all -> 0x009f }
            kotlin.p018io.CloseableKt.closeFinally(r7, r8)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00ac
        L_0x009f:
            r14 = move-exception
            throw r14     // Catch:{ all -> 0x00a1 }
        L_0x00a1:
            r2 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r7, r14)     // Catch:{ Exception -> 0x00a6 }
            throw r2     // Catch:{ Exception -> 0x00a6 }
        L_0x00a6:
            r14 = move-exception
            java.lang.String r2 = "Exception while attempting to dump buffers, bailing"
            android.util.Log.e(r4, r2, r14)
        L_0x00ac:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r14.append(r5)
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            android.util.Log.i(r4, r14)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.LogBufferEulogizer.record(java.lang.IllegalStateException):java.lang.Exception");
    }

    public LogBufferEulogizer(Context context, DumpManager dumpManager2, SystemClock systemClock2, Files files2) {
        Path path = Paths.get(context.getFilesDir().toPath().toString(), new String[]{"log_buffers.txt"});
        long j = LogBufferEulogizerKt.MIN_WRITE_GAP;
        long j2 = LogBufferEulogizerKt.MAX_AGE_TO_DUMP;
        this.dumpManager = dumpManager2;
        this.systemClock = systemClock2;
        this.files = files2;
        this.logPath = path;
        this.minWriteGap = j;
        this.maxLogAgeToDump = j2;
    }
}
