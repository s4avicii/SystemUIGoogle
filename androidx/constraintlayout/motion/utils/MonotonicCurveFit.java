package androidx.constraintlayout.motion.utils;

import java.lang.reflect.Array;

public final class MonotonicCurveFit extends CurveFit {

    /* renamed from: mT */
    public double[] f9mT;
    public double[][] mTangent;

    /* renamed from: mY */
    public double[][] f10mY;

    public static double diff(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d2 * d2;
        double d8 = d2 * 6.0d;
        double d9 = 6.0d * d7 * d3;
        double d10 = 3.0d * d;
        double d11 = d10 * d5 * d7;
        return (d * d5) + (((d11 + (((d10 * d6) * d7) + ((d9 + ((d8 * d4) + ((-6.0d * d7) * d4))) - (d8 * d3)))) - (((2.0d * d) * d6) * d2)) - (((4.0d * d) * d5) * d2));
    }

    public static double interpolate(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d2 * d2;
        double d8 = d7 * d2;
        double d9 = 3.0d * d7;
        double d10 = d9 * d4;
        double d11 = d * d6;
        double d12 = d11 * d8;
        double d13 = d * d5;
        return (d13 * d2) + ((((d8 * d13) + (d12 + (((((d8 * 2.0d) * d3) + (d10 + ((-2.0d * d8) * d4))) - (d9 * d3)) + d3))) - (d11 * d7)) - (((2.0d * d) * d5) * d7));
    }

