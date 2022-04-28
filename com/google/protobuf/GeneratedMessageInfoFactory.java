package com.google.protobuf;

public final class GeneratedMessageInfoFactory implements MessageInfoFactory {
    public static final GeneratedMessageInfoFactory instance = new GeneratedMessageInfoFactory();

    public final boolean isSupported(Class<?> cls) {
        return GeneratedMessageLite.class.isAssignableFrom(cls);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.protobuf.MessageInfo messageInfoFor(java.lang.Class<?> r3) {
        /*
            r2 = this;
            java.lang.Class<com.google.protobuf.GeneratedMessageLite> r2 = com.google.protobuf.GeneratedMessageLite.class
            boolean r0 = r2.isAssignableFrom(r3)
            if (r0 == 0) goto L_0x0031
            java.lang.Class r2 = r3.asSubclass(r2)     // Catch:{ Exception -> 0x0019 }
            com.google.protobuf.GeneratedMessageLite r2 = com.google.protobuf.GeneratedMessageLite.getDefaultInstance(r2)     // Catch:{ Exception -> 0x0019 }
            com.google.protobuf.GeneratedMessageLite$MethodToInvoke r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO     // Catch:{ Exception -> 0x0019 }
            java.lang.Object r2 = r2.dynamicMethod(r0)     // Catch:{ Exception -> 0x0019 }
            com.google.protobuf.MessageInfo r2 = (com.google.protobuf.MessageInfo) r2     // Catch:{ Exception -> 0x0019 }
            return r2
        L_0x0019:
            r2 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Unable to get message info for "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r3 = r3.getName()
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            r0.<init>(r3, r2)
            throw r0
        L_0x0031:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Unsupported message type: "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r3 = r3.getName()
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageInfoFactory.messageInfoFor(java.lang.Class):com.google.protobuf.MessageInfo");
    }
}
