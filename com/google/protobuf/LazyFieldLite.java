package com.google.protobuf;

public class LazyFieldLite {
    public volatile ByteString memoizedBytes;
    public volatile MessageLite value;

    /* JADX WARNING: Can't wrap try/catch for region: R(5:7|8|9|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0013 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.protobuf.MessageLite getValue(com.google.protobuf.MessageLite r2) {
        /*
            r1 = this;
            com.google.protobuf.MessageLite r0 = r1.value
            if (r0 == 0) goto L_0x0005
            goto L_0x001a
        L_0x0005:
            monitor-enter(r1)
            com.google.protobuf.MessageLite r0 = r1.value     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x000c
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            goto L_0x001a
        L_0x000c:
            r1.value = r2     // Catch:{ InvalidProtocolBufferException -> 0x0013 }
            com.google.protobuf.ByteString r0 = com.google.protobuf.ByteString.EMPTY     // Catch:{ InvalidProtocolBufferException -> 0x0013 }
            r1.memoizedBytes = r0     // Catch:{ InvalidProtocolBufferException -> 0x0013 }
            goto L_0x0019
        L_0x0013:
            r1.value = r2     // Catch:{ all -> 0x001d }
            com.google.protobuf.ByteString r2 = com.google.protobuf.ByteString.EMPTY     // Catch:{ all -> 0x001d }
            r1.memoizedBytes = r2     // Catch:{ all -> 0x001d }
        L_0x0019:
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
        L_0x001a:
            com.google.protobuf.MessageLite r1 = r1.value
            return r1
        L_0x001d:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.LazyFieldLite.getValue(com.google.protobuf.MessageLite):com.google.protobuf.MessageLite");
    }

    static {
        ExtensionRegistryLite.getEmptyRegistry();
    }
}
