package androidx.palette.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.SparseBooleanArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Palette {
    public static final C02891 DEFAULT_FILTER = new Filter() {
        public final boolean isAllowed(float[] fArr) {
            boolean z;
            boolean z2;
            boolean z3;
            if (fArr[2] >= 0.95f) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (fArr[2] <= 0.05f) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    if (fArr[0] < 10.0f || fArr[0] > 37.0f || fArr[1] > 0.82f) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (!z3) {
                        return true;
                    }
                }
            }
            return false;
        }
    };
    public final Swatch mDominantSwatch;
    public final SimpleArrayMap<Target, Swatch> mSelectedSwatches = new SimpleArrayMap<>();
    public final List<Swatch> mSwatches;
    public final List<Target> mTargets;
    public final SparseBooleanArray mUsedColors = new SparseBooleanArray();

    public static final class Builder {
        public final Bitmap mBitmap;
        public final ArrayList mFilters;
        public int mMaxColors = 16;
        public Rect mRegion;
        public int mResizeArea = 12544;
        public int mResizeMaxDimension = -1;
        public final ArrayList mTargets;

        /* JADX WARNING: Removed duplicated region for block: B:64:0x01a7  */
        /* JADX WARNING: Removed duplicated region for block: B:85:0x020b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final androidx.palette.graphics.Palette generate() {
            /*
                r19 = this;
                r0 = r19
                android.graphics.Bitmap r1 = r0.mBitmap
                if (r1 == 0) goto L_0x0229
                int r2 = r0.mResizeArea
                r3 = -4616189618054758400(0xbff0000000000000, double:-1.0)
                if (r2 <= 0) goto L_0x0021
                int r2 = r1.getWidth()
                int r5 = r1.getHeight()
                int r5 = r5 * r2
                int r2 = r0.mResizeArea
                if (r5 <= r2) goto L_0x0038
                double r2 = (double) r2
                double r4 = (double) r5
                double r2 = r2 / r4
                double r3 = java.lang.Math.sqrt(r2)
                goto L_0x0038
            L_0x0021:
                int r2 = r0.mResizeMaxDimension
                if (r2 <= 0) goto L_0x0038
                int r2 = r1.getWidth()
                int r5 = r1.getHeight()
                int r2 = java.lang.Math.max(r2, r5)
                int r5 = r0.mResizeMaxDimension
                if (r2 <= r5) goto L_0x0038
                double r3 = (double) r5
                double r5 = (double) r2
                double r3 = r3 / r5
            L_0x0038:
                r5 = 0
                int r2 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                r5 = 0
                if (r2 > 0) goto L_0x0040
                goto L_0x005a
            L_0x0040:
                int r2 = r1.getWidth()
                double r6 = (double) r2
                double r6 = r6 * r3
                double r6 = java.lang.Math.ceil(r6)
                int r2 = (int) r6
                int r6 = r1.getHeight()
                double r6 = (double) r6
                double r6 = r6 * r3
                double r3 = java.lang.Math.ceil(r6)
                int r3 = (int) r3
                android.graphics.Bitmap r1 = android.graphics.Bitmap.createScaledBitmap(r1, r2, r3, r5)
            L_0x005a:
                android.graphics.Rect r2 = r0.mRegion
                android.graphics.Bitmap r3 = r0.mBitmap
                if (r1 == r3) goto L_0x00ab
                if (r2 == 0) goto L_0x00ab
                int r3 = r1.getWidth()
                double r3 = (double) r3
                android.graphics.Bitmap r6 = r0.mBitmap
                int r6 = r6.getWidth()
                double r6 = (double) r6
                double r3 = r3 / r6
                int r6 = r2.left
                double r6 = (double) r6
                double r6 = r6 * r3
                double r6 = java.lang.Math.floor(r6)
                int r6 = (int) r6
                r2.left = r6
                int r6 = r2.top
                double r6 = (double) r6
                double r6 = r6 * r3
                double r6 = java.lang.Math.floor(r6)
                int r6 = (int) r6
                r2.top = r6
                int r6 = r2.right
                double r6 = (double) r6
                double r6 = r6 * r3
                double r6 = java.lang.Math.ceil(r6)
                int r6 = (int) r6
                int r7 = r1.getWidth()
                int r6 = java.lang.Math.min(r6, r7)
                r2.right = r6
                int r6 = r2.bottom
                double r6 = (double) r6
                double r6 = r6 * r3
                double r3 = java.lang.Math.ceil(r6)
                int r3 = (int) r3
                int r4 = r1.getHeight()
                int r3 = java.lang.Math.min(r3, r4)
                r2.bottom = r3
            L_0x00ab:
                androidx.palette.graphics.ColorCutQuantizer r2 = new androidx.palette.graphics.ColorCutQuantizer
                int r3 = r1.getWidth()
                int r13 = r1.getHeight()
                int r4 = r3 * r13
                int[] r4 = new int[r4]
                r8 = 0
                r10 = 0
                r11 = 0
                r6 = r1
                r7 = r4
                r9 = r3
                r12 = r3
                r6.getPixels(r7, r8, r9, r10, r11, r12, r13)
                android.graphics.Rect r6 = r0.mRegion
                if (r6 != 0) goto L_0x00c8
                goto L_0x00eb
            L_0x00c8:
                int r6 = r6.width()
                android.graphics.Rect r7 = r0.mRegion
                int r7 = r7.height()
                int r8 = r6 * r7
                int[] r8 = new int[r8]
                r9 = r5
            L_0x00d7:
                if (r9 >= r7) goto L_0x00ea
                android.graphics.Rect r10 = r0.mRegion
                int r11 = r10.top
                int r11 = r11 + r9
                int r11 = r11 * r3
                int r10 = r10.left
                int r11 = r11 + r10
                int r10 = r9 * r6
                java.lang.System.arraycopy(r4, r11, r8, r10, r6)
                int r9 = r9 + 1
                goto L_0x00d7
            L_0x00ea:
                r4 = r8
            L_0x00eb:
                int r3 = r0.mMaxColors
                java.util.ArrayList r6 = r0.mFilters
                boolean r6 = r6.isEmpty()
                if (r6 == 0) goto L_0x00f7
                r6 = 0
                goto L_0x0105
            L_0x00f7:
                java.util.ArrayList r6 = r0.mFilters
                int r8 = r6.size()
                androidx.palette.graphics.Palette$Filter[] r8 = new androidx.palette.graphics.Palette.Filter[r8]
                java.lang.Object[] r6 = r6.toArray(r8)
                androidx.palette.graphics.Palette$Filter[] r6 = (androidx.palette.graphics.Palette.Filter[]) r6
            L_0x0105:
                r2.<init>(r4, r3, r6)
                android.graphics.Bitmap r3 = r0.mBitmap
                if (r1 == r3) goto L_0x010f
                r1.recycle()
            L_0x010f:
                java.util.ArrayList r1 = r2.mQuantizedColors
                androidx.palette.graphics.Palette r2 = new androidx.palette.graphics.Palette
                java.util.ArrayList r0 = r0.mTargets
                r2.<init>(r1, r0)
                int r0 = r0.size()
                r1 = r5
            L_0x011d:
                if (r1 >= r0) goto L_0x0223
                java.util.List<androidx.palette.graphics.Target> r3 = r2.mTargets
                java.lang.Object r3 = r3.get(r1)
                androidx.palette.graphics.Target r3 = (androidx.palette.graphics.Target) r3
                java.util.Objects.requireNonNull(r3)
                float[] r4 = r3.mWeights
                int r4 = r4.length
                r6 = 0
                r8 = r5
                r9 = r6
            L_0x0130:
                if (r8 >= r4) goto L_0x013e
                float[] r10 = r3.mWeights
                r10 = r10[r8]
                int r11 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r11 <= 0) goto L_0x013b
                float r9 = r9 + r10
            L_0x013b:
                int r8 = r8 + 1
                goto L_0x0130
            L_0x013e:
                int r4 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x0158
                float[] r4 = r3.mWeights
                int r4 = r4.length
                r8 = r5
            L_0x0146:
                if (r8 >= r4) goto L_0x0158
                float[] r10 = r3.mWeights
                r11 = r10[r8]
                int r11 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
                if (r11 <= 0) goto L_0x0155
                r11 = r10[r8]
                float r11 = r11 / r9
                r10[r8] = r11
            L_0x0155:
                int r8 = r8 + 1
                goto L_0x0146
            L_0x0158:
                androidx.collection.SimpleArrayMap<androidx.palette.graphics.Target, androidx.palette.graphics.Palette$Swatch> r4 = r2.mSelectedSwatches
                java.util.List<androidx.palette.graphics.Palette$Swatch> r8 = r2.mSwatches
                int r8 = r8.size()
                r9 = r5
                r11 = r6
                r10 = 0
            L_0x0163:
                r12 = 1
                if (r9 >= r8) goto L_0x0212
                java.util.List<androidx.palette.graphics.Palette$Swatch> r13 = r2.mSwatches
                java.lang.Object r13 = r13.get(r9)
                androidx.palette.graphics.Palette$Swatch r13 = (androidx.palette.graphics.Palette.Swatch) r13
                float[] r14 = r13.getHsl()
                r15 = r14[r12]
                float[] r7 = r3.mSaturationTargets
                r16 = r7[r5]
                int r15 = (r15 > r16 ? 1 : (r15 == r16 ? 0 : -1))
                r16 = 2
                if (r15 < 0) goto L_0x01a4
                r15 = r14[r12]
                r7 = r7[r16]
                int r7 = (r15 > r7 ? 1 : (r15 == r7 ? 0 : -1))
                if (r7 > 0) goto L_0x01a4
                r7 = r14[r16]
                float[] r15 = r3.mLightnessTargets
                r17 = r15[r5]
                int r7 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
                if (r7 < 0) goto L_0x01a4
                r7 = r14[r16]
                r14 = r15[r16]
                int r7 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
                if (r7 > 0) goto L_0x01a4
                android.util.SparseBooleanArray r7 = r2.mUsedColors
                int r14 = r13.mRgb
                boolean r7 = r7.get(r14)
                if (r7 != 0) goto L_0x01a4
                r7 = r12
                goto L_0x01a5
            L_0x01a4:
                r7 = r5
            L_0x01a5:
                if (r7 == 0) goto L_0x020b
                float[] r7 = r13.getHsl()
                androidx.palette.graphics.Palette$Swatch r14 = r2.mDominantSwatch
                if (r14 == 0) goto L_0x01b2
                int r14 = r14.mPopulation
                goto L_0x01b3
            L_0x01b2:
                r14 = r12
            L_0x01b3:
                float[] r15 = r3.mWeights
                r17 = r15[r5]
                int r17 = (r17 > r6 ? 1 : (r17 == r6 ? 0 : -1))
                r18 = 1065353216(0x3f800000, float:1.0)
                if (r17 <= 0) goto L_0x01cf
                r15 = r15[r5]
                r17 = r7[r12]
                float[] r5 = r3.mSaturationTargets
                r5 = r5[r12]
                float r17 = r17 - r5
                float r5 = java.lang.Math.abs(r17)
                float r5 = r18 - r5
                float r5 = r5 * r15
                goto L_0x01d0
            L_0x01cf:
                r5 = r6
            L_0x01d0:
                float[] r15 = r3.mWeights
                r17 = r15[r12]
                int r17 = (r17 > r6 ? 1 : (r17 == r6 ? 0 : -1))
                if (r17 <= 0) goto L_0x01ea
                r15 = r15[r12]
                r7 = r7[r16]
                float[] r6 = r3.mLightnessTargets
                r6 = r6[r12]
                float r7 = r7 - r6
                float r6 = java.lang.Math.abs(r7)
                float r18 = r18 - r6
                float r18 = r18 * r15
                goto L_0x01ec
            L_0x01ea:
                r18 = 0
            L_0x01ec:
                float[] r6 = r3.mWeights
                r7 = r6[r16]
                r15 = 0
                int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
                if (r7 <= 0) goto L_0x01fe
                r6 = r6[r16]
                int r7 = r13.mPopulation
                float r7 = (float) r7
                float r12 = (float) r14
                float r7 = r7 / r12
                float r7 = r7 * r6
                goto L_0x01ff
            L_0x01fe:
                r7 = r15
            L_0x01ff:
                float r5 = r5 + r18
                float r5 = r5 + r7
                if (r10 == 0) goto L_0x0208
                int r6 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
                if (r6 <= 0) goto L_0x020c
            L_0x0208:
                r11 = r5
                r10 = r13
                goto L_0x020c
            L_0x020b:
                r15 = r6
            L_0x020c:
                int r9 = r9 + 1
                r6 = r15
                r5 = 0
                goto L_0x0163
            L_0x0212:
                if (r10 == 0) goto L_0x021b
                android.util.SparseBooleanArray r5 = r2.mUsedColors
                int r6 = r10.mRgb
                r5.append(r6, r12)
            L_0x021b:
                r4.put(r3, r10)
                int r1 = r1 + 1
                r5 = 0
                goto L_0x011d
            L_0x0223:
                android.util.SparseBooleanArray r0 = r2.mUsedColors
                r0.clear()
                return r2
            L_0x0229:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.palette.graphics.Palette.Builder.generate():androidx.palette.graphics.Palette");
        }

        public Builder(Bitmap bitmap) {
            ArrayList arrayList = new ArrayList();
            this.mTargets = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.mFilters = arrayList2;
            if (!bitmap.isRecycled()) {
                arrayList2.add(Palette.DEFAULT_FILTER);
                this.mBitmap = bitmap;
                arrayList.add(Target.LIGHT_VIBRANT);
                arrayList.add(Target.VIBRANT);
                arrayList.add(Target.DARK_VIBRANT);
                arrayList.add(Target.LIGHT_MUTED);
                arrayList.add(Target.MUTED);
                arrayList.add(Target.DARK_MUTED);
                return;
            }
            throw new IllegalArgumentException("Bitmap is not valid");
        }
    }

    public interface Filter {
        boolean isAllowed(float[] fArr);
    }

    public static final class Swatch {
        public final int mBlue;
        public int mBodyTextColor;
        public boolean mGeneratedTextColors;
        public final int mGreen;
        public float[] mHsl;
        public final int mPopulation;
        public final int mRed;
        public final int mRgb;
        public int mTitleTextColor;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || Swatch.class != obj.getClass()) {
                return false;
            }
            Swatch swatch = (Swatch) obj;
            return this.mPopulation == swatch.mPopulation && this.mRgb == swatch.mRgb;
        }

        public final void ensureTextColorsGenerated() {
            int i;
            int i2;
            if (!this.mGeneratedTextColors) {
                int calculateMinimumAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 4.5f);
                int calculateMinimumAlpha2 = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 3.0f);
                if (calculateMinimumAlpha == -1 || calculateMinimumAlpha2 == -1) {
                    int calculateMinimumAlpha3 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 4.5f);
                    int calculateMinimumAlpha4 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 3.0f);
                    if (calculateMinimumAlpha3 == -1 || calculateMinimumAlpha4 == -1) {
                        if (calculateMinimumAlpha != -1) {
                            i = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha);
                        } else {
                            i = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha3);
                        }
                        this.mBodyTextColor = i;
                        if (calculateMinimumAlpha2 != -1) {
                            i2 = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2);
                        } else {
                            i2 = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha4);
                        }
                        this.mTitleTextColor = i2;
                        this.mGeneratedTextColors = true;
                        return;
                    }
                    this.mBodyTextColor = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha3);
                    this.mTitleTextColor = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha4);
                    this.mGeneratedTextColors = true;
                    return;
                }
                this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2);
                this.mGeneratedTextColors = true;
            }
        }

        public final float[] getHsl() {
            if (this.mHsl == null) {
                this.mHsl = new float[3];
            }
            ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
            return this.mHsl;
        }

        public final int hashCode() {
            return (this.mRgb * 31) + this.mPopulation;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(Swatch.class.getSimpleName());
            sb.append(" [RGB: #");
            sb.append(Integer.toHexString(this.mRgb));
            sb.append(']');
            sb.append(" [HSL: ");
            sb.append(Arrays.toString(getHsl()));
            sb.append(']');
            sb.append(" [Population: ");
            sb.append(this.mPopulation);
            sb.append(']');
            sb.append(" [Title Text: #");
            ensureTextColorsGenerated();
            sb.append(Integer.toHexString(this.mTitleTextColor));
            sb.append(']');
            sb.append(" [Body Text: #");
            ensureTextColorsGenerated();
            sb.append(Integer.toHexString(this.mBodyTextColor));
            sb.append(']');
            return sb.toString();
        }

        public Swatch(int i, int i2) {
            this.mRed = Color.red(i);
            this.mGreen = Color.green(i);
            this.mBlue = Color.blue(i);
            this.mRgb = i;
            this.mPopulation = i2;
        }
    }

    public Palette(ArrayList arrayList, ArrayList arrayList2) {
        this.mSwatches = arrayList;
        this.mTargets = arrayList2;
        int size = arrayList.size();
        int i = Integer.MIN_VALUE;
        Swatch swatch = null;
        for (int i2 = 0; i2 < size; i2++) {
            Swatch swatch2 = this.mSwatches.get(i2);
            Objects.requireNonNull(swatch2);
            int i3 = swatch2.mPopulation;
            if (i3 > i) {
                swatch = swatch2;
                i = i3;
            }
        }
        this.mDominantSwatch = swatch;
    }
}
