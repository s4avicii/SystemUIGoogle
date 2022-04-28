package androidx.constraintlayout.motion.utils;

public final class LinearCurveFit extends CurveFit {

    /* renamed from: mT */
    public double[] f7mT;

    /* renamed from: mY */
    public double[][] f8mY;

    public final void getPos(double d, double[] dArr) {
        double[] dArr2 = this.f7mT;
        int length = dArr2.length;
        int i = 0;
        int length2 = this.f8mY[0].length;
        if (d <= dArr2[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                dArr[i2] = this.f8mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr2[i3]) {
            while (i < length2) {
                dArr[i] = this.f8mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.f7mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    dArr[i5] = this.f8mY[i4][i5];
                }
            }
            double[] dArr3 = this.f7mT;
            int i6 = i4 + 1;
            if (d < dArr3[i6]) {
                double d2 = (d - dArr3[i4]) / (dArr3[i6] - dArr3[i4]);
                while (i < length2) {
                    double[][] dArr4 = this.f8mY;
                    dArr[i] = (dArr4[i6][i] * d2) + ((1.0d - d2) * dArr4[i4][i]);
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    public final void getSlope(double d, double[] dArr) {
        double[] dArr2 = this.f7mT;
        int length = dArr2.length;
        int length2 = this.f8mY[0].length;
        if (d <= dArr2[0]) {
            d = dArr2[0];
        } else {
            int i = length - 1;
            if (d >= dArr2[i]) {
                d = dArr2[i];
            }
        }
        int i2 = 0;
        while (i2 < length - 1) {
            double[] dArr3 = this.f7mT;
            int i3 = i2 + 1;
            if (d <= dArr3[i3]) {
                double d2 = dArr3[i3] - dArr3[i2];
                double d3 = dArr3[i2];
                for (int i4 = 0; i4 < length2; i4++) {
                    double[][] dArr4 = this.f8mY;
                    dArr[i4] = (dArr4[i3][i4] - dArr4[i2][i4]) / d2;
                }
                return;
            }
            i2 = i3;
        }
    }

    public LinearCurveFit(double[] dArr, double[][] dArr2) {
        int length = dArr.length;
        int length2 = dArr2[0].length;
        this.f7mT = dArr;
        this.f8mY = dArr2;
        if (length2 > 2) {
            double d = 0.0d;
            int i = 0;
            double d2 = 0.0d;
            while (i < dArr.length) {
                double d3 = dArr2[i][0];
                double d4 = dArr2[i][0];
                if (i > 0) {
                    Math.hypot(d3 - d, d4 - d2);
                }
                i++;
                d = d3;
                d2 = d4;
            }
        }
    }

    public final double getSlope(double d) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        if (d < dArr[0]) {
            d = dArr[0];
        } else {
            int i = length - 1;
            if (d >= dArr[i]) {
                d = dArr[i];
            }
        }
        int i2 = 0;
        while (i2 < length - 1) {
            double[] dArr2 = this.f7mT;
            int i3 = i2 + 1;
            if (d <= dArr2[i3]) {
                double d2 = dArr2[i3] - dArr2[i2];
                double d3 = dArr2[i2];
                double[][] dArr3 = this.f8mY;
                return (dArr3[i3][0] - dArr3[i2][0]) / d2;
            }
            i2 = i3;
        }
        return 0.0d;
    }

    public final void getPos(double d, float[] fArr) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        int i = 0;
        int length2 = this.f8mY[0].length;
        if (d <= dArr[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                fArr[i2] = (float) this.f8mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr[i3]) {
            while (i < length2) {
                fArr[i] = (float) this.f8mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.f7mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    fArr[i5] = (float) this.f8mY[i4][i5];
                }
            }
            double[] dArr2 = this.f7mT;
            int i6 = i4 + 1;
            if (d < dArr2[i6]) {
                double d2 = (d - dArr2[i4]) / (dArr2[i6] - dArr2[i4]);
                while (i < length2) {
                    double[][] dArr3 = this.f8mY;
                    fArr[i] = (float) ((dArr3[i6][i] * d2) + ((1.0d - d2) * dArr3[i4][i]));
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    public final double getPos(double d) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        if (d <= dArr[0]) {
            return this.f8mY[0][0];
        }
        int i = length - 1;
        if (d >= dArr[i]) {
            return this.f8mY[i][0];
        }
        int i2 = 0;
        while (i2 < i) {
            double[] dArr2 = this.f7mT;
            if (d == dArr2[i2]) {
                return this.f8mY[i2][0];
            }
            int i3 = i2 + 1;
            if (d < dArr2[i3]) {
                double d2 = (d - dArr2[i2]) / (dArr2[i3] - dArr2[i2]);
                double[][] dArr3 = this.f8mY;
                return (dArr3[i3][0] * d2) + ((1.0d - d2) * dArr3[i2][0]);
            }
            i2 = i3;
        }
        return 0.0d;
    }

    public final double[] getTimePoints() {
        return this.f7mT;
    }
}
