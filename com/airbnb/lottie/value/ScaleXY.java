package com.airbnb.lottie.value;

public final class ScaleXY {
    public float scaleX;
    public float scaleY;

    public ScaleXY(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public final String toString() {
        return this.scaleX + "x" + this.scaleY;
    }

    public ScaleXY() {
        this(1.0f, 1.0f);
    }
}
