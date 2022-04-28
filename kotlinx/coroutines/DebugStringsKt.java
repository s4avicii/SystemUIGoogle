package kotlinx.coroutines;

/* compiled from: DebugStrings.kt */
public final class DebugStringsKt {
    /* JADX WARNING: type inference failed for: r2v3, types: [kotlin.Result$Failure] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String toDebugString(kotlin.coroutines.Continuation<?> r3) {
        /*
            boolean r0 = r3 instanceof kotlinx.coroutines.internal.DispatchedContinuation
            if (r0 == 0) goto L_0x0009
            java.lang.String r3 = r3.toString()
            goto L_0x0051
        L_0x0009:
            r0 = 64
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0022 }
            r1.<init>()     // Catch:{ all -> 0x0022 }
            r1.append(r3)     // Catch:{ all -> 0x0022 }
            r1.append(r0)     // Catch:{ all -> 0x0022 }
            java.lang.String r2 = getHexAddress(r3)     // Catch:{ all -> 0x0022 }
            r1.append(r2)     // Catch:{ all -> 0x0022 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0022 }
            goto L_0x0029
        L_0x0022:
            r1 = move-exception
            kotlin.Result$Failure r2 = new kotlin.Result$Failure
            r2.<init>(r1)
            r1 = r2
        L_0x0029:
            java.lang.Throwable r2 = kotlin.Result.m320exceptionOrNullimpl(r1)
            if (r2 != 0) goto L_0x0030
            goto L_0x004e
        L_0x0030:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.Class r2 = r3.getClass()
            java.lang.String r2 = r2.getName()
            r1.append(r2)
            r1.append(r0)
            java.lang.String r3 = getHexAddress(r3)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
        L_0x004e:
            r3 = r1
            java.lang.String r3 = (java.lang.String) r3
        L_0x0051:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DebugStringsKt.toDebugString(kotlin.coroutines.Continuation):java.lang.String");
    }

    public static final String getClassSimpleName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static final String getHexAddress(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
}
