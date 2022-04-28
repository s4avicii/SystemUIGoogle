package com.android.systemui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.hardware.graphics.common.DisplayDecorationSupport;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScreenDecorHwcLayer.kt */
public final class ScreenDecorHwcLayer extends DisplayCutoutBaseView {
    public static final boolean DEBUG_COLOR = ScreenDecorations.DEBUG_COLOR;
    public final int bgColor;
    public final Paint clearPaint;
    public final int color;
    public final PorterDuffColorFilter cornerBgFilter;
    public final PorterDuffColorFilter cornerFilter;
    public int roundedCornerBottomSize;
    public Drawable roundedCornerDrawableBottom;
    public Drawable roundedCornerDrawableTop;
    public int roundedCornerTopSize;
    public final boolean useInvertedAlphaColor;

    public final void drawRoundedCorner(Canvas canvas, Drawable drawable, int i) {
        if (this.useInvertedAlphaColor) {
            float f = (float) i;
            canvas.drawRect(0.0f, 0.0f, f, f, this.clearPaint);
            if (drawable != null) {
                drawable.setColorFilter(this.cornerBgFilter);
            }
        } else if (drawable != null) {
            drawable.setColorFilter(this.cornerFilter);
        }
        if (drawable != null) {
            drawable.draw(canvas);
        }
        if (drawable != null) {
            drawable.clearColorFilter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDraw(android.graphics.Canvas r10) {
        /*
            r9 = this;
            boolean r0 = r9.useInvertedAlphaColor
            if (r0 == 0) goto L_0x0009
            int r0 = r9.bgColor
            r10.drawColor(r0)
        L_0x0009:
            super.onDraw(r10)
            int r0 = r9.roundedCornerTopSize
            if (r0 != 0) goto L_0x0016
            int r0 = r9.roundedCornerBottomSize
            if (r0 != 0) goto L_0x0016
            goto L_0x009a
        L_0x0016:
            r0 = 0
            r1 = r0
        L_0x0018:
            r2 = 4
            if (r1 >= r2) goto L_0x009a
            int r2 = r1 + 1
            r10.save()
            int r3 = r1 * 90
            int r4 = r9.displayRotation
            r5 = 90
            int r4 = r4 * r5
            int r3 = r3 - r4
            int r3 = r3 + 360
            int r3 = r3 % 360
            float r4 = (float) r3
            r10.rotate(r4)
            java.lang.String r4 = "Incorrect degree: "
            r6 = 270(0x10e, float:3.78E-43)
            r7 = 180(0xb4, float:2.52E-43)
            if (r3 == 0) goto L_0x0057
            if (r3 == r5) goto L_0x0057
            if (r3 == r7) goto L_0x0051
            if (r3 != r6) goto L_0x0043
            int r8 = r9.getHeight()
            goto L_0x0055
        L_0x0043:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.Integer r10 = java.lang.Integer.valueOf(r3)
            java.lang.String r10 = kotlin.jvm.internal.Intrinsics.stringPlus(r4, r10)
            r9.<init>(r10)
            throw r9
        L_0x0051:
            int r8 = r9.getWidth()
        L_0x0055:
            int r8 = -r8
            goto L_0x0058
        L_0x0057:
            r8 = r0
        L_0x0058:
            float r8 = (float) r8
            if (r3 == 0) goto L_0x007b
            if (r3 == r5) goto L_0x0075
            if (r3 == r7) goto L_0x0070
            if (r3 != r6) goto L_0x0062
            goto L_0x007b
        L_0x0062:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.Integer r10 = java.lang.Integer.valueOf(r3)
            java.lang.String r10 = kotlin.jvm.internal.Intrinsics.stringPlus(r4, r10)
            r9.<init>(r10)
            throw r9
        L_0x0070:
            int r3 = r9.getHeight()
            goto L_0x0079
        L_0x0075:
            int r3 = r9.getWidth()
        L_0x0079:
            int r3 = -r3
            goto L_0x007c
        L_0x007b:
            r3 = r0
        L_0x007c:
            float r3 = (float) r3
            r10.translate(r8, r3)
            if (r1 == 0) goto L_0x008d
            r3 = 1
            if (r1 == r3) goto L_0x008d
            android.graphics.drawable.Drawable r1 = r9.roundedCornerDrawableBottom
            int r3 = r9.roundedCornerBottomSize
            r9.drawRoundedCorner(r10, r1, r3)
            goto L_0x0094
        L_0x008d:
            android.graphics.drawable.Drawable r1 = r9.roundedCornerDrawableTop
            int r3 = r9.roundedCornerTopSize
            r9.drawRoundedCorner(r10, r1, r3)
        L_0x0094:
            r10.restore()
            r1 = r2
            goto L_0x0018
        L_0x009a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorHwcLayer.onDraw(android.graphics.Canvas):void");
    }

    public final void updateRoundedCornerDrawableBounds() {
        Drawable drawable = this.roundedCornerDrawableTop;
        if (!(drawable == null || drawable == null)) {
            int i = this.roundedCornerTopSize;
            drawable.setBounds(0, 0, i, i);
        }
        Drawable drawable2 = this.roundedCornerDrawableBottom;
        if (!(drawable2 == null || drawable2 == null)) {
            int i2 = this.roundedCornerBottomSize;
            drawable2.setBounds(0, 0, i2, i2);
        }
        invalidate();
    }

    public ScreenDecorHwcLayer(Context context, DisplayDecorationSupport displayDecorationSupport) {
        super(context);
        boolean z;
        if (displayDecorationSupport.format == 56) {
            if (DEBUG_COLOR) {
                this.color = -16711936;
                this.bgColor = 0;
                this.useInvertedAlphaColor = false;
            } else {
                if (displayDecorationSupport.alphaInterpretation == 0) {
                    z = true;
                } else {
                    z = false;
                }
                this.useInvertedAlphaColor = z;
                if (z) {
                    this.color = 0;
                    this.bgColor = -16777216;
                } else {
                    this.color = -16777216;
                    this.bgColor = 0;
                }
            }
            this.cornerFilter = new PorterDuffColorFilter(this.color, PorterDuff.Mode.SRC_IN);
            this.cornerBgFilter = new PorterDuffColorFilter(this.bgColor, PorterDuff.Mode.SRC_OUT);
            Paint paint = new Paint();
            this.clearPaint = paint;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return;
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Attempting to use unsupported mode ", PixelFormat.formatToString(displayDecorationSupport.format)));
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewRootImpl().setDisplayDecoration(true);
        if (this.useInvertedAlphaColor) {
            this.paint.set(this.clearPaint);
            return;
        }
        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.FILL);
    }
}
