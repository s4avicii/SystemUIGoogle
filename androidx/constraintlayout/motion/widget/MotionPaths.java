package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public final class MotionPaths implements Comparable<MotionPaths> {
    public static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    public LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap<>();
    public float height;
    public int mDrawPath = 0;
    public Easing mKeyFrameEasing;
    public int mLastMeasuredHeight = 0;
    public int mLastMeasuredWidth = 0;
    public int mMode = 0;
    public int mPathMotionArc = -1;
    public float mPathRotate = Float.NaN;
    public double[] mTempDelta = new double[18];
    public double[] mTempValue = new double[18];
    public float position;
    public float time;
    public float width;

    /* renamed from: x */
    public float f12x;

    /* renamed from: y */
    public float f13y;

    public MotionPaths() {
    }

    public static void setDpDt(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        int[] iArr2 = iArr;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = 0; i < iArr2.length; i++) {
            float f7 = (float) dArr[i];
            double d = dArr2[i];
            int i2 = iArr2[i];
            if (i2 == 1) {
                f4 = f7;
            } else if (i2 == 2) {
                f6 = f7;
            } else if (i2 == 3) {
                f3 = f7;
            } else if (i2 == 4) {
                f5 = f7;
            }
        }
        float f8 = f4 - ((0.0f * f3) / 2.0f);
        float f9 = f6 - ((0.0f * f5) / 2.0f);
        fArr[0] = (((f3 * 1.0f) + f8) * f) + ((1.0f - f) * f8) + 0.0f;
        fArr[1] = (((f5 * 1.0f) + f9) * f2) + ((1.0f - f2) * f9) + 0.0f;
    }

    public final void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        float f = constraint.propertySet.mProgress;
        for (String next : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(next);
            Objects.requireNonNull(constraintAttribute);
            if (constraintAttribute.mType != ConstraintAttribute.AttributeType.STRING_TYPE) {
                this.attributes.put(next, constraintAttribute);
            }
        }
    }

    public final int compareTo(Object obj) {
        return Float.compare(this.position, ((MotionPaths) obj).position);
    }

    public final void getCenter(int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.f12x;
        float f2 = this.f13y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f5 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f5;
            } else if (i3 == 2) {
                f2 = f5;
            } else if (i3 == 3) {
                f3 = f5;
            } else if (i3 == 4) {
                f4 = f5;
            }
        }
        fArr[i] = (f3 / 2.0f) + f + 0.0f;
        fArr[i + 1] = (f4 / 2.0f) + f2 + 0.0f;
    }

    public final void setBounds(float f, float f2, float f3, float f4) {
        this.f12x = f;
        this.f13y = f2;
        this.width = f3;
        this.height = f4;
        this.mLastMeasuredWidth = View.MeasureSpec.makeMeasureSpec((int) f3, 1073741824);
        this.mLastMeasuredHeight = View.MeasureSpec.makeMeasureSpec((int) f4, 1073741824);
    }

    public static boolean diff(float f, float f2) {
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            if (Float.isNaN(f) != Float.isNaN(f2)) {
                return true;
            }
            return false;
        } else if (Math.abs(f - f2) > 1.0E-6f) {
            return true;
        } else {
            return false;
        }
    }

    public MotionPaths(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        int i3;
        KeyPosition keyPosition2 = keyPosition;
        MotionPaths motionPaths3 = motionPaths;
        MotionPaths motionPaths4 = motionPaths2;
        this.mLastMeasuredHeight = motionPaths4.mLastMeasuredHeight;
        this.mLastMeasuredWidth = motionPaths4.mLastMeasuredWidth;
        int i4 = keyPosition2.mPositionType;
        if (i4 == 1) {
            float f2 = ((float) keyPosition2.mFramePosition) / 100.0f;
            this.time = f2;
            this.mDrawPath = keyPosition2.mDrawPath;
            float f3 = Float.isNaN(keyPosition2.mPercentWidth) ? f2 : keyPosition2.mPercentWidth;
            float f4 = Float.isNaN(keyPosition2.mPercentHeight) ? f2 : keyPosition2.mPercentHeight;
            float f5 = motionPaths4.width - motionPaths3.width;
            float f6 = motionPaths4.height - motionPaths3.height;
            this.position = this.time;
            f2 = !Float.isNaN(keyPosition2.mPercentX) ? keyPosition2.mPercentX : f2;
            float f7 = motionPaths3.f12x;
            float f8 = motionPaths3.width;
            float f9 = motionPaths3.f13y;
            float f10 = motionPaths3.height;
            float f11 = ((motionPaths4.width / 2.0f) + motionPaths4.f12x) - ((f8 / 2.0f) + f7);
            float f12 = ((motionPaths4.height / 2.0f) + motionPaths4.f13y) - ((f10 / 2.0f) + f9);
            float f13 = f11 * f2;
            float f14 = f5 * f3;
            float f15 = f14 / 2.0f;
            this.f12x = (float) ((int) ((f7 + f13) - f15));
            float f16 = f2 * f12;
            float f17 = f6 * f4;
            float f18 = f17 / 2.0f;
            this.f13y = (float) ((int) ((f9 + f16) - f18));
            this.width = (float) ((int) (f8 + f14));
            this.height = (float) ((int) (f10 + f17));
            float f19 = Float.isNaN(keyPosition2.mPercentY) ? 0.0f : keyPosition2.mPercentY;
            this.mMode = 1;
            MotionPaths motionPaths5 = motionPaths;
            float f20 = (float) ((int) ((motionPaths5.f12x + f13) - f15));
            float f21 = (float) ((int) ((motionPaths5.f13y + f16) - f18));
            this.f12x = f20 + ((-f12) * f19);
            this.f13y = f21 + (f11 * f19);
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition2.mTransitionEasing);
            this.mPathMotionArc = keyPosition2.mPathMotionArc;
        } else if (i4 != 2) {
            float f22 = ((float) keyPosition2.mFramePosition) / 100.0f;
            this.time = f22;
            this.mDrawPath = keyPosition2.mDrawPath;
            float f23 = Float.isNaN(keyPosition2.mPercentWidth) ? f22 : keyPosition2.mPercentWidth;
            float f24 = Float.isNaN(keyPosition2.mPercentHeight) ? f22 : keyPosition2.mPercentHeight;
            float f25 = motionPaths4.width;
            float f26 = motionPaths3.width;
            float f27 = f25 - f26;
            float f28 = motionPaths4.height;
            float f29 = motionPaths3.height;
            float f30 = f28 - f29;
            this.position = this.time;
            float f31 = motionPaths3.f12x;
            float f32 = motionPaths3.f13y;
            float f33 = ((f25 / 2.0f) + motionPaths4.f12x) - ((f26 / 2.0f) + f31);
            float f34 = ((f28 / 2.0f) + motionPaths4.f13y) - ((f29 / 2.0f) + f32);
            float f35 = f27 * f23;
            float f36 = f35 / 2.0f;
            this.f12x = (float) ((int) (((f33 * f22) + f31) - f36));
            float f37 = f30 * f24;
            float f38 = f37 / 2.0f;
            this.f13y = (float) ((int) (((f34 * f22) + f32) - f38));
            this.width = (float) ((int) (f26 + f35));
            this.height = (float) ((int) (f29 + f37));
            float f39 = Float.isNaN(keyPosition2.mPercentX) ? f22 : keyPosition2.mPercentX;
            float f40 = Float.isNaN(Float.NaN) ? 0.0f : Float.NaN;
            f22 = !Float.isNaN(keyPosition2.mPercentY) ? keyPosition2.mPercentY : f22;
            if (Float.isNaN(Float.NaN)) {
                i3 = 2;
                f = 0.0f;
            } else {
                f = Float.NaN;
                i3 = 2;
            }
            this.mMode = i3;
            this.f12x = (float) ((int) (((f * f34) + ((f39 * f33) + motionPaths3.f12x)) - f36));
            this.f13y = (float) ((int) (((f34 * f22) + ((f33 * f40) + motionPaths3.f13y)) - f38));
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition2.mTransitionEasing);
            this.mPathMotionArc = keyPosition2.mPathMotionArc;
        } else {
            float f41 = ((float) keyPosition2.mFramePosition) / 100.0f;
            this.time = f41;
            this.mDrawPath = keyPosition2.mDrawPath;
            float f42 = Float.isNaN(keyPosition2.mPercentWidth) ? f41 : keyPosition2.mPercentWidth;
            float f43 = Float.isNaN(keyPosition2.mPercentHeight) ? f41 : keyPosition2.mPercentHeight;
            float f44 = motionPaths4.width;
            float f45 = motionPaths3.width;
            float f46 = f44 - f45;
            float f47 = motionPaths4.height;
            float f48 = motionPaths3.height;
            float f49 = f47 - f48;
            this.position = this.time;
            float f50 = motionPaths3.f12x;
            float f51 = motionPaths3.f13y;
            float f52 = (f44 / 2.0f) + motionPaths4.f12x;
            float f53 = (f47 / 2.0f) + motionPaths4.f13y;
            float f54 = f46 * f42;
            this.f12x = (float) ((int) ((((f52 - ((f45 / 2.0f) + f50)) * f41) + f50) - (f54 / 2.0f)));
            float f55 = f49 * f43;
            this.f13y = (float) ((int) ((((f53 - ((f48 / 2.0f) + f51)) * f41) + f51) - (f55 / 2.0f)));
            this.width = (float) ((int) (f45 + f54));
            this.height = (float) ((int) (f48 + f55));
            this.mMode = 3;
            KeyPosition keyPosition3 = keyPosition;
            if (!Float.isNaN(keyPosition3.mPercentX)) {
                this.f12x = (float) ((int) (keyPosition3.mPercentX * ((float) ((int) (((float) i) - this.width)))));
            }
            if (!Float.isNaN(keyPosition3.mPercentY)) {
                this.f13y = (float) ((int) (keyPosition3.mPercentY * ((float) ((int) (((float) i2) - this.height)))));
            }
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition3.mTransitionEasing);
            this.mPathMotionArc = keyPosition3.mPathMotionArc;
        }
    }
}
