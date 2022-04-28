package com.android.systemui.util.animation;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* compiled from: MeasurementInput.kt */
public final class MeasurementOutput {
    public int measuredHeight = 0;
    public int measuredWidth = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeasurementOutput)) {
            return false;
        }
        MeasurementOutput measurementOutput = (MeasurementOutput) obj;
        return this.measuredWidth == measurementOutput.measuredWidth && this.measuredHeight == measurementOutput.measuredHeight;
    }

    public final int hashCode() {
        return Integer.hashCode(this.measuredHeight) + (Integer.hashCode(this.measuredWidth) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MeasurementOutput(measuredWidth=");
        m.append(this.measuredWidth);
        m.append(", measuredHeight=");
        return Insets$$ExternalSyntheticOutline0.m11m(m, this.measuredHeight, ')');
    }
}
