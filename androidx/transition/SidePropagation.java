package androidx.transition;

public final class SidePropagation extends VisibilityPropagation {
    public int mSide = 80;

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0095, code lost:
        if (r6 != false) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a7, code lost:
        if (r6 != false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ab, code lost:
        r8 = 5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0103  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long getStartDelay(android.view.ViewGroup r19, androidx.transition.Transition r20, androidx.transition.TransitionValues r21, androidx.transition.TransitionValues r22) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            r2 = r21
            r3 = 0
            if (r2 != 0) goto L_0x000d
            if (r22 != 0) goto L_0x000d
            return r3
        L_0x000d:
            java.util.Objects.requireNonNull(r20)
            androidx.transition.Transition$EpicenterCallback r5 = r1.mEpicenterCallback
            if (r5 != 0) goto L_0x0016
            r5 = 0
            goto L_0x001a
        L_0x0016:
            android.graphics.Rect r5 = r5.onGetEpicenter()
        L_0x001a:
            r6 = 1
            if (r22 == 0) goto L_0x003a
            r7 = 8
            if (r2 != 0) goto L_0x0022
            goto L_0x0033
        L_0x0022:
            java.util.HashMap r8 = r2.values
            java.lang.String r9 = "android:visibilityPropagation:visibility"
            java.lang.Object r8 = r8.get(r9)
            java.lang.Integer r8 = (java.lang.Integer) r8
            if (r8 != 0) goto L_0x002f
            goto L_0x0033
        L_0x002f:
            int r7 = r8.intValue()
        L_0x0033:
            if (r7 != 0) goto L_0x0036
            goto L_0x003a
        L_0x0036:
            r2 = r22
            r7 = r6
            goto L_0x003b
        L_0x003a:
            r7 = -1
        L_0x003b:
            r8 = 0
            int r9 = androidx.transition.VisibilityPropagation.getViewCoordinate(r2, r8)
            int r2 = androidx.transition.VisibilityPropagation.getViewCoordinate(r2, r6)
            r10 = 2
            int[] r11 = new int[r10]
            r12 = r19
            r12.getLocationOnScreen(r11)
            r13 = r11[r8]
            float r14 = r19.getTranslationX()
            int r14 = java.lang.Math.round(r14)
            int r14 = r14 + r13
            r11 = r11[r6]
            float r13 = r19.getTranslationY()
            int r13 = java.lang.Math.round(r13)
            int r13 = r13 + r11
            int r11 = r19.getWidth()
            int r11 = r11 + r14
            int r15 = r19.getHeight()
            int r15 = r15 + r13
            if (r5 == 0) goto L_0x0077
            int r10 = r5.centerX()
            int r5 = r5.centerY()
            goto L_0x0083
        L_0x0077:
            int r5 = r14 + r11
            int r5 = r5 / r10
            int r16 = r13 + r15
            int r10 = r16 / 2
            r17 = r10
            r10 = r5
            r5 = r17
        L_0x0083:
            int r8 = r0.mSide
            r4 = 8388611(0x800003, float:1.1754948E-38)
            r3 = 3
            if (r8 != r4) goto L_0x0098
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r8 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r8 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r19)
            if (r8 != r6) goto L_0x0094
            goto L_0x0095
        L_0x0094:
            r6 = 0
        L_0x0095:
            if (r6 == 0) goto L_0x00a9
            goto L_0x00ab
        L_0x0098:
            r4 = 8388613(0x800005, float:1.175495E-38)
            if (r8 != r4) goto L_0x00ac
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r4 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r19)
            if (r4 != r6) goto L_0x00a6
            goto L_0x00a7
        L_0x00a6:
            r6 = 0
        L_0x00a7:
            if (r6 == 0) goto L_0x00ab
        L_0x00a9:
            r8 = r3
            goto L_0x00ac
        L_0x00ab:
            r8 = 5
        L_0x00ac:
            if (r8 == r3) goto L_0x00d6
            r4 = 5
            if (r8 == r4) goto L_0x00cd
            r4 = 48
            if (r8 == r4) goto L_0x00c4
            r4 = 80
            if (r8 == r4) goto L_0x00bb
            r8 = 0
            goto L_0x00de
        L_0x00bb:
            int r2 = r2 - r13
            int r10 = r10 - r9
            int r4 = java.lang.Math.abs(r10)
            int r8 = r4 + r2
            goto L_0x00de
        L_0x00c4:
            int r15 = r15 - r2
            int r10 = r10 - r9
            int r2 = java.lang.Math.abs(r10)
            int r8 = r2 + r15
            goto L_0x00de
        L_0x00cd:
            int r9 = r9 - r14
            int r5 = r5 - r2
            int r2 = java.lang.Math.abs(r5)
            int r8 = r2 + r9
            goto L_0x00de
        L_0x00d6:
            int r11 = r11 - r9
            int r5 = r5 - r2
            int r2 = java.lang.Math.abs(r5)
            int r8 = r2 + r11
        L_0x00de:
            float r2 = (float) r8
            int r0 = r0.mSide
            if (r0 == r3) goto L_0x00f5
            r3 = 5
            if (r0 == r3) goto L_0x00f5
            r3 = 8388611(0x800003, float:1.1754948E-38)
            if (r0 == r3) goto L_0x00f5
            r3 = 8388613(0x800005, float:1.175495E-38)
            if (r0 == r3) goto L_0x00f5
            int r0 = r19.getHeight()
            goto L_0x00f9
        L_0x00f5:
            int r0 = r19.getWidth()
        L_0x00f9:
            float r0 = (float) r0
            float r2 = r2 / r0
            long r0 = r1.mDuration
            r3 = 0
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x0105
            r0 = 300(0x12c, double:1.48E-321)
        L_0x0105:
            long r3 = (long) r7
            long r0 = r0 * r3
            float r0 = (float) r0
            r1 = 1077936128(0x40400000, float:3.0)
            float r0 = r0 / r1
            float r0 = r0 * r2
            int r0 = java.lang.Math.round(r0)
            long r0 = (long) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.SidePropagation.getStartDelay(android.view.ViewGroup, androidx.transition.Transition, androidx.transition.TransitionValues, androidx.transition.TransitionValues):long");
    }
}
