package com.google.android.setupdesign;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.lang.ref.SoftReference;

public final class GlifPatternDrawable extends Drawable {
    public static SoftReference<Bitmap> bitmapCache;
    public static int[] patternLightness;
    public static Path[] patternPaths;
    public int color;
    public final Paint tempPaint = new Paint(1);

    public static void invalidatePattern() {
        bitmapCache = null;
    }

    public final int getOpacity() {
        return 0;
    }

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public Bitmap createBitmapCache(int i, int i2) {
        float min = Math.min(1.5f, Math.max(((float) i) / 1366.0f, ((float) i2) / 768.0f));
        Bitmap createBitmap = Bitmap.createBitmap((int) (min * 1366.0f), (int) (min * 768.0f), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(createBitmap);
        canvas.save();
        canvas.scale(min, min);
        this.tempPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        if (patternPaths == null) {
            Path[] pathArr = new Path[7];
            patternPaths = pathArr;
            patternLightness = new int[]{10, 40, 51, 66, 91, 112, 130};
            Path path = new Path();
            pathArr[0] = path;
            path.moveTo(1029.4f, 357.5f);
            path.lineTo(1366.0f, 759.1f);
            path.lineTo(1366.0f, 0.0f);
            path.lineTo(1137.7f, 0.0f);
            path.close();
            Path[] pathArr2 = patternPaths;
            Path path2 = new Path();
            pathArr2[1] = path2;
            path2.moveTo(1138.1f, 0.0f);
            path2.rLineTo(-144.8f, 768.0f);
            path2.rLineTo(372.7f, 0.0f);
            path2.rLineTo(0.0f, -524.0f);
            path2.cubicTo(1290.7f, 121.6f, 1219.2f, 41.1f, 1178.7f, 0.0f);
            path2.close();
            Path[] pathArr3 = patternPaths;
            Path path3 = new Path();
            pathArr3[2] = path3;
            path3.moveTo(949.8f, 768.0f);
            Path path4 = path3;
            path3.rCubicTo(92.6f, -170.6f, 213.0f, -440.3f, 269.4f, -768.0f);
            path4.lineTo(585.0f, 0.0f);
            path4.rLineTo(2.1f, 766.0f);
            path4.close();
            Path[] pathArr4 = patternPaths;
            Path path5 = new Path();
            pathArr4[3] = path5;
            path5.moveTo(471.1f, 768.0f);
            path5.rMoveTo(704.5f, 0.0f);
            Path path6 = path5;
            path5.cubicTo(1123.6f, 563.3f, 1027.4f, 275.2f, 856.2f, 0.0f);
            path6.lineTo(476.4f, 0.0f);
            path6.rLineTo(-5.3f, 768.0f);
            path6.close();
            Path[] pathArr5 = patternPaths;
            Path path7 = new Path();
            pathArr5[4] = path7;
            path7.moveTo(323.1f, 768.0f);
            path7.moveTo(777.5f, 768.0f);
            Path path8 = path7;
            path7.cubicTo(661.9f, 348.8f, 427.2f, 21.4f, 401.2f, 25.4f);
            path8.lineTo(323.1f, 768.0f);
            path8.close();
            Path[] pathArr6 = patternPaths;
            Path path9 = new Path();
            pathArr6[5] = path9;
            path9.moveTo(178.44286f, 766.8571f);
            path9.lineTo(308.7f, 768.0f);
            path9.cubicTo(381.7f, 604.6f, 481.6f, 344.3f, 562.2f, 0.0f);
            path9.lineTo(0.0f, 0.0f);
            path9.close();
            Path[] pathArr7 = patternPaths;
            Path path10 = new Path();
            pathArr7[6] = path10;
            path10.moveTo(146.0f, 0.0f);
            path10.lineTo(0.0f, 0.0f);
            path10.lineTo(0.0f, 768.0f);
            path10.lineTo(394.2f, 768.0f);
            path10.cubicTo(327.7f, 475.3f, 228.5f, 201.0f, 146.0f, 0.0f);
            path10.close();
        }
        for (int i3 = 0; i3 < 7; i3++) {
            this.tempPaint.setColor(patternLightness[i3] << 24);
            canvas.drawPath(patternPaths[i3], this.tempPaint);
        }
        canvas.restore();
        this.tempPaint.reset();
        return createBitmap;
    }

    public GlifPatternDrawable(int i) {
        this.color = Color.argb(204, Color.red(i), Color.green(i), Color.blue(i));
        invalidateSelf();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
        if (((float) r6) < 1152.0f) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002b, code lost:
        if (((float) r5) < 2049.0f) goto L_0x0039;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void draw(android.graphics.Canvas r9) {
        /*
            r8 = this;
            android.graphics.Rect r0 = r8.getBounds()
            int r1 = r0.width()
            int r2 = r0.height()
            java.lang.ref.SoftReference<android.graphics.Bitmap> r3 = bitmapCache
            r4 = 0
            if (r3 == 0) goto L_0x0018
            java.lang.Object r3 = r3.get()
            android.graphics.Bitmap r3 = (android.graphics.Bitmap) r3
            goto L_0x0019
        L_0x0018:
            r3 = r4
        L_0x0019:
            if (r3 == 0) goto L_0x0038
            int r5 = r3.getWidth()
            int r6 = r3.getHeight()
            if (r1 <= r5) goto L_0x002e
            float r5 = (float) r5
            r7 = 1157632000(0x45001000, float:2049.0)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x002e
            goto L_0x0039
        L_0x002e:
            if (r2 <= r6) goto L_0x0038
            float r5 = (float) r6
            r6 = 1150287872(0x44900000, float:1152.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x0038
            goto L_0x0039
        L_0x0038:
            r4 = r3
        L_0x0039:
            if (r4 != 0) goto L_0x0050
            android.graphics.Paint r3 = r8.tempPaint
            r3.reset()
            android.graphics.Bitmap r4 = r8.createBitmapCache(r1, r2)
            java.lang.ref.SoftReference r1 = new java.lang.ref.SoftReference
            r1.<init>(r4)
            bitmapCache = r1
            android.graphics.Paint r1 = r8.tempPaint
            r1.reset()
        L_0x0050:
            r9.save()
            r9.clipRect(r0)
            r8.scaleCanvasToBounds(r9, r4, r0)
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r9.drawColor(r0)
            android.graphics.Paint r0 = r8.tempPaint
            r1 = -1
            r0.setColor(r1)
            android.graphics.Paint r0 = r8.tempPaint
            r1 = 0
            r9.drawBitmap(r4, r1, r1, r0)
            int r8 = r8.color
            r9.drawColor(r8)
            r9.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.GlifPatternDrawable.draw(android.graphics.Canvas):void");
    }

    public void scaleCanvasToBounds(Canvas canvas, Bitmap bitmap, Rect rect) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = (float) width;
        float width2 = ((float) rect.width()) / f;
        float f2 = (float) height;
        float height2 = ((float) rect.height()) / f2;
        canvas.scale(width2, height2);
        if (height2 > width2) {
            canvas.scale(height2 / width2, 1.0f, f * 0.146f, 0.0f);
        } else if (width2 > height2) {
            canvas.scale(1.0f, width2 / height2, 0.0f, f2 * 0.228f);
        }
    }
}
