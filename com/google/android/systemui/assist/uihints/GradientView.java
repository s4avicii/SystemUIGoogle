package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.internal.graphics.ColorUtils;

public final class GradientView extends View {
    public int[] mColors = new int[100];
    public final Paint mGradientPaint;
    public final PathInterpolator mInterpolator = new PathInterpolator(0.5f, 0.5f, 0.7f, 1.0f);
    public final float[] mStops;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GradientView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        Paint paint = new Paint();
        this.mGradientPaint = paint;
        paint.setDither(true);
        this.mStops = new float[100];
        for (int i = 0; i < 100; i++) {
            this.mStops[i] = ((float) i) / 100.0f;
        }
        updateGradient();
    }

    public final void updateGradient() {
        for (int i = 0; i < 100; i++) {
            this.mColors[i] = ColorUtils.blendARGB(0, 0, this.mInterpolator.getInterpolation(this.mStops[i]));
        }
        this.mGradientPaint.setShader(new LinearGradient(0.0f, (float) getBottom(), 0.0f, (float) getTop(), this.mColors, this.mStops, Shader.TileMode.CLAMP));
        invalidate();
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect((float) getLeft(), (float) getTop(), (float) getWidth(), (float) getHeight(), this.mGradientPaint);
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateGradient();
    }
}
