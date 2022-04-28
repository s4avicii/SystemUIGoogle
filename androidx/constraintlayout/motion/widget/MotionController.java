package androidx.constraintlayout.motion.widget;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.ArcCurveFit;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public final class MotionController {
    public ArcCurveFit mArcSpline;
    public int[] mAttributeInterpCount;
    public String[] mAttributeNames;
    public HashMap<String, SplineSet> mAttributesMap;
    public int mCurveFitType = -1;
    public HashMap<String, KeyCycleOscillator> mCycleMap;
    public MotionPaths mEndMotionPath = new MotionPaths();
    public MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    public int mId;
    public double[] mInterpolateData;
    public int[] mInterpolateVariables;
    public double[] mInterpolateVelocity;
    public ArrayList<Key> mKeyList = new ArrayList<>();
    public KeyTrigger[] mKeyTriggers;
    public ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    public float mMotionStagger = Float.NaN;
    public int mPathMotionArc = -1;
    public CurveFit[] mSpline;
    public float mStaggerOffset = 0.0f;
    public float mStaggerScale = 1.0f;
    public MotionPaths mStartMotionPath = new MotionPaths();
    public MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    public HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    public float[] mValuesBuff = new float[4];
    public float[] mVelocity = new float[1];
    public View mView;

    public final float getAdjustedPosition(float f, float[] fArr) {
        float f2 = 0.0f;
        float f3 = 1.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f4 = this.mStaggerScale;
            if (((double) f4) != 1.0d) {
                float f5 = this.mStaggerOffset;
                if (f < f5) {
                    f = 0.0f;
                }
                if (f > f5 && ((double) f) < 1.0d) {
                    f = (f - f5) * f4;
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float f6 = Float.NaN;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths next = it.next();
            Easing easing2 = next.mKeyFrameEasing;
            if (easing2 != null) {
                float f7 = next.time;
                if (f7 < f) {
                    easing = easing2;
                    f2 = f7;
                } else if (Float.isNaN(f6)) {
                    f6 = next.time;
                }
            }
        }
        if (easing == null) {
            return f;
        }
        if (!Float.isNaN(f6)) {
            f3 = f6;
        }
        float f8 = f3 - f2;
        double d = (double) ((f - f2) / f8);
        float f9 = f2 + (((float) easing.get(d)) * f8);
        if (fArr != null) {
            fArr[0] = (float) easing.getDiff(d);
        }
        return f9;
    }

    public final void getDpDt(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(f, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i = 0;
        if (curveFitArr != null) {
            double d = (double) adjustedPosition;
            curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
            this.mSpline[0].getPos(d, this.mInterpolateData);
            float f4 = this.mVelocity[0];
            while (true) {
                dArr = this.mInterpolateVelocity;
                if (i >= dArr.length) {
                    break;
                }
                dArr[i] = dArr[i] * ((double) f4);
                i++;
            }
            ArcCurveFit arcCurveFit = this.mArcSpline;
            if (arcCurveFit != null) {
                double[] dArr2 = this.mInterpolateData;
                if (dArr2.length > 0) {
                    arcCurveFit.getPos(d, dArr2);
                    this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                    MotionPaths motionPaths = this.mStartMotionPath;
                    int[] iArr = this.mInterpolateVariables;
                    double[] dArr3 = this.mInterpolateVelocity;
                    double[] dArr4 = this.mInterpolateData;
                    Objects.requireNonNull(motionPaths);
                    MotionPaths.setDpDt(f2, f3, fArr, iArr, dArr3, dArr4);
                    return;
                }
                return;
            }
            MotionPaths motionPaths2 = this.mStartMotionPath;
            int[] iArr2 = this.mInterpolateVariables;
            double[] dArr5 = this.mInterpolateData;
            Objects.requireNonNull(motionPaths2);
            MotionPaths.setDpDt(f2, f3, fArr, iArr2, dArr, dArr5);
            return;
        }
        MotionPaths motionPaths3 = this.mEndMotionPath;
        float f5 = motionPaths3.f12x;
        MotionPaths motionPaths4 = this.mStartMotionPath;
        float f6 = f5 - motionPaths4.f12x;
        float f7 = motionPaths3.f13y - motionPaths4.f13y;
        fArr[0] = (((motionPaths3.width - motionPaths4.width) + f6) * f2) + ((1.0f - f2) * f6);
        fArr[1] = (((motionPaths3.height - motionPaths4.height) + f7) * f3) + ((1.0f - f3) * f7);
    }

    /* JADX WARNING: Removed duplicated region for block: B:147:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x031d  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0333  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0349  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0370  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0389  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x038b  */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x03a7  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x041c  */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x0428 A[SYNTHETIC, Splitter:B:207:0x0428] */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x049b A[SYNTHETIC, Splitter:B:220:0x049b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean interpolate(android.view.View r24, float r25, long r26, androidx.constraintlayout.motion.widget.KeyCache r28) {
        /*
            r23 = this;
            r0 = r23
            r7 = r24
            r1 = 0
            r2 = r25
            float r8 = r0.getAdjustedPosition(r2, r1)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r2 = r0.mAttributesMap
            if (r2 == 0) goto L_0x0027
            java.util.Collection r2 = r2.values()
            java.util.Iterator r2 = r2.iterator()
        L_0x0017:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0027
            java.lang.Object r3 = r2.next()
            androidx.constraintlayout.motion.widget.SplineSet r3 = (androidx.constraintlayout.motion.widget.SplineSet) r3
            r3.setProperty(r7, r8)
            goto L_0x0017
        L_0x0027:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r2 = r0.mTimeCycleAttributesMap
            r9 = 0
            if (r2 == 0) goto L_0x0057
            java.util.Collection r2 = r2.values()
            java.util.Iterator r10 = r2.iterator()
            r11 = r1
            r12 = r9
        L_0x0036:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L_0x0059
            java.lang.Object r1 = r10.next()
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet r1 = (androidx.constraintlayout.motion.widget.TimeCycleSplineSet) r1
            boolean r2 = r1 instanceof androidx.constraintlayout.motion.widget.TimeCycleSplineSet.PathRotate
            if (r2 == 0) goto L_0x004a
            r11 = r1
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$PathRotate r11 = (androidx.constraintlayout.motion.widget.TimeCycleSplineSet.PathRotate) r11
            goto L_0x0036
        L_0x004a:
            r2 = r24
            r3 = r8
            r4 = r26
            r6 = r28
            boolean r1 = r1.setProperty(r2, r3, r4, r6)
            r12 = r12 | r1
            goto L_0x0036
        L_0x0057:
            r11 = r1
            r12 = r9
        L_0x0059:
            androidx.constraintlayout.motion.utils.CurveFit[] r1 = r0.mSpline
            if (r1 == 0) goto L_0x050f
            r1 = r1[r9]
            double r13 = (double) r8
            double[] r2 = r0.mInterpolateData
            r1.getPos((double) r13, (double[]) r2)
            androidx.constraintlayout.motion.utils.CurveFit[] r1 = r0.mSpline
            r1 = r1[r9]
            double[] r2 = r0.mInterpolateVelocity
            r1.getSlope(r13, r2)
            androidx.constraintlayout.motion.utils.ArcCurveFit r1 = r0.mArcSpline
            if (r1 == 0) goto L_0x0081
            double[] r2 = r0.mInterpolateData
            int r3 = r2.length
            if (r3 <= 0) goto L_0x0081
            r1.getPos((double) r13, (double[]) r2)
            androidx.constraintlayout.motion.utils.ArcCurveFit r1 = r0.mArcSpline
            double[] r2 = r0.mInterpolateVelocity
            r1.getSlope(r13, r2)
        L_0x0081:
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            int[] r2 = r0.mInterpolateVariables
            double[] r3 = r0.mInterpolateData
            double[] r4 = r0.mInterpolateVelocity
            java.util.Objects.requireNonNull(r1)
            float r5 = r1.f12x
            float r6 = r1.f13y
            float r9 = r1.width
            float r10 = r1.height
            int r15 = r2.length
            if (r15 == 0) goto L_0x00b3
            double[] r15 = r1.mTempValue
            int r15 = r15.length
            r25 = r5
            int r5 = r2.length
            int r5 = r5 + -1
            r5 = r2[r5]
            if (r15 > r5) goto L_0x00b5
            int r5 = r2.length
            int r5 = r5 + -1
            r5 = r2[r5]
            int r5 = r5 + 1
            double[] r15 = new double[r5]
            r1.mTempValue = r15
            double[] r5 = new double[r5]
            r1.mTempDelta = r5
            goto L_0x00b5
        L_0x00b3:
            r25 = r5
        L_0x00b5:
            double[] r5 = r1.mTempValue
            r15 = r9
            r16 = r10
            r9 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            java.util.Arrays.fill(r5, r9)
            r5 = 0
        L_0x00c0:
            int r9 = r2.length
            if (r5 >= r9) goto L_0x00d6
            double[] r9 = r1.mTempValue
            r10 = r2[r5]
            r17 = r3[r5]
            r9[r10] = r17
            double[] r9 = r1.mTempDelta
            r10 = r2[r5]
            r17 = r4[r5]
            r9[r10] = r17
            int r5 = r5 + 1
            goto L_0x00c0
        L_0x00d6:
            r2 = 0
            r3 = 2143289344(0x7fc00000, float:NaN)
            r4 = 0
            r5 = 0
            r9 = 0
            r10 = 0
            r19 = r13
            r17 = r16
            r16 = r10
            r10 = r9
            r9 = r6
            r6 = r25
        L_0x00e7:
            double[] r13 = r1.mTempValue
            int r14 = r13.length
            if (r2 >= r14) goto L_0x0138
            r13 = r13[r2]
            boolean r13 = java.lang.Double.isNaN(r13)
            if (r13 == 0) goto L_0x00f5
            goto L_0x0135
        L_0x00f5:
            double[] r13 = r1.mTempValue
            r13 = r13[r2]
            boolean r13 = java.lang.Double.isNaN(r13)
            if (r13 == 0) goto L_0x0102
            r13 = 0
            goto L_0x010a
        L_0x0102:
            double[] r13 = r1.mTempValue
            r13 = r13[r2]
            r21 = 0
            double r13 = r13 + r21
        L_0x010a:
            float r13 = (float) r13
            double[] r14 = r1.mTempDelta
            r18 = r13
            r13 = r14[r2]
            float r13 = (float) r13
            r14 = 1
            if (r2 == r14) goto L_0x0132
            r14 = 2
            if (r2 == r14) goto L_0x012e
            r14 = 3
            if (r2 == r14) goto L_0x0129
            r14 = 4
            if (r2 == r14) goto L_0x0125
            r13 = 5
            if (r2 == r13) goto L_0x0122
            goto L_0x0135
        L_0x0122:
            r3 = r18
            goto L_0x0135
        L_0x0125:
            r5 = r13
            r17 = r18
            goto L_0x0135
        L_0x0129:
            r16 = r13
            r15 = r18
            goto L_0x0135
        L_0x012e:
            r4 = r13
            r9 = r18
            goto L_0x0135
        L_0x0132:
            r10 = r13
            r6 = r18
        L_0x0135:
            int r2 = r2 + 1
            goto L_0x00e7
        L_0x0138:
            boolean r2 = java.lang.Float.isNaN(r3)
            if (r2 == 0) goto L_0x014c
            r2 = 2143289344(0x7fc00000, float:NaN)
            boolean r3 = java.lang.Float.isNaN(r2)
            if (r3 != 0) goto L_0x0149
            r7.setRotation(r2)
        L_0x0149:
            r25 = r11
            goto L_0x0171
        L_0x014c:
            r2 = 2143289344(0x7fc00000, float:NaN)
            boolean r13 = java.lang.Float.isNaN(r2)
            if (r13 == 0) goto L_0x0155
            r2 = 0
        L_0x0155:
            r13 = 1073741824(0x40000000, float:2.0)
            float r16 = r16 / r13
            float r10 = r16 + r10
            float r5 = r5 / r13
            float r5 = r5 + r4
            double r13 = (double) r2
            double r2 = (double) r3
            double r4 = (double) r5
            r25 = r11
            double r10 = (double) r10
            double r4 = java.lang.Math.atan2(r4, r10)
            double r4 = java.lang.Math.toDegrees(r4)
            double r4 = r4 + r2
            double r4 = r4 + r13
            float r2 = (float) r4
            r7.setRotation(r2)
        L_0x0171:
            r2 = 1056964608(0x3f000000, float:0.5)
            float r6 = r6 + r2
            int r3 = (int) r6
            float r9 = r9 + r2
            int r2 = (int) r9
            float r6 = r6 + r15
            int r4 = (int) r6
            float r9 = r9 + r17
            int r5 = (int) r9
            int r6 = r4 - r3
            int r9 = r5 - r2
            int r10 = r24.getWidth()
            if (r6 != r10) goto L_0x018f
            int r10 = r24.getHeight()
            if (r9 == r10) goto L_0x018d
            goto L_0x018f
        L_0x018d:
            r10 = 0
            goto L_0x0190
        L_0x018f:
            r10 = 1
        L_0x0190:
            r11 = 1073741824(0x40000000, float:2.0)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r11)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r11)
            int r11 = r1.mLastMeasuredWidth
            if (r11 != r6) goto L_0x01a2
            int r11 = r1.mLastMeasuredHeight
            if (r11 == r9) goto L_0x01a3
        L_0x01a2:
            r10 = 1
        L_0x01a3:
            if (r10 == 0) goto L_0x01ac
            r1.mLastMeasuredWidth = r6
            r1.mLastMeasuredHeight = r9
            r7.measure(r6, r9)
        L_0x01ac:
            r7.layout(r3, r2, r4, r5)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r1 = r0.mAttributesMap
            if (r1 == 0) goto L_0x01ea
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
        L_0x01bb:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x01ea
            java.lang.Object r2 = r1.next()
            androidx.constraintlayout.motion.widget.SplineSet r2 = (androidx.constraintlayout.motion.widget.SplineSet) r2
            boolean r3 = r2 instanceof androidx.constraintlayout.motion.widget.SplineSet.PathRotate
            if (r3 == 0) goto L_0x01bb
            androidx.constraintlayout.motion.widget.SplineSet$PathRotate r2 = (androidx.constraintlayout.motion.widget.SplineSet.PathRotate) r2
            double[] r3 = r0.mInterpolateVelocity
            r4 = 0
            r4 = r3[r4]
            r6 = 1
            r9 = r3[r6]
            java.util.Objects.requireNonNull(r2)
            float r2 = r2.get(r8)
            double r3 = java.lang.Math.atan2(r9, r4)
            double r3 = java.lang.Math.toDegrees(r3)
            float r3 = (float) r3
            float r2 = r2 + r3
            r7.setRotation(r2)
            goto L_0x01bb
        L_0x01ea:
            if (r25 == 0) goto L_0x0214
            double[] r1 = r0.mInterpolateVelocity
            r2 = 0
            r9 = r1[r2]
            r2 = 1
            r13 = r1[r2]
            r1 = r25
            r2 = r8
            r3 = r26
            r5 = r24
            r6 = r28
            float r1 = r1.get(r2, r3, r5, r6)
            double r2 = java.lang.Math.atan2(r13, r9)
            double r2 = java.lang.Math.toDegrees(r2)
            float r2 = (float) r2
            float r1 = r1 + r2
            r7.setRotation(r1)
            r11 = r25
            boolean r1 = r11.mContinue
            r1 = r1 | r12
            r12 = r1
        L_0x0214:
            r1 = 1
        L_0x0215:
            androidx.constraintlayout.motion.utils.CurveFit[] r2 = r0.mSpline
            int r3 = r2.length
            if (r1 >= r3) goto L_0x023b
            r2 = r2[r1]
            float[] r3 = r0.mValuesBuff
            r4 = r19
            r2.getPos((double) r4, (float[]) r3)
            androidx.constraintlayout.motion.widget.MotionPaths r2 = r0.mStartMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r2 = r2.attributes
            java.lang.String[] r3 = r0.mAttributeNames
            int r6 = r1 + -1
            r3 = r3[r6]
            java.lang.Object r2 = r2.get(r3)
            androidx.constraintlayout.widget.ConstraintAttribute r2 = (androidx.constraintlayout.widget.ConstraintAttribute) r2
            float[] r3 = r0.mValuesBuff
            r2.setInterpolatedValue(r7, r3)
            int r1 = r1 + 1
            goto L_0x0215
        L_0x023b:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mStartPoint
            int r2 = r1.mVisibilityMode
            if (r2 != 0) goto L_0x0266
            r2 = 0
            int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r2 > 0) goto L_0x024c
            int r1 = r1.visibility
            r7.setVisibility(r1)
            goto L_0x0266
        L_0x024c:
            r2 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x025a
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mEndPoint
            int r1 = r1.visibility
            r7.setVisibility(r1)
            goto L_0x0266
        L_0x025a:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r2 = r0.mEndPoint
            int r2 = r2.visibility
            int r1 = r1.visibility
            if (r2 == r1) goto L_0x0266
            r1 = 0
            r7.setVisibility(r1)
        L_0x0266:
            androidx.constraintlayout.motion.widget.KeyTrigger[] r1 = r0.mKeyTriggers
            if (r1 == 0) goto L_0x050a
            r1 = 0
        L_0x026b:
            androidx.constraintlayout.motion.widget.KeyTrigger[] r2 = r0.mKeyTriggers
            int r3 = r2.length
            if (r1 >= r3) goto L_0x050a
            r2 = r2[r1]
            java.util.Objects.requireNonNull(r2)
            int r3 = r2.mTriggerCollisionId
            r4 = -1
            if (r3 == r4) goto L_0x02da
            android.view.View r3 = r2.mTriggerCollisionView
            if (r3 != 0) goto L_0x028c
            android.view.ViewParent r3 = r24.getParent()
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            int r4 = r2.mTriggerCollisionId
            android.view.View r3 = r3.findViewById(r4)
            r2.mTriggerCollisionView = r3
        L_0x028c:
            android.graphics.RectF r3 = r2.mCollisionRect
            android.view.View r4 = r2.mTriggerCollisionView
            boolean r5 = r2.mPostLayout
            androidx.constraintlayout.motion.widget.KeyTrigger.setUpRect(r3, r4, r5)
            android.graphics.RectF r3 = r2.mTargetRect
            boolean r4 = r2.mPostLayout
            androidx.constraintlayout.motion.widget.KeyTrigger.setUpRect(r3, r7, r4)
            android.graphics.RectF r3 = r2.mCollisionRect
            android.graphics.RectF r4 = r2.mTargetRect
            boolean r3 = r3.intersect(r4)
            if (r3 == 0) goto L_0x02c1
            boolean r3 = r2.mFireCrossReset
            if (r3 == 0) goto L_0x02af
            r3 = 0
            r2.mFireCrossReset = r3
            r4 = 1
            goto L_0x02b1
        L_0x02af:
            r3 = 0
            r4 = r3
        L_0x02b1:
            boolean r5 = r2.mFirePositiveReset
            if (r5 == 0) goto L_0x02ba
            r2.mFirePositiveReset = r3
            r3 = 1
            r5 = 1
            goto L_0x02bc
        L_0x02ba:
            r3 = 1
            r5 = 0
        L_0x02bc:
            r2.mFireNegativeReset = r3
            r3 = 0
            goto L_0x035b
        L_0x02c1:
            r3 = 1
            boolean r4 = r2.mFireCrossReset
            if (r4 != 0) goto L_0x02ca
            r2.mFireCrossReset = r3
            r4 = r3
            goto L_0x02cb
        L_0x02ca:
            r4 = 0
        L_0x02cb:
            boolean r5 = r2.mFireNegativeReset
            if (r5 == 0) goto L_0x02d4
            r5 = 0
            r2.mFireNegativeReset = r5
            r5 = r3
            goto L_0x02d5
        L_0x02d4:
            r5 = 0
        L_0x02d5:
            r2.mFirePositiveReset = r3
            r3 = r5
            goto L_0x035a
        L_0x02da:
            boolean r3 = r2.mFireCrossReset
            if (r3 == 0) goto L_0x02f0
            float r3 = r2.mFireThreshold
            float r4 = r8 - r3
            float r5 = r2.mFireLastPos
            float r5 = r5 - r3
            float r5 = r5 * r4
            r3 = 0
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x0301
            r3 = 0
            r2.mFireCrossReset = r3
            r3 = 1
            goto L_0x0302
        L_0x02f0:
            float r3 = r2.mFireThreshold
            float r3 = r8 - r3
            float r3 = java.lang.Math.abs(r3)
            float r4 = r2.mTriggerSlack
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x0301
            r3 = 1
            r2.mFireCrossReset = r3
        L_0x0301:
            r3 = 0
        L_0x0302:
            r4 = r3
            boolean r3 = r2.mFireNegativeReset
            if (r3 == 0) goto L_0x031d
            float r3 = r2.mFireThreshold
            float r5 = r8 - r3
            float r6 = r2.mFireLastPos
            float r6 = r6 - r3
            float r6 = r6 * r5
            r3 = 0
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x032e
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x032e
            r3 = 0
            r2.mFireNegativeReset = r3
            r3 = 1
            goto L_0x032f
        L_0x031d:
            float r3 = r2.mFireThreshold
            float r3 = r8 - r3
            float r3 = java.lang.Math.abs(r3)
            float r5 = r2.mTriggerSlack
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x032e
            r3 = 1
            r2.mFireNegativeReset = r3
        L_0x032e:
            r3 = 0
        L_0x032f:
            boolean r5 = r2.mFirePositiveReset
            if (r5 == 0) goto L_0x0349
            float r5 = r2.mFireThreshold
            float r6 = r8 - r5
            float r9 = r2.mFireLastPos
            float r9 = r9 - r5
            float r9 = r9 * r6
            r5 = 0
            int r9 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r9 >= 0) goto L_0x035a
            int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x035a
            r5 = 0
            r2.mFirePositiveReset = r5
            r5 = 1
            goto L_0x035b
        L_0x0349:
            float r5 = r2.mFireThreshold
            float r5 = r8 - r5
            float r5 = java.lang.Math.abs(r5)
            float r6 = r2.mTriggerSlack
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x035a
            r5 = 1
            r2.mFirePositiveReset = r5
        L_0x035a:
            r5 = 0
        L_0x035b:
            r2.mFireLastPos = r8
            if (r3 != 0) goto L_0x0363
            if (r4 != 0) goto L_0x0363
            if (r5 == 0) goto L_0x0384
        L_0x0363:
            android.view.ViewParent r6 = r24.getParent()
            androidx.constraintlayout.motion.widget.MotionLayout r6 = (androidx.constraintlayout.motion.widget.MotionLayout) r6
            java.util.Objects.requireNonNull(r6)
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener> r6 = r6.mTransitionListeners
            if (r6 == 0) goto L_0x0384
            java.util.Iterator r6 = r6.iterator()
        L_0x0374:
            boolean r9 = r6.hasNext()
            if (r9 == 0) goto L_0x0384
            java.lang.Object r9 = r6.next()
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionListener r9 = (androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener) r9
            r9.onTransitionTrigger()
            goto L_0x0374
        L_0x0384:
            int r6 = r2.mTriggerReceiver
            r9 = -1
            if (r6 != r9) goto L_0x038b
            r6 = r7
            goto L_0x0397
        L_0x038b:
            android.view.ViewParent r6 = r24.getParent()
            androidx.constraintlayout.motion.widget.MotionLayout r6 = (androidx.constraintlayout.motion.widget.MotionLayout) r6
            int r9 = r2.mTriggerReceiver
            android.view.View r6 = r6.findViewById(r9)
        L_0x0397:
            java.lang.String r9 = "Could not find method \""
            java.lang.String r10 = "Exception in call \""
            java.lang.String r11 = " "
            java.lang.String r13 = "\"on class "
            java.lang.String r14 = "KeyTrigger"
            if (r3 == 0) goto L_0x041c
            java.lang.String r3 = r2.mNegativeCross
            if (r3 == 0) goto L_0x041c
            java.lang.reflect.Method r3 = r2.mFireNegativeCross
            if (r3 != 0) goto L_0x03e8
            java.lang.Class r3 = r6.getClass()     // Catch:{ NoSuchMethodException -> 0x03bd }
            java.lang.String r15 = r2.mNegativeCross     // Catch:{ NoSuchMethodException -> 0x03bd }
            r25 = r12
            r12 = 0
            java.lang.Class[] r12 = new java.lang.Class[r12]     // Catch:{ NoSuchMethodException -> 0x03bf }
            java.lang.reflect.Method r3 = r3.getMethod(r15, r12)     // Catch:{ NoSuchMethodException -> 0x03bf }
            r2.mFireNegativeCross = r3     // Catch:{ NoSuchMethodException -> 0x03bf }
            goto L_0x03ea
        L_0x03bd:
            r25 = r12
        L_0x03bf:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
            java.lang.String r12 = r2.mNegativeCross
            r3.append(r12)
            r3.append(r13)
            java.lang.Class r12 = r6.getClass()
            java.lang.String r12 = r12.getSimpleName()
            r3.append(r12)
            r3.append(r11)
            java.lang.String r12 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r14, r3)
            goto L_0x03ea
        L_0x03e8:
            r25 = r12
        L_0x03ea:
            java.lang.reflect.Method r3 = r2.mFireNegativeCross     // Catch:{ Exception -> 0x03f3 }
            r12 = 0
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x03f3 }
            r3.invoke(r6, r12)     // Catch:{ Exception -> 0x03f3 }
            goto L_0x041e
        L_0x03f3:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            java.lang.String r12 = r2.mNegativeCross
            r3.append(r12)
            r3.append(r13)
            java.lang.Class r12 = r6.getClass()
            java.lang.String r12 = r12.getSimpleName()
            r3.append(r12)
            r3.append(r11)
            java.lang.String r12 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r14, r3)
            goto L_0x041e
        L_0x041c:
            r25 = r12
        L_0x041e:
            if (r5 == 0) goto L_0x0491
            java.lang.String r3 = r2.mPositiveCross
            if (r3 == 0) goto L_0x0491
            java.lang.reflect.Method r3 = r2.mFirePositiveCross
            if (r3 != 0) goto L_0x0460
            java.lang.Class r3 = r6.getClass()     // Catch:{ NoSuchMethodException -> 0x0438 }
            java.lang.String r5 = r2.mPositiveCross     // Catch:{ NoSuchMethodException -> 0x0438 }
            r12 = 0
            java.lang.Class[] r12 = new java.lang.Class[r12]     // Catch:{ NoSuchMethodException -> 0x0438 }
            java.lang.reflect.Method r3 = r3.getMethod(r5, r12)     // Catch:{ NoSuchMethodException -> 0x0438 }
            r2.mFirePositiveCross = r3     // Catch:{ NoSuchMethodException -> 0x0438 }
            goto L_0x0460
        L_0x0438:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
            java.lang.String r5 = r2.mPositiveCross
            r3.append(r5)
            r3.append(r13)
            java.lang.Class r5 = r6.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r3.append(r5)
            r3.append(r11)
            java.lang.String r5 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r14, r3)
        L_0x0460:
            java.lang.reflect.Method r3 = r2.mFirePositiveCross     // Catch:{ Exception -> 0x0469 }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0469 }
            r3.invoke(r6, r5)     // Catch:{ Exception -> 0x0469 }
            goto L_0x0491
        L_0x0469:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            java.lang.String r5 = r2.mPositiveCross
            r3.append(r5)
            r3.append(r13)
            java.lang.Class r5 = r6.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r3.append(r5)
            r3.append(r11)
            java.lang.String r5 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r14, r3)
        L_0x0491:
            if (r4 == 0) goto L_0x0504
            java.lang.String r3 = r2.mCross
            if (r3 == 0) goto L_0x0504
            java.lang.reflect.Method r3 = r2.mFireCross
            if (r3 != 0) goto L_0x04d3
            java.lang.Class r3 = r6.getClass()     // Catch:{ NoSuchMethodException -> 0x04ab }
            java.lang.String r4 = r2.mCross     // Catch:{ NoSuchMethodException -> 0x04ab }
            r5 = 0
            java.lang.Class[] r5 = new java.lang.Class[r5]     // Catch:{ NoSuchMethodException -> 0x04ab }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch:{ NoSuchMethodException -> 0x04ab }
            r2.mFireCross = r3     // Catch:{ NoSuchMethodException -> 0x04ab }
            goto L_0x04d3
        L_0x04ab:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
            java.lang.String r4 = r2.mCross
            r3.append(r4)
            r3.append(r13)
            java.lang.Class r4 = r6.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            r3.append(r11)
            java.lang.String r4 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r14, r3)
        L_0x04d3:
            java.lang.reflect.Method r3 = r2.mFireCross     // Catch:{ Exception -> 0x04dc }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x04dc }
            r3.invoke(r6, r4)     // Catch:{ Exception -> 0x04dc }
            goto L_0x0504
        L_0x04dc:
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            java.lang.String r2 = r2.mCross
            r3.append(r2)
            r3.append(r13)
            java.lang.Class r2 = r6.getClass()
            java.lang.String r2 = r2.getSimpleName()
            r3.append(r2)
            r3.append(r11)
            java.lang.String r2 = androidx.constraintlayout.motion.widget.Debug.getName(r6)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            android.util.Log.e(r14, r2)
        L_0x0504:
            int r1 = r1 + 1
            r12 = r25
            goto L_0x026b
        L_0x050a:
            r25 = r12
            r12 = r25
            goto L_0x0559
        L_0x050f:
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            float r2 = r1.f12x
            androidx.constraintlayout.motion.widget.MotionPaths r3 = r0.mEndMotionPath
            float r4 = r3.f12x
            float r2 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r4, r2, r8, r2)
            float r4 = r1.f13y
            float r5 = r3.f13y
            float r4 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r5, r4, r8, r4)
            float r5 = r1.width
            float r6 = r3.width
            float r9 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r6, r5, r8, r5)
            float r1 = r1.height
            float r3 = r3.height
            float r10 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r3, r1, r8, r1)
            r11 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r11
            int r13 = (int) r2
            float r4 = r4 + r11
            int r11 = (int) r4
            float r2 = r2 + r9
            int r2 = (int) r2
            float r4 = r4 + r10
            int r4 = (int) r4
            int r9 = r2 - r13
            int r10 = r4 - r11
            int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x0549
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0556
        L_0x0549:
            r1 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r1)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r1)
            r7.measure(r3, r1)
        L_0x0556:
            r7.layout(r13, r11, r2, r4)
        L_0x0559:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r1 = r0.mCycleMap
            if (r1 == 0) goto L_0x0598
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
        L_0x0565:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0598
            java.lang.Object r2 = r1.next()
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r2 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r2
            boolean r3 = r2 instanceof androidx.constraintlayout.motion.widget.KeyCycleOscillator.PathRotateSet
            if (r3 == 0) goto L_0x0594
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$PathRotateSet r2 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator.PathRotateSet) r2
            double[] r3 = r0.mInterpolateVelocity
            r4 = 0
            r4 = r3[r4]
            r6 = 1
            r9 = r3[r6]
            java.util.Objects.requireNonNull(r2)
            float r2 = r2.get(r8)
            double r3 = java.lang.Math.atan2(r9, r4)
            double r3 = java.lang.Math.toDegrees(r3)
            float r3 = (float) r3
            float r2 = r2 + r3
            r7.setRotation(r2)
            goto L_0x0565
        L_0x0594:
            r2.setProperty(r7, r8)
            goto L_0x0565
        L_0x0598:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionController.interpolate(android.view.View, float, long, androidx.constraintlayout.motion.widget.KeyCache):boolean");
    }

    public final void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((float) ((int) this.mView.getX()), (float) ((int) this.mView.getY()), (float) this.mView.getWidth(), (float) this.mView.getHeight());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v100, resolved type: androidx.constraintlayout.motion.widget.KeyCycleOscillator$CustomSet} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v67, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v95, resolved type: double[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v167, resolved type: androidx.constraintlayout.motion.widget.TimeCycleSplineSet$CustomSet} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v172, resolved type: androidx.constraintlayout.motion.widget.SplineSet$CustomSet} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x027c, code lost:
        r7 = r20;
        r1 = r22;
        r4 = r28;
        r3 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0284, code lost:
        r22 = r6;
        r20 = r13;
        r13 = r17;
        r6 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x028c, code lost:
        r19 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02d4, code lost:
        r7 = r20;
        r4 = r28;
        r3 = r29;
        r20 = r13;
        r13 = r17;
        r17 = r1;
        r1 = r22;
        r22 = r6;
        r6 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02fa, code lost:
        r20 = r13;
        r13 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0397, code lost:
        r19 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x03da, code lost:
        r17 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x03dc, code lost:
        switch(r17) {
            case 0: goto L_0x0430;
            case 1: goto L_0x042a;
            case 2: goto L_0x0424;
            case 3: goto L_0x041e;
            case 4: goto L_0x0418;
            case 5: goto L_0x0412;
            case com.android.systemui.plugins.FalsingManager.VERSION :int: goto L_0x040c;
            case 7: goto L_0x0406;
            case 8: goto L_0x0400;
            case 9: goto L_0x03fa;
            case 10: goto L_0x03f4;
            case com.android.systemui.qs.tileimpl.QSTileImpl.H.STALE :int: goto L_0x03ee;
            case com.android.systemui.keyguard.KeyguardSliceProvider.ALARM_VISIBILITY_HOURS :int: goto L_0x03e8;
            case com.android.systemui.plugins.qs.QS.VERSION :int: goto L_0x03e2;
            default: goto L_0x03df;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x03df, code lost:
        r17 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x03e2, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x03e8, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03ee, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.PathRotate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03f4, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.ElevationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x03fa, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.RotationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0400, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0406, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.ScaleYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x040c, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.ScaleXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0412, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.ProgressSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0418, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.TranslationZset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x041e, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.TranslationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0424, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.TranslationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x042a, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.RotationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0430, code lost:
        r17 = new androidx.constraintlayout.motion.widget.SplineSet.RotationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0435, code lost:
        r18 = r13;
        r13 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x05ae, code lost:
        r2 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x05b0, code lost:
        r13 = r18;
        r5 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x05ef, code lost:
        r15 = r2;
        r2 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x0620, code lost:
        r13 = r18;
        r5 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x0646, code lost:
        r15 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0647, code lost:
        switch(r15) {
            case 0: goto L_0x0694;
            case 1: goto L_0x068e;
            case 2: goto L_0x0688;
            case 3: goto L_0x0682;
            case 4: goto L_0x067c;
            case 5: goto L_0x0676;
            case com.android.systemui.plugins.FalsingManager.VERSION :int: goto L_0x0670;
            case 7: goto L_0x066a;
            case 8: goto L_0x0664;
            case 9: goto L_0x065e;
            case 10: goto L_0x0658;
            case com.android.systemui.qs.tileimpl.QSTileImpl.H.STALE :int: goto L_0x0652;
            default: goto L_0x064a;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x064a, code lost:
        r17 = r4;
        r18 = r5;
        r4 = r46;
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x0652, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x0658, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.PathRotate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x065e, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.ElevationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x0664, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.RotationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x066a, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.ScaleYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x0670, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.ScaleXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x0676, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.ProgressSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x067c, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.TranslationZset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x0682, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.TranslationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x0688, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.TranslationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x068e, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.RotationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:270:0x0694, code lost:
        r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet.RotationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x0699, code lost:
        r17 = r4;
        r18 = r5;
        r15.last_time = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x0777, code lost:
        r0 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:306:0x07a2, code lost:
        r5 = r0;
        r0 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:313:0x07b7, code lost:
        r8 = r0;
        r0 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:325:0x07e7, code lost:
        r5 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:327:0x07eb, code lost:
        r8 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:328:0x07ec, code lost:
        r5 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:331:0x07f9, code lost:
        r6 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:333:0x07fe, code lost:
        r6 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:337:0x0810, code lost:
        r8 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x0811, code lost:
        switch(r8) {
            case 0: goto L_0x09e8;
            case 1: goto L_0x09c2;
            case 2: goto L_0x099c;
            case 3: goto L_0x0975;
            case 4: goto L_0x094e;
            case 5: goto L_0x0927;
            case com.android.systemui.plugins.FalsingManager.VERSION :int: goto L_0x0900;
            case 7: goto L_0x08d9;
            case 8: goto L_0x08b2;
            case 9: goto L_0x088b;
            case 10: goto L_0x0864;
            case com.android.systemui.qs.tileimpl.QSTileImpl.H.STALE :int: goto L_0x0837;
            default: goto L_0x0814;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:339:0x0814, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
        android.util.Log.e("KeyTimeCycles", "UNKNOWN addValues \"" + r4 + "\"");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:340:0x0837, code lost:
        r8 = r47;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:341:0x083f, code lost:
        if (java.lang.Float.isNaN(r8.mAlpha) != false) goto L_0x085e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:342:0x0841, code lost:
        r17 = r6;
        r18 = r5;
        r32.setPoint(r8.mFramePosition, r8.mAlpha, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:343:0x085e, code lost:
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:344:0x0864, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:345:0x0870, code lost:
        if (java.lang.Float.isNaN(r8.mTransitionPathRotate) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:346:0x0872, code lost:
        r32.setPoint(r8.mFramePosition, r8.mTransitionPathRotate, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:347:0x088b, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:348:0x0897, code lost:
        if (java.lang.Float.isNaN(r8.mElevation) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:349:0x0899, code lost:
        r32.setPoint(r8.mFramePosition, r8.mElevation, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:350:0x08b2, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:351:0x08be, code lost:
        if (java.lang.Float.isNaN(r8.mRotation) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:352:0x08c0, code lost:
        r32.setPoint(r8.mFramePosition, r8.mRotation, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:353:0x08d9, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:354:0x08e5, code lost:
        if (java.lang.Float.isNaN(r8.mScaleY) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:355:0x08e7, code lost:
        r32.setPoint(r8.mFramePosition, r8.mScaleY, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:356:0x0900, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:357:0x090c, code lost:
        if (java.lang.Float.isNaN(r8.mScaleX) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:358:0x090e, code lost:
        r32.setPoint(r8.mFramePosition, r8.mScaleX, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:359:0x0927, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:0x0933, code lost:
        if (java.lang.Float.isNaN(r8.mProgress) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:361:0x0935, code lost:
        r32.setPoint(r8.mFramePosition, r8.mProgress, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:362:0x094e, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:363:0x095a, code lost:
        if (java.lang.Float.isNaN(r8.mTranslationZ) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:364:0x095c, code lost:
        r32.setPoint(r8.mFramePosition, r8.mTranslationZ, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:365:0x0975, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:366:0x0981, code lost:
        if (java.lang.Float.isNaN(r8.mTranslationY) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:367:0x0983, code lost:
        r32.setPoint(r8.mFramePosition, r8.mTranslationY, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:0x099c, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:369:0x09a8, code lost:
        if (java.lang.Float.isNaN(r8.mTranslationX) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:0x09aa, code lost:
        r32.setPoint(r8.mFramePosition, r8.mTranslationX, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:371:0x09c2, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:372:0x09ce, code lost:
        if (java.lang.Float.isNaN(r8.mRotationY) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:373:0x09d0, code lost:
        r32.setPoint(r8.mFramePosition, r8.mRotationY, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:374:0x09e8, code lost:
        r8 = r47;
        r18 = r5;
        r17 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:375:0x09f4, code lost:
        if (java.lang.Float.isNaN(r8.mRotationX) != false) goto L_0x0a0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:376:0x09f6, code lost:
        r32.setPoint(r8.mFramePosition, r8.mRotationX, r8.mWavePeriod, r8.mWaveShape, r8.mWaveOffset);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:377:0x0a0d, code lost:
        r4 = r45;
        r15 = r46;
        r5 = r8;
        r13 = r17;
        r6 = r19;
        r8 = r30;
        r17 = r0;
        r0 = r43;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:492:0x0e30, code lost:
        r15 = r27;
        r10 = r28;
        r14 = r29;
        r6 = r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:493:0x0e38, code lost:
        r13 = r33;
        r11 = r34;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:496:0x0e46, code lost:
        r47 = r0;
        r7 = r16;
        r8 = r17;
        r0 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:507:0x0e9c, code lost:
        r7 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:509:0x0ea1, code lost:
        r47 = r0;
        r8 = r17;
        r0 = r19;
        r15 = r27;
        r10 = r28;
        r14 = r29;
        r13 = r33;
        r11 = r34;
        r42 = r16;
        r16 = r7;
        r7 = r42;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:512:0x0ec5, code lost:
        r8 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:516:0x0edb, code lost:
        r10 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:518:0x0ee0, code lost:
        r47 = r0;
        r22 = r8;
        r16 = r10;
        r8 = r17;
        r0 = r19;
        r15 = r27;
        r10 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:521:0x0eff, code lost:
        r47 = r0;
        r22 = r8;
        r8 = r17;
        r0 = r19;
        r15 = r27;
        r14 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:523:0x0f1a, code lost:
        r14 = r29;
        r13 = r33;
        r11 = r34;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:532:0x0f70, code lost:
        r47 = r0;
        r22 = r8;
        r16 = r14;
        r8 = r17;
        r0 = r19;
        r15 = r27;
        r14 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:535:0x0f96, code lost:
        r47 = r0;
        r22 = r8;
        r8 = r17;
        r0 = r19;
        r15 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:541:0x0fcb, code lost:
        r47 = r0;
        r22 = r8;
        r8 = r17;
        r0 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:549:0x1017, code lost:
        r8 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:551:0x101e, code lost:
        r8 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:556:0x1043, code lost:
        switch(r16) {
            case 0: goto L_0x109b;
            case 1: goto L_0x1095;
            case 2: goto L_0x108f;
            case 3: goto L_0x1089;
            case 4: goto L_0x1083;
            case 5: goto L_0x107d;
            case com.android.systemui.plugins.FalsingManager.VERSION :int: goto L_0x1077;
            case 7: goto L_0x1071;
            case 8: goto L_0x106b;
            case 9: goto L_0x1065;
            case 10: goto L_0x105f;
            case com.android.systemui.qs.tileimpl.QSTileImpl.H.STALE :int: goto L_0x1059;
            case com.android.systemui.keyguard.KeyguardSliceProvider.ALARM_VISIBILITY_HOURS :int: goto L_0x1053;
            case com.android.systemui.plugins.qs.QS.VERSION :int: goto L_0x104d;
            default: goto L_0x1046;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:557:0x1046, code lost:
        r16 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:558:0x1048, code lost:
        r17 = r8;
        r8 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:559:0x104d, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:560:0x1053, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:561:0x1059, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.PathRotateSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:562:0x105f, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.ElevationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:563:0x1065, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.RotationSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:564:0x106b, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.AlphaSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:565:0x1071, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.ScaleYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:566:0x1077, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.ScaleXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:567:0x107d, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.ProgressSet();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:568:0x1083, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.TranslationZset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:569:0x1089, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.TranslationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:570:0x108f, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.TranslationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:571:0x1095, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.RotationYset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:572:0x109b, code lost:
        r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator.RotationXset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:634:0x1264, code lost:
        r4 = r16;
        r15 = r17;
        r14 = r18;
        r13 = r19;
        r11 = r20;
        r0 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:640:0x1281, code lost:
        r0 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:642:0x1286, code lost:
        r4 = r0;
        r0 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:648:0x129e, code lost:
        r4 = r16;
        r15 = r17;
        r14 = r18;
        r13 = r19;
        r11 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:649:0x12a8, code lost:
        r10 = r28;
        r3 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:651:0x12af, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:652:0x12b0, code lost:
        r3 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:656:0x12c9, code lost:
        r8 = r33;
        r6 = r34;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:658:0x12d1, code lost:
        r13 = r4;
        r4 = r16;
        r11 = r20;
        r10 = r28;
        r8 = r33;
        r6 = r34;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:665:0x130e, code lost:
        r15 = r17;
        r14 = r18;
        r13 = r19;
        r11 = r20;
        r10 = r28;
        r8 = r33;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:673:0x1359, code lost:
        r15 = r17;
        r14 = r18;
        r13 = r19;
        r11 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:679:0x1384, code lost:
        r16 = r13;
        r15 = r17;
        r14 = r18;
        r13 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:692:0x13f2, code lost:
        switch(r16) {
            case 0: goto L_0x144e;
            case 1: goto L_0x1449;
            case 2: goto L_0x1444;
            case 3: goto L_0x143f;
            case 4: goto L_0x143a;
            case 5: goto L_0x1435;
            case com.android.systemui.plugins.FalsingManager.VERSION :int: goto L_0x1430;
            case 7: goto L_0x142b;
            case 8: goto L_0x1426;
            case 9: goto L_0x1421;
            case 10: goto L_0x141c;
            case com.android.systemui.qs.tileimpl.QSTileImpl.H.STALE :int: goto L_0x1417;
            case com.android.systemui.keyguard.KeyguardSliceProvider.ALARM_VISIBILITY_HOURS :int: goto L_0x1412;
            default: goto L_0x13f5;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:693:0x13f5, code lost:
        r32 = r0;
        r0 = new java.lang.StringBuilder();
        r16 = r3;
        r0.append("  UNKNOWN  ");
        r0.append(r7);
        android.util.Log.v("WARNING! KeyCycle", r0.toString());
        r0 = Float.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:694:0x1412, code lost:
        r32 = r0;
        r0 = r1.mWaveOffset;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:695:0x1417, code lost:
        r32 = r0;
        r0 = r1.mAlpha;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:696:0x141c, code lost:
        r32 = r0;
        r0 = r1.mTransitionPathRotate;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:697:0x1421, code lost:
        r32 = r0;
        r0 = r1.mElevation;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:698:0x1426, code lost:
        r32 = r0;
        r0 = r1.mRotation;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:699:0x142b, code lost:
        r32 = r0;
        r0 = r1.mScaleY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:700:0x1430, code lost:
        r32 = r0;
        r0 = r1.mScaleX;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:701:0x1435, code lost:
        r32 = r0;
        r0 = r1.mProgress;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:702:0x143a, code lost:
        r32 = r0;
        r0 = r1.mTranslationZ;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:703:0x143f, code lost:
        r32 = r0;
        r0 = r1.mTranslationY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:704:0x1444, code lost:
        r32 = r0;
        r0 = r1.mTranslationX;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:705:0x1449, code lost:
        r32 = r0;
        r0 = r1.mRotationY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:706:0x144e, code lost:
        r32 = r0;
        r0 = r1.mRotationX;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:707:0x1452, code lost:
        r16 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:709:0x1458, code lost:
        if (java.lang.Float.isNaN(r0) != false) goto L_0x14ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:710:0x145a, code lost:
        r3 = r2.get(r7);
        r7 = r1.mFramePosition;
        r17 = r2;
        r2 = r1.mWaveShape;
        r28 = r4;
        r4 = r1.mWaveVariesBy;
        r20 = r5;
        r5 = r1.mWavePeriod;
        r34 = r6;
        r6 = r1.mWaveOffset;
        java.util.Objects.requireNonNull(r3);
        r18 = r1;
        r29 = r8;
        r3.mWavePoints.add(new androidx.constraintlayout.motion.widget.KeyCycleOscillator.WavePoint(r7, r5, r6, r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:711:0x1484, code lost:
        if (r4 == -1) goto L_0x1488;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:712:0x1486, code lost:
        r3.mVariesBy = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:713:0x1488, code lost:
        r3.mWaveShape = r2;
        r4 = r43;
        r0 = r47;
        r19 = r13;
        r2 = r17;
        r1 = r18;
        r5 = r20;
        r6 = r22;
        r33 = r29;
        r3 = r30;
        r27 = r32;
        r20 = r11;
        r18 = r14;
        r17 = r15;
        r15 = r16;
        r16 = r28;
        r28 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:714:0x14ac, code lost:
        r0 = r47;
        r34 = r6;
        r33 = r8;
        r28 = r10;
        r20 = r11;
        r19 = r13;
        r18 = r14;
        r17 = r15;
        r15 = r16;
        r6 = r22;
        r3 = r30;
        r27 = r32;
        r16 = r4;
        r4 = r43;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setup(int r44, int r45, long r46) {
        /*
            r43 = this;
            r0 = r43
            java.lang.Class<double> r1 = double.class
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            int r6 = r0.mPathMotionArc
            r7 = -1
            if (r6 == r7) goto L_0x0026
            androidx.constraintlayout.motion.widget.MotionPaths r7 = r0.mStartMotionPath
            r7.mPathMotionArc = r6
        L_0x0026:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r6 = r0.mStartPoint
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r7 = r0.mEndPoint
            java.util.Objects.requireNonNull(r6)
            float r8 = r6.alpha
            float r9 = r7.alpha
            boolean r8 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r9)
            java.lang.String r9 = "alpha"
            if (r8 == 0) goto L_0x003c
            r3.add(r9)
        L_0x003c:
            float r8 = r6.elevation
            float r10 = r7.elevation
            boolean r8 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r10)
            java.lang.String r10 = "elevation"
            if (r8 == 0) goto L_0x004b
            r3.add(r10)
        L_0x004b:
            int r8 = r6.visibility
            int r11 = r7.visibility
            if (r8 == r11) goto L_0x005c
            int r12 = r6.mVisibilityMode
            if (r12 != 0) goto L_0x005c
            if (r8 == 0) goto L_0x0059
            if (r11 != 0) goto L_0x005c
        L_0x0059:
            r3.add(r9)
        L_0x005c:
            float r8 = r6.rotation
            float r11 = r7.rotation
            boolean r8 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r11)
            java.lang.String r11 = "rotation"
            if (r8 == 0) goto L_0x006c
            r3.add(r11)
        L_0x006c:
            float r8 = r6.mPathRotate
            boolean r8 = java.lang.Float.isNaN(r8)
            java.lang.String r12 = "transitionPathRotate"
            if (r8 == 0) goto L_0x007f
            float r8 = r7.mPathRotate
            boolean r8 = java.lang.Float.isNaN(r8)
            if (r8 != 0) goto L_0x0082
        L_0x007f:
            r3.add(r12)
        L_0x0082:
            float r8 = r6.mProgress
            boolean r8 = java.lang.Float.isNaN(r8)
            java.lang.String r13 = "progress"
            if (r8 == 0) goto L_0x0094
            float r8 = r7.mProgress
            boolean r8 = java.lang.Float.isNaN(r8)
            if (r8 != 0) goto L_0x0097
        L_0x0094:
            r3.add(r13)
        L_0x0097:
            float r8 = r6.rotationX
            float r14 = r7.rotationX
            boolean r8 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r14)
            java.lang.String r14 = "rotationX"
            if (r8 == 0) goto L_0x00a7
            r3.add(r14)
        L_0x00a7:
            float r8 = r6.rotationY
            float r15 = r7.rotationY
            boolean r8 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r15)
            java.lang.String r15 = "rotationY"
            if (r8 == 0) goto L_0x00b7
            r3.add(r15)
        L_0x00b7:
            float r8 = r6.scaleX
            r16 = r1
            float r1 = r7.scaleX
            boolean r1 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r8, r1)
            java.lang.String r8 = "scaleX"
            if (r1 == 0) goto L_0x00c9
            r3.add(r8)
        L_0x00c9:
            float r1 = r6.scaleY
            r17 = r14
            float r14 = r7.scaleY
            boolean r1 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r1, r14)
            java.lang.String r14 = "scaleY"
            if (r1 == 0) goto L_0x00db
            r3.add(r14)
        L_0x00db:
            float r1 = r6.translationX
            r18 = r15
            float r15 = r7.translationX
            boolean r1 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r1, r15)
            java.lang.String r15 = "translationX"
            if (r1 == 0) goto L_0x00ed
            r3.add(r15)
        L_0x00ed:
            float r1 = r6.translationY
            r19 = r15
            float r15 = r7.translationY
            boolean r1 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r1, r15)
            java.lang.String r15 = "translationY"
            if (r1 == 0) goto L_0x00ff
            r3.add(r15)
        L_0x00ff:
            float r1 = r6.translationZ
            float r6 = r7.translationZ
            boolean r1 = androidx.constraintlayout.motion.widget.MotionConstrainedPoint.diff(r1, r6)
            java.lang.String r6 = "translationZ"
            if (r1 == 0) goto L_0x010f
            r3.add(r6)
        L_0x010f:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r1 = r0.mKeyList
            if (r1 == 0) goto L_0x01c0
            java.util.Iterator r1 = r1.iterator()
            r7 = 0
        L_0x0118:
            boolean r20 = r1.hasNext()
            if (r20 == 0) goto L_0x01b7
            java.lang.Object r20 = r1.next()
            r21 = r1
            r1 = r20
            androidx.constraintlayout.motion.widget.Key r1 = (androidx.constraintlayout.motion.widget.Key) r1
            r20 = r15
            boolean r15 = r1 instanceof androidx.constraintlayout.motion.widget.KeyPosition
            if (r15 == 0) goto L_0x017e
            androidx.constraintlayout.motion.widget.KeyPosition r1 = (androidx.constraintlayout.motion.widget.KeyPosition) r1
            androidx.constraintlayout.motion.widget.MotionPaths r15 = new androidx.constraintlayout.motion.widget.MotionPaths
            r28 = r6
            androidx.constraintlayout.motion.widget.MotionPaths r6 = r0.mStartMotionPath
            r29 = r13
            androidx.constraintlayout.motion.widget.MotionPaths r13 = r0.mEndMotionPath
            r22 = r15
            r23 = r44
            r24 = r45
            r25 = r1
            r26 = r6
            r27 = r13
            r22.<init>(r23, r24, r25, r26, r27)
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r6 = r0.mMotionPaths
            int r6 = java.util.Collections.binarySearch(r6, r15)
            if (r6 != 0) goto L_0x016d
            java.lang.String r13 = " KeyPath positon \""
            java.lang.StringBuilder r13 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r13)
            r22 = r8
            float r8 = r15.position
            r13.append(r8)
            java.lang.String r8 = "\" outside of range"
            r13.append(r8)
            java.lang.String r8 = r13.toString()
            java.lang.String r13 = "MotionController"
            android.util.Log.e(r13, r8)
            goto L_0x016f
        L_0x016d:
            r22 = r8
        L_0x016f:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r8 = r0.mMotionPaths
            int r6 = -r6
            r13 = -1
            int r6 = r6 + r13
            r8.add(r6, r15)
            int r1 = r1.mCurveFit
            if (r1 == r13) goto L_0x01ab
            r0.mCurveFitType = r1
            goto L_0x01ab
        L_0x017e:
            r28 = r6
            r22 = r8
            r29 = r13
            boolean r6 = r1 instanceof androidx.constraintlayout.motion.widget.KeyCycle
            if (r6 == 0) goto L_0x018c
            r1.getAttributeNames(r4)
            goto L_0x01ab
        L_0x018c:
            boolean r6 = r1 instanceof androidx.constraintlayout.motion.widget.KeyTimeCycle
            if (r6 == 0) goto L_0x0194
            r1.getAttributeNames(r2)
            goto L_0x01ab
        L_0x0194:
            boolean r6 = r1 instanceof androidx.constraintlayout.motion.widget.KeyTrigger
            if (r6 == 0) goto L_0x01a5
            if (r7 != 0) goto L_0x019f
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x019f:
            androidx.constraintlayout.motion.widget.KeyTrigger r1 = (androidx.constraintlayout.motion.widget.KeyTrigger) r1
            r7.add(r1)
            goto L_0x01ab
        L_0x01a5:
            r1.setInterpolation(r5)
            r1.getAttributeNames(r3)
        L_0x01ab:
            r15 = r20
            r1 = r21
            r8 = r22
            r6 = r28
            r13 = r29
            goto L_0x0118
        L_0x01b7:
            r28 = r6
            r22 = r8
            r29 = r13
            r20 = r15
            goto L_0x01c9
        L_0x01c0:
            r28 = r6
            r22 = r8
            r29 = r13
            r20 = r15
            r7 = 0
        L_0x01c9:
            r1 = 0
            if (r7 == 0) goto L_0x01d6
            androidx.constraintlayout.motion.widget.KeyTrigger[] r1 = new androidx.constraintlayout.motion.widget.KeyTrigger[r1]
            java.lang.Object[] r1 = r7.toArray(r1)
            androidx.constraintlayout.motion.widget.KeyTrigger[] r1 = (androidx.constraintlayout.motion.widget.KeyTrigger[]) r1
            r0.mKeyTriggers = r1
        L_0x01d6:
            boolean r1 = r3.isEmpty()
            java.lang.String r6 = "waveVariesBy"
            java.lang.String r7 = ","
            java.lang.String r13 = "waveOffset"
            java.lang.String r15 = "CUSTOM,"
            r21 = 11
            r23 = 10
            r24 = 9
            r25 = 8
            r26 = 5
            if (r1 != 0) goto L_0x04ff
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r0.mAttributesMap = r1
            java.util.Iterator r1 = r3.iterator()
        L_0x01fb:
            boolean r27 = r1.hasNext()
            if (r27 == 0) goto L_0x047e
            java.lang.Object r27 = r1.next()
            r8 = r27
            java.lang.String r8 = (java.lang.String) r8
            boolean r27 = r8.startsWith(r15)
            if (r27 == 0) goto L_0x026d
            r45 = r1
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            java.lang.String[] r27 = r8.split(r7)
            r30 = 1
            r31 = r4
            r4 = r27[r30]
            r27 = r3
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r3 = r0.mKeyList
            java.util.Iterator r3 = r3.iterator()
        L_0x0228:
            boolean r30 = r3.hasNext()
            if (r30 == 0) goto L_0x0251
            java.lang.Object r30 = r3.next()
            r32 = r3
            r3 = r30
            androidx.constraintlayout.motion.widget.Key r3 = (androidx.constraintlayout.motion.widget.Key) r3
            r30 = r7
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r7 = r3.mCustomConstraints
            if (r7 != 0) goto L_0x023f
            goto L_0x024c
        L_0x023f:
            java.lang.Object r7 = r7.get(r4)
            androidx.constraintlayout.widget.ConstraintAttribute r7 = (androidx.constraintlayout.widget.ConstraintAttribute) r7
            if (r7 == 0) goto L_0x024c
            int r3 = r3.mFramePosition
            r1.append(r3, r7)
        L_0x024c:
            r7 = r30
            r3 = r32
            goto L_0x0228
        L_0x0251:
            r30 = r7
            androidx.constraintlayout.motion.widget.SplineSet$CustomSet r3 = new androidx.constraintlayout.motion.widget.SplineSet$CustomSet
            r3.<init>(r8, r1)
            r7 = r20
            r1 = r22
            r4 = r28
            r22 = r6
            r20 = r13
            r6 = r19
            r13 = r3
            r19 = r18
            r3 = r29
            r18 = r17
            goto L_0x0439
        L_0x026d:
            r45 = r1
            r27 = r3
            r31 = r4
            r30 = r7
            int r1 = r8.hashCode()
            switch(r1) {
                case -1249320806: goto L_0x03be;
                case -1249320805: goto L_0x039a;
                case -1225497657: goto L_0x037b;
                case -1225497656: goto L_0x0359;
                case -1225497655: goto L_0x0333;
                case -1001078227: goto L_0x0314;
                case -908189618: goto L_0x02e8;
                case -908189617: goto L_0x02cc;
                case -797520672: goto L_0x02c2;
                case -40300674: goto L_0x02b8;
                case -4379043: goto L_0x02ae;
                case 37232917: goto L_0x02a4;
                case 92909918: goto L_0x029a;
                case 156108012: goto L_0x0290;
                default: goto L_0x027c;
            }
        L_0x027c:
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
        L_0x0284:
            r22 = r6
            r20 = r13
            r13 = r17
            r6 = r19
        L_0x028c:
            r19 = r18
            goto L_0x03da
        L_0x0290:
            boolean r1 = r8.equals(r13)
            if (r1 != 0) goto L_0x0297
            goto L_0x027c
        L_0x0297:
            r1 = 13
            goto L_0x02d4
        L_0x029a:
            boolean r1 = r8.equals(r9)
            if (r1 != 0) goto L_0x02a1
            goto L_0x027c
        L_0x02a1:
            r1 = 12
            goto L_0x02d4
        L_0x02a4:
            boolean r1 = r8.equals(r12)
            if (r1 != 0) goto L_0x02ab
            goto L_0x027c
        L_0x02ab:
            r1 = r21
            goto L_0x02d4
        L_0x02ae:
            boolean r1 = r8.equals(r10)
            if (r1 != 0) goto L_0x02b5
            goto L_0x027c
        L_0x02b5:
            r1 = r23
            goto L_0x02d4
        L_0x02b8:
            boolean r1 = r8.equals(r11)
            if (r1 != 0) goto L_0x02bf
            goto L_0x027c
        L_0x02bf:
            r1 = r24
            goto L_0x02d4
        L_0x02c2:
            boolean r1 = r8.equals(r6)
            if (r1 != 0) goto L_0x02c9
            goto L_0x027c
        L_0x02c9:
            r1 = r25
            goto L_0x02d4
        L_0x02cc:
            boolean r1 = r8.equals(r14)
            if (r1 != 0) goto L_0x02d3
            goto L_0x027c
        L_0x02d3:
            r1 = 7
        L_0x02d4:
            r7 = r20
            r4 = r28
            r3 = r29
            r20 = r13
            r13 = r17
            r17 = r1
            r1 = r22
            r22 = r6
            r6 = r19
            goto L_0x0397
        L_0x02e8:
            r1 = r22
            boolean r3 = r8.equals(r1)
            if (r3 != 0) goto L_0x02ff
            r22 = r6
            r6 = r19
            r7 = r20
            r4 = r28
            r3 = r29
        L_0x02fa:
            r20 = r13
            r13 = r17
            goto L_0x028c
        L_0x02ff:
            r3 = 6
            r22 = r6
            r6 = r19
            r7 = r20
            r4 = r28
            r20 = r13
            r13 = r17
            r19 = r18
            r17 = r3
            r3 = r29
            goto L_0x03dc
        L_0x0314:
            r1 = r22
            r3 = r29
            boolean r4 = r8.equals(r3)
            r22 = r6
            r6 = r19
            r7 = r20
            if (r4 != 0) goto L_0x0327
            r4 = r28
            goto L_0x02fa
        L_0x0327:
            r4 = r28
            r20 = r13
            r13 = r17
            r19 = r18
            r17 = r26
            goto L_0x03dc
        L_0x0333:
            r1 = r22
            r4 = r28
            r3 = r29
            boolean r7 = r8.equals(r4)
            if (r7 != 0) goto L_0x0346
            r22 = r6
            r6 = r19
            r7 = r20
            goto L_0x02fa
        L_0x0346:
            r7 = 4
            r22 = r6
            r6 = r19
            r19 = r18
            r42 = r17
            r17 = r7
            r7 = r20
            r20 = r13
            r13 = r42
            goto L_0x03dc
        L_0x0359:
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            boolean r20 = r8.equals(r7)
            if (r20 != 0) goto L_0x0369
            goto L_0x0284
        L_0x0369:
            r20 = 3
            r22 = r6
            r6 = r19
            r19 = r18
            r42 = r20
            r20 = r13
            r13 = r17
            r17 = r42
            goto L_0x03dc
        L_0x037b:
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            r22 = r6
            r6 = r19
            boolean r19 = r8.equals(r6)
            if (r19 != 0) goto L_0x038f
            goto L_0x02fa
        L_0x038f:
            r19 = 2
            r20 = r13
            r13 = r17
            r17 = r19
        L_0x0397:
            r19 = r18
            goto L_0x03dc
        L_0x039a:
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            r22 = r6
            r20 = r13
            r13 = r18
            r6 = r19
            boolean r18 = r8.equals(r13)
            if (r18 != 0) goto L_0x03b5
            r19 = r13
            r13 = r17
            goto L_0x03da
        L_0x03b5:
            r18 = 1
            r19 = r13
            r13 = r17
            r17 = r18
            goto L_0x03dc
        L_0x03be:
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            r22 = r6
            r20 = r13
            r13 = r17
            r6 = r19
            r19 = r18
            boolean r17 = r8.equals(r13)
            if (r17 != 0) goto L_0x03d7
            goto L_0x03da
        L_0x03d7:
            r17 = 0
            goto L_0x03dc
        L_0x03da:
            r17 = -1
        L_0x03dc:
            switch(r17) {
                case 0: goto L_0x0430;
                case 1: goto L_0x042a;
                case 2: goto L_0x0424;
                case 3: goto L_0x041e;
                case 4: goto L_0x0418;
                case 5: goto L_0x0412;
                case 6: goto L_0x040c;
                case 7: goto L_0x0406;
                case 8: goto L_0x0400;
                case 9: goto L_0x03fa;
                case 10: goto L_0x03f4;
                case 11: goto L_0x03ee;
                case 12: goto L_0x03e8;
                case 13: goto L_0x03e2;
                default: goto L_0x03df;
            }
        L_0x03df:
            r17 = 0
            goto L_0x0435
        L_0x03e2:
            androidx.constraintlayout.motion.widget.SplineSet$AlphaSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$AlphaSet
            r17.<init>()
            goto L_0x0435
        L_0x03e8:
            androidx.constraintlayout.motion.widget.SplineSet$AlphaSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$AlphaSet
            r17.<init>()
            goto L_0x0435
        L_0x03ee:
            androidx.constraintlayout.motion.widget.SplineSet$PathRotate r17 = new androidx.constraintlayout.motion.widget.SplineSet$PathRotate
            r17.<init>()
            goto L_0x0435
        L_0x03f4:
            androidx.constraintlayout.motion.widget.SplineSet$ElevationSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$ElevationSet
            r17.<init>()
            goto L_0x0435
        L_0x03fa:
            androidx.constraintlayout.motion.widget.SplineSet$RotationSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$RotationSet
            r17.<init>()
            goto L_0x0435
        L_0x0400:
            androidx.constraintlayout.motion.widget.SplineSet$AlphaSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$AlphaSet
            r17.<init>()
            goto L_0x0435
        L_0x0406:
            androidx.constraintlayout.motion.widget.SplineSet$ScaleYset r17 = new androidx.constraintlayout.motion.widget.SplineSet$ScaleYset
            r17.<init>()
            goto L_0x0435
        L_0x040c:
            androidx.constraintlayout.motion.widget.SplineSet$ScaleXset r17 = new androidx.constraintlayout.motion.widget.SplineSet$ScaleXset
            r17.<init>()
            goto L_0x0435
        L_0x0412:
            androidx.constraintlayout.motion.widget.SplineSet$ProgressSet r17 = new androidx.constraintlayout.motion.widget.SplineSet$ProgressSet
            r17.<init>()
            goto L_0x0435
        L_0x0418:
            androidx.constraintlayout.motion.widget.SplineSet$TranslationZset r17 = new androidx.constraintlayout.motion.widget.SplineSet$TranslationZset
            r17.<init>()
            goto L_0x0435
        L_0x041e:
            androidx.constraintlayout.motion.widget.SplineSet$TranslationYset r17 = new androidx.constraintlayout.motion.widget.SplineSet$TranslationYset
            r17.<init>()
            goto L_0x0435
        L_0x0424:
            androidx.constraintlayout.motion.widget.SplineSet$TranslationXset r17 = new androidx.constraintlayout.motion.widget.SplineSet$TranslationXset
            r17.<init>()
            goto L_0x0435
        L_0x042a:
            androidx.constraintlayout.motion.widget.SplineSet$RotationYset r17 = new androidx.constraintlayout.motion.widget.SplineSet$RotationYset
            r17.<init>()
            goto L_0x0435
        L_0x0430:
            androidx.constraintlayout.motion.widget.SplineSet$RotationXset r17 = new androidx.constraintlayout.motion.widget.SplineSet$RotationXset
            r17.<init>()
        L_0x0435:
            r18 = r13
            r13 = r17
        L_0x0439:
            if (r13 != 0) goto L_0x0457
            r29 = r3
            r28 = r4
            r17 = r18
            r18 = r19
            r13 = r20
            r3 = r27
            r4 = r31
            r19 = r6
            r20 = r7
            r6 = r22
            r7 = r30
            r22 = r1
            r1 = r45
            goto L_0x01fb
        L_0x0457:
            r13.mType = r8
            r17 = r6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r6 = r0.mAttributesMap
            r6.put(r8, r13)
            r29 = r3
            r28 = r4
            r13 = r20
            r6 = r22
            r3 = r27
            r4 = r31
            r22 = r1
            r20 = r7
            r7 = r30
            r1 = r45
            r42 = r19
            r19 = r17
            r17 = r18
            r18 = r42
            goto L_0x01fb
        L_0x047e:
            r27 = r3
            r31 = r4
            r30 = r7
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            r22 = r6
            r20 = r13
            r42 = r18
            r18 = r17
            r17 = r19
            r19 = r42
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r6 = r0.mKeyList
            if (r6 == 0) goto L_0x04b6
            java.util.Iterator r6 = r6.iterator()
        L_0x04a0:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x04b6
            java.lang.Object r8 = r6.next()
            androidx.constraintlayout.motion.widget.Key r8 = (androidx.constraintlayout.motion.widget.Key) r8
            boolean r13 = r8 instanceof androidx.constraintlayout.motion.widget.KeyAttributes
            if (r13 == 0) goto L_0x04a0
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r13 = r0.mAttributesMap
            r8.addValues(r13)
            goto L_0x04a0
        L_0x04b6:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r6 = r0.mStartPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r8 = r0.mAttributesMap
            r13 = 0
            r6.addValues(r8, r13)
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r6 = r0.mEndPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r8 = r0.mAttributesMap
            r13 = 100
            r6.addValues(r8, r13)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r6 = r0.mAttributesMap
            java.util.Set r6 = r6.keySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x04d1:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x0519
            java.lang.Object r8 = r6.next()
            java.lang.String r8 = (java.lang.String) r8
            boolean r13 = r5.containsKey(r8)
            if (r13 == 0) goto L_0x04ee
            java.lang.Object r13 = r5.get(r8)
            java.lang.Integer r13 = (java.lang.Integer) r13
            int r13 = r13.intValue()
            goto L_0x04ef
        L_0x04ee:
            r13 = 0
        L_0x04ef:
            r45 = r6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r6 = r0.mAttributesMap
            java.lang.Object r6 = r6.get(r8)
            androidx.constraintlayout.motion.widget.SplineSet r6 = (androidx.constraintlayout.motion.widget.SplineSet) r6
            r6.setup(r13)
            r6 = r45
            goto L_0x04d1
        L_0x04ff:
            r27 = r3
            r31 = r4
            r30 = r7
            r7 = r20
            r1 = r22
            r4 = r28
            r3 = r29
            r22 = r6
            r20 = r13
            r42 = r18
            r18 = r17
            r17 = r19
            r19 = r42
        L_0x0519:
            boolean r6 = r2.isEmpty()
            java.lang.String r8 = "CUSTOM"
            if (r6 != 0) goto L_0x0a72
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r6 = r0.mTimeCycleAttributesMap
            if (r6 != 0) goto L_0x052c
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r0.mTimeCycleAttributesMap = r6
        L_0x052c:
            java.util.Iterator r2 = r2.iterator()
        L_0x0530:
            boolean r6 = r2.hasNext()
            if (r6 == 0) goto L_0x06bb
            java.lang.Object r6 = r2.next()
            java.lang.String r6 = (java.lang.String) r6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r13 = r0.mTimeCycleAttributesMap
            boolean r13 = r13.containsKey(r6)
            if (r13 == 0) goto L_0x0545
            goto L_0x0530
        L_0x0545:
            boolean r13 = r6.startsWith(r15)
            if (r13 == 0) goto L_0x05a1
            android.util.SparseArray r13 = new android.util.SparseArray
            r13.<init>()
            r45 = r2
            r2 = r30
            java.lang.String[] r28 = r6.split(r2)
            r29 = 1
            r2 = r28[r29]
            r28 = r15
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r15 = r0.mKeyList
            java.util.Iterator r15 = r15.iterator()
        L_0x0564:
            boolean r29 = r15.hasNext()
            if (r29 == 0) goto L_0x058d
            java.lang.Object r29 = r15.next()
            r32 = r15
            r15 = r29
            androidx.constraintlayout.motion.widget.Key r15 = (androidx.constraintlayout.motion.widget.Key) r15
            r29 = r5
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r15.mCustomConstraints
            if (r5 != 0) goto L_0x057b
            goto L_0x0588
        L_0x057b:
            java.lang.Object r5 = r5.get(r2)
            androidx.constraintlayout.widget.ConstraintAttribute r5 = (androidx.constraintlayout.widget.ConstraintAttribute) r5
            if (r5 == 0) goto L_0x0588
            int r15 = r15.mFramePosition
            r13.append(r15, r5)
        L_0x0588:
            r5 = r29
            r15 = r32
            goto L_0x0564
        L_0x058d:
            r29 = r5
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$CustomSet r2 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$CustomSet
            r2.<init>(r6, r13)
            r15 = r2
            r2 = r17
            r13 = r18
            r18 = r19
            r17 = r4
            r4 = r46
            goto L_0x06a1
        L_0x05a1:
            r45 = r2
            r29 = r5
            r28 = r15
            int r2 = r6.hashCode()
            switch(r2) {
                case -1249320806: goto L_0x0637;
                case -1249320805: goto L_0x0625;
                case -1225497657: goto L_0x0615;
                case -1225497656: goto L_0x060c;
                case -1225497655: goto L_0x0603;
                case -1001078227: goto L_0x05f3;
                case -908189618: goto L_0x05e7;
                case -908189617: goto L_0x05de;
                case -40300674: goto L_0x05d4;
                case -4379043: goto L_0x05ca;
                case 37232917: goto L_0x05c0;
                case 92909918: goto L_0x05b6;
                default: goto L_0x05ae;
            }
        L_0x05ae:
            r2 = r17
        L_0x05b0:
            r13 = r18
            r5 = r19
            goto L_0x0646
        L_0x05b6:
            boolean r2 = r6.equals(r9)
            if (r2 != 0) goto L_0x05bd
            goto L_0x05ae
        L_0x05bd:
            r2 = r21
            goto L_0x05ef
        L_0x05c0:
            boolean r2 = r6.equals(r12)
            if (r2 != 0) goto L_0x05c7
            goto L_0x05ae
        L_0x05c7:
            r2 = r23
            goto L_0x05ef
        L_0x05ca:
            boolean r2 = r6.equals(r10)
            if (r2 != 0) goto L_0x05d1
            goto L_0x05ae
        L_0x05d1:
            r2 = r24
            goto L_0x05ef
        L_0x05d4:
            boolean r2 = r6.equals(r11)
            if (r2 != 0) goto L_0x05db
            goto L_0x05ae
        L_0x05db:
            r2 = r25
            goto L_0x05ef
        L_0x05de:
            boolean r2 = r6.equals(r14)
            if (r2 != 0) goto L_0x05e5
            goto L_0x05ae
        L_0x05e5:
            r2 = 7
            goto L_0x05ef
        L_0x05e7:
            boolean r2 = r6.equals(r1)
            if (r2 != 0) goto L_0x05ee
            goto L_0x05ae
        L_0x05ee:
            r2 = 6
        L_0x05ef:
            r15 = r2
            r2 = r17
            goto L_0x0620
        L_0x05f3:
            boolean r2 = r6.equals(r3)
            if (r2 != 0) goto L_0x05fa
            goto L_0x05ae
        L_0x05fa:
            r2 = r17
            r13 = r18
            r5 = r19
            r15 = r26
            goto L_0x0647
        L_0x0603:
            boolean r2 = r6.equals(r4)
            if (r2 != 0) goto L_0x060a
            goto L_0x05ae
        L_0x060a:
            r2 = 4
            goto L_0x05ef
        L_0x060c:
            boolean r2 = r6.equals(r7)
            if (r2 != 0) goto L_0x0613
            goto L_0x05ae
        L_0x0613:
            r2 = 3
            goto L_0x05ef
        L_0x0615:
            r2 = r17
            boolean r5 = r6.equals(r2)
            if (r5 != 0) goto L_0x061e
            goto L_0x05b0
        L_0x061e:
            r5 = 2
            r15 = r5
        L_0x0620:
            r13 = r18
            r5 = r19
            goto L_0x0647
        L_0x0625:
            r2 = r17
            r5 = r19
            boolean r13 = r6.equals(r5)
            if (r13 != 0) goto L_0x0632
            r13 = r18
            goto L_0x0646
        L_0x0632:
            r13 = 1
            r15 = r13
            r13 = r18
            goto L_0x0647
        L_0x0637:
            r2 = r17
            r13 = r18
            r5 = r19
            boolean r15 = r6.equals(r13)
            if (r15 != 0) goto L_0x0644
            goto L_0x0646
        L_0x0644:
            r15 = 0
            goto L_0x0647
        L_0x0646:
            r15 = -1
        L_0x0647:
            switch(r15) {
                case 0: goto L_0x0694;
                case 1: goto L_0x068e;
                case 2: goto L_0x0688;
                case 3: goto L_0x0682;
                case 4: goto L_0x067c;
                case 5: goto L_0x0676;
                case 6: goto L_0x0670;
                case 7: goto L_0x066a;
                case 8: goto L_0x0664;
                case 9: goto L_0x065e;
                case 10: goto L_0x0658;
                case 11: goto L_0x0652;
                default: goto L_0x064a;
            }
        L_0x064a:
            r17 = r4
            r18 = r5
            r4 = r46
            r15 = 0
            goto L_0x06a1
        L_0x0652:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$AlphaSet r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$AlphaSet
            r15.<init>()
            goto L_0x0699
        L_0x0658:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$PathRotate r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$PathRotate
            r15.<init>()
            goto L_0x0699
        L_0x065e:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ElevationSet r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ElevationSet
            r15.<init>()
            goto L_0x0699
        L_0x0664:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationSet r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationSet
            r15.<init>()
            goto L_0x0699
        L_0x066a:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ScaleYset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ScaleYset
            r15.<init>()
            goto L_0x0699
        L_0x0670:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ScaleXset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ScaleXset
            r15.<init>()
            goto L_0x0699
        L_0x0676:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ProgressSet r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$ProgressSet
            r15.<init>()
            goto L_0x0699
        L_0x067c:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationZset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationZset
            r15.<init>()
            goto L_0x0699
        L_0x0682:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationYset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationYset
            r15.<init>()
            goto L_0x0699
        L_0x0688:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationXset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$TranslationXset
            r15.<init>()
            goto L_0x0699
        L_0x068e:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationYset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationYset
            r15.<init>()
            goto L_0x0699
        L_0x0694:
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationXset r15 = new androidx.constraintlayout.motion.widget.TimeCycleSplineSet$RotationXset
            r15.<init>()
        L_0x0699:
            r17 = r4
            r18 = r5
            r4 = r46
            r15.last_time = r4
        L_0x06a1:
            if (r15 != 0) goto L_0x06a4
            goto L_0x06ab
        L_0x06a4:
            r15.mType = r6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r4 = r0.mTimeCycleAttributesMap
            r4.put(r6, r15)
        L_0x06ab:
            r4 = r17
            r19 = r18
            r15 = r28
            r5 = r29
            r17 = r2
            r18 = r13
            r2 = r45
            goto L_0x0530
        L_0x06bb:
            r29 = r5
            r28 = r15
            r2 = r17
            r13 = r18
            r18 = r19
            r17 = r4
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r4 = r0.mKeyList
            if (r4 == 0) goto L_0x0a32
            java.util.Iterator r4 = r4.iterator()
        L_0x06cf:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0a32
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.motion.widget.Key r5 = (androidx.constraintlayout.motion.widget.Key) r5
            boolean r6 = r5 instanceof androidx.constraintlayout.motion.widget.KeyTimeCycle
            if (r6 == 0) goto L_0x0a1e
            androidx.constraintlayout.motion.widget.KeyTimeCycle r5 = (androidx.constraintlayout.motion.widget.KeyTimeCycle) r5
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r6 = r0.mTimeCycleAttributesMap
            java.util.Objects.requireNonNull(r5)
            java.util.Set r15 = r6.keySet()
            java.util.Iterator r15 = r15.iterator()
        L_0x06ee:
            boolean r19 = r15.hasNext()
            if (r19 == 0) goto L_0x0a1e
            java.lang.Object r19 = r15.next()
            r45 = r4
            r4 = r19
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r19 = r6.get(r4)
            r32 = r19
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet r32 = (androidx.constraintlayout.motion.widget.TimeCycleSplineSet) r32
            boolean r19 = r4.startsWith(r8)
            if (r19 == 0) goto L_0x0766
            r19 = r6
            r6 = 7
            java.lang.String r4 = r4.substring(r6)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r6 = r5.mCustomConstraints
            java.lang.Object r4 = r6.get(r4)
            androidx.constraintlayout.widget.ConstraintAttribute r4 = (androidx.constraintlayout.widget.ConstraintAttribute) r4
            if (r4 == 0) goto L_0x075f
            r6 = r32
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet$CustomSet r6 = (androidx.constraintlayout.motion.widget.TimeCycleSplineSet.CustomSet) r6
            r46 = r15
            int r15 = r5.mFramePosition
            r30 = r8
            float r8 = r5.mWavePeriod
            int r0 = r5.mWaveShape
            r38 = r13
            float r13 = r5.mWaveOffset
            java.util.Objects.requireNonNull(r6)
            r47 = r5
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintAttribute> r5 = r6.mConstraintAttributeList
            r5.append(r15, r4)
            android.util.SparseArray<float[]> r4 = r6.mWaveProperties
            r5 = 2
            float[] r5 = new float[r5]
            r32 = 0
            r5[r32] = r8
            r8 = 1
            r5[r8] = r13
            r4.append(r15, r5)
            int r4 = r6.mWaveShape
            int r0 = java.lang.Math.max(r4, r0)
            r6.mWaveShape = r0
            r0 = r43
            r4 = r45
            r15 = r46
            r5 = r47
            r6 = r19
            r8 = r30
            r13 = r38
            goto L_0x06ee
        L_0x075f:
            r0 = r43
            r4 = r45
            r6 = r19
            goto L_0x06ee
        L_0x0766:
            r47 = r5
            r19 = r6
            r30 = r8
            r38 = r13
            r46 = r15
            int r0 = r4.hashCode()
            switch(r0) {
                case -1249320806: goto L_0x0801;
                case -1249320805: goto L_0x07ef;
                case -1225497657: goto L_0x07df;
                case -1225497656: goto L_0x07d4;
                case -1225497655: goto L_0x07c9;
                case -1001078227: goto L_0x07bb;
                case -908189618: goto L_0x07af;
                case -908189617: goto L_0x07a6;
                case -40300674: goto L_0x0799;
                case -4379043: goto L_0x078f;
                case 37232917: goto L_0x0785;
                case 92909918: goto L_0x077b;
                default: goto L_0x0777;
            }
        L_0x0777:
            r0 = r17
            goto L_0x07e7
        L_0x077b:
            boolean r0 = r4.equals(r9)
            if (r0 != 0) goto L_0x0782
            goto L_0x0777
        L_0x0782:
            r0 = r21
            goto L_0x07a2
        L_0x0785:
            boolean r0 = r4.equals(r12)
            if (r0 != 0) goto L_0x078c
            goto L_0x07c1
        L_0x078c:
            r0 = r23
            goto L_0x07a2
        L_0x078f:
            boolean r0 = r4.equals(r10)
            if (r0 != 0) goto L_0x0796
            goto L_0x0777
        L_0x0796:
            r0 = r24
            goto L_0x07a2
        L_0x0799:
            boolean r0 = r4.equals(r11)
            if (r0 != 0) goto L_0x07a0
            goto L_0x07c1
        L_0x07a0:
            r0 = r25
        L_0x07a2:
            r5 = r0
            r0 = r17
            goto L_0x07eb
        L_0x07a6:
            boolean r0 = r4.equals(r14)
            if (r0 != 0) goto L_0x07ad
            goto L_0x0777
        L_0x07ad:
            r0 = 7
            goto L_0x07b7
        L_0x07af:
            boolean r0 = r4.equals(r1)
            if (r0 != 0) goto L_0x07b6
            goto L_0x07c1
        L_0x07b6:
            r0 = 6
        L_0x07b7:
            r8 = r0
            r0 = r17
            goto L_0x07ec
        L_0x07bb:
            boolean r0 = r4.equals(r3)
            if (r0 != 0) goto L_0x07c2
        L_0x07c1:
            goto L_0x0777
        L_0x07c2:
            r0 = r17
            r5 = r18
            r8 = r26
            goto L_0x07fe
        L_0x07c9:
            r0 = r17
            boolean r5 = r4.equals(r0)
            if (r5 != 0) goto L_0x07d2
            goto L_0x07dc
        L_0x07d2:
            r5 = 4
            goto L_0x07eb
        L_0x07d4:
            r0 = r17
            boolean r5 = r4.equals(r7)
            if (r5 != 0) goto L_0x07dd
        L_0x07dc:
            goto L_0x07e7
        L_0x07dd:
            r5 = 3
            goto L_0x07eb
        L_0x07df:
            r0 = r17
            boolean r5 = r4.equals(r2)
            if (r5 != 0) goto L_0x07ea
        L_0x07e7:
            r5 = r18
            goto L_0x07f9
        L_0x07ea:
            r5 = 2
        L_0x07eb:
            r8 = r5
        L_0x07ec:
            r5 = r18
            goto L_0x07fe
        L_0x07ef:
            r0 = r17
            r5 = r18
            boolean r6 = r4.equals(r5)
            if (r6 != 0) goto L_0x07fc
        L_0x07f9:
            r6 = r38
            goto L_0x0810
        L_0x07fc:
            r6 = 1
            r8 = r6
        L_0x07fe:
            r6 = r38
            goto L_0x0811
        L_0x0801:
            r0 = r17
            r5 = r18
            r6 = r38
            boolean r8 = r4.equals(r6)
            if (r8 != 0) goto L_0x080e
            goto L_0x0810
        L_0x080e:
            r8 = 0
            goto L_0x0811
        L_0x0810:
            r8 = -1
        L_0x0811:
            switch(r8) {
                case 0: goto L_0x09e8;
                case 1: goto L_0x09c2;
                case 2: goto L_0x099c;
                case 3: goto L_0x0975;
                case 4: goto L_0x094e;
                case 5: goto L_0x0927;
                case 6: goto L_0x0900;
                case 7: goto L_0x08d9;
                case 8: goto L_0x08b2;
                case 9: goto L_0x088b;
                case 10: goto L_0x0864;
                case 11: goto L_0x0837;
                default: goto L_0x0814;
            }
        L_0x0814:
            r8 = r47
            r18 = r5
            r17 = r6
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "UNKNOWN addValues \""
            r5.append(r6)
            r5.append(r4)
            java.lang.String r4 = "\""
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            java.lang.String r5 = "KeyTimeCycles"
            android.util.Log.e(r5, r4)
            goto L_0x0a0d
        L_0x0837:
            r8 = r47
            float r4 = r8.mAlpha
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x085e
            int r4 = r8.mFramePosition
            float r13 = r8.mAlpha
            float r15 = r8.mWavePeriod
            r17 = r6
            int r6 = r8.mWaveShape
            r18 = r5
            float r5 = r8.mWaveOffset
            r33 = r4
            r34 = r13
            r35 = r15
            r36 = r6
            r37 = r5
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x085e:
            r18 = r5
            r17 = r6
            goto L_0x0a0d
        L_0x0864:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mTransitionPathRotate
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mTransitionPathRotate
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x088b:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mElevation
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mElevation
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x08b2:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mRotation
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mRotation
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x08d9:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mScaleY
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mScaleY
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x0900:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mScaleX
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mScaleX
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x0927:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mProgress
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mProgress
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x094e:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mTranslationZ
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mTranslationZ
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x0975:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mTranslationY
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mTranslationY
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x099c:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mTranslationX
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mTranslationX
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x09c2:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mRotationY
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mRotationY
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
            goto L_0x0a0d
        L_0x09e8:
            r8 = r47
            r18 = r5
            r17 = r6
            float r4 = r8.mRotationX
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L_0x0a0d
            int r4 = r8.mFramePosition
            float r5 = r8.mRotationX
            float r6 = r8.mWavePeriod
            int r13 = r8.mWaveShape
            float r15 = r8.mWaveOffset
            r33 = r4
            r34 = r5
            r35 = r6
            r36 = r13
            r37 = r15
            r32.setPoint(r33, r34, r35, r36, r37)
        L_0x0a0d:
            r4 = r45
            r15 = r46
            r5 = r8
            r13 = r17
            r6 = r19
            r8 = r30
            r17 = r0
            r0 = r43
            goto L_0x06ee
        L_0x0a1e:
            r45 = r4
            r30 = r8
            r0 = r17
            r17 = r13
            r4 = r45
            r13 = r17
            r8 = r30
            r17 = r0
            r0 = r43
            goto L_0x06cf
        L_0x0a32:
            r30 = r8
            r0 = r17
            r17 = r13
            r4 = r43
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r5 = r4.mTimeCycleAttributesMap
            java.util.Set r5 = r5.keySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x0a44:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0a81
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            r8 = r29
            boolean r13 = r8.containsKey(r6)
            if (r13 == 0) goto L_0x0a63
            java.lang.Object r13 = r8.get(r6)
            java.lang.Integer r13 = (java.lang.Integer) r13
            int r13 = r13.intValue()
            goto L_0x0a64
        L_0x0a63:
            r13 = 0
        L_0x0a64:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r15 = r4.mTimeCycleAttributesMap
            java.lang.Object r6 = r15.get(r6)
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet r6 = (androidx.constraintlayout.motion.widget.TimeCycleSplineSet) r6
            r6.setup(r13)
            r29 = r8
            goto L_0x0a44
        L_0x0a72:
            r30 = r8
            r28 = r15
            r2 = r17
            r17 = r18
            r18 = r19
            r42 = r4
            r4 = r0
            r0 = r42
        L_0x0a81:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r5 = r4.mMotionPaths
            int r5 = r5.size()
            int r5 = r5 + 2
            androidx.constraintlayout.motion.widget.MotionPaths[] r6 = new androidx.constraintlayout.motion.widget.MotionPaths[r5]
            androidx.constraintlayout.motion.widget.MotionPaths r8 = r4.mStartMotionPath
            r13 = 0
            r6[r13] = r8
            int r8 = r5 + -1
            androidx.constraintlayout.motion.widget.MotionPaths r15 = r4.mEndMotionPath
            r6[r8] = r15
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r8 = r4.mMotionPaths
            int r8 = r8.size()
            if (r8 <= 0) goto L_0x0aa5
            int r8 = r4.mCurveFitType
            r15 = -1
            if (r8 != r15) goto L_0x0aa5
            r4.mCurveFitType = r13
        L_0x0aa5:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r8 = r4.mMotionPaths
            java.util.Iterator r8 = r8.iterator()
            r13 = 1
        L_0x0aac:
            boolean r15 = r8.hasNext()
            if (r15 == 0) goto L_0x0abf
            java.lang.Object r15 = r8.next()
            androidx.constraintlayout.motion.widget.MotionPaths r15 = (androidx.constraintlayout.motion.widget.MotionPaths) r15
            int r19 = r13 + 1
            r6[r13] = r15
            r13 = r19
            goto L_0x0aac
        L_0x0abf:
            java.util.HashSet r8 = new java.util.HashSet
            r8.<init>()
            androidx.constraintlayout.motion.widget.MotionPaths r13 = r4.mEndMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r13 = r13.attributes
            java.util.Set r13 = r13.keySet()
            java.util.Iterator r13 = r13.iterator()
        L_0x0ad0:
            boolean r15 = r13.hasNext()
            if (r15 == 0) goto L_0x0b12
            java.lang.Object r15 = r13.next()
            java.lang.String r15 = (java.lang.String) r15
            r45 = r13
            androidx.constraintlayout.motion.widget.MotionPaths r13 = r4.mStartMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r13 = r13.attributes
            boolean r13 = r13.containsKey(r15)
            if (r13 == 0) goto L_0x0b07
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r19 = r2
            r2 = r28
            r13.append(r2)
            r13.append(r15)
            java.lang.String r13 = r13.toString()
            r2 = r27
            boolean r13 = r2.contains(r13)
            if (r13 != 0) goto L_0x0b0b
            r8.add(r15)
            goto L_0x0b0b
        L_0x0b07:
            r19 = r2
            r2 = r27
        L_0x0b0b:
            r13 = r45
            r27 = r2
            r2 = r19
            goto L_0x0ad0
        L_0x0b12:
            r19 = r2
            r2 = 0
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.Object[] r2 = r8.toArray(r2)
            java.lang.String[] r2 = (java.lang.String[]) r2
            r4.mAttributeNames = r2
            int r2 = r2.length
            int[] r2 = new int[r2]
            r4.mAttributeInterpCount = r2
            r2 = 0
        L_0x0b25:
            java.lang.String[] r8 = r4.mAttributeNames
            int r13 = r8.length
            if (r2 >= r13) goto L_0x0b5b
            r8 = r8[r2]
            int[] r13 = r4.mAttributeInterpCount
            r15 = 0
            r13[r2] = r15
            r13 = 0
        L_0x0b32:
            if (r13 >= r5) goto L_0x0b58
            r15 = r6[r13]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r15 = r15.attributes
            boolean r15 = r15.containsKey(r8)
            if (r15 == 0) goto L_0x0b55
            int[] r15 = r4.mAttributeInterpCount
            r27 = r15[r2]
            r13 = r6[r13]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r13 = r13.attributes
            java.lang.Object r8 = r13.get(r8)
            androidx.constraintlayout.widget.ConstraintAttribute r8 = (androidx.constraintlayout.widget.ConstraintAttribute) r8
            int r8 = r8.noOfInterpValues()
            int r8 = r8 + r27
            r15[r2] = r8
            goto L_0x0b58
        L_0x0b55:
            int r13 = r13 + 1
            goto L_0x0b32
        L_0x0b58:
            int r2 = r2 + 1
            goto L_0x0b25
        L_0x0b5b:
            r2 = 0
            r2 = r6[r2]
            int r2 = r2.mPathMotionArc
            r13 = -1
            if (r2 == r13) goto L_0x0b65
            r2 = 1
            goto L_0x0b66
        L_0x0b65:
            r2 = 0
        L_0x0b66:
            int r8 = r8.length
            int r8 = r8 + 18
            boolean[] r13 = new boolean[r8]
            r15 = 1
        L_0x0b6c:
            if (r15 >= r5) goto L_0x0bdb
            r27 = r7
            r7 = r6[r15]
            int r28 = r15 + -1
            r29 = r0
            r0 = r6[r28]
            java.util.Objects.requireNonNull(r7)
            r28 = 0
            boolean r32 = r13[r28]
            r33 = r3
            float r3 = r7.position
            r34 = r1
            float r1 = r0.position
            boolean r1 = androidx.constraintlayout.motion.widget.MotionPaths.diff(r3, r1)
            r1 = r32 | r1
            r13[r28] = r1
            r1 = 1
            boolean r1 = r13[r1]
            float r3 = r7.f12x
            r28 = r14
            float r14 = r0.f12x
            boolean r3 = androidx.constraintlayout.motion.widget.MotionPaths.diff(r3, r14)
            r3 = r3 | r2
            r1 = r1 | r3
            r3 = 1
            r13[r3] = r1
            r1 = 2
            boolean r1 = r13[r1]
            float r3 = r7.f13y
            float r14 = r0.f13y
            boolean r3 = androidx.constraintlayout.motion.widget.MotionPaths.diff(r3, r14)
            r3 = r3 | r2
            r1 = r1 | r3
            r3 = 2
            r13[r3] = r1
            r1 = 3
            boolean r1 = r13[r1]
            float r3 = r7.width
            float r14 = r0.width
            boolean r3 = androidx.constraintlayout.motion.widget.MotionPaths.diff(r3, r14)
            r1 = r1 | r3
            r3 = 3
            r13[r3] = r1
            r1 = 4
            boolean r3 = r13[r1]
            float r7 = r7.height
            float r0 = r0.height
            boolean r0 = androidx.constraintlayout.motion.widget.MotionPaths.diff(r7, r0)
            r0 = r0 | r3
            r13[r1] = r0
            int r15 = r15 + 1
            r7 = r27
            r14 = r28
            r0 = r29
            r3 = r33
            r1 = r34
            goto L_0x0b6c
        L_0x0bdb:
            r29 = r0
            r34 = r1
            r33 = r3
            r27 = r7
            r28 = r14
            r0 = 0
            r1 = 1
        L_0x0be7:
            if (r1 >= r8) goto L_0x0bf2
            boolean r2 = r13[r1]
            if (r2 == 0) goto L_0x0bef
            int r0 = r0 + 1
        L_0x0bef:
            int r1 = r1 + 1
            goto L_0x0be7
        L_0x0bf2:
            int[] r1 = new int[r0]
            r4.mInterpolateVariables = r1
            double[] r1 = new double[r0]
            r4.mInterpolateData = r1
            double[] r0 = new double[r0]
            r4.mInterpolateVelocity = r0
            r0 = 0
            r1 = 1
        L_0x0c00:
            if (r1 >= r8) goto L_0x0c10
            boolean r2 = r13[r1]
            if (r2 == 0) goto L_0x0c0d
            int[] r2 = r4.mInterpolateVariables
            int r3 = r0 + 1
            r2[r0] = r1
            r0 = r3
        L_0x0c0d:
            int r1 = r1 + 1
            goto L_0x0c00
        L_0x0c10:
            int[] r0 = r4.mInterpolateVariables
            int r0 = r0.length
            r1 = 2
            int[] r1 = new int[r1]
            r2 = 1
            r1[r2] = r0
            r0 = 0
            r1[r0] = r5
            r0 = r16
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r0, r1)
            double[][] r1 = (double[][]) r1
            double[] r2 = new double[r5]
            r3 = 0
        L_0x0c27:
            if (r3 >= r5) goto L_0x0c85
            r7 = r6[r3]
            r8 = r1[r3]
            int[] r13 = r4.mInterpolateVariables
            java.util.Objects.requireNonNull(r7)
            r14 = 6
            float[] r14 = new float[r14]
            float r15 = r7.position
            r16 = 0
            r14[r16] = r15
            float r15 = r7.f12x
            r16 = 1
            r14[r16] = r15
            float r15 = r7.f13y
            r16 = 2
            r14[r16] = r15
            float r15 = r7.width
            r16 = 3
            r14[r16] = r15
            float r15 = r7.height
            r16 = 4
            r14[r16] = r15
            float r7 = r7.mPathRotate
            r14[r26] = r7
            r7 = 0
            r15 = 0
            r16 = r11
        L_0x0c5b:
            int r11 = r13.length
            if (r7 >= r11) goto L_0x0c77
            r11 = r13[r7]
            r32 = r10
            r10 = 6
            if (r11 >= r10) goto L_0x0c72
            int r10 = r15 + 1
            r11 = r13[r7]
            r11 = r14[r11]
            r45 = r10
            double r10 = (double) r11
            r8[r15] = r10
            r15 = r45
        L_0x0c72:
            int r7 = r7 + 1
            r10 = r32
            goto L_0x0c5b
        L_0x0c77:
            r32 = r10
            r7 = r6[r3]
            float r7 = r7.time
            double r7 = (double) r7
            r2[r3] = r7
            int r3 = r3 + 1
            r11 = r16
            goto L_0x0c27
        L_0x0c85:
            r32 = r10
            r16 = r11
            r8 = 0
        L_0x0c8a:
            int[] r10 = r4.mInterpolateVariables
            int r11 = r10.length
            if (r8 >= r11) goto L_0x0cbf
            r10 = r10[r8]
            java.lang.String[] r11 = androidx.constraintlayout.motion.widget.MotionPaths.names
            r13 = 6
            if (r10 >= r13) goto L_0x0cbc
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            int[] r13 = r4.mInterpolateVariables
            r13 = r13[r8]
            r11 = r11[r13]
            java.lang.String r13 = " ["
            java.lang.String r10 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1.m8m(r10, r11, r13)
            r11 = 0
        L_0x0ca8:
            if (r11 >= r5) goto L_0x0cbc
            java.lang.StringBuilder r10 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            r13 = r1[r11]
            r13 = r13[r8]
            r10.append(r13)
            java.lang.String r10 = r10.toString()
            int r11 = r11 + 1
            goto L_0x0ca8
        L_0x0cbc:
            int r8 = r8 + 1
            goto L_0x0c8a
        L_0x0cbf:
            java.lang.String[] r8 = r4.mAttributeNames
            int r8 = r8.length
            int r8 = r8 + 1
            androidx.constraintlayout.motion.utils.CurveFit[] r8 = new androidx.constraintlayout.motion.utils.CurveFit[r8]
            r4.mSpline = r8
            r8 = 0
        L_0x0cc9:
            java.lang.String[] r10 = r4.mAttributeNames
            int r11 = r10.length
            if (r8 >= r11) goto L_0x0d8c
            r10 = r10[r8]
            r11 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0cd4:
            if (r11 >= r5) goto L_0x0d71
            r3 = r6[r11]
            java.util.Objects.requireNonNull(r3)
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r3 = r3.attributes
            boolean r3 = r3.containsKey(r10)
            if (r3 == 0) goto L_0x0d65
            if (r15 != 0) goto L_0x0d09
            double[] r14 = new double[r5]
            r3 = r6[r11]
            java.util.Objects.requireNonNull(r3)
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r3 = r3.attributes
            java.lang.Object r3 = r3.get(r10)
            androidx.constraintlayout.widget.ConstraintAttribute r3 = (androidx.constraintlayout.widget.ConstraintAttribute) r3
            int r3 = r3.noOfInterpValues()
            r15 = 2
            int[] r15 = new int[r15]
            r35 = 1
            r15[r35] = r3
            r3 = 0
            r15[r3] = r5
            java.lang.Object r3 = java.lang.reflect.Array.newInstance(r0, r15)
            r15 = r3
            double[][] r15 = (double[][]) r15
        L_0x0d09:
            r3 = r6[r11]
            float r3 = r3.time
            r35 = r8
            double r7 = (double) r3
            r14[r13] = r7
            r3 = r6[r11]
            r7 = r15[r13]
            java.util.Objects.requireNonNull(r3)
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r3 = r3.attributes
            java.lang.Object r3 = r3.get(r10)
            androidx.constraintlayout.widget.ConstraintAttribute r3 = (androidx.constraintlayout.widget.ConstraintAttribute) r3
            int r8 = r3.noOfInterpValues()
            r47 = r10
            r10 = 1
            if (r8 != r10) goto L_0x0d37
            float r3 = r3.getValueToInterpolate()
            r8 = r14
            r10 = r15
            double r14 = (double) r3
            r3 = 0
            r7[r3] = r14
        L_0x0d34:
            r38 = r8
            goto L_0x0d5f
        L_0x0d37:
            r8 = r14
            r10 = r15
            int r14 = r3.noOfInterpValues()
            float[] r15 = new float[r14]
            r3.getValuesToInterpolate(r15)
            r3 = 0
            r36 = 0
        L_0x0d45:
            if (r3 >= r14) goto L_0x0d34
            int r37 = r36 + 1
            r38 = r8
            r8 = r15[r3]
            r39 = r14
            r40 = r15
            double r14 = (double) r8
            r7[r36] = r14
            int r3 = r3 + 1
            r36 = r37
            r8 = r38
            r14 = r39
            r15 = r40
            goto L_0x0d45
        L_0x0d5f:
            int r13 = r13 + 1
            r15 = r10
            r14 = r38
            goto L_0x0d69
        L_0x0d65:
            r35 = r8
            r47 = r10
        L_0x0d69:
            int r11 = r11 + 1
            r10 = r47
            r8 = r35
            goto L_0x0cd4
        L_0x0d71:
            r35 = r8
            double[] r3 = java.util.Arrays.copyOf(r14, r13)
            java.lang.Object[] r7 = java.util.Arrays.copyOf(r15, r13)
            double[][] r7 = (double[][]) r7
            androidx.constraintlayout.motion.utils.CurveFit[] r8 = r4.mSpline
            int r10 = r35 + 1
            int r11 = r4.mCurveFitType
            androidx.constraintlayout.motion.utils.CurveFit r3 = androidx.constraintlayout.motion.utils.CurveFit.get(r11, r3, r7)
            r8[r10] = r3
            r8 = r10
            goto L_0x0cc9
        L_0x0d8c:
            androidx.constraintlayout.motion.utils.CurveFit[] r3 = r4.mSpline
            int r7 = r4.mCurveFitType
            androidx.constraintlayout.motion.utils.CurveFit r1 = androidx.constraintlayout.motion.utils.CurveFit.get(r7, r2, r1)
            r2 = 0
            r3[r2] = r1
            r1 = r6[r2]
            int r1 = r1.mPathMotionArc
            r3 = -1
            if (r1 == r3) goto L_0x0dde
            int[] r1 = new int[r5]
            double[] r3 = new double[r5]
            r7 = 2
            int[] r8 = new int[r7]
            r10 = 1
            r8[r10] = r7
            r8[r2] = r5
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r0, r8)
            double[][] r0 = (double[][]) r0
            r2 = 0
        L_0x0db1:
            if (r2 >= r5) goto L_0x0dd7
            r7 = r6[r2]
            int r7 = r7.mPathMotionArc
            r1[r2] = r7
            r7 = r6[r2]
            float r7 = r7.time
            double r7 = (double) r7
            r3[r2] = r7
            r7 = r0[r2]
            r8 = r6[r2]
            float r8 = r8.f12x
            double r10 = (double) r8
            r8 = 0
            r7[r8] = r10
            r7 = r0[r2]
            r8 = r6[r2]
            float r8 = r8.f13y
            double r10 = (double) r8
            r8 = 1
            r7[r8] = r10
            int r2 = r2 + 1
            goto L_0x0db1
        L_0x0dd7:
            androidx.constraintlayout.motion.utils.ArcCurveFit r2 = new androidx.constraintlayout.motion.utils.ArcCurveFit
            r2.<init>(r1, r3, r0)
            r4.mArcSpline = r2
        L_0x0dde:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r4.mCycleMap = r0
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r0 = r4.mKeyList
            if (r0 == 0) goto L_0x1519
            java.util.Iterator r0 = r31.iterator()
            r1 = 2143289344(0x7fc00000, float:NaN)
        L_0x0def:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x11c8
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            r3 = r30
            boolean r5 = r2.startsWith(r3)
            if (r5 == 0) goto L_0x0e1f
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$CustomSet r5 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$CustomSet
            r5.<init>()
            r47 = r0
            r8 = r5
            r7 = r16
            r0 = r19
            r5 = r20
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            goto L_0x10a1
        L_0x0e1f:
            int r5 = r2.hashCode()
            switch(r5) {
                case -1249320806: goto L_0x1021;
                case -1249320805: goto L_0x0ff9;
                case -1225497657: goto L_0x0fd5;
                case -1225497656: goto L_0x0fa7;
                case -1225497655: goto L_0x0f80;
                case -1001078227: goto L_0x0f57;
                case -908189618: goto L_0x0f22;
                case -908189617: goto L_0x0eef;
                case -797520672: goto L_0x0ecd;
                case -40300674: goto L_0x0eb9;
                case -4379043: goto L_0x0e92;
                case 37232917: goto L_0x0e82;
                case 92909918: goto L_0x0e69;
                case 156108012: goto L_0x0e3e;
                default: goto L_0x0e26;
            }
        L_0x0e26:
            r47 = r0
            r7 = r16
            r8 = r17
            r0 = r19
            r5 = r20
        L_0x0e30:
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
        L_0x0e38:
            r13 = r33
            r11 = r34
            goto L_0x1041
        L_0x0e3e:
            r5 = r20
            boolean r6 = r2.equals(r5)
            if (r6 != 0) goto L_0x0e4f
        L_0x0e46:
            r47 = r0
            r7 = r16
            r8 = r17
            r0 = r19
            goto L_0x0e30
        L_0x0e4f:
            r6 = 13
            r47 = r0
            r7 = r16
            r8 = r17
            r0 = r19
            r15 = r27
            r10 = r28
            r14 = r29
            r13 = r33
            r11 = r34
            r16 = r6
            r6 = r32
            goto L_0x1043
        L_0x0e69:
            r5 = r20
            boolean r6 = r2.equals(r9)
            if (r6 != 0) goto L_0x0e72
            goto L_0x0e46
        L_0x0e72:
            r7 = r16
            r8 = r22
            r10 = r28
            r6 = r32
            r13 = r33
            r11 = r34
            r14 = 12
            goto L_0x0f70
        L_0x0e82:
            r5 = r20
            boolean r6 = r2.equals(r12)
            if (r6 != 0) goto L_0x0e8d
            r6 = r32
            goto L_0x0e9c
        L_0x0e8d:
            r7 = r21
            r6 = r32
            goto L_0x0ea1
        L_0x0e92:
            r5 = r20
            r6 = r32
            boolean r7 = r2.equals(r6)
            if (r7 != 0) goto L_0x0e9f
        L_0x0e9c:
            r7 = r16
            goto L_0x0ec5
        L_0x0e9f:
            r7 = r23
        L_0x0ea1:
            r47 = r0
            r8 = r17
            r0 = r19
            r15 = r27
            r10 = r28
            r14 = r29
            r13 = r33
            r11 = r34
            r42 = r16
            r16 = r7
            r7 = r42
            goto L_0x1043
        L_0x0eb9:
            r7 = r16
            r5 = r20
            r6 = r32
            boolean r8 = r2.equals(r7)
            if (r8 != 0) goto L_0x0ec8
        L_0x0ec5:
            r8 = r22
            goto L_0x0edb
        L_0x0ec8:
            r8 = r22
            r10 = r24
            goto L_0x0ee0
        L_0x0ecd:
            r7 = r16
            r5 = r20
            r8 = r22
            r6 = r32
            boolean r10 = r2.equals(r8)
            if (r10 != 0) goto L_0x0ede
        L_0x0edb:
            r10 = r28
            goto L_0x0eff
        L_0x0ede:
            r10 = r25
        L_0x0ee0:
            r47 = r0
            r22 = r8
            r16 = r10
            r8 = r17
            r0 = r19
            r15 = r27
            r10 = r28
            goto L_0x0f1a
        L_0x0eef:
            r7 = r16
            r5 = r20
            r8 = r22
            r10 = r28
            r6 = r32
            boolean r11 = r2.equals(r10)
            if (r11 != 0) goto L_0x0f0d
        L_0x0eff:
            r47 = r0
            r22 = r8
            r8 = r17
            r0 = r19
            r15 = r27
            r14 = r29
            goto L_0x0e38
        L_0x0f0d:
            r11 = 7
            r47 = r0
            r22 = r8
            r16 = r11
            r8 = r17
            r0 = r19
            r15 = r27
        L_0x0f1a:
            r14 = r29
            r13 = r33
            r11 = r34
            goto L_0x1043
        L_0x0f22:
            r7 = r16
            r5 = r20
            r8 = r22
            r10 = r28
            r6 = r32
            r11 = r34
            boolean r13 = r2.equals(r11)
            if (r13 != 0) goto L_0x0f44
            r47 = r0
            r22 = r8
            r8 = r17
            r0 = r19
            r15 = r27
            r14 = r29
            r13 = r33
            goto L_0x1041
        L_0x0f44:
            r13 = 6
            r47 = r0
            r22 = r8
            r16 = r13
            r8 = r17
            r0 = r19
            r15 = r27
            r14 = r29
            r13 = r33
            goto L_0x1043
        L_0x0f57:
            r7 = r16
            r5 = r20
            r8 = r22
            r10 = r28
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r14 = r2.equals(r13)
            if (r14 != 0) goto L_0x0f6e
            r14 = r29
            goto L_0x0f96
        L_0x0f6e:
            r14 = r26
        L_0x0f70:
            r47 = r0
            r22 = r8
            r16 = r14
            r8 = r17
            r0 = r19
            r15 = r27
            r14 = r29
            goto L_0x1043
        L_0x0f80:
            r7 = r16
            r5 = r20
            r8 = r22
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r15 = r2.equals(r14)
            if (r15 != 0) goto L_0x0fa2
        L_0x0f96:
            r47 = r0
            r22 = r8
            r8 = r17
            r0 = r19
            r15 = r27
            goto L_0x1041
        L_0x0fa2:
            r15 = r27
            r16 = 4
            goto L_0x0fcb
        L_0x0fa7:
            r7 = r16
            r5 = r20
            r8 = r22
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r16 = r2.equals(r15)
            if (r16 != 0) goto L_0x0fc9
            r47 = r0
            r22 = r8
            r8 = r17
            r0 = r19
            goto L_0x1041
        L_0x0fc9:
            r16 = 3
        L_0x0fcb:
            r47 = r0
            r22 = r8
            r8 = r17
            r0 = r19
            goto L_0x1043
        L_0x0fd5:
            r47 = r0
            r7 = r16
            r0 = r19
            r5 = r20
            r8 = r22
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r16 = r2.equals(r0)
            if (r16 != 0) goto L_0x0ff4
            r22 = r8
            goto L_0x1017
        L_0x0ff4:
            r16 = 2
            r22 = r8
            goto L_0x101e
        L_0x0ff9:
            r47 = r0
            r7 = r16
            r8 = r18
            r0 = r19
            r5 = r20
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r16 = r2.equals(r8)
            if (r16 != 0) goto L_0x101a
            r18 = r8
        L_0x1017:
            r8 = r17
            goto L_0x1041
        L_0x101a:
            r16 = 1
            r18 = r8
        L_0x101e:
            r8 = r17
            goto L_0x1043
        L_0x1021:
            r47 = r0
            r7 = r16
            r8 = r17
            r0 = r19
            r5 = r20
            r15 = r27
            r10 = r28
            r14 = r29
            r6 = r32
            r13 = r33
            r11 = r34
            boolean r16 = r2.equals(r8)
            if (r16 != 0) goto L_0x103e
            goto L_0x1041
        L_0x103e:
            r16 = 0
            goto L_0x1043
        L_0x1041:
            r16 = -1
        L_0x1043:
            switch(r16) {
                case 0: goto L_0x109b;
                case 1: goto L_0x1095;
                case 2: goto L_0x108f;
                case 3: goto L_0x1089;
                case 4: goto L_0x1083;
                case 5: goto L_0x107d;
                case 6: goto L_0x1077;
                case 7: goto L_0x1071;
                case 8: goto L_0x106b;
                case 9: goto L_0x1065;
                case 10: goto L_0x105f;
                case 11: goto L_0x1059;
                case 12: goto L_0x1053;
                case 13: goto L_0x104d;
                default: goto L_0x1046;
            }
        L_0x1046:
            r16 = 0
        L_0x1048:
            r17 = r8
            r8 = r16
            goto L_0x10a1
        L_0x104d:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet
            r16.<init>()
            goto L_0x1048
        L_0x1053:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet
            r16.<init>()
            goto L_0x1048
        L_0x1059:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$PathRotateSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$PathRotateSet
            r16.<init>()
            goto L_0x1048
        L_0x105f:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$ElevationSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$ElevationSet
            r16.<init>()
            goto L_0x1048
        L_0x1065:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationSet
            r16.<init>()
            goto L_0x1048
        L_0x106b:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$AlphaSet
            r16.<init>()
            goto L_0x1048
        L_0x1071:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$ScaleYset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$ScaleYset
            r16.<init>()
            goto L_0x1048
        L_0x1077:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$ScaleXset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$ScaleXset
            r16.<init>()
            goto L_0x1048
        L_0x107d:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$ProgressSet r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$ProgressSet
            r16.<init>()
            goto L_0x1048
        L_0x1083:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationZset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationZset
            r16.<init>()
            goto L_0x1048
        L_0x1089:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationYset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationYset
            r16.<init>()
            goto L_0x1048
        L_0x108f:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationXset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$TranslationXset
            r16.<init>()
            goto L_0x1048
        L_0x1095:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationYset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationYset
            r16.<init>()
            goto L_0x1048
        L_0x109b:
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationXset r16 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$RotationXset
            r16.<init>()
            goto L_0x1048
        L_0x10a1:
            if (r8 != 0) goto L_0x10b4
            r19 = r0
            r27 = r6
            r16 = r10
            r34 = r11
            r33 = r13
            r28 = r14
            r20 = r15
            r15 = r7
            goto L_0x11b6
        L_0x10b4:
            r19 = r0
            int r0 = r8.mVariesBy
            r20 = r15
            r15 = 1
            if (r0 != r15) goto L_0x10bf
            r0 = 1
            goto L_0x10c0
        L_0x10bf:
            r0 = 0
        L_0x10c0:
            if (r0 == 0) goto L_0x11a4
            boolean r0 = java.lang.Float.isNaN(r1)
            if (r0 == 0) goto L_0x11a4
            r0 = 2
            float[] r0 = new float[r0]
            r1 = 99
            float r1 = (float) r1
            r15 = 1065353216(0x3f800000, float:1.0)
            float r15 = r15 / r1
            r27 = 0
            r1 = 0
            r16 = 0
            r33 = r13
            r29 = r27
            r31 = r29
            r28 = r14
            r14 = r16
        L_0x10e0:
            r13 = 100
            if (r14 >= r13) goto L_0x119c
            float r13 = (float) r14
            float r13 = r13 * r15
            r16 = r10
            r34 = r11
            double r10 = (double) r13
            r35 = r10
            androidx.constraintlayout.motion.widget.MotionPaths r10 = r4.mStartMotionPath
            androidx.constraintlayout.motion.utils.Easing r10 = r10.mKeyFrameEasing
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r11 = r4.mMotionPaths
            java.util.Iterator r11 = r11.iterator()
            r27 = 0
            r37 = 2143289344(0x7fc00000, float:NaN)
        L_0x10fb:
            boolean r38 = r11.hasNext()
            if (r38 == 0) goto L_0x112d
            java.lang.Object r38 = r11.next()
            r39 = r11
            r11 = r38
            androidx.constraintlayout.motion.widget.MotionPaths r11 = (androidx.constraintlayout.motion.widget.MotionPaths) r11
            r38 = r15
            androidx.constraintlayout.motion.utils.Easing r15 = r11.mKeyFrameEasing
            if (r15 == 0) goto L_0x1128
            r40 = r15
            float r15 = r11.time
            int r41 = (r15 > r13 ? 1 : (r15 == r13 ? 0 : -1))
            if (r41 >= 0) goto L_0x111e
            r27 = r15
            r10 = r40
            goto L_0x1128
        L_0x111e:
            boolean r15 = java.lang.Float.isNaN(r37)
            if (r15 == 0) goto L_0x1128
            float r11 = r11.time
            r37 = r11
        L_0x1128:
            r15 = r38
            r11 = r39
            goto L_0x10fb
        L_0x112d:
            r38 = r15
            if (r10 == 0) goto L_0x114d
            boolean r11 = java.lang.Float.isNaN(r37)
            if (r11 == 0) goto L_0x1139
            r37 = 1065353216(0x3f800000, float:1.0)
        L_0x1139:
            float r13 = r13 - r27
            float r37 = r37 - r27
            float r13 = r13 / r37
            r11 = r6
            r15 = r7
            double r6 = (double) r13
            double r6 = r10.get(r6)
            float r6 = (float) r6
            float r6 = r6 * r37
            float r6 = r6 + r27
            double r6 = (double) r6
            goto L_0x1151
        L_0x114d:
            r11 = r6
            r15 = r7
            r6 = r35
        L_0x1151:
            androidx.constraintlayout.motion.utils.CurveFit[] r10 = r4.mSpline
            r13 = 0
            r10 = r10[r13]
            double[] r13 = r4.mInterpolateData
            r10.getPos((double) r6, (double[]) r13)
            androidx.constraintlayout.motion.widget.MotionPaths r6 = r4.mStartMotionPath
            int[] r7 = r4.mInterpolateVariables
            double[] r10 = r4.mInterpolateData
            r13 = 0
            r6.getCenter(r7, r10, r0, r13)
            if (r14 <= 0) goto L_0x117f
            double r6 = (double) r1
            r1 = 1
            r1 = r0[r1]
            r27 = r11
            double r10 = (double) r1
            double r10 = r29 - r10
            r1 = r0[r13]
            r29 = r14
            double r13 = (double) r1
            double r13 = r31 - r13
            double r10 = java.lang.Math.hypot(r10, r13)
            double r10 = r10 + r6
            float r1 = (float) r10
            r6 = 0
            goto L_0x1184
        L_0x117f:
            r27 = r11
            r29 = r14
            r6 = r13
        L_0x1184:
            r6 = r0[r6]
            double r6 = (double) r6
            r10 = 1
            r10 = r0[r10]
            double r10 = (double) r10
            int r14 = r29 + 1
            r31 = r6
            r29 = r10
            r7 = r15
            r10 = r16
            r6 = r27
            r11 = r34
            r15 = r38
            goto L_0x10e0
        L_0x119c:
            r27 = r6
            r15 = r7
            r16 = r10
            r34 = r11
            goto L_0x11af
        L_0x11a4:
            r27 = r6
            r15 = r7
            r16 = r10
            r34 = r11
            r33 = r13
            r28 = r14
        L_0x11af:
            r8.mType = r2
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r0 = r4.mCycleMap
            r0.put(r2, r8)
        L_0x11b6:
            r0 = r47
            r30 = r3
            r32 = r27
            r29 = r28
            r28 = r16
            r27 = r20
            r20 = r5
            r16 = r15
            goto L_0x0def
        L_0x11c8:
            r15 = r16
            r5 = r20
            r20 = r27
            r16 = r28
            r28 = r29
            r3 = r30
            r27 = r32
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r0 = r4.mKeyList
            java.util.Iterator r0 = r0.iterator()
        L_0x11dc:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x14fe
            java.lang.Object r1 = r0.next()
            androidx.constraintlayout.motion.widget.Key r1 = (androidx.constraintlayout.motion.widget.Key) r1
            boolean r2 = r1 instanceof androidx.constraintlayout.motion.widget.KeyCycle
            if (r2 == 0) goto L_0x14ca
            androidx.constraintlayout.motion.widget.KeyCycle r1 = (androidx.constraintlayout.motion.widget.KeyCycle) r1
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r2 = r4.mCycleMap
            java.util.Objects.requireNonNull(r1)
            java.util.Set r6 = r2.keySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x11fb:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x14ca
            java.lang.Object r7 = r6.next()
            java.lang.String r7 = (java.lang.String) r7
            boolean r8 = r7.startsWith(r3)
            if (r8 == 0) goto L_0x1255
            r8 = 7
            java.lang.String r8 = r7.substring(r8)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r10 = r1.mCustomConstraints
            java.lang.Object r8 = r10.get(r8)
            androidx.constraintlayout.widget.ConstraintAttribute r8 = (androidx.constraintlayout.widget.ConstraintAttribute) r8
            if (r8 == 0) goto L_0x1255
            androidx.constraintlayout.widget.ConstraintAttribute$AttributeType r10 = r8.mType
            androidx.constraintlayout.widget.ConstraintAttribute$AttributeType r11 = androidx.constraintlayout.widget.ConstraintAttribute.AttributeType.FLOAT_TYPE
            if (r10 != r11) goto L_0x1255
            java.lang.Object r10 = r2.get(r7)
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r10 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r10
            int r11 = r1.mFramePosition
            int r13 = r1.mWaveShape
            int r14 = r1.mWaveVariesBy
            r47 = r0
            float r0 = r1.mWavePeriod
            r30 = r3
            float r3 = r1.mWaveOffset
            r22 = r6
            float r6 = r8.getValueToInterpolate()
            java.util.Objects.requireNonNull(r10)
            r29 = r15
            java.util.ArrayList<androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint> r15 = r10.mWavePoints
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint r4 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint
            r4.<init>(r11, r0, r3, r6)
            r15.add(r4)
            r0 = -1
            if (r14 == r0) goto L_0x1250
            r10.mVariesBy = r14
        L_0x1250:
            r10.mWaveShape = r13
            r10.mCustom = r8
            goto L_0x125d
        L_0x1255:
            r47 = r0
            r30 = r3
            r22 = r6
            r29 = r15
        L_0x125d:
            int r0 = r7.hashCode()
            switch(r0) {
                case -1249320806: goto L_0x13d2;
                case -1249320805: goto L_0x13b1;
                case -1225497657: goto L_0x138e;
                case -1225497656: goto L_0x1367;
                case -1225497655: goto L_0x1347;
                case -1001078227: goto L_0x132d;
                case -908189618: goto L_0x1300;
                case -908189617: goto L_0x12de;
                case -40300674: goto L_0x12b3;
                case -4379043: goto L_0x1296;
                case 37232917: goto L_0x128a;
                case 92909918: goto L_0x127b;
                case 156108012: goto L_0x1271;
                default: goto L_0x1264;
            }
        L_0x1264:
            r4 = r16
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r0 = r27
            goto L_0x12a8
        L_0x1271:
            boolean r0 = r7.equals(r5)
            if (r0 != 0) goto L_0x1278
            goto L_0x1264
        L_0x1278:
            r0 = 12
            goto L_0x1286
        L_0x127b:
            boolean r0 = r7.equals(r9)
            if (r0 != 0) goto L_0x1284
        L_0x1281:
            r0 = r27
            goto L_0x129e
        L_0x1284:
            r0 = r21
        L_0x1286:
            r4 = r0
            r0 = r27
            goto L_0x12b0
        L_0x128a:
            boolean r0 = r7.equals(r12)
            if (r0 != 0) goto L_0x1291
            goto L_0x1281
        L_0x1291:
            r3 = r23
            r0 = r27
            goto L_0x12af
        L_0x1296:
            r0 = r27
            boolean r3 = r7.equals(r0)
            if (r3 != 0) goto L_0x12ad
        L_0x129e:
            r4 = r16
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
        L_0x12a8:
            r10 = r28
            r3 = r29
            goto L_0x12c9
        L_0x12ad:
            r3 = r24
        L_0x12af:
            r4 = r3
        L_0x12b0:
            r3 = r29
            goto L_0x12d1
        L_0x12b3:
            r0 = r27
            r3 = r29
            boolean r4 = r7.equals(r3)
            if (r4 != 0) goto L_0x12cf
            r4 = r16
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r10 = r28
        L_0x12c9:
            r8 = r33
            r6 = r34
            goto L_0x13f0
        L_0x12cf:
            r4 = r25
        L_0x12d1:
            r13 = r4
            r4 = r16
            r11 = r20
            r10 = r28
            r8 = r33
            r6 = r34
            goto L_0x1384
        L_0x12de:
            r4 = r16
            r0 = r27
            r3 = r29
            boolean r6 = r7.equals(r4)
            if (r6 != 0) goto L_0x12ed
            r6 = r34
            goto L_0x130e
        L_0x12ed:
            r6 = 7
            r16 = r6
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r10 = r28
            r8 = r33
            r6 = r34
            goto L_0x13f2
        L_0x1300:
            r4 = r16
            r0 = r27
            r3 = r29
            r6 = r34
            boolean r8 = r7.equals(r6)
            if (r8 != 0) goto L_0x131c
        L_0x130e:
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r10 = r28
            r8 = r33
            goto L_0x13f0
        L_0x131c:
            r8 = 6
            r16 = r8
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r10 = r28
            r8 = r33
            goto L_0x13f2
        L_0x132d:
            r4 = r16
            r0 = r27
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r10 = r7.equals(r8)
            if (r10 != 0) goto L_0x1340
            r10 = r28
            goto L_0x1359
        L_0x1340:
            r11 = r20
            r13 = r26
            r10 = r28
            goto L_0x1384
        L_0x1347:
            r4 = r16
            r0 = r27
            r10 = r28
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r11 = r7.equals(r10)
            if (r11 != 0) goto L_0x1363
        L_0x1359:
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            goto L_0x13f0
        L_0x1363:
            r11 = r20
            r13 = 4
            goto L_0x1384
        L_0x1367:
            r4 = r16
            r11 = r20
            r0 = r27
            r10 = r28
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r13 = r7.equals(r11)
            if (r13 != 0) goto L_0x1383
            r15 = r17
            r14 = r18
            r13 = r19
            goto L_0x13f0
        L_0x1383:
            r13 = 3
        L_0x1384:
            r16 = r13
            r15 = r17
            r14 = r18
            r13 = r19
            goto L_0x13f2
        L_0x138e:
            r4 = r16
            r13 = r19
            r11 = r20
            r0 = r27
            r10 = r28
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r14 = r7.equals(r13)
            if (r14 != 0) goto L_0x13a9
            r15 = r17
            r14 = r18
            goto L_0x13f0
        L_0x13a9:
            r14 = 2
            r16 = r14
            r15 = r17
            r14 = r18
            goto L_0x13f2
        L_0x13b1:
            r4 = r16
            r14 = r18
            r13 = r19
            r11 = r20
            r0 = r27
            r10 = r28
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r15 = r7.equals(r14)
            if (r15 != 0) goto L_0x13cc
            r15 = r17
            goto L_0x13f0
        L_0x13cc:
            r15 = 1
            r16 = r15
            r15 = r17
            goto L_0x13f2
        L_0x13d2:
            r4 = r16
            r15 = r17
            r14 = r18
            r13 = r19
            r11 = r20
            r0 = r27
            r10 = r28
            r3 = r29
            r8 = r33
            r6 = r34
            boolean r16 = r7.equals(r15)
            if (r16 != 0) goto L_0x13ed
            goto L_0x13f0
        L_0x13ed:
            r16 = 0
            goto L_0x13f2
        L_0x13f0:
            r16 = -1
        L_0x13f2:
            switch(r16) {
                case 0: goto L_0x144e;
                case 1: goto L_0x1449;
                case 2: goto L_0x1444;
                case 3: goto L_0x143f;
                case 4: goto L_0x143a;
                case 5: goto L_0x1435;
                case 6: goto L_0x1430;
                case 7: goto L_0x142b;
                case 8: goto L_0x1426;
                case 9: goto L_0x1421;
                case 10: goto L_0x141c;
                case 11: goto L_0x1417;
                case 12: goto L_0x1412;
                default: goto L_0x13f5;
            }
        L_0x13f5:
            r32 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r16 = r3
            java.lang.String r3 = "  UNKNOWN  "
            r0.append(r3)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "WARNING! KeyCycle"
            android.util.Log.v(r3, r0)
            r0 = 2143289344(0x7fc00000, float:NaN)
            goto L_0x1454
        L_0x1412:
            r32 = r0
            float r0 = r1.mWaveOffset
            goto L_0x1452
        L_0x1417:
            r32 = r0
            float r0 = r1.mAlpha
            goto L_0x1452
        L_0x141c:
            r32 = r0
            float r0 = r1.mTransitionPathRotate
            goto L_0x1452
        L_0x1421:
            r32 = r0
            float r0 = r1.mElevation
            goto L_0x1452
        L_0x1426:
            r32 = r0
            float r0 = r1.mRotation
            goto L_0x1452
        L_0x142b:
            r32 = r0
            float r0 = r1.mScaleY
            goto L_0x1452
        L_0x1430:
            r32 = r0
            float r0 = r1.mScaleX
            goto L_0x1452
        L_0x1435:
            r32 = r0
            float r0 = r1.mProgress
            goto L_0x1452
        L_0x143a:
            r32 = r0
            float r0 = r1.mTranslationZ
            goto L_0x1452
        L_0x143f:
            r32 = r0
            float r0 = r1.mTranslationY
            goto L_0x1452
        L_0x1444:
            r32 = r0
            float r0 = r1.mTranslationX
            goto L_0x1452
        L_0x1449:
            r32 = r0
            float r0 = r1.mRotationY
            goto L_0x1452
        L_0x144e:
            r32 = r0
            float r0 = r1.mRotationX
        L_0x1452:
            r16 = r3
        L_0x1454:
            boolean r3 = java.lang.Float.isNaN(r0)
            if (r3 != 0) goto L_0x14ac
            java.lang.Object r3 = r2.get(r7)
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r3 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r3
            int r7 = r1.mFramePosition
            r17 = r2
            int r2 = r1.mWaveShape
            r28 = r4
            int r4 = r1.mWaveVariesBy
            r20 = r5
            float r5 = r1.mWavePeriod
            r34 = r6
            float r6 = r1.mWaveOffset
            java.util.Objects.requireNonNull(r3)
            r18 = r1
            java.util.ArrayList<androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint> r1 = r3.mWavePoints
            r29 = r8
            androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint r8 = new androidx.constraintlayout.motion.widget.KeyCycleOscillator$WavePoint
            r8.<init>(r7, r5, r6, r0)
            r1.add(r8)
            r0 = -1
            if (r4 == r0) goto L_0x1488
            r3.mVariesBy = r4
        L_0x1488:
            r3.mWaveShape = r2
            r4 = r43
            r0 = r47
            r19 = r13
            r2 = r17
            r1 = r18
            r5 = r20
            r6 = r22
            r33 = r29
            r3 = r30
            r27 = r32
            r20 = r11
            r18 = r14
            r17 = r15
            r15 = r16
            r16 = r28
            r28 = r10
            goto L_0x11fb
        L_0x14ac:
            r0 = r47
            r34 = r6
            r33 = r8
            r28 = r10
            r20 = r11
            r19 = r13
            r18 = r14
            r17 = r15
            r15 = r16
            r6 = r22
            r3 = r30
            r27 = r32
            r16 = r4
            r4 = r43
            goto L_0x11fb
        L_0x14ca:
            r47 = r0
            r30 = r3
            r14 = r18
            r13 = r19
            r11 = r20
            r32 = r27
            r10 = r28
            r29 = r33
            r20 = r5
            r28 = r16
            r16 = r15
            r15 = r17
            r4 = r43
            r0 = r47
            r19 = r13
            r18 = r14
            r17 = r15
            r15 = r16
            r5 = r20
            r16 = r28
            r33 = r29
            r3 = r30
            r27 = r32
            r28 = r10
            r20 = r11
            goto L_0x11dc
        L_0x14fe:
            r0 = r4
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.KeyCycleOscillator> r0 = r0.mCycleMap
            java.util.Collection r0 = r0.values()
            java.util.Iterator r0 = r0.iterator()
        L_0x1509:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x1519
            java.lang.Object r1 = r0.next()
            androidx.constraintlayout.motion.widget.KeyCycleOscillator r1 = (androidx.constraintlayout.motion.widget.KeyCycleOscillator) r1
            r1.setup()
            goto L_0x1509
        L_0x1519:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionController.setup(int, int, long):void");
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" start: x: ");
        m.append(this.mStartMotionPath.f12x);
        m.append(" y: ");
        m.append(this.mStartMotionPath.f13y);
        m.append(" end: x: ");
        m.append(this.mEndMotionPath.f12x);
        m.append(" y: ");
        m.append(this.mEndMotionPath.f13y);
        return m.toString();
    }

    public MotionController(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            Objects.requireNonNull((ConstraintLayout.LayoutParams) layoutParams);
        }
    }
}
