package kotlin.text;

import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: Strings.kt */
final class StringsKt__StringsKt$rangesDelimitedBy$2 extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
    public final /* synthetic */ List<String> $delimitersList;
    public final /* synthetic */ boolean $ignoreCase;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StringsKt__StringsKt$rangesDelimitedBy$2(List<String> list, boolean z) {
        super(2);
        this.$delimitersList = list;
        this.$ignoreCase = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invoke(java.lang.Object r14, java.lang.Object r15) {
        /*
            r13 = this;
            java.lang.CharSequence r14 = (java.lang.CharSequence) r14
            java.lang.Number r15 = (java.lang.Number) r15
            int r15 = r15.intValue()
            java.util.List<java.lang.String> r0 = r13.$delimitersList
            boolean r13 = r13.$ignoreCase
            r1 = 0
            r7 = 0
            if (r13 != 0) goto L_0x0049
            int r2 = r0.size()
            r3 = 1
            if (r2 != r3) goto L_0x0049
            int r13 = r0.size()
            if (r13 == 0) goto L_0x0041
            if (r13 != r3) goto L_0x0039
            java.lang.Object r13 = r0.get(r1)
            java.lang.String r13 = (java.lang.String) r13
            r0 = 4
            int r14 = kotlin.text.StringsKt__StringsKt.indexOf$default(r14, r13, r15, r1, r0)
            if (r14 >= 0) goto L_0x002e
            goto L_0x00db
        L_0x002e:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            kotlin.Pair r15 = new kotlin.Pair
            r15.<init>(r14, r13)
            goto L_0x00dc
        L_0x0039:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.String r14 = "List has more than one element."
            r13.<init>(r14)
            throw r13
        L_0x0041:
            java.util.NoSuchElementException r13 = new java.util.NoSuchElementException
            java.lang.String r14 = "List is empty."
            r13.<init>(r14)
            throw r13
        L_0x0049:
            kotlin.ranges.IntRange r2 = new kotlin.ranges.IntRange
            if (r15 >= 0) goto L_0x004e
            r15 = r1
        L_0x004e:
            int r1 = r14.length()
            r2.<init>(r15, r1)
            boolean r1 = r14 instanceof java.lang.String
            if (r1 == 0) goto L_0x009d
            int r8 = r2.last
            int r9 = r2.step
            if (r9 <= 0) goto L_0x0061
            if (r15 <= r8) goto L_0x0065
        L_0x0061:
            if (r9 >= 0) goto L_0x00db
            if (r8 > r15) goto L_0x00db
        L_0x0065:
            int r10 = r15 + r9
            java.util.Iterator r11 = r0.iterator()
        L_0x006b:
            boolean r1 = r11.hasNext()
            if (r1 == 0) goto L_0x0089
            java.lang.Object r12 = r11.next()
            r1 = r12
            java.lang.String r1 = (java.lang.String) r1
            r2 = 0
            r3 = r14
            java.lang.String r3 = (java.lang.String) r3
            int r5 = r1.length()
            r4 = r15
            r6 = r13
            boolean r1 = kotlin.text.StringsKt__StringsJVMKt.regionMatches(r1, r2, r3, r4, r5, r6)
            if (r1 == 0) goto L_0x006b
            goto L_0x008a
        L_0x0089:
            r12 = r7
        L_0x008a:
            java.lang.String r12 = (java.lang.String) r12
            if (r12 == 0) goto L_0x0098
            java.lang.Integer r13 = java.lang.Integer.valueOf(r15)
            kotlin.Pair r15 = new kotlin.Pair
            r15.<init>(r13, r12)
            goto L_0x00dc
        L_0x0098:
            if (r15 != r8) goto L_0x009b
            goto L_0x00db
        L_0x009b:
            r15 = r10
            goto L_0x0065
        L_0x009d:
            int r1 = r2.last
            int r2 = r2.step
            if (r2 <= 0) goto L_0x00a5
            if (r15 <= r1) goto L_0x00a9
        L_0x00a5:
            if (r2 >= 0) goto L_0x00db
            if (r1 > r15) goto L_0x00db
        L_0x00a9:
            int r3 = r15 + r2
            java.util.Iterator r4 = r0.iterator()
        L_0x00af:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00c7
            java.lang.Object r5 = r4.next()
            r6 = r5
            java.lang.String r6 = (java.lang.String) r6
            int r8 = r6.length()
            boolean r6 = kotlin.text.StringsKt__StringsKt.regionMatchesImpl(r6, r14, r15, r8, r13)
            if (r6 == 0) goto L_0x00af
            goto L_0x00c8
        L_0x00c7:
            r5 = r7
        L_0x00c8:
            java.lang.String r5 = (java.lang.String) r5
            if (r5 == 0) goto L_0x00d6
            java.lang.Integer r13 = java.lang.Integer.valueOf(r15)
            kotlin.Pair r15 = new kotlin.Pair
            r15.<init>(r13, r5)
            goto L_0x00dc
        L_0x00d6:
            if (r15 != r1) goto L_0x00d9
            goto L_0x00db
        L_0x00d9:
            r15 = r3
            goto L_0x00a9
        L_0x00db:
            r15 = r7
        L_0x00dc:
            if (r15 != 0) goto L_0x00df
            goto L_0x00f6
        L_0x00df:
            java.lang.Object r13 = r15.getFirst()
            java.lang.Object r14 = r15.getSecond()
            java.lang.String r14 = (java.lang.String) r14
            int r14 = r14.length()
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            kotlin.Pair r7 = new kotlin.Pair
            r7.<init>(r13, r14)
        L_0x00f6:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.StringsKt__StringsKt$rangesDelimitedBy$2.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
