package com.android.systemui.statusbar.charging;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RuntimeShader;
import android.util.AttributeSet;
import android.view.View;
import java.util.Objects;

/* compiled from: ChargingRippleView.kt */
public final class ChargingRippleView extends View {
    public long duration = 1750;
    public PointF origin = new PointF();
    public float radius;
    public boolean rippleInProgress;
    public final Paint ripplePaint;
    public final RippleShader rippleShader;

    public final void onAttachedToWindow() {
        RippleShader rippleShader2 = this.rippleShader;
        float f = getResources().getDisplayMetrics().density;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_pixelDensity", f);
        super.onAttachedToWindow();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        RippleShader rippleShader2 = this.rippleShader;
        float f = getResources().getDisplayMetrics().density;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_pixelDensity", f);
        super.onConfigurationChanged(configuration);
    }

    public final void onDraw(Canvas canvas) {
        if (canvas != null && canvas.isHardwareAccelerated()) {
            float f = (float) 1;
            RippleShader rippleShader2 = this.rippleShader;
            Objects.requireNonNull(rippleShader2);
            RippleShader rippleShader3 = this.rippleShader;
            Objects.requireNonNull(rippleShader3);
            float f2 = (f - rippleShader3.progress) * (f - rippleShader2.progress);
            RippleShader rippleShader4 = this.rippleShader;
            Objects.requireNonNull(rippleShader4);
            float f3 = (f - ((f - rippleShader4.progress) * f2)) * this.radius * ((float) 2);
            PointF pointF = this.origin;
            canvas.drawCircle(pointF.x, pointF.y, f3, this.ripplePaint);
        }
    }

    public final void startRipple(Runnable runnable) {
        if (!this.rippleInProgress) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.setDuration(this.duration);
            ofFloat.addUpdateListener(new ChargingRippleView$startRipple$1(this));
            ofFloat.addListener(new ChargingRippleView$startRipple$2(this, runnable));
            ofFloat.start();
            this.rippleInProgress = true;
        }
    }

    public ChargingRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        RuntimeShader rippleShader2 = new RippleShader();
        this.rippleShader = rippleShader2;
        Paint paint = new Paint();
        this.ripplePaint = paint;
        rippleShader2.setColor(-1);
        rippleShader2.setProgress(0.0f);
        rippleShader2.setFloatUniform("in_sparkle_strength", 0.3f);
        paint.setShader(rippleShader2);
    }
}
