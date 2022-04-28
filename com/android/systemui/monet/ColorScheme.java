package com.android.systemui.monet;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.internal.graphics.cam.Cam;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: ColorScheme.kt */
public final class ColorScheme {
    public static final Companion Companion = new Companion();
    public final ArrayList accent1;
    public final ArrayList accent2;
    public final ArrayList accent3;
    public final ArrayList neutral1;
    public final ArrayList neutral2;
    public final Style style;

    /* compiled from: ColorScheme.kt */
    public static final class Companion {
        public static final String access$humanReadable(ArrayList arrayList) {
            return CollectionsKt___CollectionsKt.joinToString$default(arrayList, (String) null, (String) null, (String) null, ColorScheme$Companion$humanReadable$1.INSTANCE, 31);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:104:0x0381, code lost:
            if (r3 != 15) goto L_0x0391;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.util.List getSeedColors(android.app.WallpaperColors r18) {
            /*
                java.util.Map r0 = r18.getAllColors()
                java.util.Collection r0 = r0.values()
                java.util.Iterator r0 = r0.iterator()
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x0394
                java.lang.Object r1 = r0.next()
            L_0x0016:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0032
                java.lang.Object r2 = r0.next()
                java.lang.Integer r2 = (java.lang.Integer) r2
                java.lang.Integer r1 = (java.lang.Integer) r1
                int r1 = r1.intValue()
                int r2 = r2.intValue()
                int r2 = r2 + r1
                java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
                goto L_0x0016
            L_0x0032:
                java.lang.Number r1 = (java.lang.Number) r1
                int r0 = r1.intValue()
                double r0 = (double) r0
                r2 = 0
                int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r5 = 0
                r6 = 1
                if (r4 != 0) goto L_0x0043
                r4 = r6
                goto L_0x0044
            L_0x0043:
                r4 = r5
            L_0x0044:
                r7 = -14979341(0xffffffffff1b6ef3, float:-2.0660642E38)
                r8 = 1084227584(0x40a00000, float:5.0)
                if (r4 == 0) goto L_0x00c0
                java.util.List r0 = r18.getMainColors()
                java.util.ArrayList r1 = new java.util.ArrayList
                r2 = 10
                int r2 = kotlin.collections.CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(r0, r2)
                r1.<init>(r2)
                java.util.Iterator r0 = r0.iterator()
            L_0x005e:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0076
                java.lang.Object r2 = r0.next()
                android.graphics.Color r2 = (android.graphics.Color) r2
                int r2 = r2.toArgb()
                java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
                r1.add(r2)
                goto L_0x005e
            L_0x0076:
                java.util.Set r0 = kotlin.collections.CollectionsKt___CollectionsKt.toMutableSet(r1)
                java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r0)
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.Iterator r0 = r0.iterator()
            L_0x0087:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x00ad
                java.lang.Object r2 = r0.next()
                r3 = r2
                java.lang.Number r3 = (java.lang.Number) r3
                int r3 = r3.intValue()
                com.android.internal.graphics.cam.Cam r3 = com.android.internal.graphics.cam.Cam.fromInt(r3)
                float r3 = r3.getChroma()
                int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
                if (r3 < 0) goto L_0x00a6
                r3 = r6
                goto L_0x00a7
            L_0x00a6:
                r3 = r5
            L_0x00a7:
                if (r3 == 0) goto L_0x0087
                r1.add(r2)
                goto L_0x0087
            L_0x00ad:
                java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r1)
                boolean r1 = r0.isEmpty()
                if (r1 == 0) goto L_0x00bf
                java.lang.Integer r0 = java.lang.Integer.valueOf(r7)
                java.util.List r0 = java.util.Collections.singletonList(r0)
            L_0x00bf:
                return r0
            L_0x00c0:
                java.util.Map r9 = r18.getAllColors()
                java.util.LinkedHashMap r10 = new java.util.LinkedHashMap
                int r11 = r9.size()
                int r11 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r11)
                r10.<init>(r11)
                java.util.Set r9 = r9.entrySet()
                java.util.Iterator r9 = r9.iterator()
            L_0x00d9:
                boolean r11 = r9.hasNext()
                if (r11 == 0) goto L_0x00fd
                java.lang.Object r11 = r9.next()
                java.util.Map$Entry r11 = (java.util.Map.Entry) r11
                java.lang.Object r12 = r11.getKey()
                java.lang.Object r11 = r11.getValue()
                java.lang.Number r11 = (java.lang.Number) r11
                int r11 = r11.intValue()
                double r13 = (double) r11
                double r13 = r13 / r0
                java.lang.Double r11 = java.lang.Double.valueOf(r13)
                r10.put(r12, r11)
                goto L_0x00d9
            L_0x00fd:
                java.util.Map r0 = r18.getAllColors()
                java.util.LinkedHashMap r1 = new java.util.LinkedHashMap
                int r9 = r0.size()
                int r9 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r9)
                r1.<init>(r9)
                java.util.Set r0 = r0.entrySet()
                java.util.Iterator r0 = r0.iterator()
            L_0x0116:
                boolean r9 = r0.hasNext()
                if (r9 == 0) goto L_0x0138
                java.lang.Object r9 = r0.next()
                java.util.Map$Entry r9 = (java.util.Map.Entry) r9
                java.lang.Object r11 = r9.getKey()
                java.lang.Object r9 = r9.getKey()
                java.lang.Number r9 = (java.lang.Number) r9
                int r9 = r9.intValue()
                com.android.internal.graphics.cam.Cam r9 = com.android.internal.graphics.cam.Cam.fromInt(r9)
                r1.put(r11, r9)
                goto L_0x0116
            L_0x0138:
                java.util.ArrayList r0 = new java.util.ArrayList
                r9 = 360(0x168, float:5.04E-43)
                r0.<init>(r9)
                r11 = r5
            L_0x0140:
                if (r11 >= r9) goto L_0x014c
                int r11 = r11 + 1
                java.lang.Double r12 = java.lang.Double.valueOf(r2)
                r0.add(r12)
                goto L_0x0140
            L_0x014c:
                java.util.ArrayList r11 = new java.util.ArrayList
                r11.<init>(r0)
                java.util.Set r0 = r10.entrySet()
                java.util.Iterator r0 = r0.iterator()
            L_0x0159:
                boolean r12 = r0.hasNext()
                if (r12 == 0) goto L_0x01a9
                java.lang.Object r12 = r0.next()
                java.util.Map$Entry r12 = (java.util.Map.Entry) r12
                java.lang.Object r13 = r12.getKey()
                java.lang.Object r13 = r10.get(r13)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r13)
                java.lang.Number r13 = (java.lang.Number) r13
                double r13 = r13.doubleValue()
                java.lang.Object r12 = r12.getKey()
                java.lang.Object r12 = r1.get(r12)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r12)
                com.android.internal.graphics.cam.Cam r12 = (com.android.internal.graphics.cam.Cam) r12
                float r15 = r12.getHue()
                int r15 = androidx.leanback.R$drawable.roundToInt(r15)
                int r15 = r15 % r9
                float r12 = r12.getChroma()
                int r12 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
                if (r12 > 0) goto L_0x0195
                goto L_0x0159
            L_0x0195:
                java.lang.Object r12 = r11.get(r15)
                java.lang.Number r12 = (java.lang.Number) r12
                double r16 = r12.doubleValue()
                double r16 = r16 + r13
                java.lang.Double r12 = java.lang.Double.valueOf(r16)
                r11.set(r15, r12)
                goto L_0x0159
            L_0x01a9:
                java.util.Map r0 = r18.getAllColors()
                java.util.LinkedHashMap r10 = new java.util.LinkedHashMap
                int r12 = r0.size()
                int r12 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r12)
                r10.<init>(r12)
                java.util.Set r0 = r0.entrySet()
                java.util.Iterator r0 = r0.iterator()
            L_0x01c2:
                boolean r12 = r0.hasNext()
                r13 = 15
                if (r12 == 0) goto L_0x021d
                java.lang.Object r12 = r0.next()
                java.util.Map$Entry r12 = (java.util.Map.Entry) r12
                java.lang.Object r14 = r12.getKey()
                java.lang.Object r12 = r12.getKey()
                java.lang.Object r12 = r1.get(r12)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r12)
                com.android.internal.graphics.cam.Cam r12 = (com.android.internal.graphics.cam.Cam) r12
                float r12 = r12.getHue()
                int r12 = androidx.leanback.R$drawable.roundToInt(r12)
                int r15 = r12 + -15
                int r12 = r12 + r13
                if (r15 > r12) goto L_0x0211
                r16 = r2
            L_0x01f0:
                int r13 = r15 + 1
                if (r15 >= 0) goto L_0x01f8
                int r2 = r15 % 360
                int r2 = r2 + r9
                goto L_0x01fe
            L_0x01f8:
                if (r15 < r9) goto L_0x01fd
                int r2 = r15 % 360
                goto L_0x01fe
            L_0x01fd:
                r2 = r15
            L_0x01fe:
                java.lang.Object r2 = r11.get(r2)
                java.lang.Number r2 = (java.lang.Number) r2
                double r2 = r2.doubleValue()
                double r16 = r2 + r16
                if (r15 != r12) goto L_0x020d
                goto L_0x0213
            L_0x020d:
                r15 = r13
                r2 = 0
                goto L_0x01f0
            L_0x0211:
                r16 = 0
            L_0x0213:
                java.lang.Double r2 = java.lang.Double.valueOf(r16)
                r10.put(r14, r2)
                r2 = 0
                goto L_0x01c2
            L_0x021d:
                java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
                r0.<init>()
                java.util.Set r2 = r1.entrySet()
                java.util.Iterator r2 = r2.iterator()
            L_0x022a:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x027e
                java.lang.Object r3 = r2.next()
                java.util.Map$Entry r3 = (java.util.Map.Entry) r3
                java.lang.Object r9 = r3.getValue()
                com.android.internal.graphics.cam.Cam r9 = (com.android.internal.graphics.cam.Cam) r9
                java.lang.Object r11 = r3.getKey()
                java.lang.Number r11 = (java.lang.Number) r11
                int r11 = r11.intValue()
                com.android.internal.graphics.cam.CamUtils.lstarFromInt(r11)
                java.lang.Object r11 = r3.getKey()
                java.lang.Object r11 = r10.get(r11)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
                java.lang.Number r11 = (java.lang.Number) r11
                double r11 = r11.doubleValue()
                float r9 = r9.getChroma()
                int r9 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
                if (r9 < 0) goto L_0x026f
                if (r4 != 0) goto L_0x026d
                r14 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
                int r9 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
                if (r9 <= 0) goto L_0x026f
            L_0x026d:
                r9 = r6
                goto L_0x0270
            L_0x026f:
                r9 = r5
            L_0x0270:
                if (r9 == 0) goto L_0x022a
                java.lang.Object r9 = r3.getKey()
                java.lang.Object r3 = r3.getValue()
                r0.put(r9, r3)
                goto L_0x022a
            L_0x027e:
                java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
                int r3 = r0.size()
                int r3 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r3)
                r2.<init>(r3)
                java.util.Set r0 = r0.entrySet()
                java.util.Iterator r0 = r0.iterator()
            L_0x0293:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x02e9
                java.lang.Object r3 = r0.next()
                java.util.Map$Entry r3 = (java.util.Map.Entry) r3
                java.lang.Object r4 = r3.getKey()
                java.lang.Object r8 = r3.getValue()
                com.android.internal.graphics.cam.Cam r8 = (com.android.internal.graphics.cam.Cam) r8
                java.lang.Object r3 = r3.getKey()
                java.lang.Object r3 = r10.get(r3)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                java.lang.Number r3 = (java.lang.Number) r3
                double r11 = r3.doubleValue()
                r14 = 4634626229029306368(0x4051800000000000, double:70.0)
                double r11 = r11 * r14
                float r3 = r8.getChroma()
                r9 = 1111490560(0x42400000, float:48.0)
                int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
                if (r3 >= 0) goto L_0x02d4
                r14 = 4591870180066957722(0x3fb999999999999a, double:0.1)
                float r3 = r8.getChroma()
                goto L_0x02dd
            L_0x02d4:
                r14 = 4599075939470750515(0x3fd3333333333333, double:0.3)
                float r3 = r8.getChroma()
            L_0x02dd:
                float r3 = r3 - r9
                double r8 = (double) r3
                double r8 = r8 * r14
                double r8 = r8 + r11
                java.lang.Double r3 = java.lang.Double.valueOf(r8)
                r2.put(r4, r3)
                goto L_0x0293
            L_0x02e9:
                java.util.Set r0 = r2.entrySet()
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>(r0)
                int r0 = r2.size()
                if (r0 <= r6) goto L_0x0300
                com.android.systemui.monet.ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1 r0 = new com.android.systemui.monet.ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1
                r0.<init>()
                kotlin.collections.CollectionsKt__MutableCollectionsJVMKt.sortWith(r2, r0)
            L_0x0300:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r3 = 90
            L_0x0307:
                int r4 = r3 + -1
                r0.clear()
                java.util.Iterator r8 = r2.iterator()
            L_0x0310:
                boolean r9 = r8.hasNext()
                if (r9 == 0) goto L_0x0381
                java.lang.Object r9 = r8.next()
                java.util.Map$Entry r9 = (java.util.Map.Entry) r9
                java.lang.Object r9 = r9.getKey()
                java.lang.Integer r9 = (java.lang.Integer) r9
                java.util.Iterator r10 = r0.iterator()
            L_0x0326:
                boolean r11 = r10.hasNext()
                if (r11 == 0) goto L_0x036d
                java.lang.Object r11 = r10.next()
                r12 = r11
                java.lang.Number r12 = (java.lang.Number) r12
                int r12 = r12.intValue()
                java.lang.Object r14 = r1.get(r9)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r14)
                com.android.internal.graphics.cam.Cam r14 = (com.android.internal.graphics.cam.Cam) r14
                float r14 = r14.getHue()
                java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
                java.lang.Object r12 = r1.get(r12)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r12)
                com.android.internal.graphics.cam.Cam r12 = (com.android.internal.graphics.cam.Cam) r12
                float r12 = r12.getHue()
                float r14 = r14 - r12
                float r12 = java.lang.Math.abs(r14)
                r14 = 1127481344(0x43340000, float:180.0)
                float r12 = r12 - r14
                float r12 = java.lang.Math.abs(r12)
                float r14 = r14 - r12
                float r12 = (float) r3
                int r12 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                if (r12 >= 0) goto L_0x0369
                r12 = r6
                goto L_0x036a
            L_0x0369:
                r12 = r5
            L_0x036a:
                if (r12 == 0) goto L_0x0326
                goto L_0x036e
            L_0x036d:
                r11 = 0
            L_0x036e:
                if (r11 == 0) goto L_0x0372
                r10 = r6
                goto L_0x0373
            L_0x0372:
                r10 = r5
            L_0x0373:
                if (r10 == 0) goto L_0x0376
                goto L_0x0310
            L_0x0376:
                r0.add(r9)
                int r9 = r0.size()
                r10 = 4
                if (r9 < r10) goto L_0x0310
                goto L_0x0383
            L_0x0381:
                if (r3 != r13) goto L_0x0391
            L_0x0383:
                boolean r1 = r0.isEmpty()
                if (r1 == 0) goto L_0x0390
                java.lang.Integer r1 = java.lang.Integer.valueOf(r7)
                r0.add(r1)
            L_0x0390:
                return r0
            L_0x0391:
                r3 = r4
                goto L_0x0307
            L_0x0394:
                java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
                java.lang.String r1 = "Empty collection can't be reduced."
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.monet.ColorScheme.Companion.getSeedColors(android.app.WallpaperColors):java.util.List");
        }
    }

    public ColorScheme(int i, Style style2) {
        this.style = style2;
        Cam fromInt = Cam.fromInt((i == 0 || Cam.fromInt(i).getChroma() < 5.0f) ? -14979341 : i);
        CoreSpec coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet = style2.mo9171x334ec08f();
        Objects.requireNonNull(coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet);
        this.accent1 = coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet.f62a1.shades(fromInt);
        CoreSpec coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet2 = style2.mo9171x334ec08f();
        Objects.requireNonNull(coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet2);
        this.accent2 = coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet2.f63a2.shades(fromInt);
        CoreSpec coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet3 = style2.mo9171x334ec08f();
        Objects.requireNonNull(coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet3);
        this.accent3 = coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet3.f64a3.shades(fromInt);
        CoreSpec coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet4 = style2.mo9171x334ec08f();
        Objects.requireNonNull(coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet4);
        this.neutral1 = coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet4.f65n1.shades(fromInt);
        CoreSpec coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet5 = style2.mo9171x334ec08f();
        Objects.requireNonNull(coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet5);
        this.neutral2 = coreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet5.f66n2.shades(fromInt);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ColorScheme {\n  neutral1: ");
        m.append(Companion.access$humanReadable(this.neutral1));
        m.append("\n  neutral2: ");
        m.append(Companion.access$humanReadable(this.neutral2));
        m.append("\n  accent1: ");
        m.append(Companion.access$humanReadable(this.accent1));
        m.append("\n  accent2: ");
        m.append(Companion.access$humanReadable(this.accent2));
        m.append("\n  accent3: ");
        m.append(Companion.access$humanReadable(this.accent3));
        m.append("\n  style: ");
        m.append(this.style);
        m.append("\n}");
        return m.toString();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ColorScheme(android.app.WallpaperColors r2) {
        /*
            r1 = this;
            java.util.List r2 = com.android.systemui.monet.ColorScheme.Companion.getSeedColors(r2)
            boolean r0 = r2.isEmpty()
            if (r0 != 0) goto L_0x001b
            r0 = 0
            java.lang.Object r2 = r2.get(r0)
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            com.android.systemui.monet.Style r0 = com.android.systemui.monet.Style.TONAL_SPOT
            r1.<init>(r2, r0)
            return
        L_0x001b:
            java.util.NoSuchElementException r1 = new java.util.NoSuchElementException
            java.lang.String r2 = "List is empty."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.monet.ColorScheme.<init>(android.app.WallpaperColors):void");
    }
}
