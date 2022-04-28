package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class RelativeCornerSize implements CornerSize {
    public final float percent;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RelativeCornerSize)) {
            return false;
        }
        return this.percent == ((RelativeCornerSize) obj).percent;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.percent)});
    }

    public final float getCornerSize(RectF rectF) {
        return rectF.height() * this.percent;
    }

    public RelativeCornerSize(float f) {
        this.percent = f;
    }
}
