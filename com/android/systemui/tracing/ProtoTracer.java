package com.android.systemui.tracing;

import android.content.Context;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceEntryProto;
import com.android.systemui.tracing.nano.SystemUiTraceFileProto;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.google.protobuf.nano.MessageNano;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public final class ProtoTracer implements Dumpable, FrameProtoTracer.ProtoTraceParams<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> {
    public final Context mContext;
    public final FrameProtoTracer<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> mProtoTracer = new FrameProtoTracer<>(this);

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("ProtoTracer:");
        printWriter.print("    ");
        StringBuilder sb = new StringBuilder();
        sb.append("enabled: ");
        FrameProtoTracer<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> frameProtoTracer = this.mProtoTracer;
        Objects.requireNonNull(frameProtoTracer);
        sb.append(frameProtoTracer.mEnabled);
        printWriter.println(sb.toString());
        printWriter.print("    ");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("usagePct: ");
        FrameProtoTracer<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> frameProtoTracer2 = this.mProtoTracer;
        Objects.requireNonNull(frameProtoTracer2);
        sb2.append(((float) frameProtoTracer2.mBuffer.getBufferSize()) / 1048576.0f);
        printWriter.println(sb2.toString());
        printWriter.print("    ");
        printWriter.println("file: " + new File(this.mContext.getFilesDir(), "sysui_trace.pb"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        android.os.Trace.beginSection("ProtoTracer.writeToFile");
        r0 = r2.mBuffer;
        r1 = r2.mTraceFile;
        java.util.Objects.requireNonNull((com.android.systemui.tracing.ProtoTracer) r2.mParams);
        r0.writeTraceToFile(r1, new com.android.systemui.tracing.nano.SystemUiTraceFileProto());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        android.util.Log.e("FrameProtoTracer", "Unable to write buffer to file", r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003c, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void stop() {
        /*
            r2 = this;
            com.android.systemui.shared.tracing.FrameProtoTracer<com.google.protobuf.nano.MessageNano, com.android.systemui.tracing.nano.SystemUiTraceFileProto, com.android.systemui.tracing.nano.SystemUiTraceEntryProto, com.android.systemui.tracing.nano.SystemUiTraceProto> r2 = r2.mProtoTracer
            java.util.Objects.requireNonNull(r2)
            java.lang.Object r0 = r2.mLock
            monitor-enter(r0)
            boolean r1 = r2.mEnabled     // Catch:{ all -> 0x003d }
            if (r1 != 0) goto L_0x000e
            monitor-exit(r0)     // Catch:{ all -> 0x003d }
            goto L_0x0038
        L_0x000e:
            r1 = 0
            r2.mEnabled = r1     // Catch:{ all -> 0x003d }
            monitor-exit(r0)     // Catch:{ all -> 0x003d }
            java.lang.String r0 = "ProtoTracer.writeToFile"
            android.os.Trace.beginSection(r0)     // Catch:{ IOException -> 0x002d }
            com.android.internal.util.TraceBuffer<P, S, T> r0 = r2.mBuffer     // Catch:{ IOException -> 0x002d }
            java.io.File r1 = r2.mTraceFile     // Catch:{ IOException -> 0x002d }
            com.android.systemui.shared.tracing.FrameProtoTracer$ProtoTraceParams<P, S, T, R> r2 = r2.mParams     // Catch:{ IOException -> 0x002d }
            com.android.systemui.tracing.ProtoTracer r2 = (com.android.systemui.tracing.ProtoTracer) r2     // Catch:{ IOException -> 0x002d }
            java.util.Objects.requireNonNull(r2)     // Catch:{ IOException -> 0x002d }
            com.android.systemui.tracing.nano.SystemUiTraceFileProto r2 = new com.android.systemui.tracing.nano.SystemUiTraceFileProto     // Catch:{ IOException -> 0x002d }
            r2.<init>()     // Catch:{ IOException -> 0x002d }
            r0.writeTraceToFile(r1, r2)     // Catch:{ IOException -> 0x002d }
            goto L_0x0035
        L_0x002b:
            r2 = move-exception
            goto L_0x0039
        L_0x002d:
            r2 = move-exception
            java.lang.String r0 = "FrameProtoTracer"
            java.lang.String r1 = "Unable to write buffer to file"
            android.util.Log.e(r0, r1, r2)     // Catch:{ all -> 0x002b }
        L_0x0035:
            android.os.Trace.endSection()
        L_0x0038:
            return
        L_0x0039:
            android.os.Trace.endSection()
            throw r2
        L_0x003d:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.tracing.ProtoTracer.stop():void");
    }

    public ProtoTracer(Context context, DumpManager dumpManager) {
        this.mContext = context;
        dumpManager.registerDumpable(this);
    }
}
