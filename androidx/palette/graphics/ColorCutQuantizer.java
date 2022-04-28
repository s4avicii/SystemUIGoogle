package androidx.palette.graphics;

import androidx.palette.graphics.Palette;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public final class ColorCutQuantizer {
    public static final C02881 VBOX_COMPARATOR_VOLUME = new Comparator<Vbox>() {
        public final int compare(Object obj, Object obj2) {
            Vbox vbox = (Vbox) obj;
            Vbox vbox2 = (Vbox) obj2;
            Objects.requireNonNull(vbox2);
            int i = ((vbox2.mMaxBlue - vbox2.mMinBlue) + 1) * ((vbox2.mMaxGreen - vbox2.mMinGreen) + 1) * ((vbox2.mMaxRed - vbox2.mMinRed) + 1);
            Objects.requireNonNull(vbox);
            return i - (((vbox.mMaxBlue - vbox.mMinBlue) + 1) * (((vbox.mMaxGreen - vbox.mMinGreen) + 1) * ((vbox.mMaxRed - vbox.mMinRed) + 1)));
        }
    };
    public final int[] mColors;
    public final Palette.Filter[] mFilters;
    public final int[] mHistogram;
    public final ArrayList mQuantizedColors;
    public final float[] mTempHsl = new float[3];

    public class Vbox {
        public int mLowerIndex;
        public int mMaxBlue;
        public int mMaxGreen;
        public int mMaxRed;
        public int mMinBlue;
        public int mMinGreen;
        public int mMinRed;
        public int mPopulation;
        public int mUpperIndex;

        public Vbox(int i, int i2) {
            this.mLowerIndex = i;
            this.mUpperIndex = i2;
            fitBox();
        }

        public final void fitBox() {
            ColorCutQuantizer colorCutQuantizer = ColorCutQuantizer.this;
            int[] iArr = colorCutQuantizer.mColors;
            int[] iArr2 = colorCutQuantizer.mHistogram;
            int i = Integer.MAX_VALUE;
            int i2 = Integer.MIN_VALUE;
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MIN_VALUE;
            int i5 = 0;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MAX_VALUE;
            for (int i8 = this.mLowerIndex; i8 <= this.mUpperIndex; i8++) {
                int i9 = iArr[i8];
                i5 += iArr2[i9];
                int i10 = (i9 >> 10) & 31;
                int i11 = (i9 >> 5) & 31;
                int i12 = i9 & 31;
                if (i10 > i2) {
                    i2 = i10;
                }
                if (i10 < i) {
                    i = i10;
                }
                if (i11 > i3) {
                    i3 = i11;
                }
                if (i11 < i6) {
                    i6 = i11;
                }
                if (i12 > i4) {
                    i4 = i12;
                }
                if (i12 < i7) {
                    i7 = i12;
                }
            }
            this.mMinRed = i;
            this.mMaxRed = i2;
            this.mMinGreen = i6;
            this.mMaxGreen = i3;
            this.mMinBlue = i7;
            this.mMaxBlue = i4;
            this.mPopulation = i5;
        }
    }

    public static void modifySignificantOctet(int[] iArr, int i, int i2, int i3) {
        if (i == -2) {
            while (i2 <= i3) {
                int i4 = iArr[i2];
                iArr[i2] = (i4 & 31) | (((i4 >> 5) & 31) << 10) | (((i4 >> 10) & 31) << 5);
                i2++;
            }
        } else if (i == -1) {
            while (i2 <= i3) {
                int i5 = iArr[i2];
                iArr[i2] = ((i5 >> 10) & 31) | ((i5 & 31) << 10) | (((i5 >> 5) & 31) << 5);
                i2++;
            }
        }
    }

    public static int modifyWordWidth(int i, int i2, int i3) {
        return (i3 > i2 ? i << (i3 - i2) : i >> (i2 - i3)) & ((1 << i3) - 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:110:0x01ae A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0235  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ColorCutQuantizer(int[] r19, int r20, androidx.palette.graphics.Palette.Filter[] r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r18.<init>()
            r3 = 3
            float[] r3 = new float[r3]
            r0.mTempHsl = r3
            r3 = r21
            r0.mFilters = r3
            r3 = 32768(0x8000, float:4.5918E-41)
            int[] r4 = new int[r3]
            r0.mHistogram = r4
            r5 = 0
            r6 = r5
        L_0x001b:
            int r7 = r1.length
            r8 = 8
            r9 = 5
            r10 = 1
            if (r6 >= r7) goto L_0x004c
            r7 = r1[r6]
            int r11 = android.graphics.Color.red(r7)
            int r11 = modifyWordWidth(r11, r8, r9)
            int r12 = android.graphics.Color.green(r7)
            int r12 = modifyWordWidth(r12, r8, r9)
            int r7 = android.graphics.Color.blue(r7)
            int r7 = modifyWordWidth(r7, r8, r9)
            int r8 = r11 << 10
            int r9 = r12 << 5
            r8 = r8 | r9
            r7 = r7 | r8
            r1[r6] = r7
            r8 = r4[r7]
            int r8 = r8 + r10
            r4[r7] = r8
            int r6 = r6 + 1
            goto L_0x001b
        L_0x004c:
            r1 = r5
            r6 = r1
        L_0x004e:
            if (r1 >= r3) goto L_0x00ab
            r7 = r4[r1]
            if (r7 <= 0) goto L_0x00a2
            int r7 = r1 >> 10
            r7 = r7 & 31
            int r11 = r1 >> 5
            r11 = r11 & 31
            r12 = r1 & 31
            int r7 = modifyWordWidth(r7, r9, r8)
            int r11 = modifyWordWidth(r11, r9, r8)
            int r12 = modifyWordWidth(r12, r9, r8)
            int r7 = android.graphics.Color.rgb(r7, r11, r12)
            float[] r11 = r0.mTempHsl
            java.lang.ThreadLocal<double[]> r12 = androidx.core.graphics.ColorUtils.TEMP_ARRAY
            int r12 = android.graphics.Color.red(r7)
            int r13 = android.graphics.Color.green(r7)
            int r7 = android.graphics.Color.blue(r7)
            androidx.core.graphics.ColorUtils.RGBToHSL(r12, r13, r7, r11)
            float[] r7 = r0.mTempHsl
            androidx.palette.graphics.Palette$Filter[] r11 = r0.mFilters
            if (r11 == 0) goto L_0x009d
            int r12 = r11.length
            if (r12 <= 0) goto L_0x009d
            int r11 = r11.length
            r12 = r5
        L_0x008c:
            if (r12 >= r11) goto L_0x009d
            androidx.palette.graphics.Palette$Filter[] r13 = r0.mFilters
            r13 = r13[r12]
            boolean r13 = r13.isAllowed(r7)
            if (r13 != 0) goto L_0x009a
            r7 = r10
            goto L_0x009e
        L_0x009a:
            int r12 = r12 + 1
            goto L_0x008c
        L_0x009d:
            r7 = r5
        L_0x009e:
            if (r7 == 0) goto L_0x00a2
            r4[r1] = r5
        L_0x00a2:
            r7 = r4[r1]
            if (r7 <= 0) goto L_0x00a8
            int r6 = r6 + 1
        L_0x00a8:
            int r1 = r1 + 1
            goto L_0x004e
        L_0x00ab:
            int[] r1 = new int[r6]
            r0.mColors = r1
            r7 = r5
            r11 = r7
        L_0x00b1:
            if (r7 >= r3) goto L_0x00bf
            r12 = r4[r7]
            if (r12 <= 0) goto L_0x00bc
            int r12 = r11 + 1
            r1[r11] = r7
            r11 = r12
        L_0x00bc:
            int r7 = r7 + 1
            goto L_0x00b1
        L_0x00bf:
            if (r6 > r2) goto L_0x00f5
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r0.mQuantizedColors = r2
        L_0x00c8:
            if (r5 >= r6) goto L_0x023c
            r2 = r1[r5]
            java.util.ArrayList r3 = r0.mQuantizedColors
            androidx.palette.graphics.Palette$Swatch r7 = new androidx.palette.graphics.Palette$Swatch
            int r10 = r2 >> 10
            r10 = r10 & 31
            int r11 = r2 >> 5
            r11 = r11 & 31
            r12 = r2 & 31
            int r10 = modifyWordWidth(r10, r9, r8)
            int r11 = modifyWordWidth(r11, r9, r8)
            int r12 = modifyWordWidth(r12, r9, r8)
            int r10 = android.graphics.Color.rgb(r10, r11, r12)
            r2 = r4[r2]
            r7.<init>(r10, r2)
            r3.add(r7)
            int r5 = r5 + 1
            goto L_0x00c8
        L_0x00f5:
            java.util.PriorityQueue r1 = new java.util.PriorityQueue
            androidx.palette.graphics.ColorCutQuantizer$1 r3 = VBOX_COMPARATOR_VOLUME
            r1.<init>(r2, r3)
            androidx.palette.graphics.ColorCutQuantizer$Vbox r3 = new androidx.palette.graphics.ColorCutQuantizer$Vbox
            int[] r4 = r0.mColors
            int r4 = r4.length
            r6 = -1
            int r4 = r4 + r6
            r3.<init>(r5, r4)
            r1.offer(r3)
        L_0x0109:
            int r3 = r1.size()
            if (r3 >= r2) goto L_0x01a1
            java.lang.Object r3 = r1.poll()
            androidx.palette.graphics.ColorCutQuantizer$Vbox r3 = (androidx.palette.graphics.ColorCutQuantizer.Vbox) r3
            if (r3 == 0) goto L_0x01a1
            int r4 = r3.mUpperIndex
            int r7 = r4 + 1
            int r11 = r3.mLowerIndex
            int r7 = r7 - r11
            if (r7 <= r10) goto L_0x0122
            r7 = r10
            goto L_0x0123
        L_0x0122:
            r7 = r5
        L_0x0123:
            if (r7 == 0) goto L_0x01a1
            int r7 = r4 + 1
            int r7 = r7 - r11
            if (r7 <= r10) goto L_0x012c
            r7 = r10
            goto L_0x012d
        L_0x012c:
            r7 = r5
        L_0x012d:
            if (r7 == 0) goto L_0x0199
            int r7 = r3.mMaxRed
            int r12 = r3.mMinRed
            int r7 = r7 - r12
            int r12 = r3.mMaxGreen
            int r13 = r3.mMinGreen
            int r12 = r12 - r13
            int r13 = r3.mMaxBlue
            int r14 = r3.mMinBlue
            int r13 = r13 - r14
            if (r7 < r12) goto L_0x0144
            if (r7 < r13) goto L_0x0144
            r7 = -3
            goto L_0x014b
        L_0x0144:
            if (r12 < r7) goto L_0x014a
            if (r12 < r13) goto L_0x014a
            r7 = -2
            goto L_0x014b
        L_0x014a:
            r7 = r6
        L_0x014b:
            androidx.palette.graphics.ColorCutQuantizer r12 = androidx.palette.graphics.ColorCutQuantizer.this
            int[] r13 = r12.mColors
            int[] r12 = r12.mHistogram
            modifySignificantOctet(r13, r7, r11, r4)
            int r4 = r3.mLowerIndex
            int r11 = r3.mUpperIndex
            int r11 = r11 + r10
            java.util.Arrays.sort(r13, r4, r11)
            int r4 = r3.mLowerIndex
            int r11 = r3.mUpperIndex
            modifySignificantOctet(r13, r7, r4, r11)
            int r4 = r3.mPopulation
            int r4 = r4 / 2
            int r7 = r3.mLowerIndex
            r11 = r5
        L_0x016a:
            int r14 = r3.mUpperIndex
            if (r7 > r14) goto L_0x017f
            r15 = r13[r7]
            r15 = r12[r15]
            int r11 = r11 + r15
            if (r11 < r4) goto L_0x017c
            int r14 = r14 + -1
            int r4 = java.lang.Math.min(r14, r7)
            goto L_0x0181
        L_0x017c:
            int r7 = r7 + 1
            goto L_0x016a
        L_0x017f:
            int r4 = r3.mLowerIndex
        L_0x0181:
            androidx.palette.graphics.ColorCutQuantizer$Vbox r7 = new androidx.palette.graphics.ColorCutQuantizer$Vbox
            androidx.palette.graphics.ColorCutQuantizer r11 = androidx.palette.graphics.ColorCutQuantizer.this
            int r12 = r4 + 1
            int r13 = r3.mUpperIndex
            r7.<init>(r12, r13)
            r3.mUpperIndex = r4
            r3.fitBox()
            r1.offer(r7)
            r1.offer(r3)
            goto L_0x0109
        L_0x0199:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Can not split a box with only 1 color"
            r0.<init>(r1)
            throw r0
        L_0x01a1:
            java.util.ArrayList r2 = new java.util.ArrayList
            int r3 = r1.size()
            r2.<init>(r3)
            java.util.Iterator r1 = r1.iterator()
        L_0x01ae:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x023a
            java.lang.Object r3 = r1.next()
            androidx.palette.graphics.ColorCutQuantizer$Vbox r3 = (androidx.palette.graphics.ColorCutQuantizer.Vbox) r3
            java.util.Objects.requireNonNull(r3)
            androidx.palette.graphics.ColorCutQuantizer r4 = androidx.palette.graphics.ColorCutQuantizer.this
            int[] r6 = r4.mColors
            int[] r4 = r4.mHistogram
            int r7 = r3.mLowerIndex
            r11 = r5
            r12 = r11
            r13 = r12
            r14 = r13
        L_0x01c9:
            int r15 = r3.mUpperIndex
            if (r7 > r15) goto L_0x01ec
            r15 = r6[r7]
            r16 = r4[r15]
            int r12 = r12 + r16
            int r17 = r15 >> 10
            r17 = r17 & 31
            int r17 = r17 * r16
            int r11 = r17 + r11
            int r17 = r15 >> 5
            r17 = r17 & 31
            int r17 = r17 * r16
            int r13 = r17 + r13
            r15 = r15 & 31
            int r16 = r16 * r15
            int r14 = r16 + r14
            int r7 = r7 + 1
            goto L_0x01c9
        L_0x01ec:
            float r3 = (float) r11
            float r4 = (float) r12
            float r3 = r3 / r4
            int r3 = java.lang.Math.round(r3)
            float r6 = (float) r13
            float r6 = r6 / r4
            int r6 = java.lang.Math.round(r6)
            float r7 = (float) r14
            float r7 = r7 / r4
            int r4 = java.lang.Math.round(r7)
            androidx.palette.graphics.Palette$Swatch r7 = new androidx.palette.graphics.Palette$Swatch
            int r3 = modifyWordWidth(r3, r9, r8)
            int r6 = modifyWordWidth(r6, r9, r8)
            int r4 = modifyWordWidth(r4, r9, r8)
            int r3 = android.graphics.Color.rgb(r3, r6, r4)
            r7.<init>(r3, r12)
            float[] r3 = r7.getHsl()
            androidx.palette.graphics.Palette$Filter[] r4 = r0.mFilters
            if (r4 == 0) goto L_0x0232
            int r6 = r4.length
            if (r6 <= 0) goto L_0x0232
            int r4 = r4.length
            r6 = r5
        L_0x0221:
            if (r6 >= r4) goto L_0x0232
            androidx.palette.graphics.Palette$Filter[] r11 = r0.mFilters
            r11 = r11[r6]
            boolean r11 = r11.isAllowed(r3)
            if (r11 != 0) goto L_0x022f
            r3 = r10
            goto L_0x0233
        L_0x022f:
            int r6 = r6 + 1
            goto L_0x0221
        L_0x0232:
            r3 = r5
        L_0x0233:
            if (r3 != 0) goto L_0x01ae
            r2.add(r7)
            goto L_0x01ae
        L_0x023a:
            r0.mQuantizedColors = r2
        L_0x023c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.palette.graphics.ColorCutQuantizer.<init>(int[], int, androidx.palette.graphics.Palette$Filter[]):void");
    }
}
