package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AbsoluteCornerSize implements CornerSize {
    public final float size;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbsoluteCornerSize)) {
            return false;
        }
        return this.size == ((AbsoluteCornerSize) obj).size;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.size)});
    }

    public AbsoluteCornerSize(float f) {
        this.size = f;
    }

    public final float getCornerSize(RectF rectF) {
        return this.size;
    }
}
