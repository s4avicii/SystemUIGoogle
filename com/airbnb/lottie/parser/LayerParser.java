package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class LayerParser {
    public static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.m22of("nm");
    public static final JsonReader.Options NAMES = JsonReader.Options.m22of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    public static final JsonReader.Options TEXT_NAMES = JsonReader.Options.m22of("d", "a");

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: com.airbnb.lottie.model.content.Mask$MaskMode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: com.airbnb.lottie.model.animatable.AnimatableIntegerValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: com.airbnb.lottie.model.animatable.AnimatableIntegerValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: com.airbnb.lottie.model.animatable.AnimatableShapeValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: com.airbnb.lottie.model.animatable.AnimatableIntegerValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: com.airbnb.lottie.model.animatable.AnimatableIntegerValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v15, resolved type: com.airbnb.lottie.model.animatable.AnimatableShapeValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v23, resolved type: com.airbnb.lottie.model.animatable.AnimatableShapeValue} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0395, code lost:
        r11 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0396, code lost:
        r6 = r39;
        r14 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03a7, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x03de, code lost:
        r11 = r0;
        r6 = r39;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x03e2, code lost:
        r11 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00b2, code lost:
        r39 = r6;
        r40 = r14;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.layer.Layer parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader r42, com.airbnb.lottie.LottieComposition r43) throws java.io.IOException {
        /*
            r0 = r42
            r7 = r43
            com.airbnb.lottie.model.layer.Layer$MatteType r1 = com.airbnb.lottie.model.layer.Layer.MatteType.NONE
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r42.beginObject()
            r2 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r9 = java.lang.Float.valueOf(r2)
            r11 = 0
            java.lang.Float r12 = java.lang.Float.valueOf(r11)
            r3 = 0
            java.lang.String r5 = "UNSET"
            r13 = 0
            r15 = -1
            r30 = r1
            r24 = r2
            r21 = r3
            r22 = r21
            r23 = r22
            r26 = r23
            r27 = r26
            r32 = r27
            r1 = r11
            r25 = r1
            r17 = r15
            r6 = 0
            r16 = 0
            r19 = 0
            r20 = 0
            r28 = 0
            r29 = 0
            r31 = 0
            r14 = r13
            r13 = r5
            r5 = r25
            r11 = r0
        L_0x004c:
            boolean r34 = r42.hasNext()
            if (r34 == 0) goto L_0x03e4
            com.airbnb.lottie.parser.moshi.JsonReader$Options r4 = NAMES
            int r4 = r11.selectName(r4)
            r2 = 1
            switch(r4) {
                case 0: goto L_0x03d6;
                case 1: goto L_0x03ce;
                case 2: goto L_0x03c5;
                case 3: goto L_0x03ac;
                case 4: goto L_0x039b;
                case 5: goto L_0x0384;
                case 6: goto L_0x0372;
                case 7: goto L_0x0364;
                case 8: goto L_0x035a;
                case 9: goto L_0x0342;
                case 10: goto L_0x0237;
                case 11: goto L_0x0218;
                case 12: goto L_0x010a;
                case 13: goto L_0x00bb;
                case 14: goto L_0x00ab;
                case 15: goto L_0x00a3;
                case 16: goto L_0x0095;
                case 17: goto L_0x0087;
                case 18: goto L_0x0081;
                case 19: goto L_0x007a;
                case 20: goto L_0x0074;
                case 21: goto L_0x006e;
                case 22: goto L_0x0068;
                default: goto L_0x005c;
            }
        L_0x005c:
            r39 = r6
            r40 = r14
            r42.skipName()
            r42.skipValue()
            goto L_0x03e2
        L_0x0068:
            boolean r32 = r42.nextBoolean()
            goto L_0x03a7
        L_0x006e:
            java.lang.String r6 = r42.nextString()
            goto L_0x03a7
        L_0x0074:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r31 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r11, r7, r3)
            goto L_0x03a7
        L_0x007a:
            double r4 = r42.nextDouble()
            float r5 = (float) r4
            goto L_0x03a7
        L_0x0081:
            double r1 = r42.nextDouble()
            float r1 = (float) r1
            goto L_0x00b2
        L_0x0087:
            int r2 = r42.nextInt()
            float r2 = (float) r2
            float r4 = com.airbnb.lottie.utils.Utils.dpScale()
            float r4 = r4 * r2
            int r2 = (int) r4
            r27 = r2
            goto L_0x00b2
        L_0x0095:
            int r2 = r42.nextInt()
            float r2 = (float) r2
            float r4 = com.airbnb.lottie.utils.Utils.dpScale()
            float r4 = r4 * r2
            int r2 = (int) r4
            r26 = r2
            goto L_0x00b2
        L_0x00a3:
            double r3 = r42.nextDouble()
            float r2 = (float) r3
            r25 = r2
            goto L_0x00b2
        L_0x00ab:
            double r2 = r42.nextDouble()
            float r2 = (float) r2
            r24 = r2
        L_0x00b2:
            r39 = r6
            r40 = r14
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0396
        L_0x00bb:
            r42.beginArray()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L_0x00c3:
            boolean r3 = r42.hasNext()
            if (r3 == 0) goto L_0x00ed
            r42.beginObject()
        L_0x00cc:
            boolean r3 = r42.hasNext()
            if (r3 == 0) goto L_0x00e9
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = EFFECTS_NAMES
            int r3 = r11.selectName(r3)
            if (r3 == 0) goto L_0x00e1
            r42.skipName()
            r42.skipValue()
            goto L_0x00cc
        L_0x00e1:
            java.lang.String r3 = r42.nextString()
            r2.add(r3)
            goto L_0x00cc
        L_0x00e9:
            r42.endObject()
            goto L_0x00c3
        L_0x00ed:
            r42.endArray()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r7.addWarning(r2)
            r39 = r6
            r40 = r14
            goto L_0x03e2
        L_0x010a:
            r42.beginObject()
        L_0x010d:
            boolean r3 = r42.hasNext()
            if (r3 == 0) goto L_0x020c
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = TEXT_NAMES
            int r3 = r11.selectName(r3)
            if (r3 == 0) goto L_0x01f1
            if (r3 == r2) goto L_0x0124
            r42.skipName()
            r42.skipValue()
            goto L_0x010d
        L_0x0124:
            r42.beginArray()
            boolean r3 = r42.hasNext()
            if (r3 == 0) goto L_0x01dc
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.AnimatableTextPropertiesParser.PROPERTIES_NAMES
            r42.beginObject()
            r3 = 0
        L_0x0133:
            boolean r4 = r42.hasNext()
            if (r4 == 0) goto L_0x01c6
            com.airbnb.lottie.parser.moshi.JsonReader$Options r4 = com.airbnb.lottie.parser.AnimatableTextPropertiesParser.PROPERTIES_NAMES
            int r4 = r11.selectName(r4)
            if (r4 == 0) goto L_0x0148
            r42.skipName()
            r42.skipValue()
            goto L_0x0133
        L_0x0148:
            r42.beginObject()
            r3 = 0
            r4 = 0
            r37 = 0
            r38 = 0
        L_0x0151:
            boolean r29 = r42.hasNext()
            if (r29 == 0) goto L_0x01ae
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.AnimatableTextPropertiesParser.ANIMATABLE_PROPERTIES_NAMES
            int r2 = r11.selectName(r2)
            if (r2 == 0) goto L_0x0195
            r39 = r6
            r6 = 1
            if (r2 == r6) goto L_0x0182
            r6 = 2
            if (r2 == r6) goto L_0x0179
            r6 = 3
            if (r2 == r6) goto L_0x0173
            r42.skipName()
            r42.skipValue()
            r40 = r14
            goto L_0x01a8
        L_0x0173:
            r2 = 1
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r4 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r11, r7, r2)
            goto L_0x017f
        L_0x0179:
            r2 = 1
            r6 = 3
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r3 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r11, r7, r2)
        L_0x017f:
            r6 = r39
            goto L_0x01ac
        L_0x0182:
            r6 = 3
            com.airbnb.lottie.model.animatable.AnimatableColorValue r2 = new com.airbnb.lottie.model.animatable.AnimatableColorValue
            com.airbnb.lottie.parser.ColorParser r6 = com.airbnb.lottie.parser.ColorParser.INSTANCE
            r40 = r14
            r14 = 1065353216(0x3f800000, float:1.0)
            java.util.ArrayList r6 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r7, r14, r6)
            r2.<init>(r6)
            r38 = r2
            goto L_0x01a8
        L_0x0195:
            r39 = r6
            r40 = r14
            r14 = 1065353216(0x3f800000, float:1.0)
            com.airbnb.lottie.model.animatable.AnimatableColorValue r2 = new com.airbnb.lottie.model.animatable.AnimatableColorValue
            com.airbnb.lottie.parser.ColorParser r6 = com.airbnb.lottie.parser.ColorParser.INSTANCE
            java.util.ArrayList r6 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r7, r14, r6)
            r2.<init>(r6)
            r37 = r2
        L_0x01a8:
            r6 = r39
            r14 = r40
        L_0x01ac:
            r2 = 1
            goto L_0x0151
        L_0x01ae:
            r39 = r6
            r40 = r14
            r42.endObject()
            com.airbnb.lottie.model.animatable.AnimatableTextProperties r2 = new com.airbnb.lottie.model.animatable.AnimatableTextProperties
            r6 = r37
            r14 = r38
            r2.<init>(r6, r14, r3, r4)
            r3 = r2
            r6 = r39
            r14 = r40
            r2 = 1
            goto L_0x0133
        L_0x01c6:
            r39 = r6
            r40 = r14
            r42.endObject()
            if (r3 != 0) goto L_0x01d8
            com.airbnb.lottie.model.animatable.AnimatableTextProperties r2 = new com.airbnb.lottie.model.animatable.AnimatableTextProperties
            r4 = 0
            r2.<init>(r4, r4, r4, r4)
            r29 = r2
            goto L_0x01e1
        L_0x01d8:
            r4 = 0
            r29 = r3
            goto L_0x01e1
        L_0x01dc:
            r39 = r6
            r40 = r14
            r4 = 0
        L_0x01e1:
            boolean r2 = r42.hasNext()
            if (r2 == 0) goto L_0x01eb
            r42.skipValue()
            goto L_0x01e1
        L_0x01eb:
            r42.endArray()
            r6 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0205
        L_0x01f1:
            r39 = r6
            r40 = r14
            r4 = 0
            com.airbnb.lottie.model.animatable.AnimatableTextFrame r2 = new com.airbnb.lottie.model.animatable.AnimatableTextFrame
            com.airbnb.lottie.parser.DocumentDataParser r3 = com.airbnb.lottie.parser.DocumentDataParser.INSTANCE
            r6 = 1065353216(0x3f800000, float:1.0)
            java.util.ArrayList r3 = com.airbnb.lottie.parser.KeyframesParser.parse(r11, r7, r6, r3)
            r2.<init>(r3)
            r28 = r2
        L_0x0205:
            r6 = r39
            r14 = r40
            r2 = 1
            goto L_0x010d
        L_0x020c:
            r39 = r6
            r40 = r14
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            r42.endObject()
            goto L_0x0396
        L_0x0218:
            r39 = r6
            r40 = r14
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            r42.beginArray()
        L_0x0222:
            boolean r2 = r42.hasNext()
            if (r2 == 0) goto L_0x0232
            com.airbnb.lottie.model.content.ContentModel r2 = com.airbnb.lottie.parser.ContentModelParser.parse(r42, r43)
            if (r2 == 0) goto L_0x0222
            r8.add(r2)
            goto L_0x0222
        L_0x0232:
            r42.endArray()
            goto L_0x03e2
        L_0x0237:
            r39 = r6
            r40 = r14
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            r42.beginArray()
        L_0x0241:
            boolean r2 = r42.hasNext()
            if (r2 == 0) goto L_0x0334
            com.airbnb.lottie.model.content.Mask$MaskMode r2 = com.airbnb.lottie.model.content.Mask.MaskMode.MASK_MODE_ADD
            r42.beginObject()
            r3 = r4
            r11 = r3
            r14 = r11
            r15 = 0
        L_0x0250:
            boolean r34 = r42.hasNext()
            if (r34 == 0) goto L_0x0324
            java.lang.String r4 = r42.nextName()
            java.util.Objects.requireNonNull(r4)
            int r35 = r4.hashCode()
            r37 = -1
            switch(r35) {
                case 111: goto L_0x028a;
                case 3588: goto L_0x027f;
                case 104433: goto L_0x0274;
                case 3357091: goto L_0x0269;
                default: goto L_0x0266;
            }
        L_0x0266:
            r6 = r37
            goto L_0x0294
        L_0x0269:
            java.lang.String r6 = "mode"
            boolean r6 = r4.equals(r6)
            if (r6 != 0) goto L_0x0272
            goto L_0x0266
        L_0x0272:
            r6 = 3
            goto L_0x0294
        L_0x0274:
            java.lang.String r6 = "inv"
            boolean r6 = r4.equals(r6)
            if (r6 != 0) goto L_0x027d
            goto L_0x0266
        L_0x027d:
            r6 = 2
            goto L_0x0294
        L_0x027f:
            java.lang.String r6 = "pt"
            boolean r6 = r4.equals(r6)
            if (r6 != 0) goto L_0x0288
            goto L_0x0266
        L_0x0288:
            r6 = 1
            goto L_0x0294
        L_0x028a:
            java.lang.String r6 = "o"
            boolean r6 = r4.equals(r6)
            if (r6 != 0) goto L_0x0293
            goto L_0x0266
        L_0x0293:
            r6 = 0
        L_0x0294:
            switch(r6) {
                case 0: goto L_0x031b;
                case 1: goto L_0x030b;
                case 2: goto L_0x0306;
                case 3: goto L_0x029c;
                default: goto L_0x0297;
            }
        L_0x0297:
            r42.skipValue()
            goto L_0x031f
        L_0x029c:
            java.lang.String r3 = r42.nextString()
            java.util.Objects.requireNonNull(r3)
            int r6 = r3.hashCode()
            switch(r6) {
                case 97: goto L_0x02cf;
                case 105: goto L_0x02c4;
                case 110: goto L_0x02b9;
                case 115: goto L_0x02ad;
                default: goto L_0x02aa;
            }
        L_0x02aa:
            r6 = r37
            goto L_0x02d9
        L_0x02ad:
            java.lang.String r6 = "s"
            boolean r3 = r3.equals(r6)
            if (r3 != 0) goto L_0x02b7
            goto L_0x02aa
        L_0x02b7:
            r6 = 3
            goto L_0x02d9
        L_0x02b9:
            java.lang.String r6 = "n"
            boolean r3 = r3.equals(r6)
            if (r3 != 0) goto L_0x02c2
            goto L_0x02aa
        L_0x02c2:
            r6 = 2
            goto L_0x02d9
        L_0x02c4:
            java.lang.String r6 = "i"
            boolean r3 = r3.equals(r6)
            if (r3 != 0) goto L_0x02cd
            goto L_0x02aa
        L_0x02cd:
            r6 = 1
            goto L_0x02d9
        L_0x02cf:
            java.lang.String r6 = "a"
            boolean r3 = r3.equals(r6)
            if (r3 != 0) goto L_0x02d8
            goto L_0x02aa
        L_0x02d8:
            r6 = 0
        L_0x02d9:
            switch(r6) {
                case 0: goto L_0x0304;
                case 1: goto L_0x02fc;
                case 2: goto L_0x02f9;
                case 3: goto L_0x02f6;
                default: goto L_0x02dc;
            }
        L_0x02dc:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "Unknown mask mode "
            r3.append(r6)
            r3.append(r4)
            java.lang.String r4 = ". Defaulting to Add."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.airbnb.lottie.utils.Logger.warning(r3)
            goto L_0x0304
        L_0x02f6:
            com.airbnb.lottie.model.content.Mask$MaskMode r3 = com.airbnb.lottie.model.content.Mask.MaskMode.MASK_MODE_SUBTRACT
            goto L_0x031f
        L_0x02f9:
            com.airbnb.lottie.model.content.Mask$MaskMode r3 = com.airbnb.lottie.model.content.Mask.MaskMode.MASK_MODE_NONE
            goto L_0x031f
        L_0x02fc:
            java.lang.String r3 = "Animation contains intersect masks. They are not supported but will be treated like add masks."
            r7.addWarning(r3)
            com.airbnb.lottie.model.content.Mask$MaskMode r3 = com.airbnb.lottie.model.content.Mask.MaskMode.MASK_MODE_INTERSECT
            goto L_0x031f
        L_0x0304:
            r3 = r2
            goto L_0x031f
        L_0x0306:
            boolean r15 = r42.nextBoolean()
            goto L_0x031f
        L_0x030b:
            com.airbnb.lottie.model.animatable.AnimatableShapeValue r11 = new com.airbnb.lottie.model.animatable.AnimatableShapeValue
            float r4 = com.airbnb.lottie.utils.Utils.dpScale()
            com.airbnb.lottie.parser.ShapeDataParser r6 = com.airbnb.lottie.parser.ShapeDataParser.INSTANCE
            java.util.ArrayList r4 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r7, r4, r6)
            r11.<init>(r4)
            goto L_0x031f
        L_0x031b:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r14 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r42, r43)
        L_0x031f:
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0250
        L_0x0324:
            r42.endObject()
            com.airbnb.lottie.model.content.Mask r2 = new com.airbnb.lottie.model.content.Mask
            r2.<init>(r3, r11, r14, r15)
            r10.add(r2)
            r4 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0241
        L_0x0334:
            int r2 = r10.size()
            int r3 = r7.maskAndMatteCount
            int r3 = r3 + r2
            r7.maskAndMatteCount = r3
            r42.endArray()
            goto L_0x03e2
        L_0x0342:
            r39 = r6
            r40 = r14
            com.airbnb.lottie.model.layer.Layer$MatteType[] r2 = com.airbnb.lottie.model.layer.Layer.MatteType.values()
            int r3 = r42.nextInt()
            r30 = r2[r3]
            int r2 = r7.maskAndMatteCount
            r3 = 1
            int r2 = r2 + r3
            r7.maskAndMatteCount = r2
            r14 = r40
            goto L_0x03de
        L_0x035a:
            r39 = r6
            r40 = r14
            com.airbnb.lottie.model.animatable.AnimatableTransform r20 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r42, r43)
            goto L_0x03de
        L_0x0364:
            r39 = r6
            r40 = r14
            java.lang.String r2 = r42.nextString()
            int r23 = android.graphics.Color.parseColor(r2)
            goto L_0x03de
        L_0x0372:
            r39 = r6
            r40 = r14
            int r2 = r42.nextInt()
            float r2 = (float) r2
            float r3 = com.airbnb.lottie.utils.Utils.dpScale()
            float r3 = r3 * r2
            int r2 = (int) r3
            r22 = r2
            goto L_0x0395
        L_0x0384:
            r39 = r6
            r40 = r14
            int r2 = r42.nextInt()
            float r2 = (float) r2
            float r3 = com.airbnb.lottie.utils.Utils.dpScale()
            float r3 = r3 * r2
            int r2 = (int) r3
            r21 = r2
        L_0x0395:
            r11 = r0
        L_0x0396:
            r6 = r39
            r14 = r40
            goto L_0x03a7
        L_0x039b:
            r39 = r6
            r40 = r14
            int r2 = r42.nextInt()
            long r2 = (long) r2
            r11 = r0
            r17 = r2
        L_0x03a7:
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 0
            goto L_0x004c
        L_0x03ac:
            r39 = r6
            r40 = r14
            int r2 = r42.nextInt()
            com.airbnb.lottie.model.layer.Layer$LayerType r3 = com.airbnb.lottie.model.layer.Layer.LayerType.UNKNOWN
            r4 = 6
            if (r2 >= r4) goto L_0x03c2
            com.airbnb.lottie.model.layer.Layer$LayerType[] r3 = com.airbnb.lottie.model.layer.Layer.LayerType.values()
            r2 = r3[r2]
            r16 = r2
            goto L_0x03e2
        L_0x03c2:
            r16 = r3
            goto L_0x03e2
        L_0x03c5:
            r39 = r6
            r40 = r14
            java.lang.String r19 = r42.nextString()
            goto L_0x03de
        L_0x03ce:
            r39 = r6
            int r2 = r42.nextInt()
            long r14 = (long) r2
            goto L_0x03de
        L_0x03d6:
            r39 = r6
            r40 = r14
            java.lang.String r13 = r42.nextString()
        L_0x03de:
            r11 = r0
            r6 = r39
            goto L_0x03a7
        L_0x03e2:
            r11 = r0
            goto L_0x0396
        L_0x03e4:
            r39 = r6
            r40 = r14
            r42.endObject()
            float r11 = r1 / r24
            float r14 = r5 / r24
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            r0 = 0
            int r1 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x0416
            com.airbnb.lottie.value.Keyframe r6 = new com.airbnb.lottie.value.Keyframe
            r4 = 0
            r5 = 0
            java.lang.Float r34 = java.lang.Float.valueOf(r11)
            r0 = r6
            r1 = r43
            r2 = r12
            r3 = r12
            r36 = r8
            r35 = r10
            r10 = r39
            r8 = r6
            r6 = r34
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r15.add(r8)
            goto L_0x041c
        L_0x0416:
            r36 = r8
            r35 = r10
            r10 = r39
        L_0x041c:
            r0 = 0
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0422
            goto L_0x0425
        L_0x0422:
            float r0 = r7.endFrame
            r14 = r0
        L_0x0425:
            com.airbnb.lottie.value.Keyframe r8 = new com.airbnb.lottie.value.Keyframe
            r33 = 0
            java.lang.Float r6 = java.lang.Float.valueOf(r14)
            r4 = 0
            r0 = r8
            r1 = r43
            r2 = r9
            r3 = r9
            r5 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r15.add(r8)
            com.airbnb.lottie.value.Keyframe r8 = new com.airbnb.lottie.value.Keyframe
            r0 = 2139095039(0x7f7fffff, float:3.4028235E38)
            java.lang.Float r6 = java.lang.Float.valueOf(r0)
            r0 = r8
            r2 = r12
            r3 = r12
            r4 = r33
            r5 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r15.add(r8)
            java.lang.String r0 = ".ai"
            boolean r0 = r13.endsWith(r0)
            if (r0 != 0) goto L_0x045f
            java.lang.String r0 = "ai"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0464
        L_0x045f:
            java.lang.String r0 = "Convert your Illustrator layers to shape layers."
            r7.addWarning(r0)
        L_0x0464:
            com.airbnb.lottie.model.layer.Layer r33 = new com.airbnb.lottie.model.layer.Layer
            r0 = r33
            r1 = r36
            r2 = r43
            r3 = r13
            r4 = r40
            r6 = r16
            r7 = r17
            r9 = r19
            r10 = r35
            r11 = r20
            r12 = r21
            r13 = r22
            r14 = r23
            r21 = r15
            r15 = r24
            r16 = r25
            r17 = r26
            r18 = r27
            r19 = r28
            r20 = r29
            r22 = r30
            r23 = r31
            r24 = r32
            r0.<init>(r1, r2, r3, r4, r6, r7, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return r33
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.LayerParser.parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.layer.Layer");
    }
}
