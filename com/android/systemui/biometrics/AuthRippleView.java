package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RuntimeShader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.charging.DwellRippleShader;
import com.android.systemui.statusbar.charging.RippleShader;
import java.util.Objects;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView extends View {
    public long alphaInDuration;
    public boolean drawDwell;
    public boolean drawRipple;
    public final long dwellExpandDuration = 1900;
    public PointF dwellOrigin;
    public final Paint dwellPaint;
    public final long dwellPulseDuration = 100;
    public AnimatorSet dwellPulseOutAnimator;
    public float dwellRadius;
    public final DwellRippleShader dwellShader;
    public int lockScreenColorVal = -1;
    public PointF origin;
    public float radius;
    public AnimatorSet retractAnimator;
    public final long retractDuration = 400;
    public final PathInterpolator retractInterpolator = new PathInterpolator(0.05f, 0.93f, 0.1f, 1.0f);
    public final Paint ripplePaint;
    public final RippleShader rippleShader;
    public boolean unlockedRippleInProgress;

    public final void onDraw(Canvas canvas) {
        if (this.drawDwell) {
            float f = (float) 1;
            DwellRippleShader dwellRippleShader = this.dwellShader;
            Objects.requireNonNull(dwellRippleShader);
            DwellRippleShader dwellRippleShader2 = this.dwellShader;
            Objects.requireNonNull(dwellRippleShader2);
            float f2 = (f - dwellRippleShader2.progress) * (f - dwellRippleShader.progress);
            DwellRippleShader dwellRippleShader3 = this.dwellShader;
            Objects.requireNonNull(dwellRippleShader3);
            float f3 = (f - ((f - dwellRippleShader3.progress) * f2)) * this.dwellRadius * 2.0f;
            if (canvas != null) {
                PointF pointF = this.dwellOrigin;
                canvas.drawCircle(pointF.x, pointF.y, f3, this.dwellPaint);
            }
        }
        if (this.drawRipple) {
            float f4 = (float) 1;
            RippleShader rippleShader2 = this.rippleShader;
            Objects.requireNonNull(rippleShader2);
            RippleShader rippleShader3 = this.rippleShader;
            Objects.requireNonNull(rippleShader3);
            float f5 = (f4 - rippleShader3.progress) * (f4 - rippleShader2.progress);
            RippleShader rippleShader4 = this.rippleShader;
            Objects.requireNonNull(rippleShader4);
            float f6 = (f4 - ((f4 - rippleShader4.progress) * f5)) * this.radius * 2.0f;
            if (canvas != null) {
                PointF pointF2 = this.origin;
                canvas.drawCircle(pointF2.x, pointF2.y, f6, this.ripplePaint);
            }
        }
    }

    public final void retractRipple() {
        boolean z;
        boolean z2;
        AnimatorSet animatorSet = this.retractAnimator;
        if (animatorSet != null && animatorSet.isRunning()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            AnimatorSet animatorSet2 = this.dwellPulseOutAnimator;
            if (animatorSet2 != null && animatorSet2.isRunning()) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                DwellRippleShader dwellRippleShader = this.dwellShader;
                Objects.requireNonNull(dwellRippleShader);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{dwellRippleShader.progress, 0.0f});
                ofFloat.setInterpolator(this.retractInterpolator);
                ofFloat.setDuration(this.retractDuration);
                ofFloat.addUpdateListener(new AuthRippleView$retractRipple$retractRippleAnimator$1$1(this));
                ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{255, 0});
                ofInt.setInterpolator(Interpolators.LINEAR);
                ofInt.setDuration(this.retractDuration);
                ofInt.addUpdateListener(new AuthRippleView$retractRipple$retractAlphaAnimator$1$1(this));
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(new Animator[]{ofFloat, ofInt});
                animatorSet3.addListener(new AuthRippleView$retractRipple$1$1(this));
                animatorSet3.start();
                this.retractAnimator = animatorSet3;
            }
        }
    }

    public final void setFingerprintSensorLocation(PointF pointF, float f) {
        RippleShader rippleShader2 = this.rippleShader;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_origin", pointF.x, pointF.y);
        this.origin = pointF;
        float f2 = pointF.x;
        int i = 0;
        float[] fArr = {pointF.y, ((float) getWidth()) - pointF.x, ((float) getHeight()) - pointF.y};
        while (i < 3) {
            float f3 = fArr[i];
            i++;
            f2 = Math.max(f2, f3);
        }
        RippleShader rippleShader3 = this.rippleShader;
        Objects.requireNonNull(rippleShader3);
        rippleShader3.radius = f2;
        rippleShader3.setFloatUniform("in_maxRadius", f2);
        this.radius = f2;
        DwellRippleShader dwellRippleShader = this.dwellShader;
        Objects.requireNonNull(dwellRippleShader);
        dwellRippleShader.setFloatUniform("in_origin", pointF.x, pointF.y);
        this.dwellOrigin = pointF;
        float f4 = f * 1.5f;
        DwellRippleShader dwellRippleShader2 = this.dwellShader;
        Objects.requireNonNull(dwellRippleShader2);
        dwellRippleShader2.maxRadius = f4;
        this.dwellRadius = f4;
    }

    public final void setSensorLocation(PointF pointF) {
        RippleShader rippleShader2 = this.rippleShader;
        Objects.requireNonNull(rippleShader2);
        rippleShader2.setFloatUniform("in_origin", pointF.x, pointF.y);
        this.origin = pointF;
        float f = pointF.x;
        int i = 0;
        float[] fArr = {pointF.y, ((float) getWidth()) - pointF.x, ((float) getHeight()) - pointF.y};
        while (i < 3) {
            float f2 = fArr[i];
            i++;
            f = Math.max(f, f2);
        }
        RippleShader rippleShader3 = this.rippleShader;
        Objects.requireNonNull(rippleShader3);
        rippleShader3.radius = f;
        rippleShader3.setFloatUniform("in_maxRadius", f);
        this.radius = f;
    }

    public AuthRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        RuntimeShader dwellRippleShader = new DwellRippleShader();
        this.dwellShader = dwellRippleShader;
        Paint paint = new Paint();
        this.dwellPaint = paint;
        RuntimeShader rippleShader2 = new RippleShader();
        this.rippleShader = rippleShader2;
        Paint paint2 = new Paint();
        this.ripplePaint = paint2;
        this.dwellOrigin = new PointF();
        this.origin = new PointF();
        rippleShader2.setColor(-1);
        rippleShader2.setProgress(0.0f);
        rippleShader2.setFloatUniform("in_sparkle_strength", 0.4f);
        paint2.setShader(rippleShader2);
        dwellRippleShader.color = -1;
        dwellRippleShader.setColorUniform("in_color", -1);
        dwellRippleShader.setProgress(0.0f);
        dwellRippleShader.setFloatUniform("in_distortion_strength", 0.4f);
        paint.setShader(dwellRippleShader);
        setVisibility(8);
    }
}
