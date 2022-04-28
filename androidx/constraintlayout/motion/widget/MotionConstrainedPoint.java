package androidx.constraintlayout.motion.widget;

import android.util.Log;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public final class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    public float alpha = 1.0f;
    public LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap<>();
    public float elevation = 0.0f;
    public float mPathRotate = Float.NaN;
    public float mProgress = Float.NaN;
    public int mVisibilityMode = 0;
    public float rotation = 0.0f;
    public float rotationX = 0.0f;
    public float rotationY = 0.0f;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float translationX = 0.0f;
    public float translationY = 0.0f;
    public float translationZ = 0.0f;
    public int visibility;

    public final int compareTo(Object obj) {
        Objects.requireNonNull((MotionConstrainedPoint) obj);
        return Float.compare(0.0f, 0.0f);
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

    public final void addValues(HashMap<String, SplineSet> hashMap, int i) {
        for (String next : hashMap.keySet()) {
            SplineSet splineSet = hashMap.get(next);
            Objects.requireNonNull(next);
            char c = 65535;
            switch (next.hashCode()) {
                case -1249320806:
                    if (next.equals("rotationX")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1249320805:
                    if (next.equals("rotationY")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1225497657:
                    if (next.equals("translationX")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1225497656:
                    if (next.equals("translationY")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1225497655:
                    if (next.equals("translationZ")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1001078227:
                    if (next.equals("progress")) {
                        c = 5;
                        break;
                    }
                    break;
                case -908189618:
                    if (next.equals("scaleX")) {
                        c = 6;
                        break;
                    }
                    break;
                case -908189617:
                    if (next.equals("scaleY")) {
                        c = 7;
                        break;
                    }
                    break;
                case -40300674:
                    if (next.equals("rotation")) {
                        c = 8;
                        break;
                    }
                    break;
                case -4379043:
                    if (next.equals("elevation")) {
                        c = 9;
                        break;
                    }
                    break;
                case 37232917:
                    if (next.equals("transitionPathRotate")) {
                        c = 10;
                        break;
                    }
                    break;
                case 92909918:
                    if (next.equals("alpha")) {
                        c = 11;
                        break;
                    }
                    break;
            }
            float f = 1.0f;
            float f2 = 0.0f;
            switch (c) {
                case 0:
                    if (!Float.isNaN(this.rotationX)) {
                        f2 = this.rotationX;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 1:
                    if (!Float.isNaN(this.rotationY)) {
                        f2 = this.rotationY;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 2:
                    if (!Float.isNaN(this.translationX)) {
                        f2 = this.translationX;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 3:
                    if (!Float.isNaN(this.translationY)) {
                        f2 = this.translationY;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 4:
                    if (!Float.isNaN(this.translationZ)) {
                        f2 = this.translationZ;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 5:
                    if (!Float.isNaN(this.mProgress)) {
                        f2 = this.mProgress;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case FalsingManager.VERSION /*6*/:
                    if (!Float.isNaN(this.scaleX)) {
                        f = this.scaleX;
                    }
                    splineSet.setPoint(i, f);
                    break;
                case 7:
                    if (!Float.isNaN(this.scaleY)) {
                        f = this.scaleY;
                    }
                    splineSet.setPoint(i, f);
                    break;
                case 8:
                    if (!Float.isNaN(this.rotation)) {
                        f2 = this.rotation;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 9:
                    if (!Float.isNaN(this.elevation)) {
                        f2 = this.elevation;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 10:
                    if (!Float.isNaN(this.mPathRotate)) {
                        f2 = this.mPathRotate;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case QSTileImpl.C1034H.STALE /*11*/:
                    if (!Float.isNaN(this.alpha)) {
                        f = this.alpha;
                    }
                    splineSet.setPoint(i, f);
                    break;
                default:
                    if (!next.startsWith("CUSTOM")) {
                        Log.e("MotionPaths", "UNKNOWN spline " + next);
                        break;
                    } else {
                        String str = next.split(",")[1];
                        if (!this.attributes.containsKey(str)) {
                            Log.e("MotionPaths", "UNKNOWN customName " + str);
                            break;
                        } else {
                            ConstraintAttribute constraintAttribute = this.attributes.get(str);
                            if (!(splineSet instanceof SplineSet.CustomSet)) {
                                Log.e("MotionPaths", next + " splineSet not a CustomSet frame = " + i + ", value" + constraintAttribute.getValueToInterpolate() + splineSet);
                                break;
                            } else {
                                SplineSet.CustomSet customSet = (SplineSet.CustomSet) splineSet;
                                Objects.requireNonNull(customSet);
                                customSet.mConstraintAttributeList.append(i, constraintAttribute);
                                break;
                            }
                        }
                    }
            }
        }
    }

    public final void setState(ConstraintWidget constraintWidget, ConstraintSet constraintSet, int i) {
        float f;
        constraintWidget.getX();
        constraintWidget.getY();
        ConstraintSet.Constraint constraint = constraintSet.get(i);
        ConstraintSet.PropertySet propertySet = constraint.propertySet;
        int i2 = propertySet.mVisibilityMode;
        this.mVisibilityMode = i2;
        int i3 = propertySet.visibility;
        this.visibility = i3;
        if (i3 == 0 || i2 != 0) {
            f = propertySet.alpha;
        } else {
            f = 0.0f;
        }
        this.alpha = f;
        ConstraintSet.Transform transform = constraint.transform;
        boolean z = transform.applyElevation;
        this.elevation = transform.elevation;
        this.rotation = transform.rotation;
        this.rotationX = transform.rotationX;
        this.rotationY = transform.rotationY;
        this.scaleX = transform.scaleX;
        this.scaleY = transform.scaleY;
        this.translationX = transform.translationX;
        this.translationY = transform.translationY;
        this.translationZ = transform.translationZ;
        Easing.getInterpolator(constraint.motion.mTransitionEasing);
        this.mPathRotate = constraint.motion.mPathRotate;
        this.mProgress = constraint.propertySet.mProgress;
        for (String next : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(next);
            Objects.requireNonNull(constraintAttribute);
            if (constraintAttribute.mType != ConstraintAttribute.AttributeType.STRING_TYPE) {
                this.attributes.put(next, constraintAttribute);
            }
        }
    }
}
