package kotlin.text;

/* compiled from: StringsJVM.kt */
public class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    public static String replace$default(String str, String str2, String str3) {
        int indexOf = StringsKt__StringsKt.indexOf(str, str2, 0, false);
        if (indexOf < 0) {
            return str;
        }
        int length = str2.length();
        int i = 1;
        if (length >= 1) {
            i = length;
        }
        int length2 = str3.length() + (str.length() - length);
        if (length2 >= 0) {
            StringBuilder sb = new StringBuilder(length2);
            int i2 = 0;
            do {
                sb.append(str, i2, indexOf);
                sb.append(str3);
                i2 = indexOf + length;
                if (indexOf >= str.length() || (indexOf = StringsKt__StringsKt.indexOf(str, str2, indexOf + i, false)) <= 0) {
                    sb.append(str, i2, str.length());
                }
                sb.append(str, i2, indexOf);
                sb.append(str3);
                i2 = indexOf + length;
                break;
            } while ((indexOf = StringsKt__StringsKt.indexOf(str, str2, indexOf + i, false)) <= 0);
            sb.append(str, i2, str.length());
            return sb.toString();
        }
        throw new OutOfMemoryError();
    }

    public static final boolean regionMatches(String str, int i, String str2, int i2, int i3, boolean z) {
        if (!z) {
            return str.regionMatches(i, str2, i2, i3);
        }
        return str.regionMatches(z, i, str2, i2, i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final boolean isBlank(java.lang.String r4) {
        /*
            int r0 = r4.length()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0042
            kotlin.ranges.IntRange r0 = new kotlin.ranges.IntRange
            int r3 = r4.length()
            int r3 = r3 + -1
            r0.<init>(r1, r3)
            boolean r3 = r0 instanceof java.util.Collection
            if (r3 == 0) goto L_0x0021
            r3 = r0
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x0021
            goto L_0x003f
        L_0x0021:
            java.util.Iterator r0 = r0.iterator()
        L_0x0025:
            r3 = r0
            kotlin.ranges.IntProgressionIterator r3 = (kotlin.ranges.IntProgressionIterator) r3
            boolean r3 = r3.hasNext
            if (r3 == 0) goto L_0x003f
            r3 = r0
            kotlin.collections.IntIterator r3 = (kotlin.collections.IntIterator) r3
            int r3 = r3.nextInt()
            char r3 = r4.charAt(r3)
            boolean r3 = kotlin.text.CharsKt__CharKt.isWhitespace(r3)
            if (r3 != 0) goto L_0x0025
            r4 = r1
            goto L_0x0040
        L_0x003f:
            r4 = r2
        L_0x0040:
            if (r4 == 0) goto L_0x0043
        L_0x0042:
            r1 = r2
        L_0x0043:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.StringsKt__StringsJVMKt.isBlank(java.lang.String):boolean");
    }
}
