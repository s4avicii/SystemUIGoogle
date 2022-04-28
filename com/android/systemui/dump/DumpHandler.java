package com.android.systemui.dump;

import android.content.Context;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CoreStartable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DumpHandler.kt */
public final class DumpHandler {
    public final Context context;
    public final DumpManager dumpManager;
    public final LogBufferEulogizer logBufferEulogizer;
    public final Map<Class<?>, Provider<CoreStartable>> startables;

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006a, code lost:
        r1.tailLength = ((java.lang.Number) readArgument(r6, r3, com.android.systemui.dump.DumpHandler$parseArgs$2.INSTANCE)).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0081, code lost:
        r1.listOnly = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008c, code lost:
        r1.command = "help";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009c, code lost:
        throw new com.android.systemui.dump.ArgParseException(kotlin.jvm.internal.Intrinsics.stringPlus("Unknown flag: ", r3));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.android.systemui.dump.ParsedArgs parseArgs(java.lang.String[] r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            kotlin.collections.ArrayAsCollection r1 = new kotlin.collections.ArrayAsCollection
            r2 = 0
            r1.<init>(r6, r2)
            r0.<init>(r1)
            com.android.systemui.dump.ParsedArgs r1 = new com.android.systemui.dump.ParsedArgs
            r1.<init>(r6, r0)
            java.util.Iterator r6 = r0.iterator()
        L_0x0014:
            boolean r3 = r6.hasNext()
            r4 = 1
            if (r3 == 0) goto L_0x009d
            java.lang.Object r3 = r6.next()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r5 = "-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x0014
            r6.remove()
            int r5 = r3.hashCode()
            switch(r5) {
                case 1499: goto L_0x0084;
                case 1503: goto L_0x0079;
                case 1511: goto L_0x0062;
                case 1056887741: goto L_0x004f;
                case 1333069025: goto L_0x0046;
                case 1333192254: goto L_0x003d;
                case 1333422576: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x0091
        L_0x0034:
            java.lang.String r4 = "--tail"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0091
            goto L_0x006a
        L_0x003d:
            java.lang.String r5 = "--list"
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x0091
            goto L_0x0081
        L_0x0046:
            java.lang.String r4 = "--help"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0091
            goto L_0x008c
        L_0x004f:
            java.lang.String r4 = "--dump-priority"
            boolean r5 = r3.equals(r4)
            if (r5 == 0) goto L_0x0091
            com.android.systemui.dump.DumpHandler$parseArgs$1 r3 = com.android.systemui.dump.DumpHandler$parseArgs$1.INSTANCE
            java.lang.Object r3 = readArgument(r6, r4, r3)
            java.lang.String r3 = (java.lang.String) r3
            r1.dumpPriority = r3
            goto L_0x0014
        L_0x0062:
            java.lang.String r4 = "-t"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0091
        L_0x006a:
            com.android.systemui.dump.DumpHandler$parseArgs$2 r4 = com.android.systemui.dump.DumpHandler$parseArgs$2.INSTANCE
            java.lang.Object r3 = readArgument(r6, r3, r4)
            java.lang.Number r3 = (java.lang.Number) r3
            int r3 = r3.intValue()
            r1.tailLength = r3
            goto L_0x0014
        L_0x0079:
            java.lang.String r5 = "-l"
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x0091
        L_0x0081:
            r1.listOnly = r4
            goto L_0x0014
        L_0x0084:
            java.lang.String r4 = "-h"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0091
        L_0x008c:
            java.lang.String r3 = "help"
            r1.command = r3
            goto L_0x0014
        L_0x0091:
            com.android.systemui.dump.ArgParseException r6 = new com.android.systemui.dump.ArgParseException
            java.lang.String r0 = "Unknown flag: "
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r3)
            r6.<init>(r0)
            throw r6
        L_0x009d:
            java.lang.String r6 = r1.command
            if (r6 != 0) goto L_0x00bc
            boolean r6 = r0.isEmpty()
            r6 = r6 ^ r4
            if (r6 == 0) goto L_0x00bc
            java.lang.String[] r6 = com.android.systemui.dump.DumpHandlerKt.COMMANDS
            java.lang.Object r3 = r0.get(r2)
            boolean r6 = kotlin.collections.ArraysKt___ArraysKt.contains((T[]) r6, r3)
            if (r6 == 0) goto L_0x00bc
            java.lang.Object r6 = r0.remove(r2)
            java.lang.String r6 = (java.lang.String) r6
            r1.command = r6
        L_0x00bc:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.DumpHandler.parseArgs(java.lang.String[]):com.android.systemui.dump.ParsedArgs");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dump(java.io.FileDescriptor r11, java.io.PrintWriter r12, java.lang.String[] r13) {
        /*
            r10 = this;
            java.lang.String r0 = "DumpManager#dump()"
            android.os.Trace.beginSection(r0)
            long r0 = android.os.SystemClock.uptimeMillis()
            com.android.systemui.dump.ParsedArgs r13 = parseArgs(r13)     // Catch:{ ArgParseException -> 0x01d7 }
            java.lang.String r2 = r13.dumpPriority
            java.lang.String r3 = "CRITICAL"
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r3 == 0) goto L_0x0023
            com.android.systemui.dump.DumpManager r2 = r10.dumpManager
            java.lang.String[] r13 = r13.rawArgs
            r2.dumpDumpables(r11, r12, r13)
            r10.dumpConfig(r12)
            goto L_0x01b2
        L_0x0023:
            java.lang.String r3 = "NORMAL"
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 == 0) goto L_0x0030
            r10.dumpNormal(r12, r13)
            goto L_0x01b2
        L_0x0030:
            java.lang.String r2 = r13.command
            if (r2 == 0) goto L_0x011c
            int r3 = r2.hashCode()
            switch(r3) {
                case -1354792126: goto L_0x010e;
                case -1353714459: goto L_0x00f1;
                case -1045369428: goto L_0x00e3;
                case 3198785: goto L_0x0071;
                case 227996723: goto L_0x0053;
                case 842828580: goto L_0x003d;
                default: goto L_0x003b;
            }
        L_0x003b:
            goto L_0x011c
        L_0x003d:
            java.lang.String r3 = "bugreport-critical"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0047
            goto L_0x011c
        L_0x0047:
            com.android.systemui.dump.DumpManager r2 = r10.dumpManager
            java.lang.String[] r13 = r13.rawArgs
            r2.dumpDumpables(r11, r12, r13)
            r10.dumpConfig(r12)
            goto L_0x01b2
        L_0x0053:
            java.lang.String r3 = "buffers"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x005d
            goto L_0x011c
        L_0x005d:
            boolean r11 = r13.listOnly
            if (r11 == 0) goto L_0x0068
            com.android.systemui.dump.DumpManager r10 = r10.dumpManager
            r10.listBuffers(r12)
            goto L_0x01b2
        L_0x0068:
            com.android.systemui.dump.DumpManager r10 = r10.dumpManager
            int r11 = r13.tailLength
            r10.dumpBuffers(r12, r11)
            goto L_0x01b2
        L_0x0071:
            java.lang.String r3 = "help"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x007b
            goto L_0x011c
        L_0x007b:
            java.lang.String r10 = "Let <invocation> be:"
            r12.println(r10)
            java.lang.String r10 = "$ adb shell dumpsys activity service com.android.systemui/.SystemUIService"
            r12.println(r10)
            r12.println()
            java.lang.String r10 = "Most common usage:"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> <targets>"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> NotifLog"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> StatusBar FalsingManager BootCompleteCacheImpl"
            r12.println(r10)
            java.lang.String r10 = "etc."
            r12.println(r10)
            r12.println()
            java.lang.String r10 = "Special commands:"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> dumpables"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> buffers"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> bugreport-critical"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> bugreport-normal"
            r12.println(r10)
            r12.println()
            java.lang.String r10 = "Targets can be listed:"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> --list"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> dumpables --list"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> buffers --list"
            r12.println(r10)
            r12.println()
            java.lang.String r10 = "Show only the most recent N lines of buffers"
            r12.println(r10)
            java.lang.String r10 = "$ <invocation> NotifLog --tail 30"
            r12.println(r10)
            goto L_0x01b2
        L_0x00e3:
            java.lang.String r3 = "bugreport-normal"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x00ec
            goto L_0x011c
        L_0x00ec:
            r10.dumpNormal(r12, r13)
            goto L_0x01b2
        L_0x00f1:
            java.lang.String r3 = "dumpables"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x00fa
            goto L_0x011c
        L_0x00fa:
            boolean r2 = r13.listOnly
            if (r2 == 0) goto L_0x0105
            com.android.systemui.dump.DumpManager r10 = r10.dumpManager
            r10.listDumpables(r12)
            goto L_0x01b2
        L_0x0105:
            com.android.systemui.dump.DumpManager r10 = r10.dumpManager
            java.lang.String[] r13 = r13.rawArgs
            r10.dumpDumpables(r11, r12, r13)
            goto L_0x01b2
        L_0x010e:
            java.lang.String r3 = "config"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0117
            goto L_0x011c
        L_0x0117:
            r10.dumpConfig(r12)
            goto L_0x01b2
        L_0x011c:
            java.util.List<java.lang.String> r2 = r13.nonFlagArgs
            boolean r3 = r2.isEmpty()
            r3 = r3 ^ 1
            if (r3 == 0) goto L_0x0191
            java.util.Iterator r2 = r2.iterator()
        L_0x012a:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x01b2
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            com.android.systemui.dump.DumpManager r4 = r10.dumpManager
            java.lang.String[] r5 = r13.rawArgs
            int r6 = r13.tailLength
            java.util.Objects.requireNonNull(r4)
            monitor-enter(r4)
            android.util.ArrayMap r7 = r4.dumpables     // Catch:{ all -> 0x018e }
            java.util.Collection r7 = r7.values()     // Catch:{ all -> 0x018e }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x018e }
        L_0x014a:
            boolean r8 = r7.hasNext()     // Catch:{ all -> 0x018e }
            if (r8 == 0) goto L_0x0166
            java.lang.Object r8 = r7.next()     // Catch:{ all -> 0x018e }
            com.android.systemui.dump.RegisteredDumpable r8 = (com.android.systemui.dump.RegisteredDumpable) r8     // Catch:{ all -> 0x018e }
            java.util.Objects.requireNonNull(r8)     // Catch:{ all -> 0x018e }
            java.lang.String r9 = r8.name     // Catch:{ all -> 0x018e }
            boolean r9 = r9.endsWith(r3)     // Catch:{ all -> 0x018e }
            if (r9 == 0) goto L_0x014a
            com.android.systemui.dump.DumpManager.dumpDumpable(r8, r11, r12, r5)     // Catch:{ all -> 0x018e }
            monitor-exit(r4)
            goto L_0x012a
        L_0x0166:
            android.util.ArrayMap r5 = r4.buffers     // Catch:{ all -> 0x018e }
            java.util.Collection r5 = r5.values()     // Catch:{ all -> 0x018e }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x018e }
        L_0x0170:
            boolean r7 = r5.hasNext()     // Catch:{ all -> 0x018e }
            if (r7 == 0) goto L_0x018c
            java.lang.Object r7 = r5.next()     // Catch:{ all -> 0x018e }
            com.android.systemui.dump.RegisteredDumpable r7 = (com.android.systemui.dump.RegisteredDumpable) r7     // Catch:{ all -> 0x018e }
            java.util.Objects.requireNonNull(r7)     // Catch:{ all -> 0x018e }
            java.lang.String r8 = r7.name     // Catch:{ all -> 0x018e }
            boolean r8 = r8.endsWith(r3)     // Catch:{ all -> 0x018e }
            if (r8 == 0) goto L_0x0170
            com.android.systemui.dump.DumpManager.dumpBuffer(r7, r12, r6)     // Catch:{ all -> 0x018e }
            monitor-exit(r4)
            goto L_0x012a
        L_0x018c:
            monitor-exit(r4)
            goto L_0x012a
        L_0x018e:
            r10 = move-exception
            monitor-exit(r4)
            throw r10
        L_0x0191:
            boolean r11 = r13.listOnly
            if (r11 == 0) goto L_0x01ad
            java.lang.String r11 = "Dumpables:"
            r12.println(r11)
            com.android.systemui.dump.DumpManager r11 = r10.dumpManager
            r11.listDumpables(r12)
            r12.println()
            java.lang.String r11 = "Buffers:"
            r12.println(r11)
            com.android.systemui.dump.DumpManager r10 = r10.dumpManager
            r10.listBuffers(r12)
            goto L_0x01b2
        L_0x01ad:
            java.lang.String r10 = "Nothing to dump :("
            r12.println(r10)
        L_0x01b2:
            r12.println()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Dump took "
            r10.append(r11)
            long r2 = android.os.SystemClock.uptimeMillis()
            long r2 = r2 - r0
            r10.append(r2)
            java.lang.String r11 = "ms"
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r12.println(r10)
            android.os.Trace.endSection()
            return
        L_0x01d7:
            r10 = move-exception
            java.lang.String r10 = r10.getMessage()
            r12.println(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.DumpHandler.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void dumpConfig(PrintWriter printWriter) {
        printWriter.println("SystemUiServiceComponents configuration:");
        printWriter.print("vendor component: ");
        printWriter.println(this.context.getResources().getString(C1777R.string.config_systemUIVendorServiceComponent));
        Set<Class<?>> keySet = this.startables.keySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(keySet, 10));
        for (Class simpleName : keySet) {
            arrayList.add(simpleName.getSimpleName());
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList2.add(this.context.getResources().getString(C1777R.string.config_systemUIVendorServiceComponent));
        Object[] array = arrayList2.toArray(new String[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        dumpServiceList(printWriter, "global", (String[]) array);
        dumpServiceList(printWriter, "per-user", this.context.getResources().getStringArray(C1777R.array.config_systemUIServiceComponentsPerUser));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0061, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        androidx.cardview.R$attr.closeFinally(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0065, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dumpNormal(java.io.PrintWriter r5, com.android.systemui.dump.ParsedArgs r6) {
        /*
            r4 = this;
            com.android.systemui.dump.DumpManager r0 = r4.dumpManager
            int r6 = r6.tailLength
            r0.dumpBuffers(r5, r6)
            com.android.systemui.dump.LogBufferEulogizer r4 = r4.logBufferEulogizer
            java.util.Objects.requireNonNull(r4)
            java.lang.String r6 = "BufferEulogizer"
            java.nio.file.Path r0 = r4.logPath     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            long r0 = r4.getMillisSinceLastWrite(r0)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            long r2 = r4.maxLogAgeToDump     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x003c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            r4.<init>()     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.lang.String r5 = "Not eulogizing buffers; they are "
            r4.append(r5)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.HOURS     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            long r0 = r5.convert(r0, r2)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            r4.append(r0)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.lang.String r5 = " hours old"
            r4.append(r5)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            android.util.Log.i(r6, r4)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            goto L_0x006c
        L_0x003c:
            com.android.systemui.util.io.Files r0 = r4.files     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.nio.file.Path r4 = r4.logPath     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.util.Objects.requireNonNull(r0)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            java.util.stream.Stream r4 = java.nio.file.Files.lines(r4)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            r0 = 0
            r5.println()     // Catch:{ all -> 0x005f }
            r5.println()     // Catch:{ all -> 0x005f }
            java.lang.String r1 = "=============== BUFFERS FROM MOST RECENT CRASH ==============="
            r5.println(r1)     // Catch:{ all -> 0x005f }
            com.android.systemui.dump.LogBufferEulogizer$readEulogyIfPresent$1$1 r1 = new com.android.systemui.dump.LogBufferEulogizer$readEulogyIfPresent$1$1     // Catch:{ all -> 0x005f }
            r1.<init>(r5)     // Catch:{ all -> 0x005f }
            r4.forEach(r1)     // Catch:{ all -> 0x005f }
            androidx.cardview.R$attr.closeFinally(r4, r0)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            goto L_0x006c
        L_0x005f:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r0 = move-exception
            androidx.cardview.R$attr.closeFinally(r4, r5)     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
            throw r0     // Catch:{ IOException -> 0x006c, UncheckedIOException -> 0x0066 }
        L_0x0066:
            r4 = move-exception
            java.lang.String r5 = "UncheckedIOException while dumping the core"
            android.util.Log.e(r6, r5, r4)
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.DumpHandler.dumpNormal(java.io.PrintWriter, com.android.systemui.dump.ParsedArgs):void");
    }

    public DumpHandler(Context context2, DumpManager dumpManager2, LogBufferEulogizer logBufferEulogizer2, Map<Class<?>, Provider<CoreStartable>> map) {
        this.context = context2;
        this.dumpManager = dumpManager2;
        this.logBufferEulogizer = logBufferEulogizer2;
        this.startables = map;
    }

    public static void dumpServiceList(PrintWriter printWriter, String str, String[] strArr) {
        printWriter.print(str);
        printWriter.print(": ");
        if (strArr == null) {
            printWriter.println("N/A");
            return;
        }
        printWriter.print(strArr.length);
        printWriter.println(" services");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            printWriter.print("  ");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(strArr[i]);
        }
    }

    public static Object readArgument(Iterator it, String str, Function1 function1) {
        if (it.hasNext()) {
            String str2 = (String) it.next();
            try {
                Object invoke = function1.invoke(str2);
                it.remove();
                return invoke;
            } catch (Exception unused) {
                throw new ArgParseException("Invalid argument '" + str2 + "' for flag " + str);
            }
        } else {
            throw new ArgParseException(Intrinsics.stringPlus("Missing argument for ", str));
        }
    }
}