    public final void getPos(double d, double[] dArr) {
        double[] dArr2 = this.f9mT;
        int length = dArr2.length;
        int i = 0;
        int length2 = this.f10mY[0].length;
        if (d <= dArr2[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                dArr[i2] = this.f10mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr2[i3]) {
            while (i < length2) {
                dArr[i] = this.f10mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.f9mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    dArr[i5] = this.f10mY[i4][i5];
                }
            }
            double[] dArr3 = this.f9mT;
            int i6 = i4 + 1;
            if (d < dArr3[i6]) {
                double d2 = dArr3[i6] - dArr3[i4];
                double d3 = (d - dArr3[i4]) / d2;
                while (i < length2) {
                    double[][] dArr4 = this.f10mY;
                    double d4 = dArr4[i4][i];
                    double d5 = dArr4[i6][i];
                    double[][] dArr5 = this.mTangent;
                    dArr[i] = interpolate(d2, d3, d4, d5, dArr5[i4][i], dArr5[i6][i]);
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    public final void getSlope(double d, double[] dArr) {
        double d2;
        double[] dArr2 = this.f9mT;
        int length = dArr2.length;
        int length2 = this.f10mY[0].length;
        if (d <= dArr2[0]) {
            d2 = dArr2[0];
        } else {
            int i = length - 1;
            d2 = d >= dArr2[i] ? dArr2[i] : d;
        }
        int i2 = 0;
        while (i2 < length - 1) {
            double[] dArr3 = this.f9mT;
            int i3 = i2 + 1;
            if (d2 <= dArr3[i3]) {
                double d3 = dArr3[i3] - dArr3[i2];
                double d4 = (d2 - dArr3[i2]) / d3;
                for (int i4 = 0; i4 < length2; i4++) {
                    double[][] dArr4 = this.f10mY;
                    double d5 = dArr4[i2][i4];
                    double d6 = dArr4[i3][i4];
                    double[][] dArr5 = this.mTangent;
                    dArr[i4] = diff(d3, d4, d5, d6, dArr5[i2][i4], dArr5[i3][i4]) / d3;
                }
                return;
            }
            i2 = i3;
        }
    }

    public MonotonicCurveFit(double[] dArr, double[][] dArr2) {
        double[] dArr3 = dArr;
        double[][] dArr4 = dArr2;
        Class<double> cls = double.class;
        int length = dArr3.length;
        int length2 = dArr4[0].length;
        int i = length - 1;
        int[] iArr = new int[2];
        iArr[1] = length2;
        iArr[0] = i;
        double[][] dArr5 = (double[][]) Array.newInstance(cls, iArr);
        int[] iArr2 = new int[2];
        iArr2[1] = length2;
        iArr2[0] = length;
        double[][] dArr6 = (double[][]) Array.newInstance(cls, iArr2);
        for (int i2 = 0; i2 < length2; i2++) {
            int i3 = 0;
            while (i3 < i) {
                int i4 = i3 + 1;
                dArr5[i3][i2] = (dArr4[i4][i2] - dArr4[i3][i2]) / (dArr3[i4] - dArr3[i3]);
                if (i3 == 0) {
                    dArr6[i3][i2] = dArr5[i3][i2];
                } else {
                    dArr6[i3][i2] = (dArr5[i3 - 1][i2] + dArr5[i3][i2]) * 0.5d;
                }
                i3 = i4;
            }
            dArr6[i][i2] = dArr5[length - 2][i2];
        }
        for (int i5 = 0; i5 < i; i5++) {
            for (int i6 = 0; i6 < length2; i6++) {
                if (dArr5[i5][i6] == 0.0d) {
                    dArr6[i5][i6] = 0.0d;
                    dArr6[i5 + 1][i6] = 0.0d;
                } else {
                    double d = dArr6[i5][i6] / dArr5[i5][i6];
                    int i7 = i5 + 1;
                    double d2 = dArr6[i7][i6] / dArr5[i5][i6];
                    double hypot = Math.hypot(d, d2);
                    if (hypot > 9.0d) {
                        double d3 = 3.0d / hypot;
                        dArr6[i5][i6] = d * d3 * dArr5[i5][i6];
                        dArr6[i7][i6] = d3 * d2 * dArr5[i5][i6];
                    }
                }
            }
        }
        this.f9mT = dArr3;
        this.f10mY = dArr4;
        this.mTangent = dArr6;
    }

    public final double getSlope(double d) {
        double d2;
        double[] dArr = this.f9mT;
        int length = dArr.length;
        if (d < dArr[0]) {
            d2 = dArr[0];
        } else {
            int i = length - 1;
            d2 = d >= dArr[i] ? dArr[i] : d;
        }
        int i2 = 0;
        while (i2 < length - 1) {
            double[] dArr2 = this.f9mT;
            int i3 = i2 + 1;
            if (d2 <= dArr2[i3]) {
                double d3 = dArr2[i3] - dArr2[i2];
                double d4 = (d2 - dArr2[i2]) / d3;
                double[][] dArr3 = this.f10mY;
                double d5 = dArr3[i2][0];
                double d6 = dArr3[i3][0];
                double[][] dArr4 = this.mTangent;
                return diff(d3, d4, d5, d6, dArr4[i2][0], dArr4[i3][0]) / d3;
            }
            i2 = i3;
        }
        return 0.0d;
    }

    public final void getPos(double d, float[] fArr) {
        double[] dArr = this.f9mT;
        int length = dArr.length;
        int i = 0;
        int length2 = this.f10mY[0].length;
        if (d <= dArr[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                fArr[i2] = (float) this.f10mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr[i3]) {
            while (i < length2) {
                fArr[i] = (float) this.f10mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.f9mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    fArr[i5] = (float) this.f10mY[i4][i5];
                }
            }
            double[] dArr2 = this.f9mT;
            int i6 = i4 + 1;
            if (d < dArr2[i6]) {
                double d2 = dArr2[i6] - dArr2[i4];
                double d3 = (d - dArr2[i4]) / d2;
                while (i < length2) {
                    double[][] dArr3 = this.f10mY;
                    double d4 = dArr3[i4][i];
                    double d5 = dArr3[i6][i];
                    double[][] dArr4 = this.mTangent;
                    fArr[i] = (float) interpolate(d2, d3, d4, d5, dArr4[i4][i], dArr4[i6][i]);
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    public final double getPos(double d) {
        double[] dArr = this.f9mT;
        int length = dArr.length;
        if (d <= dArr[0]) {
            return this.f10mY[0][0];
        }
        int i = length - 1;
        if (d >= dArr[i]) {
            return this.f10mY[i][0];
        }
        int i2 = 0;
        while (i2 < i) {
            double[] dArr2 = this.f9mT;
            if (d == dArr2[i2]) {
                return this.f10mY[i2][0];
            }
            int i3 = i2 + 1;
            if (d < dArr2[i3]) {
                double d2 = dArr2[i3] - dArr2[i2];
                double[][] dArr3 = this.f10mY;
                double d3 = dArr3[i2][0];
                double d4 = dArr3[i3][0];
                double[][] dArr4 = this.mTangent;
                return interpolate(d2, (d - dArr2[i2]) / d2, d3, d4, dArr4[i2][0], dArr4[i3][0]);
            }
            i2 = i3;
        }
        return 0.0d;
    }

    public final double[] getTimePoints() {
        return this.f9mT;
    }
}
