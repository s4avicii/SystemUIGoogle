package com.google.common.primitives;

import java.util.Comparator;
import java.util.Objects;
import sun.misc.Unsafe;

public final class UnsignedBytes {

    public static class LexicographicalComparatorHolder {

        public enum PureJavaComparator implements Comparator<byte[]> {
            ;

            public final String toString() {
                return "UnsignedBytes.lexicographicalComparator() (pure Java version)";
            }

            /* access modifiers changed from: public */
            PureJavaComparator() {
            }

            public final int compare(Object obj, Object obj2) {
                byte[] bArr = (byte[]) obj;
                byte[] bArr2 = (byte[]) obj2;
                int min = Math.min(bArr.length, bArr2.length);
                for (int i = 0; i < min; i++) {
                    int i2 = (bArr[i] & 255) - (bArr2[i] & 255);
                    if (i2 != 0) {
                        return i2;
                    }
                }
                return bArr.length - bArr2.length;
            }
        }

        public enum UnsafeComparator implements Comparator<byte[]> {
            ;
            
            public static final boolean BIG_ENDIAN = false;
            public static final int BYTE_ARRAY_BASE_OFFSET = 0;
            public static final Unsafe theUnsafe = null;

            public final String toString() {
                return "UnsignedBytes.lexicographicalComparator() (sun.misc.Unsafe version)";
            }

            /* access modifiers changed from: public */
            /* JADX WARNING: Can't wrap try/catch for region: R(2:3|4) */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0053, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x005f, code lost:
                throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
             */
            /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
                r1 = (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.primitives.UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator.C24801());
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0020 */
            static {
                /*
                    java.lang.Class<byte[]> r0 = byte[].class
                    com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator r1 = new com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator
                    r1.<init>()
                    r2 = 1
                    com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator[] r3 = new com.google.common.primitives.UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator[r2]
                    r4 = 0
                    r3[r4] = r1
                    $VALUES = r3
                    java.nio.ByteOrder r1 = java.nio.ByteOrder.nativeOrder()
                    java.nio.ByteOrder r3 = java.nio.ByteOrder.BIG_ENDIAN
                    boolean r1 = r1.equals(r3)
                    BIG_ENDIAN = r1
                    sun.misc.Unsafe r1 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0020 }
                    goto L_0x002b
                L_0x0020:
                    com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator$1 r1 = new com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator$1     // Catch:{ PrivilegedActionException -> 0x0053 }
                    r1.<init>()     // Catch:{ PrivilegedActionException -> 0x0053 }
                    java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)     // Catch:{ PrivilegedActionException -> 0x0053 }
                    sun.misc.Unsafe r1 = (sun.misc.Unsafe) r1     // Catch:{ PrivilegedActionException -> 0x0053 }
                L_0x002b:
                    theUnsafe = r1
                    int r3 = r1.arrayBaseOffset(r0)
                    BYTE_ARRAY_BASE_OFFSET = r3
                    java.lang.String r4 = "sun.arch.data.model"
                    java.lang.String r4 = java.lang.System.getProperty(r4)
                    java.lang.String r5 = "64"
                    boolean r4 = r5.equals(r4)
                    if (r4 == 0) goto L_0x004d
                    int r3 = r3 % 8
                    if (r3 != 0) goto L_0x004d
                    int r0 = r1.arrayIndexScale(r0)
                    if (r0 != r2) goto L_0x004d
                    return
                L_0x004d:
                    java.lang.Error r0 = new java.lang.Error
                    r0.<init>()
                    throw r0
                L_0x0053:
                    r0 = move-exception
                    java.lang.RuntimeException r1 = new java.lang.RuntimeException
                    java.lang.Throwable r0 = r0.getCause()
                    java.lang.String r2 = "Could not initialize intrinsics"
                    r1.<init>(r2, r0)
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator.<clinit>():void");
            }

            /* access modifiers changed from: public */
            UnsafeComparator() {
            }

            public final int compare(Object obj, Object obj2) {
                byte[] bArr = (byte[]) obj;
                byte[] bArr2 = (byte[]) obj2;
                int min = Math.min(bArr.length, bArr2.length);
                int i = min & -8;
                int i2 = 0;
                while (i2 < i) {
                    Unsafe unsafe = theUnsafe;
                    long j = ((long) BYTE_ARRAY_BASE_OFFSET) + ((long) i2);
                    long j2 = unsafe.getLong(bArr, j);
                    long j3 = unsafe.getLong(bArr2, j);
                    if (j2 == j3) {
                        i2 += 8;
                    } else if (BIG_ENDIAN) {
                        int i3 = ((j2 ^ Long.MIN_VALUE) > (Long.MIN_VALUE ^ j3) ? 1 : ((j2 ^ Long.MIN_VALUE) == (Long.MIN_VALUE ^ j3) ? 0 : -1));
                        if (i3 < 0) {
                            return -1;
                        }
                        if (i3 > 0) {
                            return 1;
                        }
                        return 0;
                    } else {
                        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j2 ^ j3) & -8;
                        return ((int) ((j2 >>> numberOfTrailingZeros) & 255)) - ((int) (255 & (j3 >>> numberOfTrailingZeros)));
                    }
                }
                while (i2 < min) {
                    int i4 = (bArr[i2] & 255) - (bArr2[i2] & 255);
                    if (i4 != 0) {
                        return i4;
                    }
                    i2++;
                }
                return bArr.length - bArr2.length;
            }
        }

        static {
            try {
                Object[] enumConstants = Class.forName(LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator").getEnumConstants();
                Objects.requireNonNull(enumConstants);
                Comparator comparator = (Comparator) enumConstants[0];
            } catch (Throwable unused) {
                UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
        }
    }

    public static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }
}
