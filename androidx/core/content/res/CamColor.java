package androidx.core.content.res;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import java.util.Objects;

public final class CamColor {
    public final float mAstar;
    public final float mBstar;
    public final float mChroma;
    public final float mHue;

    /* renamed from: mJ */
    public final float f18mJ;
    public final float mJstar;

    public final int viewed(ViewingConditions viewingConditions) {
        float f;
        ViewingConditions viewingConditions2 = viewingConditions;
        float f2 = this.mChroma;
        if (((double) f2) != 0.0d) {
            double d = (double) this.f18mJ;
            if (d != 0.0d) {
                f = f2 / ((float) Math.sqrt(d / 100.0d));
                Objects.requireNonNull(viewingConditions);
                float pow = (float) Math.pow(((double) f) / Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions2.f20mN), 0.73d), 1.1111111111111112d);
                double d2 = (double) ((this.mHue * 3.1415927f) / 180.0f);
                float pow2 = viewingConditions2.mAw * ((float) Math.pow(((double) this.f18mJ) / 100.0d, (1.0d / ((double) viewingConditions2.f19mC)) / ((double) viewingConditions2.f21mZ)));
                float cos = ((float) (Math.cos(2.0d + d2) + 3.8d)) * 0.25f * 3846.1538f * viewingConditions2.mNc * viewingConditions2.mNcb;
                float f3 = pow2 / viewingConditions2.mNbb;
                float sin = (float) Math.sin(d2);
                float cos2 = (float) Math.cos(d2);
                float f4 = 11.0f * pow * cos2;
                float f5 = (((0.305f + f3) * 23.0f) * pow) / (((pow * 108.0f) * sin) + (f4 + (cos * 23.0f)));
                float f6 = cos2 * f5;
                float f7 = f5 * sin;
                float f8 = f3 * 460.0f;
                float f9 = ((288.0f * f7) + ((451.0f * f6) + f8)) / 1403.0f;
                float f10 = ((f8 - (891.0f * f6)) - (261.0f * f7)) / 1403.0f;
                float f11 = ((f8 - (f6 * 220.0f)) - (f7 * 6300.0f)) / 1403.0f;
                float max = (float) Math.max(0.0d, (((double) Math.abs(f9)) * 27.13d) / (400.0d - ((double) Math.abs(f9))));
                float signum = (100.0f / viewingConditions2.mFl) * Math.signum(f9) * ((float) Math.pow((double) max, 2.380952380952381d));
                float signum2 = (100.0f / viewingConditions2.mFl) * Math.signum(f10) * ((float) Math.pow((double) ((float) Math.max(0.0d, (((double) Math.abs(f10)) * 27.13d) / (400.0d - ((double) Math.abs(f10))))), 2.380952380952381d));
                float signum3 = (100.0f / viewingConditions2.mFl) * Math.signum(f11) * ((float) Math.pow((double) ((float) Math.max(0.0d, (((double) Math.abs(f11)) * 27.13d) / (400.0d - ((double) Math.abs(f11))))), 2.380952380952381d));
                float[] fArr = viewingConditions2.mRgbD;
                float f12 = signum / fArr[0];
                float f13 = signum2 / fArr[1];
                float f14 = signum3 / fArr[2];
                float[][] fArr2 = CamUtils.CAM16RGB_TO_XYZ;
                float f15 = (fArr2[0][2] * f14) + (fArr2[0][1] * f13) + (fArr2[0][0] * f12);
                float f16 = (fArr2[1][2] * f14) + (fArr2[1][1] * f13) + (fArr2[1][0] * f12);
                float f17 = f12 * fArr2[2][0];
                return ColorUtils.XYZToColor((double) f15, (double) f16, (double) ((f14 * fArr2[2][2]) + (f13 * fArr2[2][1]) + f17));
            }
        }
        f = 0.0f;
        Objects.requireNonNull(viewingConditions);
        float pow3 = (float) Math.pow(((double) f) / Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions2.f20mN), 0.73d), 1.1111111111111112d);
        double d22 = (double) ((this.mHue * 3.1415927f) / 180.0f);
        float pow22 = viewingConditions2.mAw * ((float) Math.pow(((double) this.f18mJ) / 100.0d, (1.0d / ((double) viewingConditions2.f19mC)) / ((double) viewingConditions2.f21mZ)));
        float cos3 = ((float) (Math.cos(2.0d + d22) + 3.8d)) * 0.25f * 3846.1538f * viewingConditions2.mNc * viewingConditions2.mNcb;
        float f32 = pow22 / viewingConditions2.mNbb;
        float sin2 = (float) Math.sin(d22);
        float cos22 = (float) Math.cos(d22);
        float f42 = 11.0f * pow3 * cos22;
        float f52 = (((0.305f + f32) * 23.0f) * pow3) / (((pow3 * 108.0f) * sin2) + (f42 + (cos3 * 23.0f)));
        float f62 = cos22 * f52;
        float f72 = f52 * sin2;
        float f82 = f32 * 460.0f;
        float f92 = ((288.0f * f72) + ((451.0f * f62) + f82)) / 1403.0f;
        float f102 = ((f82 - (891.0f * f62)) - (261.0f * f72)) / 1403.0f;
        float f112 = ((f82 - (f62 * 220.0f)) - (f72 * 6300.0f)) / 1403.0f;
        float max2 = (float) Math.max(0.0d, (((double) Math.abs(f92)) * 27.13d) / (400.0d - ((double) Math.abs(f92))));
        float signum4 = (100.0f / viewingConditions2.mFl) * Math.signum(f92) * ((float) Math.pow((double) max2, 2.380952380952381d));
        float signum22 = (100.0f / viewingConditions2.mFl) * Math.signum(f102) * ((float) Math.pow((double) ((float) Math.max(0.0d, (((double) Math.abs(f102)) * 27.13d) / (400.0d - ((double) Math.abs(f102))))), 2.380952380952381d));
        float signum32 = (100.0f / viewingConditions2.mFl) * Math.signum(f112) * ((float) Math.pow((double) ((float) Math.max(0.0d, (((double) Math.abs(f112)) * 27.13d) / (400.0d - ((double) Math.abs(f112))))), 2.380952380952381d));
        float[] fArr3 = viewingConditions2.mRgbD;
        float f122 = signum4 / fArr3[0];
        float f132 = signum22 / fArr3[1];
        float f142 = signum32 / fArr3[2];
        float[][] fArr22 = CamUtils.CAM16RGB_TO_XYZ;
        float f152 = (fArr22[0][2] * f142) + (fArr22[0][1] * f132) + (fArr22[0][0] * f122);
        float f162 = (fArr22[1][2] * f142) + (fArr22[1][1] * f132) + (fArr22[1][0] * f122);
        float f172 = f122 * fArr22[2][0];
        return ColorUtils.XYZToColor((double) f152, (double) f162, (double) ((f142 * fArr22[2][2]) + (f132 * fArr22[2][1]) + f172));
    }

    public static CamColor fromColor(int i) {
        float f;
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float linearized = CamUtils.linearized(Color.red(i));
        float linearized2 = CamUtils.linearized(Color.green(i));
        float linearized3 = CamUtils.linearized(Color.blue(i));
        float[][] fArr = CamUtils.SRGB_TO_XYZ;
        float[] fArr2 = {(fArr[0][2] * linearized3) + (fArr[0][1] * linearized2) + (fArr[0][0] * linearized), (fArr[1][2] * linearized3) + (fArr[1][1] * linearized2) + (fArr[1][0] * linearized), (linearized3 * fArr[2][2]) + (linearized2 * fArr[2][1]) + (linearized * fArr[2][0])};
        float[][] fArr3 = CamUtils.XYZ_TO_CAM16RGB;
        float f2 = (fArr2[2] * fArr3[0][2]) + (fArr2[1] * fArr3[0][1]) + (fArr2[0] * fArr3[0][0]);
        float f3 = (fArr2[2] * fArr3[1][2]) + (fArr2[1] * fArr3[1][1]) + (fArr2[0] * fArr3[1][0]);
        float f4 = fArr2[0] * fArr3[2][0];
        float f5 = fArr2[2] * fArr3[2][2];
        Objects.requireNonNull(viewingConditions);
        float[] fArr4 = viewingConditions.mRgbD;
        float f6 = fArr4[0] * f2;
        float f7 = fArr4[1] * f3;
        float f8 = fArr4[2] * (f5 + (fArr2[1] * fArr3[2][1]) + f4);
        float pow = (float) Math.pow(((double) (Math.abs(f6) * viewingConditions.mFl)) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow(((double) (Math.abs(f7) * viewingConditions.mFl)) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow(((double) (Math.abs(f8) * viewingConditions.mFl)) / 100.0d, 0.42d);
        float signum = ((Math.signum(f6) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f7) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f8) * 400.0f) * pow3) / (pow3 + 27.13f);
        double d = (double) signum3;
        float f9 = ((float) (((((double) signum2) * -12.0d) + (((double) signum) * 11.0d)) + d)) / 11.0f;
        float f10 = ((float) (((double) (signum + signum2)) - (d * 2.0d))) / 9.0f;
        float f11 = signum2 * 20.0f;
        float f12 = ((21.0f * signum3) + ((signum * 20.0f) + f11)) / 20.0f;
        float f13 = (((signum * 40.0f) + f11) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2((double) f10, (double) f9)) * 180.0f) / 3.1415927f;
        if (atan2 < 0.0f) {
            atan2 += 360.0f;
        } else if (atan2 >= 360.0f) {
            atan2 -= 360.0f;
        }
        float f14 = atan2;
        float f15 = (3.1415927f * f14) / 180.0f;
        float pow4 = ((float) Math.pow((double) ((f13 * viewingConditions.mNbb) / viewingConditions.mAw), (double) (viewingConditions.f19mC * viewingConditions.f21mZ))) * 100.0f;
        Math.sqrt((double) (pow4 / 100.0f));
        if (((double) f14) < 20.14d) {
            f = 360.0f + f14;
        } else {
            f = f14;
        }
        float pow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions.f20mN), 0.73d)) * ((float) Math.pow((double) ((((((((float) (Math.cos(((((double) f) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.mNc) * viewingConditions.mNcb) * ((float) Math.sqrt((double) ((f10 * f10) + (f9 * f9))))) / (f12 + 0.305f)), 0.9d));
        float sqrt = pow5 * ((float) Math.sqrt(((double) pow4) / 100.0d));
        Math.sqrt((double) ((pow5 * viewingConditions.f19mC) / (viewingConditions.mAw + 4.0f)));
        float f16 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) Math.log((double) ((viewingConditions.mFlRoot * sqrt * 0.0228f) + 1.0f))) * 43.85965f;
        double d2 = (double) f15;
        return new CamColor(f14, sqrt, pow4, f16, log * ((float) Math.cos(d2)), log * ((float) Math.sin(d2)));
    }

    public static CamColor fromJch(float f, float f2, float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        Objects.requireNonNull(viewingConditions);
        double d = ((double) f) / 100.0d;
        Math.sqrt(d);
        Math.sqrt((double) (((f2 / ((float) Math.sqrt(d))) * viewingConditions.f19mC) / (viewingConditions.mAw + 4.0f)));
        float f4 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) Math.log((((double) (viewingConditions.mFlRoot * f2)) * 0.0228d) + 1.0d)) * 43.85965f;
        double d2 = (double) ((3.1415927f * f3) / 180.0f);
        return new CamColor(f3, f2, f, f4, log * ((float) Math.cos(d2)), log * ((float) Math.sin(d2)));
    }

    public CamColor(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mHue = f;
        this.mChroma = f2;
        this.f18mJ = f3;
        this.mJstar = f4;
        this.mAstar = f5;
        this.mBstar = f6;
    }
}
