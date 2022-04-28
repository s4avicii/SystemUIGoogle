package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public final class IntegerKeyframeAnimation extends KeyframeAnimation<Integer> {
    public final int getIntValue(Keyframe<Integer> keyframe, float f) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            keyframe.endFrame.floatValue();
            T t = keyframe.startValue;
            T t2 = keyframe.endValue;
            getLinearCurrentKeyframeProgress();
            Integer num = (Integer) lottieValueCallback.getValueInternal(t, t2);
            if (num != null) {
                return num.intValue();
            }
        }
        if (keyframe.startValueInt == 784923401) {
            keyframe.startValueInt = ((Integer) keyframe.startValue).intValue();
        }
        int i = keyframe.startValueInt;
        if (keyframe.endValueInt == 784923401) {
            keyframe.endValueInt = ((Integer) keyframe.endValue).intValue();
        }
        int i2 = keyframe.endValueInt;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        return (int) ((f * ((float) (i2 - i))) + ((float) i));
    }

    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }

    public IntegerKeyframeAnimation(List<Keyframe<Integer>> list) {
        super(list);
    }
}
