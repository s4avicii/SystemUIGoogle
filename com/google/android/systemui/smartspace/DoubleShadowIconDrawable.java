package com.google.android.systemui.smartspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.android.internal.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;

public final class DoubleShadowIconDrawable extends LayerDrawable {
    public BitmapDrawable mIconDrawable;
    public BitmapDrawable mShadowDrawable;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DoubleShadowIconDrawable(Drawable drawable, Context context) {
        super(new Drawable[0]);
        float f;
        Drawable drawable2 = drawable;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(C1777R.dimen.enhanced_smartspace_icon_size);
        Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        float f2 = (float) ((dimensionPixelSize / 2) + ((-dimensionPixelSize) / 2));
        canvas.translate(f2, f2);
        drawable2.draw(canvas);
        this.mIconDrawable = new BitmapDrawable(context.getResources(), createBitmap);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap.getWidth(), createBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        float dimensionPixelSize2 = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.ambient_text_shadow_radius);
        float dimensionPixelSize3 = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.key_text_shadow_radius);
        float dimensionPixelSize4 = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.key_text_shadow_dx);
        float dimensionPixelSize5 = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.key_text_shadow_dy);
        int[] iArr = new int[2];
        Paint paint = new Paint(3);
        Paint paint2 = new Paint(3);
        if (dimensionPixelSize2 != 0.0f) {
            paint.setMaskFilter(new BlurMaskFilter(dimensionPixelSize2, BlurMaskFilter.Blur.NORMAL));
            Bitmap extractAlpha = createBitmap.extractAlpha(paint, iArr);
            paint2.setAlpha(64);
            canvas2.drawBitmap(extractAlpha, (float) iArr[0], (float) iArr[1], paint2);
            f = 0.0f;
        } else {
            f = 0.0f;
        }
        if (dimensionPixelSize3 != f) {
            paint.setMaskFilter(new BlurMaskFilter(dimensionPixelSize3, BlurMaskFilter.Blur.NORMAL));
            Bitmap extractAlpha2 = createBitmap.extractAlpha(paint, iArr);
            paint2.setAlpha(72);
            canvas2.drawBitmap(extractAlpha2, ((float) iArr[0]) + dimensionPixelSize4, ((float) iArr[1]) + dimensionPixelSize5, paint2);
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), createBitmap2);
        this.mShadowDrawable = bitmapDrawable;
        addLayer(bitmapDrawable);
        addLayer(this.mIconDrawable);
        setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
    }

    public final void setTint(int i) {
        int i2;
        BitmapDrawable bitmapDrawable = this.mIconDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.setTint(i);
        }
        BitmapDrawable bitmapDrawable2 = this.mShadowDrawable;
        if (bitmapDrawable2 != null) {
            if (ColorUtils.calculateLuminance(i) > 0.5d) {
                i2 = 255;
            } else {
                i2 = 0;
            }
            bitmapDrawable2.setAlpha(i2);
        }
    }
}
