package androidx.constraintlayout.motion.utils;

import java.util.Arrays;
import java.util.Objects;

public final class ArcCurveFit extends CurveFit {
    public Arc[] mArcs;
    public final double[] mTime;

    public static class Arc {
        public static double[] ourPercent = new double[91];
        public boolean linear = false;
        public double mArcDistance;
        public double mArcVelocity;
        public double mEllipseA;
        public double mEllipseB;
        public double mEllipseCenterX;
        public double mEllipseCenterY;
        public double[] mLut;
        public double mOneOverDeltaTime;
        public double mTime1;
        public double mTime2;
        public double mTmpCosAngle;
        public double mTmpSinAngle;
        public boolean mVertical;
        public double mX1;
        public double mX2;
        public double mY1;
        public double mY2;

        public Arc(int i, double d, double d2, double d3, double d4, double d5, double d6) {
            int i2;
            double d7;
            int i3 = i;
            double d8 = d;
            double d9 = d2;
            double d10 = d3;
            double d11 = d4;
            double d12 = d5;
            double d13 = d6;
            boolean z = false;
            int i4 = 1;
            this.mVertical = i3 == 1 ? true : z;
            this.mTime1 = d8;
            this.mTime2 = d9;
            this.mOneOverDeltaTime = 1.0d / (d9 - d8);
            if (3 == i3) {
                this.linear = true;
            }
            double d14 = d12 - d10;
            double d15 = d13 - d11;
            if (this.linear || Math.abs(d14) < 0.001d || Math.abs(d15) < 0.001d) {
                this.linear = true;
                this.mX1 = d10;
                this.mX2 = d12;
                this.mY1 = d11;
                this.mY2 = d13;
                double hypot = Math.hypot(d15, d14);
                this.mArcDistance = hypot;
                this.mArcVelocity = hypot * this.mOneOverDeltaTime;
                double d16 = this.mTime2;
                double d17 = this.mTime1;
                this.mEllipseCenterX = d14 / (d16 - d17);
                this.mEllipseCenterY = d15 / (d16 - d17);
                return;
            }
            this.mLut = new double[101];
            boolean z2 = this.mVertical;
            this.mEllipseA = ((double) (z2 ? -1 : i4)) * d14;
            if (z2) {
                i2 = 1;
            } else {
                i2 = -1;
            }
            this.mEllipseB = d15 * ((double) i2);
            this.mEllipseCenterX = z2 ? d12 : d10;
            if (z2) {
                d7 = d11;
            } else {
                d7 = d13;
            }
            this.mEllipseCenterY = d7;
            double d18 = d11 - d13;
            int i5 = 0;
            double d19 = 0.0d;
            double d20 = 0.0d;
            double d21 = 0.0d;
            while (true) {
                double[] dArr = ourPercent;
                if (i5 >= 91) {
                    break;
                }
                double[] dArr2 = dArr;
                double radians = Math.toRadians((((double) i5) * 90.0d) / ((double) 90));
                double sin = Math.sin(radians) * d14;
                double cos = Math.cos(radians) * d18;
                if (i5 > 0) {
                    d19 += Math.hypot(sin - d20, cos - d21);
                    dArr2[i5] = d19;
                }
                i5++;
                d21 = cos;
                d20 = sin;
            }
            this.mArcDistance = d19;
            int i6 = 0;
            while (true) {
                double[] dArr3 = ourPercent;
                if (i6 >= 91) {
                    break;
                }
                dArr3[i6] = dArr3[i6] / d19;
                i6++;
            }
            int i7 = 0;
            while (true) {
                double[] dArr4 = this.mLut;
                if (i7 < dArr4.length) {
                    double length = ((double) i7) / ((double) (dArr4.length - 1));
                    double[] dArr5 = ourPercent;
                    int binarySearch = Arrays.binarySearch(dArr5, length);
                    if (binarySearch >= 0) {
                        this.mLut[i7] = (double) (binarySearch / 90);
                    } else if (binarySearch == -1) {
                        this.mLut[i7] = 0.0d;
                    } else {
                        int i8 = -binarySearch;
                        int i9 = i8 - 2;
                        this.mLut[i7] = (((length - dArr5[i9]) / (dArr5[i8 - 1] - dArr5[i9])) + ((double) i9)) / ((double) 90);
                    }
                    i7++;
                } else {
                    this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                    return;
                }
            }
        }

