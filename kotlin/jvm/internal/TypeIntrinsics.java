package kotlin.jvm.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

public final class TypeIntrinsics {
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00b9, code lost:
        if (r0 == r4) goto L_0x00bd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object beforeCheckcastToFunctionOfArity(java.lang.Object r3, int r4) {
        /*
            if (r3 == 0) goto L_0x00d6
            boolean r0 = r3 instanceof kotlin.Function
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x00bc
            boolean r0 = r3 instanceof kotlin.jvm.internal.FunctionBase
            if (r0 == 0) goto L_0x0015
            r0 = r3
            kotlin.jvm.internal.FunctionBase r0 = (kotlin.jvm.internal.FunctionBase) r0
            int r0 = r0.getArity()
            goto L_0x00b9
        L_0x0015:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function0
            if (r0 == 0) goto L_0x001c
            r0 = r2
            goto L_0x00b9
        L_0x001c:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function1
            if (r0 == 0) goto L_0x0023
            r0 = r1
            goto L_0x00b9
        L_0x0023:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function2
            if (r0 == 0) goto L_0x002a
            r0 = 2
            goto L_0x00b9
        L_0x002a:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function3
            if (r0 == 0) goto L_0x0031
            r0 = 3
            goto L_0x00b9
        L_0x0031:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function4
            if (r0 == 0) goto L_0x0038
            r0 = 4
            goto L_0x00b9
        L_0x0038:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function5
            if (r0 == 0) goto L_0x003f
            r0 = 5
            goto L_0x00b9
        L_0x003f:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function6
            if (r0 == 0) goto L_0x0046
            r0 = 6
            goto L_0x00b9
        L_0x0046:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function7
            if (r0 == 0) goto L_0x004d
            r0 = 7
            goto L_0x00b9
        L_0x004d:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function8
            if (r0 == 0) goto L_0x0055
            r0 = 8
            goto L_0x00b9
        L_0x0055:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function9
            if (r0 == 0) goto L_0x005d
            r0 = 9
            goto L_0x00b9
        L_0x005d:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function10
            if (r0 == 0) goto L_0x0064
            r0 = 10
            goto L_0x00b9
        L_0x0064:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function11
            if (r0 == 0) goto L_0x006b
            r0 = 11
            goto L_0x00b9
        L_0x006b:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function12
            if (r0 == 0) goto L_0x0072
            r0 = 12
            goto L_0x00b9
        L_0x0072:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function13
            if (r0 == 0) goto L_0x0079
            r0 = 13
            goto L_0x00b9
        L_0x0079:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function14
            if (r0 == 0) goto L_0x0080
            r0 = 14
            goto L_0x00b9
        L_0x0080:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function15
            if (r0 == 0) goto L_0x0087
            r0 = 15
            goto L_0x00b9
        L_0x0087:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function16
            if (r0 == 0) goto L_0x008e
            r0 = 16
            goto L_0x00b9
        L_0x008e:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function17
            if (r0 == 0) goto L_0x0095
            r0 = 17
            goto L_0x00b9
        L_0x0095:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function18
            if (r0 == 0) goto L_0x009c
            r0 = 18
            goto L_0x00b9
        L_0x009c:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function19
            if (r0 == 0) goto L_0x00a3
            r0 = 19
            goto L_0x00b9
        L_0x00a3:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function20
            if (r0 == 0) goto L_0x00aa
            r0 = 20
            goto L_0x00b9
        L_0x00aa:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function21
            if (r0 == 0) goto L_0x00b1
            r0 = 21
            goto L_0x00b9
        L_0x00b1:
            boolean r0 = r3 instanceof kotlin.jvm.functions.Function22
            if (r0 == 0) goto L_0x00b8
            r0 = 22
            goto L_0x00b9
        L_0x00b8:
            r0 = -1
        L_0x00b9:
            if (r0 != r4) goto L_0x00bc
            goto L_0x00bd
        L_0x00bc:
            r1 = r2
        L_0x00bd:
            if (r1 == 0) goto L_0x00c0
            goto L_0x00d6
        L_0x00c0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "kotlin.jvm.functions.Function"
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            throwCce(r3, r4)
            r3 = 0
            throw r3
        L_0x00d6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.internal.TypeIntrinsics.beforeCheckcastToFunctionOfArity(java.lang.Object, int):java.lang.Object");
    }

    public static void throwCce(Object obj, String str) {
        String str2;
        if (obj == null) {
            str2 = "null";
        } else {
            str2 = obj.getClass().getName();
        }
        ClassCastException classCastException = new ClassCastException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m(str2, " cannot be cast to ", str));
        Intrinsics.sanitizeStackTrace(classCastException, TypeIntrinsics.class.getName());
        throw classCastException;
    }
}
