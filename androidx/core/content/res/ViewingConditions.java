package androidx.core.content.res;

public final class ViewingConditions {
    public static final ViewingConditions DEFAULT;
    public final float mAw;

    /* renamed from: mC */
    public final float f19mC;
    public final float mFl;
    public final float mFlRoot;

    /* renamed from: mN */
    public final float f20mN;
    public final float mNbb;
    public final float mNc;
    public final float mNcb;
    public final float[] mRgbD;

    /* renamed from: mZ */
    public final float f21mZ;

    static {
        float f;
        float[] fArr = CamUtils.WHITE_POINT_D65;
        float yFromLStar = (float) ((((double) CamUtils.yFromLStar()) * 63.66197723675813d) / 100.0d);
        float[][] fArr2 = CamUtils.XYZ_TO_CAM16RGB;
        float f2 = (fArr[2] * fArr2[0][2]) + (fArr[1] * fArr2[0][1]) + (fArr[0] * fArr2[0][0]);
        float f3 = (fArr[2] * fArr2[1][2]) + (fArr[1] * fArr2[1][1]) + (fArr[0] * fArr2[1][0]);
        float f4 = (fArr[2] * fArr2[2][2]) + (fArr[1] * fArr2[2][1]) + (fArr[0] * fArr2[2][0]);
        if (((double) 1.0f) >= 0.9d) {
            f = 0.69f;
        } else {
            f = 0.655f;
        }
        float f5 = f;
        float exp = (1.0f - (((float) Math.exp((double) (((-yFromLStar) - 42.0f) / 92.0f))) * 0.2777778f)) * 1.0f;
        double d = (double) exp;
        if (d > 1.0d) {
            exp = 1.0f;
        } else if (d < 0.0d) {
            exp = 0.0f;
        }
        float[] fArr3 = {(((100.0f / f2) * exp) + 1.0f) - exp, (((100.0f / f3) * exp) + 1.0f) - exp, (((100.0f / f4) * exp) + 1.0f) - exp};
        float f6 = 1.0f / ((5.0f * yFromLStar) + 1.0f);
        float f7 = f6 * f6 * f6 * f6;
        float f8 = 1.0f - f7;
        float cbrt = (0.1f * f8 * f8 * ((float) Math.cbrt(((double) yFromLStar) * 5.0d))) + (f7 * yFromLStar);
        float yFromLStar2 = CamUtils.yFromLStar() / fArr[1];
        double d2 = (double) yFromLStar2;
        float sqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float pow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr4 = {(float) Math.pow(((double) ((fArr3[0] * cbrt) * f2)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr3[1] * cbrt) * f3)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr3[2] * cbrt) * f4)) / 100.0d, 0.42d)};
        float[] fArr5 = {(fArr4[0] * 400.0f) / (fArr4[0] + 27.13f), (fArr4[1] * 400.0f) / (fArr4[1] + 27.13f), (fArr4[2] * 400.0f) / (fArr4[2] + 27.13f)};
        DEFAULT = new ViewingConditions(yFromLStar2, ((fArr5[2] * 0.05f) + (fArr5[0] * 2.0f) + fArr5[1]) * pow, pow, pow, f5, 1.0f, fArr3, cbrt, (float) Math.pow((double) cbrt, 0.25d), sqrt);
    }

    public ViewingConditions(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.f20mN = f;
        this.mAw = f2;
        this.mNbb = f3;
        this.mNcb = f4;
        this.f19mC = f5;
        this.mNc = f6;
        this.mRgbD = fArr;
        this.mFl = f7;
        this.mFlRoot = f8;
        this.f21mZ = f9;
    }
}
