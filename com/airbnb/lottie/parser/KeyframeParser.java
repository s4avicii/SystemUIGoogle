package com.airbnb.lottie.parser;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.lang.ref.WeakReference;

public final class KeyframeParser {
    public static final LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static JsonReader.Options NAMES = JsonReader.Options.m22of("t", "s", "e", "o", "i", "h", "to", "ti");
    public static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: android.view.animation.Interpolator} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.airbnb.lottie.value.Keyframe<T> parse(com.airbnb.lottie.parser.moshi.JsonReader r16, com.airbnb.lottie.LottieComposition r17, float r18, com.airbnb.lottie.parser.ValueParser<T> r19, boolean r20) throws java.io.IOException {
        /*
            r0 = r16
            r1 = r18
            r2 = r19
            if (r20 == 0) goto L_0x0155
            java.lang.Class<com.airbnb.lottie.parser.KeyframeParser> r3 = com.airbnb.lottie.parser.KeyframeParser.class
            r16.beginObject()
            r4 = 0
            r14 = r4
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r13 = 0
            r15 = 0
        L_0x0016:
            boolean r12 = r16.hasNext()
            if (r12 == 0) goto L_0x0058
            com.airbnb.lottie.parser.moshi.JsonReader$Options r12 = NAMES
            int r12 = r0.selectName(r12)
            switch(r12) {
                case 0: goto L_0x0052;
                case 1: goto L_0x004d;
                case 2: goto L_0x0048;
                case 3: goto L_0x0043;
                case 4: goto L_0x003e;
                case 5: goto L_0x0033;
                case 6: goto L_0x002e;
                case 7: goto L_0x0029;
                default: goto L_0x0025;
            }
        L_0x0025:
            r16.skipValue()
            goto L_0x0016
        L_0x0029:
            android.graphics.PointF r13 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r0, r1)
            goto L_0x0016
        L_0x002e:
            android.graphics.PointF r15 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r0, r1)
            goto L_0x0016
        L_0x0033:
            int r7 = r16.nextInt()
            r12 = 1
            if (r7 != r12) goto L_0x003c
            r7 = r12
            goto L_0x0016
        L_0x003c:
            r7 = 0
            goto L_0x0016
        L_0x003e:
            android.graphics.PointF r9 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r0, r1)
            goto L_0x0016
        L_0x0043:
            android.graphics.PointF r8 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r0, r1)
            goto L_0x0016
        L_0x0048:
            java.lang.Object r10 = r2.parse(r0, r1)
            goto L_0x0016
        L_0x004d:
            java.lang.Object r11 = r2.parse(r0, r1)
            goto L_0x0016
        L_0x0052:
            double r5 = r16.nextDouble()
            float r14 = (float) r5
            goto L_0x0016
        L_0x0058:
            r16.endObject()
            if (r7 == 0) goto L_0x0062
            android.view.animation.LinearInterpolator r0 = LINEAR_INTERPOLATOR
            r12 = r11
            goto L_0x0143
        L_0x0062:
            if (r8 == 0) goto L_0x013f
            if (r9 == 0) goto L_0x013f
            float r0 = r8.x
            float r2 = -r1
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r2, r1)
            r8.x = r0
            float r0 = r8.y
            r5 = -1027080192(0xffffffffc2c80000, float:-100.0)
            r6 = 1120403456(0x42c80000, float:100.0)
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r5, r6)
            r8.y = r0
            float r0 = r9.x
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r2, r1)
            r9.x = r0
            float r0 = r9.y
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r5, r6)
            r9.y = r0
            float r2 = r8.x
            float r5 = r8.y
            float r6 = r9.x
            android.graphics.PathMeasure r7 = com.airbnb.lottie.utils.Utils.pathMeasure
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x009d
            r7 = 527(0x20f, float:7.38E-43)
            float r7 = (float) r7
            float r7 = r7 * r2
            int r2 = (int) r7
            goto L_0x009f
        L_0x009d:
            r2 = 17
        L_0x009f:
            int r7 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x00a8
            int r2 = r2 * 31
            float r2 = (float) r2
            float r2 = r2 * r5
            int r2 = (int) r2
        L_0x00a8:
            int r5 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x00b1
            int r2 = r2 * 31
            float r2 = (float) r2
            float r2 = r2 * r6
            int r2 = (int) r2
        L_0x00b1:
            int r5 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x00ba
            int r2 = r2 * 31
            float r2 = (float) r2
            float r2 = r2 * r0
            int r2 = (int) r2
        L_0x00ba:
            monitor-enter(r3)
            androidx.collection.SparseArrayCompat<java.lang.ref.WeakReference<android.view.animation.Interpolator>> r0 = pathInterpolatorCache     // Catch:{ all -> 0x013c }
            if (r0 != 0) goto L_0x00c6
            androidx.collection.SparseArrayCompat r0 = new androidx.collection.SparseArrayCompat     // Catch:{ all -> 0x013c }
            r0.<init>()     // Catch:{ all -> 0x013c }
            pathInterpolatorCache = r0     // Catch:{ all -> 0x013c }
        L_0x00c6:
            androidx.collection.SparseArrayCompat<java.lang.ref.WeakReference<android.view.animation.Interpolator>> r0 = pathInterpolatorCache     // Catch:{ all -> 0x013c }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x013c }
            r5 = 0
            java.lang.Object r0 = r0.get(r2, r5)     // Catch:{ all -> 0x013c }
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0     // Catch:{ all -> 0x013c }
            monitor-exit(r3)     // Catch:{ all -> 0x013c }
            if (r0 == 0) goto L_0x00dd
            java.lang.Object r5 = r0.get()
            r6 = r5
            android.view.animation.Interpolator r6 = (android.view.animation.Interpolator) r6
            goto L_0x00de
        L_0x00dd:
            r6 = r5
        L_0x00de:
            if (r0 == 0) goto L_0x00e2
            if (r6 != 0) goto L_0x0141
        L_0x00e2:
            float r0 = r8.x
            float r0 = r0 / r1
            r8.x = r0
            float r0 = r8.y
            float r0 = r0 / r1
            r8.y = r0
            float r0 = r9.x
            float r0 = r0 / r1
            r9.x = r0
            float r5 = r9.y
            float r5 = r5 / r1
            r9.y = r5
            float r1 = r8.x     // Catch:{ IllegalArgumentException -> 0x0101 }
            float r6 = r8.y     // Catch:{ IllegalArgumentException -> 0x0101 }
            android.view.animation.PathInterpolator r7 = new android.view.animation.PathInterpolator     // Catch:{ IllegalArgumentException -> 0x0101 }
            r7.<init>(r1, r6, r0, r5)     // Catch:{ IllegalArgumentException -> 0x0101 }
            r6 = r7
            goto L_0x012c
        L_0x0101:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            java.lang.String r1 = "The Path cannot loop back on itself."
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0126
            float r0 = r8.x
            r1 = 1065353216(0x3f800000, float:1.0)
            float r0 = java.lang.Math.min(r0, r1)
            float r1 = r8.y
            float r5 = r9.x
            float r4 = java.lang.Math.max(r5, r4)
            float r5 = r9.y
            android.view.animation.PathInterpolator r6 = new android.view.animation.PathInterpolator
            r6.<init>(r0, r1, r4, r5)
            goto L_0x012c
        L_0x0126:
            android.view.animation.LinearInterpolator r0 = new android.view.animation.LinearInterpolator
            r0.<init>()
            r6 = r0
        L_0x012c:
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0141 }
            r0.<init>(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0141 }
            monitor-enter(r3)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0141 }
            androidx.collection.SparseArrayCompat<java.lang.ref.WeakReference<android.view.animation.Interpolator>> r1 = pathInterpolatorCache     // Catch:{ all -> 0x0139 }
            r1.put(r2, r0)     // Catch:{ all -> 0x0139 }
            monitor-exit(r3)     // Catch:{ all -> 0x0139 }
            goto L_0x0141
        L_0x0139:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0139 }
            throw r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0141 }
        L_0x013c:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x013c }
            throw r0
        L_0x013f:
            android.view.animation.LinearInterpolator r6 = LINEAR_INTERPOLATOR
        L_0x0141:
            r0 = r6
            r12 = r10
        L_0x0143:
            com.airbnb.lottie.value.Keyframe r1 = new com.airbnb.lottie.value.Keyframe
            r2 = 0
            r9 = r1
            r10 = r17
            r6 = r13
            r13 = r0
            r3 = r15
            r15 = r2
            r9.<init>(r10, r11, r12, r13, r14, r15)
            r1.pathCp1 = r3
            r1.pathCp2 = r6
            return r1
        L_0x0155:
            java.lang.Object r0 = r2.parse(r0, r1)
            com.airbnb.lottie.value.Keyframe r1 = new com.airbnb.lottie.value.Keyframe
            r1.<init>(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.KeyframeParser.parse(com.airbnb.lottie.parser.moshi.JsonReader, com.airbnb.lottie.LottieComposition, float, com.airbnb.lottie.parser.ValueParser, boolean):com.airbnb.lottie.value.Keyframe");
    }
}
