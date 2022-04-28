package androidx.constraintlayout.motion.utils;

public final class VelocityMatrix {
    public float mDRotate;
    public float mDScaleX;
    public float mDScaleY;
    public float mDTranslateX;
    public float mDTranslateY;
    public float mRotate;

    public final void applyTransform(float f, float f2, int i, int i2, float[] fArr) {
        int i3 = i;
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f - 0.5f) * 2.0f;
        float f6 = (f2 - 0.5f) * 2.0f;
        float f7 = f3 + this.mDTranslateX;
        float f8 = f4 + this.mDTranslateY;
        float f9 = (this.mDScaleX * f5) + f7;
        float f10 = (this.mDScaleY * f6) + f8;
        float radians = (float) Math.toRadians((double) this.mRotate);
        float radians2 = (float) Math.toRadians((double) this.mDRotate);
        double d = (double) radians;
        double sin = Math.sin(d);
        double d2 = (double) (((float) i2) * f6);
        double cos = Math.cos(d);
        fArr[0] = (((float) ((sin * ((double) (((float) (-i3)) * f5))) - (Math.cos(d) * d2))) * radians2) + f9;
        fArr[1] = (radians2 * ((float) ((cos * ((double) (((float) i3) * f5))) - (Math.sin(d) * d2)))) + f10;
    }
}