        public final void setPoint(double d) {
            double d2;
            if (this.mVertical) {
                d2 = this.mTime2 - d;
            } else {
                d2 = d - this.mTime1;
            }
            double d3 = d2 * this.mOneOverDeltaTime;
            double d4 = 0.0d;
            if (d3 > 0.0d) {
                d4 = 1.0d;
                if (d3 < 1.0d) {
                    double[] dArr = this.mLut;
                    double length = d3 * ((double) (dArr.length - 1));
                    int i = (int) length;
                    double d5 = dArr[i];
                    d4 = ((dArr[i + 1] - dArr[i]) * (length - ((double) i))) + d5;
                }
            }
            double d6 = d4 * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(d6);
            this.mTmpCosAngle = Math.cos(d6);
        }
    }

    public final void getPos(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        if (d < arcArr[0].mTime1) {
            d = arcArr[0].mTime1;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            if (d > arcArr2[i].mTime2) {
                i++;
            } else if (arcArr2[i].linear) {
                Arc arc = arcArr2[i];
                Objects.requireNonNull(arc);
                double d2 = (d - arc.mTime1) * arc.mOneOverDeltaTime;
                double d3 = arc.mX1;
                dArr[0] = ((arc.mX2 - d3) * d2) + d3;
                Arc arc2 = this.mArcs[i];
                Objects.requireNonNull(arc2);
                double d4 = (d - arc2.mTime1) * arc2.mOneOverDeltaTime;
                double d5 = arc2.mY1;
                dArr[1] = ((arc2.mY2 - d5) * d4) + d5;
                return;
            } else {
                arcArr2[i].setPoint(d);
                Arc arc3 = this.mArcs[i];
                Objects.requireNonNull(arc3);
                dArr[0] = (arc3.mEllipseA * arc3.mTmpSinAngle) + arc3.mEllipseCenterX;
                Arc arc4 = this.mArcs[i];
                Objects.requireNonNull(arc4);
                dArr[1] = (arc4.mEllipseB * arc4.mTmpCosAngle) + arc4.mEllipseCenterY;
                return;
            }
        }
    }

