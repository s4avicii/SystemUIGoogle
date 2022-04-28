package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

public final class LottieCompositionMoshiParser {
    public static JsonReader.Options ASSETS_NAMES = JsonReader.Options.m22of("id", "layers", "w", "h", "p", "u");
    public static final JsonReader.Options FONT_NAMES = JsonReader.Options.m22of("list");
    public static final JsonReader.Options MARKER_NAMES = JsonReader.Options.m22of("cm", "tm", "dr");
    public static final JsonReader.Options NAMES = JsonReader.Options.m22of("w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");

    /* JADX WARNING: Code restructure failed: missing block: B:156:0x03c8, code lost:
        r12 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03f0, code lost:
        r2 = r8;
        r12 = r18;
        r1 = r19;
        r13 = r20;
        r7 = r21;
        r14 = r22;
        r6 = r23;
        r8 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0401, code lost:
        r12 = r18;
        r13 = r20;
        r14 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0407, code lost:
        r2 = r8;
        r1 = r19;
        r7 = r21;
        r6 = r23;
        r8 = r24;
        r10 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0178, code lost:
        r19 = r1;
        r23 = r6;
        r21 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0206, code lost:
        r24 = r8;
        r25 = r10;
        r8 = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.LottieComposition parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader r30) throws java.io.IOException {
        /*
            r0 = r30
            float r1 = com.airbnb.lottie.utils.Utils.dpScale()
            androidx.collection.LongSparseArray r2 = new androidx.collection.LongSparseArray
            r2.<init>()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.HashMap r4 = new java.util.HashMap
            r4.<init>()
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            androidx.collection.SparseArrayCompat r8 = new androidx.collection.SparseArrayCompat
            r8.<init>()
            com.airbnb.lottie.LottieComposition r9 = new com.airbnb.lottie.LottieComposition
            r9.<init>()
            r30.beginObject()
            r10 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0036:
            boolean r16 = r30.hasNext()
            if (r16 == 0) goto L_0x0414
            com.airbnb.lottie.parser.moshi.JsonReader$Options r11 = NAMES
            int r11 = r0.selectName(r11)
            r17 = 0
            r18 = r12
            switch(r11) {
                case 0: goto L_0x03dd;
                case 1: goto L_0x03cb;
                case 2: goto L_0x03b6;
                case 3: goto L_0x039e;
                case 4: goto L_0x0388;
                case 5: goto L_0x033f;
                case 6: goto L_0x02e9;
                case 7: goto L_0x020d;
                case 8: goto L_0x0180;
                case 9: goto L_0x00cb;
                case 10: goto L_0x0060;
                default: goto L_0x0049;
            }
        L_0x0049:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r22 = r14
            r8 = r2
            r30.skipName()
            r30.skipValue()
            goto L_0x0401
        L_0x0060:
            r30.beginArray()
        L_0x0063:
            boolean r11 = r30.hasNext()
            if (r11 == 0) goto L_0x00c2
            r30.beginObject()
            r21 = r17
            r11 = 0
            r20 = 0
        L_0x0071:
            boolean r19 = r30.hasNext()
            if (r19 == 0) goto L_0x00a9
            com.airbnb.lottie.parser.moshi.JsonReader$Options r12 = MARKER_NAMES
            int r12 = r0.selectName(r12)
            if (r12 == 0) goto L_0x009f
            r22 = r14
            r14 = 1
            if (r12 == r14) goto L_0x0097
            r14 = 2
            if (r12 == r14) goto L_0x008e
            r30.skipName()
            r30.skipValue()
            goto L_0x00a6
        L_0x008e:
            r14 = r13
            double r12 = r30.nextDouble()
            float r12 = (float) r12
            r20 = r12
            goto L_0x009d
        L_0x0097:
            r14 = r13
            double r11 = r30.nextDouble()
            float r11 = (float) r11
        L_0x009d:
            r13 = r14
            goto L_0x00a6
        L_0x009f:
            r22 = r14
            r14 = r13
            java.lang.String r21 = r30.nextString()
        L_0x00a6:
            r14 = r22
            goto L_0x0071
        L_0x00a9:
            r22 = r14
            r14 = r13
            r30.endObject()
            com.airbnb.lottie.model.Marker r12 = new com.airbnb.lottie.model.Marker
            r13 = r20
            r20 = r14
            r14 = r21
            r12.<init>(r14, r11, r13)
            r7.add(r12)
            r13 = r20
            r14 = r22
            goto L_0x0063
        L_0x00c2:
            r20 = r13
            r22 = r14
            r30.endArray()
            goto L_0x0178
        L_0x00cb:
            r20 = r13
            r22 = r14
            r30.beginArray()
        L_0x00d2:
            boolean r11 = r30.hasNext()
            if (r11 == 0) goto L_0x0175
            com.airbnb.lottie.parser.moshi.JsonReader$Options r11 = com.airbnb.lottie.parser.FontCharacterParser.NAMES
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r30.beginObject()
            r12 = 0
            r26 = r12
            r28 = r17
            r29 = r28
            r25 = 0
        L_0x00ec:
            boolean r12 = r30.hasNext()
            if (r12 == 0) goto L_0x0160
            com.airbnb.lottie.parser.moshi.JsonReader$Options r12 = com.airbnb.lottie.parser.FontCharacterParser.NAMES
            int r12 = r0.selectName(r12)
            if (r12 == 0) goto L_0x0156
            r13 = 1
            if (r12 == r13) goto L_0x0152
            r13 = 2
            if (r12 == r13) goto L_0x014d
            r13 = 3
            if (r12 == r13) goto L_0x0148
            r13 = 4
            if (r12 == r13) goto L_0x0143
            r13 = 5
            if (r12 == r13) goto L_0x0110
            r30.skipName()
            r30.skipValue()
            goto L_0x00ec
        L_0x0110:
            r30.beginObject()
        L_0x0113:
            boolean r12 = r30.hasNext()
            if (r12 == 0) goto L_0x013f
            com.airbnb.lottie.parser.moshi.JsonReader$Options r12 = com.airbnb.lottie.parser.FontCharacterParser.DATA_NAMES
            int r12 = r0.selectName(r12)
            if (r12 == 0) goto L_0x0128
            r30.skipName()
            r30.skipValue()
            goto L_0x0113
        L_0x0128:
            r30.beginArray()
        L_0x012b:
            boolean r12 = r30.hasNext()
            if (r12 == 0) goto L_0x013b
            com.airbnb.lottie.model.content.ContentModel r12 = com.airbnb.lottie.parser.ContentModelParser.parse(r0, r9)
            com.airbnb.lottie.model.content.ShapeGroup r12 = (com.airbnb.lottie.model.content.ShapeGroup) r12
            r11.add(r12)
            goto L_0x012b
        L_0x013b:
            r30.endArray()
            goto L_0x0113
        L_0x013f:
            r30.endObject()
            goto L_0x00ec
        L_0x0143:
            java.lang.String r29 = r30.nextString()
            goto L_0x00ec
        L_0x0148:
            java.lang.String r28 = r30.nextString()
            goto L_0x00ec
        L_0x014d:
            double r26 = r30.nextDouble()
            goto L_0x00ec
        L_0x0152:
            r30.nextDouble()
            goto L_0x00ec
        L_0x0156:
            java.lang.String r12 = r30.nextString()
            r13 = 0
            char r25 = r12.charAt(r13)
            goto L_0x00ec
        L_0x0160:
            r30.endObject()
            com.airbnb.lottie.model.FontCharacter r12 = new com.airbnb.lottie.model.FontCharacter
            r23 = r12
            r24 = r11
            r23.<init>(r24, r25, r26, r28, r29)
            int r11 = r12.hashCode()
            r8.put(r11, r12)
            goto L_0x00d2
        L_0x0175:
            r30.endArray()
        L_0x0178:
            r19 = r1
            r23 = r6
            r21 = r7
            goto L_0x0206
        L_0x0180:
            r20 = r13
            r22 = r14
            r30.beginObject()
        L_0x0187:
            boolean r11 = r30.hasNext()
            if (r11 == 0) goto L_0x01fd
            com.airbnb.lottie.parser.moshi.JsonReader$Options r11 = FONT_NAMES
            int r11 = r0.selectName(r11)
            if (r11 == 0) goto L_0x019c
            r30.skipName()
            r30.skipValue()
            goto L_0x0187
        L_0x019c:
            r30.beginArray()
        L_0x019f:
            boolean r11 = r30.hasNext()
            if (r11 == 0) goto L_0x01f7
            com.airbnb.lottie.parser.moshi.JsonReader$Options r11 = com.airbnb.lottie.parser.FontParser.NAMES
            r30.beginObject()
            r11 = r17
            r12 = r11
            r13 = r12
        L_0x01ae:
            boolean r14 = r30.hasNext()
            if (r14 == 0) goto L_0x01e7
            com.airbnb.lottie.parser.moshi.JsonReader$Options r14 = com.airbnb.lottie.parser.FontParser.NAMES
            int r14 = r0.selectName(r14)
            if (r14 == 0) goto L_0x01e0
            r21 = r7
            r7 = 1
            if (r14 == r7) goto L_0x01d8
            r7 = 2
            if (r14 == r7) goto L_0x01d2
            r7 = 3
            if (r14 == r7) goto L_0x01ce
            r30.skipName()
            r30.skipValue()
            goto L_0x01dd
        L_0x01ce:
            r30.nextDouble()
            goto L_0x01dd
        L_0x01d2:
            java.lang.String r7 = r30.nextString()
            r13 = r7
            goto L_0x01dd
        L_0x01d8:
            java.lang.String r7 = r30.nextString()
            r12 = r7
        L_0x01dd:
            r7 = r21
            goto L_0x01ae
        L_0x01e0:
            r21 = r7
            java.lang.String r11 = r30.nextString()
            goto L_0x01ae
        L_0x01e7:
            r21 = r7
            r30.endObject()
            com.airbnb.lottie.model.Font r7 = new com.airbnb.lottie.model.Font
            r7.<init>(r11, r12, r13)
            r6.put(r12, r7)
            r7 = r21
            goto L_0x019f
        L_0x01f7:
            r21 = r7
            r30.endArray()
            goto L_0x0187
        L_0x01fd:
            r21 = r7
            r30.endObject()
            r19 = r1
            r23 = r6
        L_0x0206:
            r24 = r8
            r25 = r10
            r8 = r2
            goto L_0x0401
        L_0x020d:
            r21 = r7
            r20 = r13
            r22 = r14
            r30.beginArray()
        L_0x0216:
            boolean r7 = r30.hasNext()
            if (r7 == 0) goto L_0x02d8
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            androidx.collection.LongSparseArray r11 = new androidx.collection.LongSparseArray
            r11.<init>()
            r30.beginObject()
            r23 = r6
            r6 = r17
            r14 = r6
            r12 = 0
            r13 = 0
        L_0x0230:
            boolean r24 = r30.hasNext()
            if (r24 == 0) goto L_0x02b2
            r24 = r8
            com.airbnb.lottie.parser.moshi.JsonReader$Options r8 = ASSETS_NAMES
            int r8 = r0.selectName(r8)
            if (r8 == 0) goto L_0x02a1
            r25 = r10
            r10 = 1
            if (r8 == r10) goto L_0x0272
            r10 = 2
            if (r8 == r10) goto L_0x026c
            r10 = 3
            if (r8 == r10) goto L_0x0266
            r10 = 4
            if (r8 == r10) goto L_0x0260
            r10 = 5
            if (r8 == r10) goto L_0x025c
            r30.skipName()
            r30.skipValue()
            r19 = r1
            r26 = r2
            goto L_0x0298
        L_0x025c:
            r30.nextString()
            goto L_0x02ac
        L_0x0260:
            r10 = 5
            java.lang.String r14 = r30.nextString()
            goto L_0x02ac
        L_0x0266:
            r10 = 5
            int r13 = r30.nextInt()
            goto L_0x02ac
        L_0x026c:
            r10 = 5
            int r12 = r30.nextInt()
            goto L_0x02ac
        L_0x0272:
            r10 = 5
            r30.beginArray()
        L_0x0276:
            boolean r8 = r30.hasNext()
            if (r8 == 0) goto L_0x0291
            com.airbnb.lottie.model.layer.Layer r8 = com.airbnb.lottie.parser.LayerParser.parse(r0, r9)
            r19 = r1
            r26 = r2
            long r1 = r8.layerId
            r11.put(r1, r8)
            r7.add(r8)
            r1 = r19
            r2 = r26
            goto L_0x0276
        L_0x0291:
            r19 = r1
            r26 = r2
            r30.endArray()
        L_0x0298:
            r1 = r19
            r8 = r24
            r10 = r25
            r2 = r26
            goto L_0x0230
        L_0x02a1:
            r19 = r1
            r26 = r2
            r25 = r10
            r10 = 5
            java.lang.String r6 = r30.nextString()
        L_0x02ac:
            r8 = r24
            r10 = r25
            goto L_0x0230
        L_0x02b2:
            r19 = r1
            r26 = r2
            r24 = r8
            r25 = r10
            r10 = 5
            r30.endObject()
            if (r14 == 0) goto L_0x02c9
            com.airbnb.lottie.LottieImageAsset r1 = new com.airbnb.lottie.LottieImageAsset
            r1.<init>(r12, r13, r6, r14)
            r5.put(r6, r1)
            goto L_0x02cc
        L_0x02c9:
            r4.put(r6, r7)
        L_0x02cc:
            r1 = r19
            r6 = r23
            r8 = r24
            r10 = r25
            r2 = r26
            goto L_0x0216
        L_0x02d8:
            r19 = r1
            r26 = r2
            r23 = r6
            r24 = r8
            r25 = r10
            r30.endArray()
            r8 = r26
            goto L_0x0401
        L_0x02e9:
            r19 = r1
            r26 = r2
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r22 = r14
            r30.beginArray()
            r1 = 0
        L_0x02fd:
            boolean r2 = r30.hasNext()
            if (r2 == 0) goto L_0x0338
            com.airbnb.lottie.model.layer.Layer r2 = com.airbnb.lottie.parser.LayerParser.parse(r0, r9)
            com.airbnb.lottie.model.layer.Layer$LayerType r6 = r2.layerType
            com.airbnb.lottie.model.layer.Layer$LayerType r7 = com.airbnb.lottie.model.layer.Layer.LayerType.IMAGE
            if (r6 != r7) goto L_0x030f
            int r1 = r1 + 1
        L_0x030f:
            r3.add(r2)
            long r6 = r2.layerId
            r8 = r26
            r8.put(r6, r2)
            r2 = 4
            if (r1 <= r2) goto L_0x0335
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "You have "
            r2.append(r6)
            r2.append(r1)
            java.lang.String r6 = " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers."
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            com.airbnb.lottie.utils.Logger.warning(r2)
        L_0x0335:
            r26 = r8
            goto L_0x02fd
        L_0x0338:
            r8 = r26
            r30.endArray()
            goto L_0x0401
        L_0x033f:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r22 = r14
            r8 = r2
            java.lang.String r1 = r30.nextString()
            java.lang.String r2 = "\\."
            java.lang.String[] r1 = r1.split(r2)
            r2 = 0
            r6 = r1[r2]
            int r2 = java.lang.Integer.parseInt(r6)
            r6 = 1
            r7 = r1[r6]
            int r7 = java.lang.Integer.parseInt(r7)
            r10 = 2
            r1 = r1[r10]
            int r1 = java.lang.Integer.parseInt(r1)
            r10 = 4
            if (r2 >= r10) goto L_0x0371
            goto L_0x037e
        L_0x0371:
            if (r2 <= r10) goto L_0x0374
            goto L_0x037c
        L_0x0374:
            if (r7 >= r10) goto L_0x0377
            goto L_0x037e
        L_0x0377:
            if (r7 <= r10) goto L_0x037a
            goto L_0x037c
        L_0x037a:
            if (r1 < 0) goto L_0x037e
        L_0x037c:
            r12 = r6
            goto L_0x037f
        L_0x037e:
            r12 = 0
        L_0x037f:
            if (r12 != 0) goto L_0x0401
            java.lang.String r1 = "Lottie only supports bodymovin >= 4.4.0"
            r9.addWarning(r1)
            goto L_0x0401
        L_0x0388:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r22 = r14
            r8 = r2
            double r1 = r30.nextDouble()
            float r12 = (float) r1
            goto L_0x0407
        L_0x039e:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r8 = r2
            double r1 = r30.nextDouble()
            float r1 = (float) r1
            r2 = 1008981770(0x3c23d70a, float:0.01)
            float r14 = r1 - r2
            goto L_0x03c8
        L_0x03b6:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r22 = r14
            r8 = r2
            double r1 = r30.nextDouble()
            float r13 = (float) r1
        L_0x03c8:
            r12 = r18
            goto L_0x0407
        L_0x03cb:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r20 = r13
            r22 = r14
            r8 = r2
            int r10 = r30.nextInt()
            goto L_0x03f0
        L_0x03dd:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r20 = r13
            r22 = r14
            r8 = r2
            int r15 = r30.nextInt()
        L_0x03f0:
            r2 = r8
            r12 = r18
            r1 = r19
            r13 = r20
            r7 = r21
            r14 = r22
            r6 = r23
            r8 = r24
            goto L_0x0036
        L_0x0401:
            r12 = r18
            r13 = r20
            r14 = r22
        L_0x0407:
            r2 = r8
            r1 = r19
            r7 = r21
            r6 = r23
            r8 = r24
            r10 = r25
            goto L_0x0036
        L_0x0414:
            r19 = r1
            r23 = r6
            r21 = r7
            r24 = r8
            r25 = r10
            r18 = r12
            r20 = r13
            r22 = r14
            r8 = r2
            float r0 = (float) r15
            float r0 = r0 * r19
            int r0 = (int) r0
            float r1 = (float) r10
            float r1 = r1 * r19
            int r1 = (int) r1
            android.graphics.Rect r2 = new android.graphics.Rect
            r6 = 0
            r2.<init>(r6, r6, r0, r1)
            r9.bounds = r2
            r10 = r20
            r9.startFrame = r10
            r10 = r22
            r9.endFrame = r10
            r10 = r18
            r9.frameRate = r10
            r9.layers = r3
            r9.layerMap = r8
            r9.precomps = r4
            r9.images = r5
            r0 = r24
            r9.characters = r0
            r0 = r23
            r9.fonts = r0
            r0 = r21
            r9.markers = r0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.LottieCompositionMoshiParser.parse(com.airbnb.lottie.parser.moshi.JsonUtf8Reader):com.airbnb.lottie.LottieComposition");
    }
}
