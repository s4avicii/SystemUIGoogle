package kotlin.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;

/* compiled from: _Arrays.kt */
public class ArraysKt___ArraysKt extends ArraysKt__ArraysKt {
    public static final <T> Sequence<T> asSequence(T[] tArr) {
        boolean z;
        if (tArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return EmptySequence.INSTANCE;
        }
        return new ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1(tArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> boolean contains(T[] r5, T r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0010
            int r6 = r5.length
            r1 = r0
        L_0x0005:
            if (r1 >= r6) goto L_0x0022
            int r2 = r1 + 1
            r3 = r5[r1]
            if (r3 != 0) goto L_0x000e
            goto L_0x0023
        L_0x000e:
            r1 = r2
            goto L_0x0005
        L_0x0010:
            int r1 = r5.length
            r2 = r0
        L_0x0012:
            if (r2 >= r1) goto L_0x0022
            int r3 = r2 + 1
            r4 = r5[r2]
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r4)
            if (r4 == 0) goto L_0x0020
            r1 = r2
            goto L_0x0023
        L_0x0020:
            r2 = r3
            goto L_0x0012
        L_0x0022:
            r1 = -1
        L_0x0023:
            if (r1 < 0) goto L_0x0026
            r0 = 1
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.ArraysKt___ArraysKt.contains(java.lang.Object[], java.lang.Object):boolean");
    }

    public static final <T extends Comparable<? super T>> T maxOrNull(T[] tArr) {
        boolean z;
        int i = 1;
        if (tArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return null;
        }
        T t = tArr[0];
        int length = tArr.length - 1;
        if (1 <= length) {
            while (true) {
                int i2 = i + 1;
                T t2 = tArr[i];
                if (t.compareTo(t2) < 0) {
                    t = t2;
                }
                if (i == length) {
                    break;
                }
                i = i2;
            }
        }
        return t;
    }

    public static final <T> Set<T> toSet(T[] tArr) {
        int length = tArr.length;
        if (length == 0) {
            return EmptySet.INSTANCE;
        }
        int i = 0;
        if (length == 1) {
            return Collections.singleton(tArr[0]);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(tArr.length));
        int length2 = tArr.length;
        while (i < length2) {
            T t = tArr[i];
            i++;
            linkedHashSet.add(t);
        }
        return linkedHashSet;
    }

    public static Object[] copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        System.arraycopy(objArr, i2, objArr2, i, i3 - i2);
        return objArr2;
    }

    public static final ArrayList filterNotNull(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            i++;
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final boolean contains(int[] iArr, int i) {
        int length = iArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            }
            int i3 = i2 + 1;
            if (i == iArr[i2]) {
                break;
            }
            i2 = i3;
        }
        if (i2 >= 0) {
            return true;
        }
        return false;
    }
}
