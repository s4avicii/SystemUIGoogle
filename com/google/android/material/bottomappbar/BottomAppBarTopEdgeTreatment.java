package com.google.android.material.bottomappbar;

import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.leanback.R$fraction;
import com.google.android.material.shape.ShapePath;

public final class BottomAppBarTopEdgeTreatment extends R$fraction implements Cloneable {
    public float cradleVerticalOffset;
    public float fabCornerSize = -1.0f;
    public float fabDiameter;
    public float fabMargin;
    public float horizontalOffset;
    public float roundedCornerRadius;

    public final void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        boolean z;
        float f4;
        float f5;
        float f6 = f;
        float f7 = f3;
        ShapePath shapePath2 = shapePath;
        float f8 = this.fabDiameter;
        if (f8 == 0.0f) {
            shapePath2.lineTo(f6, 0.0f);
            return;
        }
        float f9 = ((this.fabMargin * 2.0f) + f8) / 2.0f;
        float f10 = f7 * this.roundedCornerRadius;
        float f11 = f2 + this.horizontalOffset;
        float m = MotionController$$ExternalSyntheticOutline0.m7m(1.0f, f7, f9, this.cradleVerticalOffset * f7);
        if (m / f9 >= 1.0f) {
            shapePath2.lineTo(f6, 0.0f);
            return;
        }
        float f12 = this.fabCornerSize;
        float f13 = f12 * f7;
        if (f12 == -1.0f || Math.abs((f12 * 2.0f) - f8) < 0.1f) {
            z = true;
        } else {
            z = false;
        }
        boolean z2 = z;
        if (!z2) {
            f5 = 1.75f;
            f4 = 0.0f;
        } else {
            f4 = m;
            f5 = 0.0f;
        }
        float f14 = f9 + f10;
        float f15 = f4 + f10;
        float sqrt = (float) Math.sqrt((double) ((f14 * f14) - (f15 * f15)));
        float f16 = f11 - sqrt;
        float f17 = f11 + sqrt;
        float degrees = (float) Math.toDegrees(Math.atan((double) (sqrt / f15)));
        float f18 = (90.0f - degrees) + f5;
        shapePath2.lineTo(f16, 0.0f);
        float f19 = f10 * 2.0f;
        float f20 = degrees;
        shapePath.addArc(f16 - f10, 0.0f, f16 + f10, f19, 270.0f, degrees);
        if (z2) {
            shapePath.addArc(f11 - f9, (-f9) - f4, f11 + f9, f9 - f4, 180.0f - f18, (f18 * 2.0f) - 180.0f);
        } else {
            float f21 = this.fabMargin;
            float f22 = f13 * 2.0f;
            float f23 = f11 - f9;
            float f24 = f13 + f21;
            shapePath.addArc(f23, -f24, f23 + f21 + f22, f24, 180.0f - f18, ((f18 * 2.0f) - 180.0f) / 2.0f);
            float f25 = f11 + f9;
            float f26 = this.fabMargin;
            shapePath2.lineTo(f25 - ((f26 / 2.0f) + f13), f26 + f13);
            float f27 = this.fabMargin;
            float f28 = f13 + f27;
            shapePath.addArc(f25 - (f22 + f27), -f28, f25, f28, 90.0f, f18 - 0.049804688f);
        }
        shapePath.addArc(f17 - f10, 0.0f, f17 + f10, f19, 270.0f - f20, f20);
        shapePath2.lineTo(f6, 0.0f);
    }

    public BottomAppBarTopEdgeTreatment(float f, float f2, float f3) {
        this.fabMargin = f;
        this.roundedCornerRadius = f2;
        if (f3 >= 0.0f) {
            this.cradleVerticalOffset = f3;
            this.horizontalOffset = 0.0f;
            return;
        }
        throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
    }
}
