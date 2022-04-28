package com.android.systemui.p006qs;

import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.TouchAnimator */
public final class TouchAnimator {
    public static final C10061 POSITION = new FloatProperty<TouchAnimator>() {
        public final Object get(Object obj) {
            return Float.valueOf(((TouchAnimator) obj).mLastT);
        }

        public final void setValue(Object obj, float f) {
            ((TouchAnimator) obj).setPosition(f);
        }
    };
    public final Interpolator mInterpolator;
    public final KeyframeSet[] mKeyframeSets;
    public float mLastT = -1.0f;
    public final Listener mListener;
    public final float mSpan;
    public final float mStartDelay;
    public final Object[] mTargets;

    /* renamed from: com.android.systemui.qs.TouchAnimator$Builder */
    public static class Builder {
        public float mEndDelay;
        public Interpolator mInterpolator;
        public Listener mListener;
        public float mStartDelay;
        public ArrayList mTargets = new ArrayList();
        public ArrayList mValues = new ArrayList();

        public final Builder addFloat(Object obj, String str, float... fArr) {
            Property<?, V> property;
            Class cls = Float.TYPE;
            if (obj instanceof View) {
                char c = 65535;
                switch (str.hashCode()) {
                    case -1225497657:
                        if (str.equals("translationX")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1225497656:
                        if (str.equals("translationY")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1225497655:
                        if (str.equals("translationZ")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -908189618:
                        if (str.equals("scaleX")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -908189617:
                        if (str.equals("scaleY")) {
                            c = 4;
                            break;
                        }
                        break;
                    case -40300674:
                        if (str.equals("rotation")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 120:
                        if (str.equals("x")) {
                            c = 6;
                            break;
                        }
                        break;
                    case 121:
                        if (str.equals("y")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 92909918:
                        if (str.equals("alpha")) {
                            c = 8;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        property = View.TRANSLATION_X;
                        break;
                    case 1:
                        property = View.TRANSLATION_Y;
                        break;
                    case 2:
                        property = View.TRANSLATION_Z;
                        break;
                    case 3:
                        property = View.SCALE_X;
                        break;
                    case 4:
                        property = View.SCALE_Y;
                        break;
                    case 5:
                        property = View.ROTATION;
                        break;
                    case FalsingManager.VERSION:
                        property = View.X;
                        break;
                    case 7:
                        property = View.Y;
                        break;
                    case 8:
                        property = View.ALPHA;
                        break;
                }
            }
            if (!(obj instanceof TouchAnimator) || !"position".equals(str)) {
                property = Property.of(obj.getClass(), cls, str);
            } else {
                property = TouchAnimator.POSITION;
            }
            FloatKeyframeSet floatKeyframeSet = new FloatKeyframeSet(property, fArr);
            this.mTargets.add(obj);
            this.mValues.add(floatKeyframeSet);
            return this;
        }

        public final TouchAnimator build() {
            ArrayList arrayList = this.mTargets;
            Object[] array = arrayList.toArray(new Object[arrayList.size()]);
            ArrayList arrayList2 = this.mValues;
            return new TouchAnimator(array, (KeyframeSet[]) arrayList2.toArray(new KeyframeSet[arrayList2.size()]), this.mStartDelay, this.mEndDelay, this.mInterpolator, this.mListener);
        }
    }

    /* renamed from: com.android.systemui.qs.TouchAnimator$FloatKeyframeSet */
    public static class FloatKeyframeSet<T> extends KeyframeSet {
        public final Property<T, Float> mProperty;
        public final float[] mValues;

        public FloatKeyframeSet(Property<T, Float> property, float[] fArr) {
            super(fArr.length);
            this.mProperty = property;
            this.mValues = fArr;
        }

        public final void interpolate(int i, float f, Object obj) {
            float[] fArr = this.mValues;
            float f2 = fArr[i - 1];
            this.mProperty.set(obj, Float.valueOf(((fArr[i] - f2) * f) + f2));
        }
    }

    /* renamed from: com.android.systemui.qs.TouchAnimator$Listener */
    public interface Listener {
        void onAnimationAtEnd();

        void onAnimationAtStart();

        void onAnimationStarted();
    }

    /* renamed from: com.android.systemui.qs.TouchAnimator$ListenerAdapter */
    public static class ListenerAdapter implements Listener {
        public void onAnimationAtStart() {
        }
    }

    /* renamed from: com.android.systemui.qs.TouchAnimator$KeyframeSet */
    public static abstract class KeyframeSet {
        public final float mFrameWidth;
        public final int mSize;

        public abstract void interpolate(int i, float f, Object obj);

        public KeyframeSet(int i) {
            this.mSize = i;
            this.mFrameWidth = 1.0f / ((float) (i - 1));
        }
    }

    public TouchAnimator(Object[] objArr, KeyframeSet[] keyframeSetArr, float f, float f2, Interpolator interpolator, Listener listener) {
        this.mTargets = objArr;
        this.mKeyframeSets = keyframeSetArr;
        this.mStartDelay = f;
        this.mSpan = (1.0f - f2) - f;
        this.mInterpolator = interpolator;
        this.mListener = listener;
    }

    public final void setPosition(float f) {
        if (!Float.isNaN(f)) {
            float constrain = MathUtils.constrain((f - this.mStartDelay) / this.mSpan, 0.0f, 1.0f);
            Interpolator interpolator = this.mInterpolator;
            if (interpolator != null) {
                constrain = interpolator.getInterpolation(constrain);
            }
            float f2 = this.mLastT;
            if (constrain != f2) {
                Listener listener = this.mListener;
                if (listener != null) {
                    if (constrain == 1.0f) {
                        listener.onAnimationAtEnd();
                    } else if (constrain == 0.0f) {
                        listener.onAnimationAtStart();
                    } else if (f2 <= 0.0f || f2 == 1.0f) {
                        listener.onAnimationStarted();
                    }
                    this.mLastT = constrain;
                }
                int i = 0;
                while (true) {
                    Object[] objArr = this.mTargets;
                    if (i < objArr.length) {
                        KeyframeSet keyframeSet = this.mKeyframeSets[i];
                        Object obj = objArr[i];
                        Objects.requireNonNull(keyframeSet);
                        int constrain2 = MathUtils.constrain((int) Math.ceil((double) (constrain / keyframeSet.mFrameWidth)), 1, keyframeSet.mSize - 1);
                        float f3 = keyframeSet.mFrameWidth;
                        keyframeSet.interpolate(constrain2, (constrain - (((float) (constrain2 - 1)) * f3)) / f3, obj);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
