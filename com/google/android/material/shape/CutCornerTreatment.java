package com.google.android.material.shape;

import androidx.leanback.R$drawable;

public final class CutCornerTreatment extends R$drawable {
    public final void getCornerPath(ShapePath shapePath, float f, float f2) {
        shapePath.reset(0.0f, f2 * f, 180.0f, 90.0f);
        double d = (double) f2;
        double d2 = (double) f;
        shapePath.lineTo((float) (Math.sin(Math.toRadians((double) 90.0f)) * d * d2), (float) (Math.sin(Math.toRadians((double) 0.0f)) * d * d2));
    }
}
