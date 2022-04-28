package com.android.systemui.p006qs;

import android.graphics.Path;
import android.view.animation.BaseInterpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;

/* renamed from: com.android.systemui.qs.PathInterpolatorBuilder */
public final class PathInterpolatorBuilder {
    public float[] mDist;

    /* renamed from: mX */
    public float[] f68mX;

    /* renamed from: mY */
    public float[] f69mY;

    /* renamed from: com.android.systemui.qs.PathInterpolatorBuilder$PathInterpolator */
    public static class PathInterpolator extends BaseInterpolator {

        /* renamed from: mX */
        public final float[] f70mX;

        /* renamed from: mY */
        public final float[] f71mY;

        public final float getInterpolation(float f) {
            if (f <= 0.0f) {
                return 0.0f;
            }
            if (f >= 1.0f) {
                return 1.0f;
            }
            int i = 0;
            int length = this.f70mX.length - 1;
            while (length - i > 1) {
                int i2 = (i + length) / 2;
                if (f < this.f70mX[i2]) {
                    length = i2;
                } else {
                    i = i2;
                }
            }
            float[] fArr = this.f70mX;
            float f2 = fArr[length] - fArr[i];
            if (f2 == 0.0f) {
                return this.f71mY[i];
            }
            float[] fArr2 = this.f71mY;
            float f3 = fArr2[i];
            return MotionController$$ExternalSyntheticOutline0.m7m(fArr2[length], f3, (f - fArr[i]) / f2, f3);
        }

        public PathInterpolator(float[] fArr, float[] fArr2) {
            this.f70mX = fArr;
            this.f71mY = fArr2;
        }
    }

    public PathInterpolatorBuilder() {
        Path path = new Path();
        float f = 0.0f;
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        float[] approximate = path.approximate(0.002f);
        int length = approximate.length / 3;
        if (approximate[1] == 0.0f && approximate[2] == 0.0f && approximate[approximate.length - 2] == 1.0f && approximate[approximate.length - 1] == 1.0f) {
            this.f68mX = new float[length];
            this.f69mY = new float[length];
            this.mDist = new float[length];
            int i = 0;
            int i2 = 0;
            float f2 = 0.0f;
            while (i < length) {
                int i3 = i2 + 1;
                float f3 = approximate[i2];
                int i4 = i3 + 1;
                float f4 = approximate[i3];
                int i5 = i4 + 1;
                float f5 = approximate[i4];
                if (f3 == f && f4 != f2) {
                    throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
                } else if (f4 >= f2) {
                    float[] fArr = this.f68mX;
                    fArr[i] = f4;
                    float[] fArr2 = this.f69mY;
                    fArr2[i] = f5;
                    if (i > 0) {
                        int i6 = i - 1;
                        float f6 = fArr[i] - fArr[i6];
                        float f7 = fArr2[i] - fArr2[i6];
                        float sqrt = (float) Math.sqrt((double) ((f7 * f7) + (f6 * f6)));
                        float[] fArr3 = this.mDist;
                        fArr3[i] = fArr3[i6] + sqrt;
                    }
                    i++;
                    f = f3;
                    f2 = f4;
                    i2 = i5;
                } else {
                    throw new IllegalArgumentException("The Path cannot loop back on itself.");
                }
            }
            float[] fArr4 = this.mDist;
            float f8 = fArr4[fArr4.length - 1];
            for (int i7 = 0; i7 < length; i7++) {
                float[] fArr5 = this.mDist;
                fArr5[i7] = fArr5[i7] / f8;
            }
            return;
        }
        throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
    }
}
