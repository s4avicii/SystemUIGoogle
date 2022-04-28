package androidx.constraintlayout.motion.widget;

import android.annotation.TargetApi;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public abstract class KeyCycleOscillator {
    public ConstraintAttribute mCustom;
    public CycleOscillator mCycleOscillator;
    public String mType;
    public int mVariesBy = 0;
    public ArrayList<WavePoint> mWavePoints = new ArrayList<>();
    public int mWaveShape = 0;

    public static class CustomSet extends KeyCycleOscillator {
        public float[] value = new float[1];

        public final void setProperty(View view, float f) {
            this.value[0] = get(f);
            this.mCustom.setInterpolatedValue(view, this.value);
        }
    }

    public static class PathRotateSet extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
        }
    }

    public abstract void setProperty(View view, float f);

    public static class AlphaSet extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setAlpha(get(f));
        }
    }

    public static class CycleOscillator {
        public CurveFit mCurveFit;
        public float[] mOffset;
        public Oscillator mOscillator = new Oscillator();
        public float[] mPeriod;
        public double[] mPosition;
        public double[] mSplineSlopeCache;
        public double[] mSplineValueCache;
        public float[] mValues;

        public CycleOscillator(int i, int i2) {
            new HashMap();
            Oscillator oscillator = this.mOscillator;
            Objects.requireNonNull(oscillator);
            oscillator.mType = i;
            this.mValues = new float[i2];
            this.mPosition = new double[i2];
            this.mPeriod = new float[i2];
            this.mOffset = new float[i2];
            float[] fArr = new float[i2];
        }
    }

    public static class ElevationSet extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setElevation(get(f));
        }
    }

    public static class ProgressSet extends KeyCycleOscillator {
        public boolean mNoMethod = false;

        public final void setProperty(View view, float f) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f));
            } else if (!this.mNoMethod) {
                Method method = null;
                try {
                    method = view.getClass().getMethod("setProgress", new Class[]{Float.TYPE});
                } catch (NoSuchMethodException unused) {
                    this.mNoMethod = true;
                }
                if (method != null) {
                    try {
                        method.invoke(view, new Object[]{Float.valueOf(get(f))});
                    } catch (IllegalAccessException e) {
                        Log.e("KeyCycleOscillator", "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e("KeyCycleOscillator", "unable to setProgress", e2);
                    }
                }
            }
        }
    }

    public static class RotationSet extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setRotation(get(f));
        }
    }

    public static class RotationXset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setRotationX(get(f));
        }
    }

    public static class RotationYset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setRotationY(get(f));
        }
    }

    public static class ScaleXset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setScaleX(get(f));
        }
    }

    public static class ScaleYset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setScaleY(get(f));
        }
    }

    public static class TranslationXset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setTranslationX(get(f));
        }
    }

    public static class TranslationYset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setTranslationY(get(f));
        }
    }

    public static class TranslationZset extends KeyCycleOscillator {
        public final void setProperty(View view, float f) {
            view.setTranslationZ(get(f));
        }
    }

    public static class WavePoint {
        public float mOffset;
        public float mPeriod;
        public int mPosition;
        public float mValue;

        public WavePoint(int i, float f, float f2, float f3) {
            this.mPosition = i;
            this.mValue = f3;
            this.mOffset = f2;
            this.mPeriod = f;
        }
    }

    public final float get(float f) {
        CycleOscillator cycleOscillator = this.mCycleOscillator;
        Objects.requireNonNull(cycleOscillator);
        CurveFit curveFit = cycleOscillator.mCurveFit;
        if (curveFit != null) {
            curveFit.getPos((double) f, cycleOscillator.mSplineValueCache);
        } else {
            double[] dArr = cycleOscillator.mSplineValueCache;
            dArr[0] = (double) cycleOscillator.mOffset[0];
            dArr[1] = (double) cycleOscillator.mValues[0];
        }
        return (float) ((cycleOscillator.mOscillator.getValue((double) f) * cycleOscillator.mSplineValueCache[1]) + cycleOscillator.mSplineValueCache[0]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x007a, code lost:
        r5 = r5 * r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x008f, code lost:
        r5 = r5 * r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float getSlope(float r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$CycleOscillator r0 = r0.mCycleOscillator
            java.util.Objects.requireNonNull(r0)
            androidx.constraintlayout.motion.utils.CurveFit r2 = r0.mCurveFit
            r3 = 0
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L_0x001f
            double r7 = (double) r1
            double[] r9 = r0.mSplineSlopeCache
            r2.getSlope(r7, r9)
            androidx.constraintlayout.motion.utils.CurveFit r2 = r0.mCurveFit
            double[] r9 = r0.mSplineValueCache
            r2.getPos((double) r7, (double[]) r9)
            goto L_0x0025
        L_0x001f:
            double[] r2 = r0.mSplineSlopeCache
            r2[r3] = r5
            r2[r4] = r5
        L_0x0025:
            androidx.constraintlayout.motion.utils.Oscillator r2 = r0.mOscillator
            double r7 = (double) r1
            double r1 = r2.getValue(r7)
            androidx.constraintlayout.motion.utils.Oscillator r9 = r0.mOscillator
            java.util.Objects.requireNonNull(r9)
            int r10 = r9.mType
            r11 = 4618760256179416344(0x401921fb54442d18, double:6.283185307179586)
            r13 = 4611686018427387904(0x4000000000000000, double:2.0)
            r15 = 4616189618054758400(0x4010000000000000, double:4.0)
            switch(r10) {
                case 1: goto L_0x0090;
                case 2: goto L_0x007c;
                case 3: goto L_0x0076;
                case 4: goto L_0x0070;
                case 5: goto L_0x005c;
                case 6: goto L_0x004e;
                default: goto L_0x003f;
            }
        L_0x003f:
            double r5 = r9.getDP(r7)
            double r5 = r5 * r11
            double r7 = r9.getP(r7)
            double r7 = r7 * r11
            double r7 = java.lang.Math.cos(r7)
            goto L_0x008f
        L_0x004e:
            double r5 = r9.getDP(r7)
            double r5 = r5 * r15
            double r7 = r9.getP(r7)
            double r7 = r7 * r15
            double r7 = r7 + r13
            double r7 = r7 % r15
            double r7 = r7 - r13
            goto L_0x008f
        L_0x005c:
            r5 = -4604611780675359464(0xc01921fb54442d18, double:-6.283185307179586)
            double r13 = r9.getDP(r7)
            double r13 = r13 * r5
            double r5 = r9.getP(r7)
            double r5 = r5 * r11
            double r5 = java.lang.Math.sin(r5)
            goto L_0x007a
        L_0x0070:
            double r5 = r9.getDP(r7)
            double r5 = -r5
            goto L_0x007a
        L_0x0076:
            double r5 = r9.getDP(r7)
        L_0x007a:
            double r5 = r5 * r13
            goto L_0x0090
        L_0x007c:
            double r5 = r9.getDP(r7)
            double r5 = r5 * r15
            double r7 = r9.getP(r7)
            double r7 = r7 * r15
            r9 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r7 = r7 + r9
            double r7 = r7 % r15
            double r7 = r7 - r13
            double r7 = java.lang.Math.signum(r7)
        L_0x008f:
            double r5 = r5 * r7
        L_0x0090:
            double[] r7 = r0.mSplineSlopeCache
            r8 = r7[r3]
            r10 = r7[r4]
            double r1 = r1 * r10
            double r1 = r1 + r8
            double[] r0 = r0.mSplineValueCache
            r3 = r0[r4]
            double r5 = r5 * r3
            double r5 = r5 + r1
            float r0 = (float) r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyCycleOscillator.getSlope(float):float");
    }

    @TargetApi(19)
    public final void setup() {
        int i;
        Class<double> cls = double.class;
        int size = this.mWavePoints.size();
        if (size != 0) {
            Collections.sort(this.mWavePoints, new Comparator<WavePoint>() {
                public final int compare(Object obj, Object obj2) {
                    return Integer.compare(((WavePoint) obj).mPosition, ((WavePoint) obj2).mPosition);
                }
            });
            double[] dArr = new double[size];
            int[] iArr = new int[2];
            iArr[1] = 2;
            iArr[0] = size;
            double[][] dArr2 = (double[][]) Array.newInstance(cls, iArr);
            this.mCycleOscillator = new CycleOscillator(this.mWaveShape, size);
            Iterator<WavePoint> it = this.mWavePoints.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                WavePoint next = it.next();
                float f = next.mPeriod;
                dArr[i2] = ((double) f) * 0.01d;
                double[] dArr3 = dArr2[i2];
                float f2 = next.mValue;
                dArr3[0] = (double) f2;
                double[] dArr4 = dArr2[i2];
                float f3 = next.mOffset;
                dArr4[1] = (double) f3;
                CycleOscillator cycleOscillator = this.mCycleOscillator;
                int i3 = next.mPosition;
                Objects.requireNonNull(cycleOscillator);
                cycleOscillator.mPosition[i2] = ((double) i3) / 100.0d;
                cycleOscillator.mPeriod[i2] = f;
                cycleOscillator.mOffset[i2] = f3;
                cycleOscillator.mValues[i2] = f2;
                i2++;
                dArr2 = dArr2;
            }
            double[][] dArr5 = dArr2;
            CycleOscillator cycleOscillator2 = this.mCycleOscillator;
            Objects.requireNonNull(cycleOscillator2);
            int length = cycleOscillator2.mPosition.length;
            int[] iArr2 = new int[2];
            iArr2[1] = 2;
            iArr2[0] = length;
            double[][] dArr6 = (double[][]) Array.newInstance(cls, iArr2);
            float[] fArr = cycleOscillator2.mValues;
            cycleOscillator2.mSplineValueCache = new double[(fArr.length + 1)];
            cycleOscillator2.mSplineSlopeCache = new double[(fArr.length + 1)];
            if (cycleOscillator2.mPosition[0] > 0.0d) {
                cycleOscillator2.mOscillator.addPoint(0.0d, cycleOscillator2.mPeriod[0]);
            }
            double[] dArr7 = cycleOscillator2.mPosition;
            int length2 = dArr7.length - 1;
            if (dArr7[length2] < 1.0d) {
                cycleOscillator2.mOscillator.addPoint(1.0d, cycleOscillator2.mPeriod[length2]);
            }
            for (int i4 = 0; i4 < dArr6.length; i4++) {
                dArr6[i4][0] = (double) cycleOscillator2.mOffset[i4];
                int i5 = 0;
                while (true) {
                    float[] fArr2 = cycleOscillator2.mValues;
                    if (i5 >= fArr2.length) {
                        break;
                    }
                    dArr6[i5][1] = (double) fArr2[i5];
                    i5++;
                }
                cycleOscillator2.mOscillator.addPoint(cycleOscillator2.mPosition[i4], cycleOscillator2.mPeriod[i4]);
            }
            Oscillator oscillator = cycleOscillator2.mOscillator;
            Objects.requireNonNull(oscillator);
            int i6 = 0;
            double d = 0.0d;
            while (true) {
                float[] fArr3 = oscillator.mPeriod;
                if (i6 >= fArr3.length) {
                    break;
                }
                d += (double) fArr3[i6];
                i6++;
            }
            int i7 = 1;
            double d2 = 0.0d;
            while (true) {
                float[] fArr4 = oscillator.mPeriod;
                if (i7 >= fArr4.length) {
                    break;
                }
                int i8 = i7 - 1;
                float f4 = (fArr4[i8] + fArr4[i7]) / 2.0f;
                double[] dArr8 = oscillator.mPosition;
                d2 = ((dArr8[i7] - dArr8[i8]) * ((double) f4)) + d2;
                i7++;
            }
            int i9 = 0;
            while (true) {
                float[] fArr5 = oscillator.mPeriod;
                if (i9 >= fArr5.length) {
                    break;
                }
                fArr5[i9] = (float) (((double) fArr5[i9]) * (d / d2));
                i9++;
            }
            oscillator.mArea[0] = 0.0d;
            int i10 = 1;
            while (true) {
                float[] fArr6 = oscillator.mPeriod;
                if (i10 >= fArr6.length) {
                    break;
                }
                int i11 = i10 - 1;
                double[] dArr9 = oscillator.mPosition;
                double d3 = dArr9[i10] - dArr9[i11];
                double[] dArr10 = oscillator.mArea;
                dArr10[i10] = (d3 * ((double) ((fArr6[i11] + fArr6[i10]) / 2.0f))) + dArr10[i11];
                i10++;
            }
            double[] dArr11 = cycleOscillator2.mPosition;
            if (dArr11.length > 1) {
                i = 0;
                cycleOscillator2.mCurveFit = CurveFit.get(0, dArr11, dArr6);
            } else {
                i = 0;
                cycleOscillator2.mCurveFit = null;
            }
            CurveFit.get(i, dArr, dArr5);
        }
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator<WavePoint> it = this.mWavePoints.iterator();
        while (it.hasNext()) {
            WavePoint next = it.next();
            StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str, "[");
            m.append(next.mPosition);
            m.append(" , ");
            m.append(decimalFormat.format((double) next.mValue));
            m.append("] ");
            str = m.toString();
        }
        return str;
    }
}
