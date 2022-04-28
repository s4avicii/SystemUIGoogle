package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public final class FloatKeyframeAnimation extends KeyframeAnimation<Float> {
    public final float getFloatValue(Keyframe<Float> keyframe, float f) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            keyframe.endFrame.floatValue();
            T t = keyframe.startValue;
            T t2 = keyframe.endValue;
            getLinearCurrentKeyframeProgress();
            Float f2 = (Float) lottieValueCallback.getValueInternal(t, t2);
            if (f2 != null) {
                return f2.floatValue();
            }
        }
        if (keyframe.startValueFloat == -3987645.8f) {
            keyframe.startValueFloat = ((Float) keyframe.startValue).floatValue();
        }
        float f3 = keyframe.startValueFloat;
        if (keyframe.endValueFloat == -3987645.8f) {
            keyframe.endValueFloat = ((Float) keyframe.endValue).floatValue();
        }
        float f4 = keyframe.endValueFloat;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        return MotionController$$ExternalSyntheticOutline0.m7m(f4, f3, f, f3);
    }

    public final Object getValue(Keyframe keyframe, float f) {
        return Float.valueOf(getFloatValue(keyframe, f));
    }

    public final float getFloatValue() {
        return getFloatValue(getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }

    public FloatKeyframeAnimation(List<Keyframe<Float>> list) {
        super(list);
    }
}
