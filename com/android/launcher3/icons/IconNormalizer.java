package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;

public final class IconNormalizer {
    public final RectF mAdaptiveIconBounds = new RectF();
    public float mAdaptiveIconScale;
    public final Bitmap mBitmap;
    public final Rect mBounds = new Rect();
    public final Canvas mCanvas;
    public boolean mEnableShapeDetection;
    public final float[] mLeftBorder;
    public final Matrix mMatrix;
    public final int mMaxSize;
    public final Paint mPaintMaskShape;
    public final Paint mPaintMaskShapeOutline;
    public final byte[] mPixels;
    public final float[] mRightBorder;
    public final Path mShapePath;

    public static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[(fArr.length - 1)];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            if (fArr[i5] > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f2 = ((fArr[i5] - fArr[i4]) / ((float) (i5 - i4))) - f;
                    float f3 = (float) i;
                    if (f2 * f3 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / ((float) (i5 - i4))) - fArr2[i4]) * f3 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / ((float) (i5 - i4));
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = (((float) (i6 - i4)) * f) + fArr[i4];
                }
                i4 = i5;
            }
        }
    }

    public static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        float m = f4 < 0.7853982f ? 0.6597222f : MotionController$$ExternalSyntheticOutline0.m7m(1.0f, f4, 0.040449437f, 0.6510417f);
        float f5 = f / f3;
        if (f5 > m) {
            return (float) Math.sqrt((double) (m / f5));
        }
        return 1.0f;
    }

    @TargetApi(26)
    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        int i2 = 0;
        drawable.setBounds(0, 0, i, i);
        Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect2 = new Rect();
        while (regionIterator.next(rect2)) {
            i2 += rect2.height() * rect2.width();
        }
        if (rectF != null) {
            float f = (float) i;
            rectF.set(((float) bounds.left) / f, ((float) bounds.top) / f, 1.0f - (((float) bounds.right) / f), 1.0f - (((float) bounds.bottom) / f));
        }
        drawable.setBounds(rect);
        float f2 = (float) i2;
        return getScale(f2, f2, (float) (i * i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:72:0x012b, code lost:
        return 1.0f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized float getScale(android.graphics.drawable.Drawable r17, android.graphics.RectF r18, android.graphics.Path r19, boolean[] r20) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            r2 = r18
            r3 = r20
            monitor-enter(r16)
            boolean r4 = r0 instanceof android.graphics.drawable.AdaptiveIconDrawable     // Catch:{ all -> 0x012c }
            r5 = 0
            if (r4 == 0) goto L_0x0029
            float r3 = r1.mAdaptiveIconScale     // Catch:{ all -> 0x012c }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x001e
            int r3 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            android.graphics.RectF r4 = r1.mAdaptiveIconBounds     // Catch:{ all -> 0x012c }
            float r0 = normalizeAdaptiveIcon(r0, r3, r4)     // Catch:{ all -> 0x012c }
            r1.mAdaptiveIconScale = r0     // Catch:{ all -> 0x012c }
        L_0x001e:
            if (r2 == 0) goto L_0x0025
            android.graphics.RectF r0 = r1.mAdaptiveIconBounds     // Catch:{ all -> 0x012c }
            r2.set(r0)     // Catch:{ all -> 0x012c }
        L_0x0025:
            float r0 = r1.mAdaptiveIconScale     // Catch:{ all -> 0x012c }
            monitor-exit(r16)
            return r0
        L_0x0029:
            int r4 = r17.getIntrinsicWidth()     // Catch:{ all -> 0x012c }
            int r6 = r17.getIntrinsicHeight()     // Catch:{ all -> 0x012c }
            if (r4 <= 0) goto L_0x0048
            if (r6 > 0) goto L_0x0036
            goto L_0x0048
        L_0x0036:
            int r7 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            if (r4 > r7) goto L_0x003c
            if (r6 <= r7) goto L_0x0058
        L_0x003c:
            int r7 = java.lang.Math.max(r4, r6)     // Catch:{ all -> 0x012c }
            int r8 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            int r4 = r4 * r8
            int r4 = r4 / r7
            int r8 = r8 * r6
            int r6 = r8 / r7
            goto L_0x0058
        L_0x0048:
            if (r4 <= 0) goto L_0x004e
            int r7 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            if (r4 <= r7) goto L_0x0050
        L_0x004e:
            int r4 = r1.mMaxSize     // Catch:{ all -> 0x012c }
        L_0x0050:
            if (r6 <= 0) goto L_0x0056
            int r7 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            if (r6 <= r7) goto L_0x0058
        L_0x0056:
            int r6 = r1.mMaxSize     // Catch:{ all -> 0x012c }
        L_0x0058:
            android.graphics.Bitmap r7 = r1.mBitmap     // Catch:{ all -> 0x012c }
            r8 = 0
            r7.eraseColor(r8)     // Catch:{ all -> 0x012c }
            r0.setBounds(r8, r8, r4, r6)     // Catch:{ all -> 0x012c }
            android.graphics.Canvas r7 = r1.mCanvas     // Catch:{ all -> 0x012c }
            r0.draw(r7)     // Catch:{ all -> 0x012c }
            byte[] r0 = r1.mPixels     // Catch:{ all -> 0x012c }
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.wrap(r0)     // Catch:{ all -> 0x012c }
            r0.rewind()     // Catch:{ all -> 0x012c }
            android.graphics.Bitmap r7 = r1.mBitmap     // Catch:{ all -> 0x012c }
            r7.copyPixelsToBuffer(r0)     // Catch:{ all -> 0x012c }
            int r0 = r1.mMaxSize     // Catch:{ all -> 0x012c }
            int r7 = r0 + 1
            int r0 = r0 - r4
            r10 = r8
            r14 = r10
            r11 = -1
            r12 = -1
            r13 = -1
        L_0x007e:
            if (r10 >= r6) goto L_0x00bd
            r15 = r8
            r5 = -1
            r8 = -1
        L_0x0083:
            if (r15 >= r4) goto L_0x009b
            byte[] r9 = r1.mPixels     // Catch:{ all -> 0x012c }
            byte r9 = r9[r14]     // Catch:{ all -> 0x012c }
            r9 = r9 & 255(0xff, float:3.57E-43)
            r3 = 40
            if (r9 <= r3) goto L_0x0094
            r3 = -1
            if (r5 != r3) goto L_0x0093
            r5 = r15
        L_0x0093:
            r8 = r15
        L_0x0094:
            int r14 = r14 + 1
            int r15 = r15 + 1
            r3 = r20
            goto L_0x0083
        L_0x009b:
            int r14 = r14 + r0
            float[] r3 = r1.mLeftBorder     // Catch:{ all -> 0x012c }
            float r9 = (float) r5     // Catch:{ all -> 0x012c }
            r3[r10] = r9     // Catch:{ all -> 0x012c }
            float[] r3 = r1.mRightBorder     // Catch:{ all -> 0x012c }
            float r9 = (float) r8     // Catch:{ all -> 0x012c }
            r3[r10] = r9     // Catch:{ all -> 0x012c }
            r3 = -1
            if (r5 == r3) goto L_0x00b6
            if (r11 != r3) goto L_0x00ac
            r11 = r10
        L_0x00ac:
            int r3 = java.lang.Math.min(r7, r5)     // Catch:{ all -> 0x012c }
            int r12 = java.lang.Math.max(r12, r8)     // Catch:{ all -> 0x012c }
            r7 = r3
            r13 = r10
        L_0x00b6:
            int r10 = r10 + 1
            r3 = r20
            r5 = 0
            r8 = 0
            goto L_0x007e
        L_0x00bd:
            r0 = 1065353216(0x3f800000, float:1.0)
            r3 = -1
            if (r11 == r3) goto L_0x012a
            if (r12 != r3) goto L_0x00c5
            goto L_0x012a
        L_0x00c5:
            float[] r5 = r1.mLeftBorder     // Catch:{ all -> 0x012c }
            r8 = 1
            convertToConvexArray(r5, r8, r11, r13)     // Catch:{ all -> 0x012c }
            float[] r5 = r1.mRightBorder     // Catch:{ all -> 0x012c }
            convertToConvexArray(r5, r3, r11, r13)     // Catch:{ all -> 0x012c }
            r3 = 0
            r5 = 0
        L_0x00d2:
            if (r3 >= r6) goto L_0x00ec
            float[] r9 = r1.mLeftBorder     // Catch:{ all -> 0x012c }
            r10 = r9[r3]     // Catch:{ all -> 0x012c }
            r14 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r10 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r10 > 0) goto L_0x00df
            goto L_0x00e9
        L_0x00df:
            float[] r10 = r1.mRightBorder     // Catch:{ all -> 0x012c }
            r10 = r10[r3]     // Catch:{ all -> 0x012c }
            r9 = r9[r3]     // Catch:{ all -> 0x012c }
            float r10 = r10 - r9
            float r10 = r10 + r0
            float r10 = r10 + r5
            r5 = r10
        L_0x00e9:
            int r3 = r3 + 1
            goto L_0x00d2
        L_0x00ec:
            android.graphics.Rect r3 = r1.mBounds     // Catch:{ all -> 0x012c }
            r3.left = r7     // Catch:{ all -> 0x012c }
            r3.right = r12     // Catch:{ all -> 0x012c }
            r3.top = r11     // Catch:{ all -> 0x012c }
            r3.bottom = r13     // Catch:{ all -> 0x012c }
            if (r2 == 0) goto L_0x0108
            float r3 = (float) r7     // Catch:{ all -> 0x012c }
            float r9 = (float) r4     // Catch:{ all -> 0x012c }
            float r3 = r3 / r9
            float r10 = (float) r11     // Catch:{ all -> 0x012c }
            float r14 = (float) r6     // Catch:{ all -> 0x012c }
            float r10 = r10 / r14
            float r15 = (float) r12     // Catch:{ all -> 0x012c }
            float r15 = r15 / r9
            float r9 = r0 - r15
            float r15 = (float) r13     // Catch:{ all -> 0x012c }
            float r15 = r15 / r14
            float r0 = r0 - r15
            r2.set(r3, r10, r9, r0)     // Catch:{ all -> 0x012c }
        L_0x0108:
            r0 = r20
            if (r0 == 0) goto L_0x011c
            boolean r2 = r1.mEnableShapeDetection     // Catch:{ all -> 0x012c }
            if (r2 == 0) goto L_0x011c
            int r2 = r0.length     // Catch:{ all -> 0x012c }
            if (r2 <= 0) goto L_0x011c
            r2 = r19
            boolean r2 = r1.isShape(r2)     // Catch:{ all -> 0x012c }
            r3 = 0
            r0[r3] = r2     // Catch:{ all -> 0x012c }
        L_0x011c:
            int r13 = r13 + r8
            int r13 = r13 - r11
            int r12 = r12 + r8
            int r12 = r12 - r7
            int r12 = r12 * r13
            float r0 = (float) r12     // Catch:{ all -> 0x012c }
            int r4 = r4 * r6
            float r2 = (float) r4     // Catch:{ all -> 0x012c }
            float r0 = getScale(r5, r0, r2)     // Catch:{ all -> 0x012c }
            monitor-exit(r16)
            return r0
        L_0x012a:
            monitor-exit(r16)
            return r0
        L_0x012c:
            r0 = move-exception
            monitor-exit(r16)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.IconNormalizer.getScale(android.graphics.drawable.Drawable, android.graphics.RectF, android.graphics.Path, boolean[]):float");
    }

    public final boolean isShape(Path path) {
        Rect rect;
        if (Math.abs((((float) this.mBounds.width()) / ((float) this.mBounds.height())) - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale((float) this.mBounds.width(), (float) this.mBounds.height());
        Matrix matrix = this.mMatrix;
        Rect rect2 = this.mBounds;
        matrix.postTranslate((float) rect2.left, (float) rect2.top);
        path.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        Rect rect3 = this.mBounds;
        int i = rect3.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - rect3.right;
        int i5 = 0;
        while (true) {
            rect = this.mBounds;
            if (i >= rect.bottom) {
                break;
            }
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < this.mBounds.right) {
                if ((this.mPixels[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        if (((float) i5) / ((float) (this.mBounds.height() * rect.width())) < 0.005f) {
            return true;
        }
        return false;
    }

    public IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[(i2 * i2)];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16777216);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = z;
    }
}