    public final void getSlope(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        if (d < arcArr[0].mTime1) {
            d = arcArr[0].mTime1;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            if (d > arcArr2[i].mTime2) {
                i++;
            } else if (arcArr2[i].linear) {
                Arc arc = arcArr2[i];
                Objects.requireNonNull(arc);
                dArr[0] = arc.mEllipseCenterX;
                Arc arc2 = this.mArcs[i];
                Objects.requireNonNull(arc2);
                dArr[1] = arc2.mEllipseCenterY;
                return;
            } else {
                arcArr2[i].setPoint(d);
                Arc arc3 = this.mArcs[i];
                Objects.requireNonNull(arc3);
                double d2 = arc3.mEllipseA * arc3.mTmpCosAngle;
                double hypot = arc3.mArcVelocity / Math.hypot(d2, (-arc3.mEllipseB) * arc3.mTmpSinAngle);
                if (arc3.mVertical) {
                    d2 = -d2;
                }
                dArr[0] = d2 * hypot;
                Arc arc4 = this.mArcs[i];
                Objects.requireNonNull(arc4);
                double d3 = arc4.mEllipseA * arc4.mTmpCosAngle;
                double d4 = (-arc4.mEllipseB) * arc4.mTmpSinAngle;
                double hypot2 = arc4.mArcVelocity / Math.hypot(d3, d4);
                dArr[1] = arc4.mVertical ? (-d4) * hypot2 : d4 * hypot2;
                return;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
        if (r5 == 1) goto L_0x0028;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ArcCurveFit(int[] r25, double[] r26, double[][] r27) {
        /*
            r24 = this;
            r0 = r24
            r1 = r26
            r24.<init>()
            r0.mTime = r1
            int r2 = r1.length
            r3 = 1
            int r2 = r2 - r3
            androidx.constraintlayout.motion.utils.ArcCurveFit$Arc[] r2 = new androidx.constraintlayout.motion.utils.ArcCurveFit.Arc[r2]
            r0.mArcs = r2
            r2 = 0
            r4 = r2
            r5 = r3
            r6 = r5
        L_0x0014:
            androidx.constraintlayout.motion.utils.ArcCurveFit$Arc[] r7 = r0.mArcs
            int r8 = r7.length
            if (r4 >= r8) goto L_0x0051
            r8 = r25[r4]
            r9 = 3
            r10 = 2
            if (r8 == 0) goto L_0x002d
            if (r8 == r3) goto L_0x002a
            if (r8 == r10) goto L_0x0028
            if (r8 == r9) goto L_0x0026
            goto L_0x002e
        L_0x0026:
            if (r5 != r3) goto L_0x002a
        L_0x0028:
            r5 = r10
            goto L_0x002b
        L_0x002a:
            r5 = r3
        L_0x002b:
            r6 = r5
            goto L_0x002e
        L_0x002d:
            r6 = r9
        L_0x002e:
            androidx.constraintlayout.motion.utils.ArcCurveFit$Arc r22 = new androidx.constraintlayout.motion.utils.ArcCurveFit$Arc
            r10 = r1[r4]
            int r23 = r4 + 1
            r12 = r1[r23]
            r8 = r27[r4]
            r14 = r8[r2]
            r8 = r27[r4]
            r16 = r8[r3]
            r8 = r27[r23]
            r18 = r8[r2]
            r8 = r27[r23]
            r20 = r8[r3]
            r8 = r22
            r9 = r6
            r8.<init>(r9, r10, r12, r14, r16, r18, r20)
            r7[r4] = r22
            r4 = r23
            goto L_0x0014
        L_0x0051:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.utils.ArcCurveFit.<init>(int[], double[], double[][]):void");
    }

    public final void getPos(double d, float[] fArr) {
        Arc[] arcArr = this.mArcs;
        if (d < arcArr[0].mTime1) {
            d = arcArr[0].mTime1;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            if (d > arcArr2[i].mTime2) {
                i++;
            } else if (arcArr2[i].linear) {
                Arc arc = arcArr2[i];
                Objects.requireNonNull(arc);
                double d2 = (d - arc.mTime1) * arc.mOneOverDeltaTime;
                double d3 = arc.mX1;
                fArr[0] = (float) (((arc.mX2 - d3) * d2) + d3);
                Arc arc2 = this.mArcs[i];
                Objects.requireNonNull(arc2);
                double d4 = (d - arc2.mTime1) * arc2.mOneOverDeltaTime;
                double d5 = arc2.mY1;
                fArr[1] = (float) (((arc2.mY2 - d5) * d4) + d5);
                return;
            } else {
                arcArr2[i].setPoint(d);
                Arc arc3 = this.mArcs[i];
                Objects.requireNonNull(arc3);
                fArr[0] = (float) ((arc3.mEllipseA * arc3.mTmpSinAngle) + arc3.mEllipseCenterX);
                Arc arc4 = this.mArcs[i];
                Objects.requireNonNull(arc4);
                fArr[1] = (float) ((arc4.mEllipseB * arc4.mTmpCosAngle) + arc4.mEllipseCenterY);
                return;
            }
        }
    }

    public final double getSlope(double d) {
        Arc[] arcArr = this.mArcs;
        int i = 0;
        if (d < arcArr[0].mTime1) {
            d = arcArr[0].mTime1;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return Double.NaN;
            }
            if (d > arcArr2[i].mTime2) {
                i++;
            } else if (arcArr2[i].linear) {
                Arc arc = arcArr2[i];
                Objects.requireNonNull(arc);
                return arc.mEllipseCenterX;
            } else {
                arcArr2[i].setPoint(d);
                Arc arc2 = this.mArcs[i];
                Objects.requireNonNull(arc2);
                double d2 = arc2.mEllipseA * arc2.mTmpCosAngle;
                double hypot = arc2.mArcVelocity / Math.hypot(d2, (-arc2.mEllipseB) * arc2.mTmpSinAngle);
                if (arc2.mVertical) {
                    d2 = -d2;
                }
                return d2 * hypot;
            }
        }
    }

    public final double getPos(double d) {
        Arc[] arcArr = this.mArcs;
        int i = 0;
        if (d < arcArr[0].mTime1) {
            d = arcArr[0].mTime1;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return Double.NaN;
            }
            if (d > arcArr2[i].mTime2) {
                i++;
            } else if (arcArr2[i].linear) {
                Arc arc = arcArr2[i];
                Objects.requireNonNull(arc);
                double d2 = (d - arc.mTime1) * arc.mOneOverDeltaTime;
                double d3 = arc.mX1;
                return ((arc.mX2 - d3) * d2) + d3;
            } else {
                arcArr2[i].setPoint(d);
                Arc arc2 = this.mArcs[i];
                Objects.requireNonNull(arc2);
                return (arc2.mEllipseA * arc2.mTmpSinAngle) + arc2.mEllipseCenterX;
            }
        }
    }

    public final double[] getTimePoints() {
        return this.mTime;
    }
}
