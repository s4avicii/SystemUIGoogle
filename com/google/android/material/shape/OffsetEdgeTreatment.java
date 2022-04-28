package com.google.android.material.shape;

import androidx.leanback.R$fraction;

public final class OffsetEdgeTreatment extends R$fraction {
    public final float offset;
    public final R$fraction other;

    public final boolean forceIntersection() {
        return this.other.forceIntersection();
    }

    public final void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        this.other.getEdgePath(f, f2 - this.offset, f3, shapePath);
    }

    public OffsetEdgeTreatment(MarkerEdgeTreatment markerEdgeTreatment, float f) {
        this.other = markerEdgeTreatment;
        this.offset = f;
    }
}
