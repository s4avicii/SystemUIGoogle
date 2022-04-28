package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class ContentModelParser {
    public static JsonReader.Options NAMES = JsonReader.Options.m22of("ty", "d");

    /* JADX WARNING: type inference failed for: r1v1, types: [com.airbnb.lottie.model.content.ContentModel] */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r12v5, types: [com.airbnb.lottie.model.content.CircleShape] */
    /* JADX WARNING: type inference failed for: r16v23, types: [com.airbnb.lottie.model.content.ShapeFill] */
    /* JADX WARNING: type inference failed for: r16v24, types: [com.airbnb.lottie.model.content.GradientFill] */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r17v32, types: [com.airbnb.lottie.model.content.GradientStroke] */
    /* JADX WARNING: type inference failed for: r12v6, types: [com.airbnb.lottie.model.content.RectangleShape] */
    /* JADX WARNING: type inference failed for: r12v7, types: [com.airbnb.lottie.model.content.Repeater] */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: type inference failed for: r13v15, types: [com.airbnb.lottie.model.content.PolystarShape] */
    /* JADX WARNING: type inference failed for: r17v33, types: [com.airbnb.lottie.model.content.ShapeStroke] */
    /* JADX WARNING: type inference failed for: r13v16, types: [com.airbnb.lottie.model.content.ShapeTrimPath] */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0214 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.content.ContentModel parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader r31, com.airbnb.lottie.LottieComposition r32) throws java.io.IOException {
        /*
            r0 = r31
            r1 = r32
            com.airbnb.lottie.model.content.GradientType r2 = com.airbnb.lottie.model.content.GradientType.LINEAR
            com.airbnb.lottie.model.content.GradientType r3 = com.airbnb.lottie.model.content.GradientType.RADIAL
            r31.beginObject()
            r4 = 2
            r5 = r4
        L_0x000d:
            boolean r6 = r31.hasNext()
            r7 = 1
            r8 = 0
            if (r6 == 0) goto L_0x0030
            com.airbnb.lottie.parser.moshi.JsonReader$Options r6 = NAMES
            int r6 = r0.selectName(r6)
            if (r6 == 0) goto L_0x002b
            if (r6 == r7) goto L_0x0026
            r31.skipName()
            r31.skipValue()
            goto L_0x000d
        L_0x0026:
            int r5 = r31.nextInt()
            goto L_0x000d
        L_0x002b:
            java.lang.String r6 = r31.nextString()
            goto L_0x0031
        L_0x0030:
            r6 = r8
        L_0x0031:
            if (r6 != 0) goto L_0x0034
            return r8
        L_0x0034:
            int r8 = r6.hashCode()
            r9 = 5
            r10 = 4
            r11 = 3
            switch(r8) {
                case 3239: goto L_0x00db;
                case 3270: goto L_0x00d0;
                case 3295: goto L_0x00c5;
                case 3307: goto L_0x00ba;
                case 3308: goto L_0x00af;
                case 3488: goto L_0x00a4;
                case 3633: goto L_0x0098;
                case 3646: goto L_0x008b;
                case 3669: goto L_0x007c;
                case 3679: goto L_0x006d;
                case 3681: goto L_0x005e;
                case 3705: goto L_0x004f;
                case 3710: goto L_0x0040;
                default: goto L_0x003e;
            }
        L_0x003e:
            goto L_0x00e6
        L_0x0040:
            java.lang.String r8 = "tr"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x004b
            goto L_0x00e6
        L_0x004b:
            r8 = 12
            goto L_0x00e7
        L_0x004f:
            java.lang.String r8 = "tm"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x005a
            goto L_0x00e6
        L_0x005a:
            r8 = 11
            goto L_0x00e7
        L_0x005e:
            java.lang.String r8 = "st"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0069
            goto L_0x00e6
        L_0x0069:
            r8 = 10
            goto L_0x00e7
        L_0x006d:
            java.lang.String r8 = "sr"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0078
            goto L_0x00e6
        L_0x0078:
            r8 = 9
            goto L_0x00e7
        L_0x007c:
            java.lang.String r8 = "sh"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0087
            goto L_0x00e6
        L_0x0087:
            r8 = 8
            goto L_0x00e7
        L_0x008b:
            java.lang.String r8 = "rp"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0096
            goto L_0x00e6
        L_0x0096:
            r8 = 7
            goto L_0x00e7
        L_0x0098:
            java.lang.String r8 = "rc"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00a2
            goto L_0x00e6
        L_0x00a2:
            r8 = 6
            goto L_0x00e7
        L_0x00a4:
            java.lang.String r8 = "mm"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00ad
            goto L_0x00e6
        L_0x00ad:
            r8 = r9
            goto L_0x00e7
        L_0x00af:
            java.lang.String r8 = "gs"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00b8
            goto L_0x00e6
        L_0x00b8:
            r8 = r10
            goto L_0x00e7
        L_0x00ba:
            java.lang.String r8 = "gr"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00c3
            goto L_0x00e6
        L_0x00c3:
            r8 = r11
            goto L_0x00e7
        L_0x00c5:
            java.lang.String r8 = "gf"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00ce
            goto L_0x00e6
        L_0x00ce:
            r8 = r4
            goto L_0x00e7
        L_0x00d0:
            java.lang.String r8 = "fl"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00d9
            goto L_0x00e6
        L_0x00d9:
            r8 = r7
            goto L_0x00e7
        L_0x00db:
            java.lang.String r8 = "el"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00e4
            goto L_0x00e6
        L_0x00e4:
            r8 = 0
            goto L_0x00e7
        L_0x00e6:
            r8 = -1
        L_0x00e7:
            java.lang.String r12 = "o"
            java.lang.String r13 = "g"
            java.lang.String r14 = "d"
            r15 = 1065353216(0x3f800000, float:1.0)
            switch(r8) {
                case 0: goto L_0x06b7;
                case 1: goto L_0x064e;
                case 2: goto L_0x05ac;
                case 3: goto L_0x0565;
                case 4: goto L_0x042c;
                case 5: goto L_0x03d8;
                case 6: goto L_0x038f;
                case 7: goto L_0x0344;
                case 8: goto L_0x0300;
                case 9: goto L_0x028a;
                case 10: goto L_0x017c;
                case 11: goto L_0x010f;
                case 12: goto L_0x0109;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unknown shape type "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.airbnb.lottie.utils.Logger.warning(r1)
            r1 = 0
            goto L_0x070c
        L_0x0109:
            com.airbnb.lottie.model.animatable.AnimatableTransform r1 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r31, r32)
            goto L_0x070c
        L_0x010f:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.ShapeTrimPathParser.NAMES
            r2 = 0
            r3 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r12 = 0
            r14 = r2
            r15 = r3
            r16 = r5
            r17 = r6
            r18 = r8
            r19 = r12
        L_0x0121:
            boolean r2 = r31.hasNext()
            if (r2 == 0) goto L_0x0174
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.ShapeTrimPathParser.NAMES
            int r2 = r0.selectName(r2)
            if (r2 == 0) goto L_0x016e
            if (r2 == r7) goto L_0x0168
            if (r2 == r4) goto L_0x0162
            if (r2 == r11) goto L_0x015d
            if (r2 == r10) goto L_0x0142
            if (r2 == r9) goto L_0x013d
            r31.skipValue()
            goto L_0x0121
        L_0x013d:
            boolean r19 = r31.nextBoolean()
            goto L_0x0121
        L_0x0142:
            int r2 = r31.nextInt()
            if (r2 == r7) goto L_0x0159
            if (r2 != r4) goto L_0x014d
            com.airbnb.lottie.model.content.ShapeTrimPath$Type r2 = com.airbnb.lottie.model.content.ShapeTrimPath.Type.INDIVIDUALLY
            goto L_0x015b
        L_0x014d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Unknown trim path type "
            java.lang.String r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r1, r2)
            r0.<init>(r1)
            throw r0
        L_0x0159:
            com.airbnb.lottie.model.content.ShapeTrimPath$Type r2 = com.airbnb.lottie.model.content.ShapeTrimPath.Type.SIMULTANEOUSLY
        L_0x015b:
            r15 = r2
            goto L_0x0121
        L_0x015d:
            java.lang.String r14 = r31.nextString()
            goto L_0x0121
        L_0x0162:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r18 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x0121
        L_0x0168:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r17 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x0121
        L_0x016e:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r16 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x0121
        L_0x0174:
            com.airbnb.lottie.model.content.ShapeTrimPath r1 = new com.airbnb.lottie.model.content.ShapeTrimPath
            r13 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19)
            goto L_0x070c
        L_0x017c:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.ShapeStrokeParser.NAMES
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r17 = 0
            r18 = r3
            r19 = r5
            r21 = r6
            r22 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r27 = r17
            r26 = 0
        L_0x019e:
            boolean r3 = r31.hasNext()
            if (r3 == 0) goto L_0x027f
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.ShapeStrokeParser.NAMES
            int r3 = r0.selectName(r3)
            switch(r3) {
                case 0: goto L_0x0279;
                case 1: goto L_0x026a;
                case 2: goto L_0x0264;
                case 3: goto L_0x025e;
                case 4: goto L_0x0251;
                case 5: goto L_0x0244;
                case 6: goto L_0x023b;
                case 7: goto L_0x0235;
                case 8: goto L_0x01b1;
                default: goto L_0x01ad;
            }
        L_0x01ad:
            r31.skipValue()
            goto L_0x019e
        L_0x01b1:
            r31.beginArray()
        L_0x01b4:
            boolean r3 = r31.hasNext()
            if (r3 == 0) goto L_0x0222
            r31.beginObject()
            r3 = 0
            r5 = 0
        L_0x01bf:
            boolean r6 = r31.hasNext()
            if (r6 == 0) goto L_0x01e0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r6 = com.airbnb.lottie.parser.ShapeStrokeParser.DASH_PATTERN_NAMES
            int r6 = r0.selectName(r6)
            if (r6 == 0) goto L_0x01db
            if (r6 == r7) goto L_0x01d6
            r31.skipName()
            r31.skipValue()
            goto L_0x01bf
        L_0x01d6:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r3 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x01bf
        L_0x01db:
            java.lang.String r5 = r31.nextString()
            goto L_0x01bf
        L_0x01e0:
            r31.endObject()
            java.util.Objects.requireNonNull(r5)
            int r6 = r5.hashCode()
            r8 = 100
            if (r6 == r8) goto L_0x0209
            r8 = 103(0x67, float:1.44E-43)
            if (r6 == r8) goto L_0x0200
            r8 = 111(0x6f, float:1.56E-43)
            if (r6 == r8) goto L_0x01f7
            goto L_0x020f
        L_0x01f7:
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x01fe
            goto L_0x020f
        L_0x01fe:
            r5 = r4
            goto L_0x0212
        L_0x0200:
            boolean r5 = r5.equals(r13)
            if (r5 != 0) goto L_0x0207
            goto L_0x020f
        L_0x0207:
            r5 = r7
            goto L_0x0212
        L_0x0209:
            boolean r5 = r5.equals(r14)
            if (r5 != 0) goto L_0x0211
        L_0x020f:
            r5 = -1
            goto L_0x0212
        L_0x0211:
            r5 = 0
        L_0x0212:
            if (r5 == 0) goto L_0x021c
            if (r5 == r7) goto L_0x021c
            if (r5 == r4) goto L_0x0219
            goto L_0x01b4
        L_0x0219:
            r19 = r3
            goto L_0x01b4
        L_0x021c:
            r1.hasDashPattern = r7
            r2.add(r3)
            goto L_0x01b4
        L_0x0222:
            r31.endArray()
            int r3 = r2.size()
            if (r3 != r7) goto L_0x019e
            r3 = 0
            java.lang.Object r3 = r2.get(r3)
            r2.add(r3)
            goto L_0x019e
        L_0x0235:
            boolean r27 = r31.nextBoolean()
            goto L_0x019e
        L_0x023b:
            double r5 = r31.nextDouble()
            float r3 = (float) r5
            r26 = r3
            goto L_0x019e
        L_0x0244:
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType[] r3 = com.airbnb.lottie.model.content.ShapeStroke.LineJoinType.values()
            int r5 = r31.nextInt()
            int r5 = r5 - r7
            r25 = r3[r5]
            goto L_0x019e
        L_0x0251:
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType[] r3 = com.airbnb.lottie.model.content.ShapeStroke.LineCapType.values()
            int r5 = r31.nextInt()
            int r5 = r5 - r7
            r24 = r3[r5]
            goto L_0x019e
        L_0x025e:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r22 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r31, r32)
            goto L_0x019e
        L_0x0264:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r23 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x019e
        L_0x026a:
            com.airbnb.lottie.model.animatable.AnimatableColorValue r3 = new com.airbnb.lottie.model.animatable.AnimatableColorValue
            com.airbnb.lottie.parser.ColorParser r5 = com.airbnb.lottie.parser.ColorParser.INSTANCE
            java.util.ArrayList r5 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r1, r15, r5)
            r3.<init>(r5)
            r21 = r3
            goto L_0x019e
        L_0x0279:
            java.lang.String r18 = r31.nextString()
            goto L_0x019e
        L_0x027f:
            com.airbnb.lottie.model.content.ShapeStroke r1 = new com.airbnb.lottie.model.content.ShapeStroke
            r17 = r1
            r20 = r2
            r17.<init>(r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            goto L_0x070c
        L_0x028a:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.PolystarShapeParser.NAMES
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r14 = r2
            r15 = r3
            r16 = r4
            r17 = r5
            r18 = r6
            r19 = r8
            r20 = r9
            r21 = r10
            r22 = r11
            r23 = r12
        L_0x02a8:
            boolean r2 = r31.hasNext()
            if (r2 == 0) goto L_0x02f8
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.PolystarShapeParser.NAMES
            int r2 = r0.selectName(r2)
            switch(r2) {
                case 0: goto L_0x02f3;
                case 1: goto L_0x02ea;
                case 2: goto L_0x02e4;
                case 3: goto L_0x02df;
                case 4: goto L_0x02d9;
                case 5: goto L_0x02d4;
                case 6: goto L_0x02ce;
                case 7: goto L_0x02c9;
                case 8: goto L_0x02c3;
                case 9: goto L_0x02be;
                default: goto L_0x02b7;
            }
        L_0x02b7:
            r31.skipName()
            r31.skipValue()
            goto L_0x02a8
        L_0x02be:
            boolean r23 = r31.nextBoolean()
            goto L_0x02a8
        L_0x02c3:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r21 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x02a8
        L_0x02c9:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r19 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x02a8
        L_0x02ce:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r22 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x02a8
        L_0x02d4:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r20 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x02a8
        L_0x02d9:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r18 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x02a8
        L_0x02df:
            com.airbnb.lottie.model.animatable.AnimatableValue r17 = com.airbnb.lottie.parser.AnimatablePathValueParser.parseSplitPath(r31, r32)
            goto L_0x02a8
        L_0x02e4:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r16 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x02a8
        L_0x02ea:
            int r2 = r31.nextInt()
            com.airbnb.lottie.model.content.PolystarShape$Type r15 = com.airbnb.lottie.model.content.PolystarShape.Type.forValue(r2)
            goto L_0x02a8
        L_0x02f3:
            java.lang.String r14 = r31.nextString()
            goto L_0x02a8
        L_0x02f8:
            com.airbnb.lottie.model.content.PolystarShape r1 = new com.airbnb.lottie.model.content.PolystarShape
            r13 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            goto L_0x070c
        L_0x0300:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.ShapePathParser.NAMES
            r2 = 0
            r3 = 0
            r5 = 0
            r6 = 0
        L_0x0306:
            boolean r8 = r31.hasNext()
            if (r8 == 0) goto L_0x033d
            com.airbnb.lottie.parser.moshi.JsonReader$Options r8 = com.airbnb.lottie.parser.ShapePathParser.NAMES
            int r8 = r0.selectName(r8)
            if (r8 == 0) goto L_0x0338
            if (r8 == r7) goto L_0x0333
            if (r8 == r4) goto L_0x0323
            if (r8 == r11) goto L_0x031e
            r31.skipValue()
            goto L_0x0306
        L_0x031e:
            boolean r3 = r31.nextBoolean()
            goto L_0x0306
        L_0x0323:
            com.airbnb.lottie.model.animatable.AnimatableShapeValue r2 = new com.airbnb.lottie.model.animatable.AnimatableShapeValue
            float r8 = com.airbnb.lottie.utils.Utils.dpScale()
            com.airbnb.lottie.parser.ShapeDataParser r9 = com.airbnb.lottie.parser.ShapeDataParser.INSTANCE
            java.util.ArrayList r8 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r1, r8, r9)
            r2.<init>(r8)
            goto L_0x0306
        L_0x0333:
            int r6 = r31.nextInt()
            goto L_0x0306
        L_0x0338:
            java.lang.String r5 = r31.nextString()
            goto L_0x0306
        L_0x033d:
            com.airbnb.lottie.model.content.ShapePath r1 = new com.airbnb.lottie.model.content.ShapePath
            r1.<init>(r5, r6, r2, r3)
            goto L_0x070c
        L_0x0344:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.RepeaterParser.NAMES
            r2 = 0
            r3 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r13 = r2
            r14 = r3
            r15 = r5
            r16 = r6
            r17 = r8
        L_0x0352:
            boolean r2 = r31.hasNext()
            if (r2 == 0) goto L_0x0387
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.RepeaterParser.NAMES
            int r2 = r0.selectName(r2)
            if (r2 == 0) goto L_0x0382
            if (r2 == r7) goto L_0x037c
            if (r2 == r4) goto L_0x0376
            if (r2 == r11) goto L_0x0371
            if (r2 == r10) goto L_0x036c
            r31.skipValue()
            goto L_0x0352
        L_0x036c:
            boolean r17 = r31.nextBoolean()
            goto L_0x0352
        L_0x0371:
            com.airbnb.lottie.model.animatable.AnimatableTransform r16 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r31, r32)
            goto L_0x0352
        L_0x0376:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r15 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x0352
        L_0x037c:
            r2 = 0
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r14 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r2)
            goto L_0x0352
        L_0x0382:
            java.lang.String r13 = r31.nextString()
            goto L_0x0352
        L_0x0387:
            com.airbnb.lottie.model.content.Repeater r1 = new com.airbnb.lottie.model.content.Repeater
            r12 = r1
            r12.<init>(r13, r14, r15, r16, r17)
            goto L_0x070c
        L_0x038f:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.RectangleShapeParser.NAMES
            r2 = 0
            r3 = 0
            r5 = 0
            r6 = 0
            r8 = 0
            r13 = r2
            r14 = r3
            r15 = r5
            r16 = r6
            r17 = r8
        L_0x039d:
            boolean r2 = r31.hasNext()
            if (r2 == 0) goto L_0x03d0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.RectangleShapeParser.NAMES
            int r2 = r0.selectName(r2)
            if (r2 == 0) goto L_0x03cb
            if (r2 == r7) goto L_0x03c6
            if (r2 == r4) goto L_0x03c1
            if (r2 == r11) goto L_0x03bc
            if (r2 == r10) goto L_0x03b7
            r31.skipValue()
            goto L_0x039d
        L_0x03b7:
            boolean r17 = r31.nextBoolean()
            goto L_0x039d
        L_0x03bc:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r16 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x039d
        L_0x03c1:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r15 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x039d
        L_0x03c6:
            com.airbnb.lottie.model.animatable.AnimatableValue r14 = com.airbnb.lottie.parser.AnimatablePathValueParser.parseSplitPath(r31, r32)
            goto L_0x039d
        L_0x03cb:
            java.lang.String r13 = r31.nextString()
            goto L_0x039d
        L_0x03d0:
            com.airbnb.lottie.model.content.RectangleShape r1 = new com.airbnb.lottie.model.content.RectangleShape
            r12 = r1
            r12.<init>(r13, r14, r15, r16, r17)
            goto L_0x070c
        L_0x03d8:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r2 = com.airbnb.lottie.parser.MergePathsParser.NAMES
            r2 = 0
            r3 = 0
            r5 = 0
        L_0x03dd:
            boolean r6 = r31.hasNext()
            if (r6 == 0) goto L_0x041f
            com.airbnb.lottie.parser.moshi.JsonReader$Options r6 = com.airbnb.lottie.parser.MergePathsParser.NAMES
            int r6 = r0.selectName(r6)
            if (r6 == 0) goto L_0x041a
            if (r6 == r7) goto L_0x03fb
            if (r6 == r4) goto L_0x03f6
            r31.skipName()
            r31.skipValue()
            goto L_0x03dd
        L_0x03f6:
            boolean r5 = r31.nextBoolean()
            goto L_0x03dd
        L_0x03fb:
            int r2 = r31.nextInt()
            com.airbnb.lottie.model.content.MergePaths$MergePathsMode r6 = com.airbnb.lottie.model.content.MergePaths.MergePathsMode.MERGE
            if (r2 == r7) goto L_0x0418
            if (r2 == r4) goto L_0x0415
            if (r2 == r11) goto L_0x0412
            if (r2 == r10) goto L_0x040f
            if (r2 == r9) goto L_0x040c
            goto L_0x0418
        L_0x040c:
            com.airbnb.lottie.model.content.MergePaths$MergePathsMode r2 = com.airbnb.lottie.model.content.MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS
            goto L_0x03dd
        L_0x040f:
            com.airbnb.lottie.model.content.MergePaths$MergePathsMode r2 = com.airbnb.lottie.model.content.MergePaths.MergePathsMode.INTERSECT
            goto L_0x03dd
        L_0x0412:
            com.airbnb.lottie.model.content.MergePaths$MergePathsMode r2 = com.airbnb.lottie.model.content.MergePaths.MergePathsMode.SUBTRACT
            goto L_0x03dd
        L_0x0415:
            com.airbnb.lottie.model.content.MergePaths$MergePathsMode r2 = com.airbnb.lottie.model.content.MergePaths.MergePathsMode.ADD
            goto L_0x03dd
        L_0x0418:
            r2 = r6
            goto L_0x03dd
        L_0x041a:
            java.lang.String r3 = r31.nextString()
            goto L_0x03dd
        L_0x041f:
            com.airbnb.lottie.model.content.MergePaths r4 = new com.airbnb.lottie.model.content.MergePaths
            r4.<init>(r3, r2, r5)
            java.lang.String r2 = "Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove()."
            r1.addWarning(r2)
            r1 = r4
            goto L_0x070c
        L_0x042c:
            com.airbnb.lottie.parser.moshi.JsonReader$Options r4 = com.airbnb.lottie.parser.GradientStrokeParser.NAMES
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
            r6 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = r10
            r23 = r11
            r24 = r17
            r25 = r18
            r26 = r19
            r29 = r20
            r30 = r21
            r27 = 0
            r18 = r5
            r19 = r6
            r20 = r8
            r21 = r9
        L_0x045b:
            boolean r5 = r31.hasNext()
            if (r5 == 0) goto L_0x055a
            com.airbnb.lottie.parser.moshi.JsonReader$Options r5 = com.airbnb.lottie.parser.GradientStrokeParser.NAMES
            int r5 = r0.selectName(r5)
            switch(r5) {
                case 0: goto L_0x0554;
                case 1: goto L_0x051e;
                case 2: goto L_0x0518;
                case 3: goto L_0x050a;
                case 4: goto L_0x0504;
                case 5: goto L_0x04fe;
                case 6: goto L_0x04f8;
                case 7: goto L_0x04eb;
                case 8: goto L_0x04de;
                case 9: goto L_0x04d5;
                case 10: goto L_0x04d0;
                case 11: goto L_0x0471;
                default: goto L_0x046a;
            }
        L_0x046a:
            r31.skipName()
            r31.skipValue()
            goto L_0x045b
        L_0x0471:
            r31.beginArray()
        L_0x0474:
            boolean r5 = r31.hasNext()
            if (r5 == 0) goto L_0x04be
            r31.beginObject()
            r5 = 0
            r6 = 0
        L_0x047f:
            boolean r8 = r31.hasNext()
            if (r8 == 0) goto L_0x04a0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r8 = com.airbnb.lottie.parser.GradientStrokeParser.DASH_PATTERN_NAMES
            int r8 = r0.selectName(r8)
            if (r8 == 0) goto L_0x049b
            if (r8 == r7) goto L_0x0496
            r31.skipName()
            r31.skipValue()
            goto L_0x047f
        L_0x0496:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x047f
        L_0x049b:
            java.lang.String r6 = r31.nextString()
            goto L_0x047f
        L_0x04a0:
            r31.endObject()
            boolean r8 = r6.equals(r12)
            if (r8 == 0) goto L_0x04ac
            r29 = r5
            goto L_0x0474
        L_0x04ac:
            boolean r8 = r6.equals(r14)
            if (r8 != 0) goto L_0x04b8
            boolean r6 = r6.equals(r13)
            if (r6 == 0) goto L_0x0474
        L_0x04b8:
            r1.hasDashPattern = r7
            r4.add(r5)
            goto L_0x0474
        L_0x04be:
            r31.endArray()
            int r5 = r4.size()
            r6 = 0
            if (r5 != r7) goto L_0x045b
            java.lang.Object r5 = r4.get(r6)
            r4.add(r5)
            goto L_0x045b
        L_0x04d0:
            boolean r30 = r31.nextBoolean()
            goto L_0x045b
        L_0x04d5:
            double r5 = r31.nextDouble()
            float r5 = (float) r5
            r27 = r5
            goto L_0x045b
        L_0x04de:
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType[] r5 = com.airbnb.lottie.model.content.ShapeStroke.LineJoinType.values()
            int r6 = r31.nextInt()
            int r6 = r6 - r7
            r26 = r5[r6]
            goto L_0x045b
        L_0x04eb:
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType[] r5 = com.airbnb.lottie.model.content.ShapeStroke.LineCapType.values()
            int r6 = r31.nextInt()
            int r6 = r6 - r7
            r25 = r5[r6]
            goto L_0x045b
        L_0x04f8:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r24 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r0, r1, r7)
            goto L_0x045b
        L_0x04fe:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r23 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x045b
        L_0x0504:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r22 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x045b
        L_0x050a:
            int r5 = r31.nextInt()
            if (r5 != r7) goto L_0x0514
            r19 = r2
            goto L_0x045b
        L_0x0514:
            r19 = r3
            goto L_0x045b
        L_0x0518:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r21 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r31, r32)
            goto L_0x045b
        L_0x051e:
            r31.beginObject()
            r5 = -1
        L_0x0522:
            boolean r6 = r31.hasNext()
            if (r6 == 0) goto L_0x054f
            com.airbnb.lottie.parser.moshi.JsonReader$Options r6 = com.airbnb.lottie.parser.GradientStrokeParser.GRADIENT_NAMES
            int r6 = r0.selectName(r6)
            if (r6 == 0) goto L_0x054a
            if (r6 == r7) goto L_0x0539
            r31.skipName()
            r31.skipValue()
            goto L_0x0522
        L_0x0539:
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r6 = new com.airbnb.lottie.model.animatable.AnimatableGradientColorValue
            com.airbnb.lottie.parser.GradientColorParser r8 = new com.airbnb.lottie.parser.GradientColorParser
            r8.<init>(r5)
            java.util.ArrayList r8 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r1, r15, r8)
            r6.<init>(r8)
            r20 = r6
            goto L_0x0522
        L_0x054a:
            int r5 = r31.nextInt()
            goto L_0x0522
        L_0x054f:
            r31.endObject()
            goto L_0x045b
        L_0x0554:
            java.lang.String r18 = r31.nextString()
            goto L_0x045b
        L_0x055a:
            com.airbnb.lottie.model.content.GradientStroke r1 = new com.airbnb.lottie.model.content.GradientStroke
            r17 = r1
            r28 = r4
            r17.<init>(r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30)
            goto L_0x070c
        L_0x0565:
            r2 = 0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.ShapeGroupParser.NAMES
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r5 = 0
        L_0x056e:
            boolean r6 = r31.hasNext()
            if (r6 == 0) goto L_0x05a5
            com.airbnb.lottie.parser.moshi.JsonReader$Options r6 = com.airbnb.lottie.parser.ShapeGroupParser.NAMES
            int r6 = r0.selectName(r6)
            if (r6 == 0) goto L_0x05a0
            if (r6 == r7) goto L_0x059b
            if (r6 == r4) goto L_0x0584
            r31.skipValue()
            goto L_0x056e
        L_0x0584:
            r31.beginArray()
        L_0x0587:
            boolean r6 = r31.hasNext()
            if (r6 == 0) goto L_0x0597
            com.airbnb.lottie.model.content.ContentModel r6 = parse(r31, r32)
            if (r6 == 0) goto L_0x0587
            r3.add(r6)
            goto L_0x0587
        L_0x0597:
            r31.endArray()
            goto L_0x056e
        L_0x059b:
            boolean r2 = r31.nextBoolean()
            goto L_0x056e
        L_0x05a0:
            java.lang.String r5 = r31.nextString()
            goto L_0x056e
        L_0x05a5:
            com.airbnb.lottie.model.content.ShapeGroup r1 = new com.airbnb.lottie.model.content.ShapeGroup
            r1.<init>(r5, r3, r2)
            goto L_0x070c
        L_0x05ac:
            r4 = 0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r5 = com.airbnb.lottie.parser.GradientFillParser.NAMES
            android.graphics.Path$FillType r5 = android.graphics.Path.FillType.WINDING
            r6 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r24 = r4
            r19 = r5
            r17 = r6
            r18 = r8
            r20 = r9
            r21 = r10
            r22 = r11
            r23 = r12
        L_0x05c7:
            boolean r4 = r31.hasNext()
            if (r4 == 0) goto L_0x0645
            com.airbnb.lottie.parser.moshi.JsonReader$Options r4 = com.airbnb.lottie.parser.GradientFillParser.NAMES
            int r4 = r0.selectName(r4)
            switch(r4) {
                case 0: goto L_0x0640;
                case 1: goto L_0x060b;
                case 2: goto L_0x0606;
                case 3: goto L_0x05fa;
                case 4: goto L_0x05f5;
                case 5: goto L_0x05f0;
                case 6: goto L_0x05e2;
                case 7: goto L_0x05dd;
                default: goto L_0x05d6;
            }
        L_0x05d6:
            r31.skipName()
            r31.skipValue()
            goto L_0x05c7
        L_0x05dd:
            boolean r24 = r31.nextBoolean()
            goto L_0x05c7
        L_0x05e2:
            int r4 = r31.nextInt()
            if (r4 != r7) goto L_0x05eb
            android.graphics.Path$FillType r4 = android.graphics.Path.FillType.WINDING
            goto L_0x05ed
        L_0x05eb:
            android.graphics.Path$FillType r4 = android.graphics.Path.FillType.EVEN_ODD
        L_0x05ed:
            r19 = r4
            goto L_0x05c7
        L_0x05f0:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r23 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x05c7
        L_0x05f5:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r22 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x05c7
        L_0x05fa:
            int r4 = r31.nextInt()
            if (r4 != r7) goto L_0x0603
            r18 = r2
            goto L_0x05c7
        L_0x0603:
            r18 = r3
            goto L_0x05c7
        L_0x0606:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r21 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r31, r32)
            goto L_0x05c7
        L_0x060b:
            r31.beginObject()
            r4 = -1
        L_0x060f:
            boolean r5 = r31.hasNext()
            if (r5 == 0) goto L_0x063c
            com.airbnb.lottie.parser.moshi.JsonReader$Options r5 = com.airbnb.lottie.parser.GradientFillParser.GRADIENT_NAMES
            int r5 = r0.selectName(r5)
            if (r5 == 0) goto L_0x0637
            if (r5 == r7) goto L_0x0626
            r31.skipName()
            r31.skipValue()
            goto L_0x060f
        L_0x0626:
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r5 = new com.airbnb.lottie.model.animatable.AnimatableGradientColorValue
            com.airbnb.lottie.parser.GradientColorParser r6 = new com.airbnb.lottie.parser.GradientColorParser
            r6.<init>(r4)
            java.util.ArrayList r6 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r1, r15, r6)
            r5.<init>(r6)
            r20 = r5
            goto L_0x060f
        L_0x0637:
            int r4 = r31.nextInt()
            goto L_0x060f
        L_0x063c:
            r31.endObject()
            goto L_0x05c7
        L_0x0640:
            java.lang.String r17 = r31.nextString()
            goto L_0x05c7
        L_0x0645:
            com.airbnb.lottie.model.content.GradientFill r1 = new com.airbnb.lottie.model.content.GradientFill
            r16 = r1
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24)
            goto L_0x070c
        L_0x064e:
            r2 = 0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.ShapeFillParser.NAMES
            r3 = 0
            r5 = 0
            r6 = 0
            r18 = r2
            r22 = r18
            r17 = r3
            r20 = r5
            r21 = r6
            r2 = r7
        L_0x065f:
            boolean r3 = r31.hasNext()
            if (r3 == 0) goto L_0x06a5
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.ShapeFillParser.NAMES
            int r3 = r0.selectName(r3)
            if (r3 == 0) goto L_0x06a0
            if (r3 == r7) goto L_0x0692
            if (r3 == r4) goto L_0x068d
            if (r3 == r11) goto L_0x0688
            if (r3 == r10) goto L_0x0683
            if (r3 == r9) goto L_0x067e
            r31.skipName()
            r31.skipValue()
            goto L_0x065f
        L_0x067e:
            boolean r22 = r31.nextBoolean()
            goto L_0x065f
        L_0x0683:
            int r2 = r31.nextInt()
            goto L_0x065f
        L_0x0688:
            boolean r18 = r31.nextBoolean()
            goto L_0x065f
        L_0x068d:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r21 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r31, r32)
            goto L_0x065f
        L_0x0692:
            com.airbnb.lottie.model.animatable.AnimatableColorValue r3 = new com.airbnb.lottie.model.animatable.AnimatableColorValue
            com.airbnb.lottie.parser.ColorParser r5 = com.airbnb.lottie.parser.ColorParser.INSTANCE
            java.util.ArrayList r5 = com.airbnb.lottie.parser.KeyframesParser.parse(r0, r1, r15, r5)
            r3.<init>(r5)
            r20 = r3
            goto L_0x065f
        L_0x06a0:
            java.lang.String r17 = r31.nextString()
            goto L_0x065f
        L_0x06a5:
            if (r2 != r7) goto L_0x06aa
            android.graphics.Path$FillType r1 = android.graphics.Path.FillType.WINDING
            goto L_0x06ac
        L_0x06aa:
            android.graphics.Path$FillType r1 = android.graphics.Path.FillType.EVEN_ODD
        L_0x06ac:
            r19 = r1
            com.airbnb.lottie.model.content.ShapeFill r1 = new com.airbnb.lottie.model.content.ShapeFill
            r16 = r1
            r16.<init>(r17, r18, r19, r20, r21, r22)
            goto L_0x070c
        L_0x06b7:
            r2 = 0
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.CircleShapeParser.NAMES
            if (r5 != r11) goto L_0x06be
            r3 = r7
            goto L_0x06bf
        L_0x06be:
            r3 = r2
        L_0x06bf:
            r5 = 0
            r6 = 0
            r8 = 0
            r17 = r2
            r16 = r3
            r13 = r5
            r14 = r6
            r15 = r8
        L_0x06c9:
            boolean r3 = r31.hasNext()
            if (r3 == 0) goto L_0x0706
            com.airbnb.lottie.parser.moshi.JsonReader$Options r3 = com.airbnb.lottie.parser.CircleShapeParser.NAMES
            int r3 = r0.selectName(r3)
            if (r3 == 0) goto L_0x0701
            if (r3 == r7) goto L_0x06fc
            if (r3 == r4) goto L_0x06f7
            if (r3 == r11) goto L_0x06f2
            if (r3 == r10) goto L_0x06e6
            r31.skipName()
            r31.skipValue()
            goto L_0x06c9
        L_0x06e6:
            int r3 = r31.nextInt()
            if (r3 != r11) goto L_0x06ef
            r16 = r7
            goto L_0x06c9
        L_0x06ef:
            r16 = r2
            goto L_0x06c9
        L_0x06f2:
            boolean r17 = r31.nextBoolean()
            goto L_0x06c9
        L_0x06f7:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r15 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r31, r32)
            goto L_0x06c9
        L_0x06fc:
            com.airbnb.lottie.model.animatable.AnimatableValue r14 = com.airbnb.lottie.parser.AnimatablePathValueParser.parseSplitPath(r31, r32)
            goto L_0x06c9
        L_0x0701:
            java.lang.String r13 = r31.nextString()
            goto L_0x06c9
        L_0x0706:
            com.airbnb.lottie.model.content.CircleShape r1 = new com.airbnb.lottie.model.content.CircleShape
            r12 = r1
            r12.<init>(r13, r14, r15, r16, r17)
        L_0x070c:
            boolean r2 = r31.hasNext()
            if (r2 == 0) goto L_0x0716
            r31.skipValue()
            goto L_0x070c
        L_0x0716:
            r31.endObject()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ContentModelParser.parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ContentModel");
    }
}
