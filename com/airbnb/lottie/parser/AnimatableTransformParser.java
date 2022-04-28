package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class AnimatableTransformParser {
    public static JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.m22of("k");
    public static JsonReader.Options NAMES = JsonReader.Options.m22of("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005e, code lost:
        r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0068, code lost:
        if (r5.keyframes.isEmpty() == false) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006a, code lost:
        r4 = r5.keyframes;
        r9 = r1;
        r10 = r5;
        r26 = r6;
        r18 = r13;
        r13 = r7;
        r1 = new com.airbnb.lottie.value.Keyframe(r28, java.lang.Float.valueOf(0.0f), java.lang.Float.valueOf(0.0f), (android.view.animation.Interpolator) null, 0.0f, java.lang.Float.valueOf(r8.endFrame));
        r4.add(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009d, code lost:
        r10 = r5;
        r26 = r6;
        r18 = r13;
        r13 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ae, code lost:
        if (r10.keyframes.get(0).startValue != null) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b0, code lost:
        r10.keyframes.set(0, new com.airbnb.lottie.value.Keyframe(r28, java.lang.Float.valueOf(0.0f), java.lang.Float.valueOf(0.0f), (android.view.animation.Interpolator) null, 0.0f, java.lang.Float.valueOf(r8.endFrame)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00d4, code lost:
        r1 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00d5, code lost:
        r6 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0116, code lost:
        r7 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0117, code lost:
        r13 = r18;
        r10 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01c5, code lost:
        if (r0 != false) goto L_0x01ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0213, code lost:
        if (((java.lang.Float) r12.keyframes.get(0).startValue).floatValue() == 0.0f) goto L_0x021b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x021e  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01cd  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01f2  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01f5  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.animatable.AnimatableTransform parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader r27, com.airbnb.lottie.LottieComposition r28) throws java.io.IOException {
        /*
            r0 = r27
            r8 = r28
            com.airbnb.lottie.parser.moshi.JsonReader$Token r1 = r27.peek()
            com.airbnb.lottie.parser.moshi.JsonReader$Token r2 = com.airbnb.lottie.parser.moshi.JsonReader.Token.BEGIN_OBJECT
            r10 = 0
            if (r1 != r2) goto L_0x000f
            r11 = 1
            goto L_0x0010
        L_0x000f:
            r11 = r10
        L_0x0010:
            if (r11 == 0) goto L_0x0015
            r27.beginObject()
        L_0x0015:
            r1 = 0
            r6 = 0
            r7 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r21 = 0
            r22 = 0
            r23 = 0
        L_0x0021:
            boolean r2 = r27.hasNext()
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r2 == 0) goto L_0x011c
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = NAMES
            int r2 = r0.selectName(r2)
            switch(r2) {
                case 0: goto L_0x00f0;
                case 1: goto L_0x00e7;
                case 2: goto L_0x00d8;
                case 3: goto L_0x0059;
                case 4: goto L_0x005e;
                case 5: goto L_0x0054;
                case 6: goto L_0x004f;
                case 7: goto L_0x004a;
                case 8: goto L_0x0045;
                case 9: goto L_0x0040;
                default: goto L_0x0033;
            }
        L_0x0033:
            r26 = r6
            r18 = r13
            r13 = r7
            r27.skipName()
            r27.skipValue()
            goto L_0x0117
        L_0x0040:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r13 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10)
            goto L_0x0021
        L_0x0045:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r14 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10)
            goto L_0x0021
        L_0x004a:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r23 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10)
            goto L_0x0021
        L_0x004f:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r22 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10)
            goto L_0x0021
        L_0x0054:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r21 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r27, r28)
            goto L_0x0021
        L_0x0059:
            java.lang.String r1 = "Lottie doesn't support 3D layers."
            r8.addWarning(r1)
        L_0x005e:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r8, r10)
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r1 = r5.keyframes
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x009d
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r4 = r5.keyframes
            com.airbnb.lottie.value.Keyframe r2 = new com.airbnb.lottie.value.Keyframe
            java.lang.Float r16 = java.lang.Float.valueOf(r3)
            java.lang.Float r17 = java.lang.Float.valueOf(r3)
            r18 = 0
            r19 = 0
            float r1 = r8.endFrame
            java.lang.Float r20 = java.lang.Float.valueOf(r1)
            r1 = r2
            r3 = r2
            r2 = r28
            r9 = r3
            r3 = r16
            r12 = r4
            r4 = r17
            r10 = r5
            r5 = r18
            r26 = r6
            r6 = r19
            r18 = r13
            r13 = r7
            r7 = r20
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r12.add(r9)
            goto L_0x00d4
        L_0x009d:
            r10 = r5
            r26 = r6
            r18 = r13
            r13 = r7
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r1 = r10.keyframes
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            com.airbnb.lottie.value.Keyframe r1 = (com.airbnb.lottie.value.Keyframe) r1
            T r1 = r1.startValue
            if (r1 != 0) goto L_0x00d4
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r9 = r10.keyframes
            com.airbnb.lottie.value.Keyframe r12 = new com.airbnb.lottie.value.Keyframe
            java.lang.Float r4 = java.lang.Float.valueOf(r3)
            java.lang.Float r5 = java.lang.Float.valueOf(r3)
            r6 = 0
            r7 = 0
            float r1 = r8.endFrame
            java.lang.Float r19 = java.lang.Float.valueOf(r1)
            r1 = r12
            r2 = r28
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r1 = 0
            r9.set(r1, r12)
        L_0x00d4:
            r1 = r10
        L_0x00d5:
            r6 = r26
            goto L_0x0116
        L_0x00d8:
            r18 = r13
            r13 = r7
            com.airbnb.lottie.model.animatable.AnimatableScaleValue r6 = new com.airbnb.lottie.model.animatable.AnimatableScaleValue
            com.airbnb.lottie.parser.ScaleXYParser r2 = com.airbnb.lottie.parser.ScaleXYParser.INSTANCE
            java.util.ArrayList r2 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r8, r4, r2)
            r6.<init>(r2)
            goto L_0x0116
        L_0x00e7:
            r26 = r6
            r18 = r13
            com.airbnb.lottie.model.animatable.AnimatableValue r7 = com.airbnb.lottie.parser.AnimatablePathValueParser.parseSplitPath(r27, r28)
            goto L_0x0117
        L_0x00f0:
            r26 = r6
            r18 = r13
            r13 = r7
            r27.beginObject()
        L_0x00f8:
            boolean r2 = r27.hasNext()
            if (r2 == 0) goto L_0x0112
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = ANIMATABLE_NAMES
            int r2 = r0.selectName(r2)
            if (r2 == 0) goto L_0x010d
            r27.skipName()
            r27.skipValue()
            goto L_0x00f8
        L_0x010d:
            com.android.systemui.qs.external.QSTileServiceWrapper r15 = com.airbnb.lottie.parser.AnimatablePathValueParser.parse(r27, r28)
            goto L_0x00f8
        L_0x0112:
            r27.endObject()
            goto L_0x00d5
        L_0x0116:
            r7 = r13
        L_0x0117:
            r13 = r18
            r10 = 0
            goto L_0x0021
        L_0x011c:
            r26 = r6
            r18 = r13
            r13 = r7
            if (r11 == 0) goto L_0x0126
            r27.endObject()
        L_0x0126:
            if (r15 == 0) goto L_0x0146
            boolean r0 = r15.isStatic()
            if (r0 == 0) goto L_0x0144
            java.lang.Object r0 = r15.mService
            java.util.List r0 = (java.util.List) r0
            r2 = 0
            java.lang.Object r0 = r0.get(r2)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            android.graphics.PointF r0 = (android.graphics.PointF) r0
            boolean r0 = r0.equals(r3, r3)
            if (r0 == 0) goto L_0x0144
            goto L_0x0146
        L_0x0144:
            r0 = 0
            goto L_0x0147
        L_0x0146:
            r0 = 1
        L_0x0147:
            if (r0 == 0) goto L_0x014a
            r15 = 0
        L_0x014a:
            if (r13 == 0) goto L_0x016e
            boolean r0 = r13 instanceof com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue
            if (r0 != 0) goto L_0x016c
            boolean r0 = r13.isStatic()
            if (r0 == 0) goto L_0x016c
            java.util.List r0 = r13.getKeyframes()
            r2 = 0
            java.lang.Object r0 = r0.get(r2)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            android.graphics.PointF r0 = (android.graphics.PointF) r0
            boolean r0 = r0.equals(r3, r3)
            if (r0 == 0) goto L_0x016c
            goto L_0x016e
        L_0x016c:
            r0 = 0
            goto L_0x016f
        L_0x016e:
            r0 = 1
        L_0x016f:
            if (r0 == 0) goto L_0x0172
            r13 = 0
        L_0x0172:
            if (r1 == 0) goto L_0x0192
            boolean r0 = r1.isStatic()
            if (r0 == 0) goto L_0x0190
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r0 = r1.keyframes
            r2 = 0
            java.lang.Object r0 = r0.get(r2)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            java.lang.Float r0 = (java.lang.Float) r0
            float r0 = r0.floatValue()
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0190
            goto L_0x0192
        L_0x0190:
            r0 = 0
            goto L_0x0193
        L_0x0192:
            r0 = 1
        L_0x0193:
            if (r0 == 0) goto L_0x019a
            r6 = r26
            r20 = 0
            goto L_0x019e
        L_0x019a:
            r20 = r1
            r6 = r26
        L_0x019e:
            if (r6 == 0) goto L_0x01ca
            boolean r0 = r6.isStatic()
            if (r0 == 0) goto L_0x01c8
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r0 = r6.keyframes
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            com.airbnb.lottie.value.ScaleXY r0 = (com.airbnb.lottie.value.ScaleXY) r0
            java.util.Objects.requireNonNull(r0)
            float r1 = r0.scaleX
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x01c4
            float r0 = r0.scaleY
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x01c4
            r0 = 1
            goto L_0x01c5
        L_0x01c4:
            r0 = 0
        L_0x01c5:
            if (r0 == 0) goto L_0x01c8
            goto L_0x01ca
        L_0x01c8:
            r0 = 0
            goto L_0x01cb
        L_0x01ca:
            r0 = 1
        L_0x01cb:
            if (r0 == 0) goto L_0x01d0
            r19 = 0
            goto L_0x01d2
        L_0x01d0:
            r19 = r6
        L_0x01d2:
            if (r14 == 0) goto L_0x01f2
            boolean r0 = r14.isStatic()
            if (r0 == 0) goto L_0x01f0
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r0 = r14.keyframes
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            java.lang.Float r0 = (java.lang.Float) r0
            float r0 = r0.floatValue()
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x01f0
            goto L_0x01f2
        L_0x01f0:
            r2 = 0
            goto L_0x01f3
        L_0x01f2:
            r2 = 1
        L_0x01f3:
            if (r2 == 0) goto L_0x01f6
            r14 = 0
        L_0x01f6:
            if (r18 == 0) goto L_0x0219
            boolean r0 = r18.isStatic()
            r12 = r18
            if (r0 == 0) goto L_0x0216
            java.util.List<com.airbnb.lottie.value.Keyframe<V>> r0 = r12.keyframes
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.airbnb.lottie.value.Keyframe r0 = (com.airbnb.lottie.value.Keyframe) r0
            T r0 = r0.startValue
            java.lang.Float r0 = (java.lang.Float) r0
            float r0 = r0.floatValue()
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0217
            goto L_0x021b
        L_0x0216:
            r1 = 0
        L_0x0217:
            r9 = r1
            goto L_0x021c
        L_0x0219:
            r12 = r18
        L_0x021b:
            r9 = 1
        L_0x021c:
            if (r9 == 0) goto L_0x0221
            r25 = 0
            goto L_0x0223
        L_0x0221:
            r25 = r12
        L_0x0223:
            com.airbnb.lottie.model.animatable.AnimatableTransform r0 = new com.airbnb.lottie.model.animatable.AnimatableTransform
            r16 = r0
            r17 = r15
            r18 = r13
            r24 = r14
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.AnimatableTransformParser.parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.animatable.AnimatableTransform");
    }
}
