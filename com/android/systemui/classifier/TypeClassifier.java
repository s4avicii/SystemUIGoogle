package com.android.systemui.classifier;

public final class TypeClassifier extends FalsingClassifier {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0030, code lost:
        if (r4 != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
        if (r4 != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        if (r4 != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        if (r4 == false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
        if (r2 == false) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        r10 = r9.mDataProvider;
        java.util.Objects.requireNonNull(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return com.android.systemui.classifier.FalsingClassifier.Result.passed(0.5d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return falsed(r6, java.lang.String.format("{interaction=%s, vertical=%s, up=%s, right=%s}", new java.lang.Object[]{java.lang.Integer.valueOf(r10), java.lang.Boolean.valueOf(!r10.isHorizontal()), java.lang.Boolean.valueOf(isUp()), java.lang.Boolean.valueOf(isRight())}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0028, code lost:
        if (r4 != false) goto L_0x0045;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.systemui.classifier.FalsingClassifier.Result calculateFalsingResult(int r10) {
        /*
            r9 = this;
            r0 = 0
            r2 = 13
            if (r10 == r2) goto L_0x008c
            r2 = 14
            if (r10 != r2) goto L_0x000c
            goto L_0x008c
        L_0x000c:
            com.android.systemui.classifier.FalsingDataProvider r2 = r9.mDataProvider
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.isHorizontal()
            r3 = 1
            r2 = r2 ^ r3
            boolean r4 = r9.isUp()
            boolean r5 = r9.isRight()
            r6 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r8 = 0
            switch(r10) {
                case 0: goto L_0x0040;
                case 1: goto L_0x003d;
                case 2: goto L_0x0040;
                case 3: goto L_0x0025;
                case 4: goto L_0x0038;
                case 5: goto L_0x0033;
                case 6: goto L_0x002e;
                case 7: goto L_0x0025;
                case 8: goto L_0x0038;
                case 9: goto L_0x0040;
                case 10: goto L_0x003e;
                case 11: goto L_0x002b;
                case 12: goto L_0x0026;
                case 13: goto L_0x0025;
                case 14: goto L_0x0025;
                case 15: goto L_0x0048;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x0047
        L_0x0026:
            if (r2 == 0) goto L_0x0047
            if (r4 != 0) goto L_0x0045
            goto L_0x0047
        L_0x002b:
            r2 = r2 ^ 1
            goto L_0x0048
        L_0x002e:
            if (r5 != 0) goto L_0x0047
            if (r4 != 0) goto L_0x0045
            goto L_0x0047
        L_0x0033:
            if (r5 == 0) goto L_0x0047
            if (r4 != 0) goto L_0x0045
            goto L_0x0047
        L_0x0038:
            if (r2 == 0) goto L_0x0047
            if (r4 != 0) goto L_0x0045
            goto L_0x0047
        L_0x003d:
            r0 = r6
        L_0x003e:
            r6 = r0
            goto L_0x0048
        L_0x0040:
            if (r2 == 0) goto L_0x0047
            if (r4 == 0) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r2 = r8
            goto L_0x0048
        L_0x0047:
            r2 = r3
        L_0x0048:
            if (r2 == 0) goto L_0x0085
            r0 = 4
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r0[r8] = r10
            com.android.systemui.classifier.FalsingDataProvider r10 = r9.mDataProvider
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.isHorizontal()
            r10 = r10 ^ r3
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)
            r0[r3] = r10
            boolean r10 = r9.isUp()
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)
            r1 = 2
            r0[r1] = r10
            boolean r10 = r9.isRight()
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)
            r1 = 3
            r0[r1] = r10
            java.lang.String r10 = "{interaction=%s, vertical=%s, up=%s, right=%s}"
            java.lang.String r10 = java.lang.String.format(r10, r0)
            com.android.systemui.classifier.FalsingClassifier$Result r9 = r9.falsed(r6, r10)
            goto L_0x008b
        L_0x0085:
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            com.android.systemui.classifier.FalsingClassifier$Result r9 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r9)
        L_0x008b:
            return r9
        L_0x008c:
            com.android.systemui.classifier.FalsingClassifier$Result r9 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.TypeClassifier.calculateFalsingResult(int):com.android.systemui.classifier.FalsingClassifier$Result");
    }

    public TypeClassifier(FalsingDataProvider falsingDataProvider) {
        super(falsingDataProvider);
    }
}
