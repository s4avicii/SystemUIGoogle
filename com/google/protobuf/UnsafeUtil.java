package com.google.protobuf;

import java.util.Objects;
import java.util.logging.Logger;
import sun.misc.Unsafe;

public final class UnsafeUtil {
    public static final long BYTE_ARRAY_BASE_OFFSET = ((long) arrayBaseOffset(byte[].class));
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    public static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    public static final JvmMemoryAccessor MEMORY_ACCESSOR;
    public static final Unsafe UNSAFE;
    public static final Logger logger = Logger.getLogger(UnsafeUtil.class.getName());

    public static abstract class MemoryAccessor {
        public Unsafe unsafe;

        public MemoryAccessor(Unsafe unsafe2) {
            this.unsafe = unsafe2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x009b A[Catch:{ all -> 0x00fa }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0126 A[SYNTHETIC, Splitter:B:47:0x0126] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0266  */
    static {
        /*
            java.lang.Class<java.lang.Object[]> r1 = java.lang.Object[].class
            java.lang.Class<double[]> r2 = double[].class
            java.lang.Class<float[]> r3 = float[].class
            java.lang.Class<long[]> r4 = long[].class
            java.lang.Class<int[]> r5 = int[].class
            java.lang.Class<boolean[]> r6 = boolean[].class
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            java.lang.Class<com.google.protobuf.UnsafeUtil> r0 = com.google.protobuf.UnsafeUtil.class
            java.lang.String r0 = r0.getName()
            java.util.logging.Logger r0 = java.util.logging.Logger.getLogger(r0)
            logger = r0
            com.google.protobuf.UnsafeUtil$1 r0 = new com.google.protobuf.UnsafeUtil$1     // Catch:{ all -> 0x0026 }
            r0.<init>()     // Catch:{ all -> 0x0026 }
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ all -> 0x0026 }
            sun.misc.Unsafe r0 = (sun.misc.Unsafe) r0     // Catch:{ all -> 0x0026 }
            goto L_0x0027
        L_0x0026:
            r0 = 0
        L_0x0027:
            UNSAFE = r0
            if (r0 != 0) goto L_0x002d
            r9 = 0
            goto L_0x0032
        L_0x002d:
            com.google.protobuf.UnsafeUtil$JvmMemoryAccessor r9 = new com.google.protobuf.UnsafeUtil$JvmMemoryAccessor
            r9.<init>(r0)
        L_0x0032:
            MEMORY_ACCESSOR = r9
            java.lang.String r9 = "copyMemory"
            java.lang.String r10 = "putLong"
            java.lang.String r11 = "putInt"
            java.lang.String r12 = "getInt"
            java.lang.String r13 = "putByte"
            java.lang.String r14 = "getByte"
            java.lang.String r15 = "platform method missing - proto runtime falling back to safer methods: "
            java.lang.String r8 = "objectFieldOffset"
            r16 = 0
            r17 = r1
            java.lang.String r1 = "getLong"
            r18 = r2
            java.lang.String r2 = "address"
            r19 = r3
            if (r0 != 0) goto L_0x0057
            r22 = r2
            r21 = r4
            goto L_0x0097
        L_0x0057:
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x0100 }
            r21 = r4
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x00fc }
            java.lang.Class<java.lang.reflect.Field> r3 = java.lang.reflect.Field.class
            r4[r16] = r3     // Catch:{ all -> 0x00fc }
            r0.getMethod(r8, r4)     // Catch:{ all -> 0x00fc }
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x00fc }
            r4[r16] = r7     // Catch:{ all -> 0x00fc }
            java.lang.Class r3 = java.lang.Long.TYPE     // Catch:{ all -> 0x00fc }
            r20 = 1
            r4[r20] = r3     // Catch:{ all -> 0x00fc }
            r0.getMethod(r1, r4)     // Catch:{ all -> 0x00fc }
            java.lang.Class<java.nio.Buffer> r4 = java.nio.Buffer.class
            java.lang.reflect.Field r4 = r4.getDeclaredField(r2)     // Catch:{ all -> 0x007c }
            goto L_0x007d
        L_0x007c:
            r4 = 0
        L_0x007d:
            if (r4 == 0) goto L_0x0092
            r22 = r2
            java.lang.Class r2 = r4.getType()     // Catch:{ all -> 0x00fa }
            r23 = r4
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ all -> 0x00fa }
            if (r2 != r4) goto L_0x0094
            r4 = r23
            goto L_0x0095
        L_0x008e:
            r23 = r5
            goto L_0x0106
        L_0x0092:
            r22 = r2
        L_0x0094:
            r4 = 0
        L_0x0095:
            if (r4 != 0) goto L_0x009b
        L_0x0097:
            r23 = r5
            goto L_0x011c
        L_0x009b:
            r2 = 1
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x00fa }
            r4[r16] = r3     // Catch:{ all -> 0x00fa }
            r0.getMethod(r14, r4)     // Catch:{ all -> 0x00fa }
            r2 = 2
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x00fa }
            r4[r16] = r3     // Catch:{ all -> 0x00fa }
            java.lang.Class r2 = java.lang.Byte.TYPE     // Catch:{ all -> 0x00fa }
            r23 = r5
            r5 = 1
            r4[r5] = r2     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r13, r4)     // Catch:{ all -> 0x00f8 }
            java.lang.Class[] r2 = new java.lang.Class[r5]     // Catch:{ all -> 0x00f8 }
            r2[r16] = r3     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r12, r2)     // Catch:{ all -> 0x00f8 }
            r2 = 2
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x00f8 }
            r4[r16] = r3     // Catch:{ all -> 0x00f8 }
            java.lang.Class r2 = java.lang.Integer.TYPE     // Catch:{ all -> 0x00f8 }
            r5 = 1
            r4[r5] = r2     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r11, r4)     // Catch:{ all -> 0x00f8 }
            java.lang.Class[] r2 = new java.lang.Class[r5]     // Catch:{ all -> 0x00f8 }
            r2[r16] = r3     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r1, r2)     // Catch:{ all -> 0x00f8 }
            r2 = 2
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x00f8 }
            r4[r16] = r3     // Catch:{ all -> 0x00f8 }
            r4[r5] = r3     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r10, r4)     // Catch:{ all -> 0x00f8 }
            r2 = 3
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x00f8 }
            r4[r16] = r3     // Catch:{ all -> 0x00f8 }
            r4[r5] = r3     // Catch:{ all -> 0x00f8 }
            r2 = 2
            r4[r2] = r3     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r9, r4)     // Catch:{ all -> 0x00f8 }
            r4 = 5
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ all -> 0x00f8 }
            r4[r16] = r7     // Catch:{ all -> 0x00f8 }
            r4[r5] = r3     // Catch:{ all -> 0x00f8 }
            r4[r2] = r7     // Catch:{ all -> 0x00f8 }
            r2 = 3
            r4[r2] = r3     // Catch:{ all -> 0x00f8 }
            r2 = 4
            r4[r2] = r3     // Catch:{ all -> 0x00f8 }
            r0.getMethod(r9, r4)     // Catch:{ all -> 0x00f8 }
            r0 = 1
            goto L_0x011e
        L_0x00f8:
            r0 = move-exception
            goto L_0x0106
        L_0x00fa:
            r0 = move-exception
            goto L_0x008e
        L_0x00fc:
            r0 = move-exception
            r22 = r2
            goto L_0x008e
        L_0x0100:
            r0 = move-exception
            r22 = r2
            r21 = r4
            goto L_0x008e
        L_0x0106:
            java.util.logging.Logger r2 = logger
            java.util.logging.Level r3 = java.util.logging.Level.WARNING
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r15)
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            r2.log(r3, r0)
        L_0x011c:
            r0 = r16
        L_0x011e:
            HAS_UNSAFE_BYTEBUFFER_OPERATIONS = r0
            sun.misc.Unsafe r0 = UNSAFE
            if (r0 != 0) goto L_0x0126
            goto L_0x0221
        L_0x0126:
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x020a }
            r2 = 1
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{ all -> 0x020a }
            java.lang.Class<java.lang.reflect.Field> r4 = java.lang.reflect.Field.class
            r3[r16] = r4     // Catch:{ all -> 0x020a }
            r0.getMethod(r8, r3)     // Catch:{ all -> 0x020a }
            java.lang.String r3 = "arrayBaseOffset"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x020a }
            java.lang.Class<java.lang.Class> r5 = java.lang.Class.class
            r4[r16] = r5     // Catch:{ all -> 0x020a }
            r0.getMethod(r3, r4)     // Catch:{ all -> 0x020a }
            java.lang.String r3 = "arrayIndexScale"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x020a }
            java.lang.Class<java.lang.Class> r2 = java.lang.Class.class
            r4[r16] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r3, r4)     // Catch:{ all -> 0x020a }
            r2 = 2
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            java.lang.Class r2 = java.lang.Long.TYPE     // Catch:{ all -> 0x020a }
            r4 = 1
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r12, r3)     // Catch:{ all -> 0x020a }
            r3 = 3
            java.lang.Class[] r5 = new java.lang.Class[r3]     // Catch:{ all -> 0x020a }
            r5[r16] = r7     // Catch:{ all -> 0x020a }
            r5[r4] = r2     // Catch:{ all -> 0x020a }
            java.lang.Class r3 = java.lang.Integer.TYPE     // Catch:{ all -> 0x020a }
            r4 = 2
            r5[r4] = r3     // Catch:{ all -> 0x020a }
            r0.getMethod(r11, r5)     // Catch:{ all -> 0x020a }
            java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r4 = 1
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r3)     // Catch:{ all -> 0x020a }
            r1 = 3
            java.lang.Class[] r3 = new java.lang.Class[r1]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            r1 = 2
            r3[r1] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r10, r3)     // Catch:{ all -> 0x020a }
            java.lang.String r3 = "getObject"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ all -> 0x020a }
            r4[r16] = r7     // Catch:{ all -> 0x020a }
            r1 = 1
            r4[r1] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r3, r4)     // Catch:{ all -> 0x020a }
            java.lang.String r3 = "putObject"
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r5[r16] = r7     // Catch:{ all -> 0x020a }
            r5[r1] = r2     // Catch:{ all -> 0x020a }
            r4 = 2
            r5[r4] = r7     // Catch:{ all -> 0x020a }
            r0.getMethod(r3, r5)     // Catch:{ all -> 0x020a }
            java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r3[r1] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r14, r3)     // Catch:{ all -> 0x020a }
            r3 = 3
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x020a }
            r4[r16] = r7     // Catch:{ all -> 0x020a }
            r4[r1] = r2     // Catch:{ all -> 0x020a }
            java.lang.Class r1 = java.lang.Byte.TYPE     // Catch:{ all -> 0x020a }
            r3 = 2
            r4[r3] = r1     // Catch:{ all -> 0x020a }
            r0.getMethod(r13, r4)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "getBoolean"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x020a }
            r4[r16] = r7     // Catch:{ all -> 0x020a }
            r3 = 1
            r4[r3] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r4)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "putBoolean"
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r5[r16] = r7     // Catch:{ all -> 0x020a }
            r5[r3] = r2     // Catch:{ all -> 0x020a }
            java.lang.Class r3 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x020a }
            r4 = 2
            r5[r4] = r3     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r5)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "getFloat"
            java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r4 = 1
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r3)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "putFloat"
            r3 = 3
            java.lang.Class[] r5 = new java.lang.Class[r3]     // Catch:{ all -> 0x020a }
            r5[r16] = r7     // Catch:{ all -> 0x020a }
            r5[r4] = r2     // Catch:{ all -> 0x020a }
            java.lang.Class r3 = java.lang.Float.TYPE     // Catch:{ all -> 0x020a }
            r4 = 2
            r5[r4] = r3     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r5)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "getDouble"
            java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r4 = 1
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r3)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = "putDouble"
            r3 = 3
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ all -> 0x020a }
            r3[r16] = r7     // Catch:{ all -> 0x020a }
            r3[r4] = r2     // Catch:{ all -> 0x020a }
            java.lang.Class r2 = java.lang.Double.TYPE     // Catch:{ all -> 0x020a }
            r5 = 2
            r3[r5] = r2     // Catch:{ all -> 0x020a }
            r0.getMethod(r1, r3)     // Catch:{ all -> 0x020a }
            r16 = r4
            goto L_0x0221
        L_0x020a:
            r0 = move-exception
            java.util.logging.Logger r1 = logger
            java.util.logging.Level r2 = java.util.logging.Level.WARNING
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r15)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r1.log(r2, r0)
        L_0x0221:
            HAS_UNSAFE_ARRAY_OPERATIONS = r16
            java.lang.Class<byte[]> r0 = byte[].class
            int r0 = arrayBaseOffset(r0)
            long r0 = (long) r0
            BYTE_ARRAY_BASE_OFFSET = r0
            arrayBaseOffset(r6)
            arrayIndexScale(r6)
            arrayBaseOffset(r23)
            arrayIndexScale(r23)
            arrayBaseOffset(r21)
            arrayIndexScale(r21)
            arrayBaseOffset(r19)
            arrayIndexScale(r19)
            arrayBaseOffset(r18)
            arrayIndexScale(r18)
            arrayBaseOffset(r17)
            arrayIndexScale(r17)
            java.lang.Class<java.nio.Buffer> r0 = java.nio.Buffer.class
            r1 = r22
            java.lang.reflect.Field r0 = r0.getDeclaredField(r1)     // Catch:{ all -> 0x0259 }
            goto L_0x025a
        L_0x0259:
            r0 = 0
        L_0x025a:
            if (r0 == 0) goto L_0x0266
            java.lang.Class r1 = r0.getType()
            java.lang.Class r2 = java.lang.Long.TYPE
            if (r1 != r2) goto L_0x0266
            r8 = r0
            goto L_0x0267
        L_0x0266:
            r8 = 0
        L_0x0267:
            if (r8 == 0) goto L_0x0273
            com.google.protobuf.UnsafeUtil$JvmMemoryAccessor r0 = MEMORY_ACCESSOR
            if (r0 != 0) goto L_0x026e
            goto L_0x0273
        L_0x026e:
            sun.misc.Unsafe r0 = r0.unsafe
            r0.objectFieldOffset(r8)
        L_0x0273:
            java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r0 = java.nio.ByteOrder.BIG_ENDIAN
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.UnsafeUtil.<clinit>():void");
    }

    public static <T> T allocateInstance(Class<T> cls) {
        try {
            return UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int arrayBaseOffset(Class<?> cls) {
        if (!HAS_UNSAFE_ARRAY_OPERATIONS) {
            return -1;
        }
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.arrayBaseOffset(cls);
    }

    public static int arrayIndexScale(Class<?> cls) {
        if (!HAS_UNSAFE_ARRAY_OPERATIONS) {
            return -1;
        }
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.arrayIndexScale(cls);
    }

    public static boolean getBoolean(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getBoolean(obj, j);
    }

    public static byte getByte(byte[] bArr, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        long j2 = BYTE_ARRAY_BASE_OFFSET + j;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getByte(bArr, j2);
    }

    public static double getDouble(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getDouble(obj, j);
    }

    public static float getFloat(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getFloat(obj, j);
    }

    public static int getInt(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getInt(obj, j);
    }

    public static long getLong(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getLong(obj, j);
    }

    public static Object getObject(Object obj, long j) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        return jvmMemoryAccessor.unsafe.getObject(obj, j);
    }

    public static void putInt(Object obj, long j, int i) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        jvmMemoryAccessor.unsafe.putInt(obj, j, i);
    }

    public static void putLong(Object obj, long j, long j2) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        jvmMemoryAccessor.unsafe.putLong(obj, j, j2);
    }

    public static void putObject(Object obj, long j, Object obj2) {
        JvmMemoryAccessor jvmMemoryAccessor = MEMORY_ACCESSOR;
        Objects.requireNonNull(jvmMemoryAccessor);
        jvmMemoryAccessor.unsafe.putObject(obj, j, obj2);
    }

    public static final class JvmMemoryAccessor extends MemoryAccessor {
        public JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }
    }
}
